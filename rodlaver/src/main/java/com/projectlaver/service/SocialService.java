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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.InvalidAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.databind.JsonNode;
import com.projectlaver.domain.User;

@Service
@Transactional(readOnly = false)
public class SocialService {

	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	UsersConnectionRepository usersConnectionRepository;
	
	@Autowired
	private ConnectionRepository connectionRepository;

	@Transactional(readOnly = true)
	public <A> Boolean testCurrentUserConnection( Class<A> type ) {
		
		Connection<A> connection = this.connectionRepository.findPrimaryConnection( type );
		
		return ( connection == null ? false : connection.test() );
	}
	
	@Transactional(readOnly = true)
	public <A> Connection<A> getCurrentUserSocialConnection( Class<A> type ) {
		return this.connectionRepository.findPrimaryConnection( type );
	}
	
	@Transactional(readOnly = true)
	public MultiValueMap<String, Connection<?>> findAllCurrentUserConnections() {
		return this.connectionRepository.findAllConnections();
	}
	
	@Transactional(readOnly = true )
	public Boolean confirmCurrentUserFacebookPermissions( String... permissions ) {

		Boolean result = false;
		
		try {
			Connection<Facebook> connection = this.getCurrentUserSocialConnection( Facebook.class );
			String providerUserId = connection.getKey().getProviderUserId();
			RestOperations ro = connection.getApi().restOperations();
			JsonNode dataJson = ro.getForObject( Facebook.GRAPH_API_URL + "{providerUserId}/permissions", JsonNode.class, providerUserId );
			JsonNode permissionsArrayJson = dataJson.path( "data" );
			Iterator<JsonNode> permissionsJson = permissionsArrayJson.elements();
			
			result = this.doesTokenHaveAllPermissions( permissionsJson, permissions );
		} catch( InvalidAuthorizationException e ) {
			this.logger.error( String.format( "Caught InvalidAuthorizationException with message: %s while trying to confirm facebook permissions. Returning false.", e.getMessage() ) );
		}
		
		return result;
	}

	@Transactional(readOnly = true)
	public Connection<Facebook> getFacebookConnection( String username ) {
		ConnectionRepository cr = this.usersConnectionRepository.createConnectionRepository( username );
		Connection<Facebook> connection = cr.findPrimaryConnection( Facebook.class );
		
		return connection;
	}

	Boolean doesTokenHaveAllPermissions( Iterator<JsonNode> permissionsJson, String... requiredPermissions) {
		
		Set<String> requiredPermissionsSet = new HashSet<String>( Arrays.asList( requiredPermissions ) );
		
		while( permissionsJson.hasNext() ) {
			
			JsonNode permissionJson = permissionsJson.next();
			
			String permission = permissionJson.path( "permission" ).asText();
			String status = permissionJson.path( "status" ).asText();
			
			if( StringUtils.equals( status, "granted" )) {
				requiredPermissionsSet.remove( permission );
			}
		}
		
		// If the requiredPermissions set is empty then this token does have all of the required permissions
		return requiredPermissionsSet.isEmpty();
	}
	
}
