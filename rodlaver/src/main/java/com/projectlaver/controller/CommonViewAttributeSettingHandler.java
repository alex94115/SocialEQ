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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.projectlaver.domain.User;
import com.projectlaver.service.UserService;

public class CommonViewAttributeSettingHandler implements HandlerInterceptor {

	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private UserService userService;

	@Value("${facebook.isEnabled}")
	private Boolean isFacebookEnabled;

	@Value("${twitter.isEnabled}")
	private Boolean isTwitterEnabled;

	@Value("${instagram.isEnabled}")
	private Boolean isInstagramEnabled;
	
	@Value("${google.doEnableGoogleAnalytics}")
	private Boolean isGoogleAnalyticsEnabled;
	
	@Value("${aws.cloudfront.public.base.url}")
	private String cloudfrontPublicBaseUrl;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "+++\" %s %s\"", request.getMethod(), request.getRequestURI()  ) );
		}
		
		return true;
	}

	
	/** 
	 * Set a couple of common variables onto the model before dispatching to the view
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) 
			throws Exception {

		if( this.logger.isDebugEnabled() ) {
			
			if( SecurityContextHolder.getContext() == null || 
				SecurityContextHolder.getContext().getAuthentication() == null ) {
				this.logger.debug( String.format( "SecurityContext: %s , authentication: %s", SecurityContextHolder.getContext(), ( SecurityContextHolder.getContext() == null ? "" :SecurityContextHolder.getContext().getAuthentication() ) ) );
			} else {
				this.logger.debug( String.format( "authenticationName: [%s]", SecurityContextHolder.getContext().getAuthentication().getName() ) );
			}
			
			// this may be null in the case of a streaming operation
			if( modelAndView != null ) {
				this.logger.debug( String.format( "view: [%s]", modelAndView.getViewName() ) );
			}
		}
		
		if( this.doAddCommonAttributes( modelAndView ) ) {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Boolean isUserLoggedIn = ( authentication != null && authentication.getName() != null && !authentication.getName().equals( "anonymousUser" ) );
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( String.format( "isUserLoggedIn: [%s] for authentication.getName(): [%s]", isUserLoggedIn, (authentication == null ? "" : authentication.getName() ) ) );
			}
			
			// is the user logged in?
			modelAndView.getModel().put( "isUserLoggedIn", isUserLoggedIn );
			
			if( isUserLoggedIn ) {
				User currentUser = this.userService.findByUsername( authentication.getName() );
				modelAndView.getModel().put( "currentUser", currentUser );
			}
			
			// add the base cloudfront URL
			modelAndView.getModel().put( "cloudfrontPublicBaseUrl", cloudfrontPublicBaseUrl );
			
			// what is the active view?
			String viewName = modelAndView.getViewName();
			if( viewName != null ) {
				String activePage = modelAndView.getViewName().replace( "/", "." );
				modelAndView.getModel().put( "activePage", activePage );
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( String.format( "activePage: [%s]", activePage ) );
				}
			}
			
			// Add some properties about features configured on or off
			this.configureEnabledFeatures( modelAndView.getModel() );
		}

		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "---\"%s %s\" %d", request.getMethod(), request.getRequestURI(), response.getStatus() ) );
		}

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// no op

	}
	
	Boolean doAddCommonAttributes( ModelAndView modelAndView ) {
		return( modelAndView != null && 
				modelAndView.getViewName() != null &&
				!modelAndView.getViewName().toString().startsWith( "redirect" ) );
	}

	void configureEnabledFeatures( Map<String, Object> model ) {
		
		model.put( "isFacebookEnabled", this.isFacebookEnabled );
		model.put( "isTwitterEnabled", this.isTwitterEnabled );
		model.put( "isInstagramEnabled", this.isInstagramEnabled );
		model.put( "doEnableGoogleAnalytics", this.isGoogleAnalyticsEnabled );
		
		
	}
}
