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
package com.projectlaver.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projectlaver.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
	
	String FIND_BY_PAYMENT_ID = " SELECT m "
							  + " FROM Messages m "
							  + "   INNER JOIN m.payments p "
							  + " WHERE p.id = (:paymentId) ";
	
	String FIND_LISTING_MESSAGES_BY_LISTING_ID =  " SELECT m "
												+ " FROM Messages m "
												+ "  INNER JOIN m.listing l "
												+ " WHERE l.id = (:listingId) "
												+ "   AND m.inResponseToTwitterId is null ";
	
	String FIND_TWITTER_LISTING_MESSAGES_BY_LISTER_AND_HASHTAG = 
												  " SELECT m "
												+ " FROM Messages m "
												+ "  INNER JOIN m.listing l "
												+ " WHERE m.providerId = 'twitter' "
												+ "   AND m.providerUserId = (:providerUserId) "
												+ "   AND l.keyword = (:hashtag) "
												+ " ORDER BY l.created desc ";
	
	String FIND_REPLY_MESSAGES_BY_LISTING_ID = 
		 	  " SELECT m "
			+ " FROM Messages m "
		 	+ " WHERE m.inResponseToTwitterId IN " 
		 	+ " ( SELECT lm.twitterId "
		 	+ "    FROM Listings l "
		 	+ "      INNER JOIN l.messages lm "
		 	+ "    WHERE l.id = (:listingId) "
		 	+ " ) "; 
	
	String FIND_PENDING_MEANS_OF_PAYMENT_MESSAGE_BY_LISTING_AND_USER = 
			  " SELECT m "
			+ " FROM Messages m "
			+ "   INNER JOIN m.user u "
		 	+ " WHERE u.id = (:userId) "
		 	+ "   AND ( m.status = 'PENDING_MEANS_OF_PAYMENT' OR m.status = 'PAYMENT_FAILED' ) "
		 	+ "   AND m.inResponseToTwitterId IN " 
		 	+ "   ( SELECT lm.twitterId "
		 	+ "     FROM Listings l "
		 	+ "       INNER JOIN l.messages lm "
		 	+ "     WHERE l.id = (:listingId) "
		 	+ "   ) "; 
	
	@Query(FIND_BY_PAYMENT_ID)
	Message findByPaymentId( @Param("paymentId") Long paymentId );
	
	@Query(FIND_LISTING_MESSAGES_BY_LISTING_ID)
	Set<Message> findListingMessagesByListingId( @Param("listingId") Long listingId );
	
	@Query(FIND_TWITTER_LISTING_MESSAGES_BY_LISTER_AND_HASHTAG)
	Set<Message> findUsersTwitterListingMessages( @Param("providerUserId") String providerUserId, @Param("hashtag") String hashtag );
	
	@Query(FIND_REPLY_MESSAGES_BY_LISTING_ID)
	Page<Message> findReplyActivityByListingId( @Param("listingId") Long listingId, Pageable p );
	
	@Query(FIND_PENDING_MEANS_OF_PAYMENT_MESSAGE_BY_LISTING_AND_USER)
	Message findPendingMeansOfPaymentMessage( @Param("listingId") Long listingId, @Param("userId")Long userId );
	
}
