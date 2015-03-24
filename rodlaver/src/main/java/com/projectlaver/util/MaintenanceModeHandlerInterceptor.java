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
package com.projectlaver.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.projectlaver.service.MaintenanceModeService;

public class MaintenanceModeHandlerInterceptor implements HandlerInterceptor {
	
	@Value("${application.maintenanceModeRedirect}")
	private String redirect;
	
	@Autowired
	MaintenanceModeService service;
	
	private List<RequestMatcher> matchers;
	
	public MaintenanceModeHandlerInterceptor() {
		
		String[] antPatterns = { 
				"/maintenance", 
				"/user/userAdminSearch",
				"/user/userAdminUpdate",
				"/logout",
				"/signin",
				"/auth/**"
			};

		this.matchers = new ArrayList<RequestMatcher>();
        for(String pattern : antPatterns) {
            this.matchers.add(new AntPathRequestMatcher(pattern));
        }
	}
	
	@Override
	public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception {
		
		Boolean result = true;
		
		if( this.service.isInMaintenanceMode() ) {
			//if( !request.getServletPath().equals( "/maintenance" )) {
			if( !this.doesRequestBypassMaintenanceMode( request ) ) {
				response.sendRedirect( this.redirect );
				result = false;
			} 
		}
		
		return result;
	} 

	@Override
	public void afterCompletion( HttpServletRequest request, HttpServletResponse response, Object handler, Exception e ) throws Exception {
		// no op
	}

	@Override
	public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model ) throws Exception {
		// no op
	}
	
	/**
	 * Helper methods
	 */
	
	Boolean doesRequestBypassMaintenanceMode( HttpServletRequest request ) {
		
		Boolean result = false; 
		
		for( RequestMatcher matcher : this.matchers )  {
			if( matcher.matches( request )) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
}
