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
public class MessageDivvyingTaskletManyRepliesTest extends BatchDatabaseIntegrationTest {
	
	@Before
	public void setUp() {
		super.setUp( ListingType.SELLING, GoodsType.PHYSICAL, null );
	}
	
	@Test
	public void testMultipleReplies() {
		
		
		/**
		 * Setup the replies
		 */
		
		String listingMessageTwitterId = super.testListingMessageTwitterId;
		
		// irrelevant reply from bob
		Map<String, Object> bobIrrelevant = super.insertReplyMessage( "622133588", 179L, listingMessageTwitterId, MessageStatus.PROCESSING, null, Boolean.FALSE );
		
		// irrelevant reply from duff
		Map<String, Object> duffIrrelevant = super.insertReplyMessage( "1688913799", 237L, listingMessageTwitterId, MessageStatus.PROCESSING, null, Boolean.FALSE );
		
		// relevant reply from bob (registered w/o preapproval)
		Map<String, Object> bobRelevant = super.insertReplyMessage( "622133588", 179L, listingMessageTwitterId, MessageStatus.PROCESSING, null, Boolean.TRUE );
		
		// relevant reply from lily (registered w preapproval)
		Map<String, Object> lilyRelevant = super.insertReplyMessage( "2596726242", 178L, listingMessageTwitterId, MessageStatus.PROCESSING, null, Boolean.TRUE );

		// relevant reply from reggie (unregistered)
		Map<String, Object> reggieRelevant = super.insertReplyMessage( "2579383766", null, listingMessageTwitterId, MessageStatus.PROCESSING, null, Boolean.TRUE );

		// relevant reply from billy (blocked)
		Map<String, Object> billyBlocked = super.insertReplyMessage( "2578905258", 180L, listingMessageTwitterId, MessageStatus.PROCESSING, null, Boolean.TRUE );

		// redundant reply from bob (registered w/o preapproval)
		Map<String, Object> bobRedundant = super.insertReplyMessage( "622133588", 179L, listingMessageTwitterId, MessageStatus.PROCESSING, null, Boolean.TRUE );
		
		// redundant reply from lily (registered w preapproval)
		Map<String, Object> lilyRedundant = super.insertReplyMessage( "2596726242", 178L, listingMessageTwitterId, MessageStatus.PROCESSING, null, Boolean.TRUE );

		// redundant reply from reggie (unregistered)
		Map<String, Object> reggieRedundant = super.insertReplyMessage( "2579383766", null, listingMessageTwitterId, MessageStatus.PROCESSING, null, Boolean.TRUE );
		
		/**
		 *  run the tasklet
		 */
		super.runDivvyUpMessages();
		
		/**
		 * confirm the messages are divvied up properly
		 */
		assertTrue( super.doesMessageWithBatchProcessorExist( 
				( String )bobIrrelevant.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-1-IRRELEVANT" ) );
		
		assertTrue( super.doesMessageWithBatchProcessorExist( 
				( String )duffIrrelevant.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-1-IRRELEVANT" ) );
		
		assertTrue( super.doesMessageWithBatchProcessorExist( 
				( String )bobRelevant.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-1-PENDING_PREAPPROVAL" ) );
		
		assertTrue( super.doesMessageWithBatchProcessorExist( 
				( String )lilyRelevant.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-1-ATTEMPT_PURCHASE-T%" ) );

		assertTrue( super.doesMessageWithBatchProcessorExist( 
				( String )reggieRelevant.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-1-PENDING_REGISTRATION" ) );
		
		assertTrue( super.doesMessageWithBatchProcessorExist( 
				( String )billyBlocked.get( BatchDatabaseIntegrationTest.REPLY_TWITTER_ID ), "BATCH_EXECUTION_INSTANCE-1-IRRELEVANT" ) );

		
		
	}

}
