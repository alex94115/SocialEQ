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

import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.projectlaver.batch.communication.BuyerNonPhysicalPaymentCompleted;
import com.projectlaver.batch.communication.BuyerFailedDuplicatePurchase;
import com.projectlaver.batch.communication.BuyerFailedInactiveSale;
import com.projectlaver.batch.communication.BuyerFailedSoldOut;
import com.projectlaver.batch.communication.BuyerPaymentFailed;
import com.projectlaver.batch.communication.BuyerPaymentPending;
import com.projectlaver.batch.communication.BuyerPendingMeansOfPayment;
import com.projectlaver.batch.communication.BuyerPendingShipment;
import com.projectlaver.batch.communication.BuyerPendingShippingAddress;
import com.projectlaver.batch.communication.BuyerPendingUserRegistration;
import com.projectlaver.batch.communication.BuyerPhysicalPurchaseShipped;
import com.projectlaver.batch.communication.BuyerWonGiveaway;
import com.projectlaver.batch.communication.ExternalCommunicationSender;
import com.projectlaver.batch.communication.FacebookCommunicationSender;
import com.projectlaver.batch.communication.NoCommunicationRequired;
import com.projectlaver.batch.communication.StateChangeCommunication;
import com.projectlaver.batch.communication.TwitterCommunicationSender;
import com.projectlaver.batch.domain.MessageStateChangeCommunicationsCursorItem;
import com.projectlaver.integration.SocialProviders;


/**
 *
 * Generates the appropriate twitter message for buyers and sellers based on the state transition. Sends the message
 * using the twitter api. If successful, marks the MessageStateChangeCommunicationsCursorItem as such so that the
 * batch job can update the appropriate column in the message_state_change table.
 *
 * @author alexduff
 *
 */
public class CommunicationsItemProcessor implements ItemProcessor<MessageStateChangeCommunicationsCursorItem, MessageStateChangeCommunicationsCursorItem> {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private ResourceBundleMessageSource messageSource;

	@Autowired
	BuyerPendingUserRegistration buyerPendingUserRegistration;

	@Autowired
	BuyerFailedInactiveSale buyerFailedInactiveSale;

	@Autowired
	BuyerWonGiveaway buyerWonGiveaway;

	@Autowired
	BuyerNonPhysicalPaymentCompleted buyerNonPhysicalPaymentCompleted;

	@Autowired
	BuyerFailedSoldOut buyerFailedSoldOut;

	@Autowired
	BuyerFailedDuplicatePurchase buyerFailedDuplicatePurchase;

	@Autowired
	BuyerPendingMeansOfPayment buyerPendingMeansOfPayment;

	@Autowired
	BuyerPaymentPending buyerPaymentPending;

	@Autowired
	BuyerPendingShipment buyerPendingShipment;

	@Autowired
	BuyerPendingShippingAddress buyerPendingShippingAddress;

	@Autowired
	BuyerPhysicalPurchaseShipped buyerPhysicalPurchaseShipped;

	@Autowired
	BuyerPaymentFailed buyerPaymentFailed;

	@Autowired
	NoCommunicationRequired noCommunicationRequired;

	// communication senders
	TwitterCommunicationSender twitterCommunicationSender;
	
	FacebookCommunicationSender facebookCommunicationSender;

	// Environment variables
	private String schema;

	private Map<String, StateChangeCommunication> buyerStateChangeCommunications;
	private Map<String, StateChangeCommunication> sellerStateChangeCommunications;

	/**
	 * Constructors
	 */

	// Default constructor
	public CommunicationsItemProcessor() {
	}


	/**
	 * Main method used by the batch job to generate the appropriate message and send it via the twitter api.
	 */
	@Override
	public MessageStateChangeCommunicationsCursorItem process( MessageStateChangeCommunicationsCursorItem item ) throws Exception {

		if( this.isKnownDestination( item.getProviderId() ) ) {

			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Handle communications for: " + item.toString() );
			}

			String formattedMessage = null;
			String destinationType = item.getRecipientType();
			String stateTransition = this.formatStateChangeCommunicationKey( item.getPreviousMessageState(), item.getNewMessageState() );

			StateChangeCommunication communication = this.getStateChangeCommunication( destinationType, stateTransition );
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "StateChangeCommunication : " + communication );
			}

			if( communication == null ) {
				this.handleUnexpectedStatus(item );
				
			} else if( !(communication instanceof NoCommunicationRequired ) ) {
				formattedMessage = communication.getFormattedMessage( item );
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "Formatted message: " + formattedMessage );
				}

				if( formattedMessage != null ) {

					ExternalCommunicationSender sender = null;
					if( StringUtils.equalsIgnoreCase( SocialProviders.TWITTER, item.getProviderId() ) ) {
						
						// set the sender to be the TwitterCommunicationSender
						sender = this.twitterCommunicationSender;
						
					} else if( StringUtils.equalsIgnoreCase( SocialProviders.FACEBOOK, item.getProviderId() ) ) {
						
						// set the sender to be the FacebookCommunicationSender
						sender = this.facebookCommunicationSender;
						
					} else {
						throw new RuntimeException( "Unexpected communication providerId: " + item.getProviderId() );
					}

					Boolean didCommunicationsComplete = sender.sendExternalCommunication( item, formattedMessage );
					item.setDidCommunicationsComplete( didCommunicationsComplete );

				} else {
					throw new RuntimeException( "No formatted message was returned by : " + communication );
				}

			} else {
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "Judged to be a \"no communication situation\" for status change: " + stateTransition );
				}

				// Must be a NoCommunicationRequired
				item.setDidCommunicationsComplete( true );
			}

		} else {
			throw new RuntimeException( "Unknown communications destination provider: " + item.getProviderId() );
		}

		return item;
	}

	/**
	 * Setter injection of the schema system property.
	 * @param schema
	 */
	public void setSchema( String schema ) {
		this.schema = schema;
	}
	
	/**
	 * Setter injection of the twitter and fb senders.
	 */
	
	public void setTwitterCommunicationSender( TwitterCommunicationSender twitterCommunicationSender ) {
		this.twitterCommunicationSender = twitterCommunicationSender;
	}

	public void setFacebookCommunicationSender( FacebookCommunicationSender facebookCommunicationSender ) {
		this.facebookCommunicationSender = facebookCommunicationSender;
	}
	
	/**
	 * Setter for unit testing.
	 *
	 * @param messageSource
	 */
	public void setMessageSource( ResourceBundleMessageSource messageSource ) {
		this.messageSource = messageSource;
	}

	String getCurrencySymbol( Locale locale ) {
		Currency currency = Currency.getInstance( locale );
		return currency.getSymbol();
	}

	String formatScreenName( String userId ) {
		int index = userId.indexOf( "/" );
		if( index > 0 ) {
			return userId.substring( index + 1 );
		}

		return userId;
	}

	void handleUnexpectedStatus( MessageStateChangeCommunicationsCursorItem item ) {
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Choked on item: " + ToStringBuilder.reflectionToString( item ) );
		}
		throw new RuntimeException( "Unexpected message status change FROM: " + item.getPreviousMessageState() + " TO: " + item.getNewMessageState() );
	}

	synchronized void initializeBuyerStateChangeCommunications() {
		if( this.buyerStateChangeCommunications == null ) {
			this.buyerStateChangeCommunications = new HashMap<String,StateChangeCommunication>();

			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PROCESSING", "PENDING_USER_REGISTRATION" ), this.buyerPendingUserRegistration );

			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PROCESSING", "FAILED_INACTIVE_SALE" ), this.buyerFailedInactiveSale );

			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PROCESSING", "FAILED_SOLD_OUT" ), this.buyerFailedSoldOut );

			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PROCESSING", "FAILED_DUPLICATE_PURCHASE" ), this.buyerFailedDuplicatePurchase );

			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PROCESSING", "PENDING_MEANS_OF_PAYMENT" ), this.buyerPendingMeansOfPayment );

			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PROCESSING", "PAYMENT_PROCESSING" ), this.noCommunicationRequired );
			
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PROCESSING", "PENDING_LISTING_EXPIRY" ), this.noCommunicationRequired );

			// for giveaway campaigns, digital goods winners go from PENDING_LISTING_EXPIRY->COMPLETED
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PENDING_LISTING_EXPIRY", "COMPLETED" ), this.buyerWonGiveaway );

			// for giveaway campaigns, physical goods winners go from PENDING_LISTING_EXPIRY->PENDING_SHIPPING_ADDRESS or PENDING_SHIPMENT
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PENDING_LISTING_EXPIRY", "PENDING_SHIPPING_ADDRESS" ), this.buyerWonGiveaway );
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PENDING_LISTING_EXPIRY", "PENDING_SHIPMENT" ), this.buyerWonGiveaway );

			// immediate failures
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PROCESSING", "PAYMENT_FAILED" ), this.buyerPaymentFailed );
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_PROCESSING", "PAYMENT_FAILED" ), this.buyerPaymentFailed );
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_PENDING", "PAYMENT_FAILED" ), this.buyerPaymentFailed );

			// if the payment becomes pending
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_PROCESSING", "PAYMENT_PENDING" ), this.buyerPaymentPending );

			// any initial state becoming PENDING_SHIPPING_ADDRESS
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PROCESSING", "PENDING_SHIPPING_ADDRESS" ), this.buyerPendingShippingAddress );
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PENDING_USER_REGISTRATION", "PENDING_SHIPPING_ADDRESS" ), this.buyerPendingShippingAddress );
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PENDING_MEANS_OF_PAYMENT", "PENDING_SHIPPING_ADDRESS" ), this.buyerPendingShippingAddress );
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_FAILED", "PENDING_SHIPPING_ADDRESS" ), this.buyerPendingShippingAddress );
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_PROCESSING", "PENDING_SHIPPING_ADDRESS" ), this.buyerPendingShippingAddress );
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_PENDING", "PENDING_SHIPPING_ADDRESS" ), this.buyerPendingShippingAddress );

			// any initial state becoming PENDING_SHIPMENT			
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PROCESSING", "PENDING_SHIPMENT" ), this.buyerPendingShipment );
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PENDING_USER_REGISTRATION", "PENDING_SHIPMENT" ), this.buyerPendingShipment );
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PENDING_MEANS_OF_PAYMENT", "PENDING_SHIPMENT" ), this.buyerPendingShipment );
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_FAILED", "PENDING_SHIPMENT" ), this.buyerPendingShipment );
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_PROCESSING", "PENDING_SHIPMENT" ), this.buyerPendingShipment );
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_PENDING", "PENDING_SHIPMENT" ), this.buyerPendingShipment );

			// For payments that jump straight to COMPLETED, it cannot be a physical good
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PROCESSING", "COMPLETED" ), this.buyerNonPhysicalPaymentCompleted );
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_PROCESSING", "COMPLETED" ), this.buyerNonPhysicalPaymentCompleted );
			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_PENDING", "COMPLETED" ), this.buyerNonPhysicalPaymentCompleted );

			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PENDING_SHIPPING_ADDRESS", "PENDING_SHIPMENT" ), this.noCommunicationRequired );

			this.buyerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PENDING_SHIPMENT", "COMPLETED" ), this.buyerPhysicalPurchaseShipped );
			
			// put all the no-state transitions into a map with "no comms required"
			this.buyerStateChangeCommunications.putAll( this.createNoStateChangeMap() );

		}
	}
	
	/**
	 * Not sending messages to sellers at the moment -- all of these state transitions 
	 * are configured to be "noCommunicationRequired".
	 */
	synchronized void initializeSellerStateChangeCommunications() {
		if( this.sellerStateChangeCommunications == null ) {
			this.sellerStateChangeCommunications = new HashMap<String,StateChangeCommunication>();

			this.sellerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PROCESSING", "PENDING_USER_REGISTRATION" ), this.noCommunicationRequired );

			this.sellerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PROCESSING", "FAILED_INACTIVE_SALE" ), this.noCommunicationRequired );

			this.sellerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PROCESSING", "FAILED_SOLD_OUT" ), this.noCommunicationRequired );

			this.sellerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PROCESSING", "PENDING_MEANS_OF_PAYMENT" ), this.noCommunicationRequired );

			this.sellerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PROCESSING", "PAYMENT_PROCESSING" ), this.noCommunicationRequired );

			this.sellerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_PROCESSING", "PAYMENT_PENDING" ), this.noCommunicationRequired );

			this.sellerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_PROCESSING", "COMPLETED" ), this.noCommunicationRequired );
			this.sellerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_PENDING", "COMPLETED" ), this.noCommunicationRequired );

			this.sellerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_PROCESSING", "PENDING_SHIPPING_ADDRESS" ), this.noCommunicationRequired );
			this.sellerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_PENDING", "PENDING_SHIPPING_ADDRESS" ), this.noCommunicationRequired );

			this.sellerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_PROCESSING", "PENDING_SHIPMENT" ), this.noCommunicationRequired );
			this.sellerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_PENDING", "PENDING_SHIPMENT" ), this.noCommunicationRequired );
			this.sellerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PENDING_SHIPPING_ADDRESS", "PENDING_SHIPMENT" ), this.noCommunicationRequired );

			this.sellerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PENDING_SHIPMENT", "COMPLETED" ), this.noCommunicationRequired );

			this.sellerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_PROCESSING", "PAYMENT_FAILED" ), this.noCommunicationRequired );
			this.sellerStateChangeCommunications.put( this.formatStateChangeCommunicationKey(
					"PAYMENT_PENDING", "PAYMENT_FAILED" ), this.noCommunicationRequired );
			
			// put all the no-state transitions into a map with "no comms required"
			this.sellerStateChangeCommunications.putAll( this.createNoStateChangeMap() );

		}
	}

	String formatStateChangeCommunicationKey( String fromState, String toState ) {
		StringBuffer sb = new StringBuffer( fromState );
		sb.append( "->" );
		sb.append( toState );

		return sb.toString();
	}

	StateChangeCommunication getStateChangeCommunication( String destinationType, String stateTransition ) {
		if( (this.sellerStateChangeCommunications == null) || (this.buyerStateChangeCommunications == null) ) {
			this.initializeBuyerStateChangeCommunications();
			this.initializeSellerStateChangeCommunications();
		}

		StateChangeCommunication result = null;
		if( StringUtils.equalsIgnoreCase( destinationType, "BUYER" )) {
			result = this.buyerStateChangeCommunications.get( stateTransition );
		} else if( StringUtils.equalsIgnoreCase( destinationType, "SELLER" ) ) {
			result = this.sellerStateChangeCommunications.get( stateTransition );
		} else {
			throw new RuntimeException( "Unexpected destinationType: " + destinationType );
		}

		return result;
	}

	/**
	 * Twitter and Facebook are supported
	 *
	 * @param destinationProviderId
	 * @return
	 */
	Boolean isKnownDestination( String destinationProviderId ) {
		return ( StringUtils.equalsIgnoreCase( destinationProviderId, SocialProviders.TWITTER ) ||
				StringUtils.equalsIgnoreCase( destinationProviderId, SocialProviders.FACEBOOK ) );
	}
	
	/**
	 * Add all the no-state transitions into a map with "No Communication Required".
	 * @return
	 */
	Map<String, StateChangeCommunication> createNoStateChangeMap() {
		Map<String, StateChangeCommunication>  result = new HashMap<String, StateChangeCommunication>();
		
		result.put( this.formatStateChangeCommunicationKey(
				"PROCESSING", "PROCESSING" ), this.noCommunicationRequired );
		result.put( this.formatStateChangeCommunicationKey(
				"IRRELEVANT", "IRRELEVANT" ), this.noCommunicationRequired );
		result.put( this.formatStateChangeCommunicationKey(
				"PENDING_USER_REGISTRATION", "PENDING_USER_REGISTRATION" ), this.noCommunicationRequired );
		result.put( this.formatStateChangeCommunicationKey(
				"PENDING_MEANS_OF_PAYMENT", "PENDING_MEANS_OF_PAYMENT" ), this.noCommunicationRequired );
		result.put( this.formatStateChangeCommunicationKey(
				"PENDING_LISTING_EXPIRY", "PENDING_LISTING_EXPIRY" ), this.noCommunicationRequired );
		result.put( this.formatStateChangeCommunicationKey(
				"FAILED_SOLD_OUT", "FAILED_SOLD_OUT" ), this.noCommunicationRequired );
		result.put( this.formatStateChangeCommunicationKey(
				"FAILED_INACTIVE_SALE", "FAILED_INACTIVE_SALE" ), this.noCommunicationRequired );
		result.put( this.formatStateChangeCommunicationKey(
				"FAILED_DUPLICATE_PURCHASE", "FAILED_DUPLICATE_PURCHASE" ), this.noCommunicationRequired );
		result.put( this.formatStateChangeCommunicationKey(
				"FAILED_DUPLICATE_OPT_IN", "FAILED_DUPLICATE_OPT_IN" ), this.noCommunicationRequired );
		result.put( this.formatStateChangeCommunicationKey(
				"FAILED_LOST_GIVEAWAY", "FAILED_LOST_GIVEAWAY" ), this.noCommunicationRequired );
		result.put( this.formatStateChangeCommunicationKey(
				"PAYMENT_PROCESSING", "PAYMENT_PROCESSING" ), this.noCommunicationRequired );
		result.put( this.formatStateChangeCommunicationKey(
				"PAYMENT_PENDING", "PAYMENT_PENDING" ), this.noCommunicationRequired );
		result.put( this.formatStateChangeCommunicationKey(
				"PAYMENT_FAILED", "PAYMENT_FAILED" ), this.noCommunicationRequired );
		result.put( this.formatStateChangeCommunicationKey(
				"PENDING_SHIPPING_ADDRESS", "PENDING_SHIPPING_ADDRESS" ), this.noCommunicationRequired );
		result.put( this.formatStateChangeCommunicationKey(
				"PENDING_SHIPMENT", "PENDING_SHIPMENT" ), this.noCommunicationRequired );
		result.put( this.formatStateChangeCommunicationKey(
				"ACTIVE", "ACTIVE" ), this.noCommunicationRequired );
		result.put( this.formatStateChangeCommunicationKey(
				"CANCELED", "CANCELED" ), this.noCommunicationRequired );
		result.put( this.formatStateChangeCommunicationKey(
				"COMPLETED", "COMPLETED" ), this.noCommunicationRequired );
		result.put( this.formatStateChangeCommunicationKey(
				"PROCESSING_ERROR", "PROCESSING_ERROR" ), this.noCommunicationRequired );
		
		return result;
	}

}
