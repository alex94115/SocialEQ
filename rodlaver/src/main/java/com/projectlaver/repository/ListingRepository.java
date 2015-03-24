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

import com.projectlaver.domain.Listing;


public interface ListingRepository extends JpaRepository<Listing, Long>{

	String FIND_BY_SELLER_AND_CONTENT_FILENAME =
			   "SELECT l "
			+ " FROM Listings l "
			+ "   INNER JOIN l.seller u "
			+ "   INNER JOIN l.contentFiles cf "
			+ " WHERE u.username=(:userName) "
			+ "   AND cf.contentFilename= (:contentFilename)";

	String FIND_ALL_BY_SELLER_ID =
			  " SELECT l "
		    + " FROM Listings l "
		    + "   INNER JOIN l.seller u "
		    + " WHERE u.id=(:userId) "
		    + " ORDER BY l.created DESC ";

	String FIND_ONE_BY_ID_EAGERLY_FETCH_CONTENT_AND_MESSAGE =
			  " SELECT l "
			+ " FROM Listings l "
			+ "   LEFT OUTER JOIN FETCH l.contentFiles c "		  
			+ "   LEFT OUTER JOIN FETCH l.messages m "
			+ "   INNER JOIN FETCH l.seller u "
			+ " WHERE l.id = (:id) ";
	
	String FIND_ACTIVE_FACEBOOK_LISTINGS = 
			  " SELECT l "
			+ " FROM Listings l "
			+ " WHERE l.doPostToFacebook IS TRUE "
			+ "  AND l.status = 'ACTIVE' ";
	

	@Query(FIND_BY_SELLER_AND_CONTENT_FILENAME)
	Listing findBySellerByUsernameAndContentFilename( @Param("userName") String sellerUsername, @Param("contentFilename") String contentFilename );

	@Query(FIND_ALL_BY_SELLER_ID)
	Page<Listing> findListingsBySellerId( @Param("userId") Long userId, Pageable p );

	@Query(FIND_ONE_BY_ID_EAGERLY_FETCH_CONTENT_AND_MESSAGE)
	Listing findByListingIdEagerlyFetchContentAndMessage( @Param("id") Long id );
	
	@Query(FIND_ACTIVE_FACEBOOK_LISTINGS)
	Set<Listing> findActiveFacebookListings();
	
}
