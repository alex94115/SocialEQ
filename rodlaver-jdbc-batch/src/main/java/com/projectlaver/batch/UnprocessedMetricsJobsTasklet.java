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

import java.sql.Timestamp;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class UnprocessedMetricsJobsTasklet implements Tasklet {
	
	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private DataSource dataSource;

	/**
	 * Static Constants
	 */
	
	private static final String SELECT_MAX_END_TIME =
			"SELECT MAX(end_time) FROM MetricsJobExecutions";
	
	/**
	 * Finds the start of the ten-minute window preceding the oldest reply message,
	 * which is the time of our oldest activity.
	 * 
	 * The math here:
	 * - turns the timestamp into a unix time using UNIX_TIMESTAMP
	 * - divides by 600 (10 minutes * 60 seconds / min) to find the number of 10 minute periods since time began
	 * - truncates the decimal part to round down using TRUNCATE
	 * - multiplies by 600 to bring us back to unix time
	 * - parses that into a timestamp using FROM_UNIXTIME
	 * 
	 */
	private static final String SELECT_START_OF_OLDEST_REPLY_WINDOW = 
			  " SELECT FROM_UNIXTIME( TRUNCATE( UNIX_TIMESTAMP(start) / 600, 0 ) * 600 ) "
			+ " FROM "
			+ " ( "
			+ "   SELECT MIN( created ) start "
			+ "   FROM Messages "
			+ "   WHERE listing_id IS NULL "
			+ " ) As Sub";
	
	/**
	 * This query finds the start of the most recent ten minute window where the end of the window has passed.
	 * 
	 * The math here:
	 * - turns the current time into a unix time using UNIX_TIMESTAMP
	 * - divides by 600 (10 minutes * 60 seconds / min) to find the number of 10 minute periods since time began
	 * - truncates the decimal part to round down using TRUNCATE
	 * - multiplies by 600 to bring us back to unix time
	 * - parses that into a timestamp using FROM_UNIXTIME
	 * 
	 */
	private static final String SELECT_START_OF_MOST_RECENT_WINDOW = 
			"SELECT FROM_UNIXTIME( TRUNCATE( UNIX_TIMESTAMP(NOW()) / 600, 0 ) * 600 ) - INTERVAL 10 MINUTE";
	
	private static final String IS_WINDOW_END_IN_PAST = 
			"SELECT ( ? + INTERVAL 10 MINUTE) < NOW()";
	
	private static final String INSERT_METRICS_JOB_EXECUTION = 
			  " INSERT INTO MetricsJobExecutions "
			+ " SET start_time = ?, end_time = ( ? + INTERVAL 10 MINUTE ), status='PROCESSING', version = 0 ";
	
	private static final String TEN_MINUTES_LATER = 
			"SELECT ( ? + INTERVAL 10 MINUTE)";
			
	@Override
	public RepeatStatus execute( StepContribution step, ChunkContext context ) throws Exception {
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate( dataSource );
		
		// 1. Make sure the table isn't empty
		Timestamp startDatetime = jdbcTemplate.queryForObject( SELECT_MAX_END_TIME, Timestamp.class );
		if( startDatetime == null ) {
			
			// find the hour of the oldest reply message (the first instance of activity) 
			startDatetime = jdbcTemplate.queryForObject( SELECT_START_OF_OLDEST_REPLY_WINDOW, Timestamp.class );
			
			// otherwise just find the time at the start of the 2nd to last hour
			if( startDatetime == null ) {
				startDatetime = jdbcTemplate.queryForObject( SELECT_START_OF_MOST_RECENT_WINDOW, Timestamp.class );
			}
		}
		
		// 2. While the start time + 1 hr (aka the end time) is less than current time 
		while( jdbcTemplate.queryForObject( IS_WINDOW_END_IN_PAST, new Object[] { startDatetime }, Boolean.class ) ) {
			
			// insert a new 'processing' row into the table
			jdbcTemplate.update( INSERT_METRICS_JOB_EXECUTION, startDatetime, startDatetime );
			
			// increment the start time to the next hour to continue the loop...
			startDatetime = jdbcTemplate.queryForObject( TEN_MINUTES_LATER, new Object[] { startDatetime }, Timestamp.class );
		}
	
		return RepeatStatus.FINISHED;
	}

}
