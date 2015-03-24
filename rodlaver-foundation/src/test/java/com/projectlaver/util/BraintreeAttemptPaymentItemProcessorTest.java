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

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;

import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.MockType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.util.ReflectionTestUtils;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.CreditCard;
import com.braintreegateway.PayPalDetails;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionGateway;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;
import com.braintreegateway.ValidationErrorCode;
import com.braintreegateway.ValidationErrors;
import com.braintreegateway.util.NodeWrapper;
import com.braintreegateway.util.SimpleNodeWrapper;

@RunWith(EasyMockRunner.class)
public class BraintreeAttemptPaymentItemProcessorTest extends AttemptPaymentItemProcessorTest {
	
	// Backend root object
	@Mock(type=MockType.STRICT)
	BraintreeGateway braintreeGateway;
	
	// Root transaction facade
	@Mock(type=MockType.STRICT)
	TransactionGateway transactionGateway;
	
	@Mock(type=MockType.STRICT)
	Result<Transaction> result;
	
	@Mock(type=MockType.STRICT)
	Transaction transaction;
	
	@Mock(type=MockType.STRICT)
	CreditCard creditCard;
	
	@Override
	void setupAttemptPreapprovalPaymentBackendError( AttemptPaymentItemProcessor processor ) throws Exception {
		
		// Set expectations for a transaction that fails
		expect( this.braintreeGateway.transaction() ).andReturn( this.transactionGateway );

		// Setup an error result from the backend "sale" request
		Result<Transaction> result = new Result<Transaction>();
		ValidationErrors errors = new ValidationErrors();
		errors.addError( new ValidationError( "someAttribute", ValidationErrorCode.TRANSACTION_PAYMENT_METHOD_TOKEN_IS_INVALID, "Some message." ) );
		ReflectionTestUtils.setField( result, "errors", errors );
		expect( this.transactionGateway.sale( anyObject( TransactionRequest.class )))
			.andReturn( result );
		
		// Set the mock backend onto a braintreePaymentProvider
		PaymentProvider braintreePaymentProvider = new BraintreePaymentProvider();
		ReflectionTestUtils.setField( braintreePaymentProvider, "braintreeGateway", this.braintreeGateway );
		
		// Set the paymentProvider onto the batch processor
		ReflectionTestUtils.setField( processor, "paymentProvider", braintreePaymentProvider );

	}

	@Override
	void setupAttemptPreapprovalPaymentNormalFlow( AttemptPaymentItemProcessor processor ) throws Exception {
		
		// Set expectations for a transaction that fails
		expect( this.braintreeGateway.transaction() ).andReturn( this.transactionGateway );

		// Setup successful result from the backend "sale" request
		expect( this.transactionGateway.sale( anyObject( TransactionRequest.class ) ))
			.andReturn( this.result );
		
		expect( this.result.isSuccess() )
			.andReturn( true );
		
		expect( this.result.getTarget() )
			.andReturn( this.transaction );
		
		expect( this.transaction.getCreditCard() )
			.andReturn( this.creditCard );
		
		expect( this.transaction.getId() )
			.andReturn( "transaction id" );
		
		expect( this.transaction.getProcessorAuthorizationCode() )
			.andReturn( "processor auth code" );
		
		expect( this.creditCard.getToken() )
			.andReturn( "token" );
		
		// Set the mock backend onto a braintreePaymentProvider
		PaymentProvider braintreePaymentProvider = new BraintreePaymentProvider();
		ReflectionTestUtils.setField( braintreePaymentProvider, "braintreeGateway", this.braintreeGateway );
		
		// Set the paymentProvider onto the batch processor
		ReflectionTestUtils.setField( processor, "paymentProvider", braintreePaymentProvider );

	}

	@Test
	public void testGetUpdatedMessageStatus() {
		
		AttemptPaymentItemProcessor processor = new AttemptPaymentItemProcessor();
		String status = processor.getUpdatedMessageStatus( PaymentStatus.COMPLETED, false, GoodsType.PHYSICAL );
		assertEquals( status, MessageStatus.PENDING_SHIPPING_ADDRESS.toString() );
		
	}


}
