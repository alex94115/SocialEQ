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

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import com.projectlaver.util.FileList;

public class ListingFormValidatorTest {

	@Test
	public void testIsValidImageMimeType() {
		ListingFormValidator validator = new ListingFormValidator(null, null, null, null, null);
		assertTrue( "GIF should be valid.", validator.isValidImageMimeType( "image/gif" ) );
		assertTrue( "JPEG should be valid.", validator.isValidImageMimeType( "image/jpeg" ) );
		assertTrue( "PNG should be valid.", validator.isValidImageMimeType( "image/png" ) );
		assertFalse( "TIFF should be invalid.", validator.isValidImageMimeType( "image/tiff" ) );
		assertFalse( "Empty string should be invalid.", validator.isValidImageMimeType( "" ) );
	}
	
	@Test
	public void testFormValidation() {
		ListingForm listingForm = new ListingForm();
		listingForm.setKeyword( "#hashtag" );

		FileList fileList = new FileList();
		BindingResult bindingResult = new MapBindingResult(new HashMap(), "errors");
		MessageSource messageSource = this.initializeStaticMessageSource();
		Locale locale = Locale.US;
		
		
		ListingFormValidator validator = new ListingFormValidator( listingForm, fileList, bindingResult, messageSource, locale );
		
		
		BindingResult result = validator.doValidation();
		assertNotNull( result );
	}
	
	@Test
	public void testDoubleHashtagValidation() {
		ListingForm listingForm = new ListingForm();
		FileList fileList = new FileList();
		BindingResult bindingResult = new MapBindingResult(new HashMap(), "errors");
		MessageSource messageSource = this.initializeStaticMessageSource();
		Locale locale = Locale.US;
		
		listingForm.setKeyword( "##double" );
		
		ListingFormValidator validator = new ListingFormValidator( listingForm, fileList, bindingResult, messageSource, locale );
		BindingResult result = validator.doValidation();
		assertNotNull( result.getFieldError( "keyword" ) );
	}
	
	@Test
	public void testHashtagSoldValidation() {
		ListingForm listingForm = new ListingForm();
		FileList fileList = new FileList();
		BindingResult bindingResult = new MapBindingResult(new HashMap(), "errors");
		MessageSource messageSource = this.initializeStaticMessageSource();
		Locale locale = Locale.US;
		
		listingForm.setKeyword( "#SOLD" );
		
		ListingFormValidator validator = new ListingFormValidator( listingForm, fileList, bindingResult, messageSource, locale );
		BindingResult result = validator.doValidation();
		assertNotNull( result.getFieldError( "keyword" ) );
	}
	
	@Test
	public void testNonAlphaHashtagValidation() {
		ListingForm listingForm = new ListingForm();
		FileList fileList = new FileList();
		BindingResult bindingResult = new MapBindingResult(new HashMap(), "errors");
		MessageSource messageSource = this.initializeStaticMessageSource();
		Locale locale = Locale.US;
		
		listingForm.setKeyword( "#Funky-Hashtag" );
		
		ListingFormValidator validator = new ListingFormValidator( listingForm, fileList, bindingResult, messageSource, locale );
		BindingResult result = validator.doValidation();
		assertNotNull( result.getFieldError( "keyword" ) );
	}
	
	MessageSource initializeStaticMessageSource() {
		StaticMessageSource result = new StaticMessageSource();

		result.addMessage( "saleForm.invalid", Locale.US, "We can't create your campaign yet. Please correct the errors below and then click \"Continue\"." );
		result.addMessage( "Size.saleForm.publishTo", Locale.US, "At least one site must be selected" );
		result.addMessage( "typeMismatch.saleForm.price", Locale.US, "Price must be specified in dollars and cents" );
		result.addMessage( "NotNull.saleForm.price", Locale.US, "Price is required" );
		result.addMessage( "NotNull.saleForm.quantity", Locale.US, "Quantity is required" );
		result.addMessage( "typeMismatch.saleForm.quantity", Locale.US, "Quantity must be a number" );
		result.addMessage( "Size.saleForm.keyword", Locale.US, "#hashtag is required" );
		result.addMessage( "Size.saleForm.title", Locale.US, "Title is required" );
		result.addMessage( "Size.saleForm.longDescription", Locale.US, "The long description is required and cannot exceed 200 characters" );
		result.addMessage( "Size.saleForm.tweet", Locale.US, "Message is required, and the formatted tweet cannot exceed 140 characters" );
		result.addMessage( "saleForm.tweet.exceedsMaxLength", Locale.US, "The total length of your tweet cannot exceed 95 characters" );
		result.addMessage( "Size.saleForm.message", Locale.US, "You must specify a message" );
		result.addMessage( "saleForm.image.required", Locale.US, "Campaign image is required" );
		result.addMessage( "saleForm.image.invalidType", Locale.US, "Image must be a valid gif, jpeg, or png" );
		result.addMessage( "Size.saleForm.image", Locale.US, "Your preview image must be less than 1 MB in size" );
		result.addMessage( "saleForm.image.insufficientResolution", Locale.US, "Your campaign image must be at least 300 x 300" );
		result.addMessage( "saleForm.digitalContent.required", Locale.US, "If the listing type is ''Digital'', digital content is required" );
		result.addMessage( "saleForm.keyword.invalid", Locale.US, "That hashtag is invalid." );
		result.addMessage( "saleForm.keyword.reserved", Locale.US, "#SOLD is a reserved #hashtag. Please choose another one." );

		return result;
	}
	
}
