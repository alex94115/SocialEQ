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

public class AssignUserIdToMessageCursorItemRowMapper implements RowMapper<AssignUserIdToMessageCursorItem> {

	public static final String MESSAGE_ID_COLUMN = "message_id";
	public static final String USER_ID_COLUMN = "user_id";
	public static final String MESSAGE_VERSION_COLUMN = "message_version";
	public static final String NEW_MESSAGE_VERSION_COLUMN = "new_message_version";
	
	@Override
	public AssignUserIdToMessageCursorItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		AssignUserIdToMessageCursorItem item = new AssignUserIdToMessageCursorItem();
		
		item.setMessageId( rs.getLong( MESSAGE_ID_COLUMN ));
		item.setUserId( rs.getLong( USER_ID_COLUMN ));
		item.setMessageVersion( rs.getInt( MESSAGE_VERSION_COLUMN ));
		item.setNewMessageVersion( rs.getInt( NEW_MESSAGE_VERSION_COLUMN ));
		
		return item;
	}

}
