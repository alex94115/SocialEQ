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

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.ClientTokenRequest;
import com.braintreegateway.Customer;
import com.braintreegateway.CustomerRequest;
import com.braintreegateway.Environment;
import com.braintreegateway.MerchantAccount;
import com.braintreegateway.MerchantAccountRequest;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.projectlaver.domain.Listing;
import com.projectlaver.domain.Message;
import com.projectlaver.domain.Payment;
import com.projectlaver.domain.User;
import com.projectlaver.test.config.SocialTestConfig;
import com.projectlaver.test.config.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class BraintreePaymentProviderTest {
	
	@Autowired
	SocialTestConfig socialTestConfig;
	
	private BraintreeGateway gateway = new BraintreeGateway(
			// sandbox properties
			Environment.SANDBOX,
			this.socialTestConfig.getBraintreeMasterMerchantAccountId(),
			this.socialTestConfig.getBraintreePublicKey(),
			this.socialTestConfig.getBraintreePrivateKey()
		);

	
	@Test
	public void testCompleteCheckout() {
		
		BraintreePaymentProvider provider = new BraintreePaymentProvider();
		
		ReflectionTestUtils.setField( provider, "braintreeGateway", this.gateway );
		
		/**
		 * Make sure that this customer does not exist by deleting them from Braintree prior to the test
		 */
		try {
			Result<Customer> result = gateway.customer().delete( new Long( 166 ).toString() );
		} catch( Exception e ) {
			// no worries
		}
		
		// setup an initiated payment DTO
		InitiatedPaymentDTO initiatedPayment = new InitiatedPaymentDTO();
		initiatedPayment.setCheckoutToken( com.braintreegateway.test.Nonce.Transactable ); // valid nonce for testing
		//initiatedPayment.setCheckoutToken( com.braintreegateway.test.Nonce.Consumed ); // this will cause a failure due to reuse of a nonce 
		
		// Setup a payment
		Payment payment = new Payment();
		ReflectionTestUtils.setField( payment, "id", 1L );
		payment.setTotalAmount( new BigDecimal( "10.0000" ));
		payment.setRodLaverAmount( new BigDecimal( "1.00000" ));
		
		// Add a listing to the payment
		Listing listing = new Listing();
		listing.setTitle( "Test listing" );
		listing.setKeyword( "#keyword" );
		payment.setListing( listing );
		
		// Add a message to the payment
		Message message = new Message();
		ReflectionTestUtils.setField( message, "id", 1L );
		payment.setMessage( message );
		
		User payee = new User();
		ReflectionTestUtils.setField( payee, "id", 164L );
		ReflectionTestUtils.setField( payee, "username", "Test seller" );
		payment.setPayee( payee );
		
		User payer = new User();
		ReflectionTestUtils.setField( payer, "id", 166L );
		payment.setPayer( payer );
		
		Map<String, Object> result = provider.completeCheckout( initiatedPayment, payment );
		PaymentQueryResultDTO dto = (PaymentQueryResultDTO)result.get( "paymentQueryResultDTO" );
		assertEquals( dto.getPaymentStatus(), PaymentStatus.COMPLETED ); 
		
		System.out.println( initiatedPayment.getPreapprovalKey() );
	}
	
	@Test
	public void testAttemptPaymentWithPreapprovalKey() {
		
		BraintreePaymentProvider provider = new BraintreePaymentProvider();
		
		ReflectionTestUtils.setField( provider, "braintreeGateway", this.gateway );
		
		AttemptPaymentDTO dto = new AttemptPaymentDTO();
		dto.setPaymentAmount( new BigDecimal( "10.00000" ));
		dto.setSellerRevenueRatio( new BigDecimal( "0.90000" ));
		dto.setPreapprovalKey( "jjjr9r" );
		
		
		provider.attemptPaymentWithPreapprovalKey( dto );
		assertEquals( dto.getPaymentStatus(), PaymentStatus.COMPLETED );
		assertNotNull( dto.getCorrelationId() );
	}
	
	
	/**
	 * Simulate creating a sub merchant for @regtestbob
	 */
	@Test
	public void testCreateSubMerchant() {
		
		MerchantAccountRequest request = new MerchantAccountRequest();
		
		request
			.individual()
				.firstName( "Test" )
				.lastName( "Fred" )
				.email( "regulartestfred@test.com" )
				.phone( "5053334444" )
				.dateOfBirth( "1990-10-01" )
				.address()
					.streetAddress( "301 Main St" )
					.locality( "SF" )
					.region( "CA" )
					.postalCode( "94105" )
					.done()
				.done()
			.funding()
				.destination( MerchantAccount.FundingDestination.EMAIL)
				.email( "regulartestfred@test.com" )
				.done()
			.tosAccepted( true )
			.masterMerchantAccountId( this.socialTestConfig.getBraintreeMasterMerchantAccountId() )
			.id( "164" );
		
		// Attempt to create the sub merchant
		Result<MerchantAccount> result = this.gateway.merchantAccount().create( request );
		
		System.out.println( result.getMessage() );
		assertTrue( result.isSuccess() );
	}
	

	/**
	 * Use this code to "hand create" a Braintree production sub merchant
	 */

//	private BraintreeGateway productionGateway = new BraintreeGateway(
//			// production properties
//			Environment.PRODUCTION,
//			this.socialTestConfig.getBraintreeMerchantId(),
//			this.socialTestConfig.getBraintreePublicKey(),
//			this.socialTestConfig.getBraintreePrivateKey(),
//		);
	
//	@Test
//	public void createProductionSubMerchant() {
//		
//		MerchantAccountRequest request = new MerchantAccountRequest();
//		
//		request
//			.individual()
//				.firstName( "" )
//				.lastName( "" )
//				.email( "" )
//				.phone( "" )
//				.dateOfBirth( "" )
//				.address()
//					.streetAddress( "" )
//					.locality( "" )
//					.region( "" )
//					.postalCode( "" )
//					.done()
//				.done()
//			.funding()
//				.destination( MerchantAccount.FundingDestination.EMAIL)
//				.email( "" )
//				.done()
//			.tosAccepted( true )
//			.masterMerchantAccountId( this.socialTestConfig.getBraintreeMasterMerchantAccountId() )
//			.id( "" );
//		
//		// Attempt to create the sub merchant
//		Result<MerchantAccount> result = this.productionGateway.merchantAccount().create( request );
//		
//		System.out.println( result.getMessage() );
//		assertTrue( result.isSuccess() );
//	}
	
	
	@Test
	public void testRetrieveMerchantAccount() {
		
		// Find an account
		//MerchantAccount account = this.gateway.merchantAccount().find( "1051" );

	}

	@Test
	public void testValid() {
		
		// Create a TransactionRequest using a payment token
		TransactionRequest request = new TransactionRequest();
		request
			.paymentMethodToken( "c6zv5r" ) // equivalent to a PreapprovalKey
			.amount( new BigDecimal ( "300.00" ))
			.options()
				.submitForSettlement( true )
				.done();
			
		
		// Submit the transaction for processing
		Result<Transaction> result = gateway.transaction().sale( request );
		System.out.println( ToStringBuilder.reflectionToString( result ) );
		assertTrue( result.isSuccess() );
		
	}
	
	@Test
	public void testRevokedPreapproval() {
		
		// Create a TransactionRequest using a payment token
		TransactionRequest request = new TransactionRequest();
		request
			.paymentMethodToken( "3vjczr" ) // equivalent to a PreapprovalKey
			.amount( new BigDecimal ( "2070.00" )) // special dollar amount to cause a Paypal Buyer Revoked Future Payment Authorization error
			.options()
				.submitForSettlement( true )
				.done();
			
		
		// Submit the transaction for processing
		Result<Transaction> result = gateway.transaction().sale( request );
		
		assertFalse( result.isSuccess() );
		Transaction transaction = result.getTransaction();
		assertEquals( "2070", transaction.getProcessorResponseCode() );
	}
	
	@Test 
	public void testFailedSettlement() {
		
		// Create a TransactionRequest using a payment token
		TransactionRequest request = new TransactionRequest();
		request
			.paymentMethodToken( "3vjczr" ) // equivalent to a PreapprovalKey
			.amount( new BigDecimal ( "4001.00" )) // special dollar amount to cause a PayPal settlement failure
			.options()
				.submitForSettlement( true )
				.done();
			
		
		// Submit the transaction for processing
		Result<Transaction> result = gateway.transaction().sale( request );
		
		assertFalse( result.isSuccess() );
		Transaction transaction = result.getTransaction();
		assertEquals( "4001", transaction.getProcessorSettlementResponseCode() );
	}
	
	@Test
	public void testCreateCustomer() {
		
		// Create a customer
		CustomerRequest request = new CustomerRequest();
		request
			.firstName( "Test" )
			.lastName( "Joe" )
			.customerId( "166" );
		
		Result<Customer> result = this.gateway.customer().create( request );
		assertFalse( result.isSuccess() );
	}
	
	@Test
	public void testCreateToken() {
		
		ClientTokenRequest request = new ClientTokenRequest();
		request
			.customerId( "19579780" );
		
		String clientToken = this.gateway.clientToken().generate( request );
		assertNotNull( clientToken );
		
	}

}
