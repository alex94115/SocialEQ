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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

@Entity(name="ContentFiles")
public class ContentFile extends AbstractVersionedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// this is the obscured filename that buyers will request to download
	@Column
	private String contentFilename;

	// for digital listings, this is the original uploaded file's name
	@Column(length=512)
	private String digitalContentOriginalFilename;

	@Column
	private String digitalContentType;

	@Transient
	private String transientPath;

	@ManyToOne(fetch=FetchType.LAZY)
	private Listing listing;
	
	@Transient
	private String cloudFrontUrl;

	@Transient
	private String cloudFrontDownloadableUrl;

	
	public String getContentFilename() {
		return contentFilename;
	}

	public void setContentFilename(String contentFilename) {
		this.contentFilename = contentFilename;
	}

	public String getDigitalContentOriginalFilename() {
		return digitalContentOriginalFilename;
	}

	public void setDigitalContentOriginalFilename(
			String digitalContentOriginalFilename) {
		this.digitalContentOriginalFilename = digitalContentOriginalFilename;
	}

	public String getDigitalContentType() {
		return digitalContentType;
	}

	public void setDigitalContentType(String digitalContentType) {
		this.digitalContentType = digitalContentType;
	}

	public String getTransientPath() {
		return transientPath;
	}

	public void setTransientPath(String transientPath) {
		this.transientPath = transientPath;
	}
	
	public File getDigitalContentAsFile() {
		return new File( this.transientPath );
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Listing getListing() {
		return listing;
	}

	public void setListing(Listing listing) {
		this.listing = listing;
	}
	
	public void setCloudFrontUrl( String cloudFrontUrl ) {
		this.cloudFrontUrl = cloudFrontUrl;
	}
	
	public String getCloudFrontUrl() {
		return this.cloudFrontUrl;
	}
	
	public void setCloudFrontDownloadableUrl( String cloudFrontDownloadableUrl ) {
		this.cloudFrontDownloadableUrl = cloudFrontDownloadableUrl;
	}
	
	public String getCloudFrontDownloadableUrl() {
		return this.cloudFrontDownloadableUrl;
	}
	
	public Boolean getIsAudioFile() {
		Boolean result = false;
		
		if( this.digitalContentType != null && StringUtils.containsIgnoreCase( this.digitalContentType, "audio" )) {
			result = true;
		}
		
		return result;
	}
	
	public Boolean getIsVideoFile() {
		Boolean result = false;
		
		if( this.digitalContentType != null && StringUtils.containsIgnoreCase( this.digitalContentType, "video" )) {
			result = true;
		}
		
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((contentFilename == null) ? 0 : contentFilename.hashCode());
		result = prime
				* result
				+ ((digitalContentOriginalFilename == null) ? 0
						: digitalContentOriginalFilename.hashCode());
		result = prime
				* result
				+ ((digitalContentType == null) ? 0 : digitalContentType
						.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ContentFile other = (ContentFile) obj;
		if (contentFilename == null) {
			if (other.contentFilename != null) {
				return false;
			}
		} else if (!contentFilename.equals(other.contentFilename)) {
			return false;
		}
		if (digitalContentOriginalFilename == null) {
			if (other.digitalContentOriginalFilename != null) {
				return false;
			}
		} else if (!digitalContentOriginalFilename
				.equals(other.digitalContentOriginalFilename)) {
			return false;
		}
		if (digitalContentType == null) {
			if (other.digitalContentType != null) {
				return false;
			}
		} else if (!digitalContentType.equals(other.digitalContentType)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
	
	
}
