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
import javax.validation.constraints.Size;

public class SellerInfoForm {
	
	@NotNull
	@Size(min=2,max=255)
	private String firstName;
	
	@NotNull
	@Size(min=2,max=255)
	private String lastName;
	
	// phone
	
	@NotNull
	@Size(min=3,max=3)
	private String phoneAreaCode;
	
	@NotNull
	@Size(min=3,max=3)
	private String phoneNxx;
	
	@NotNull
	@Size(min=4,max=4)
	private String phoneXxxx;
		
	// dob
	
	@NotNull
	@Size(min=4,max=5)
	private String dobYear;
	
	@NotNull
	@Size(min=2,max=2)
	private String dobMonth;
	
	@NotNull
	@Size(min=2,max=2)
	private String dobDay;
	
	// address
	@NotNull
	@Size(min=6)
	private String addressFirstLine;
	
	private String addressSecondLine;
	
	@NotNull
	@Size(min=1)
	private String city;
	
	@NotNull
	@Size(min=1)
	private String state;
	
	@NotNull
	@Size(min=5)
	private String zip;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneAreaCode() {
		return phoneAreaCode;
	}

	public void setPhoneAreaCode(String phoneAreaCode) {
		this.phoneAreaCode = phoneAreaCode;
	}

	public String getPhoneNxx() {
		return phoneNxx;
	}

	public void setPhoneNxx(String phoneNxx) {
		this.phoneNxx = phoneNxx;
	}

	public String getPhoneXxxx() {
		return phoneXxxx;
	}

	public void setPhoneXxxx(String phoneXxxx) {
		this.phoneXxxx = phoneXxxx;
	}

	public String getDobYear() {
		return dobYear;
	}

	public void setDobYear(String dobYear) {
		this.dobYear = dobYear;
	}

	public String getDobMonth() {
		return dobMonth;
	}

	public void setDobMonth(String dobMonth) {
		this.dobMonth = dobMonth;
	}

	public String getDobDay() {
		return dobDay;
	}

	public void setDobDay(String dobDay) {
		this.dobDay = dobDay;
	}

	public String getAddressFirstLine() {
		return addressFirstLine;
	}

	public void setAddressFirstLine(String addressFirstLine) {
		this.addressFirstLine = addressFirstLine;
	}

	public String getAddressSecondLine() {
		return addressSecondLine;
	}

	public void setAddressSecondLine(String addressSecondLine) {
		this.addressSecondLine = addressSecondLine;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
}
