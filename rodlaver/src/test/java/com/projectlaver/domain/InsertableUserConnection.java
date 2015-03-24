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

import org.apache.commons.lang3.StringUtils;

import com.projectlaver.integration.SocialProviders;

public class InsertableUserConnection implements AsSqlInsert {

	private String userId;
	private String providerId;
	private Long providerUserId;
	private String displayName;
	
	
	public InsertableUserConnection( RandomTwitterUser randomTwitterUser ) {
		this.userId = randomTwitterUser.getUsername();
		this.providerId = SocialProviders.TWITTER;
		this.providerUserId = randomTwitterUser.getProviderUserId();
		this.displayName = StringUtils.replace( randomTwitterUser.getUsername(), "twitter/", "@" );
	}
	
	@Override
	public String asSqlInsert() {
		return TestDataUtils.buildInsertFromTokens(
				"INSERT INTO `UserConnection` VALUES (",
				this.userId,
				this.providerId,
				this.providerUserId,
				1,
				this.displayName,
				"NULL",
				"NULL",
				"access token",
				"NULL",
				"NULL",
				"NULL" );
		
			// Updated schema
			//  `userId` varchar(255) NOT NULL,
			//  `providerId` varchar(255) NOT NULL,
			//  `providerUserId` varchar(255) NOT NULL DEFAULT '',
			//  `rank` int(11) NOT NULL,
			//  `displayName` varchar(255) DEFAULT NULL,
			//  `profileUrl` varchar(512) DEFAULT NULL,
			//  `imageUrl` varchar(512) DEFAULT NULL,
			//  `accessToken` varchar(255) NOT NULL,
			//  `secret` varchar(255) DEFAULT NULL,
			//  `refreshToken` varchar(255) DEFAULT NULL,
			//  `expireTime` bigint(20) DEFAULT NULL,
	}

}
