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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.projectlaver.util.PreapprovalStatus;
import com.projectlaver.util.PreapprovalType;

@Entity(name="Preapprovals")
public class Preapproval extends AbstractVersionedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne//( cascade = {CascadeType.ALL} )
	private User user;
	
	@Column(unique=true)
	private String preapprovalKey;
	
	@Column
	private String correlationId;
	
	@Enumerated(EnumType.STRING)
	private PreapprovalType type;
	
	@Enumerated(EnumType.STRING)
	private PreapprovalStatus status;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start", nullable = false)
    private Date start;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end", nullable = false)
    private Date end;

	// default constructor
	public Preapproval() {
		super();		
	}
	
	public Preapproval(String preapprovalKey, String correlationId, User user, PreapprovalType type, PreapprovalStatus status, Date start, Date end ) {
		super();
		this.preapprovalKey = preapprovalKey;
		this.correlationId = correlationId;
		this.user = user;
		this.type = type;
		this.status = status;
		this.start = start;
		this.end = end;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Preapproval other = (Preapproval) obj;
		if (correlationId == null) {
			if (other.correlationId != null)
				return false;
		} else if (!correlationId.equals(other.correlationId))
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (preapprovalKey == null) {
			if (other.preapprovalKey != null)
				return false;
		} else if (!preapprovalKey.equals(other.preapprovalKey))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (status != other.status)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((correlationId == null) ? 0 : correlationId.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result
				+ ((preapprovalKey == null) ? 0 : preapprovalKey.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getPreapprovalKey() {
		return preapprovalKey;
	}
	
	public void setPreapprovalKey( String preapprovalKey ) {
		this.preapprovalKey = preapprovalKey;
	}
	
	public void setStatus( PreapprovalStatus status ) {
		this.status = status;
	}
	
	public PreapprovalStatus getStatus() {
		return this.status;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public PreapprovalType getType() {
		return type;
	}

	public void setType(PreapprovalType type) {
		this.type = type;
	}
	
	
	
}
