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
package com.projectlaver.service;

import org.easymock.IArgumentMatcher;

import com.projectlaver.domain.Payment;

/**
 * Lets us use a mock expect( paymentService.save( eqPayment( somePayment ) ) )  
 * expression and compare the actual payment argument with an expectation.
 * 
 * In practice, we are intercepting a call with a Payment that would go into the database
 * and instead just confirming it has the expected attributes.
 * 
 * Presently this is only comparing the big decimal amounts & the current effective payment status's 
 * status field. Other attributes of the payment and other effective payment statuses are not being
 * taken into account.
 *
 */
public class PaymentMatcher implements IArgumentMatcher {

	private Payment expected;
	
	public PaymentMatcher( Payment expected) {
        this.expected = expected;
    }

    @Override
    public void appendTo(StringBuffer buffer) {
        buffer.append("eqPayment("); 
        buffer.append(expected.getCurrentEffectivePaymentStatus()); 
        buffer.append(")\""); 
    }

    @Override
    public boolean matches(Object actual) {
    	
    	if( !(actual instanceof Payment )) {
    		return false;
    	}
    	
    	if( !expected.getClass().equals(actual.getClass()) ) {
    		return false;
    	}
    	
    	Payment actualPayment = ( Payment )actual;
    	
    	if( expected.getTotalAmount().compareTo( actualPayment.getTotalAmount() ) != 0 ) {
    		return false;
    	}
    	
    	if( expected.getSellerAmount().compareTo( actualPayment.getSellerAmount() ) != 0 ) {
    		return false;
    	}
    	
    	if( expected.getRodLaverAmount().compareTo( actualPayment.getRodLaverAmount() ) != 0 ) {
    		return false;
    	}
    	
    	if( !expected.getCurrentEffectivePaymentStatus().getStatus().equals( ( actualPayment.getCurrentEffectivePaymentStatus().getStatus() ) ) ) {
    		return false;
    	}
    		
    	return true;
    }
}
