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
package com.projectlaver.domain;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class ListingTest {
	
	@Test
	public void testNoTruncLongDescription() {
		
		Listing listing = new Listing();
		
		String nonTruncatedLD = "Some long description.";
		listing.setLongDescription( nonTruncatedLD );
		assertEquals( "No need to truncate", nonTruncatedLD, listing.getTruncatedLongDescription() );
	}
	
	@Test
	public void testSpecialCharacterTrunc() {
		
		Listing listing = new Listing();
		
		String needsTruncatingLD = "This is a long description that exceeds two hundred characters because of how long and descriptive it is. In fact, it is one of the longest and most descriptive descriptions of all time. Just look \n\nhow long it is.";
		String expectedTruncatedLD = "This is a long description that exceeds two hundred characters because of how long and descriptive it is. In fact, it is one of the longest and most descriptive descriptions of all time. Just look ...";
		
		listing.setLongDescription( needsTruncatingLD );
		
		assertEquals( "Has linebreaks; shorten to under 200 characters.", expectedTruncatedLD, listing.getTruncatedLongDescription() );
	}
	
	@Test
	public void testNoLinebreakTrunc() {
		
		Listing listing = new Listing();
		
		String needsTruncatingLD = "Much of the music on My Little One was inspired by my love for the classic recordings of Antonio Carlos Jobim, Billy Holiday, and Frank Sinatra. The songs are nostalgic in nature and may recall the feeling of classic standards, yet still have a modern aesthetic in approach. In creating this atmosphere, the band is made up of a wonderful group of musicians featuring a Portuguese vocalist, Luisa Sobral; drummer, Dan Reiser; bassist, Gary Wang; and Pete Rende plays on a multitude of keyboard instruments.";
		String expectedTruncatedLD = "Much of the music on My Little One was inspired by my love for the classic recordings of Antonio Carlos Jobim, Billy Holiday, and Frank Sinatra. The songs are nostalgic in nature and may recall the...";
		
		listing.setLongDescription( needsTruncatingLD );
		
		assertEquals( "No linebreaks but shorten to under 200 characters.", expectedTruncatedLD, listing.getTruncatedLongDescription() );
	}
	
	@Test
	public void testGetAmountCents() {
		Listing listing = new Listing();
		listing.setAmount( new BigDecimal( "3.33" ));
		assertEquals( new Integer( 333 ), listing.getAmountCents() );
		
		listing.setAmount( new BigDecimal( "3.330" ));
		assertEquals( new Integer( 333 ), listing.getAmountCents() );
		
		listing.setAmount( new BigDecimal( "3.335" ));
		assertEquals( new Integer( 334 ), listing.getAmountCents() );
	}

}
