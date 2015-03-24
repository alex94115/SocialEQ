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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;

public class MessageDivvyingTasklet extends RodLaverTasklet implements Tasklet, StepExecutionListener {

	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * SQL Statements
	 */

	private static String PRIOR_RUN_SKIPPED_MESSAGES =
			  " UPDATE Messages m "
			+ " SET m.batch_processor = NULL "
		    + " WHERE m.batch_processor IS NOT NULL "
			+ "   AND m.status = 'PROCESSING' "
			+ "   AND m.id <= ? ";

	private static String UNBLOCKED_PENDING_MEANS_OF_PAYMENT_MESSAGES =
			  " UPDATE Messages m "
			+ "   INNER JOIN CurrentPreapprovals cp USING(user_id) "
			+ "   INNER JOIN ListingReplies lr ON m.id = lr.reply_id "
		    + " SET m.batch_processor = NULL, m.status='PROCESSING' "
			+ " WHERE m.status='PENDING_MEANS_OF_PAYMENT' "
			+ "   AND m.batch_processor IS NOT NULL "
			+ "   /* only if it's a simple item that can be purchased with a preapproval */"
			+ "   AND lr.listing_has_single_inventory IS TRUE "
			+ "   AND m.id <= ? ";

	private static String IRRELEVANT_REPLY_MESSAGES =
			  " UPDATE Messages m "
			+ "   INNER JOIN ListingReplies lr ON m.id = lr.reply_id "
			+ " SET m.batch_processor = ? "
			+ " WHERE ( lr.reply_contains_keyword IS NOT TRUE OR lr.reply_user_id = lr.seller_id ) "
			+ "   /* not processed */ "
			+ "   AND lr.reply_status = 'PROCESSING' "
			+ "   /* not claimed by another job */ "
			+ "   AND m.batch_processor IS NULL "
			+ "   AND m.id <= ? ";
	
	private static String BLOCKED_USER_REPLY_MESSAGES =
			  " UPDATE Messages m "
			+ "   INNER JOIN ListingReplies lr ON m.id = lr.reply_id "
			+ "   INNER JOIN Users u ON m.user_id = u.id "
			+ " SET m.batch_processor = ? "
			+ " /* not processed */ "
			+ " WHERE lr.reply_status = 'PROCESSING' "
			+ "   /* not claimed by another job */ "
			+ "   AND m.batch_processor IS NULL "
			+ "   AND u.is_user_blocked IS TRUE "
			+ "   AND m.id <= ? ";

	private static String PENDING_USER_REGISTRATION_MESSAGES =
			  " UPDATE Messages m "
			+ "   INNER JOIN ListingReplies lr ON m.id = lr.reply_id "
			+ "   JOIN " 
			+ "   (  "
			+ "     SELECT MIN( m2.id ) AS reply_id " 
			+ "     FROM Messages m2 "
			+ "     /* 1. not processed */  "
			+ "     WHERE status='PROCESSING' "
			+ "       AND containsKeyword IS TRUE "
			+ "     GROUP BY m2.providerId, m2.providerUserId, m2.inResponseToTwitterId " 
			+ "   ) m2 ON lr.reply_id = m2.reply_id "
			+ " SET m.batch_processor=? "
			+ " /* 2. does contain the magic word */ "
			+ " WHERE lr.reply_contains_keyword IS TRUE "
			+ "   /* 3. not claimed by another job  */ "
			+ "   AND lr.reply_batch_processor IS NULL "
			+ "   /* 4. null user_id means it's an unregistered screen name */ "
			+ "   AND ( lr.reply_user_id IS NULL  OR  ( lr.reply_user_id IS NOT NULL AND lr.has_reply_user_accepted_tos IS NOT TRUE ) ) " 
			+ "   /* 5. ignore giveaways, since unregistered users can enter them */ "
			+ "   AND lr.listing_type = 'SELLING' "
			+ "   /* 6. before the 'high' reply message id */ "
			+ "   AND lr.reply_id <= ? ";
	

	// Used (in two separate steps) to identify replies to CANCELED and SOLD OUT listings
	private static String REGISTERED_USER_REPLIES_TO_LISTINGS_WITH_STATUS_FILTER =
			  " UPDATE Messages m "
			+ "   INNER JOIN ListingReplies lr ON m.id = lr.reply_id "
			+ "   JOIN  "
			+ "   (  "
			+ "     SELECT MIN( m2.id ) AS reply_id " 
			+ "     FROM Messages m2 "
			+ "     /* 3. not processed */ " 
			+ "     WHERE status='PROCESSING' "
			+ "       AND containsKeyword IS TRUE " 
			+ "     GROUP BY m2.providerId, m2.providerUserId, m2.inResponseToTwitterId " 
			+ "   ) m2 ON lr.reply_id = m2.reply_id "
			+ " SET m.batch_processor=? "
			+ " WHERE lr.reply_contains_keyword IS TRUE "
			+ "   AND lr.reply_status = 'PROCESSING' "
			+ "   AND lr.reply_batch_processor IS NULL "
			+ "   AND lr.listing_status = ? "
			+ "   AND "
			+ "   ( "
			+ "     /* 5. This clause looks for registered, unblocked, and current-TOS-accepted users */ "
			+ "     ( lr.reply_user_id IS NOT NULL AND lr.is_reply_user_blocked IS NOT TRUE AND lr.has_reply_user_accepted_tos IS TRUE ) "
			+ "     OR "
			+ "     /* 6. This clause looks for anyone replying to a canceled or sold out giveaway, regardless whether they are registered */ "
			+ "     ( lr.reply_user_id IS NULL AND lr.listing_type='CAMPAIGN' ) "
			+ "   )"
			+ "   AND lr.reply_id <= ? ";

	private static String PENDING_PREAPPROVAL_USER_REPLIES_TO_ACTIVE_LISTINGS =
			  " UPDATE Messages m "
			+ "   INNER JOIN ListingReplies lr ON m.id = lr.reply_id "
			+ "   JOIN  "
			+ "   (  "
			+ "     SELECT MIN( m2.id ) AS reply_id " 
			+ "     FROM Messages m2 "
			+ "     /* 3. not processed */ " 
			+ "     WHERE status='PROCESSING' "
			+ "       AND containsKeyword IS TRUE " 
			+ "     GROUP BY m2.providerId, m2.providerUserId, m2.inResponseToTwitterId " 
			+ "   ) m2 ON lr.reply_id = m2.reply_id "
			+ "   /* 1. inner join to user means this will only fetch rows associated to a registered user */ "
			+ "   INNER JOIN Users u ON lr.reply_user_id = u.id "
			+ "   LEFT OUTER JOIN CurrentPreapprovals cp ON lr.reply_user_id = cp.user_id "
			+ " SET m.batch_processor=? "
			+ " /* 2. user is not blocked */ "
			+ " WHERE lr.is_reply_user_blocked IS NOT TRUE "
			+ "   /* 3. user has accepted current tos */ "
			+ "   AND lr.has_reply_user_accepted_tos IS TRUE "
			+ "   /* 4. add a lag here so that users that have perhaps just registered have a few minutes to add a preapproval before we spam them */ "
			+ "   AND (u.updated < CURRENT_TIMESTAMP() - INTERVAL 10 MINUTE ) "
			+ "   /* 5. does contains the magic word */ "
			+ "   AND lr.reply_contains_keyword IS TRUE "
			+ "   /* 6. unprocessed */ "
			+ "   AND lr.reply_status='PROCESSING' "
			+ "   /* 7. not claimed by another job */ "
			+ "   AND lr.reply_batch_processor IS NULL "
			+ "   /* 8. active listings */ "
			+ "   AND lr.listing_status='ACTIVE' "
			+ "   /* 9. ignore giveaways */ "
			+ "   AND lr.listing_type = 'SELLING' "
			+ "   /* 10. does not have a Preapproval OR listing has complex inventory */ "
			+ "   AND ( cp.id IS NULL OR lr.listing_has_single_inventory IS FALSE ) "
			+ "   /* 11. less than the 'high' id */ "
			+ "   AND m.id <= ?  ";
	
	private static String ALREADY_PURCHASED_ATTEMPTS =
			  " UPDATE Messages m "
			+ "   INNER JOIN ListingReplies lr ON m.id = lr.reply_id "
			+ "   INNER JOIN CurrentPaymentStatus cp ON ( lr.listing_id = cp.listing_id AND lr.reply_user_id = cp.payer_id ) "
			+ " SET m.batch_processor = ? "
			+ " /* 2. does contains the magic word */ "
			+ " WHERE lr.reply_contains_keyword IS TRUE "
			+ "   /* 3. unprocessed */ "
			+ "   AND lr.reply_status='PROCESSING' "
			+ "   /* 4. not claimed by another job */ "
			+ "   AND lr.reply_batch_processor IS NULL "
			+ "   /* 5. active listings */ "
			+ "   AND lr.listing_status='ACTIVE' "
			+ "   /* 6. ignore giveaways */ "
			+ "   AND lr.listing_type = 'SELLING' "
			+ "   /* 7. less than 'high' id */ "
			+ "   AND m.id <= ? "
			+ "   /* 8. don't worry about associated payments that are failed or denied */ "
			+ "   AND cp.status != 'DENIED' "
			+ "   AND cp.status != 'FAILED' ";

	private static String ALREADY_HAS_PENDING_PURCHASE_ATTEMPTS = 
			" UPDATE Messages m "
			+ "  INNER JOIN ListingReplies lr1 ON m.id = lr1.reply_id "
			+ "  JOIN "
			+ "  ( "
			+ "    SELECT lr2.reply_user_id previous_reply_user_id, "
			+ "      lr2.listing_id previous_reply_listing_id,"
			+ "      lr2.provider_id previous_reply_provider_id,"
			+ "      lr2.reply_provider_user_id previous_reply_provider_user_id "
			+ "    FROM ListingReplies lr2 "
			+ "    WHERE ( lr2.reply_status = 'PENDING_MEANS_OF_PAYMENT' OR lr2.reply_status = 'PENDING_USER_REGISTRATION' ) "
			+ "  ) As Sub ON ( lr1.listing_id = previous_reply_listing_id  ) "
		  + " SET m.batch_processor = ? "
		  + " WHERE lr1.listing_status='ACTIVE' "
		  + "   AND lr1.listing_type = 'SELLING' "
		  + "   AND lr1.reply_contains_keyword IS TRUE "
		  + "   AND lr1.reply_status='PROCESSING' "
		  + "   AND lr1.reply_batch_processor IS NULL "
		  + "   AND "
		  + "   ( lr1.reply_user_id = previous_reply_user_id OR "
		  + "     ( lr1.provider_id = previous_reply_provider_id AND lr1.reply_provider_user_id = previous_reply_provider_user_id ) "
		  + "   )"
		  + "   AND lr1.reply_id <= ? ";

	private static String DUPLICATE_OPT_IN_ATTEMPTS =
			" UPDATE Messages m "
		  + " SET m.batch_processor = ? "
		  + " WHERE m.id IN  "
		  + " ( SELECT DISTINCT reply_id FROM "
		  + "   ( SELECT reply.id reply_id, reply.user_id, listing_message.listing_id l_id  "
		  + "	  FROM Messages reply  "
		  + "	    INNER JOIN Messages listing_message ON reply.inResponseToTwitterId = listing_message.twitterId "
		  + "	    INNER JOIN Listings l ON listing_message.listing_id = l.id "
		  + "	    INNER JOIN "
		  + "       ( "
		  + "	    	SELECT reply.providerUserId processed_opt_in_provider_user_id, reply.providerId processed_opt_in_provider_id, l.id processed_opt_in_listing_id "
		  + "	    	FROM Messages reply  "
		  + "	    	  INNER JOIN Messages lm ON reply.inResponseToTwitterId = lm.twitterId "
		  + "	    	  INNER JOIN Listings l ON lm.listing_id = l.id "
		  + "	    	 WHERE reply.providerId = lm.providerId "
		                  /* make sure we're joining against processed messages */
		  + "             AND reply.status != 'PROCESSING' "
		  + "             AND reply.status != 'IRRELEVANT' "
		  + "             AND reply.batch_processor IS NOT NULL "
		  + "             AND l.type='CAMPAIGN' "
		  			  // matching on the provider id and provider user id since the user may not be a hoot it registered user (i.e., their user id can be null)
		  + "	    ) As Sub ON reply.providerUserId = processed_opt_in_provider_user_id "
		  + "	  WHERE reply.providerId = processed_opt_in_provider_id"
		  + "       AND l.id =  processed_opt_in_listing_id"
		  + "       AND l.status='ACTIVE' "
		  + "	    AND l.type = 'CAMPAIGN' "
		  + "	    AND reply.containsKeyword IS TRUE "
		  + "	    AND reply.status='PROCESSING' "
		  + "	    AND reply.batch_processor IS NULL "
		  + "       AND reply.id <= ? "
		  + "   ) As Sub2 "
		  + " ) ";

	private static String ATTEMPT_PURCHASE_USER_REPLIES_TO_ACTIVE_PAY_LISTINGS =
			  " UPDATE Messages m "
			+ "   INNER JOIN ListingReplies lr ON m.id = lr.reply_id "
			+ "   JOIN "
			+ "   ( "
			+ "     SELECT MIN(lr2.reply_id) min_reply_id "
			+ "     FROM ListingReplies lr2 "
			+ "       JOIN CurrentPreapprovals cp ON lr2.reply_user_id = cp.user_id " 
			+ "     WHERE lr2.reply_status='PROCESSING' "
			+ "       AND lr2.reply_contains_keyword IS TRUE "
			+ "       AND lr2.has_reply_user_accepted_tos IS TRUE "
			+ "       AND lr2.is_reply_user_blocked IS NOT TRUE "
			+ "       AND lr2.reply_batch_processor IS NULL "
			+ "       AND lr2.listing_type = 'SELLING' "
			+ "       AND lr2.listing_status='ACTIVE' "
			+ "       AND lr2.listing_has_single_inventory = TRUE "
			+ "       AND lr2.listing_id % 10 = ? " 
			+ "     GROUP BY lr2.reply_user_id, lr2.listing_id "
			+ "    ) As Sub ON m.id = min_reply_id  "
			+ " SET m.batch_processor = ? "
			+ " WHERE m.id <= ? ";

	private static String ATTEMPT_ENTRY_USER_REPLIES_TO_ACTIVE_GIVEAWAY_LISTINGS =
			  " UPDATE Messages m "
			+ "   INNER JOIN ListingReplies lr ON m.id = lr.reply_id "
			+ "   /* Operate on only the earliest message (smallest id) grouping by the providerId, providerUserId, and the listing message's twitterId */ "
			+ "   JOIN "
			+ "   ( "
			+ "     SELECT MIN( m2.id ) AS reply_id " 
			+ "     FROM Messages m2 "
			+ "     /* 3. un processed */ " 
			+ "     WHERE status='PROCESSING' "
			+ "       AND containsKeyword IS TRUE " 
			+ "     GROUP BY m2.providerId, m2.providerUserId, m2.inResponseToTwitterId " 
			+ "   ) m2 ON lr.reply_id = m2.reply_id "
			+ " SET m.batch_processor = ? "
			+ "	WHERE lr.reply_contains_keyword IS TRUE " 
			+ "	  /* unprocessed */ "
			+ "	  AND lr.reply_status='PROCESSING' "
			+ "	  /* not claimed by another job */  "
			+ "	  AND lr.reply_batch_processor IS NULL  " 
			+ "	  /* active listings */ "
			+ "	  AND lr.listing_status='ACTIVE' "
			+ "	  /* only giveaways */ "
			+ "	  AND lr.listing_type = 'CAMPAIGN' "
			+ "	  /* allow unregistered users, but require TOS and unblocked if user is registered */ "
			+ "   AND ( lr.reply_user_id IS NULL OR ( lr.is_reply_user_blocked IS NOT TRUE AND lr.has_reply_user_accepted_tos IS TRUE  ) ) "
			+ "   AND m.id <= ? ";


	private static String UNBLOCKED_PENDING_SHIPPING_ADDRESS_MESSAGES =
			   "UPDATE Messages reply "
		    + "   INNER JOIN Payments p ON reply.id = p.message_id "
			+ " SET reply.batch_processor = ? "
		    + " WHERE reply.status = 'PENDING_SHIPPING_ADDRESS' "
            + "   AND p.shippingAddress_id IS NOT NULL "
            + "   AND reply.batch_processor IS NULL "
            + "   AND reply.id <= ? ";

	private static String UNBLOCKED_PENDING_SHIPMENT_MESSAGES =
			   "UPDATE Messages reply "
		    + "  INNER JOIN Payments p ON reply.id = p.message_id "
			+ " SET reply.batch_processor = ? "
            + " WHERE reply.status = 'PENDING_SHIPMENT' "
            + "   AND p.has_been_shipped IS TRUE "
            + "   AND reply.batch_processor IS NULL "
            + "   AND reply.id <= ? ";


	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		JdbcTemplate jdbcTemplate = new JdbcTemplate( super.getDataSource() );
		
		Long maxMessageId = jdbcTemplate.queryForObject( "SELECT max(id) FROM Messages", Long.class );
		System.out.println( "Message Divvying Tasklet current max message id: " + maxMessageId );

		// TODO figure out some way to identify messages that get skipped a lot (e.g., if their created on time gets to be old).
		// Clear the "batch processor" attribute of any prior-run skipped messages
		this.executeBatchUpdate( jdbcTemplate, PRIOR_RUN_SKIPPED_MESSAGES, maxMessageId, " prior run skipped messages." );

		// Formerly PENDING MEANS OF PAYMENT messages for which the user now has a PayPal preapproval
		this.executeBatchUpdate( jdbcTemplate, UNBLOCKED_PENDING_MEANS_OF_PAYMENT_MESSAGES, maxMessageId, " unblocked pending means of payment messages" );

		// Irrelevant and Blocked User messages
		super.executeUpdate( 
				jdbcTemplate, 
				IRRELEVANT_REPLY_MESSAGES, 
				super.createBatchProcessorKey( "-IRRELEVANT" ), 
				maxMessageId, 
				" irrelevant messages in elapsed time: " );
		
		super.executeUpdate( 
				jdbcTemplate, 
				BLOCKED_USER_REPLY_MESSAGES, 
				super.createBatchProcessorKey( "-IRRELEVANT" ), 
				maxMessageId, 
				" irrelevant blocked user messages in elapsed time: " );

		// Payment-intent replies where the user has already replied the same listing
		// These duplicate-request filters needs to be run prior to the 'pending registration' and 'pending preapproval' 
		// queries, so that a user cannot have two replies to the same listing become 'pending registration' or 'pending preapproval'
		super.executeUpdate( 
				jdbcTemplate, 
				ALREADY_PURCHASED_ATTEMPTS, 
				super.createBatchProcessorKey( "-FAILED_DUPLICATE_PURCHASE_ATTEMPT"), 
				maxMessageId, 
				" duplicate purchase (already paid) attempt messages in elapsed time: " );
		
		super.executeUpdate( 
				jdbcTemplate, 
				ALREADY_HAS_PENDING_PURCHASE_ATTEMPTS, 
				super.createBatchProcessorKey( "-FAILED_DUPLICATE_PURCHASE_ATTEMPT" ), 
				maxMessageId, 
				" duplicate purchase (has pending registration or pending purchase) attempt messages in elapsed time: " );
				
		// Payment-intent replies by unregistered users
		super.executeUpdate( 
				jdbcTemplate, 
				PENDING_USER_REGISTRATION_MESSAGES, 
				super.createBatchProcessorKey( "-PENDING_REGISTRATION" ), 
				maxMessageId, 
				" pending registration messages in elapsed time: " );

		// Payment-intent replies to canceled listings
		String canceledListingBatchProcessorKey = super.createBatchProcessorKey( "-CANCELED_LISTING" );

		long start = System.currentTimeMillis();
		int canceledListingMessages = jdbcTemplate.update( 
				REGISTERED_USER_REPLIES_TO_LISTINGS_WITH_STATUS_FILTER, 
				canceledListingBatchProcessorKey, 
				"CANCELED", 
				maxMessageId );

		if( this.logger.isDebugEnabled() ) {
			long elapsedTime = System.currentTimeMillis() - start;
			this.logger.debug( "Updated: " + canceledListingMessages + " canceled listing messages in elapsed time: " + elapsedTime );
		}

		// Payment-intent replies to completed (sold out) listings
		String completedListingBatchProcessorKey = super.createBatchProcessorKey( "-COMPLETED_LISTING" );

		start = System.currentTimeMillis();
		int completedListingMessages = jdbcTemplate.update( 
				REGISTERED_USER_REPLIES_TO_LISTINGS_WITH_STATUS_FILTER, 
				completedListingBatchProcessorKey, 
				"COMPLETED",
				maxMessageId );
		
		if( this.logger.isDebugEnabled() ) {
			long elapsedTime = System.currentTimeMillis() - start;
			this.logger.debug( "Updated: " + completedListingMessages + " completed listing messages in elapsed time: " + elapsedTime );
		}

		// Payment-intent replies where the user doesn't have a means of payment
		super.executeUpdate( 
				jdbcTemplate, 
				PENDING_PREAPPROVAL_USER_REPLIES_TO_ACTIVE_LISTINGS, 
				super.createBatchProcessorKey( "-PENDING_PREAPPROVAL" ), 
				maxMessageId,
				" pending preapproval messages in elapsed time: " );

		// Payment-intent replies where we can initate a payment attempt
		this.updateAttemptPurchaseMessages( jdbcTemplate, maxMessageId );

		// Enter-giveaway intent replies where the user has already entered the same giveaway
		super.executeUpdate( 
				jdbcTemplate, 
				DUPLICATE_OPT_IN_ATTEMPTS, 
				super.createBatchProcessorKey( "-FAILED_DUPLICATE_OPT_IN_ATTEMPT"), 
				maxMessageId, 
				" duplicate opt-in attempts in elapsed time: " );

		// Enter-giveaway intent replies
		super.executeUpdate( 
				jdbcTemplate, 
				ATTEMPT_ENTRY_USER_REPLIES_TO_ACTIVE_GIVEAWAY_LISTINGS, 
				super.createBatchProcessorKey( "-USER_OPT_IN_ACTIVE_CAMPAIGN" ), 
				maxMessageId, 
				" valid giveaway opt-in messages in elapsed time: " );

		// Unblocked PENDING_SHIPPING_ADDRESS messages
		super.executeUpdate( 
				jdbcTemplate, 
				UNBLOCKED_PENDING_SHIPPING_ADDRESS_MESSAGES, 
				super.createBatchProcessorKey( "-UNBLOCKED_PENDING_SHIPPING_ADDRESS" ), 
				maxMessageId, 
				" unblocked pending shipping address messages in elapsed time: " );

		// Unblocked PENDING_SHIPMENT messages
		super.executeUpdate( 
				jdbcTemplate, 
				UNBLOCKED_PENDING_SHIPMENT_MESSAGES, 
				super.createBatchProcessorKey( "-UNBLOCKED_PENDING_SHIPMENT" ), 
				maxMessageId, 
				" unblocked pending shipment messages in elapsed time: " );

		return RepeatStatus.FINISHED;
	}


	void updateAttemptPurchaseMessages( JdbcTemplate jdbcTemplate, Long maxMessageId ) {

		for( int i = 0; i < 10; i++ ) {
			long start = System.currentTimeMillis();
			String attemptPurchaseBatchProcessorKey = super.createBatchProcessorKey( "-ATTEMPT_PURCHASE-T" + i );
			int completedListingMessages = jdbcTemplate.update( 
					ATTEMPT_PURCHASE_USER_REPLIES_TO_ACTIVE_PAY_LISTINGS,  
					new Integer( i ), // mod parameter
					attemptPurchaseBatchProcessorKey, // batch processor key 
					maxMessageId // high message id
				 );
			
			if( this.logger.isDebugEnabled() ) {
				long elapsedTime = System.currentTimeMillis() - start;
				this.logger.debug( "Updated: " + completedListingMessages + " attempt purchase messages into thread: " + i + " in elapsed time: " + elapsedTime );
			}
		}
	}
	
	void executeBatchUpdate( JdbcTemplate jdbcTemplate, String sql, Long maxMessageId, String debugMessage ) {
		long start = System.currentTimeMillis();
		int updated = jdbcTemplate.update( sql, maxMessageId );
		if( this.logger.isDebugEnabled() ) {
			long elapsedTime = System.currentTimeMillis() - start;
			this.logger.debug( "Updated: " + updated + debugMessage + " in elapsed time: " + elapsedTime );
		}
	}


}
