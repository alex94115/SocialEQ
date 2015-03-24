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

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.StaticMessageSource;

import com.projectlaver.domain.ContentFile;
import com.projectlaver.domain.Listing;

public class AnonymousUserListingActionButtonTest extends ListingActionButtonTest {

	@Test
	public void testInactivePhysicalListingNonWinnerReferrer() {
		Listing listing = super.createInactiveListing( ListingType.SELLING, GoodsType.PHYSICAL );
		listing.setAmount( new BigDecimal( 1.50 ));
		
		Boolean isUserLoggedIn = false;
		Boolean isUserLister = false;
		ListingActionButton button = this.createListingActionButton( listing, isUserLoggedIn, isUserLister, null, null, null, null );
		
		Assert.assertFalse( button.isEnabled() );
		Assert.assertEquals( "Purchase for $1.50 via @ieeiee", button.getText() );
		
		// since listing is inactive, user is anonymous, and referred by is not a purchase link, expect inactive button
		Assert.assertEquals( ListingActionButton.UNDEFINED_MODE, button.getActionMode() );
	}

	@Test
	public void testInactivePhysicalListingPendingPurchaseReferrer() {
		Listing listing = super.createInactiveListing( ListingType.SELLING, GoodsType.PHYSICAL );
		listing.setAmount( new BigDecimal( 1.50 ));
		
		Boolean isUserLoggedIn = false;
		Boolean isUserLister = false;
		Boolean isUserReferredByPendingPurchaseLink = true;

		ListingActionButton button = this.createListingActionButton( listing, isUserLoggedIn, isUserLister, null, null, isUserReferredByPendingPurchaseLink, null );
		
		Assert.assertFalse( button.isEnabled() );
		Assert.assertEquals( "Purchase for $1.50 via @ieeiee", button.getText() );
		
		// since listing is inactive, user is anonymous, and referred by is not a purchase link, expect inactive button
		Assert.assertEquals( ListingActionButton.UNDEFINED_MODE, button.getActionMode() );
	}
	
	@Test
	public void testActivePhysicalListingNonWinnerReferrer() {
		Listing listing = super.createFutureExpiryActiveListing( ListingType.SELLING, GoodsType.PHYSICAL );
		listing.setAmount( new BigDecimal( 1.50 ));

		Boolean isUserLoggedIn = false;
		Boolean isUserLister = false;
		ListingActionButton button = this.createListingActionButton( listing, isUserLoggedIn, isUserLister, null, null, null, null );
		
		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Purchase for $1.50 via @ieeiee", button.getText() );
		Assert.assertEquals( ListingActionButton.COMMENT_MODE, button.getActionMode() );
	}
	
	@Test
	public void testActivePhysicalListingPendingPurchaseReferrer() {
		Listing listing = super.createFutureExpiryActiveListing( ListingType.SELLING, GoodsType.PHYSICAL );
		listing.setAmount( new BigDecimal( 1.50 ));

		Boolean isUserLoggedIn = false;
		Boolean isUserLister = false;
		ListingActionButton button = this.createListingActionButton( listing, isUserLoggedIn, isUserLister, null, null, null, null );
		button.setIsUserReferredByPendingPurchaseLink( true );
		
		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Proceed to Checkout", button.getText() );
		Assert.assertEquals( ListingActionButton.PRE_CHECKOUT_MODE, button.getActionMode() );
	}

	@Test
	public void testWinnerReferredInactiveDigitalGiveawayListing() {
		Listing listing = super.createInactiveListing( ListingType.CAMPAIGN, GoodsType.DIGITAL );
		
		Set<ContentFile> contentFiles = new HashSet<ContentFile>();
		ContentFile file = new ContentFile();
		file.setDigitalContentOriginalFilename( "champagne.jpeg");
		contentFiles.add( file );
		listing.setContentFiles( contentFiles );
		
		Boolean isUserLoggedIn = false;
		Boolean isUserLister = false;
		ListingActionButton button = this.createListingActionButton( listing, isUserLoggedIn, isUserLister, null, null, null, null );
		
		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Download File", button.getText() );
		Assert.assertEquals( ListingActionButton.TRANSACTION_DETAIL_MODE, button.getActionMode() );
	}

}
