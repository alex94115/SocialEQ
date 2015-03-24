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
package com.projectlaver.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectlaver.domain.Preapproval;
import com.projectlaver.domain.User;
import com.projectlaver.repository.PreapprovalRepository;
import com.projectlaver.util.PreapprovalStatus;
import com.projectlaver.util.PreapprovalType;

@Service
@Transactional(readOnly = false)
public class PreapprovalService {
	
	/**
	 * Static variables
	 */
	private static final List<PreapprovalStatus> ACTIVE_PREAPPROVAL_STATUSES = new ArrayList<PreapprovalStatus>();


	@Autowired
	private UserService userService;
	
	@Autowired
	private PreapprovalRepository repository;
	
	/**
	 * Convenience method which instantiates a Preapproval based on the input parameters.
	 * 
	 * @param preapprovalKey
	 * @param correlationId
	 * @param status
	 * @return
	 */
	@Transactional(readOnly = false)
	public Preapproval create( String preapprovalKey, String correlationId, PreapprovalType type, PreapprovalStatus status, Date start, Date end ) {
		
		User user = this.userService.getCurrentUser();
		Preapproval preapproval = new Preapproval( preapprovalKey, correlationId, user, type, status, start, end );
		
		return this.create( preapproval );	
	}
	
	/**
	 * Persists a new Preapproval.
	 * 
	 * @param preapproval
	 * @return
	 */
	@Transactional(readOnly = false)
	public Preapproval create( Preapproval preapproval ) {

		return this.repository.save( preapproval ); 
	}
	
	/**
	 * Find the preapprovals associated to a user having one of the specified statuses
	 * 
	 * @param user
	 * @param statuses
	 * @return
	 */
	@Transactional(readOnly = true)
	public Set<Preapproval> findByUserAndPreapprovalStatus( Long userId, List<PreapprovalStatus> statuses) {
		List<Preapproval> preapprovals = this.repository.findByUserAndPreapprovalStatus( userId, statuses );
		return new HashSet<Preapproval>( preapprovals );
	}
	
	/**
	 * Convenience method that uses the definition of valid statuses to fetch
	 * by user.
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = true)
	public Set<Preapproval> findByUserWithValidStatus( Long userId ) {
		List<Preapproval> preapprovals = this.repository.findByUserAndPreapprovalStatus( userId, PreapprovalService.validPreapprovalStatuses() );
		return new HashSet<Preapproval>( preapprovals ); 
	}
	
	@Transactional(readOnly = true)
	public Long countPreapprovalsByUserIdAndStatus( Long userId, List<PreapprovalStatus> statuses ) {
		return this.repository.countPreapprovalsByUserIdAndStatus( userId, statuses );
	}
	
	@Transactional(readOnly = true)
	public Boolean doesUserHaveValidPreapproval( Long userId ) {
		Long validPreapprovals = this.countPreapprovalsByUserIdAndStatus( userId, PreapprovalService.validPreapprovalStatuses() );
		return validPreapprovals != null && validPreapprovals > 0;
	}
	
	/**
	 * Class methods
	 */
	public static List<PreapprovalStatus> validPreapprovalStatuses() {
		if( PreapprovalService.ACTIVE_PREAPPROVAL_STATUSES.isEmpty() ) {
			// Active is the only "valid" status
			PreapprovalService.ACTIVE_PREAPPROVAL_STATUSES.add( PreapprovalStatus.ACTIVE );
		}
		
		return PreapprovalService.ACTIVE_PREAPPROVAL_STATUSES;
	}

}
