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

import java.util.Locale;

import org.springframework.context.MessageSource;

import com.projectlaver.domain.Listing;

public class ListingActionButton {

	private MessageSource messageSource;
	private Locale locale;
	private Listing listing;
	private boolean isUserLoggedIn;
	private boolean isUserLister;
	private boolean doesUserHavePendingPurchase;
	private boolean hasUserPaidForThisItem;
	private Long paymentId;
	private boolean isUserReferredByPendingPurchaseLink;
	private boolean hasChosenInventoryAndQuantity;
	
	public static final String UNDEFINED_MODE		   = "";
	public static final String PRE_CHECKOUT_MODE	   = "PRE_CHECKOUT";
	public static final String START_CHECKOUT_MODE	   = "START_CHECKOUT";
	public static final String COMMENT_MODE			   = "COMMENT";
	public static final String TRANSACTION_DETAIL_MODE = "TRANSACTION_DETAIL";


	public ListingActionButton() {}
	
	public ListingActionButton( 
			MessageSource messageSource, 
			Locale locale, 
			Listing listing, 
			Boolean isUserLoggedIn, 
			Boolean isUserLister, 
			Boolean doesUserHavePendingPurchase, 
			Boolean hasUserPaidForThisItem, 
			Boolean isUserReferredByPendingPurchaseLink,
			Boolean hasChosenInventoryAndQuantity ) {
		
		this.messageSource = messageSource;
		this.locale = locale;
		this.listing = listing;
		this.isUserLoggedIn = (isUserLoggedIn == null) ? false : isUserLoggedIn;
		this.isUserLister = (isUserLister == null) ? false : isUserLister;
		this.doesUserHavePendingPurchase = (doesUserHavePendingPurchase == null) ? false : doesUserHavePendingPurchase;
		this.hasUserPaidForThisItem = (hasUserPaidForThisItem == null) ? false : hasUserPaidForThisItem;
		this.isUserReferredByPendingPurchaseLink = (isUserReferredByPendingPurchaseLink == null) ? false : isUserReferredByPendingPurchaseLink;
		this.hasChosenInventoryAndQuantity = (hasChosenInventoryAndQuantity == null ) ? false : hasChosenInventoryAndQuantity;
		
	}
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setListing(Listing listing) {
		this.listing = listing;
	}

	public void setIsUserLoggedIn(Boolean isUserLoggedIn) {
		this.isUserLoggedIn = isUserLoggedIn;
	}

	public void setIsUserLister(Boolean isUserLister) {
		this.isUserLister = isUserLister;
	}

	public void setHasUserPaidForThisItem(Boolean hasUserPaidForThisItem) {
		this.hasUserPaidForThisItem = hasUserPaidForThisItem;
	}
	
	public Boolean getHasUserPaidForThisItem() {
		return this.hasUserPaidForThisItem;
	}
	
	public void setDoesUserHavePendingPurchase( Boolean doesUserHavePendingPurchase ) {
		this.doesUserHavePendingPurchase = doesUserHavePendingPurchase;
	}
	
	public Boolean getDoesUserHavePendingPurchase() {
		return this.doesUserHavePendingPurchase;
	}
	
	public void setIsUserReferredByPendingPurchaseLink( Boolean isUserReferredByPendingPurchaseLink ) { 
		this.isUserReferredByPendingPurchaseLink = isUserReferredByPendingPurchaseLink;
	}
	
	public Boolean isHasChosenInventoryAndQuantity() {
		return hasChosenInventoryAndQuantity;
	}

	public void setHasChosenInventoryAndQuantity( Boolean hasChosenInventoryAndQuantity) {
		this.hasChosenInventoryAndQuantity = hasChosenInventoryAndQuantity;
	}
	
	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getText() {
		String buttonText = null;

		// anonymous users
		if( !this.isUserLoggedIn ) {
			if( this.isUserReferredByPendingPurchaseLink && this.isListingActive() ) {
				buttonText = this.messageSource.getMessage( "sale.detail.checkout.listing.button", null, locale );
			} else {
				buttonText = this.getTextActiveUnpurchasedListing();
			}
		// authenticated users
		} else {
			// Authenticated user & inactive listing that the user hasn't bought
			if( !this.isListingActive() && !this.hasUserPaidForThisItem ) {
				buttonText = this.messageSource.getMessage( "sale.detail.expired.listing.button", null, locale );
			// Authenticated user that has a pending purchase message
			} else if( this.doesUserHavePendingPurchase ) {
				buttonText = this.getTextForPendingPayment();
			// Authenticated user & active listing that the user hasn't started or completed a purchase
			} else if( !this.hasUserPaidForThisItem ) {
				buttonText = this.getTextActiveUnpurchasedListing();
			// Authenticated user & active listing that the user has bought
			} else if( this.hasUserPaidForThisItem ) {
				buttonText = this.getPurchaseCompleteButtonText();
			}
		}


		return buttonText;
	}

	private String getPurchaseCompleteButtonText() {
		
		String buttonText;
		
		// Payment Complete / Donation Complete / Won Giveaway
		if( this.listing.getType().equals( ListingType.CAMPAIGN ) ) {
			buttonText = this.messageSource.getMessage( "sale.detail.won.giveaway.button", null, locale );
		} else if ( this.listing.getType().equals( ListingType.DONATION ) ) {
			buttonText = this.messageSource.getMessage( "sale.detail.donation.complete.button", null, locale );
		} else {
			buttonText = this.messageSource.getMessage( "sale.detail.purchase.complete.button", null, locale );
		}
			
		return buttonText;
	}

	public Boolean isEnabled() {
		Boolean result = false;

		// If the user is not logged in
		if( !this.isUserLoggedIn ) {
			result = this.isListingActive();
			
		} else if( this.isUserLoggedIn ) {
			// Authenticated user who created this listing
			if( this.isUserLister ) {
				result = false;
			// Authenticated user where listing is INACTIVE and user hasn't bought this item
			} else if( !this.isListingActive() && !this.hasUserPaidForThisItem  ) {
				result = false;
			// Authenticated user who bought this item
			} else if( this.hasUserPaidForThisItem ) {
				result = true;

			// Active listing but unpurchased
			} else if( this.isListingActive() ) {

				result = true;
				
			}
		}

		return result;
	}

	public String getActionMode() {
		String buttonActionMode = ListingActionButton.UNDEFINED_MODE;

		if( this.isEnabled() ) {
			// if the button is active but the user isn't logged in or hasn't paid for (or won) the item, go to comment mode
			if( !this.isUserLoggedIn || (!this.hasUserPaidForThisItem && !this.doesUserHavePendingPurchase ) ) {
				buttonActionMode = ListingActionButton.COMMENT_MODE;
			} else if( !this.hasUserPaidForThisItem && this.doesUserHavePendingPurchase ) {
				
				if( this.listing.getHasSingleInventory() || this.hasChosenInventoryAndQuantity ) {
					buttonActionMode = ListingActionButton.START_CHECKOUT_MODE;
				} else {
					buttonActionMode = ListingActionButton.PRE_CHECKOUT_MODE;
				}
			} else {
				// User is logged in and has purchased
				buttonActionMode = ListingActionButton.TRANSACTION_DETAIL_MODE;
			}
		}
		return buttonActionMode;
	}

	Boolean isListingActive() {
		Boolean result = false;

		if( this.listing.getStatus().equals( ListingStatus.PENDING ) ||
			this.listing.getStatus().equals( ListingStatus.ACTIVE ) ) {
			result = true;
		}

		return result;
	}

	Boolean doesListingHaveDigitalContent() {
		return ( this.listing.getContentFiles() != null && this.listing.getContentFiles().size() > 0 );
	}

	String getTextActiveUnpurchasedListing() {
		String result = null;

		if( this.listing.getType().equals( ListingType.CAMPAIGN ) ) {
			// setup a button that says "enter giveaway"
			result = this.messageSource.getMessage( "sale.detail.enter.intent.button", null, locale );
		} else {
			String actionButtonMessageKey = null;
			if( listing.getType().equals( ListingType.DONATION ) ) {
				// setup a button that says "donate $x.xx"
				actionButtonMessageKey = "sale.detail.donate.intent.button";
			} else {
				// setup a button that says "buy for $x.xx"
				actionButtonMessageKey = "sale.detail.buy.intent.button";
			}
			String formattedPrice = String.format( "$%.2f", listing.getAmount() );
			result = this.messageSource.getMessage( actionButtonMessageKey, new Object[] { formattedPrice }, locale );
		}

		return result;
	}
	
	String getTextForPendingPayment() {
		
		String result = "";
		
		if( this.listing.getHasSingleInventory() || this.hasChosenInventoryAndQuantity ) {
			result = this.messageSource.getMessage( "sale.detail.checkout.listing.button", null, locale );
		} else {
			result = this.messageSource.getMessage( "sale.detail.pre-checkout.listing.button", null, locale );
		}
		
		return result;
	}

}
