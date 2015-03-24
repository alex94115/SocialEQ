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

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.projectlaver.batch.communication.BuyerNonPhysicalPaymentCompleted;
import com.projectlaver.batch.communication.BuyerFailedInactiveSale;
import com.projectlaver.batch.communication.BuyerFailedSoldOut;
import com.projectlaver.batch.communication.BuyerPaymentFailed;
import com.projectlaver.batch.communication.BuyerPaymentPending;
import com.projectlaver.batch.communication.BuyerPendingMeansOfPayment;
import com.projectlaver.batch.communication.BuyerPendingShipment;
import com.projectlaver.batch.communication.BuyerPendingShippingAddress;
import com.projectlaver.batch.communication.BuyerPendingUserRegistration;
import com.projectlaver.batch.communication.BuyerPhysicalPurchaseShipped;
import com.projectlaver.batch.communication.StateChangeCommunication;
import com.projectlaver.batch.domain.MessageStateChangeCommunicationsCursorItem;
import com.projectlaver.integration.SocialProviders;
import com.projectlaver.util.GoodsType;
import com.projectlaver.util.ListingType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/simple-job-launcher-context.xml",
									"/jobs/rodLaverJob.xml" })
public class CommunicationsItemProcessorTest {

	private static final String BASE_CHECKOUT_URL 		= "http://localhost:8080/rodlaver-core/listing/checkout/";
	private static final String BASE_PAYMENT_DETAIL_URL	= "http://localhost:8080/rodlaver-core/listing/transactionDetail/";
	
	@Test
	public void testBuyerPendingUserRegistration() {
		CommunicationsItemProcessor processor = this.createCommunicationsItemProcessor();
		
		MessageStateChangeCommunicationsCursorItem item = new MessageStateChangeCommunicationsCursorItem();
		item.setRecipientType( "BUYER" );
		item.setPreviousMessageState("PROCESSING");
		item.setNewMessageState("PENDING_USER_REGISTRATION");
		item.setListingLocale( Locale.US );
		item.setProviderId( SocialProviders.TWITTER );
		item.setListingId( 100L );
		
		String expectedMessage = "To complete your transaction click here: http://localhost:8080/rodlaver-core/listing/checkout/100";
		
		String stateTransition = processor.formatStateChangeCommunicationKey( item.getPreviousMessageState(), item.getNewMessageState() );
		StateChangeCommunication communication = processor.getStateChangeCommunication( item.getRecipientType(), stateTransition );
		String formattedMessage = communication.getFormattedMessage( item );
		assertEquals( expectedMessage, formattedMessage );
		assertTrue( this.trimLink( formattedMessage ).length() <= 118 );
	}
	
	@Test
	public void testBuyerFailedInactiveSale() {
		CommunicationsItemProcessor processor = this.createCommunicationsItemProcessor();
		
		MessageStateChangeCommunicationsCursorItem item = new MessageStateChangeCommunicationsCursorItem();
		item.setRecipientType( "BUYER" );
		item.setPreviousMessageState("PROCESSING");
		item.setNewMessageState("FAILED_INACTIVE_SALE");
		item.setListingLocale( Locale.US );
		item.setProviderId( SocialProviders.TWITTER );
		
		String expectedMessage = "Sorry, that sale is now over :(";
		
		String stateTransition = processor.formatStateChangeCommunicationKey( item.getPreviousMessageState(), item.getNewMessageState() );
		StateChangeCommunication communication = processor.getStateChangeCommunication( item.getRecipientType(), stateTransition );
		String formattedMessage = communication.getFormattedMessage( item );
		
		assertEquals( expectedMessage, formattedMessage );
		assertTrue( this.trimLink( formattedMessage ).length() <= 118 );
	}
	
	@Test
	public void testBuyerFailedSoldOut() {
		CommunicationsItemProcessor processor = this.createCommunicationsItemProcessor();
		
		MessageStateChangeCommunicationsCursorItem item = new MessageStateChangeCommunicationsCursorItem();
		item.setRecipientType( "BUYER" );
		item.setPreviousMessageState("PROCESSING");
		item.setNewMessageState("FAILED_SOLD_OUT");
		item.setListingLocale( Locale.US );
		item.setProviderId( SocialProviders.TWITTER );
		
		String expectedMessage = "Sorry, the item you''re trying to buy is now sold out :(";

		String stateTransition = processor.formatStateChangeCommunicationKey( item.getPreviousMessageState(), item.getNewMessageState() );
		StateChangeCommunication communication = processor.getStateChangeCommunication( item.getRecipientType(), stateTransition );
		String formattedMessage = communication.getFormattedMessage( item );

		assertEquals( expectedMessage, formattedMessage );
		assertTrue( this.trimLink( formattedMessage ).length() <= 118 );
	}
	
	@Test
	public void testBuyerPendingMeansOfPayment() {
		CommunicationsItemProcessor processor = this.createCommunicationsItemProcessor();
		
		MessageStateChangeCommunicationsCursorItem item = new MessageStateChangeCommunicationsCursorItem();
		item.setRecipientType( "BUYER" );
		item.setPreviousMessageState("PROCESSING");
		item.setNewMessageState("PENDING_MEANS_OF_PAYMENT");
		item.setListingLocale( Locale.US );
		item.setProviderId( SocialProviders.TWITTER );
		item.setListingId( 100L );
		
		String expectedMessage = "To complete your transaction click here: http://localhost:8080/rodlaver-core/listing/checkout/100";
		
		String stateTransition = processor.formatStateChangeCommunicationKey( item.getPreviousMessageState(), item.getNewMessageState() );
		StateChangeCommunication communication = processor.getStateChangeCommunication( item.getRecipientType(), stateTransition );
		String formattedMessage = communication.getFormattedMessage( item );
		
		assertEquals( expectedMessage, formattedMessage );
		assertTrue( this.trimLink( formattedMessage ).length() <= 118 );
	}
	
	@Test
	public void testBuyerDigitalPurchaseComplete() {
		CommunicationsItemProcessor processor = this.createCommunicationsItemProcessor();
		
		MessageStateChangeCommunicationsCursorItem item = new MessageStateChangeCommunicationsCursorItem();
		item.setRecipientType( "BUYER" );
		item.setPreviousMessageState("PAYMENT_PROCESSING");
		item.setNewMessageState("COMPLETED");
		item.setListingType( ListingType.SELLING );
		item.setGoodsType( GoodsType.DIGITAL );
		item.setHasDigitalContent( true );
		item.setListingLocale( Locale.US );
		item.setTotalAmount( new BigDecimal( "9.99" ).setScale( 2 ) );
		item.setListingLocale( Locale.US );
		item.setProviderId( SocialProviders.TWITTER );
		item.setPaymentId( 100L );
		
		String expectedMessage = "#SOLD! Click here to download: http://localhost:8080/rodlaver-core/listing/transactionDetail/100";
		
		String stateTransition = processor.formatStateChangeCommunicationKey( item.getPreviousMessageState(), item.getNewMessageState() );
		StateChangeCommunication communication = processor.getStateChangeCommunication( item.getRecipientType(), stateTransition );
		String formattedMessage = communication.getFormattedMessage( item );
		
		assertEquals( expectedMessage, formattedMessage );
		assertTrue( this.trimLink( formattedMessage ).length() <= 118 );
	}

	@Test
	public void testBuyerPhysicalPurchaseWithAddressPendingShipment() {
		CommunicationsItemProcessor processor = this.createCommunicationsItemProcessor();
		
		MessageStateChangeCommunicationsCursorItem item = new MessageStateChangeCommunicationsCursorItem();
		item.setRecipientType( "BUYER" );
		item.setPreviousMessageState("PAYMENT_PROCESSING");
		item.setNewMessageState("PENDING_SHIPMENT");
		item.setListingLocale( Locale.US );
		item.setTotalAmount( new BigDecimal( "9.99" ).setScale( 2 ) );
		item.setListingLocale( Locale.US );
		item.setProviderId( SocialProviders.TWITTER );
		
		String expectedMessage = "Won't be long now -- we'll notify you when your item ships.";
		
		String stateTransition = processor.formatStateChangeCommunicationKey( item.getPreviousMessageState(), item.getNewMessageState() );
		StateChangeCommunication communication = processor.getStateChangeCommunication( item.getRecipientType(), stateTransition );
		String formattedMessage = communication.getFormattedMessage( item );
		
		assertEquals( expectedMessage, formattedMessage );
		assertTrue( this.trimLink( formattedMessage ).length() <= 118 );
	}
	
	@Test
	public void testBuyerPhysicalPurchaseShipped() {
		CommunicationsItemProcessor processor = this.createCommunicationsItemProcessor();
		
		MessageStateChangeCommunicationsCursorItem item = new MessageStateChangeCommunicationsCursorItem();
		item.setRecipientType( "BUYER" );
		item.setPreviousMessageState("PENDING_SHIPMENT");
		item.setNewMessageState("COMPLETED");
		item.setListingLocale( Locale.US );
		item.setTotalAmount( new BigDecimal( "9.99" ).setScale( 2 ) );
		item.setSellerUserId( "twitter/regtestnancy" );
		item.setListingLocale( Locale.US );
		item.setProviderId( SocialProviders.TWITTER );
		
		String expectedMessage = "Good news, we just shipped your item!";
		
		String stateTransition = processor.formatStateChangeCommunicationKey( item.getPreviousMessageState(), item.getNewMessageState() );
		StateChangeCommunication communication = processor.getStateChangeCommunication( item.getRecipientType(), stateTransition );
		String formattedMessage = communication.getFormattedMessage( item );
		
		assertEquals( expectedMessage, formattedMessage );
		assertTrue( this.trimLink( formattedMessage ).length() <= 118 );
	}
	
	@Test
	public void testSellerFailedInactiveSale() {
		CommunicationsItemProcessor processor = this.createCommunicationsItemProcessor();
		
		MessageStateChangeCommunicationsCursorItem item = new MessageStateChangeCommunicationsCursorItem();
		item.setRecipientType( "SELLER" );
		item.setPreviousMessageState("PROCESSING");
		item.setNewMessageState("FAILED_INACTIVE_SALE");
		item.setListingLocale( Locale.US );
		item.setBuyerUserId( "twitter/regtestjoe" );
		item.setListingId( 12345L );
		item.setListingLocale( Locale.US );
		item.setProviderId( SocialProviders.TWITTER );
		
		String expectedMessage = "The attempt of @regtestjoe to buy your listing with id: 12345 failed because the sale's inactive.";
		
		String stateTransition = processor.formatStateChangeCommunicationKey( item.getPreviousMessageState(), item.getNewMessageState() );
		StateChangeCommunication communication = processor.getStateChangeCommunication( item.getRecipientType(), stateTransition );
		String formattedMessage = communication.getFormattedMessage( item );
		
		assertEquals( expectedMessage, formattedMessage );
		assertTrue( this.trimLink( formattedMessage ).length() <= 118 );
	}
	
	@Test
	public void testSellerFailedSoldOut() {
		CommunicationsItemProcessor processor = this.createCommunicationsItemProcessor();
		
		MessageStateChangeCommunicationsCursorItem item = new MessageStateChangeCommunicationsCursorItem();
		item.setRecipientType( "SELLER" );
		item.setPreviousMessageState("PROCESSING");
		item.setNewMessageState("FAILED_SOLD_OUT");
		item.setListingLocale( Locale.US );
		item.setBuyerUserId( "twitter/regtestjoe" );
		item.setListingId( 12345L );
		item.setListingLocale( Locale.US );
		item.setProviderId( SocialProviders.TWITTER );
		
		String expectedMessage = "The attempt of @regtestjoe to buy your listing with id: 12345 failed because it's sold out.";
		
		String stateTransition = processor.formatStateChangeCommunicationKey( item.getPreviousMessageState(), item.getNewMessageState() );
		StateChangeCommunication communication = processor.getStateChangeCommunication( item.getRecipientType(), stateTransition );
		String formattedMessage = communication.getFormattedMessage( item );		

		assertEquals( expectedMessage, formattedMessage );
		assertTrue( this.trimLink( formattedMessage ).length() <= 118 );
	}

	@Test
	public void testSellerPurchasePendingPayment() {
		CommunicationsItemProcessor processor = this.createCommunicationsItemProcessor();
		
		MessageStateChangeCommunicationsCursorItem item = new MessageStateChangeCommunicationsCursorItem();
		item.setRecipientType( "SELLER" );
		item.setPreviousMessageState("PROCESSING");
		item.setNewMessageState("PENDING_MEANS_OF_PAYMENT");
		item.setListingLocale( Locale.US );
		item.setBuyerUserId( "twitter/regtestjoe" );
		item.setListingId( 12345L );
		item.setListingLocale( Locale.US );
		item.setProviderId( SocialProviders.TWITTER );
		
		String expectedMessage = "The attempt of @regtestjoe to buy your listing with id: 12345 is pending a means of payment.";
		
		String stateTransition = processor.formatStateChangeCommunicationKey( item.getPreviousMessageState(), item.getNewMessageState() );
		StateChangeCommunication communication = processor.getStateChangeCommunication( item.getRecipientType(), stateTransition );
		String formattedMessage = communication.getFormattedMessage( item );
		
		assertEquals( expectedMessage, formattedMessage );
		assertTrue( this.trimLink( formattedMessage ).length() <= 118 );
	}
	
	@Test
	public void testSellerPendingShippingAddress() {
		CommunicationsItemProcessor processor = this.createCommunicationsItemProcessor();
		
		MessageStateChangeCommunicationsCursorItem item = new MessageStateChangeCommunicationsCursorItem();
		item.setRecipientType( "SELLER" );
		item.setPreviousMessageState("PAYMENT_PROCESSING");
		item.setNewMessageState("PENDING_SHIPPING_ADDRESS");
		item.setListingLocale( Locale.US );
		item.setBuyerUserId( "twitter/regtestjoe" );
		item.setListingId( 12345L );
		item.setListingLocale( Locale.US );
		item.setProviderId( SocialProviders.TWITTER );
		
		String expectedMessage = "The user @regtestjoe has purchased your listing with id: 12345. We will advise you when we have the shipping address.";
		
		String stateTransition = processor.formatStateChangeCommunicationKey( item.getPreviousMessageState(), item.getNewMessageState() );
		StateChangeCommunication communication = processor.getStateChangeCommunication( item.getRecipientType(), stateTransition );
		String formattedMessage = communication.getFormattedMessage( item );
		
		assertEquals( expectedMessage, formattedMessage );
		assertTrue( this.trimLink( formattedMessage ).length() <= 118 );
	}
	
	@Test
	public void testSellerReceivedShippingAddress() {
		CommunicationsItemProcessor processor = this.createCommunicationsItemProcessor();
		
		MessageStateChangeCommunicationsCursorItem item = new MessageStateChangeCommunicationsCursorItem();
		item.setRecipientType( "SELLER" );
		item.setPreviousMessageState("PENDING_SHIPPING_ADDRESS");
		item.setNewMessageState("PENDING_SHIPMENT");
		item.setListingLocale( Locale.US );
		item.setBuyerUserId( "twitter/regtestjoe" );
		item.setPaymentId( 12345L );
		item.setListingType( ListingType.SELLING );
		item.setGoodsType( GoodsType.PHYSICAL );
		item.setListingLocale( Locale.US );
		item.setProviderId( SocialProviders.TWITTER );
		
		String expectedMessage = "We've got the shipping info for @regtestjoe - visit us here for the info http://localhost:8080/rodlaver-core/listing/transactionDetail/12345";
		
		String stateTransition = processor.formatStateChangeCommunicationKey( item.getPreviousMessageState(), item.getNewMessageState() );
		StateChangeCommunication communication = processor.getStateChangeCommunication( item.getRecipientType(), stateTransition );
		String formattedMessage = communication.getFormattedMessage( item );
		
		assertEquals( expectedMessage, formattedMessage );
		assertTrue( this.trimLink( formattedMessage ).length() <= 118 );
	}
	
	@Test
	public void testSellerDigitalSaleCompleted() {
		CommunicationsItemProcessor processor = this.createCommunicationsItemProcessor();
		
		MessageStateChangeCommunicationsCursorItem item = new MessageStateChangeCommunicationsCursorItem();
		item.setRecipientType( "SELLER" );
		item.setPreviousMessageState("PAYMENT_PROCESSING");
		item.setNewMessageState("COMPLETED");
		item.setListingType( ListingType.SELLING );
		item.setGoodsType( GoodsType.DIGITAL);
		item.setListingLocale( Locale.US );
		item.setBuyerUserId( "twitter/regtestjoe" );
		item.setSellerAmount( new BigDecimal( "5.55").setScale( 2 ) );
		item.setPaymentId( 12345L );
		item.setListingLocale( Locale.US );
		item.setProviderId( SocialProviders.TWITTER );
		
		String expectedMessage = "Congrats! You made a sale of $5.55 (net) to @regtestjoe via @hootit -- get the details here http://localhost:8080/rodlaver/sale/paymentDetail?id=12345";
		
		String stateTransition = processor.formatStateChangeCommunicationKey( item.getPreviousMessageState(), item.getNewMessageState() );
		StateChangeCommunication communication = processor.getStateChangeCommunication( item.getRecipientType(), stateTransition );
		String formattedMessage = communication.getFormattedMessage( item );
		
		assertEquals( expectedMessage, formattedMessage );
		assertTrue( this.trimLink( formattedMessage ).length() <= 118 );
	}

	@Test
	public void testSellerPhysicalSaleCompleted() {
		CommunicationsItemProcessor processor = this.createCommunicationsItemProcessor();
		
		MessageStateChangeCommunicationsCursorItem item = new MessageStateChangeCommunicationsCursorItem();
		item.setRecipientType( "SELLER" );
		item.setPreviousMessageState("PAYMENT_PROCESSING");
		item.setNewMessageState("PENDING_SHIPMENT");
		item.setListingType( ListingType.SELLING );
		item.setGoodsType( GoodsType.PHYSICAL );
		item.setListingLocale( Locale.US );
		item.setBuyerUserId( "twitter/regtestjoe" );
		item.setSellerAmount( new BigDecimal( "5.55").setScale( 2 ) );
		item.setPaymentId( 12345L );
		item.setListingLocale( Locale.US );
		item.setProviderId( SocialProviders.TWITTER );
		
		String expectedMessage = "We've got the shipping info for @regtestjoe - visit us here for the info http://localhost:8080/rodlaver-core/listing/transactionDetail/12345";
		
		String stateTransition = processor.formatStateChangeCommunicationKey( item.getPreviousMessageState(), item.getNewMessageState() );
		StateChangeCommunication communication = processor.getStateChangeCommunication( item.getRecipientType(), stateTransition );
		String formattedMessage = communication.getFormattedMessage( item );
		
		assertEquals( expectedMessage, formattedMessage );
		assertTrue( this.trimLink( formattedMessage ).length() <= 118 );
	}
	
	@Test
	public void testBigDecimalToInt() {
		BigDecimal amount = new BigDecimal( 2.00 ).setScale( 2, RoundingMode.CEILING );
		BigDecimal multiplicand = new BigDecimal (100 ).setScale( 0, RoundingMode.CEILING );
		assertEquals(200, amount.multiply( multiplicand ).intValue() );
		
		BigDecimal amount2 = new BigDecimal( 0.00 ).setScale( 2, RoundingMode.CEILING );
		BigDecimal multiplicand2 = new BigDecimal (100 ).setScale( 0, RoundingMode.CEILING );
		assertEquals( 0, amount2.multiply( multiplicand2 ).intValue() );
		
		BigDecimal amount3 = new BigDecimal( 3.33 ).setScale( 2, RoundingMode.FLOOR );
		BigDecimal multiplicand3 = new BigDecimal (100 ).setScale( 0, RoundingMode.FLOOR );
		assertEquals( 333, amount3.multiply( multiplicand3 ).intValue() );
		
		double i = 0.00;
		
		for( int j = 0; j < 10000; j++ ) {
			System.out.println( "Testing with i: " + i + ", j: " + j );
			
			BigDecimal bd = new BigDecimal( String.valueOf( i ) ).setScale( 2, RoundingMode.FLOOR );
			System.out.println( "$" + String.format( "%.2f", bd ));
			System.out.println( "Big decimal amount: " + bd );
			BigDecimal inCents = bd.movePointRight( 2 );
			System.out.println( "Big decimal in cents: " + inCents );
			assertEquals( j, inCents.intValue() );
			i = i + 0.01;
		}
	}
	

	//	A simple test to see if the Twitter API is able to send a public reply using a given set of credentials	
	@Test
	public void testSendPublicReply() {
		String consumerKey = "";
		String consumerSecret = "";
		String accessToken = "";
		String accessTokenSecret = "";
		
		
		Twitter twitterApi = new TwitterTemplate( consumerKey, consumerSecret, accessToken, accessTokenSecret );
		TwitterProfile profile = twitterApi.userOperations().getUserProfile( 622133588 );
		String screenName = profile.getScreenName();
		System.out.println( screenName );
		Tweet tweet = twitterApi.timelineOperations().updateStatus(new TweetData("Message in reply @" + screenName ).inReplyToStatus(372814478531764224L));
		assertNotNull( tweet );
	}
	
	
	public ResourceBundleMessageSource createMessageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}
	
	private CommunicationsItemProcessor createCommunicationsItemProcessor() {
		CommunicationsItemProcessor processor = new CommunicationsItemProcessor();
		
		ResourceBundleMessageSource messageSource = this.createMessageSource();
		
		// Set the objects that encapsulate the messages
		ReflectionTestUtils.setField( processor, "buyerPendingUserRegistration", new BuyerPendingUserRegistration( messageSource, BASE_CHECKOUT_URL, BASE_PAYMENT_DETAIL_URL ) );
		ReflectionTestUtils.setField( processor, "buyerFailedInactiveSale", new BuyerFailedInactiveSale( messageSource, BASE_CHECKOUT_URL, BASE_PAYMENT_DETAIL_URL ) );
		ReflectionTestUtils.setField( processor, "buyerNonPhysicalPaymentCompleted", new BuyerNonPhysicalPaymentCompleted( messageSource, BASE_CHECKOUT_URL, BASE_PAYMENT_DETAIL_URL ) );
		ReflectionTestUtils.setField( processor, "buyerFailedSoldOut", new BuyerFailedSoldOut( messageSource, BASE_CHECKOUT_URL, BASE_PAYMENT_DETAIL_URL ) );
		ReflectionTestUtils.setField( processor, "buyerPendingMeansOfPayment", new BuyerPendingMeansOfPayment( messageSource, BASE_CHECKOUT_URL, BASE_PAYMENT_DETAIL_URL ) );
		ReflectionTestUtils.setField( processor, "buyerPaymentPending", new BuyerPaymentPending( messageSource, BASE_CHECKOUT_URL, BASE_PAYMENT_DETAIL_URL ) );
		ReflectionTestUtils.setField( processor, "buyerPendingShipment", new BuyerPendingShipment( messageSource, BASE_CHECKOUT_URL, BASE_PAYMENT_DETAIL_URL ) );
		ReflectionTestUtils.setField( processor, "buyerPendingShippingAddress", new BuyerPendingShippingAddress( messageSource, BASE_CHECKOUT_URL, BASE_PAYMENT_DETAIL_URL ) );
		ReflectionTestUtils.setField( processor, "buyerPhysicalPurchaseShipped", new BuyerPhysicalPurchaseShipped( messageSource, BASE_CHECKOUT_URL, BASE_PAYMENT_DETAIL_URL ) );
		ReflectionTestUtils.setField( processor, "buyerPaymentFailed", new BuyerPaymentFailed( messageSource, BASE_CHECKOUT_URL, BASE_PAYMENT_DETAIL_URL ) );
		
		return processor;
	}
	
	private String trimLink( String formattedMessage ) {
		
		int index = formattedMessage.indexOf( "http" );
		if( index > 0 ) {
			return formattedMessage.substring( index );
		}
		return formattedMessage;
	}
}
