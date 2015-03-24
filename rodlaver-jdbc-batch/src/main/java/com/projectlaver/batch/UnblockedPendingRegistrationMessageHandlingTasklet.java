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

public class UnblockedPendingRegistrationMessageHandlingTasklet implements Tasklet {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private DataSource dataSource;
	
	/**
	 * SQL Statements
	 */

	private static String UNBLOCKED_PENDING_REGISTRATION_MESSAGES =
			  " UPDATE Messages m "
		    + "   INNER JOIN UserConnection uc ON ( uc.providerUserId = m.providerUserId AND uc.providerId = m.providerId ) "
		    + "   INNER JOIN Users u ON uc.userId = u.username "
		    + " SET m.batch_processor = NULL, m.status='PROCESSING' "
		    + " WHERE m.status = 'PENDING_USER_REGISTRATION' "
			+ "   AND u.is_user_blocked IS FALSE "
			+ "   AND u.has_accepted_current_buyer_tos IS TRUE "; 
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate( dataSource );
		
		long start = System.currentTimeMillis();
		// Formerly PENDING REGISTRATION messages for which the user is now registered, unblocked, and has accepted the buyer tos
		int unblockedPendingRegistrationMessages = jdbcTemplate.update( UNBLOCKED_PENDING_REGISTRATION_MESSAGES );
		if( this.logger.isDebugEnabled() ) {
			long elapsedTime = System.currentTimeMillis() - start;
			this.logger.debug( "Updated: " + unblockedPendingRegistrationMessages + " unblocked pending registration messages in elapsed time: " + elapsedTime );
		}	

		return RepeatStatus.FINISHED;
	}

}
