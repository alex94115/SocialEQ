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
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;

public class FacebookFetchPagedCommentDivvyingTasklet extends RodLaverTasklet implements Tasklet, StepExecutionListener {

	/**
	 * Instance variables
	 */

	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * Constants
	 */
	
	private static String RESET_STUCK_REPLY_PAGING_STATUS_ROWS = 
			   " UPDATE ReplyPagingStatuses "
			 + " SET status='IDLE', batch_processor=NULL "
			 + " WHERE status != 'IDLE' "
			 + "   OR batch_processor IS NOT NULL ";
	
	private static String FETCH_PAGED_COMMENTS_RELATED_TO_FACEBOOK_LISTING_CONTENT =
			   " UPDATE ReplyPagingStatuses rps "
			 + "   INNER JOIN Messages lm ON rps.listing_message_id = lm.id "
			 + "   INNER JOIN Listings l ON lm.listing_id = l.id "
			 + "   INNER JOIN Users u ON lm.user_id = u.id "
			 + " SET rps.status='PROCESSING', rps.batch_processor = ? "
			 + " WHERE lm.providerId='facebook' "
			 + "   AND rps.status='IDLE' "
			 + "   AND rps.batch_processor IS NULL "
			 + "   /* select only recent records, i.e., rows related to listings that are either */ "
			 + "   /* not expired or expired within the last week */ "
			 + "   AND l.expires >= CURRENT_TIMESTAMP() - INTERVAL 1 WEEK ";
	
	/**
	 * Public methods
	 */


	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		// only run this 1 out of 5 batch job instances so we don't hammer FB too hard
		Long jobExecutionInstanceId = super.getJobExecutionInstanceId();
		
		if( jobExecutionInstanceId % 5 == 0 ) {
		
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Looking for Facebook listings for which we need to fetch comments." );
			}
			
			JdbcTemplate jdbcTemplate = new JdbcTemplate( super.getDataSource() );
			
			// Reset any paging status rows that have gotten 'stuck' in a prior batch run (e.g., if a communications error caused the step to be skipped)
			int resetStuckRows = jdbcTemplate.update( RESET_STUCK_REPLY_PAGING_STATUS_ROWS );
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Reset: " + resetStuckRows + " from the reply_paging_status table that had either a non-IDLE status or a not-NULL batch_processor." );
			}
			
			// Claim the latest update for each listing within the paging_status table
			String facebookChangeNotificationBatchProcessorKey = super.createBatchProcessorKey( "-FACEBOOK_CHANGE_NOTIFICATION" );
			int fetchPagedCommentsRelatedToFbListingContent = jdbcTemplate.update( FETCH_PAGED_COMMENTS_RELATED_TO_FACEBOOK_LISTING_CONTENT, facebookChangeNotificationBatchProcessorKey );
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Will fetch comments for: " + fetchPagedCommentsRelatedToFbListingContent + " Facebook Listings." );
			}
		} else {
			
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Bypassing fetching Facebook comments on this run." );
			}
			
		}
		
		return RepeatStatus.FINISHED;
	}
}
