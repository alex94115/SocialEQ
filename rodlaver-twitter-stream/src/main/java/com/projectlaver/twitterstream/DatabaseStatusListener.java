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
package com.projectlaver.twitterstream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;

import com.projectlaver.util.TwitterListingResponseProcessor;
import com.twitter.hbc.twitter4j.handler.StatusStreamHandler;
import com.twitter.hbc.twitter4j.message.DisconnectMessage;
import com.twitter.hbc.twitter4j.message.StallWarningMessage;

public class DatabaseStatusListener implements StatusStreamHandler {

	/**
	 * Instance variables
	 */
	
	private final Log logger = LogFactory.getLog(getClass());
	
	private TwitterListingResponseProcessor responseProcessor;
	
	/**
	 * Static variables
	 */
	
	public DatabaseStatusListener( TwitterListingResponseProcessor responseProcessor ) {
		this.responseProcessor = responseProcessor;
	}
	
	@Override
	public void onStatus(Status status) {
		this.responseProcessor.processStatus( status );
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		logger.info( statusDeletionNotice );
	}

	@Override
	public void onTrackLimitationNotice(int limit) {
		logger.info( limit );
	}

	@Override
	public void onScrubGeo(long user, long upToStatus) {
		logger.info( user + upToStatus );
	}

	@Override
	public void onStallWarning(StallWarning warning) {
		logger.info( warning );
	}

	@Override
	public void onException(Exception e) {
		logger.info( e );
	}

	@Override
	public void onDisconnectMessage(DisconnectMessage message) {
		logger.info( message );
	}

	@Override
	public void onStallWarningMessage(StallWarningMessage warning) {
		logger.info( warning );
	}

	@Override
	public void onUnknownMessageType(String s) {
		logger.info( s );
	}
	
}
