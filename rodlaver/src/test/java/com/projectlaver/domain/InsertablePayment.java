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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class InsertablePayment extends Payment implements AsSqlInsert {
	
	Random r = new Random();
	
	public InsertablePayment( Long id, User buyer, InsertableListing listing, Message message ) {
		super.setId( id );
		super.setPayKey( "AP-" + StringUtils.upperCase( RandomStringUtils.randomAlphanumeric(17) ) );
		super.setPayer( buyer );
		
		BigDecimal totalAmount = this.newRandomBigDecimal( this.r );
		BigDecimal rodLaverAmount = this.getRodLaverAmount( totalAmount );
		BigDecimal sellerAmount = this.getSellerAmount( totalAmount, rodLaverAmount );
		
		super.setTotalAmount( totalAmount );
		super.setCurrencyCode( "USD" );
		
		super.setPayee( listing.getSeller() );
		super.setListing( listing );
		super.setMessage( message );
		super.setRodLaverAmount( rodLaverAmount );
		super.setSellerAmount( sellerAmount );
		
		
	}
	
	@Override
	public String asSqlInsert() {
		StringBuilder result = new StringBuilder();
		result.append( 
				TestDataUtils.buildInsertFromTokens( 
					"INSERT INTO `payment` VALUES(",
						super.getId(),
						super.getPayKey(),
						super.getPayer().getId(),
						super.getTotalAmount(),
						super.getCurrencyCode(),
						0,
						"current_timestamp",
						"current_timestamp",
						super.getListing().getId(),
						super.getMessage().getId(),
						super.getListing().getSeller().getId(),
						"NULL",
						super.getRodLaverAmount(),
						super.getSellerAmount(),
						0 ) );
		
				// Updated schema
				//  `id` bigint(20) NOT NULL AUTO_INCREMENT,
				//  `payKey` varchar(255) DEFAULT NULL,
				//  `correlationId` varchar(255) DEFAULT NULL,
				//  `payer_id` bigint(20) DEFAULT NULL,
				//  `amount` decimal(15,5) DEFAULT NULL,
				//  `currencyCode` varchar(255) DEFAULT NULL,
				//  `version` int(11) NOT NULL,
				//  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
				//  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
				//  `listing_id` bigint(20) DEFAULT NULL,
				//  `message_id` bigint(20) DEFAULT NULL,
				//  `payee_id` bigint(20) DEFAULT NULL,
				//  `shippingAddress_id` bigint(20) DEFAULT NULL,
				//  `rodLaverAmount` decimal(15,5) DEFAULT NULL,
				//  `sellerAmount` decimal(15,5) DEFAULT NULL,
				//  `has_been_shipped` tinyint(1) DEFAULT NULL,
		
		result.append( "\n" );
		
		result.append( TestDataUtils.buildInsertFromTokens(
				"INSERT INTO `effective_payment_status` VALUES (",
				( super.getId() * 2 ) - 1,
				"2013-01-01 12:00:00",
				"2013-09-12 12:00:00",
				"PAYMENT_PROCESSING",
				super.getId(),
				"current_timestamp",
				"current_timestamp",
				0 ));
		
		result.append( "\n" );
		
		result.append( TestDataUtils.buildInsertFromTokens(
				"INSERT INTO `effective_payment_status` VALUES (",
				( super.getId() * 2 ),
				"2013-09-12 12:00:01",
				"4500-02-01 00:00:00",
				"COMPLETED",
				super.getId(),
				"current_timestamp",
				"current_timestamp",
				0 ));
		
		return result.toString();
	}
	
	private BigDecimal newRandomBigDecimal(Random r ) {
	    BigInteger n = BigInteger.TEN.pow( 2);
	    return new BigDecimal(newRandomBigInteger(n, r), 2 );
	}

	private BigInteger newRandomBigInteger(BigInteger n, Random rnd) {
	    BigInteger r;
	    do {
	        r = new BigInteger(n.bitLength(), rnd);
	    } while (r.compareTo(n) >= 0);

	    return r.multiply( (new BigInteger( "10" )));
	}
	
	BigDecimal getSellerAmount( BigDecimal grossAmount, BigDecimal rodLaverAmount ) {
		BigDecimal unrounded = grossAmount.subtract( rodLaverAmount );
		return unrounded.setScale( 2, RoundingMode.CEILING);
	}
	
	BigDecimal getRodLaverAmount( BigDecimal grossAmount ) {
		BigDecimal unrounded = grossAmount.multiply( new BigDecimal( "0.05") ).add( new BigDecimal( "0.3") );
		return unrounded.setScale( 2, RoundingMode.CEILING );
	}

}
