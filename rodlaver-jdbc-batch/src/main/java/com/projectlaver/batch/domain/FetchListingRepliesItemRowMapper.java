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

import org.springframework.jdbc.core.RowMapper;

public class FetchListingRepliesItemRowMapper implements RowMapper<FetchListingRepliesItem> {

	public static final String PROVIDER_ID_COLUMN = "provider_id";
	public static final String LISTING_MESSAGE_PROVIDER_ID_COLUMN = "listing_message_provider_id";
	public static final String LISTING_MESSAGE_CONTAINER_PROVIDER_ID_COLUMN = "listing_message_container_provider_id";
	public static final String SELLER_FACEBOOK_ID_COLUMN = "seller_facebook_id";
	public static final String SELLER_ACCESS_TOKEN_COLUMN = "seller_access_token";
	public static final String LISTING_KEYWORD_COLUMN = "listing_keyword";
	public static final String PAGING_STATUS_ID_COLUMN = "paging_status_id";
	public static final String MAX_FETCHED_COMMENT_TIME_COLUMN = "max_fetched_comment_time"; 
	public static final String PAGING_STATUS_VERSION_COLUMN = "paging_status_version";
	public static final String UPDATED_PAGING_STATUS_VERSION_COLUMN = "updated_paging_status_version";

	@Override
	public FetchListingRepliesItem mapRow( ResultSet rs, int rowNum ) throws SQLException {
		FetchListingRepliesItem item = new FetchListingRepliesItem();

		item.setProviderId( rs.getString( PROVIDER_ID_COLUMN ) );
		item.setListingMessageProviderId( rs.getString( LISTING_MESSAGE_PROVIDER_ID_COLUMN ) );
		item.setListingMessageContainerProviderId( rs.getString( LISTING_MESSAGE_CONTAINER_PROVIDER_ID_COLUMN ) );
		item.setSellerFacebookId( rs.getString( SELLER_FACEBOOK_ID_COLUMN ));
		item.setSellerAccessToken( rs.getString( SELLER_ACCESS_TOKEN_COLUMN ) );
		item.setListingKeyword( rs.getString( LISTING_KEYWORD_COLUMN ));
		item.setPagingStatusId( rs.getLong( PAGING_STATUS_ID_COLUMN ));
		item.setExistingMaxFetchedCommentTime( rs.getDate( MAX_FETCHED_COMMENT_TIME_COLUMN ));
		item.setPagingStatusVersion( rs.getInt( PAGING_STATUS_VERSION_COLUMN ));
		item.setUpdatedPagingStatusVersion( rs.getInt( UPDATED_PAGING_STATUS_VERSION_COLUMN ));

		return item;
	}
}
