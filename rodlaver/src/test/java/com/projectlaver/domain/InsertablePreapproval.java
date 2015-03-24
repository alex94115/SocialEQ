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

public class InsertablePreapproval extends Preapproval implements AsSqlInsert {

	Boolean doGenerateCurrentCanceled;
	Boolean doGenerateCurrentActive;
	Boolean doGenerateExpiredCanceled;
	Boolean doGenerateExpiredActive;
	
	public InsertablePreapproval( Long id, 
								  User user, 
								  Boolean doGenerateCurrentCanceled, 
								  Boolean doGenerateCurrentActive, 
								  Boolean doGenerateExpiredCanceled,
								  Boolean doGenerateExpiredActive ) {
		ReflectionTestUtils.setField( this, "id", id );
		this.doGenerateCurrentActive = doGenerateCurrentActive;
		this.doGenerateCurrentCanceled = doGenerateCurrentCanceled;
		this.doGenerateExpiredActive = doGenerateExpiredActive;
		this.doGenerateExpiredCanceled = doGenerateExpiredCanceled; 
		
		super.setUser( user );
	}
	
	@Override
	public String asSqlInsert() {
			
		StringBuilder result = new StringBuilder();
		
		if( doGenerateCurrentCanceled ) {
			result.append( this.generateCurrentPreapprovalWithStatus( 0L, "CANCELED" ));
			
			if( doGenerateCurrentActive || doGenerateExpiredCanceled || doGenerateExpiredActive ) {
				result.append( "\n" );
			}
		}
		
		if( doGenerateCurrentActive ) {
			result.append( this.generateCurrentPreapprovalWithStatus( 1L, "ACTIVE" ));
			
			if( doGenerateExpiredCanceled || doGenerateExpiredActive ) {
				result.append( "\n" );
			}
		}
		
		if( doGenerateExpiredCanceled ) {
			result.append( this.generateExpiredPreapproavalWithStatus( 2L, "CANCELED" ));
			
			if( doGenerateExpiredActive ) {
				result.append( "\n" );
			}
		}
		
		if( doGenerateExpiredActive ) {
			result.append( this.generateExpiredPreapproavalWithStatus( 3L, "ACTIVE" ) );
		}
		
		return result.toString();
	}
	
	String generateExpiredPreapproavalWithStatus( Long offset, String status ) {
		String startToken = "2012-09-01 00:00:00";
		String endToken = "current_timestamp";
		
		return this.generatePreapproval( offset, status, startToken, endToken );
	}
	
	String generateCurrentPreapprovalWithStatus( Long offset, String status ) {
		String startToken = "current_timestamp";
		String endToken = "2014-09-01 00:00:00";
		
		return this.generatePreapproval( offset, status, startToken, endToken );
	}
	
	String generatePreapproval( Long offset, String status, String startToken, String endToken ) {
		return TestDataUtils.buildInsertFromTokens(
				"INSERT INTO `preapproval` VALUES (",
				super.getId() + offset,
				"NULL",
				"NULL",
				super.getUser().getId(),
				0,
				status,
				"current_timestamp",
				"current_timestamp",
				endToken,
				startToken,
				"PAYPAL" );
		
				// Updated schema
				//  `id` bigint(20) NOT NULL AUTO_INCREMENT,
				//  `correlationId` varchar(255) DEFAULT NULL,
				//  `preapprovalKey` varchar(255) DEFAULT NULL,
				//  `user_id` bigint(20) DEFAULT NULL,
				//  `version` int(11) NOT NULL,
				//  `status` varchar(255) DEFAULT NULL,
				//  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
				//  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
				//  `end` datetime NOT NULL,
				//  `start` datetime NOT NULL,
				//  `type` varchar(255) DEFAULT NULL,
	}

}
