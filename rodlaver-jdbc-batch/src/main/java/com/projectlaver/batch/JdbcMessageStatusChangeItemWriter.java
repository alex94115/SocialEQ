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
package com.projectlaver.batch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.database.JdbcParameterUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.util.Assert;

import com.projectlaver.util.CommunicationChannel;
import com.projectlaver.util.MessageStatus;
import com.projectlaver.util.SimpleMessageCursorItem;
import com.projectlaver.util.PaymentItem;

public class JdbcMessageStatusChangeItemWriter extends RodLaverJdbcItemWriter<List<SimpleMessageCursorItem>> {

	private Log logger = LogFactory.getLog(getClass());
	private String sql;

	public void setSql( String sql ) {
		this.sql = sql;
	}
	
	@Override
	public void afterPropertiesSet() {
		Assert.notNull( super.getJdbcTemplate(), "A DataSource or a NamedParameterJdbcTemplate is required." );
		Assert.notNull( this.sql, "An SQL statement is required." );
		List<String> namedParameters = new ArrayList<String>();
		int parameterCount = JdbcParameterUtils.countParameterPlaceholders( this.sql, namedParameters);
		if (namedParameters.size() > 0) {
			if (parameterCount != namedParameters.size()) {
				throw new InvalidDataAccessApiUsageException("You can't use both named parameters and classic \"?\" placeholders: " + this.sql );
			}
		}
	}
	
	/**
	 * Expect a List to be passed in with only one item. Generally speaking we just insert this item
	 * to the MessageStateChanges table and let a subsequent step handle any required communications.
	 * 
	 * Special logic exists for state changes that require emails to be sent, for example when 
	 * a payment is completed.
	 * 
	 */
	@Override
	public void write(List<? extends List<SimpleMessageCursorItem>> items) throws Exception {

		if (!items.isEmpty()) {

			if( logger.isDebugEnabled() ) {
				logger.debug("Executing insert MessageStatusChanges batch with " + items.size() + " items.");
			}
			
			// Only expect one SimpleMessageCursorItem
			if( items.size() != 1 ) {
				throw new InvalidDataAccessApiUsageException( "This class is not written to expect multiple SimpleMessageCursorItem, but the list size is: " + items.size() );
			}
			
			SimpleMessageCursorItem inputItem = (SimpleMessageCursorItem)items.get(0);
			
			// special logic here to handle cases where we want to send an email
			final List<SimpleMessageCursorItem > insertItems = this.createInsertItems( inputItem );
			
			for( final SimpleMessageCursorItem insertItem : insertItems ) {
			
				PreparedStatementCreator insertMessageStatusChangePsc = new PreparedStatementCreator() {
	
					@Override
					public PreparedStatement createPreparedStatement( Connection connection ) throws SQLException {
						PreparedStatement ps = connection.prepareStatement( sql );
						
						ps.setLong(    1, insertItem.getMessageId() ); // message_id
						ps.setString(  2, insertItem.getPriorMessageStatus() ); // previousState
						ps.setString(  3, insertItem.getNewMessageStatus() ); // newState
						ps.setBoolean( 4, insertItem.getDoesRequireBuyerCommunication() ); // doesRequireBuyerCommunication
						ps.setBoolean( 5, false ); // areBuyerCommunicationsComplete
						ps.setBoolean( 6, insertItem.getDoesRequireSellerCommunication() ); // doesRequireSellerCommunication
						ps.setBoolean( 7, false ); // areSellerCommunicationsComplete
						ps.setString(  8, insertItem.getChannel().toString() ); // communicationChannel
						ps.setString(  9, "PENDING" ); // status
						ps.setInt(    10, 0 );// version
						
						return ps;
					}
				};
				
				int i = super.getJdbcTemplate().update( insertMessageStatusChangePsc );
				
				if( super.getAssertUpdates() ) {
					if( i == 0 ) {
						throw new EmptyResultDataAccessException( String.format( "Attempted and failed to insert MessageStatusChange for message id: %d going from: %s to: %s via channel: %s",
								insertItem.getMessageId(), insertItem.getPriorMessageStatus(), insertItem.getNewMessageStatus(), insertItem.getChannel() ), 1 );
					}
				}
			}
		}
	}
	
	/**
	 * See if based on the state change we want to send an email too (in addition to the FB or Twitter message). 
	 * 
	 * @param inputItem
	 * @return
	 */
	List<SimpleMessageCursorItem> createInsertItems( SimpleMessageCursorItem inputItem ) {

		List<SimpleMessageCursorItem> insertItems = new ArrayList<SimpleMessageCursorItem>();
		insertItems.add( inputItem );
		
		MessageStatus newStatus = MessageStatus.valueOf( inputItem.getNewMessageStatus() );
		
		if( newStatus.equals( MessageStatus.COMPLETED ) || 
			newStatus.equals( MessageStatus.PENDING_SHIPPING_ADDRESS ) ||
			newStatus.equals( MessageStatus.PENDING_SHIPMENT ) ) {
			
			SimpleMessageCursorItem emailItem = new SimpleMessageCursorItem();

			// create a shallow copy of the input item but set the channel to email
			emailItem.setMessageId( inputItem.getMessageId() );
			emailItem.setPriorMessageStatus( inputItem.getPriorMessageStatus() );
			emailItem.setNewMessageStatus( inputItem.getNewMessageStatus() );
			emailItem.setDoesRequireBuyerCommunication( inputItem.getDoesRequireBuyerCommunication() );
			emailItem.setDoesRequireSellerCommunication( inputItem.getDoesRequireSellerCommunication() );
			emailItem.setChannel( CommunicationChannel.EMAIL );
			
			insertItems.add( emailItem );
		}
		
		return insertItems;
		
	}
	
	

}
