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

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import com.ibm.icu.text.BreakIterator;
import com.projectlaver.integration.SocialProviders;
import com.projectlaver.util.GoodsType;
import com.projectlaver.util.ListingStatus;
import com.projectlaver.util.ListingSubType;
import com.projectlaver.util.ListingType;

@Entity(name="Listings")
@Access(AccessType.FIELD)
public class Listing extends AbstractVersionedEntity {

	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	private User seller;

	@Enumerated(EnumType.STRING)
	private ListingType type;
	
	@Enumerated(EnumType.STRING)
	private GoodsType goodsType;
	
	@Column(nullable=true)
	@Enumerated(EnumType.STRING)
	private ListingSubType subType;

	@Column
	private String title;

	@Column
	private String longDescription;
	
	@Column(columnDefinition="TEXT")
	private String termsAndConditions;

	@Column(length=512)
	private String backgroundImageUrl;

	@Column(precision=15, scale=4)
	private BigDecimal amount;
	
	@Column(precision=6, scale=5)
	private BigDecimal sellerRevenueRatio;

	@Column
	private String currencyCode;

	@Enumerated(EnumType.STRING)
	private ListingStatus status;

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expires", nullable = false)
    private Date expires;
	
	// TODO figure out a way to avoid duplicate storage of identical content
	@OneToMany(mappedBy="listing", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	private Set<ContentFile> contentFiles;

	/**
	 * Start listing image section
	 */
	
	@Column(unique=true)
	private String imageFilename;

	@Column
	private Integer imageWidth;

	@Column
	private Integer imageHeight;

	@Transient
	private String imageTransientPath;
	
	/**
	 * End listing image section
	 */
	
	@OneToMany(mappedBy="listing", fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE } )
	private Set<Message> messages;

	@OneToMany(mappedBy="listing", fetch=FetchType.LAZY)
	private Set<Payment> payments;

	@Column
	private Locale locale;

	@Column
	private String keyword;

	@Column
	private Integer giveawaySeed;
	
	@Column
	private String announcementPreamble;
	
	@Column
	private Boolean doPostToTwitter;
	
	@Column
	private Boolean doPostToFacebook;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="listing", cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
	private List<Inventory> inventories;
	
	@Column(name="has_single_inventory")
	private Boolean hasSingleInventory;
	
	@Column(name="max_qty_per_purchase")
	private Integer maxQtyPerPurchase;

	public Listing() {
		super();

		// new Listing objects have ACTIVE status
		this.status = ListingStatus.ACTIVE;

		// default the currency code
		this.currencyCode = "USD";
	}


	/**
	 * Moved the annotations to the method to allow the ID to be fetched on a
	 * List page without querying the database for the entire Listing object
	 * until the user requests the Detail page.
	 *
	 */
	@Access(AccessType.PROPERTY)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public ListingType getType() {
		return type;
	}

	public void setType( ListingType type) {
		this.type = type;
	}
	
	public GoodsType getGoodsType() {
		return this.goodsType;
	}
	
	public void setGoodsType( GoodsType goodsType ) {
		this.goodsType = goodsType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setTitleWithUnsanitized( String unsanitizedTitle ) {
		this.title = Jsoup.clean( unsanitizedTitle, Whitelist.none() );
	}

	public BigDecimal getAmount() {
		return amount;
	}
	
	public Integer getAmountCents() {
		Integer result = null;
		
		if( this.amount != null ) {
			BigDecimal scaled = this.amount.scaleByPowerOfTen( 2 );
			BigDecimal rounded = scaled.setScale( 0, RoundingMode.HALF_UP );
			result = rounded.intValue();
		}
		
		return result;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getImageFilename() {
		return imageFilename;
	}

	public void setImageFilename(String imageFilename) {
		this.imageFilename = imageFilename;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public Set<Payment> getPayments() {
		return payments;
	}

	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	public ListingStatus getStatus() {
		return status;
	}

	public void setStatus(ListingStatus status) {
		this.status = status;
	}

	public Integer getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	public Integer getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(Integer imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Inventory getDefaultProductInventory() {
		return this.getInventory( Inventory.DEFAULT_PRODUCT_CODE );
	}
	
	public Inventory getInventory( String productCode ) {
		Inventory result = null;
		
		if( this.inventories != null && this.inventories.size() > 0 ) {
			for( Inventory inventory : this.inventories ) {
				if( StringUtils.equalsIgnoreCase( productCode, inventory.getProductCode() )) {
					result = inventory;
					break;
				} 
			}
		}

		if( result == null ) {
			throw new RuntimeException( "No match for input productCode: " + productCode );
		}
		
		return result;
	}
	
	public List<Inventory> getInventories() {
		return inventories;
	}

	public void setInventories(List<Inventory> inventories) {
		this.inventories = inventories;
	}
	
	public void setHasSingleInventory( Boolean hasSingleInventory ) {
		this.hasSingleInventory = hasSingleInventory;
	}
	
	public Boolean getHasSingleInventory() {
		return this.hasSingleInventory;
	}
	
	public void setMaxQtyPerPurchase( Integer maxQtyPerPurchase ) {
		this.maxQtyPerPurchase = maxQtyPerPurchase;
	}
	
	public Integer getMaxQtyPerPurchase() {
		return this.maxQtyPerPurchase;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public String getLongDescription() {
		return this.longDescription;
	}

	public void setLongDescription( String longDescription ) {
		this.longDescription = longDescription;
	}
	
	public void setLongDescriptionWithUnsanitized( String longDescriptionUnsanitized ) {
		this.longDescription = Jsoup.clean( longDescriptionUnsanitized, Whitelist.none() );
	}
	
	public String getTermsAndConditions() {
		return this.termsAndConditions;
	}
	
	public void setTermsAndConditions( String termsAndConditions ) {
		this.termsAndConditions = termsAndConditions;
	}
	
	public void setTermsAndConditionsWithUnsanitized( String termsAndConditionsUnsanitized ) {
		this.termsAndConditions = Jsoup.clean( termsAndConditionsUnsanitized, Whitelist.basic() );
	}

	public String getBackgroundImageUrl() {
		return this.backgroundImageUrl;
	}

	public void setBackgroundImageUrl( String backgroundImageUrl ) {
		this.backgroundImageUrl = backgroundImageUrl;
	}
	
	public void setBackgroundImageUrlWithUnsanitized( String backgroundImageUrlUnsanitized ) {
		this.backgroundImageUrl = Jsoup.clean( backgroundImageUrlUnsanitized, Whitelist.none() );
	}

	public String getImageTransientPath() {
		return imageTransientPath;
	}

	public void setImageTransientPath(String imageTransientPath) {
		this.imageTransientPath = imageTransientPath;
	}

	public File getImageAsFile() throws IOException {
		return new File( this.imageTransientPath );
	}

	public void setGiveawaySeed( Integer giveawaySeed ) {
		this.giveawaySeed = giveawaySeed;
	}

	public Integer getGiveawaySeed() {
		return this.giveawaySeed;
	}
	
	public String getAnnouncementPreamble() {
		return this.announcementPreamble;
	}
	
	public void setAnnouncementPreamble( String announcementPreamble ) {
		this.announcementPreamble = announcementPreamble;
	}
	
	public void setAnnouncementPreambleWithUnsanitized( String announcementPreambleUnsanitized ) {
		this.announcementPreamble = Jsoup.clean( announcementPreambleUnsanitized, Whitelist.none() );
	}
	
	public void setPublishTo( List<String> providers ) {
		if( providers != null ) {
			this.doPostToFacebook = providers.contains( SocialProviders.FACEBOOK );
			this.doPostToTwitter = providers.contains( SocialProviders.TWITTER );
		}

	}
	
	public Boolean getDoPostToTwitter() {
		return doPostToTwitter;
	}

	public void setDoPostToTwitter(Boolean doPostToTwitter) {
		this.doPostToTwitter = doPostToTwitter;
	}

	public Boolean getDoPostToFacebook() {
		return doPostToFacebook;
	}

	public void setDoPostToFacebook(Boolean doPostToFacebook) {
		this.doPostToFacebook = doPostToFacebook;
	}

	public ListingSubType getSubType() {
		return subType;
	}

	public void setSubType(ListingSubType subType) {
		this.subType = subType;
	}
	
	public Set<ContentFile> getContentFiles() {
		return contentFiles;
	}

	public void setContentFiles(Set<ContentFile> contentFiles) {
		this.contentFiles = contentFiles;
	}

	public void cleanupFiles() {
		if( this.contentFiles != null && this.contentFiles.size() > 0 ) {
			for( ContentFile file : this.contentFiles ) {
				new File( file.getTransientPath() ).delete();
			}
		}
		
		if( this.imageTransientPath != null ) {
			new File( this.imageTransientPath ).delete();
		}
	}
	
	public BigDecimal getSellerRevenueRatio() {
		return sellerRevenueRatio;
	}

	public void setSellerRevenueRatio( BigDecimal sellerRevenueRatio ) {
		this.sellerRevenueRatio = sellerRevenueRatio ;
	}
	
	/**
	 * Get a message key that corresponds to a localized version of this listing's type. The
	 * key can be translated into the actual human-readable listing type in the UI. 
	 * 
	 * @return message key corresponding to this listing's type.
	 */
	public String getListingTypeMessageKey() {
		String result = null;
		
		if( this.type != null && this.goodsType != null ) {
			
			// Digital sale?
			if( this.type.equals( ListingType.SELLING ) ) {
				if( this.goodsType.equals( GoodsType.DIGITAL )) {
					result = "sale.detail.type.digital";
				} else if( this.goodsType.equals( GoodsType.PHYSICAL )) {
					result = "sale.detail.type.physical";
				} else {
					result = "sale.detail.type.voucher";
				}
			
			// Sweepstakes giveaway?
			} else if( this.type.equals( ListingType.CAMPAIGN ) && this.subType.equals( ListingSubType.DRAWING ) ) {
				result = "sale.detail.type.drawing";
			
			// Instant giveaway
			} else if( this.type.equals( ListingType.CAMPAIGN ) && this.subType.equals( ListingSubType.INSTANT ) ) {
				result = "sale.detail.type.instant.giveaway";
			}
		}
		
		return result;
	}
	
	/**
	 * Get a message key that corresponds to a localized version of this listing's status. The
	 * key can be translated into the actual human-readable listing type in the UI. 
	 * 
	 * @return message key corresponding to this listing's status.
	 */
	public String getListingStatusMessageKey() {
		String result = null;
		
		if( this.status != null ) {

			if( this.status.equals( ListingStatus.COMPLETED )) {
				result = "sale.detail.listing.status.completed";
			} else if( this.status.equals( ListingStatus.ACTIVE ) || this.status.equals( ListingStatus.PENDING ) ) {
				result = "sale.detail.listing.status.active";
			} else if( this.status.equals( ListingStatus.CANCELED ) ) {
				result = "sale.detail.listing.status.canceled";
			} else {
				result = "sale.detail.listing.status.expired";
			}
		}
		
		return result;
	}
	
	public Boolean isActive() {
		return this.status != null && ( this.status.equals( ListingStatus.ACTIVE ) || this.status.equals( ListingStatus.PENDING ) );
	}
	
	/**
	 * Takes the longDescription and truncates it to keep it under 200 characters.
	 * 
	 * @return the first 200 characters of the longDescription 
	 */
	public String getTruncatedLongDescription() {
		
		String result = null;
		
		if( this.longDescription != null ) {
			
			if( this.longDescription.length() <= 200 ) {
				result = this.longDescription;
			} else {
				
				// Use a BreakIterator to find the word break prior to the maximum truncated length
				BreakIterator bi = BreakIterator.getWordInstance();
				bi.setText( this.longDescription );
				
				// Find the end index
				int endIndex = bi.preceding( 198 );
				
				// Set the result to the substring with "..." appended
				result = String.format( "%s...", this.longDescription.substring( 0, endIndex ));
			}
			
		}
		
		return result;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime
				* result
				+ ((announcementPreamble == null) ? 0 : announcementPreamble
						.hashCode());
		result = prime
				* result
				+ ((backgroundImageUrl == null) ? 0 : backgroundImageUrl
						.hashCode());
		result = prime * result
				+ ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime
				* result
				+ ((doPostToFacebook == null) ? 0 : doPostToFacebook.hashCode());
		result = prime * result
				+ ((doPostToTwitter == null) ? 0 : doPostToTwitter.hashCode());
		result = prime * result + ((expires == null) ? 0 : expires.hashCode());
		result = prime * result
				+ ((giveawaySeed == null) ? 0 : giveawaySeed.hashCode());
		result = prime * result
				+ ((goodsType == null) ? 0 : goodsType.hashCode());
		result = prime
				* result
				+ ((hasSingleInventory == null) ? 0 : hasSingleInventory
						.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((imageFilename == null) ? 0 : imageFilename.hashCode());
		result = prime * result
				+ ((imageHeight == null) ? 0 : imageHeight.hashCode());
		result = prime
				* result
				+ ((imageTransientPath == null) ? 0 : imageTransientPath
						.hashCode());
		result = prime * result
				+ ((imageWidth == null) ? 0 : imageWidth.hashCode());
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result
				+ ((longDescription == null) ? 0 : longDescription.hashCode());
		result = prime
				* result
				+ ((maxQtyPerPurchase == null) ? 0 : maxQtyPerPurchase
						.hashCode());
		result = prime
				* result
				+ ((sellerRevenueRatio == null) ? 0 : sellerRevenueRatio
						.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((subType == null) ? 0 : subType.hashCode());
		result = prime
				* result
				+ ((termsAndConditions == null) ? 0 : termsAndConditions
						.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Listing other = (Listing) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (announcementPreamble == null) {
			if (other.announcementPreamble != null)
				return false;
		} else if (!announcementPreamble.equals(other.announcementPreamble))
			return false;
		if (backgroundImageUrl == null) {
			if (other.backgroundImageUrl != null)
				return false;
		} else if (!backgroundImageUrl.equals(other.backgroundImageUrl))
			return false;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (doPostToFacebook == null) {
			if (other.doPostToFacebook != null)
				return false;
		} else if (!doPostToFacebook.equals(other.doPostToFacebook))
			return false;
		if (doPostToTwitter == null) {
			if (other.doPostToTwitter != null)
				return false;
		} else if (!doPostToTwitter.equals(other.doPostToTwitter))
			return false;
		if (expires == null) {
			if (other.expires != null)
				return false;
		} else if (!expires.equals(other.expires))
			return false;
		if (giveawaySeed == null) {
			if (other.giveawaySeed != null)
				return false;
		} else if (!giveawaySeed.equals(other.giveawaySeed))
			return false;
		if (goodsType != other.goodsType)
			return false;
		if (hasSingleInventory == null) {
			if (other.hasSingleInventory != null)
				return false;
		} else if (!hasSingleInventory.equals(other.hasSingleInventory))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imageFilename == null) {
			if (other.imageFilename != null)
				return false;
		} else if (!imageFilename.equals(other.imageFilename))
			return false;
		if (imageHeight == null) {
			if (other.imageHeight != null)
				return false;
		} else if (!imageHeight.equals(other.imageHeight))
			return false;
		if (imageTransientPath == null) {
			if (other.imageTransientPath != null)
				return false;
		} else if (!imageTransientPath.equals(other.imageTransientPath))
			return false;
		if (imageWidth == null) {
			if (other.imageWidth != null)
				return false;
		} else if (!imageWidth.equals(other.imageWidth))
			return false;
		if (keyword == null) {
			if (other.keyword != null)
				return false;
		} else if (!keyword.equals(other.keyword))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (longDescription == null) {
			if (other.longDescription != null)
				return false;
		} else if (!longDescription.equals(other.longDescription))
			return false;
		if (maxQtyPerPurchase == null) {
			if (other.maxQtyPerPurchase != null)
				return false;
		} else if (!maxQtyPerPurchase.equals(other.maxQtyPerPurchase))
			return false;
		if (sellerRevenueRatio == null) {
			if (other.sellerRevenueRatio != null)
				return false;
		} else if (!sellerRevenueRatio.equals(other.sellerRevenueRatio))
			return false;
		if (status != other.status)
			return false;
		if (subType != other.subType)
			return false;
		if (termsAndConditions == null) {
			if (other.termsAndConditions != null)
				return false;
		} else if (!termsAndConditions.equals(other.termsAndConditions))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	

}
