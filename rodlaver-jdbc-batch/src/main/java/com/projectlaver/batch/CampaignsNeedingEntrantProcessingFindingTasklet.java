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

public class CampaignsNeedingEntrantProcessingFindingTasklet extends RodLaverTasklet implements Tasklet, StepExecutionListener {

	private final Log logger = LogFactory.getLog(getClass());


	/**
	 * Constants
	 */


	/**
	 * SQL to find these newly expired campaigns
	 */
	private static String ASSIGN_NEWLY_EXPIRED_CAMPAIGNS_INTO_BATCH =
			  " UPDATE Listings l "
			+ " SET l.batch_processor = ? "
				// considered active
			+ " WHERE l.status = 'ACTIVE' "
				  // only DRAWING-style campaigns
			+ "   AND l.type = 'CAMPAIGN' "
			+ "   AND l.subType = 'DRAWING' "
				  // expiration date has passed
			+ "   AND l.expires < current_timestamp "
				  // not claimed by another job
			+ "   AND l.batch_processor IS NULL ";
	
	/**
	 * SQL to find ongoing instant-win campaigns
	 */
	private static String ASSIGN_INSTANT_WIN_CAMPAIGNS_INTO_BATCH =
			  " UPDATE Listings l "
			+ " SET l.batch_processor = ? "
				// considered active
			+ " WHERE l.status = 'ACTIVE' "
				  // only INSTANT-style campaigns
			+ "   AND l.type = 'CAMPAIGN' "
			+ "   AND l.subType = 'INSTANT' "
				  // not claimed by another job
			+ "   AND l.batch_processor IS NULL ";


	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		JdbcTemplate jdbcTemplate = new JdbcTemplate( super.getDataSource() );

		String batchSuffix = super.createBatchProcessorKey( "-NEWLY_EXPIRED_CAMPAIGN" );
		String debugMessage = " newly expired campaigns in elapsed time: ";
		super.executeUpdate( jdbcTemplate, ASSIGN_NEWLY_EXPIRED_CAMPAIGNS_INTO_BATCH, batchSuffix, debugMessage );

		String instantWinBatchSuffix = super.createBatchProcessorKey( "-INSTANT_WIN_CAMPAIGN" );
		String instantWinDebugMessage = " instant win campaigns in elapsed time: ";
		super.executeUpdate( jdbcTemplate, ASSIGN_INSTANT_WIN_CAMPAIGNS_INTO_BATCH, instantWinBatchSuffix, instantWinDebugMessage );

		return null;
	}


}
