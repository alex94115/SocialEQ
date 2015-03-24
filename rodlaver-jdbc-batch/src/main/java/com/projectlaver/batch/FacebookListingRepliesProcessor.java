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

import java.net.URI;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.social.facebook.api.Comment;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.databind.JsonNode;
import com.projectlaver.batch.domain.FetchListingRepliesItem;
import com.projectlaver.integration.AbstractFacebookCommentFetcher;
import com.projectlaver.util.FacebookListingResponseHandler;
import com.projectlaver.util.RodlaverQueries;

public class FacebookListingRepliesProcessor extends AbstractFacebookCommentFetcher
	implements ItemProcessor<FetchListingRepliesItem, FetchListingRepliesItem> {

	/** 
	 * Instance variables
	 */
	
	@Autowired
	private DataSource datasource;
	
	@Autowired
	private FacebookListingResponseHandler responseProcessor;

	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * Static variables
	 */
	
	public static final String PAGE_SIZE_LIMIT_KEY = "limit";
	public static final String PAGE_SIZE_LIMIT = "50";

	public static final String AFTER_KEY = "after";

	public static final String FILTER_TYPE_KEY = "filter";
	public static final String STREAM_FILTER = "stream";

	public static final String COMMENT_PAGING_GET_URL = Facebook.GRAPH_API_URL + "{contentId}/comments?limit=" + PAGE_SIZE_LIMIT + "&filter=" + STREAM_FILTER + "&after={nextPagePointer}";

	/**
	 * Environment properties
	 */
	private String schema;

	/**
	 * Public methods
	 */

	@Override
	public FetchListingRepliesItem process( FetchListingRepliesItem cursorItem ) throws Exception {

		if( this.logger.isDebugEnabled() ) {
			String itemRaw = ToStringBuilder.reflectionToString( cursorItem );
			String accessTokenSuppressed = StringUtils.replacePattern( itemRaw, "accessToken=[0-9a-zA-Z]*,", "accessToken={protected},");
			this.logger.debug( String.format( "Entered the process method with item: %s", accessTokenSuppressed ));
		}

		FacebookTemplate api = new FacebookTemplate( cursorItem.getSellerAccessToken() );

		String url = String.format( Facebook.GRAPH_API_URL + "%s/comments?fields=id,from,message,created_time&limit=%s", cursorItem.getListingMessageProviderId(), PAGE_SIZE_LIMIT );
		URI nextUrl = URIBuilder.fromUri( url ).build();
		String sellerFacebookId = cursorItem.getSellerFacebookId(); 
		String listingMessageFacebookId = cursorItem.getListingMessageProviderId();
		
		Date existingMaxFetchedCommentTime = cursorItem.getExistingMaxFetchedCommentTime();
		
		Date newMaxFetchedCommentTime = super.fetchComments( api, sellerFacebookId, listingMessageFacebookId, nextUrl, true, existingMaxFetchedCommentTime );
		
		// set the updated max fetch comment time so we can store it
		cursorItem.setNewMaxFetchedCommentTime( ( newMaxFetchedCommentTime != null ? newMaxFetchedCommentTime : existingMaxFetchedCommentTime ));
		
		return cursorItem;
	}
	
	@Override
	public URI determineNextGetUrl( String facebookObjectId, JsonNode paging, Date originalMaxFetchedPostTime ) {		
		URI result = null;
		
		String afterCursor = paging.path( "cursors" ).path( "after" ).asText();
		if( StringUtils.isNotBlank( afterCursor ) ) {
			
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Expect that more comments exist, so continuing loop: " + paging );
			}

			String url = String.format( Facebook.GRAPH_API_URL + "%s/comments?fields=id,from,message,created_time&limit=%s&after=%s", 
					facebookObjectId, PAGE_SIZE_LIMIT, afterCursor );
			result = URIBuilder.fromUri( url ).build();
		}
		
		return result;
	}

	public void setSchema( String schema ) {
		this.schema = schema;
	}

}
