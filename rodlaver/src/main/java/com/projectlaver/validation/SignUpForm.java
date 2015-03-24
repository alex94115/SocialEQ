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

import javax.validation.constraints.NotNull;

public class SignUpForm {

	@NotNull
	private String emailInput1;

	@NotNull
	private String emailInput2;
	
	private Boolean doAllowSellerEmails;
	
	@NotNull
	private Integer tosVersion;

	public String getEmailInput1() {
		return emailInput1;
	}

	public void setEmailInput1(String emailInput1) {
		this.emailInput1 = emailInput1;
	}

	public String getEmailInput2() {
		return emailInput2;
	}

	public void setEmailInput2(String emailInput2) {
		this.emailInput2 = emailInput2;
	}
	
	public Boolean getDoAllowSellerEmails() {
		return doAllowSellerEmails;
	}

	public void setDoAllowSellerEmails(Boolean doAllowSellerEmails) {
		this.doAllowSellerEmails = doAllowSellerEmails;
	}

	public Integer getTosVersion() {
		return tosVersion;
	}

	public void setTosVersion(Integer tosVersion) {
		this.tosVersion = tosVersion;
	}
	
}
