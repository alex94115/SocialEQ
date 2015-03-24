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
package com.projectlaver.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Comment;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookLink;
import org.springframework.social.facebook.api.PageOperations;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.support.URIBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.projectlaver.integration.SocialProviders;
import com.projectlaver.test.config.SocialTestConfig;
import com.projectlaver.test.config.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class FacebookCommunicationTest {

	@Autowired
	SocialTestConfig socialTestConfig;
	
	@Test
	public void testPostToFacebook() {

		Facebook api = new FacebookTemplate( this.socialTestConfig.getFredFacebookAccessToken() );

		String pageId = "636521906382409";
		String text = "Test post without a link via the app.";
		FacebookLink facebookLink = new FacebookLink( "http://rodlaver.elasticbeanstalk.com/listing/listingDetail/47", "Name", "Caption", "Description" );

		PageOperations po = api.pageOperations();
		po.post( pageId, "Message", facebookLink );
	}


	@Test
	public void testReadCommentsFromFacebook() throws Exception {
		
		FacebookTemplate template = new FacebookTemplate( this.socialTestConfig.getFacebookAppToken() );

		String listingMessgeFacebookId = "753837901317475";

		//PagingParameters paging = new PagingParameters( new Integer( 100 ), new Integer( 0 ), 1382960511000L, 1382960513000L );
		//PagingParameters paging = new PagingParameters( new Integer(1), null, null, null, null, "MQ==");
		//PagedList<Comment> comments = template.commentOperations().getComments( listingMessgeFacebookId, paging );

		MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<String, String>();
		//queryParameters.add( "after", "MTA" );
		queryParameters.add( "limit", "5" );
		queryParameters.add( "filter", "stream" );
		PagedList<Comment> comments = template.fetchConnections( listingMessgeFacebookId, "comments", Comment.class, queryParameters );
		//CommentResponsePayload payload = template.fetchConnections( listingMessgeFacebookId, "comments", queryParameters );

		for( Comment comment : comments ) {
			System.out.println( "ID: " + comment.getId() );
			System.out.println( "Text: " + comment.getMessage() );
			System.out.println( "In Reponse To: " + listingMessgeFacebookId );
			System.out.println( "Provider Id: " + SocialProviders.FACEBOOK );
			System.out.println( "Provider User Id: " + comment.getFrom().getId() );
		}

		PagingParameters nextPage = comments.getNextPage();
		System.out.println( "Next page is null: " + (nextPage == null) );
		if( nextPage != null ) {
			System.out.println( "Next page pointer: " + nextPage.getAfter() );
		}

		PagingParameters priorPage = comments.getPreviousPage();
		System.out.println( "Prior page is null: " + (priorPage == null ));
		if( priorPage != null ) {
			System.out.println( "Previous page pointer: " + priorPage.getBefore() );
		}


	}

	@Test
	public void testDateProcessing() throws Exception {
		//String dateString = "2013-10-28 00:00:00+0000"; // 1382918400 in seconds
		//String dateString = "2013-10-29 00:00:00+0000"; // 1383004800 in seconds
		String dateString = "2013-10-30 00:00:00+0000"; //   1383091200
													    //   1364849754
		String dateFormat = "yyyy-MM-dd HH:mm:ssZ";
		SimpleDateFormat sdf = new SimpleDateFormat( dateFormat );
		Date date = sdf.parse( dateString );

		String outputDateFormat = "yyyy.MM.dd G 'at' HH:mm:ss z";
		System.out.println( "Date: " + new SimpleDateFormat( outputDateFormat ).format( date ) );
		System.out.println( "Date in millis: " + date.getTime() );

	}

	@Test
	public void testManualGraphApiFetch() throws Exception {

		URLConnection connection = new URL( Facebook.GRAPH_API_URL + "680498321984767/comments?limit=1&before=Mw==" ).openConnection();
		connection.setRequestProperty("Accept-Charset", "UTF-8" );
		InputStream response = connection.getInputStream();

		String contentType = connection.getHeaderField("Content-Type");
		String charset = null;
		for (String param : contentType.replace(" ", "").split(";")) {
		    if (param.startsWith("charset=")) {
		        charset = param.split("=", 2)[1];
		        break;
		    }
		}

		if (charset != null) {
		    BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset));
		    try {
		        for (String line; (line = reader.readLine()) != null;) {
		            System.out.println( line );
		        }
		    } finally {
		        try { reader.close(); } catch (IOException logOrIgnore) {}
		    }
		} else {
		    // It's likely binary content, use InputStream/OutputStream.
		}


	}

	@Test
	public void testReplyToComment() {
		FacebookTemplate template = new FacebookTemplate( this.socialTestConfig.getFacebookAppToken() );

		String commentToReplyTo = "753837901317475";

		URIBuilder uriBuilder = URIBuilder.fromUri( Facebook.GRAPH_API_URL + commentToReplyTo + "/comments/" );
		uriBuilder.queryParam( "message", "@[100007261000324] is the bomb!" );

		Object result = template.restOperations().postForObject( uriBuilder.build(), null, Object.class );
		System.out.println( result );

	}
	
	@Test
	public void testCommunitactionSender() {
		
		Facebook api = new FacebookTemplate( this.socialTestConfig.getFredFacebookAccessToken() );
		
		String replyToMessageId = "753837901317475_2364955";
		URIBuilder uriBuilder = URIBuilder.fromUri( Facebook.GRAPH_API_URL + replyToMessageId + "/comments/" );
		uriBuilder.queryParam( "message", 
				"Good news! You won the @regtestnancy giveaway via @hootit - you can download your digital " +
				"item here: http://rodlaver.elasticbeanstalk.com/listing/listingDetail/giveawayWinner/97" );
		
		Object result = api.restOperations().postForObject( uriBuilder.build(), null, Object.class );
		System.out.println( result );
	}
}
