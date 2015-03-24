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
package com.projectlaver.config;



import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ReconnectFilter;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.facebook.web.DisconnectController;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import com.projectlaver.controller.RedirectConnectController;
import com.projectlaver.repository.BulkOperationsRepository;
import com.projectlaver.repository.JdbcBulkOperationsRepository;
import com.projectlaver.util.SimpleSignInAdapter;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

	/**
	 * INSTANCE VARIABLES
	 */
	
	@Autowired
	private DataSource dataSource;

	// Properties values
	@Value("${facebook.clientId}")
	private String facebookClientId;

	@Value("${facebook.clientSecret}")
	private String facebookClientSecret;

	@Value("${twitter.consumerKey}")
	private String twitterConsumerKey;

	@Value("${twitter.consumerSecret}")
	private String twitterConsumerSecret;
	
    @Bean
    @Scope(value="singleton", proxyMode=ScopedProxyMode.INTERFACES)
    public BulkOperationsRepository bulkMessageOperationsRepository() {
    	return new JdbcBulkOperationsRepository(this.dataSource);
    }
	
    @Bean
	public ConnectController connectController( final ConnectionFactoryLocator connectionFactoryLocator, final ConnectionRepository connectionRepository ) {
    	return new RedirectConnectController( connectionFactoryLocator, connectionRepository );
	}
    
    @Bean
	public DisconnectController disconnectController( final UsersConnectionRepository usersConnectionRepository, final Environment env) {
		return new DisconnectController(usersConnectionRepository, env.getProperty("facebook.appSecret"));
	}

	@Bean
	public ReconnectFilter apiExceptionHandler( final UsersConnectionRepository usersConnectionRepository, final UserIdSource userIdSource) {
		return new ReconnectFilter(usersConnectionRepository, userIdSource);
	}
	
    @Bean
    public SimpleSignInAdapter simpleSignInAdapter() {

    	SimpleSignInAdapter signInAdapter = new SimpleSignInAdapter( new HttpSessionRequestCache() );

    	return signInAdapter;
    }

	 @Override
     public void addConnectionFactories( final ConnectionFactoryConfigurer cfConfig, final Environment env ) {
         cfConfig.addConnectionFactory(new TwitterConnectionFactory( this.twitterConsumerKey, this.twitterConsumerSecret ));
         cfConfig.addConnectionFactory(new FacebookConnectionFactory( this.facebookClientId, this.facebookClientSecret ));
     }
	 
	 @Override
     public UserIdSource getUserIdSource() {
		 
         return new UserIdSource() {                     
             @Override
             public String getUserId() {
                 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                 if (authentication == null) {
                     throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
                 }
                 return authentication.getName();
             }
         };
     }
     
     @Override
     public UsersConnectionRepository getUsersConnectionRepository( final ConnectionFactoryLocator connectionFactoryLocator ) {
             return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
     }

     @Bean
     @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
     public Facebook facebook(final ConnectionRepository repository) {
             Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
             return connection == null ? null : connection.getApi();
     }
     
     @Bean
     @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
     public Twitter twitter(final ConnectionRepository repository) {
             Connection<Twitter> connection = repository.findPrimaryConnection(Twitter.class);
             return connection == null ? null : connection.getApi();
     }
     


}
