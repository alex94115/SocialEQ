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

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class ManageExpiredAndSoldOutSellingListingsTasklet implements Tasklet {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private DataSource dataSource;


	/**
	 * This SQL finds and updates non-Campaign rows in the Listing table where the item is now expired.
	 * Campaigns are handled separately.
	 */
	private static String MARK_EXPIRED_LISTINGS_AS_CANCELED =
			  " UPDATE Listings l "
		    + " SET l.status='CANCELED' "
		    + " WHERE l.status = 'ACTIVE' "
			+ "   AND l.expires < NOW() "
			+ "   AND l.type = 'SELLING' ";

	/**
	 * This query finds non-Campaign listings where the status is ACTIVE but there all the inventory is
	 * sold out and marks the listing as COMPLETED.
	 */
	private static String MARK_SOLD_OUT_LISTINGS_AS_COMPLETED = 
			  " UPDATE Listings l "
			+ "   INNER JOIN ( "
			+ "     SELECT  i.listing_id, max(i.remainingQuantity) maxRemainingQuantity "
			+ "     FROM  Inventories i  "
			+ "     GROUP BY i.listing_id "
			+ "   ) As Sub ON l.id = listing_id "
			+ " SET l.status='COMPLETED' "
			+ " WHERE l.status='ACTIVE' "
			+ "   AND l.type='SELLING' "
			+ "   AND maxRemainingQuantity = 0";
	
	private static String MARK_NO_LONGER_SOLD_OUT_LISTINGS_AS_ACTIVE = 
			  " UPDATE Listings l "
			+ "   INNER JOIN ( "
			+ "     SELECT  i.listing_id, max(i.remainingQuantity) maxRemainingQuantity "
			+ "     FROM  Inventories i  "
			+ "     GROUP BY i.listing_id "
			+ "   ) As Sub ON l.id = listing_id "
			+ " SET l.status='ACTIVE' "
			+ " WHERE l.status='COMPLETED' "
			+ "   AND l.type='SELLING' "
			+ "   AND l.expires > NOW() "
			+ "   AND maxRemainingQuantity > 0";
	
	/**
	 * This SQL finds and updates rows in the Message table where the message is currently ACTIVE
	 * but the associated listing is CANCELED or COMPLETED.
	 */
	private static String MARK_EXPIRED_LISTING_MESSAGES_AS_CANCELED =
			  " UPDATE Messages m "
			+ "   INNER JOIN Listings l ON m.listing_id = l.id "
		    + " SET m.status='CANCELED' "
		    + " WHERE m.status = 'ACTIVE' "
			+ "   AND (l.status = 'CANCELED' OR l.status = 'COMPLETED') ";
	
	/**
	 * Kill messages that are "PENDING_USER_REGISTRATION" or "PENDING_MEANS_OF_PAYMENT" but where the listing has now expired
	 */
	private static String MARK_EXPIRED_LISTING_PENDING_REGISTRATION_MESSAGES_IRRELEVANT = 
			  " UPDATE Messages m "
			  + "  INNER JOIN ListingReplies lr ON m.id = lr.reply_id "
			  + "  INNER JOIN Listings l ON lr.listing_id = l.id "
			+ " SET m.status= 'IRRELEVANT' " // so there's no attempt to communicate
			+ " WHERE ( lr.reply_status='PENDING_USER_REGISTRATION' OR lr.reply_status='PENDING_MEANS_OF_PAYMENT') "
			+ "   AND l.expires < NOW() ";
	
	
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		JdbcTemplate jdbcTemplate = new JdbcTemplate( dataSource );

		// Expired SELLING listings
		long start = System.currentTimeMillis();
		int canceledExpiredListings = jdbcTemplate.update( MARK_EXPIRED_LISTINGS_AS_CANCELED );
		if( this.logger.isDebugEnabled() ) {
			long elapsedTime = System.currentTimeMillis() - start;
			this.logger.debug( "Updated: " + canceledExpiredListings + " expired listings in elapsed time: " + elapsedTime );
		}
		
		// Expired SELLING listings' related listing messages 
		start = System.currentTimeMillis();
		int canceledExpiredListingMessages = jdbcTemplate.update( MARK_EXPIRED_LISTING_MESSAGES_AS_CANCELED );
		if( this.logger.isDebugEnabled() ) {
			long elapsedTime = System.currentTimeMillis() - start;
			this.logger.debug( "Updated: " + canceledExpiredListingMessages + " expired listing messages in elapsed time: " + elapsedTime );
		}
		
		// Reply messages that haven't been acted on but the listing is now expired
		start = System.currentTimeMillis();
		int expiredListingPendingRegistrationMessages = jdbcTemplate.update( MARK_EXPIRED_LISTING_PENDING_REGISTRATION_MESSAGES_IRRELEVANT );
		if( this.logger.isDebugEnabled() ) {
			long elapsedTime = System.currentTimeMillis() - start;
			this.logger.debug( "Updated: " + expiredListingPendingRegistrationMessages + " expired listing pending registration messages in elapsed time: " + elapsedTime );
		}
		
		// Limited Quantity Listings that are ACTIVE but have no remaining inventory and should instead be COMPLETED
		start = System.currentTimeMillis();
		int soldOutLimitedQuantityListings = jdbcTemplate.update( MARK_SOLD_OUT_LISTINGS_AS_COMPLETED );
		if( this.logger.isDebugEnabled() ) {
			long elapsedTime = System.currentTimeMillis() - start;
			this.logger.debug( "Updated: " + soldOutLimitedQuantityListings + " limited quantity sold out listings as COMPLETED in elapsed time: " + elapsedTime );
		}
		
		// Listings that were formerly sold out but have become available again (perhaps due to expired checkout payments)
		start = System.currentTimeMillis();
		int noLongerSoldOutListings = jdbcTemplate.update( MARK_NO_LONGER_SOLD_OUT_LISTINGS_AS_ACTIVE );
		if( this.logger.isDebugEnabled() ) {
			long elapsedTime = System.currentTimeMillis() - start;
			this.logger.debug( "Updated: " + noLongerSoldOutListings + " COMPLETED but no-longer sold out listings as ACTIVE in elapsed time: " + elapsedTime );
		}

		return RepeatStatus.FINISHED;
	}

}
