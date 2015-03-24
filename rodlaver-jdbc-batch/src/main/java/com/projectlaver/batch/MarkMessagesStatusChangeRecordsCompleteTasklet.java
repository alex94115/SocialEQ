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
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class MarkMessagesStatusChangeRecordsCompleteTasklet implements Tasklet, StepExecutionListener {

	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * SQL Statements
	 */
	private static final String UPDATE_MESSAGE_STATUS_CHANGE_RECORDS = 
			" UPDATE MessageStateChanges msc "
		  + " SET msc.status = 'COMPLETED' "
		  + " WHERE msc.areBuyerCommunicationsComplete = msc.doesRequireBuyerCommunication "
		  + "    AND msc.areSellerCommunicationsComplete = msc.doesRequireSellerCommunication "
		  + "    AND msc.status = 'PENDING' ";
	
	private static final String MARK_REDUNDANT_STATUS_CHANGE_RECORDS =
			" UPDATE MessageStateChanges msc "
		  + " SET msc.status = 'IGNORED_REDUNDANT' "
		  + " WHERE msc.id IN ( "
		  + " SELECT DISTINCT pending_msc_id "
		  + " FROM ( "
		  + "   SELECT pending_msc.id pending_msc_id, pending_msc.previousState pending_msc_previousState, pending_msc.newState pending_msc_newState, pending_msc.status pending_msc_status, reply.providerId pending_msc_providerId, reply.providerUserId pending_msc_providerUserId, reply.user_id pending_msc_userId, reply.inResponseToTwitterId pending_msc_inResponseToTwitterId "
		  + "   FROM MessageStateChanges pending_msc  "
		  + "     INNER JOIN Messages reply ON pending_msc.message_id = reply.id "
		  + "     INNER JOIN Messages listing_message ON ( reply.providerId = listing_message.providerId AND reply.inResponseToTwitterId = listing_message.twitterId ) "
		  + "     INNER JOIN ( "
		  		  /* Finds previously sent messages */ 
		  + " 	    SELECT msc.previousState sent_msc_previousState, msc.newState sent_msc_newState, reply.providerId sent_msc_providerId, reply.providerUserId sent_msc_providerUserId, reply.user_id sent_msc_userId, reply.inResponseToTwitterId sent_msc_inResponseToTwitterId "
		  + " 	    FROM MessageStateChanges msc   "
		  + " 	      INNER JOIN Messages reply ON msc.message_id = reply.id "
		  + " 	      INNER JOIN Messages listing_message ON ( reply.providerId = listing_message.providerId AND reply.inResponseToTwitterId = listing_message.twitterId ) "
		  + " 	    WHERE msc.doesRequireBuyerCommunication IS TRUE "
		  + " 	      AND msc.areBuyerCommunicationsComplete IS TRUE /* previously sent */ "
		  + " 	      AND msc.previousState='PROCESSING' "
		  + " 	      AND ( msc.newState =  'FAILED_SOLD_OUT' OR msc.newState =  'FAILED_DUPLICATE_PURCHASE' OR msc.newState =  'PENDING_USER_REGISTRATION' OR msc.newState = 'PENDING_MEANS_OF_PAYMENT' ) "
		  + "     ) As Sub ON ( pending_msc.previousState = sent_msc_previousState AND pending_msc.newState = sent_msc_newState AND reply.providerId = sent_msc_providerId AND reply.providerUserId = sent_msc_providerUserId AND reply.inResponseToTwitterId = sent_msc_inResponseToTwitterId  ) " 
		  + "   WHERE pending_msc.doesRequireBuyerCommunication != pending_msc.areBuyerCommunicationsComplete "
		  + "     AND pending_msc.status = 'PENDING' "
		  + "     AND ( ( reply.user_id IS NULL AND sent_msc_userId IS NULL ) OR reply.user_id = sent_msc_userId) "
		  + " ) As Sub2 "
		  + " )";
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		// no op
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return null;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate( dataSource );
		int completed = jdbcTemplate.update( UPDATE_MESSAGE_STATUS_CHANGE_RECORDS );
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Marked: " + completed + " records from message_status_change as completed." );
		}
		
		int redundant = jdbcTemplate.update( MARK_REDUNDANT_STATUS_CHANGE_RECORDS );
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Marked: " + redundant + " records from message_status_change as redundant / ignore." );
		}
		
		return RepeatStatus.FINISHED;
	}

}
