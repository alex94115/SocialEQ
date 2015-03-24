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
package com.projectlaver.batch.communication;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import com.projectlaver.batch.domain.MessageStateChangeCommunicationsCursorItem;

public class TwitterCommunicationSender implements ExternalCommunicationSender {

	/**
	 * Instance variables
	 */
	
	private final Log logger = LogFactory.getLog(getClass());

	private Map<Long, PreviousTweetTimespanCounts> senderPreviousTweetTimespanCounts = 
			new HashMap<Long, PreviousTweetTimespanCounts>();
	
	@Autowired
	private DataSource datasource;
	
	/**
	 * Properties that are associated with our Hoot it application on Twitter
	 * (identifies the API client). 
	 */
	
	@Value("${twitter.consumerKey}")
	private String apiKey;

	@Value("${twitter.consumerSecret}")
	private String apiSecret;
	
	// used in test to disable actually sending tweets
	@Value("${doDisableSendingReplies}")
	private Boolean doDisableSendingReplies;
	
	/**
	 * Constants
	 */
	
	private static final String COUNT_TWEETS = 
			  " SELECT count(*) "
			+ " FROM SentOutboundCommunications "
			+ " WHERE provider_id = 'twitter' "
			+ "   AND type = 'tweet' "
			+ "   AND user_id = ? ";
	
	private static final String PAST_TEN_SECONDS_CLAUSE =  " AND sent_time >= ( NOW() - INTERVAL 10 SECOND ) ";
	private static final String PAST_FIVE_MINUTES_CLAUSE =  " AND sent_time >= ( NOW() - INTERVAL 5 MINUTE ) ";
	private static final String PAST_HOUR_CLAUSE =  " AND sent_time >= ( NOW() - INTERVAL 1 HOUR ) ";
	private static final String PAST_DAY_CLAUSE =  " AND sent_time >= ( NOW() - INTERVAL 24 HOUR ) ";
	
	private static final String INSERT_SENT_OUTBOUND_COMMUNICATION = 
			  " INSERT INTO SentOutboundCommunications " 
			+ " SET user_id = ?,"
			+ "     provider_id = 'twitter', "
			+ "     type = 'tweet', "
			+ "     sent_time = NOW(), "
			+ "     version = 0 ";
	
	/**
	 * Public methods
	 */
	
	// Give Spring an empty constructor
	public TwitterCommunicationSender() {};
	
	@Override
	public Boolean sendExternalCommunication( MessageStateChangeCommunicationsCursorItem item, String formattedMessage ) {
		Boolean result = false;
		
		PreviousTweetTimespanCounts counts = this.throttle( item );
		
		// throttle will throw an exception if limits are exceeded. So if we get to this line, 
		// throttle limits aren't an issue
		result = this.sendPublicReply( item, formattedMessage );
		counts.incrementByOne();

		return result;
	}

	Boolean sendPublicReply( MessageStateChangeCommunicationsCursorItem item, String formattedMessage ) {
		
		Twitter twitterApi = new TwitterTemplate( this.apiKey, this.apiSecret, item.getFromAccessToken(), item.getFromSecret() );
		
		String tweetText = null;
		if( item.getToDisplayName() != null ) {
			tweetText = String.format("%s %s", item.getToDisplayName(), formattedMessage );
		} else {
			// For some reason we don't have this user's display name in the database, so fetch it from Twitter
			TwitterProfile profile = twitterApi.userOperations().getUserProfile( item.getToProviderUserId() );
			tweetText = String.format( "@%s %s", profile.getScreenName(), formattedMessage );

		}

		TimelineOperations tlo = twitterApi.timelineOperations();
		TweetData tweetData = new TweetData( tweetText );
		tweetData.inReplyToStatus( Long.valueOf( item.getMessageTwitterId() ) );
		Boolean result = false;
		
		try {
			
			Tweet tweet = null;
			
			// Post the reply to twitter, unless we're in disabled (testing) mode
			if( !this.doDisableSendingReplies ) {
				tweet = tlo.updateStatus(tweetData);
			} else {
				
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "Sending external tweets is disabled. Simulating instead." );
				}
				
				tweet = new Tweet( 1L, null, null, null, null, null, 1L, null, null );
			}
			
			// Persist the time we sent this tweet for this seller so we can throttle Tweet velocity
			JdbcTemplate template = new JdbcTemplate( this.datasource );
			template.update( INSERT_SENT_OUTBOUND_COMMUNICATION, item.getSellerId() );
			
			result = (tweet !=null);
		} catch( Exception e ) {
			this.logger.error( "Caught exception trying to post a tweet." );
			this.logger.error( "Exception: " + e );
			this.logger.error( "Exception message: " + e.getMessage() );
			this.logger.error( "Exception localized message: " + e.getLocalizedMessage() );
			
			if( e.getCause() != null ) {
				Throwable cause = e.getCause();
				this.logger.error( "Exception cause: " + cause );
				this.logger.error( "Cause message: " + cause.getMessage() );
			}
			
		}

		return result;
	}
	
	private PreviousTweetTimespanCounts throttle( MessageStateChangeCommunicationsCursorItem item ) {
		
		Long sellerId = item.getSellerId();
		PreviousTweetTimespanCounts counts = this.senderPreviousTweetTimespanCounts.get( sellerId );
		
		/** 
		 * If counts is null (not in the map), initialize it and add it to the map
		 * TODO make sure that this is using the right connection / has the right isolation to read newly-sent Tweets 
		 */
		
		JdbcTemplate template = new JdbcTemplate( datasource );
		if( counts == null ) {
			
			String lastFiveMinutesQuery = COUNT_TWEETS + PAST_FIVE_MINUTES_CLAUSE;
			Integer lastFiveMinutes = template.queryForObject( lastFiveMinutesQuery, Integer.class, sellerId );
			
			String lastHourQuery = COUNT_TWEETS + PAST_HOUR_CLAUSE;
			Integer lastHour = template.queryForObject( lastHourQuery, Integer.class, sellerId );
			
			String lastDayQuery = COUNT_TWEETS + PAST_DAY_CLAUSE;
			Integer lastDay = template.queryForObject( lastDayQuery, Integer.class, sellerId );
			
			counts = new PreviousTweetTimespanCounts();
			counts.setLastFiveMinutes( lastFiveMinutes );
			counts.setLastHour( lastHour );
			counts.setLastDay( lastDay );
			
			// add the counts to the Map
			this.senderPreviousTweetTimespanCounts.put( sellerId, counts );
		}
		
		// check the hard limits and throw a throttle limit exceeded exception if necessary
		if( counts.getLastDay() >= item.getSellerMaxTweetsPerTwentyFourHr() || 
			counts.getLastHour() >= item.getSellerMaxTweetsPerHr() || 
			counts.getLastFiveMinutes() >= item.getSellerMaxTweetsPerFiveMin() ) {
			
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Tweet count for seller id: " + sellerId + 
						" -- past day: " + counts.getLastDay() + ", past hour: " + counts.getLastHour() + 
						", past five minutes: " + counts.getLastFiveMinutes() );
			}
			
			throw new ThrottleLimitExceededException( "One or more twitter throttle limits exceeded for seller id: " + sellerId + ", must wait to send more tweets." );
		}
		
		// Always query for a current value for last ten seconds
		String lastTenSecondsQuery = COUNT_TWEETS + PAST_TEN_SECONDS_CLAUSE;
		Integer lastTenSeconds = template.queryForObject( lastTenSecondsQuery, Integer.class, sellerId );
		
		if( lastTenSeconds >= item.getSellerMaxTweetsPerTenSec() ) {
			// sleep for ten seconds
			try {
				Thread.sleep( 10000 );
			} catch (InterruptedException e) {
				throw new ThrottleLimitExceededException( e );
			}
		}
		
		return counts;
		
	}

}
