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
package com.projectlaver.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectlaver.domain.Address;
import com.projectlaver.domain.EffectiveDatedEntity;
import com.projectlaver.domain.EffectivePaymentStatus;
import com.projectlaver.domain.EffectiveVoucherStatus;
import com.projectlaver.domain.Inventory;
import com.projectlaver.domain.Listing;
import com.projectlaver.domain.Message;
import com.projectlaver.domain.MessageStateChange;
import com.projectlaver.domain.Payment;
import com.projectlaver.domain.Preapproval;
import com.projectlaver.domain.User;
import com.projectlaver.domain.Voucher;
import com.projectlaver.integration.SocialProviders;
import com.projectlaver.repository.BulkOperationsRepository;
import com.projectlaver.util.AttemptPaymentDTO;
import com.projectlaver.util.CommunicationChannel;
import com.projectlaver.util.GoodsType;
import com.projectlaver.util.InitiatedPaymentDTO;
import com.projectlaver.util.MessageStatus;
import com.projectlaver.util.PaymentProvider;
import com.projectlaver.util.PaymentQueryResultDTO;
import com.projectlaver.util.PaymentStatus;
import com.projectlaver.util.PreapprovalStatus;
import com.projectlaver.util.PreapprovalType;
import com.projectlaver.util.VoucherDTO;
import com.projectlaver.util.VoucherService;
import com.projectlaver.util.VoucherStatus;

@Service
@Transactional(readOnly = false)
public class PaymentProviderService {

	@Autowired
	private BulkOperationsRepository bulkMessageOperationsRepository;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private MessageStateChangeService messageStateChangeService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private PaymentProvider paymentProvider;
	
	@Autowired
	private PreapprovalService preapprovalService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VoucherService voucherService;
	
	private final Log logger = LogFactory.getLog(getClass());
	
	// properties
	
	/**
	 * While braintree payment nonces are good for 24 hours,
	 * this value controls when an incomplete payment is considered
	 * abandoned, which releases it for sale again
	 */
	private Integer payKeyValidDurationMinutes = 5; 
	
	/**
	 * Static variables
	 */
	public static final String PAYPAL_DATE_MASK = "yyyy-MM-dd";
	

	@Transactional(readOnly = true)
	public Map<String, String> requestPreapproval() {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "+++requestPreapproval()" );
		}
		
		Map<String, String>  result = this.paymentProvider.requestPreapproval( null, null );
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "---requestPreapproval(), preapprovalKey: %s, correlationId: %s", result.get("preapprovalKey"), result.get( "correlationId" ) ) );
		}
		
		return result;
	}
	
	@Transactional(readOnly = false)
	public Preapproval completePreapproval( String preapprovalKey, String correlationId  ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "+++completePreapproval(), preapprovalKey: %s, correlationId: %s", preapprovalKey, correlationId ) );
		}
		
		Preapproval result = null;
		try {
			Map<String, Object> completePreapprovalResult = this.paymentProvider.completePreapproval( preapprovalKey );
		
			if( completePreapprovalResult != null ) {
				Boolean isPreapprovalApproved = (Boolean) completePreapprovalResult.get( "isPreapprovalApproved" );
				PreapprovalStatus preapprovalStatus = PreapprovalStatus.valueOf( (String)completePreapprovalResult.get( "preapprovalStatus" ) ); 
				Date startingDate = (Date) completePreapprovalResult.get( "startingDate" );
				Date endingDate = (Date) completePreapprovalResult.get( "endingDate" );
				PreapprovalType preapprovalType = (PreapprovalType) completePreapprovalResult.get( "preapprovalType" );
				
				if( isPreapprovalApproved ) {
					// Save the Preapproval Key and Correlation Id
					result = this.preapprovalService.create( preapprovalKey, correlationId, preapprovalType, preapprovalStatus, startingDate, endingDate );
					
					// Did we get an email address from Paypal?
					String paypalEmail = ( String )completePreapprovalResult.get( "paypalEmail" );
					
					if( StringUtils.isNotBlank( paypalEmail )) {
						User user = this.userService.getCurrentUser();
						// If the two email addresses are different, save the one from Paypal
						if( !StringUtils.equalsIgnoreCase( paypalEmail, user.getEmailAddress() )) {
							user.setEmailAddress( paypalEmail );
							this.userService.update( user );
						}
					}
				}
			}
		} catch( RuntimeException e ) {
			this.logger.error( "Error while attempting to complete the preapproval with key:" + preapprovalKey );
			throw e;
		}
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "---completePreapproval(), result: %s", ToStringBuilder.reflectionToString( result ) ) );
		}
		
		return result;
	}
	
	@Transactional(readOnly = false)
	public InitiatedPaymentDTO startCheckout( User buyer, User seller, Listing listing, String productCode, Integer quantity ) { 
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "+++startCheckout(), buyer id: %d, seller id: %d, listing id: %d, product code: %s, quantity: %d", 
					buyer.getId(), seller.getId(), listing.getId(), productCode, quantity ) );
		}
		
		AttemptPaymentDTO dto = this.createInteractiveCheckoutAttemptPayment( seller, listing, productCode, quantity ); 
		
		// make sure this user has a pending purchase or failed payment message
		Message replyMessage = this.messageService.findPendingMeansOfPaymentMessage( listing.getId(), buyer.getId() );
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "startCheckout() found an existing related reply message: %s", ReflectionToStringBuilder.toString( replyMessage )) );
		}
		
		InitiatedPaymentDTO result = null;
		
		if( replyMessage != null ) {
			try {
				dto = this.paymentProvider.initiateCheckout( dto );
				
				if( dto.getDidInitiateCheckoutSucceed() ) {
					
					// create and persist a payment; modify the listing quantity & status if applicable 
					BigDecimal netSellerAmount = dto.getNetSellerAmount();
					BigDecimal netRodLaverAmount = dto.getNetRodLaverAmount();
					
					// set the payment's initial effective payment status
					EffectivePaymentStatus effectivePaymentStatus = this.createInitialEffectivePaymentStatus();

					// If this is a limited quantity listing, decrement the available quantity
					Inventory inventory = listing.getInventory( productCode );
					
					if( !inventory.isUnlimitedQuantity() ) {
						Boolean didDecrementInventory = this.bulkMessageOperationsRepository.decrementInventory( inventory, quantity );
						
						/**
						 *  If didDecrementInventory is false, this means that there wasn't sufficient inventory left
						 *  to allocate to this buyer.
						 *  
						 *  Use an early return of "null" to indicate that start checkout failed here.
						 */
						if( !didDecrementInventory ) {
							result = new InitiatedPaymentDTO();
							result.setStartCheckoutErrorReason( "INSUFFICIENT_QUANTITY" );
						}
					}
					
					// only continue if there were no errors above
					if( result == null ) {
						
						// create the payment 
						Payment payment = new Payment( 
								replyMessage, 
								listing,
								inventory,
								quantity,
								buyer, 
								seller, 
								dto.getPayKey(), 
								dto.getCorrelationId(), 
								netRodLaverAmount, 
								netSellerAmount, 
								dto.getGrossPaymentAmount(),
								effectivePaymentStatus );
						
						this.paymentService.save( payment );		
						
						// create an inProgressPayment object, which will be stored in this user's session
						result = this.createInProgressPayment( dto, listing.getId(), inventory.getId(), payment.getId() );
						
					}
				}
				
			} catch( Exception e ) {
				this.logger.error( "Error while trying to initiate checkout for buyer: " + buyer.getUsername() + " and listing id: " + listing.getId() );
				throw new RuntimeException( "Could not initiate checkout.", e );
			}
		} else {
			// no existing payment-intent reply message
			throw new RuntimeException( String.format( "Tried to initiate checkout for user with id: %d and listing: %d, but there was no pending payment reply message found.", buyer.getId(), listing.getId() ) );
		}
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "---startCheckout(): %s", ToStringBuilder.reflectionToString( result ) ) );
		}
		
		return result;
	}

	@Transactional(readOnly = false)
	public Map<String, Object> completeCheckout( InitiatedPaymentDTO initiatedPayment ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "+++completeCheckout with inProgressPayment: %s", ToStringBuilder.reflectionToString( initiatedPayment )));
		}
		
		Long buyerId = this.userService.getCurrentUserId();
		Payment payment = this.paymentService.findById( initiatedPayment.getPaymentId() );
		
		if( this.didFindValidRelatedPayment( payment, buyerId ) ) {
			throw new RuntimeException( "Unexpected condition in completeCheckout(). Payment: " + ToStringBuilder.reflectionToString( payment ) + ", buyerId: " + buyerId );
		}
		
		PaymentQueryResultDTO paymentQueryResultDTO = null; 
		Address shippingAddress = null;
		if( payment.getMessage() != null ) {
			
			Map<String, Object> paymentStatusResult = this.paymentProvider.completeCheckout( initiatedPayment, payment );
			
			if( paymentStatusResult != null ) {
				paymentQueryResultDTO = (PaymentQueryResultDTO) paymentStatusResult.get( "paymentQueryResultDTO" );
				shippingAddress = ( Address )paymentStatusResult.get( "shippingAddress" );
			}
			
		} else {
			throw new RuntimeException( String.format( "Unexpected condition in completeCheckout -- could not find associated message for payment id: %d", initiatedPayment.getPaymentId() ) );
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		if( paymentQueryResultDTO != null ) {

			PaymentStatus updatedPaymentStatus = paymentQueryResultDTO.getPaymentStatus();

			/**
			 * We retrieved the payment from Paypal. If its status is not CREATED, we need to update 
			 * the already-created payment in the database. The 'else' branch here just returns null for 
			 * the payment object and lets the controller re-route to the checkout page.
			 */
			if( !PaymentStatus.CREATED.equals( updatedPaymentStatus ) ) {
				
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "Payment status is not CREATED; since it has status: " + updatedPaymentStatus + 
							", updating the existing payment's (id: " + payment.getId() + ") status in the database." );
				}
			
				/**
				 *  Map the payment provider fields to the payment object
				 */
				payment.setPayKey( paymentQueryResultDTO.getPayKey() );
				payment.setCorrelationId( paymentQueryResultDTO.getCorrelationId() );
				payment.setPayerId( paymentQueryResultDTO.getPayerId() );
				payment.setAuthorizationTransactionId( paymentQueryResultDTO.getAuthorizationTransactionId() );

				/**
				 * Create a new effective payment status, which will end date the existing CREATED
				 * payment and become the new current status.
				 */
				EffectivePaymentStatus updatedEps = new EffectivePaymentStatus( 
						new Date(), EffectiveDatedEntity.END_OF_TIME_DATE, updatedPaymentStatus, payment );
				
				payment.appendEffectivePaymentStatus( updatedEps );
				
				GoodsType goodsType = payment.getListing().getGoodsType();
				if( goodsType.equals( GoodsType.VOUCHER )) {
					
					List<Voucher> vouchers = new ArrayList<Voucher>();
					for( int i = 0; i < payment.getQuantity(); i++ ) {
					
						vouchers.add( this.createVoucher( payment ) );
					}
					payment.setVouchers( vouchers );
				}
				
				/**
				 * Persist the payment and associated status(es)
				 */
				if( shippingAddress != null ) {
					this.paymentService.savePaymentWithAddress( payment, shippingAddress );
				} else {
					this.paymentService.save( payment );
				}
	
				// add the payment to the result map
				result.put( "payment", payment );
				
				// get the updated message status
				MessageStatus updatedMessageStatus = this.getUpdatedMessageStatus( updatedEps.getStatus(), shippingAddress != null, payment.getListing() );
				
				// insert Message State Change rows
				Message replyMessage = payment.getMessage();
				String messageProviderId = replyMessage.getProviderId();
				
				CommunicationChannel channel = messageProviderId.equals( SocialProviders.FACEBOOK ) ? 
					CommunicationChannel.FACEBOOK : CommunicationChannel.TWEET;
				
				List<MessageStateChange> mscs = this.createCheckoutCompleteMscs( replyMessage, channel, replyMessage.getStatus(), updatedMessageStatus );
				this.messageStateChangeService.insert( mscs );
				
				// update the message status
				replyMessage.setStatus( updatedMessageStatus );
				replyMessage.getMessageStateChanges().addAll( mscs );
				this.messageService.update( replyMessage );
				
				// if we have a preapproval key, check on the preapproval's status
				String preapprovalKey = initiatedPayment.getPreapprovalKey();
				if( preapprovalKey != null ) {
					Preapproval preapproval = this.completePreapproval( preapprovalKey, null );
					result.put( "preapproval", preapproval );
				}
			} else {
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "Payment status is CREATED, did not update the existing payment with id: " + payment.getId() );
				}
			}
		}
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "---completeCheckout with result: %s", ToStringBuilder.reflectionToString( result )));
		}

		
		return result;
	}

	Voucher createVoucher( Payment payment ) {
		
		User seller = payment.getPayee();
		Listing listing = payment.getListing();
		Inventory inventory = payment.getInventory();
		
		// create the voucher dto object and populate its inputs
		VoucherDTO dto = new VoucherDTO();
		dto.setClientLogoUrl( seller.getVoucherLogoImageUrl() );
		dto.setClientCampaignImageUrl( seller.getVoucherCampaignImageUrl() );
		dto.setMerchantName( seller.getMerchantName() );
		
		dto.setListingTitle( listing.getTitle() );
		dto.setVoucherDetails( listing.getLongDescription() );
		dto.setVoucherTerms( listing.getTermsAndConditions() );
		dto.setProductCode( inventory.getProductCode() );
		dto.setProductDescription( inventory.getDescription() );

		// use the voucher service to create the voucher
		dto = this.voucherService.createVoucher( dto );
		
		// assign the voucher attributes to a new voucher
		Voucher voucher = new Voucher();
		voucher.setSerialNumber( dto.getSerialNumber() );
		voucher.setSalt( dto.getSalt() );
		voucher.setFilename( dto.getFilename() );
		
		// set the back pointer to the associated payment
		voucher.setPayment( payment );
		
		// Add an effective voucher status of unredeemed
		EffectiveVoucherStatus evs = new EffectiveVoucherStatus(  new Date(), EffectiveDatedEntity.END_OF_TIME_DATE, VoucherStatus.UNREDEEMED, voucher );
		voucher.appendEffectiveVoucherStatus( evs );
		
		return voucher;
	}

	
	/**
	 * Internal methods
	 */

	
	/**
	 * Records that are related to a Message that has had an important state change. In 
	 * this case, that the user has gone through live checkout.
	 * 
	 * @param message
	 * @param previousState
	 * @param newState
	 * @return
	 */
	List<MessageStateChange> createCheckoutCompleteMscs( Message message, CommunicationChannel channel, MessageStatus previousState, MessageStatus newState ) {
		
		List<MessageStateChange> changes = new ArrayList<MessageStateChange>();
		
		// Social Channel MSC -- don't send any communications since the user is with us live
		MessageStateChange socialChange = new MessageStateChange();
		socialChange.setMessage( message );
		socialChange.setPreviousState( previousState );
		socialChange.setNewState( newState );
		socialChange.setCommunicationChannel( channel );
		socialChange.setDoesRequireBuyerCommunication( false );
		socialChange.setAreBuyerCommunicationsComplete( false );
		socialChange.setDoesRequireSellerCommunication( false );
		socialChange.setAreSellerCommunicationsComplete( false );
		socialChange.setStatus( "PENDING" );
		changes.add( socialChange );
		
		// Email Channel MSC -- send a receipt to the buyer
		MessageStateChange emailChange = new MessageStateChange();
		emailChange.setMessage( message );
		emailChange.setPreviousState( previousState );
		emailChange.setNewState( newState );
		emailChange.setCommunicationChannel( CommunicationChannel.EMAIL );
		emailChange.setDoesRequireBuyerCommunication( true );
		emailChange.setAreBuyerCommunicationsComplete( false );
		emailChange.setDoesRequireSellerCommunication( true );
		emailChange.setAreSellerCommunicationsComplete( false );
		emailChange.setStatus( "PENDING" );
		changes.add( emailChange );
		
		return changes;
	}
	
	EffectivePaymentStatus createInitialEffectivePaymentStatus() {
		
		EffectivePaymentStatus effectivePaymentStatus = new EffectivePaymentStatus();
		effectivePaymentStatus.setStatus( PaymentStatus.CREATED );
		// start is now
		effectivePaymentStatus.setStart( new Date() );
		
		// set the end to when the payKey expires
		Date epsEnd = new Date();
		epsEnd.setTime( System.currentTimeMillis() + ( this.payKeyValidDurationMinutes * 60 * 1000 ) );
		effectivePaymentStatus.setEnd( epsEnd );
		
		effectivePaymentStatus.setVersion( 0 );

		return effectivePaymentStatus;
	}
	
	PaymentStatus getUpdatedPaymentStatus( String ipnStatus ) {
			
		PaymentStatus result = null;
		
		switch( ipnStatus ) {
			case "Canceled_Reversal": //A reversal has been canceled. For example, you won a dispute with the customer, and the funds for the transaction that was reversed have been returned to you.
				result = PaymentStatus.REVERSED;
				break;
			case "Completed":  //The payment has been completed, and the funds have been added successfully to your account balance.
				result = PaymentStatus.COMPLETED;
				break;
			case "Created":  //A German ELV payment is made using Express Checkout.
				result = PaymentStatus.CREATED;
				break;
			case "Denied":  //The payment was denied. This happens only if the payment was previously pending because of one of the reasons listed for the pending_reason variable or the Fraud_Management_Filters_x variable.
				result = PaymentStatus.DENIED;
				break;
			case "Expired":  //This authorization has expired and cannot be captured.
				result = PaymentStatus.FAILED; 
				break;
			case "Failed":  //The payment has failed. This happens only if the payment was made from your customer's bank account.
				result = PaymentStatus.FAILED; 
				break;
			case "Pending":  //The payment is pending. See pending_reason for more information.
				result = PaymentStatus.PENDING;
				break;
			case "Refunded":  //You refunded the payment.
				result = PaymentStatus.REFUNDED;
				break;
			case "Reversed":  //A payment was reversed due to a chargeback or other type of reversal. The funds have been removed from your account balance and returned to the buyer. The reason for the reversal is specified in the ReasonCode element.
				result = PaymentStatus.REVERSED;
				break;
			case "Processed":  //A payment has been accepted.
				result = PaymentStatus.PROCESSING;
				break;
			case "Voided":  //This authorization has been voided.
				result = PaymentStatus.FAILED;
				break;
			default:
				result = PaymentStatus.UNKNOWN;
				break;
		}
		
		return result;
	}
	
	InitiatedPaymentDTO createInProgressPayment( AttemptPaymentDTO dto, Long listingId, Long inventoryId, Long paymentId ) {
		InitiatedPaymentDTO result;
		result = new InitiatedPaymentDTO();
		result.setListingId( listingId );
		result.setInventoryId( inventoryId );
		result.setPaymentId( paymentId );

		// Paypal Properties
		result.setPayKey( dto.getPayKey() );
		result.setPreapprovalKey( dto.getPreapprovalKey() );
		// add y minutes * 60 sec / min * 1000 ms / sec to the current time to be the expiration time
		result.setPayKeyExpiration( System.currentTimeMillis() + ( this.payKeyValidDurationMinutes * 60 * 1000 ) ); 

		// Provider properties
		result.setProviderClientToken( dto.getProviderClientToken() );
		
		return result;
	}

	AttemptPaymentDTO createInteractiveCheckoutAttemptPayment(User seller, Listing listing, String productCode, Integer quantity ) { //, String returnUrl, String cancelUrl) {
		AttemptPaymentDTO dto = new AttemptPaymentDTO();
		dto.setPaymentAmount( listing.getAmount().multiply( new BigDecimal( quantity ) ) );
		dto.setSellerRevenueRatio( listing.getSellerRevenueRatio() );
		dto.setDoUseChainedPayment( seller.getDoUseChainedPayments() );
		dto.setSellerEmail( seller.getEmailAddress() );
		dto.setDoUseChainedPayment( seller.getDoUseChainedPayments() );
		dto.setListingType( listing.getType() );
		dto.setGoodsType( listing.getGoodsType() );
		dto.setMemo( this.paymentProvider.getMemo( seller.getScreenFormattedUsername(), listing.getTitle(), listing.getKeyword() ));
		dto.setPayKeyDurationMinutes( this.payKeyValidDurationMinutes );
		return dto;
	}

	MessageStatus getUpdatedMessageStatus( PaymentStatus paymentStatus, Boolean hasAddress, Listing listing ) {
		
		MessageStatus result = null;
		
		if( paymentStatus.equals( PaymentStatus.PAYMENT_PROCESSING ) || paymentStatus.equals( PaymentStatus.PROCESSING ) ) {
			result = MessageStatus.PAYMENT_PROCESSING;
		} else if( paymentStatus.equals( PaymentStatus.PENDING ) ) {
			result = MessageStatus.PAYMENT_PENDING;
		} else if( paymentStatus.equals( PaymentStatus.COMPLETED ) ) {
			
			GoodsType goodsType = listing.getGoodsType();
			
			// Digital download or Voucher sale --> no need for an address
			if( goodsType.equals( GoodsType.DIGITAL ) || goodsType.equals( GoodsType.VOUCHER ) ) {
				result = MessageStatus.COMPLETED;
				
			// Tangible item sale; check address / shipping status
			} else if( goodsType.equals( GoodsType.PHYSICAL )) {
				if( hasAddress ) {
					result = MessageStatus.PENDING_SHIPMENT;
				} else {
					result = MessageStatus.PENDING_SHIPPING_ADDRESS;
				}
				
			// Any other goods type
			} else {
				throw new RuntimeException( "Unexpected goods type: " + goodsType );
			}
			
		} else {
			result = MessageStatus.PAYMENT_FAILED;
		}
		
		return result;
	}
	
	Boolean didFindValidRelatedPayment( Payment payment, Long buyerId ) {
		
		if( payment == null || payment.getPayer() == null || payment.getPayer().getId() == null ) {
			return false;
		}
		
		Long payerId = payment.getPayer().getId(); 
		if( !payment.getId().equals( payerId ) ) {
			return false;
		}
		
		return true;
		
	}

	
}
