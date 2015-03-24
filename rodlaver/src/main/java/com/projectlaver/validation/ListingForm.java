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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.projectlaver.util.GoodsType;
import com.projectlaver.util.ListingSubType;
import com.projectlaver.util.ListingType;

public class ListingForm {
	
	@NotNull
	@Size(min=1)
	private List<String> publishTo = new ArrayList<String>();
	
	// default these so we don't have to worry about null in the UI
	private Boolean isRevenueCampaign = true;
	private Boolean isInstantGiveaway = false;
	private Boolean isFundraiser = false;
	private Boolean isFundraiserReward = false;
	private Boolean isPhysicalGood = true;
	private Boolean isDigitalDownload = true;
	private Boolean isLimitedQuantity = false;
	
	@NotNull
	@Size(min=1)
	private String title; 
	
	@NotNull
	@Size(min=1,max=512)
	private String longDescription;
	
	private String termsAndConditions;
	
	private String backgroundImageUrl;
	
	private BigDecimal price;
	
	private Integer quantity;
	
	@NotNull
	private String displayName;
	
	@NotNull
	@Size(min=1)
	private String message;

	@NotNull
	@Size(min=1)
	private String keyword;
	
	// declare these just so we can show validation errors on the UI
	private Object image;
	private Object digitalContent;

	public ListingForm() {}
	
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice( BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getLongDescription() {
		return this.longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getBackgroundImageUrl() {
		return this.backgroundImageUrl;
	}

	public void setBackgroundImageUrl(String backgroundImageUrl) {
		this.backgroundImageUrl = backgroundImageUrl;
	}

	public Boolean getIsRevenueCampaign() {
		return isRevenueCampaign;
	}

	public void setIsRevenueCampaign(Boolean isRevenueCampaign) {
		this.isRevenueCampaign = isRevenueCampaign;
	}

	public Boolean getIsFundraiser() {
		return isFundraiser;
	}

	public void setIsFundraiser(Boolean isFundraiser) {
		this.isFundraiser = isFundraiser;
	}

	public Boolean getIsPhysicalGood() {
		return isPhysicalGood;
	}

	public void setIsPhysicalGood(Boolean isPhysicalGood) {
		this.isPhysicalGood = isPhysicalGood;
	}
	
	public Boolean getIsDigitalDownload() {
		return isDigitalDownload;
	}

	public void setIsDigitalDownload(Boolean isDigitalDownload) {
		this.isDigitalDownload = isDigitalDownload;
	}

	public Boolean getIsFundraiserReward() {
		return isFundraiserReward;
	}

	public void setIsFundraiserReward(Boolean isFundraiserReward) {
		this.isFundraiserReward = isFundraiserReward;
	}

	public List<String> getPublishTo() {
		return publishTo;
	}

	public void setPublishTo(List<String> publishTo) {
		this.publishTo = publishTo;
	}

	public Boolean getIsLimitedQuantity() {
		return isLimitedQuantity;
	}

	public void setIsLimitedQuantity(Boolean isLimitedQuantity) {
		this.isLimitedQuantity = isLimitedQuantity;
	}

	public Boolean getIsInstantGiveaway() {
		return isInstantGiveaway;
	}

	public void setIsInstantGiveaway(Boolean isInstantGiveaway) {
		this.isInstantGiveaway = isInstantGiveaway;
	}
	
	public Object getImage() {
		return image;
	}

	public void setImage(Object image) {
		this.image = image;
	}

	public Object getDigitalContent() {
		return digitalContent;
	}

	public void setDigitalContent(Object digitalContent) {
		this.digitalContent = digitalContent;
	}
	
	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	public String getDisplayName() {
		return this.displayName;
	}
	
	public void setDisplayName( String displayName ) {
		this.displayName = displayName;
	}
	
	/**
	 * TODO this does not yet support donations
	 */
	public ListingType getListingType() {
		
		ListingType result = null;
		
		if( this.getIsRevenueCampaign() ) {
			result = ListingType.SELLING;
			
		// Otherwise this is a giveaway
		} else {
			result = ListingType.CAMPAIGN;
		}
		
		return result;
	}
	
	public GoodsType getGoodsType() {
		
		GoodsType result = null;
		
		if( this.isPhysicalGood ) {
			result = GoodsType.PHYSICAL;
		} else if( this.isDigitalDownload ) {
			result = GoodsType.DIGITAL;
		} else {
			result = GoodsType.VOUCHER;
		}
		
		return result;
	}
	
	/**
	 * This sub type is the giveaway type; it's only applicable to campaign listings.
	 */
	public ListingSubType getListingSubType() {
		ListingSubType result = null;
		
		if( !this.getIsRevenueCampaign() ) {
			if( this.getIsInstantGiveaway() ) {
				result = ListingSubType.INSTANT;
			} else {
				result = ListingSubType.DRAWING;
			}
		} else {
			// there is no sub-type
		}
		
		return result;
	}
	
}
