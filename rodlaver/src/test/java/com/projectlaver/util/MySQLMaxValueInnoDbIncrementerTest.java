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

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class MySQLMaxValueInnoDbIncrementerTest {
	
	private SingleConnectionDataSource ds;
	
	@Before
	public void setup() throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");
		String appJdbcUrl = "jdbc:mysql://localhost:3306/hootit?user=hootit&password=hootit&noAccessToProcedureBodies=true";
		this.ds = new SingleConnectionDataSource( appJdbcUrl, false );
	}

	@Test
	public void test() {
		
		MySQLMaxValueInnoDbIncrementer incrementer = new MySQLMaxValueInnoDbIncrementer();
		incrementer.setCacheSize( 3 );
		incrementer.setDataSource( this.ds );
		incrementer.setIncrementerName( "VOUCHER_SEQ" );
		
		Long nextKey = incrementer.getNextKey();
		System.out.println( nextKey );
		assertNotNull( nextKey );
		
	}

}
