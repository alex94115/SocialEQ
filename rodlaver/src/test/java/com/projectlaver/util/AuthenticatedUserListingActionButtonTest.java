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
import java.util.Calendar;
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

public class AuthenticatedUserListingActionButtonTest extends ListingActionButtonTest {
	
	@Test
	public void inactiveListingForLister() {
		
		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = true;
		
		ListingActionButton button = super.createListingActionButton( createInactiveListing( ListingType.SELLING, GoodsType.PHYSICAL ), isUserLoggedIn, isUserLister, null, null, null, null );

		Assert.assertFalse( button.isEnabled() );
		Assert.assertEquals( "Expired", button.getText() );
	}
	
	@Test
	public void inactivePhysicalHasPurchased() {

		Listing listing = super.createInactiveListing( ListingType.SELLING, GoodsType.PHYSICAL );
		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = true;
		
		ListingActionButton button = super.createListingActionButton(listing, isUserLoggedIn, isUserLister, null, hasUserPaidForThisItem, null, null );

		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Purchase Complete", button.getText() );
		Assert.assertEquals( ListingActionButton.TRANSACTION_DETAIL_MODE, button.getActionMode() );
	}
	
	@Test
	public void inactivePhysicalNotPurchased() {
		Listing listing = super.createInactiveListing( ListingType.SELLING, GoodsType.PHYSICAL );
		
		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = false;
		
		ListingActionButton button = super.createListingActionButton(listing, isUserLoggedIn, isUserLister, null, hasUserPaidForThisItem, null, null );

		Assert.assertFalse( button.isEnabled() );
		Assert.assertEquals( "Expired", button.getText() );
		Assert.assertEquals( ListingActionButton.UNDEFINED_MODE, button.getActionMode() );
	}
	
	@Test
	public void inactiveDonationNotPurchased() {
		Listing listing = super.createInactiveListing( ListingType.DONATION, null );
		
		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = false;
		
		ListingActionButton button = super.createListingActionButton(listing, isUserLoggedIn, isUserLister, null, hasUserPaidForThisItem, null, null );

		Assert.assertFalse( button.isEnabled() );
		Assert.assertEquals( "Expired", button.getText() );
		Assert.assertEquals( ListingActionButton.UNDEFINED_MODE, button.getActionMode()  );
	}
	
	@Test
	public void inactiveDigitalSaleNotPurchased() {
		Listing listing = super.createInactiveListing( ListingType.SELLING, GoodsType.DIGITAL );
		
		Set<ContentFile> contentFiles = new HashSet<ContentFile>();
		ContentFile file = new ContentFile();
		file.setDigitalContentOriginalFilename( "champagne.jpeg");
		contentFiles.add( file );
		listing.setContentFiles( contentFiles );

		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = false;
		
		ListingActionButton button = super.createListingActionButton(listing, isUserLoggedIn, isUserLister, null, hasUserPaidForThisItem, null, null );
		

		Assert.assertFalse( button.isEnabled() );
		Assert.assertEquals( "Expired", button.getText() );
		Assert.assertEquals( ListingActionButton.UNDEFINED_MODE, button.getActionMode()  );
	}
	
	@Test
	public void inactiveDigitalCampaignNotPurchased() {
		Listing listing = super.createInactiveListing( ListingType.CAMPAIGN, null );
		
		Set<ContentFile> contentFiles = new HashSet<ContentFile>();
		ContentFile file = new ContentFile();
		file.setDigitalContentOriginalFilename( "champagne.jpeg");
		contentFiles.add( file );
		listing.setContentFiles( contentFiles );

		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = false;
		
		ListingActionButton button = super.createListingActionButton(listing, isUserLoggedIn, isUserLister, null, hasUserPaidForThisItem, null, null );

		Assert.assertFalse( button.isEnabled() );
		Assert.assertEquals( "Expired", button.getText() );
		Assert.assertEquals( ListingActionButton.UNDEFINED_MODE, button.getActionMode()  );
	}

	@Test
	public void inactiveDigitalSaleHasPurchased() {

		Listing listing = super.createInactiveListing( ListingType.SELLING, GoodsType.DIGITAL );
		
		Set<ContentFile> contentFiles = new HashSet<ContentFile>();
		ContentFile file = new ContentFile();
		file.setDigitalContentOriginalFilename( "champagne.jpeg");
		contentFiles.add( file );
		listing.setContentFiles( contentFiles );

		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = true;
		
		ListingActionButton button = super.createListingActionButton(listing, isUserLoggedIn, isUserLister, null, hasUserPaidForThisItem, null, null );

		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Purchase Complete", button.getText() );
		Assert.assertEquals( ListingActionButton.TRANSACTION_DETAIL_MODE, button.getActionMode() );
	}

	@Test
	public void inactiveDigitalCampaignHasPurchased() {

		Listing listing = super.createInactiveListing( ListingType.CAMPAIGN, null );
		
		Set<ContentFile> contentFiles = new HashSet<ContentFile>();
		ContentFile file = new ContentFile();
		file.setDigitalContentOriginalFilename( "champagne.jpeg");
		contentFiles.add( file );
		listing.setContentFiles( contentFiles );

		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = true;
		
		ListingActionButton button = super.createListingActionButton(listing, isUserLoggedIn, isUserLister, null, hasUserPaidForThisItem, null, null );
		
		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "You Won!", button.getText() );
		Assert.assertEquals( ListingActionButton.TRANSACTION_DETAIL_MODE, button.getActionMode() );
	}

	
	@Test
	public void inactiveListingHasNotPurchased() {
		Listing listing = super.createInactiveListing( null, null );
		
		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = false;
		
		ListingActionButton button = super.createListingActionButton(listing, isUserLoggedIn, isUserLister, null, hasUserPaidForThisItem, null, null );

		Assert.assertFalse( button.isEnabled() );
	}
	
	@Test
	public void activePhysicalSaleHasPurchased() {
		Listing listing = super.createFutureExpiryActiveListing( ListingType.SELLING, GoodsType.PHYSICAL );
		
		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = true;
		
		ListingActionButton button = super.createListingActionButton(listing, isUserLoggedIn, isUserLister, null, hasUserPaidForThisItem, null, null );

		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Purchase Complete", button.getText() );
		Assert.assertEquals( ListingActionButton.TRANSACTION_DETAIL_MODE, button.getActionMode()  );
	}

	@Test
	public void activePhysicalNotPurchased() {
		
		Listing listing = super.createFutureExpiryActiveListing( ListingType.SELLING, GoodsType.PHYSICAL );
		listing.setAmount( new BigDecimal( 1.50 ));
		
		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = false;
		
		ListingActionButton button = super.createListingActionButton(listing, isUserLoggedIn, isUserLister, null, hasUserPaidForThisItem, null, null );

		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Purchase for $1.50 via @ieeiee", button.getText() );
		Assert.assertEquals( ListingActionButton.COMMENT_MODE, button.getActionMode() );
	}
	
	@Test
	public void activePhysicalSalePendingPurchase() {
		
		Listing listing = super.createFutureExpiryActiveListing( ListingType.SELLING, GoodsType.PHYSICAL );
		listing.setAmount( new BigDecimal( 1.50 ));
		
		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = false;
		Boolean doesUserHavePendingPurchase = true;

		
		ListingActionButton button = super.createListingActionButton( listing, isUserLoggedIn, isUserLister, doesUserHavePendingPurchase, hasUserPaidForThisItem, null, null );

		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Proceed to Checkout", button.getText() );
		Assert.assertEquals( ListingActionButton.START_CHECKOUT_MODE, button.getActionMode() );
	}
	
	@Test
	public void activeDonationNotPurchased() {
		Listing listing = super.createFutureExpiryActiveListing( ListingType.DONATION, null );
		listing.setAmount( new BigDecimal( 1.50 ));
		
		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = false;
		
		ListingActionButton button = super.createListingActionButton( listing, isUserLoggedIn, isUserLister, null, hasUserPaidForThisItem, null, null );

		
		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Give $1.50 via @ieeiee", button.getText() );
		Assert.assertEquals( ListingActionButton.COMMENT_MODE, button.getActionMode() );
	}
	
	@Test
	public void activeDonationPendingPurchase() {
		Listing listing = super.createFutureExpiryActiveListing( ListingType.DONATION, null );
		listing.setAmount( new BigDecimal( 1.50 ));
		
		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = false;
		Boolean doesUserHavePendingPurchase = true;
		
		ListingActionButton button = super.createListingActionButton( listing, isUserLoggedIn, isUserLister, doesUserHavePendingPurchase, hasUserPaidForThisItem, null, null );
		
		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Proceed to Checkout", button.getText() );
		Assert.assertEquals( ListingActionButton.START_CHECKOUT_MODE, button.getActionMode() );
	}
	
	@Test
	public void activeDonationHasPurchased() {
		Listing listing = super.createFutureExpiryActiveListing( ListingType.DONATION, null );
		listing.setAmount( new BigDecimal( 1.50 ));
		
		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = true;
		
		ListingActionButton button = super.createListingActionButton( listing, isUserLoggedIn, isUserLister, null, hasUserPaidForThisItem, null, null );
		
		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Donation Complete", button.getText() );
		Assert.assertEquals( ListingActionButton.TRANSACTION_DETAIL_MODE, button.getActionMode()  );
	}
	
	@Test
	public void activeDigitalSaleNotPurchased() {
		Listing listing = super.createFutureExpiryActiveListing( ListingType.SELLING, GoodsType.DIGITAL );
		listing.setAmount( new BigDecimal( 1.50 ));
		
		Set<ContentFile> contentFiles = new HashSet<ContentFile>();
		ContentFile file = new ContentFile();
		file.setDigitalContentOriginalFilename( "champagne.jpeg");
		contentFiles.add( file );
		listing.setContentFiles( contentFiles );
		
		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = false;
		
		ListingActionButton button = super.createListingActionButton( listing, isUserLoggedIn, isUserLister, null, hasUserPaidForThisItem, null, null );
		
		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Purchase for $1.50 via @ieeiee", button.getText() );
		Assert.assertEquals( ListingActionButton.COMMENT_MODE, button.getActionMode() );
	}
	
	@Test
	public void activeDigitalSalePendingPurchase() {
		Listing listing = super.createFutureExpiryActiveListing( ListingType.SELLING, GoodsType.DIGITAL );
		listing.setAmount( new BigDecimal( 1.50 ));
		
		Set<ContentFile> contentFiles = new HashSet<ContentFile>();
		ContentFile file = new ContentFile();
		file.setDigitalContentOriginalFilename( "champagne.jpeg");
		contentFiles.add( file );
		listing.setContentFiles( contentFiles );

		
		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = false;
		Boolean doesUserHavePendingPurchase = true;
		
		ListingActionButton button = super.createListingActionButton( listing, isUserLoggedIn, isUserLister, doesUserHavePendingPurchase, hasUserPaidForThisItem, null, null );
		
		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Proceed to Checkout", button.getText() );
		Assert.assertEquals( ListingActionButton.START_CHECKOUT_MODE, button.getActionMode() );
	}

	@Test
	public void activeDigitalSaleHasPurchased() {
		Listing listing = super.createFutureExpiryActiveListing( ListingType.SELLING, GoodsType.DIGITAL );
		listing.setAmount( new BigDecimal( 1.50 ));
		
		Set<ContentFile> contentFiles = new HashSet<ContentFile>();
		ContentFile file = new ContentFile();
		file.setDigitalContentOriginalFilename( "champagne.jpeg");
		contentFiles.add( file );
		listing.setContentFiles( contentFiles );
		
		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = true;

		ListingActionButton button = super.createListingActionButton( listing, isUserLoggedIn, isUserLister, null, hasUserPaidForThisItem, null, null );
		
		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Purchase Complete", button.getText() );
		Assert.assertEquals( ListingActionButton.TRANSACTION_DETAIL_MODE, button.getActionMode() );
	}


	/**
	 * Special test for the case if a user clicks through an old tweet "go here to complete..."
	 * Want to make sure that, since the user has bought, the "Download Files" text trumps
	 * the "Proceed to Checkout" text.
	 */
	@Test
	public void activeDigitalSaleHasPurchasedPendingPurchaseReferrer() {
		Listing listing = super.createFutureExpiryActiveListing( ListingType.SELLING, GoodsType.DIGITAL );
		listing.setAmount( new BigDecimal( 1.50 ));
		
		Set<ContentFile> contentFiles = new HashSet<ContentFile>();
		ContentFile file = new ContentFile();
		file.setDigitalContentOriginalFilename( "champagne.jpeg");
		contentFiles.add( file );
		listing.setContentFiles( contentFiles );
		
		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = true;
		Boolean isUserReferredByPendingPurchaseLink = true;
		
		ListingActionButton button = super.createListingActionButton( listing, isUserLoggedIn, isUserLister, null, hasUserPaidForThisItem, isUserReferredByPendingPurchaseLink, null );
		
		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Purchase Complete", button.getText() );
		Assert.assertEquals( ListingActionButton.TRANSACTION_DETAIL_MODE, button.getActionMode() );
	}
	
	@Test
	public void activeDigitalCampaignNotPurchased() {
		Listing listing = super.createFutureExpiryActiveListing( ListingType.CAMPAIGN, null );
		
		Set<ContentFile> contentFiles = new HashSet<ContentFile>();
		ContentFile file = new ContentFile();
		file.setDigitalContentOriginalFilename( "champagne.jpeg");
		contentFiles.add( file );
		listing.setContentFiles( contentFiles );
		
		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = false;
		
		ListingActionButton button = super.createListingActionButton( listing, isUserLoggedIn, isUserLister, null, hasUserPaidForThisItem, null, null );
		
		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Enter Giveaway", button.getText() );
		Assert.assertEquals( ListingActionButton.COMMENT_MODE, button.getActionMode() );
	}
	
	@Test
	public void activePhysicalCampaignNotPurchased() {
		Listing listing = super.createFutureExpiryActiveListing( ListingType.CAMPAIGN, null );
		
		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = false;
		
		ListingActionButton button = super.createListingActionButton( listing, isUserLoggedIn, isUserLister, null, hasUserPaidForThisItem, null, null );
		
		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Enter Giveaway", button.getText() );
		Assert.assertEquals( ListingActionButton.COMMENT_MODE, button.getActionMode() );
	}
	
	@Test
	public void activeSellingComplexInventoryListingPreCheckout() {
		
		Listing listing = super.createFutureExpiryActiveListing( ListingType.SELLING, GoodsType.PHYSICAL );
		super.configureComplexInventory( listing );

		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = false;
		Boolean doesUserHavePendingPurchase = true;
		Boolean hasChosenInventoryAndQuantity = false;
		
		ListingActionButton button = super.createListingActionButton( listing, isUserLoggedIn, isUserLister, doesUserHavePendingPurchase, hasUserPaidForThisItem, null, hasChosenInventoryAndQuantity );
		
		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Pre Checkout", button.getText() );
		Assert.assertEquals( ListingActionButton.PRE_CHECKOUT_MODE, button.getActionMode() );
		
	}
	
	@Test
	public void activeSellingComplexInventoryListingCheckout() {
		
		Listing listing = super.createFutureExpiryActiveListing( ListingType.SELLING, GoodsType.PHYSICAL );
		super.configureComplexInventory( listing );

		Boolean isUserLoggedIn = true; 
		Boolean isUserLister = false;
		Boolean hasUserPaidForThisItem = false;
		Boolean doesUserHavePendingPurchase = true;
		Boolean hasChosenInventoryAndQuantity = true;
		
		ListingActionButton button = super.createListingActionButton( listing, isUserLoggedIn, isUserLister, doesUserHavePendingPurchase, hasUserPaidForThisItem, null, hasChosenInventoryAndQuantity );
		
		Assert.assertTrue( button.isEnabled() );
		Assert.assertEquals( "Proceed to Checkout", button.getText() );
		Assert.assertEquals( ListingActionButton.START_CHECKOUT_MODE, button.getActionMode() );
		
	}

}
