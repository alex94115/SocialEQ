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


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jets3t.service.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.thymeleaf.resourceresolver.ClassLoaderResourceResolver;
import org.thymeleaf.util.ClassLoaderUtils;

import com.projectlaver.domain.MessageStateChange;
import com.projectlaver.domain.Role;
import com.projectlaver.domain.User;
import com.projectlaver.service.EmailService;
import com.projectlaver.service.HomepageService;
import com.projectlaver.service.ListingService;
import com.projectlaver.service.MessageStateChangeService;
import com.projectlaver.service.RoleService;
import com.projectlaver.service.UserService;
import com.projectlaver.util.MvcConstants;
import com.projectlaver.util.RoleUtil;
import com.projectlaver.util.SimpleSignInAdapter;
import com.projectlaver.util.UserInterfaceMessageDto;
import com.projectlaver.validation.ListingForm;
import com.projectlaver.validation.SignUpForm;

/**
 * This controller handles requests to the application root (/)
 *
 * @author alexduff
 *
 */
@Controller
@RequestMapping("/")
public class MediatorController {

	/**
	 * Instance variables
	 */

	@Autowired
	private HomepageService homepageService;
	
	@Autowired
	private ListingService listingService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
    private MessageSource messageSource;

	@Autowired
	private SimpleSignInAdapter simpleSignInAdapter;
	
	private final Log logger = LogFactory.getLog(getClass());

	private final ProviderSignInUtils providerSignInUtils;

	/**
	 * Constants
	 */

	public MediatorController() {
    	 this.providerSignInUtils = new ProviderSignInUtils();
    }

	/**
	 * Delegates to the getHomepage method.
	 *
	 * @param model
	 * @return
	 */
	@PreAuthorize("permitAll")
	@RequestMapping("/")
	public String getApplicationRoot(ModelMap model, HttpServletRequest currentRequest ) {

		return this.getHomepage( model);
	
	}


	/**
	 * Checks to see if the current user has an active PayPal preapproval, then routes
	 * the user to the homepage.html
	 *
	 * @param model
	 * @return
	 */
	@PreAuthorize("permitAll")
	@RequestMapping("/homepage")
	public String getHomepage(ModelMap model ) {

		return "homepage";
	}

	@PreAuthorize("hasAnyRole('ROLE_BUYER','ROLE_SELLER','ROLE_CHARITY')")
	@RequestMapping("/userHome")
	public String getUserHome(ModelMap model, HttpServletRequest currentRequest, Locale locale ) {

    	// Get the current user
		User user = this.userService.getCurrentUser();
		model.addAttribute( "user", user );
		
		if( this.isUserSellerOrCharity( user.getRoles() ) ) {
			
			/**
			 * Seller Homepage
			 */
			return "redirect:/sellerHome/0?page=0&size=1";
			
		} else {
		
			/**
			 * Buyer Homepage
			 */
    	
        	Map<String, Object> result = this.homepageService.doBuyerUserHome( user, locale );
        	model.addAllAttributes( result );
        	
        	if( ( Boolean )result.get( "doesUserHavePendingPayment" ) ) {
        		
        		Long listingId = ( Long )result.get( "mostRecentPendingPaymentListing" );
        		return "redirect:/listing/checkout/" + listingId;
        		
        	}
    	}
        	

        return "userHome";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_SELLER','ROLE_CHARITY')")
	@RequestMapping("/sellerHome/{pageId}")
	public String getSellerHome(ModelMap model, @PathVariable Long pageId, Pageable p ) {
		
		Map<String, Object> result = this.homepageService.doSellerHomepage( p );

		model.addAllAttributes( result );
		model.addAttribute( "pageId", pageId );
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "This pageId=" + pageId );
		}
		
		return "sellerHome";
	}

	@PreAuthorize("hasAnyRole('ROLE_BUYER','ROLE_SELLER','ROLE_CHARITY')")
	@RequestMapping(value="/acceptTermsOfService", method=RequestMethod.POST)
	public String postAcceptTermsOfService( Integer version, ModelMap model, HttpServletRequest currentRequest, Locale locale ) {

		Boolean result = this.userService.userAcceptsTermsOfService( version );

		if( result ) {
			return this.getUserHome( model, currentRequest, locale );
		} else {
			throw new RuntimeException( "Unexpected failure while attempting to accept terms of service." );
		}
	}

	@PreAuthorize("permitAll")
	@RequestMapping("/about")
	public String getAbout(ModelMap model) {
		return "about";
	}

	@PreAuthorize("permitAll")
	@RequestMapping("/contact")
	public String getContact(ModelMap model) {
		return "contact";
	}

	@PreAuthorize("permitAll")
	@RequestMapping("/privacy")
	public String getPrivacy(ModelMap model) {
		return "privacy";
	}
	
	@PreAuthorize("permitAll")
	@RequestMapping("/terms")
	public String getTerms(ModelMap model) {
		return "terms";
	}
	
	@PreAuthorize("permitAll")
	@RequestMapping("/faq")
	public String getFaq(ModelMap model) {
		return "faq";
	}
	
	/**
	 * Redirects the user to the Braintree merchant terms page
	 *
	 * @param model
	 * @return
	 */
	@PreAuthorize("permitAll")
	@RequestMapping("/braintreeMerchantTerms")
	public String getBraintreeMerchantTerms(ModelMap model) {

		return "redirect:https://www.braintreepayments.com/agreements/merchant";
	}	


	/**
	 * Simply routes the user to the support page
	 *
	 * @param model
	 * @return
	 */
	@PreAuthorize("permitAll")
	@RequestMapping("/support")
	public String getSupport(ModelMap model) {

		return "support";
	}


	@PreAuthorize("permitAll")
	@RequestMapping("/contestsAndPromotions")
	public String getContestsAndPromotions(ModelMap model) {

		return "contestsAndPromotions";
	}

	@PreAuthorize("permitAll")
	@RequestMapping("/sellOnFacebook")
	public String getSellOnFacebook(ModelMap model) {

		return "sellOnFacebook";
	}
	
	@PreAuthorize("permitAll")
	@RequestMapping("/sellOnTwitter")
	public String getSellOnTwitter(ModelMap model) {

		return "sellOnTwitter";
	}

	/**
	 * Simply routes the user to the signup page
	 *
	 * @param model
	 * @return
	 */
	@PreAuthorize("permitAll")
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String getSignup( WebRequest request, ModelMap model ) {

		// is the user is already logged in, forward them to the userHome page
		if( this.isUserLoggedIn() ) {
			return "forward:/userHome";
		}

		Connection<?> connection = new ProviderSignInUtils().getConnectionFromSession( request );
    	model.addAttribute( "hasPendingConnection", connection != null );
    	
    	SignUpForm form = new SignUpForm();
		model.addAttribute( "signUpForm", form );

		return "signup";
	}

	/**
	 * Signs the user up
	 *
	 * @param model
	 * @return
	 */
	@PreAuthorize("permitAll")
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String postSignup( 
			@ModelAttribute( "signUpForm") @Valid SignUpForm signUpForm,
			BindingResult bindingResult,
			NativeWebRequest request ) {
		
		// if the user is already signed in, ignore this request and forward to userHome
		if( this.isUserLoggedIn() ) {
			return "forward:/userHome";
		}

		Connection<?> connection = this.providerSignInUtils.getConnectionFromSession( request );
		
		String emailValidationError = this.emailService.validateEmailAddress( signUpForm.getEmailInput1(), signUpForm.getEmailInput2() );
		if( emailValidationError != null ) {
			bindingResult.addError( new FieldError( "signUpForm", "emailInput1", emailValidationError ) );
		}
				
		if( connection == null || bindingResult.hasErrors() ) {
			
			return "signin/signin";

		} else {
			
			// create the account
			String username = this.userService.userSignup( signUpForm, connection, request );
			
			// Sign the user in
			String originalUrl = this.simpleSignInAdapter.signIn( username, connection, request );
			
			
			String result = ( originalUrl == null ? "redirect:/userHome" : "redirect:" + originalUrl );
			return result;
	    }
		
		
	}

	@PreAuthorize("permitAll")
	@RequestMapping("/denied")
	public String getDenied(ModelMap model) {

		return "denied";
	}
	
	@RequestMapping(value="/signin", method={RequestMethod.GET, RequestMethod.HEAD})
	public String getSignin( ModelMap model, 
						     HttpServletRequest request,
						     @RequestHeader(value = "referer", required = false) final String referer,
						     Locale locale ) {

		HttpSession session = request.getSession();
		DefaultSavedRequest savedRequest = null;
		if( session != null ) {
			savedRequest = ( DefaultSavedRequest )session.getAttribute( "SPRING_SECURITY_SAVED_REQUEST" );
			if( savedRequest != null ) {
				
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "savedRequest(): " + savedRequest );
					this.logger.debug( "savedRequest servletPath(): " + savedRequest.getServletPath() );
				}
				
				String servletPath = savedRequest.getServletPath();
				String messageKey = this.getSignInUserInterfaceMessage( servletPath );
				
				if( messageKey != null ) {
					model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( 
							this.messageSource, messageKey, null, locale, UserInterfaceMessageDto.INFORMATIONAL ) );
				}
			}
		}
		
		if( this.isUserLoggedIn() ) {
			return "forward:/userHome";
		}
		
		/**
		 *  If the user is within the registration flow, add a model attribute so we can 
		 *  display custom text / links on the signin page.
		 */
		Object registrationType = session.getAttribute( SelfRegistrationController.REGISTRATION_TYPE_ATTRIBUTE );
		if( registrationType != null ) {
			model.put( "isRegistrationFlow", true );
			
			String subFlow = ( String )session.getAttribute( SelfRegistrationController.ACTIVE_SUB_FLOW_ATTRIBUTE );
			
			// logic to reset the subFlow for users who want to bypass Facebook
			if( subFlow.equals( SelfRegistrationController.SIGNUP_FACEBOOK_FLOW ) && savedRequest.getParameterNames().contains( "doBypassFacebook") ) {
				
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "User was in the SIGNUP_FACEBOOK_FLOW but requested to bypass Facebook. Updating subflow to SIGNUP_TWITTER." );
				}
				session.removeAttribute( SelfRegistrationController.ACTIVE_SUB_FLOW_ATTRIBUTE );
				session.setAttribute( SelfRegistrationController.ACTIVE_SUB_FLOW_ATTRIBUTE, SelfRegistrationController.SIGNUP_TWITTER_FLOW );
				subFlow = SelfRegistrationController.SIGNUP_TWITTER_FLOW;
				
			}
			
			model.put( "subFlow", subFlow );
		}

		return "signin/signin";
	}

	@RequestMapping(value="/newAccount", method=RequestMethod.GET)
	public String getNewAccount() {
		
		if( this.isUserLoggedIn() ) {
			return "forward:/userHome";
		}

		return "/signup";
	}
	
	
	/**
	 * Checks to see if the web container is healthy. If an HTTP GET is received by this controller, 
	 * we can assume that Tomcat is running and consider the system healthy.
	 * 
	 * Other system components' health need to be monitored separately.
	 * 
	 * @return
	 */
	@RequestMapping(value="/healthCheck", method=RequestMethod.GET)
	public ResponseEntity<String> getHealthCheck() {
		
		return new ResponseEntity<String>( HttpStatus.OK );
	}
	
	/**
	 * Instead of a 404, redirect a logged out user who goes to /signout back to the homepage 
	 */
	@PreAuthorize("permitAll")
	@RequestMapping(value="/signout", method=RequestMethod.GET)
	public String getSignout( ModelMap model ) {
		return this.getHomepage( model );
	}
	
	@PreAuthorize("permitAll")
	@RequestMapping(value="/maintenance")
	public String getMaintenance( ModelMap model ) {
		return "maintenance";
	}
	
	/**
	 * Internal methods 
	 */
	
	Boolean isUserLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && 
				authentication.getName() != null && 
				!authentication.getName().equals( "anonymousUser" );
	}
	
	Boolean isUserSellerOrCharity(Set<Role> userRoles) {
		return userRoles.contains( this.roleService.findByRoleNumber( RoleUtil.ROLE_SELLER_INT ) ) || userRoles.contains( this.roleService.findByRoleNumber( RoleUtil.ROLE_CHARITY_INT ) );
	}
	
	String getSignInUserInterfaceMessage( String servletPath ) {
		String result = null;
		
		if( servletPath != null ) {
			if( servletPath.startsWith( "/listing/giveawayWinner/" ) ) {
				result = "signin.continue.giveaway.winner.message";
			} else if( servletPath.startsWith( "/listing/paymentComplete/" ) ) {
				result = "signin.continue.digital.purchase.complete.message";
			} else if( servletPath.startsWith( "/listing/transactionDetail/" )) {
				result = "signin.continue.payment.details.message";
			}
			
		}
		
		return result;
	}

}
