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
package com.projectlaver.util;

import static org.easymock.EasyMock.anyInt;
import static org.easymock.EasyMock.anyLong;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.Test;

import com.projectlaver.integration.SocialProviders;

/**
 * Made this class so that the payment provider-specific things can go down to a subclass. It can't be
 * done in a super class because of static type inheritance and I don't know how to make EasyMock
 * work with the mocking behavior set in a support class (i.e., in a class that's an instance variable on this test).
 * 
 * The bad news is that I'm having to duplicate these child class methods because of the two branches of
 * tests: one for the web project and one for the batch project. Would be nice to fix that.
 * 
 * @author alexduff
 *
 */
public abstract class AttemptPaymentItemProcessorTest extends EasyMockSupport {

	@TestSubject
	private AttemptPaymentItemProcessor attemptPaymentItemProcessor = new AttemptPaymentItemProcessor();
	
	@Mock(type=MockType.STRICT, name="dataSource")
	private DataSource mockDataSource;
	
	@Test 
	public void testProcessNormalFlowLtdQty() throws Exception {
		
		// Set the expectations for the processor and backend. This test simulates backend success.
		this.setupAttemptPreapprovalPaymentNormalFlow( this.attemptPaymentItemProcessor );
		
		String listingStatus = "ACTIVE";
		Integer currentVersion = 0;
		Integer newVersion = 1;
		Integer remainingQuantity = 1;
		Integer newRemainingQuantity = 0;
		
		this.expectSelectListingStatus( listingStatus, currentVersion, newVersion, remainingQuantity, newRemainingQuantity );
		
		Integer previousPurchases = 0;
		this.expectCountDuplicatePurchases( previousPurchases );
		
		// replay mode
		replayAll();
		
		// Set this test up with a limited quantity available and 1 item remaining
		Integer listingQty = 1;
		Integer remainingListingQty = 1;
		
		// Use an amount of $10 and a seller share of 90%
		BigDecimal totalAmt = new BigDecimal( "10.0000" );
		BigDecimal sellerRevenueRatio = new BigDecimal( "0.9000" );
		
		PaymentItem paymentItem = this.attemptPaymentItemProcessor.process( this.createPurchaseAttemptMessageCursorItem( listingQty, remainingListingQty, totalAmt, sellerRevenueRatio ) );
		assertNotNull( paymentItem );
		assertEquals( PaymentStatus.COMPLETED, paymentItem.getNewEffectivePaymentStatus() );
		assertEquals( "COMPLETED", paymentItem.getNewMessageStatus() );
		
		// ensure that we decremeted the available quantity
		assertEquals( Integer.valueOf( 0 ), paymentItem.getUpdatedQuantityAvailable() );
		assertEquals( 0, paymentItem.getSellerAmount().compareTo( new BigDecimal( "9.0000" )));
		assertEquals( 0, paymentItem.getRodLaverAmount().compareTo( new BigDecimal( "1.0000" )));
		
		verifyAll();
	}
	
	@Test 
	public void testProcessNormalFlowUnlimitedQty() throws Exception {
		
		// Set the expectations for the processor and backend. This test simulates backend success.
		this.setupAttemptPreapprovalPaymentNormalFlow( this.attemptPaymentItemProcessor );
		
		String listingStatus = "ACTIVE";
		Integer currentVersion = 0;
		Integer newVersion = 1;
		Integer remainingQuantity = null;
		Integer newRemainingQuantity = null;
		
		this.expectSelectListingStatus( listingStatus, currentVersion, newVersion, remainingQuantity, newRemainingQuantity );
		
		Integer previousPurchases = 0;
		this.expectCountDuplicatePurchases( previousPurchases );
		
		// replay mode
		replayAll();
		
		// In the batch, null values are reported as 0
		Integer listingQty = 0;
		Integer remainingListingQty = 0;
		
		// Use an amount of $10 and a seller share of 50%
		BigDecimal totalAmt = new BigDecimal( "10.0000" );
		BigDecimal sellerRevenueRatio = new BigDecimal( "0.5000" );

		
		PaymentItem paymentItem = this.attemptPaymentItemProcessor.process( this.createPurchaseAttemptMessageCursorItem( listingQty, remainingListingQty, totalAmt, sellerRevenueRatio ) );
		assertNotNull( paymentItem );
		assertEquals( PaymentStatus.COMPLETED, paymentItem.getNewEffectivePaymentStatus() );
		assertEquals( "COMPLETED", paymentItem.getNewMessageStatus() );
		
		assertEquals( null, paymentItem.getUpdatedQuantityAvailable() );
		assertEquals( 0, paymentItem.getSellerAmount().compareTo( new BigDecimal( "5.0000" )));
		assertEquals( 0, paymentItem.getRodLaverAmount().compareTo( new BigDecimal( "5.0000" )));

		
		verifyAll();
	}
	
	/**
	 * Make sure that if the listing has become sold out since the reader cursor started
	 * we throw a ListingNotActiveException. 
	 */
	@Test 
	public void testSoldOutLtdQty() throws Exception {
		
		/**
		 * This test does not need a mock backend because it won't be called, since this
		 * test sets up a item that is sold out and cannot be purchased.
		 */
		
		// simulate that this item has become sold out since the attempt purchase driving query was created
		String listingStatus = "COMPLETED";
		Integer currentVersion = 0;
		Integer newVersion = 1;
		Integer remainingQuantity = 0;
		Integer newRemainingQuantity = -1;
		
		this.expectSelectListingStatus( listingStatus, currentVersion, newVersion, remainingQuantity, newRemainingQuantity );
		
		// replay mode
		replayAll();
		
		// These variables represent the "stale" listing attributes that got selected when the cursor ran
		// but before any other purchases for this item were processed
		Integer listingQty = 1;
		Integer remainingListingQty = 1;

		try {
			PaymentItem paymentItem = this.attemptPaymentItemProcessor.process( this.createPurchaseAttemptMessageCursorItem( listingQty, remainingListingQty, null, null ) );
			fail( "Should have thrown a ListingNotActiveException since the listingStatusAndRemainingQuantity variable is COMPLETED-0" );
		} catch( ListingNotActiveException e ) {
			// expected
		}
		
		verifyAll();
	}
	
	/**
	 * Make sure that if the user has already purchased this item that we get a DuplicatePurchaseAttemptException.
	 */
	@Test 
	public void testProcessAlreadyPurchased() throws Exception {
		
		// recording mode

		/**
		 * This test does not need a mock backend because it won't be called, since this
		 * test sets up a duplicate purchase, which is not allowed.
		 */
		
		String listingStatus = "ACTIVE";
		Integer currentVersion = 0;
		Integer newVersion = 1;
		Integer remainingQuantity = null;
		Integer newRemainingQuantity = null;
		
		this.expectSelectListingStatus( listingStatus, currentVersion, newVersion, remainingQuantity, newRemainingQuantity );
		
		Integer previousPurchases = 1;
		this.expectCountDuplicatePurchases( previousPurchases );
		
		// replay mode
		replayAll();
		
		// In the batch, null values are reported as 0
		Integer listingQty = 0;
		Integer remainingListingQty = 0;

		try {
			PaymentItem paymentItem = this.attemptPaymentItemProcessor.process( this.createPurchaseAttemptMessageCursorItem( listingQty, remainingListingQty, null, null ) );
			fail( "Should have thrown a DuplicatePurchaseAttemptExcetpion since the previousPurchases variable is 1." );
		} catch( DuplicatePurchaseAttemptException e ) {
			// expected
		}
		
		verifyAll();
	}
	
	/**
	 * Run a test with a limited quantity listing and simulate a PayPal error. When there is 
	 * an invalid ack code from PayPal (as distinct from an Exception from communications failure
	 * or whatever), the payment is considered "FAILED", and the message is considered "PAYMENT_FAILED".
	 * 
	 * Part of this test is to ensure that the available listing quantity is handled properly. Since
	 * the cursor picks up the quanities at the time the reader is run, the quantities themselves
	 * can be stale by the time a given payment attempt is started.
	 * 
	 * In this case, we set up the "cursor" quantities to be 10 total, 10 available, but then
	 * simulate that the 'SELECT quantityRemaining FROM listing...' statement returns only
	 * 5 quantity left at this instant. The test then verifies that, after the payment has failed,
	 * the updatedQuantityRemaining attribute is set to 5 and not 10. If this is mishandled,
	 * we could inadvertently oversell an inventory item.
	 * 
	 */
	@Test 
	public void testProcessLtdQtySimulatedBackendError() throws Exception {
		
		// Set the expectations for the processor and backend. This test simulates a backend error.
		this.setupAttemptPreapprovalPaymentBackendError( this.attemptPaymentItemProcessor );
		
		// Setup this test simulating that 5 items are left at the instant that this payment attempt happens
		String listingStatus = "ACTIVE";
		Integer currentVersion = 0;
		Integer newVersion = 1;
		Integer remainingQuantity = 5;
		Integer newRemainingQuantity = 4;
		
		this.expectSelectListingStatus( listingStatus, currentVersion, newVersion, remainingQuantity, newRemainingQuantity );
		
		Integer previousPurchases = 0;
		this.expectCountDuplicatePurchases( previousPurchases );
		
		// replay mode
		replayAll();
		
		// Set the test up with a limited quantity and 10 items remaining
		Integer listingQty = 10;
		Integer remainingListingQty = 10;
		
		// Use an amount of $10 and a seller share of 90%
		BigDecimal totalAmt = new BigDecimal( "10.0000" );
		BigDecimal sellerRevenueRatio = new BigDecimal( "0.9000" );
		
		PaymentItem paymentItem = this.attemptPaymentItemProcessor.process( this.createPurchaseAttemptMessageCursorItem( listingQty, remainingListingQty, totalAmt, sellerRevenueRatio ) );
		assertNotNull( paymentItem );
		assertEquals( PaymentStatus.FAILED, paymentItem.getNewEffectivePaymentStatus() );
		assertEquals( "PAYMENT_FAILED", paymentItem.getNewMessageStatus() );
		
		// the quantity remaining should still be 5 (from the "ACTIVE-5" query result, not the original cursor's 10 available value)
		assertEquals( Integer.valueOf( 5 ), paymentItem.getUpdatedQuantityAvailable() );
		
		verifyAll();
	}
	
	/**
	 * Support methods
	 */
	
	
	abstract void setupAttemptPreapprovalPaymentBackendError( AttemptPaymentItemProcessor attemptPaymentItemProcessor ) throws Exception;
	abstract void setupAttemptPreapprovalPaymentNormalFlow( AttemptPaymentItemProcessor attemptPaymentItemProcessor ) throws Exception;

	PurchaseAttemptMessageCursorItem createPurchaseAttemptMessageCursorItem( 
			Integer listingQty, 
			Integer remainingListingQty,
			BigDecimal listingAmount,
			BigDecimal sellerRevenueRatio ) {
		
		PurchaseAttemptMessageCursorItem item = new PurchaseAttemptMessageCursorItem();
		
		item.setBuyerId( 1L );
		item.setSellerId( 2L );

		item.setListingId( 3L );
		item.setListingAmount( null );
		item.setInventoryTotalQuantity( listingQty );
		item.setInventoryRemainingQuantity( remainingListingQty );
		item.setListingAmount( listingAmount );
		item.setListingSellerRevenueRatio( sellerRevenueRatio );
		item.setDoUseChainedPayment( true );
		item.setListingType( ListingType.SELLING );
		item.setGoodsType( GoodsType.DIGITAL );
		
		item.setIsAddressPrimary( null );
		item.setMessageId( 4L );
		item.setMessageStatus( "PENDING_LISTING_EXPIRY" );
		item.setMessageVersion( 0 );
		item.setInventoryVersion( 0 );
		item.setProviderId( SocialProviders.TWITTER );
		item.setProviderUserId( "1234612354" );
		

		
		return item;
	}
	
	void expectSelectListingStatus( 
			String status, 
			Integer currentVersion,
			Integer newVersion,
			Integer remainingQuantity,
			Integer newRemainingQuantity ) throws SQLException {
		
		PreparedStatement mockPreparedStatement = createMock( PreparedStatement.class );
		mockPreparedStatement.setLong( anyInt(), anyLong() );
		
		ResultSet mockResultSet = createMock( ResultSet.class );
		expect( mockPreparedStatement.executeQuery() )
			.andReturn( mockResultSet );
		
		expect( mockResultSet.next() )
			.andReturn( true );
		
		// get int "currentVersion"
		expect( mockResultSet.getInt( anyString() ))
			.andReturn( currentVersion );

		// get int "newVersion"
		expect( mockResultSet.getInt( anyString() ))
			.andReturn( newVersion );
		
		// get Object "remainingQuantity"
		expect( mockResultSet.getObject( anyString() ))
			.andReturn( remainingQuantity );
		
		// get Object "newRemainingQuantity"
		expect( mockResultSet.getObject( anyString() ))
			.andReturn( newRemainingQuantity );

		Connection mockConnection = createMock( Connection.class );
		expect( mockConnection.prepareStatement( anyObject( String.class )))
			.andReturn( mockPreparedStatement );
		
		expect( this.mockDataSource.getConnection() )
			.andReturn( mockConnection );
	}
	
void expectCountDuplicatePurchases( Integer previousPurchaseCount ) throws SQLException {
		
		PreparedStatement mockPreparedStatement = createMock( PreparedStatement.class );
		mockPreparedStatement.setObject( anyInt(), anyInt(), anyInt() );
		mockPreparedStatement.setObject( anyInt(), anyInt(), anyInt() );
		
		ResultSet mockResultSet = createMock( ResultSet.class );
		expect( mockPreparedStatement.executeQuery() )
			.andReturn( mockResultSet );
		
		expect( mockResultSet.next() )
			.andReturn( true );
		
		expect( mockResultSet.getInt( anyInt() ))
			.andReturn( previousPurchaseCount );
		
		expect( mockResultSet.wasNull() )
			.andReturn( false );

		expect( mockResultSet.next() )
			.andReturn( false );
		
		ResultSetMetaData mockResultSetMetaData = createMock( ResultSetMetaData.class );
		expect( mockResultSet.getMetaData() )
			.andReturn( mockResultSetMetaData );
		
		expect( mockResultSetMetaData.getColumnCount() )
			.andReturn( 1 );
		
		Connection mockConnection = createMock( Connection.class );
		expect( mockConnection.prepareStatement( anyObject( String.class )))
			.andReturn( mockPreparedStatement );
		
		expect( this.mockDataSource.getConnection() )
			.andReturn( mockConnection );
	}

}
