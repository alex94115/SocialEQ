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

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.social.OperationNotPermittedException;
import org.springframework.social.connect.Connection;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projectlaver.domain.Address;
import com.projectlaver.domain.Inventory;
import com.projectlaver.domain.Listing;
import com.projectlaver.domain.Message;
import com.projectlaver.domain.Payment;
import com.projectlaver.domain.User;
import com.projectlaver.integration.SocialProviders;
import com.projectlaver.service.AddressService;
import com.projectlaver.service.ListingService;
import com.projectlaver.service.MessageService;
import com.projectlaver.service.PaymentService;
import com.projectlaver.service.PaymentProviderService;
import com.projectlaver.service.SocialService;
import com.projectlaver.service.UserService;
import com.projectlaver.util.AddressValidationResponse;
import com.projectlaver.util.CheckoutAlreadyCompletedException;
import com.projectlaver.util.ContinueToCheckoutResponse;
import com.projectlaver.util.FileList;
import com.projectlaver.util.FileMetadata;
import com.projectlaver.util.GoodsType;
import com.projectlaver.util.InitiatedPaymentDTO;
import com.projectlaver.util.ListingActionButton;
import com.projectlaver.util.ListingDetailDTO;
import com.projectlaver.util.MessageStatus;
import com.projectlaver.util.MvcConstants;
import com.projectlaver.util.StartCheckoutFailedException;
import com.projectlaver.util.UserInterfaceMessageDto;
import com.projectlaver.validation.AddressForm;
import com.projectlaver.validation.ListingForm;
import com.shippingapis.usps.AddressNotFoundException;

@Controller
@RequestMapping("/listing")
@SessionAttributes({ "messageId", "listingDetailReferer", "newListingDigitalContent", "addressChoices", "inProgressPayment", "listingId" }) 
public class ListingController extends AbstractListingCreatingController {

	/**
	 * Instance variables
	 */
	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private ListingService listingService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private PaymentProviderService paymentProviderService;
	
	@Autowired
	private PaymentService paymentService;

	@Autowired
	private AddressService addressService;

	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private SocialService socialService;
	
	@Value("${facebook.clientId}")
	private String facebookAppId;
	
	/**
	 * Constants
	 */
	public static final String SALE_FORM = "saleForm";
	public static final String UPLOADED_FILES = "newListingDigitalContent";
	
	/**
	 * Public Methods
	 */
	
	@PreAuthorize("hasRole('ROLE_SELLER')")
	@RequestMapping(value="/newListing", method=RequestMethod.GET)
	public String getNewListing( ModelMap model, Locale locale ) {
		
		ListingForm form = new ListingForm();
		model.addAttribute( ListingController.SALE_FORM, form );
		
		Map<String, Object> enabledListingProviders = this.listingService.getSellerEnabledListingProviders();
	
		Boolean doEnableFacebook = ( Boolean )enabledListingProviders.get( "doEnableFacebook" );
		Boolean doEnableTwitter = ( Boolean )enabledListingProviders.get( "doEnableTwitter" );
		
		if( doEnableFacebook || doEnableTwitter ) {
			model.put( "enableFacebook", doEnableFacebook );
			model.put( "enableTwitter", doEnableTwitter );
			if( doEnableTwitter ) {
				form.setDisplayName( ( String )enabledListingProviders.get( "twitterDisplayName" ) );
			}
			
		} else {
			
			// if this seller has no enabled listing providers, show an error
			model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( 
					this.messageSource, "listing.new.configuration.error", locale, UserInterfaceMessageDto.ERROR ) );
			return "applicationError";
		}
		
		
		// remove any 'leftover' files
		FileList fileList = (FileList) model.remove( UPLOADED_FILES );
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
		model.addAttribute( UPLOADED_FILES, emptyfileList );

		return "listing/new";
	}
	
	@PreAuthorize("hasRole('ROLE_SELLER')")
	@RequestMapping(value="/newListing", method=RequestMethod.POST)
	public String postNewListing( @ModelAttribute(ListingController.SALE_FORM) @Valid ListingForm listingForm,
																	 BindingResult result,
																	 ModelMap model,
																	 RedirectAttributes redirectAttributes,
																	 SessionStatus status,
																	 Locale locale ) throws Exception {
 		// apply validation rules and add errors to the result
 		FileList fileList = (FileList) model.get( UPLOADED_FILES );
		super.validateListing( listingForm, fileList, result, locale);

		// if validation errors exist, route the user back to the new page to fix
		if( result.hasErrors() ) {
			
			model.addAttribute( "hasNewListingFieldErrors", super.containsNewListingFieldErrors( result ));
			model.addAttribute( "hasNewListingUploadErrors", super.containsNewListingUploadErrors( result ));
			model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( this.messageSource, "saleForm.invalid", locale, UserInterfaceMessageDto.ERROR ) );
			return "listing/new";

		} else {
			// validation passed
			User seller = this.userService.getCurrentUser();

			// FUTURE: re-integrate donations

			if( seller.getEmailAddress() == null || seller.getEmailAddress().isEmpty() ) {
				throw new RuntimeException( "User: " + seller.getId() + " attempted to post a sale but does not have an email address." );
			}

			Listing listing = super.createListing( seller, listingForm, fileList, locale );

			try {

				// persist the listing
				Listing persistedListing = this.listingService.create( listing ); // <-- PERSISTENCE

				// cleanup the temp files
				listing.cleanupFiles();
				status.setComplete();

			} catch( OperationNotPermittedException e ) {
				if( this.logger.isErrorEnabled() ) {
					this.logger.error( "OperationNotPermittedException from Twitter." , e );
				}
				
				model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( this.messageSource, "application.error", locale, UserInterfaceMessageDto.ERROR ) );
				return "listing/new";
			}

			// Redirect to GET listing detail
			redirectAttributes.addAttribute( "newListingCreated", "TRUE" );
			return "redirect:/listing/listingDetail/" + listing.getId();
		}
	}

	@RequestMapping(value="/listingDetail/{id}", method={RequestMethod.HEAD} )
	public ResponseEntity<String> listingDetailHead( @PathVariable Long id ) {
		
		Boolean doesListingExist = this.listingService.doesListingExist( id );
		
		if( doesListingExist ) {
			return new ResponseEntity<String>( HttpStatus.OK );
		} else {
			return new ResponseEntity<String>( HttpStatus.NOT_FOUND ); // 404
		}
	}
	
	@RequestMapping(value="/listingDetail/{id}", method={RequestMethod.GET} )
	public String listingDetail( @PathVariable Long id,
								 @RequestParam( value = "newListingCreated", defaultValue = "FALSE" ) Boolean isListingNewlyCreated,
								 @RequestHeader(value = "referer", required = false) final String referer,
								 ModelMap model,
								 Locale locale ) {
		
		Boolean isReferredByPendingPurchaseNotification = false;
		ListingDetailDTO dto = new ListingDetailDTO( id, isListingNewlyCreated, isReferredByPendingPurchaseNotification, referer, locale );
		
		return this.doListingDetail(dto, model);
	}
	
	@PreAuthorize("hasRole('ROLE_BUYER')")
	@RequestMapping(value="/checkout/{id}", method={RequestMethod.GET}) //, RequestMethod.HEAD} )
	public String startCheckoutListingDetail( @PathVariable Long id,
										 @RequestParam(value="paymentErrorMessage", required=false) String paymentErrorMessage,
										 @RequestHeader(value = "referer", required = false) final String referrer,
										 ModelMap model,
										 Locale locale ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( 
					"Controller received request for listing/checkout/%d, paymentErrorMessage: %s, referrer: %s",
					id, paymentErrorMessage, referrer ) );
		}
		
		
		String view = null;
			
		Boolean isListingNewlyCreated = false;
		Boolean isCheckoutMode = true;
		
		ListingDetailDTO dto = new ListingDetailDTO( id, isListingNewlyCreated, isCheckoutMode, referrer, locale );
		view = this.doListingDetail( dto, model);
		
		if( StringUtils.isNotBlank( paymentErrorMessage )) {
			model.addAttribute( MvcConstants.UI_MESSAGES_KEY, 
					new UserInterfaceMessageDto( this.messageSource, "payment.processing.failed", new Object[] { paymentErrorMessage }, locale, UserInterfaceMessageDto.ERROR ) );
		}
		
		return view;
	}
	
	
	@PreAuthorize("hasRole('ROLE_BUYER')")
	@RequestMapping(value="/completeCheckout/{id}", method={RequestMethod.GET, RequestMethod.HEAD, RequestMethod.POST} )
	public String completeCheckout( @PathVariable Long id, 
									@RequestParam("payment_method_nonce") String nonce,	
									@RequestParam(value = "doSaveMethodOfPayment", required=false) Boolean doStoreInVault,
									ModelMap model, 
									Locale locale ) {
		
		// retrieve these from session [put there by startCheckoutListingDetail()]
		InitiatedPaymentDTO inProgressPayment = (InitiatedPaymentDTO) model.remove( ListingService.IN_PROGRESS_PAYMENT_KEY );
		
		if( inProgressPayment.getDidStartCheckoutSucceed() ) {
			
			// add the nonce to the inProgressPayment
			inProgressPayment.setCheckoutToken( nonce );
			inProgressPayment.setDoStoreInVault( doStoreInVault );
			
			Map<String, Object> result = this.paymentProviderService.completeCheckout( inProgressPayment );
			
			Payment payment = (Payment) result.get( "payment" );
			
			if( payment != null ) {
				
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "Found a non-null payment: " + payment + ", routing user to finishCheckout view." );
				}
				
				model.put( "paymentId", payment.getId());
				return "listing/finishCheckout";
				
			} else {
				
				/**
				 * A braintree payment that fails will end up here. Need to redirect the user
				 * back to the checkout page and show them an error message.
				 */
				
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "Payment was null, redirecting back to start of checkout." );
				}
				
				return this.redirectToCheckoutWithError( inProgressPayment );
			}
			
		} else {
			return this.redirectToCheckoutWithError( inProgressPayment );
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_BUYER','ROLE_SELLER','ROLE_CHARITY')")
	@RequestMapping(value="/transactionDetail/{id}", method=RequestMethod.GET)
	public String transactionDetail( @PathVariable Long id, 
								 @RequestParam(value = "status", required = false, defaultValue = "") String status,
								 @RequestParam( value = "addressSaved", required = false, defaultValue = "FALSE" ) Boolean isAddressSaved,
								 ModelMap model, 
								 Locale locale ) {
		
		if( id == null ) {
			model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( this.messageSource, MvcConstants.STATUS_PAGE_INVALID, locale, UserInterfaceMessageDto.ERROR ) );
			return MvcConstants.ERROR_TEMPLATE;
		}
		
		Map<String, Object> transactionDetails = this.listingService.getTransactionDetails( id );
		Payment payment = ( Payment )transactionDetails.get( "paymentInfo" );
		
		if( payment != null ) {
			model.addAllAttributes( transactionDetails );
			
			Boolean isUserBuyer = ( Boolean )transactionDetails.get( "userIsBuyer" );
			Boolean isListingPhysical = ( Boolean )transactionDetails.get( "listingIsPhysical" );
			
			// Base the alerts off the payment's screen status, since this overrides the message status
			// field in "transition" states (like when the user has added an address but the batch job
			// hasn't picked it up yet.
			String screenStatus = payment.getScreenFormattedStatus();
			
			// Is this a tangible item that needs shipping?
			if( isListingPhysical && isUserBuyer ) {
				if( isAddressSaved || screenStatus.equals( "Pending Shipment" )) {
					model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( 
						this.messageSource, "payment.detail.received.shippingAddress.message", locale, UserInterfaceMessageDto.SUCCESS ) );
				} else if( screenStatus.equals( "Pending Shipping Address" ) ) {
					model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( 
							this.messageSource, "payment.detail.pending.shippingAddress.message", locale, UserInterfaceMessageDto.SUCCESS ) );
				} else if( screenStatus.equals( "Completed" ) ) {
					model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( 
							this.messageSource, "payment.detail.shipped.message", locale, UserInterfaceMessageDto.SUCCESS ) );
				}
				// TODO add new else if logic & messages for payment processing, payment pending, and payment failed
				
			// Is the user the buyer of a digital download?
			} else if ( payment.getListing().getGoodsType().equals( GoodsType.DIGITAL ) && isUserBuyer ) {
				
				// Adds success message "Thanks! Your digital download is available now."
				model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( 
						this.messageSource, "payment.detail.digital.purchase.complete.message", locale, UserInterfaceMessageDto.SUCCESS ) );
			}
		} else {
			model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( 
					this.messageSource, MvcConstants.STATUS_PAGE_INVALID, locale, UserInterfaceMessageDto.ERROR ) );
			return MvcConstants.ERROR_TEMPLATE;
		}
		
		return "listing/transactionDetail";

	}

	@PreAuthorize("hasRole('ROLE_BUYER')")
	@RequestMapping(value="/setShippingAddress", method=RequestMethod.POST )
	public String setShippingAddress( Long addressId, Long paymentId, ModelMap model, Locale locale ) {
		
		Long requestorId = this.userService.getCurrentUserId();

		// only allow the buyer to set the shipping address
		Payment payment = this.paymentService.securedFindPaymentFetchEagerly( paymentId, requestorId );

		// make sure that the status is correct
		if( payment != null &&
			payment.getShippingAddress() == null &&
			payment.getMessage().getStatus().equals( MessageStatus.PENDING_SHIPPING_ADDRESS )) {

			// make sure that this the the buyer
			if( requestorId.equals( payment.getPayer().getId() ) ) {

				// TODO there's got to be a smarter way of doing this
				User buyer = payment.getPayer();
				Set<Address> addresses = buyer.getAddresses();
				for( Address address : addresses ) {
					if( address.getId().equals( addressId )) {
						payment.setShippingAddress( address );
						break;
					}
				}

				if( payment.getShippingAddress() != null ) {
					this.paymentService.save( payment );
					if( this.logger.isDebugEnabled() ) {
						this.logger.debug( "Associated an existing shipping address to a PENDING_SHIPPING_ADDRESS message.");
					}

					// reset the message's batch processor to Null so the next batch job run will pick it up again
					Message message = this.messageService.findByPaymentId( payment.getId() );
					message.setBatchProcessor( null );
					this.messageService.updateBypassHibernate( message );

					model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( 
							this.messageSource, "payment.detail.received.shippingAddress.message", locale, UserInterfaceMessageDto.SUCCESS ) );
				} else {
					// something weird happened
					throw new RuntimeException( "User " + requestorId + " submitted an address id: " + addressId + " that does not belong to the user?" );
				}
			}
		} else if( payment != null && payment.getShippingAddress() != null ) {
			
			// Can't set the shipping address twice
			model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto(this.messageSource, "payment.detail.shippingAddress.already.set.message", locale, UserInterfaceMessageDto.ERROR ) );
			return this.transactionDetail( paymentId, null, null, model, locale );
			
		} else {
			
			model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( this.messageSource, MvcConstants.STATUS_PAGE_INVALID, locale, UserInterfaceMessageDto.ERROR ) );
			return MvcConstants.ERROR_TEMPLATE;
			
		}

		return this.transactionDetail( paymentId, null, null, model, locale );
	}

	@PreAuthorize("hasRole('ROLE_SELLER')")
	@RequestMapping(value="/setIsShipped/{paymentId}", method=RequestMethod.POST)
	public String setIsShipped( @PathVariable Long paymentId, ModelMap model, Locale locale ) {
		
		Boolean result = this.listingService.markPaymentAsShipped( paymentId );
		
		if( result ) {
			model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( this.messageSource, "payment.detail.set.isShipped.complete.message", locale, UserInterfaceMessageDto.SUCCESS  ) );
		} else {
			model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( this.messageSource, "payment.detail.set.isShipped.warning.message", locale, UserInterfaceMessageDto.INFORMATIONAL ) );
		}
		
		return this.transactionDetail( paymentId, null, null, model, locale );
		
	}

	@PreAuthorize("permitAll")
	@RequestMapping(value="/imageContent/{name:.*}", method=RequestMethod.GET )
	public void streamImageContent( @PathVariable String name, OutputStream outputStream, ModelMap model ) throws Exception {
		
		if( name == null ) {
			throw new RuntimeException( "Invalid request -- image name not specified." );
		}

		this.listingService.streamImage(name, outputStream);
	}

	@PreAuthorize("hasAnyRole('ROLE_BUYER','ROLE_SELLER')")
	@RequestMapping(value="/downloadPurchase/**", method=RequestMethod.GET)
	public void downloadPurchasedContent( String name, HttpServletResponse response, OutputStream outputStream ) throws Exception {
		
		if( name == null ) {
			throw new RuntimeException( "Invalid request -- file name not specified." );
		}

		User requestor = this.userService.getCurrentUser();
		this.listingService.streamPurchasedContent( requestor, name, response, outputStream );
	}

	@PreAuthorize("hasRole('ROLE_BUYER')")
	@RequestMapping(value="/transactionDetail/postBuyerAddress", method=RequestMethod.POST)
	public String postBuyerAddressFromPayment( Long paymentId, 
											   String addressRadio,
											   Long existingAddressRadio,
											   RedirectAttributes redirectAttributes,
											   ModelMap model, 
											   Locale locale ) throws Exception {
		
		// if validation errors exist, dispatch the user back to the new page to fix
		if( addressRadio == null && existingAddressRadio == null ) {

			Long requestorId = this.userService.getCurrentUserId();
			Payment payment = this.paymentService.securedFindPaymentFetchEagerly( paymentId, requestorId );

			// only allow viewing the Payment details if the requestor is the payer or the payee
			if( payment == null ) {
				model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( this.messageSource, MvcConstants.STATUS_PAGE_INVALID, locale, UserInterfaceMessageDto.ERROR ) );
				return MvcConstants.ERROR_TEMPLATE;
			} else {
				this.returnUserToTransactionDetailWithError( model, payment, requestorId );
			}

		}

		Long requestorId = this.userService.getCurrentUserId();
		Payment payment = this.paymentService.securedFindPaymentFetchEagerly( paymentId, requestorId );

		// Make sure that only the buyer can input the shipping address
		MessageStatus messageStatus = payment.getMessage().getStatus(); 
		if( this.isAuthorizedToSaveAddress(requestorId, payment, messageStatus) ) {

			if( addressRadio != null ) {
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "Buyer input an address that passed validation. The Payment does not have an " +
									   "associated shipping address and the message is pending a shipping address." );
				}
	
				Map<String, Address> addressChoices = (Map<String, Address>) model.remove( "addressChoices" );
				Address address = addressChoices.get( addressRadio );
				payment = this.paymentService.savePaymentWithAddress( payment, address );
			} else if( existingAddressRadio != null ) {
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "Buyer chose an existing address. The Payment does not have an " +
									   "associated shipping address and the message is pending a shipping address." );
				}
				payment = this.paymentService.savePaymentWithExistingAddress(payment, existingAddressRadio );
			}
				
			redirectAttributes.addAttribute( "addressSaved", "TRUE" );
			
			
		} else {
			if( this.logger.isDebugEnabled() ) {
				if(payment.getShippingAddress() != null) {
					this.logger.debug( "An address already exists for the payment with id: " + paymentId + " and messageId: " + payment.getMessage().getId() );
				} else if( payment.getMessage().getStatus() != MessageStatus.PENDING_SHIPPING_ADDRESS) {
					this.logger.debug( "The messsage: " + payment.getMessage().getId() + " has the unexpected status of: " + payment.getMessage().getStatus() );
				}
			}

			redirectAttributes.addAttribute( "addressSaved", "FALSE" );
		}


		return "redirect:/listing/transactionDetail/" + paymentId;
	}

	/**
	 *
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SELLER')")
	@RequestMapping(value = "/listingHistory/{pageId}", method = RequestMethod.GET )
	public String listingHistory( ModelMap model, @PathVariable Long pageId, Pageable p ) {
		
		Long userId = this.userService.getCurrentUserId();

		Page<Listing> page = this.listingService.findListingsBySellerId(userId, p );
		model.addAttribute( "page", page );
		model.addAttribute( "pageId", pageId );

		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "This pageId=" + pageId );
			this.logger.debug( "Returned page.getNumber()=" + page.getNumber() );
			this.logger.debug( "Total pages:" + page.getTotalPages() );
		}

		return "listing/listingHistory";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/replyActivity/{listingId}/{pageId}", method = RequestMethod.GET )
	public String listingReplyActivity( ModelMap model, @PathVariable Long listingId, @PathVariable Long pageId, Pageable p ) {
		
		Page<Message> page = this.messageService.findReplyActivityByListingId( listingId, p );
		model.addAttribute( "page", page );
		model.addAttribute( "pageId", pageId );

		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "This pageId=" + pageId );
			this.logger.debug( "Returned page.getNumber()=" + page.getNumber() );
			this.logger.debug( "Total pages:" + page.getTotalPages() );
		}
		
		return "listing/replyActivity";
	}

	/**
	 *
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_BUYER','ROLE_SELLER')")
	@RequestMapping(value = "/transactionHistory/{pageId}", method = RequestMethod.GET )
	public String transactionHistory( ModelMap model, @PathVariable Long pageId, Pageable p ) {
		
		Long userId = this.userService.getCurrentUserId();

		Page<Payment> page = this.paymentService.findAllByUserId( userId, p );
		model.addAttribute( "page", page );
		model.addAttribute( "pageId", pageId );

		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "This pageId=" + pageId );
			this.logger.debug( "Returned page.getNumber()=" + page.getNumber() );
			this.logger.debug( "Total pages:" + page.getTotalPages() );
		}

		return "listing/transactionHistory";
	}


	@PreAuthorize("hasRole('ROLE_BUYER')")
	@RequestMapping(value = "/transactionDetail/validateAddressForm.json", method = RequestMethod.POST)
	public @ResponseBody AddressValidationResponse validateAddressForm(
			@ModelAttribute("addressForm") @Valid AddressForm addressForm, 
			BindingResult result,
			ModelMap model,
			Locale locale ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Controller received POST request for listing/validateAddressForm" );
			this.logger.debug( "Address Form: " + ToStringBuilder.reflectionToString( addressForm ) );

			for( ObjectError error : result.getAllErrors() ) {
				this.logger.debug( "Binding errors: " + ToStringBuilder.reflectionToString( error ) );
			}
		}

		AddressValidationResponse response = new AddressValidationResponse();
		if( !result.hasErrors() ) {
			Address userAddress = this.addressFromAddressForm( addressForm );
			try {
				Address uspsAddress = this.addressService.clean( userAddress, locale );
			
			
				if( uspsAddress != null ) {
					
					// tell the UI to show the choice of the input or USPS address
					response.setStatus( "addressValidationSuggestion" );
					
					Map<String, String> userInputAddress = new HashMap<String, String>();
					userInputAddress.put( "type", "user" );
					userInputAddress.put( "compactFormat", userAddress.getCompactFormat() );
					response.setUserAddress( userInputAddress );
					
					Map<String, String> suggestedAddress = new HashMap<String, String>();
					suggestedAddress.put( "type", "suggested" );
					suggestedAddress.put( "compactFormat", uspsAddress.getCompactFormat() );
					response.setUspsAddress( suggestedAddress );
					
					// store the two addresses in the session
					Map<String, Address> addressChoices = new HashMap<String, Address>();
					addressChoices.put( "user", userAddress );
					addressChoices.put( "suggested", uspsAddress );
					model.put( "addressChoices", addressChoices );
					
				} else {
					response.setStatus( "valid" );
				}
			} catch( AddressNotFoundException e ) {
				response.setStatus( "notfound" );
			}
			
		} else {
			// otherwise, invalid
			response.setStatus( "invalid" );
			response.setErrorMessageList( result.getFieldErrors() );
		}

		return response;
	  }
	
	@RequestMapping(value="/listingDetail/{id}/comment", method={RequestMethod.GET} )
	public String listingDetailCommentInstructions( @PathVariable Long id, ModelMap model) {
		
		Long currentUserId = this.userService.getCurrentUserId();
		Boolean isUserLoggedIn = currentUserId != null;
		String listingDetailReferer = (String) ( model.get( "listingDetailReferer") == null ? "unknown" : model.get( "listingDetailReferer") );
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Referer: " + listingDetailReferer );
		}
		
		this.initializeCommentMode(isUserLoggedIn, listingDetailReferer, id, model);

		return "listing/commentInstructions";
	}

	@PreAuthorize("hasRole('ROLE_SELLER')")
	@RequestMapping(value = "/uploadFiles", method = RequestMethod.POST, produces = "application/json" )
	public @ResponseBody
	FileList uploadFiles(MultipartHttpServletRequest request, HttpServletResponse response, ModelMap model) {

		FileList fileList = (FileList) model.get( UPLOADED_FILES );
		
		if( fileList == null ) {
			LinkedList<FileMetadata> files = new LinkedList<FileMetadata>();
			
			fileList = new FileList();
			fileList.setFiles( files );
			
			// add UPLOADED_FILES to the model. Since this is declared a session attribute,
			// this will continue to be available to the user during this session
			model.addAttribute( UPLOADED_FILES, fileList );
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
	
	
	@PreAuthorize("hasRole('ROLE_SELLER')")
    @RequestMapping(value = "/uploadFiles", method = RequestMethod.GET)
    @ResponseBody 
    public FileList list( ModelMap model ) {
        
        FileList fileList = (FileList) model.get( UPLOADED_FILES );
        
        if( this.logger.isDebugEnabled() ) {
        	this.logger.debug( "Returning: {" + fileList +"}");
        }
        
        return fileList;
    }	
	
	
	@PreAuthorize("hasRole('ROLE_SELLER')")
    @RequestMapping(value = "/newListingContentDelete/{id}", method = RequestMethod.DELETE)
    @ResponseBody 
    public Map<String, List<Map<String, Object>>> deleteNewListingContent(@PathVariable Long id, ModelMap model) {
    	
    	FileList fileList = (FileList) model.get( UPLOADED_FILES );
        return this.listingService.deleteUploadedDigitalContent( id, fileList );
    }
    
	
	@PreAuthorize("hasRole('ROLE_SELLER')")
    @RequestMapping(value = "/newListingThumbnail/{id}", method = RequestMethod.GET)
    public void newListingThumbnail(ModelMap model, HttpServletResponse response, @PathVariable Long id) {
    	
    	FileList fileList = (FileList) model.get( UPLOADED_FILES );
		this.listingService.getNewListingContentThumbnail( fileList, id, response );
    }

	@PreAuthorize("hasRole('ROLE_SELLER')")
    @RequestMapping(value = "/newListingContent/{id}", method = RequestMethod.GET)
    public void content(ModelMap model, HttpServletResponse response, @PathVariable Long id) {
    	
    	FileList fileList = (FileList) model.get( UPLOADED_FILES );
		this.listingService.getNewListingContent( fileList, id, response );
    }
	
	
	/**
	 * This method should be a POST but I don't know how that could work off of a barcode.... 
	 * 
	 * @param serialNumber
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_SELLER')")
	@RequestMapping(value="/redeem/{serialNumber}", method=RequestMethod.GET )
	@ResponseBody 
	public String redeemVoucher( @PathVariable String serialNumber ) {
		
		Map<String, Object> result = this.listingService.redeemVoucher( serialNumber );
		Boolean isValid = ( Boolean )result.get( "isValid" );
		String userMessage = ( String )result.get( "userMessage" );
		// TODO add the voucher title here, which should contain a description of the offer (2 for 1 etc.)
		
		// to make this fast and also to keep the UI as clean as possible, just return the HTML directly here
		return String.format( "<!DOCTYPE html><html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\">"
				+ "<head>"
				+ "<meta charset=\"utf-8\" />"
				+ "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\" />"
				+ "<meta name=\"description\" content=\"\" />"
				+ "<meta name=\"viewport\" content=\"initial-scale=1, width=device-width, user-scalable=yes\" />"
				+ "<link href=\"//maxcdn.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css\" rel=\"stylesheet\" />"
				+ "</head>"
				+ "<body><div class=\"container\"><h1>%s</h1><p>%s</p></div></body></html>", isValid, userMessage );
	}
	
	@PreAuthorize("hasRole('ROLE_BUYER')")
	@RequestMapping(value = "/checkout/continueToCheckout.json", method = RequestMethod.POST)
	public @ResponseBody ContinueToCheckoutResponse continueToCheckout( final String product, final Integer quantity, final ModelMap model ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "+++continueToCheckout.json for product: %s, quantity: %d", product, quantity ));
		}

		ContinueToCheckoutResponse response  = new ContinueToCheckoutResponse();
		
		String productCode = null;
		if( StringUtils.isBlank( product )) {
			productCode = Inventory.DEFAULT_PRODUCT_CODE;
		} else {
			productCode = product;
		}
		
		Integer productQuantity = null;
		if( quantity == null ) {
			productQuantity = 1;
		} else {
			productQuantity = quantity;
		}
		
		this.logger.debug( String.format( "Client posted product: %s and quantity: %d", productCode, productQuantity ) );
		
		// Session attributes
		Long listingId = ( Long )model.remove( "listingId" );
		InitiatedPaymentDTO inProgressPayment = null;
		
		if( listingId != null && StringUtils.isNotBlank( productCode ) && productQuantity != null ) {
			
			User buyer = this.userService.getCurrentUser();
			InitiatedPaymentDTO existingInProgressPayment = ( InitiatedPaymentDTO )model.remove( ListingService.IN_PROGRESS_PAYMENT_KEY );
			inProgressPayment = this.listingService.startCheckout( buyer, listingId, productCode, productQuantity, existingInProgressPayment );
			
		} else {
			
			// missing required data
			response.setStatus( "invalid" );
			
		}
		
		if( inProgressPayment != null ) {
			
			if( inProgressPayment.getStartCheckoutErrorReason() == null ) {
				
				/**
				 * Add the inProgressPayment to the user's session
				 */
				model.put( ListingService.IN_PROGRESS_PAYMENT_KEY, inProgressPayment );

				/**
				 * Add attributes to the JSON response
				 */
				
				response.setStatus( "valid" );
				response.setProviderClientToken( inProgressPayment.getProviderClientToken() );
				response.setProductDescription( inProgressPayment.getProductDescription() );
				response.setAmountCents( inProgressPayment.getAmountCents() );
				response.setUserEmail( inProgressPayment.getBuyerEmail() );
				
			} else {
				
				// If the error reason is populated, there was an insufficient quantity available
				response.setStatus( "insufficientQuantity" );
			}
			
		}
		
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "---continueToCheckout.json" ));
		}
		
		return response;
	}
    
	
	/**
	 * Helper Methods
	 */
	
	String doListingDetail( ListingDetailDTO dto, ModelMap model ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Inside doListingDetail: id [" + dto.getListingId() + "], isListingNewlyCreated: [" + dto.getIsListingNewlyCreated() + 
								"], isCheckoutMode: [" + dto.getIsCheckoutMode() + "], referer: [" + dto.getReferrerHttpHeader() + "]" );
		}

		// ensure we have a listing id
		if( dto.getListingId() == null ) {
			model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( this.messageSource, "application.error", dto.getLocale(), UserInterfaceMessageDto.ERROR ) );
			return MvcConstants.ERROR_TEMPLATE;
		}

		// See if the user was redirected from postNewListing upon success, in which case add a message saying
		// that create worked & we will post soon
		if( dto.getIsListingNewlyCreated() ) {
			model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( this.messageSource, "listing.new.form.success.message", dto.getLocale(), UserInterfaceMessageDto.SUCCESS ) );
		}
		
		// remove this attribute from the session & pass to the service (in case the user already has initiated payment for this item)
		dto.setInProgressPayment( ( InitiatedPaymentDTO )model.remove( ListingService.IN_PROGRESS_PAYMENT_KEY ) );
		
		try {
			Map<String, Object> result = this.listingService.doListingDetail( dto );
			
			// ensure we have a listing
			if( result.get( "saleInfo" ) == null ) {
				model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( this.messageSource, "application.error", dto.getLocale(), UserInterfaceMessageDto.ERROR ) );
				return "applicationError";
			}
			
			model.addAllAttributes( result );
			model.put( "listingId", dto.getListingId() );
	
			ListingActionButton button = ( ListingActionButton )result.get( "listingActionButton" );
			String buttonActionMode = button.getActionMode();
			if( buttonActionMode.equals( ListingActionButton.START_CHECKOUT_MODE ) && !dto.getIsCheckoutMode() ) {
				// unusual scenario that a user somehow makes their way to the listing detail page 
				// without following their existing "PENDING_MEANS_OF_PAYMENT" link. Redirect them
				// into the Paypal Checkout flow, even though this will repeat some of the same logic that we've just run
				return String.format( "redirect:/listing/checkout/%d", dto.getListingId() );
			} 
			
			if( buttonActionMode.equals( ListingActionButton.TRANSACTION_DETAIL_MODE ) ) {
				model.put( "paymentId", button.getPaymentId() );
			}
			
		} catch( StartCheckoutFailedException e ) {

			// Something went wrong trying to initiate checkout. could have been paypal communications error or this user may have already purchased the item. 
			model.addAttribute( MvcConstants.UI_MESSAGES_KEY, new UserInterfaceMessageDto( this.messageSource, "sale.detail.initiate.checkout.failed.message", dto.getLocale(), UserInterfaceMessageDto.ERROR ) );
			return "applicationError";
			
		} catch( CheckoutAlreadyCompletedException e ) {
			
			Long paymentId = e.getPaymentId();
			return String.format( "redirect:/listing/transactionDetail/%d", paymentId );
			
		}

		// normal flow here is to the listing detail page
		return "listing/listingDetail";
	}

	Address addressFromAddressForm( AddressForm form ) {
		Address address = new Address();
		address.setFirstLine( form.getFirstLine() );
		address.setSecondLine( form.getSecondLine() );
		address.setThirdLine( form.getThirdLine() );
		address.setCity( form.getCity() );
		address.setState( form.getState() );
		address.setZip( form.getZip() );
		address.setCountry( form.getCountry() );

		return address;
	}
	

	void initializeCommentMode( Boolean isUserLoggedIn, String referrer, Long listingId, ModelMap model) {

		MultiValueMap<String, Connection<?>> connections = null; 
		if( isUserLoggedIn ) {
			// find the user's social provider connections
			connections = this.socialService.findAllCurrentUserConnections();
		}
		
		Listing listing = this.listingService.findById( listingId );
		Set<Message> listingMessages = listing.getMessages();
		
		// Figure out whether to enable Facebook comment mode. If so, add the content link.
		Boolean doEnableFacebookCommentMode = false;
		if( listing.getDoPostToFacebook() ) {
			Message facebookListingMessage = this.getListingMessageForProvider( listingMessages, SocialProviders.FACEBOOK );
			
			if( facebookListingMessage != null ) {
				doEnableFacebookCommentMode = this.doEnableProviderCommentMode( SocialProviders.FACEBOOK, isUserLoggedIn, connections, referrer );
				if( doEnableFacebookCommentMode ) {
					model.addAttribute( "facebookContentLink", facebookListingMessage.getEmbededProviderContent() );
					model.addAttribute( "facebookAppId", this.facebookAppId );
					
					if( this.logger.isDebugEnabled() ) {
						this.logger.debug( "Added facebookContentLink: " + facebookListingMessage.getEmbededProviderContent() );
					}
				}
			}

		}
		
		// Figure out whether to enable Twitter comment mode. If so, add the content link.
		Boolean doEnableTwitterCommentMode = false;
		if( listing.getDoPostToTwitter() ) {
			Message twitterListingMessage = this.getListingMessageForProvider( listingMessages, SocialProviders.TWITTER );
			
			if( twitterListingMessage != null ) {
				doEnableTwitterCommentMode = this.doEnableProviderCommentMode( SocialProviders.TWITTER, isUserLoggedIn, connections, referrer ); 
				if( doEnableTwitterCommentMode ) {
					model.addAttribute( "twitterContentLink", twitterListingMessage.getEmbededProviderContent() );
					if( this.logger.isDebugEnabled() ) {
						this.logger.debug( "Added twitterContentLink: " + twitterListingMessage.getEmbededProviderContent() );
					}
				}
			}

		}
		
		// If one or both of the comment modes is enabled, add the common attributes
		if( doEnableTwitterCommentMode || doEnableFacebookCommentMode ) {
			model.addAttribute( "sellerScreenname", listing.getSeller().getScreenFormattedUsername() );
			model.addAttribute( "keyword", listing.getKeyword() );
			model.addAttribute( "listingId", listing.getId() );
		}
			
	}
	
	/**
	 * This method only makes sense in the context of a listing published
	 * to the given provider. Otherwise, enabling comment mode is illogical.
	 * 
	 * Decides whether to enable the "comment instructions" mode for a given user, listing,
	 * and provider. If the user is referred from a site but has no SocialEQ
	 * connection to that site, we do not enable. Otherwise enable in all cases.
	 * 
	 * 
	 * @param providerId
	 * @param isUserLoggedIn
	 * @param connections
	 * @param referrer
	 * @return whether to enable the commnent instruction mode for this provider
	 */
	Boolean doEnableProviderCommentMode( String providerId,
										 Boolean isUserLoggedIn, 
										 MultiValueMap<String, Connection<?>> connections, 
										 String referrer ) { 

		// Unusual case where a logged in user is referred from a site where no SocialEQ connection exists
		if( this.isReferredByProvider( providerId, referrer ) && isUserLoggedIn && !this.hasConnection( providerId, connections ) ) {
			return false;
		}
		
		// Otherwise enable comment mode
		return true;
	}
	
	Boolean isReferredByProvider( String providerId, String referrer ) {
		return StringUtils.equals( providerId, referrer );
	}
	
	Boolean hasConnection( String providerId, MultiValueMap<String, Connection<?>> connections ) {
		return connections != null && connections.get( providerId ) != null && connections.get( providerId ).size() > 0;
	}
	
	Message getListingMessageForProvider( Set<Message> listingMessages, String provider ) {

		Message result = null;
		
		Iterator<Message> i = listingMessages.iterator();
		while( i.hasNext() ) {
			Message m = i.next();
			if( m.getProviderId().equals( provider ) ) {
				result = m;
				break;
			}
		}
		
		return result;
	}
	
	String returnUserToTransactionDetailWithError( ModelMap model, Payment payment, Long requestorId ) {
		model.addAttribute( "paymentInfo", payment );

		Boolean userIsBuyer = requestorId.equals( payment.getPayer().getId() );
		model.addAttribute( "userIsBuyer", userIsBuyer );
		model.addAttribute( "doesPaymentHaveShippingAddress", false );
		model.addAttribute( "isPendingShipment", false );
		model.addAttribute( "hasBeenShipped", false );

		// add the Country typecode list
		model.addAttribute( "countryList", this.listingService.getCountryList() );

		// unhide the modal
		model.addAttribute( "unhideModal", true );

		return "listing/transactionDetail";
	}

	boolean isAuthorizedToSaveAddress( Long requestorId, Payment payment, MessageStatus messageStatus) {
		return  payment != null  &&
			 payment.getShippingAddress() == null  &&
			  !( messageStatus.equals( MessageStatus.PENDING_SHIPMENT ) || messageStatus.equals( MessageStatus.COMPLETED ) ) &&
			  payment.getPayer().getId().equals( requestorId );
	}
	
	String redirectToCheckoutWithError( InitiatedPaymentDTO inProgressPayment ) {
		
		URIBuilder builder = URIBuilder.fromUri( "redirect:/listing/checkout/" + inProgressPayment.getListingId() );
		
		if( StringUtils.isNotBlank( inProgressPayment.getPaymentErrorReason() )) {
			builder.queryParam( "paymentErrorMessage", inProgressPayment.getPaymentErrorReason() );
		}
		
		return builder.build().toString();
	}
	
}
