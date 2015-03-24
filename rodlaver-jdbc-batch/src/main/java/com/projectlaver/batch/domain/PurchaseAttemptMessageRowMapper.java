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

import com.projectlaver.util.GoodsType;
import com.projectlaver.util.ListingType;
import com.projectlaver.util.PurchaseAttemptMessageCursorItem;

public class PurchaseAttemptMessageRowMapper implements RowMapper<PurchaseAttemptMessageCursorItem> {

	public static final String MESSAGE_ID_COLUMN = "message_id";
	public static final String MESSAGE_STATUS_COLUMN = "message_status";
	public static final String MESSAGE_VERSION_COLUMN = "message_version";
	
	public static final String BUYER_ID_COLUMN = "buyer_id";
	public static final String PROVIDER_ID_COLUMN = "provider_id";
	public static final String PROVIDER_USER_ID_COLUMN = "provider_user_id";
	public static final String PREAPPROVAL_KEY_COLUMN = "preapproval_key";
	public static final String ADDRESS_ID_COLUMN = "a_id";
	public static final String IS_ADDRESS_PRIMARY_COLUMN = "is_address_primary";

	public static final String SELLER_ID_COLUMN = "seller_id";
	public static final String SELLER_EMAIL_COLUMN = "seller_emailAddress";
	public static final String SELLER_USERNAME_COLUMN = "seller_username";
	public static final String SELLER_LOGO_IMAGE_URL_COLUMN = "seller_logo_image_url";
	public static final String SELLER_CAMPAIGN_IMAGE_URL_COLUMN = "seller_campaign_image_url";
	public static final String SELLER_MERCHANT_NAME = "seller_merchant_name";
	
	public static final String LISTING_ID_COLUMN = "listing_id";
	public static final String LISTING_TITLE_COLUMN = "listing_title";
	public static final String LISTING_LONG_DESCRIPTION_COLUMN = "listing_long_description";
	public static final String LISTING_TERMS_COLUMN = "listing_terms";
	public static final String LISTING_KEYWORD_COLUMN = "listing_keyword";
	public static final String LISTING_TYPE_COLUMN = "listing_type";
	public static final String GOODS_TYPE_COLUMN = "goods_type";
	public static final String LISTING_AMOUNT_COLUMN = "listing_amount";
	public static final String LISTING_SELLER_REVENUE_RATIO_COLUMN = "listing_seller_revenue_ratio";
	public static final String DO_USE_CHAINED_PAYMENT_COLUMN = "do_use_chained_payment";
	
	public static final String INVENTORY_ID_COLUMN = "inventory_id";
	public static final String INVENTORY_TOTAL_QUANTITY_COLUMN = "inventory_quantity";
	public static final String INVENTORY_REMAINING_QUANTITY_COLUMN = "inventory_remaining_quantity";
	public static final String INVENTORY_PRODUCT_CODE_COLUMN = "inventory_product_code";
	public static final String INVENTORY_PRODUCT_DESCRIPTION_COLUMN = "inventory_product_description";
	

	@Override
	public PurchaseAttemptMessageCursorItem mapRow(ResultSet rs, int rowNum) throws SQLException {
		PurchaseAttemptMessageCursorItem item = new PurchaseAttemptMessageCursorItem();

		// message attributes
		item.setMessageId( rs.getLong( MESSAGE_ID_COLUMN ) );
		item.setMessageStatus( rs.getString( MESSAGE_STATUS_COLUMN ) );
		item.setMessageVersion( rs.getInt( MESSAGE_VERSION_COLUMN ) );
		
		// buyer attributes
		item.setBuyerId( rs.getLong( BUYER_ID_COLUMN ) );
		item.setProviderId( rs.getString( PROVIDER_ID_COLUMN ) );
		item.setProviderUserId( rs.getString( PROVIDER_USER_ID_COLUMN ));
		item.setPreapprovalKey( rs.getString( PREAPPROVAL_KEY_COLUMN ) );
		item.setAddressId( rs.getLong( ADDRESS_ID_COLUMN ) );
		item.setIsAddressPrimary( rs.getBoolean( IS_ADDRESS_PRIMARY_COLUMN ));
		
		// seller attributes
		item.setSellerId( rs.getLong( SELLER_ID_COLUMN ) );
		item.setSellerEmail( rs.getString( SELLER_EMAIL_COLUMN ) );
		item.setSellerUsername( this.getScreenFormattedUsername( rs.getString( SELLER_USERNAME_COLUMN ) ) );
		item.setClientLogoUrl( rs.getString( SELLER_LOGO_IMAGE_URL_COLUMN ));
		item.setClientCampaignImageUrl( rs.getString( SELLER_CAMPAIGN_IMAGE_URL_COLUMN ));
		item.setMerchantName( rs.getString( SELLER_MERCHANT_NAME ));
		
		// listing attributes
		item.setListingId( rs.getLong( LISTING_ID_COLUMN ) );
		item.setListingType( ListingType.valueOf( rs.getString( LISTING_TYPE_COLUMN ) ) );
		item.setListingTitle( rs.getString( LISTING_TITLE_COLUMN ));
		item.setListingLongDescription( rs.getString( LISTING_LONG_DESCRIPTION_COLUMN ));
		item.setListingTerms( rs.getString( LISTING_TERMS_COLUMN ));
		item.setListingKeyword( rs.getString( LISTING_KEYWORD_COLUMN ));
		item.setGoodsType( GoodsType.valueOf( rs.getString( GOODS_TYPE_COLUMN )));
		item.setListingAmount( rs.getBigDecimal( LISTING_AMOUNT_COLUMN ) );
		item.setListingSellerRevenueRatio( rs.getBigDecimal( LISTING_SELLER_REVENUE_RATIO_COLUMN ) );
		item.setDoUseChainedPayment( rs.getBoolean( DO_USE_CHAINED_PAYMENT_COLUMN ));
		
		// inventory attributes
		item.setInventoryId( rs.getLong( INVENTORY_ID_COLUMN ));
		item.setInventoryTotalQuantity( rs.getInt( INVENTORY_TOTAL_QUANTITY_COLUMN ));
		item.setInventoryRemainingQuantity( rs.getInt( INVENTORY_REMAINING_QUANTITY_COLUMN ));
		item.setInventoryProductCode( rs.getString( INVENTORY_PRODUCT_CODE_COLUMN ));
		item.setInventoryProductDescription( rs.getString( INVENTORY_PRODUCT_DESCRIPTION_COLUMN ));

		return item;
	}

	String getScreenFormattedUsername( String rawUsername ) {
		String result = null;

		if( rawUsername != null ) {
			if( rawUsername.startsWith( "twitter/") ) {
				result = "@" + rawUsername.substring( 8 );
			} else if( rawUsername.startsWith( "facebook/") ) {
				result = rawUsername.substring( 9 );
			} else {
				throw new RuntimeException( "Unexpected username prefix: " + rawUsername );
			}
 		}

		return result;
	}
}
