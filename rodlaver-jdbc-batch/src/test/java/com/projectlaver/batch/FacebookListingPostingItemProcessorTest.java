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

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Locale;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.StepRunner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.social.facebook.api.Comment;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookObject;
import org.springframework.social.facebook.api.FeedOperations;
import org.springframework.social.facebook.api.GraphApi;
import org.springframework.social.facebook.api.MediaOperations;
import org.springframework.social.facebook.api.OpenGraphOperations;
import org.springframework.social.facebook.api.PageOperations;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Photo;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.PostData;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.support.URIBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.JsonNode;
import com.projectlaver.batch.domain.ListingStateChangeCursorItem;
import com.projectlaver.test.config.SocialTestConfig;
import com.projectlaver.test.config.TestConfig;
import com.projectlaver.util.ListingType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class FacebookListingPostingItemProcessorTest {

	@Autowired
	SocialTestConfig socialTestConfig;
	
	// facebook
	private static final String PAGE_SIZE_LIMIT_KEY = "limit";
	private static final String PAGE_SIZE_LIMIT = "1";
	private static final String FILTER_TYPE_KEY = "filter";
	private static final String STREAM_FILTER = "stream";
	private static final String ACCESS_TOKEN_KEY = "access_token";

	
	@Test
	public void testPostPhotoTest() throws Exception {

		// Setup objects
		FacebookListingPostingItemProcessor processor = new FacebookListingPostingItemProcessor();

		ReflectionTestUtils.setField( processor, "s3accessKey", this.socialTestConfig.getS3AccessKey() );
		ReflectionTestUtils.setField( processor, "s3secretKey", this.socialTestConfig.getS3SecretKey() );
		ReflectionTestUtils.setField( processor, "s3publicBucketName", this.socialTestConfig.getS3PublicBucketName() );
		
		ListingStateChangeCursorItem item = new ListingStateChangeCursorItem();
		item.setFacebookPageId( this.socialTestConfig.getFredFacebookPageId() );
		item.setFacebookAlbumId( this.socialTestConfig.getFredFacebookAlbumId() );
		item.setAccessToken( this.socialTestConfig.getFredFacebookAccessToken() );
		Facebook api = new FacebookTemplate( item.getAccessToken() );
		
		// Emulate the FB photo posting method
		String filename = "IxwYC54Rfn9UjaQgf4ay.jpg";
		File tempFile = processor.createTempFile( filename );
		
		try {
			processor.copyS3ObjectToTempFile( tempFile, filename );
		} catch( AmazonS3Exception e ) {
			System.out.println( e.getMessage() );
			if( "AccessDenied".equals( e.getErrorCode() ) ) {
				fail( "S3 Access Denied Exception. Check the accesss key and secret key, and make sure that the image exists in the bucket." );
			}
			fail( "Failed to load image from S3" );
		}
		
		Resource photo = new FileSystemResource( tempFile );
		PageOperations po = api.pageOperations();
		
		String caption = "Caption at: " + System.currentTimeMillis();
		String photoId = po.postPhoto( item.getFacebookPageId(), item.getFacebookAlbumId(), photo, caption );
		
		
		JsonNode facebookPostJson = processor.getLatestFacebookPost( api.restOperations(), item.getFacebookPageId() );
		
		// get the content id
		String contentId = facebookPostJson.findValue( "id" ).toString().replaceAll( "\"", "" );
		
		// confirm the content id refers to our post
		Boolean isContentIdConfirmed = processor.confirmContentIdIsListing( api.restOperations(), contentId, caption, photoId );
		assertTrue( isContentIdConfirmed );

		String facebookContentLink = facebookPostJson.findValue( "link" ).toString();
		facebookContentLink = facebookContentLink.substring( 1, facebookContentLink.length() - 1 );

		System.out.println( "The Facebook post content id is: " + contentId );
		System.out.println( "The facebook contentLink is: " + facebookContentLink );
		assertNotNull( facebookContentLink );
		
		// cleanup
		processor.deletePhoto( api, photoId );
	}
	
	@Test
	public void testSuppressAccessTokenDebugLog() {
		String object = "com.projectlaver.batch.domain.ListingStateChangeCursorItem@600b9fdc[listingStateChangeId=486,listingStateChangeVersion=2,newListingStateChangeVersion=3,listingStateChangeStatus=PENDING,newListingStateChangeUpdatedStatus=<null>,sellerId=59,listingId=33523,listingType=SELLING,announcementPreamble=Now available.,listingKeyword=#WLmiVsUt,listingAmount=0.99000,locale=en_us,imageFilename=Uk5mjP05ESaPL7Y1Z2pG.jpg,facebookPageId=607584439357574,facebookAlbumId=607600446022640,providerId=facebook,providerUserId=100005884934811,accessToken=CAAHYJqqJM3QBAB9sIht2ou4UaWnCQ7L98zDTrZAJlMHnumjZCOXqdgZAw8jw8SZC3feWNqRhemRrYRy3ehkWblwZCtZBu0K15iQ3TW8rr7QEAXPhv2bFVHEuzCq0H6MwlOG9ZCCc10OlwE5LZBH4LdvPfxC1x6mdCFRX6D0SrKPLa3P6b2Q04WF7xKosKDZAZBBOVPanqoCQIWLXwzJmDNsD76,destinationSecret=<null>,didPostingComplete=<null>";
		
		String accessTokenSuppressed = StringUtils.replacePattern( object, "accessToken=[0-9a-zA-Z]*,", "accessToken={protected},");
		String expected = "com.projectlaver.batch.domain.ListingStateChangeCursorItem@600b9fdc[listingStateChangeId=486,listingStateChangeVersion=2,newListingStateChangeVersion=3,listingStateChangeStatus=PENDING,newListingStateChangeUpdatedStatus=<null>,sellerId=59,listingId=33523,listingType=SELLING,announcementPreamble=Now available.,listingKeyword=#WLmiVsUt,listingAmount=0.99000,locale=en_us,imageFilename=Uk5mjP05ESaPL7Y1Z2pG.jpg,facebookPageId=607584439357574,facebookAlbumId=607600446022640,providerId=facebook,providerUserId=100005884934811,accessToken={protected},destinationSecret=<null>,didPostingComplete=<null>";
		
		assertEquals( expected, accessTokenSuppressed );
	}
	
	@Test
	public void testRetrivePostMetadata() {
		Facebook api = new FacebookTemplate( this.socialTestConfig.getFredFacebookAccessToken() );
		
		Post post = api.feedOperations().getPost( "" );

		assertEquals( "Scratch n sniff fish photo Comment '#NewOldHotness' to purchase for $1.00 via Hoot it:\n\nhttps://www.myhootit.com/listing/listingDetail/90010", 
				post.getMessage() );
		assertEquals( Post.PostType.PHOTO, post.getType() );
	}
	
	@Test
	public void testDeletePhotoById() {
		Facebook api = new FacebookTemplate( this.socialTestConfig.getFredFacebookAccessToken() );
		
		// this is the id returned by the po.postPhoto() method
		api.delete( "829541367080461" );
	}
	
	/**
	 * This code uses the graph API call to see what permissions a token has. Could be used to pre-authorize sellers before they create listings
	 * if that should become troublesome.
	 */
	@Test
	public void testAuthorized() {
		Facebook api = new FacebookTemplate( this.socialTestConfig.getFacebookAppToken() ); 
		
		URIBuilder builder = URIBuilder.fromUri( Facebook.GRAPH_API_URL + "100007005190253/permissions" );
		
		builder.queryParam("accessToken", this.socialTestConfig.getFredFacebookAccessToken() );
		
		String result = api.restOperations().getForObject( builder.build(), String.class );
		
		System.out.println( result );
		assertTrue( result.indexOf( "manage_pages") > 0 ); 
	}
	
	@Test
	public void testPostToFeed() throws Exception {
		
		// this filename corresponds to an image that exists in S3 qa-public.rodlaver
		String filename = "1XUFxz7GroGGMGlnFxDB.png";
		
		// this is the regtestfred FB test user's id
		String feedId = this.socialTestConfig.getFredFacebookProviderUserId();
		
		// this is one of regtestfred's photo albums
		String albumId = "318249428381219";
		
		this.postToFacebook( filename, this.socialTestConfig.getFredFacebookAccessToken(), null, feedId, albumId );
	}
	
	@Test
	public void testRetrieveComments() {
		
		String postId = "100005884934811_318258781713617";
		
		MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<String, String>();
		queryParameters.add( "limit", "50" );
		queryParameters.add( "filter", "stream" );
		
		Facebook api = new FacebookTemplate( this.socialTestConfig.getFredFacebookAccessToken() );
		PagedList<Comment> comments = api.fetchConnections( postId, "comments", Comment.class, queryParameters );
		
		for( Comment comment : comments ) {
			System.out.println( String.format( "Retrieved comment id: %s with message: %s", comment.getId(), comment.getMessage() ));
		}
	}
	
	
	private void postToFacebook( String filename, String accessToken, String pageId, String feedId, String albumId ) throws Exception {
		
		// Hard code the caption
		String caption = String.format( "Test post to see if I can write a photo to this album at %d.", System.currentTimeMillis() ); 

		// Get the image from AWS
		AWSCredentials myCredentials = new BasicAWSCredentials( this.socialTestConfig.getS3AccessKey(), this.socialTestConfig.getS3SecretKey() );
		AmazonS3 s3 = new AmazonS3Client( myCredentials );
		S3Object object = s3.getObject( this.socialTestConfig.getS3PublicBucketName(), filename );

		int dotSeparator = filename.indexOf( "." );
		String filePrefix = filename.substring( 0, dotSeparator );
		String fileSuffix = filename.substring( dotSeparator + 1 );

		System.out.println( "File prefix: " + filePrefix );
		System.out.println( "File suffix: " + fileSuffix );

		File tempFile = null;
		try {
			tempFile = File.createTempFile( filePrefix, fileSuffix );
			IOUtils.copy( object.getObjectContent(), new FileOutputStream( tempFile ) );

			Resource photo = new FileSystemResource( tempFile );

			// Post the photo and get the ID. Note that the photo id is not the same as the facebook content id that we'll use to retrieve comments
			
			Facebook api = new FacebookTemplate( accessToken );
			
			String photoId = null;
			if( pageId != null ) {
				// use Page Operations to post the photo to the FB page
				PageOperations po = api.pageOperations();
				
				// for posting to a page, this requires the "manage_pages" and "publish_actions" permissions
				photoId = po.postPhoto( feedId, albumId, photo, caption );
				System.out.println( "Used pageOperations to upload a photo to Facebook with id: " + photoId );
				
			} else {

				this.publishWithMediaOperations( albumId, photo,  caption, api );
				
				Thread.sleep( 5000 );
				
				URIBuilder uriBuilder = URIBuilder.fromUri( Facebook.GRAPH_API_URL + photoId );
				uriBuilder.queryParam( ACCESS_TOKEN_KEY, accessToken );
				uriBuilder.queryParam( "fields", "link");
				URI uri = uriBuilder.build();
				
				RestTemplate restTemplate = new RestTemplate();
				JsonNode responseNode = restTemplate.getForObject( uri, JsonNode.class );
				JsonNode linkNode = responseNode.path( "link" );
				System.out.println( "Photo link link node: " + linkNode.textValue() );
				
				PostData pd = new PostData(feedId );
				pd.message( "My message. Comment '#Yep' to purchase for $3.99.\n\nFull details here:\nhttps://rodlaver.elasticbeanstalk.com/listing/listingDetail/33507" );
				pd.name( "Use the Listing's Title Here" );
				pd.link( linkNode.textValue() );

				// Use the feed operations to upload the post
				FeedOperations fo = api.feedOperations();
				String postId = fo.post( pd );
				System.out.println( "The postId is: " + postId );
				
			}
			
			Thread.sleep( 5000 );
			
			// Get the content id with the expectation that it's the topmost post on this page
			URIBuilder uriBuilder = null;
			
			if( feedId != null ) {
				uriBuilder = URIBuilder.fromUri( Facebook.GRAPH_API_URL + feedId + "/posts" );
			} else {
				uriBuilder = URIBuilder.fromUri( Facebook.GRAPH_API_URL + pageId + "/posts" );
			}
			uriBuilder.queryParam( PAGE_SIZE_LIMIT_KEY, PAGE_SIZE_LIMIT );
			uriBuilder.queryParam( FILTER_TYPE_KEY, STREAM_FILTER );
			uriBuilder.queryParam( ACCESS_TOKEN_KEY, accessToken );
			URI uri = uriBuilder.build();

			RestTemplate restTemplate = new RestTemplate();
			JsonNode responseNode = restTemplate.getForObject( uri, JsonNode.class );
			JsonNode dataNode = responseNode.path( "data" );
			
			// get the content id
			String contentId = dataNode.findValue( "id" ).toString().replaceAll( "\"", "" );
			
			// get the content href and remove the surrounding double quotes (")
			String facebookContentLink = dataNode.findValue( "link" ).toString();
			facebookContentLink = facebookContentLink.substring( 1, facebookContentLink.length() - 1 );

			// set the twitter content link to be null since this is a FB post 
			System.out.println( "The Facebook post content id is: " + contentId );
			System.out.println( "The facebook contentLink is: " + facebookContentLink );
			
			FeedOperations fo = api.feedOperations();
			Post post = fo.getPost( contentId );
			
			assertTrue( StringUtils.equalsIgnoreCase( post.getMessage(), caption ) && Post.PostType.PHOTO.equals( post.getType() ) );
			

		} finally {

			// cleanup the temp file
			if( tempFile != null ) {
				tempFile.delete();
			}
		}
	}

	void publishWithMediaOperations( String albumId, Resource photo, String caption, Facebook api) {
		MediaOperations mo = api.mediaOperations();
		
		// for posting to a feed, this requires the "publish_actions" permission. But we also need "user_photos" to be able to retrieve comments.
		//String photoId = mo.postPhoto( albumId, photo, caption );
		String photoId = mo.postPhoto(photo, caption);
		//String photoId = mo.postPhoto( photo );
		System.out.println( "Used mediaOperations to upload a photo to Facebook with id: " + photoId );
	}
	
	void publishWithAlexPublisher(String albumId, String caption, Resource photo,
			Facebook api) {
		AlexPublisher ap = new AlexPublisher( api );
		String photoId = ap.postPhoto( albumId, photo, caption );
		System.out.println( "Used AlexPublisher to upload a photo to Facebook with id: " + photoId );
	}
	
	class AlexPublisher {
		
		private final GraphApi graphApi;
		
		private AlexPublisher( GraphApi graphApi ) {
			this.graphApi = graphApi;
		}
		
		private String postPhoto( String albumId, Resource photo, String caption ) {
			MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
			parts.set("source", photo);
			if(caption != null) {
				parts.set("message", caption);
			}
			return graphApi.publish(albumId, "photos", parts);
		}
		
	}

}
