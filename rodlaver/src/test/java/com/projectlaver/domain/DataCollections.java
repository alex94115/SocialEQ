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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataCollections {

	private InsertableUser[] users = new InsertableUser[1000];
	private InsertableUserConnection[] userConnections = new InsertableUserConnection[1000];
	
	// Map of UserId's to those user who are sellers
	private Map<Integer, InsertableUser> sellersMap = new HashMap<Integer, InsertableUser>();
	
	// Map of UserId's to those user who are buyers
	private Map<Integer, InsertableUser> buyersMap = new HashMap<Integer, InsertableUser>();
	
	// Map of UserId's to Preapprovals
	private Map<Integer, List<InsertablePreapproval>> preapprovalsMap = new HashMap<Integer, List<InsertablePreapproval>>();
	// Just active preapprovals
	private List<InsertablePreapproval> activePreapprovals = new ArrayList<InsertablePreapproval>();
	private List<InsertablePreapproval> allPreapprovals = new ArrayList<InsertablePreapproval>();
	
	// Map of UserId's to Listings
	private Map<Integer, List<InsertableListing>> listingsMap = new HashMap<Integer, List<InsertableListing>>();
	
	// Sets of listing types
	private Set<InsertableListing> activeListings = new HashSet<InsertableListing>();
	private Set<InsertableListing> completedListings = new HashSet<InsertableListing>();
	private Set<InsertableListing> canceledListings = new HashSet<InsertableListing>();
	
	// Arrays of listings
	InsertableListing[] allListings; 

	InsertableListing[] activeListingArray;
	InsertableListing[] canceledListingArray;
	InsertableListing[] completedListingArray;
	
	
	// Lists of message types
	private List<InsertableMessage> irrelevantMessages = new ArrayList<InsertableMessage>();
	private List<InsertableMessage> pendingRegistrationMessages = new ArrayList<InsertableMessage>();
	private List<InsertableMessage> pendingPaymentMessages = new ArrayList<InsertableMessage>();
	private List<InsertableMessage> repliesToCanceledMessages = new ArrayList<InsertableMessage>();
	private List<InsertableMessage> repliesToSoldOutMessages = new ArrayList<InsertableMessage>();
	private List<InsertableMessage> unblockedPendingRegistrationMessages = new ArrayList<InsertableMessage>();
	private List<InsertableMessage> unblockedPendingPaymentRegistrationMessages = new ArrayList<InsertableMessage>();
	private List<InsertableMessage> qualifiedRepliesToActiveListingMessages = new ArrayList<InsertableMessage>();
	
	private List<InsertableMessage> allReplyMessages;
	
	// payments
	
	private List<InsertablePayment> allPayments = new ArrayList<InsertablePayment>();
	
	public InsertableUser[] getUsers() {
		return users;
	}
	public InsertableUserConnection[] getUserConnections() {
		return userConnections;
	}
	public Map<Integer, InsertableUser> getSellersMap() {
		return sellersMap;
	}
	public Map<Integer, InsertableUser> getBuyersMap() {
		return buyersMap;
	}
	public Map<Integer, List<InsertablePreapproval>> getPreapprovalsMap() {
		return preapprovalsMap;
	}
	public Map<Integer, List<InsertableListing>> getListingsMap() {
		return listingsMap;
	}
	public Set<InsertableListing> getActiveListings() {
		return activeListings;
	}
	public Set<InsertableListing> getCompletedListings() {
		return completedListings;
	}
	public Set<InsertableListing> getCanceledListings() {
		return canceledListings;
	}
	public List<InsertableMessage> getIrrelevantMessages() {
		return irrelevantMessages;
	}
	public List<InsertableMessage> getPendingRegistrationMessages() {
		return pendingRegistrationMessages;
	}
	public List<InsertableMessage> getPendingPaymentMessages() {
		return pendingPaymentMessages;
	}
	public List<InsertableMessage> getRepliesToCanceledMessages() {
		return repliesToCanceledMessages;
	}
	public List<InsertableMessage> getRepliesToSoldOutMessages() {
		return repliesToSoldOutMessages;
	}
	public List<InsertableMessage> getUnblockedPendingRegistrationMessages() {
		return unblockedPendingRegistrationMessages;
	}
	public List<InsertableMessage> getUnblockedPendingPaymentRegistrationMessages() {
		return unblockedPendingPaymentRegistrationMessages;
	}
	public List<InsertableMessage> getQualifiedRepliesToActiveListingMessages() {
		return qualifiedRepliesToActiveListingMessages;
	}
	public InsertableListing[] getAllListings() {
		return allListings;
	}
	public void setAllListings(InsertableListing[] allListings) {
		this.allListings = allListings;
	}
	public InsertableListing[] getActiveListingArray() {
		return activeListingArray;
	}
	public void setActiveListingArray(InsertableListing[] activeListingArray) {
		this.activeListingArray = activeListingArray;
	}
	public InsertableListing[] getCanceledListingArray() {
		return canceledListingArray;
	}
	public void setCanceledListingArray(InsertableListing[] canceledListingArray) {
		this.canceledListingArray = canceledListingArray;
	}
	public InsertableListing[] getCompletedListingArray() {
		return completedListingArray;
	}
	public void setCompletedListingArray(InsertableListing[] completedListingArray) {
		this.completedListingArray = completedListingArray;
	}
	public List<InsertablePreapproval> getActivePreapprovals() {
		return this.activePreapprovals;
	}
	public List<InsertableMessage> getAllReplyMessages() {
		return this.allReplyMessages;
	}
	public void setAllReplyMessages( List<InsertableMessage> allReplyMessages ) {
		this.allReplyMessages = allReplyMessages;
	}
	public InsertableMessage[] getAllReplyMessagesAsArray() {
		return this.allReplyMessages.toArray( new InsertableMessage[0] );
	}
	public List<InsertablePreapproval> getAllPreapprovals() {
		return this.allPreapprovals;
	}
	
	public List<InsertablePayment> getAllPayments() {
		return this.allPayments;
	}
	
}
