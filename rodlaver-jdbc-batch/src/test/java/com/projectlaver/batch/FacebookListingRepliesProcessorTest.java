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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.social.facebook.api.Comment;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.databind.JsonNode;
import com.projectlaver.util.RodlaverQueries;

public class FacebookListingRepliesProcessorTest {

//	@Test
//	public void testGetFacebookPagePosts() {
//		
//		// Facebook Page for our test user Regtestfred (it's the 'Regtestfred Page')
//		String pageId = "607584439357574";
//		String lastCommentRetrievedTime = "2014-11-08T23:20:00+0000";
//		
//		Facebook api = new FacebookTemplate( TestConstants.FRED_ACCESS_TOKEN );
//		RestOperations ro = api.restOperations();
//		
//		JsonNode responseNode = ro.getForObject( Facebook.GRAPH_API_URL + "{pageId}/tagged?limit=25&since={lastCommentRetrievedTime}", JsonNode.class, pageId, lastCommentRetrievedTime );
//		//JsonNode responseNode = ro.getForObject( Facebook.GRAPH_API_URL + "{pageId}/tagged?limit=25", JsonNode.class, pageId );
//		JsonNode dataNode = responseNode.findPath( "data" );
//		Iterator<JsonNode> taggedStatuses = dataNode.iterator();
//		while( taggedStatuses.hasNext() ) {
//			JsonNode taggedStatus = taggedStatuses.next();
//			
//			String id = taggedStatus.path( "id" ).asText();
//			String text= taggedStatus.path( "message" ).asText();
//			String createdTime = taggedStatus.path( "created_time" ).asText();
//			JsonNode fromNode = taggedStatus.path( "from" );
//			String username = fromNode.path( "name" ).asText();
//			String providerUserId = fromNode.path( "id" ).asText();
//			
//			System.out.println( String.format( "Message id: %s, text: '%s', createdTime: %s, from username: %s, and providerUserId: %s", id, text, createdTime, username, providerUserId  ));
//		}
//	}
	
//	@Test
//	public void testGetPhotoComments() {
//		
//		FacebookListingRepliesProcessor processor = new FacebookListingRepliesProcessor();
//		MultiValueMap<String, String> queryParameters = this.createBasicQueryParameters();
//
//		// first page pointer is the last one retrieved
//		String nextPagePointer = null;
//		if( nextPagePointer != null ) {
//			queryParameters.add( FacebookListingRepliesProcessor.AFTER_KEY, nextPagePointer );
//			System.out.println( "Trying to fetch comments after the cursor pointer: " + nextPagePointer );
//		}
//
//		Facebook api = new FacebookTemplate( TestConstants.FRED_ACCESS_TOKEN );
//		
//		Boolean isFirstPass = true;
//		while( true ) {
//			
//			PagedList<Comment> comments = null;
//			// Ask facebook for comments on this piece of content
//			String contentId = "607584439357574_677535389029145";
//			
//			System.out.println( String.format( "Querying with parameters: %s", queryParameters ));
//			comments = api.fetchConnections( contentId, "comments", Comment.class, queryParameters );
//			
//			// Write comments to the database (if any)
//			this.writeCommentsPage( comments, processor );
//
//			PagingParameters paging = comments.getNextPage();
//
//			// If paging is not null, then there is another page of comments. 
//			// In other words, the number of new comments is greater than the PAGE_SIZE_LIMIT
//			if( paging != null ) {
//				
//				System.out.println( "Expect that more comments exist, so continuing loop: " + ReflectionToStringBuilder.reflectionToString( paging ));
//
//				nextPagePointer = paging.getAfter();
//				queryParameters.remove( FacebookListingRepliesProcessor.AFTER_KEY);
//				queryParameters.add( FacebookListingRepliesProcessor.AFTER_KEY, nextPagePointer );
//				
//				// remove the first pass parameter
//				if( isFirstPass ) {
//					queryParameters.remove( "since" );
//					isFirstPass = false;
//				}
//				
//			} else {
//
//				RestOperations ro = api.restOperations();
//				JsonNode node = ro.getForObject( Facebook.GRAPH_API_URL + "{contentId}/comments?limit=50&filter=stream&after={nextPagePointer}", JsonNode.class, contentId, nextPagePointer );
//				String updatedNextPagePointer = node.findPath( "paging" ).path( "cursors" ).path( "after" ).asText(); 
//				
//				// Paging is null, so there are no more comments to retrieve on this piece of content. 
//				System.out.println( "No more comments expected, so saving cursor for next fetch: " + updatedNextPagePointer );
//				break;
//			}
//
//		}
//	}
	
//	void writeCommentsPage( PagedList<Comment> comments, FacebookListingRepliesProcessor processor ) {
//		
//		for( Comment comment : comments ) {
//
//			String messageKeyword = processor.parseKeyword( comment.getMessage() );
//
//			System.out.println( String.format( "Read comment with id: %s, createdTime: %d", comment.getId(), comment.getCreatedTime().getTime()  ));
//
//		}
//		
//	}
	
	@Test
	public void testTime() {
		
		System.out.println( new Date( 0L ) );
		
		Long oneDay = 1000L * 60 * 60 * 24;
		System.out.println( oneDay );
		System.out.println( new Date( oneDay ) );
		
	}
	
	MultiValueMap<String, String> createBasicQueryParameters() {
		// Set up query parameters
		MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<String, String>();

		// max comments per page
		queryParameters.add( FacebookListingRepliesProcessor.PAGE_SIZE_LIMIT_KEY, "1" );

		// use strict chronological order
		queryParameters.add( FacebookListingRepliesProcessor.FILTER_TYPE_KEY, FacebookListingRepliesProcessor.STREAM_FILTER );

		// used on the first pass
		queryParameters.add( "since", "1415037811000" );
		
		return queryParameters;
	}
	
}
