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

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class ListingResponseHandler {

	/**
	 * Constants
	 */
	
	public static final String INITIAL_STATUS = "PROCESSING";
	
	/**
	 * Instance variables
	 */
	
	private final Log logger = LogFactory.getLog(getClass());
	
	private JdbcTemplate jdbcTemplate;
	
	public ListingResponseHandler()  {}
	
	public ListingResponseHandler( DataSource dataSource ) {
		this.jdbcTemplate = new JdbcTemplate( dataSource );
	}
	
	/**
	 * Instance methods
	 */
	
	public abstract String getSelectListingMessageProviderContentIdSql();
	
	public void processUserMessage( String sellerProviderUserId, ReplyMessageDTO message, Boolean doSelectBeforeInsert ) {
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "+++processUserMessage()" );
		}
		

		if( StringUtils.isNoneBlank( sellerProviderUserId, message.getHashtag() ) ) {
		
			/**
			 * First we check to see if this is a virtual reply to a campaign
			 * being run by this seller. This allows us to catch replies 
			 * where the "mother" listing message message is another id,
			 * the lister has repeated the campaign in another message,
			 * and the user has replied to _that_ message (in other words, not the actual 
			 * listing message).
			 */
			String virtualReplyTwitterId = this.correlateVirtualReplyToListingMessage( sellerProviderUserId, message.getHashtag() );
			
			if( virtualReplyTwitterId != null ) {
				
				/**
				 *  set isNaturalReply to false and override the inResponseToTwitterId since this 
				 * is a "virtual" reply using @mention #hashtag style
				 */
				
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( String.format( "Inserting message from provider: %s with id: %s as a virtual reply to: %s", message.getProviderId(), message.getTwitterId(), virtualReplyTwitterId ));
				}
				
				message.setInResponseToTwitterId( virtualReplyTwitterId );
				message.setIsNaturalReply( false );
				
			} else if( this.isNaturalReply( message.getInResponseToTwitterId() ) ) { 

				/**
				 * We didn't find a match using the @mention #hashtag style lookup, 
				 * but this message is a natural reply so we will treat it as such
				 */
				
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "Inserting message with id: " + message.getTwitterId() + " as a natural reply to: " + message.getInResponseToTwitterId() );
				}

				message.setIsNaturalReply( true );
				
			} 
			
			// if the message has been initialized (i.e., it's either a natural reply or a "virtual" reply), insert it
			if( message.getIsNaturalReply() != null ) {

				Boolean hasMessageBeenInsertedAlready = false;
				if( doSelectBeforeInsert ) {
					hasMessageBeenInsertedAlready = this.hasMessageBeenInsertedAlready( message );
				}
				
				// insert the message and lookup the content's author's user id, if it exists
				
				if( !hasMessageBeenInsertedAlready ) {
					Boolean messageCreated = this.insertReply( message );
	
					if( messageCreated && this.logger.isDebugEnabled() ) {
						this.logger.debug( "Message persistence complete for message with id: " + message.getTwitterId() );
					}
					
					if( !messageCreated ) {
						this.logger.error( "Unexpected persistence result of false for message with id: " + message.getTwitterId() + ", message: " + ReflectionToStringBuilder.toString( message ) );
					}
				} else {
					if( this.logger.isDebugEnabled() ) {
						this.logger.debug( "Ignoring message with id: " + message.getTwitterId() + " as it has already been inserted." );
					}	
				}
				
			} else {
				if( this.logger.isDebugEnabled() ) {
					this.logger.debug( "Ignoring message with id: " + message.getTwitterId() + " (neither a natural nor a virtual reply). " );
				}
			}
		} else {
			if( this.logger.isDebugEnabled() ) {
				this.logger.debug( "Ignoring message with id: " + message.getTwitterId() + " (does not contain an @mention and a #hashtag). " );
			}
		}
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "---processUserMessage()" );
		}

	}
	
	/**
	 * Attempts to correlate a "virtual" reply with the original listing message.
	 * 
	 * For Twitter, this is when a user writes a tweet in the style "#hashtag @user". For Facebook, 
	 * this is when a user writes on a seller's wall.
	 * 
	 * In either case, the logic attempts to lookup the provider's unique id for the root
	 * listing message that this tweet / Facebook post would be a "virtual" reply to.
	 * 
	 * @param mentionedProviderUserId the twitter user id of the first mention
	 * @param 
	 * @return the listing message's twitter id that this tweet is a "virtual" reply to, if any.
	 */
	protected String correlateVirtualReplyToListingMessage( String mentionedProviderUserId, String hashtag ) {
		String result = null;
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "+correlateVirtualReplyToListingMessage()" );
		}
		
		if( StringUtils.isNoneBlank( mentionedProviderUserId, hashtag ) ) {
				
			if( this.logger.isTraceEnabled() ) {
				this.logger.trace( "Message mentioned providerUserId: " + mentionedProviderUserId );
				this.logger.trace( "Message included hashtag: " + hashtag );
			}
			
			result = this.selectListingMessageProviderContentId( mentionedProviderUserId, hashtag );
			if( this.logger.isTraceEnabled() ) {
				if( result != null ) {
					this.logger.trace( "Listing message lookup based on providerUserId and hashtag returned original message twitter id: " + result );
				} else {
					this.logger.trace( "No matching active twitter listing message for this lister." );
				}
			}
		}
		
		if( this.logger.isDebugEnabled() ) {
			this.logger.debug( "-correlateVirtualReplyToListingMessage() with result: " + result );
		}
		
		return result;
	}
	
	protected Boolean insertReply( ReplyMessageDTO message ) {
		
    	if( this.logger.isTraceEnabled() ) {
			this.logger.trace( "RodlaverQueries.INSERT_MESSAGE_AND_LOOKUP_USER_ID_FK" );
		}
		int rows = this.jdbcTemplate.update( RodlaverQueries.INSERT_MESSAGE_AND_LOOKUP_USER_ID_FK,
				 new Object[] {
					message.getCreated(),
					message.getInResponseToTwitterId(),
					message.getIsNaturalReply(),
					message.getIsRetweet(),
					message.getProviderId(),
					message.getProviderUserId(),
					message.getText(),
					message.getHashtag(),
					message.getTwitterId(),
					message.getProviderId(),
					message.getProviderUserId(),
					message.getInResponseToTwitterId(),
					message.getInResponseToTwitterId() });
		
		return ( rows == 1 );
	}
	
	String selectListingMessageProviderContentId( String twitterProviderUserId, String hashtag ) {

		String sql = this.getSelectListingMessageProviderContentIdSql();
		
    	if( this.logger.isTraceEnabled() ) {
			this.logger.trace( sql + " with seller providerUserId: " + twitterProviderUserId + " and hashtag: " + hashtag );
		}
		Object[] args = new Object[] { twitterProviderUserId, hashtag };
		String result = null;
		
		try {
			result = jdbcTemplate.queryForObject( sql, args, String.class );
		} catch( EmptyResultDataAccessException e ) {
			if( this.logger.isTraceEnabled() ) {
				this.logger.trace( "No result found for seller providerUserId: " + twitterProviderUserId + " and hashtag: " + hashtag );
			}
		}

		return result;
	}
	
	Boolean isNaturalReply( String inReplyToStatusId ) {
		if( this.logger.isTraceEnabled() ) {
			this.logger.trace( "isNaturalReply arguments - inReplyToStatusId: " + inReplyToStatusId );
			this.logger.trace( "Result of isNaturalReply: " + StringUtils.isNotBlank( inReplyToStatusId )  );
		}

		return ( StringUtils.isNotBlank( inReplyToStatusId ) );
	}
	
	Boolean hasMessageBeenInsertedAlready( ReplyMessageDTO dto ) {
		
		return this.jdbcTemplate.queryForObject( "SELECT count(*) > 0 FROM Messages where providerId=? and twitterId=?", Boolean.class, dto.getProviderId(), dto.getTwitterId() );
		
	}
	
}
