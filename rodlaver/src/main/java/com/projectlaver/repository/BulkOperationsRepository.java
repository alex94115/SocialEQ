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

import com.projectlaver.domain.Inventory;
import com.projectlaver.domain.Message;
import com.projectlaver.domain.User;
import com.projectlaver.util.BusinessMetricsDTO;

/**
 * Interface that gives services the ability to do direct database interactions
 * (bypassing hibernate).
 *
 * @author alexduff
 *
 */
public interface BulkOperationsRepository {

	String getUsernameByProviderUserId( final String providerId, final String providerUserId );

	Message insertListingMessage( final Message message );

	Boolean updateMessage( final Message message );

	List<Long> findUserPendingMeansOfPaymentListingIds( Long userId );
	
	/**
	 * When a user initially signs up, they may have purchase messages in "pending registration" status. If
	 * this is the case, some web functionality will not work. So upon sign up, this method 
	 * will tick any of these messages into "pending means of payment" status. This will allow the 
	 * user to immediately go to Checkout for a pending purchase.
	 * 
	 * @param providerId
	 * @param providerUserId
	 * @return
	 */
	int unblockPendingRegistrationPurchaseMessages( String providerId, String providerUserId );

	/**
	 * Get business metrics for a given listing id between the provided start and end dates.
	 * 
	 * @param listingId
	 * @param start
	 * @param end
	 * @return
	 */
	BusinessMetricsDTO getMetricsByListingId(Long listingId, Long start, Long end);

	/**
	 * Get business metrics for all seller's listings between the provided start and end dates.
	 * 
	 * @param listingId
	 * @param start
	 * @param end
	 * @return
	 */
	BusinessMetricsDTO getMetricsBySellerId( Long sellerId, Long start, Long end);

	/**
	 * Records the act of a user downloading a content file
	 * 
	 * @param userId
	 * @param listingId
	 * @param contentFileId
	 * @param paymentId
	 */
	void insertDownloadInstance( Long userId, Long listingId, Long contentFileId, Long paymentId );

	/**
	 * Checks to see if this seller is in the CurrentTwitterStreamSellers table, 
	 * which is in effect a check to see if this user had "seller" privileges the last
	 * time Tomcat started up and initialized the Twitter stream.
	 * 
	 * @param seller
	 * @return
	 */
	Boolean isInCurrentTwitterStream(User seller);

	/**
	 * Locks & decrements the given row in the Inventories table by the requested quantity. Returns
	 * true if this operation succeeds or returns false if there is insufficient quantity available to
	 * decrement (could be sold out or too few remaining).
	 * 
	 * @param inventory
	 * @param quantity
	 * @return
	 */
	Boolean decrementInventory( Inventory inventory, Integer quantity );

	/**
	 * Check to see if this is a valid (existent) listing id
	 * 
	 * @param id
	 * @return true if this listing id exists
	 */
	Boolean doesListingExist(Long id);
	
	List<Long> findUsersWithActiveFacebookListings(); 

}
