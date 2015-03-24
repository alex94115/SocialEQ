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

public class AbandonedPaymentCursorItemRowMapper implements RowMapper<AbandonedPaymentCursorItem> {

	public static final String PAYMENT_ID_COLUMN = "payment_id";
	public static final String CURRENT_PAYMENT_VERSION_COLUMN = "current_payment_version";
	public static final String UPDATED_PAYMENT_VERSION_COLUMN = "updated_payment_version";
	public static final String UPDATED_PAYMENT_BATCH_STATUS_COLUMN = "updated_payment_batch_status";
	public static final String LISTING_ID_COLUMN = "listing_id";
	public static final String INVENTORY_ID_COLUMN = "inventory_id";
	public static final String PAYMENT_QUANTITY_COLUMN = "payment_quantity";

	
	@Override
	public AbandonedPaymentCursorItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		AbandonedPaymentCursorItem item = new AbandonedPaymentCursorItem();
	
		item.setPaymentId( rs.getLong( PAYMENT_ID_COLUMN ));
		item.setCurrentPaymentVersion( rs.getInt( CURRENT_PAYMENT_VERSION_COLUMN ));
		item.setUpdatedPaymentVersion( rs.getInt( UPDATED_PAYMENT_VERSION_COLUMN ));
		item.setUpdatedPaymentBatchStatus( rs.getString( UPDATED_PAYMENT_BATCH_STATUS_COLUMN ));
		item.setListingId( rs.getLong( LISTING_ID_COLUMN ));
		item.setInventoryId( rs.getLong( INVENTORY_ID_COLUMN ));
		item.setPaymentQuantity( rs.getInt( PAYMENT_QUANTITY_COLUMN ));
		
		return item;
	}
		
}
