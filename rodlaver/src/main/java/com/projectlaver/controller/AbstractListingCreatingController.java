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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.MessageSource;
import org.springframework.util.StringValueResolver;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.projectlaver.domain.ContentFile;
import com.projectlaver.domain.Inventory;
import com.projectlaver.domain.Listing;
import com.projectlaver.domain.User;
import com.projectlaver.util.FileList;
import com.projectlaver.util.FileMetadata;
import com.projectlaver.util.GoodsType;
import com.projectlaver.util.ListingStatus;
import com.projectlaver.util.ListingSubType;
import com.projectlaver.util.ListingType;
import com.projectlaver.validation.ListingForm;
import com.projectlaver.validation.ListingFormValidator;

public abstract class AbstractListingCreatingController implements EmbeddedValueResolverAware {

	/**
	 * Instance variables
	 */
	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
    private MessageSource messageSource;

	private StringValueResolver resolver;

	// properties

	@Value("${communications.baseListingDetailUrl}")
	private String baseListingDetailUrl;
	
	@Value("${defaultSellerRevenueRatio}")
	private String defaultSellerRevenueRatio;
	
	/**
	 * Constants
	 */
	private static final Splitter SPLITTER = Splitter.on(CharMatcher.WHITESPACE);
	
	private static final Integer MAX_TWEET_CODE_POINTS = 140;
	
	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		this.resolver = resolver;
	}
	
	protected Listing createListing( User seller, ListingForm listingForm, FileList fileList, Locale locale ) throws IOException {
		Listing listing = new Listing();
		listing.setSeller( seller );
		listing.setTitleWithUnsanitized( listingForm.getTitle() );
		listing.setAnnouncementPreambleWithUnsanitized( listingForm.getMessage() );
		listing.setPublishTo( listingForm.getPublishTo() );
		
		listing.setLongDescriptionWithUnsanitized( listingForm.getLongDescription() );
		listing.setTermsAndConditionsWithUnsanitized( listingForm.getTermsAndConditions() );
		listing.setAmount( listingForm.getPrice() );
		listing.setSellerRevenueRatio( this.getSellerRevenueRatio( seller.getId() ) );
		
		// Inventory with Default product code
		Inventory inventory = new Inventory();
		inventory.setQuantity( listingForm.getQuantity() );
		inventory.setRemainingQuantity( listingForm.getQuantity() );
		inventory.setProductCode( Inventory.DEFAULT_PRODUCT_CODE );
		inventory.setListing( listing );

		List<Inventory> inventoryList = new ArrayList<Inventory>();
		inventoryList.add( inventory );
		listing.setInventories( inventoryList );
		listing.setHasSingleInventory( true );
		
		listing.setStatus( ListingStatus.PENDING );
		listing.setLocale( locale );

		if( listingForm.getBackgroundImageUrl() != null ) {
			listing.setBackgroundImageUrlWithUnsanitized( listingForm.getBackgroundImageUrl() );
		}
		
		ListingType listingType = listingForm.getListingType();
		listing.setType( listingType );

		// Physical, Digital, or Voucher Goods Type?
		GoodsType goodsType = listingForm.getGoodsType();
		listing.setGoodsType( goodsType );
		
		// set the sub-type (aka the giveaway type, instant or sweepstakes) for CAMPAIGNS
		if( listingType.equals( ListingType.CAMPAIGN )) {
			listing.setSubType( listingForm.getListingSubType() );
		}
		
		listing.setKeyword( this.getListingKeyword( listing.getType(), listingForm.getKeyword(), locale ));
		
		/**
		 * Start digital content section
		 */
		
		// see if the file list contains digital content
		Boolean doesListingHaveDigitalContent = false;
		if( fileList.getFiles() != null && fileList.getFiles().size() > 0 ) {
			for( int i = 0; i < fileList.getFiles().size(); i++ ) {
				FileMetadata meta = fileList.getFiles().get( i );
				if( !meta.getIsCampaignImage() ) {
					doesListingHaveDigitalContent = true;
					break;
				}
			}
		}

		// makes sure a digital listing always has digital content
		//if( isDigitalListing && !listingContainsDigitalContent ) {
		if( goodsType.equals( GoodsType.DIGITAL ) && !doesListingHaveDigitalContent ) {
			throw new RuntimeException( "Seller: " + seller.getUsername() + " attempting to create a digital listing with title: "
										+ listingForm.getTitle() + " but there's no digital content." );

		// For digital listings (and giveaways where digital content is uploaded), add the digital content to the listing
		} else if( goodsType.equals( GoodsType.DIGITAL ) && doesListingHaveDigitalContent ) {

			Set<ContentFile> contentFiles = new HashSet<ContentFile>();
			listing.setContentFiles( contentFiles );

			List<FileMetadata> files = fileList.getFiles();
			for( int i = 0; i < files.size(); i++ ) {
				FileMetadata meta = files.get( i );
				if( !meta.getIsCampaignImage() ) {
					ContentFile contentFile = new ContentFile();
					
					// save the original filename so that we can display it to the seller / set it as the download file name
					contentFile.setDigitalContentOriginalFilename( meta.getName() );
					
					// create an obscured filename so that the GET URL's will not collide
					contentFile.setContentFilename( meta.getAwsFilename() );
					contentFile.setTransientPath( meta.getTransientPath() );
					
					// save the content type so that we can set the MIME properly on download
					contentFile.setDigitalContentType( meta.getContentType() );
					
					// set the reverse relationship
					contentFile.setListing( listing );
					
					contentFiles.add( contentFile );
				}
			}
		}
		
		/**
		 * End digital content section
		 */


		/**
		 * Start listing image section
		 */
		
		List<FileMetadata> files = fileList.getFiles();
		for( int i = 0; i < files.size(); i++ ) {
			
			FileMetadata meta = files.get( i );
			if( meta.getIsCampaignImage() ) {
				
				// Set the image filename / content
				listing.setImageFilename( meta.getAwsFilename() );
				listing.setImageHeight( meta.getImageHeight() );
				listing.setImageWidth( meta.getImageWidth() );
				listing.setImageTransientPath( meta.getTransientPath() );

				break;
			}
			
		}
		
		/**
		 * End listing image section
		 */
		
		// Limited quantity giveaways get a random seed that is used to determine the winner(s)
		if( listingType.equals( ListingType.CAMPAIGN ) && listing.getSubType().equals( ListingSubType.DRAWING ) ) {
			// get a random number between 0 and 2147483647
			Random rand = new Random();
			listing.setGiveawaySeed( rand.nextInt( 2147483647 ) );
		}

		return listing;
	}
	
	protected BindingResult validateListing(ListingForm listingForm, FileList fileList, BindingResult result, Locale locale ) {

		ListingType listingType = listingForm.getListingType();
		
		// validate everything except for the tweet length
		ListingFormValidator validator = new ListingFormValidator( listingForm, fileList, result, this.messageSource, locale );
		validator.doValidation();

		// check the tweet length
		if( listingForm.getPrice() != null ) {

			String displayName = listingForm.getDisplayName(); 
			String keyword = listingForm.getKeyword();
			String tweetContent = null;
			String message = listingForm.getMessage();

			Long dummyListingId = 1L;
			tweetContent = this.formatTwitterListingMessageContent( listingType, displayName, keyword, listingForm.getPrice(), message, dummyListingId, locale );
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Formatted twitter listing message is: '" + tweetContent + "'");this.logger.debug(message);
			}
			
			int codePoints = this.getTotalTweetCodePoints( tweetContent );

			if( codePoints > MAX_TWEET_CODE_POINTS ) {
				String validationMessage = this.messageSource.getMessage( "saleForm.tweet.exceedsMaxLength",  null, locale );
				result.addError( new FieldError( ListingController.SALE_FORM, "tweet", validationMessage ) );
			}
		}

		return result;
	}
	
	/**
	 * Note: this method is duplicated in the batch job and needs to be kept in sync
	 */
	String formatTwitterListingMessageContent( ListingType listingType, String displayName, String keyword, BigDecimal price, String message, Long listingId, Locale locale ) {

		String messageKey = this.getListingAnnouncementTweetMessageKey( listingType );

		String formattedPrice = "";
		if( price != null ) {
			formattedPrice = String.format( "$%.2f", price );
		}

		Object[] messageParameters = null;
		if( listingType.equals( ListingType.SELLING )) {
			messageParameters = new Object[] { message, displayName, keyword, formattedPrice, this.baseListingDetailUrl + listingId };
		} else {
			messageParameters = new Object[] { message, displayName, keyword, this.baseListingDetailUrl + listingId };
		}

		return this.messageSource.getMessage( messageKey, messageParameters, locale ).trim();
	}


	protected Boolean containsNewListingFieldErrors(BindingResult bindingResult) {
		
		Boolean containsErrors = null;
		
		if( bindingResult.getFieldError( "publishTo" ) == null &&
			bindingResult.getFieldError( "isRevenueCampaign" ) == null && 
			bindingResult.getFieldError( "isInstantGiveaway" ) == null && 
			bindingResult.getFieldError( "isFundraiser" ) == null &&
			bindingResult.getFieldError( "isFundraiserReward" ) == null &&
			bindingResult.getFieldError( "isDigitalGood" ) == null && 
			bindingResult.getFieldError( "isLimitedQuantity" ) == null && 
			bindingResult.getFieldError( "price" ) == null &&
			bindingResult.getFieldError( "quantity" ) == null && 
			bindingResult.getFieldError( "keyword" ) == null && 
			bindingResult.getFieldError( "title" ) == null &&
			bindingResult.getFieldError( "longDescription" ) == null &&
			bindingResult.getFieldError( "message" ) == null ) {
			
			containsErrors = false;
		} else {
			containsErrors = true;
		}
		
		return containsErrors;
	}

	protected Boolean containsNewListingUploadErrors(BindingResult result) {
		return result.getFieldError( "image" ) != null || result.getFieldError( "digitalContent" ) != null;
	}
	
	protected BigDecimal getSellerRevenueRatio( Long sellerId ) {
		
		/**
		 *  The property key is "sellerRevenueRatio.{sellerId}, so we need
		 *  to build the key so we can attempt to lookup this value.
		 */
		String key = String.format( "${sellerRevenueRatio.%d}", sellerId );
		
		/**
		 *  Confusingly, if spring doesn't find the property with the key we passed in, 
		 *  instead of null it returns the key value.
		 */
		String lookupResult = resolver.resolveStringValue( key );
		
		BigDecimal sellerRevenueRatio = null;
		if( lookupResult.equals( key ) ) {
			// no override value found, so return the default
			sellerRevenueRatio = new BigDecimal( this.defaultSellerRevenueRatio );
		} else {
			// we did find an override value, so return it.
			sellerRevenueRatio = new BigDecimal( lookupResult );
		}
		
		return sellerRevenueRatio;
	}

	/**
	 * Note: this method is duplicated in the batch job and needs to be kept in sync
	 */
	String getListingKeyword(ListingType listingType, String inputKeyword, Locale locale) {

		String keyword = Jsoup.clean( inputKeyword, Whitelist.none() );
		
		// prepend the # if the keyword doesn't have it
		if( keyword.charAt(0) != '#' ) { 
			keyword = "#" + keyword;
		}
		
		if( keyword == null || keyword.isEmpty() ) {
			
			// Sale type listing
			if( listingType.equals( ListingType.SELLING )) {
				keyword = this.messageSource.getMessage( "listing.new.form.default.sale.keyword", null, locale );

			// Donation type listing
			} else if( listingType.equals( ListingType.DONATION ) ) {
				keyword = this.messageSource.getMessage( "listing.new.form.default.donation.keyword", null, locale );

			// Giveaway type listing
			} else {
				keyword = this.messageSource.getMessage( "listing.new.form.default.opt-in-campaign.keyword", null, locale );
			}
		}

		return keyword;
	}
	
	int getTotalTweetCodePoints( String tweet ) {

		Iterable<String> tokens = SPLITTER.split( tweet );
		Iterator<String> iter = tokens.iterator();
		
		int httpUrlCodePoints = 22;
		int httpsUrlCodePoints = 23;

		int count = 0;
		while( iter.hasNext() ) {
			String token = iter.next();

			if( token.startsWith( "https://") ) {
				count = count + httpsUrlCodePoints;
			} else if( token.startsWith( "http://" )) {
				count = count + httpUrlCodePoints;
			} else{
				count = count + token.codePointCount( 0, token.length() );
			}

			//if( tokenizer.hasMoreElements() ) {
			if( iter.hasNext() ) {
				count = count + 1;
			}
		}

		return count;
	}
	
	/**
	 * Note: this method is duplicated in the batch job and needs to be kept in sync
	 */
	String getListingAnnouncementTweetMessageKey(ListingType listingType) {
		String messageKey;
		
		// Sale type listing
		if( listingType.equals( ListingType.SELLING )) {
			messageKey = "saleForm.tweet.template.sale";

		// Donation type listing
		} else if( listingType.equals( ListingType.DONATION ) ) {
			messageKey = "saleForm.tweet.template.donation";

		// Campaign type listing
		} else {
			messageKey = "saleForm.tweet.template.opt-in-campaign";
		}
		
		return messageKey;
	}

	
}
