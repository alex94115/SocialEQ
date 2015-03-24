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
package com.projectlaver.util;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class SimpleMessageCursorItem {

	private Long messageId;
	private Integer messageVersion;
	private Integer newMessageVersion;
	private String priorMessageStatus;
	private String newMessageStatus;
	private Boolean doesRequireBuyerCommunication;
	private Boolean doesRequireSellerCommunication;
	private String channel;
	
	public SimpleMessageCursorItem() {}
	
	public Long getMessageId() {
		return messageId;
	}
	
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
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
			Boolean doesRequreSellerCommunication) {
		this.doesRequireSellerCommunication = doesRequreSellerCommunication;
	}

	public String getPriorMessageStatus() {
		return priorMessageStatus;
	}

	public void setPriorMessageStatus(String priorMessageStatus) {
		this.priorMessageStatus = priorMessageStatus;
	}

	public String getNewMessageStatus() {
		return newMessageStatus;
	}

	public void setNewMessageStatus(String newMessageStatus) {
		this.newMessageStatus = newMessageStatus;
	}

	public Integer getMessageVersion() {
		return messageVersion;
	}

	public void setMessageVersion(Integer messageVersion) {
		this.messageVersion = messageVersion;
	}

	public Integer getNewMessageVersion() {
		return newMessageVersion;
	}

	public void setNewMessageVersion(Integer newMessageVersion) {
		this.newMessageVersion = newMessageVersion;
	}
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(CommunicationChannel channel) {
		this.channel = channel.toString();
	}

	@Override
	public String toString() {
	   return ToStringBuilder.reflectionToString(this);
	}	
	
}
