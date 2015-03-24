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

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity(name="TermsOfServices")
public class TermsOfService extends AbstractVersionedEntity {

	public TermsOfService() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="acceptedTermsOfService")
    private Set<User> users;
	
	@Column( name="internal_version" )
	private Integer internalVersion;
	
	@Column( name="public_name" )
	private String publicName;
	
	@Column( name="is_for_buyers" )
	private Boolean isForBuyers;
	
	@Column( name="is_for_sellers" )
	private Boolean isForSellers;

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Integer getInternalVersion() {
		return internalVersion;
	}

	public void setInternalVersion(Integer internalVersion) {
		this.internalVersion = internalVersion;
	}

	public String getPublicName() {
		return publicName;
	}

	public void setPublicName(String publicName) {
		this.publicName = publicName;
	}

	public Boolean getIsForBuyers() {
		return isForBuyers;
	}

	public void setIsForBuyers(Boolean isForBuyers) {
		this.isForBuyers = isForBuyers;
	}

	public Boolean getIsForSellers() {
		return isForSellers;
	}

	public void setIsForSellers(Boolean isForSellers) {
		this.isForSellers = isForSellers;
	}

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((internalVersion == null) ? 0 : internalVersion.hashCode());
		result = prime * result
				+ ((isForBuyers == null) ? 0 : isForBuyers.hashCode());
		result = prime * result
				+ ((isForSellers == null) ? 0 : isForSellers.hashCode());
		result = prime * result
				+ ((publicName == null) ? 0 : publicName.hashCode());
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
		TermsOfService other = (TermsOfService) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (internalVersion == null) {
			if (other.internalVersion != null)
				return false;
		} else if (!internalVersion.equals(other.internalVersion))
			return false;
		if (isForBuyers == null) {
			if (other.isForBuyers != null)
				return false;
		} else if (!isForBuyers.equals(other.isForBuyers))
			return false;
		if (isForSellers == null) {
			if (other.isForSellers != null)
				return false;
		} else if (!isForSellers.equals(other.isForSellers))
			return false;
		if (publicName == null) {
			if (other.publicName != null)
				return false;
		} else if (!publicName.equals(other.publicName))
			return false;
		return true;
	}
	
	

}
