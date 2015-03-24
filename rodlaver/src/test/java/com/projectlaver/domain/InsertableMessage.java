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
package com.projectlaver.domain;

import org.springframework.test.util.ReflectionTestUtils;

import com.projectlaver.integration.SocialProviders;
import com.projectlaver.util.MessageStatus;

public class InsertableMessage extends Message implements AsSqlInsert {

	// Creates a message that announces a listing
	public InsertableMessage( Long id, String twitterId, User user, Listing listing, MessageStatus status ) {
		ReflectionTestUtils.setField( this, "id",  id );
		super.setTwitterId( twitterId );
		super.setUser( user );
		super.setListing( listing );
		super.setText( "Listing." );
		super.setStatus( status );
	}

	// Creates a message that is in reply to a listing
	public InsertableMessage( Long id, String twitterId, String inResponseToTwitterId, User user, MessageStatus status, String batchProcessor, Boolean containsKeyword ) {
		ReflectionTestUtils.setField( this, "id",  id );
		super.setTwitterId( twitterId );
		super.setInResponseToTwitterId( inResponseToTwitterId );
		super.setUser( user );
		super.setText( "Reply to listing." );
		super.setStatus( status );
		super.setBatchProcessor( batchProcessor );
		super.setContainsKeyword( containsKeyword );
	}

	@Override
	public String asSqlInsert() {
		InsertableUser user = (InsertableUser)super.getUser();
		Long providerUserId = user.getRandomTwitterUser().getProviderUserId();

		return TestDataUtils.buildInsertFromTokens(
				"INSERT INTO `Messages` VALUES (",
				     super.getId(),
				     super.getText(),
				     super.getTwitterId(),
				     ( super.getInResponseToTwitterId() != null ? super.getInResponseToTwitterId() : "NULL" ),
				     ( ( (super.getUser() != null) && (super.getUser().getId() != null) ) ? super.getUser().getId() : "NULL" ),
				     "current_timestamp",
				     "current_timestamp",
				     0,
				     ( super.getListing() != null ? super.getListing().getId() : "NULL" ),
				     super.getStatus().toString(),
				     SocialProviders.TWITTER,
				     providerUserId,
				     ( super.getBatchProcessor() != null ? super.getBatchProcessor() : "NULL" ),
				     ( super.getContainsKeyword() != null ? super.getContainsKeyword() : "NULL" ) );
		
				// Updated schema
				//  `id` bigint(20) NOT NULL AUTO_INCREMENT,
				//  `text` varchar(255) DEFAULT NULL,
				//  `twitterId` varchar(255) DEFAULT NULL,
				//  `inResponseToTwitterId` varchar(255) DEFAULT NULL,
				//  `user_id` bigint(20) DEFAULT NULL,
				//  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
				//  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
				//  `version` int(11) NOT NULL,
				//  `listing_id` bigint(20) DEFAULT NULL,
				//  `status` varchar(255) DEFAULT NULL,
				//  `providerId` varchar(255) DEFAULT NULL,
				//  `providerUserId` varchar(255) DEFAULT NULL,
				//  `batch_processor` varchar(255) DEFAULT NULL,
				//  `containsKeyword` tinyint(1) DEFAULT NULL,
				//  `embeded_provider_content` varchar(512) DEFAULT NULL,
				//  `isNaturalReply` tinyint(1) DEFAULT NULL,
				//  `isRetweet` tinyint(1) DEFAULT NULL,
	}

}
