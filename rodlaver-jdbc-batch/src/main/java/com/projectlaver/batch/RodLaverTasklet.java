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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class RodLaverTasklet implements Tasklet, StepExecutionListener {

	/**
	 * Instance variables
	 */

	private final Log logger = LogFactory.getLog(getClass());
	private static final String BATCH_PROCESSOR_KEY_PREFIX = "BATCH_EXECUTION_INSTANCE-";

	@Autowired
	private DataSource dataSource;

	private Long jobExecutionInstanceId;

	protected DataSource getDataSource() {
		return this.dataSource;
	}
	
	protected Long getJobExecutionInstanceId() {
		return this.jobExecutionInstanceId;
	}
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		this.logger.debug( stepExecution );
		this.jobExecutionInstanceId = stepExecution.getJobExecution().getJobInstance().getId();
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Job Instance ID: " + this.jobExecutionInstanceId );
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return null;
	}

	/**
	 * Creates a batch processor key containing this execution instance's id with the
	 * appropriate suffix for eatch batch of messages appended
	 */
	protected String createBatchProcessorKey( String suffix ) {
		StringBuffer buffer = new StringBuffer( BATCH_PROCESSOR_KEY_PREFIX );
		buffer.append( this.jobExecutionInstanceId );
		buffer.append( suffix );
		return buffer.toString();
	}
	
	protected int executeUpdate( JdbcTemplate jdbcTemplate, String sql, String batchSuffix, String debugMessage ) {
		long start = System.currentTimeMillis();
		int updated = jdbcTemplate.update( sql, batchSuffix );
		if( this.logger.isDebugEnabled() ) {
			long elapsedTime = System.currentTimeMillis() - start;
			this.logger.debug( "Updated: " + updated + debugMessage + elapsedTime );
		}
		
		return updated;
	}
	
	protected int executeUpdate( JdbcTemplate jdbcTemplate, String sql, String batchSuffix, Long maxId, String debugMessage ) {
		long start = System.currentTimeMillis();
		int updated = jdbcTemplate.update( sql, batchSuffix, maxId );
		if( this.logger.isDebugEnabled() ) {
			long elapsedTime = System.currentTimeMillis() - start;
			this.logger.debug( "Updated: " + updated + debugMessage + elapsedTime );
		}
		
		return updated;
	}

}
