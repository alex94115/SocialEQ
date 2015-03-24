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
package com.projectlaver.batch;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.projectlaver.batch.domain.UpdatedInventoryAttributes;
import com.projectlaver.integration.SocialProviders;
import com.projectlaver.util.AttemptPaymentItemProcessor;
import com.projectlaver.util.CommunicationChannel;
import com.projectlaver.util.GoodsType;
import com.projectlaver.util.PaymentItem;
import com.projectlaver.util.PaymentStatus;
import com.projectlaver.util.PurchaseAttemptMessageCursorItem;
import com.projectlaver.util.VoucherDTO;
import com.projectlaver.util.VoucherService;

public class GiveawayWinnerItemProcessor extends AttemptPaymentItemProcessor 
	implements ItemProcessor<PurchaseAttemptMessageCursorItem, PaymentItem> {

	private static final String SELECT_GIVEAWAY_LISTING_ATTRIBUTES_SQL = 
			  " SELECT i.version currentVersion, (i.version + 1) updatedVersion, i.quantity, i.remainingQuantity "
			+ " FROM Listings l "
			+ "   INNER JOIN Inventories i ON l.id = i.listing_id "
			+ " WHERE l.id = ? ";
	
	@Autowired
	private DataSource datasource;
	
	@Autowired
	private VoucherService voucherService;

	private final Log logger = LogFactory.getLog(getClass());


	@Override
	public PaymentItem process( final PurchaseAttemptMessageCursorItem cursorItem ) throws Exception {

		// Giveaways have a 'payment' tying together the winner & lister, but there's no revenue
		BigDecimal grossPaymentAmount = null;
		BigDecimal netRodLaverAmount = null;
		BigDecimal netSellerAmount = null;
		
		PaymentItem paymentItem = super.initializeCommonBatchPaymentItem( cursorItem, grossPaymentAmount, netRodLaverAmount, netSellerAmount );
		paymentItem.setPayKey( "WON_GIVEAWAY_NO_PAYMENT" );
		paymentItem.setNewEffectivePaymentStatus( PaymentStatus.COMPLETED );
		
		// Set a serial number onto the Payment if this listing is selling a voucher
		GoodsType goodsType = cursorItem.getGoodsType();
		
		if( goodsType.equals( GoodsType.VOUCHER ) ) {
			
			// Create the DTO and set the inputs
			VoucherDTO voucherDto = new VoucherDTO();
			voucherDto.setClientLogoUrl( cursorItem.getClientLogoUrl() );
			voucherDto.setClientCampaignImageUrl( cursorItem.getClientCampaignImageUrl() );
			voucherDto.setMerchantName( cursorItem.getMerchantName() );
			voucherDto.setListingTitle( cursorItem.getListingTitle() );
			voucherDto.setVoucherDetails( cursorItem.getListingLongDescription() );
			voucherDto.setVoucherTerms( cursorItem.getListingTerms() );
			voucherDto.setProductCode( cursorItem.getInventoryProductCode() );
			voucherDto.setProductDescription( cursorItem.getInventoryProductDescription() );

			
			// create the voucher
			voucherDto = this.voucherService.createVoucher( voucherDto );
			
			// Put the DTO in a list
			List<VoucherDTO> vouchers = new ArrayList<VoucherDTO>();
			vouchers.add( voucherDto );
			
			// Add the voucher list to the batch payment item
			paymentItem.setVouchers( vouchers );
		}

		paymentItem.setDoesRequireBuyerCommunication( true );
		paymentItem.setChannel( cursorItem.getProviderId().equals( SocialProviders.FACEBOOK ) ? CommunicationChannel.FACEBOOK : CommunicationChannel.TWEET );

		// Set the new message status based on the type of giveaway and whether there's an address
		if( goodsType.equals( GoodsType.DIGITAL ) || goodsType.equals( GoodsType.VOUCHER ) ) {
			
			// digital giveaway winning messages go straight to completed
			paymentItem.setNewMessageStatus( "COMPLETED" );
			paymentItem.setDoesRequireSellerCommunication( false );

		} else {
			
			if( cursorItem.getIsAddressPrimary() ) {
				// physical giveaway winning messages with a primary address go to pending shipment
				paymentItem.setNewMessageStatus( "PENDING_SHIPMENT" );
				paymentItem.setDoesRequireSellerCommunication( true  );

			} else {
				// physical giveaway winning messages without an address go to pending shipping address
				paymentItem.setNewMessageStatus( "PENDING_SHIPPING_ADDRESS" );
				paymentItem.setDoesRequireSellerCommunication( false );
			}
		}

		/**
		 * These listing attributes are initialized in the superclass, but may be stale coming from the cursor.
		 * Need to read them myself and override the values.
		 */

		JdbcTemplate template = new JdbcTemplate( datasource );

		UpdatedInventoryAttributes updatedInventoryAttributes = (UpdatedInventoryAttributes)template.queryForObject(
				SELECT_GIVEAWAY_LISTING_ATTRIBUTES_SQL,
				new Object[] { cursorItem.getListingId() },
				new RowMapper<UpdatedInventoryAttributes>() {

					@Override
					public UpdatedInventoryAttributes mapRow(ResultSet rs, int rowNum) throws SQLException {
						UpdatedInventoryAttributes result = new UpdatedInventoryAttributes();
						result.setCurrentVersion( rs.getInt( "currentVersion") );
						result.setUpdatedVersion( rs.getInt( "updatedVersion") );

						// Do some gymnastics here to figure out if we are dealing with a limited quantity item or not
						
						// Check the listing quantity. If it's null, rs.getInt will return 0. Correct this to be null.
						int totalQuantity = rs.getInt( "quantity" );
						if( totalQuantity == 0 ) {
							result.setQuantity( null );
						} else {
							result.setQuantity( totalQuantity );
						}
						
						// Only set the remainingQuantity if the listingQuantity is not null
						if( result.getQuantity() != null ) {
							result.setRemainingQuantity( rs.getInt( "remainingQuantity" ) );
						}

						return result;
					}

				});


		paymentItem.setInventoryVersion( updatedInventoryAttributes.getCurrentVersion() );
		paymentItem.setNewInventoryVersion( updatedInventoryAttributes.getUpdatedVersion() );

		// manage the quantity if this is an item with a limited quantity (i.e., not null remaining quantity)
		if( updatedInventoryAttributes.getRemainingQuantity() != null ) {
			super.decrementQuantityAvailable( updatedInventoryAttributes.getRemainingQuantity(), paymentItem );
		}

		return paymentItem;
	}

}
