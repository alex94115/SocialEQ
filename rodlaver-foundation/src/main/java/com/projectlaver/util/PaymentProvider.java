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
import java.util.Map;

import com.projectlaver.domain.Payment;

public interface PaymentProvider {

	AttemptPaymentDTO initiateCheckout( AttemptPaymentDTO dto );

	BigDecimal getGrossPaymentAmount( BigDecimal grossPaymentAmount );
	
	BigDecimal getNetSellerAmount( BigDecimal grossAmount, BigDecimal sellerRevenueRatio );
	
	String getMemo( String sellerUsername, String listingTitle, String listingKeyword );

	Map<String, Object> completeCheckout( InitiatedPaymentDTO inProgressPayment, Payment payment );

	AttemptPaymentDTO attemptPaymentWithPreapprovalKey( AttemptPaymentDTO dto );

	Map<String, String> requestPreapproval( String returnUrl, String cancelUrl );

	Map<String, Object> completePreapproval( String preapprovalKey );
}
