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
package com.projectlaver.batch;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

public class GenerateSqlInserts {
	
	private final Log logger = LogFactory.getLog(getClass());

	@Test
	public void test() {
		
		StringBuffer buffer = new StringBuffer();
		
		
		Integer i = 100; 		// {0} -- completed listing id
		Integer j = 500;		// {1} -- completing listing message's twitter id
		Integer k = 1250;		// {2}
		Integer l = 8000;		// {3} -- completed listing reply message's twitter id
		Integer m = 9000;		// {4} -- cancelled listing id
		Integer n = 13000;		// {5} -- cancelled listing message's twitter id
		Integer o = 25000;		// {6} -- cancelled listing reply message's twitter id
		Integer p = 33000;		// {7} -- active listing id
		Integer q = 47000;		// {8} -- active listing's message's twitter id
		Integer r = 55000;		// {9} -- preapproved user's reply to active listing message's twitterId
		Integer s = 66000;		// {10} -- NOT preapproved user's reply to active listing message's twitterId

		for( ; i < 101; i++ ) {
			//Object[] array = new Object[] { String.valueOf( 10 ), String.valueOf( 20 ), String.valueOf( 30 ), String.valueOf( 40 ), String.valueOf( 50 ), String.valueOf( 60 ), String.valueOf( 70 ) };
			Object[] array = new Object[] { 
					String.valueOf( i ), 
					String.valueOf( j ), 
					String.valueOf( k ), 
					String.valueOf( l ), 
					String.valueOf( m ), 
					String.valueOf( n ), 
					String.valueOf( o ),
					String.valueOf( p ),
					String.valueOf( q ),
					String.valueOf( r ),
					String.valueOf( s )
				};
			buffer.append( this.messageSource().getMessage( "completedListingInsertStatements", array , Locale.US ) );	
			buffer.append( this.messageSource().getMessage( "completedListingPurchaseAttemptInsertStatements", array , Locale.US ) );
			buffer.append( this.messageSource().getMessage( "cancelledListingInsertStatements", array , Locale.US ) );
			buffer.append( this.messageSource().getMessage( "cancelledListingPurchaseAttemptInsertStatements", array , Locale.US ) );
			buffer.append( this.messageSource().getMessage( "activeListingInsertStatements", array , Locale.US ) );
			buffer.append( this.messageSource().getMessage( "activeListingPurchaseAttemptInsertStatements", array , Locale.US ) );
			
			j++;
			k++;
			l++;
			m++;
			n++;
			o++;
			p++;
			q++;
			r++;
			s++;
		}
		
		System.out.println ( buffer.toString() );
	}
	
	private ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("test-messages");
		return messageSource;
	}

}
