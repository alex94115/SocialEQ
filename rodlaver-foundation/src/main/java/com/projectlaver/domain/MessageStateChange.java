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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.projectlaver.util.CommunicationChannel;
import com.projectlaver.util.ListingStatus;
import com.projectlaver.util.MessageStatus;

@Entity(name="MessageStateChanges")
public class MessageStateChange extends AbstractVersionedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Message message;
	
	@Enumerated(EnumType.STRING)
	private MessageStatus previousState;
	
	@Enumerated(EnumType.STRING)
	private MessageStatus newState;
	
	@Column
	private Boolean doesRequireBuyerCommunication;
	
	@Column
	private Boolean doesRequireSellerCommunication;
	
	@Enumerated(EnumType.STRING)
	private CommunicationChannel communicationChannel;
	
	@Column
	private Boolean areBuyerCommunicationsComplete;
	
	@Column
	private Boolean areSellerCommunicationsComplete;
	
	@Column
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public MessageStatus getPreviousState() {
		return previousState;
	}

	public void setPreviousState(MessageStatus previousState) {
		this.previousState = previousState;
	}

	public MessageStatus getNewState() {
		return newState;
	}

	public void setNewState(MessageStatus newState) {
		this.newState = newState;
	}

	public Boolean getDoesRequireBuyerCommunication() {
		return doesRequireBuyerCommunication;
	}

	public void setDoesRequireBuyerCommunication(
			Boolean doesRequireBuyerCommunication) {
		this.doesRequireBuyerCommunication = doesRequireBuyerCommunication;
	}

	public Boolean getDoesRequireSellerCommunication() {
		return doesRequireSellerCommunication;
	}

	public void setDoesRequireSellerCommunication(
			Boolean doesRequireSellerCommunication) {
		this.doesRequireSellerCommunication = doesRequireSellerCommunication;
	}

	public Boolean getAreBuyerCommunicationsComplete() {
		return areBuyerCommunicationsComplete;
	}

	public void setAreBuyerCommunicationsComplete(
			Boolean areBuyerCommunicationsComplete) {
		this.areBuyerCommunicationsComplete = areBuyerCommunicationsComplete;
	}

	public Boolean getAreSellerCommunicationsComplete() {
		return areSellerCommunicationsComplete;
	}

	public void setAreSellerCommunicationsComplete(
			Boolean areSellerCommunicationsComplete) {
		this.areSellerCommunicationsComplete = areSellerCommunicationsComplete;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CommunicationChannel getCommunicationChannel() {
		return communicationChannel;
	}

	public void setCommunicationChannel(CommunicationChannel communicationChannel) {
		this.communicationChannel= communicationChannel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((areBuyerCommunicationsComplete == null) ? 0
						: areBuyerCommunicationsComplete.hashCode());
		result = prime
				* result
				+ ((areSellerCommunicationsComplete == null) ? 0
						: areSellerCommunicationsComplete.hashCode());
		result = prime
				* result
				+ ((communicationChannel == null) ? 0 : communicationChannel
						.hashCode());
		result = prime
				* result
				+ ((doesRequireBuyerCommunication == null) ? 0
						: doesRequireBuyerCommunication.hashCode());
		result = prime
				* result
				+ ((doesRequireSellerCommunication == null) ? 0
						: doesRequireSellerCommunication.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((newState == null) ? 0 : newState.hashCode());
		result = prime * result
				+ ((previousState == null) ? 0 : previousState.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		MessageStateChange other = (MessageStateChange) obj;
		if (areBuyerCommunicationsComplete == null) {
			if (other.areBuyerCommunicationsComplete != null)
				return false;
		} else if (!areBuyerCommunicationsComplete
				.equals(other.areBuyerCommunicationsComplete))
			return false;
		if (areSellerCommunicationsComplete == null) {
			if (other.areSellerCommunicationsComplete != null)
				return false;
		} else if (!areSellerCommunicationsComplete
				.equals(other.areSellerCommunicationsComplete))
			return false;
		if (communicationChannel != other.communicationChannel)
			return false;
		if (doesRequireBuyerCommunication == null) {
			if (other.doesRequireBuyerCommunication != null)
				return false;
		} else if (!doesRequireBuyerCommunication
				.equals(other.doesRequireBuyerCommunication))
			return false;
		if (doesRequireSellerCommunication == null) {
			if (other.doesRequireSellerCommunication != null)
				return false;
		} else if (!doesRequireSellerCommunication
				.equals(other.doesRequireSellerCommunication))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (newState != other.newState)
			return false;
		if (previousState != other.previousState)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	
}
