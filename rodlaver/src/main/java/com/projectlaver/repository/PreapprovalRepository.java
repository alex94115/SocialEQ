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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projectlaver.domain.Preapproval;
import com.projectlaver.util.PreapprovalStatus;

public interface PreapprovalRepository extends JpaRepository<Preapproval, Long>{
	
	String FIND_ALL_BY_USER_AND_STATUS = 
			"SELECT p"
	        + " FROM Preapprovals p "
	        + " INNER JOIN p.user u "
	        + " WHERE p.status IN (:status) "
	        + "   AND u.id = (:userId)";
	
	String COUNT_PREAPPROVALS_BY_USER_AND_STATUS =
			"SELECT count( p ) "
	        + " FROM Preapprovals p " 
			+ " INNER JOIN p.user u"
			+ " WHERE p.status IN (:status) "
			+ " AND u.id = (:userId)";
	
	@Query(FIND_ALL_BY_USER_AND_STATUS)
	List<Preapproval> findByUserAndPreapprovalStatus(@Param("userId") Long userId, @Param("status") List<PreapprovalStatus> statuses);

	@Query(COUNT_PREAPPROVALS_BY_USER_AND_STATUS)
	Long countPreapprovalsByUserIdAndStatus(@Param("userId") Long userId, @Param("status") List<PreapprovalStatus> statuses);

}
