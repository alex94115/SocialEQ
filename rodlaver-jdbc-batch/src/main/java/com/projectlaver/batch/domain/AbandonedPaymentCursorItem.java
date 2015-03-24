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

public class AbandonedPaymentCursorItem {

	private Long paymentId;
	private Integer currentPaymentVersion;
	private Integer updatedPaymentVersion;
	private String updatedPaymentBatchStatus;
	private Long listingId;
	private Long inventoryId;
	private Integer paymentQuantity;
	
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public Integer getCurrentPaymentVersion() {
		return currentPaymentVersion;
	}
	public void setCurrentPaymentVersion(Integer currentPaymentVersion) {
		this.currentPaymentVersion = currentPaymentVersion;
	}
	public Integer getUpdatedPaymentVersion() {
		return updatedPaymentVersion;
	}
	public void setUpdatedPaymentVersion(Integer updatedPaymentVersion) {
		this.updatedPaymentVersion = updatedPaymentVersion;
	}
	public String getUpdatedPaymentBatchStatus() {
		return updatedPaymentBatchStatus;
	}
	public void setUpdatedPaymentBatchStatus(String updatedPaymentBatchStatus) {
		this.updatedPaymentBatchStatus = updatedPaymentBatchStatus;
	}
	public Long getListingId() {
		return listingId;
	}
	public void setListingId(Long listingId) {
		this.listingId = listingId;
	}
	public Long getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}
	public Integer getPaymentQuantity() {
		return paymentQuantity;
	}
	public void setPaymentQuantity(Integer paymentQuantity) {
		this.paymentQuantity = paymentQuantity;
	}
	
}
