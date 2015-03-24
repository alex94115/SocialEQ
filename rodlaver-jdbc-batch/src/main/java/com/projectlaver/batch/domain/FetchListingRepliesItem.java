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

import java.util.Date;

public class FetchListingRepliesItem {

	private String providerId;
	private String listingMessageProviderId;
	private String listingMessageContainerProviderId;
	private String sellerFacebookId;
	private String sellerAccessToken;
	private String listingKeyword;
	private Long pagingStatusId;
	private Date existingMaxFetchedCommentTime;
	private Date newMaxFetchedCommentTime;
	private Integer pagingStatusVersion;
	private Integer updatedPagingStatusVersion;

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getListingMessageProviderId() {
		return listingMessageProviderId;
	}

	public void setListingMessageProviderId(String listingMessageProviderId) {
		this.listingMessageProviderId = listingMessageProviderId;
	}

	public String getListingMessageContainerProviderId() {
		return listingMessageContainerProviderId;
	}

	public void setListingMessageContainerProviderId(String listingMessageContainerProviderId) {
		this.listingMessageContainerProviderId = listingMessageContainerProviderId;
	}

	public String getListingKeyword() {
		return listingKeyword;
	}

	public void setListingKeyword(String listingKeyword) {
		this.listingKeyword = listingKeyword;
	}

	public Long getPagingStatusId() {
		return pagingStatusId;
	}

	public void setPagingStatusId(Long pagingStatusId) {
		this.pagingStatusId = pagingStatusId;
	}

	public Date getExistingMaxFetchedCommentTime() {
		return existingMaxFetchedCommentTime;
	}

	public void setExistingMaxFetchedCommentTime(Date existingMaxFetchedCommentTime) {
		this.existingMaxFetchedCommentTime = existingMaxFetchedCommentTime;
	}

	public Date getNewMaxFetchedCommentTime() {
		return newMaxFetchedCommentTime;
	}

	public void setNewMaxFetchedCommentTime(Date newMaxFetchedCommentTime) {
		this.newMaxFetchedCommentTime = newMaxFetchedCommentTime;
	}

	public Integer getPagingStatusVersion() {
		return pagingStatusVersion;
	}
	
	public void setPagingStatusVersion(Integer pagingStatusVersion) {
		this.pagingStatusVersion = pagingStatusVersion;
	}

	public Integer getUpdatedPagingStatusVersion() {
		return updatedPagingStatusVersion;
	}

	public void setUpdatedPagingStatusVersion(Integer updatedPagingStatusVersion) {
		this.updatedPagingStatusVersion = updatedPagingStatusVersion;
	}

	public String getSellerAccessToken() {
		return sellerAccessToken;
	}

	public void setSellerAccessToken(String sellerAccessToken) {
		this.sellerAccessToken = sellerAccessToken;
	}

	public String getSellerFacebookId() {
		return sellerFacebookId;
	}

	public void setSellerFacebookId(String sellerFacebookId) {
		this.sellerFacebookId = sellerFacebookId;
	}

}
