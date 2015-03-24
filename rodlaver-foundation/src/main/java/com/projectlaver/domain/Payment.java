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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.projectlaver.util.GoodsType;
import com.projectlaver.util.MessageStatus;
import com.projectlaver.util.PaymentStatus;


@Entity(name="Payments")
public class Payment extends AbstractVersionedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	private User payer;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private User payee;
	
	@Column
	private String payKey;
	
	@Column
	private String correlationId;
	
	@Column(name="backendPayerId")
	private String payerId;
	
	@Column
	private String authorizationTransactionId;
	
	@Column(name="amount", precision=15, scale=4)
	private BigDecimal totalAmount;
	
	@Column(precision=15, scale=4)
	private BigDecimal sellerAmount;
	
	@Column(precision=15, scale=4)
	private BigDecimal rodLaverAmount;
	
	@Column
	private String currencyCode;
	
	@Column(name="has_been_shipped")
	private Boolean hasBeenShipped;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Listing listing;
	
	@OneToOne(fetch=FetchType.LAZY)
	private Message message;
	
	@OneToOne(fetch=FetchType.LAZY)
	private Inventory inventory;
	
	@OneToOne(fetch=FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
	private Address shippingAddress;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="payment", cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
	private List<EffectivePaymentStatus> effectivePaymentStatus;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="payment", cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
	private List<Voucher> vouchers;

	@Column
	private Integer quantity;
	
	@Transient
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * Constructors
	 */
	public Payment() {
		super();

		// TODO Ticket-#465 -- Internationalization
		this.currencyCode = "USD";
	}
	
	public Payment( 
			Message message, 
			Listing listing, 
			Inventory inventory,
			Integer quantity,
			User buyer, 
			User seller, 
			String payKey, 
			String correlationId, 
			BigDecimal rodlaverAmount, 
			BigDecimal sellerAmount,
			BigDecimal totalAmount,
			EffectivePaymentStatus effectivePaymentStatus ) {
		
		super();
		
		this.setVersion( 0 );
		
		// TODO Ticket-#465 -- Internationalization
		this.setCurrencyCode( "USD" );
		
		this.listing = listing;
		this.inventory = inventory;
		this.quantity = quantity;
		
		//if( listing.getType().equals( ListingType.PHYSICAL )) {
		if( listing.getGoodsType().equals( GoodsType.PHYSICAL )) {
			this.setHasBeenShipped( false );
		}
		
		this.setMessage( message );
		this.setPayer( buyer );
		this.setPayee( seller );
		this.setPayKey( payKey );
		this.correlationId = correlationId;
		
		this.setRodLaverAmount( rodlaverAmount );
		this.setSellerAmount( sellerAmount );
		
		// confirm the totals agree
		if( sellerAmount.add( rodlaverAmount ).compareTo( totalAmount ) != 0 ) {
			// expect the payment to seller to be exactly the same amount as the total amount 
			this.logger.error( "Amounts don't add up! payKey: " + payKey + ", sellerAmount: " + sellerAmount + 
								", rodlaverAmount: " + rodLaverAmount + ", listing total: " + listing.getAmount() );
			
			effectivePaymentStatus.setStatus( PaymentStatus.UNKNOWN );
			
		}
		
		
		this.setTotalAmount( totalAmount );
		
		// create the initial effective payment status
		this.appendEffectivePaymentStatus( effectivePaymentStatus );
		// set the back pointer
		effectivePaymentStatus.setPayment( this );
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (hasBeenShipped == null) {
			if (other.hasBeenShipped != null)
				return false;
		} else if (!hasBeenShipped.equals(other.hasBeenShipped))
			return false;
		if (payKey == null) {
			if (other.payKey != null)
				return false;
		} else if (!payKey.equals(other.payKey))
			return false;
		if (correlationId == null) {
			if (other.correlationId != null)
				return false;
		} else if (!correlationId.equals(other.correlationId))
			return false;
		if (payerId == null) {
			if (other.payerId != null)
				return false;
		} else if (!payerId.equals(other.payerId))
			return false;
		if (authorizationTransactionId == null) {
			if (other.authorizationTransactionId != null)
				return false;
		} else if (!authorizationTransactionId.equals(other.authorizationTransactionId))
			return false;
		if (rodLaverAmount == null) {
			if (other.rodLaverAmount != null)
				return false;
		} else if (!rodLaverAmount.equals(other.rodLaverAmount))
			return false;
		if (sellerAmount == null) {
			if (other.sellerAmount != null)
				return false;
		} else if (!sellerAmount.equals(other.sellerAmount))
			return false;
		if (totalAmount == null) {
			if (other.totalAmount != null)
				return false;
		} else if (!totalAmount.equals(other.totalAmount))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime * result + ((hasBeenShipped == null) ? 0 : hasBeenShipped.hashCode());
		result = prime * result + ((payKey == null) ? 0 : payKey.hashCode());
		result = prime * result + ((correlationId == null) ? 0 : correlationId.hashCode());
		result = prime * result + ((payerId == null) ? 0 : payerId.hashCode());
		result = prime * result + ((authorizationTransactionId == null) ? 0 : authorizationTransactionId.hashCode());
		result = prime * result + ((rodLaverAmount == null) ? 0 : rodLaverAmount.hashCode());
		result = prime * result + ((sellerAmount == null) ? 0 : sellerAmount.hashCode());
		result = prime * result + ((totalAmount == null) ? 0 : totalAmount.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		return result;
	}
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public BigDecimal getSellerAmount() {
		return sellerAmount;
	}

	public void setSellerAmount(BigDecimal sellerAmount) {
		this.sellerAmount = sellerAmount;
	}

	public BigDecimal getRodLaverAmount() {
		return rodLaverAmount;
	}

	public void setRodLaverAmount(BigDecimal rodLaverAmount) {
		this.rodLaverAmount = rodLaverAmount;
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getPayer() {
		return payer;
	}

	public void setPayer(User payer) {
		this.payer = payer;
	}

	public String getPayKey() {
		return payKey;
	}

	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}
	
	public String getCorrelationId() {
		return this.correlationId;
	}
	
	public void setCorrelationId( String correlationId ) {
		this.correlationId = correlationId;
	}

	public User getPayee() {
		return payee;
	}

	public void setPayee(User payee) {
		this.payee = payee;
	}

	public Listing getListing() {
		return listing;
	}

	public void setListing(Listing listing) {
		this.listing = listing;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public void setInventory( Inventory inventory) {
		this.inventory = inventory;
	}

	public Address getShippingAddress() {
		return this.shippingAddress;
	}
	
	public void setShippingAddress( Address shippingAddress ) {
		this.shippingAddress = shippingAddress;
	}

	public Boolean getHasBeenShipped() {
		return hasBeenShipped;
	}

	public void setHasBeenShipped(Boolean hasBeenShipped) {
		this.hasBeenShipped = hasBeenShipped;
	}

	public List<EffectivePaymentStatus> getEffectivePaymentStatus() {
		return effectivePaymentStatus;
	}

	public void setEffectivePaymentStatus( List<EffectivePaymentStatus> effectivePaymentStatus) {
		this.effectivePaymentStatus = effectivePaymentStatus;
	}
	
	public List<Voucher> getVouchers() {
		return this.vouchers;
	}
	
	public void setVouchers( List<Voucher> vouchers ) {
		this.vouchers = vouchers;
	}
	
	public EffectivePaymentStatus getCurrentEffectivePaymentStatus() {
		return this.getEffectivePaymentStatusAsOf( new Date() );
	}
	
	public Integer getQuantity() {
		return this.quantity;
	}
	
	public void setQuantity( Integer quantity ) {
		this.quantity = quantity;
	}
	
	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getAuthorizationTransactionId() {
		return authorizationTransactionId;
	}

	public void setAuthorizationTransactionId(String authorizationTransactionId) {
		this.authorizationTransactionId = authorizationTransactionId;
	}

	public EffectivePaymentStatus getEffectivePaymentStatusAsOf( Date asOfDate ) {
		EffectivePaymentStatus result = null;
		
		if( this.effectivePaymentStatus != null && this.effectivePaymentStatus.size() > 0 ) {
			
			for( EffectivePaymentStatus status : this.effectivePaymentStatus  ) {
				Date start = status.getStart();
				Date end = status.getEnd();
				if( ( start.equals( asOfDate ) || start.before( asOfDate ) ) && asOfDate.before( end ) ) {
					result = status;
				}
			}
		}
		
		return result;
	}
	
	public void appendEffectivePaymentStatus( EffectivePaymentStatus effectivePaymentStatus ) {
		Date newStatusEffectiveStart = effectivePaymentStatus.getStart();
		
		// ensure the status list is not null
		if( this.effectivePaymentStatus == null ) {
			this.effectivePaymentStatus = new ArrayList<EffectivePaymentStatus>();
		}
		
		// adjust the end date of the prior status, if any
		if( this.effectivePaymentStatus.size() > 0 ) {
			int i = this.effectivePaymentStatus.size() - 1;
			EffectivePaymentStatus priorEffectiveStatus = this.effectivePaymentStatus.get( i );
			Date revisedEndDate = new Date( newStatusEffectiveStart.getTime() - 1000 );
			priorEffectiveStatus.setEnd( revisedEndDate );
		}
		
		// append the input status
		this.effectivePaymentStatus.add( effectivePaymentStatus );
	}
	
	public String getScreenFormattedStatus() {
		
		MessageStatus messageStatus = this.getMessage().getStatus();
		
		if( messageStatus.equals( MessageStatus.PAYMENT_PROCESSING )) {
			return "Processing Payment";
		} else if( messageStatus.equals( MessageStatus.PAYMENT_PENDING ) ) {
			return "Pending Payment";
		} else if( messageStatus.equals( MessageStatus.PAYMENT_FAILED ) ) {
			return "Payment Failed";
		} else if( messageStatus.equals( MessageStatus.PENDING_SHIPPING_ADDRESS ) ) {
			if( this.getShippingAddress() == null ) {
				return "Pending Shipping Address";
			} else {
				return "Pending Shipment";
			}
		} else if( messageStatus.equals( MessageStatus.PENDING_SHIPMENT ) ) {
			if( this.getHasBeenShipped() == null || !this.getHasBeenShipped() ) {
				return "Pending Shipment";
			} else {
				return "Completed";
			}
		} else if( messageStatus.equals( MessageStatus.COMPLETED ) ) {
			return "Completed";
		} else {
			return "";
		}
	}
	
	
	
}
