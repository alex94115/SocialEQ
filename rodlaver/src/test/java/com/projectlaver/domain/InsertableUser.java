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
package com.projectlaver.domain;

import org.springframework.test.util.ReflectionTestUtils;

public class InsertableUser extends User implements AsSqlInsert {
	
	private RandomTwitterUser randomTwitterUser;
	
	public InsertableUser( Long id, RandomTwitterUser randomName, Long roleId ) {
		
		this.randomTwitterUser = randomName;
		
		ReflectionTestUtils.setField( this, "id",  id );
		super.setFirstName( randomName.getFirstname() );
		super.setLastName( randomName.getLastname() );
		super.setUsername( randomName.getUsername() );
		
		Role role = new Role();
		ReflectionTestUtils.setField( role, "id", roleId );
		
		//Need to insert into user_role instead
		//super.setRole( role );
		super.setEmailAddress( randomName.getEmail() );
	}

	@Override
	public String asSqlInsert() {
		
		return TestDataUtils.buildInsertFromTokens( 
				"INSERT INTO `user` VALUES (", 
				super.getId(), 				// id
				super.getFirstName(), 		// first name
				super.getLastName(),  		// last name
				"NULL", 					//  
				super.getUsername(), 		// user name
				"current_timestamp", 		// created
				"current_timestamp",		// updated	
				0,							// version
				super.getEmailAddress(),	// email address
				"NULL",						// 
				"NULL",						// 
				0,							//
				0,							// 
				1 );						//
		
		// Updated schema:
		// `id` bigint(20) NOT NULL AUTO_INCREMENT,
		// `firstName` varchar(255) DEFAULT NULL,
		// `lastName` varchar(255) DEFAULT NULL,
		// `password` varchar(255) DEFAULT NULL,
		// `username` varchar(255) DEFAULT NULL,
		// `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
		// `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
		// `version` int(11) NOT NULL,
		// `role_id` bigint(20) DEFAULT NULL,
		// `emailAddress` varchar(255) DEFAULT NULL,
		// `country_code` varchar(255) DEFAULT NULL,
		// `mobile_number` varchar(255) DEFAULT NULL,
		// `is_mobile_verified` tinyint(1) DEFAULT NULL,
		// `facebook_album_id` varchar(255) DEFAULT NULL,
		// `facebook_page_url` varchar(255) DEFAULT NULL,
		// `facebook_page_id` varchar(255) DEFAULT NULL,
		// `has_accepted_current_buyer_tos` bit(1) DEFAULT NULL,
		// `is_user_blocked` bit(1) DEFAULT NULL,

	}
	
	public RandomTwitterUser getRandomTwitterUser() {
		return this.randomTwitterUser;
	}
	
	

}
