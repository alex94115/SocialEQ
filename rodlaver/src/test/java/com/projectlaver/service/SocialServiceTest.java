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
package com.projectlaver.service;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.databind.JsonNode;
import com.projectlaver.test.config.SocialTestConfig;

public class SocialServiceTest {
	
	private SocialTestConfig socialTestUsers;

	@Test
	public void testDoesNotHavePermissions() {
		
		
		Facebook api = new FacebookTemplate( this.socialTestUsers.getFredFacebookAccessToken() );
		RestOperations ro = api.restOperations();
		JsonNode dataJson = ro.getForObject( Facebook.GRAPH_API_URL + "{providerUserId}/permissions", JsonNode.class, this.socialTestUsers.getFredFacebookProviderUserId() );
		
		JsonNode permissionsArrayJson = dataJson.path( "data" );
		//List<JsonNode> permissionsJson = permissionsArrayJson.findValues( "permission" );
		Iterator<JsonNode> permissionsJson = permissionsArrayJson.elements();
		
		SocialService service = new SocialService();
		assertFalse( service.doesTokenHaveAllPermissions( permissionsJson, "read_stream" ) );
	}

	@Test
	public void testDoesHavePermissions() {
		
		
		Facebook api = new FacebookTemplate( this.socialTestUsers.getFredFacebookAccessToken() );
		RestOperations ro = api.restOperations();
		JsonNode dataJson = ro.getForObject( Facebook.GRAPH_API_URL + "{providerUserId}/permissions", JsonNode.class, this.socialTestUsers.getFredFacebookProviderUserId() );
		
		JsonNode permissionsArrayJson = dataJson.path( "data" );
		//List<JsonNode> permissionsJson = permissionsArrayJson.findValues( "permission" );
		Iterator<JsonNode> permissionsJson = permissionsArrayJson.elements();
		
		SocialService service = new SocialService();
		assertTrue( service.doesTokenHaveAllPermissions( permissionsJson, "publish_actions", "manage_pages" ) );
	}

	
}
