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
package com.projectlaver.batch.item.database.support;

import javax.sql.DataSource;

import org.springframework.batch.item.database.support.DataFieldMaxValueIncrementerFactory;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

import com.projectlaver.util.MySQLMaxValueInnoDbIncrementer;

public class RodlaverDataFieldMaxValueIncrementerFactory implements DataFieldMaxValueIncrementerFactory {
	
	private DataSource dataSource;
	
	private String incrementerColumnName = "ID";
	
	public RodlaverDataFieldMaxValueIncrementerFactory() {}

	public RodlaverDataFieldMaxValueIncrementerFactory( DataSource dataSource ) {
		this.dataSource = dataSource;
	}
	
	public void setDataSource( DataSource dataSource ) {
		this.dataSource = dataSource;
	}
	
	@Override
	public DataFieldMaxValueIncrementer getIncrementer(String databaseType, String incrementerName) {
		return new MySQLMaxValueInnoDbIncrementer( this.dataSource, incrementerName, this.incrementerColumnName );
	}

	@Override
	public boolean isSupportedIncrementerType(String databaseType) {
		if( "MYSQL".equals( databaseType.toUpperCase())) {
			return true;
		}
		
		return false;
	}

	@Override
	public String[] getSupportedIncrementerTypes() {
		String[] result = { "mysql" };
		return result;
	}

}
