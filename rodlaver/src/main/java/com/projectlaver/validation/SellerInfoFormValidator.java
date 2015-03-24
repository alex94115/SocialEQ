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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.projectlaver.controller.SelfRegistrationController;

public class SellerInfoFormValidator {

	/**
	 * Instance variables
	 */
	
	private final Log logger = LogFactory.getLog(getClass());

	private final SellerInfoForm sellerInfoForm;
	
	private final BindingResult result;
	
	private final MessageSource messageSource;
	
	private final Locale locale;
	
	/**
	 * Static variables
	 */
	
	private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile( "^[2-9]{1}[0-9]{9}$" );
	private static final String DOB_DATE_FORMAT = "yyyy-MM-dd";
	
	public SellerInfoFormValidator( SellerInfoForm sellerInfoForm, BindingResult result, MessageSource messageSource, Locale locale ) {
		this.sellerInfoForm = sellerInfoForm;
		this.result = result;
		this.messageSource = messageSource;
		this.locale = locale;
	}
	
	public BindingResult doValidation() {
		
		/**
		 * Phone number
		 */
		
		String phoneNumber = this.sellerInfoForm.getPhoneAreaCode()
				.concat( this.sellerInfoForm.getPhoneNxx() )
				.concat( this.sellerInfoForm.getPhoneXxxx() );
		
		if( !PHONE_NUMBER_PATTERN.matcher( phoneNumber ).matches() ) {
			this.addValidationError( "sellerInfoForm.phoneNumber.invalid", "phoneAreaCode" );
		}
		
		/**
		 * DOB
		 */
		
		SimpleDateFormat sdf = new SimpleDateFormat( DOB_DATE_FORMAT );
		sdf.setLenient( false );
		
		String dobString = this.sellerInfoForm.getDobYear()
				.concat( "-" )
				.concat( this.sellerInfoForm.getDobMonth() )
				.concat( "-" )
				.concat( this.sellerInfoForm.getDobDay() );
				
		try {
			Date dob = sdf.parse( dobString );
			Calendar dobCalendar = Calendar.getInstance();
			dobCalendar.setTime( dob );
			
			Calendar eighteenYearsAgo = Calendar.getInstance();
			eighteenYearsAgo.add( Calendar.YEAR, -18 );
			
			if( dobCalendar.compareTo( eighteenYearsAgo ) > 0 ) {
				this.addValidationError( "sellerInfoForm.dob.not18", "dobYear" );
			}
		} catch( ParseException e ) {
			this.addValidationError( "sellerInfoForm.dob.invalid", "dobYear" );
		}
		
		return this.result;
		
	}
	
	void addValidationError( String messageKey, String field ) {
		String validationMessage = this.messageSource.getMessage( messageKey,  null, this.locale );
		this.result.addError( new FieldError( SelfRegistrationController.SELLER_INFO_FORM_ATTRIBUTE, field, validationMessage ) );
	}
	
}
