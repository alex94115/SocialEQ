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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

import com.projectlaver.batch.domain.ListingCursorItem;

public class GiveawayDrawProcessor extends CampaignProcessor 
	implements ItemProcessor<ListingCursorItem, ListingCursorItem>, StepExecutionListener {

	private final Log logger = LogFactory.getLog(getClass());


	/**
	 * Constants
	 */

	private static String IDENTIFY_DRAWING_GIVEAWAY_WINNERS =
			" UPDATE Messages m "
		  + " SET m.batch_processor = ? "
		  + " WHERE m.id IN "
		  + " ( SELECT id FROM "
		  + "  ( SELECT id, rand(?) rank FROM "
		  + "   ( SELECT reply.id "
		  + "     FROM Listings l "
		  + "      INNER JOIN Messages listing_message ON l.id = listing_message.listing_id "
		  + "      INNER JOIN Messages reply ON listing_message.twitterId = reply.inResponseToTwitterId "
		  + "     WHERE l.id = ? "
		  + "      AND reply.status='PENDING_LISTING_EXPIRY' "
		  		   // make sure that the entry came before the deadline
		  + "      AND reply.created < l.expires "
		  		  // explicitly order the replies by their ids
		  + "     ORDER BY reply.id ASC "
		  + "    ) As Sub1 "
		  		 // then apply a sort based on the random number and
		  		 // limit the results to the maximum number of winners
		  + "    ORDER BY rank LIMIT ? "
		  + "  ) AS Sub2 "
		  + " ) ";

	@Override
	int identifyWinningMessages( JdbcTemplate jdbcTemplate, ListingCursorItem item, String winningMessagesBatchProcessorKey ) {

		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Entered the identifyWinningMessages method with cursor item: " +  ToStringBuilder.reflectionToString( item ));
		}

		// Choose the winners for this giveaway
		Integer giveawaySeed = item.getGiveawaySeed();
		Long listingId = item.getListingId();
		Integer maxWinnersQuantity = item.getQuantity();

		long start = System.currentTimeMillis();
		int winners = jdbcTemplate.update( IDENTIFY_DRAWING_GIVEAWAY_WINNERS, winningMessagesBatchProcessorKey, giveawaySeed, listingId, maxWinnersQuantity );
		if( this.logger.isDebugEnabled() ) {
			long elapsedTime = System.currentTimeMillis() - start;
			this.logger.debug( "Updated: " + winners + " giveaway winners for giveaway id: " + listingId +
								" and isDigitalGiveaway: " + item.getIsDigitalGiveaway() + " in elapsed time: " + elapsedTime );
		}
		
		return winners;

	}


}
