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
package com.projectlaver.batch.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import org.springframework.jdbc.core.RowMapper;

import com.projectlaver.util.GoodsType;
import com.projectlaver.util.ListingType;

public class MessageStateChangeCommunicationsCursorItemRowMapper  implements RowMapper<MessageStateChangeCommunicationsCursorItem>  {


	public static final String MSC_ID_COLUMN = "mscId";
	public static final String MSC_VERSION_COLUMN = "mscVersion";
	public static final String NEW_MSC_VERSION_COLUMN = "newMscVersion";
	public static final String MSC_STATUS_COLUMN = "mscStatus";
	public static final String MESSAGE_ID_COLUMN = "messageId";
	public static final String MESSAGE_TWITTER_ID_COLUMN = "messageTwitterId";
	public static final String PREVIOUS_MESSAGE_STATE_COLUMN = "previousMessageState";
	public static final String NEW_MESSAGE_STATE_COLUMN = "newMessageState";
	public static final String RECIPIENT_TYPE_COLUMN = "recipientType";
	public static final String PROVIDER_ID_COLUMN = "providerId";
	public static final String TO_PROVIDER_USER_ID_COLUMN = "toProviderUserId";
	public static final String TO_DISPLAY_NAME_COLUMN = "toDisplayName";
	public static final String FROM_ACCESS_TOKEN_COLUMN = "fromAccessToken";
	public static final String FROM_SECRET_COLUMN = "fromSecret";
	public static final String BUYER_USER_ID_COLUMN = "buyerUserId";
	public static final String SELLER_USER_ID_COLUMN = "sellerUserId";
	public static final String LISTING_ID_COLUMN = "listingId";
	public static final String PAYMENT_ID_COLUMN = "paymentId";
	public static final String TOTAL_PAYMENT_AMOUNT_COLUMN = "totalAmount";
	public static final String SELLER_AMOUNT_COLUMN = "sellerAmount";
	public static final String SELLER_ID_COLUMN = "sellerId";
	public static final String LISTING_TYPE_COLUMN = "listingType";
	public static final String GOODS_TYPE_COLUMN = "goodsType";
	public static final String LISTING_LOCALE_COLUMN = "listingLocale";
	public static final String HAS_DIGITAL_CONTENT_COLUMN = "hasDigitalContent";
	public static final String SELLER_MAX_TWEETS_PER_TEN_SEC_COLUMN = "sellerMaxTweetsPerTenSec";
	public static final String SELLER_MAX_TWEETS_PER_FIVE_MIN_COLUMN = "sellerMaxTweetsPerFiveMin";
	public static final String SELLER_MAX_TWEETS_PER_HR_COLUMN = "sellerMaxTweetsPerHr";
	public static final String SELLER_MAX_TWEETS_PER_TWENTY_FOUR_HR_COLUMN = "sellerMaxTweetsPerTwentyFourHr";

	@Override
	public MessageStateChangeCommunicationsCursorItem mapRow( ResultSet rs, int rowNum ) throws SQLException {
		MessageStateChangeCommunicationsCursorItem item = new MessageStateChangeCommunicationsCursorItem();

		item.setMessageStateChangeId( rs.getLong( MSC_ID_COLUMN ) );
		item.setMessageStateChangeVersion( rs.getInt( MSC_VERSION_COLUMN ) );
		item.setNewMessageStateChangeVersion( rs.getInt( NEW_MSC_VERSION_COLUMN ) );
		item.setMessageStateChangeStatus( rs.getString( MSC_STATUS_COLUMN ) );
		item.setMessageId( rs.getLong( MESSAGE_ID_COLUMN ) );
		item.setMessageTwitterId( rs.getString( MESSAGE_TWITTER_ID_COLUMN ));
		item.setPreviousMessageState( rs.getString( PREVIOUS_MESSAGE_STATE_COLUMN ) );
		item.setNewMessageState( rs.getString( NEW_MESSAGE_STATE_COLUMN ) );
		item.setRecipientType( rs.getString( RECIPIENT_TYPE_COLUMN ));
		item.setProviderId( rs.getString( PROVIDER_ID_COLUMN ) );
		item.setToProviderUserId( rs.getLong( TO_PROVIDER_USER_ID_COLUMN ) );
		item.setToDisplayName( rs.getString( TO_DISPLAY_NAME_COLUMN ) );
		item.setFromAccessToken( rs.getString( FROM_ACCESS_TOKEN_COLUMN ));
		item.setFromSecret( rs.getString( FROM_SECRET_COLUMN ));
		item.setBuyerUserId( rs.getString( BUYER_USER_ID_COLUMN ) );
		item.setSellerUserId( rs.getString( SELLER_USER_ID_COLUMN ) );
		item.setListingId( rs.getLong( LISTING_ID_COLUMN ) );
		item.setPaymentId( rs.getLong( PAYMENT_ID_COLUMN ) );
		item.setTotalAmount( rs.getBigDecimal( TOTAL_PAYMENT_AMOUNT_COLUMN ) );
		item.setSellerAmount( rs.getBigDecimal( SELLER_AMOUNT_COLUMN ) );
		item.setSellerId( rs.getLong( SELLER_ID_COLUMN ));
		item.setListingType( ListingType.valueOf( rs.getString( LISTING_TYPE_COLUMN ) ) );
		item.setGoodsType( GoodsType.valueOf( rs.getString( GOODS_TYPE_COLUMN )));
		
		// set the tweet rate limits for this seller
		item.setSellerMaxTweetsPerTenSec( rs.getInt( SELLER_MAX_TWEETS_PER_TEN_SEC_COLUMN ) );
		item.setSellerMaxTweetsPerFiveMin( rs.getInt( SELLER_MAX_TWEETS_PER_FIVE_MIN_COLUMN ) );
		item.setSellerMaxTweetsPerHr( rs.getInt( SELLER_MAX_TWEETS_PER_HR_COLUMN ) );
		item.setSellerMaxTweetsPerTwentyFourHr( rs.getInt( SELLER_MAX_TWEETS_PER_TWENTY_FOUR_HR_COLUMN ) );

		// Build a locale out of "en_US"
		String fullLocale = rs.getString( LISTING_LOCALE_COLUMN );
		String[] localeComponents = fullLocale.split( "_" );
		Locale listingLocale = new Locale( localeComponents[0], localeComponents[1] );
		item.setListingLocale( listingLocale );

		item.setHasDigitalContent( rs.getBoolean( HAS_DIGITAL_CONTENT_COLUMN ) );

		// default to false
		item.setDidCommunicationsComplete( false );

		return item;
	}
}
