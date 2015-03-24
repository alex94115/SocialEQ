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
package com.projectlaver.batch.communication;

import org.springframework.context.support.ResourceBundleMessageSource;

import com.projectlaver.batch.domain.MessageStateChangeCommunicationsCursorItem;

public class BuyerPendingUserRegistration extends StateChangeCommunication {

	public BuyerPendingUserRegistration() {}
	
	// Constructor for injecting the URL's; used for unit testing
	public BuyerPendingUserRegistration( 
			ResourceBundleMessageSource messageSource,
			String pendingPurchaseCheckoutUrl,
			String baseTransactionDetailUrl) {
		super( messageSource, pendingPurchaseCheckoutUrl, baseTransactionDetailUrl );
	}
	
	
	@Override
	public String getFormattedMessage( MessageStateChangeCommunicationsCursorItem item) {
		String result = super.getFormattedMessage( "communications.dm.buyer.pending.user.registration", 
				new Object[] { super.getBasePendingPurchaseCheckoutUrl() + item.getListingId() }, 
				item );
		return result;
	}

}
