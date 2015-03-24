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

import java.util.List;
import java.util.Map;

import org.springframework.validation.FieldError;

public class AddressValidationResponse {

	private String status;
	private List<FieldError> errorMessageList;
	private Map<String, String> userInputAddress;
	private Map<String, String> uspsSuggestedAddress;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<FieldError> getErrorMessageList() {
		return this.errorMessageList;
	}

	public void setErrorMessageList(List<FieldError> errorMessageList) {
		this.errorMessageList = errorMessageList;
	}

	public Map<String, String> getUserAddress() {
		return userInputAddress;
	}

	public void setUserAddress(Map<String, String> userInputAddress) {
		this.userInputAddress = userInputAddress;
	}

	public Map<String, String> getUspsAddress() {
		return uspsSuggestedAddress;
	}

	public void setUspsAddress(Map<String, String> uspsSuggestedAddress) {
		this.uspsSuggestedAddress = uspsSuggestedAddress;
	}
	
}
