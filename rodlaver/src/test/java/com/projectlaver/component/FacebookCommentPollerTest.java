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

import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.support.URIBuilder;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.projectlaver.domain.EffectiveDatedEntity;
import com.projectlaver.domain.User;
import com.projectlaver.integration.SocialProviders;
import com.projectlaver.repository.BulkOperationsRepository;
import com.projectlaver.service.SocialService;
import com.projectlaver.service.UserService;
import com.projectlaver.test.config.SocialTestConfig;
import com.projectlaver.util.FacebookListingResponseHandler;

@RunWith(EasyMockRunner.class)
public class FacebookCommentPollerTest extends EasyMockSupport {
	
	private SocialTestConfig socialTestUsers;
	
	@TestSubject
	private FacebookCommentPoller commentPoller = new FacebookCommentPoller();
	
	@Mock(type=MockType.NICE, name="bulkOperationsRepository")
	private BulkOperationsRepository bulkOperationsRepository;
	
	@Mock(type=MockType.NICE, name="socialService")
	private SocialService socialService;
	
	@Mock(type=MockType.NICE, name="userService")
	private UserService userService;
	
	@Mock(type=MockType.NICE, name="responseProcessor")
	private FacebookListingResponseHandler responseProcessor;
	
	@Mock(type=MockType.STRICT)
	private Connection<Facebook> facebookConnection;
	
	@Mock(type=MockType.STRICT)
	private Facebook api;
	
	@Mock(type=MockType.STRICT)
	private RestOperations ro;
	
	private ConnectionKey sellerConnectionKey = new ConnectionKey( SocialProviders.FACEBOOK, "33333" );

	@Test
	public void testRequestMultiplePostPages() throws Exception {
		
		expect( bulkOperationsRepository.findUsersWithActiveFacebookListings() ).andReturn( Arrays.asList( new Long[] { 1L } ) );
		
		/**
		 * Simulate that the next page has an until value that is greater than our maxFetchedPostTime. This case
		 * is when there is another page of posts that came after the last post we have previously fetched.
		 */
		Long maxFetchedPostTime = 0L;
		Long nextPageUntilValue = 1415896881L;
		
		// Set up the seller
		User user = new User();
		user.setUsername( "facebook/username" );
		user.setLastFacebookPagePostRetrievedAt( new Date( maxFetchedPostTime * 1000 ) ); // java dates use milliseconds precision
		user.setFacebookPageId( "12345" );
		expect( userService.findOne( eq( 1L ) )).andReturn( user );
		
		// The Facebook interaction
		expect( socialService.getFacebookConnection( eq( "facebook/username" ))).andReturn( facebookConnection );

		expect( facebookConnection.getApi() ).andReturn( api );
		expect( facebookConnection.getKey() ).andReturn( sellerConnectionKey ).anyTimes();
		
		expect( api.restOperations() ).andReturn( ro );
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode firstResponse = mapper.readTree("{ \"data\": [{\"from\": {\"id\": \"344102512424860\",\"name\": \"Barf Grooks\"},\"id\": \"815035588538880_815303421845430\",\"message\": \"#BourbonDigital\",\"created_time\": \"2014-11-13T16:41:22+0000\"}],\"paging\": {\"previous\": \"https://graph.facebook.com/v2.0/815035588538880/tagged?fields=from,id,message,created_time&limit=1&since=1415896882\",\"next\": \"https://graph.facebook.com/v2.0/815035588538880/tagged?fields=from,id,message,created_time&limit=1&until=" + 
				nextPageUntilValue +"\"}}" );
		
		// Expect an initial call and a follow up call for the next page
		expect( ro.getForObject( isA( URI.class ), isA( JsonNode.class.getClass() ) )).andReturn( firstResponse );
		
		JsonNode secondResponse = mapper.readTree("{\"data\": [ ] }" );
		
		expect( ro.getForObject( isA( URI.class ), isA( JsonNode.class.getClass() ) )).andReturn( secondResponse );
		
		// replay mode
		replayAll();
		
		commentPoller.pollFacebookPagePosts();
		
		verifyAll();
	}
	
	@Test
	public void testAlreadyRetrievedOlderPosts() throws Exception {
		
		expect( bulkOperationsRepository.findUsersWithActiveFacebookListings() ).andReturn( Arrays.asList( new Long[] { 1L } ) );
		
		/**
		 * Simulate that the next page has an until value that is greater than our maxFetchedPostTime. This case
		 * is when there is another page of posts that came after the last post we have previously fetched.
		 */
		Long maxFetchedPostTime = 1415907469L;
		Long nextPageUntilValue = 1415907468L;
		
		// Set up the seller
		User user = new User();
		user.setUsername( "facebook/username" );
		user.setLastFacebookPagePostRetrievedAt( new Date( maxFetchedPostTime * 1000 ) ); // the java method uses milliseconds precision
		user.setFacebookPageId( "12345" );
		expect( userService.findOne( eq( 1L ) )).andReturn( user );
		
		// The Facebook interaction
		expect( socialService.getFacebookConnection( eq( "facebook/username" ))).andReturn( facebookConnection );

		expect( facebookConnection.getApi() ).andReturn( api );
		expect( facebookConnection.getKey() ).andReturn( sellerConnectionKey ).anyTimes();
		
		expect( api.restOperations() ).andReturn( ro );
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode firstResponse = mapper.readTree("{ \"data\": [{\"from\": {\"id\": \"344102512424860\",\"name\": \"Barf Grooks\"},\"id\": \"815035588538880_815303421845430\",\"message\": \"#BourbonDigital\",\"created_time\": \"2014-11-13T16:41:22+0000\"}],\"paging\": {\"previous\": \"https://graph.facebook.com/v2.0/815035588538880/tagged?fields=from,id,message,created_time&limit=1&since=1415896882\",\"next\": \"https://graph.facebook.com/v2.0/815035588538880/tagged?fields=from,id,message,created_time&limit=1&until=" + 
				nextPageUntilValue +"\"}}" );
		
		// Expect only one call for posts, since the next page pointer ends prior to a post we've already retrieved
		expect( ro.getForObject( isA( URI.class ), isA( JsonNode.class.getClass() ) )).andReturn( firstResponse );
		
		// replay mode
		replayAll();
		
		commentPoller.pollFacebookPagePosts();
		
		verifyAll();
	}
	
	
	@Test
	public void tempTest() throws Exception {

		// Fred access token 
		Facebook api = new FacebookTemplate( this.socialTestUsers.getFredFacebookAccessToken() );
		RestOperations ro = api.restOperations();

		Long since = 0L;
		String pageId = "512734142199949"; //select facebook_page_id from Users where username='facebook/regfred828';
		String urlString = String.format( Facebook.GRAPH_API_URL + "%s/tagged?fields=from,id,message,created_time&limit=1&since=%d", pageId, since );
		System.out.println( String.format( "Requesting posts with the url: %s, since: %d", urlString, since ));
		this.logUnixTime( since );
		
		URI nextUrl = URIBuilder.fromUri( urlString ).build();
		
		while( true ) {
			JsonNode responseNode = ro.getForObject( nextUrl, JsonNode.class );
			
			Iterator postsIterator = responseNode.path( "data" ).iterator();
			
			while( postsIterator.hasNext() ) {
				JsonNode postNode = (JsonNode) postsIterator.next();
				String postId = postNode.get( "id" ).asText();
				String byProviderUserId = postNode.get( "from" ).get( "id" ).asText();
				String message = postNode.get( "message" ).asText();
				
				String createdAtText = postNode.get( "created_time" ).asText();
				Date createdAtDate = FacebookCommentPoller.FB_DATE_FORMAT.parse( createdAtText );
				
				System.out.println( String.format( "Post id: %s, byProviderUserId: %s, message: %s, createdAt: %s", postId, byProviderUserId, message, FacebookCommentPoller.FB_DATE_FORMAT.format( createdAtDate ) ) );
			}
			
			String nextPage = responseNode.path( "paging" ).path( "next" ).asText();
			
			if( StringUtils.isNotBlank( nextPage )) {
				System.out.println( String.format( "Next page pointer: %s", nextPage ));
				Long until = Long.valueOf( nextPage.substring( nextPage.lastIndexOf( "=" ) + 1 ) );
				this.logUnixTime( until );
				
				nextUrl = URIBuilder.fromUri( nextPage ).build();
			} else {
				System.out.println( "No more pages." );
				break;
			}
		}
	}
	
	@Test
	public void testFetchSinceTime() {
		
		Facebook api = new FacebookTemplate(  this.socialTestUsers.getFredFacebookAccessToken() );
		RestOperations ro = api.restOperations();

		String pageId = "607584439357574";
		String pageSize = "1";
		Long since = 1415674207000L / 1000;
		
		String url = String.format( Facebook.GRAPH_API_URL + "%s/tagged?fields=from,id,message,created_time&limit=%s&since=%d", pageId, pageSize, since );
		URI nextUrl = URIBuilder.fromUri( url ).build();
		
		System.out.println( String.format( "Will request page posts with the url: %s", nextUrl.toString() ) );
		JsonNode responseNode = ro.getForObject( nextUrl, JsonNode.class );
		
	}
	
	@Test
	public void testTranslateTimes() {
		
		Long previousLong = 1415896882L;
		Date previous = new Date( previousLong * 1000 );
		
		Long untilLong = 1415896881L;
		Date until = new Date( untilLong * 1000 );
		
		System.out.println( String.format( "Previous page is posts since: %d, or %s", previousLong, FacebookCommentPoller.FB_DATE_FORMAT.format( previous ) ));
		System.out.println( String.format( "Next page is posts until: %d, or %s", untilLong, FacebookCommentPoller.FB_DATE_FORMAT.format( until ) ));
		
		System.out.println( EffectiveDatedEntity.START_OF_TIME_DATE );
	}
	
	void logUnixTime( long unixTime ) {
		Date date = new Date( unixTime * 1000 );
		System.out.println( String.format( "Unix time of: %d, is %s", unixTime, FacebookCommentPoller.FB_DATE_FORMAT.format( date ) ));
	}
	
	Connection<Facebook> createMockConnection() {
		Connection<Facebook> connection = EasyMock.createMock( Connection.class );
		expect( connection.getApi() ).andReturn( this.createMockApi() );
		
		return connection;
	}
	
	Facebook createMockApi() {
		Facebook api = EasyMock.createMock( Facebook.class );
		expect( api.restOperations() ).andReturn( this.createMockRestOperations() );
		
		return api;
	}
	
	RestOperations createMockRestOperations() {
		JsonNode node = new TextNode( "{ \"data\": [{\"from\": {\"id\": \"344102512424860\",\"name\": \"Barf Grooks\"},\"id\": \"815035588538880_815303421845430\",\"message\": \"#BourbonDigital\",\"created_time\": \"2014-11-13T16:41:22+0000\"}],\"paging\": {\"previous\": \"https://graph.facebook.com/v2.0/815035588538880/tagged?fields=from,id,message,created_time&limit=1&since=1415896882\",\"next\": \"https://graph.facebook.com/v2.0/815035588538880/tagged?fields=from,id,message,created_time&limit=1&until=1415896881\"}}" );

		RestOperations ro = EasyMock.createMock( RestOperations.class );
		expect( ro.getForObject( anyString(), isA(JsonNode.class.getClass())) ).andReturn( node );

		return ro;
	}

}
