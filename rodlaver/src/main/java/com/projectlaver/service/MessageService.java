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

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectlaver.domain.Message;
import com.projectlaver.repository.BulkOperationsRepository;
import com.projectlaver.repository.MessageRepository;


@Service
@Transactional(readOnly = false)
public class MessageService {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private MessageRepository repository;

	@Autowired UserService userService;

	@Autowired
	private BulkOperationsRepository bulkMessageOperationsRepository;

	/**
	 * Uses JDBC to insert a listing message (without Hibernate).
	 *
	 */
	@Transactional(readOnly = false)
	public Message insertListingMessage( Message message  ) {
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "In the MessageService insertListingMessage( Message) method. " );
			this.logger.debug( "Message: " + ToStringBuilder.reflectionToString( message ));
		}
		return this.bulkMessageOperationsRepository.insertListingMessage( message );
	}
	
	@Transactional(readOnly = false)
	public Message update( Message message ) {
		return this.repository.save( message );
	}

	/**
	 * Uses JDBC to update the message (without Hibernate).
	 *
	 */
	@Transactional(readOnly = false)
	public Boolean updateBypassHibernate( Message message  ) {
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "In the MessageService update( Message) method. " );
			this.logger.debug( "Message: " + ToStringBuilder.reflectionToString( message ));
		}
		return this.bulkMessageOperationsRepository.updateMessage( message );
	}

	@Transactional(readOnly = true)
	public Message findOne( Long id ) {
		return this.repository.findOne( id );
	}

	@Transactional(readOnly = true)
	public Message findByPaymentId( Long paymentId ) {
		return this.repository.findByPaymentId( paymentId );
	}

	@Transactional(readOnly = true)
	public Set<Message> findListingMessagesByListingId( Long listingId ) {
		return this.repository.findListingMessagesByListingId( listingId );
	}
	
	
	@Transactional(readOnly = true)
	public Page<Message> findReplyActivityByListingId( Long listingId, Pageable p ) {
		Page<Message> result =  this.repository.findReplyActivityByListingId( listingId, p );
		
		// force it to load the Users (join fetch with the sub select in the repository wasn't working, so this is plan b)
		List<Message> messages = result.getContent();
		for( Message message : messages ) {
			if( message.getUser() != null ) {
				String username = message.getUser().getUsername();
			}
		}
		
		return result;
	}
	
	@Transactional(readOnly = true)
	public Message findPendingMeansOfPaymentMessage( Long listingId, Long userId ) {
		Message result = this.repository.findPendingMeansOfPaymentMessage( listingId, userId );
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "findPendingMeansOfPaymentMessage returns: %s", ToStringBuilder.reflectionToString( result ) ) );
		}

		return result;
	}
	
	@Transactional(readOnly = false)
	public Integer unblockPendingRegistrationPurchaseMessages( String providerId, String providerUserId ) {
			
		// unblock any associated 'pending registration' messages
		Integer result = this.bulkMessageOperationsRepository.unblockPendingRegistrationPurchaseMessages( providerId, providerUserId );
		
		return result;
	}

}
