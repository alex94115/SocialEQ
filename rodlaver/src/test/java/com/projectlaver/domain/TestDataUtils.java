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

public class TestDataUtils {

	public static String buildInsertFromTokens( Object... tokens ) {
		
		StringBuilder sb = new StringBuilder();
		
		for( int i = 0; i < tokens.length; i++ ) {
			if( i == 0 ) {
				sb.append( tokens[i] );
			} else if( tokens[i] instanceof String && !tokens[i].equals("NULL") && !tokens[i].equals("current_timestamp") ) {
				sb.append( "'" );
				sb.append( tokens[i] );
				sb.append( "'" );
			} else {
				sb.append( tokens[i] );
			}
			
			if( i != 0 && i+1 < tokens.length ) {
				sb.append( ", " );
			} else if( i+1 == tokens.length ) {
				sb.append( ");" );
			}
		}
		
		return sb.toString();
	}
}
