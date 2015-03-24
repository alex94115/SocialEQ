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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.social.OperationNotPermittedException;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Account;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PageOperations;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestOperations;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.MerchantAccount;
import com.braintreegateway.MerchantAccountRequest;
import com.braintreegateway.Result;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.projectlaver.domain.Address;
import com.projectlaver.domain.Listing;
import com.projectlaver.domain.User;
import com.projectlaver.integration.SocialProviders;
import com.projectlaver.service.EmailService;
import com.projectlaver.service.ListingService;
import com.projectlaver.service.SocialService;
import com.projectlaver.service.UserService;
import com.projectlaver.util.FileList;
import com.projectlaver.util.FileMetadata;
import com.projectlaver.util.ListingStatus;
import com.projectlaver.util.MvcConstants;
import com.projectlaver.util.UserInterfaceMessageDto;
import com.projectlaver.validation.SellerInfoForm;
import com.projectlaver.validation.ListingForm;
import com.projectlaver.validation.SellerInfoFormValidator;
import com.shippingapis.usps.AddressNotFoundException;

@Controller
@RequestMapping("/registration")
@SessionAttributes({ "newListingDigitalContent" }) 
public class SelfRegistrationController extends AbstractListingCreatingController {
	
	/**
	 * Instance variables
	 */
	
	@Autowired
	private ListingService listingService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private SocialService socialService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
    private MessageSource messageSource;
	
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * Static variables
	 */
	
	// Http Session Attributes
	
	// Registration types
	public static final String REGISTRATION_TYPE_ATTRIBUTE = "REGISTRATION_TYPE";
	public static final String MUSICIAN_REGISTATION_TYPE = "MUSICIAN";
	
	// Sub flows
	public static final String ACTIVE_SUB_FLOW_ATTRIBUTE = "ACTIVE_SUB_FLOW";
	public static final String SIGNUP_FACEBOOK_FLOW = "SIGNUP_FACEBOOK";
	public static final String CONNECT_TWITTER_FLOW = "CONNECT_TWITTER";
	public static final String SIGNUP_TWITTER_FLOW = "SIGNUP_TWITTER";
	public static final String CREATE_LISTING_FLOW = "CREATE_LISTING";
	
	// Facebook Album 
	public static final String SOCIAL_EQ_ALBUM_NAME = "SocialEQ Album";
	public static final String SOCIAL_EQ_ALBUM_MESSAGE = "SocialEQ is the in-stream solution for selling and contests on Twitter and Facebook. See www.socialeq.co for more information.";

	// forms
	public static final String SELLER_INFO_FORM_ATTRIBUTE = "sellerInfoForm";
	public static final String SALE_FORM_ATTRIBUTE = "saleForm";
	
	public static final String UPLOADED_FILES_MODEL_ATTRIBUTE = "newListingDigitalContent";
	private static final Splitter SPLITTER = Splitter.on(CharMatcher.WHITESPACE);

	@PreAuthorize("permitAll")
	@RequestMapping("/musician")
	public String musicianStart( HttpServletRequest request, ModelMap model ) {
		
		/**
		 *  Add an attribue to the HttpSession so that even as the user gets bounced around to different controller we
		 *  can maintain the flow.
		 */
		HttpSession session = request.getSession( true );
		session.setAttribute( REGISTRATION_TYPE_ATTRIBUTE, MUSICIAN_REGISTATION_TYPE );
		session.setAttribute( ACTIVE_SUB_FLOW_ATTRIBUTE, SIGNUP_FACEBOOK_FLOW );
		this.getSessionAttributes( request );
		
		model.addAttribute( "isMusicianRegistrationLandingPage", true );
		
		return "registration/start";
	}
	
	@PreAuthorize("permitAll")
	@RequestMapping("/bypassFacebook")
	public String signupWithTwitter( HttpServletRequest request ) {
		
		Map<String, String> sessionAttributes = this.getSessionAttributes( request );
		HttpSession session = request.getSession();
		session.removeAttribute( ACTIVE_SUB_FLOW_ATTRIBUTE );
		session.setAttribute( ACTIVE_SUB_FLOW_ATTRIBUTE, SIGNUP_TWITTER_FLOW );
		
		// redirect to a secured URL so that we will trigger the signin page again
		return "redirect:/registration/addSellerInfo"; 
	}
	
	@PreAuthorize("permitAll")
	@RequestMapping("/bypassTwitter")
	public String bypassTwitter( HttpServletRequest request ) {
		
		return "registration/unableToRegister"; 
	}
	
	@PreAuthorize("hasRole('ROLE_BUYER')")
	@RequestMapping("/returnFromProvider") 
	public String returnFromProvider( HttpServletRequest request ) {
		
		Map<String, String> sessionAttributes = this.getSessionAttributes( request );
		String activeSubFlow = sessionAttributes.get( ACTIVE_SUB_FLOW_ATTRIBUTE );
		
		String result;
		if( activeSubFlow.equals( SIGNUP_FACEBOOK_FLOW ) ) {
			
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Signup facebook flow; redirecting to confirm the facebook access token permissions." );
			}
			
			// confirm that we have the necessary Facebook permissions
			result = "redirect:/registration/confirmFacebookPermissions";
			
		} else if( activeSubFlow.equals( SIGNUP_TWITTER_FLOW ) ) {
			
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Signup twitter flow complete; redirecting to add email next." );
			}
			
			HttpSession session = request.getSession();
			session.removeAttribute( ACTIVE_SUB_FLOW_ATTRIBUTE );
			session.setAttribute( ACTIVE_SUB_FLOW_ATTRIBUTE, CREATE_LISTING_FLOW );
			
			result = "redirect:/registration/addSellerInfo";
			
		} else if( activeSubFlow.equals( CONNECT_TWITTER_FLOW )) {
			
			if( !this.socialService.testCurrentUserConnection( Twitter.class )) {
				
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "Link twitter flow, but user did not authorize us; redirecting back to linkTwitter." );
				}
				
				// TODO add a user alert message
				result = "redirect:/registration/linkTwitter";
				
			} else {
				
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "Link twitter flow complete; redirecting to add email next." );
				}

				// good connection to Twitter
				HttpSession session = request.getSession();
				session.removeAttribute( ACTIVE_SUB_FLOW_ATTRIBUTE );
				session.setAttribute( ACTIVE_SUB_FLOW_ATTRIBUTE, CREATE_LISTING_FLOW );
				
				result = "redirect:/registration/addSellerInfo";
			}
			
		} else {
			throw new RuntimeException( "Unknown subflow: " + activeSubFlow );
		}
		
		return result;
	}
	
	@PreAuthorize("hasRole('ROLE_BUYER')")
	@RequestMapping("/confirmFacebookPermissions")
	public String confirmFacebookPermissions( HttpServletRequest request, ModelMap model ) {
		
		this.getSessionAttributes( request );
		
		if( this.socialService.confirmCurrentUserFacebookPermissions( "manage_pages", "publish_actions" )) {
			
			// we have the necessary permissions
			return "redirect:/registration/chooseFacebookPage";
			
		} else {
			
			return "registration/facebookPermissionsAppeal";
			
		}
		
	}
	
	@PreAuthorize("hasRole('ROLE_BUYER')")
	@RequestMapping(value="/chooseFacebookPage", method=RequestMethod.GET)
	public String confirmFacebookPage( HttpServletRequest request, ModelMap model ) {
		
		this.getSessionAttributes( request );
		
		Connection<Facebook> facebookConnection = this.socialService.getCurrentUserSocialConnection( Facebook.class );
		
		String result = null;
		if( facebookConnection != null ) {
			
			Facebook api = facebookConnection.getApi();
			PagedList<Account> accounts = api.pageOperations().getAccounts();
			
			if( accounts != null && accounts.size() > 0 ) {

				Map<String, String> managedPages = new HashMap<String, String>();
				for( Account account : accounts ) {
					String id = account.getId();
					String name = account.getName();
					managedPages.put( id, name );
				}
				
				model.put( "managedPageOptions", managedPages );
			
				result = "registration/chooseFacebookPage";
				
			} else { // no pages
				result = "registration/ineligibleForFacebook";
				
			}
		}
		
		return result;
		
	}
	
	@PreAuthorize("hasRole('ROLE_BUYER')")
	@RequestMapping(value="/chooseFacebookPage",method=RequestMethod.POST)
	public String chooseFacebookPage( String facebookPageId, HttpServletRequest request, ModelMap model ) {
		
		this.getSessionAttributes( request );
		
		// Set the FB page id onto the user
		User user = this.userService.getCurrentUser();
		user.setFacebookPageId( facebookPageId );
		
		// If it doesn't exist, create an album on the page and save the album id
		Connection<Facebook> connection = this.socialService.getCurrentUserSocialConnection( Facebook.class );
		Facebook api = connection.getApi();

		// Get existing (or create new) SocialEQ photo album
		this.setFacebookPageAttributesOnUser( user, api, facebookPageId );
		
		// save the user
		this.userService.update( user );
		
		// end the FACEBOOK flow
		HttpSession session = request.getSession();
		session.removeAttribute( ACTIVE_SUB_FLOW_ATTRIBUTE );
		session.setAttribute( ACTIVE_SUB_FLOW_ATTRIBUTE, CONNECT_TWITTER_FLOW );
		
		return "registration/linkTwitter";
	}

	@PreAuthorize("hasRole('ROLE_BUYER')")
	@RequestMapping("/linkTwitter")
	public String linkTwitter( HttpServletRequest request ) {
		
		Map<String, String> sessionAttributes = this.getSessionAttributes( request );
		if( !sessionAttributes.get( ACTIVE_SUB_FLOW_ATTRIBUTE ).equals( CONNECT_TWITTER_FLOW ) ) {
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Updating sub flow to be CONNECT_TWITTER_FLOW" );
			}
			
			HttpSession session = request.getSession();
			session.removeAttribute( ACTIVE_SUB_FLOW_ATTRIBUTE );
			session.setAttribute( ACTIVE_SUB_FLOW_ATTRIBUTE, CONNECT_TWITTER_FLOW );
			
		}
		
		// direct to the link twitter page
		return "registration/linkTwitter";
	}
	
	@PreAuthorize("hasRole('ROLE_BUYER')")
	@RequestMapping("/addSellerInfo")
	public String addSellerInfo( ModelMap model, HttpServletRequest request  ) {
		
		this.getSessionAttributes( request );
		
		SellerInfoForm form = new SellerInfoForm();
		model.addAttribute( SELLER_INFO_FORM_ATTRIBUTE, form );
		
		return "registration/addSellerInfo";
	}
	
	@PreAuthorize("hasRole('ROLE_BUYER')")
	@RequestMapping(value="/submitSellerInfo", method=RequestMethod.GET)
	public String getSubmitSellerInfo( ModelMap model, HttpServletRequest request  ) {
		
		this.getSessionAttributes( request );
		
		SellerInfoForm form = new SellerInfoForm();
		model.addAttribute( SELLER_INFO_FORM_ATTRIBUTE, form );
		
		return "registration/addSellerInfo";
	}
	
	@PreAuthorize("hasRole('ROLE_BUYER')")
	@RequestMapping(value="/submitSellerInfo", method=RequestMethod.POST)
	public String submitSellerInfo( @ModelAttribute(SELLER_INFO_FORM_ATTRIBUTE) @Valid SellerInfoForm sellerInfoForm, 
							   BindingResult result,
							   HttpServletRequest request,
							   ModelMap model,
							   Locale locale ) {
		
		this.getSessionAttributes( request );
		
		SellerInfoFormValidator validator = new SellerInfoFormValidator( sellerInfoForm, result, this.messageSource, locale );
		result = validator.doValidation();
		
		/**
		 *  Redirect the user back to input data again if validation errors exist
		 */
		if( result.hasErrors()) {
			
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Seller data validation has errors; redirecting the user to fix." );
			}
			
			model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( this.messageSource, "sellerInfoForm.invalid", locale, UserInterfaceMessageDto.ERROR ) );
			return "registration/addSellerInfo";
		}

		/**
		 *  Otherwise persist the seller info and continue
		 */
		try {
			
			this.userService.updateSellerInfo( sellerInfoForm, locale );
		
		} catch( AddressNotFoundException e ) {
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Couldn't validate the seller's address. Redirecting back to the input page to ask them to try again." );
			}
			
			model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( this.messageSource, "sellerInfoForm.invalid", locale, UserInterfaceMessageDto.ERROR ) );
			return "registration/addSellerInfo";
		}
		
		return this.createListing( model, request );
	}
	
	@PreAuthorize("hasRole('ROLE_BUYER')")
	@RequestMapping(value="/createListing", method=RequestMethod.GET)
	public String createListing( ModelMap model, HttpServletRequest request ) {

		this.getSessionAttributes( request );
		
		ListingForm form = new ListingForm();
		model.addAttribute( SALE_FORM_ATTRIBUTE, form );
		
		/**
		 * Remove any 'leftover' files
		 */
		Object uploadedFilesObject = model.remove( UPLOADED_FILES_MODEL_ATTRIBUTE );
		FileList fileList = null;
		if( uploadedFilesObject != null ) {
			fileList = (FileList)uploadedFilesObject;
		}
			
		if( fileList != null ) {
			List<FileMetadata> files = fileList.getFiles();
			for( FileMetadata file : files ) {
				this.listingService.deleteUploadedDigitalContent( file.getId(), fileList );
			}
		}
		
		// create the digital content upload attribute
		FileList emptyfileList = new FileList();
		LinkedList<FileMetadata> files = new LinkedList<FileMetadata>();
		emptyfileList.setFiles( files );
		model.addAttribute( UPLOADED_FILES_MODEL_ATTRIBUTE, emptyfileList );
		
		return "registration/createListing";
	}
	
	@PreAuthorize("hasRole('ROLE_BUYER')")
	@RequestMapping(value = "/uploadFiles", method = RequestMethod.POST, produces = "application/json" )
	public @ResponseBody
	FileList uploadFiles(MultipartHttpServletRequest request, HttpServletResponse response, ModelMap model) {

		FileList fileList = (FileList) model.get( UPLOADED_FILES_MODEL_ATTRIBUTE );
		if( fileList == null ) {
			LinkedList<FileMetadata> files = new LinkedList<FileMetadata>();
			
			fileList = new FileList();
			fileList.setFiles( files );
			
			// add UPLOADED_FILES to the model. Since this is declared a session attribute,
			// this will continue to be available to the user during this session
			model.addAttribute( UPLOADED_FILES_MODEL_ATTRIBUTE, fileList );
		}
		
		Iterator<String> itr = request.getFileNames();
		LinkedList<MultipartFile> multipartFiles = new LinkedList<MultipartFile>();
		while( itr.hasNext() ) {
			MultipartFile mpf = request.getFile( itr.next() );
			multipartFiles.add( mpf );
		}
		
		this.listingService.handleDigitalContentUpload( fileList, multipartFiles );

		return fileList;
	}

	@PreAuthorize("hasRole('ROLE_BUYER')")
    @RequestMapping(value = "/uploadFiles", method = RequestMethod.GET)
    @ResponseBody 
    public FileList list( ModelMap model ) {
        
        FileList fileList = (FileList) model.get( UPLOADED_FILES_MODEL_ATTRIBUTE );
        
        if( this.logger.isDebugEnabled() ) {
        	this.logger.debug( "Returning: {" + fileList +"}");
        }
        return fileList;
    }	
	
	
	@PreAuthorize("hasRole('ROLE_BUYER')")
    @RequestMapping(value = "/newListingContentDelete/{id}", method = RequestMethod.DELETE)
    @ResponseBody 
    public Map<String, List<Map<String, Object>>> deleteNewListingContent(@PathVariable Long id, ModelMap model) {
    	
    	FileList fileList = (FileList) model.get( UPLOADED_FILES_MODEL_ATTRIBUTE );
        return this.listingService.deleteUploadedDigitalContent( id, fileList );
    }
    
	
	@PreAuthorize("hasRole('ROLE_BUYER')")
    @RequestMapping(value = "/newListingThumbnail/{id}", method = RequestMethod.GET)
    public void newListingThumbnail(ModelMap model, HttpServletResponse response, @PathVariable Long id) {
    	
    	FileList fileList = (FileList) model.get( UPLOADED_FILES_MODEL_ATTRIBUTE );
		this.listingService.getNewListingContentThumbnail( fileList, id, response );
    }

	@PreAuthorize("hasRole('ROLE_BUYER')")
    @RequestMapping(value = "/newListingContent/{id}", method = RequestMethod.GET)
    public void content(ModelMap model, HttpServletResponse response, @PathVariable Long id) {
    	
    	FileList fileList = (FileList) model.get( UPLOADED_FILES_MODEL_ATTRIBUTE );
		this.listingService.getNewListingContent( fileList, id, response );
    }
	
	@PreAuthorize("hasRole('ROLE_BUYER')")
	@RequestMapping(value="/firstListing", method=RequestMethod.POST)
	public String postFirstListing( @ModelAttribute(SALE_FORM_ATTRIBUTE) @Valid ListingForm listingForm,
									BindingResult result,
									HttpServletRequest request,
									ModelMap model,
									RedirectAttributes redirectAttributes,
									SessionStatus status,
									Locale locale ) throws Exception {
		
		this.getSessionAttributes( request );
		
		/**
		 * Set the listing publish to field
		 */
		
		List<String> publishTo = new ArrayList<String>();
		User user = this.userService.getCurrentUser();

		// Publish to Facebook if the user has a facebook page id
		if( user.getFacebookPageId() != null ) {
			publishTo.add( SocialProviders.FACEBOOK );
		}
		
		// Publish to Twitter if this user has a Twitter connection
		if( this.socialService.testCurrentUserConnection( Twitter.class ) ) {
			publishTo.add( SocialProviders.TWITTER );
		}
		
		listingForm.setPublishTo( publishTo );
		
		// Calculate the keyword
		listingForm.setKeyword( this.createKeywordFromTitle( listingForm.getTitle() ));
		
		// Use a default message
		listingForm.setMessage( "My digital music is now for sale in stream via SocialEQ!" );
		
		// apply validation rules and add errors to the result
 		FileList fileList = (FileList) model.get( UPLOADED_FILES_MODEL_ATTRIBUTE );
		super.validateListing( listingForm, fileList, result, locale);

		// TODO need to look at the specific errors that matter here (since the incoming BindingResult has errors that we don't care about).
		//   another alternative would be to look at removing errors above for the keyword, publishTo, message fields
		BindingResult musicianFirstListingResult  = this.filterBindingResult( listingForm, result );
		
		// if validation errors exist, route the user back to the create listing page to fix
		if( musicianFirstListingResult.hasErrors() ) {
			
			if( this.logger.isDebugEnabled() ) {
				List<FieldError> errors = musicianFirstListingResult.getFieldErrors();
				
				for( FieldError error : errors ) {
					this.logger.debug( String.format( "Validation error: field: %s, message: %s", error.getField(), error.getDefaultMessage() ) );
				}
			}
				
			model.addAttribute( "hasNewListingFieldErrors", super.containsNewListingFieldErrors( musicianFirstListingResult ));
			model.addAttribute( "hasNewListingUploadErrors", super.containsNewListingUploadErrors( musicianFirstListingResult ));
			model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( this.messageSource, "saleForm.invalid", locale, UserInterfaceMessageDto.ERROR ) );
			
			return "registration/createListing";

		} else {

			// validation passed
			User seller = this.userService.getCurrentUser();
			Listing listing = super.createListing( seller, listingForm, fileList, locale );
			
			// Listings created by brand new users need to be reviewed
			listing.setStatus( ListingStatus.UNREVIEWED );
			
			try {
				
				// persist the listing
				Listing persistedListing = this.listingService.create( listing ); 
				
				// create the braintree sub merchant
				Boolean isRegisteredWithBraintree = this.listingService.createBraintreeSubMerchant( seller.getId() );

				// send email notifications
				this.sendRegistrationNotifications( listing, isRegisteredWithBraintree );
				
				// cleanup the temp files
				listing.cleanupFiles();
				status.setComplete();
				
				// remove the session flow attribute
				HttpSession session = request.getSession();
				if( session != null ) {
					session.removeAttribute( REGISTRATION_TYPE_ATTRIBUTE );
					session.removeAttribute( ACTIVE_SUB_FLOW_ATTRIBUTE );
				}

			} catch( OperationNotPermittedException e ) {
				logger.error( "OperationNotPermittedException from Twitter." , e );
				model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( this.messageSource, "application.error", locale, UserInterfaceMessageDto.ERROR ) );
				return "listing/new";
			}

			return "registration/finished";
		}	
	}
	
	String createKeywordFromTitle( String title ) {

		Iterable<String> tokens = SPLITTER.split( title );
		Iterator<String> iter = tokens.iterator();
		
		StringBuffer keywordBuffer = new StringBuffer( "#" ); 
		while( iter.hasNext() ) {
			String word = iter.next();
			
			// Just drop words that aren't alphanumeric
			if( !StringUtils.isAlphanumericSpace( word ) ) {
				continue;
			}
			
			if( keywordBuffer.length() + word.length() > 20 ) {
				// too long to append this word
				break;
			} else {
				keywordBuffer.append( WordUtils.capitalize( word ) );
			}
		}
		
		if( keywordBuffer.length() <= 13 ) {
			keywordBuffer.append( "Digital" );
		}
		
		return keywordBuffer.toString();
	}
	
	Map<String, String> getSessionAttributes( HttpServletRequest request ) {
		
		HttpSession session = request.getSession();
		String registrationType = (String)session.getAttribute(REGISTRATION_TYPE_ATTRIBUTE );
		String activeSubFlow = (String)session.getAttribute(ACTIVE_SUB_FLOW_ATTRIBUTE );
		
		if( registrationType == null || activeSubFlow == null ) {
			
			throw new RuntimeException( String.format( "Lost session attributes. registrationType: %s, activeSubFlow: %s", registrationType, activeSubFlow ) ); 
		}
		
		Map<String, String> sessionAttributes = new HashMap<String, String>();
		sessionAttributes.put( REGISTRATION_TYPE_ATTRIBUTE, registrationType );
		sessionAttributes.put( ACTIVE_SUB_FLOW_ATTRIBUTE, activeSubFlow );
		
		return sessionAttributes;
		
	}
	
	/**
	 * Takes a full "listing" binding result and then filters it for field errors that
	 * we don't care about. Specifically, since in this flow we are auto generating
	 * the keyword (hashtag), publish to, and tweet (message), errors on these fields
	 * shouldn't block us.
	 * 
	 * @param form
	 * @param input
	 * @return
	 */
	BindingResult filterBindingResult( ListingForm form, BindingResult input ) {
		
		BeanPropertyBindingResult result = new BeanPropertyBindingResult( form, "saleForm" );
		List<FieldError> errors = input.getFieldErrors();
		for( FieldError fieldError : errors ) {
			String field = fieldError.getField();
			if( !this.canSkipListingValidationErrorOn( field )) {
				result.addError( fieldError );
			}
		}
		
		return result;
	}
	
	Boolean canSkipListingValidationErrorOn( String field ) {
		return StringUtils.equals( "publishTo", field ) || StringUtils.equals( "keyword", field ) || StringUtils.equals( "message", field );
	}
	
	void setFacebookPageAttributesOnUser( User user, Facebook api, String facebookPageId) {
		
		RestOperations ro = api.restOperations();
		
		// Query for this page's link and the page's albums' names and id's
		String fieldsFilter="albums{name,id},link";
		JsonNode node = ro.getForObject( Facebook.GRAPH_API_URL + "{pageId}/?fields={fieldsFilter}", JsonNode.class, facebookPageId, fieldsFilter );
		
		// Set the facebook page link onto the user
		user.setFacebookPageUrl( node.path("link").asText() );
		
		Iterator<JsonNode> albumNodes = node.path("albums").path("data").iterator();
		
		String pageAlbumId = null;
		while( albumNodes.hasNext() ) {
			JsonNode album = albumNodes.next();
			String albumId = album.findValue( "id" ).asText();
			String albumName = album.findValue( "name" ).asText();
			
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( String.format( "Found existing album id: %s, name: %s", albumId, albumName ) );
			}
			
			if( albumName.equals( SOCIAL_EQ_ALBUM_NAME )) {
				pageAlbumId = albumId;
				break;
			}
		}
		
		if( pageAlbumId == null ) {
			pageAlbumId = this.createSocialEqAlbum( api, facebookPageId );
		}
		
		// Set the facebook album id onto the user
		user.setFacebookAlbumId( pageAlbumId );
		
	}
	
	String createSocialEqAlbum( Facebook api, String facebookPageId ) {
		
		PageOperations po = api.pageOperations();
		PagedList<Account> accounts = po.getAccounts();
		
		String pageAccessToken = null;
		for( Account account : accounts ) {
			if( account.getId().equals( facebookPageId )) {
				pageAccessToken = account.getAccessToken();
			}
		}
		
		String result = null;
		if( pageAccessToken != null ) {
			Facebook pageApi = new FacebookTemplate( pageAccessToken );
			RestOperations ro = pageApi.restOperations();
			
			MultiValueMap<String, String> request = new LinkedMultiValueMap<String, String>();
			request.set( "name", SOCIAL_EQ_ALBUM_NAME );
			request.set( "message", SOCIAL_EQ_ALBUM_MESSAGE );
			
			JsonNode jsonNode = ro.postForObject( Facebook.GRAPH_API_URL + "{pageId}/albums", request, JsonNode.class, facebookPageId );
			result = jsonNode.path( "id" ).asText();
		}
		
		return result;
		
	}
	
	void sendRegistrationNotifications( Listing listing, Boolean isRegisteredWithBraintree ) {
		
		String[] recipients = { "justin@socialeq.co", "alex@socialeq.co" };
		
		this.emailService.sendEmail( 
				recipients,
				String.format( "Newly registered user: %s", listing.getSeller().getUsername() ), 
				String.format( "A new user has registered and created a listing.\nUsername: %s\nUser Id: %d\nBraintree Registration Succeeded?: %s\nListing Id: %d\nListing Title: %s\nListing Amount: %f\nKeyword: %s", 
						listing.getSeller().getUsername(),
						listing.getSeller().getId(),
						isRegisteredWithBraintree,
						listing.getId(),
						listing.getTitle(), 
						listing.getAmount(),
						listing.getKeyword()) );
		
	}
	
}
