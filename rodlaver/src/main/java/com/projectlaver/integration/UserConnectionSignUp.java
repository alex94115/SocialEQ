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

import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

import com.projectlaver.domain.Role;
import com.projectlaver.domain.TermsOfService;
import com.projectlaver.domain.User;
import com.projectlaver.service.RoleService;
import com.projectlaver.service.TermsOfServiceService;
import com.projectlaver.service.UserService;
import com.projectlaver.util.RoleUtil;
import com.projectlaver.validation.SignUpForm;

public class UserConnectionSignUp implements ConnectionSignUp {
	
	private final Log logger = LogFactory.getLog(getClass());
	
    private final UserService userService;
    private final RoleService roleService;
    private final TermsOfServiceService termsOfServiceService;
    private final SignUpForm signUpForm;

    public UserConnectionSignUp( UserService userService, RoleService roleService, TermsOfServiceService termsOfServiceService, SignUpForm signUpForm ) {
        this.userService = userService;
        this.roleService = roleService;
        this.termsOfServiceService = termsOfServiceService;
        this.signUpForm = signUpForm;
    }

    /**
     * This method is called after a new user (e.g., not existent in the UserConnection table) authorizes via Facebook or Twitter to
     * create the User (populate the user table) and to assign the username that is used as part of the UserConenction's PK (and is the FK to the User table).
     *
     * Note that in the case of giveaways, winners are assigned a "shell" User without a username. In this case, the user is updated by this method
     * with a username and profile information.
     *
     * TODO add a way to get to the User's connection profile from the User class
     *
     * @return the username to use in the UserConnection table
     */
    @Override
	public String execute(Connection<?> connection) {
    	
    	if( this.logger.isDebugEnabled() ) {
    		
    		String itemRaw = ToStringBuilder.reflectionToString( connection );
			String accessTokenSuppressed = StringUtils.replacePattern( itemRaw, "accessToken=[0-9a-zA-Z]*,", "accessToken={protected},");
			this.logger.debug( String.format( "Entered the execute method with item: %s", accessTokenSuppressed ));
			
    	}

    	UserProfile profile = connection.fetchUserProfile();
        String trueUsername = this.createUsername(connection, profile);

        // Check to see if a surrogate user has been created, e.g., in the case of an unregistered giveaway winner
        ConnectionKey connectionKey = connection.getKey();
        String surrogateUsername = StringUtils.join( connectionKey.getProviderId(), "/", connectionKey.getProviderUserId() );
    	User user = this.userService.findByUsername( surrogateUsername );
    	Boolean doesUserAlreadyExist = user != null;
    	
    	// fetch the accepted TOS
    	Set<TermsOfService> tosSet = new HashSet<TermsOfService>();
    	tosSet.add( this.termsOfServiceService.findByVersion( this.signUpForm.getTosVersion() ) );

    	if( doesUserAlreadyExist ) {
    		user.setUsername( trueUsername );
    		user.setFirstName( profile.getFirstName() );
    		user.setLastName( profile.getLastName() );
    	} else {
    		user = new User( trueUsername, profile.getFirstName(), profile.getLastName() );
    	}

    	user.setEmailAddress( this.signUpForm.getEmailInput1() );
    	user.setDoAllowSellerEmails( this.signUpForm.getDoAllowSellerEmails() );
    	user.setRoles( this.createInitalRoleSet() );
        user.setHasAcceptedCurrentBuyerTos( true );
        user.setIsMobileVerified( false );
        user.setIsUserBlocked( false );
        user.setAcceptedTermsOfService( tosSet );
        user.setProfileImageUrl( connection.getImageUrl() );
        user.setLastFacebookPagePostRetrievedAt( new Date( 86400000L ) ); // sometime after 12:00am UTC on 1/1/1970
        user.setDoUseChainedPayments( true );

    	if( doesUserAlreadyExist ) {
    		this.userService.update( user );

    	} else {
    		// not yet existent in the database -- must insert
	        this.userService.create( user );
    	}


        return trueUsername;
    }

	String createUsername(Connection<?> connection, UserProfile profile) {
		String usernamePrefix = this.determineUsernamePrefix( connection );
		String usernameSuffix = profile.getUsername();

		if( usernameSuffix == null ) {
			String firstName = profile.getFirstName();
			String lastName = profile.getLastName();
			Random rand = new Random();
			int number = rand.nextInt( 999 );
			usernameSuffix = StringUtils.lowerCase( firstName + lastName + number, Locale.US );
		}

		return usernamePrefix + usernameSuffix;
	}


    Set<Role> createInitalRoleSet() {
    	Set<Role> roles = new HashSet<Role>();
        // By default, new users are created with only the BUYER role
    	Role buyerRole = this.roleService.findByRoleNumber( RoleUtil.ROLE_BUYER_INT );
        roles.add( buyerRole );

        return roles;
    }
    
    String determineUsernamePrefix( Connection<?> connection ) {
    	ConnectionKey connectionKey = connection.getKey();
    	String providerId = connectionKey.getProviderId();
    	return providerId + "/";
    }
    
}
