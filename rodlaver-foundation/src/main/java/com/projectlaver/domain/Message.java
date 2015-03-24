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

import java.util.List;
import java.util.Set;

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

import com.projectlaver.util.MessageStatus;


@Entity(name="Messages")
public class Message extends AbstractVersionedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	private User user;

	@Column(unique=true)
	private String twitterId;

	@Column(nullable=true)
	private String inResponseToTwitterId;
	
	@Column(nullable=true)
	private Boolean isNaturalReply;
	
	@Column(nullable=true)
	private Boolean isRetweet;

	@Column
	private String text;

	@Column
	private Boolean containsKeyword;

	@ManyToOne(fetch=FetchType.LAZY)
	private Listing listing;

	// note: changed this to one to many, since lazy loading is impossible with one to one
	// even though it's logically expected to be one to one
	@OneToMany(mappedBy="message", fetch=FetchType.LAZY )
	private Set<Payment> payments;

	@Enumerated(EnumType.STRING)
	private MessageStatus status;

	@Column
	private String providerId;

	@Column
	private String providerUserId;

	@OneToMany(mappedBy="message", fetch=FetchType.LAZY )
	private List<MessageStateChange> messageStateChanges;

	@Column(name="batch_processor")
	private String batchProcessor;
	
	@Column(name="embeded_provider_content", length=512)
	private String embededProviderContent;

	public Message() {}

	public Message( User user, MessageStatus status, Listing listing ) {
		super();
		this.user = user;
		this.status = status;
		this.listing = listing;
	}

	public Message( String providerId, String providerUserId, String twitterId, String inReplyToStatusId, Boolean isNaturalReply, Boolean isRetweet, String text, MessageStatus status ) {
		super();
		this.providerId = providerId;
		this.providerUserId = providerUserId;
		this.twitterId = twitterId;
		this.inResponseToTwitterId = inReplyToStatusId;
		this.isNaturalReply = isNaturalReply;
		this.isRetweet = isRetweet;
		this.text = text;
		this.status = status;
	}


	public Message( String twitterId, User user, String inResponseToTwitterId, MessageStatus status, String text, Listing listing ) {
		super();
		this.twitterId = twitterId;
		this.user = user;
		if( inResponseToTwitterId != null ) {
			this.inResponseToTwitterId = inResponseToTwitterId;
		}
		this.status = status;
		this.text = text;
		this.listing = listing;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (batchProcessor == null) {
			if (other.batchProcessor != null)
				return false;
		} else if (!batchProcessor.equals(other.batchProcessor))
			return false;
		if (containsKeyword == null) {
			if (other.containsKeyword != null)
				return false;
		} else if (!containsKeyword.equals(other.containsKeyword))
			return false;
		if (embededProviderContent == null) {
			if (other.embededProviderContent != null)
				return false;
		} else if (!embededProviderContent.equals(other.embededProviderContent))
			return false;
		if (inResponseToTwitterId == null) {
			if (other.inResponseToTwitterId != null)
				return false;
		} else if (!inResponseToTwitterId.equals(other.inResponseToTwitterId))
			return false;
		if (isNaturalReply == null) {
			if (other.isNaturalReply != null)
				return false;
		} else if (!isNaturalReply.equals(other.isNaturalReply))
			return false;
		if (isRetweet == null) {
			if (other.isRetweet != null)
				return false;
		} else if (!isRetweet.equals(other.isRetweet))
			return false;
		if (providerId == null) {
			if (other.providerId != null)
				return false;
		} else if (!providerId.equals(other.providerId))
			return false;
		if (providerUserId == null) {
			if (other.providerUserId != null)
				return false;
		} else if (!providerUserId.equals(other.providerUserId))
			return false;
		if (status != other.status)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (twitterId == null) {
			if (other.twitterId != null)
				return false;
		} else if (!twitterId.equals(other.twitterId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((batchProcessor == null) ? 0 : batchProcessor.hashCode());
		result = prime * result
				+ ((containsKeyword == null) ? 0 : containsKeyword.hashCode());
		result = prime
				* result
				+ ((embededProviderContent == null) ? 0
						: embededProviderContent.hashCode());
		result = prime
				* result
				+ ((inResponseToTwitterId == null) ? 0 : inResponseToTwitterId
						.hashCode());
		result = prime * result
				+ ((isNaturalReply == null) ? 0 : isNaturalReply.hashCode());
		result = prime * result
				+ ((isRetweet == null) ? 0 : isRetweet.hashCode());
		result = prime * result
				+ ((providerId == null) ? 0 : providerId.hashCode());
		result = prime * result
				+ ((providerUserId == null) ? 0 : providerUserId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result
				+ ((twitterId == null) ? 0 : twitterId.hashCode());
		return result;
	}


	public String getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public String getInResponseToTwitterId() {
		return inResponseToTwitterId;
	}

	public String getText() {
		return text;
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

	public void setInResponseToTwitterId(String inResponseToTwitterId) {
		this.inResponseToTwitterId = inResponseToTwitterId;
	}

	public Set<Payment> getPayments() {
		return payments;
	}

	public void setPayments( Set<Payment> payments ) {
		this.payments = payments;
	}

	public void setText(String text) {
		this.text = text;
	}

	public MessageStatus getStatus() {
		return status;
	}

	public void setStatus(MessageStatus status) {
		this.status = status;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public Boolean getContainsKeyword() {
		return containsKeyword;
	}

	public void setContainsKeyword(Boolean containsKeyword) {
		this.containsKeyword = containsKeyword;
	}

	public String getBatchProcessor() {
		return batchProcessor;
	}

	public void setBatchProcessor(String batchProcessor) {
		this.batchProcessor = batchProcessor;
	}

	public List<MessageStateChange> getMessageStateChanges() {
		return messageStateChanges;
	}

	public void setMessageStateChanges(List<MessageStateChange> messageStateChanges) {
		this.messageStateChanges = messageStateChanges;
	}

	public String getEmbededProviderContent() {
		return embededProviderContent;
	}

	public void setEmbededProviderContent(String embededProviderContent) {
		this.embededProviderContent = embededProviderContent;
	}

	public Boolean getIsNaturalReply() {
		return isNaturalReply;
	}

	public void setIsNaturalReply (Boolean isNaturalReply ) {
		this.isNaturalReply = isNaturalReply;
	}
	
	public Boolean getIsRetweet() {
		return this.isRetweet;
	}
	
	public void setIsRetweet( Boolean isRetweet ) {
		this.isRetweet = isRetweet;
	}
	
}
