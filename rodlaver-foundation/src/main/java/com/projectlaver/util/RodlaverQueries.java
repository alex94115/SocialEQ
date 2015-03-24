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

public interface RodlaverQueries {

	public static final String INSERT_MESSAGE_AND_LOOKUP_USER_ID_FK =
			"INSERT INTO Messages (version, created, inResponseToTwitterId, isNaturalReply,  isRetweet,		providerId, 	providerUserId,  status, 	  	text, containsKeyword, twitterId, user_id)  "
			+ " SELECT 			  0, 	   ?, 		?, 					   ?, 				?, 				?, 				?, 				 'PROCESSING', 	?, 	  (l.keyword = ?), ?, 		  u.id "
	  		+ " FROM Users u INNER JOIN UserConnection c ON u.username=c.userid "
	  		+ " RIGHT OUTER JOIN ( "
	  		+ " 	SELECT ? AS providerId, ? AS providerUserId FROM dual "
	  		+ " ) AS p ON p.providerId = c.providerId AND p.providerUserId = c.providerUserId "
	  		+ " LEFT OUTER JOIN ( "
	  		+ " 	SELECT l.keyword, listing_message.twitterId "
	  		+ "     FROM Listings l "
	  		+ "       INNER JOIN Messages listing_message ON l.id = listing_message.listing_id "
	  		+ "	    WHERE listing_message.twitterId = ? "
	  		+ " ) As l ON l.twitterId = ? ";
	
}
