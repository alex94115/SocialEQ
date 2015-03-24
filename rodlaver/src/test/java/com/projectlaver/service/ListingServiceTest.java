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
package com.projectlaver.service;

import java.io.FileInputStream;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.jets3t.service.CloudFrontService;
import org.jets3t.service.utils.ServiceUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.support.URIBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.projectlaver.domain.Listing;
import com.projectlaver.test.config.SocialTestConfig;
import com.projectlaver.test.config.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class ListingServiceTest {
	
	@Autowired
	SocialTestConfig socialTestConfig;
	
	private byte[] derPrivateKey = new byte[0]; 
	private int cloudfrontSignedUrlValidMinutes = 10080; // 1 wk

	@Test 
	public void testTimeRemaining() {
		Calendar calendar = Calendar.getInstance();
		calendar.add( Calendar.DAY_OF_YEAR, -1 );
		
		Listing listing = new Listing();
		listing.setExpires( calendar.getTime() );
		
		ListingService service = new ListingService();
		
		Map<String, Object> results = service.addTimeRemainingAttributes( listing );
		
		System.out.println( results.get( "remainingDays" ) );
		System.out.println( results.get( "remainingHours" ) );
		System.out.println( results.get( "remainingMinutes" ) );
		System.out.println( results.get( "remainingSeconds" ) );
	}
	
	@Test
	public void testCreateDownloadUrl() throws Exception {
		
		String downloadFilename = "somefile.mp4";
		String contentFilename = "somecontenfilename.mp4";
		
		// One time conversion of the DER file into a byte array.
		synchronized( this.derPrivateKey ) {
			
			if( this.derPrivateKey.length == 0 ) {
				this.derPrivateKey = ServiceUtils.readInputStreamToBytes( new FileInputStream( this.socialTestConfig.getCloudfrontPrivateKeyFilePath() )); 
			}
		}
		
		// Create an expiry date and set its time for a number of minutes from now
		Date expires = new Date();
		expires.setTime(  System.currentTimeMillis() + ( this.cloudfrontSignedUrlValidMinutes * 60 * 1000 ) );
		
		// Generate a "canned" signed URL to allow access to a 
		// specific distribution and object
		URIBuilder builder = URIBuilder.fromUri( String.format( "https://%s/%s", this.socialTestConfig.getCloudfrontDistributionDomain(), contentFilename ) );
		
		if( downloadFilename != null ) {
			builder.queryParam( "response-content-disposition", "attachment;filename=\""+ downloadFilename +"\"" );
			builder.queryParam( "response-content-type", URLConnection.guessContentTypeFromName( contentFilename ) );
		}
		
		String result = CloudFrontService.signUrlCanned(
				builder.build().toString(),		// Resource URL or Path
				this.socialTestConfig.getCloudfrontKeyPairId(), // Certificate identifier, an active trusted signer for the distribution
			    this.derPrivateKey, 			// DER Private key data
			    expires );						// DateLessThan
			
		System.out.println( result );
	}

}
