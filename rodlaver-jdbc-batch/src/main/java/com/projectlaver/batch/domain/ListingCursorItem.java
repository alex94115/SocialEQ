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

public class ListingCursorItem {

	private Long listingId;
	private Boolean doesRequireFacebookPosting;
	private Boolean doesRequireTwitterPosting;
	private Boolean isDigitalGiveaway;
	private Integer listingVersion;
	private Integer newListingVersion;
	private Boolean isExpired;
	private Integer quantity;
	private Integer giveawaySeed;
	private String newListingStatus;
	private String batchProcessor;

	public Long getListingId() {
		return listingId;
	}

	public void setListingId(Long listingId) {
		this.listingId = listingId;
	}

	public Integer getListingVersion() {
		return listingVersion;
	}

	public void setListingVersion(Integer listingVersion) {
		this.listingVersion = listingVersion;
	}

	public Integer getNewListingVersion() {
		return newListingVersion;
	}

	public void setNewListingVersion(Integer newListingVersion) {
		this.newListingVersion = newListingVersion;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getGiveawaySeed() {
		return giveawaySeed;
	}

	public void setGiveawaySeed(Integer giveawaySeed) {
		this.giveawaySeed = giveawaySeed;
	}

	public String getNewListingStatus() {
		return newListingStatus;
	}

	public void setNewListingStatus(String newListingStatus) {
		this.newListingStatus = newListingStatus;
	}

	public Boolean getIsDigitalGiveaway() {
		return isDigitalGiveaway;
	}

	public void setIsDigitalGiveaway(Boolean isDigitalGiveaway) {
		this.isDigitalGiveaway = isDigitalGiveaway;
	}

	public Boolean getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Boolean isExpired) {
		this.isExpired = isExpired;
	}

	public String getBatchProcessor() {
		return batchProcessor;
	}

	public void setBatchProcessor(String batchProcessor) {
		this.batchProcessor = batchProcessor;
	}

	public Boolean getDoesRequireFacebookPosting() {
		return doesRequireFacebookPosting;
	}

	public void setDoesRequireFacebookPosting(Boolean doesRequireFacebookPosting) {
		this.doesRequireFacebookPosting = doesRequireFacebookPosting;
	}

	public Boolean getDoesRequireTwitterPosting() {
		return doesRequireTwitterPosting;
	}

	public void setDoesRequireTwitterPosting(Boolean doesRequireTwitterPosting) {
		this.doesRequireTwitterPosting = doesRequireTwitterPosting;
	}
	
}
