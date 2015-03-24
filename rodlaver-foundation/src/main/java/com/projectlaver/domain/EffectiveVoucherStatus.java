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

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.projectlaver.util.VoucherStatus;

@Entity(name="EffectiveVoucherStatuses")
public class EffectiveVoucherStatus extends EffectiveDatedEntity {
	
	/**
	 * Instance variables
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private VoucherStatus status;
	
	@ManyToOne(fetch=FetchType.LAZY) 
	private Voucher voucher;
	
	/**
	 * Constructors
	 */
	
	public EffectiveVoucherStatus() {
		super();
	}
	
	public EffectiveVoucherStatus( Date start, Date end, VoucherStatus voucherStatus, Voucher voucher) {
		super( start, end );
		this.status = voucherStatus;
		this.voucher = voucher;
	}
	
	/**
	 * Public methods 
	 */
	
	public Long getId() {
		return id;
	}

	public VoucherStatus getStatus() {
		return status;
	}

	public void setStatus( VoucherStatus status ) {
		this.status = status;
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}

	@Override
	protected void onUpdate() {
		// no op
	}

}
