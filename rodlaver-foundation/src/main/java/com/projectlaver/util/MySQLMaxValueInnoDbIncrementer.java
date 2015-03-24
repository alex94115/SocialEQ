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
package com.projectlaver.util;

import java.math.BigInteger;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.incrementer.MySQLMaxValueIncrementer;

public class MySQLMaxValueInnoDbIncrementer extends MySQLMaxValueIncrementer {
	
	/** The next id to serve */
	private long nextId = 0;

	/** The max id to serve */
	private long maxId = 0;
	
	/**
	 * Default constructor for bean property style usage.
	 * @see #setDataSource
	 * @see #setIncrementerName
	 * @see #setColumnName
	 */
	public MySQLMaxValueInnoDbIncrementer() {
	}

	/**
	 * Convenience constructor.
	 * @param dataSource the DataSource to use
	 * @param incrementerName the name of the sequence/table to use
	 * @param columnName the name of the column in the sequence table to use
	 */
	public MySQLMaxValueInnoDbIncrementer(DataSource dataSource, String incrementerName, String columnName) {
		super(dataSource, incrementerName, columnName);
	}

	/**
	 * This method implements a strategy for loading sequence numbers via a MySQL stored procedure.
	 * The method calls the stored procedure, passing in the sequence name and the cache size. The
	 * stored procedure will create a transaction, get the next value, commit the transaction and
	 * return the result.
	 */
	@Override
	protected synchronized long getNextKey() throws DataAccessException {
		if (this.maxId == this.nextId) {
			
			JdbcTemplate template = new JdbcTemplate( super.getDataSource() );
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall( template )
				.withSchemaName( "hootit" )
				.withProcedureName( "getNextSequenceValue" )
				.declareParameters( new SqlParameter( "p_seqName", Types.VARCHAR ), new SqlParameter( "p_cacheSize", Types.BIGINT ))
				.withoutProcedureColumnMetaDataAccess();
			
			SqlParameterSource in = new MapSqlParameterSource()
				.addValue("p_seqName", super.getIncrementerName() )
				.addValue("p_cacheSize", super.getCacheSize() ); 
			Map<String, Object> outputParams = jdbcCall.execute( in );
			if( outputParams != null && outputParams.get( "#result-set-1" ) != null ) {
				List<Map<Object, Object>> resultSetList = (List<Map<Object, Object>>) outputParams.get( "#result-set-1" );
				if( resultSetList.size() > 0 ) {
					Map<Object, Object> resultSet = resultSetList.get( 0 );
					
					if( resultSet.containsKey( "@next" )) {
						BigInteger next = (BigInteger) resultSet.get( "@next" );
						this.maxId = next.longValue();
					} else {
						throw new DataAccessResourceFailureException("Result set did not contain next sequence value.");
					}
				} else {
					throw new DataAccessResourceFailureException("Empty result set while attempting to load next sequence value.");
				}
			} else {
				throw new DataAccessResourceFailureException("Unexpected stored procedure output parameters while attempting to load next sequence value.");
			}
			this.nextId = this.maxId - super.getCacheSize() + 1;

		}
		else {
			this.nextId++;
		}
		return this.nextId;
	}

}
