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

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.projectlaver.integration.SocialProviders;

import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.User;
import twitter4j.UserMentionEntity;

public class TwitterListingResponseHandler extends ListingResponseHandler {

	/**
	 * Instance variables
	 */
	
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * Static variables
	 */
	
	private static final int MAX_RETRIES = 5;
	
	public static final String SELECT_LISTING_MESSAGE_TWITTER_ID_BY_SELLER_AND_HASHTAG = 
			  " SELECT m.twitterId "
			+ " FROM Messages m "
			+ "   INNER JOIN Listings l ON m.listing_id = l.id "
			+ " WHERE m.providerId = 'twitter' "
			+ "   AND m.providerUserId = ? "
			+ "   AND l.keyword = ? "
			+ " ORDER BY l.created DESC "
			+ " LIMIT 1 ";
	
	/**
	 * Constructors
	 */
	
	public TwitterListingResponseHandler() {
		super();
	}

	public TwitterListingResponseHandler( DataSource dataSource ) {
		super( dataSource );
	}
	
	public void processStatus( Status status ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "+processStatus() with status id: " + status.getId() );
		}
		
		String twitterId = this.longToString( status.getId() );
		User profile = status.getUser();

		// Convert twitterIds to Strings here because these fields are treated as varchars by the database
		String providerUserId = this.longToString( profile.getId() );
		String inReplyToStatusId = this.longToString( status.getInReplyToStatusId() );
		String tweetText = status.getText();
		Boolean isRetweet = ( status.getRetweetedStatus() != null );
		
		// these two fields are required; without an @mention and a #hashtag we do not need to persist this tweet
		String mentionedProviderUserId = this.getMentionedProviderUserId( status.getUserMentionEntities() );
		String hashtag = this.getMentionedHashtag( status.getHashtagEntities() );
		
		if( StringUtils.isNoneBlank( mentionedProviderUserId, hashtag )) {
			
			ReplyMessageDTO dto = new ReplyMessageDTO( SocialProviders.TWITTER, providerUserId, twitterId, inReplyToStatusId, null, isRetweet, tweetText, hashtag, INITIAL_STATUS, status.getCreatedAt() );
			
			// Use a try catch here to trap excptions
			try {
				super.processUserMessage( mentionedProviderUserId, dto, false );
			
			// Retry on deadlock
			} catch( DeadlockLoserDataAccessException e ) {
				
				this.logger.error( "Lost deadlock trying to insert tweet. Falling back on retry logic." );
				this.retryProcessAfterDeadlock( mentionedProviderUserId, dto );
								
			// Log on uncaught exception
			} catch( Exception e ) {
				this.logger.error( String.format( "Valid message with id: %s read by stream, but processing failed with an exception.", twitterId ), e );
				this.logger.error( "UNINSERTED TWEET: " + ToStringBuilder.reflectionToString( dto ));
			}
		}
		
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "-processStatus() with status id: " + status.getId() );
		}
	}
	
	public String getSelectListingMessageProviderContentIdSql() {
		return SELECT_LISTING_MESSAGE_TWITTER_ID_BY_SELLER_AND_HASHTAG;
	}
	
	void retryProcessAfterDeadlock( String mentionedProviderUserId, ReplyMessageDTO dto ) {
		
		int retryCount = 0;
		while( retryCount < MAX_RETRIES ) {
			
			try {
				super.processUserMessage( mentionedProviderUserId, dto, false );
				return; 
				
			} catch( DeadlockLoserDataAccessException e ) {
				retryCount = retryCount + 1;
				this.logger.error( "Lost deadlock trying to insert tweet. Retry Count is: " + retryCount );
			}
		}
		
		throw new RuntimeException( "Unable to recover from deadlock." );
	}

	String getMentionedHashtag(HashtagEntity[] hashtagEntities) {
		
		String hashtag = null;
		
		if( hashtagEntities != null && hashtagEntities.length > 0 ) {
			hashtag = "#" + hashtagEntities[0].getText();
		}
		
		return hashtag;
	}

	String getMentionedProviderUserId( UserMentionEntity[] userMentionEntities) {
		
		String mentionedProviderUserId = null;
		
		if( userMentionEntities != null && userMentionEntities.length > 0 ) {
			mentionedProviderUserId = this.longToString( userMentionEntities[0].getId() );
		}
		
		return mentionedProviderUserId;
	}

	String longToString( long longValue ) {
		
		String stringValue = null;
		if( longValue != -1 ) {
			stringValue = String.valueOf( longValue );
		}
		return stringValue;
	}
	
}
