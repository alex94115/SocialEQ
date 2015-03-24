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

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.Connection;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.NativeWebRequest;

import com.projectlaver.domain.Address;
import com.projectlaver.domain.Preapproval;
import com.projectlaver.domain.Role;
import com.projectlaver.domain.TermsOfService;
import com.projectlaver.domain.User;
import com.projectlaver.integration.UserConnectionSignUp;
import com.projectlaver.repository.BulkOperationsRepository;
import com.projectlaver.repository.UserRepository;
import com.projectlaver.util.RoleUtil;
import com.projectlaver.validation.SellerInfoForm;
import com.projectlaver.validation.SignUpForm;
import com.shippingapis.usps.AddressNotFoundException;


@Service
@Transactional(readOnly = false)
public class UserService {

	@Autowired
	private BulkOperationsRepository bulkMessageOperationsRepository;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private PreapprovalService preapprovalService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private TermsOfServiceService termsOfServiceService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private UserRepository userRepository;
	
	@PersistenceContext
	private EntityManager em;

	private final Log logger = LogFactory.getLog(getClass());

	@Transactional(readOnly = false)
	public Boolean create(User user) {
		boolean created = false;

		User existingUser =  this.userRepository.findByUsername(user.getUsername());
		if (existingUser == null) {

			// merge the user's roles
			Set<Role> mergedRoles = new HashSet<Role>();
			for( Role role : user.getRoles() ) {
				Role mergedRole = this.em.merge( role );
				mergedRoles.add( mergedRole );
			}
			user.setRoles( mergedRoles );
			
			// merge the user's accepted tos
			Set<TermsOfService> mergedTosSet = new HashSet<TermsOfService>();
			for( TermsOfService tos : user.getAcceptedTermsOfService() ) {
				TermsOfService mergedTos = this.em.merge( tos );
				mergedTosSet.add( mergedTos );
			}
			user.setAcceptedTermsOfService( mergedTosSet );

			// Persist the user
			User saved = this.userRepository.save(user);
			if (saved != null) {
				created = true;
			}
			
		}

		return created;
	}

	@Transactional(readOnly = false)
	public Boolean update(User inputUser) {
		boolean updated = false;

		// merge the user (reattach to DB)
		User mergedUser = this.em.merge( inputUser );

		User existingUser = this.userRepository.findByUsername( mergedUser.getUsername() );
		if (existingUser != null) {

			// Cannot update id!

			// Update username so that we can replace surrogate usernames with real ones
			existingUser.setUsername( mergedUser.getUsername() );

			existingUser.setFirstName(mergedUser.getFirstName());
			existingUser.setLastName(mergedUser.getLastName());
			existingUser.setEmailAddress( mergedUser.getEmailAddress() );
			existingUser.setRoles( mergedUser.getRoles() );
			existingUser.setCountryCode( mergedUser.getCountryCode() );
			existingUser.setMobileNumber( mergedUser.getMobileNumber() );
			existingUser.setIsMobileVerified( mergedUser.getIsMobileVerified() );
			existingUser.setIsUserBlocked( mergedUser.getIsUserBlocked() );
			existingUser.setAcceptedTermsOfService( mergedUser.getAcceptedTermsOfService() );
			existingUser.setHasAcceptedCurrentBuyerTos( mergedUser.getHasAcceptedCurrentBuyerTos() );

			User saved = this.userRepository.save(existingUser);
			if (saved != null) {
				updated = true;
			}
		}

		return updated;
	}
	
	@Transactional(readOnly = false)
	public Boolean delete(User user) {
		boolean deleted = false;

		User existingUser = this.userRepository.findOne(user.getId());
		if (existingUser != null) {

			userRepository.delete(existingUser);
			User deletedUser = this.userRepository.findOne(user.getId());
			if (deletedUser == null) {
				deleted = true;
			}
		}

		return deleted;
	}

	@Transactional(readOnly = true)
	public User findByUsername( String username ) {
		return this.userRepository.findByUsername( username );
	}

	@Transactional(readOnly = true)
	public User findByProviderUserId( String providerId, String providerUserId ) {
		String username = this.bulkMessageOperationsRepository.getUsernameByProviderUserId( providerId, providerUserId );
		
		User result = null;
		if( username != null ) {
			result = this.userRepository.findByUsername( username );
		}
		
		return result;
	}

	@Transactional(readOnly = true)
	public User findByUserIdAndFetchMessages( Long userId ) {
		return this.userRepository.findByUserIdAndFetchMessages( userId );
	}

	@Transactional(readOnly = true)
	public User findByUserIdAndFetchAcceptedTos( Long userId ) {
		return this.userRepository.findByUserIdAndFetchAcceptedTos( userId );
	}

	@Transactional(readOnly = true)
	public Page<User> findAll( Pageable pageRequest ) {
		return this.userRepository.findAll( pageRequest );
	}

	@Transactional(readOnly = true)
	public User findOne( Long id ) {
		return this.userRepository.findOne( id );
	}

	@Transactional(readOnly = true)
	public User getCurrentUser() {
		return this.findOne( this.getCurrentUserId() );
	}

	/**
	 * Shortcut to get the current user's id
	 *
	 * @return
	 */
	@Transactional(readOnly = true)
	public Long getCurrentUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Object principal = auth.getPrincipal();
		if( "anonymousUser".equals( principal )) {
			return null;
		} else {
			org.springframework.security.core.userdetails.User socialUser = (org.springframework.security.core.userdetails.User)principal;
			User user = this.findByUsername(socialUser.getUsername());
			return ( user == null ? null : user.getId() );
		}
	}

	@Transactional(readOnly = true)
	public User findUserAndFetchPreapprovalsAndAddresses( Long id ) {
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Fetching user with id: " + id );
		}

		User user = this.userRepository.findUserEagerlyFetchAddresses( id );
		Set<Preapproval> validPreapprovals = this.preapprovalService.findByUserAndPreapprovalStatus( id, PreapprovalService.validPreapprovalStatuses() );
		user.setPreapprovals( validPreapprovals );

		return user;
	}
	
	@Transactional(readOnly = false)
	public String userSignup( SignUpForm signUpForm, Connection<?> connection,  NativeWebRequest request ) {
		UserConnectionSignUp ucsu = new UserConnectionSignUp( this, this.roleService, this.termsOfServiceService, signUpForm );
		String username = ucsu.execute( connection );
		
		new ProviderSignInUtils().doPostSignUp( username, request );
		
		
		// Upon signup, unblock any 'pending user regitration' methods
		this.messageService.unblockPendingRegistrationPurchaseMessages( connection.getKey().getProviderId(), connection.getKey().getProviderUserId() );
		
		return username;
	}
	
	@Transactional(readOnly = false)
	public Boolean userAcceptsTermsOfService( Integer tosVersion ) {
		User user = this.findByUserIdAndFetchAcceptedTos( this.getCurrentUserId() );

		TermsOfService newlyAcceptedTos = this.termsOfServiceService.findByVersion( tosVersion );

		Set<TermsOfService> userTos = user.getAcceptedTermsOfService();
		if( userTos == null ) {
			userTos = new HashSet<TermsOfService>();
		}
		userTos.add( newlyAcceptedTos );
		user.setHasAcceptedCurrentBuyerTos( true );
		return this.update( user );
	}
	
	@Transactional(readOnly = false)
	public Boolean grantCurrentUserSellerRole() {
		
		Boolean result = true;
		
		Role sellerRole = this.roleService.findByRoleNumber( RoleUtil.ROLE_SELLER_INT );
		User user = this.getCurrentUser();
		Set<Role> currentRoles = user.getRoles();
		if( currentRoles.contains( sellerRole )) {
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( String.format( "User with username: %s already has the ROLE_SELLER.", user.getUsername() ));
			}
			
		} else {
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( String.format( "User with username: %s does not have the ROLE_SELLER; adding it.", user.getUsername() ));
			}
			
			currentRoles.add( sellerRole );
			result = this.update( user );
		}
		
		return result;
	}
	
	/**
	 * Updates the current user with information from a validated input form (the controller
	 * tier is expected to have completed the validation before calling this method).
	 * 
	 * @param form
	 * @return
	 * @throws AddressNotFoundException 
	 */
	@Transactional(readOnly = false)
	public Boolean updateSellerInfo( SellerInfoForm form, Locale locale ) throws AddressNotFoundException {
		
		User seller = this.getCurrentUser();
		
		seller.setFirstName( form.getFirstName() );
		seller.setLastName( form.getLastName() );
		seller.setMobileNumber( this.formatPhoneNumber( form ));
		seller.setDob( this.formatDob( form ));
		
		// Get an address object and run it through the USPS validation
		Address address = this.createAddress( form );
		Address cleanedAddress = this.addressService.clean( address, locale );
		
		Set<Address> addresses = seller.getAddresses();

		// set the entity relationship pointers
		addresses.add( cleanedAddress );
		cleanedAddress.setUser( seller );
		
		// persist the updates to the user (expect this to cascade the address entity)
		return this.update( seller );
	}
	
	/**
	 * Helper methods
	 */
	
	String formatPhoneNumber( SellerInfoForm form ) {
		return String.format( "1%s%s%s", form.getPhoneAreaCode(), form.getPhoneNxx(), form.getPhoneXxxx() ); 
	}
	
	String formatDob( SellerInfoForm form ) {
		return String.format( "%s-%s-%s", form.getDobYear(), form.getDobMonth(), form.getDobDay() );
	}
	
	Address createAddress( SellerInfoForm form ) {
		Address address = new Address();
		
		address.setFirstLine( form.getAddressFirstLine() );
		address.setSecondLine( form.getAddressSecondLine() );
		address.setCity( form.getCity() );
		address.setState( form.getState() );
		address.setZip( form.getZip() );
		address.setCountry( "US" );
		
		return address;
	}

}
