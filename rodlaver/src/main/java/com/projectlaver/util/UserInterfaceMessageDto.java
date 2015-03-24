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
package com.projectlaver.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;

public class UserInterfaceMessageDto {

	/**
	 * Instance variables
	 */
	private final List<String> errors = new ArrayList<String>();
	private final List<String> successes = new ArrayList<String>();
	private final List<String> informations = new ArrayList<String>();

	/**
	 * Constants
	 */
	public static final String SUCCESS = "SUCCESS";
	public static final String INFORMATIONAL = "INFORMATIONAL";
	public static final String ERROR = "ERROR";
	
	
	public UserInterfaceMessageDto( MessageSource messageSource, String key, Locale locale, String severity ) {
		this( messageSource, key, null, locale, severity );
	}
	
	public UserInterfaceMessageDto( MessageSource messageSource, String key, Object[] parameters, Locale locale, String severity ) {
		String userInterfaceMessage = messageSource.getMessage( key, parameters, locale );
		
		if( StringUtils.equalsIgnoreCase(severity, "ERROR" ) ) {
			this.errors.add( userInterfaceMessage );
		} else if(StringUtils.equalsIgnoreCase(severity, "SUCCESS" ) ) {
			this.successes.add( userInterfaceMessage );
		} else {
			// informational
			this.informations.add( userInterfaceMessage );
		}
	}
	
	public void addError( String message ) {
		this.errors.add( message );
	}

	public void addSuccess( String message ) {
		this.successes.add( message );
	}

	public void addInformation( String message ) {
		this.informations.add( message );
	}
	
	public Boolean hasMessages() {
		return ( !this.errors.isEmpty() || !this.successes.isEmpty() || !this.informations.isEmpty() );
	}
	
	public String getHighestPriorityLevel() {
		String result = null;
		
		if( !errors.isEmpty() ) {
			result = "alert-error";
		} else if( !successes.isEmpty() ) {
			result = "alert-success";
		} else if( !informations.isEmpty() ) {
			result = "alert-info";
		} else {
			result = "None";
		}
		
		return result;
	}
	
	public String getHighestPriorityMessage() {
		String result = null;
		
		if( !errors.isEmpty() ) {
			result = this.errors.get( 0 );
		} else if( !successes.isEmpty() ) {
			result = this.successes.get( 0 );
		} else if( !informations.isEmpty() ) {
			result = this.informations.get( 0 );
		} else {
			result = "None";
		}
		
		return result;
	}
	
}
