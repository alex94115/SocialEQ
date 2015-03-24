# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1000, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2000, 32, current_timestamp(), current_timestamp(), 0, 1000, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4000, 2000, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5000, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6000, 32, current_timestamp(), current_timestamp(), 0, 5000, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7000, 6000, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1001, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2001, 32, current_timestamp(), current_timestamp(), 0, 1001, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4001, 2001, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5001, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6001, 32, current_timestamp(), current_timestamp(), 0, 5001, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7001, 6001, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1002, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2002, 32, current_timestamp(), current_timestamp(), 0, 1002, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4002, 2002, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5002, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6002, 32, current_timestamp(), current_timestamp(), 0, 5002, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7002, 6002, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1003, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2003, 32, current_timestamp(), current_timestamp(), 0, 1003, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4003, 2003, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5003, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6003, 32, current_timestamp(), current_timestamp(), 0, 5003, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7003, 6003, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1004, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2004, 32, current_timestamp(), current_timestamp(), 0, 1004, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4004, 2004, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5004, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6004, 32, current_timestamp(), current_timestamp(), 0, 5004, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7004, 6004, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1005, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2005, 32, current_timestamp(), current_timestamp(), 0, 1005, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4005, 2005, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5005, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6005, 32, current_timestamp(), current_timestamp(), 0, 5005, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7005, 6005, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1006, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2006, 32, current_timestamp(), current_timestamp(), 0, 1006, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4006, 2006, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5006, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6006, 32, current_timestamp(), current_timestamp(), 0, 5006, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7006, 6006, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1007, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2007, 32, current_timestamp(), current_timestamp(), 0, 1007, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4007, 2007, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5007, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6007, 32, current_timestamp(), current_timestamp(), 0, 5007, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7007, 6007, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1008, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2008, 32, current_timestamp(), current_timestamp(), 0, 1008, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4008, 2008, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5008, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6008, 32, current_timestamp(), current_timestamp(), 0, 5008, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7008, 6008, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1009, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2009, 32, current_timestamp(), current_timestamp(), 0, 1009, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4009, 2009, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5009, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6009, 32, current_timestamp(), current_timestamp(), 0, 5009, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7009, 6009, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1010, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2010, 32, current_timestamp(), current_timestamp(), 0, 1010, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4010, 2010, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5010, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6010, 32, current_timestamp(), current_timestamp(), 0, 5010, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7010, 6010, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1011, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2011, 32, current_timestamp(), current_timestamp(), 0, 1011, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4011, 2011, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5011, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6011, 32, current_timestamp(), current_timestamp(), 0, 5011, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7011, 6011, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1012, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2012, 32, current_timestamp(), current_timestamp(), 0, 1012, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4012, 2012, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5012, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6012, 32, current_timestamp(), current_timestamp(), 0, 5012, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7012, 6012, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1013, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2013, 32, current_timestamp(), current_timestamp(), 0, 1013, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4013, 2013, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5013, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6013, 32, current_timestamp(), current_timestamp(), 0, 5013, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7013, 6013, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1014, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2014, 32, current_timestamp(), current_timestamp(), 0, 1014, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4014, 2014, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5014, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6014, 32, current_timestamp(), current_timestamp(), 0, 5014, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7014, 6014, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1015, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2015, 32, current_timestamp(), current_timestamp(), 0, 1015, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4015, 2015, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5015, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6015, 32, current_timestamp(), current_timestamp(), 0, 5015, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7015, 6015, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1016, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2016, 32, current_timestamp(), current_timestamp(), 0, 1016, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4016, 2016, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5016, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6016, 32, current_timestamp(), current_timestamp(), 0, 5016, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7016, 6016, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1017, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2017, 32, current_timestamp(), current_timestamp(), 0, 1017, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4017, 2017, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5017, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6017, 32, current_timestamp(), current_timestamp(), 0, 5017, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7017, 6017, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1018, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2018, 32, current_timestamp(), current_timestamp(), 0, 1018, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4018, 2018, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5018, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6018, 32, current_timestamp(), current_timestamp(), 0, 5018, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7018, 6018, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1019, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2019, 32, current_timestamp(), current_timestamp(), 0, 1019, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4019, 2019, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5019, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6019, 32, current_timestamp(), current_timestamp(), 0, 5019, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7019, 6019, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1020, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2020, 32, current_timestamp(), current_timestamp(), 0, 1020, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4020, 2020, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5020, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6020, 32, current_timestamp(), current_timestamp(), 0, 5020, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7020, 6020, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1021, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2021, 32, current_timestamp(), current_timestamp(), 0, 1021, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4021, 2021, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5021, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6021, 32, current_timestamp(), current_timestamp(), 0, 5021, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7021, 6021, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1022, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2022, 32, current_timestamp(), current_timestamp(), 0, 1022, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4022, 2022, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5022, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6022, 32, current_timestamp(), current_timestamp(), 0, 5022, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7022, 6022, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1023, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2023, 32, current_timestamp(), current_timestamp(), 0, 1023, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4023, 2023, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5023, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6023, 32, current_timestamp(), current_timestamp(), 0, 5023, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7023, 6023, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1024, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2024, 32, current_timestamp(), current_timestamp(), 0, 1024, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4024, 2024, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5024, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6024, 32, current_timestamp(), current_timestamp(), 0, 5024, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7024, 6024, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1025, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2025, 32, current_timestamp(), current_timestamp(), 0, 1025, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4025, 2025, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5025, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6025, 32, current_timestamp(), current_timestamp(), 0, 5025, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7025, 6025, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1026, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2026, 32, current_timestamp(), current_timestamp(), 0, 1026, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4026, 2026, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5026, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6026, 32, current_timestamp(), current_timestamp(), 0, 5026, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7026, 6026, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1027, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2027, 32, current_timestamp(), current_timestamp(), 0, 1027, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4027, 2027, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5027, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6027, 32, current_timestamp(), current_timestamp(), 0, 5027, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7027, 6027, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1028, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2028, 32, current_timestamp(), current_timestamp(), 0, 1028, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4028, 2028, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5028, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6028, 32, current_timestamp(), current_timestamp(), 0, 5028, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7028, 6028, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1029, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2029, 32, current_timestamp(), current_timestamp(), 0, 1029, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4029, 2029, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5029, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6029, 32, current_timestamp(), current_timestamp(), 0, 5029, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7029, 6029, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1030, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2030, 32, current_timestamp(), current_timestamp(), 0, 1030, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4030, 2030, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5030, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6030, 32, current_timestamp(), current_timestamp(), 0, 5030, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7030, 6030, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1031, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2031, 32, current_timestamp(), current_timestamp(), 0, 1031, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4031, 2031, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5031, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6031, 32, current_timestamp(), current_timestamp(), 0, 5031, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7031, 6031, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1032, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2032, 32, current_timestamp(), current_timestamp(), 0, 1032, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4032, 2032, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5032, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6032, 32, current_timestamp(), current_timestamp(), 0, 5032, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7032, 6032, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1033, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2033, 32, current_timestamp(), current_timestamp(), 0, 1033, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4033, 2033, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5033, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6033, 32, current_timestamp(), current_timestamp(), 0, 5033, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7033, 6033, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1034, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2034, 32, current_timestamp(), current_timestamp(), 0, 1034, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4034, 2034, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5034, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6034, 32, current_timestamp(), current_timestamp(), 0, 5034, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7034, 6034, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1035, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2035, 32, current_timestamp(), current_timestamp(), 0, 1035, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4035, 2035, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5035, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6035, 32, current_timestamp(), current_timestamp(), 0, 5035, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7035, 6035, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1036, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2036, 32, current_timestamp(), current_timestamp(), 0, 1036, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4036, 2036, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5036, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6036, 32, current_timestamp(), current_timestamp(), 0, 5036, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7036, 6036, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1037, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2037, 32, current_timestamp(), current_timestamp(), 0, 1037, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4037, 2037, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5037, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6037, 32, current_timestamp(), current_timestamp(), 0, 5037, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7037, 6037, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1038, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2038, 32, current_timestamp(), current_timestamp(), 0, 1038, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4038, 2038, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5038, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6038, 32, current_timestamp(), current_timestamp(), 0, 5038, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7038, 6038, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1039, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2039, 32, current_timestamp(), current_timestamp(), 0, 1039, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4039, 2039, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5039, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6039, 32, current_timestamp(), current_timestamp(), 0, 5039, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7039, 6039, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1040, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2040, 32, current_timestamp(), current_timestamp(), 0, 1040, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4040, 2040, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5040, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6040, 32, current_timestamp(), current_timestamp(), 0, 5040, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7040, 6040, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1041, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2041, 32, current_timestamp(), current_timestamp(), 0, 1041, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4041, 2041, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5041, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6041, 32, current_timestamp(), current_timestamp(), 0, 5041, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7041, 6041, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1042, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2042, 32, current_timestamp(), current_timestamp(), 0, 1042, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4042, 2042, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5042, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6042, 32, current_timestamp(), current_timestamp(), 0, 5042, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7042, 6042, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1043, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2043, 32, current_timestamp(), current_timestamp(), 0, 1043, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4043, 2043, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5043, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6043, 32, current_timestamp(), current_timestamp(), 0, 5043, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7043, 6043, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1044, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2044, 32, current_timestamp(), current_timestamp(), 0, 1044, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4044, 2044, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5044, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6044, 32, current_timestamp(), current_timestamp(), 0, 5044, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7044, 6044, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1045, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2045, 32, current_timestamp(), current_timestamp(), 0, 1045, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4045, 2045, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5045, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6045, 32, current_timestamp(), current_timestamp(), 0, 5045, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7045, 6045, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1046, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2046, 32, current_timestamp(), current_timestamp(), 0, 1046, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4046, 2046, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5046, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6046, 32, current_timestamp(), current_timestamp(), 0, 5046, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7046, 6046, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1047, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2047, 32, current_timestamp(), current_timestamp(), 0, 1047, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4047, 2047, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5047, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6047, 32, current_timestamp(), current_timestamp(), 0, 5047, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7047, 6047, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1048, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2048, 32, current_timestamp(), current_timestamp(), 0, 1048, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4048, 2048, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5048, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6048, 32, current_timestamp(), current_timestamp(), 0, 5048, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7048, 6048, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1049, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2049, 32, current_timestamp(), current_timestamp(), 0, 1049, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4049, 2049, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5049, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6049, 32, current_timestamp(), current_timestamp(), 0, 5049, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7049, 6049, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1050, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2050, 32, current_timestamp(), current_timestamp(), 0, 1050, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4050, 2050, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5050, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6050, 32, current_timestamp(), current_timestamp(), 0, 5050, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7050, 6050, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1051, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2051, 32, current_timestamp(), current_timestamp(), 0, 1051, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4051, 2051, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5051, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6051, 32, current_timestamp(), current_timestamp(), 0, 5051, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7051, 6051, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1052, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2052, 32, current_timestamp(), current_timestamp(), 0, 1052, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4052, 2052, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5052, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6052, 32, current_timestamp(), current_timestamp(), 0, 5052, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7052, 6052, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1053, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2053, 32, current_timestamp(), current_timestamp(), 0, 1053, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4053, 2053, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5053, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6053, 32, current_timestamp(), current_timestamp(), 0, 5053, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7053, 6053, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1054, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2054, 32, current_timestamp(), current_timestamp(), 0, 1054, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4054, 2054, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5054, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6054, 32, current_timestamp(), current_timestamp(), 0, 5054, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7054, 6054, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1055, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2055, 32, current_timestamp(), current_timestamp(), 0, 1055, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4055, 2055, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5055, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6055, 32, current_timestamp(), current_timestamp(), 0, 5055, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7055, 6055, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1056, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2056, 32, current_timestamp(), current_timestamp(), 0, 1056, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4056, 2056, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5056, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6056, 32, current_timestamp(), current_timestamp(), 0, 5056, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7056, 6056, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1057, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2057, 32, current_timestamp(), current_timestamp(), 0, 1057, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4057, 2057, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5057, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6057, 32, current_timestamp(), current_timestamp(), 0, 5057, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7057, 6057, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1058, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2058, 32, current_timestamp(), current_timestamp(), 0, 1058, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4058, 2058, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5058, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6058, 32, current_timestamp(), current_timestamp(), 0, 5058, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7058, 6058, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1059, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2059, 32, current_timestamp(), current_timestamp(), 0, 1059, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4059, 2059, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5059, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6059, 32, current_timestamp(), current_timestamp(), 0, 5059, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7059, 6059, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1060, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2060, 32, current_timestamp(), current_timestamp(), 0, 1060, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4060, 2060, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5060, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6060, 32, current_timestamp(), current_timestamp(), 0, 5060, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7060, 6060, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1061, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2061, 32, current_timestamp(), current_timestamp(), 0, 1061, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4061, 2061, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5061, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6061, 32, current_timestamp(), current_timestamp(), 0, 5061, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7061, 6061, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1062, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2062, 32, current_timestamp(), current_timestamp(), 0, 1062, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4062, 2062, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5062, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6062, 32, current_timestamp(), current_timestamp(), 0, 5062, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7062, 6062, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1063, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2063, 32, current_timestamp(), current_timestamp(), 0, 1063, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4063, 2063, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5063, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6063, 32, current_timestamp(), current_timestamp(), 0, 5063, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7063, 6063, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1064, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2064, 32, current_timestamp(), current_timestamp(), 0, 1064, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4064, 2064, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5064, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6064, 32, current_timestamp(), current_timestamp(), 0, 5064, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7064, 6064, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1065, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2065, 32, current_timestamp(), current_timestamp(), 0, 1065, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4065, 2065, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5065, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6065, 32, current_timestamp(), current_timestamp(), 0, 5065, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7065, 6065, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1066, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2066, 32, current_timestamp(), current_timestamp(), 0, 1066, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4066, 2066, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5066, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6066, 32, current_timestamp(), current_timestamp(), 0, 5066, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7066, 6066, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1067, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2067, 32, current_timestamp(), current_timestamp(), 0, 1067, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4067, 2067, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5067, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6067, 32, current_timestamp(), current_timestamp(), 0, 5067, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7067, 6067, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1068, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2068, 32, current_timestamp(), current_timestamp(), 0, 1068, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4068, 2068, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5068, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6068, 32, current_timestamp(), current_timestamp(), 0, 5068, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7068, 6068, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1069, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2069, 32, current_timestamp(), current_timestamp(), 0, 1069, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4069, 2069, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5069, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6069, 32, current_timestamp(), current_timestamp(), 0, 5069, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7069, 6069, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1070, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2070, 32, current_timestamp(), current_timestamp(), 0, 1070, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4070, 2070, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5070, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6070, 32, current_timestamp(), current_timestamp(), 0, 5070, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7070, 6070, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1071, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2071, 32, current_timestamp(), current_timestamp(), 0, 1071, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4071, 2071, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5071, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6071, 32, current_timestamp(), current_timestamp(), 0, 5071, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7071, 6071, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1072, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2072, 32, current_timestamp(), current_timestamp(), 0, 1072, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4072, 2072, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5072, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6072, 32, current_timestamp(), current_timestamp(), 0, 5072, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7072, 6072, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1073, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2073, 32, current_timestamp(), current_timestamp(), 0, 1073, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4073, 2073, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5073, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6073, 32, current_timestamp(), current_timestamp(), 0, 5073, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7073, 6073, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1074, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2074, 32, current_timestamp(), current_timestamp(), 0, 1074, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4074, 2074, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5074, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6074, 32, current_timestamp(), current_timestamp(), 0, 5074, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7074, 6074, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1075, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2075, 32, current_timestamp(), current_timestamp(), 0, 1075, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4075, 2075, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5075, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6075, 32, current_timestamp(), current_timestamp(), 0, 5075, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7075, 6075, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1076, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2076, 32, current_timestamp(), current_timestamp(), 0, 1076, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4076, 2076, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5076, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6076, 32, current_timestamp(), current_timestamp(), 0, 5076, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7076, 6076, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1077, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2077, 32, current_timestamp(), current_timestamp(), 0, 1077, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4077, 2077, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5077, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6077, 32, current_timestamp(), current_timestamp(), 0, 5077, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7077, 6077, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1078, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2078, 32, current_timestamp(), current_timestamp(), 0, 1078, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4078, 2078, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5078, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6078, 32, current_timestamp(), current_timestamp(), 0, 5078, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7078, 6078, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1079, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2079, 32, current_timestamp(), current_timestamp(), 0, 1079, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4079, 2079, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5079, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6079, 32, current_timestamp(), current_timestamp(), 0, 5079, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7079, 6079, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1080, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2080, 32, current_timestamp(), current_timestamp(), 0, 1080, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4080, 2080, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5080, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6080, 32, current_timestamp(), current_timestamp(), 0, 5080, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7080, 6080, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1081, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2081, 32, current_timestamp(), current_timestamp(), 0, 1081, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4081, 2081, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5081, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6081, 32, current_timestamp(), current_timestamp(), 0, 5081, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7081, 6081, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1082, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2082, 32, current_timestamp(), current_timestamp(), 0, 1082, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4082, 2082, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5082, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6082, 32, current_timestamp(), current_timestamp(), 0, 5082, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7082, 6082, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1083, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2083, 32, current_timestamp(), current_timestamp(), 0, 1083, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4083, 2083, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5083, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6083, 32, current_timestamp(), current_timestamp(), 0, 5083, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7083, 6083, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1084, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2084, 32, current_timestamp(), current_timestamp(), 0, 1084, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4084, 2084, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5084, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6084, 32, current_timestamp(), current_timestamp(), 0, 5084, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7084, 6084, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1085, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2085, 32, current_timestamp(), current_timestamp(), 0, 1085, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4085, 2085, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5085, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6085, 32, current_timestamp(), current_timestamp(), 0, 5085, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7085, 6085, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1086, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2086, 32, current_timestamp(), current_timestamp(), 0, 1086, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4086, 2086, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5086, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6086, 32, current_timestamp(), current_timestamp(), 0, 5086, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7086, 6086, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1087, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2087, 32, current_timestamp(), current_timestamp(), 0, 1087, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4087, 2087, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5087, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6087, 32, current_timestamp(), current_timestamp(), 0, 5087, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7087, 6087, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1088, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2088, 32, current_timestamp(), current_timestamp(), 0, 1088, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4088, 2088, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5088, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6088, 32, current_timestamp(), current_timestamp(), 0, 5088, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7088, 6088, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1089, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2089, 32, current_timestamp(), current_timestamp(), 0, 1089, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4089, 2089, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5089, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6089, 32, current_timestamp(), current_timestamp(), 0, 5089, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7089, 6089, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1090, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2090, 32, current_timestamp(), current_timestamp(), 0, 1090, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4090, 2090, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5090, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6090, 32, current_timestamp(), current_timestamp(), 0, 5090, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7090, 6090, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1091, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2091, 32, current_timestamp(), current_timestamp(), 0, 1091, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4091, 2091, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5091, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6091, 32, current_timestamp(), current_timestamp(), 0, 5091, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7091, 6091, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1092, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2092, 32, current_timestamp(), current_timestamp(), 0, 1092, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4092, 2092, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5092, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6092, 32, current_timestamp(), current_timestamp(), 0, 5092, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7092, 6092, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1093, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2093, 32, current_timestamp(), current_timestamp(), 0, 1093, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4093, 2093, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5093, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6093, 32, current_timestamp(), current_timestamp(), 0, 5093, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7093, 6093, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1094, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2094, 32, current_timestamp(), current_timestamp(), 0, 1094, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4094, 2094, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5094, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6094, 32, current_timestamp(), current_timestamp(), 0, 5094, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7094, 6094, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1095, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2095, 32, current_timestamp(), current_timestamp(), 0, 1095, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4095, 2095, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5095, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6095, 32, current_timestamp(), current_timestamp(), 0, 5095, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7095, 6095, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1096, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2096, 32, current_timestamp(), current_timestamp(), 0, 1096, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4096, 2096, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5096, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6096, 32, current_timestamp(), current_timestamp(), 0, 5096, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7096, 6096, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1097, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2097, 32, current_timestamp(), current_timestamp(), 0, 1097, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4097, 2097, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5097, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6097, 32, current_timestamp(), current_timestamp(), 0, 5097, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7097, 6097, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1098, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2098, 32, current_timestamp(), current_timestamp(), 0, 1098, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4098, 2098, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5098, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6098, 32, current_timestamp(), current_timestamp(), 0, 5098, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7098, 6098, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1099, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2099, 32, current_timestamp(), current_timestamp(), 0, 1099, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4099, 2099, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5099, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6099, 32, current_timestamp(), current_timestamp(), 0, 5099, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7099, 6099, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1100, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2100, 32, current_timestamp(), current_timestamp(), 0, 1100, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4100, 2100, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5100, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6100, 32, current_timestamp(), current_timestamp(), 0, 5100, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7100, 6100, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1101, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2101, 32, current_timestamp(), current_timestamp(), 0, 1101, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4101, 2101, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5101, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6101, 32, current_timestamp(), current_timestamp(), 0, 5101, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7101, 6101, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1102, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2102, 32, current_timestamp(), current_timestamp(), 0, 1102, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4102, 2102, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5102, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6102, 32, current_timestamp(), current_timestamp(), 0, 5102, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7102, 6102, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1103, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2103, 32, current_timestamp(), current_timestamp(), 0, 1103, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4103, 2103, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5103, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6103, 32, current_timestamp(), current_timestamp(), 0, 5103, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7103, 6103, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1104, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2104, 32, current_timestamp(), current_timestamp(), 0, 1104, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4104, 2104, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5104, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6104, 32, current_timestamp(), current_timestamp(), 0, 5104, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7104, 6104, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1105, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2105, 32, current_timestamp(), current_timestamp(), 0, 1105, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4105, 2105, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5105, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6105, 32, current_timestamp(), current_timestamp(), 0, 5105, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7105, 6105, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1106, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2106, 32, current_timestamp(), current_timestamp(), 0, 1106, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4106, 2106, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5106, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6106, 32, current_timestamp(), current_timestamp(), 0, 5106, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7106, 6106, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1107, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2107, 32, current_timestamp(), current_timestamp(), 0, 1107, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4107, 2107, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5107, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6107, 32, current_timestamp(), current_timestamp(), 0, 5107, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7107, 6107, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1108, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2108, 32, current_timestamp(), current_timestamp(), 0, 1108, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4108, 2108, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5108, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6108, 32, current_timestamp(), current_timestamp(), 0, 5108, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7108, 6108, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1109, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2109, 32, current_timestamp(), current_timestamp(), 0, 1109, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4109, 2109, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5109, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6109, 32, current_timestamp(), current_timestamp(), 0, 5109, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7109, 6109, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1110, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2110, 32, current_timestamp(), current_timestamp(), 0, 1110, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4110, 2110, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5110, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6110, 32, current_timestamp(), current_timestamp(), 0, 5110, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7110, 6110, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1111, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2111, 32, current_timestamp(), current_timestamp(), 0, 1111, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4111, 2111, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5111, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6111, 32, current_timestamp(), current_timestamp(), 0, 5111, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7111, 6111, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1112, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2112, 32, current_timestamp(), current_timestamp(), 0, 1112, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4112, 2112, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5112, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6112, 32, current_timestamp(), current_timestamp(), 0, 5112, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7112, 6112, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1113, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2113, 32, current_timestamp(), current_timestamp(), 0, 1113, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4113, 2113, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5113, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6113, 32, current_timestamp(), current_timestamp(), 0, 5113, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7113, 6113, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1114, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2114, 32, current_timestamp(), current_timestamp(), 0, 1114, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4114, 2114, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5114, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6114, 32, current_timestamp(), current_timestamp(), 0, 5114, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7114, 6114, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1115, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2115, 32, current_timestamp(), current_timestamp(), 0, 1115, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4115, 2115, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5115, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6115, 32, current_timestamp(), current_timestamp(), 0, 5115, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7115, 6115, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1116, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2116, 32, current_timestamp(), current_timestamp(), 0, 1116, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4116, 2116, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5116, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6116, 32, current_timestamp(), current_timestamp(), 0, 5116, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7116, 6116, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1117, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2117, 32, current_timestamp(), current_timestamp(), 0, 1117, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4117, 2117, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5117, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6117, 32, current_timestamp(), current_timestamp(), 0, 5117, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7117, 6117, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1118, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2118, 32, current_timestamp(), current_timestamp(), 0, 1118, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4118, 2118, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5118, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6118, 32, current_timestamp(), current_timestamp(), 0, 5118, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7118, 6118, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1119, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2119, 32, current_timestamp(), current_timestamp(), 0, 1119, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4119, 2119, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5119, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6119, 32, current_timestamp(), current_timestamp(), 0, 5119, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7119, 6119, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1120, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2120, 32, current_timestamp(), current_timestamp(), 0, 1120, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4120, 2120, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5120, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6120, 32, current_timestamp(), current_timestamp(), 0, 5120, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7120, 6120, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1121, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2121, 32, current_timestamp(), current_timestamp(), 0, 1121, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4121, 2121, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5121, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6121, 32, current_timestamp(), current_timestamp(), 0, 5121, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7121, 6121, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1122, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2122, 32, current_timestamp(), current_timestamp(), 0, 1122, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4122, 2122, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5122, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6122, 32, current_timestamp(), current_timestamp(), 0, 5122, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7122, 6122, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1123, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2123, 32, current_timestamp(), current_timestamp(), 0, 1123, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4123, 2123, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5123, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6123, 32, current_timestamp(), current_timestamp(), 0, 5123, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7123, 6123, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1124, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2124, 32, current_timestamp(), current_timestamp(), 0, 1124, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4124, 2124, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5124, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6124, 32, current_timestamp(), current_timestamp(), 0, 5124, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7124, 6124, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1125, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2125, 32, current_timestamp(), current_timestamp(), 0, 1125, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4125, 2125, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5125, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6125, 32, current_timestamp(), current_timestamp(), 0, 5125, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7125, 6125, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1126, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2126, 32, current_timestamp(), current_timestamp(), 0, 1126, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4126, 2126, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5126, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6126, 32, current_timestamp(), current_timestamp(), 0, 5126, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7126, 6126, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1127, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2127, 32, current_timestamp(), current_timestamp(), 0, 1127, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4127, 2127, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5127, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6127, 32, current_timestamp(), current_timestamp(), 0, 5127, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7127, 6127, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1128, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2128, 32, current_timestamp(), current_timestamp(), 0, 1128, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4128, 2128, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5128, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6128, 32, current_timestamp(), current_timestamp(), 0, 5128, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7128, 6128, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1129, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2129, 32, current_timestamp(), current_timestamp(), 0, 1129, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4129, 2129, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5129, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6129, 32, current_timestamp(), current_timestamp(), 0, 5129, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7129, 6129, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1130, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2130, 32, current_timestamp(), current_timestamp(), 0, 1130, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4130, 2130, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5130, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6130, 32, current_timestamp(), current_timestamp(), 0, 5130, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7130, 6130, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1131, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2131, 32, current_timestamp(), current_timestamp(), 0, 1131, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4131, 2131, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5131, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6131, 32, current_timestamp(), current_timestamp(), 0, 5131, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7131, 6131, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1132, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2132, 32, current_timestamp(), current_timestamp(), 0, 1132, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4132, 2132, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5132, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6132, 32, current_timestamp(), current_timestamp(), 0, 5132, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7132, 6132, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1133, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2133, 32, current_timestamp(), current_timestamp(), 0, 1133, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4133, 2133, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5133, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6133, 32, current_timestamp(), current_timestamp(), 0, 5133, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7133, 6133, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1134, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2134, 32, current_timestamp(), current_timestamp(), 0, 1134, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4134, 2134, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5134, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6134, 32, current_timestamp(), current_timestamp(), 0, 5134, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7134, 6134, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1135, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2135, 32, current_timestamp(), current_timestamp(), 0, 1135, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4135, 2135, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5135, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6135, 32, current_timestamp(), current_timestamp(), 0, 5135, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7135, 6135, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1136, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2136, 32, current_timestamp(), current_timestamp(), 0, 1136, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4136, 2136, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5136, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6136, 32, current_timestamp(), current_timestamp(), 0, 5136, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7136, 6136, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1137, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2137, 32, current_timestamp(), current_timestamp(), 0, 1137, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4137, 2137, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5137, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6137, 32, current_timestamp(), current_timestamp(), 0, 5137, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7137, 6137, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1138, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2138, 32, current_timestamp(), current_timestamp(), 0, 1138, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4138, 2138, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5138, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6138, 32, current_timestamp(), current_timestamp(), 0, 5138, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7138, 6138, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1139, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2139, 32, current_timestamp(), current_timestamp(), 0, 1139, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4139, 2139, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5139, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6139, 32, current_timestamp(), current_timestamp(), 0, 5139, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7139, 6139, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1140, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2140, 32, current_timestamp(), current_timestamp(), 0, 1140, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4140, 2140, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5140, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6140, 32, current_timestamp(), current_timestamp(), 0, 5140, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7140, 6140, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1141, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2141, 32, current_timestamp(), current_timestamp(), 0, 1141, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4141, 2141, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5141, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6141, 32, current_timestamp(), current_timestamp(), 0, 5141, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7141, 6141, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1142, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2142, 32, current_timestamp(), current_timestamp(), 0, 1142, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4142, 2142, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5142, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6142, 32, current_timestamp(), current_timestamp(), 0, 5142, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7142, 6142, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1143, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2143, 32, current_timestamp(), current_timestamp(), 0, 1143, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4143, 2143, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5143, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6143, 32, current_timestamp(), current_timestamp(), 0, 5143, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7143, 6143, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1144, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2144, 32, current_timestamp(), current_timestamp(), 0, 1144, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4144, 2144, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5144, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6144, 32, current_timestamp(), current_timestamp(), 0, 5144, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7144, 6144, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1145, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2145, 32, current_timestamp(), current_timestamp(), 0, 1145, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4145, 2145, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5145, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6145, 32, current_timestamp(), current_timestamp(), 0, 5145, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7145, 6145, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1146, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2146, 32, current_timestamp(), current_timestamp(), 0, 1146, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4146, 2146, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5146, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6146, 32, current_timestamp(), current_timestamp(), 0, 5146, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7146, 6146, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1147, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2147, 32, current_timestamp(), current_timestamp(), 0, 1147, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4147, 2147, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5147, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6147, 32, current_timestamp(), current_timestamp(), 0, 5147, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7147, 6147, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1148, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2148, 32, current_timestamp(), current_timestamp(), 0, 1148, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4148, 2148, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5148, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6148, 32, current_timestamp(), current_timestamp(), 0, 5148, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7148, 6148, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1149, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2149, 32, current_timestamp(), current_timestamp(), 0, 1149, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4149, 2149, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5149, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6149, 32, current_timestamp(), current_timestamp(), 0, 5149, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7149, 6149, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1150, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2150, 32, current_timestamp(), current_timestamp(), 0, 1150, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4150, 2150, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5150, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6150, 32, current_timestamp(), current_timestamp(), 0, 5150, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7150, 6150, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1151, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2151, 32, current_timestamp(), current_timestamp(), 0, 1151, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4151, 2151, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5151, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6151, 32, current_timestamp(), current_timestamp(), 0, 5151, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7151, 6151, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1152, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2152, 32, current_timestamp(), current_timestamp(), 0, 1152, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4152, 2152, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5152, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6152, 32, current_timestamp(), current_timestamp(), 0, 5152, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7152, 6152, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1153, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2153, 32, current_timestamp(), current_timestamp(), 0, 1153, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4153, 2153, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5153, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6153, 32, current_timestamp(), current_timestamp(), 0, 5153, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7153, 6153, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1154, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2154, 32, current_timestamp(), current_timestamp(), 0, 1154, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4154, 2154, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5154, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6154, 32, current_timestamp(), current_timestamp(), 0, 5154, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7154, 6154, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1155, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2155, 32, current_timestamp(), current_timestamp(), 0, 1155, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4155, 2155, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5155, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6155, 32, current_timestamp(), current_timestamp(), 0, 5155, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7155, 6155, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1156, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2156, 32, current_timestamp(), current_timestamp(), 0, 1156, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4156, 2156, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5156, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6156, 32, current_timestamp(), current_timestamp(), 0, 5156, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7156, 6156, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1157, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2157, 32, current_timestamp(), current_timestamp(), 0, 1157, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4157, 2157, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5157, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6157, 32, current_timestamp(), current_timestamp(), 0, 5157, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7157, 6157, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1158, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2158, 32, current_timestamp(), current_timestamp(), 0, 1158, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4158, 2158, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5158, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6158, 32, current_timestamp(), current_timestamp(), 0, 5158, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7158, 6158, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1159, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2159, 32, current_timestamp(), current_timestamp(), 0, 1159, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4159, 2159, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5159, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6159, 32, current_timestamp(), current_timestamp(), 0, 5159, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7159, 6159, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1160, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2160, 32, current_timestamp(), current_timestamp(), 0, 1160, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4160, 2160, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5160, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6160, 32, current_timestamp(), current_timestamp(), 0, 5160, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7160, 6160, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1161, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2161, 32, current_timestamp(), current_timestamp(), 0, 1161, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4161, 2161, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5161, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6161, 32, current_timestamp(), current_timestamp(), 0, 5161, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7161, 6161, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1162, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2162, 32, current_timestamp(), current_timestamp(), 0, 1162, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4162, 2162, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5162, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6162, 32, current_timestamp(), current_timestamp(), 0, 5162, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7162, 6162, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1163, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2163, 32, current_timestamp(), current_timestamp(), 0, 1163, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4163, 2163, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5163, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6163, 32, current_timestamp(), current_timestamp(), 0, 5163, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7163, 6163, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1164, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2164, 32, current_timestamp(), current_timestamp(), 0, 1164, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4164, 2164, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5164, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6164, 32, current_timestamp(), current_timestamp(), 0, 5164, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7164, 6164, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1165, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2165, 32, current_timestamp(), current_timestamp(), 0, 1165, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4165, 2165, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5165, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6165, 32, current_timestamp(), current_timestamp(), 0, 5165, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7165, 6165, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1166, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2166, 32, current_timestamp(), current_timestamp(), 0, 1166, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4166, 2166, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5166, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6166, 32, current_timestamp(), current_timestamp(), 0, 5166, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7166, 6166, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1167, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2167, 32, current_timestamp(), current_timestamp(), 0, 1167, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4167, 2167, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5167, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6167, 32, current_timestamp(), current_timestamp(), 0, 5167, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7167, 6167, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1168, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2168, 32, current_timestamp(), current_timestamp(), 0, 1168, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4168, 2168, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5168, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6168, 32, current_timestamp(), current_timestamp(), 0, 5168, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7168, 6168, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1169, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2169, 32, current_timestamp(), current_timestamp(), 0, 1169, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4169, 2169, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5169, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6169, 32, current_timestamp(), current_timestamp(), 0, 5169, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7169, 6169, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1170, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2170, 32, current_timestamp(), current_timestamp(), 0, 1170, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4170, 2170, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5170, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6170, 32, current_timestamp(), current_timestamp(), 0, 5170, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7170, 6170, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1171, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2171, 32, current_timestamp(), current_timestamp(), 0, 1171, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4171, 2171, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5171, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6171, 32, current_timestamp(), current_timestamp(), 0, 5171, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7171, 6171, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1172, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2172, 32, current_timestamp(), current_timestamp(), 0, 1172, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4172, 2172, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5172, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6172, 32, current_timestamp(), current_timestamp(), 0, 5172, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7172, 6172, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1173, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2173, 32, current_timestamp(), current_timestamp(), 0, 1173, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4173, 2173, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5173, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6173, 32, current_timestamp(), current_timestamp(), 0, 5173, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7173, 6173, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1174, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2174, 32, current_timestamp(), current_timestamp(), 0, 1174, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4174, 2174, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5174, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6174, 32, current_timestamp(), current_timestamp(), 0, 5174, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7174, 6174, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1175, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2175, 32, current_timestamp(), current_timestamp(), 0, 1175, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4175, 2175, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5175, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6175, 32, current_timestamp(), current_timestamp(), 0, 5175, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7175, 6175, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1176, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2176, 32, current_timestamp(), current_timestamp(), 0, 1176, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4176, 2176, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5176, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6176, 32, current_timestamp(), current_timestamp(), 0, 5176, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7176, 6176, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1177, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2177, 32, current_timestamp(), current_timestamp(), 0, 1177, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4177, 2177, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5177, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6177, 32, current_timestamp(), current_timestamp(), 0, 5177, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7177, 6177, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1178, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2178, 32, current_timestamp(), current_timestamp(), 0, 1178, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4178, 2178, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5178, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6178, 32, current_timestamp(), current_timestamp(), 0, 5178, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7178, 6178, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1179, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2179, 32, current_timestamp(), current_timestamp(), 0, 1179, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4179, 2179, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5179, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6179, 32, current_timestamp(), current_timestamp(), 0, 5179, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7179, 6179, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1180, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2180, 32, current_timestamp(), current_timestamp(), 0, 1180, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4180, 2180, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5180, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6180, 32, current_timestamp(), current_timestamp(), 0, 5180, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7180, 6180, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1181, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2181, 32, current_timestamp(), current_timestamp(), 0, 1181, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4181, 2181, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5181, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6181, 32, current_timestamp(), current_timestamp(), 0, 5181, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7181, 6181, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1182, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2182, 32, current_timestamp(), current_timestamp(), 0, 1182, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4182, 2182, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5182, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6182, 32, current_timestamp(), current_timestamp(), 0, 5182, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7182, 6182, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1183, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2183, 32, current_timestamp(), current_timestamp(), 0, 1183, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4183, 2183, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5183, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6183, 32, current_timestamp(), current_timestamp(), 0, 5183, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7183, 6183, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1184, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2184, 32, current_timestamp(), current_timestamp(), 0, 1184, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4184, 2184, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5184, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6184, 32, current_timestamp(), current_timestamp(), 0, 5184, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7184, 6184, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1185, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2185, 32, current_timestamp(), current_timestamp(), 0, 1185, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4185, 2185, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5185, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6185, 32, current_timestamp(), current_timestamp(), 0, 5185, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7185, 6185, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1186, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2186, 32, current_timestamp(), current_timestamp(), 0, 1186, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4186, 2186, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5186, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6186, 32, current_timestamp(), current_timestamp(), 0, 5186, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7186, 6186, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1187, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2187, 32, current_timestamp(), current_timestamp(), 0, 1187, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4187, 2187, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5187, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6187, 32, current_timestamp(), current_timestamp(), 0, 5187, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7187, 6187, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1188, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2188, 32, current_timestamp(), current_timestamp(), 0, 1188, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4188, 2188, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5188, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6188, 32, current_timestamp(), current_timestamp(), 0, 5188, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7188, 6188, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1189, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2189, 32, current_timestamp(), current_timestamp(), 0, 1189, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4189, 2189, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5189, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6189, 32, current_timestamp(), current_timestamp(), 0, 5189, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7189, 6189, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1190, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2190, 32, current_timestamp(), current_timestamp(), 0, 1190, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4190, 2190, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5190, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6190, 32, current_timestamp(), current_timestamp(), 0, 5190, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7190, 6190, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1191, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2191, 32, current_timestamp(), current_timestamp(), 0, 1191, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4191, 2191, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5191, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6191, 32, current_timestamp(), current_timestamp(), 0, 5191, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7191, 6191, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1192, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2192, 32, current_timestamp(), current_timestamp(), 0, 1192, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4192, 2192, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5192, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6192, 32, current_timestamp(), current_timestamp(), 0, 5192, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7192, 6192, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1193, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2193, 32, current_timestamp(), current_timestamp(), 0, 1193, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4193, 2193, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5193, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6193, 32, current_timestamp(), current_timestamp(), 0, 5193, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7193, 6193, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1194, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2194, 32, current_timestamp(), current_timestamp(), 0, 1194, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4194, 2194, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5194, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6194, 32, current_timestamp(), current_timestamp(), 0, 5194, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7194, 6194, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1195, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2195, 32, current_timestamp(), current_timestamp(), 0, 1195, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4195, 2195, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5195, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6195, 32, current_timestamp(), current_timestamp(), 0, 5195, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7195, 6195, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1196, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2196, 32, current_timestamp(), current_timestamp(), 0, 1196, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4196, 2196, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5196, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6196, 32, current_timestamp(), current_timestamp(), 0, 5196, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7196, 6196, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1197, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2197, 32, current_timestamp(), current_timestamp(), 0, 1197, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4197, 2197, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5197, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6197, 32, current_timestamp(), current_timestamp(), 0, 5197, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7197, 6197, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1198, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2198, 32, current_timestamp(), current_timestamp(), 0, 1198, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4198, 2198, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5198, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6198, 32, current_timestamp(), current_timestamp(), 0, 5198, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7198, 6198, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1199, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2199, 32, current_timestamp(), current_timestamp(), 0, 1199, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4199, 2199, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5199, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6199, 32, current_timestamp(), current_timestamp(), 0, 5199, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7199, 6199, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1200, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2200, 32, current_timestamp(), current_timestamp(), 0, 1200, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4200, 2200, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5200, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6200, 32, current_timestamp(), current_timestamp(), 0, 5200, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7200, 6200, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1201, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2201, 32, current_timestamp(), current_timestamp(), 0, 1201, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4201, 2201, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5201, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6201, 32, current_timestamp(), current_timestamp(), 0, 5201, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7201, 6201, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1202, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2202, 32, current_timestamp(), current_timestamp(), 0, 1202, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4202, 2202, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5202, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6202, 32, current_timestamp(), current_timestamp(), 0, 5202, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7202, 6202, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1203, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2203, 32, current_timestamp(), current_timestamp(), 0, 1203, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4203, 2203, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5203, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6203, 32, current_timestamp(), current_timestamp(), 0, 5203, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7203, 6203, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1204, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2204, 32, current_timestamp(), current_timestamp(), 0, 1204, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4204, 2204, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5204, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6204, 32, current_timestamp(), current_timestamp(), 0, 5204, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7204, 6204, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1205, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2205, 32, current_timestamp(), current_timestamp(), 0, 1205, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4205, 2205, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5205, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6205, 32, current_timestamp(), current_timestamp(), 0, 5205, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7205, 6205, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1206, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2206, 32, current_timestamp(), current_timestamp(), 0, 1206, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4206, 2206, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5206, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6206, 32, current_timestamp(), current_timestamp(), 0, 5206, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7206, 6206, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1207, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2207, 32, current_timestamp(), current_timestamp(), 0, 1207, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4207, 2207, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5207, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6207, 32, current_timestamp(), current_timestamp(), 0, 5207, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7207, 6207, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1208, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2208, 32, current_timestamp(), current_timestamp(), 0, 1208, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4208, 2208, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5208, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6208, 32, current_timestamp(), current_timestamp(), 0, 5208, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7208, 6208, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1209, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2209, 32, current_timestamp(), current_timestamp(), 0, 1209, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4209, 2209, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5209, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6209, 32, current_timestamp(), current_timestamp(), 0, 5209, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7209, 6209, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1210, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2210, 32, current_timestamp(), current_timestamp(), 0, 1210, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4210, 2210, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5210, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6210, 32, current_timestamp(), current_timestamp(), 0, 5210, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7210, 6210, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1211, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2211, 32, current_timestamp(), current_timestamp(), 0, 1211, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4211, 2211, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5211, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6211, 32, current_timestamp(), current_timestamp(), 0, 5211, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7211, 6211, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1212, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2212, 32, current_timestamp(), current_timestamp(), 0, 1212, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4212, 2212, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5212, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6212, 32, current_timestamp(), current_timestamp(), 0, 5212, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7212, 6212, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1213, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2213, 32, current_timestamp(), current_timestamp(), 0, 1213, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4213, 2213, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5213, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6213, 32, current_timestamp(), current_timestamp(), 0, 5213, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7213, 6213, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1214, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2214, 32, current_timestamp(), current_timestamp(), 0, 1214, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4214, 2214, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5214, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6214, 32, current_timestamp(), current_timestamp(), 0, 5214, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7214, 6214, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1215, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2215, 32, current_timestamp(), current_timestamp(), 0, 1215, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4215, 2215, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5215, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6215, 32, current_timestamp(), current_timestamp(), 0, 5215, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7215, 6215, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1216, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2216, 32, current_timestamp(), current_timestamp(), 0, 1216, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4216, 2216, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5216, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6216, 32, current_timestamp(), current_timestamp(), 0, 5216, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7216, 6216, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1217, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2217, 32, current_timestamp(), current_timestamp(), 0, 1217, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4217, 2217, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5217, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6217, 32, current_timestamp(), current_timestamp(), 0, 5217, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7217, 6217, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1218, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2218, 32, current_timestamp(), current_timestamp(), 0, 1218, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4218, 2218, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5218, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6218, 32, current_timestamp(), current_timestamp(), 0, 5218, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7218, 6218, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1219, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2219, 32, current_timestamp(), current_timestamp(), 0, 1219, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4219, 2219, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5219, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6219, 32, current_timestamp(), current_timestamp(), 0, 5219, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7219, 6219, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1220, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2220, 32, current_timestamp(), current_timestamp(), 0, 1220, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4220, 2220, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5220, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6220, 32, current_timestamp(), current_timestamp(), 0, 5220, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7220, 6220, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1221, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2221, 32, current_timestamp(), current_timestamp(), 0, 1221, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4221, 2221, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5221, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6221, 32, current_timestamp(), current_timestamp(), 0, 5221, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7221, 6221, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1222, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2222, 32, current_timestamp(), current_timestamp(), 0, 1222, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4222, 2222, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5222, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6222, 32, current_timestamp(), current_timestamp(), 0, 5222, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7222, 6222, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1223, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2223, 32, current_timestamp(), current_timestamp(), 0, 1223, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4223, 2223, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5223, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6223, 32, current_timestamp(), current_timestamp(), 0, 5223, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7223, 6223, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1224, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2224, 32, current_timestamp(), current_timestamp(), 0, 1224, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4224, 2224, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5224, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6224, 32, current_timestamp(), current_timestamp(), 0, 5224, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7224, 6224, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1225, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2225, 32, current_timestamp(), current_timestamp(), 0, 1225, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4225, 2225, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5225, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6225, 32, current_timestamp(), current_timestamp(), 0, 5225, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7225, 6225, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1226, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2226, 32, current_timestamp(), current_timestamp(), 0, 1226, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4226, 2226, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5226, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6226, 32, current_timestamp(), current_timestamp(), 0, 5226, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7226, 6226, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1227, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2227, 32, current_timestamp(), current_timestamp(), 0, 1227, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4227, 2227, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5227, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6227, 32, current_timestamp(), current_timestamp(), 0, 5227, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7227, 6227, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1228, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2228, 32, current_timestamp(), current_timestamp(), 0, 1228, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4228, 2228, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5228, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6228, 32, current_timestamp(), current_timestamp(), 0, 5228, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7228, 6228, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1229, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2229, 32, current_timestamp(), current_timestamp(), 0, 1229, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4229, 2229, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5229, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6229, 32, current_timestamp(), current_timestamp(), 0, 5229, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7229, 6229, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1230, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2230, 32, current_timestamp(), current_timestamp(), 0, 1230, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4230, 2230, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5230, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6230, 32, current_timestamp(), current_timestamp(), 0, 5230, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7230, 6230, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1231, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2231, 32, current_timestamp(), current_timestamp(), 0, 1231, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4231, 2231, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5231, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6231, 32, current_timestamp(), current_timestamp(), 0, 5231, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7231, 6231, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1232, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2232, 32, current_timestamp(), current_timestamp(), 0, 1232, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4232, 2232, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5232, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6232, 32, current_timestamp(), current_timestamp(), 0, 5232, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7232, 6232, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1233, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2233, 32, current_timestamp(), current_timestamp(), 0, 1233, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4233, 2233, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5233, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6233, 32, current_timestamp(), current_timestamp(), 0, 5233, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7233, 6233, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1234, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2234, 32, current_timestamp(), current_timestamp(), 0, 1234, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4234, 2234, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5234, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6234, 32, current_timestamp(), current_timestamp(), 0, 5234, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7234, 6234, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1235, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2235, 32, current_timestamp(), current_timestamp(), 0, 1235, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4235, 2235, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5235, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6235, 32, current_timestamp(), current_timestamp(), 0, 5235, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7235, 6235, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1236, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2236, 32, current_timestamp(), current_timestamp(), 0, 1236, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4236, 2236, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5236, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6236, 32, current_timestamp(), current_timestamp(), 0, 5236, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7236, 6236, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1237, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2237, 32, current_timestamp(), current_timestamp(), 0, 1237, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4237, 2237, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5237, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6237, 32, current_timestamp(), current_timestamp(), 0, 5237, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7237, 6237, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1238, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2238, 32, current_timestamp(), current_timestamp(), 0, 1238, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4238, 2238, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5238, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6238, 32, current_timestamp(), current_timestamp(), 0, 5238, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7238, 6238, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1239, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2239, 32, current_timestamp(), current_timestamp(), 0, 1239, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4239, 2239, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5239, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6239, 32, current_timestamp(), current_timestamp(), 0, 5239, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7239, 6239, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1240, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2240, 32, current_timestamp(), current_timestamp(), 0, 1240, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4240, 2240, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5240, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6240, 32, current_timestamp(), current_timestamp(), 0, 5240, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7240, 6240, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1241, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2241, 32, current_timestamp(), current_timestamp(), 0, 1241, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4241, 2241, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5241, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6241, 32, current_timestamp(), current_timestamp(), 0, 5241, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7241, 6241, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1242, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2242, 32, current_timestamp(), current_timestamp(), 0, 1242, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4242, 2242, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5242, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6242, 32, current_timestamp(), current_timestamp(), 0, 5242, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7242, 6242, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1243, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2243, 32, current_timestamp(), current_timestamp(), 0, 1243, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4243, 2243, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5243, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6243, 32, current_timestamp(), current_timestamp(), 0, 5243, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7243, 6243, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1244, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2244, 32, current_timestamp(), current_timestamp(), 0, 1244, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4244, 2244, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5244, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6244, 32, current_timestamp(), current_timestamp(), 0, 5244, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7244, 6244, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1245, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2245, 32, current_timestamp(), current_timestamp(), 0, 1245, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4245, 2245, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5245, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6245, 32, current_timestamp(), current_timestamp(), 0, 5245, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7245, 6245, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1246, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2246, 32, current_timestamp(), current_timestamp(), 0, 1246, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4246, 2246, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5246, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6246, 32, current_timestamp(), current_timestamp(), 0, 5246, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7246, 6246, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1247, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2247, 32, current_timestamp(), current_timestamp(), 0, 1247, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4247, 2247, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5247, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6247, 32, current_timestamp(), current_timestamp(), 0, 5247, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7247, 6247, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1248, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2248, 32, current_timestamp(), current_timestamp(), 0, 1248, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4248, 2248, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5248, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6248, 32, current_timestamp(), current_timestamp(), 0, 5248, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7248, 6248, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1249, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2249, 32, current_timestamp(), current_timestamp(), 0, 1249, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4249, 2249, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5249, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6249, 32, current_timestamp(), current_timestamp(), 0, 5249, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7249, 6249, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1250, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2250, 32, current_timestamp(), current_timestamp(), 0, 1250, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4250, 2250, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5250, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6250, 32, current_timestamp(), current_timestamp(), 0, 5250, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7250, 6250, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1251, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2251, 32, current_timestamp(), current_timestamp(), 0, 1251, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4251, 2251, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5251, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6251, 32, current_timestamp(), current_timestamp(), 0, 5251, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7251, 6251, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1252, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2252, 32, current_timestamp(), current_timestamp(), 0, 1252, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4252, 2252, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5252, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6252, 32, current_timestamp(), current_timestamp(), 0, 5252, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7252, 6252, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1253, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2253, 32, current_timestamp(), current_timestamp(), 0, 1253, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4253, 2253, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5253, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6253, 32, current_timestamp(), current_timestamp(), 0, 5253, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7253, 6253, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1254, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2254, 32, current_timestamp(), current_timestamp(), 0, 1254, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4254, 2254, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5254, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6254, 32, current_timestamp(), current_timestamp(), 0, 5254, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7254, 6254, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1255, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2255, 32, current_timestamp(), current_timestamp(), 0, 1255, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4255, 2255, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5255, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6255, 32, current_timestamp(), current_timestamp(), 0, 5255, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7255, 6255, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1256, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2256, 32, current_timestamp(), current_timestamp(), 0, 1256, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4256, 2256, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5256, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6256, 32, current_timestamp(), current_timestamp(), 0, 5256, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7256, 6256, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1257, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2257, 32, current_timestamp(), current_timestamp(), 0, 1257, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4257, 2257, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5257, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6257, 32, current_timestamp(), current_timestamp(), 0, 5257, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7257, 6257, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1258, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2258, 32, current_timestamp(), current_timestamp(), 0, 1258, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4258, 2258, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5258, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6258, 32, current_timestamp(), current_timestamp(), 0, 5258, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7258, 6258, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1259, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2259, 32, current_timestamp(), current_timestamp(), 0, 1259, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4259, 2259, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5259, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6259, 32, current_timestamp(), current_timestamp(), 0, 5259, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7259, 6259, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1260, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2260, 32, current_timestamp(), current_timestamp(), 0, 1260, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4260, 2260, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5260, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6260, 32, current_timestamp(), current_timestamp(), 0, 5260, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7260, 6260, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1261, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2261, 32, current_timestamp(), current_timestamp(), 0, 1261, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4261, 2261, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5261, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6261, 32, current_timestamp(), current_timestamp(), 0, 5261, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7261, 6261, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1262, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2262, 32, current_timestamp(), current_timestamp(), 0, 1262, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4262, 2262, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5262, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6262, 32, current_timestamp(), current_timestamp(), 0, 5262, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7262, 6262, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1263, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2263, 32, current_timestamp(), current_timestamp(), 0, 1263, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4263, 2263, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5263, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6263, 32, current_timestamp(), current_timestamp(), 0, 5263, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7263, 6263, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1264, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2264, 32, current_timestamp(), current_timestamp(), 0, 1264, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4264, 2264, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5264, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6264, 32, current_timestamp(), current_timestamp(), 0, 5264, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7264, 6264, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1265, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2265, 32, current_timestamp(), current_timestamp(), 0, 1265, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4265, 2265, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5265, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6265, 32, current_timestamp(), current_timestamp(), 0, 5265, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7265, 6265, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1266, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2266, 32, current_timestamp(), current_timestamp(), 0, 1266, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4266, 2266, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5266, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6266, 32, current_timestamp(), current_timestamp(), 0, 5266, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7266, 6266, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1267, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2267, 32, current_timestamp(), current_timestamp(), 0, 1267, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4267, 2267, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5267, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6267, 32, current_timestamp(), current_timestamp(), 0, 5267, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7267, 6267, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1268, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2268, 32, current_timestamp(), current_timestamp(), 0, 1268, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4268, 2268, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5268, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6268, 32, current_timestamp(), current_timestamp(), 0, 5268, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7268, 6268, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1269, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2269, 32, current_timestamp(), current_timestamp(), 0, 1269, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4269, 2269, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5269, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6269, 32, current_timestamp(), current_timestamp(), 0, 5269, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7269, 6269, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1270, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2270, 32, current_timestamp(), current_timestamp(), 0, 1270, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4270, 2270, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5270, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6270, 32, current_timestamp(), current_timestamp(), 0, 5270, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7270, 6270, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1271, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2271, 32, current_timestamp(), current_timestamp(), 0, 1271, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4271, 2271, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5271, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6271, 32, current_timestamp(), current_timestamp(), 0, 5271, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7271, 6271, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1272, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2272, 32, current_timestamp(), current_timestamp(), 0, 1272, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4272, 2272, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5272, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6272, 32, current_timestamp(), current_timestamp(), 0, 5272, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7272, 6272, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1273, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2273, 32, current_timestamp(), current_timestamp(), 0, 1273, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4273, 2273, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5273, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6273, 32, current_timestamp(), current_timestamp(), 0, 5273, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7273, 6273, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1274, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2274, 32, current_timestamp(), current_timestamp(), 0, 1274, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4274, 2274, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5274, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6274, 32, current_timestamp(), current_timestamp(), 0, 5274, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7274, 6274, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1275, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2275, 32, current_timestamp(), current_timestamp(), 0, 1275, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4275, 2275, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5275, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6275, 32, current_timestamp(), current_timestamp(), 0, 5275, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7275, 6275, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1276, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2276, 32, current_timestamp(), current_timestamp(), 0, 1276, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4276, 2276, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5276, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6276, 32, current_timestamp(), current_timestamp(), 0, 5276, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7276, 6276, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1277, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2277, 32, current_timestamp(), current_timestamp(), 0, 1277, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4277, 2277, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5277, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6277, 32, current_timestamp(), current_timestamp(), 0, 5277, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7277, 6277, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1278, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2278, 32, current_timestamp(), current_timestamp(), 0, 1278, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4278, 2278, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5278, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6278, 32, current_timestamp(), current_timestamp(), 0, 5278, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7278, 6278, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1279, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2279, 32, current_timestamp(), current_timestamp(), 0, 1279, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4279, 2279, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5279, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6279, 32, current_timestamp(), current_timestamp(), 0, 5279, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7279, 6279, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1280, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2280, 32, current_timestamp(), current_timestamp(), 0, 1280, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4280, 2280, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5280, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6280, 32, current_timestamp(), current_timestamp(), 0, 5280, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7280, 6280, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1281, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2281, 32, current_timestamp(), current_timestamp(), 0, 1281, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4281, 2281, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5281, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6281, 32, current_timestamp(), current_timestamp(), 0, 5281, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7281, 6281, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1282, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2282, 32, current_timestamp(), current_timestamp(), 0, 1282, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4282, 2282, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5282, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6282, 32, current_timestamp(), current_timestamp(), 0, 5282, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7282, 6282, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1283, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2283, 32, current_timestamp(), current_timestamp(), 0, 1283, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4283, 2283, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5283, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6283, 32, current_timestamp(), current_timestamp(), 0, 5283, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7283, 6283, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1284, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2284, 32, current_timestamp(), current_timestamp(), 0, 1284, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4284, 2284, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5284, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6284, 32, current_timestamp(), current_timestamp(), 0, 5284, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7284, 6284, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1285, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2285, 32, current_timestamp(), current_timestamp(), 0, 1285, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4285, 2285, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5285, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6285, 32, current_timestamp(), current_timestamp(), 0, 5285, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7285, 6285, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1286, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2286, 32, current_timestamp(), current_timestamp(), 0, 1286, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4286, 2286, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5286, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6286, 32, current_timestamp(), current_timestamp(), 0, 5286, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7286, 6286, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1287, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2287, 32, current_timestamp(), current_timestamp(), 0, 1287, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4287, 2287, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5287, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6287, 32, current_timestamp(), current_timestamp(), 0, 5287, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7287, 6287, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1288, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2288, 32, current_timestamp(), current_timestamp(), 0, 1288, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4288, 2288, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5288, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6288, 32, current_timestamp(), current_timestamp(), 0, 5288, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7288, 6288, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1289, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2289, 32, current_timestamp(), current_timestamp(), 0, 1289, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4289, 2289, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5289, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6289, 32, current_timestamp(), current_timestamp(), 0, 5289, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7289, 6289, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1290, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2290, 32, current_timestamp(), current_timestamp(), 0, 1290, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4290, 2290, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5290, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6290, 32, current_timestamp(), current_timestamp(), 0, 5290, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7290, 6290, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1291, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2291, 32, current_timestamp(), current_timestamp(), 0, 1291, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4291, 2291, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5291, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6291, 32, current_timestamp(), current_timestamp(), 0, 5291, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7291, 6291, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1292, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2292, 32, current_timestamp(), current_timestamp(), 0, 1292, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4292, 2292, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5292, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6292, 32, current_timestamp(), current_timestamp(), 0, 5292, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7292, 6292, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1293, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2293, 32, current_timestamp(), current_timestamp(), 0, 1293, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4293, 2293, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5293, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6293, 32, current_timestamp(), current_timestamp(), 0, 5293, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7293, 6293, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1294, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2294, 32, current_timestamp(), current_timestamp(), 0, 1294, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4294, 2294, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5294, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6294, 32, current_timestamp(), current_timestamp(), 0, 5294, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7294, 6294, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1295, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2295, 32, current_timestamp(), current_timestamp(), 0, 1295, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4295, 2295, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5295, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6295, 32, current_timestamp(), current_timestamp(), 0, 5295, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7295, 6295, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1296, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2296, 32, current_timestamp(), current_timestamp(), 0, 1296, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4296, 2296, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5296, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6296, 32, current_timestamp(), current_timestamp(), 0, 5296, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7296, 6296, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1297, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2297, 32, current_timestamp(), current_timestamp(), 0, 1297, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4297, 2297, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5297, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6297, 32, current_timestamp(), current_timestamp(), 0, 5297, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7297, 6297, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1298, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2298, 32, current_timestamp(), current_timestamp(), 0, 1298, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4298, 2298, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5298, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6298, 32, current_timestamp(), current_timestamp(), 0, 5298, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7298, 6298, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	 
# insert a completed listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 1299, 1.00, 1, 'Completed Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'COMPLETED' ); 
INSERT INTO message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
VALUES ( 'Cancelled listing', 2299, 32, current_timestamp(), current_timestamp(), 0, 1299, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );

# insert a response to the completed listing
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a completed item', 4299, 2299, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );



# insert a cancelled listing and message
INSERT INTO listing ( id, amount, quantity, title, type, version, seller_id, created, updated, status)  
	VALUES ( 5299, 1.00, 1, 'Cancelled Listing', 'DIGITAL', 0, 32, current_timestamp(), current_timestamp(), 'CANCELLED' );
INSERT message ( text, twitterId, user_id, created, updated, version, listing_id, status, providerId, providerUserId ) 
	VALUES ( 'Completed listing', 6299, 32, current_timestamp(), current_timestamp(), 0, 5299, 'COMPLETED', 'twitter', 'twitter/regtestnancy' );


# insert a response to the cancelled item
insert into message ( text, twitterId, inResponseToTwitterId, user_id, created, updated, version, status, providerId, providerUserId ) 
	VALUES ( '@regtestnancy buy a cancelled item', 7299, 6299, 31, current_timestamp(), current_timestamp(), 0, 'PROCESSING', 'twitter', 'regtestjoe' );	
