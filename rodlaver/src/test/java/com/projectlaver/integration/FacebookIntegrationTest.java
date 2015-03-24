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

import static org.junit.Assert.*;

import java.net.URI;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.social.facebook.api.Account;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Page;
import org.springframework.social.facebook.api.PageOperations;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Comment;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.support.URIBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.projectlaver.test.config.SocialTestConfig;
import com.projectlaver.test.config.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class FacebookIntegrationTest {
	
	private static final String COMMENTS_POST_PATH = "/comments/";
	private static final String MESSAGE_POST_PARAMETER = "message";
	
	@Autowired
	SocialTestConfig socialTestConfig;

	@Test
	public void testGetContentId() {
		
		String facebookPageId = "607584439357574";
		
		URIBuilder uriBuilder = URIBuilder.fromUri( String.format( Facebook.GRAPH_API_URL + "%s/posts", facebookPageId ));
		uriBuilder.queryParam( "limit", "5" );
		uriBuilder.queryParam( "filter", "stream" );
		uriBuilder.queryParam( "access_token", this.socialTestConfig.getFredFacebookAccessToken() );
		URI uri = uriBuilder.build();

		RestTemplate restTemplate = new RestTemplate();
		JsonNode responseNode = restTemplate.getForObject( uri, JsonNode.class );
		JsonNode dataNode = responseNode.path( "data" );
		
		String contentId = dataNode.findValue( "id" ).toString().replaceAll( "\"", "" );
		assertNotNull( contentId );	
	}
	
	@Test
	public void deletePagePosts() {
		
		Facebook api = new FacebookTemplate( this.socialTestConfig.getFredFacebookAccessToken() );
		
		String facebookPageId = "607584439357574";
		PagedList<Post> posts = api.feedOperations().getPosts( facebookPageId );
		
		for( Post post : posts ) {
			System.out.println( "Deleting post with id: " + post.getId() );
			api.delete( post.getId() );
			System.out.println( "Deleted post with id: " + post.getId() );
		}
	}
	
	@Test
	public void testGetPageToken() {
		Facebook api = new FacebookTemplate( this.socialTestConfig.getFredFacebookAccessToken() );
		PagedList<Account> accounts = api.pageOperations().getAccounts();

		String userFacebookPageId = "636521906382409";

		Iterator<Account> accountIterator = accounts.iterator();
		while( accountIterator.hasNext() ) {
			Account account = accountIterator.next();
			if( account.getId().equals( userFacebookPageId ) ) {
				System.out.println( ToStringBuilder.reflectionToString( account ) );
			}
		}
	}


	/**
	 * This is a working method posts a photo into an album on a Facebook Page.
	 */
	@Test
	public void testPostPhoto() {
		Facebook api = new FacebookTemplate( this.socialTestConfig.getFredFacebookAccessToken() );
	
		Resource photo = new FileSystemResource( "~/Desktop/A4be2cPCAAAy7Ph.jpg" );

		String pageId = "636521906382409";
		String albumId = "688769331157666";
		String caption = "Photo caption at 9:40 am GMT.";

		PageOperations po = api.pageOperations();
		String photoId = po.postPhoto(pageId, albumId, photo, caption );

		System.out.println( photoId );
	}


	@Test
	public void testGetContentIdToo() {

		String pageId = "636521906382409";
		URIBuilder uriBuilder = URIBuilder.fromUri( Facebook.GRAPH_API_URL + pageId + "/posts" );
		uriBuilder.queryParam( "limit", "1" );
		uriBuilder.queryParam( "filter", "stream" );
		
		uriBuilder.queryParam( "access_token", this.socialTestConfig.getFredFacebookAccessToken() ); 
		URI uri = uriBuilder.build();

		RestTemplate restTemplate = new RestTemplate();
		JsonNode responseNode = restTemplate.getForObject( uri, JsonNode.class );
		System.out.println( ToStringBuilder.reflectionToString( responseNode ) );

		String contentId = responseNode.path( "data" ).findValue( "id" ).toString().replaceAll( "\"", "" );
		System.out.println( "Content id: " + contentId );

	}


	@Test
	public void graphQueryTest() {
		Facebook api = new FacebookTemplate( "" );

		PagingParameters parameters = new PagingParameters(null, null, 0L, null );

		PagedList<Comment> comments = api.commentOperations().getComments( "636521906382409_636539856380614" , parameters );
		for( Comment comment : comments ) {
			System.out.println( ToStringBuilder.reflectionToString( comment ));
			Reference reference = comment.getFrom();
			System.out.println( ToStringBuilder.reflectionToString( reference ));
		}
	}
	
	@Test
	public void sandboxUserGraphQueryTest() {
		
		Facebook api = new FacebookTemplate( this.socialTestConfig.getFredFacebookAccessToken() );
		PagingParameters parameters = new PagingParameters(null, null, 0L, null );

		// the identifier here is the listing message's content id (aka twitterId in the db)
		PagedList<Comment> comments = api.commentOperations().getComments( "607584439357574_607605376022147", parameters );
		for( Comment comment : comments ) {
			System.out.println( ToStringBuilder.reflectionToString( comment ));
			Reference reference = comment.getFrom();
			System.out.println( ToStringBuilder.reflectionToString( reference ));
		}
	}
	
	@Test
	public void retrieveAsBatchJobTest() {
		
		// Set up query parameters
		MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<String, String>();

		// max comments per page
		queryParameters.add( "limit", "50" );

		// use strict chronological order
		queryParameters.add( "filter", "stream" );

		Facebook api = new FacebookTemplate( this.socialTestConfig.getFredFacebookAccessToken() );
		while( true ) {
			PagedList<Comment> comments = api.fetchConnections( "607584439357574_607605376022147", "comments", Comment.class, queryParameters );
			
			for( Comment comment : comments ) {

				// skip over comments that came from this page (in other words, our responses to users' comments)
				if( !comment.getFrom().getId().equals( "607584439357574_607638949352123" ) ) {
					System.out.println( "Comment id: " + comment.getId() + ", comment message: " + comment.getMessage() );
				}
			}

			String nextPagePointer = null;
			PagingParameters paging = comments.getNextPage();
			if( paging != null ) {
				System.out.println( "Expect that more comments exist, so continuing loop: " + paging );
				nextPagePointer = paging.getAfter();
				queryParameters.remove( "after" );
				queryParameters.add( "after", nextPagePointer );
			} else {
				System.out.println( "No more comments expected, so saving cursor for next fetch: " + nextPagePointer );
				break;
			}
		}
		
	}

	@Test
	public void postReplyCommentAsApp() {
			
			Facebook api = new FacebookTemplate( this.socialTestConfig.getFredFacebookAccessToken() ); 
			
			String replyToMessageId = "636521906382409_776396965728235";

			URIBuilder uriBuilder = URIBuilder.fromUri( Facebook.GRAPH_API_URL + replyToMessageId + COMMENTS_POST_PATH );
			
			// Create a formatted message that includes some basic HTML
			String formattedMessage = "Good news! You won the giveaway - you can download your digital item here: \n\nhttps://rodlaver.elasticbeanstalk.com/listing/giveawayWinner/33090";
			//String formattedMessage = "Good news! You won the giveaway - you can download your digital item here: ";
			uriBuilder.queryParam( MESSAGE_POST_PARAMETER, formattedMessage );

			Object result = api.restOperations().postForObject( uriBuilder.build(), null, Object.class );
	}
	
	@Test
	public void postReplyAsUser() {
		Facebook api = new FacebookTemplate( this.socialTestConfig.getFredFacebookAccessToken() );
		
		URIBuilder uriBuilder = URIBuilder.fromUri( Facebook.GRAPH_API_URL + "607584439357574_633864976729520/comments/" );
		uriBuilder.queryParam( "message", "Test message" );

		Object result = api.restOperations().postForObject( uriBuilder.build(), null, Object.class );
		assertNotNull( "Result of posting content should not be null.", result );
	}

}
