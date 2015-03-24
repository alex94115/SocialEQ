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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.security.Security;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jets3t.service.CloudFrontService;
import org.jets3t.service.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.support.URIBuilder;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.MerchantAccount;
import com.braintreegateway.MerchantAccountRequest;
import com.braintreegateway.Result;
import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.DimensionConstrain;
import com.mortennobel.imagescaling.ResampleFilters;
import com.mortennobel.imagescaling.ResampleOp;
import com.projectlaver.domain.Address;
import com.projectlaver.domain.ContentFile;
import com.projectlaver.domain.EffectiveVoucherStatus;
import com.projectlaver.domain.Inventory;
import com.projectlaver.domain.Listing;
import com.projectlaver.domain.Message;
import com.projectlaver.domain.Payment;
import com.projectlaver.domain.User;
import com.projectlaver.domain.Voucher;
import com.projectlaver.integration.SocialProviders;
import com.projectlaver.repository.BulkOperationsRepository;
import com.projectlaver.repository.ListingRepository;
import com.projectlaver.repository.UserRepository;
import com.projectlaver.util.CheckoutAlreadyCompletedException;
import com.projectlaver.util.FileList;
import com.projectlaver.util.FileMetadata;
import com.projectlaver.util.GoodsType;
import com.projectlaver.util.InitiatedPaymentDTO;
import com.projectlaver.util.ListingActionButton;
import com.projectlaver.util.ListingDetailDTO;
import com.projectlaver.util.ListingType;
import com.projectlaver.util.MessageStatus;
import com.projectlaver.util.StartCheckoutFailedException;
import com.projectlaver.util.VoucherStatus;

@Service
@Transactional(readOnly = false)
public class ListingService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private BulkOperationsRepository bulkMessageOperationsRepository;

	@Autowired
	private ListingRepository listingRepository;

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private PaymentProviderService paymentProviderService;

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private UsersConnectionRepository usersConnectionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SocialService socialService;
	
	@Autowired
    private org.springframework.context.ApplicationContext springContext;
	
	@Autowired
	BraintreeGateway braintreeGateway;
	
	private final Log logger = LogFactory.getLog(getClass());
	
	private byte[] derPrivateKey = new byte[0]; 

	// properties
	
	@Value("${aws.s3.access.key}")
	private String s3accessKey;

	@Value("${aws.s3.secret.key}")
	private String s3secretKey;

	@Value("${aws.s3.private.bucket.name}")
	private String s3privateBucketName;

	@Value("${aws.s3.public.bucket.name}")
	private String s3publicBucketName;

	@Value("${aws.cloudfront.distributionDomain}")
	private String cloudfrontDistributionDomain;
	
	@Value("${aws.cloudfront.privateKeyFilePath}")
	private String cloudfrontPrivateKeyFilePath;
	
	@Value("${aws.cloudfront.keyPairId}")
	private String cloudfrontKeyPairId;
	
	@Value("${aws.cloudfront.signedUrlValidMinutes}")
	private Integer cloudfrontSignedUrlValidMinutes;
	
	@Value("${paypalPayment.checkoutUrl}")
	private String checkoutUrl;
	
	@Value("${braintree.masterMerchantAccountId}")
	private String masterMerchantAccountId;


	/**
	 * Static variables
	 */
	
	private static Map<String, String> countryList = new HashMap<String, String>();
	
	private static final Integer MAX_FILES_PER_LISTING = 25;
	
	public static final String IN_PROGRESS_PAYMENT_KEY = "inProgressPayment";

	/**
	 * Constructor
	 */
	public ListingService() {
		// Add the bouncy castle provider so that it exists when we need to sign URL's for cloudfront
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	/**
	 * Public methods
	 */
	
	@Transactional(readOnly = false)
	public Listing create( Listing listing ) throws Exception {

		// set the expiration for one year from now if unset
		if( listing.getExpires() == null ) {
			listing.setExpires( this.addDays( new Date(), 365 ));
		}

		// merge the user (reattach to DB)
		User user = listing.getSeller();
		User mergedUser = this.em.merge( user );
		listing.setSeller( mergedUser);

		if( listing.getImageAsFile() != null ) {
			// upload the image preview to the public S3 bucket
			AWSCredentials myCredentials = new BasicAWSCredentials( this.s3accessKey, this.s3secretKey );
			TransferManager tx = new TransferManager( myCredentials );
		    Upload myUpload = tx.upload( this.s3publicBucketName, listing.getImageFilename(), listing.getImageAsFile() );
		    myUpload.waitForCompletion();
		}

		if( listing.getContentFiles() != null && listing.getContentFiles().size() > 0 ) {
			
			Set<ContentFile> contentFiles = listing.getContentFiles();

			AWSCredentials myCredentials = new BasicAWSCredentials( this.s3accessKey, this.s3secretKey );
			TransferManager tx = new TransferManager( myCredentials );
			
			for( ContentFile file : contentFiles ) {
				// upload the digital content to the private S3 bucket
			    Upload myUpload = tx.upload( this.s3privateBucketName, file.getContentFilename(), file.getDigitalContentAsFile() );
			    myUpload.waitForCompletion();
			}
		}

		return this.listingRepository.save( listing );
	}
	

	@Transactional(readOnly = true)
	@Cacheable(value="listings")
	public Listing findById( Long id ) {
		Listing listing = this.listingRepository.findByListingIdEagerlyFetchContentAndMessage(id); 
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "findById for id: " + id + " returned this listing: " + listing );
		}
		
		return listing;
	}
	
	@Transactional(readOnly = true)
	public Listing findByIdBypassCache( Long id ) {
		Listing listing = this.listingRepository.findByListingIdEagerlyFetchContentAndMessage(id); 
		
		if( this.logger.isDebugEnabled() && listing != null ) {
				
			String logInventory = null;
			List<Inventory> inventoryList = listing.getInventories();
			
			if( inventoryList == null ) {
				logInventory = "unlimited";
			} else {
				for( Inventory inventory : inventoryList ) {
					logInventory = logInventory + String.format( " productCode: %s remainingQuantity: %d totalQuantity: %d", 
							inventory.getProductCode(), inventory.getRemainingQuantity(), inventory.getQuantity() );
				}
			}
			
			this.logger.debug( String.format( "findByIdBypassCache for id: %d returned listing type: %s, subType: %s with inventory: %s", 
					listing.getId(), listing.getType(), listing.getSubType(), logInventory ));
		}
		
		return listing;
	}

	/**
	 *
	 * Authorizes that the requestor is entitled to the content by checking to see if the user has bought the item
	 * or is the item's seller.
	 *
	 * Writes the requested digital content to the OutputStream parameter if the requestor is authorized.
	 *
	 * @param requestor
	 * @param contentFilename
	 * @param outputStream
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void streamPurchasedContent( User requestor, String contentFilename, HttpServletResponse response, OutputStream outputStream ) throws Exception {

		Boolean isAuthorized = false;

		Payment payment = this.paymentService.findByStatusAndBuyerAndContentFilename(
				requestor.getId(), contentFilename, this.paymentService.getValidPaymentStatuses() );
		
		Listing listing = null;
		if( payment.getListing() == null ) {
			listing = this.listingRepository.findBySellerByUsernameAndContentFilename( requestor.getUsername(), contentFilename );
			
			if( listing != null ) {
				// The requestor is the seller
				isAuthorized = true;
			}
		} else  {
			// The requestor is a buyer
			listing = payment.getListing();
			isAuthorized = true;
		}

		ContentFile theFile = null;
		if( isAuthorized ) {
			// This is an authorized download, so stream the content
			Set<ContentFile> files = listing.getContentFiles();
			
			for( ContentFile file : files ) {
				if( file.getContentFilename().equals( contentFilename )) {
					theFile = file;
					break;
				}
			}
		}
			
		if( theFile == null ) { 
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Request for contentFilename: " + contentFilename + " was rejected because requestor " + requestor.getId() + " could not be authorized." );
			}
			response.setStatus( HttpServletResponse.SC_NOT_FOUND );
		} else {
			this.setDownloadHeaders( theFile, contentFilename, response );
			try {
				this.streamAwsContentToResponse( contentFilename, this.s3privateBucketName, outputStream );
				// Persist the download instance so we can count / calculate metrics on it
				this.bulkMessageOperationsRepository.insertDownloadInstance( requestor.getId(), listing.getId(), theFile.getId(), ( payment == null ? null : payment.getId() ) );
			} catch( IOException e ) {
				this.logger.error( "Caught an IOException while trying to stream: " + contentFilename + " with requesting user: " + requestor.getUsername(), e );
			}
		}
	}

	@Transactional(readOnly = true)
	public void streamImage( String contentFilename, OutputStream outputStream ) throws Exception {
		this.streamAwsContentToResponse( contentFilename, this.s3publicBucketName, outputStream );
	}
	
	/**
	 * Looks for listings that this user has expressed an interest in (i.e., has mentioned the hashtag) but
	 * where the message is stuck in "PENDING_MEANS_OF_PAYMENT" status
	 * 
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Long> findUserPendingMeansOfPaymentListingIds( Long userId ) {
		return this.bulkMessageOperationsRepository.findUserPendingMeansOfPaymentListingIds( userId );
	}
	
	@Transactional(readOnly = true)
	public FileList handleDigitalContentUpload( FileList fileList, LinkedList<MultipartFile> multipartFiles ) {
		
		List<FileMetadata> files = fileList.getFiles();
		for( MultipartFile mpf : multipartFiles ) {
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( mpf.getOriginalFilename() + " uploaded! " + files.size() );
			}
	
			// limit max files per listing
			if (files.size() >= MAX_FILES_PER_LISTING ) {
				throw new RuntimeException( "Exceeded max files per listing with size: " + files.size() );
			}
	
			// create new fileMeta
			FileMetadata fileMeta = new FileMetadata();
			fileMeta.setName( mpf.getOriginalFilename() );
			fileMeta.setAwsFilename( this.createFilename( mpf.getOriginalFilename() ));
			fileMeta.setId( Long.valueOf( RandomStringUtils.randomNumeric( 2 ) ) );
			fileMeta.setContentType( mpf.getContentType() );
			fileMeta.setSize( mpf.getSize() );
			fileMeta.setType( mpf.getContentType() );
			
			if( mpf.getName().equals( "image" )) {
				
				fileMeta.setIsCampaignImage( true );
				
				// there can't be two campaign images for a listing -- if another one exists, error
				for( int i = 0; i < files.size(); i++ ) {
					FileMetadata otherMeta = files.get( i );
					if( otherMeta.getIsCampaignImage() ) {
						throw new RuntimeException( "Multiple listing images are not allowed." );
					}
				}
			}
	
			String tempDir = System.getProperty( "java.io.tmpdir" );
			String transientPath = tempDir + fileMeta.getAwsFilename();
			File thisFile = new File( transientPath );
			fileMeta.setTransientPath( transientPath );
			
			try {
				// copy file to local disk
				mpf.transferTo( thisFile );
				
				// create a thumbnail version
				Boolean isImage = fileMeta.getContentType().indexOf( "image" ) != -1;
				if( isImage ) {
				
					BufferedImage sourceImage = ImageIO.read( thisFile );
										
					ResampleOp resampleOp = new ResampleOp( DimensionConstrain.createMaxDimension( 100, 100, true ) );
					resampleOp.setFilter( ResampleFilters.getLanczos3Filter() );
					resampleOp.setUnsharpenMask( AdvancedResizeOp.UnsharpenMask.Normal );
					BufferedImage destImage = resampleOp.filter( sourceImage, null );
					
					ImageWriter writer = null;
				    FileImageOutputStream output = null;
					try {
				        writer = ImageIO.getImageWritersByFormatName("png").next();
				        ImageWriteParam param = writer.getDefaultWriteParam();
				        String thumbnailFilename = this.getThumbnailFilename( fileMeta.getAwsFilename() );
				        output = new FileImageOutputStream(new File( tempDir + thumbnailFilename ));
				        writer.setOutput(output);
				        IIOImage iioImage = new IIOImage( destImage, null, null );
				        writer.write(null, iioImage, param);
				    } catch (IOException ex) {
				        throw ex;
				    } finally {
				        if (writer != null) {
				        	writer.dispose();
				        }
				        if (output != null) {
				        	output.close();
				        }
				    }
					
	                fileMeta.setThumbnailUrl( "newListingThumbnail/" + fileMeta.getId() );
	    			fileMeta.setDeleteType( "DELETE" );
	    			fileMeta.setDeleteUrl( "newListingContentDelete/" + fileMeta.getId() );
				}
				fileMeta.setUrl( "newListingContent/" + fileMeta.getId() );
	
			} catch (IOException e) {
				throw new RuntimeException( "Error occured while handling the multipart file.", e );
			}
			
			// 2.4 add to files at the head
			files.add(0, fileMeta);
		}
		
		return fileList;
	}
	
	@Transactional(readOnly = true)
	public void getNewListingContent( FileList fileList, Long id, HttpServletResponse response ) {
		if( fileList != null ) {
			
			List<FileMetadata> files = fileList.getFiles();
			for( FileMetadata meta: files ) {
				if( meta.getId().equals( id ) ) {
					
					String contentFilename = meta.getAwsFilename();
					this.streamFileToResponse( contentFilename, meta.getContentType(), meta.getSize().intValue(), response );
					
					break;
				}
			}
		}
	}
	
	@Transactional(readOnly = true)
	public void getNewListingContentThumbnail( FileList fileList, Long id, HttpServletResponse response ) {
		
		if( fileList != null ) {
		
			List<FileMetadata> files = fileList.getFiles();
			for( FileMetadata meta: files ) {
				if( meta.getId().equals( id ) ) {
					
					String thumbnailFilename = this.getThumbnailFilename( meta.getAwsFilename() );
					this.streamFileToResponse( thumbnailFilename, meta.getContentType(), meta.getSize().intValue(), response );
					
					break;
				}
			}
		}
	}
	

	@Transactional(readOnly = true)
	public Map<String, List<Map<String, Object>>> deleteUploadedDigitalContent( Long id, FileList fileList ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug("Delete uploaded file: " + id + " called." );
		}
    	
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();    	
		Map<String, List<Map<String,Object>>> results = new HashMap<String, List<Map<String,Object>>>();
		results.put("files", result );
		
		List<FileMetadata> files = fileList.getFiles();
		for( int i = 0; i < files.size(); i++ ) {
    		
    		FileMetadata fileMeta = files.get( i );
    		if( fileMeta.getId().equals( id ) ) {
    	
    			// Delete the corresponding temp files
    			String tempDir = System.getProperty( "java.io.tmpdir" );
				String thumbnailFilename = this.getThumbnailFilename( fileMeta.getAwsFilename() );
				File thumbnail = new File( tempDir + thumbnailFilename );
				thumbnail.delete();
				File file = new File( tempDir + fileMeta.getAwsFilename() );
				file.delete();

				// remove the deleted file from the fileList
				files.remove( i );
		        
		        Map<String, Object> success = new HashMap<String, Object>();
		        success.put(fileMeta.getName(), true);
		        result.add(success);
		        break;
    		}
    	}
        return results;
	}
	
	@Transactional(readOnly = true)
	public Page<Listing> findListingsBySellerId( Long userId, Pageable p ) {
		return this.listingRepository.findListingsBySellerId( userId, p );
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> doListingDetail( ListingDetailDTO dto ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "Listing Service doListingDetail for id: %d", dto.getListingId() ) );
		}

		Map<String, Object> result = new HashMap<String, Object>();
		
		// find the listing by id and add to model
		Listing listing = null;
		
		if( dto.getIsCheckoutMode() ) {
			// if we're in checkout mode, load directly from the database so we don't get a stale status / quantity
			listing = this.findByIdBypassCache( dto.getListingId() );
		} else {
			// if we're not in checkout mode, a slightly stale status / quantity isn't a show stopper
			listing = this.findById( dto.getListingId() );
		}
		
		
		if( listing != null ) {
			
			result.put( "saleInfo", listing );
			
			if( listing.getType().equals( ListingType.CAMPAIGN ) ) {
				result.put( "isDigitalGiveaway", listing.getGoodsType().equals( GoodsType.DIGITAL ) );
			}
			
			result.putAll( this.addSellerProfileAttributesToModel( listing ) );
			
			// get the listing detail action button back here
			ListingActionButton button = this.createListingActionButton( listing, dto );
			
			result.put( "listingActionButton", button );
			result.put( "actionButtonText", button.getText() );
			result.put( "isActionButtonActive", button.isEnabled() );
			
			// if the button is active, setup the action for one of: login / comment / download
			String buttonActionMode = button.getActionMode();
			
			// make the current referrer available to the listingDetail page
			String referrer = this.identifyListingDetailReferer( dto.getReferrerHttpHeader() );
			result.put( "listingDetailReferer", referrer );
			
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Setting listingDetailReferer: " + referrer );
			}
			
			result.put( "buttonActionMode", buttonActionMode );
			
			// Add the days, hours, minutes, and seconds remaining
			result.putAll( this.addTimeRemainingAttributes( listing ) );

		}

		return result;
	}


	/**
	 * Checks to see if the (presumed) seller has the right configuration to list on
	 * various sites. For Twitter, just need a connection. For Facebook, need to 
	 * have the various album / page id's as well as a page url and a valid
	 * token.
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> getSellerEnabledListingProviders() {
		User seller = this.userService.getCurrentUser();
		Boolean enableTwitterListings = false;
		String twitterDisplayName = null;
		Boolean enableFacebookListings = false;

		// if the user doesn't have an email, cannot create a listing
		if( StringUtils.isNotBlank( seller.getEmailAddress() )) {
			
			// check to see if this seller is in the current initialized twitter stream
			enableTwitterListings = this.bulkMessageOperationsRepository.isInCurrentTwitterStream( seller );
			if( enableTwitterListings ) {
				Connection<Twitter> twitterConnection = this.socialService.getCurrentUserSocialConnection( Twitter.class );
				twitterDisplayName = twitterConnection.getDisplayName();
			}
			
			// first ensure the user has their facebook metadata configured
			if( StringUtils.isNoneBlank( seller.getFacebookAlbumId(), seller.getFacebookPageId(), seller.getFacebookPageUrl() ) ) {

				Connection<Facebook> facebookConnection = this.socialService.getCurrentUserSocialConnection( Facebook.class );
				if( facebookConnection != null ) {
				
					// Confirm with facebook that this user has granted the manage_pages permissions
					enableFacebookListings = this.socialService.confirmCurrentUserFacebookPermissions( "manage_pages", "publish_actions" );
				}
			}
		}
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put( "doEnableTwitter", true );
		result.put( "twitterDisplayName", twitterDisplayName );
		
		result.put( "doEnableFacebook", enableFacebookListings );
		
		return result;
	}
	
	
	@Transactional(readOnly = true)
	public Map<String, Object> getTransactionDetails( Long transactionId ) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Long requestorId = this.userService.getCurrentUserId();

		// only allow viewing the Payment details if the requestor is the payer or the payee
		Payment payment = this.paymentService.securedFindPaymentFetchEagerly( transactionId, requestorId );
		if( payment != null ) {

			Message buyingMessage = payment.getMessage();

			result.put( "paymentInfo", payment );

			Boolean userIsBuyer = requestorId.equals( payment.getPayer().getId() );
			result.put( "userIsBuyer", userIsBuyer );
			
			if( userIsBuyer ) {
				
				// If this is the buyer, add the links to Follow / Like
				User seller = payment.getListing().getSeller();
				String sellerUsername = seller.getUsername();
				ConnectionRepository connectionRepository = this.usersConnectionRepository.createConnectionRepository( sellerUsername );
				
				Connection<Twitter> twitterConnection = null;
				try {
					twitterConnection = connectionRepository.getPrimaryConnection( Twitter.class );
					if( twitterConnection != null ) {
						result.put( "sellerTwitterProfileUrl", twitterConnection.getProfileUrl() );
					}
					
				} catch ( NotConnectedException e ) {
					// not fatal, just carry on without the twitter profile url link 
				}
				
				String sellerFacebookPageUrl = seller.getFacebookPageUrl();
				if( sellerFacebookPageUrl != null ) {
					result.put( "sellerFacebookPageUrl", sellerFacebookPageUrl );
				}
			}

			result.put( "userIsSeller", requestorId.equals( payment.getPayee().getId() ) );

			// logic depending on whether it's a digital or physical campaign
			GoodsType goodsType = payment.getListing().getGoodsType();
			
			//if( this.doesListingHavePhysicalGoods( payment, listingType ) ) {
			if( goodsType.equals( GoodsType.PHYSICAL ) ) {
				
				// Do stuff related to physical campaigns
				result.put( "listingIsPhysical", true  );
				result.put( "listingIsDigital", false );
				result.put( "listingIsVoucher", false );
				
				if( payment.getShippingAddress() == null ) {
					// no address set
					
					Set<Address> addresses = payment.getPayer().getAddresses(); 
					if( addresses != null && !addresses.isEmpty() ) {
						this.logger.debug( "Payment with id: " + transactionId + " does not have an associated address, but this user has existing addresses." );
						
						// add the buyer's addresses to the result so they can be displayed
						result.put( "buyerAddresses", addresses );
						
					} else {
						this.logger.debug( "Payment does not have an address, nor does the buyer have any existing addresses." );
					}
					result.put( "doesPaymentHaveShippingAddress", false );
					result.put( "isPendingShipment", false );
					result.put( "hasBeenShipped", false );
					
				} else {
					// this payment has an address set
					
					this.logger.debug( "Payment with id: " + transactionId + " is already associated with address: " + payment.getShippingAddress().getId() );
					result.put( "doesPaymentHaveShippingAddress", true );
					result.put( "isPendingShipment", buyingMessage.getStatus().equals( MessageStatus.PENDING_SHIPMENT ) );
					result.put( "hasBeenShipped", ( payment.getHasBeenShipped() !=null ? payment.getHasBeenShipped() : false ) );
				}
				
				// add Country typecode list
				result.put( "countryList", this.getCountryList() );
				
			} else if( goodsType.equals( GoodsType.DIGITAL ) ) {
				// Do stuff related to digital campaigns
				result.put( "listingIsDigital", true );
				result.put( "listingIsPhysical", false );
				result.put( "listingIsVoucher", false );
				
				Set<ContentFile> contentFiles = payment.getListing().getContentFiles();
				for( ContentFile file : contentFiles ) {
					
					// this is the URL for viewing the content inline
					file.setCloudFrontUrl( this.createSignedUrl( file.getContentFilename(), null ) );
					
					// this is the URL for downloading the content
					file.setCloudFrontDownloadableUrl( this.createSignedUrl( file.getContentFilename(), file.getDigitalContentOriginalFilename() ));
				}
				
			} else {
				// voucher listing
				result.put( "listingIsVoucher", true );
				result.put( "listingIsPhysical", false );
				result.put( "listingIsDigital", false );
				
				List<Voucher> vouchers = payment.getVouchers();
				if( vouchers != null && userIsBuyer ) {
					
					for( Voucher voucher : vouchers ) {

						EffectiveVoucherStatus evs = voucher.getCurrentEffectiveVoucherStatus();
						if( evs.getStatus().equals( VoucherStatus.UNREDEEMED ) ) {
						
							/**
							 *  The voucher barcode image is stored in the private S3 bucket. Create a signed
							 *  URL for the user to access it.
							 *  
							 *  Note that even though the content itself is not heavy in this case, it's still
							 *  preferable to route them through Cloudfront rather than having them go through
							 *  our secured ListingController method for private content, since that re-authorizes
							 *  the user every time (by going to the DB).
							 */
							voucher.setCloudFrontUrl( this.createSignedUrl( voucher.getFilename(), null ) );
						}
					}
				}
				
			}
		} else {
			throw new AccessDeniedException( String.format( "User with id: %d requested transaction details for a transaction to which this user is not a party. Transaction id: %d", requestorId, transactionId ) );
		}

		return result; 
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Map<String, String> getCountryList() {
		
		synchronized( ListingService.countryList ) {
			if( ListingService.countryList.isEmpty() ) {
				ListingService.countryList = (Map<String, String>) this.springContext.getBean( "countryList" );
			}
			return ListingService.countryList;
		}
	}
	
	@Transactional(readOnly=false) 
	public Boolean markPaymentAsShipped( Long paymentId ) {
	
		Boolean result = null;

		Long sellerId = this.userService.getCurrentUserId();
		Payment payment = this.paymentService.securedFindPaymentFetchEagerly( paymentId, sellerId );

		if( payment != null && payment.getPayee().getId().equals( sellerId ) ) {

			// Make sure it's not already marked "has been shipped"
			if( payment.getMessage().getStatus().equals( MessageStatus.PENDING_SHIPMENT ) && 
				(payment.getHasBeenShipped() == null) || !payment.getHasBeenShipped() ) {
					
				payment.setHasBeenShipped( true );

				// persist the updated payment
				this.paymentService.save( payment );

				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "Persisted the payment: " + paymentId + " hasBeenShipped value to true (aka this item has been shipped)" );
				}

				// reset the message's batch processor to null so the next batch job run will pick it up again
				Message message = this.messageService.findByPaymentId( payment.getId() );
				message.setBatchProcessor( null );
				this.messageService.update( message );
				
				result = true;
				
			} else {

				// this is the seller but it's either not pending shipment or it's already been shipped
				result = false;
			}
		} else {
			// throw Exception - unexpected condition
			throw new RuntimeException( "Unexpected condition in markPaymentAsShipped. payment: " + payment + ", caller userId: " + sellerId );
		}
		
		return result;
	}
	
	@Transactional(readOnly=false)
	public Map<String, Object> redeemVoucher( String serialNumber ) {
		
		Long sellerId = this.userService.getCurrentUserId();
		return this.paymentService.redeemVoucher( sellerId, serialNumber );
				
	}
	
	@Transactional(readOnly=false)
	public InitiatedPaymentDTO startCheckout( User buyer, Long listingId, String productCode, Integer quantity, InitiatedPaymentDTO existingInitiatedPayment ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "+++startCheckout for buyer id: %d, listing id: %d, and existingInProgressPayment: %s", 
					buyer.getId(), listingId, ToStringBuilder.reflectionToString( existingInitiatedPayment )));
		}
		
		Listing listing = this.findByIdBypassCache( listingId );
		User seller = listing.getSeller();
		
		InitiatedPaymentDTO initiatedPayment = null;
		if( existingInitiatedPayment != null && existingInitiatedPayment.hasRemainingValidity() ) {
			
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Re-using existing initiatedPayment object." );
			}
			
			// use the existingInitiatedPayment
			initiatedPayment = existingInitiatedPayment;
			
		} else {

			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Creating a new initiatedPayment object." );
			}
			
			// Either the existingInitiatedPayment is null or it has no remaining validity. 
			// Use the payapalService to start a new checkout.
			initiatedPayment = this.paymentProviderService.startCheckout( buyer, seller, listing, productCode, quantity );
			
			// add a few extra parameters
			initiatedPayment.setProductDescription( listing.getTitle() );
			initiatedPayment.setAmountCents( listing.getAmountCents() );
			initiatedPayment.setBuyerEmail( buyer.getEmailAddress() );
		}
		
		if( initiatedPayment == null ||
			!initiatedPayment.getDidStartCheckoutSucceed() ||  
			initiatedPayment.getStartCheckoutErrorReason() != null ) { // error reason indicates that there was an insufficient quantity errors
			
			// could not initiate payment
			throw new StartCheckoutFailedException( String.format( "Could not start checkout for buyer id: %d and listing id: %d.", buyer.getId(), listing.getId() ) );
			
		}
		
		return initiatedPayment;
	}
	
	@Transactional(readOnly=true)
	public Boolean doesListingExist( Long id ) {
		
		return this.bulkMessageOperationsRepository.doesListingExist( id );
				
	}
	
	@Transactional(readOnly=true)
	public Set<Listing> findActiveFacebookListings() {
		
		return this.listingRepository.findActiveFacebookListings();
		
	}
	
	@Transactional(readOnly=true)
	public Boolean createBraintreeSubMerchant( Long sellerId ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "Attempting to create a braintree sub merchant for seller id: %d", sellerId ));
		}
		
		// Lookup the user and fetch addresses
		User seller = this.userRepository.findUserEagerlyFetchAddresses( sellerId );
		
		Set<Address> addresses = seller.getAddresses();
		Address address = addresses.iterator().next();
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "Registering the new seller with address: %s", address.getCompactFormat() ));
		}
		
		// append address line 1 to line 2, if exists
		String streetAddress = address.getSecondLine();
		if( StringUtils.isNotBlank( address.getFirstLine() ) ) {
			streetAddress = streetAddress.concat( address.getFirstLine() );
		}
		
		MerchantAccountRequest request = new MerchantAccountRequest();
		
		request
			.individual()
				.firstName( seller.getFirstName() )
				.lastName( seller.getLastName() )
				.email( seller.getEmailAddress() )
				.phone( seller.getMobileNumber() )
				.dateOfBirth( seller.getDob() )
				.address()
					.streetAddress( streetAddress )
					.locality( address.getCity() )
					.region( address.getState() )
					.postalCode( address.getZip() )
					.done()
				.done()
			.funding()
				.destination( MerchantAccount.FundingDestination.EMAIL )
				.email( seller.getEmailAddress() )
				.done()
			.tosAccepted( true )
			.masterMerchantAccountId( this.masterMerchantAccountId )
			.id( seller.getId().toString() );
		
		// Attempt to create the sub merchant but trap any exceptions
		try {
			Result<MerchantAccount> result = this.braintreeGateway.merchantAccount().create( request );
			
			if( result.isSuccess() ) {
			
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "Braintree sub merchant registration succeeded." );
				}
				return true;
				
			} else {
				
				this.logger.error( String.format( "Unable to register the user as a sub merchant with Braintree. Error message: %s", result.getMessage() ));
				return false;
			}
			
			
		} catch( RuntimeException e ) {
			this.logger.error( "Error while attempting to create the Braintree sub merchant.", e );
			return false;
		}
			
	}
	
	/**
	 * Internal methods 
	 */

	void streamFileToResponse( String filename, String contentType, int contentLength, HttpServletResponse response ) {
		String tempDir = System.getProperty( "java.io.tmpdir" );
		
		File image = new File( tempDir + filename );

		// this wasn't working right, so I killed it. 
		// see this response if it become necessary to fix: http://stackoverflow.com/a/8797893/1325237
		// response.setContentLength( contentLength );
		response.setContentType( contentType );
		
		try {
			InputStream is = new FileInputStream( image );
			FileCopyUtils.copy( is, response.getOutputStream() );
		} catch( IOException e ) {
			this.logger.error( "Could not write thumbnail: " + filename , e );
		}
	}


	void streamAwsContentToResponse( String contentFilename, String bucketName, OutputStream outputStream ) throws IOException {
		AWSCredentials myCredentials = new BasicAWSCredentials( this.s3accessKey, this.s3secretKey );
		AmazonS3 s3  = new AmazonS3Client( myCredentials );
		S3Object object = s3.getObject( bucketName, contentFilename );

		FileCopyUtils.copy( object.getObjectContent(), outputStream );
	}

	void setDownloadHeaders( ContentFile file, String contentFilename, HttpServletResponse response ) {

		// Determine the content type. Default to the saved type, but guess if necessary.
		String contentType = file.getDigitalContentType();
		if( contentType == null ) {
			contentType = URLConnection.guessContentTypeFromName( contentFilename );
		}

		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "For the requested filename: " + contentFilename + ", setting the response header content type: " + contentType );
		}
		response.setContentType( contentType );
		response.setHeader("Content-disposition", "attachment; filename=" + file.getDigitalContentOriginalFilename() );
	}

	Date addDays( Date date, int days ) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add( Calendar.DATE, days ); //a negative number goes to a past date
        return cal.getTime();
    }
	
	String createFilename( String originalFilename ) {
		String contentFileExtension = originalFilename.substring( originalFilename.lastIndexOf( "." ) + 1 );
		return String.format("%s.%s", RandomStringUtils.randomAlphanumeric(20), contentFileExtension);
	}
	
	String getThumbnailFilename( String awsFilename ) {
		return awsFilename.substring( 0, awsFilename.lastIndexOf( "." ) ) + "-thumbnail.png";
	}
	
	Map<String, Object> addSellerProfileAttributesToModel( Listing listing ) {
		Map<String, Object> result = new HashMap<String, Object>();
 		
		// set seller attributes
		ConnectionRepository sellerConnectionRepository = this.usersConnectionRepository.createConnectionRepository( listing.getSeller().getUsername() );
		Connection sellerConnection = null;
		if( listing.getDoPostToTwitter() ) {
			sellerConnection = sellerConnectionRepository.findPrimaryConnection( Twitter.class );
		} else {
			// must be Facebook
			sellerConnection = sellerConnectionRepository.findPrimaryConnection( Facebook.class );
		}
		
		result.put( "sellerProfileImageUrl", sellerConnection.getImageUrl() );
		result.put( "sellerProfileUrl", sellerConnection.getProfileUrl() );
		result.put( "sellerFacebookPageUrl", listing.getSeller().getFacebookPageUrl() );
		

		if( StringUtils.isNotBlank( listing.getBackgroundImageUrl() ) ) {
			result.put( "backgroundImageUrl", listing.getBackgroundImageUrl() );
		}

		return result;
	}
	
	String identifyListingDetailReferer( String referrer ) {
		String referrerProviderId = null;
		
		Boolean referredByTwitter = StringUtils.contains( referrer, "https://t.co" );
		if( referredByTwitter ) {
			referrerProviderId = SocialProviders.TWITTER;
		}
		
		Boolean referredByFacebook = StringUtils.contains( referrer, "https://www.facebook.com" );
		if( referredByFacebook ) {
			referrerProviderId = SocialProviders.FACEBOOK;
		}
		
		Boolean referredByPaypal = StringUtils.contains( referrer, "https://ic.paypal.com" );
		if( referredByPaypal ) {
			referrerProviderId = "paypal";
		}
		
		if( referrerProviderId == null ) {
			referrerProviderId = "unknown";
		}
		
		return referrerProviderId;
	}
	
	/**
	 * Calculate the days, hours, minutes, and seconds before a listing expires.
	 */
	Map<String, Object> addTimeRemainingAttributes( Listing listing ) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Date now = new Date();
		Date end = listing.getExpires();

		long remainingMillis = end.getTime() - now.getTime();
		long secondsPart = (remainingMillis / 1000) % 60;
		long minutesPart = (remainingMillis / (60 * 1000)) % 60;
		long hoursPart = (remainingMillis / (60 * 60 * 1000)) % 24;
		long daysPart = remainingMillis / (24 * 60 * 60 * 1000);

		result.put( "remainingDays", ( daysPart >= 0 ? daysPart : 0 ) );
		result.put( "remainingHours", ( hoursPart >= 0 ? hoursPart : 0 ) );
		result.put( "remainingMinutes", ( minutesPart >= 0 ? minutesPart : 0 ) );
		result.put( "remainingSeconds", ( secondsPart >= 0 ? secondsPart : 0 ) );
		
		return result;
	}
	
	ListingActionButton createListingActionButton( Listing listing, ListingDetailDTO dto ) { 
		
		Long currentUserId = this.userService.getCurrentUserId();
		Boolean isUserLoggedIn = currentUserId != null;
		Boolean isUserLister = isUserLoggedIn && currentUserId.equals( listing.getSeller().getId() );
		
		// Setup the action button text and action
		ListingActionButton button = new ListingActionButton();
		button.setListing( listing );
		button.setIsUserLoggedIn( isUserLoggedIn );
		button.setIsUserLister( isUserLister );
		button.setMessageSource( this.messageSource );
		button.setLocale( dto.getLocale() );
		
		// Add extended information for logged in users who are not the item's lister
		if( isUserLoggedIn && !isUserLister ) {
		
			// has the user paid for this item?
			Payment payment = this.paymentService.findValidPaymentByListingIdAndBuyerId( listing.getId(), currentUserId );
			Boolean hasPaidForThisItem = payment != null;
			button.setHasUserPaidForThisItem( hasPaidForThisItem );
			
			// halt a user who has bought an item & then goes into checkout mode
			if( hasPaidForThisItem && dto.getIsCheckoutMode() ) {
				throw new CheckoutAlreadyCompletedException( String.format( 
						"User with id: %d has already purchased listing with id: %d, so halting checkout.", currentUserId, listing.getId() ), payment.getId() );
			} else if( hasPaidForThisItem ) {

				button.setPaymentId( payment.getId() );
				
			} else { // has not (yet) paid for this item
				
				// if this is a 'selling' listing, does the user have a "pending means of payment" or "failed payment" message?
				if( listing.getType().equals( ListingType.SELLING ) ) {
					Message pendingMeansOfPaymentMessage = this.messageService.findPendingMeansOfPaymentMessage( listing.getId(), currentUserId );
					button.setDoesUserHavePendingPurchase( pendingMeansOfPaymentMessage != null );
				}
			}
			
		}
		
		return button;
	}
	
	
	Boolean shouldStartCheckout( ListingDetailDTO dto, Listing listing, ListingActionButton button ) {
		
		if( !dto.getIsCheckoutMode() ) {
			return false;
		}
		
		if( !listing.isActive() ) {
			return false;
		}
		
		if( button.getHasUserPaidForThisItem() ) {
			return false;
		}
		
		if( listing.getInventories() != null && listing.getInventories().size() > 0 ) {
			return false;
		}
		
		// If result has not been set to false by the if logic, return true
		return true;
	}
	
	
	/**
	 * Created a signed URL for a private cloudfront distribution. These URL's will be valid for the number 
	 * of minutes configured into the system. 
	 * 
	 * Method from: http://docs.aws.amazon.com/AmazonCloudFront/latest/DeveloperGuide/CFPrivateDistJavaDevelopment.html
	 * 
	 * @return
	 * @throws Exception
	 */
	String createSignedUrl( String contentFilename, String downloadFilename ) {
		
		String result = null;
		
		try {
		
			// One time conversion of the DER file into a byte array.
			synchronized( this.derPrivateKey ) {
				
				if( this.derPrivateKey.length == 0 ) {
					this.derPrivateKey = ServiceUtils.readInputStreamToBytes( new FileInputStream( this.cloudfrontPrivateKeyFilePath ));
				}
			}
	
			
			// Create an expiry date and set its time for a number of minutes from now
			Date expires = new Date();
			expires.setTime(  System.currentTimeMillis() + ( this.cloudfrontSignedUrlValidMinutes * 60 * 1000 ) );
			
			// Generate a "canned" signed URL to allow access to a 
			// specific distribution and object
			URIBuilder builder = URIBuilder.fromUri( String.format( "https://%s/%s", this.cloudfrontDistributionDomain, contentFilename ) );
			
			if( downloadFilename != null ) {
				builder.queryParam( "response-content-disposition", "attachment;filename=\""+ downloadFilename +"\"" );
				builder.queryParam( "response-content-type", URLConnection.guessContentTypeFromName( contentFilename ) );
			}
			
			result = CloudFrontService.signUrlCanned(
					builder.build().toString(),		// Resource URL or Path
				    this.cloudfrontKeyPairId,     	// Certificate identifier, an active trusted signer for the distribution
				    this.derPrivateKey, 			// DER Private key data
				    expires							// DateLessThan
				);
			
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( result );
			}
	
			/** 
			 * Alternate approach for signing the URL using a generated policy
			 */
			
			// Build a policy document to define custom restrictions for a signed URL.
	//		String policy = CloudFrontService.buildPolicyForSignedUrl(
	//			    // Resource path (optional, may include '*' and '?' wildcards)
	//			    policyResourcePath, 
	//			    // DateLessThan
	//			    ServiceUtils.parseIso8601Date("2014-11-14T22:20:00.000Z"), 
	//			    // CIDR IP address restriction (optional, 0.0.0.0/0 means everyone)
	//			    "0.0.0.0/0", 
	//			    // DateGreaterThan (optional)
	//			    ServiceUtils.parseIso8601Date("2011-10-16T06:31:56.000Z")
	//		    );
	//
	//		// Generate a signed URL using a custom policy document.
	//		String signedUrl = CloudFrontService.signUrl(
	//		    // Resource URL or Path
	//		    "https://" + distributionDomain + "/" + s3ObjectKey, 
	//		    // Certificate identifier, an active trusted signer for the distribution
	//		    keyPairId,     
	//		    // DER Private key data
	//		    derPrivateKey, 
	//		    // Access control policy
	//		    policy 
	//		    );
	//		
	//		if( this.logger.isDebugEnabled() ) {
	//			this.logger.debug( signedUrl );
	//		}
		} catch( Exception e ) {
			throw new RuntimeException( "Exception trying to create signed cloudfront url.", e );
		}
		
		return result;
	}
	
}
