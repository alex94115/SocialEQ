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

public class PaypalPaymentStatusUpdatesDivvyingTasklet extends RodLaverTasklet implements Tasklet, StepExecutionListener {

	/**
	 * Instance variables
	 */

	private final Log logger = LogFactory.getLog(getClass());


	/**
	 * Constants
	 */

	private static String PRIOR_RUN_SKIPPED_ABANDONED_PAYMENTS =
			  " UPDATE Payments p "
			+ " SET p.batch_processor = NULL "
		    + " WHERE p.batch_processor IS NOT NULL "
			+ "   AND p.batch_status IS NULL ";

	private static String ABANDONED_LIMITED_QUANTITY_LISTING_PAYMENTS =
			  " UPDATE Payments p "
		    + "   INNER JOIN Listings l ON p.listing_id = l.id "
		    + "   INNER JOIN Inventories i ON p.inventory_id = i.id "
		    + "   INNER JOIN EffectivePaymentStatuses eps1 ON ( p.id = eps1.payment_id) "
		    + "   LEFT OUTER JOIN EffectivePaymentStatuses eps2 ON ( p.id = eps2.payment_id AND eps1.id < eps2.id) "
			+ " SET p.batch_processor = ? "
			+ " WHERE eps2.id IS NULL " // this forces only the last related eps row
			+ "   AND eps1.status='CREATED' " // created payments are created when a user 
			+ "   AND eps1.end <= NOW() " // this means that the last effective payment status is in the past
			+ "   AND i.quantity IS NOT NULL " // only limited quantity listings 
			+ "   AND p.batch_processor IS NULL "; // not claimed by another batch job
	

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		JdbcTemplate jdbcTemplate = new JdbcTemplate( super.getDataSource() );

		// TODO figure out some way to identify status updates that get skipped a lot (e.g., if their created on time gets to be old).
		
		// Reset any prior run abandoned Payments related to Limited Quantity Listings
		int priorRunAbandonedPayments = jdbcTemplate.update( PRIOR_RUN_SKIPPED_ABANDONED_PAYMENTS );
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Updated: " + priorRunAbandonedPayments + " prior run abandoned payments in elapsed time: " );
		}
		
		// Assign any expired abandoned payments related to a limited quantity listing to a batch so we can increment the availble quantity
		String abandonedLimitedQuantityListingPaymentsBatchProcessorKey = super.createBatchProcessorKey( "-ABANDONED_LTD_QTY_PAYMENT" );
		String debugMessage = " abandoned limited quantity listing payments in elapsed time: ";
		super.executeUpdate( jdbcTemplate, ABANDONED_LIMITED_QUANTITY_LISTING_PAYMENTS, abandonedLimitedQuantityListingPaymentsBatchProcessorKey, debugMessage );
		
		return RepeatStatus.FINISHED;
	}

}
