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

import java.util.Iterator;

import org.junit.Test;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.databind.JsonNode;
import com.projectlaver.test.config.SocialTestConfig;

public class SelfRegistrationControllerTest {
	
	private SocialTestConfig socialTestUsers;

	@Test
	public void testCreateKeywordFromTitle() {
		
		SelfRegistrationController controller = new SelfRegistrationController();
		
		String simpleTitle = "Simple";
		String result = controller.createKeywordFromTitle( simpleTitle );
		
		assertEquals( "No special characters should add # and postfix 'digital'", "#SimpleDigital", result );
	}
	
	@Test
	public void testCreateKeywordFromTitle2() {
		
		SelfRegistrationController controller = new SelfRegistrationController();
		
		String simpleTitle = "FutureSex / LoveSounds";
		String result = controller.createKeywordFromTitle( simpleTitle );
		
		assertEquals( "Longer titles lose the Digital suffix. Drop punctuation.", "#FutureSexLoveSounds", result );
	}
	
	@Test
	public void testGetFacebookPageAlbums() {
		
		Facebook api = new FacebookTemplate( this.socialTestUsers.getFredFacebookAccessToken() );
		RestOperations ro = api.restOperations();
		
		String pageId = "512734142199949";
		String fieldsFilter="albums{id,name}";
		JsonNode node = ro.getForObject( Facebook.GRAPH_API_URL + "{pageId}/?fields={fieldsFilter}", JsonNode.class, pageId, fieldsFilter );
		Iterator<JsonNode> albumNodes = node.path("albums").path("data").iterator();
		
		while( albumNodes.hasNext() ) {
			JsonNode album = albumNodes.next();
			String albumId = album.findValue( "id" ).asText();
			String albumName = album.findValue( "name" ).asText();
			
			System.out.println( String.format( "Album id: %s, name: %s", albumId, albumName ) );
		}
		
		
		assertTrue( node != null );
		
	}

}
