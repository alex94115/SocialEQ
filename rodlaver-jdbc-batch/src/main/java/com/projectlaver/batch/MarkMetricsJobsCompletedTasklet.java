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
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class MarkMetricsJobsCompletedTasklet implements Tasklet {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private DataSource dataSource;
	
	private static final String MARK_METRICS_JOB_AS_COMPLETED =
			  " UPDATE MetricsJobExecutions "
			+ " SET status = 'COMPLETED' "
			+ " WHERE status = 'PROCESSING' ";
	
	@Override
	public RepeatStatus execute( StepContribution step, ChunkContext context ) throws Exception {
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate( dataSource );
		
		// Mark the 'processing' rows in metrics job execution table as 'complete'
		int rows = jdbcTemplate.update( MARK_METRICS_JOB_AS_COMPLETED );
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Marked: " + rows + " 'PROCESSING' rows from the metrics_job_execution table as 'COMPLETED' ");
		}
		
		return RepeatStatus.FINISHED;
	}
}
