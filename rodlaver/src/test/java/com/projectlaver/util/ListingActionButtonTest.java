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
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.junit.BeforeClass;
import org.springframework.context.MessageSource;
import org.springframework.context.support.StaticMessageSource;

import com.projectlaver.domain.Inventory;
import com.projectlaver.domain.Listing;

public abstract class ListingActionButtonTest {

	private static MessageSource messageSource = null;
	
	@BeforeClass
	public static void initializeMessageSource() {
		
		System.out.println( "Initializing message source." );
		
		StaticMessageSource ms = new StaticMessageSource();
		
		ms.addMessage( "sale.detail.enter.intent.button", Locale.US, "Enter Giveaway" );
		ms.addMessage( "sale.detail.buy.intent.button", Locale.US, "Purchase for {0} via @ieeiee" );
		ms.addMessage( "sale.detail.finish.purchase.button", Locale.US, "Complete your {0} Purchase" );
		ms.addMessage( "sale.detail.checkout.listing.button", Locale.US, "Proceed to Checkout" );
		ms.addMessage( "sale.detail.pre-checkout.listing.button", Locale.US, "Pre Checkout" );
		ms.addMessage( "sale.detail.donate.intent.button", Locale.US, "Give {0} via @ieeiee" );
		ms.addMessage( "sale.detail.finish.donation.button", Locale.US, "Complete your {0} Donation" );
		ms.addMessage( "sale.detail.expired.listing.button", Locale.US, "Expired" );
		ms.addMessage( "sale.detail.download.intent.button", Locale.US, "Download File" );
		ms.addMessage( "sale.detail.won.giveaway.button", Locale.US, "You Won!" );
		ms.addMessage( "sale.detail.donation.complete.button", Locale.US, "Donation Complete" );
		ms.addMessage( "sale.detail.purchase.complete.button", Locale.US, "Purchase Complete" );

		ListingActionButtonTest.messageSource = ms;
	}
	
	protected Listing createFutureExpiryActiveListing( ListingType listingType, GoodsType goodsType ) {
		Listing listing = new Listing();
		listing.setStatus( ListingStatus.ACTIVE );
		listing.setType( listingType );
		listing.setGoodsType( goodsType );
		
		// by default
		listing.setHasSingleInventory( true );
		
		// Set expires on the following day
		Calendar expires = Calendar.getInstance();
		expires.add( Calendar.DATE, 1 );
		listing.setExpires( expires.getTime() );

		return listing;
	}
	
	protected Listing createPastExpiryActiveListing(ListingType listingType, GoodsType goodsType ) {
		Listing listing = new Listing();
		listing.setStatus( ListingStatus.ACTIVE );
		listing.setType( listingType );
		listing.setGoodsType( goodsType );
		
		// Set expires on the following day
		Calendar expires = Calendar.getInstance();
		expires.add( Calendar.DATE, -1 );
		listing.setExpires( expires.getTime() );

		return listing;
	}
	
	protected Listing createInactiveListing(ListingType listingType, GoodsType goodsType ) {
		Listing listing = new Listing();
		listing.setStatus( ListingStatus.CANCELED );
		listing.setType( listingType );
		listing.setGoodsType( goodsType );
		
		return listing;
	}
	
	protected void configureComplexInventory( Listing listing ) {
		listing.setHasSingleInventory( false );
		
		Inventory small = new Inventory();
		small.setProductCode( "SM" );
		small.setDescription( "Small" );
		small.setQuantity( 10 );
		small.setRemainingQuantity( 5 );
		
		Inventory medium = new Inventory();
		medium.setProductCode( "MED" );
		medium.setDescription( "Medium" );
		medium.setQuantity( 3 );
		medium.setRemainingQuantity( 0 );
		
		List<Inventory> inventories = new ArrayList<Inventory>();
		inventories.add( small );
		inventories.add( medium );
		
		listing.setInventories( inventories );
	}
	
	protected MessageSource getMessageSource() {
		return ListingActionButtonTest.messageSource;
	}
	
	protected ListingActionButton createListingActionButton( 
			Listing listing, 
			Boolean isUserLoggedIn, 
			Boolean isUserLister, 
			Boolean doesUserHavePendingPurchase, 
			Boolean hasUserPaidForThisItem, 
			Boolean isUserReferredByPendingPurchaseLink,
			Boolean hasChosenInventoryAndQuantity ) {
		
		ListingActionButton button = new ListingActionButton( 
				this.getMessageSource(),
				Locale.US, 
				listing,
				isUserLoggedIn, 
				isUserLister,
				doesUserHavePendingPurchase, 
				hasUserPaidForThisItem,
				isUserReferredByPendingPurchaseLink,
				hasChosenInventoryAndQuantity );
		
		return button;
	}
}
