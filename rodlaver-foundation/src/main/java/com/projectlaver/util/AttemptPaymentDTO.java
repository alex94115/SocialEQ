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

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

public class AttemptPaymentDTO {
	
	// Payment attributes
	private BigDecimal paymentAmount;
	private BigDecimal sellerRevenueRatio;
	private BigDecimal grossPaymentAmount;
	private BigDecimal netRodLaverAmount;
	private BigDecimal netSellerAmount;
	private Boolean doUseChainedPayment;
	private PaymentStatus paymentStatus;
	private String payKey;
	private String correlationId;
	private String authorizationTransactionId;
	private String memo;
	
	// Seller attributes
	private Long sellerId;
	private String sellerEmail;
	
	// Listing attributes
	private ListingType listingType;
	private GoodsType goodsType;

	// Message attributes
	private Long replyId;
	
	// Payment attempt attributes
	private String cancelUrl;
	private String returnUrl;
	private Integer payKeyDurationMinutes;
	private String preapprovalKey;
	private String preapprovalCorrelationId;
	private String providerClientToken;

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public BigDecimal getSellerRevenueRatio() {
		return sellerRevenueRatio;
	}
	public void setSellerRevenueRatio(BigDecimal sellerRevenueRatio) {
		this.sellerRevenueRatio = sellerRevenueRatio;
	}
	public BigDecimal getGrossPaymentAmount() {
		return grossPaymentAmount;
	}
	public void setGrossPaymentAmount(BigDecimal grossPaymentAmount) {
		this.grossPaymentAmount = grossPaymentAmount;
	}
	public BigDecimal getNetRodLaverAmount() {
		return netRodLaverAmount;
	}
	public void setNetRodLaverAmount(BigDecimal netRodLaverAmount) {
		this.netRodLaverAmount = netRodLaverAmount;
	}
	public BigDecimal getNetSellerAmount() {
		return netSellerAmount;
	}
	public void setNetSellerAmount(BigDecimal netSellerAmount) {
		this.netSellerAmount = netSellerAmount;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerEmail() {
		return sellerEmail;
	}
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}
	public ListingType getListingType() {
		return listingType;
	}
	public void setListingType( ListingType listingType) {
		this.listingType = listingType;
	}
	public String getPayKey() {
		return payKey;
	}
	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public String getCancelUrl() {
		return cancelUrl;
	}
	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getPayKeyDurationMinutes() {
		return payKeyDurationMinutes;
	}
	public void setPayKeyDurationMinutes(Integer payKeyDurationMinutes) {
		this.payKeyDurationMinutes = payKeyDurationMinutes;
	}
	public String getPreapprovalKey() {
		return preapprovalKey;
	}
	public void setPreapprovalKey(String preapprovalKey) {
		this.preapprovalKey = preapprovalKey;
	}
	public String getPreapprovalCorrelationId() {
		return preapprovalCorrelationId;
	}
	public void setPreapprovalCorrelationId(String preapprovalCorrelationId) {
		this.preapprovalCorrelationId = preapprovalCorrelationId;
	}
	public Boolean getDoUseChainedPayment() {
		return doUseChainedPayment;
	}
	public void setDoUseChainedPayment(Boolean doUseChainedPayment) {
		this.doUseChainedPayment = doUseChainedPayment;
	}
	public GoodsType getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
	}
	public String getProviderClientToken() {
		return providerClientToken;
	}
	public void setProviderClientToken(String providerClientToken) {
		this.providerClientToken = providerClientToken;
	}
	public Boolean getDidInitiateCheckoutSucceed() {
		return !StringUtils.isBlank( this.providerClientToken ) || !StringUtils.isBlank( this.payKey ); 
	}
	public String getAuthorizationTransactionId() {
		return authorizationTransactionId;
	}
	public void setAuthorizationTransactionId(String authorizationTransactionId) {
		this.authorizationTransactionId = authorizationTransactionId;
	}
	public Long getReplyId() {
		return replyId;
	}
	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}
}
