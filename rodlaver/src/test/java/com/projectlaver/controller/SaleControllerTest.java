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
package com.projectlaver.controller;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Test;

public class SaleControllerTest {

	@Test
	public void testCompareNumericTypes() {
		Double[] doubleAmounts = new Double[] { 0.00, 0.01, 0.02 };
		BigDecimal[] bigDecimalAmounts = new BigDecimal[]{ new BigDecimal( "0.00" ), new BigDecimal( "0.01" ),new BigDecimal( "0.02" )  }; 
		
		for( int i = 0; i <= 2; i++ ) {
			assertEquals( String.valueOf( (int)(doubleAmounts[i]*100) ), bigDecimalAmounts[i].toString() );
		}
		
	}
	
	@Test
	public void substringTest() {
		String active1 = "ACTIVE-1";
		String active10 = "ACTIVE-10";
		String completed0 = "COMPLETED-0";
		String canceled1 = "CANCELED-1"; 
		
		StatusQuantity sq1 = new StatusQuantity( active1 );
		assertEquals( "ACTIVE", sq1.status );
		assertEquals( Integer.valueOf( 1 ), sq1.quantity );
		
		StatusQuantity sq2 = new StatusQuantity( active10 );
		assertEquals( "ACTIVE", sq2.status );
		assertEquals( Integer.valueOf( 10 ), sq2.quantity );
		
		StatusQuantity sq3 = new StatusQuantity( completed0 );
		assertEquals( "COMPLETED", sq3.status );
		assertEquals( Integer.valueOf( 0 ), sq3.quantity );
		
		StatusQuantity sq4 = new StatusQuantity( canceled1 );
		assertEquals( "CANCELED", sq4.status );
		assertEquals( Integer.valueOf( 1 ), sq4.quantity );
		
	}
	
	private class StatusQuantity {
		private String status;
		private Integer quantity;
		
		StatusQuantity( String statusQuantity ) {
			int delimeter = statusQuantity.indexOf( "-" );
			this.status = statusQuantity.substring( 0, delimeter );
			this.quantity = Integer.valueOf( statusQuantity.substring( delimeter + 1 ));
		}
	}

}
