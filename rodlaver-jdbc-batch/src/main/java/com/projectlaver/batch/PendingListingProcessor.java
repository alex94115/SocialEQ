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

import javax.sql.DataSource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.projectlaver.batch.domain.ListingCursorItem;

public class PendingListingProcessor implements ItemProcessor<ListingCursorItem, ListingCursorItem> {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private DataSource dataSource;
	
	private static final String INSERT_LISTING_STATUS_CHANGE =
		   " INSERT INTO ListingStateChanges "
		 + " SET version = 0, "
		 + "   doesRequireFacebookPosting = ?, "
		 + "   isFacebookPostingComplete = FALSE, "
		 + "   doesRequireTwitterPosting = ?, "
		 + "   isTwitterPostingComplete = FALSE, "
		 + "   newState = 'ACTIVE', "
		 + "   previousState = 'PENDING', "
		 + "   status = 'PENDING', "
		 + "   listing_id = ? ";
	
	@Override
	public ListingCursorItem process( ListingCursorItem item ) throws Exception {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Entered the process method with cursor item: " +  ToStringBuilder.reflectionToString( item ));
		}
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate( this.dataSource );
		
		// insert one row into the listing_status_change table for twitter and fb
		jdbcTemplate.update( INSERT_LISTING_STATUS_CHANGE, item.getDoesRequireFacebookPosting(), item.getDoesRequireTwitterPosting(), item.getListingId() );
		
		// update the listing status to be active
		item.setNewListingStatus( "ACTIVE" );
		
		return item;
	}

}
