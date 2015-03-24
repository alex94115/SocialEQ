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
package com.projectlaver.batch.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.projectlaver.util.SimpleMessageCursorItem;

public class SimpleMessageCursorItemRowMapper implements RowMapper<SimpleMessageCursorItem> {
		
		public static final String ID_COLUMN = "m_id";
		public static final String MESSAGE_VERSION_COLUMN = "messageVersion";
		public static final String NEW_MESSAGE_VERSION_COLUMN = "newMessageVersion";
		public static final String PRIOR_MESSAGE_STATUS_COLUMN = "prior_message_status";
		public static final String NEW_MESSAGE_STATUS_COLUMN = "new_message_status";
		public static final String DOES_REQUIRE_BUYER_COMMUNICATIONS_COLUMN = "doesRequireBuyerCommunication";
		public static final String DOES_REQUIRE_SELLER_COMMUNICATIONS_COLUMN = "doesRequireSellerCommunication";
		
		public SimpleMessageCursorItem mapRow(ResultSet rs, int rowNum) throws SQLException {
	        SimpleMessageCursorItem item = new SimpleMessageCursorItem();
	        this.mapResultToCursorItem(rs, item);

	        return item;
		}

		protected SimpleMessageCursorItem mapResultToCursorItem(ResultSet rs, SimpleMessageCursorItem item) throws SQLException {
			item.setMessageId( rs.getLong( ID_COLUMN ) );
	        
	        // Set the current and incremented version number
	        item.setMessageVersion(  rs.getInt( MESSAGE_VERSION_COLUMN )  );
	        item.setNewMessageVersion( rs.getInt( NEW_MESSAGE_VERSION_COLUMN ) );
	        item.setPriorMessageStatus( rs.getString( PRIOR_MESSAGE_STATUS_COLUMN ) );
	        item.setNewMessageStatus( rs.getString( NEW_MESSAGE_STATUS_COLUMN ) );
	        item.setDoesRequireBuyerCommunication( rs.getBoolean( DOES_REQUIRE_BUYER_COMMUNICATIONS_COLUMN ) );
	        item.setDoesRequireSellerCommunication( rs.getBoolean( DOES_REQUIRE_SELLER_COMMUNICATIONS_COLUMN) );
	        
	        return item;
		}

}
	
	
