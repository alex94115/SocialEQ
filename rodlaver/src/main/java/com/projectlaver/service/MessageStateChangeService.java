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

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectlaver.domain.Message;
import com.projectlaver.domain.MessageStateChange;
import com.projectlaver.domain.User;
import com.projectlaver.repository.MessageStateChangeRepository;

@Service
@Transactional(readOnly = false)
public class MessageStateChangeService {
	
	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private MessageStateChangeRepository repository;
	
	@Transactional(readOnly = false)
	public MessageStateChange insert( MessageStateChange msc ) {
		return this.repository.save( msc );
	}
	
	@Transactional(readOnly = false)
	public MessageStateChange update( MessageStateChange msc ) {
		return this.repository.save( msc );
	}
	
	@Transactional(readOnly = false)
	public List<MessageStateChange> insert( Iterable<MessageStateChange> messageStateChanges ) {
		return this.repository.save( messageStateChanges );
	}
	
	@Transactional(readOnly = true)
	public MessageStateChange findById( Long id ) {
		return this.repository.findOne( id );
	}
	
	@Transactional(readOnly = true)
	public Page<MessageStateChange> findQueuedEmails( Pageable p ) {
		Page<MessageStateChange> result =  this.repository.findQueuedEmails( p );
		
		for( MessageStateChange msc : result ) {
			this.logger.debug( String.format( "result: %s", ReflectionToStringBuilder.toString( msc )));
			
			Message message = msc.getMessage();
			this.logger.debug( String.format( "related message: %s", ReflectionToStringBuilder.toString( message )));
			
			User sender = message.getUser() ;
			this.logger.debug( String.format( "message author: %s", sender.getUsername() ));
		}
		
		return result;
	}
}
