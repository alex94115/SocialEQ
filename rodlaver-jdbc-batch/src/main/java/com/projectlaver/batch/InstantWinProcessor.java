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
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

import com.projectlaver.batch.domain.ListingCursorItem;

public class InstantWinProcessor extends CampaignProcessor 
	implements ItemProcessor<ListingCursorItem, ListingCursorItem>, StepExecutionListener {

	private final Log logger = LogFactory.getLog(getClass());

	private static String IDENTIFY_INSTANT_GIVEAWAY_WINNERS =
		" UPDATE Messages m "
	  + "   INNER JOIN ListingReplies lr ON m.id = lr.reply_id "
	  + "   INNER JOIN Listings l ON lr.listing_id = l.id "
	  + " SET m.batch_processor = ? "
	  + " WHERE lr.listing_id = ? "
	  + "   AND m.status='PENDING_LISTING_EXPIRY' "
	  + "   AND m.created < l.expires ";

	@Override
	int identifyWinningMessages( JdbcTemplate jdbcTemplate, ListingCursorItem item, String winningMessagesBatchProcessorKey ) {
		
		Long listingId = item.getListingId();
		
		long start = System.currentTimeMillis();
		int winners = jdbcTemplate.update( IDENTIFY_INSTANT_GIVEAWAY_WINNERS, winningMessagesBatchProcessorKey, listingId );
		if( this.logger.isDebugEnabled() ) {
			long elapsedTime = System.currentTimeMillis() - start;
			this.logger.debug( "Updated: " + winners + " giveaway winners for giveaway id: " + listingId +
								" and isDigitalGiveaway: " + item.getIsDigitalGiveaway() + " in elapsed time: " + elapsedTime );
		}
		
		return winners;
	}
	
}
