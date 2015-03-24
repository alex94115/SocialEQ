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

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.projectlaver.util.GoodsType;
import com.projectlaver.util.ListingType;

public class MessageStateChangeCommunicationsCursorItem {

	private Long messageStateChangeId;
	private Integer messageStateChangeVersion;
	private Integer newMessageStateChangeVersion;
	private String messageStateChangeStatus;
	private Long messageId;
	private String messageTwitterId;
	private String previousMessageState;
	private String newMessageState;
	private String recipientType;
	private String providerId;
	private Long toProviderUserId;
	private String toDisplayName;
	private String fromAccessToken;
	private String fromSecret;
	private String buyerUserId;
	private String sellerUserId;
	private Long listingId;
	private Long paymentId;
	private BigDecimal totalAmount;
	private BigDecimal sellerAmount;
	private Long sellerId;
	private Boolean hasDigitalContent;
	private ListingType listingType;
	private GoodsType goodsType;
	private Locale listingLocale;
	private Boolean didCommunicationsComplete;
	private Integer sellerMaxTweetsPerTenSec;
	private Integer sellerMaxTweetsPerFiveMin;
	private Integer sellerMaxTweetsPerHr;
	private Integer sellerMaxTweetsPerTwentyFourHr;

	public Long getMessageStateChangeId() {
		return messageStateChangeId;
	}

	public Integer getMessageStateChangeVersion() {
		return messageStateChangeVersion;
	}

	public Integer getNewMessageStateChangeVersion() {
		return newMessageStateChangeVersion;
	}

	public String getMessageStateChangeStatus() {
		return messageStateChangeStatus;
	}

	public Long getMessageId() {
		return messageId;
	}

	public String getPreviousMessageState() {
		return previousMessageState;
	}

	public String getNewMessageState() {
		return newMessageState;
	}

	public String getProviderId() {
		return providerId;
	}

	public Long getToProviderUserId() {
		return toProviderUserId;
	}

	public void setMessageStateChangeId( Long messageStateChangeId ) {
		this.messageStateChangeId = messageStateChangeId;

	}

	public void setMessageStateChangeVersion( Integer messageStateChangeVersion ) {
		this.messageStateChangeVersion = messageStateChangeVersion;
	}

	public void setNewMessageStateChangeVersion( Integer newMessageStateChangeVersion ) {
		this.newMessageStateChangeVersion = newMessageStateChangeVersion;
	}

	public void setMessageStateChangeStatus(String messageStateChangeStatus ) {
		this.messageStateChangeStatus = messageStateChangeStatus;
	}

	public void setMessageId( Long messageId ) {
		this.messageId = messageId;
	}

	public void setPreviousMessageState(String previousMessageState) {
		this.previousMessageState = previousMessageState;
	}

	public void setNewMessageState(String newMessageState ) {
		this.newMessageState = newMessageState;
	}

	public void setProviderId(String destinationProviderId ) {
		this.providerId = destinationProviderId;
	}

	public void setToProviderUserId( Long toProviderUserId ) {
		this.toProviderUserId = toProviderUserId;
	}

	public String getRecipientType() {
		return recipientType;
	}

	public void setRecipientType(String recipientType) {
		this.recipientType = recipientType;
	}

	public Boolean getDidCommunicationsComplete() {
		return didCommunicationsComplete;
	}

	public void setDidCommunicationsComplete(Boolean didCommunicationsComplete) {
		this.didCommunicationsComplete = didCommunicationsComplete;
	}

	public String getBuyerUserId() {
		return buyerUserId;
	}

	public void setBuyerUserId(String buyerUserId) {
		this.buyerUserId = buyerUserId;
	}

	public Long getListingId() {
		return listingId;
	}

	public void setListingId(Long listingId) {
		this.listingId = listingId;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public BigDecimal getSellerAmount() {
		return sellerAmount;
	}

	public void setSellerAmount(BigDecimal sellerAmount) {
		this.sellerAmount = sellerAmount;
	}

	public ListingType getListingType() {
		return listingType;
	}
	
	public GoodsType getGoodsType() {
		return goodsType;
	}
	
	public void setGoodsType( GoodsType goodsType ) {
		this.goodsType = goodsType;
	}

	public Boolean getHasDigitalContent() {
		return hasDigitalContent;
	}

	public void setHasDigitalContent(Boolean hasDigitalContent) {
		this.hasDigitalContent = hasDigitalContent;
	}

	public void setListingType( ListingType listingType) {
		this.listingType = listingType;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Locale getListingLocale() {
		return listingLocale;
	}

	public void setListingLocale(Locale listingLocale) {
		this.listingLocale = listingLocale;
	}

	public String getSellerUserId() {
		return sellerUserId;
	}

	public void setSellerUserId(String sellerUserId) {
		this.sellerUserId = sellerUserId;
	}

	public String getFromAccessToken() {
		return this.fromAccessToken;
	}

	public void setFromAccessToken(String destinationAccessToken ) {
		this.fromAccessToken = destinationAccessToken;
	}

	public String getFromSecret() {
		return this.fromSecret;
	}

	public void setFromSecret(String fromSecret) {
		this.fromSecret = fromSecret;
	}

	public String getMessageTwitterId() {
		return messageTwitterId;
	}

	public void setMessageTwitterId( String messageTwitterId) {
		this.messageTwitterId = messageTwitterId;
	}
	
	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getSellerMaxTweetsPerTenSec() {
		return sellerMaxTweetsPerTenSec;
	}

	public void setSellerMaxTweetsPerTenSec(Integer sellerMaxTweetsPerTenSec) {
		this.sellerMaxTweetsPerTenSec = sellerMaxTweetsPerTenSec;
	}

	public Integer getSellerMaxTweetsPerFiveMin() {
		return sellerMaxTweetsPerFiveMin;
	}

	public void setSellerMaxTweetsPerFiveMin(Integer sellerMaxTweetsPerFiveMin) {
		this.sellerMaxTweetsPerFiveMin = sellerMaxTweetsPerFiveMin;
	}

	public Integer getSellerMaxTweetsPerHr() {
		return sellerMaxTweetsPerHr;
	}

	public void setSellerMaxTweetsPerHr(Integer sellerMaxTweetsPerHr) {
		this.sellerMaxTweetsPerHr = sellerMaxTweetsPerHr;
	}

	public Integer getSellerMaxTweetsPerTwentyFourHr() {
		return sellerMaxTweetsPerTwentyFourHr;
	}

	public void setSellerMaxTweetsPerTwentyFourHr( Integer sellerMaxTweetsPerTwentyFourHr ) {
		this.sellerMaxTweetsPerTwentyFourHr = sellerMaxTweetsPerTwentyFourHr;
	}
	
	public String getToDisplayName() {
		return toDisplayName;
	}

	public void setToDisplayName(String toDisplayName) {
		this.toDisplayName = toDisplayName;
	}

	@Override
	public String toString() {
	   return ToStringBuilder.reflectionToString(this);
	}

}
