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
package com.projectlaver.batch.domain;

import java.math.BigDecimal;
import java.util.Locale;

import com.projectlaver.util.ListingType;

public class ListingStateChangeCursorItem {
	
	private Long listingStateChangeId;
	private Integer listingStateChangeVersion;
	private Integer newListingStateChangeVersion;
	private String listingStateChangeStatus;
	private String newListingStateChangeUpdatedStatus;
	private Long sellerId;
	private Long listingId;
	private ListingType listingType;
	private String announcementPreamble;
	private String listingKeyword;
	private BigDecimal listingAmount;
	private Locale locale;
	private String imageFilename;
	private String facebookPageId;
	private String facebookAlbumId;
	private String providerId;
	private String providerUserId;
	private String accessToken;
	private String destinationSecret;
	private String displayName;
	private Boolean didPostingComplete;
	
	public Long getListingStateChangeId() {
		return listingStateChangeId;
	}
	public void setListingStateChangeId(Long listingStateChangeId) {
		this.listingStateChangeId = listingStateChangeId;
	}
	public Integer getListingStateChangeVersion() {
		return listingStateChangeVersion;
	}
	public void setListingStateChangeVersion(Integer listingStateChangeVersion) {
		this.listingStateChangeVersion = listingStateChangeVersion;
	}
	public Integer getNewListingStateChangeVersion() {
		return newListingStateChangeVersion;
	}
	public void setNewListingStateChangeVersion(Integer newListingStateChangeVersion) {
		this.newListingStateChangeVersion = newListingStateChangeVersion;
	}
	public String getListingStateChangeStatus() {
		return listingStateChangeStatus;
	}
	public void setListingStateChangeStatus(String listingStateChangeStatus) {
		this.listingStateChangeStatus = listingStateChangeStatus;
	}
	public Long getListingId() {
		return listingId;
	}
	public void setListingId(Long listingId) {
		this.listingId = listingId;
	}
	public ListingType getListingType() {
		return listingType;
	}
	public void setListingType( ListingType listingType) {
		this.listingType = listingType;
	}
	public String getAnnouncementPreamble() {
		return announcementPreamble;
	}
	public void setAnnouncementPreamble(String announcementPreamble) {
		this.announcementPreamble = announcementPreamble;
	}
	public String getListingKeyword() {
		return listingKeyword;
	}
	public void setListingKeyword(String listingKeyword) {
		this.listingKeyword = listingKeyword;
	}
	public BigDecimal getListingAmount() {
		return listingAmount;
	}
	public void setListingAmount(BigDecimal listingAmount) {
		this.listingAmount = listingAmount;
	}
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getProviderUserId() {
		return providerUserId;
	}
	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getDestinationSecret() {
		return destinationSecret;
	}
	public void setDestinationSecret(String destinationSecret) {
		this.destinationSecret = destinationSecret;
	}
	public Boolean getDidPostingComplete() {
		return didPostingComplete;
	}
	public void setDidPostingComplete(Boolean didPostingComplete) {
		this.didPostingComplete = didPostingComplete;
	}
	public Long getSellerId() {
		return this.sellerId;
	}
	public void setSellerId( Long sellerId ) {
		this.sellerId = sellerId;
	}
	public void setFacebookPageId( String facebookPageId ) {
		this.facebookPageId = facebookPageId;
	}
	public String getFacebookPageId() {
		return this.facebookPageId;
	}
	public void setFacebookAlbumId( String facebookAlbumId ) {
		this.facebookAlbumId = facebookAlbumId;
	}
	public String getFacebookAlbumId() {
		return this.facebookAlbumId;
	}
	public void setImageFilename( String imageFilename ) {
		this.imageFilename = imageFilename;
	}
	public String getImageFilename() {
		return this.imageFilename;
	}
	public String getNewListingStateChangeUpdatedStatus() {
		return newListingStateChangeUpdatedStatus;
	}
	public void setNewListingStateChangeUpdatedStatus( String newListingStateChangeUpdatedStatus) {
		this.newListingStateChangeUpdatedStatus = newListingStateChangeUpdatedStatus;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
