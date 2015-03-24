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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler( AccessDeniedException.class)
	public ModelAndView handleAccessDeniedException( AccessDeniedException e ) {
		this.logger.error( "Access denied exception: ", e );
		ModelAndView model = new ModelAndView();
		model.getModel().put( "activePage", "denied" );
		model.setViewName( "denied" );
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Boolean isUserLoggedIn = ( authentication != null && authentication.getName() != null && !authentication.getName().equals( "anonymousUser" ) );
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( String.format( "isUserLoggedIn: [%s] for authentication.getName(): [%s]", isUserLoggedIn, (authentication == null ? "" : authentication.getName() ) ) );
		}
		
		model.getModel().put( "isUserLoggedIn", isUserLoggedIn );
		
		String viewName = model.getViewName();
		if( viewName != null ) {
			String activePage = model.getViewName().replace( "/", "." );
			model.getModel().put( "activePage", activePage );
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( String.format( "activePage: [%s]", activePage ) );
			}
		}
		
		return model;
	}
	
	@ExceptionHandler(Throwable.class)
	public ModelAndView defaultErrorHandler( HttpServletRequest req, Throwable t ) throws Throwable {
		this.logger.error( "Exception bubbled up to the Controller: ", t );
		
		// If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it 
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation( t.getClass(), ResponseStatus.class ) != null) {
            throw t;
        }
		
		ModelAndView model = new ModelAndView();
		model.addObject( "exception", t );
		model.addObject( "url", req.getRequestURL() );
		model.getModel().put( "activePage", "error" );
		model.setViewName( "error" );
		
		return model;
	}
	

}
