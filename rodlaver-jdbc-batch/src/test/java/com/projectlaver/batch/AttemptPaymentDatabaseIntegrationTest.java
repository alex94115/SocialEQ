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

import static org.junit.Assert.assertEquals;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.StepRunner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.easymock.EasyMock.*;

import com.projectlaver.util.AttemptPaymentDTO;
import com.projectlaver.util.AttemptPaymentItemProcessor;
import com.projectlaver.util.BraintreePaymentProvider;
import com.projectlaver.util.GoodsType;
import com.projectlaver.util.ListingType;
import com.projectlaver.util.MessageStatus;
import com.projectlaver.util.PaymentProvider;
import com.projectlaver.util.PaymentStatus;
import com.projectlaver.util.PaypalPaymentProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/simple-job-launcher-context.xml",
									"/jobs/rodLaverJob.xml" })
public class AttemptPaymentDatabaseIntegrationTest extends BatchDatabaseIntegrationTest  {

	@Before
	public void setup() {
		super.setUp( ListingType.SELLING, GoodsType.VOUCHER, null );
		
		super.insertReplyMessage(
				"2596726242", 
				178L, 
				super.testListingMessageTwitterId, 
				MessageStatus.PROCESSING, 
				"BATCH_EXECUTION_INSTANCE-1-ATTEMPT_PURCHASE-T0",
				true );
	}
	
	@Test
	public void testRunAttemptPayment() {
		
		// Get the configured step with the reader / processor / writer
		Step attemptPaymentStep = ( Step )context.getBean( "partitionProcessPaymentAttemptMessages" );
		
		/**
		 *  Get a reference to the Payment Item Processor so that we can set
		 *  a mock PayPalHelper instance into it.
		 */
		Object partitionHandler = ReflectionTestUtils.getField( attemptPaymentStep, "partitionHandler" );
		Object partitionStep = ReflectionTestUtils.getField( partitionHandler, "step" );
		
		Object tasklet = ReflectionTestUtils.getField( partitionStep, "tasklet" );
		Object chunkProcessor = ReflectionTestUtils.getField( tasklet, "chunkProcessor" );
		
		AttemptPaymentDTO mockSuccessResponse = new AttemptPaymentDTO();
		mockSuccessResponse.setCorrelationId( "78V727760E494602K" );
		mockSuccessResponse.setPayKey( "AP-2LT29035CX5093926" );
		mockSuccessResponse.setPaymentStatus( PaymentStatus.PAYMENT_PROCESSING );
		mockSuccessResponse.setGoodsType( GoodsType.VOUCHER );
		
		AttemptPaymentItemProcessor itemProcessor =  (AttemptPaymentItemProcessor) ReflectionTestUtils.getField( chunkProcessor, "itemProcessor" );
		PaymentProvider mockPaypalPaymentProvider = this.createMockPaymentProvider( mockSuccessResponse );
		ReflectionTestUtils.setField( itemProcessor, "paymentProvider", mockPaypalPaymentProvider );
		
		assertEquals( BatchStatus.COMPLETED, stepRunner.launchStep( attemptPaymentStep ).getStatus());
	}

	PaymentProvider createMockPaymentProvider( AttemptPaymentDTO mockResponse ) {
		
		PaymentProvider mockPaymentProvider = createStrictMock( BraintreePaymentProvider.class );
		
		expect( mockPaymentProvider.getMemo( anyObject( String.class), anyObject( String.class), anyObject( String.class)))
			.andReturn( "Mock memo line." );
		
		expect( mockPaymentProvider.attemptPaymentWithPreapprovalKey( anyObject( AttemptPaymentDTO.class )))
			.andReturn( mockResponse );
		
		replay( mockPaymentProvider );
		
		return mockPaymentProvider;
	}

}
