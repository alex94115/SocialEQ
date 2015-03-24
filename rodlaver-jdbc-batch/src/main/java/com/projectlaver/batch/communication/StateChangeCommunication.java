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

import java.util.Currency;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.projectlaver.batch.domain.MessageStateChangeCommunicationsCursorItem;

public abstract class StateChangeCommunication {

	@Autowired
	private ResourceBundleMessageSource messageSource;

	/**
	 * Property values used in the communications
	 */

	@Value("${communications.basePendingPurchaseCheckoutUrl}")
	private String basePendingPurchaseCheckoutUrl;

	@Value("${communications.basePaymentDetailUrl}")
	private String baseTransactionDetailUrl;

	// Default constructor
	public StateChangeCommunication() {}

	// Constructor for injecting the URL's; used for unit testing
	public StateChangeCommunication( ResourceBundleMessageSource messageSource,
									 String pendingPurchaseCheckoutUrl,
									 String basePaymentDetailUrl ) {
		this.messageSource = messageSource;
		this.basePendingPurchaseCheckoutUrl = pendingPurchaseCheckoutUrl;
		this.baseTransactionDetailUrl = basePaymentDetailUrl;
	}

	public abstract String getFormattedMessage( MessageStateChangeCommunicationsCursorItem item );

	public void setMessageSource( ResourceBundleMessageSource messageSource ) {
		this.messageSource = messageSource;
	}

	protected String getCurrencySymbol( Locale locale ) {
		Currency currency = Currency.getInstance( locale );
		return currency.getSymbol();
	}

	protected String formatScreenName( String userId ) {
		String result = "";

		if( userId != null ) {
			int index = userId.indexOf( "/" );
			if( index > 0 ) {
				result = userId.substring( index + 1 );
			} else {
				result = userId;
			}
		}

		return result;
	}
	
	protected String getFormattedMessage( String baseKey, Object[] params, MessageStateChangeCommunicationsCursorItem item ) {
		String messageKey = this.getMessageKey( item.getProviderId(), baseKey );
		return this.messageSource.getMessage( messageKey, params, item.getListingLocale() );
	}

	protected String getBasePendingPurchaseCheckoutUrl() {
		return basePendingPurchaseCheckoutUrl;
	}

	protected String getBaseTransactionDetailUrl() {
		return baseTransactionDetailUrl;
	}

	String getMessageKey( String destinationProviderId, String baseKey ) {
		return StringUtils.join( destinationProviderId, ".", baseKey );
	}

}
