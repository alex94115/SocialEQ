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

public class ListingCursorItemRowMapper implements RowMapper<ListingCursorItem> {

	public static final String ID_COLUMN = "l_id";
	public static final String DOES_REQUIRE_FACEBOOK_POSTING_COLUMN = "doesRequireFacebookPosting";
	public static final String DOES_REQUIRE_TWITTER_POSTING_COLUMN = "doesRequireTwitterPosting";
	public static final String IS_DIGITAL_GIVEAWAY_COLUMN = "isDigitalGiveaway";
	public static final String LISTING_VERSION_COLUMN = "listingVersion";
	public static final String NEW_LISTING_VERSION_COLUMN = "newListingVersion";
	public static final String IS_EXPIRED_COLUMN = "isExpired";
	public static final String QUANTITY_COLUMN = "quantity";
	public static final String GIVEAWAY_SEED_COLUMN = "giveawaySeed";
	public static final String BATCH_PROCESSOR_COLUMN = "batchProcessor";
	
	
	public ListingCursorItem mapRow( ResultSet rs, int rowNum ) throws SQLException {
		ListingCursorItem item = new ListingCursorItem();
		this.mapResultSetToCursorItem( rs, item );
		
		return item;
	}
	
	ListingCursorItem mapResultSetToCursorItem( ResultSet rs, ListingCursorItem item ) throws SQLException {
		item.setListingId( rs.getLong( ID_COLUMN ));
		item.setDoesRequireFacebookPosting( rs.getBoolean( DOES_REQUIRE_FACEBOOK_POSTING_COLUMN ));
		item.setDoesRequireTwitterPosting( rs.getBoolean( DOES_REQUIRE_TWITTER_POSTING_COLUMN ));
		item.setIsDigitalGiveaway( rs.getBoolean( IS_DIGITAL_GIVEAWAY_COLUMN ));
		item.setListingVersion( rs.getInt( LISTING_VERSION_COLUMN ));
		item.setNewListingVersion( rs.getInt( NEW_LISTING_VERSION_COLUMN ) );
		item.setIsExpired( rs.getBoolean( IS_EXPIRED_COLUMN ));
		item.setQuantity( rs.getInt( QUANTITY_COLUMN ));
		item.setGiveawaySeed( rs.getInt( GIVEAWAY_SEED_COLUMN ));
		item.setBatchProcessor( rs.getString( BATCH_PROCESSOR_COLUMN ) );
		
		return item;
	}
}
