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

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.util.List;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.social.facebook.api.Comment;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;

import com.amazonaws.services.dynamodbv2.document.Expected;
import com.braintreegateway.TransactionRequest;
import com.projectlaver.util.FacebookListingResponseHandler;

@RunWith(EasyMockRunner.class)
public class FacebookListingResponseHandlerTest extends EasyMockSupport {

	
	/**
	 * Static variables
	 */

	private static final String ACCESS_TOKEN = "someAccessToken";
	private static final String APP_ACCESS_TOKEN = "123456|abcdefg987654321";
	
	/**
	 * Instance variables
	 */
	
	@TestSubject
	private FacebookListingResponseHandler responseProcessor = new FacebookListingResponseHandler();
	
	@Mock(type=MockType.NICE, name="jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	
	private FacebookTemplate facebook;
	private FacebookTemplate unauthorizedFacebook;
	private FacebookTemplate appFacebook;
	private MockRestServiceServer mockServer;
	private MockRestServiceServer unauthorizedMockServer;
	private MockRestServiceServer appFacebookMockServer;

	@Before
	public void setup() {
		facebook = createFacebookTemplate();
		mockServer = MockRestServiceServer.createServer(facebook.getRestTemplate());
		
		unauthorizedFacebook = new FacebookTemplate();
		unauthorizedMockServer = MockRestServiceServer.createServer(unauthorizedFacebook.getRestTemplate());
		
		appFacebook = new FacebookTemplate(APP_ACCESS_TOKEN);
		appFacebookMockServer = MockRestServiceServer.createServer(appFacebook.getRestTemplate());
	}
	
	@Test
	public void testProcessJson() {
		
		// Use this mock server to get us a list of comments loaded from a file containing the JSON
		mockServer.expect(requestTo("https://graph.facebook.com/v2.0/123456/comments?offset=0&limit=25"))
			.andExpect(method(GET))
			.andExpect(header("Authorization", "OAuth someAccessToken"))
			.andRespond(withSuccess(jsonResource("comments"), MediaType.APPLICATION_JSON));
		
		
		String lookupSql = " SELECT m.twitterId  FROM Messages m    INNER JOIN Listings l ON m.listing_id = l.id  WHERE m.providerId = 'facebook'    AND m.providerUserId = ?    AND l.keyword = ?  ORDER BY l.created DESC  LIMIT 1 ";
		Object[] lookupParams = new Object[] { "1031198396896749", "#myfavoritelunchbox" };
		
		expect( this.jdbcTemplate.queryForObject( eq( lookupSql ), aryEq( lookupParams), isA(String.class.getClass()))).andReturn( "898807130152632_898923856800000" );		
		
		replayAll();
		
		List<Comment> comments = facebook.commentOperations().getComments("123456");

		for( Comment comment : comments ) {
			
			this.responseProcessor.processPost( comment.getId(), comment.getFrom().getId(), comment.getMessage(), comment.getCreatedTime(), "1031198396896749", false );
			
		}
		
	}
	
	@Test
	public void testEmptyStringGetMentionedHashtag() {
		String[] text = { "", " ", null };
		
		for( String t : text ) {
			System.out.println( "Testing with t: '" + t + "'");
			String keyword = responseProcessor.getMentionedHashtag( t );
			assertEquals( "NO_KEYWORD_FOUND", keyword );
		}
	}
	
	@Test 
	public void testContainsHashtag() {
		
		String[] text = { "#myfavoritelunchbox hi there", " hi #myfavoritelunchbox there", " hi #myfavoritelunchbox " };
		
		for( String t : text ) {
			System.out.println( "Testing with t: '" + t + "'");
			String keyword = responseProcessor.getMentionedHashtag( t );
			assertEquals( "#myfavoritelunchbox", keyword );
		}
		
	}
	
	@Test
	public void testDoesntContainHashtag() {
		String[] text = { "@regtestnancy @hockingbird BUY ", " BUY ", " @regtestnancy BUY"  };
		
		for( String t : text ) {
			System.out.println( "Testing with t: '" + t + "'");
			String keyword = responseProcessor.getMentionedHashtag( t );
			assertEquals( "NO_KEYWORD_FOUND", keyword );
		}
	}
	
	/**
	 * Helper methods
	 */

	
	Resource jsonResource(String filename) {
		return new ClassPathResource(filename + ".json", getClass());
	}
	
	FacebookTemplate createFacebookTemplate() {
		return new FacebookTemplate(ACCESS_TOKEN);
	}

}
