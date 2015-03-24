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

public class BusinessMetricsCursorItemRowMapper implements RowMapper<BusinessMetricsCursorItem> {

	// STATIC COLUMN NAME DECLARATIONS
	private static final String SELLER_ID_COLUMN = "seller_id";
	private static final String LISTING_ID_COLUMN = "listing_id";
	private static final String METRICS_ID_COLUMN = "metrics_id";
	private static final String START_TIME_COLUMN = "start_time";
	private static final String END_TIME_COLUMN = "end_time";
	
	@Override
	public BusinessMetricsCursorItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		BusinessMetricsCursorItem item = new BusinessMetricsCursorItem();
		
		// map the result set columns into the item properties
		item.setSellerId( rs.getLong( SELLER_ID_COLUMN ) );
		item.setListingId( rs.getLong( LISTING_ID_COLUMN ) );
		item.setMetricsId( rs.getLong( METRICS_ID_COLUMN ) );
		item.setStartTime( rs.getTimestamp( START_TIME_COLUMN ) );
		item.setEndTime( rs.getTimestamp( END_TIME_COLUMN ) );
		
		return item;
	}

}
