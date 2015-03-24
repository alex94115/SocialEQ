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

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.projectlaver.util.GoodsType;
import com.projectlaver.util.ListingStatus;
import com.projectlaver.util.ListingType;
import com.projectlaver.util.MessageStatus;

public class GenerateTestData {

	private Role buyerRole;
	private Role sellerRole;
	
	private Random randomIntGenerator = new Random();
	
	@Before
	public void setup() {
		this.buyerRole = this.createRole( 1L ); 
		this.sellerRole = this.createRole( 2L );
	}
	
	@Test
	public void test() throws Exception {

		PrintStream printStream = new PrintStream(new FileOutputStream("~/Development/hootit/database/performance-test/raw-data.sql", true));
		System.setOut(printStream);
		
		DataCollections collections = new DataCollections();
		

		for( int i = 1; i < 10; i++ ) {
			
			// Two percent of users are sellers; remainder are buyers
			if( Math.random() < .03 ) {
				collections.getUsers()[i] = new InsertableUser( new Long( i ), new RandomTwitterUser(), this.sellerRole.getId() );
				collections.getSellersMap().put( i, collections.getUsers()[i] );
			} else {
				collections.getUsers()[i] = new InsertableUser( new Long( i ), new RandomTwitterUser(), this.buyerRole.getId() );
				collections.getBuyersMap().put( i, collections.getUsers()[i] );
			}
				
			collections.getUserConnections()[i] = new InsertableUserConnection( collections.getUsers()[i].getRandomTwitterUser() );

			// id counter for preapprovals
			int p = 1;
			
			// give about thirty percent of users preapprovals
			if( Math.random() > .67 ) {
				
				Boolean doGenerateCurrentCanceled =  ( Math.random() < .1 ); // ten percent
				Boolean doGenerateCurrentActive = ( Math.random() < .8 ); // eighty percent
				Boolean doGenerateExpiredCanceled = ( Math.random() < .1 ); // ten percent
				Boolean doGenerateExpiredActive = ( Math.random() < .1 ); // ten percent
				
				InsertablePreapproval preapproval =  new InsertablePreapproval( new Long(p), collections.getUsers()[i], doGenerateCurrentCanceled, doGenerateCurrentActive, doGenerateExpiredCanceled, doGenerateExpiredActive );
				//collections.getPreapprovalsMap().put( i, preapproval );
				List<InsertablePreapproval> usersPreapprovals = collections.getPreapprovalsMap().get( i );
				if( usersPreapprovals == null ) {
					usersPreapprovals = new ArrayList<InsertablePreapproval>();
					collections.getPreapprovalsMap().put( i , usersPreapprovals );
				}
				usersPreapprovals.add( preapproval );
				
				// Add every preapproval to the allPreapprovals collection
				collections.getAllPreapprovals().add( preapproval );
				
				// add the current active preapprovals to a collection
				if( doGenerateCurrentActive ) {
					collections.getActivePreapprovals().add( preapproval );
				}
			}
			p = p + 4; // need to space out the prepproval keys so they don't collide
		}
		
		//Assert.assertEquals( 998, collections.getSellersMap().size() + collections.getBuyersMap().size() );
		
		// Add the listings to the collections data structure
		this.initializeListings(collections);
		Assert.assertEquals( collections.getSellersMap().size() * 30, this.sumOfSellersListings( collections.getListingsMap() ) + 0 );
		
		this.initializeMessages(collections);
		
		this.outputInsertStatements( collections.getUserConnections(), 
									 collections.getUsers(), 
									 collections.getAllPreapprovals().toArray( new InsertablePreapproval[0]), 
									 collections.getAllListings(), 
									 collections.getAllReplyMessagesAsArray(),  
									 collections.getAllPayments().toArray( new InsertablePayment[0] ));
		
	}
	
	void outputInsertStatements( AsSqlInsert[]... asSqlInserts ) {
		
		for( AsSqlInsert[] array : asSqlInserts ) {
			for( AsSqlInsert item : array ) {
				if( item != null ) {
					System.out.println( item.asSqlInsert() );
				}
			}
		}
		
	}

	void initializeListings(DataCollections collections) {
		Iterator<Integer> sellerIdsIterator = collections.getSellersMap().keySet().iterator();
		int j = 0;
		while( sellerIdsIterator.hasNext() ) {
			InsertableUser seller = collections.getSellersMap().get( sellerIdsIterator.next() );
			for( int k = 0; k < 30; k++ ) {
				
				double listingTypeRandom = Math.random();
				ListingType type = ListingType.SELLING;
				GoodsType goodsType = GoodsType.DIGITAL;
				ListingStatus status = ListingStatus.CANCELED;
				String keyword = null;
				
				// Ten percent are physical
				if( listingTypeRandom < .10 ) {
					goodsType = GoodsType.PHYSICAL;
					keyword = "BUY";
					
					double statusRandom = Math.random();
					if( statusRandom < .7 ) {
						status = ListingStatus.COMPLETED;
					} else if( statusRandom > .9 ) {
						status = ListingStatus.ACTIVE;
					} 

				// 33 percent are Campaigns
				} else if( listingTypeRandom > .10 && listingTypeRandom < .43 ) {
					type = ListingType.CAMPAIGN;
					keyword = "YES";
					if( Math.random() > .8 ) {
						status = ListingStatus.ACTIVE;
					}
				// 33 percent are Donation drives
				} else if( listingTypeRandom > .43 && listingTypeRandom < .76 ) {
					type = ListingType.DONATION;
					keyword = "DONATE";
					if( Math.random() > .8 ) {
						status = ListingStatus.ACTIVE;
					}
				// remaining 33 percent (remain) Digital
				} else {
					keyword = "BUY";
					if( Math.random() > .8 ) {
						status = ListingStatus.ACTIVE;
					}
				}
				
				// Add this listing to the seller's listings
				InsertableListing listing = new InsertableListing( new Long( j ), seller, type, status, keyword, new Long( j ), RandomStringUtils.randomNumeric( 18 ) );
				List<InsertableListing> sellersExistingListings = collections.getListingsMap().get( seller.getId().intValue() );
				if( sellersExistingListings == null ) {
					sellersExistingListings = new ArrayList<InsertableListing>();
					collections.getListingsMap().put( seller.getId().intValue(), sellersExistingListings );
				}
				sellersExistingListings.add( listing );

				if( listing.getStatus().equals( ListingStatus.ACTIVE )) {
					// add to active set
					collections.getActiveListings().add( listing );
				} else if( listing.getStatus().equals( ListingStatus.COMPLETED )) {
					// add to completed set
					collections.getCompletedListings().add( listing );
				} else if( listing.getStatus().equals( ListingStatus.CANCELED )) {
					// add to canceled set
					collections.getCanceledListings().add( listing );
				} else {
					throw new RuntimeException( "Unexpected listing status." );
				}
				
				
				j = j + 1;
			}
		}
		
		collections.setAllListings( this.appendAllArrays( collections.getListingsMap() ) ); 
		collections.setActiveListingArray( collections.getActiveListings().toArray( new InsertableListing[0] ) );
		collections.setCanceledListingArray( collections.getCanceledListings().toArray( new InsertableListing[0] ) );
		collections.setCompletedListingArray( collections.getCompletedListings().toArray( new InsertableListing[0] ) );
	}
	
	void initializeMessages( DataCollections collections ) {
	
		// id counter for payments
		int k = 1;
		
		// keep these keys from colliding with the listing messages' keys
		for( int i = 10000; i < 200000; i++ ) {
//			String replyToListingType = "CANCELED";
//			double replyToListingRandom = Math.random();
//			if( replyToListingRandom > .98 ) {
//				replyToListingType = "COMPLETED";
//			} else if( replyToListingRandom > .8 ) {
//				replyToListingType = "ACTIVE";
//			}
			
			Long id = new Long( i );
			double replyStatusRandom = Math.random();
			if( replyStatusRandom > .2 ) {
				User user = new InsertableUser( null, new RandomTwitterUser(), null );
				
				if( replyStatusRandom > .5 ) {
					// 50% are irrelevant replies
					collections.getIrrelevantMessages().add( this.createAlreadyProcessedMessage( id, collections, MessageStatus.IRRELEVANT, user, false, null ) );
				} else {
					// Other 30% are pending registration
					collections.getPendingRegistrationMessages().add( this.createAlreadyProcessedMessage( id, collections, MessageStatus.PENDING_USER_REGISTRATION, user, true, null ) );
				}
				
			} else {
				InsertableUser user = this.chooseRandomUser( collections.getUsers() );
				
				if( replyStatusRandom > .1 ) {
					// 10% are already processed pending payment
					collections.getPendingPaymentMessages().add( this.createAlreadyProcessedMessage( id, collections, MessageStatus.PENDING_MEANS_OF_PAYMENT, user, true, null ) );
				} else if( replyStatusRandom > .09 ) {
					// 1% are already processed preapproved user replies to canceled
					collections.getRepliesToCanceledMessages().add( this.createAlreadyProcessedMessage( id, collections, MessageStatus.FAILED_INACTIVE_SALE, user, true, null ));
				} else if( replyStatusRandom > .08 ) {
					// 1% are already processed preapproved user replies to sold out
					collections.getRepliesToSoldOutMessages().add( this.createAlreadyProcessedMessage( id, collections, MessageStatus.FAILED_SOLD_OUT, user, true, null ));
				} else if( replyStatusRandom > .03 ) {
					// 5% are already processed messages that resulted in a purchase / opt-in 
					collections.getQualifiedRepliesToActiveListingMessages().add( this.createAlreadyProcessedMessage( id, collections, MessageStatus.COMPLETED, user, true, new Long( k ) ));
					k = k + 1;
				} else if( replyStatusRandom > .02 ) {
					// 1% are unblocked pending registration -- associate this message with an existing user but strip out the user's id to simulate a message that was inserted before user registration
					RandomTwitterUser randomTwitterUser = user.getRandomTwitterUser();
					InsertableUser newlyRegisteredUser = new InsertableUser( null, randomTwitterUser, null );
					collections.getUnblockedPendingPaymentRegistrationMessages().add( this.createUnblockedMessage( id, collections, MessageStatus.PENDING_USER_REGISTRATION, newlyRegisteredUser  ));
				} else if( replyStatusRandom > .01 ) {
					// 1% are unblocked pending payment
					InsertablePreapproval preapproval = this.getRandomPreapproval( collections.getActivePreapprovals() );
					User preapprovedUser = preapproval.getUser();
					collections.getPendingPaymentMessages().add( this.createAlreadyProcessedMessage( id, collections, MessageStatus.PENDING_MEANS_OF_PAYMENT, preapprovedUser, true, null ));
				} else {
					// 1% are unprocessed messages that should result in a purchase / opt-in
					InsertablePreapproval preapproval = this.getRandomPreapproval( collections.getActivePreapprovals() );
					User preapprovedUser = preapproval.getUser();
					collections.getQualifiedRepliesToActiveListingMessages().add( this.createUnprocessedMessage( id, collections, MessageStatus.PROCESSING, preapprovedUser ));
				}
			}
		}
		
		List<InsertableMessage> allReplyMessages = this.appendMessageLists( collections.getIrrelevantMessages(),
												  collections.getPendingRegistrationMessages(),
												  collections.getPendingPaymentMessages(), 
												  collections.getRepliesToCanceledMessages(),
												  collections.getRepliesToSoldOutMessages(),
												  collections.getQualifiedRepliesToActiveListingMessages(), 
												  collections.getUnblockedPendingPaymentRegistrationMessages(), 
												  collections.getUnblockedPendingRegistrationMessages() );	
		collections.setAllReplyMessages( allReplyMessages );
	}
	
	List<InsertableMessage> appendMessageLists( List<InsertableMessage>... lists ) {
		List<InsertableMessage> result = new ArrayList<InsertableMessage>();
		
		for( List<InsertableMessage> list : lists ) {
			result.addAll( list );
		}
		
		return result;
	}
	
	InsertablePreapproval getRandomPreapproval( List<InsertablePreapproval> activePreapprovals ) {
		return activePreapprovals.get( this.randomIntGenerator.nextInt( activePreapprovals.size() ) );
	}
	
	Role createRole( Long id ) {
		Role role = new Role();
		ReflectionTestUtils.setField( role, "id", id );
		
		return role;
	}
	
	Integer sumOfSellersListings( Map<Integer, List<InsertableListing>> sellersListings ) {
		
		int sum = 0;
		for( Integer userId : sellersListings.keySet() ) {
			List<InsertableListing> listings = sellersListings.get( userId.intValue() );
			sum = sum + listings.size();
		}
		
		return sum;
	}
	
	InsertableListing[] appendAllArrays( Map<Integer, List<InsertableListing>> listingsMap ) {
		InsertableListing[] result = new InsertableListing[0];
		
		for( Integer userId : listingsMap.keySet() ) {
			List<InsertableListing> listings = listingsMap.get( userId.intValue() );
			InsertableListing[] listingsArray = listings.toArray( new InsertableListing[0]);
			result = this.append( result, listingsArray );
		}
		
		return result;
	}
	
	InsertableListing[] append(InsertableListing[] a1, InsertableListing[] a2) {
	    InsertableListing[] a1a2 = Arrays.copyOf(a1, a1.length + a2.length);
	    for (int i = a1.length; i < a1a2.length; i++) {
	        a1a2[i] = a2[i - a1.length];
	    }
	    return a1a2;
	}
	
	InsertableMessage createAlreadyProcessedMessage( Long messageId, DataCollections collections, MessageStatus status, User user, Boolean containsKeyword, Long paymentId ) {
		InsertableListing messageInReplyToListing = this.randomListingMessage(collections);
		String inResponseToTwitterId = messageInReplyToListing.getMessages().iterator().next().getTwitterId();
		InsertableMessage message = new InsertableMessage( messageId, RandomStringUtils.randomNumeric( 18 ), inResponseToTwitterId, user, status, "ALREADY_BATCH_PROCESSED", containsKeyword );
		
		// TODO I don't think this logic is working -- I think we are getting payments against campaigns
		if( status.equals( MessageStatus.COMPLETED ) && !messageInReplyToListing.getType().equals( ListingType.CAMPAIGN )) {
			// add a payment to the collection
			InsertablePayment payment = new InsertablePayment( paymentId, user, messageInReplyToListing, message );
			collections.getAllPayments().add( payment );
		}
		return message;
	}
	
	InsertableMessage createUnprocessedMessage( Long id, DataCollections collections, MessageStatus status, User user ) {
		// Get an active message
		InsertableListing listing = this.chooseRandomListing( collections.getActiveListingArray() );
		String inResponseToTwitterId = listing.getMessages().iterator().next().getTwitterId();
		return new InsertableMessage( id, RandomStringUtils.randomNumeric( 18 ), inResponseToTwitterId, user, status, null, true );
	}

	InsertableMessage createUnblockedMessage( Long id, DataCollections collections, MessageStatus status, User user ) {
		InsertableListing messageInReplyToListing = this.randomListingMessage(collections);
		String inResponseToTwitterId = messageInReplyToListing.getMessages().iterator().next().getTwitterId();
		return new InsertableMessage( id, RandomStringUtils.randomNumeric( 18 ), inResponseToTwitterId, user, status, "ALREADY_BATCH_PROCESSED", true );
	}
	
	InsertableListing chooseRandomListing( InsertableListing[] listings ) {
		int index = this.randomIntGenerator.nextInt( listings.length - 1 );
		if( index < 0 ) { index = 0; }
		return listings[ index ];
	}
	
	InsertableUser chooseRandomUser( InsertableUser[] users ) {
		InsertableUser result = null;
		while( result == null ) {
			result = users[ this.randomIntGenerator.nextInt( users.length - 1 ) ];
		}
		return result;
	}
	
	InsertableUserConnection chooseRandomUserConnection( InsertableUserConnection[] userConnections ) {
		return userConnections[ this.randomIntGenerator.nextInt( userConnections.length - 1 ) ];
	}
	
	InsertableListing randomListingMessage(DataCollections collections) {
		InsertableListing messageInReplyToListing;
		double listingStatusRandom = Math.random();
		if( listingStatusRandom > .2 ) {
			messageInReplyToListing = this.chooseRandomListing( collections.getCanceledListingArray() );
		} else if( listingStatusRandom > .02 ) {
			messageInReplyToListing = this.chooseRandomListing( collections.getActiveListingArray() );
		} else {
			messageInReplyToListing = this.chooseRandomListing( collections.getCompletedListingArray() );
		}
		return messageInReplyToListing;
	}

}
