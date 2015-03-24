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

import com.projectlaver.util.PaymentItem;

public class JdbcInventoriesItemWriter extends RodLaverJdbcItemWriter<List<PaymentItem>> {
	
	private final Log logger = LogFactory.getLog(getClass());
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
	
	@Override
	public void write(List<? extends List<PaymentItem>> items) throws Exception {

		if (!items.isEmpty()) {

			if( logger.isDebugEnabled() ) {
				logger.debug("Executing update inventories batch with " + items.size() + " items.");
			}

			final PaymentItem item = (PaymentItem)items.get(0);
			if( items.size() != 1 ) {
				throw new InvalidDataAccessApiUsageException( "This class is not written to expect multiple PaymentItems, but the list size is: " + items.size() );
			}

			
			if( item.getUpdatedQuantityAvailable() != null ) {
				
				PreparedStatementCreator updateInventoriesPsc = new PreparedStatementCreator() {

					@Override
					public PreparedStatement createPreparedStatement( Connection connection ) throws SQLException {
						PreparedStatement ps = connection.prepareStatement( sql );
						
						ps.setInt( 1, item.getUpdatedQuantityAvailable() ); // remainingQuantity
						ps.setInt( 2, item.getNewInventoryVersion() ); // newInventoryVersion
						ps.setLong( 3, item.getInventoryId() ); // inventoryId
						ps.setInt( 4, item.getInventoryVersion() ); // inventoryVersion
						
						return ps;
					}
				};
				
				int i = super.getJdbcTemplate().update( updateInventoriesPsc );
				
				if( super.getAssertUpdates() ) {
					if( i == 0 ) {
						throw new EmptyResultDataAccessException( String.format( "Attempted and failed to update inventory id: %d and version %d to new quantity: %d and version %d",
								item.getInventoryId(), item.getInventoryVersion(), item.getUpdatedQuantityAvailable(), item.getNewInventoryVersion() ), 1 );
					}
				}
			}
		}
	}

}
