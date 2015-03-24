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

import java.util.Date;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.social.facebook.api.Comment;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Component;

import com.projectlaver.integration.SocialProviders;

@Component
public class FacebookListingResponseHandler extends ListingResponseHandler {

	/**
	 * Instance variables
	 */
	
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * Static variables
	 */
	
	public static final String SELECT_LISTING_MESSAGE_FACEBOOK_ID_BY_SELLER_AND_HASHTAG = 
			  " SELECT m.twitterId "
			+ " FROM Messages m "
			+ "   INNER JOIN Listings l ON m.listing_id = l.id "
			+ " WHERE m.providerId = 'facebook' "
			+ "   AND m.providerUserId = ? "
			+ "   AND l.keyword = ? "
			+ " ORDER BY l.created DESC "
			+ " LIMIT 1 ";
	
	/**
	 * Constructors
	 */
	
	public FacebookListingResponseHandler() {
		super();
	}

	@Inject
	public FacebookListingResponseHandler( DataSource dataSource ) {
		super( dataSource );
	}
	
	@Override
	public String getSelectListingMessageProviderContentIdSql() {
		return SELECT_LISTING_MESSAGE_FACEBOOK_ID_BY_SELLER_AND_HASHTAG;
	}
	
	public void processStatus( Comment comment, String sellerProviderUserId, String listingMessageFacebookId, Boolean doSelectBeforeInsert ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "+processStatus() with comment id: " + comment.getId() );
		}
		
		// skip over comments that came from this seller (in other words, our responses to users' comments)
		if( !comment.getFrom().getId().equals( sellerProviderUserId ) ) {

			String commentFacebookId = comment.getId();
			String providerUserId = comment.getFrom().getId();
			String commentText = comment.getMessage();
			
			// Retweet is not applicable to Facebook
			Boolean isRetweet = false;

			String hashtag = this.getMentionedHashtag( comment.getMessage() );
			Date createdTime = comment.getCreatedTime();
			
			if( StringUtils.isNotBlank( hashtag )) {
				ReplyMessageDTO dto = new ReplyMessageDTO( SocialProviders.FACEBOOK, providerUserId, commentFacebookId, listingMessageFacebookId, null, isRetweet, commentText, hashtag, INITIAL_STATUS, createdTime );
				super.processUserMessage( sellerProviderUserId, dto, doSelectBeforeInsert );
			}
		}
		
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "-processStatus() with comment id: " + comment.getId() );
		}
	}
	
	public void processPost( String postId, String byProviderUserId, String message, Date createdAt, String sellerProviderUserId, Boolean doSelectBeforeInsert ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "+processPost() with post id: " + postId );
		}
		
		// skip over posts that came from this seller (in other words, our responses to users' comments)
		
		if( !byProviderUserId.equals( sellerProviderUserId ) ) {

			// Retweet is not applicable to Facebook
			Boolean isRetweet = false;

			String hashtag = this.getMentionedHashtag( message );
			
			if( StringUtils.isNotBlank( hashtag )) {
				ReplyMessageDTO dto = new ReplyMessageDTO( SocialProviders.FACEBOOK, byProviderUserId, postId, null, null, isRetweet, message, hashtag, INITIAL_STATUS, createdAt );
				super.processUserMessage( sellerProviderUserId, dto, doSelectBeforeInsert );
			}
		}
		
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "-processPost() with comment id: " + postId );
		}
	}
	
	
	/**
	 * Looks for the first word in the sentence that starts
	 * with the hash symbol. Will not find or process
	 * any subsequent hashtags.
	 * 
	 * TODO an imporovement would be to find all the hashtags
	 * in a given post and examine them all. 
	 * 
	 * @param text
	 * @return
	 */
	String getMentionedHashtag( String text ) {
		String keyword = "NO_KEYWORD_FOUND";

		if( StringUtils.isNotBlank( text ) ) {

			String[] tokens =  text.split("\\s+");
			for( String token : tokens ) {
				if( StringUtils.isBlank( token )) {
					continue;
				} else {
					if( token.charAt( 0 ) == '#' ) {
						keyword = token.trim();
						break;
					}
				}
			}
		}

		return keyword;
	}

}
