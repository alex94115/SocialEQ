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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.projectlaver.batch.domain.ListingCursorItem;

public abstract class CampaignProcessor implements ItemProcessor<ListingCursorItem, ListingCursorItem>, StepExecutionListener {
	
	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private DataSource dataSource;

	private Long jobExecutionInstanceId;
	
	// constants
	
	private static final String BATCH_PROCESSOR_KEY_PREFIX = "BATCH_EXECUTION_INSTANCE-";

	private static final String IDENTIFY_GIVEAWAY_LOSERS =
		    " UPDATE Messages m "
		  + "   INNER JOIN ListingReplies lr ON m.id = lr.reply_id "
		  + " SET m.batch_processor = ? "
		  + " WHERE lr.listing_id = ? "
		  + "   AND lr.reply_status='PENDING_LISTING_EXPIRY' "
		  + "   AND lr.reply_batch_processor != ? ";

	@Override
	public void beforeStep(StepExecution stepExecution) {
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( stepExecution );
		}

		this.jobExecutionInstanceId = stepExecution.getJobExecution().getJobInstance().getId();

		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Job Instance ID: " + this.jobExecutionInstanceId );
		}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// no op
		return null;
	}
	
	@Override
	public ListingCursorItem process( ListingCursorItem item ) throws Exception {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Entered the process method with cursor item: " +  ToStringBuilder.reflectionToString( item ));
		}

		JdbcTemplate jdbcTemplate = new JdbcTemplate( this.dataSource );

		// call the abstract method to identify the winners
		String winningMessagesBatchProcessor = this.createBatchProcessorKey( "-GIVEAWAY_WINNING_MESSAGE" );
		Integer winners = this.identifyWinningMessages( jdbcTemplate, item, winningMessagesBatchProcessor );
		
		// All the remaining messages as losers
		Long listingId = item.getListingId();
		Integer maxWinnersQuantity = item.getQuantity();
		
		Long start = System.currentTimeMillis();
		String losingMessagesBatchProcessor = this.createBatchProcessorKey( "-GIVEAWAY_LOSING_MESSAGE" );
		int losers = jdbcTemplate.update( IDENTIFY_GIVEAWAY_LOSERS, losingMessagesBatchProcessor, listingId, winningMessagesBatchProcessor );
		if( this.logger.isDebugEnabled() ) {
			long elapsedTime = System.currentTimeMillis() - start;
			this.logger.debug( "Updated: " + losers + " giveaway losers for giveaway id: " + listingId + " in elapsed time: " + elapsedTime );
		}

		if( (winners < maxWinnersQuantity) && (losers > 0) ) {
			// Something strange has happened, though it's possible that entries came in after expiry
			this.logger.error( "Giveaway id: " + listingId + " had a max quantity of winners of: " + maxWinnersQuantity +
							   " but we only identified: " + winners + " and we also identified: " + losers +
							   " losers. Need to make sure this is not a logic error." );
		}

		// Instant-win giveaways may or may not be expired. Drawing style giveaways will always be expired.
		if( item.getIsExpired() ) {
			item.setNewListingStatus( "COMPLETED" );
		} else {
			item.setNewListingStatus( "ACTIVE" );
		}
		
		return item;
	}

	abstract int identifyWinningMessages( JdbcTemplate jdbcTemplate, ListingCursorItem item, String winningMessagesBatchProcessorKey );
	

	/**
	 * Creates a batch processor key containing this execution instance's id with the
	 * appropriate suffix for each batch of messages appended
	 */
	String createBatchProcessorKey( String suffix ) {
		StringBuffer buffer = new StringBuffer( BATCH_PROCESSOR_KEY_PREFIX );
		buffer.append( this.jobExecutionInstanceId );
		buffer.append( suffix );
		return buffer.toString();
	}
	
}
