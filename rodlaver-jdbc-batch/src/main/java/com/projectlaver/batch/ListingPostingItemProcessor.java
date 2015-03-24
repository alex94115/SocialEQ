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

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.projectlaver.batch.domain.ListingStateChangeCursorItem;
import com.projectlaver.util.ListingType;

public abstract class ListingPostingItemProcessor 
	implements ItemProcessor<ListingStateChangeCursorItem, ListingStateChangeCursorItem> {

	private final Log logger = LogFactory.getLog(getClass());

	@Value("${communications.baseListingDetailUrl}")
	private String baseListingDetailUrl;

	@Autowired
    private MessageSource messageSource;

	@Autowired
	private DataSource datasource;

	public static String INSERT_MESSAGE_SQL =
			  " INSERT INTO Messages "
			+ " SET version = ?, "
			+ "   providerId = ?, "
			+ "   providerUserId = ?, "
			+ "   status = ?, "
			+ "   text = ?, "
			+ "   twitterId = ?, "
			+ "   listing_id = ?, "
			+ "   user_id = ?, "
			+ "   embeded_provider_content = ? ";

	protected Long insertListingMessage( final ListingStateChangeCursorItem item, 
										 final String text, 
										 final String providerContentId,
										 final String embededProviderContent ) {


		final KeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreator insertPaymentPSC = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement( Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement( INSERT_MESSAGE_SQL, new String[] { "id" });
				ps.setInt( 1, 0 ); 							 // version
				ps.setString( 2,  item.getProviderId() ); 	 // providerId
				ps.setString( 3, item.getProviderUserId() ); // providerUserId
				ps.setString( 4, "ACTIVE" ); 				 // status
				ps.setString( 5, text ); 					 // text
				ps.setString( 6, providerContentId ); 		 // twitterId
				ps.setLong( 7, item.getListingId() ); 		 // listingId
				ps.setLong( 8, item.getSellerId() ); 		 // user_id
				ps.setString( 9, embededProviderContent ); // embeded_provider_content

				return ps;
			}
		};

		JdbcTemplate jdbcTemplate = new JdbcTemplate( this.datasource );
		jdbcTemplate.update( insertPaymentPSC, keyHolder );

		Long listingMessageId = null;
		if( keyHolder != null ) {
			listingMessageId = keyHolder.getKey().longValue();
		}

		return listingMessageId;

	}

	
	protected String formatTwitterListingMessageContent( ListingType listingType, String displayName, String keyword, BigDecimal price, String message, Long listingId, Locale locale ) {

		String messageKey = this.getListingAnnouncementTweetMessageKey( listingType );

		String formattedPrice = "";
		if( price != null ) {
			formattedPrice = String.format( "$%.2f", price );
		}

		Object[] messageParameters = null;
		if( listingType.equals( ListingType.SELLING )) {
			messageParameters = new Object[] { message, displayName, keyword, formattedPrice, this.baseListingDetailUrl + listingId };
		} else {
			messageParameters = new Object[] { message, displayName, keyword, this.baseListingDetailUrl + listingId };
		}

		return this.messageSource.getMessage( messageKey, messageParameters, locale ).trim();
	}

	protected String formatFacebookListingMessageContent( ListingType listingType, String keyword, BigDecimal price, String message, Long listingId, Locale locale ) {

		String messageKey = this.getListingAnnouncementTweetMessageKey( listingType );

		String formattedPrice = "";
		if( price != null ) {
			formattedPrice = String.format( "$%.2f", price );
		}

		Object[] messageParameters = null;
		if( listingType.equals( ListingType.SELLING )) {
			messageParameters = new Object[] { message, keyword, formattedPrice, this.baseListingDetailUrl + listingId };
		} else {
			messageParameters = new Object[] { message, keyword, this.baseListingDetailUrl + listingId };
		}

		return this.messageSource.getMessage( messageKey, messageParameters, locale ).trim();
	}

	protected DataSource getDataSource() {
		return this.datasource;
	}

	protected String getListingAnnouncementTweetMessageKey( ListingType listingType) {
		String messageKey;

		// Sale type listing
		if( listingType.equals( ListingType.SELLING ) ) {
			messageKey = this.getSaleMessageKey();
			
		// Donation type listing
		} else if( listingType.equals( ListingType.DONATION ) ) {
			messageKey = this.getDonationMessageKey();
		
		// Giveaway type listing
		} else {
			messageKey = this.getCampaignMessageKey();
		}
		
		return messageKey;
	}
	
	protected abstract String getSaleMessageKey();
	protected abstract String getDonationMessageKey();
	protected abstract String getCampaignMessageKey();
}
