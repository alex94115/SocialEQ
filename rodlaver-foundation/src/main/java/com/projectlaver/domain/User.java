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

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity(name="Users")
public class User extends AbstractVersionedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String emailAddress;
	
	@Column
	private String dob;

	@Column(unique=true)
	private String username;
	
	@Column
	private String password;
	
	@Column(name="merchant_name")
	private String merchantName;

	@Column(name="country_code")
	private String countryCode;

	@Column(name="mobile_number")
	private String mobileNumber;
	
	@Column( name="has_accepted_current_buyer_tos")
	private Boolean hasAcceptedCurrentBuyerTos;

	@Column(name="is_mobile_verified")
	private Boolean isMobileVerified;

	@Column(name="is_user_blocked")
	private Boolean isUserBlocked;
	
	@Column(name="profile_image_url")
	private String profileImageUrl;

	@Column(name="facebook_page_id")
	private String facebookPageId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_facebook_page_post_retrieved")
	private Date lastFacebookPagePostRetrievedAt;
	
	@Column(name="facebook_album_id")
	private String facebookAlbumId;
	
	@Column(name="facebook_page_url")
	private String facebookPageUrl;
	
	@Column(name="do_use_chained_pmts")
	private Boolean doUseChainedPayments;
	
	@Column(name="voucher_logo_image_url")
	private String voucherLogoImageUrl;
	
	@Column(name="voucher_campaign_image_url")
	private String voucherCampaignImageUrl;
	
	@Column(name="do_allow_seller_emails")
	private Boolean doAllowSellerEmails;

	@ManyToMany (fetch=FetchType.EAGER, cascade={ CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Role> roles;

	@OneToMany( mappedBy="seller", fetch=FetchType.LAZY)
	private Set<Listing> listings;

	@OneToMany( mappedBy="payer", fetch=FetchType.LAZY)
	private Set<Payment> paymentsMade;

	@OneToMany( mappedBy="payee", fetch=FetchType.LAZY)
	private Set<Payment> paymentsReceived;

	@OneToMany( mappedBy="user", fetch=FetchType.LAZY)
	private Set<Preapproval> preapprovals;

	@OneToMany( mappedBy="user", fetch=FetchType.LAZY, cascade={ CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Address> addresses;

	@OneToMany( mappedBy="user", fetch=FetchType.LAZY)
	private Set<Message> messages;

	@ManyToMany(fetch=FetchType.LAZY, cascade={ CascadeType.PERSIST, CascadeType.MERGE})
	private Set<TermsOfService> acceptedTermsOfService;
	
	private static final String DEFAULT_PASSWORD = "hootit";


	/**
	 * Constructors
	 */

	public User() {
		super();
	}

	public User( String username, String firstName, String lastName ) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = DEFAULT_PASSWORD;
	}

	public User(String username) {
		super();
		this.username = username;
		this.password = DEFAULT_PASSWORD;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getScreenFormattedUsername() {
		String result = null;

		if( this.username != null ) {
			if( this.username.startsWith( "twitter/") ) {
				result = "@" + username.substring( 8 );
			} else if( this.username.startsWith( "facebook/") ) {
				result = username.substring( 9 );
			} else {
				throw new RuntimeException( "Unexpected username prefix: " + this.username );
			}
 		}

		return result;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles( Set<Role> roles ) {
		this.roles = roles;
	}

	public Set<Listing> getListings() {
		return listings;
	}

	public void setListings(Set<Listing> listings) {
		this.listings = listings;
	}

	public Set<Payment> getPaymentsMade() {
		return paymentsMade;
	}

	public void setPaymentsMade(Set<Payment> paymentsMade) {
		this.paymentsMade = paymentsMade;
	}

	public Set<Payment> getPaymentsReceived() {
		return paymentsReceived;
	}

	public void setPaymentsReceived(Set<Payment> paymentsReceived) {
		this.paymentsReceived = paymentsReceived;
	}

	public Set<Preapproval> getPreapprovals() {
		return preapprovals;
	}

	public void setPreapprovals(Set<Preapproval> preapprovals) {
		this.preapprovals = preapprovals;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getScreenFormattedMobileNumber() {
		StringBuffer sb = new StringBuffer( "+" );
		sb.append( this.countryCode );
		sb.append( " (" );
		sb.append( this.mobileNumber.substring( 0, 3 ));
		sb.append( ") ");
		sb.append( this.mobileNumber.substring( 3, 6 ));
		sb.append( "-" );
		sb.append( this.mobileNumber.substring( 6 ));

		return sb.toString();
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Boolean getIsMobileVerified() {
		return isMobileVerified;
	}

	public void setIsMobileVerified(Boolean isMobileVerified) {
		this.isMobileVerified = isMobileVerified;
	}

	public Boolean getIsUserBlocked() {
		return isUserBlocked;
	}

	public void setIsUserBlocked(Boolean isUserBlocked) {
		this.isUserBlocked = isUserBlocked;
	}

	public Set<TermsOfService> getAcceptedTermsOfService() {
		return acceptedTermsOfService;
	}

	public void setAcceptedTermsOfService(Set<TermsOfService> acceptedTermsOfService) {
		this.acceptedTermsOfService = acceptedTermsOfService;
	}

	public Boolean getHasAcceptedCurrentBuyerTos() {
		return hasAcceptedCurrentBuyerTos;
	}

	public void setHasAcceptedCurrentBuyerTos(Boolean hasAcceptedCurrentBuyerTos) {
		this.hasAcceptedCurrentBuyerTos = hasAcceptedCurrentBuyerTos;
	}
	
	public void setProfileImageUrl( String profileImageUrl ) {
		this.profileImageUrl = profileImageUrl;
	}
	
	public String getProfileImageUrl() {
		return this.profileImageUrl;
	}

	public String getFacebookPageId() {
		return facebookPageId;
	}

	public void setFacebookPageId(String facebookPageId) {
		this.facebookPageId = facebookPageId;
	}
	
	public Date getLastFacebookPagePostRetrievedAt() {
		return lastFacebookPagePostRetrievedAt;
	}
	
	public void setLastFacebookPagePostRetrievedAt( Date lastFacebookPagePostRetrievedAt ) {
		this.lastFacebookPagePostRetrievedAt = lastFacebookPagePostRetrievedAt;
	}

	public String getFacebookAlbumId() {
		return facebookAlbumId;
	}

	public void setFacebookAlbumId(String facebookAlbumId) {
		this.facebookAlbumId = facebookAlbumId;
	}

	public String getFacebookPageUrl() {
		return facebookPageUrl;
	}

	public void setFacebookPageUrl(String facebookPageUrl) {
		this.facebookPageUrl = facebookPageUrl;
	}

	public Boolean getDoUseChainedPayments() {
		return doUseChainedPayments;
	}

	public void setDoUseChainedPayments(Boolean doUseChainedPayments) {
		this.doUseChainedPayments = doUseChainedPayments;
	}
	
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getVoucherLogoImageUrl() {
		return voucherLogoImageUrl;
	}

	public void setVoucherLogoImageUrl(String voucherLogoImageUrl) {
		this.voucherLogoImageUrl = voucherLogoImageUrl;
	}

	public String getVoucherCampaignImageUrl() {
		return voucherCampaignImageUrl;
	}

	public void setVoucherCampaignImageUrl(String voucherCampaignImageUrl) {
		this.voucherCampaignImageUrl = voucherCampaignImageUrl;
	}
	
	public Boolean getDoAllowSellerEmails() {
		return doAllowSellerEmails;
	}

	public void setDoAllowSellerEmails(Boolean doAllowSellerEmails) {
		this.doAllowSellerEmails = doAllowSellerEmails;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (acceptedTermsOfService == null) {
			if (other.acceptedTermsOfService != null)
				return false;
		} else if (!acceptedTermsOfService.equals(other.acceptedTermsOfService))
			return false;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		if (doUseChainedPayments == null) {
			if (other.doUseChainedPayments != null)
				return false;
		} else if (!doUseChainedPayments.equals(other.doUseChainedPayments))
			return false;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (dob == null) {
			if (other.dob != null)
				return false;
		} else if (!dob.equals(other.dob))
			return false;
		if (facebookAlbumId == null) {
			if (other.facebookAlbumId != null)
				return false;
		} else if (!facebookAlbumId.equals(other.facebookAlbumId))
			return false;
		if (profileImageUrl == null) {
			if (other.profileImageUrl != null)
				return false;
		} else if (!profileImageUrl.equals(other.profileImageUrl))
			return false;
		if (facebookPageId == null) {
			if (other.facebookPageId != null)
				return false;
		} else if (!facebookPageId.equals(other.facebookPageId))
			return false;
		if (lastFacebookPagePostRetrievedAt == null) {
			if (other.lastFacebookPagePostRetrievedAt != null)
				return false;
		} else if (!lastFacebookPagePostRetrievedAt.equals(other.lastFacebookPagePostRetrievedAt))
			return false;
		if (facebookPageUrl == null) {
			if (other.facebookPageUrl != null)
				return false;
		} else if (!facebookPageUrl.equals(other.facebookPageUrl))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (hasAcceptedCurrentBuyerTos == null) {
			if (other.hasAcceptedCurrentBuyerTos != null)
				return false;
		} else if (!hasAcceptedCurrentBuyerTos
				.equals(other.hasAcceptedCurrentBuyerTos))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isMobileVerified == null) {
			if (other.isMobileVerified != null)
				return false;
		} else if (!isMobileVerified.equals(other.isMobileVerified))
			return false;
		if (isUserBlocked == null) {
			if (other.isUserBlocked != null)
				return false;
		} else if (!isUserBlocked.equals(other.isUserBlocked))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (merchantName == null) {
			if (other.merchantName != null)
				return false;
		} else if (!merchantName.equals(other.merchantName))
			return false;
		if (mobileNumber == null) {
			if (other.mobileNumber != null)
				return false;
		} else if (!mobileNumber.equals(other.mobileNumber))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (voucherCampaignImageUrl == null) {
			if (other.voucherCampaignImageUrl != null)
				return false;
		} else if (!voucherCampaignImageUrl
				.equals(other.voucherCampaignImageUrl))
			return false;
		if (voucherLogoImageUrl == null) {
			if (other.voucherLogoImageUrl != null)
				return false;
		} else if (!voucherLogoImageUrl.equals(other.voucherLogoImageUrl))
			return false;
		if (doAllowSellerEmails == null) {
			if (other.doAllowSellerEmails != null)
				return false;
		} else if (!doAllowSellerEmails.equals(other.doAllowSellerEmails))
			return false;
		
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((acceptedTermsOfService == null) ? 0
						: acceptedTermsOfService.hashCode());
		result = prime * result
				+ ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime
				* result
				+ ((doUseChainedPayments == null) ? 0 : doUseChainedPayments
						.hashCode());
		result = prime * result
				+ ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result
				+ ((dob == null) ? 0 : dob.hashCode());
		result = prime * result
				+ ((facebookAlbumId == null) ? 0 : facebookAlbumId.hashCode());
		result = prime * result
				+ ((profileImageUrl == null) ? 0 : profileImageUrl.hashCode());
		result = prime * result
				+ ((facebookPageId == null) ? 0 : facebookPageId.hashCode());
		result = prime * result
				+ ((facebookPageUrl == null) ? 0 : facebookPageUrl.hashCode());
		result = prime * result
				+ ((lastFacebookPagePostRetrievedAt == null) ? 0 : lastFacebookPagePostRetrievedAt.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime
				* result
				+ ((hasAcceptedCurrentBuyerTos == null) ? 0
						: hasAcceptedCurrentBuyerTos.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((isMobileVerified == null) ? 0 : isMobileVerified.hashCode());
		result = prime * result
				+ ((isUserBlocked == null) ? 0 : isUserBlocked.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((merchantName == null) ? 0 : merchantName.hashCode());
		result = prime * result
				+ ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime
				* result
				+ ((voucherCampaignImageUrl == null) ? 0
						: voucherCampaignImageUrl.hashCode());
		result = prime
				* result
				+ ((voucherLogoImageUrl == null) ? 0 : voucherLogoImageUrl
						.hashCode());
		result = prime
				* result
				+ ((doAllowSellerEmails == null) ? 0 : doAllowSellerEmails
						.hashCode());
		return result;
	}
}
