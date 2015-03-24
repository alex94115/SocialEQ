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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.projectlaver.batch.domain.BusinessMetricsItem;
import com.projectlaver.batch.domain.BusinessMetricsCursorItem;

public class MetricsCalculatingProcessor implements ItemProcessor<BusinessMetricsCursorItem, BusinessMetricsItem> {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private DataSource dataSource;
	
	private static final String SELECT_METRICS = 
	  " SELECT responses, retweets, tw_responses, fb_responses, ( tw_hashtag_responses + fb_hashtag_responses ) hashtag_responses, tw_hashtag_responses, fb_hashtag_responses, " 
	+ "   opt_ins, tw_opt_ins, fb_opt_ins, ( tw_payments + fb_payments ) payments, tw_payments, fb_payments, ( tw_gross_sales + fb_gross_sales ) gross_sales, tw_gross_sales, fb_gross_sales, "
	+ "   ( tw_net_sales + fb_net_sales ) net_sales, tw_net_sales, fb_net_sales "
	+ " FROM " 
	+ " (  "
	+ " SELECT COUNT(*) responses,  "
	+ "   SUM( CASE WHEN rm.isRetweet IS TRUE then 1 ELSE 0 END ) retweets, "
	+ "   SUM( CASE WHEN rm.providerId = 'twitter' THEN 1 ELSE 0 END ) tw_responses, "
	+ "   SUM( CASE WHEN rm.providerId = 'facebook' THEN 1 ELSE 0 END ) fb_responses, "
	+ "   SUM( CASE WHEN rm.providerId = 'twitter' AND rm.containsKeyword IS TRUE THEN 1 ELSE 0 END ) tw_hashtag_responses, "
	+ "   SUM( CASE WHEN rm.providerId = 'facebook' AND rm.containsKeyword IS TRUE THEN 1 ELSE 0 END ) fb_hashtag_responses "
	+ "  FROM Listings l  "
	+ "   INNER JOIN Messages lm ON lm.listing_id = l.id  "
	+ "   INNER JOIN Messages rm ON rm.inResponseToTwitterId = lm.twitterId " 
	+ "  WHERE l.id = ?        /* 1. listing id */  "
	+ "   AND rm.created >= ? /* 2. start time */ "
	+ "   AND rm.created < ?   /* 3. end time */ "
	+ "  ) As Sub1, " 
	+ "  (  "
	+ "  SELECT COUNT(*) opt_ins, "
	+ "   SUM( CASE WHEN rm.providerId = 'twitter' THEN 1 ELSE 0 END ) tw_opt_ins, "
	+ "   SUM( CASE WHEN rm.providerId = 'facebook' THEN 1 ELSE 0 END ) fb_opt_ins "
	+ "  FROM Listings l   "
	+ "   INNER JOIN Messages lm ON lm.listing_id = l.id  "
	+ "   INNER JOIN Messages rm ON rm.inResponseToTwitterId = lm.twitterId " 
	+ "  WHERE l.id = ?        /* 4. listing id */ "
	+ "   /* looks for replies to campaigns where the result is better than irrelevant or duplicate, in other words 'true' entrants */ " 
	+ "   AND l.type='CAMPAIGN' "
	+ "   AND rm.status != 'IRRELEVANT'  "
	+ "   AND rm.status != 'FAILED_DUPLICATE_OPT_IN'  "
	+ "   AND rm.created >= ?  /* 5. start time */ "
	+ "   AND rm.created < ?   /* 6. end time */ "
	+ " ) As Sub2, "
	+ "  (  "
	+ " SELECT SUM( CASE WHEN rm.providerId = 'twitter' THEN 1 ELSE 0 END ) tw_payments, "
	+ "   SUM( CASE WHEN rm.providerId = 'facebook' THEN 1 ELSE 0 END ) fb_payments, "
	+ "   SUM( CASE WHEN rm.providerId = 'twitter' THEN p.amount ELSE 0 END ) tw_gross_sales, "
	+ "   SUM( CASE WHEN rm.providerId = 'facebook' THEN p.amount ELSE 0 END ) fb_gross_sales, "
	+ "   SUM( CASE WHEN rm.providerId = 'twitter' THEN p.sellerAmount ELSE 0 END ) tw_net_sales, "
	+ "   SUM( CASE WHEN rm.providerId = 'facebook' THEN p.sellerAmount ELSE 0 END ) fb_net_sales "
	+ " FROM Payments p  "
	+ "   INNER JOIN EffectivePaymentStatuses eps ON p.id = eps.payment_id " 
	+ "   INNER JOIN Listings l ON p.listing_id = l.id "
	+ "   INNER JOIN Messages rm ON p.message_id = rm.id " 
	+ " WHERE l.id = ?         /* 7. listing id */ "
	+ "   /* ignore giveaways and donations */ "
	+ "   AND l.type = 'SELLING'  "
	+ "   /* look for an effective payment status that was inserted in our metrics window  */ "
	+ "   AND eps.created >= ? /* 8. start time */ "
	+ "   AND eps.created < ?  /* 9. end time */ "
	+ "    /* just select the row that is effective at the conclusion of our window  */ "
	+ "   AND eps.start <= ?   /* 10. end time */ "
	+ "   AND eps.end > ?      /* 11. end time */ "
	+ "   /* only count payments that have become 'COMPLETED'  */ "
	+ "   AND eps.status = 'COMPLETED' "
	+ " ) As Sub3 ";

	@Override
	public BusinessMetricsItem process( BusinessMetricsCursorItem item ) throws Exception {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Entered the process method with cursor item: " +  ToStringBuilder.reflectionToString( item ));
		}
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate( this.dataSource );
		
		// For this cursor item, query for the metrics and populate the result
		final Long listingId = item.getListingId();
		final Timestamp start = item.getStartTime();
		final Timestamp end = item.getEndTime();
		
		BusinessMetricsItem result = jdbcTemplate.query(
				SELECT_METRICS,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement preparedStatement) throws SQLException {
						preparedStatement.setLong( 1, listingId );
						preparedStatement.setTimestamp( 2, start );
						preparedStatement.setTimestamp( 3, end );
						preparedStatement.setLong( 4, listingId );
						preparedStatement.setTimestamp( 5, start );
						preparedStatement.setTimestamp( 6, end );
						preparedStatement.setLong( 7, listingId );
						preparedStatement.setTimestamp( 8, start );
						preparedStatement.setTimestamp( 9, end );
						preparedStatement.setTimestamp( 10, end );
						preparedStatement.setTimestamp( 11, end );
					}
				}, new ResultSetExtractor<BusinessMetricsItem>() {
					@Override
					public BusinessMetricsItem extractData(ResultSet resultSet) throws SQLException, DataAccessException {
						BusinessMetricsItem result = null;
						if (resultSet.next()) {
							result = new BusinessMetricsItem();
							result.setResponses( resultSet.getInt( "responses" ) );
							result.setRetweets( resultSet.getInt( "retweets" ) );
							result.setTwitterResponses( resultSet.getInt( "tw_responses" ) );
							result.setFacebookResponses( resultSet.getInt( "fb_responses" ) );
							result.setHashtagResponses( resultSet.getInt( "hashtag_responses" ) );
							result.setTwitterHashtagResponses( resultSet.getInt( "tw_hashtag_responses" ) );
							result.setFacebookHashtagResponses( resultSet.getInt( "fb_hashtag_responses" ) );
							result.setOptIns( resultSet.getInt( "opt_ins" ) );
							result.setTwitterOptIns( resultSet.getInt( "tw_opt_ins" ) );
							result.setFacebookOptIns( resultSet.getInt( "fb_opt_ins" ) );
							result.setPayments( resultSet.getInt( "payments" ) );
							result.setTwitterPayments( resultSet.getInt( "tw_payments" ) );
							result.setFacebookPayments( resultSet.getInt( "fb_payments" ) );
							result.setGrossSales( resultSet.getBigDecimal( "gross_sales" ) );
							result.setTwitterGrossSales( resultSet.getBigDecimal( "tw_gross_sales" ) );
							result.setFacebookGrossSales( resultSet.getBigDecimal( "fb_gross_sales" ) );
							result.setNetSales( resultSet.getBigDecimal( "net_sales" ) );
							result.setTwitterNetSales( resultSet.getBigDecimal( "tw_net_sales" ) );
							result.setFacebookNetSales( resultSet.getBigDecimal( "fb_net_sales" ) );
							
						}
						return result;
					}
				});
		
		// add the input parameters to the result
		if( result != null ) {
			result.setSellerId( item.getSellerId() );
			result.setListingId( listingId );
			result.setMetricsExecutionId( item.getMetricsId() );
		}
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Returning the process method with metrics item: " +  ToStringBuilder.reflectionToString( result ));
		}
		
		return result;
	}
	
}
