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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class RunInLoopListener {

	@Autowired
	private JobOperator operator;

	// This configuration value lets us run X times in between shutdowns
	@Value("${batch.jobInstanceStartsPerRun}")
	Integer jobInstanceStartsPerRun;
	
	// This configuration value lets us introduce a variable sleep time before starting up again
	@Value("${batch.sleepTimePerLoopSeconds}")
	Integer sleepTimePerLoopSeconds;
	
	private final Log logger = LogFactory.getLog(getClass());
	
	@AfterJob
	public void afterJob( JobExecution jobExecution ) throws Exception {

		Long jobInstanceId = jobExecution.getJobInstance().getId();
		BatchStatus jobExecutionStatus = jobExecution.getStatus();
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "Within the loop listener's afterJob() method. jobExecution: %s, operator: %s, jobInstanceId: %d", jobExecution.getStatus(), this.operator, jobInstanceId ) );
		}
		
		// did this execution complete normally?
		if( jobExecutionStatus.equals( BatchStatus.COMPLETED )) {

			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Job status is completed. Seeing if we should start a new job instance." );
			}
			
			// Use a variable number of loops in between shutdowns
			if( jobInstanceId % this.jobInstanceStartsPerRun != 0 ) {

				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( String.format( "Current run count of: %d is not a multiple of: %d, so continuing.", jobInstanceId, this.jobInstanceStartsPerRun ));
				}
				
				// Allow a variable sleep time per loop
				if( this.sleepTimePerLoopSeconds != null ) {
					Thread.sleep( this.sleepTimePerLoopSeconds * 1000 );
				}
				
				// Make the next run's count equal to this job instance's id plus 1
				operator.start( "rodLaverJob", "run.count=" + (jobInstanceId + 1) );
				
				
			} else {
				
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "Current run count is a multiple of 25, so exiting." );
				}
				
			}
			
		}

	}

	@BeforeJob
	public void beforeJob( JobExecution jobExecution ) throws Exception {

		this.logger.debug( "Within the loop listener's beforeJob() method. ");
	
	}

}
