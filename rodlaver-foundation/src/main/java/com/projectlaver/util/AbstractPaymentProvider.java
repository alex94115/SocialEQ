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
import java.math.RoundingMode;
import java.util.Map;

import com.projectlaver.domain.Payment;

public abstract class AbstractPaymentProvider implements PaymentProvider {

	@Override
	public abstract AttemptPaymentDTO initiateCheckout( AttemptPaymentDTO dto );

	@Override
	public abstract Map<String, Object> completeCheckout( InitiatedPaymentDTO inProgressPayment, Payment payment );

	@Override
	public abstract AttemptPaymentDTO attemptPaymentWithPreapprovalKey( AttemptPaymentDTO dto );

	@Override
	public abstract Map<String, Object> completePreapproval( String preapprovalKey );

	@Override
	public abstract Map<String, String> requestPreapproval( String returnUrl, String cancelUrl );
	
	
	/**
	 * Format the payment memo line that will show up on the payment receipt email / statement.
	 */
	@Override
	public String getMemo( String sellerUsername, String listingTitle, String listingKeyword ) {
		
		StringBuilder builder = new StringBuilder( "Payment to " );
		builder.append( sellerUsername );
		builder.append( " for " );
		builder.append( listingTitle );
		builder.append( " using " );
		builder.append( listingKeyword );
		builder.append( " via SocialEQ." );
		
		return builder.toString();
	}
	
	/**
	 * Calculate the net amount for the seller. Since RodLaver fee is 
	 * currently a fixed 10%, return a seller amount of 90% of the gross.
	 * 
	 * @param grossAmount
	 * @return
	 */
	@Override
	public BigDecimal getNetSellerAmount( BigDecimal grossAmount, BigDecimal sellerRevenueRatio ) {
		BigDecimal unrounded = grossAmount.multiply( sellerRevenueRatio );
		return unrounded.setScale( 2, RoundingMode.CEILING );
	}

	/**
	 * Calculate gross payment amount by simply rounding the extended price to two decimal places.
	 * 
	 * @param grossAmount
	 * @return
	 */
	@Override
	public BigDecimal getGrossPaymentAmount( BigDecimal grossPaymentAmount ) {
		return grossPaymentAmount.setScale( 2, RoundingMode.CEILING);
	}

}
