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
package com.projectlaver.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.projectlaver.domain.EffectiveDatedEntity;
import com.projectlaver.domain.Inventory;
import com.projectlaver.domain.Message;
import com.projectlaver.domain.User;
import com.projectlaver.util.BusinessMetricsDTO;
import com.projectlaver.util.MessageStatus;

public class JdbcBulkOperationsRepository implements BulkOperationsRepository {

	/**
	 * Instance variables
	 */
	
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * A simple Jdbc template for executing static SQL
	 */
	private final JdbcTemplate jdbcTemplate;

	/**
	 * A named parameter jdbc template to allow querying with a variable number of parameters (for all sellers)
	 */
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	
	/**
	 * Static variables
	 */

	private static final String SELECT_USER_ID_BY_PROVIDER_USER_ID =
			"SELECT c.userId "
			+ " FROM UserConnection c "
			+ " WHERE c.providerId=? "
			+ "   AND c.providerUserId=? ";

	private static final String INSERT_LISTING_MESSAGE =
			  " INSERT INTO Messages "
			+ " SET user_id = ?, "
			+ "     listing_id = ?, "
			+ "     providerId = ?, "
			+ "     providerUserId = ?, "
			+ "     text = ?, "
			+ "     twitterId = ?, "
			+ "     inResponseToTwitterId = ?, "
			+ "     status = ?, "
			+ "     created = NOW(), "
			+ "     updated = NOW(), "
			+ "     version = ? ";

	private static final String UPDATE_MESSAGE =
			  " UPDATE Messages "
			+ " SET user_id = ?, "
			+ "     listing_id = ?, "
			+ "     providerId = ?, "
			+ "     providerUserId = ?, "
			+ "     text = ?, "
			+ "     twitterId = ?, "
			+ "     inResponseToTwitterId = ?, "
			+ "     containsKeyword = ?, "
			+ "     status = ?, "
			+ "     updated = NOW(), "
			+ "     version = ?, "
			+ "     batch_processor = ? "
			+ " WHERE id = ? AND version = ? ";
	
	private static final String FIND_USER_PENDING_MEANS_OF_PAYMENT_LISTINGS = 
			  " SELECT l.id "
			+ " FROM Listings l "
			+ "   INNER JOIN Messages lm on l.id = lm.listing_id "
			+ "   INNER JOIN Messages rm ON (lm.providerId = rm.providerId AND lm.twitterId = rm.inResponseToTwitterId) "
			+ " WHERE rm.status='PENDING_MEANS_OF_PAYMENT' "
			+ "   AND rm.user_id = ? "
			+ " ORDER BY rm.created DESC ";
	
	private static final String FIND_RECENT_BATCH_JOB_EXECUTIONS = 
			  " SELECT COUNT( JOB_EXECUTION_ID ) "
			+ " FROM BATCH_JOB_EXECUTION "
			+ " WHERE END_TIME >= (NOW() - INTERVAL 3 MINUTE ) "
			+ "   AND EXIT_CODE = 'COMPLETED' ";
	
	private static final String SELECT_BLOCKED_PENDING_REGISTRATION_PURCHASE_MESSAGES =
			  " SELECT rm.id, rm.version, u.id "
			+ " FROM Messages rm "
			+ "   INNER JOIN Messages lm ON ( rm.inResponseToTwitterId = lm.twitterId AND rm.providerId = lm.providerId ) "
			+ "   INNER JOIN Listings l ON lm.listing_id = l.id "
			+ "   INNER JOIN UserConnection uc ON ( uc.providerUserId = rm.providerUserId AND uc.providerId = rm.providerId ) "
			+ "   INNER JOIN Users u ON uc.userId = u.username "
			+ " WHERE rm.status = 'PENDING_USER_REGISTRATION' "
			+ "   /* A sale-type listing */ "
			+ "   AND l.type = 'SELLING' "
			+ "   AND u.is_user_blocked IS FALSE "
			+ "   AND u.has_accepted_current_buyer_tos IS TRUE "
			+ "   AND uc.providerId = ? "
			+ "   AND uc.providerUserId = ? ";
	
	private static final String UPDATE_PENDING_REGISTRATION_PURCHASE_MESSAGE = 
			  " UPDATE Messages "
	  		+ " SET version = ( :newMessageVersion ),"
	  		+ "     batch_processor = ( :batchProcessor ),"
	  		+ "     status = ( :messageStatus ), "
	  		+ "     user_id = ( :userId ) "
	  		+ " WHERE id = ( :messageId )"
	  		+ "   AND version = ( :messageVersion ) ";
	
	public static final String SELECT_BUSINESS_METRICS = 
			  " SELECT "
			+ "  bm.seller_id as seller_id, "
			+ "  bm.listing_id as listing_id, "
			+ "  SUM(bm.responses) as responses, "
			+ "  SUM(bm.retweets) as retweets, "
			+ "  SUM(bm.fb_responses) as fb_responses, "
			+ "  SUM(bm.tw_responses) as tw_responses, "
			+ "  SUM(bm.hashtag_responses) as hashtag_responses, "
			+ "  SUM(bm.fb_hashtag_responses) as fb_hashtag_responses, "
			+ "  SUM(bm.tw_hashtag_responses) as tw_hashtag_responses, "
			+ "  SUM(bm.opt_ins) as opt_ins, "
			+ "  SUM(bm.tw_opt_ins) as tw_opt_ins, "
			+ "  SUM(bm.fb_opt_ins) as fb_opt_ins, "
			+ "  SUM(bm.payments) as payments, "
			+ "  SUM(bm.tw_payments) as tw_payments, "
			+ "  SUM(bm.fb_payments) as fb_payments, "
			+ "  SUM(bm.gross_sales) as gross_sales, "
			+ "  SUM(bm.tw_gross_sales) as tw_gross_sales, "
			+ "  SUM(bm.fb_gross_sales) as fb_gross_sales, "
			+ "  SUM(bm.net_sales) as net_sales, "
			+ "  SUM(bm.tw_net_sales) as tw_net_sales, "
			+ "  SUM(bm.fb_net_sales) as fb_net_sales "
			+ " FROM BusinessMetrics bm "
			+ "   INNER JOIN MetricsJobExecutions mje ON bm.metrics_execution_id = mje.id  "
			+ " WHERE mje.start_time >=  ? "
			+ "   AND mje.end_time < ? ";
	
	public static final String SELECT_BUSINESS_METRICS_BY_LISTING_ID =
			SELECT_BUSINESS_METRICS + " AND bm.listing_id = ? ";
	
	public static final String SELECT_BUSINESS_METRICS_BY_SELLER_ID =
			SELECT_BUSINESS_METRICS + " AND bm.seller_id = ? ";
	
	public static final String INSERT_DOWNLOAD_INSTANCE =
			  " INSERT INTO DownloadInstances "
			+ " SET version = 0,"
			+ "   user_id = ?, "
			+ "   listing_id = ?, "
		 	+ "   content_file_id = ?, "
			+ "   payment_id = ? ";
	
	private static final String IS_USER_IN_CURRENT_TWITTER_STREAM =
			  " SELECT COUNT(*) > 0  FROM CurrentTwitterStreamSellers WHERE user_id = ? ";
	
	private static final String SELECT_AND_LOCK_INVENTORY_QUANTITY = 
			  " SELECT i.version currentVersion, i.remainingQuantity remainingQuantity "
			+ " FROM Inventories i "
			+ " WHERE i.id = ? "
			+ " LOCK IN SHARE MODE ";
	
	private static final String UPDATE_INVENTORY_QUANTITY = 
			  " UPDATE Inventories "
			+ " SET version = ?, remainingQuantity = ? "
			+ " WHERE id = ? and version = ? ";
	
	private static final String DOES_LISTING_ID_EXIST = 
			"SELECT count(id) = 1 FROM Listings WHERE id = ?";
	
	private static final String SELECT_SELLERS_WITH_ACTIVE_FACEBOOK_LISTINGS = 
			  " SELECT DISTINCT u.id "
			+ " FROM Users u "
			+ "   INNER JOIN Listings l ON u.id = l.seller_id "
			+ " WHERE l.doPostToFacebook IS TRUE "
			+ "   AND l.status='ACTIVE'";
	
	/**
	 * Constructor
	 */

	public JdbcBulkOperationsRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate( dataSource );
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate( dataSource );
	}
	
	/** 
	 * Public methods
	 */

	@Override
	public String getUsernameByProviderUserId( final String providerId, final String providerUserId ) {
		String result = null;

		try {
			this.logger.info( SELECT_USER_ID_BY_PROVIDER_USER_ID );
			
			result = this.jdbcTemplate.query(
				SELECT_USER_ID_BY_PROVIDER_USER_ID,
				new PreparedStatementSetter() {
		            @Override
					public void setValues(PreparedStatement preparedStatement) throws SQLException {
		                  preparedStatement.setString(1, providerId);
		                  preparedStatement.setString(2, providerUserId);

		            }
		        },
		        new ResultSetExtractor<String>() {
		        	@Override
		        	public String extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		        		if (resultSet.next()) {
		        			return resultSet.getString(1);
		        		}
		        		return null;
		        	}
		        }
		    );
		} catch( Exception e ) {
			throw new RuntimeException( e );
		}

		return result;
	}

	@Override
	public Boolean updateMessage( Message message ) {

		int newVersion = message.getVersion() + 1;
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "In updateMessage method." );
			this.logger.debug( "User id: " + message.getUser().getId() );
			
			this.logger.debug( "Listing id: " + ( message.getListing() == null ? "" : message.getListing().getId() ));
			this.logger.debug( "Provider Id: " + message.getProviderId() );
			this.logger.debug( "Provider User Id: " + message.getProviderUserId() );
			this.logger.debug( "Text: " + message.getText() );
			this.logger.debug( "Twitter Id: " + message.getTwitterId() );
			this.logger.debug( "In Response To Twitter Id: " + message.getInResponseToTwitterId() );
			this.logger.debug( "Contains Keyword: " + message.getContainsKeyword() );
			this.logger.debug( "Status: " + message.getStatus().toString() );
			this.logger.debug( "New version: " + newVersion );
			this.logger.debug( "Batch processor: " + message.getBatchProcessor() );
			this.logger.debug( "Message id: " + message.getId() );
			this.logger.debug( "Current message version: " + message.getVersion() );
		}

		this.logger.info( UPDATE_MESSAGE );
		int rows = this.jdbcTemplate.update( UPDATE_MESSAGE,
											 new Object[] {
												 message.getUser().getId(),
												 ( message.getListing() == null ? null : message.getListing().getId()),
												 message.getProviderId(),
												 message.getProviderUserId(),
												 message.getText(),
												 message.getTwitterId(),
												 message.getInResponseToTwitterId(),
												 message.getContainsKeyword(),
												 message.getStatus().toString(),
												 newVersion,
												 message.getBatchProcessor(),
												 message.getId(),
												 message.getVersion()
											} );

		return rows == 1;
	}

	@Override
	public Message insertListingMessage( final Message message ) {
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreator insertMessagePSC = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement( Connection connection ) throws SQLException {
				PreparedStatement ps = connection.prepareStatement( INSERT_LISTING_MESSAGE, new String[] { "id" } );
				ps.setLong( 	1, message.getUser().getId() ); // user_id
				ps.setLong( 	2, message.getListing().getId() ); // listing_id
				ps.setString( 	3, message.getProviderId() ); // providerId
				ps.setString( 	4, message.getProviderUserId() ); // providerUserId
				ps.setString( 	5, message.getText() ); // text
				ps.setString( 	6, message.getTwitterId() ); // twitterId
				ps.setString( 	7, message.getInResponseToTwitterId() ); // inResponseToTwitterId
				ps.setString( 	8, message.getStatus().toString() ); // status
				ps.setInt( 		9, message.getVersion() ); // version
				return ps;
			}
		};

		this.logger.info( INSERT_LISTING_MESSAGE );
		this.jdbcTemplate.update( insertMessagePSC, keyHolder );
		if( keyHolder.getKey() != null ) {
			message.setId( keyHolder.getKey().longValue() );
		}

		return message;
	}

	@Override
	public List<Long> findUserPendingMeansOfPaymentListingIds( final Long userId ) {
		
		List<Long> result = null;
		try {
			this.logger.info( FIND_USER_PENDING_MEANS_OF_PAYMENT_LISTINGS );
			result = this.jdbcTemplate.query(
				FIND_USER_PENDING_MEANS_OF_PAYMENT_LISTINGS,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement preparedStatement) throws SQLException {
		                  preparedStatement.setLong( 1, userId );
		              }
		        },
		        new ResultSetExtractor<List<Long>>() {
		        	@Override
		            public List<Long> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
		        		List<Long> result = new ArrayList<Long>();
		            	  
		            	while (resultSet.next()) {
		            		Long id = resultSet.getLong( "id" );
		                	result.add( id );
		                }
		                return result;
		            }
		        });
		} catch( Exception e ) {
			throw new RuntimeException( e );
		}
		
		return result;
	}
	
	/**
	 * Queries the database for batch job executions that ended within the past few minutes.
	 */
	public Boolean isBatchJobHealthy() {
		
		Integer recentBatchExecutions = null;
		try {
			// omitting this cause it clutters the logs too much
			//this.logger.info( FIND_RECENT_BATCH_JOB_EXECUTIONS );
			recentBatchExecutions = this.jdbcTemplate.query(
				FIND_RECENT_BATCH_JOB_EXECUTIONS,
	            new ResultSetExtractor<Integer>() {
	              @Override
	              public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
	                  if (resultSet.next()) {
	                	  return resultSet.getInt(1);
	                  }
	                  return null;
	              }
	            });
		} catch( Exception e ) {
			throw new RuntimeException( e );
		}

		return recentBatchExecutions != null && recentBatchExecutions > 0;
	}
	
	@Override
	public int unblockPendingRegistrationPurchaseMessages( final String providerId, final String providerUserId  ) {
		
		int updatedRows = 0;
		try {
			List<Map<String, Object>> messages = null;
			// query for any 'pending user regitstration' messages for this user
			this.logger.info( SELECT_BLOCKED_PENDING_REGISTRATION_PURCHASE_MESSAGES );
			messages = this.jdbcTemplate.query( SELECT_BLOCKED_PENDING_REGISTRATION_PURCHASE_MESSAGES,
				new PreparedStatementSetter() {
		            @Override
					public void setValues(PreparedStatement preparedStatement) throws
		                SQLException {
		                  preparedStatement.setString(1, providerId);
		                  preparedStatement.setString(2, providerUserId);

		              }
		            },
		            new ResultSetExtractor<List<Map<String, Object>>>() {
			            @Override
						public List<Map<String, Object>> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			            
			            	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			            	while( resultSet.next() ) {
			                	Map<String, Object> dto = new HashMap<String, Object>();

			                	dto.put( "messageId", resultSet.getLong( 1 ));
			                	dto.put( "messageVersion", resultSet.getInt( 2 ));
			                	dto.put( "userId", resultSet.getLong( 3 ));
			                	result.add( dto );
			                 }
			                 return result;
			              }
			            }
		        );
			
			// if exists, unblock these messages by updating their userId, resetting to 'processing' status, and nulling out the batch processor
			if( messages != null && !messages.isEmpty() ) {
				for( Map<String, Object> message : messages ) {
					// update each message to update the batch processor, the status, and the user id
					
					Integer newMessageVersion = ( Integer )message.get( "messageVersion" ) + 1;
					String batchProcessor = null;
					String messageStatus = MessageStatus.PENDING_MEANS_OF_PAYMENT.toString();
					Long userId = ( Long )message.get( "userId" );
					
					message.put( "newMessageVersion", newMessageVersion );
					message.put( "batchProcessor", batchProcessor );
					message.put( "messageStatus", messageStatus );
					message.put( "userId", userId );
					
					int rows = this.namedParameterJdbcTemplate.update( UPDATE_PENDING_REGISTRATION_PURCHASE_MESSAGE, message );
					
					updatedRows += rows;
				}
			}
			
		} catch( Exception e ) {
			throw new RuntimeException( e );
		}
		
		return updatedRows;
		
	}
	
	@Override
	public BusinessMetricsDTO getMetricsByListingId( final Long listingId, final Long start, final Long end ) {
		return this.doBusinessMetrics( SELECT_BUSINESS_METRICS_BY_LISTING_ID, listingId, start, end );
	}

	
	@Override
	public BusinessMetricsDTO getMetricsBySellerId( final Long sellerId, final Long start, final Long end ) {
		return this.doBusinessMetrics( SELECT_BUSINESS_METRICS_BY_SELLER_ID, sellerId, start, end );
	}
	
	@Override
	public void insertDownloadInstance( Long userId, Long listingId, Long contentFileId, Long paymentId ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( INSERT_DOWNLOAD_INSTANCE );
		}
		this.jdbcTemplate.update( INSERT_DOWNLOAD_INSTANCE, userId, listingId, contentFileId, paymentId );		
	}
	
	@Override
	public Boolean isInCurrentTwitterStream( User seller ) {
		this.logger.info( IS_USER_IN_CURRENT_TWITTER_STREAM );
		return this.jdbcTemplate.queryForObject( IS_USER_IN_CURRENT_TWITTER_STREAM, new Object[] { seller.getId() }, Boolean.class );
	}

	@Override
	public Boolean decrementInventory( Inventory inventory, Integer decrementQuantity ) {
		
		// Return a boolean if this method succeeds in decrementing the inventory
		Boolean didSucceed = false;
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "+++decrementListingQuantity for inventory id: %d using decrementQuantity: %d", inventory.getId(), decrementQuantity ) );
		}
		
		this.logger.info( SELECT_AND_LOCK_INVENTORY_QUANTITY );
		Map<String, Object> values = this.jdbcTemplate.query( 
				SELECT_AND_LOCK_INVENTORY_QUANTITY, 
			new Object[] { inventory.getId() },  
			new ResultSetExtractor<Map<String, Object>>() {
	        	@Override
	            public Map<String, Object> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
	        		Map<String, Object> result = null;
	            	  
	            	if( resultSet.next() ) {
	            		result = new HashMap<String, Object>();
	            		result.put( "currentVersion", resultSet.getInt( "currentVersion" ));
	            		result.put( "remainingQuantity", resultSet.getInt( "remainingQuantity" ));
	                }
	                return result;
	            }
        });
		
		// Extract the items from the map
		Integer currentVersion = (Integer) values.get( "currentVersion" );
		Integer remainingQuantity = (Integer) values.get( "remainingQuantity" );
		
		if( decrementQuantity > 0 && remainingQuantity >= decrementQuantity ) {
			
			Integer updatedVersion = currentVersion + 1;
			Integer updatedRemainingQuantity = remainingQuantity - decrementQuantity;
	
			this.logger.info( UPDATE_INVENTORY_QUANTITY );
			Integer rows = this.jdbcTemplate.update( 
					UPDATE_INVENTORY_QUANTITY,  updatedVersion, updatedRemainingQuantity, inventory.getId(), currentVersion );
	
			if( rows != 1 ) {
				throw new RuntimeException( String.format( "Unexpected condition trying to decrement the quantity of the inventory with id: %d ", inventory.getId() ) );
			}
		
			// If we get to this point, there was sufficient quantity available and we were able to decrement a single row in the Inventories table
			didSucceed = true;
			
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Decrement quantity succeeded." );
			}
			
		} else {

			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Decrement quantity failed." );
			}

		}
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "---decrementListingQuantity" );
		}
		
		return didSucceed;
	}
	
	@Override
	public Boolean doesListingExist( Long id ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( DOES_LISTING_ID_EXIST );
		}
		return this.jdbcTemplate.queryForObject( DOES_LISTING_ID_EXIST, new Object[] { id }, Boolean.class );
	}
	
	@Override
	public List<Long> findUsersWithActiveFacebookListings() {
		
		return this.jdbcTemplate.queryForList( SELECT_SELLERS_WITH_ACTIVE_FACEBOOK_LISTINGS, Long.class );
		
	}
	
	
	/**
	 * Private Methods
	 */
	
	/**
	 * 
	 * @param sql the SQL select statement
	 * @param id either the seller id or the listing id, depending on the mode
	 * @param start earlist metrics date
	 * @param end latest metrics date
	 * @return summary metrics for this listing or seller
	 */
	private BusinessMetricsDTO doBusinessMetrics( final String sql, final Long id, final Long start, Long end ) {

		final Timestamp queryStart = ( start == null ) ?  new Timestamp( EffectiveDatedEntity.START_OF_TIME ) : new Timestamp( start );
		
		final Timestamp queryEnd = ( end == null ) ?  new Timestamp( EffectiveDatedEntity.END_OF_TIME ) : new Timestamp( end ); 
		
		this.logger.info( sql );
		BusinessMetricsDTO result = this.jdbcTemplate.query(
				sql,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement preparedStatement) throws SQLException {
						preparedStatement.setTimestamp( 1, queryStart );
						preparedStatement.setTimestamp( 2, queryEnd );
						preparedStatement.setLong( 3, id );
					}
				}, new ResultSetExtractor<BusinessMetricsDTO>() {
					@Override
					public BusinessMetricsDTO extractData(ResultSet resultSet) throws SQLException, DataAccessException {
						BusinessMetricsDTO result = null;
						if (resultSet.next()) {
							result = new BusinessMetricsDTO();

							result.setSellerId( resultSet.getLong( "seller_id" ) );
							result.setListingId( resultSet.getLong( "listing_id" ) );
							result.setResponses( resultSet.getInt( "responses" ) );
							result.setRetweets( resultSet.getInt( "retweets" ) );
							result.setFacebookResponses( resultSet.getInt( "fb_responses" ) );
							result.setTwitterResponses( resultSet.getInt( "tw_responses" ) );
							result.setHashtagResponses( resultSet.getInt( "hashtag_responses" ) );
							result.setFacebookHashtagResponses( resultSet.getInt( "fb_hashtag_responses" ) );
							result.setTwitterHashtagResponses( resultSet.getInt( "tw_hashtag_responses" ) );
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
		
		return result;
	}

}
