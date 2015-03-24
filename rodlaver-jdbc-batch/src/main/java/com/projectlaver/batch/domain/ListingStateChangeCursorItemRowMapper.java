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

import com.projectlaver.util.ListingType;

public class ListingStateChangeCursorItemRowMapper implements RowMapper<ListingStateChangeCursorItem> {

	private static final String LSC_ID_COLUMN = "lscId";
	private static final String LSC_VERSION_COLUMN = "lscVersion";
	private static final String NEW_LSC_VERSION_COLUMN = "newLscVersion";
	private static final String LSC_STATUS_COLUMN = "lscStatus";
	private static final String SELLER_ID_COLUMN = "sellerId";
	private static final String LISTING_ID_COLUMN = "listingId";
	private static final String LISTING_TYPE_COLUMN = "listingType";
	private static final String ANNOUNCEMENT_PREAMBLE_COLUMN = "announcementPreamble";
	private static final String LISTING_KEYWORD_COLUMN = "listingKeyword";
	private static final String LISTING_AMOUNT_COLUMN = "listingAmount";
	private static final String LISTING_LOCALE_COLUMN = "listingLocale";
	private static final String LISTING_IMAGE_FILENAME_COLUMN = "listingImageFilename";
	private static final String FACEBOOK_PAGE_ID_COLUMN = "facebookPageId";
	private static final String FACEBOOK_ALBUM_ID_COLUMN = "facebookAlbumId";
	private static final String PROVIDER_ID_COLUMN = "providerId";
	private static final String PROVIDER_USER_ID_COLUMN = "providerUserId";
	private static final String ACCESS_TOKEN_COLUMN = "accessToken";
	private static final String DESTINATION_SECRET_COLUMN = "destinationSecret";
	private static final String DISPLAY_NAME_COLUMN = "displayName";

	@Override
	public ListingStateChangeCursorItem mapRow(ResultSet rs, int rowNum ) throws SQLException {
		ListingStateChangeCursorItem item = new ListingStateChangeCursorItem();
		
		item.setListingStateChangeId( rs.getLong( LSC_ID_COLUMN ));
		item.setListingStateChangeVersion( rs.getInt( LSC_VERSION_COLUMN ));
		item.setNewListingStateChangeVersion( rs.getInt( NEW_LSC_VERSION_COLUMN ));
		item.setListingStateChangeStatus( rs.getString( LSC_STATUS_COLUMN ));
		item.setSellerId( rs.getLong( SELLER_ID_COLUMN ));
		item.setListingId( rs.getLong( LISTING_ID_COLUMN ));
		item.setListingType( ListingType.valueOf( rs.getString( LISTING_TYPE_COLUMN )));
		item.setAnnouncementPreamble( rs.getString( ANNOUNCEMENT_PREAMBLE_COLUMN ));
		item.setListingKeyword( rs.getString( LISTING_KEYWORD_COLUMN ));
		item.setListingAmount( rs.getBigDecimal( LISTING_AMOUNT_COLUMN ));
		item.setLocale( new Locale( rs.getString( LISTING_LOCALE_COLUMN )));
		item.setImageFilename( rs.getString( LISTING_IMAGE_FILENAME_COLUMN ));
		item.setFacebookPageId( rs.getString( FACEBOOK_PAGE_ID_COLUMN ));
		item.setFacebookAlbumId( rs.getString( FACEBOOK_ALBUM_ID_COLUMN ));
		item.setProviderId( rs.getString( PROVIDER_ID_COLUMN ));
		item.setProviderUserId( rs.getString( PROVIDER_USER_ID_COLUMN ));
		item.setAccessToken( rs.getString( ACCESS_TOKEN_COLUMN ));
		item.setDestinationSecret( rs.getString( DESTINATION_SECRET_COLUMN ));
		item.setDisplayName( rs.getString( DISPLAY_NAME_COLUMN ));
		
		return item;
	}

}
