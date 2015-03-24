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
package com.projectlaver.integration;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.social.InvalidAuthorizationException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.databind.JsonNode;
import com.projectlaver.util.FacebookListingResponseHandler;

public abstract class AbstractFacebookCommentFetcher {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private FacebookListingResponseHandler responseHandler;
	
	public static final DateFormat FB_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);

	public Date fetchComments( Facebook api, String sellerFacebookUserId, String facebookObjectId, URI getUrl, Boolean doSelectBeforeInsert, Date originalMaxFetchedPostTime ) {
		
		RestOperations ro = api.restOperations();
		Date newMaxFetchedPostTime = null;
		
		// Loop over the pages of tagged posts for this seller
		while( true ) {
			
			Boolean doFetchNextPage = false;
			
			JsonNode responseNode = null;
			try {
				
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( String.format( "Requesting tagged posts via: %s", getUrl.toString() ) );
				}
				
				responseNode = ro.getForObject( getUrl, JsonNode.class );
				
			} catch( InvalidAuthorizationException e ) {
				this.logger.error( String.format( "Invalid access token for the seller with facebook id: %s, cannot retrieve page posts. Continuing.", sellerFacebookUserId ) );
				break;
			}
			
			Iterator<JsonNode> postsIterator = responseNode.path( "data" ).iterator();
			
			Boolean doesResponseContainPosts = false;
			if( postsIterator.hasNext() ) {
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( String.format( "Page contains post(s); entering the while loop to process the iterator." ) );
				}
				
				doesResponseContainPosts = true;
				
			} else {
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( String.format( "The request did not return any posts." ) );
				}
			}

			
			while( postsIterator.hasNext() ) {
				
				// Spring Social 2.0.0.M1 has a bug that prevents fetching Posts, so instead we have to extract the values from JSON directly
				JsonNode postNode = (JsonNode) postsIterator.next();
				String postId = postNode.get( "id" ).asText();
				String byProviderUserId = postNode.get( "from" ).get( "id" ).asText();
				String message = postNode.get( "message" ).asText();
				
				Date createdAt = null;
				try {
					createdAt = FB_DATE_FORMAT.parse( postNode.get( "created_time" ).asText() );
				} catch( ParseException e ) {
					throw new RuntimeException( "Error parsing Facebook date.", e );
				}
				
				// Pass the data to the response processor, which will lookup the virtual reply id and insert into the db
				try {
					this.responseHandler.processPost( postId, byProviderUserId, message, createdAt, sellerFacebookUserId, doSelectBeforeInsert );
				} catch ( DuplicateKeyException e ) {
					// don't treat this as fatal
					this.logger.info( "Non-fatal: Attempted to insert post with id: " + postId + ", but it has already been inserted." );
					this.logger.info( "DuplicateKeyException: " + e );
				}
				
				// keep track of the latest createdAt time
				if( createdAt != null && ( newMaxFetchedPostTime == null || createdAt.after( newMaxFetchedPostTime ) ) ) {
					newMaxFetchedPostTime = createdAt;
				}
			}
			
			if( this.logger.isDebugEnabled() && doesResponseContainPosts ) {
				this.logger.debug( "Completed iterating over this page." );
			}
			
			URI nextGetUrl = this.determineNextGetUrl( facebookObjectId, responseNode.path( "paging" ), originalMaxFetchedPostTime );
			
			if( nextGetUrl != null ) {
				
				// continue the loop to fetch another page
				getUrl = nextGetUrl;
				continue;
				
			} else {
				
				// break out of the loop and return
				break;
			}
				
		}		

		return newMaxFetchedPostTime;
	}
	
	public abstract URI determineNextGetUrl( String facebookObjectId, JsonNode paging, Date originalMaxFetchedPostTime );
	
}
