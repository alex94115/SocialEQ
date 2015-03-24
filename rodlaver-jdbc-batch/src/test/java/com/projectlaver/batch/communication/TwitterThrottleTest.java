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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.projectlaver.batch.domain.MessageStateChangeCommunicationsCursorItem;

public class TwitterThrottleTest {
	
	private String twitterConsumerKey;
	private String twitterConsumerSecret;
	private MessageStateChangeCommunicationsCursorItem item;
	private String formattedMessage;
	
	
	@Before
	public void before() {
		// information regarding the sender and the tweet
		this.twitterConsumerKey = "consumerKey";
		this.twitterConsumerSecret = "consumerSecret";
		this.item = null;
		this.formattedMessage = null;
	}
	
	@Test
	public void testNoThrottleNeeded() {

		List<Long> callTimes = new ArrayList<Long>();
		callTimes.add( 0L );
		TwitterThrottle throttler = new TwitterThrottle( callTimes );
		
		Boolean result = throttler.send( this.twitterConsumerKey, this.twitterConsumerSecret, this.item, this.formattedMessage );
		assertTrue( "Should have returned true since no throttle limit was exceeded.", result );
	}

	@Test
	public void testBlockMoreThanOncePerTenSeconds() {

		// instantiate the throttler and seed it with a call that just happened
		List<Long> callTimes = new ArrayList<Long>();
		callTimes.add( System.currentTimeMillis() );
		TwitterThrottle throttler = new TwitterThrottle( callTimes );
		
		try {
			throttler.send( this.twitterConsumerKey, this.twitterConsumerSecret, this.item, this.formattedMessage );
			fail( "Should have thrown a ThrottleLimitExceededException." );
		} catch( ThrottleLimitExceededException e ) {
			// pass
		}
	}
	
	@Test
	public void testAllowOncePerTenSeconds() {

		List<Long> callTimes = new ArrayList<Long>();
		callTimes.add( System.currentTimeMillis() - 10001 ); // one millisecond more than 10s ago
		TwitterThrottle throttler = new TwitterThrottle( callTimes );
		
		Boolean result = throttler.send( this.twitterConsumerKey, this.twitterConsumerSecret, this.item, this.formattedMessage );
		assertTrue( "Should have returned true since once per 10s is allowed.", result );
	}
	
	@Test
	public void testBlockMoreThanEightPerFiveMinutes() {

		// instantiate the throttler and seed it with eight calls in the past 5m (300s)
		List<Long> callTimes = new ArrayList<Long>();
		
		long previous = System.currentTimeMillis() - 290000;
		
		// add eight previous tweets in the past five minutes
		for( int i = 0; i < 8; i++ ) {
			callTimes.add( previous );
		}

		TwitterThrottle throttler = new TwitterThrottle( callTimes );
		
		try {
			throttler.send( this.twitterConsumerKey, this.twitterConsumerSecret, this.item, this.formattedMessage );
			fail( "Should have thrown a ThrottleLimitExceededException." );
		} catch( ThrottleLimitExceededException e ) {
			// pass
		}
	}
	
	@Test
	public void testAllowEightPerFiveMinutes() {

		// instantiate the throttler and seed it with eight calls in the past 5m (300s)
		List<Long> callTimes = new ArrayList<Long>();
		
		long previous = System.currentTimeMillis() - 290000;
		
		// add seven previous tweets in the past five minutes
		for( int i = 0; i < 7; i++ ) {
			callTimes.add( previous );
		}

		TwitterThrottle throttler = new TwitterThrottle( callTimes );
		
		Boolean result = throttler.send( this.twitterConsumerKey, this.twitterConsumerSecret, this.item, this.formattedMessage );
		assertTrue( "Should have returned true since eight per five minutes is allowed.", result );
	}
	
	@Test
	public void testBlockMoreThanOneHundredPerHour() {

		// instantiate the throttler and seed it with 100 calls in the past 60m (3600s)
		List<Long> callTimes = new ArrayList<Long>();
		
		long previous = System.currentTimeMillis() - 3590000;
		
		// add 100 previous tweets in the past hour
		for( int i = 0; i < 100; i++ ) {
			callTimes.add( previous );
		}

		TwitterThrottle throttler = new TwitterThrottle( callTimes );
		
		try {
			throttler.send( this.twitterConsumerKey, this.twitterConsumerSecret, this.item, this.formattedMessage );
			fail( "Should have thrown a ThrottleLimitExceededException." );
		} catch( ThrottleLimitExceededException e ) {
			// pass
		}
	}
	
	@Test
	public void testAllowOneHundredPerHour() {

		// instantiate the throttler and seed it with eight calls in the past 5m (300s)
		List<Long> callTimes = new ArrayList<Long>();
		
		long previous = System.currentTimeMillis() - 3590000;
		
		// add ninety-nine previous tweets in the past five minutes
		for( int i = 0; i < 99; i++ ) {
			callTimes.add( previous );
		}

		TwitterThrottle throttler = new TwitterThrottle( callTimes );
		
		Boolean result = throttler.send( this.twitterConsumerKey, this.twitterConsumerSecret, this.item, this.formattedMessage );
		assertTrue( "Should have returned true since one hundred per hour is allowed.", result );
	}
	


}
