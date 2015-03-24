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
package com.projectlaver.component;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.projectlaver.domain.Listing;
import com.projectlaver.domain.MessageStateChange;
import com.projectlaver.domain.Payment;
import com.projectlaver.service.EmailService;
import com.projectlaver.service.MessageStateChangeService;
import com.projectlaver.service.PaymentService;
import com.projectlaver.util.GoodsType;
import com.projectlaver.util.ListingType;

@Component
public class OutboundEmailSender {
	
	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private MessageStateChangeService messageStateChangeService;

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private EmailService emailService;
	
	private static final int PAGE_SIZE = 10;
	
	@Scheduled(fixedDelay = 60000L)
	public void sendQueuedEmails() {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "+++sendQueuedEmails()" );
		}
		
		Pageable nextPage = new PageRequest( 0, PAGE_SIZE );
		
		while( true ) {
			
			// request the next page
			Page<MessageStateChange> queuedEmails = this.messageStateChangeService.findQueuedEmails( nextPage );

			this.processQueuedEmails(queuedEmails);
			
			if( queuedEmails.hasNext() ) {

				// move the pointer to the next page
				nextPage = queuedEmails.nextPageable();
				
			} else {

				// no more pages, break out of the while loop
				break;
			}
		}
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "---sendQueuedEmails()" );
		}

	}

	void processQueuedEmails(Page<MessageStateChange> queuedEmails) {
		
		for( MessageStateChange msc : queuedEmails ) {

			if( this.doSendToBuyer( msc ) ) {
				Boolean didSend = this.emailService.sendBuyerPurchaseReceipt( msc, Locale.US );

				// record as sent if succeeded
				if( didSend ) {
					msc.setAreBuyerCommunicationsComplete( true );
					msc = this.messageStateChangeService.update( msc );
				}
			}
			
			if( this.doSendToSeller( msc ) ) {
				Boolean didSend = this.emailService.sendSellerSaleReceipt( msc, Locale.US );

				// record as sent if succeeded
				if( didSend ) {
					msc.setAreSellerCommunicationsComplete( true );
					this.messageStateChangeService.update( msc );
				}
			}
		}
	}
	
	Boolean doSendToBuyer( MessageStateChange msc ) {
		return( msc.getDoesRequireBuyerCommunication() && !msc.getAreBuyerCommunicationsComplete() );
	}
	
	Boolean doSendToSeller( MessageStateChange msc ) {
		return( msc.getDoesRequireSellerCommunication() && !msc.getAreSellerCommunicationsComplete() );
	}
}
