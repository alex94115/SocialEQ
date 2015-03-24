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

import org.apache.commons.lang3.StringUtils;

public class InitiatedPaymentDTO {
	
	private static final long MINIMUM_REMAINING_DURATION = 90 * 1000; // 90 seconds

	private String payKey;
	private String preapprovalKey;
	private Long listingId;
	private Long inventoryId;
	private Long paymentId;
	private Long payKeyExpiration;
	private String startCheckoutErrorReason;
	private String productDescription;
	private Integer amountCents;
	private String buyerEmail;
	
	private String providerClientToken;
	private String checkoutToken;
	private Boolean doStoreInVault;
	private String paymentErrorReason;
	
	public String getPayKey() {
		return payKey;
	}
	
	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}
	
	public String getPreapprovalKey() {
		return preapprovalKey;
	}
	
	public void setPreapprovalKey(String preapprovalKey) {
		this.preapprovalKey = preapprovalKey;
	}
	
	public Long getListingId() {
		return listingId;
	}
	
	public void setListingId(Long listingId) {
		this.listingId = listingId;
	}
	
	public Long getInventoryId() {
		return this.inventoryId;
	}
	
	public void setInventoryId( Long inventoryId ) {
		this.inventoryId = inventoryId;
	}
	
	public Long getPaymentId() {
		return paymentId;
	}
	
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Long getPayKeyExpiration() {
		return payKeyExpiration;
	}

	public void setPayKeyExpiration(Long payKeyExpiration) {
		this.payKeyExpiration = payKeyExpiration;
	}

	public Boolean hasRemainingValidity() {
		return ( System.currentTimeMillis() + MINIMUM_REMAINING_DURATION ) < this.payKeyExpiration;
	}

	public String getProviderClientToken() {
		return providerClientToken;
	}

	public void setProviderClientToken(String providerClientToken) {
		this.providerClientToken = providerClientToken;
	}

	public String getCheckoutToken() {
		return checkoutToken;
	}

	public void setCheckoutToken(String checkoutToken) {
		this.checkoutToken = checkoutToken;
	}
	
	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Integer getAmountCents() {
		return amountCents;
	}

	public void setAmountCents(Integer amountCents) {
		this.amountCents = amountCents;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public Boolean getDidStartCheckoutSucceed() {
		return !StringUtils.isBlank( this.providerClientToken ) || !StringUtils.isBlank( this.payKey );
	}

	public Boolean getDoStoreInVault() {
		return doStoreInVault;
	}

	public void setDoStoreInVault(Boolean doStoreInVault) {
		this.doStoreInVault = doStoreInVault;
	}

	public String getStartCheckoutErrorReason() {
		return startCheckoutErrorReason;
	}

	public void setStartCheckoutErrorReason(String startCheckoutErrorReason) {
		this.startCheckoutErrorReason = startCheckoutErrorReason;
	}

	public String getPaymentErrorReason() {
		return paymentErrorReason;
	}

	public void setPaymentErrorReason(String paymentErrorReason) {
		this.paymentErrorReason = paymentErrorReason;
	}
	
	
}
