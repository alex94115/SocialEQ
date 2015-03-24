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

import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;

public class SellerInfoFormValidatorTest {

	@Test
	public void testValidForm() {
		
		SellerInfoForm form = this.initializeValidForm();
		
		SellerInfoFormValidator validator = new SellerInfoFormValidator( form,  new MapBindingResult(new HashMap(), "errors"), this.initializeStaticMessageSource(), Locale.US );
		BindingResult result = validator.doValidation();
		
		// expect no errors
		assertFalse( result.hasErrors() );
	}
	
	@Test
	public void testNot18() {
		SellerInfoForm form = this.initializeValidForm();
		form.setDobYear( "2000" );
		
		SellerInfoFormValidator validator = new SellerInfoFormValidator( form,  new MapBindingResult(new HashMap(), "errors"), this.initializeStaticMessageSource(), Locale.US );
		BindingResult result = validator.doValidation();
		
		// expect an error on the dob year field, since this person is not 18
		assertTrue( result.hasErrors() );
		assertNotNull( result.getFieldError( "dobYear" ) );
	}
	
	@Test
	public void testInvalidDob() {
		SellerInfoForm form = this.initializeValidForm();
		form.setDobMonth( "13" );
		
		SellerInfoFormValidator validator = new SellerInfoFormValidator( form,  new MapBindingResult(new HashMap(), "errors"), this.initializeStaticMessageSource(), Locale.US );
		BindingResult result = validator.doValidation();
		
		// expect an error on the dob year field, since a dob month of '13' doesn't make sense
		assertTrue( result.hasErrors() );
		assertNotNull( result.getFieldError( "dobYear" ) );
	}
	
	@Test
	public void testInvalidPhone() {
		SellerInfoForm form = this.initializeValidForm();
		form.setPhoneAreaCode( "abc" );
		
		SellerInfoFormValidator validator = new SellerInfoFormValidator( form,  new MapBindingResult(new HashMap(), "errors"), this.initializeStaticMessageSource(), Locale.US );
		BindingResult result = validator.doValidation();
		
		// expect an error on the phone area code
		assertTrue( result.hasErrors() );
		assertNotNull( result.getFieldError( "phoneAreaCode" ) );
	}
	
	SellerInfoForm initializeValidForm() {
		SellerInfoForm form = new SellerInfoForm();
		form.setFirstName( "Joe" );
		form.setLastName( "Schmoe" );
		
		form.setPhoneAreaCode( "555" );
		form.setPhoneNxx( "555" );
		form.setPhoneXxxx( "1212" );
		
		form.setDobYear( "1990" );
		form.setDobMonth( "12" );
		form.setDobDay( "31" );
		
		form.setAddressFirstLine( "301 Main St" );
		form.setCity( "San Francisco" );
		form.setState( "CA" );
		form.setZip( "94105" );
		
		return form;
	}

	MessageSource initializeStaticMessageSource() {
		StaticMessageSource result = new StaticMessageSource();

		result.addMessage( "sellerInfoForm.dob.not18", Locale.US, "You must be at least 18 years old to sell using SocialEQ." );
		result.addMessage( "sellerInfoForm.dob.invalid", Locale.US, "Enter a valid birthdate." );
		result.addMessage( "sellerInfoForm.phoneNumber.invalid", Locale.US, "Enter a valid US phone number." );
		
		return result;
	}
}
