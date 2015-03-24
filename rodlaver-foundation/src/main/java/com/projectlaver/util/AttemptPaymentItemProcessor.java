/*******************************************************************************
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 PURPLE & GOLD, INC
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.projectlaver.util;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.projectlaver.domain.Listing;
import com.projectlaver.integration.SocialProviders;

/**
 * @author alexduff
 *
 */
public class AttemptPaymentItemProcessor implements ItemProcessor<PurchaseAttemptMessageCursorItem, PaymentItem> {

	/**
	 * Instance variables
	 */

	@Autowired
	private DataSource datasource;
	
	@Autowired
	private PaymentProvider paymentProvider;
	
	@Autowired
	private VoucherService voucherService;
	
	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * Environment properties
	 */
	private String schema;

	/**
	 * Static variables
	 */

	/**
	 * Query to get the updated listing status, version, and quantity attributes.
	 * Also locks the listing temporarily while we attempt a payment so that
	 * we never: 1) get an optimistic locking failure on the version number
	 * changes, and 2) don't sell out an item that has a limited quantity.
	 */
	private static final String SELECT_UPDATED_LISTING_ATTRIBUTES_AND_LOCK_ROW = 
			  " SELECT i.version currentVersion, "
			+ "   (i.version + 1) newVersion, "
			+ "   i.remainingQuantity, "
			+ "   (i.remainingQuantity - 1) newRemainingQuantity "
			+ " FROM Inventories i "
			+ " WHERE i.listing_id = ? "
			+ " LOCK IN SHARE MODE ";

	// Fail-safe to ensure the user can't (accidentally) purchase the same item twice
	private static final String COUNT_DUPLICATE_PURCHASES = " SELECT count( cp.payment_id ) "
			 											  + " FROM CurrentPaymentStatus cp "
														  + " WHERE cp.payer_id = ? "
														  + "   AND cp.listing_id = ? "
														  + "   AND cp.status != 'DENIED' "
														  + "   AND cp.status != 'FAILED' ";

	public void setSchema( String schema ) {
		this.schema = schema;
	}
	
	/**
	 * Main method used by the batch job to handle validated "buy" or "opt-in" messages. Intent
	 * is to create a payment with the initial status.
	 */
	public PaymentItem process( PurchaseAttemptMessageCursorItem cursorItem ) throws Exception {

		PaymentItem paymentItem = null;
		JdbcTemplate template = new JdbcTemplate( datasource );

		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "+++process(), listingId: %d, listingType: %s, remainingQuantity: %d",
					cursorItem.getListingId(), cursorItem.getListingType(), cursorItem.getInventoryRemainingQuantity() ) );
		}

		// Get the updated listing attributes and create an exclusive write lock 
		// on the listing so we attempt payment without having the listing change in the meantime
		Map<String, Object> updatedListingAttributes = this.updateListingAttributesAndLockRow( template, cursorItem.getListingId() );
		this.updateCursorItemListingAttributes( cursorItem, updatedListingAttributes );
			
		
		// Make sure the listing has not sold out since the cursor driving this Processor was created
		if( this.isListingStillAvailable( cursorItem.getInventoryRemainingQuantity() ) ) {

			// make sure the user hasn't bought this item already
			Integer count = this.countDuplicatePurchases( template, cursorItem.getBuyerId(), cursorItem.getListingId() );
			
			if( count == 0 ) {
				paymentItem = this.batchAttemptPayment( cursorItem );
			} else {
				// This user has already purchased this item. Cause it to be skipped -- the next run will mark it duplicate.
				throw new DuplicatePurchaseAttemptException();
			}

		} else {
			// Has just sold out or been canceled
			throw new ListingNotActiveException();
		}

		return paymentItem;
	}

	protected void decrementQuantityAvailable( Integer currentQuantityRemaining, PaymentItem batchPaymentItem ) {
		int updatedQuantityAvailable = currentQuantityRemaining - 1;
		batchPaymentItem.setUpdatedQuantityAvailable( updatedQuantityAvailable );
	}


	protected PaymentItem initializeCommonBatchPaymentItem( 
			PurchaseAttemptMessageCursorItem batchMessageItem, 
			BigDecimal grossPaymentAmount,
			BigDecimal netRodLaverAmount,
			BigDecimal netSellerAmount ) {

		PaymentItem batchPaymentItem = new PaymentItem();

		// 1. Payment Attributes
		batchPaymentItem.setPayerId( batchMessageItem.getBuyerId() );
		batchPaymentItem.setPayeeId( batchMessageItem.getSellerId() );
		batchPaymentItem.setListingId( batchMessageItem.getListingId() );
		
		// If this is a real payment, the amounts are non-null. For giveaways, skip the amounts.
		if( batchMessageItem.getListingAmount() != null ) {
			
			// TODO Ticket-#465 -- Internationalization
			batchPaymentItem.setCurrencyCode( "USD" );
			
			// batchPaymentItem.amount represents the total for this payment (i.e., the listing price)
			batchPaymentItem.setAmount( batchMessageItem.getListingAmount() );
			batchPaymentItem.setRodLaverAmount( netRodLaverAmount );
			batchPaymentItem.setSellerAmount( netSellerAmount );
		}

		// only set the shipping address if IS PRIMARY is true.
		// (note that no result for address is giving address id = 0 in the batchMessageItem(), which could lead to a bug).
		if( batchMessageItem.getIsAddressPrimary() != null && batchMessageItem.getIsAddressPrimary() ) {
			batchPaymentItem.setShippingAddressId( batchMessageItem.getAddressId() );
		}

		// 2. Message attributes
		batchPaymentItem.setMessageId( batchMessageItem.getMessageId() );
		batchPaymentItem.setPriorMessageStatus( batchMessageItem.getMessageStatus() );

		int currentMessageVersion = batchMessageItem.getMessageVersion();
		batchPaymentItem.setMessageVersion( currentMessageVersion );
		batchPaymentItem.setNewMessageVersion( currentMessageVersion + 1);

		batchPaymentItem.setDoesRequireSellerCommunication( false );

		// 3. Inventory attributes
		batchPaymentItem.setInventoryId( batchMessageItem.getInventoryId() );
		batchPaymentItem.setInventoryVersion( batchMessageItem.getInventoryVersion() );
		batchPaymentItem.setNewInventoryVersion( batchMessageItem.getInventoryNewVersion() );

		// 4. User attributes
		batchPaymentItem.setProviderId( batchMessageItem.getProviderId() );
		batchPaymentItem.setProviderUserId( batchMessageItem.getProviderUserId() );

		return batchPaymentItem;
	}
	
	/**
	 * Internal helper methods
	 */
	
	/**
	 * Used by the batch job to attempt a payment on an item.
	 * 
	 * @param batchCursorItem
	 * @param currentQuantityRemaining
	 * @return
	 * @throws Exception
	 */
	PaymentItem batchAttemptPayment( PurchaseAttemptMessageCursorItem batchCursorItem ) throws Exception  {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "+++batchAttemptPayment(), batchPaymentItem: %s", 
					ToStringBuilder.reflectionToString( batchCursorItem ) ) );
		}
		
		AttemptPaymentDTO dto = new AttemptPaymentDTO();
		
		// batch payments only go in quantity of one, so the payment amount will be the listing amount
		dto.setPaymentAmount( batchCursorItem.getListingAmount() );
		dto.setSellerRevenueRatio( batchCursorItem.getListingSellerRevenueRatio() );
		dto.setSellerId( batchCursorItem.getSellerId() );
		dto.setSellerEmail( batchCursorItem.getSellerEmail() );
		dto.setDoUseChainedPayment( batchCursorItem.getDoUseChainedPayment() );
		dto.setListingType( batchCursorItem.getListingType() );
		dto.setGoodsType( batchCursorItem.getGoodsType() );
		dto.setPreapprovalKey( batchCursorItem.getPreapprovalKey() );
		dto.setMemo( this.paymentProvider.getMemo( batchCursorItem.getSellerUsername(), batchCursorItem.getListingTitle(), batchCursorItem.getListingKeyword() ));
		dto.setReplyId( batchCursorItem.getMessageId() );
		
		dto = this.paymentProvider.attemptPaymentWithPreapprovalKey( dto );
		
		PaymentItem batchPaymentItem = null;
		
		// attempt payment succeeded
		if( dto.getCorrelationId() != null && dto.getPaymentStatus().equals( PaymentStatus.COMPLETED )) {
			
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( String.format( "attemptPaymentWithPreapprovalKey appears to have succeeded. payKey: %s, correlationId: %s, paymentStatus: %s", 
						dto.getPayKey(), dto.getCorrelationId(), dto.getPaymentStatus() ) );
			}
			
			// Create the Payment object that will be returned
			BigDecimal grossPaymentAmount = dto.getGrossPaymentAmount();
			BigDecimal netRodLaverAmount = dto.getNetRodLaverAmount();
			BigDecimal netSellerAmount = dto.getNetSellerAmount();
			batchPaymentItem = this.initializeCommonBatchPaymentItem( batchCursorItem, grossPaymentAmount, netRodLaverAmount, netSellerAmount );

			/**
			 *  1. Payment attributes
			 */
			batchPaymentItem.setPayKey( dto.getPayKey() );
			batchPaymentItem.setCorrelationId( dto.getCorrelationId() );
			batchPaymentItem.setNewEffectivePaymentStatus( dto.getPaymentStatus() );
			
			// If this listing is selling a voucher, create it
			if( dto.getGoodsType().equals( GoodsType.VOUCHER ) ) {
				
				// Create the DTO and set the inputs
				VoucherDTO voucherDto = new VoucherDTO();
				voucherDto.setClientLogoUrl( batchCursorItem.getClientLogoUrl() );
				voucherDto.setClientCampaignImageUrl( batchCursorItem.getClientCampaignImageUrl() );
				voucherDto.setMerchantName( batchCursorItem.getMerchantName() );
				voucherDto.setListingTitle( batchCursorItem.getListingTitle() );
				voucherDto.setVoucherDetails( batchCursorItem.getListingLongDescription() );
				voucherDto.setVoucherTerms( batchCursorItem.getListingTerms() );
				voucherDto.setProductCode( batchCursorItem.getInventoryProductCode() );
				voucherDto.setProductDescription( batchCursorItem.getInventoryProductDescription() );

				// create the voucher voucher (this step assigns the key attributes to the voucherDto)
				voucherDto = this.voucherService.createVoucher( voucherDto );

				// Put the DTO in a list
				List<VoucherDTO> vouchers = new ArrayList<VoucherDTO>();
				vouchers.add( voucherDto );
				
				// Add the voucher list to the batch payment item
				batchPaymentItem.setVouchers( vouchers );
				
			}

			/**
			 *  2. Message attributes
			 */
			
			/**
			 * Set the new message status.
			 * 
			 * With PayPal, we set the message status to PAYMENT_PROCESSING because the IPN notification would update it. But 
			 * with Braintree, there is no known webhook for this purpose, so we need to set the message status based on the
			 * payment status (in effect, the message status goes to COMPLETED for digital items and PENDING SHIPPING ADDRESS
			 * or PENDING SHIPMENT for physical items).
			 */
			String newMessageStatus = this.getUpdatedMessageStatus( dto.getPaymentStatus(), batchCursorItem.getIsAddressPrimary(), batchCursorItem.getGoodsType() );
			batchPaymentItem.setNewMessageStatus( newMessageStatus );
			batchPaymentItem.setDoesRequireBuyerCommunication( true );
			batchPaymentItem.setChannel( batchCursorItem.getProviderId().equals( SocialProviders.FACEBOOK ) ? CommunicationChannel.FACEBOOK : CommunicationChannel.TWEET );

			/**
			 *  3. Inventory attributes
			 */

			// manage the quantity if this is an item with a limited quantity (i.e., not null remaining quantity)
			if( batchCursorItem.getInventoryRemainingQuantity() != null ) {
				this.decrementQuantityAvailable( batchCursorItem.getInventoryRemainingQuantity(), batchPaymentItem );
			}

		// attempt payment failed (invalid ack), but no exception thrown
		} else {
			
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( String.format( "attemptPaymentWithPreapprovalKey appears to have failed. payKey: %s, correlationId: %s, paymentStatus: %s", 
						dto.getPayKey(), dto.getCorrelationId(), dto.getPaymentStatus() ) );
			}
			
			batchPaymentItem = this.initializeCommonBatchPaymentItem( batchCursorItem, dto.getGrossPaymentAmount(), dto.getNetRodLaverAmount(), dto.getNetSellerAmount() );

			// payment attributes
			batchPaymentItem.setPayKey( dto.getPayKey() );
			batchPaymentItem.setCorrelationId( dto.getCorrelationId() );
			batchPaymentItem.setAuthorizationTransactionId( dto.getAuthorizationTransactionId() );
			batchPaymentItem.setNewEffectivePaymentStatus( dto.getPaymentStatus() );


			// message attributes
			batchPaymentItem.setNewMessageStatus( "PAYMENT_FAILED" );
			batchPaymentItem.setDoesRequireBuyerCommunication( true );

			// inventory attributes -- status quo
			if( batchCursorItem.getInventoryRemainingQuantity() != null ) {
				batchPaymentItem.setUpdatedQuantityAvailable( batchCursorItem.getInventoryRemainingQuantity() );
			}
		}
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "---batchAttemptPayment(), batchPaymentItem: %s", ToStringBuilder.reflectionToString( batchPaymentItem ) ) );
		}
		
		return batchPaymentItem;
	}

	Integer countDuplicatePurchases( JdbcTemplate template, Long buyerUserId, Long listingId ) {
		Object[] parametersArray = new Object[] { buyerUserId, listingId };
		int[] parameterTypes = { Types.BIGINT, Types.BIGINT };
		Integer count = template.queryForObject( COUNT_DUPLICATE_PURCHASES, parametersArray, parameterTypes, Integer.class );
		return count;
	}
	
	/**
	 * Reads the up-to-date attributes of this listing and locks the row in share mode
	 * so that it won't get changed from under us while the paypal payment is attempted.
	 * 
	 * @param jdbcTemplate
	 * @param listingId
	 * @return
	 */
	Map<String, Object> updateListingAttributesAndLockRow( JdbcTemplate jdbcTemplate, final Long listingId ) {

		Map<String, Object> result = jdbcTemplate.query(
			SELECT_UPDATED_LISTING_ATTRIBUTES_AND_LOCK_ROW, 
			new PreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setLong( 1, listingId );
				}
				
			}, 
			new ResultSetExtractor<Map<String, Object>>() {

				@Override
				public Map<String, Object> extractData(ResultSet rs) throws SQLException, DataAccessException {
					
					Map<String, Object> result = null;
					if( rs.next() ) {
						result = new HashMap<String, Object>(); 
								
						result.put( "currentVersion", rs.getInt( "currentVersion" ));
						result.put( "newVersion", rs.getInt( "newVersion" ));
						result.put( "remainingQuantity", rs.getObject( "remainingQuantity" ));
						result.put( "newRemainingQuantity", rs.getObject( "newRemainingQuantity" ));
					}
					
					return result;
				}
				
			});
		
		return result;
	}
	
	void updateCursorItemListingAttributes( 
			PurchaseAttemptMessageCursorItem cursorItem, 
			Map<String, Object> updatedListingAttributes ) {

		cursorItem.setInventoryVersion( ( Integer )updatedListingAttributes.get( "currentVersion" ));
		cursorItem.setInventoryNewVersion( ( Integer )updatedListingAttributes.get( "newVersion" ));
		cursorItem.setInventoryRemainingQuantity( ( Integer )updatedListingAttributes.get( "remainingQuantity" ));
	}
	
	/** 
	 * Is the listing still ACTIVE and is there still an item for sale?
	 */
	//Boolean isListingStillAvailable( String listingStatus, Integer currentQuantityRemaining) {
	Boolean isListingStillAvailable( Integer currentQuantityRemaining) {
		
		// If the listing has a remaining quantity and it is not at least 1, it is not available
		if( currentQuantityRemaining != null && currentQuantityRemaining < 1 ) {
			return false;
		}
		
		// otherwise, return true
		return true;
	}
	
	String getUpdatedMessageStatus( PaymentStatus paymentStatus, Boolean hasPrimaryAddress, GoodsType goodsType ) {
		
		// by default...
		MessageStatus result = MessageStatus.PAYMENT_FAILED;
		
		if( paymentStatus.equals( PaymentStatus.PAYMENT_PROCESSING ) || paymentStatus.equals( PaymentStatus.PROCESSING ) ) {
			result = MessageStatus.PAYMENT_PROCESSING;
		} else if( paymentStatus.equals( PaymentStatus.PENDING ) ) {
			result = MessageStatus.PAYMENT_PENDING;
		} else if( paymentStatus.equals( PaymentStatus.COMPLETED ) ) {
			
			// Digital download or Voucher sale --> no need for an address
			if( goodsType.equals( GoodsType.DIGITAL ) || goodsType.equals( GoodsType.VOUCHER ) ) {
				result = MessageStatus.COMPLETED;
				
			// Tangible item sale; check address / shipping status
			} else if( goodsType.equals( GoodsType.PHYSICAL )) {
				if( hasPrimaryAddress ) {
					result = MessageStatus.PENDING_SHIPMENT;
				} else {
					result = MessageStatus.PENDING_SHIPPING_ADDRESS;
				}
				
			// Any other goods type
			} else {
				throw new RuntimeException( "Unexpected goods type: " + goodsType );
			}
			
		} 
		
		return result.toString();
	}

}


