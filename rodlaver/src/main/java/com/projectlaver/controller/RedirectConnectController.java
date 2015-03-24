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
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;

public class RedirectConnectController extends ConnectController {

	private final Log logger = LogFactory.getLog(getClass());
	
	public RedirectConnectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
		super( connectionFactoryLocator, connectionRepository );
	}

	/**
	 * Overrides the superclass method so that we can check to see if we're in the midst of a Registration flow.
	 * 
	 * Since this information is stored in the HttpSession, but the extensibility point for the ConnectController is the connectedView( String providerId)
	 * method (see below), there's no way to know what flow we're in.
	 * 
	 * This method lets the superclass calculate the result and then checks the Session to see if we're in the special flow. If so, redirect the
	 * user back into that controller.
	 */
	@Override
	@RequestMapping(value="/{providerId}", method=RequestMethod.GET)
	public String connectionStatus(@PathVariable String providerId, NativeWebRequest request, Model model) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Controller received GET request for /connect/" + providerId );
		}
		
		String result = super.connectionStatus( providerId, request, model );

		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Superclass returns result:" + result );
		}
		
		HttpServletRequest servletRequest = (HttpServletRequest)request.getNativeRequest();
		HttpSession session = servletRequest.getSession();
		Boolean isRegistrationPath = session.getAttribute( SelfRegistrationController.REGISTRATION_TYPE_ATTRIBUTE ) != null;
		
		if( isRegistrationPath ) {
			result = "redirect:/registration/returnFromProvider";
			
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "In a Registration Drive flow, so overriding the routing result to: " + result );
			}
		}
		
		return result;
	}

	
	@Override
	protected String connectedView(String providerId) {
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Controller received request for connectedView and providerId: " + providerId );
		}
		
		return "redirect:/user/profile";
	}
}
