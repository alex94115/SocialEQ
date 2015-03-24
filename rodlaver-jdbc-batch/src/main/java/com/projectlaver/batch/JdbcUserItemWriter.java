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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.database.JdbcParameterUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import com.projectlaver.util.PaymentItem;

/**
 *
 * Class used by the batch job to insert a row into the User table in the specific case that an unregistered
 * user has both entered and won a giveaway.
 *
 * The reason for this class is that unregistered users are able to enter (and win) giveaways. Since they
 * are unregistered, there is initially no row in the User table. However, when a giveaway is finalized,
 * a "NO PAYMENT" row is inserted into the payment table, which allows the payment history / payment detail /
 * download digital content functionality in the system to work in the same way as if a user had
 * made a purchase (changing this would have required major surgery to handle giveaways differently).
 *
 * But since we need a FK to the user table to insert rows into the Payment table, this class's responsibilty
 * is to insert a "surrogate" User row, which waits as a placeholder until the user actually logs in, at which
 * point the surrogate username is replaced by the real username but the User FK relationship stays in tact.
 *
 * @author alexduff
 *
 * @param <T>
 */
public class JdbcUserItemWriter extends RodLaverJdbcItemWriter<List<PaymentItem>> {

	private final Log logger = LogFactory.getLog(getClass());
	private String sqlInsertUser;

	public void setSqlInsertUser( String sqlInsertUser ) {
		this.sqlInsertUser = sqlInsertUser;
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull( super.getJdbcTemplate(), "A DataSource or a NamedParameterJdbcTemplate is required." );
		Assert.notNull( this.sqlInsertUser, "An SQL statement is required." );
		List<String> namedParameters = new ArrayList<String>();
		int parameterCount = JdbcParameterUtils.countParameterPlaceholders( this.sqlInsertUser, namedParameters);
		if (namedParameters.size() > 0) {
			if (parameterCount != namedParameters.size()) {
				throw new InvalidDataAccessApiUsageException("You can't use both named parameters and classic \"?\" placeholders: " + this.sqlInsertUser );
			}
		}
	}


	@Override
	public void write(List<? extends List<PaymentItem>> items) throws Exception {

		if (!items.isEmpty()) {

			if( logger.isDebugEnabled() ) {
				logger.debug("Executing insert user batch with " + items.size() + " items.");
			}

			final PaymentItem item = (PaymentItem)items.get(0);

			// test to see if the payer id is null, in other words this is an unregistered user who has won a giveaway
			// if the payer id is not null, don't insert anything.
			if( (item.getPayerId() == null) || !(item.getPayerId() > 0) ) {

				final KeyHolder keyHolder = new GeneratedKeyHolder();
				PreparedStatementCreator insertUserPSC = new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement( Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement( sqlInsertUser, new String[] { "id" });
						ps.setString( 1, item.getProviderId() ); // providerId
						ps.setString( 2, item.getProviderUserId() ); // providerUserId

						return ps;
					}
				};

				try {
					super.getJdbcTemplate().update( insertUserPSC, keyHolder );
					item.setPayerId( keyHolder.getKey().longValue() );
				} catch( DuplicateKeyException e ) {
					
					if( logger.isDebugEnabled() ) {
						logger.debug( String.format( "Caught a duplicate key exception, indicating that the user: [%s/%s] has won multiple giveaways without claiming one yet.", 
								item.getProviderId(), 
								item.getProviderUserId() ) );
					}
					
					// On the chance that the user has won multiple giveaways without claiming any, this will find the user.id
					Long payerId = super.getJdbcTemplate().queryForObject( "SELECT id FROM Users WHERE username='"+ item.getProviderId() + "/" + item.getProviderUserId() + "'", Long.class);
					item.setPayerId( payerId );
				}
			}
		}
	}

}
