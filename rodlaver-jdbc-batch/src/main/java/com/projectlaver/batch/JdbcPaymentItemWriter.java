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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.database.JdbcParameterUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import com.projectlaver.util.PaymentItem;
import com.projectlaver.util.VoucherDTO;


public class JdbcPaymentItemWriter extends RodLaverJdbcItemWriter<List<PaymentItem>> {

	/**
	 * Instance variables
	 */

	private final Log logger = LogFactory.getLog(getClass());
	
	private String sqlInsertPayment;
	private String sqlInsertEffectivePaymentStatus;
	private String sqlInsertVoucher;
	private String sqlInsertEffectiveVoucherStatus;

	/**
	 * Static variables
	 */


	/**
	 * Public methods
	 */


	public void setSqlInsertPayment(String sqlInsertPayment) {
		this.sqlInsertPayment = sqlInsertPayment;
	}

	public void setSqlInsertEffectivePaymentStatus( String sqlInsertEffectivePaymentStatus ) {
		this.sqlInsertEffectivePaymentStatus = sqlInsertEffectivePaymentStatus;
	}
	
	public void setSqlInsertVoucher( String sqlInsertVoucher ) {
		this.sqlInsertVoucher = sqlInsertVoucher; 
	}
	
	public void setSqlInsertEffectiveVoucherStatus( String sqlInsertEffectiveVoucherStatus ) {
		this.sqlInsertEffectiveVoucherStatus = sqlInsertEffectiveVoucherStatus;
	}


	/**
	 * Public setter for the {@link NamedParameterJdbcOperations}.
	 * @param namedParameterJdbcTemplate the {@link NamedParameterJdbcOperations} to set
	 */

	@Override
	public void afterPropertiesSet() {
		Assert.notNull( super.getJdbcTemplate(), "A DataSource or a NamedParameterJdbcTemplate is required." );
		Assert.notNull( this.sqlInsertPayment, "An SQL statement is required." );
		List<String> namedParameters = new ArrayList<String>();
		int parameterCount = JdbcParameterUtils.countParameterPlaceholders( this.sqlInsertPayment, namedParameters );
		if( namedParameters.size() > 0 ) {
			if( parameterCount != namedParameters.size() ) {
				throw new InvalidDataAccessApiUsageException( "You can't use both named parameters and classic \"?\" placeholders: " + this.sqlInsertPayment );
			}
		}

	}


	/**
	 * This method will receive a List of one PaymentItem, then use a JDBC Template to insert it,
	 * get the generated key, and insert the effective payment status row. For vouchers, we additionally
	 * insert the voucher and effective voucher status rows.
	 * 
	 *
	 * IMPORTANT NOTE: the commit interval of the step that calls this writer must be set to 1,
	 * otherwise the List of payment items will be > 1 and the logic in this method is wrong.
	 */
	@Override
	public void write(List<? extends List<PaymentItem>> items) throws Exception {

		if (!items.isEmpty()) {

			if (logger.isDebugEnabled()) {
				logger.debug("Executing insert payment batch with " + items.size() + " items.");
			}

			int[] updateCounts = null;

			final PaymentItem item = (PaymentItem)items.get(0);

			// test to see if the payment completed (i.e., the Pay Key is not null)
			// otherwise just skip inserting anything into payment/effective_payment_status
			if( item.getPayKey() != null ) {
				
				/**
				 * 1. Payments table.
				 * 
				 * Prepare to insert a payment row & get its primary key back to use as a FK.
				 */

				final KeyHolder paymentIdKeyHolder = new GeneratedKeyHolder();
				PreparedStatementCreator insertPaymentPSC = new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement( Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement( sqlInsertPayment, new String[] { "id" });
						ps.setString( 1, item.getPayKey() ); // payKey
						ps.setString( 2, item.getCorrelationId() ); // correlationId
						ps.setLong( 3, item.getPayerId() ); // payerId
						ps.setLong( 4, item.getPayeeId() ); // payeeId
						ps.setLong( 5, item.getListingId() ); // listingId
						ps.setLong( 6, item.getInventoryId() ); // inventoryId
						ps.setLong( 7, item.getMessageId() ); // messageId
						ps.setBigDecimal( 8, item.getAmount() ); // amount
						ps.setBigDecimal( 9, item.getRodLaverAmount() ); // rodLaverAmount
						ps.setBigDecimal( 10, item.getSellerAmount() ); // sellerAmount
						ps.setString( 11, item.getCurrencyCode() ); // currencyCode

						// May be null
						if( item.getShippingAddressId() != null && item.getShippingAddressId() != 0 ) {
							ps.setLong( 12, item.getShippingAddressId() ); // shippingAddressId
						} else {
							ps.setNull( 12, Types.INTEGER );
						}
						
						return ps;
					}
				};

				// insert the payment row
				int i = super.getJdbcTemplate().update( insertPaymentPSC, paymentIdKeyHolder );

				
				/**
				 *  2. EffectivePaymentStatuses table. 
				 *  
				 *  Using the payment id, prepare to insert the effective payment status row
				 */
				
				if( paymentIdKeyHolder.getKey() != null ) {

					PreparedStatementCreator insertEffectivePaymentStatusPSC = new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement( Connection connection) throws SQLException {
							PreparedStatement ps = connection.prepareStatement( sqlInsertEffectivePaymentStatus );
							ps.setLong(1, paymentIdKeyHolder.getKey().longValue() ); // paymentId
							ps.setDate( 2, END_OF_TIME_DATE ); // end
							ps.setString( 3, item.getNewEffectivePaymentStatus().toString() ); // status

							return ps;
						}
					};

					// insert the effective payment status
					int j = this.getJdbcTemplate().update( insertEffectivePaymentStatusPSC );
					updateCounts = new int[] {i,j};
					
					
					/**
					 *  3. Vouchers table. 
					 *  
					 *  Using the payment id, prepare to insert the Vouchers row
					 */
					
					if( j > 0 && item.getVouchers() != null ) {
						
						// Assume there is only one voucher in a batch payment
						final VoucherDTO voucher = item.getVouchers().get( 0 );
						
						// Declare a keyholder so we can use the Voucher Id as an FK to its Effective Status
						final KeyHolder voucherIdKeyHolder = new GeneratedKeyHolder();
						
						PreparedStatementCreator insertVoucherPSC = new PreparedStatementCreator() {

							@Override
							public PreparedStatement createPreparedStatement( Connection connection ) throws SQLException {
								PreparedStatement ps = connection.prepareStatement( sqlInsertVoucher, new String[] { "id" } );
								ps.setString( 1, voucher.getSerialNumber() ); // serial number
								ps.setString( 2, voucher.getSalt() ); // salt
								ps.setString( 3, voucher.getFilename() ); // filename
								ps.setLong( 4, paymentIdKeyHolder.getKey().longValue() ); // payment_id
								
								return ps;
							}
						};
						
						// insert the voucher row
						int k = super.getJdbcTemplate().update( insertVoucherPSC, voucherIdKeyHolder );
						
						/**
						 *  4. EffectiveVoucherStatuses table. 
						 *  
						 *  Insert the unredeemed status
						 */

						PreparedStatementCreator insertEffectiveVoucherStatusPSC = new PreparedStatementCreator() {
							@Override
							public PreparedStatement createPreparedStatement( Connection connection) throws SQLException {
								PreparedStatement ps = connection.prepareStatement( sqlInsertEffectiveVoucherStatus );
								ps.setLong(1, voucherIdKeyHolder.getKey().longValue() ); // voucher_id
								ps.setDate( 2, END_OF_TIME_DATE ); // end (start is implicitly NOW() as defined in the insert sql) 
								ps.setString( 3, voucher.getStatus().toString() ); // status

								return ps;
							}
						};

						// insert the effective payment status
						int l = this.getJdbcTemplate().update( insertEffectiveVoucherStatusPSC );
						updateCounts = new int[] {i,j,k,l};
					}

					
				} else {
					updateCounts = new int[] {0};
				}
				
				if( super.getAssertUpdates() ) {
					for (int z = 0; z < updateCounts.length; z++) {
						int value = updateCounts[z];
						if (value == 0) {
							throw new EmptyResultDataAccessException("Item " + z + " of " + updateCounts.length + " did not update any rows: [" + items.get(z) + "]", 1);
						}
					}
				}
			}
		}
	}

}
