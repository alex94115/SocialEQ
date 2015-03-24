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
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PaymentItem extends SimpleMessageCursorItem {

	// Payment attributes
	private String payKey;
	private String correlationId;
	private String authorizationTransactionId;
	private Long payerId;
	private Long payeeId;
	private Long listingId;
	private Long inventoryId;
	private Long messageId;
	private BigDecimal amount;
	private BigDecimal rodLaverAmount;
	private BigDecimal sellerAmount;
	private String currencyCode;
	private Long shippingAddressId;
	private PaymentStatus newEffectivePaymentStatus;
	
	// vouchers
	private List<VoucherDTO> vouchers;
	
	// Inventory attributes
	private Integer updatedQuantityAvailable;
	private Integer inventoryVersion;
	private Integer newInventoryVersion;

	// User attributes
	private String providerId;
	private String providerUserId;

	
	public String getPayKey() {
		return payKey;
	}
	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}
	public Long getPayerId() {
		return payerId;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public String getAuthorizationTransactionId() {
		return authorizationTransactionId;
	}
	public void setAuthorizationTransactionId(String authorizationTransactionId) {
		this.authorizationTransactionId = authorizationTransactionId;
	}
	public void setPayerId(Long payerId) {
		this.payerId = payerId;
	}
	public Long getPayeeId() {
		return payeeId;
	}
	public void setPayeeId(Long payeeId) {
		this.payeeId = payeeId;
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
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getRodLaverAmount() {
		return rodLaverAmount;
	}
	public void setRodLaverAmount(BigDecimal rodLaverAmount) {
		this.rodLaverAmount = rodLaverAmount;
	}
	public BigDecimal getSellerAmount() {
		return sellerAmount;
	}
	public void setSellerAmount(BigDecimal sellerAmount) {
		this.sellerAmount = sellerAmount;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public Long getShippingAddressId() {
		return shippingAddressId;
	}
	public void setShippingAddressId(Long shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}
	public Integer getUpdatedQuantityAvailable() {
		return updatedQuantityAvailable;
	}
	public void setUpdatedQuantityAvailable(Integer updatedQuantityAvailable) {
		this.updatedQuantityAvailable = updatedQuantityAvailable;
	}
	public PaymentStatus getNewEffectivePaymentStatus() {
		return newEffectivePaymentStatus;
	}
	public void setVouchers( List<VoucherDTO> vouchers ) {
		this.vouchers = vouchers;
	}
	public List<VoucherDTO> getVouchers() {
		return this.vouchers;
	}
	public Integer getInventoryVersion() {
		return inventoryVersion;
	}
	public void setInventoryVersion(Integer inventoryVersion) {
		this.inventoryVersion = inventoryVersion;
	}
	public Integer getNewInventoryVersion() {
		return newInventoryVersion;
	}
	public void setNewInventoryVersion(Integer newInventoryVersion) {
		this.newInventoryVersion = newInventoryVersion;
	}
	public void setNewEffectivePaymentStatus(PaymentStatus newEffectivePaymentStatus) {
		this.newEffectivePaymentStatus = newEffectivePaymentStatus;
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
	@Override
	public String toString() {
	   return ToStringBuilder.reflectionToString(this);
	}

}
