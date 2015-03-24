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
package com.projectlaver.util;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class BusinessMetricsDTOTest {

	@Test
	public void testIntegerRatioAsString() {
		
		BusinessMetricsDTO dto = new BusinessMetricsDTO();
		
		assertNull( "Null numerator should return null.", dto.ratioAsString( null, 5 ) );
		assertNull( "Null denominator should return null.", dto.ratioAsString( 5, null ) );
		assertNull( "Zero denominator should return null.", dto.ratioAsString( 5, 0 ) );
		assertEquals( "100%", dto.ratioAsString( 1, 1 ));
		assertEquals( "0%", dto.ratioAsString( 0, 1 ));
		assertEquals( "10%", dto.ratioAsString( 1, 10 ));
	}

	@Test
	public void testBigDecimalRatioAsString() {
		
		BusinessMetricsDTO dto = new BusinessMetricsDTO();
		
		assertNull( "Null numerator should return null.", dto.ratioAsString( null, new BigDecimal( "5.00" ) ) );
		assertNull( "Null denominator should return null.", dto.ratioAsString( new BigDecimal( "5.00" ), null ) );
		assertEquals( "100%", dto.ratioAsString( new BigDecimal( "5.00" ), new BigDecimal( "5.00" ) ));
		assertEquals( "0%", dto.ratioAsString( new BigDecimal( "0.00" ), new BigDecimal( "5.00" ) ));
		assertEquals( "10%", dto.ratioAsString( new BigDecimal( "5.00" ), new BigDecimal( "50.00" ) ));
	}
	
	@Test
	public void testRoundingException() {
		BusinessMetricsDTO dto = new BusinessMetricsDTO();
		dto.setTwitterGrossSales( new BigDecimal( "0.00001" ));
		dto.setGrossSales( new BigDecimal( "0.50000" ));
		
		String result = dto.getTwitterGrossSalesRatio();
		assertNotNull( "Result shouldn't be null", result );
	}
	
	@Test
	public void testNonTerminatingDecimalException() {
		BusinessMetricsDTO dto = new BusinessMetricsDTO();
		dto.setGrossSales( new BigDecimal( "1.50000" ) );
		dto.setTwitterGrossSales( new BigDecimal( "1.00000" ) );
		
		String result = dto.getTwitterGrossSalesRatio();
		assertNotNull( "Result shouldn't be null", result );
	}

	
}
