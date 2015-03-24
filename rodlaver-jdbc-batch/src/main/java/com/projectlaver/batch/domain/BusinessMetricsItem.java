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

public class BusinessMetricsItem {
	
	private Long sellerId;
	private Long listingId;
	private Long metricsExecutionId;
	private Integer responses; 
	private Integer retweets;
	private Integer twitterResponses;
	private Integer facebookResponses;
	private Integer hashtagResponses;
	private Integer twitterHashtagResponses;
	private Integer facebookHashtagResponses;
	private Integer optIns;
	private Integer twitterOptIns;
	private Integer facebookOptIns;
	private Integer payments; 
	private Integer twitterPayments;
	private Integer facebookPayments;
	private BigDecimal grossSales;
	private BigDecimal twitterGrossSales;
	private BigDecimal facebookGrossSales;
	private BigDecimal netSales;
	private BigDecimal twitterNetSales;
	private BigDecimal facebookNetSales;
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public Long getListingId() {
		return listingId;
	}
	public void setListingId(Long listingId) {
		this.listingId = listingId;
	}
	public Long getMetricsExecutionId() {
		return metricsExecutionId;
	}
	public void setMetricsExecutionId(Long metricsExecutionId) {
		this.metricsExecutionId = metricsExecutionId;
	}
	public Integer getResponses() {
		return responses;
	}
	public void setResponses(Integer responses) {
		this.responses = responses;
	}
	public Integer getRetweets() {
		return retweets;
	}
	public void setRetweets(Integer retweets) {
		this.retweets = retweets;
	}
	public Integer getTwitterResponses() {
		return twitterResponses;
	}
	public void setTwitterResponses(Integer twitterResponses) {
		this.twitterResponses = twitterResponses;
	}
	public Integer getFacebookResponses() {
		return facebookResponses;
	}
	public void setFacebookResponses(Integer facebookResponses) {
		this.facebookResponses = facebookResponses;
	}
	public Integer getHashtagResponses() {
		return hashtagResponses;
	}
	public void setHashtagResponses(Integer hashtagResponses) {
		this.hashtagResponses = hashtagResponses;
	}
	public Integer getTwitterHashtagResponses() {
		return twitterHashtagResponses;
	}
	public void setTwitterHashtagResponses(Integer twitterHashtagResponses) {
		this.twitterHashtagResponses = twitterHashtagResponses;
	}
	public Integer getFacebookHashtagResponses() {
		return facebookHashtagResponses;
	}
	public void setFacebookHashtagResponses(Integer facebookHashtagResponses) {
		this.facebookHashtagResponses = facebookHashtagResponses;
	}
	public Integer getOptIns() {
		return optIns;
	}
	public void setOptIns(Integer optIns) {
		this.optIns = optIns;
	}
	public Integer getTwitterOptIns() {
		return twitterOptIns;
	}
	public void setTwitterOptIns(Integer twitterOptIns) {
		this.twitterOptIns = twitterOptIns;
	}
	public Integer getFacebookOptIns() {
		return facebookOptIns;
	}
	public void setFacebookOptIns(Integer facebookOptIns) {
		this.facebookOptIns = facebookOptIns;
	}
	public Integer getPayments() {
		return payments;
	}
	public void setPayments(Integer payments) {
		this.payments = payments;
	}
	public Integer getTwitterPayments() {
		return twitterPayments;
	}
	public void setTwitterPayments(Integer twitterPayments) {
		this.twitterPayments = twitterPayments;
	}
	public Integer getFacebookPayments() {
		return facebookPayments;
	}
	public void setFacebookPayments(Integer facebookPayments) {
		this.facebookPayments = facebookPayments;
	}
	public BigDecimal getGrossSales() {
		return grossSales;
	}
	public void setGrossSales(BigDecimal grossSales) {
		this.grossSales = grossSales;
	}
	public BigDecimal getTwitterGrossSales() {
		return twitterGrossSales;
	}
	public void setTwitterGrossSales(BigDecimal twitterGrossSales) {
		this.twitterGrossSales = twitterGrossSales;
	}
	public BigDecimal getFacebookGrossSales() {
		return facebookGrossSales;
	}
	public void setFacebookGrossSales(BigDecimal facebookGrossSales) {
		this.facebookGrossSales = facebookGrossSales;
	}
	public BigDecimal getNetSales() {
		return netSales;
	}
	public void setNetSales(BigDecimal netSales) {
		this.netSales = netSales;
	}
	public BigDecimal getTwitterNetSales() {
		return twitterNetSales;
	}
	public void setTwitterNetSales(BigDecimal twitterNetSales) {
		this.twitterNetSales = twitterNetSales;
	}
	public BigDecimal getFacebookNetSales() {
		return facebookNetSales;
	}
	public void setFacebookNetSales(BigDecimal facebookNetSales) {
		this.facebookNetSales = facebookNetSales;
	}
	
}
