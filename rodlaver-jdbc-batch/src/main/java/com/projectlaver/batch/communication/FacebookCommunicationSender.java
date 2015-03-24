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
package com.projectlaver.batch.communication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.support.URIBuilder;

import com.projectlaver.batch.domain.MessageStateChangeCommunicationsCursorItem;

public class FacebookCommunicationSender implements ExternalCommunicationSender {

	private final Log logger = LogFactory.getLog(getClass());

	private static final String COMMENTS_POST_PATH = "/comments/";
	private static final String MESSAGE_POST_PARAMETER = "message";
	
	// used in test to disable actually sending facebook replies
	@Value("${doDisableSendingReplies}")
	private Boolean doDisableSendingReplies;

	@Override
	public Boolean sendExternalCommunication( MessageStateChangeCommunicationsCursorItem item, String formattedMessage ) {

		// Post the reply to Facebook, unless we're in disabled (testing) mode
		if( !doDisableSendingReplies ) {
			FacebookTemplate template = new FacebookTemplate( item.getFromAccessToken() );
	
			String replyToMessageId = item.getMessageTwitterId();
	
			URIBuilder uriBuilder = URIBuilder.fromUri( Facebook.GRAPH_API_URL + replyToMessageId + COMMENTS_POST_PATH );
			uriBuilder.queryParam( MESSAGE_POST_PARAMETER, formattedMessage );
	
			Object result = template.restOperations().postForObject( uriBuilder.build(), null, Object.class );
	
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( result );
			}
	
			return (result != null);
		
		} else {
			
			// actually sending replies externally is disabled (test mode), so just return true
			return true;
		}
	}

}
