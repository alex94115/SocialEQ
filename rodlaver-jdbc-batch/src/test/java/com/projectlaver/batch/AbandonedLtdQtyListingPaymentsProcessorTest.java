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

import com.projectlaver.util.GoodsType;
import com.projectlaver.util.ListingType;
import com.projectlaver.util.MessageStatus;
import com.projectlaver.util.PaymentStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/simple-job-launcher-context.xml",
									"/jobs/rodLaverJob.xml" })
public class AbandonedLtdQtyListingPaymentsProcessorTest extends BatchDatabaseIntegrationTest  {
	
	@Before
	public void setUp() {
		super.setUp( ListingType.SELLING, GoodsType.PHYSICAL, null );
		
		String providerUserId = ( String )super.testRegisteredUserAttributes.get( PROVIDER_USER_ID );
		Long buyerId = ( Long )super.testRegisteredUserAttributes.get( USER_ID );
		
		Long listingId = super.jdbcTemplate.queryForObject( 
				"SELECT listing_id FROM Messages WHERE twitterId = ?", new Object[] { super.testListingMessageTwitterId }, Long.class );
		
		Long sellerId = super.jdbcTemplate.queryForObject(
				"SELECT seller_id FROM Listings WHERE id = ?", new Object[] { listingId }, Long.class );
		
		// insert payment
		super.insertReplyWithPayment(
				providerUserId, 
				buyerId, 
				super.testListingMessageTwitterId, 
				MessageStatus.PENDING_MEANS_OF_PAYMENT, // message status 
				PaymentStatus.CREATED, // paymentStatus 
				"2014-02-01 00:00:00", 
				"2014-02-01 03:00:00", 
				1, // purchase quantity
				listingId, 
				sellerId );
	}
	
	@Test
	public void testProcessAbandonedPayments() {
		Step finalizeAbandonedPaymentsStep = ( Step )super.context.getBean( "finalizeAbandonedLtdQtyListingPayments" );
		
		assertEquals( BatchStatus.COMPLETED, stepRunner.launchStep( finalizeAbandonedPaymentsStep ).getStatus());
	}


}
