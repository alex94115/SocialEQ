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
package com.projectlaver.twitterstream;

import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import twitter4j.StatusListener;

import com.google.common.collect.Lists;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.projectlaver.util.TwitterListingResponseProcessor;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import com.twitter.hbc.twitter4j.Twitter4jStatusClient;

public class TwitterStream {
	
	private static final String DELETE_FROM_CURRENT_TWITTER_STREAM_SELLERS =
			  " DELETE FROM CurrentTwitterStreamSellers ";

	private static final String SELECT_INTO_CURRENT_TWITTER_STREAM_SELLERS = 
			  " INSERT INTO CurrentTwitterStreamSellers (user_id, providerUserId, displayName, version) " 
			+ "   SELECT u.id, uc.providerUserId, uc.displayName, 0 "  
			+ "   FROM Users u "
			+ "     INNER JOIN UserConnection uc ON u.username = uc.userId "
			+ "   WHERE EXISTS (  "
			+ "     SELECT ur.users_id " 
			+ "     FROM Users_Roles ur  "
			+ "     WHERE ur.roles_id > 1 " 
			+ "       AND u.id = ur.users_id  "
			+ "   )  "
			+ "   AND uc.providerId='twitter' ";
	
	private static final String SELECT_TWITTER_STREAM_SELLERS = 
			  " SELECT displayName FROM CurrentTwitterStreamSellers ";
	
	private final Log logger = LogFactory.getLog(getClass());
	

    public static void main( String args[] ) throws Exception {
    	TwitterStream application = new TwitterStream();
    	application.runTwitterFilterStream();
    }
    
    public void runTwitterFilterStream()  throws Exception {

    	Properties properties = System.getProperties();
    	
    	DataSource dataSource = this.initializeJdbcDataSource( properties );
        
        /** 
         * Set up blocking queues. Be sure to size these properly based on expected TPS of your stream. 
         */
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);
        BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>(1000);

        /** 
         * Declare the host to connect to & the endpoint 
         */
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        /**
         *  Clear the current_twitter_stream sellers, insert a row for every Twitter 
         *  seller in the system, and return a list of the twitter displayNames
         *  that will be used to initialize the filter stream.
         */
        hosebirdEndpoint.trackTerms( this.initializeTwitterStreamSellers( dataSource ) );

        /**
         * This is the oAuth object
         */
        Authentication hosebirdAuth = this.createHosebirdAuth(properties);
        
        /**
         * Create the hosebird client
         */
		Client hosebirdClient = this.createHosebirdClient( msgQueue, eventQueue, hosebirdHosts, hosebirdEndpoint, hosebirdAuth );
		
		/**
		 * Hand the message queue and hosebird client to the method that starts the Twitter4jClient
		 */
		ExecutorService executorService = this.startTwitter4jClient(msgQueue, hosebirdClient, dataSource ); 

		/**
		 * Add a shutdown handler to gracefully close the stream and executorService upon termination
		 */
		this.configureShutdownHandler( hosebirdClient, executorService );
    }

	ExecutorService startTwitter4jClient( BlockingQueue<String> msgQueue, Client hosebirdClient, DataSource dataSource ) { // JdbcTemplate jdbcTemplate ) {
		
		ExecutorService executorService = this.createExecutorService();
		
		// hosebirdClient is our hbc.core.Client object
		// msgQueue is our BlockingQueue<String> of messages that the handlers will receive from
		// listeners is a List<StatusListener> of the t4j StatusListeners
		// executorService coordinates the asynchronous processing
		DatabaseStatusListener listener = new DatabaseStatusListener( new TwitterListingResponseProcessor( dataSource ) );
		List<? extends StatusListener> listeners = Lists.newArrayList( listener );
		Twitter4jStatusClient t4jClient = new Twitter4jStatusClient( hosebirdClient, msgQueue, listeners, executorService);
		t4jClient.connect();
		
		// Call this once for every thread you want to spin off for processing the raw messages.
		// This should be called at least once.
		// required to start processing the messages
		t4jClient.process();
		
		return executorService;
	}

	void configureShutdownHandler( Client hosebirdClient, ExecutorService executorService ) {
		ShutdownHandler shutdownHandler = new ShutdownHandler();
		shutdownHandler.setClient( hosebirdClient );
		shutdownHandler.setExecutorService( executorService );
		Runtime.getRuntime().addShutdownHook( shutdownHandler );
	}

	ExecutorService createExecutorService() {
		/**
		  * Initialize an ExecutorService that will coordinate asynchronous processing
		  */
		 int numProcessingThreads = 1;
		 ExecutorService executorService = Executors.newFixedThreadPool( numProcessingThreads );
		return executorService;
	}

	Authentication createHosebirdAuth(Properties properties) {
		/**
         * Read the connection OAuth values from environment properties 
         */
        
        String consumerKey = (String) properties.get( "consumerKey" );
        String consumerSecret = (String) properties.get( "consumerSecret" );
        String token = (String) properties.get( "token" );
        String secret = (String) properties.get( "secret" );
        Authentication hosebirdAuth = new OAuth1( consumerKey, consumerSecret, token, secret );
		return hosebirdAuth;
	}

	Client createHosebirdClient(BlockingQueue<String> msgQueue,
			BlockingQueue<Event> eventQueue, Hosts hosebirdHosts,
			StatusesFilterEndpoint hosebirdEndpoint, Authentication hosebirdAuth) {
		ClientBuilder builder = new ClientBuilder()
			// optional: mainly for the logs
			.name("Hosebird-Client-01")                              
			.hosts(hosebirdHosts)
			.authentication(hosebirdAuth)
			.endpoint(hosebirdEndpoint)
			.processor(new StringDelimitedProcessor(msgQueue))
			// optional: use this if you want to process client events
			.eventMessageQueue(eventQueue);                          
			
		 Client hosebirdClient = builder.build();
		return hosebirdClient;
	}

	DataSource initializeJdbcDataSource( Properties properties ) throws PropertyVetoException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		
		ds.setDriverClass(  "com.mysql.jdbc.Driver" );
		
		String appJdbcUrl = properties.getProperty( "appJdbcUrl" );
		ds.setJdbcUrl( appJdbcUrl );
		ds.setAcquireIncrement(1);
		ds.setIdleConnectionTestPeriod(60);
		ds.setMaxPoolSize(5);
		ds.setMaxStatements(50);
		ds.setMinPoolSize(1);

		return ds;
	}
    
	/**
	 * This method used to return a List<Long> of the sellers' twitter ids. However,
	 * a Twitter stream initialized in that way will only pick up tweets that are 
	 * direct replies to our sellers or where the @mention is the first token in the 
	 * tweet. Since we'd like to be able to let a user tweet "#hashtag @seller", 
	 * we need to use the track filter with the sellers' display names instead of using the 
	 * follow filter with their ids. 
	 * 
	 * @param jdbcTemplate
	 * @return
	 */
    List<String> initializeTwitterStreamSellers( DataSource dataSource ) {
    	
    	JdbcTemplate jdbcTemplate = new JdbcTemplate( dataSource );
    	
    	this.logger.info( DELETE_FROM_CURRENT_TWITTER_STREAM_SELLERS );
		int deleted = jdbcTemplate.update( DELETE_FROM_CURRENT_TWITTER_STREAM_SELLERS );
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Deleted: " + deleted + " from CurrentTwitterStreamSellers" );
		}
		
		this.logger.info( SELECT_INTO_CURRENT_TWITTER_STREAM_SELLERS );
		int inserted = jdbcTemplate.update( SELECT_INTO_CURRENT_TWITTER_STREAM_SELLERS );
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "Inserted: " + inserted + " into CurrentTwitterStreamSellers" );
		}
		
		List<String> result = jdbcTemplate.query( 
			SELECT_TWITTER_STREAM_SELLERS, 
			new ResultSetExtractor<List<String>>() {

				@Override
				public List<String> extractData( ResultSet resultSet ) throws SQLException, DataAccessException {
					List<String> result = new ArrayList<String>();
	            	  
	            	while (resultSet.next()) {
	            		String displayName = resultSet.getString( "displayName" );
	                	result.add( displayName );
	                }
	                return result;
				}
			}
		);
		
		return result;
    }
    
	class ShutdownHandler extends Thread {

		private Client client;
		private ExecutorService executorService;

		void setClient(Client client) {
			this.client = client;
		}

		void setExecutorService(ExecutorService executorService) {
			this.executorService = executorService;
		}

		public void run() {
			if( this.client != null ) {
				this.client.stop();
			}
			
			if( this.executorService != null ) {
				this.executorService.shutdown();
			}
		}
	};
    
}
