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
package com.projectlaver.integration;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.social.twitter.api.UserOperations;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.projectlaver.test.config.SocialTestConfig;
import com.projectlaver.test.config.TestConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class TwitterIntegrationTest {

	@Autowired
	SocialTestConfig socialTestConfig;

	@Test
	public void testSendPublicReply() {
	
		// App credentials
		String hockingbirdApiKey = "";
		String hockingbirdApiSecret = "";
		
		// Seller credentials (@regtestnancy)
		String sellerAccessToken = "";
		String sellerSecret = "";
		
		// Tweet mentionee
		Long destinationProviderUserId = 622133588L;
		String formattedMessage = "Congratulations! You won a giveaway from @regtestnancy - please input your shipping address here: https://www.myhootit.com/listing/paymentDetail?id=3005";
		String replyToMessageTwitterId = "472496154278510592";
		
		try {
			this.sendPublicReply( hockingbirdApiKey,
								  hockingbirdApiSecret,
								  sellerAccessToken,
								  sellerSecret,
								  destinationProviderUserId,
								  formattedMessage,
								  replyToMessageTwitterId );
		} catch( Exception e ) {
			System.out.println( "Send public reply failed with exception: " + e );
		}
	}
	
	@Test
	public void testPostTweet() throws Exception {
		
		// linked to the app called "Hoot it" owned by @hootit
		String apiKey = "";
		String apiSecret = "";
		
		// linked to @regtestnancy
		String senderAccessToken = "";
		String senderSecret = "";
		
		Twitter api = new TwitterTemplate( apiKey, apiSecret, senderAccessToken, senderSecret );
		TimelineOperations to = api.timelineOperations();
		TweetData tweetData = new TweetData( "If this works Reply '#luckyme' to purchase for $1.00 via @hootit https://rodlaver.elasticbeanstalk.com/listing/listingDetail/33116" );
		
		try {
			to.updateStatus( tweetData );
		} catch( Exception e ) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void testGetLatestMentions() throws Exception {
		
		TwitterTemplate bobTemplate = new TwitterTemplate(
				this.socialTestConfig.getHootitTwitterConsumerKey(), 
				this.socialTestConfig.getHootitTwitterSecret(),
				this.socialTestConfig.getFredTwitterAccessToken(), 
				this.socialTestConfig.getFredTwitterSecret() 
			);
		
		List<Tweet> mentions = bobTemplate.timelineOperations().getMentions();
		
		for( Tweet tweet : mentions ) {
			System.out.println( "Mention from: " + tweet.getFromUser() );
			System.out.println( "Tweet text: " + tweet.getText() );
		}
		
	}
	
	Boolean sendPublicReply( String applicationApiKey,
						 String applicationApiSecret,
						 String senderAccessToken,
						 String senderSecret,
						 Long recipientTwitterId,
						 String formattedMessage,
						 String messageTwitterId ) throws Exception {
		
		Twitter twitterApi = new TwitterTemplate( applicationApiKey, applicationApiSecret, senderAccessToken, senderSecret );
		TwitterProfile profile = twitterApi.userOperations().getUserProfile( recipientTwitterId );
		
		StringBuilder sb = new StringBuilder( "@" );
		sb.append( profile.getScreenName() );
		sb.append( " " );
		sb.append( formattedMessage );

		TweetData tweetData = new TweetData( sb.toString() );
		tweetData.inReplyToStatus( Long.valueOf( messageTwitterId ) );
		TimelineOperations tlo = twitterApi.timelineOperations();
		Tweet tweet = tlo.updateStatus( tweetData );
		
		return  ( tweet != null );
		
		// Wrote the code below to test whether a single twitterApi instance could be reused 
		// to send multiple tweets from the same account (in this case, @regtestnancy )
		
		/**
		TwitterProfile profile = twitterApi.userOperations().getUserProfile( 635751915L ); // @regtestfred
		TweetData tweetData = new TweetData( "@regtestfred you rock!" );
		TimelineOperations tlo = twitterApi.timelineOperations();
		Tweet tweet = tlo.updateStatus(tweetData);
		
		Thread.sleep( 5000 );
		
		TwitterProfile profile2 = twitterApi.userOperations().getUserProfile( 622133588 ); // @regtestbob
		TweetData tweetData2 = new TweetData( "@regtestbob you rule!" );
		TimelineOperations tlo2 = twitterApi.timelineOperations();
		Tweet tweet2 = tlo.updateStatus( tweetData2 );

		return  ( tweet != null && tweet2 != null );
		*/
	}
	
}
