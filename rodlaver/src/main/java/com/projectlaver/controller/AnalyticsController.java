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
package com.projectlaver.controller;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projectlaver.service.AnalyticsService;
import com.projectlaver.service.ListingService;
import com.projectlaver.service.UserService;
import com.projectlaver.util.BusinessMetricsDTO;
import com.projectlaver.util.MvcConstants;
import com.projectlaver.util.UserInterfaceMessageDto;

@Controller
@RequestMapping("/analytics")
public class AnalyticsController {

	/**
	 * Instance variables
	 */
	
	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private AnalyticsService analyticsService;
		
	@Autowired
    private MessageSource messageSource;
	
	/**
	 * Constants
	 */

	private static final Long DAY_IN_MS = 1000 * 60 * 60 * 24L;
	private static final Long WEEK_IN_MS = 7 * DAY_IN_MS;
	private static final Long YEAR_IN_MS = 52 * WEEK_IN_MS;

	
	/**
	 * Public methods
	 */

	@PreAuthorize("hasAnyRole('ROLE_CHARITY','ROLE_SELLER')")
	@RequestMapping(value="/overview/{period}") 
	public String getSummaryAnalytics( ModelMap model, @PathVariable String period, Locale locale ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Controller received request for analytics/overview" );
		}
		
		if( period == null ) {
			period = "DAY";
		}
		
		Long start = this.getTimeAtStartOfPeriod( period );
		Long end = System.currentTimeMillis();
		
		Map<String, Object> analyticsAttributes = this.analyticsService.getOverviewAnalytics( start, end );
		model.addAllAttributes( analyticsAttributes );
		model.put( "period", period );
		
		// add UI alerts to the model
		model.addAttribute( MvcConstants.UI_MESSAGES_KEY, this.createUserAlert( analyticsAttributes, locale ) );
		
		
		return "analytics/overview";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_CHARITY','ROLE_SELLER')")
	@RequestMapping(value="/detail/{id}/{period}") 
	public String getDetailAnalytics( @PathVariable Long id, @PathVariable String period, ModelMap model, Locale locale ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Controller received request for analytics/detail" );
		}

		Long start = this.getTimeAtStartOfPeriod( period );
		Long end = System.currentTimeMillis();
		
		// look up listing and metrics
		Map<String, Object> analyticsAttributes = this.analyticsService.getListingAndDetailMetrics( id, start, end );
		model.addAllAttributes( analyticsAttributes );
		
		// add UI alerts to the model
		model.addAttribute( MvcConstants.UI_MESSAGES_KEY, this.createUserAlert( analyticsAttributes, locale ) );
		
		return "analytics/detail";
	}
	
	
	UserInterfaceMessageDto createUserAlert( Map<String, Object> analyticsAttributes, Locale locale ) {
		
		BusinessMetricsDTO dto = ( BusinessMetricsDTO )analyticsAttributes.get( "metrics" );
		
		UserInterfaceMessageDto message = null;
		BigDecimal zero = new BigDecimal( "0.00" );
		
		if( dto.getGrossSales().compareTo( zero ) > 0 ) {
			message = new UserInterfaceMessageDto( this.messageSource, 
												   "analytics.alert.gross.sales", 
												   new Object[] { dto.getGrossSales() }, 
												   locale, 
												   UserInterfaceMessageDto.SUCCESS );
		
		} else if( dto.getOptIns() > 0 ) {
			message = new UserInterfaceMessageDto( this.messageSource, 
												   "analytics.alert.total.opt.ins", 
												   new Object[] { dto.getOptIns() }, 
												   locale, 
												   UserInterfaceMessageDto.SUCCESS );
		
		} else if( dto.getResponses() > 0 ) {
			message = new UserInterfaceMessageDto( this.messageSource, 
												   "analytics.alert.total.responses", 
												   new Object[] { dto.getResponses() }, 
												   locale, 
												   UserInterfaceMessageDto.SUCCESS );
		}
		
		return message;
		
	}
	
	private Long getTimeAtStartOfPeriod( String period ) {
		Long result = null;
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Calculating start time for the period starting one " + period + " ago." );
		}
		
		if( StringUtils.equalsIgnoreCase( period, "YEAR" )) {
			result = System.currentTimeMillis() - YEAR_IN_MS;
		} else if( StringUtils.equalsIgnoreCase( period, "WEEK" )) {
			result = System.currentTimeMillis() - WEEK_IN_MS;
			
		} else {
			result = System.currentTimeMillis() - DAY_IN_MS ;
		}
		
		return result;
	}
	
}
