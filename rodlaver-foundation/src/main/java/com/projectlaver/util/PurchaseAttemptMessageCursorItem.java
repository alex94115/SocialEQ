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

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PurchaseAttemptMessageCursorItem {

	// Message attributes
	private Long messageId;
	private String messageStatus;
	private Integer messageVersion;
	
	// Buyer attributes
	private Long buyerId;
	private String providerId;
	private String providerUserId;
	private String preapprovalKey;
	private Long addressId;
	private Boolean isAddressPrimary;
	
	// Seller attributes
	private Long sellerId;
	private String sellerEmail;
	private String sellerUsername;
	private String clientLogoUrl;
	private String clientCampaignImageUrl;
	private String merchantName;
	
	// Listing attributes
	private Long listingId;
	private String listingTitle;
	private String listingLongDescription;
	private String listingTerms;
	private String listingKeyword;
	private ListingType listingType;
	private GoodsType goodsType;
	private BigDecimal listingAmount;
	private BigDecimal listingSellerRevenueRatio;
	private Boolean doUseChainedPayment;
	
	// Inventory attributes
	private Long inventoryId;
	private String inventoryProductCode;
	private String inventoryProductDescription;
	private Integer inventoryTotalQuantity;
	private Integer inventoryRemainingQuantity;
	private Integer inventoryVersion;
	private Integer inventoryNewVersion;
	
	public PurchaseAttemptMessageCursorItem() {}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
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

	public BigDecimal getListingAmount() {
		return listingAmount;
	}

	public void setListingAmount(BigDecimal listingAmount) {
		this.listingAmount = listingAmount;
	}

	public Integer getInventoryTotalQuantity() {
		return inventoryTotalQuantity;
	}

	public void setInventoryTotalQuantity(Integer inventoryTotalQuantity) {
		this.inventoryTotalQuantity = inventoryTotalQuantity;
	}

	public Integer getInventoryRemainingQuantity() {
		return inventoryRemainingQuantity;
	}

	public void setInventoryRemainingQuantity(Integer inventoryRemainingQuantity) {
		this.inventoryRemainingQuantity = inventoryRemainingQuantity;
	}

	public Integer getInventoryVersion() {
		return inventoryVersion;
	}

	public void setInventoryVersion(Integer inventoryVersion) {
		this.inventoryVersion = inventoryVersion;
	}

	public Integer getInventoryNewVersion() {
		return inventoryNewVersion;
	}

	public void setInventoryNewVersion(Integer inventoryNewVersion) {
		this.inventoryNewVersion = inventoryNewVersion;
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

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public Boolean getIsAddressPrimary() {
		return isAddressPrimary;
	}

	public void setIsAddressPrimary(Boolean isAddressPrimary) {
		this.isAddressPrimary = isAddressPrimary;
	}

	public ListingType getListingType() {
		return listingType;
	}

	public void setListingType( ListingType listingType) {
		this.listingType = listingType;
	}

	public Integer getMessageVersion() {
		return messageVersion;
	}

	public void setMessageVersion(Integer messageVersion) {
		this.messageVersion = messageVersion;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
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
	

	public String getSellerUsername() {
		return sellerUsername;
	}

	public void setSellerUsername(String sellerUsername) {
		this.sellerUsername = sellerUsername;
	}

	public String getListingTitle() {
		return listingTitle;
	}

	public void setListingTitle(String listingTitle) {
		this.listingTitle = listingTitle;
	}

	public String getListingKeyword() {
		return listingKeyword;
	}

	public void setListingKeyword(String listingKeyword) {
		this.listingKeyword = listingKeyword;
	}
	
	public BigDecimal getListingSellerRevenueRatio() {
		return listingSellerRevenueRatio;
	}

	public void setListingSellerRevenueRatio(BigDecimal listingSellerRevenueRatio) {
		this.listingSellerRevenueRatio = listingSellerRevenueRatio;
	}
	
	public Boolean getDoUseChainedPayment() {
		return doUseChainedPayment;
	}

	public void setInventoryId( Long inventoryId ) {
		this.inventoryId = inventoryId;
	}
	
	public Long getInventoryId() {
		return this.inventoryId;
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
	
	public String getClientLogoUrl() {
		return clientLogoUrl;
	}

	public void setClientLogoUrl(String clientLogoUrl) {
		this.clientLogoUrl = clientLogoUrl;
	}

	public String getClientCampaignImageUrl() {
		return clientCampaignImageUrl;
	}

	public void setClientCampaignImageUrl(String clientCampaignImageUrl) {
		this.clientCampaignImageUrl = clientCampaignImageUrl;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getInventoryProductCode() {
		return inventoryProductCode;
	}

	public void setInventoryProductCode(String inventoryProductCode) {
		this.inventoryProductCode = inventoryProductCode;
	}

	public String getInventoryProductDescription() {
		return inventoryProductDescription;
	}

	public void setInventoryProductDescription(String inventoryProductDescription) {
		this.inventoryProductDescription = inventoryProductDescription;
	}
	
	@Override
	public String toString() {
	   return ToStringBuilder.reflectionToString(this);
	}

	public String getListingLongDescription() {
		return listingLongDescription;
	}

	public void setListingLongDescription(String listingLongDescription) {
		this.listingLongDescription = listingLongDescription;
	}

	public String getListingTerms() {
		return listingTerms;
	}

	public void setListingTerms(String listingTerms) {
		this.listingTerms = listingTerms;
	}

}
