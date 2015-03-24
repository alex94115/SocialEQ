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
package com.projectlaver.domain;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomTwitterUser {
	
	private String username;
	private String firstname;
	private String lastname;
	private Long providerUserId;
	
	public RandomTwitterUser() {
		this.username = RandomStringUtils.randomAlphabetic( 8 );
		this.firstname = RandomStringUtils.randomAlphabetic( 5 );
		this.lastname = RandomStringUtils.randomAlphabetic( 8 );
		this.providerUserId = new Long( RandomStringUtils.randomNumeric( 9 ));
	}
	
	public RandomTwitterUser( String username, String firstname, String lastname, Long providerUserId ) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.providerUserId = providerUserId;
	}
	
	String getUsername() {
		return "twitter/" + this.username;
	}
	
	String getEmail() {
		return this.username + "@test.com";
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}
	
	public Long getProviderUserId() {
		return providerUserId;
	}
	
	
	
}
