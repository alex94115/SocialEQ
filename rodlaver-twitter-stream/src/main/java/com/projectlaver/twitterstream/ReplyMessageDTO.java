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
package com.projectlaver.twitterstream;

import java.sql.Timestamp;
import java.util.Date;

public class ReplyMessageDTO {
	
	private String providerId;
	private String providerUserId;
	private String twitterId;
	private String inResponseToTwitterId;
	private Boolean isNaturalReply; 
	private Boolean isRetweet;
	private String text;
	private String hashtag;
	private String processingStatus;
	private Timestamp created;

	public ReplyMessageDTO() {}
	
	public ReplyMessageDTO( 
			String providerId, 
			String providerUserId, 
			String twitterId, 
			String inResponseToTwitterId, 
			Boolean isNaturalReply, 
			Boolean isRetweet, 
			String text, 
			String hashtag,
			String processingStatus,
			Date createdDate ) {
		
		
		this.providerId = providerId;
		this.providerUserId = providerUserId;
		this.twitterId = twitterId;
		this.inResponseToTwitterId = inResponseToTwitterId;
		this.isNaturalReply = isNaturalReply;
		this.isRetweet = isRetweet;
		this.text = text;
		this.hashtag = hashtag;
		this.processingStatus = processingStatus;
		this.created = this.timestampFromDate( createdDate );
	} 
	
	Timestamp timestampFromDate(Date createdDate) {
		return new java.sql.Timestamp( createdDate.getTime() );
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public String getInResponseToTwitterId() {
		return inResponseToTwitterId;
	}

	public void setInResponseToTwitterId(String inResponseToTwitterId) {
		this.inResponseToTwitterId = inResponseToTwitterId;
	}

	public Boolean getIsNaturalReply() {
		return isNaturalReply;
	}

	public void setIsNaturalReply(Boolean isNaturalReply) {
		this.isNaturalReply = isNaturalReply;
	}

	public Boolean getIsRetweet() {
		return isRetweet;
	}

	public void setIsRetweet(Boolean isRetweet) {
		this.isRetweet = isRetweet;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag ) {
		this.hashtag = hashtag ;
	}

	public String getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}
	
	public void setStatus( String status ) {
		this.processingStatus = status;
	}
	
	public String getStatus() {
		return this.processingStatus;
	}

}
