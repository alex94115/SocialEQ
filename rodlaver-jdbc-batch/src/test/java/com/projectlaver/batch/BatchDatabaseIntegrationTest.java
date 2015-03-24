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


import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.StepRunner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.projectlaver.integration.SocialProviders;
import com.projectlaver.util.GoodsType;
import com.projectlaver.util.ListingSubType;
import com.projectlaver.util.ListingType;
import com.projectlaver.util.MessageStatus;
import com.projectlaver.util.PaymentStatus;

public abstract class BatchDatabaseIntegrationTest implements ApplicationContextAware {
	
	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * Static variables
	 */
	
	public static final String LISTING_MESSAGE_TWITTER_ID = "listingMessageTwitterId";
	public static final String LISTING_ID = "listingId";
	public static final String INVENTORY_ID = "inventoryId";
	public static final String PROVIDER_ID = "providerId";
	public static final String PROVIDER_USER_ID = "providerUserId";
	public static final String USER_ID = "userId";
	public static final String REPLY_TWITTER_ID = "twitterId";
	public static final String REPLY_MESSAGE_ID = "messageId";
	
	private static final String INSERT_LISTING_SQL = 
			  " INSERT INTO Listings "
			+ " SET version=?, "
			+ "     amount=?, "
			+ "     currencyCode=?, "
			+ "     keyword=?, "
			+ "     status=?, "
			+ "     seller_id=?, "
			+ "     expires=?, "
			+ "     title=?, "
			+ "     type=?, "
			+ "     goodsType=?, "
			+ "     subType=?,"
			+ "     has_single_inventory=? ";
	
	private static final String INSERT_LISTING_MESSAGE =
			  " INSERT INTO Messages "
			+ " SET version=?, "
			+ "     providerId=?, "
			+ "     providerUserId=?, "
			+ "     status=?, "
			+ "     text=?, "
			+ "     twitterId=?, "
			+ "     listing_id=?, "
			+ "     user_id=?";
	
	private static final String INSERT_REPLY_MESSAGE =
			  " INSERT INTO Messages "
			+ " SET version=?, "
			+ "     providerId=?, "
			+ "     providerUserId=?, "
			+ "     user_id=?, "
			+ "     status=?, "
			+ "     text=?, "
			+ "     twitterId=?, "
			+ "     containsKeyword=?, "
			+ "     inResponseToTwitterId=?, "
			+ "     batch_processor=? ";
	
	private static final String INSERT_PAYMENT =
			  " INSERT INTO Payments "
			+ " SET version=?, "
			+ "     payKey=?, "
			+ "     correlationId=?, "
			+ "     rodlaverAmount=?, "
			+ "     sellerAmount=?, "
			+ "     amount=?, "
			+ "     listing_id=?, "
			+ "     inventory_id=?, "
			+ "     quantity=?, "
			+ "     message_id=?, "
			+ "     payee_id=?, "
			+ "     payer_id=? ";
	
	private static final String INSERT_EFFECTIVE_PAYMENT_STATUS_SQL =  
		    " INSERT INTO `EffectivePaymentStatuses` "
		  + " SET start = ?, " 
		  + "   end = ?, " 
		  + "   status = ?, "
		  + "   payment_id = ?, "
		  + "   version = ? ";
	
	private static final String INSERT_INVENTORY_SQL =
			" INSERT INTO `Inventories` "
		  + " SET product_description = ?, "
		  + "   quantity = ?, "
		  + "   remainingQuantity = ?, "
		  + "   listing_id = ?, "
		  + "   version = 0 ";

	/**
	 * Instance variables
	 */
	
	protected JdbcTemplate jdbcTemplate;
	
	protected StepRunner stepRunner;
	
	@Autowired
	protected JobLauncher jobLauncher;

	@Autowired
	protected JobRepository jobRepository;
	
	protected ApplicationContext context;
	
	// used to pass the listing message's twitter id from setup -> test
	protected String testListingMessageTwitterId;
	protected String alternateListingMessageTwitterId;
	
	protected Map<String, Object> sellerAttributes = new HashMap<String, Object>();
	protected Map<String, Object> otherRegisteredUserAttributes = new HashMap<String, Object>();
	protected Map<String, Object> otherUnregisteredUserAttributes = new HashMap<String, Object>();
	protected Map<String, Object> testRegisteredUserAttributes = new HashMap<String, Object>();
	protected Map<String, Object> testPreapprovedUserAttributes = new HashMap<String, Object>();
	protected Map<String, Object> testUnregisteredUserAttributes = new HashMap<String, Object>();
	protected Map<String, Object> testBlockedUserAttributes = new HashMap<String, Object>();

	/**
	 * Public Methods 
	 */

	
    @Autowired
    public void setDataSource( DataSource dataSource ) {
        this.jdbcTemplate = new JdbcTemplate( dataSource );
    }

    
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
    
	protected void setUp( ListingType listingType, GoodsType goodsType, ListingSubType subType ) {
		this.clearDatabase();
    	this.stepRunner = new StepRunner(jobLauncher, jobRepository);
    	
    	// populate our test seller and test users
    	this.sellerAttributes.putAll( this.createUserAttributes(                SocialProviders.TWITTER, "606486210", 48L ) );   // nancy -- seller
    	this.otherRegisteredUserAttributes.putAll( this.createUserAttributes(   SocialProviders.TWITTER, "1688913799", 237L ) );    // duff -- registered w/o preapproval
    	this.testRegisteredUserAttributes.putAll( this.createUserAttributes(   SocialProviders.TWITTER, "622133588", 179L ) );    // regtestbob -- registered w/o preapproval
    	this.otherUnregisteredUserAttributes.putAll( this.createUserAttributes(   SocialProviders.TWITTER, "2579383766", null  ) );    // regtestreggie -- unregistered
    	this.testUnregisteredUserAttributes.putAll( this.createUserAttributes(   SocialProviders.TWITTER, "2753386369", null ) );    // regsellerdavid -- unregistered
    	this.testBlockedUserAttributes.putAll( this.createUserAttributes(   SocialProviders.TWITTER, "2578905258", 180L ) );    // RegTestBilly -- blocked
    	this.testPreapprovedUserAttributes.putAll( this.createUserAttributes(   SocialProviders.TWITTER, "2596726242", 178L ) );    // lily -- registered w/ preapproval
    	
		// Insert "other" listings of this type along with activity from our "test" and "other" users
		this.setupBackgroundListingsAndActivity( listingType, goodsType, subType );
		
		// Insert the "test" listing 
		Boolean listingHasSingleInventory = true;
		Map<String, Object> result = this.insertListingAndListingMessage( listingType, goodsType, subType, listingHasSingleInventory );
		this.testListingMessageTwitterId = ( String )result.get( LISTING_MESSAGE_TWITTER_ID );
		
		// Insert an "alternate" test listing
		Map<String, Object> alternate = this.insertListingAndListingMessage( listingType, goodsType, subType, listingHasSingleInventory );
		this.alternateListingMessageTwitterId = ( String )alternate.get( LISTING_MESSAGE_TWITTER_ID ); 

	}

	/**
	 * Protected Methods 
	 */


	/**
	 * Insert a reply message into the database
	 * 
	 * @param providerUserId the twitter or Facebook user id
	 * @param userId the RodLaver User Id
	 * @param inResponseToTwitterId the listing message's Twitter or Facebook content id
	 * @param status the RodLaver message status, e.g. PROCESSING or COMPLETED
	 * @param batchProcessor text that represents the batch_processor this message was assigned to
	 * @return Map with the generated twitterId and messageId of the inserted message
	 */
	protected Map<String, Object> insertReplyMessage( 
			String providerUserId, 
			Long userId,
			String inResponseToTwitterId, 
			MessageStatus status, 
			String batchProcessor,
			Boolean containsKeyword ) {
		
		assertNotNull( "Provider User Id should never be null.", providerUserId );
		
		// Insert the reply and get its database id 
		final String twitterId = this.createTwitterId();
		PreparedStatementCreator replyPSC = this.createInsertReplyPSC( providerUserId, userId, inResponseToTwitterId, status, batchProcessor, twitterId, containsKeyword );
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update( replyPSC, keyHolder );
		Long messageId = keyHolder.getKey().longValue();
		
		// add the message keys to the result
		Map<String, Object> result = new HashMap<String, Object>();
		result.put( REPLY_TWITTER_ID, twitterId );
		result.put( REPLY_MESSAGE_ID, messageId );
		
		return result;
	}

	protected String createTwitterId() {
		return RandomStringUtils.randomNumeric( 18 ); // 461564888285196200	
	}
	
	protected Boolean doesMessageWithBatchProcessorExist( String replyMessageId, String batchProcessorPattern ) {
		Integer rows = this.jdbcTemplate.queryForObject( 
			  " SELECT count(id) FROM Messages "
			+ " WHERE twitterId='" + replyMessageId + "'"
			+ "   AND batch_processor LIKE '" + batchProcessorPattern + "'", 
			Integer.class );
		
		Boolean result = (rows != null && rows > 0);
		
		this.logger.debug( String.format( "doesMessageWithBatchProcessorExist( %s, %s ) returns: %s", replyMessageId, batchProcessorPattern, result ) );
		
		return result;
	}
	
	protected Boolean doesMessageWithoutBatchProcessorExist( String replyMessageId ) {
		Integer rows = this.jdbcTemplate.queryForObject( 
			  " SELECT count(id) FROM Messages "
			+ " WHERE twitterId='" + replyMessageId + "'"
			+ "   AND batch_processor IS NULL ", 
			Integer.class );
			
			return (rows != null && rows > 0);
	}

	/**
	 * Executes the MessageDivvyingTasklet via the divvyUpMessages step
	 */
	protected void runDivvyUpMessages() {
		Step step = ( Step )context.getBean( "divvyUpMessages" );
		assertEquals( BatchStatus.COMPLETED, this.stepRunner.launchStep(step).getStatus());
	}
	
	protected Map<String, Object> insertUnprocessedUnregisteredUserReplyMessage( String providerUserId, String listingMessageTwitterId, Boolean containsKeyword ) {
		return this.insertReplyMessage( 
				providerUserId,
				null, // userId
				listingMessageTwitterId, 
				MessageStatus.PROCESSING,
				null,
				containsKeyword );
	}
	
	protected Map<String, Object> insertUnprocessedRegisteredUserReplyMessage( String providerUserId, Long userId, String listingMessageTwitterId, Boolean containsKeyword ) {
		return this.insertReplyMessage( 
				providerUserId,
				userId,
				listingMessageTwitterId, 
				MessageStatus.PROCESSING,
				null,
				containsKeyword  );
	}
	

	
	protected Map<String, Object> insertListingAndListingMessage( ListingType listingType, GoodsType goodsType, ListingSubType subType, Boolean listingHasSingleInventory ) {
		
		PreparedStatementCreator psc = this.createInsertListingPsc( listingType, goodsType, subType, listingHasSingleInventory );
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update( psc, keyHolder );
		Long listingId = keyHolder.getKey().longValue();
		assertTrue( listingId > 0 );
		
		// add an associated inventory row
		Long inventoryId = this.insertInventory( listingId );
		
		// Insert a corresponding listing message & save its twitterId for the test method
		String listingMessageTwitterId = this.createListingMessage( listingId );
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put( LISTING_ID, listingId );
		result.put( INVENTORY_ID,  inventoryId );
		result.put( LISTING_MESSAGE_TWITTER_ID, listingMessageTwitterId );
		
		return result;
	}
	
	protected Long insertReplyWithPayment( 
			String providerUserId, 
			Long buyerId,
			String listingMessageTwitterId,
			MessageStatus messageStatus,
			PaymentStatus paymentStatus,
			String effectiveStart,
			String effectiveEnd,
			Integer paymentQuantity,
			Long listingId, 
			Long sellerId
		) {
		
		Boolean replyContainsKeyword = true;
		Map<String, Object> insertMsgResult = this.insertReplyMessage( 
				providerUserId, buyerId, listingMessageTwitterId, messageStatus, "UNIT_TEST_SIMULATED_PRIOR_BATCH_PROCESSOR", replyContainsKeyword );
		Long messageId = ( Long )insertMsgResult.get( REPLY_MESSAGE_ID );

		Long inventoryId = this.jdbcTemplate.queryForObject( "SELECT id FROM Inventories WHERE listing_id = ?", new Object[] { listingId }, Long.class );
		
		PreparedStatementCreator insertPaymentPSC = this.createInsertPaymentPSC( 
				listingId, inventoryId, paymentQuantity, messageId, sellerId, buyerId );
		
		// Insert the reply and get its database id 
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update( insertPaymentPSC, keyHolder );
		Long paymentId = keyHolder.getKey().longValue();
		
		Object[] insertEffectivePaymentArgs = new Object[] { effectiveStart, effectiveEnd, paymentStatus.toString(), paymentId, 0 };

		// insert a corresponding effective payment status row
		this.jdbcTemplate.update( INSERT_EFFECTIVE_PAYMENT_STATUS_SQL, insertEffectivePaymentArgs );
		
		return paymentId;
	}

	
	/**
	 * Internal Methods 
	 */
	
	private PreparedStatementCreator createInsertListingPsc( final ListingType type, final GoodsType goodsType, final ListingSubType subType, final Boolean listingHasSingleInventory ) {
		
		PreparedStatementCreator insertPaymentPSC = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement( Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement( INSERT_LISTING_SQL, new String[] { "id" } );
				ps.setInt( 1, 0 ); 										 // version
				ps.setBigDecimal( 2, new BigDecimal( "1.00"));			 // amount
				ps.setString( 3, "USD" );								 // currencyCode
				ps.setString( 4, "#Buy" );								 // keyword
				ps.setString( 5, "ACTIVE" );							 // status
				ps.setLong( 6, 48L );									 // seller_id
				ps.setDate( 7, new Date( System.currentTimeMillis() ) ); // expires
				ps.setString( 8, "Title" );								 // title
				ps.setString( 9, type.toString() );						 // type
				ps.setString( 10, goodsType.toString() ); 				 // goods type
				
				if( subType != null ) {
					ps.setString( 11, subType.toString() );				 // subType aka giveaway type
				} else {
					ps.setNull( 11, java.sql.Types.VARCHAR );
				}
				
				ps.setBoolean( 12, listingHasSingleInventory );			 // has single inventory
				
				return ps;
			}
		};
		
		return insertPaymentPSC;
	}
	
	private void clearDatabase() {
		
		
		/**
		 * Reset the batch job execution tables so that we have a 
		 * predictable batch job execution id
		 */
		this.jdbcTemplate.update( "delete from BATCH_STEP_EXECUTION_CONTEXT" );
		this.jdbcTemplate.update( "delete from BATCH_STEP_EXECUTION" );
		this.jdbcTemplate.update( "delete from BATCH_JOB_EXECUTION_PARAMS" );
		this.jdbcTemplate.update( "delete from BATCH_JOB_EXECUTION_CONTEXT" );
		this.jdbcTemplate.update( "delete from BATCH_JOB_EXECUTION" );
		this.jdbcTemplate.update( "delete from BATCH_JOB_INSTANCE" );
		this.jdbcTemplate.update( "update BATCH_JOB_SEQ set ID = 0" );
		this.jdbcTemplate.update( "update BATCH_JOB_EXECUTION_SEQ set ID = 0" );
		
		/**
		 * Clear the application tables
		 */
		
		int addressRows = this.jdbcTemplate.update( "DELETE FROM Addresses" );
		int epsRows = this.jdbcTemplate.update( "DELETE FROM EffectivePaymentStatuses" );
		int evsRows = this.jdbcTemplate.update( "DELETE FROM EffectiveVoucherStatuses" );
		int paymentRows = this.jdbcTemplate.update( "DELETE FROM Payments" );
		int messageStateChangeRows = this.jdbcTemplate.update( "DELETE FROM MessageStateChanges" );
		int replyPagingStatusRows = this.jdbcTemplate.update( "DELETE FROM ReplyPagingStatuses" ); 
		int messageRows = this.jdbcTemplate.update( "DELETE FROM Messages" );
		int listingStateChangeRows = this.jdbcTemplate.update( "DELETE FROM ListingStateChanges" );
		int contentFileRows = this.jdbcTemplate.update( "DELETE FROM ContentFiles" );
		int listingRows = this.jdbcTemplate.update( "DELETE FROM Listings" );
		//int userRoleRows = this.jdbcTemplate.update( "DELETE FROM Users_Roles WHERE users_id > 5 " );
		//int userTermsOfServiceRows = this.jdbcTemplate.update( "DELETE FROM Users_TermsOfServices WHERE users_id > 5 " );
		//int userRows = this.jdbcTemplate.update( "DELETE FROM Users WHERE id > 5 " );
		
		int userRows = this.jdbcTemplate.update( 
				  " DELETE FROM Users "
				+ " WHERE username != 'twitter/regtestnancy' "
				+ "   AND username != 'twitter/Regtestduff' "
				+ "   AND username != 'twitter/regtestbob' "
				+ "   AND username != 'twitter/RegTestLily' "
				+ "   AND username != 'twitter/RegTestBilly' " );
		
	}
	
	private String createListingMessage( Long listingId ) {
		
		String twitterId = this.createTwitterId();
		
		Object[] listingMessageArgs = new Object[] { 
			0, 								// version
			SocialProviders.TWITTER, 	 			 		// providerId
			"606486210", 			 		// providerUserId of @regtestnancy
			"ACTIVE", 				 		// status
			"Unit test listing message", 	// text
			twitterId, 	 					// twitterId
			listingId, 		 		 		// listingId
			48L		 				 		// user_id of @regtestnancy
		};
		
		this.jdbcTemplate.update( INSERT_LISTING_MESSAGE, listingMessageArgs );
		
		return twitterId;
	}
	
	private PreparedStatementCreator createInsertReplyPSC(
			final String providerUserId, 
			final Long userId,
			final String inResponseToTwitterId, 
			final MessageStatus status,
			final String batchProcessor, 
			final String twitterId,
			final Boolean containsKeyword ) {
		return new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement( Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement( INSERT_REPLY_MESSAGE, new String[] { "id" } );
				ps.setInt( 1,  0 );								// version
				ps.setString( 2, SocialProviders.TWITTER ); 					// providerId
				ps.setString( 3, providerUserId ); 				// providerUserId

				// set the user id, if exists.
				if( userId != null ) {
					ps.setLong( 4, userId ); 					// user_id
				} else {
					ps.setNull( 4, Types.BIGINT );
				}
				
				ps.setString( 5, status.toString() ); // status
				ps.setString( 6, "Unit test reply message" ); 	// text
				ps.setString( 7, twitterId ); 					// twitterId
				ps.setBoolean( 8 , containsKeyword ); 			// containsKeyword
				ps.setString( 9, inResponseToTwitterId ); 		// inResponseToTwitterId
				ps.setString( 10, batchProcessor ); 			// batch_processor
				
				return ps;
			}
		};
	}
	
	private PreparedStatementCreator createInsertPaymentPSC( 
			final Long listingId, final Long inventoryId, final Integer purchaseQuantity, final Long messageId, final Long sellerId, final Long buyerId ) {
		return new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement( Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement( INSERT_PAYMENT, new String[] { "id" } );
				
				ps.setInt( 1, 0 ); 												// version
				ps.setString( 2, RandomStringUtils.randomAlphanumeric( 10) ); 	// payKey
				ps.setString( 3, RandomStringUtils.randomAlphanumeric( 10) ); 	// correlationId
				ps.setBigDecimal( 4, new BigDecimal( "0.10" )); 				// rodlaverAmount
				ps.setBigDecimal( 5, new BigDecimal( "0.90" )); 				// sellerAmount
				ps.setBigDecimal( 6, new BigDecimal( "1.00" )); 				// amount
				ps.setLong( 7, listingId ); 									// listingId
				ps.setLong( 8, inventoryId );									// inventoryId
				ps.setInt(  9, purchaseQuantity );								// quantity
				ps.setLong( 10, messageId ); 									// messageId
				ps.setLong( 11, sellerId ); 									// payeeId
				ps.setLong( 12, buyerId ); 										// payerId
				
				return ps;
			}
		};
	}
	
	


	
	
	// create a little map bundle of user attributes
	private Map<String, Object> createUserAttributes( String providerId, String providerUserId, Long userId ) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put( PROVIDER_ID, providerId );
		result.put( PROVIDER_USER_ID, providerUserId );
		if( userId != null ) {
			result.put( USER_ID, userId );
		}
		
		return result;
	}
	
	// Insert some "existing" listings and messages, with various statuses
	private void setupBackgroundListingsAndActivity( ListingType listingType, GoodsType goodsType, ListingSubType subType ) {
		
		// Insert a listing with unprocessed irrelevant (containsKeyword = false) messages
		this.insertBackgroundListingAndUnprocessedActivity( listingType, goodsType, subType, true, true, false );
		
		//Insert a listing with unprocessed relevant (containsKeyword = true) messages
		this.insertBackgroundListingAndUnprocessedActivity( listingType, goodsType, subType, true, true, true );
		
		// IRRELEVANT for both reg and unreg
		this.insertBackgroundListingAndProcessedActivity( listingType, goodsType, subType, true, true, MessageStatus.IRRELEVANT, null );
		
		// PENDING_USER_REG for unreg only
		this.insertBackgroundListingAndProcessedActivity( listingType, goodsType, subType, true, false, MessageStatus.PENDING_USER_REGISTRATION, null );

		// PENDING_MEANS_OF_PAYMENT for both reg and unreg
		this.insertBackgroundListingAndProcessedActivity( listingType, goodsType, subType, true, true, MessageStatus.PENDING_MEANS_OF_PAYMENT, null );

		// FAILED DUPLICATE PURCHASE for both reg and unreg
		// TODO insert one with an actual payment		
		this.insertBackgroundListingAndProcessedActivity( listingType, goodsType, subType, true, true, MessageStatus.FAILED_DUPLICATE_PURCHASE, null );
		

		// Replies with an associated payment in various statuses (only for registered users)
		this.insertBackgroundListingAndProcessedActivity( listingType, goodsType, subType, false, true, MessageStatus.PAYMENT_PROCESSING, PaymentStatus.PAYMENT_PROCESSING );
		this.insertBackgroundListingAndProcessedActivity( listingType, goodsType, subType, false, true, MessageStatus.PAYMENT_PENDING, PaymentStatus.PENDING );
		this.insertBackgroundListingAndProcessedActivity( listingType, goodsType, subType, false, true, MessageStatus.PAYMENT_FAILED, PaymentStatus.FAILED );
		this.insertBackgroundListingAndProcessedActivity( listingType, goodsType, subType, false, true, MessageStatus.PENDING_SHIPPING_ADDRESS, PaymentStatus.COMPLETED );
		this.insertBackgroundListingAndProcessedActivity( listingType, goodsType, subType, false, true, MessageStatus.PENDING_SHIPMENT, PaymentStatus.COMPLETED );
		this.insertBackgroundListingAndProcessedActivity( listingType, goodsType, subType, false, true, MessageStatus.COMPLETED, PaymentStatus.COMPLETED );
		this.insertBackgroundListingAndProcessedActivity( listingType, goodsType, subType, false, true, MessageStatus.PROCESSING_ERROR, PaymentStatus.UNKNOWN );
	}
	
	/**
	 * Inserts a listing, its associated listing message, and then associated 
	 * unprocessed reply activity, simulating activity that could potentially be 
	 * concurrent with our unit test. Useful for making sure all the SQL 
	 * queries in MessageDivvyingTasklet are able to divvy up replies
	 * to multiple listings at once.
	 * 
	 * @param listingType
	 * @param subType
	 * @param doUnregisteredUserActivity
	 * @param doRegisteredUserActivity
	 * @param messageStatus
	 * @param paymentStatus
	 */
	private void insertBackgroundListingAndUnprocessedActivity(
			ListingType listingType, 
			GoodsType goodsType, 
			ListingSubType subType, 
			Boolean doUnregisteredUserActivity, 
			Boolean doRegisteredUserActivity, 
			Boolean containsKeyword ) {
		
		Boolean listingHasSingleInventory = true;

		// Insert the listing and listing message
		Map<String, Object> result = this.insertListingAndListingMessage( listingType, goodsType, subType, listingHasSingleInventory );
		String listingMessageId = ( String )result.get( LISTING_MESSAGE_TWITTER_ID );
		Long listingId = ( Long )result.get( LISTING_ID );

		// insert the reply activity
		if( doUnregisteredUserActivity ) {
			// other user
			String otherUnregisteredUserTwitterId = ( String )this.otherUnregisteredUserAttributes.get( PROVIDER_USER_ID );
			this.insertUnprocessedUnregisteredUserReplyMessage( otherUnregisteredUserTwitterId, listingMessageId, containsKeyword );
			
			// test user
			String testUnretisteredUserTwitterId = ( String )this.testUnregisteredUserAttributes.get( PROVIDER_USER_ID );
			this.insertUnprocessedUnregisteredUserReplyMessage( testUnretisteredUserTwitterId, listingMessageId, containsKeyword );
		}
		
		if( doRegisteredUserActivity ) {
			// Get the identifiers
			String otherRegisteredUserTwitterId = ( String )this.otherRegisteredUserAttributes.get( PROVIDER_USER_ID );
			Long otherRegisteredUserId = ( Long )this.otherRegisteredUserAttributes.get( USER_ID );

			String testRegisteredUserTwitterId = ( String )this.testRegisteredUserAttributes.get( PROVIDER_USER_ID );
			Long testRegisteredUserId = ( Long )this.testRegisteredUserAttributes.get( USER_ID );
			
			// other user
			this.insertUnprocessedRegisteredUserReplyMessage( otherRegisteredUserTwitterId, otherRegisteredUserId, listingMessageId, containsKeyword );
			
			// test user
			this.insertUnprocessedRegisteredUserReplyMessage( testRegisteredUserTwitterId, testRegisteredUserId, listingMessageId, containsKeyword );
		}
		
	}


	/**
	 * 
	 * Inserts a listing, its associated listing message, and then associated 
	 * "preexisting" reply activity, simulating activity that would have
	 * preceded our unit test. Useful for making sure all the SQL 
	 * queries in MessageDivvyingTasklet are able to distinguish new replies
	 * from unrelated "preexisting" reply activity.
	 * 
	 * @param listingType
	 * @param subType
	 * @param doUnregisteredUserActivity
	 * @param doRegisteredUserActivity
	 * @param messageStatus
	 * @param paymentStatus
	 */
	private void insertBackgroundListingAndProcessedActivity(
			ListingType listingType, 
			GoodsType goodsType,
			ListingSubType subType, 
			Boolean doUnregisteredUserActivity, 
			Boolean doRegisteredUserActivity, 
			MessageStatus messageStatus, 
			PaymentStatus paymentStatus ) {
		
		Boolean listingHasSingleInventory = true;
		
		// Insert the listing and listing message
		Map<String, Object> result = this.insertListingAndListingMessage( listingType, goodsType, subType, listingHasSingleInventory );
		String listingMessageId = ( String )result.get( LISTING_MESSAGE_TWITTER_ID );
		Long listingId = ( Long )result.get( LISTING_ID );

		// insert the reply activity
		this.insertPreexistingProcessedReplies( 
				doUnregisteredUserActivity, 
				doRegisteredUserActivity,
				listingMessageId,
				messageStatus, 
				listingId,
				paymentStatus );
	}

	/**
	 * Inserts a set of reply messages related to a given listing.
	 * 
	 * @param doUnregisteredUserActivity
	 * @param doRegisteredUserActivity
	 * @param inResponseToTwitterId
	 * @param messageStatus
	 * @param listingId
	 * @param paymentStatus
	 */
	private void insertPreexistingProcessedReplies( 
			Boolean doUnregisteredUserActivity,
			Boolean doRegisteredUserActivity,
			String inResponseToTwitterId, 
			MessageStatus messageStatus, 
			Long listingId, 
			PaymentStatus paymentStatus ) {
		
		// UN-registered user activity
		if( doUnregisteredUserActivity ){
			
			// other user
			String otherUnregisteredUserTwitterId = ( String )this.otherUnregisteredUserAttributes.get( PROVIDER_USER_ID );
			this.insertPreexistingUnregisteredUserProcessedReply( otherUnregisteredUserTwitterId, inResponseToTwitterId, messageStatus );
			
			// test user
			String testUnretisteredUserTwitterId = ( String )this.testUnregisteredUserAttributes.get( PROVIDER_USER_ID );
			this.insertPreexistingUnregisteredUserProcessedReply( testUnretisteredUserTwitterId, inResponseToTwitterId, messageStatus );
		}
		
		// registered user activity
		if( doRegisteredUserActivity ) {
			
			// Get the identifiers
			String otherRegisteredUserTwitterId = ( String )this.otherRegisteredUserAttributes.get( PROVIDER_USER_ID );
			Long otherRegisteredUserId = ( Long )this.otherRegisteredUserAttributes.get( USER_ID );

			String testRegisteredUserTwitterId = ( String )this.testRegisteredUserAttributes.get( PROVIDER_USER_ID );
			Long testRegisteredUserId = ( Long )this.testRegisteredUserAttributes.get( USER_ID );
			
			if( paymentStatus == null ) {

				// Straight replies (no associated payment)
				this.insertPreexistingRegisteredUserProcessedReply( 
						otherRegisteredUserTwitterId, otherRegisteredUserId, inResponseToTwitterId, messageStatus );
				this.insertPreexistingRegisteredUserProcessedReply( 
						testRegisteredUserTwitterId, testRegisteredUserId, inResponseToTwitterId, messageStatus );
			} else {

				// Replies plus payment
				this.insertReplyWithPayment( 
						otherRegisteredUserTwitterId, 
						otherRegisteredUserId, 
						inResponseToTwitterId, 
						messageStatus, 
						paymentStatus, 
						"2014-02-01 00:00:00", 
						"4500-02-01 00:00:00", 
						1, // paymentQuantity
						listingId, 
						48L );
				
				this.insertReplyWithPayment( 
						testRegisteredUserTwitterId, 
						testRegisteredUserId, 
						inResponseToTwitterId, 
						messageStatus, 
						paymentStatus, 
						"2014-02-01 00:00:00", 
						"4500-02-01 00:00:00",
						1, // paymentQuantity
						listingId, 
						48L );
			}
			
		}
	}


	private Map<String, Object> insertPreexistingRegisteredUserProcessedReply( String providerUserId, Long userId, String inResponseToTwitterId, MessageStatus messageStatus ) {
		Boolean replyContainsKeyword = true;
		
		// if this message is "FAILED_DUPLICATE_PURCHASE", add another existing "PENDING_MEANS_OF_PAYMENT" reply for this user and listing
		if( messageStatus.equals( MessageStatus.FAILED_DUPLICATE_PURCHASE )) {
			this.insertReplyMessage( providerUserId, userId, inResponseToTwitterId, MessageStatus.PENDING_MEANS_OF_PAYMENT, "SOME_UNIT_TEST_BATCH_PROCESSOR", replyContainsKeyword );
		}
		
		return this.insertReplyMessage( providerUserId, userId, inResponseToTwitterId, messageStatus, "SOME_UNIT_TEST_BATCH_PROCESSOR", replyContainsKeyword );
	}


	private Map<String, Object> insertPreexistingUnregisteredUserProcessedReply( String providerUserId, String inResponseToTwitterId, MessageStatus messageStatus ) {
		Boolean replyContainsKeyword = true;
		
		// if this message is "FAILED_DUPLICATE_PURCHASE", add another existing "PENDING_MEANS_OF_PAYMENT" reply for this user and listing
		if( messageStatus.equals( MessageStatus.FAILED_DUPLICATE_PURCHASE )) {
			this.insertReplyMessage( providerUserId, null, inResponseToTwitterId, MessageStatus.PENDING_MEANS_OF_PAYMENT, "SOME_UNIT_TEST_BATCH_PROCESSOR", replyContainsKeyword );
		}
		
		return this.insertReplyMessage( providerUserId, null, inResponseToTwitterId, messageStatus, "SOME_UNIT_TEST_BATCH_PROCESSOR", replyContainsKeyword );
	}
	
	private Long insertInventory( final Long listingId ) {
		
		PreparedStatementCreator insertInventoryPsc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement( Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement( INSERT_INVENTORY_SQL, new String[] { "id" } );
				ps.setString( 1, "DEFAULT" );		// product_description
				ps.setInt( 2, 1 ); 					// quantity
				ps.setInt( 3, 0 ); 					// remainingQuantity
				ps.setLong( 4, listingId );			// listing id

				return ps;
			}
		};
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update( insertInventoryPsc, keyHolder );
		
		Long inventoryId = keyHolder.getKey().longValue();
		
		return inventoryId;
	}
		
}
