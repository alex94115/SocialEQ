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
package com.projectlaver.test.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * Class to allow unit tests to load test property values at execution time (instead
 * of hard coding these values into the source files). 
 * 
 * @author alexduff
 *
 */
public class SocialTestConfig {

	/**
	 * Facebook App
	 */
	
	@Value("${facebook.app.token}")
	private String facebookAppToken;
	
	/**
	 * Facebook Users
	 * 
	 * These values have been extracted from rodlaver after signing in with the SocialEQ Test App's 
	 * respective test user. NOTE: the seller has to have the manage pages / publish actions privileges.
	 */
	@Value("${fred.facebook.accessToken}")
	private String fredFacebookAccessToken;
	
	@Value("${fred.facebook.providerUserId}")
	private String fredFacebookProviderUserId;
	
	@Value("${fred.facebook.pageId}")
	private String fredFacebookPageId;
	
	@Value("${fred.facebook.albumId}")
	private String fredFacebookAlbumId;

	/**
	 * These tokens must have the publish actions privilege (because the test publishes comments automatically
	 * on behalf of these users). Not sure if there's a simpler way to get it, but I modified the signin page 
	 * to have this action:
	 * 
	 * <a th:href="@{/auth/facebook(scope='publish_actions')}">
	 * 
	 * And then signed in with each of the test users, then extracted the accessToken from the database.
	 * 
	 */
	
	@Value("${keef.facebook.username}")
	private String keefFacebookUsername;
	
	@Value("${keef.facebook.providerUserId}")
	private String keefFacebookProviderUserId;
	
	@Value("${keef.facebook.accessToken}")
	private String keefFacebookAccessToken;
	
	@Value("${joe.facebook.username}")
	private String joeFacebookUsername;
	
	@Value("${joe.facebook.accessToken}")
	private String joeFacebookAccessToken;
	
	@Value("${lily.facebook.username}")
	private String lilyFacebookUsername;
	
	@Value("${lily.facebook.accessToken}")
	private String lilyFacebookAccessToken;
	
	@Value("${bob.facebook.username}")
	private String bobFacebookUsername;
	
	@Value("${bob.facebook.accessToken}")
	private String bobFacebookAccessToken;
	
	/**
	 * Twitter
	 */
	
	// App
	
	@Value("${hootit.twitter.consumerKey}")
	private String hootitTwitterConsumerKey;
	
	@Value("${hootit.twitter.secret}")
	private String hootitTwitterSecret;
	
	// Users
	
	@Value("${nancy.twitter.username}")
	private String nancyTwitterUsername;
	
	@Value("${nancy.twitter.accessToken}")
	private String nancyTwitterAccessToken;
	
	@Value("${nancy.twitter.secret}")
	private String nancyTwitterSecret;
	
	@Value("${fred.twitter.username}")
	private String fredTwitterUsername;
	
	@Value("${fred.twitter.accessToken}")
	private String fredTwitterAccessToken;
	
	@Value("${fred.twitter.secret}")
	private String fredTwitterSecret;
	
	@Value("${lily.twitter.username}")
	private String lilyTwitterUsername;
	
	@Value("${lily.twitter.accessToken}")
	private String lilyTwitterAccessToken;
	
	@Value("${lily.twitter.secret}")
	private String lilyTwitterSecret;
	
	@Value("${reggie.twitter.username}")
	private String reggieTwitterUsername;
	
	@Value("${reggie.twitter.providerUserId}")
	private String reggieTwitterProviderUserId;
	
	@Value("${reggie.twitter.accessToken}")
	private String reggieTwitterAccessToken;
	
	@Value("${reggie.twitter.secret}")
	private String reggieTwitterSecret;
	
	@Value("${duff.twitter.username}")
	private String duffTwitterUsername;
	
	@Value("${duff.twitter.accessToken}")
	private String duffTwitterAccessToken;
	
	@Value("${duff.twitter.twitterSecret}")
	private String duffTwitterSecret;
	
	@Value("${billy.twitter.username}")
	private String billyTwitterUsername;
	
	@Value("${billy.twitter.accessToken}")
	private String billyTwitterAccessToken;
	
	@Value("${billy.twitter.secret}")
	private String billyTwitterSecret;
	
	/**
	 * S3
	 */
	
	@Value("${s3.accessKey}")
	private String s3AccessKey;
	
	@Value("${s3.secretKey}")
	private String s3SecretKey;
	
	@Value("${s3.public.bucketName}")
	private String s3PublicBucketName;
	
	/**
	 * Cloudfront
	 */
	
	@Value("${cloudfront.distribution.domain}")
	private String cloudfrontDistributionDomain;
	
	@Value("${cloudfront.key.pair.id}")
	private String cloudfrontKeyPairId;
	
	@Value("${cloudfront.private.key.file.path}")
	private String cloudfrontPrivateKeyFilePath;
	
	/**
	 * Email
	 */
	
	@Value("${email.do.auth}")
	private String emailDoAuth;

	@Value("${email.from.address}")
	private String emailFromAddress;
	
	@Value("${email.host}")
	private String emailHost;
	
	@Value("${email.smtp.username}")
	private String emailSmtpUsername;
	
	@Value("${email.smtp.password}")
	private String emailSmtpPassword;
	
	@Value("${email.protocol}")
	private String emailProtocol;
	
	@Value("${email.port}")
	private String emailPort;
	
	@Value("${email.do.enable.tls}")
	private String emailDoEnableTls;
	
	@Value("${email.do.require.tls}")
	private String emailDoRequireTls;

	/**
	 * Braintree
	 */
	
	@Value("${braintree.master.merchant.accountId}")
	private String braintreeMasterMerchantAccountId;
	
	@Value("${braintree.test.merchant.accountId}")
	private String braintreeTestMerchantAccountId;
	
	@Value("${braintree.private.key}")
	private String braintreePrivateKey;
	
	@Value("${braintree.public.key}")
	private String braintreePublicKey;
	
	/**
	 * Getters
	 */
	
	public String getFredFacebookAccessToken() {
		return fredFacebookAccessToken;
	}

	public String getFredFacebookProviderUserId() {
		return fredFacebookProviderUserId;
	}

	public String getFredFacebookPageId() {
		return fredFacebookPageId;
	}

	public String getFredFacebookAlbumId() {
		return fredFacebookAlbumId;
	}

	public String getKeefFacebookUsername() {
		return keefFacebookUsername;
	}

	public String getKeefFacebookProviderUserId() {
		return keefFacebookProviderUserId;
	}

	public String getKeefFacebookAccessToken() {
		return keefFacebookAccessToken;
	}

	public String getJoeFacebookUsername() {
		return joeFacebookUsername;
	}

	public String getJoeFacebookAccessToken() {
		return joeFacebookAccessToken;
	}

	public String getLilyFacebookUsername() {
		return lilyFacebookUsername;
	}

	public String getLilyFacebookAccessToken() {
		return lilyFacebookAccessToken;
	}

	public String getBobFacebookUsername() {
		return bobFacebookUsername;
	}

	public String getBobFacebookAccessToken() {
		return bobFacebookAccessToken;
	}

	public String getHootitTwitterConsumerKey() {
		return hootitTwitterConsumerKey;
	}

	public String getHootitTwitterSecret() {
		return hootitTwitterSecret;
	}

	public String getNancyTwitterUsername() {
		return nancyTwitterUsername;
	}

	public String getNancyTwitterAccessToken() {
		return nancyTwitterAccessToken;
	}

	public String getNancyTwitterSecret() {
		return nancyTwitterSecret;
	}

	public String getFredTwitterUsername() {
		return fredTwitterUsername;
	}

	public String getFredTwitterAccessToken() {
		return fredTwitterAccessToken;
	}

	public String getFredTwitterSecret() {
		return fredTwitterSecret;
	}

	public String getLilyTwitterUsername() {
		return lilyTwitterUsername;
	}

	public String getLilyTwitterAccessToken() {
		return lilyTwitterAccessToken;
	}

	public String getLilyTwitterSecret() {
		return lilyTwitterSecret;
	}

	public String getReggieTwitterUsername() {
		return reggieTwitterUsername;
	}

	public String getReggieTwitterProviderUserId() {
		return reggieTwitterProviderUserId;
	}

	public String getReggieTwitterAccessToken() {
		return reggieTwitterAccessToken;
	}

	public String getReggieTwitterSecret() {
		return reggieTwitterSecret;
	}

	public String getDuffTwitterUsername() {
		return duffTwitterUsername;
	}

	public String getDuffTwitterAccessToken() {
		return duffTwitterAccessToken;
	}

	public String getDuffTwitterSecret() {
		return duffTwitterSecret;
	}

	public String getBillyTwitterUsername() {
		return billyTwitterUsername;
	}

	public String getBillyTwitterAccessToken() {
		return billyTwitterAccessToken;
	}

	public String getBillyTwitterSecret() {
		return billyTwitterSecret;
	}

	public String getS3AccessKey() {
		return s3AccessKey;
	}

	public String getS3SecretKey() {
		return s3SecretKey;
	}

	public String getS3PublicBucketName() {
		return s3PublicBucketName;
	}

	public String getCloudfrontDistributionDomain() {
		return cloudfrontDistributionDomain;
	}

	public String getCloudfrontKeyPairId() {
		return cloudfrontKeyPairId;
	}

	public String getCloudfrontPrivateKeyFilePath() {
		return cloudfrontPrivateKeyFilePath;
	}

	public String getEmailDoAuth() {
		return emailDoAuth;
	}

	public String getEmailFromAddress() {
		return emailFromAddress;
	}

	public String getEmailHost() {
		return emailHost;
	}

	public String getEmailSmtpUsername() {
		return emailSmtpUsername;
	}

	public String getEmailSmtpPassword() {
		return emailSmtpPassword;
	}

	public String getEmailProtocol() {
		return emailProtocol;
	}

	public String getEmailPort() {
		return emailPort;
	}

	public String getEmailDoEnableTls() {
		return emailDoEnableTls;
	}

	public String getEmailDoRequireTls() {
		return emailDoRequireTls;
	}

	public String getFacebookAppToken() {
		return facebookAppToken;
	}

	public String getBraintreeMasterMerchantAccountId() {
		return braintreeMasterMerchantAccountId;
	}

	public String getBraintreeTestMerchantAccountId() {
		return braintreeTestMerchantAccountId;
	}

	public String getBraintreePrivateKey() {
		return braintreePrivateKey;
	}

	public String getBraintreePublicKey() {
		return braintreePublicKey;
	}
	
}
