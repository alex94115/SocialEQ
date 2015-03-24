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
package com.projectlaver.batch;

import java.net.URI;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.social.support.URIBuilder;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.projectlaver.batch.domain.ListingStateChangeCursorItem;

public class TwitterListingPostingItemProcessor extends ListingPostingItemProcessor 
	implements ItemProcessor<ListingStateChangeCursorItem, ListingStateChangeCursorItem> {

	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
	private DataSource datasource;
	
	private String schema;
	
	@Value("${communications.baseListingDetailUrl}")
	private String baseListingDetailUrl;
	
	@Value("${twitter.consumerKey}")
	private String twitterConsumerKey;

	@Value("${twitter.consumerSecret}")
	private String twitterConsumerSecret;
	
	// Static constants
	private static final String END_BlOCKQUOTE_HTML = "</blockquote>";
	
	/**
	 * Setter injection of the schema system property.
	 * @param schema
	 */
	public void setSchema( String schema ) {
		this.schema = schema;
	}
	
	@Override
	public ListingStateChangeCursorItem process( ListingStateChangeCursorItem item ) throws Exception {

		if( this.logger.isDebugEnabled() ) {
			
			// Output the item for debugging purposes, but suppress the access token and secret
			String itemRaw = ToStringBuilder.reflectionToString( item );
			String accessTokenSuppressed = StringUtils.replacePattern( itemRaw, "accessToken=.*didPostingComplete", "accessToken={protected},destinationSecret={protected},didPostingComplete");
			this.logger.debug(  String.format( "Entered the process method with item: %s", accessTokenSuppressed ));
		}
		
		// Create the tweet and post it to the lister's timeline
		String finalTweetContent = super.formatTwitterListingMessageContent( item.getListingType(), 
																		item.getDisplayName(),
																		item.getListingKeyword(), 
																		item.getListingAmount(), 
																		item.getAnnouncementPreamble(), 
																		item.getListingId(), 
																		item.getLocale() );
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Formatted twitter listing message is: '" + finalTweetContent + "'");
		}

		
		
		Twitter twitter = new TwitterTemplate( this.twitterConsumerKey, this.twitterConsumerSecret, item.getAccessToken(), item.getDestinationSecret() );
		TweetData tweetData = new TweetData( finalTweetContent );
		Tweet tweeted = twitter.timelineOperations().updateStatus( tweetData );
		
		String contentId = Long.toString( tweeted.getId() );
		String embedTweetHtml = this.getEmbedTweetHtml( twitter.restOperations(), contentId );
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Posted the tweet with id: " + tweeted.getId() );
			this.logger.debug( "Embeded tweet html is: " + embedTweetHtml ); 
		}
		
		// insert the listing message row with the FK to the listing
		Long listingMessageId = super.insertListingMessage( item, tweeted.getText(), contentId, embedTweetHtml );
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Inserted the listing message with id: " + listingMessageId );
		}
		
		item.setDidPostingComplete( listingMessageId != null );
		
		return item;
	}
	
	@Override
	protected String getSaleMessageKey() {
		return "saleForm.tweet.template.sale";
	}

	@Override
	protected String getDonationMessageKey() {
		return "saleForm.tweet.template.donation";
	}

	@Override
	protected String getCampaignMessageKey() {
		return "saleForm.tweet.template.opt-in-campaign";
	}
	
	String getEmbedTweetHtml( RestOperations ro, String contentId ) {
		
		JsonNode responseNode = ro.getForObject( "https://api.twitter.com/1/statuses/oembed.json?id={contentId}", JsonNode.class, contentId );
		String embedTweetHtml = responseNode.path( "html" ).toString();

		// get rid of the escaped double quotes 
		embedTweetHtml = embedTweetHtml.replace( "\\\"", "\"");
				
		// substring the link to drop the script tag, which follows </blockquote>, and the surrounding double quotes
		embedTweetHtml = embedTweetHtml.substring( 
				1, embedTweetHtml.indexOf( END_BlOCKQUOTE_HTML ) + END_BlOCKQUOTE_HTML.length() );
		
		return embedTweetHtml;
	}

}
