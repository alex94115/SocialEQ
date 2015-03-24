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


import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.projectlaver.domain.Preapproval;
import com.projectlaver.domain.Role;
import com.projectlaver.domain.User;
import com.projectlaver.integration.SocialProviders;
import com.projectlaver.service.AddressService;
import com.projectlaver.service.RoleService;
import com.projectlaver.service.UserService;
import com.projectlaver.util.MvcConstants;
import com.projectlaver.util.PreapprovalType;
import com.projectlaver.util.RoleUtil;
import com.projectlaver.util.UserInterfaceMessageDto;
import com.projectlaver.validation.UserAdminForm;

/**
 * This controller handles requests to the path 'user' (/user)
 *
 * @author alexduff
 *
 */
@Controller
@RequestMapping("/user")
@SessionAttributes({ "mobilePhoneValue", "mobilePhoneVerificationCode", "mobilePhoneVerificationAttempts", "successfullyValidatedMobileNumber" })
public class UserAccountController {

	/**
	 * Instance variables
	 */

	@Autowired
	private UsersConnectionRepository usersConnectionRepository;

	@Autowired
	private UserService userService;

	@Autowired
	public AddressService addressService;

	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private RoleService roleService;

	private final Log logger = LogFactory.getLog(getClass());

	public UserAccountController() {}

	/**
	 * Gathers information about the user's account, including preapprovals and addresses.
	 *
	 * @param model
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_BUYER','ROLE_SELLER','ROLE_CHARITY')")
	@RequestMapping("/profile")
	public String getUserAccount(ModelMap model ) {

		Long userId = this.userService.getCurrentUserId();

		User user = this.userService.findUserAndFetchPreapprovalsAndAddresses( userId );
		model.addAttribute( "user", user );

		MultiValueMap<String, Connection<?>> usersConnectionsMap = this.usersConnectionRepository.createConnectionRepository( user.getUsername() ).findAllConnections();
		
		Boolean hasTwitterConnection = !usersConnectionsMap.get( SocialProviders.TWITTER ).isEmpty();
		model.put( "hasTwitterConnection", hasTwitterConnection );
		if( hasTwitterConnection ) {
			model.put( "twitterConnections", usersConnectionsMap.get( SocialProviders.TWITTER ) );
		}
		
		Boolean hasFacebookConnection = !usersConnectionsMap.get( SocialProviders.FACEBOOK ).isEmpty();
		model.put( "hasFacebookConnection", hasFacebookConnection );
		if( hasFacebookConnection ) {
			model.put( "facebookConnections", usersConnectionsMap.get( SocialProviders.FACEBOOK ) );
		}
		
		// add preapprovals to the model
		this.addPreapprovalAttributesToModel( user.getPreapprovals(), model );

		// add addresses to the model
		model.addAttribute( "addresses", user.getAddresses() );

		return "user/profile";
	}

	/**
	 * Allows the user to select the address to treat as "Primary"
	 */
	@PreAuthorize("hasAnyRole('ROLE_BUYER','ROLE_SELLER','ROLE_CHARITY')")
	@RequestMapping(value="/setPrimaryAddress", method=RequestMethod.POST)
	public String postSetPrimaryAddress( Long id, ModelMap model, Locale locale ) {

		if( id == null ) {
			model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( this.messageSource, "status.page.invalid", locale, UserInterfaceMessageDto.ERROR ) );
			return "error";
		}

		this.addressService.setPrimaryAddress( id );

		return this.getUserAccount( model );

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/userAdminSearch", method = RequestMethod.GET)
	public String getUserAdmin( ModelMap model, Locale locale ) {
		
		return "user/admin";
	}

	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/userAdminSearch", method = RequestMethod.POST)
	public String postUserAdminSearch( 
			@RequestParam(value="providerId", defaultValue="") String providerId,
			@RequestParam(value="providerUserId", defaultValue="") String providerUserId,
			@RequestParam(value="username", defaultValue="") String username,
			ModelMap model, 
			Locale locale ) {

		this.logger.debug( "Received providerId: " + providerId + ", providerUserId: " + providerUserId + " username: " + username );
		
		User user = null;
		if( StringUtils.isNotBlank( providerId ) && StringUtils.isNotBlank( providerUserId )) {
			user = this.userService.findByProviderUserId( providerId, providerUserId );
		} else if( StringUtils.isNotBlank( username ) ) {
			user = this.userService.findByUsername( username );
		}
		
		if( user != null ) {
			
			// TODO this is stupid inefficient but can be fixed later
			user = this.userService.findUserAndFetchPreapprovalsAndAddresses( user.getId() );
			
			this.logger.debug( "User: " + user.getUsername() );
			this.logger.debug( "User roles: " + ReflectionToStringBuilder.toString( user.getRoles() ) );
			
			// Handle User Roles
			boolean isBuyer = false; 
			boolean isSeller = false;
			boolean isCharity = false;
			boolean isAdmin = false;
			
			Iterator<Role> roles = user.getRoles().iterator();
			while( roles.hasNext() ) {
				Role role = roles.next();
				this.logger.debug( "User Role: " + role.getDescription() );
				
				String roleDescription = role.getDescription();
				if( StringUtils.equalsIgnoreCase( roleDescription, RoleUtil.ROLE_BUYER )) {
					isBuyer = true;
				} else if( StringUtils.equalsIgnoreCase( roleDescription, RoleUtil.ROLE_SELLER )) {
					isSeller = true;
				} else if( StringUtils.equalsIgnoreCase( roleDescription, RoleUtil.ROLE_CHARITY )) {
					isCharity = true;
				} else if( StringUtils.equalsIgnoreCase( roleDescription, RoleUtil.ROLE_ADMIN )) {
					isAdmin = true;
				}
			}
			
			UserAdminForm form = new UserAdminForm();
			
			form.setUsername( user.getUsername() );
			
			// for the checkboxes
			form.setHasBuyer( isBuyer );
			form.setHasSeller( isSeller );
			form.setHasAdmin( isAdmin );
			form.setHasCharity( isCharity );

			// User Attributes
			form.setIsBlocked( user.getIsUserBlocked() );
			form.setEmailAddress( user.getEmailAddress() );
			form.setFacebookPageId( user.getFacebookPageId() );
			form.setFacebookPageUrl( user.getFacebookPageUrl() );
			form.setFacebookAlbumId( user.getFacebookAlbumId() );
			
			// Preapprovals
			form.setHasActivePreapproval( user.getPreapprovals() != null && user.getPreapprovals().size() > 0 );
			
			model.put( "adminForm", form );
		}
		
		model.put( "didSearch", true );
		model.put( "providerId", providerId );
		model.put( "providerUserId", providerUserId );
		
		
		return "user/admin";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/userAdminUpdate", method = RequestMethod.POST)
	public String postUserAdminUpdate( 
			@ModelAttribute("userAdminForm") UserAdminForm userAdminForm,
			ModelMap model, 
			Locale locale ) {
		
		// Get the current user who is making changes so we can log it
		User currentUser = this.userService.getCurrentUser();
		
		// fetch the user and current roles
		User userToUpdate = this.userService.findByUsername( userAdminForm.getUsername() );
		Set<Role> roles = userToUpdate.getRoles();

		// reconcile the user's roles with the input
		this.updateUsersRoles( this.roleService.findByRoleNumber( RoleUtil.ROLE_BUYER_INT ), 
							   userAdminForm.getHasBuyer(), 
							   roles,
							   userToUpdate.getUsername(),
							   currentUser.getUsername() );

		this.updateUsersRoles( this.roleService.findByRoleNumber( RoleUtil.ROLE_SELLER_INT ), 
				   			   userAdminForm.getHasSeller(), 
				   			   roles,
				   			   userToUpdate.getUsername(),
							   currentUser.getUsername() );

		this.updateUsersRoles( this.roleService.findByRoleNumber( RoleUtil.ROLE_ADMIN_INT ), 
	   			   			   userAdminForm.getHasAdmin(), 
	   			   			   roles,
	   			   			   userToUpdate.getUsername(),
							   currentUser.getUsername() );

		this.updateUsersRoles( this.roleService.findByRoleNumber( RoleUtil.ROLE_CHARITY_INT ), 
	   			   			   userAdminForm.getHasCharity(), 
	   			   			   roles,
	   			   			   userToUpdate.getUsername(),
							   currentUser.getUsername() );
		
		// userEmail
		String updatedEmailAddress = userAdminForm.getEmailAddress();
		String existingEmailAddress = userToUpdate.getEmailAddress();
		if( !StringUtils.equals( updatedEmailAddress, existingEmailAddress ) ) {
			userToUpdate.setEmailAddress( updatedEmailAddress.trim() );
			this.logger.error( "The user: " + currentUser + " updated the user: " + userToUpdate.getUsername() + 
							   " changing email from: " + existingEmailAddress + " to: " + updatedEmailAddress );
		}
		
		// userIsBlocked
		Boolean updatedIsUserBlocked = userAdminForm.getIsBlocked();
		Boolean existingIsUserBlocked = userToUpdate.getIsUserBlocked();
		if( updatedIsUserBlocked != existingIsUserBlocked ) {
			userToUpdate.setIsUserBlocked( updatedIsUserBlocked );
			this.logger.error( "The user: " + currentUser + " updated the user: " + userToUpdate.getUsername() + 
							   " changing isBlocked from: " + existingIsUserBlocked + " to: " + updatedIsUserBlocked );
		}
		
		// facebookPageId
		String updatedFacebookPageId = userAdminForm.getFacebookPageId();
		String existingFacebookPageId = userToUpdate.getFacebookPageId();
		if( !StringUtils.equals( updatedFacebookPageId, existingFacebookPageId ) ) {
			userToUpdate.setFacebookPageId( updatedFacebookPageId.trim() );
			this.logger.error( "The user: " + currentUser + " updated the user: " + userToUpdate.getUsername() + 
							   " changing facebook page id from: " + existingFacebookPageId + " to: " + updatedFacebookPageId );
		}
		
		// facebookPageUrl
		String updatedFacebookPageUrl = userAdminForm.getFacebookPageUrl();
		String existingFacebookPageUrl = userToUpdate.getFacebookPageUrl();
		if( !StringUtils.equals( updatedFacebookPageUrl, existingFacebookPageUrl ) ) {
			userToUpdate.setFacebookPageUrl( updatedFacebookPageUrl.trim() );
			this.logger.error( "The user: " + currentUser + " updated the user: " + userToUpdate.getUsername() + 
					   " changing facebook page url from: " + existingFacebookPageUrl + " to: " + updatedFacebookPageUrl );
		}
		
		// facebookAlbumId
		String updatedFacebookAlbumId = userAdminForm.getFacebookAlbumId();
		String existingFacebookAlbumId = userToUpdate.getFacebookAlbumId();
		if( !StringUtils.equals( updatedFacebookAlbumId, existingFacebookAlbumId ) ) {
			userToUpdate.setFacebookAlbumId( updatedFacebookAlbumId.trim() );
			this.logger.error( "The user: " + currentUser + " updated the user: " + userToUpdate.getUsername() + 
							   " changing facebook album id from: " + existingFacebookAlbumId + " to: " + updatedFacebookAlbumId );
		}
		
        this.userService.update( userToUpdate );
		
        model.addAttribute( MvcConstants.UI_MESSAGES_KEY, 
        		new UserInterfaceMessageDto( this.messageSource, "userAdmin.updated", locale, UserInterfaceMessageDto.SUCCESS ) );
        
        return this.postUserAdminSearch( null, null, userToUpdate.getUsername(), model, locale );
	}
	
	@PreAuthorize("hasRole('ROLE_SELLER')")
	@RequestMapping(value="/sellerFacebookToken", method = RequestMethod.GET)
	public String getSellerFacebookToken( ModelMap model, Locale locale ) {
		
		return "user/sellerFacebookToken";
	}

	/**
	 * Given a target role, whether the user should have that role, and a set of current roles,
	 * this method will either add, remove, or leave the current roles set alone.
	 * 
	 * @param targetRole
	 * @param shouldHaveRole
	 * @param currentRoles
	 */
	void updateUsersRoles( Role targetRole, boolean shouldHaveRole, Set<Role> currentRoles, String userToUpdate, String currentUser ) {
		
		boolean userHasTargetRole = currentRoles.contains( targetRole );
		this.logger.debug( "User hasTargetRole: " + userHasTargetRole );
		
		if( shouldHaveRole != userHasTargetRole ) {
			this.logger.debug( "shouldHaveRole is not equal to userHasTargetRole" );

			if( shouldHaveRole ) {
				this.logger.error( "The user: " + currentUser + " granted the role: " + targetRole.getDescription() + " to the user: " + userToUpdate );
				currentRoles.add( targetRole );
			} else {
				this.logger.error( "The user: " + currentUser + " removed the role: " + targetRole.getDescription() + " to the user: " + userToUpdate );
				currentRoles.remove( targetRole );
			}
		}
	}

	void addPreapprovalAttributesToModel( Set<Preapproval> preapprovals, ModelMap model ) {
		model.addAttribute( "preapprovals", preapprovals );

		Boolean hasAmazonPreapproval = false;
		Boolean hasPaypalPreapproval = false;

		for( Preapproval preapproval : preapprovals ) {
			if( preapproval.getType().equals( PreapprovalType.BRAINTREE ) ) {

				// at the moment, all Braintree preapprovals are PayPal preapprovals
				hasPaypalPreapproval = true;
			} else if( preapproval.getType().equals( PreapprovalType.AMAZON_FPS )) {
				hasAmazonPreapproval = true;
			}

			if( hasAmazonPreapproval && hasPaypalPreapproval ) {
				break;
			}
		}

		model.addAttribute( "hasAmazonPreapproval", hasAmazonPreapproval );
		model.addAttribute( "hasPaypalPreapproval", hasPaypalPreapproval );

	}

}
