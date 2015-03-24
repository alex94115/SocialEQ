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

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMockRunner;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.test.util.ReflectionTestUtils;

//import com.paypal.svcs.services.AdaptivePaymentsService;
//import com.paypal.svcs.types.ap.PayRequest;
//import com.paypal.svcs.types.ap.PayResponse;
//import com.paypal.svcs.types.ap.PaymentInfo;
//import com.paypal.svcs.types.ap.PaymentInfoList;
//import com.paypal.svcs.types.common.AckCode;
//import com.paypal.svcs.types.common.ResponseEnvelope;

@RunWith(EasyMockRunner.class)
//public class PaypalAttemptPaymentItemProcessorTest extends AttemptPaymentItemProcessorTest {
public abstract class PaypalAttemptPaymentItemProcessorTest extends AttemptPaymentItemProcessorTest {

	/**
	 * Constants
	 */

//	private static final String PAY_KEY = "AP-17W146535C155111X";
//	private static final String PREAPPROVAL_KEY = "PA-87D168076W5006101";
//	private static final String CORRELATION_ID = "47E58301AH146725P";
//	private static final String TRANSACTION_ID = "5LB54332RL3606414";
//
//	@Override
//	void setupAttemptPreapprovalPaymentBackendError( AttemptPaymentItemProcessor processor ) throws Exception {
//		
//		AdaptivePaymentsService mockPaypalBackend = createMock( AdaptivePaymentsService.class );
//		
//		Boolean doSimulatePaypalFailure = true;
//		expect( mockPaypalBackend.pay( anyObject( PayRequest.class ) ) )
//			.andReturn( this.payWithPreapprovalKeyResponse( doSimulatePaypalFailure ) );
//		
//		PaymentProvider paymentProvider = this.createBackendMockingPaymentProvider( mockPaypalBackend );
//		ReflectionTestUtils.setField( processor, "paymentProvider", paymentProvider );
//	}
//	
//	@Override
//	void setupAttemptPreapprovalPaymentNormalFlow( AttemptPaymentItemProcessor processor ) throws Exception {
//		
//		AdaptivePaymentsService mockPaypalBackend = createMock( AdaptivePaymentsService.class );
//		
//		Boolean doSimulatePaypalFailure = false;
//		expect( mockPaypalBackend.pay( anyObject( PayRequest.class ) ) )
//			.andReturn( this.payWithPreapprovalKeyResponse( doSimulatePaypalFailure ) );
//		
//		PaymentProvider paymentProvider = this.createBackendMockingPaymentProvider( mockPaypalBackend );
//		ReflectionTestUtils.setField( processor, "paymentProvider", paymentProvider );
//		
//	}
//	
//	/**
//	 * Helper methods
//	 */
//
//	/**
//	 * TODO need to take a look at a real PayPal payment failure to see what the contents look like
//	 * when the preapproved payment is declined. Then update that branch of the if statement below.
//	 * 
//	 * @param doSimulatePaypalFailure
//	 * @return
//	 */
//	PayResponse payWithPreapprovalKeyResponse( Boolean doSimulatePaypalFailure ) {
//		
//		PayResponse response = null;
//		
//		if( !doSimulatePaypalFailure ) {
//			ResponseEnvelope responseEnvelope = this.createResponseEnvelope( AckCode.SUCCESS, CORRELATION_ID );
//		
//			response = new PayResponse();
//			response.setResponseEnvelope( responseEnvelope );
//			response.setPaymentExecStatus( "CREATED" );
//			response.setPayKey( PAY_KEY );
//	
//			// add a payment info list to the pay response
//			PaymentInfoList paymentInfoList = new PaymentInfoList();
//			List<PaymentInfo> paymentInfoContents = new ArrayList<PaymentInfo>();
//			
//			PaymentInfo paymentToRodlaver = new PaymentInfo();
//			paymentToRodlaver.setTransactionId( TRANSACTION_ID );
//			paymentInfoContents.add( paymentToRodlaver );
//			
//			PaymentInfo paymentToSeller = new PaymentInfo();
//			paymentInfoContents.add( paymentToSeller );
//			
//			paymentInfoList.setPaymentInfo( paymentInfoContents );
//			response.setPaymentInfoList( paymentInfoList );
//			
//		} else {
//			
//			ResponseEnvelope responseEnvelope = this.createResponseEnvelope( AckCode.FAILURE, CORRELATION_ID );
//			response = new PayResponse();
//			response.setResponseEnvelope( responseEnvelope );
//			
//		}
//		
//		return response;
//	}
//	
//	PaymentProvider createBackendMockingPaymentProvider( AdaptivePaymentsService mockAdaptivePaymentsService) {
//
//		// set the adaptivePaymentServiceFactory and configure it to return the mock adaptive payments service
//		ObjectFactory<AdaptivePaymentsService> adaptivePaymentsServiceFactory = createMock( ObjectFactory.class );
//		expect( adaptivePaymentsServiceFactory.getObject() )
//			.andReturn( mockAdaptivePaymentsService )
//			.anyTimes();
//		
//		// create a PaypalPaymentProvider instance and set the adaptivePaymentsServiceFactory on it
//		PaymentProvider paypalPaymentProvider = new PaypalPaymentProvider();
//		ReflectionTestUtils.setField( paypalPaymentProvider, "adaptivePaymentsServiceFactory", adaptivePaymentsServiceFactory );
//		
//		return paypalPaymentProvider;
//	}
//	
//	ResponseEnvelope createResponseEnvelope(AckCode ackCode, String correlationId) {
//		ResponseEnvelope responseEnvelope = new ResponseEnvelope();
//		responseEnvelope.setAck( ackCode );
//		responseEnvelope.setCorrelationId( correlationId );
//		return responseEnvelope;
//	}

	
}
