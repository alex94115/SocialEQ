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

public class VoucherDTO {

	/**
	 *  Inputs
	 */
	
	// Users.voucherLogoUrl
	private String clientLogoUrl;
	
	// Users.voucherCampaignUrl
	private String clientCampaignImageUrl;
	
	// Users.merchantName
	private String merchantName;
	
	// Listings.title
	private String listingTitle;
	
	// Inventories.productCode
	private String productCode;
	
	// Inventories.productDescription
	private String productDescription;
	
	// Listings.long_description
	private String voucherDetails;
	
	// Listings.termsAndConditions
	private String voucherTerms;

	/**
	 *  Outputs
	 */

	private String serialNumber;
	private String salt;
	private String filename;
	private VoucherStatus status;
	
	
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public VoucherStatus getStatus() {
		return status;
	}
	public void setStatus(VoucherStatus status) {
		this.status = status;
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
	public String getVoucherDetails() {
		return voucherDetails;
	}
	public void setVoucherDetails(String voucherDetails) {
		this.voucherDetails = voucherDetails;
	}
	public String getVoucherTerms() {
		return voucherTerms;
	}
	public void setVoucherTerms(String voucherTerms) {
		this.voucherTerms = voucherTerms;
	}
	public String getListingTitle() {
		return listingTitle;
	}
	public void setListingTitle(String listingTitle) {
		this.listingTitle = listingTitle;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	
	// Return just the listing title if the product code is DEFAULT. Otherwise append the product description to the title.
	public String getVoucherTitle() {
		
		String voucherTitle = null;
		if( this.productCode == null || this.productCode.equals( "DEFAULT" )) {
			voucherTitle = this.listingTitle;
		} else {
			voucherTitle = String.format( "%s -- %s", this.listingTitle, this.productDescription );
		}
		
		return voucherTitle;
	}
}
