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

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projectlaver.domain.EffectivePaymentStatus;
import com.projectlaver.domain.Payment;
import com.projectlaver.util.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	String SELECT_BY_LISTING_ID_AND_BUYER_ID =
		  " SELECT p "
		+ " FROM Payments p "
		+ "   INNER JOIN p.effectivePaymentStatus e "
		+ "   INNER JOIN p.listing l "
		+ "   INNER JOIN p.payer u "
		+ " WHERE u.id = (:userId) "
		+ "   AND l.id = (:listingId) "
		+ "   AND e.status IN (:status) "
		+ "   AND NOW() BETWEEN e.start AND e.end ";
	
	String SELECT_BY_REPLY_ID_IN_STATUS =
			  " SELECT p "
			+ " FROM Payments p "
			+ "   INNER JOIN p.effectivePaymentStatus e "
			+ "   INNER JOIN p.message m "
			+ "   INNER JOIN FETCH p.payer pr "
			+ "   INNER JOIN FETCH p.payee pe "
			+ " WHERE m.id = (:replyId) "
			+ "   AND e.status IN (:status) "
			+ "   AND NOW() BETWEEN e.start AND e.end ";

	String SELECT_ALL_CURRENT_PAYMENTS_BY_USER_ID =
			"   SELECT p "
			+ " FROM Payments p "
			+ "   INNER JOIN p.payer u "
			+ "   INNER JOIN p.payee u2 "
			+ "   INNER JOIN p.effectivePaymentStatus e "
			+ " WHERE ( u.id = (:userId) OR u2.id = (:userId) ) "
			+ "   AND NOW() BETWEEN e.start AND e.end "
			+ " ORDER BY p.id DESC ";

	String COUNT_VALID_ASSOCIATED_PAYMENTS =
			  " SELECT count( m )"
			+ " FROM Messages m "
			+ "   INNER JOIN m.payments p "
			+ "   INNER JOIN p.effectivePaymentStatus e "
			+ " WHERE m.inResponseToTwitterId = (:twitterId) "
			+ "   AND ( e.status IS NULL OR ( e.status IN (:status) AND NOW() BETWEEN e.start AND e.end ) ) ";

	String FIND_BY_ID_WITH_EAGER_FETCHING =
			"   SELECT p "
			+ " FROM Payments p "
			+ "   INNER JOIN FETCH p.payer u "
			+ "   INNER JOIN FETCH p.payee u2 "
			+ "   INNER JOIN FETCH p.listing l "
			+ "   INNER JOIN FETCH p.message m "
			+ "   LEFT OUTER JOIN FETCH u.addresses "
			+ " WHERE p.id = (:paymentId) "
			+ "   AND ( u.id = (:userId) OR u2.id = (:userId) ) ";

	String FIND_CURRENT_EFFECTIVE_PAYMENT_STATUS_BY_PAYMENT_ID =
			"   SELECT e "
			+ " FROM EffectivePaymentStatuses e "
			+ "   INNER JOIN e.payment p "
			+ " WHERE p.id = (:paymentId) "
			+ "   AND NOW() BETWEEN e.start AND e.end ";
	
	// used to make sure a request to download digital content is coming from an actual buyer
	String SELECT_BY_PAYER_ID_AND_STATUS_AND_CONTENT_FILENAME =
			  " SELECT p "
			+ " FROM Payments p "
			+ "   INNER JOIN p.effectivePaymentStatus e "
			+ "   INNER JOIN FETCH p.listing l "
			+ "   INNER JOIN l.contentFiles cf "
			+ "   INNER JOIN p.payer u "
			+ " WHERE u.id = (:payerId) "
			+ "   AND e.status IN (:status) "
			+ "   AND NOW() BETWEEN e.start AND e.end "
			+ "   AND cf.contentFilename= (:contentFilename) ";
	
	@Query(SELECT_BY_LISTING_ID_AND_BUYER_ID)
	Payment findByListingIdAndBuyerIdInStatus( @Param("listingId") Long listingId, @Param("userId") Long buyerId, @Param("status") List<PaymentStatus> statuses );
	
	@Query(SELECT_BY_REPLY_ID_IN_STATUS)
	Payment findByReplyIdInStatus( @Param("replyId") Long replyId, @Param("status") List<PaymentStatus> statuses );

	@Query(SELECT_ALL_CURRENT_PAYMENTS_BY_USER_ID)
	Page<Payment> findAllByUserId( @Param("userId") Long userId, Pageable p );

	@Query(COUNT_VALID_ASSOCIATED_PAYMENTS)
	Long countValidAssociatedPayments(@Param("twitterId") Long twitterId, @Param("status") List<PaymentStatus> statuses );

	@Query(FIND_BY_ID_WITH_EAGER_FETCHING)
	Payment findByIdWithEagerFetching( @Param("paymentId") Long paymentId, @Param("userId") Long userId );

	@Query(FIND_CURRENT_EFFECTIVE_PAYMENT_STATUS_BY_PAYMENT_ID)
	EffectivePaymentStatus findCurrentEffectivePaymentStatusByPaymentId( @Param("paymentId") Long paymentId );
	
	@Query(SELECT_BY_PAYER_ID_AND_STATUS_AND_CONTENT_FILENAME)
	Payment findByStatusAndBuyerAndContentFilename( @Param("payerId") Long payerId, @Param("contentFilename")String contentFilename, @Param("status") List<PaymentStatus> paymentStatuses);

}
