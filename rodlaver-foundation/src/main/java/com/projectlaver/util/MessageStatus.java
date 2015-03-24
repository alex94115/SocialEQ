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

public enum MessageStatus {
	// Applicable to 'Buying' messages
	PROCESSING, // Initially
	IRRELEVANT, // If it's not a 'buying' message
	PENDING_USER_REGISTRATION, // If the buyer is not a Rod Laver user (don't think is this used for giveaways anymore)
	PENDING_MEANS_OF_PAYMENT, // If the buyer does not have an active payment mechanism 
	PENDING_LISTING_EXPIRY, // Used for giveaway entrants before a winner is chosen
	FAILED_SOLD_OUT, // If the sale is sold out
	FAILED_INACTIVE_SALE, // If the sale has been canceled / expired
	FAILED_DUPLICATE_PURCHASE, // If the user attempts to buy the same thing twice
	FAILED_DUPLICATE_OPT_IN, // If the user attempts to opt-in twice
	FAILED_LOST_GIVEAWAY, // For users who enter but don't win a giveaway
	PAYMENT_PROCESSING, // After the payment has been created but before it completes / fails
	PAYMENT_PENDING, // Awaiting success / failure
	PAYMENT_FAILED, // If the payment failed
	PENDING_SHIPPING_ADDRESS, // For physical goods where a shipping address is required
	PENDING_SHIPMENT, // If we have the shipping address but haven't been notified by the seller that it's been shipped
	
	// Applicable to 'Selling' messages
	ACTIVE,
	
	// Applicable to both types of messages
	CANCELED, 
	COMPLETED, // Upon completion without error
	PROCESSING_ERROR
	
}
