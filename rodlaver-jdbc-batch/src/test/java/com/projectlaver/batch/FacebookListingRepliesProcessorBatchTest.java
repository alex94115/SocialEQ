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

import javax.sql.DataSource;

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
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.social.facebook.api.Comment;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.projectlaver.test.config.SocialTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/simple-job-launcher-context.xml",
									"/jobs/rodLaverJob.xml" })
public class FacebookListingRepliesProcessorBatchTest implements ApplicationContextAware {

	
	private JdbcTemplate jdbcTemplate;
	
	private StepRunner stepRunner;
	
	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private JobRepository jobRepository;
	
	protected ApplicationContext context;

	private SocialTestConfig socialTestUsers;
	
	@Autowired
    public void setDataSource( DataSource dataSource ) {
        this.jdbcTemplate = new JdbcTemplate( dataSource );
    }
	
	@Before
	public void setUp() {
		this.stepRunner = new StepRunner( this.jobLauncher, this.jobRepository );
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
	
	/**
	 * Standalone test
	 */
	@Test
	public void test() {
		// Set up query parameters
		MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<String, String>();

		// max comments per page
		queryParameters.add( "limit", "50" );

		// use strict chronological order
		queryParameters.add( "filter", "stream" );
		
		Facebook api = new FacebookTemplate( this.socialTestUsers.getFacebookAppToken() );
	
		PagedList<Comment> comments = api.fetchConnections( "636521906382409_753837887984143", "comments", Comment.class, queryParameters );
		System.out.println( comments );
	}
	
	
	/**
	 * This test will run through the steps that the batch job uses to read a reply_paging_status row for a piece of listing content
	 * and then (using the local batch spring properties) will attempt to fetch the comments from Facebook related to the content.
	 * 
	 * Make sure that: 1) the batch processor column in the reply_paging_status row will match the current batch run (may
	 * have to look at the SQL logs while running it once to get the next incremented number). Also make sure that the
	 * access token in the localhost spring batch properties file is valid for the test.
	 */
	@Test
	public void testBatchStep() {
		
		// First run the divvy Comment Pages step
		{
//			Step step = ( Step )context.getBean( "divvyFacebookPagedCommentsToFetch" );
//			assertEquals( BatchStatus.COMPLETED, this.stepRunner.launchStep( step ).getStatus() );
		}
		
		// Then run the fetch comments step
		{
			Step step = ( Step )context.getBean( "readFacebookListingReplyMessages" );
			assertEquals( BatchStatus.COMPLETED, this.stepRunner.launchStep( step ).getStatus() );
		}
	}

}
