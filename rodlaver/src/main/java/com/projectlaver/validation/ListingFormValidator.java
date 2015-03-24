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
package com.projectlaver.validation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.projectlaver.controller.ListingController;
import com.projectlaver.util.FileList;
import com.projectlaver.util.FileMetadata;
import com.projectlaver.util.GoodsType;
import com.projectlaver.util.ListingSubType;
import com.projectlaver.util.ListingType;

public class ListingFormValidator {

	/**
	 * Instance variables
	 */
	private final Log logger = LogFactory.getLog(getClass());

	private final ListingForm listingForm;
	
	private final FileList fileList;
	
	private final BindingResult result;
	
	private final MessageSource messageSource;
	
	private final Locale locale;
	
	public ListingFormValidator( ListingForm listingForm, FileList fileList, BindingResult result, MessageSource messageSource, Locale locale ) { 
		this.listingForm = listingForm;
		this.fileList = fileList;
		this.result = result;
		this.messageSource = messageSource;
		this.locale = locale;
	}
	
	/**
	 * Validates everything about the Listing Form except for the tweet length.
	 * 
	 * @return
	 */
	public BindingResult doValidation() {

		ListingType listingType = null;
		ListingSubType listingSubType = null;
		if( listingForm.getListingType().equals( ListingType.CAMPAIGN ) ) {
			listingType = ListingType.CAMPAIGN;
			listingSubType = listingForm.getListingSubType();
			
		} else {
			listingType = listingForm.getListingType();
		}
		
		GoodsType goodsType = listingForm.getGoodsType();

		// Image is always required
		FileMetadata image = null;
		if( this.fileList != null && this.fileList.getFiles() != null ) {
			for( int i = 0; i < this.fileList.getFiles().size(); i++ ) {
				FileMetadata meta = this.fileList.getFiles().get( i );
				if( meta.getIsCampaignImage() ) {
					image = meta;
					break;
				}
			}
		}
		
		if( (image == null) ) {
			this.addValidationError( "saleForm.image.required", "image" );
			
		} else {

			/**
			 *  Validate the preview image's extension & resolution
			 */
			String imageContentType = image.getContentType();
			if( !this.isValidImageMimeType( imageContentType )) {
				this.addValidationError( "saleForm.image.invalidType", "image" );
			}
			
			try {
				BufferedImage bufferedImage =  ImageIO.read( new File( image.getTransientPath() ));
				if( bufferedImage != null ) {
					int height = bufferedImage.getHeight();
					int width = bufferedImage.getWidth();
					if( height < 300 || width < 300 ) {
						this.addValidationError( "saleForm.image.insufficientResolution", "image" );
					}
					
					// keep the height and width
					image.setImageHeight( height );
					image.setImageWidth( width );
				}			
			} catch ( IOException e ) {
				this.addValidationError( "saleForm.image.invalidType", "image" );
			}
			
			// Make sure the image size is not greater than 30 MB
			if( image.getSize() > 30000000 ) {
				this.addValidationError( "Size.saleForm.image", "image" );
			}
		}

		if( this.listingForm.getGoodsType().equals( GoodsType.DIGITAL ) ) {
			
			// Digital Content is required if if the sale type is digital
			Boolean doesFileListHaveDigitalContent = false;
			if( this.fileList.getFiles() != null && this.fileList.getFiles().size() > 0 ) {
				for( int i = 0; i < this.fileList.getFiles().size(); i++ ) {
					FileMetadata meta = this.fileList.getFiles().get( i );
					if( !meta.getIsCampaignImage() ) {
						doesFileListHaveDigitalContent = true;
						if( !this.isValidDigitalContentMimeType( meta.getContentType() ) ) {
							this.addValidationError( "saleForm.digitalContent.not.accepted", "digitalContent" );
						}
					}
				}
			}
			
			if( !doesFileListHaveDigitalContent ) {
				this.addValidationError( "saleForm.digitalContent.required", "digitalContent" );
			}
		}

		// if listing type is SELLING, price is required
		if( listingType.equals( ListingType.SELLING ) ) {
			BigDecimal price = listingForm.getPrice();
			if( (price == null) || price.equals( new BigDecimal( "0.00" ))) {
				this.addValidationError( "NotNull.saleForm.price", "price" );
			}
		}

		// if listing type is physical sale or drawing-style giveaway, quantity is required
		if( ( listingType.equals( ListingType.SELLING ) && goodsType.equals( GoodsType.PHYSICAL ) ) || 
			( listingType.equals( ListingType.CAMPAIGN ) && listingSubType.equals( ListingSubType.DRAWING )  ) ) {
			
			Integer quantity = listingForm.getQuantity();
			if( (quantity == null) || quantity.equals( 0 )) {
				this.addValidationError( "NotNull.saleForm.quantity", "quantity" );
			}
			
		} else if ( (listingType.equals( ListingType.CAMPAIGN ) && listingSubType.equals( ListingSubType.INSTANT )  ) ) {
			// instant-win giveaways cannot have a quantity
			listingForm.setQuantity( null );
		} else if( (listingForm.getQuantity() != null) && (listingForm.getQuantity() <= 0) ) {
			// keep the quantity as 'null' unless its a positive integer
			listingForm.setQuantity( null );
		}
		
		// make sure the hashtag doesn't have multiple ## in it
		String keyword = listingForm.getKeyword(); 
		if( keyword.indexOf( "##" ) >=0 || !this.isAlphanumeric( keyword) ) {
			this.addValidationError( "saleForm.keyword.invalid", "keyword" );
		} 
		
		if( StringUtils.equalsIgnoreCase( keyword, "#SOLD" ) ) {
			this.addValidationError( "saleForm.keyword.reserved", "keyword" );
		}

		return result;
	}
	
	/**
	 * Helper method to add form field errors to the saleForm's BindingResult 
	 */
	void addValidationError( String messageKey, String field ) {
		String validationMessage = this.messageSource.getMessage( messageKey,  null, this.locale );
		this.result.addError( new FieldError( ListingController.SALE_FORM, field, validationMessage ) );
	}
	
	/**
	 * 
	 * The valid mime types are image/png, image/gif, and image/jpeg
	 * 
	 * @param imageMimeType
	 * @return
	 */
	Boolean isValidImageMimeType( String imageMimeType ) {
		
		return ( StringUtils.equals( imageMimeType, "image/gif" ) ||
				 StringUtils.equals( imageMimeType, "image/jpeg" ) ||
				 StringUtils.equals( imageMimeType, "image/png" ) );
	}
	
	/**
	 * NOTE: this must stay in sync with the client-side validation in fileupload-main.js
	 * 
	 * @param digitalContentMimeType
	 */
	boolean isValidDigitalContentMimeType( String digitalContentMimeType ) {
		
		Boolean result = ( 
				 StringUtils.equals( digitalContentMimeType, "image/gif" ) ||
				 StringUtils.equals( digitalContentMimeType, "image/jpeg" ) ||
				 StringUtils.equals( digitalContentMimeType, "image/png" ) ||
				 StringUtils.equals( digitalContentMimeType, "audio/mpeg" ) ||
				 StringUtils.equals( digitalContentMimeType, "audio/mp3" ) ||
				 StringUtils.equals( digitalContentMimeType, "audio/mp4" ) ||
				 StringUtils.equals( digitalContentMimeType, "audio/x-m4a" ) ||
				 StringUtils.equals( digitalContentMimeType, "video/mp4" ) ||
				 StringUtils.equals( digitalContentMimeType, "video/quicktime" ) ||
				 StringUtils.equals( digitalContentMimeType, "video/3gpp" ) );

		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "Digital content type of: %s is considered valid = %s", digitalContentMimeType, result ));
		}
		
		return result;
	}
	
	Boolean isAlphanumeric( String hashtag ) {
		
		Boolean result = false;
		if( StringUtils.isNotBlank( hashtag ) ) {
			
			String toEvaluate = hashtag;
			if( hashtag.startsWith( "#" ) ) {
				toEvaluate = hashtag.substring( 1 );
			}
			
			result = StringUtils.isAlphanumeric( toEvaluate );
		}
		
		return result;
	}

}
