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

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.projectlaver.util.GoodsType;
import com.projectlaver.util.ListingSubType;
import com.projectlaver.util.ListingType;
import com.projectlaver.util.MessageStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/simple-job-launcher-context.xml",
									"/jobs/rodLaverJob.xml" })
public class MessageDivvyingTaskletInstantCampaignListingTest extends BatchDatabaseIntegrationTest {

	private static final ListingType CAMPAIGN_TYPE_LISTING = ListingType.CAMPAIGN;
	private static final GoodsType GOODS_TYPE = GoodsType.DIGITAL;
	private static final ListingSubType INSTANT_CAMPAIGN = ListingSubType.INSTANT;
	
	@Before
	public void setUp() {
		super.setUp( CAMPAIGN_TYPE_LISTING, GOODS_TYPE, INSTANT_CAMPAIGN );
	}
	
	@Test
	public void testInitialUnregisteredReply() {
		
		Boolean replyContainsKeyword = true;
		
		// insert a couple valid replies from unregistered users
		String providerUserId = ( String )super.testUnregisteredUserAttributes.get( BatchDatabaseIntegrationTest.PROVIDER_USER_ID );
		Map<String, Object> result1 = super.insertUnprocessedUnregisteredUserReplyMessage( providerUserId,
				super.testListingMessageTwitterId, replyContainsKeyword );
		
		Map<String, Object> result2 = super.insertUnprocessedUnregisteredUserReplyMessage(RandomStringUtils.randomNumeric( 9 ),
				super.testListingMessageTwitterId, replyContainsKeyword );
		
		super.runDivvyUpMessages();
		
		assertTrue( "Single unregistered user reply goes to Opted In", super.doesMessageWithBatchProcessorExist( 
				( String )result1.get( "twitterId" ), "BATCH_EXECUTION_INSTANCE-%-USER_OPT_IN_ACTIVE_CAMPAIGN" ) );
		
		assertTrue( "Single unregistered user reply goes to Opted In", super.doesMessageWithBatchProcessorExist( 
				( String )result2.get( "twitterId" ), "BATCH_EXECUTION_INSTANCE-%-USER_OPT_IN_ACTIVE_CAMPAIGN" ) );
	}
	
	@Test
	public void testTwoAtOnceSameUserUnregisteredReplies() {
		
		String providerUserId = ( String )super.testUnregisteredUserAttributes.get( BatchDatabaseIntegrationTest.PROVIDER_USER_ID );
		
		Boolean replyContainsKeyword = true;
		
		// insert a couple unprocessed replies to the same listing from the same unregistered user
		Map<String, Object> result1 = super.insertUnprocessedUnregisteredUserReplyMessage( providerUserId,
				super.testListingMessageTwitterId, replyContainsKeyword );
		
		Map<String, Object> result2 = super.insertUnprocessedUnregisteredUserReplyMessage( providerUserId,
				super.testListingMessageTwitterId, replyContainsKeyword );
		
		super.runDivvyUpMessages();
		
		assertTrue( "First reply goes to Opted In", super.doesMessageWithBatchProcessorExist( 
				( String )result1.get( "twitterId" ), "BATCH_EXECUTION_INSTANCE-%-USER_OPT_IN_ACTIVE_CAMPAIGN" ) );
		
		assertTrue( "Second reply is ignored (batch processor should stay null)", 
				super.doesMessageWithoutBatchProcessorExist( ( String )result2.get( "twitterId" ) ) );
	}
	
	@Test
	public void testTwoFollowUpAtOnceSameUserUnregisteredReplies() {
		
		Boolean replyContainsKeyword = true;
		
		// insert an already-processed PENDING_LISTING_EXPIRY message
		String providerUserId = ( String )super.testUnregisteredUserAttributes.get( BatchDatabaseIntegrationTest.PROVIDER_USER_ID );
		super.insertReplyMessage( 
				providerUserId, null, super.testListingMessageTwitterId, MessageStatus.PENDING_LISTING_EXPIRY, "SOME_UNIT_TEST_BATCH_PROCESSOR", replyContainsKeyword );
		
		// insert a couple unprocessed reply to the same listing from the same user
		Map<String, Object> result1 = super.insertUnprocessedUnregisteredUserReplyMessage( providerUserId,
				super.testListingMessageTwitterId, replyContainsKeyword );
		
		Map<String, Object> result2 = super.insertUnprocessedUnregisteredUserReplyMessage( providerUserId,
				super.testListingMessageTwitterId, replyContainsKeyword );
		
		super.runDivvyUpMessages();
		
		assertTrue( "Duplicate unregistered user reply goes to Duplicate Purchase Attempt", super.doesMessageWithBatchProcessorExist( 
				( String )result1.get( "twitterId" ), "BATCH_EXECUTION_INSTANCE-%-FAILED_DUPLICATE_OPT_IN_ATTEMPT" ) );
		
		assertTrue( "Duplicate unregistered user reply goes to Duplicate Purchase Attempt", super.doesMessageWithBatchProcessorExist( 
				( String )result2.get( "twitterId" ), "BATCH_EXECUTION_INSTANCE-%-FAILED_DUPLICATE_OPT_IN_ATTEMPT" ) );
	}
	
}
