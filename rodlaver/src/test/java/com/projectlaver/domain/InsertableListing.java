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

import java.util.HashSet;
import java.util.Set;

import org.springframework.test.util.ReflectionTestUtils;

import com.projectlaver.util.ListingStatus;
import com.projectlaver.util.ListingType;
import com.projectlaver.util.MessageStatus;

public class InsertableListing extends Listing implements AsSqlInsert {

	public InsertableListing( Long listingId , User seller, ListingType type, ListingStatus status, String keyword, Long messageId, String twitterId ) {
		ReflectionTestUtils.setField( this, "id", listingId );
		super.setSeller( seller );
		super.setType( type );
		super.setStatus( status );
		super.setKeyword( keyword );

		Set<Message> messages = new HashSet<Message>();
		MessageStatus messageStatus = MessageStatus.ACTIVE;
		if( status.equals( ListingStatus.CANCELED )) {
			messageStatus = MessageStatus.CANCELED;
		} else if( status.equals( ListingStatus.COMPLETED )) {
			messageStatus = MessageStatus.COMPLETED;
		}

		messages.add( new InsertableMessage( messageId, twitterId, seller, this, messageStatus ));
		super.setMessages(messages);
	}


	@Override
	public String asSqlInsert() {
		Object quantity = super.getType().equals( ListingType.SELLING ) ? 1 : "NULL";

		Object remainingQuantity = "NULL";
		if( super.getType().equals( ListingType.SELLING ) && super.getStatus().equals( ListingStatus.COMPLETED )) {
			remainingQuantity = 0;
		}

		StringBuilder result = new StringBuilder();
		result.append( TestDataUtils.buildInsertFromTokens(
				"INSERT INTO `listing` VALUES (",
				super.getId(),
				1.00,
				"NULL",
				"NULL",
				"NULL",
				"NULL",
				quantity,
				"Listing title",
				super.getType().toString(),
				0,
				super.getSeller().getId(),
				"current_timestamp",
				"current_timestamp",
				super.getStatus().toString(),
				"NULL",
				"NULL",
				"NULL",
				remainingQuantity,
				"NULL",
				super.getKeyword() ) );
		result.append( "\n" );
		
		// Updated schema
		//  `id` bigint(20) NOT NULL AUTO_INCREMENT,
		//  `amount` decimal(15,5) DEFAULT NULL,
		//  `contentFilename` varchar(255) DEFAULT NULL,
		//  `digitalContent` longblob,
		//  `image` longblob,
		//  `imageFilename` varchar(255) DEFAULT NULL,
		//  `quantity` int(11) DEFAULT NULL,
		//  `title` varchar(255) DEFAULT NULL,
		//  `type` varchar(255) DEFAULT NULL,
		//  `version` int(11) NOT NULL,
		//  `seller_id` bigint(20) DEFAULT NULL,
		//  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
		//  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
		//  `status` varchar(255) DEFAULT NULL,
		//  `imageHeight` int(11) DEFAULT NULL,
		//  `imageWidth` int(11) DEFAULT NULL,
		//  `currencyCode` varchar(255) DEFAULT NULL,
		//  `remainingQuantity` int(11) DEFAULT NULL,
		//  `locale` varchar(10) DEFAULT 'en_US',
		//  `keyword` varchar(255) DEFAULT NULL,
		//  `announcementPreamble` varchar(255) DEFAULT NULL,
		//  `backgroundImageUrl` varchar(512) DEFAULT NULL,
		//  `digitalContentOriginalFilename` varchar(512) DEFAULT NULL,
		//  `digitalContentType` varchar(255) DEFAULT NULL,
		//  `expires` datetime NOT NULL,
		//  `giveawaySeed` int(11) DEFAULT NULL,
		//  `longDescription` varchar(255) DEFAULT NULL,
		//  `batch_processor` varchar(255) DEFAULT NULL,
		//  `subType` varchar(255) DEFAULT NULL,
		//  `doPostToFacebook` bit(1) DEFAULT NULL,
		//  `doPostToTwitter` bit(1) DEFAULT NULL,

		InsertableMessage message = (InsertableMessage) super.getMessages().iterator().next();
		result.append( message.asSqlInsert() );

		return result.toString();

	}

}
