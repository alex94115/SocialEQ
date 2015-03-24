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
package com.projectlaver.component;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.projectlaver.domain.User;
import com.projectlaver.integration.AbstractFacebookCommentFetcher;
import com.projectlaver.repository.BulkOperationsRepository;
import com.projectlaver.service.SocialService;
import com.projectlaver.service.UserService;

@Component
public class FacebookCommentPoller extends AbstractFacebookCommentFetcher {

	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private BulkOperationsRepository bulkOperationsRepository;
	
	@Autowired
	private SocialService socialService;
	
	@Autowired 
	private UserService userService;
	
	
	/**
	 * Constants
	 */
	private static final String PAGE_SIZE = "50";
	public static final DateFormat FB_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
	
	/**
	 * Instance methods
	 */
	
	@Scheduled(fixedDelay = 60000L)
	public void pollFacebookPagePosts() {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "+++pollFacebookPagePosts()" );
		}
		
		List<Long> sellerIds = this.bulkOperationsRepository.findUsersWithActiveFacebookListings();

		// iterate over the active listings and fetch new comments
		for( Long sellerId : sellerIds ) {

			// Get the seller
			User seller = this.userService.findOne( sellerId );
			
			// cut the last three 000's off of this time, since FB doesn't like them
			Date originalMaxFetchedPostTime = seller.getLastFacebookPagePostRetrievedAt();
			
			// Get a the facebook connection details and api client
			Connection<Facebook> connection = this.socialService.getFacebookConnection( seller.getUsername() );
			Facebook api = connection.getApi();
			
			// Ask facebook for tagged items on this page
			Long sinceValue = originalMaxFetchedPostTime.getTime() / 1000; // facebook doesn't use millis
			String url = String.format( Facebook.GRAPH_API_URL + "%s/tagged?fields=from,id,message,created_time&limit=%s&since=%d", seller.getFacebookPageId(), PAGE_SIZE, sinceValue );
			URI nextUrl = URIBuilder.fromUri( url ).build();
			
			Date newMaxFetchedPostTime = super.fetchComments( api, connection.getKey().getProviderUserId(), seller.getFacebookPageId(), nextUrl, false, originalMaxFetchedPostTime );
					
			if( newMaxFetchedPostTime != null ) {
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( String.format( "Updating the user's latest post retrieved to: %s", newMaxFetchedPostTime ) );
				}
				
				seller.setLastFacebookPagePostRetrievedAt( newMaxFetchedPostTime );
				this.userService.update( seller );
			}

		}
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "---pollFacebookPagePosts()" );
		}
	}
	
	@Override
	public URI determineNextGetUrl( final String facebookObjectId, final JsonNode paging, final Date originalMaxFetchedPostTime ) {
		
		URI result = null;
		
		if( StringUtils.isNotBlank( paging.path( "next" ).asText() ) ) {
			
			String nextPage = paging.path( "next" ).asText();
			Date nextPageUntilDate = this.getUntilValue( nextPage );
			
			if( nextPageUntilDate.after( originalMaxFetchedPostTime ) ) {
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( String.format( "Another page of posts exists for Facebook Page id: %s", facebookObjectId ) );
				}

				result = URIBuilder.fromUri( nextPage ).build();
				
			} else {
				
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( String.format( "For Facebook Page id: %s the next page until time is: %s but we've already retrieved a post at time %s, so continuing to next seller.", 
							facebookObjectId, nextPageUntilDate, originalMaxFetchedPostTime ) );
				}
			}
		} else {
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( String.format( "No more posts exist for Facebook Page id: %s", facebookObjectId ) );
			}
		}

		return result;
	}
	
	Date getUntilValue( final String url ) {
		
		Date result = null;
		
		if( StringUtils.isNotBlank( url ) && url.contains( "until=" )) {
			
			String until = url.substring( url.indexOf( "until=" ) + 6 );
			if( until.contains( "&" )) {
				until = until.substring( 0, until.indexOf( '&' ));
			}
			
			Long facebookLongResult = Long.valueOf( until );

			// facebook unix times don't have milliseconds
			result = new Date( facebookLongResult * 1000 );
		}
		
		return result;
	}

}
