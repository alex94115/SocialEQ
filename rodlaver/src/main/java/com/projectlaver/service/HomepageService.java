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

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectlaver.domain.Listing;
import com.projectlaver.domain.User;
import com.projectlaver.integration.SocialProviders;
import com.projectlaver.util.BusinessMetricsDTO;

@Service
@Transactional(readOnly = true)
public class HomepageService {
	
	@Autowired
	private AnalyticsService analyticsService;
	
	@Autowired
	private ListingService listingService;
	
	@Autowired
	private PreapprovalService preapprovalService;
	
	@Autowired
	private UserService userService;
	
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * Static constants
	 */
	
	private static final Long DAY_IN_MS = 1000 * 60 * 60 * 24L;
	private static final Long YEAR_IN_MS = 365 * DAY_IN_MS;

	/**
	 * Instance methods
	 */

	@Transactional(readOnly = true)
	public Map<String, Object> doBuyerUserHome( User user, Locale locale ) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		// Figure out this user's primary connection, based on their username
		result.put( "isFacebookUser", user.getUsername().contains( SocialProviders.FACEBOOK ) );
		result.put( "isTwitterUser", user.getUsername().contains( SocialProviders.TWITTER ) );
		
		// Check if the the user has accepted the current TOS
		result.put( "hasAcceptedCurrentBuyerTos", ( user.getHasAcceptedCurrentBuyerTos() == null ? false : user.getHasAcceptedCurrentBuyerTos() ) );

		// Check for preapprovals
		Long validPreapprovalsCount = this.preapprovalService.countPreapprovalsByUserIdAndStatus( user.getId(), PreapprovalService.validPreapprovalStatuses() );
		result.put( "validPreapprovalsCount", ( validPreapprovalsCount == null ? 0 : validPreapprovalsCount ) );
		result.put( "userHasActivePaypalPreapproval", validPreapprovalsCount != null && validPreapprovalsCount > 0 );
		
		// Check for any listings this user has a PENDING_MEANS_OF_PAYMENT message
		List<Long> userPendingPaymentListingIds = this.listingService.findUserPendingMeansOfPaymentListingIds( user.getId() );
		
		// Add attributes related to any pendingPayments
		if( userPendingPaymentListingIds != null && userPendingPaymentListingIds.size() > 0 ) {
			result.put( "doesUserHavePendingPayment", true );
			result.put( "mostRecentPendingPaymentListing", userPendingPaymentListingIds.get(0) );
			
		} else {
			result.put( "doesUserHavePendingPayment", false );
		}

		return result;
	}

	public Map<String, Object> doSellerHomepage( Pageable p ) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		User seller = this.userService.getCurrentUser();
		Page<Listing> listingPage = this.listingService.findListingsBySellerId( seller.getId(), p );
		
		// Get the metrics on these listings
		Map<Long, BusinessMetricsDTO> metricsMap = new HashMap<Long, BusinessMetricsDTO>();
		
		List<Listing> listings = listingPage.getContent();
		for( Listing listing : listings ) {
			// get the past year (aka all-time) statistics
			Long start = System.currentTimeMillis() - YEAR_IN_MS; 
			Long end = System.currentTimeMillis();
			
			BusinessMetricsDTO dto = ( BusinessMetricsDTO )this.analyticsService.getDetailMetrics( listing, start, end ).get( "metrics" );
			metricsMap.put( listing.getId(), dto );
		}
		
		result.put( "metricsMap", metricsMap );
		result.put( "seller", seller );
		result.put( "page", listingPage );
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Returned page.getNumber()=" + listingPage.getNumber() );
			this.logger.debug( "Total pages:" + listingPage.getTotalPages() );
		}
		
		return result;
	}
	
}
