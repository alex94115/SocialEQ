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
package com.projectlaver.batch.communication;

import java.util.List;

import com.projectlaver.batch.domain.MessageStateChangeCommunicationsCursorItem;

public class TwitterThrottle implements ExternalCommunicationSender {
	
	/**
	 *  instance variables
	 */
	
	// Array of historical call times
	List<Long> callTimes;
	
	// Pointers
	Integer oldestInPastTenSeconds;
	Integer oldestInPastFiveMinutes;
	Integer oldestInPastHour;
	
	/**
	 *  constants
	 */

	public static final long TEN_SECONDS = 10000L; // 10 s * 1000 ms / sec 
	public static final long FIVE_MINUTES = 300000L; // 300 s * 1000 ms / sec
	public static final long ONE_HOUR = 3600000L; // 3600 sec * 1000 ms / sec
	
	
	// Constructor 
	public TwitterThrottle( List<Long> callTimes ) {
		this.callTimes = callTimes;
	}
	

	@Override
	public Boolean sendExternalCommunication( MessageStateChangeCommunicationsCursorItem item, String formattedMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Boolean send( String twitterConsumerKey, 
						 String twitterConsumerSecret, 
						 MessageStateChangeCommunicationsCursorItem item,
						 String formattedMessage ) {
		
		// move the pointers so that we can count the number of previous tweets in the throttled timespans
		this.updatePointers( System.currentTimeMillis() );
		
		if( this.oldestInPastTenSeconds != null ) {
			throw new ThrottleLimitExceededException( "Request to tweet was fewer than 10s after the previous tweet." );
		}
		
		if( this.oldestInPastFiveMinutes != null ) {
			// get the number of items in the list after five minutes ago
			int count = this.callTimes.size() - this.oldestInPastFiveMinutes;
			if( count >= 8 ) {
				throw new ThrottleLimitExceededException( "There are at least eight tweets in the past five minutes." );
			}
		}
		
		if( this.oldestInPastHour != null ) {
			// get the number of items in the list after one hour ago
			int count = this.callTimes.size() - this.oldestInPastHour;
			if( count >= 100 ) {
				throw new ThrottleLimitExceededException( "There are at least one hundred tweets in the past hour." );
			}
		}
		
		return true;
	}
	
	private void updatePointers( long now ) {
		if( this.callTimes != null && this.callTimes.size() > 0 ) {
			
			// see if the most recent call (last in the list) is within the last
			// ten seconds, five minutes, and one hour. If so, set these pointers
			int i = callTimes.size() - 1; 
			long sinceLastCall = now - this.callTimes.get( i );
			
			if( sinceLastCall <= ONE_HOUR ) {
				this.oldestInPastHour = i;
				
				if( sinceLastCall <= FIVE_MINUTES ) {
					this.oldestInPastFiveMinutes = i;
					
					if( sinceLastCall <= TEN_SECONDS ) {
						this.oldestInPastTenSeconds = i;
					}
				}
			}
			
			// start going backwards through the list and move the pointers 
			// if the previous item is still within the relevant timespan
			for( int j = callTimes.size() - 1; j >= 0; j-- ) {
				long sincePreviousCall = now - this.callTimes.get( j );
				
				if( sincePreviousCall <= ONE_HOUR ) {
					this.oldestInPastHour = j;
					
					if( sincePreviousCall <= FIVE_MINUTES ) {
						this.oldestInPastFiveMinutes = j;
						
						if( sincePreviousCall <= TEN_SECONDS ) {
							this.oldestInPastTenSeconds = j;
						}
					}
				} else {
					// if this item is older than one hour, the pointers
					// are all set and we can stop going backwards
					break;
				}
				
			}
			
		}
	}
	
}
