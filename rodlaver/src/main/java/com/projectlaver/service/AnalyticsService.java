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
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectlaver.domain.Listing;
import com.projectlaver.repository.BulkOperationsRepository;
import com.projectlaver.util.BusinessMetricsDTO;
import com.projectlaver.util.GoodsType;
import com.projectlaver.util.ListingType;

@Service
@Transactional(readOnly = true)
public class AnalyticsService {

	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private BulkOperationsRepository bulkMessageOperationsRepository;
		
	@Autowired
	private ListingService listingService;
	
	@Autowired 
	private UserService userService;
	
	/**
	 * 
	 * 
	 * @param listingId
	 * @param start to constrain the metrics based on a start, pass in the begin date. If null, all metrics will be retrieved.
	 * @param end to constrain the metrics based on a end date, pass in the end date. If null, all metrics will be retrieved.
	 * @return
	 */
	public Map<String, Object> getOverviewAnalytics( Long start, Long end ) {
		
		Long sellerId = this.userService.getCurrentUserId();
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		BusinessMetricsDTO metrics = this.bulkMessageOperationsRepository.getMetricsBySellerId( sellerId, start, end );
		
		result.put( "metrics", metrics );
		result.put( "mode", "overview" );
		
		return result;
	}
	
	/**
	 * 
	 * 
	 * @param listingId
	 * @param start to constrain the metrics based on a start, pass in the begin date. If null, all metrics will be retrieved.
	 * @param end to constrain the metrics based on a end date, pass in the end date. If null, all metrics will be retrieved.
	 * @return
	 */
	public Map<String, Object> getListingAndDetailMetrics( Long listingId, Long start, Long end ) {
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		Listing listing = this.listingService.findById( listingId );
		result.put( "listing", listing );
		result.putAll( this.createListingDetailTypeBooleans( listing ) );
		
		result.putAll( this.getDetailMetrics( listing, start, end ));
		
		return result;
		 
	}
	
	public Map<String, Object> getDetailMetrics( Listing listing, Long start, Long end ) {
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		BusinessMetricsDTO metrics = this.bulkMessageOperationsRepository.getMetricsByListingId( listing.getId(), start, end );
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "MetricsDTO: " + ToStringBuilder.reflectionToString( metrics ));
		}
		
		result.put( "metrics", metrics );
		result.put( "mode", "detail" );
		
		result.putAll( this.createListingDetailTypeBooleans( listing ) );
		
		return result;
		 
	}
	

	/**
	 * Internal methods 
	 */
	
	Map<String, Boolean> createListingDetailTypeBooleans( Listing listing ) {
		
		Boolean isGiveaway = false;
		Boolean isDigitalGiveaway = false;
		Boolean isDonation = false;
		Boolean isSale = false;
		
		
		if( listing.getType().equals( ListingType.CAMPAIGN )) {
			isGiveaway = true;
			if( listing.getGoodsType().equals( GoodsType.DIGITAL ) ) {
				isDigitalGiveaway = true;
			}
			
		} else if( listing.getType().equals( ListingType.DONATION )) {
			isDonation = true;
		} else {
			isSale = true;
		}
		
		
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		result.put( "isGiveaway", isGiveaway );
		result.put( "isDigitalGiveaway", isDigitalGiveaway );
		result.put( "isDonation", isDonation );
		result.put( "isSale", isSale );
		
		return result;
	}
	
}
