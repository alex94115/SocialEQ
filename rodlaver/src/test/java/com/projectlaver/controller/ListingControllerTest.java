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

import static org.junit.Assert.*;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.support.URIBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.projectlaver.domain.Listing;
import com.projectlaver.test.config.SocialTestConfig;
import com.projectlaver.test.config.TestConfig;
import com.projectlaver.validation.ListingFormValidator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class ListingControllerTest {

	@Autowired
	SocialTestConfig socialTestConfig;
	
	ListingController controller = new ListingController();

	@Test
	public void test140CharacterTweet() {
		String tweet = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		int length = controller.getTotalTweetCodePoints( tweet );
		assertEquals( 140,  length );
	}

	@Test
	public void test140CharacterTweetWithSpaces() {
		String tweet = "aaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaa";
		int length = controller.getTotalTweetCodePoints( tweet );
		assertEquals( 140,  length );
	}

	@Test
	public void test140CharacterTweetWithLink() {
		String tweet = "Hi this is using the new twitter text length counter so hopefully it's right on Reply 'YES' to be entered via @ieeiee\n\nhttps://rodlaver.elasticbeanstalk.com/listing/detail/30";
		int length = controller.getTotalTweetCodePoints( tweet );
		assertEquals( 142,  length );
	}

	@Test
	public void test140CharacterTweetWitTwoLinks() {
		String tweet = "http://rodlaver.elasticbeanstalk.com/analytics/overview/week https://www.amazon.com/ap/signin and some other text that eventually takes us right up to the 140 character limit but not past";
		int length = controller.getTotalTweetCodePoints( tweet );
		assertEquals( 140,  length );
	}

	@Test
	public void testTime() {
		Date date = new Date(1380702259L);
		DateFormat formatter = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss:SSS z");
		String dateFormatted = formatter.format(date);
		System.out.println( dateFormatted );
	}
	
	/**
	 * This method retrieves the "embed" link from the Facebook graph API based on the content's id
	 * (not sure why, but the content id and FBID are different).
	 */
	@Test
	public void getFacebookEmbedLink() {
		URIBuilder uriBuilder = URIBuilder.fromUri( Facebook.GRAPH_API_URL + "636521906382409_758229314211667" );
		uriBuilder.queryParam( "fields", "link" );
		
		uriBuilder.queryParam( "access_token", this.socialTestConfig.getFredFacebookAccessToken() );
		URI uri = uriBuilder.build();

		RestTemplate restTemplate = new RestTemplate();
		JsonNode responseNode = restTemplate.getForObject( uri, JsonNode.class );
		String link = responseNode.path( "link" ).toString();
		System.out.println( link );
		System.out.println( HtmlUtils.htmlEscape( link ));
	}
	
	@Test
	public void getFacebookPosts() {
		URIBuilder uriBuilder = URIBuilder.fromUri( Facebook.GRAPH_API_URL + "636521906382409/posts" );
		uriBuilder.queryParam( "limit", "1");
		uriBuilder.queryParam( "filter", "stream" );
		
		uriBuilder.queryParam( "access_token", this.socialTestConfig.getFredFacebookAccessToken() );
		URI uri = uriBuilder.build();

		RestTemplate restTemplate = new RestTemplate();
		JsonNode responseNode = restTemplate.getForObject( uri, JsonNode.class );
		System.out.println( "Response Node: " + responseNode );
		JsonNode dataNode = responseNode.path( "data" );
		String contentId = dataNode.findValue( "id" ).toString().replaceAll( "\"", "" );
		System.out.println( "Content id: " + contentId );
		String link = dataNode.findValue( "link" ).toString();
		System.out.println( "Embed link: " + link );

	}
	
	/**
	 * This method retrieves the "embed" link from the Twitter graph API based on the content's id
	 */
	@Test
	public void getTwitterEmbedLink() {
		URIBuilder uriBuilder = URIBuilder.fromUri( "https://api.twitter.com/1/statuses/oembed.json" );
		uriBuilder.queryParam( "id", "133640144317198338" );
		URI uri = uriBuilder.build();

		RestTemplate restTemplate = new RestTemplate();
		JsonNode responseNode = restTemplate.getForObject( uri, JsonNode.class );
		String link = responseNode.path( "html" ).toString();

		// get rid of the escaped double quotes 
		link = link.replace( "\\\"", "\"");
		
		String endTag = "</blockquote>";
		link = link.substring( 0, link.indexOf( endTag ) + endTag.length() );
		
		System.out.println( link );
		//System.out.println( HtmlUtils.htmlEscape( link ));
	}

}
