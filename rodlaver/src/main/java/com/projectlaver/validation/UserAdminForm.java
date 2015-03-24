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

public class UserAdminForm {
	
	private String username;
	private boolean hasBuyer;
	private boolean hasSeller;
	private boolean hasCharity;
	private boolean hasAdmin;
	private boolean isBlocked;
	private String emailAddress;
	private String facebookPageId;
	private String facebookPageUrl;
	private String facebookAlbumId;
	private Boolean hasActivePreapproval;
	
	public UserAdminForm() {}
	
	public boolean getHasBuyer() {
		return hasBuyer;
	}
	public void setHasBuyer(boolean hasBuyer) {
		this.hasBuyer = hasBuyer;
	}
	public boolean getHasSeller() {
		return hasSeller;
	}
	public void setHasSeller(boolean hasSeller) {
		this.hasSeller = hasSeller;
	}
	public boolean getHasCharity() {
		return hasCharity;
	}
	public void setHasCharity(boolean hasCharity) {
		this.hasCharity = hasCharity;
	}
	public boolean getHasAdmin() {
		return hasAdmin;
	}
	public void setHasAdmin(boolean hasAdmin) {
		this.hasAdmin = hasAdmin;
	}
	public boolean getIsBlocked() {
		return isBlocked;
	}
	public void setIsBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getFacebookPageId() {
		return facebookPageId;
	}
	public void setFacebookPageId(String facebookPageId) {
		this.facebookPageId = facebookPageId;
	}
	public String getFacebookAlbumId() {
		return facebookAlbumId;
	}
	public void setFacebookAlbumId(String facebookAlbumId) {
		this.facebookAlbumId = facebookAlbumId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFacebookPageUrl() {
		return facebookPageUrl;
	}
	public void setFacebookPageUrl(String facebookPageUrl) {
		this.facebookPageUrl = facebookPageUrl;
	}
	public Boolean getHasActivePreapproval() {
		return hasActivePreapproval;
	}
	public void setHasActivePreapproval(Boolean hasActivePreapproval) {
		this.hasActivePreapproval = hasActivePreapproval;
	}
	
}
