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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PageOperations;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.JsonNode;
import com.projectlaver.batch.domain.ListingStateChangeCursorItem;

public class FacebookListingPostingItemProcessor extends ListingPostingItemProcessor
	implements ItemProcessor<ListingStateChangeCursorItem, ListingStateChangeCursorItem> {

	/**
	 * Instance variables
	 */
	
	private final Log logger = LogFactory.getLog(getClass());

	private String schema;

	@Value("${aws.s3.access.key}")
	private String s3accessKey;

	@Value("${aws.s3.secret.key}")
	private String s3secretKey;

	@Value("${aws.s3.public.bucket.name}")
	private String s3publicBucketName;
	
	/**
	 * Static variables
	 */

	private static String INSERT_REPLY_PAGING_STATUS_SQL =
			  " INSERT INTO ReplyPagingStatuses "
			+ " SET version = ?, "
			+ "   status = ?, "
			+ "   listing_message_id = ?, "
			+ "   providerId = ?, "
			+ "   providerContentId = ? ";

	private static final String PAGE_SIZE_LIMIT_KEY = "limit";
	private static final String PAGE_SIZE_LIMIT = "1";

	private static final String FILTER_TYPE_KEY = "filter";
	private static final String STREAM_FILTER = "stream";

	private static final String ACCESS_TOKEN_KEY = "access_token";
	
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
			
			// Output the item for debugging purposes, but suppress the access token value
			String itemRaw = ToStringBuilder.reflectionToString( item );
			String accessTokenSuppressed = StringUtils.replacePattern( itemRaw, "accessToken=[0-9a-zA-Z]*,", "accessToken={protected},");
			this.logger.debug( String.format( "Entered the process method with item: %s", accessTokenSuppressed ));
		}

		// Create the caption
		String caption = super.formatFacebookListingMessageContent( item.getListingType(),
															item.getListingKeyword(),
															item.getListingAmount(),
															item.getAnnouncementPreamble(),
															item.getListingId(),
															item.getLocale() );
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Formatted facebook listing message is: '" + caption + "'");
		}

		File tempFile = null;
		try {

			String filename = item.getImageFilename();
			
			tempFile = this.createTempFile( filename );
			
			// Get the image from AWS
			this.copyS3ObjectToTempFile(tempFile, filename);

			Resource photo = new FileSystemResource( tempFile );

			Facebook api = new FacebookTemplate( item.getAccessToken() );
			PageOperations po = api.pageOperations();

			// Post the photo and get the ID. 
			// Note that the photo id is not the same as the facebook content id that we'll use to retrieve comments
			String photoId = po.postPhoto( item.getFacebookPageId(), item.getFacebookAlbumId(), photo, caption );
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Uploaded a photo to Facebook with id: " + photoId );
			}

			JsonNode facebookPostJson = this.getLatestFacebookPost( api.restOperations(), item.getFacebookPageId() );

			// get the content id
			String contentId = facebookPostJson.findValue( "id" ).toString().replaceAll( "\"", "" );
			
			// confirm the content id refers to our post
			Boolean isContentIdConfirmed = this.confirmContentIdIsListing( api.restOperations(), contentId, caption, photoId );
			
			if( !isContentIdConfirmed ) {
				
				this.logger.error( "Unable to correlate the facebook content id: " + contentId + " with the photoId: " + photoId + 
						" in the context of our listing with id: " + item.getListingId() + ". Taking compensating actions.");
				
				// this means we didn't get the right content id corresponding with the photo we just posted.
				// to compensate, delete the photo using the photoId
				this.deletePhoto( api, photoId );
				
				// throw an exception
				throw new RuntimeException( "Could not correlate newly posted Facebook photo with the latest Page content id." );
			}
			
			// get the content href and remove the surrounding double quotes (")
			String facebookContentLink = facebookPostJson.findValue( "link" ).toString();
			facebookContentLink = facebookContentLink.substring( 1, facebookContentLink.length() - 1 );

			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "The Facebook post content id is: " + contentId );
				this.logger.debug( "The facebook contentLink is: " + facebookContentLink );
			}

			// insert the listing message and get the row's id
			Long listingMessageId = super.insertListingMessage( item, caption, contentId, facebookContentLink );

			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Inserted the listing message with id: " + listingMessageId );
			}

			// insert a row into reply_paging_status to enable paged batch fetching of comments
			JdbcTemplate jdbcTemplate = new JdbcTemplate( super.getDataSource() );
			int rows = jdbcTemplate.update( INSERT_REPLY_PAGING_STATUS_SQL,
											new Object[] {
												0, // version
												"IDLE", // status
												listingMessageId, // listingMessageId
												item.getProviderId(), // providerId
												contentId // providerContentId
											} );

			item.setDidPostingComplete( ( listingMessageId != null ) && ( rows == 1 ) );
			
			// we have to set this field because the writer will set it to "null" otherwise.
			item.setNewListingStateChangeUpdatedStatus( "PENDING" );
			
		} catch( Exception e ) {
			
			this.logger.error( "Exception while trying to post listing: " + item.getListingId() + " to facebook.", e);
			
			// Could not confirm the contentId. Mark this item as set aside
			// without a listing message.
			item.setDidPostingComplete( false );
			item.setNewListingStateChangeUpdatedStatus( "POSTING-ERROR" );
		
		} finally {

			// cleanup the temp file
			if( tempFile != null ) {
				tempFile.delete();
			}
		}
		
		return item;
	}

	@Override
	protected String getSaleMessageKey() {
		return "saleForm.facebook.template.sale";
	}

	@Override
	protected String getDonationMessageKey() {
		return "saleForm.facebook.template.donation";
	}

	@Override
	protected String getCampaignMessageKey() {
		return "saleForm.facebook.template.opt-in-campaign";
	}
	
	Boolean confirmContentIdIsListing( RestOperations ro, String contentId, String caption, String photoId ) {

		// Fetch the object using the contentId
		JsonNode contentNode = ro.getForObject( Facebook.GRAPH_API_URL + "{contentId}", JsonNode.class, contentId );
		String contentMessage = contentNode.findValue( "message" ).asText();
		String contentObjectId = contentNode.findValue( "object_id" ).asText();
		
		// Return true if the caption and photoId match
		return StringUtils.equals( caption, contentMessage ) && StringUtils.equals( photoId, contentObjectId );
	}
	
	JsonNode getLatestFacebookPost( RestOperations ro, String pageId ) throws InterruptedException {
		
		// Introduce a slight delay here so that when we query for the latest post the 
		// eventual consistency is likely to have caught up
		Thread.sleep( 2000 );
		
		// Get the content id with the expectation that it's the topmost post on this page
		JsonNode responseNode = ro.getForObject( Facebook.GRAPH_API_URL + "{facebookPageId}/posts?limit=1&filter=stream", JsonNode.class, pageId );
		JsonNode dataNode = responseNode.path( "data" );
		
		return dataNode;
	}
	
	void deletePhoto( Facebook api, String photoId ) {
		this.logger.error( "+deletePhoto() with photoId: " + photoId );
		
		api.delete( photoId );
		this.logger.error( "-deletePhoto()" );
	}
	
	File createTempFile(String filename) throws IOException {
		int dotSeparator = filename.indexOf( "." );
		String filePrefix = filename.substring( 0, dotSeparator );
		String fileSuffix = filename.substring( dotSeparator + 1 );
		File tempFile = File.createTempFile( filePrefix, fileSuffix );
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "File prefix: " + filePrefix + ", file suffix: " + fileSuffix );
		}
		return tempFile;
	}
	
	void copyS3ObjectToTempFile(File tempFile, String filename) throws IOException, FileNotFoundException {
		AWSCredentials myCredentials = new BasicAWSCredentials( this.s3accessKey, this.s3secretKey );
		AmazonS3 s3 = new AmazonS3Client( myCredentials );
		S3Object object = s3.getObject( this.s3publicBucketName, filename );

		IOUtils.copy( object.getObjectContent(), new FileOutputStream( tempFile ) );
	}

}
