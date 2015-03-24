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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.projectlaver.batch.domain.AbandonedPaymentCursorItem;

public class AbandonedLtdQtyListingPaymentsProcessor implements ItemProcessor<AbandonedPaymentCursorItem, AbandonedPaymentCursorItem> {

	/**
	 * Static variables
	 */
	
	// select the up-to-date attributes for this inventory & listing. 
	// lock it so we can immediately update the quantity and make the listing active again if necessary
	private static final String SELECT_INVENTORY_ATTRIBUTES = 
			  " SELECT i.remainingQuantity + ? updated_remaining_quantity, "
			+ "   i.version current_inventory_version, "
			+ "   i.version + 1 updated_inventory_version "
			+ " FROM Inventories i "
			+ " WHERE i.id = ?"
			+ " LOCK IN SHARE MODE ";
	
	// update the quantity, status and increment the version
	private static final String UPDATE_INVENTORY =
			  " UPDATE Inventories "
			+ " SET remainingQuantity = ?, "
			+ "   version = ? "
			+ " WHERE id = ? "
			+ "   AND version = ? ";
	
	/**
	 * Instance variables
	 */

	@Autowired
	private DataSource datasource;
	
	private final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public AbandonedPaymentCursorItem process( AbandonedPaymentCursorItem cursorItem ) throws Exception {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Entering the process method. cursorItem:" + ToStringBuilder.reflectionToString( cursorItem ));
		}
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate( this.datasource );
		Map<String, Object> values = jdbcTemplate.query( 
				SELECT_INVENTORY_ATTRIBUTES, 
				new Object[] { cursorItem.getPaymentQuantity(), cursorItem.getInventoryId() }, 
				new ResultSetExtractor<Map<String, Object>>() {
					@Override
					public Map<String, Object> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
						Map<String, Object> result = null;
		          	  
		            	if( resultSet.next() ) {
		            		result = new HashMap<String, Object>();
		            		result.put( "updatedRemainingQuantity", resultSet.getInt( "updated_remaining_quantity" ));
		            		result.put( "currentInventoryVersion", resultSet.getInt( "current_inventory_version" ));
		            		result.put( "updatedInventoryVersion", resultSet.getInt( "updated_inventory_version" ));
		                }
		                return result;
					}
				});
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Read these current inventory attributes from the database: {updatedRemainingQuantity] " + values.get( "updatedRemainingQuantity" ) 
					+ " [currentInventoryVersion] " + values.get( "currentInventoryVersion" ) + ", [updatedInventoryVersion] " + values.get( "updatedInventoryVersion" ) );
		}
		
		Integer updatedRemainingQuantity = ( Integer )values.get( "updatedRemainingQuantity" );
		Integer updatedVersion = ( Integer )values.get( "updatedInventoryVersion" );
		Integer currentVersion = ( Integer )values.get( "currentInventoryVersion" );
		
		int rows = jdbcTemplate.update( UPDATE_INVENTORY, updatedRemainingQuantity, updatedVersion, cursorItem.getInventoryId(), currentVersion );
		
		if( rows > 0 && this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "Updated the inventory with id: %d to increment the available quantity by: %d", cursorItem.getInventoryId(), cursorItem.getPaymentQuantity() ) );
		}
		
		return cursorItem;
	}

}
