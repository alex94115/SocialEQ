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


import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Date;

import javax.sql.DataSource;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.IArgumentMatcher;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;

import com.projectlaver.integration.SocialProviders;

import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.User;
import twitter4j.UserMentionEntity;

@RunWith(EasyMockRunner.class)
public class TwitterListingResponseHandlerTest extends EasyMockSupport {
	
	private static final String REPLY_USER_ID = "33333";
	private static final String STATUS_ID = "1111111111111";
	private static final String STATUS_REPLY_TO_ID = "99999999999999";
	private static final String VIRTUAL_REPLY_CONTENT_ID = "5555555555555555";
	private static final String SELLER_ID = "12345";
	private static final String REPLY_TEXT = "#first #second @usermention @secondmention";

	@TestSubject
	private TwitterListingResponseHandler responseProcessor = new TwitterListingResponseHandler();

	@Mock(type=MockType.NICE, name="dataSource")
	private DataSource dataSource;

	@Mock(type=MockType.STRICT, name="jdbcTemplate")
	private JdbcTemplate mockJdbcTemplate;
	
	@Mock(type=MockType.NICE)
	private User user;
	
	@Mock(type=MockType.NICE)
	private Status status;
	
	@Mock(type=MockType.STRICT)
	private UserMentionEntity firstMention;
	
	@Mock(type=MockType.STRICT)
	private UserMentionEntity secondMention;
	
	private UserMentionEntity[] emptyUserMentionEntities = new UserMentionEntity[] {};
	
	@Mock(type=MockType.STRICT)
	private HashtagEntity firstHashtag;
	
	@Mock(type=MockType.STRICT)
	private HashtagEntity secondHashtag;
	
	private HashtagEntity[] emptyHashtagEntities = new HashtagEntity[] {};
	
	@Test
	public void testProcessStatusWithoutUserMention() {
		
		this.initializeMockUser();
		
		Boolean isNaturalReply = true;
		Boolean isRetweet = false;
		this.initializeMockStatus( user, isNaturalReply, isRetweet, new Date(), false );

		/**
		 * This test sets up no user mentions in the tweet status
		 */
		UserMentionEntity[] mentionEntities = this.setupWithoutUserMentionEntities();
		expect( status.getUserMentionEntities() ).andReturn( mentionEntities );
		
		this.initializeHashtagEntities();
		HashtagEntity[] hashtagEntities = new HashtagEntity[] { firstHashtag, secondHashtag };
		expect( status.getHashtagEntities() ).andReturn( hashtagEntities );
		
		// replay mode
		replayAll();
		
		// Exepect no calls to the jdbcTemplate, since we won't persist a tweet without a user mention
		this.responseProcessor.processStatus( status );
		
		verifyAll();
	}
	
	@Test
	public void testProcessStatusWithoutHashtagMention() {
		
		this.initializeMockUser();
		
		Boolean isNaturalReply = true;
		Boolean isRetweet = false;
		this.initializeMockStatus( user, isNaturalReply, isRetweet, new Date(), false );

		this.initializeUserMentionEntities();
		UserMentionEntity[] userMentionEntities = new UserMentionEntity[] { firstMention, secondMention };
		expect( status.getUserMentionEntities() ).andReturn( userMentionEntities );
		
		/**
		 * This test sets up no hashtag mentions in the tweet status
		 */
		HashtagEntity[] hashtagEntities = this.setupWithoutHashtagEntities();
		expect( status.getHashtagEntities() ).andReturn( hashtagEntities );
		
		// replay mode
		replayAll();
		
		// Exepect no calls to the jdbcTemplate, since we won't persist a tweet without a hashtag mention
		this.responseProcessor.processStatus( status );
		
		verifyAll();
	}
	
	@Test
	public void testProcessNaturalReplyStatus() {
		
		this.initializeMockUser();

		Boolean isNaturalReply = true;
		Boolean isRetweet = false;
		Date created = new Date();
		this.initializeMockStatus( user, isNaturalReply, isRetweet, created, true );

		this.initializeUserMentionEntities();
		UserMentionEntity[] userMentionEntities = new UserMentionEntity[] { firstMention, secondMention };
		expect( status.getUserMentionEntities() ).andReturn( userMentionEntities );

		this.initializeHashtagEntities();
		HashtagEntity[] hashtagEntities = new HashtagEntity[] { firstHashtag, secondHashtag };
		expect( status.getHashtagEntities() ).andReturn( hashtagEntities );

		
		// have the "correlate virtual reply" method return null 
		String lookupSql = TwitterListingResponseHandler.SELECT_LISTING_MESSAGE_TWITTER_ID_BY_SELLER_AND_HASHTAG;
		Object[] lookupParams = new Object[] { "12345", "#first" };
		expect( this.mockJdbcTemplate.queryForObject( eq( lookupSql ), aryEq( lookupParams), isA(String.class.getClass()))).andReturn( null );
		
		// expect this status will be inserted with isNaturalReply set to true
		String insertSql = RodlaverQueries.INSERT_MESSAGE_AND_LOOKUP_USER_ID_FK;
		Object[] insertParams = new Object[] {
				created, // message.getCreated(),
				STATUS_REPLY_TO_ID, // message.getInResponseToTwitterId(),
				isNaturalReply, // message.getIsNaturalReply(),
				isRetweet, // message.getIsRetweet(),
				SocialProviders.TWITTER, // message.getProviderId(),
				REPLY_USER_ID, // message.getProviderUserId(),
				REPLY_TEXT, // message.getText(),
				"#first", // message.getHashtag(),
				STATUS_ID, // message.getTwitterId(),
				SocialProviders.TWITTER, // message.getProviderId(),
				REPLY_USER_ID, // message.getProviderUserId(),
				STATUS_REPLY_TO_ID, // message.getInResponseToTwitterId(),
				STATUS_REPLY_TO_ID, // message.getInResponseToTwitterId() 
		};

		//expect( this.mockJdbcTemplate.update( eq( insertSql ), eqInsertMessage( insertParams ))).andReturn( 1 );
		expect( this.mockJdbcTemplate.update( RodlaverQueries.INSERT_MESSAGE_AND_LOOKUP_USER_ID_FK, insertParams )).andReturn( 1 );
		
		// replay mode
		replayAll();
		
		this.responseProcessor.processStatus( status );
		
		verifyAll();
	}

	@Test
	public void testProcessVirtualReplyStatus() {
		
		// setup mocks
		this.initializeMockUser();

		Boolean isNaturalReply = false;
		Boolean isRetweet = false;
		Date created = new Date();
		this.initializeMockStatus( user, isNaturalReply, isRetweet, created, true );

		this.initializeUserMentionEntities();
		UserMentionEntity[] userMentionEntities = new UserMentionEntity[] { firstMention, secondMention };
		expect( status.getUserMentionEntities() ).andReturn( userMentionEntities );

		this.initializeHashtagEntities();
		HashtagEntity[] hashtagEntities = new HashtagEntity[] { firstHashtag, secondHashtag };
		expect( status.getHashtagEntities() ).andReturn( hashtagEntities );
		
		// have the "correlate virtual reply" method return null 
		String lookupSql = TwitterListingResponseHandler.SELECT_LISTING_MESSAGE_TWITTER_ID_BY_SELLER_AND_HASHTAG;
		Object[] lookupParams = new Object[] { "12345", "#first" };
		expect( this.mockJdbcTemplate.queryForObject( eq( lookupSql ), aryEq( lookupParams), isA(String.class.getClass()))).andReturn( VIRTUAL_REPLY_CONTENT_ID );
		
		// expect this status will be inserted with isNaturalReply set to false
		String insertSql = RodlaverQueries.INSERT_MESSAGE_AND_LOOKUP_USER_ID_FK;
		Object[] insertParams = new Object[] {
				created, // message.getCreated(),
				VIRTUAL_REPLY_CONTENT_ID, // message.getInResponseToTwitterId(),
				isNaturalReply, // message.getIsNaturalReply(),
				isRetweet, // message.getIsRetweet(),
				SocialProviders.TWITTER, // message.getProviderId(),
				REPLY_USER_ID, // message.getProviderUserId(),
				REPLY_TEXT, // message.getText(),
				"#first", // message.getHashtag(),
				STATUS_ID, // message.getTwitterId(),
				SocialProviders.TWITTER, // message.getProviderId(),
				REPLY_USER_ID, // message.getProviderUserId(),
				VIRTUAL_REPLY_CONTENT_ID, // message.getInResponseToTwitterId(),
				VIRTUAL_REPLY_CONTENT_ID, // message.getInResponseToTwitterId() 
		};

		//expect( this.mockJdbcTemplate.update( eq( insertSql ), eqInsertMessage( insertParams ))).andReturn( 1 );
		expect( this.mockJdbcTemplate.update( RodlaverQueries.INSERT_MESSAGE_AND_LOOKUP_USER_ID_FK, insertParams )).andReturn( 1 );
		
		// replay mode
		replayAll();
		
		this.responseProcessor.processStatus( status );
		
		verifyAll();
	}
	

	@Test
	public void testGetMentionedHashtag() {
		
		// setup mocks
		this.initializeHashtagEntities();
		
		// replay mode
		replayAll();
		
		// test
		HashtagEntity[] hashtagEntities = new HashtagEntity[] { firstHashtag, secondHashtag };
		String mentionedHashtag = this.responseProcessor.getMentionedHashtag( hashtagEntities );
		assertEquals( "Only the first mentioned hashtag is expected, along with a prepended #", "#first", mentionedHashtag );
 	}

	@Test
	public void testGetMentionedProviderUserId() {

		// setup mocks
		this.initializeUserMentionEntities();
		
		// replay mode
		replayAll();
		
		// test
		UserMentionEntity[] userMentionEntities = new UserMentionEntity[] { firstMention, secondMention };
		String mentionedProviderUserId = this.responseProcessor.getMentionedProviderUserId( userMentionEntities );
		assertEquals( "Only the first mentioned user in the tweet is considered.", "12345", mentionedProviderUserId );
		
		verifyAll();
	}

	@Test
	public void testLongToString() {
		long value = 100L;
		String actual = responseProcessor.longToString( value );
		assertEquals( "Convert a long to string", "100", actual );
	}
	
	HashtagEntity[] setupWithoutHashtagEntities() {
		return new HashtagEntity[] {};
	}
	
	void initializeHashtagEntities() {
		expect( firstHashtag.getText() ).andReturn( "first" ).anyTimes();
		expect( secondHashtag.getText() ).andReturn( "second" ).anyTimes();
	}
	
	UserMentionEntity[] setupWithoutUserMentionEntities() {
		return new UserMentionEntity[] {};
	}
	
	void initializeUserMentionEntities() {
		expect( this.firstMention.getId() ).andReturn( Long.valueOf(SELLER_ID) ).anyTimes();
		expect( this.secondMention.getId() ).andReturn( 98765L ).anyTimes();
	}
	
	void initializeMockStatus( User user, Boolean isNaturalReply, Boolean isRetweet, Date createdAt, Boolean doesContainMentionAndHashtag ) {
		expect( this.status.getId() ).andReturn( Long.valueOf( STATUS_ID ) );
		expect( this.status.getText() ).andReturn( REPLY_TEXT );
		
		// If it's a natural reply, return an id. Otherwise return null.
		expect( this.status.getInReplyToStatusId() ).andReturn( isNaturalReply ? Long.valueOf( STATUS_REPLY_TO_ID ): -1L );
		expect( this.status.getUser() ).andReturn( user );
		
		// If it's a retweet, return another mock status. Otherwise return null.
		expect( this.status.getRetweetedStatus() ).andReturn( isRetweet ? createMock( "status", MockType.NICE, Status.class ) : null );
		
		if( doesContainMentionAndHashtag ) {
			expect( this.status.getCreatedAt() ).andReturn( createdAt );
		}
	}

	void initializeMockUser() {
		expect( this.user.getId() ).andReturn( Long.valueOf( REPLY_USER_ID ));
	}
	
	<T extends Object> T eqInsertMessage(T[] in) {
	    reportMatcher(new InsertMessageMatcher(in));
	    return null;
	}
	
	class InsertMessageMatcher implements IArgumentMatcher {
		
		private Object[] expected;
		
		InsertMessageMatcher( Object[] expected ) {
			this.expected = expected;
		}

		@Override
		public void appendTo(StringBuffer buffer) {
	        buffer.append("eqSqk("); 
	        buffer.append(expected); 
	        buffer.append(")\""); 
		}

		@Override
		public boolean matches(Object other ) {
			return expected.equals( other );
		}
	}

}
