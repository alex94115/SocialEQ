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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projectlaver.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * This query needs to have an outer join to addresses, since not all users
	 * have addresses / messages
	 */
	String FIND_USER_EAGERLY_FETCH_ADDRESSES =
		  " SELECT u "
		+ " FROM Users u "
		+ "   LEFT OUTER JOIN FETCH u.addresses a "
		+ " WHERE u.id = (:id) ";

	String FIND_USER_AND_FETCH_MESSAGES =
		  " SELECT u "
		+ " FROM Users u "
		+ "   LEFT OUTER JOIN FETCH u.messages "
		+ " WHERE u.id = (:id)";

	String FIND_USER_AND_FETCH_ACCEPTED_TOS =
		  " SELECT u "
		+ " FROM Users u "
		+ "   LEFT OUTER JOIN FETCH u.acceptedTermsOfService "
		+ " WHERE u.id = (:id)";

	User findByUsername(String username);

	@Query(FIND_USER_EAGERLY_FETCH_ADDRESSES)
	User findUserEagerlyFetchAddresses( @Param("id") Long id );

	@Query(FIND_USER_AND_FETCH_MESSAGES)
	User findByUserIdAndFetchMessages( @Param("id") Long id );

	@Query(FIND_USER_AND_FETCH_ACCEPTED_TOS)
	User findByUserIdAndFetchAcceptedTos( @Param("id") Long id );

}
