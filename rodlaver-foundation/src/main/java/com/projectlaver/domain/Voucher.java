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
import javax.persistence.Transient;

@Entity(name="Vouchers")
public class Voucher extends AbstractVersionedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String serialNumber;
	
	@Column
	private String salt;
	
	@Column
	private String filename;
	
	@Transient
	private String cloudFrontUrl;
	
	@ManyToOne(fetch=FetchType.LAZY) 
	private Payment payment;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="voucher", cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
	private List<EffectiveVoucherStatus> effectiveVoucherStatus;
	
	public String getVoucherSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public List<EffectiveVoucherStatus> getEffectiveVoucherStatus() {
		return effectiveVoucherStatus;
	}

	public void setEffectiveVoucherStatus(
			List<EffectiveVoucherStatus> effectiveVoucherStatus) {
		this.effectiveVoucherStatus = effectiveVoucherStatus;
	}

	public Long getId() {
		return id;
	}
	
	public String getCloudFrontUrl() {
		return cloudFrontUrl;
	}

	public void setCloudFrontUrl(String cloudFrontUrl) {
		this.cloudFrontUrl = cloudFrontUrl;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public EffectiveVoucherStatus getCurrentEffectiveVoucherStatus() {
		return this.getEffectiveVoucherStatusAsOf( new Date() );
	}

	public EffectiveVoucherStatus getEffectiveVoucherStatusAsOf( Date asOfDate ) {
		EffectiveVoucherStatus result = null;
		
		if( this.effectiveVoucherStatus != null && this.effectiveVoucherStatus.size() > 0 ) {
			
			for( EffectiveVoucherStatus status : this.effectiveVoucherStatus  ) {
				Date start = status.getStart();
				Date end = status.getEnd();
				if( ( start.equals( asOfDate ) || start.before( asOfDate ) ) && asOfDate.before( end ) ) {
					result = status;
				}
			}
		}
		
		return result;
	}
	
	public void appendEffectiveVoucherStatus( EffectiveVoucherStatus effectiveVoucherStatus ) {
		Date newStatusEffectiveStart = effectiveVoucherStatus.getStart();
		
		// ensure the status list is not null
		if( this.effectiveVoucherStatus == null ) {
			this.effectiveVoucherStatus = new ArrayList<EffectiveVoucherStatus>();
		}
		
		// adjust the end date of the prior status, if any
		if( !this.effectiveVoucherStatus.isEmpty() ) {
			int i = this.effectiveVoucherStatus.size() - 1;
			EffectiveVoucherStatus priorEffectiveStatus = this.effectiveVoucherStatus.get( i );
			Date revisedEndDate = new Date( newStatusEffectiveStart.getTime() - 1000 );
			priorEffectiveStatus.setEnd( revisedEndDate );
		}
		
		// append the input status
		this.effectiveVoucherStatus.add( effectiveVoucherStatus );
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((payment == null) ? 0 : payment.hashCode());
		result = prime * result
				+ ((filename == null) ? 0 : filename.hashCode());
		result = prime * result
				+ ((salt == null) ? 0 : salt.hashCode());
		result = prime
				* result
				+ ((serialNumber == null) ? 0 : serialNumber
						.hashCode());
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
		Voucher other = (Voucher) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (payment == null) {
			if (other.payment != null)
				return false;
		} else if (!payment.equals(other.payment))
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (salt == null) {
			if (other.salt != null)
				return false;
		} else if (!salt.equals(other.salt))
			return false;
		if (serialNumber == null) {
			if (other.serialNumber != null)
				return false;
		} else if (!serialNumber.equals(other.serialNumber))
			return false;
		return true;
	}

}
