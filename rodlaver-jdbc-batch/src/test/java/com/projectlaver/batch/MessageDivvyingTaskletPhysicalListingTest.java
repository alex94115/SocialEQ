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
import com.projectlaver.util.ListingType;
import com.projectlaver.util.MessageStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/simple-job-launcher-context.xml",
									"/jobs/rodLaverJob.xml" })
public class MessageDivvyingTaskletPhysicalListingTest extends BatchDatabaseIntegrationTest {

	@Before
	public void setUp() {
		super.setUp( ListingType.SELLING, GoodsType.PHYSICAL, null );
	}
	
	@Test
	public void testInitialUnregisteredReply() {
		
		// primary test user
		String providerUserId1 = ( String )super.testUnregisteredUserAttributes.get( BatchDatabaseIntegrationTest.PROVIDER_USER_ID );

		// alternate test user
		String providerUserId2 = RandomStringUtils.randomNumeric( 9 );

		// reply to the primary test listing
		Map<String, Object> result1a = super.insertUnprocessedUnregisteredUserReplyMessage( providerUserId1, super.testListingMessageTwitterId, true );
		Map<String, Object> result2a = super.insertUnprocessedUnregisteredUserReplyMessage( providerUserId2, super.testListingMessageTwitterId, true );
		
		// reply to the alternate test listing
		Map<String, Object> result1b = super.insertUnprocessedUnregisteredUserReplyMessage( providerUserId1, super.alternateListingMessageTwitterId, true );
		Map<String, Object> result2b = super.insertUnprocessedUnregisteredUserReplyMessage( providerUserId2, super.alternateListingMessageTwitterId, true );
		
		// run the tasklet
		super.runDivvyUpMessages();
		
		assertTrue( "Single unregistered user reply goes to Pending Registration", this.doesMessageWithBatchProcessorExist( 
				( String )result1a.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-%-PENDING_REGISTRATION" ) );
		assertTrue( "Single unregistered user reply goes to Pending Registration", this.doesMessageWithBatchProcessorExist( 
				( String )result1b.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-%-PENDING_REGISTRATION" ) );
		
		assertTrue( "Single unregistered user reply goes to Pending Registration", this.doesMessageWithBatchProcessorExist( 
				( String )result2a.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-%-PENDING_REGISTRATION" ) );
		assertTrue( "Single unregistered user reply goes to Pending Registration", this.doesMessageWithBatchProcessorExist( 
				( String )result2b.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-%-PENDING_REGISTRATION" ) );
	}
	
	@Test
	public void testInitialRegisteredReply() {
		
		// primary test user
		String providerUserId1 = ( String )super.testRegisteredUserAttributes.get( BatchDatabaseIntegrationTest.PROVIDER_USER_ID );
		Long userId1 = ( Long )super.testRegisteredUserAttributes.get( BatchDatabaseIntegrationTest.USER_ID );
		
		// secondary test user
		String providerUserId2 = ( String )super.otherRegisteredUserAttributes.get( BatchDatabaseIntegrationTest.PROVIDER_USER_ID );
		Long userId2 = ( Long )super.otherRegisteredUserAttributes.get( BatchDatabaseIntegrationTest.USER_ID );
		
		Boolean replyContainsKeyword = true;
		
		// reply to the primary test listing
		Map<String, Object> result1a = super.insertUnprocessedRegisteredUserReplyMessage( providerUserId1, userId1, super.testListingMessageTwitterId, replyContainsKeyword );
		Map<String, Object> result2a = super.insertUnprocessedRegisteredUserReplyMessage( providerUserId2, userId2, super.testListingMessageTwitterId, replyContainsKeyword );
		
		// reply to the alternate test listing
		Map<String, Object> result1b = super.insertUnprocessedRegisteredUserReplyMessage( providerUserId1, userId1, super.alternateListingMessageTwitterId, replyContainsKeyword );
		Map<String, Object> result2b = super.insertUnprocessedRegisteredUserReplyMessage( providerUserId2, userId2, super.alternateListingMessageTwitterId, replyContainsKeyword );
		
		// run the tasklet
		super.runDivvyUpMessages();
		
		assertTrue( "Single registered user w/o preapproval reply goes to Pending Preapproval", this.doesMessageWithBatchProcessorExist( 
				( String )result1a.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-%-PENDING_PREAPPROVAL" ) );
		assertTrue( "Single unregistered user w/o preapproval  reply goes to Pending Registration", this.doesMessageWithBatchProcessorExist( 
				( String )result1b.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-%-PENDING_PREAPPROVAL" ) );
		
		assertTrue( "Single registered user w/o preapproval reply goes to Pending Registration", this.doesMessageWithBatchProcessorExist( 
				( String )result2a.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-%-PENDING_PREAPPROVAL" ) );
		assertTrue( "Single registered user reply goes to Pending Registration", this.doesMessageWithBatchProcessorExist( 
				( String )result2b.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-%-PENDING_PREAPPROVAL" ) );
	}
	
	@Test
	public void testDuplicateUnregisteredReplies() {
		
		String providerUserId = ( String )super.testUnregisteredUserAttributes.get( BatchDatabaseIntegrationTest.PROVIDER_USER_ID );

		Boolean replyContainsKeyword = true;
		
		// insert a couple unprocessed reply to the same listing from the same unregistered user
		Map<String, Object> result1 = super.insertUnprocessedUnregisteredUserReplyMessage( providerUserId,
				super.testListingMessageTwitterId, replyContainsKeyword );
		
		Map<String, Object> result2 = super.insertUnprocessedUnregisteredUserReplyMessage( providerUserId,
				super.testListingMessageTwitterId, replyContainsKeyword );
		
		super.runDivvyUpMessages();
		
		assertTrue( "First reply goes to Pending Registration", this.doesMessageWithBatchProcessorExist( 
				( String )result1.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-%-PENDING_REGISTRATION" ) );
		
		assertTrue( "Second reply is ignored (batch processor should stay null)", 
				this.doesMessageWithoutBatchProcessorExist( ( String )result2.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ) ) );
	}
	
	@Test
	public void testDuplicateFollowUpUnregisteredReplies() {
		
		Boolean replyContainsKeyword = true;
		
		// insert an already-processed PENDING REGISTRATION message
		String providerUserId = ( String )super.testUnregisteredUserAttributes.get( BatchDatabaseIntegrationTest.PROVIDER_USER_ID );
		super.insertReplyMessage( 
				providerUserId, null, super.testListingMessageTwitterId, MessageStatus.PENDING_USER_REGISTRATION, "SOME_UNIT_TEST_BATCH_PROCESSOR", replyContainsKeyword );
		
		// insert a couple unprocessed reply to the same listing from the same user
		Map<String, Object> result1 = super.insertUnprocessedUnregisteredUserReplyMessage( providerUserId,
				super.testListingMessageTwitterId, replyContainsKeyword );
		
		Map<String, Object> result2 = super.insertUnprocessedUnregisteredUserReplyMessage( providerUserId,
				super.testListingMessageTwitterId, replyContainsKeyword );
		
		super.runDivvyUpMessages();
		
		assertTrue( "Duplicate unregistered user reply goes to Duplicate Purchase Attempt", this.doesMessageWithBatchProcessorExist( 
				( String )result1.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-%-FAILED_DUPLICATE_PURCHASE_ATTEMPT" ) );
		
		assertTrue( "Duplicate unregistered user reply goes to Duplicate Purchase Attempt", this.doesMessageWithBatchProcessorExist( 
				( String )result2.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-%-FAILED_DUPLICATE_PURCHASE_ATTEMPT" ) );
	}
	
	@Test
	public void testReplyToSelfIsIgnored() {
		String sellerProviderUserId = ( String )super.sellerAttributes.get( BatchDatabaseIntegrationTest.PROVIDER_USER_ID );
		Long sellerUserId = ( Long )super.sellerAttributes.get( BatchDatabaseIntegrationTest.USER_ID );
		
		Boolean replyContainsKeyword = true;
		
		// reply to the primary test listing
		Map<String, Object> result1a = super.insertUnprocessedRegisteredUserReplyMessage( sellerProviderUserId, sellerUserId, super.testListingMessageTwitterId, replyContainsKeyword );
		
		// reply to the alternate test listing
		Map<String, Object> result1b = super.insertUnprocessedRegisteredUserReplyMessage( sellerProviderUserId, sellerUserId, super.alternateListingMessageTwitterId, replyContainsKeyword );
		
		// run the tasklet
		super.runDivvyUpMessages();
		
		assertTrue( "Single unregistered user reply goes to Pending Preapproval", this.doesMessageWithBatchProcessorExist( 
				( String )result1a.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-%-IRRELEVANT" ) );
		assertTrue( "Single unregistered user reply goes to Pending Registration", this.doesMessageWithBatchProcessorExist( 
				( String )result1b.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-%-IRRELEVANT" ) );
	}
	
	@Test
	public void testBlockedUserIsIgnored() {
		String blockedProviderUserId = ( String )super.testBlockedUserAttributes.get( BatchDatabaseIntegrationTest.PROVIDER_USER_ID );
		Long blockedUserId = ( Long )super.testBlockedUserAttributes.get( BatchDatabaseIntegrationTest.USER_ID );
		
		Boolean replyContainsKeyword = true;
		
		// reply to the primary test listing
		Map<String, Object> result1a = super.insertUnprocessedRegisteredUserReplyMessage( 
				blockedProviderUserId, blockedUserId, super.testListingMessageTwitterId, replyContainsKeyword );
		
		// reply to the alternate test listing
		Map<String, Object> result1b = super.insertUnprocessedRegisteredUserReplyMessage( 
				blockedProviderUserId, blockedUserId, super.alternateListingMessageTwitterId, replyContainsKeyword );
		
		// run the tasklet
		super.runDivvyUpMessages();
		
		assertTrue( "Blocked user reply goes to irrelevant", this.doesMessageWithBatchProcessorExist( 
				( String )result1a.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-%-IRRELEVANT" ) );
		assertTrue( "Blocked user reply goes to irrelevant", this.doesMessageWithBatchProcessorExist( 
				( String )result1b.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-%-IRRELEVANT" ) );
	}
	
	@Test
	public void testPreapprovalReply() {
		
		// primary test user
		String providerUserId1 = ( String )super.testPreapprovedUserAttributes.get( BatchDatabaseIntegrationTest.PROVIDER_USER_ID );
		Long userId1 = ( Long )super.testPreapprovedUserAttributes.get( BatchDatabaseIntegrationTest.USER_ID );
		
		Boolean replyContainsKeyword = true;
		
		// reply to the primary test listing
		Map<String, Object> result1a = super.insertUnprocessedRegisteredUserReplyMessage( providerUserId1, userId1, super.testListingMessageTwitterId, replyContainsKeyword );
		
		// run the tasklet
		super.runDivvyUpMessages();
		
		assertTrue( "Single reply registered user with preapproval reply goes to Attempt Payment", this.doesMessageWithBatchProcessorExist( 
				( String )result1a.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-1-ATTEMPT_PURCHASE-T%" ) );
	}

	
}
