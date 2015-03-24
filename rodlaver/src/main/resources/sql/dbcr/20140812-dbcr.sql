/* Make it possible to override the tweet rate limits on a client-by-client basis */
CREATE TABLE `seller_tweet_rate_limits` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `per_ten_seconds` int(11) NOT NULL,
  `per_five_minutes` int(11) NOT NULL,
  `per_hour` int(11) NOT NULL,
  `per_twenty_four_hours` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_client_tweet_rate_limits_user_id` (`user_id`),
  CONSTRAINT `FK_client_tweet_rate_limits_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE `sent_outbound_communications`;

CREATE TABLE `SentOutboundCommunications` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `provider_id` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `sent_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,  
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `sent_outbound_communications_user_id_fk` (`user_id`),
  CONSTRAINT `sent_outbound_communications_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


GRANT SELECT ON hootit.seller_tweet_rate_limits TO 'batch'@'%';

/* Create a view to simplify batch queries that want to select buying messages and join to a single row in the preapproval table, if one exists */
CREATE VIEW hootit.CurrentPreapproval AS SELECT id, correlationId, preapprovalKey, user_id, version, status, start, max(end), created, updated FROM preapproval WHERE status='ACTIVE' GROUP BY user_id;
GRANT SELECT ON hootit.CurrentPreapproval TO 'batch'@'%';

/* Create a view to simplify deciding whether a given listing has any associated digital content */
CREATE VIEW hootit.ListingHasDigitalContent AS SELECT l.id listing_id, (cf.id IS NOT NULL) has_digital_content FROM listing l LEFT OUTER JOIN content_file cf ON l.id = cf.listing_id GROUP BY l.id;
GRANT SELECT ON hootit.ListingHasDigitalContent TO `'%';

/* Simplify querying for currently-effective payments and their current status */
CREATE VIEW hootit.CurrentPaymentStatus AS SELECT p.id payment_id, p.payer_id, p.payee_id, p.message_id, p.listing_id, p.currencyCode, p.amount, p.rodLaverAmount, p.sellerAmount, p.payKey, p.correlationId, p.shippingAddress_id, p.has_been_shipped, eps.id eps_id, eps.status, eps.start start, eps.end eps_end 
FROM payment p 
  INNER JOIN effective_payment_status eps ON p.id = eps.payment_id 
WHERE NOW() BETWEEN eps.start AND eps.end;

GRANT SELECT ON hootit.CurrentPaymentStatus TO 'batch'@'%';

/* Add a user-level switch to control whether sellers are paid with chained payments */
alter table user add column `do_use_chained_pmts` bit(1) DEFAULT b'1';

alter table listing add column `sellerRevenueRatio` decimal(6,5) DEFAULT NULL;

/* Create a view that joins reply messages to their associated listing via the listing message */
CREATE OR REPLACE VIEW `hootit`.`ListingReplies` AS SELECT l.id listing_id, l.locale listing_locale, l.seller_id seller_id, lm.providerId provider_id, lm.providerUserId seller_provider_user_id, l.status listing_status, l.type listing_type, l.goodsType goods_type, l.subType listing_sub_type, reply.id reply_id, reply.providerUserId reply_provider_user_id, reply.twitterId reply_content_id, reply.user_id reply_user_id, respondant.has_accepted_current_buyer_tos IS TRUE has_reply_user_accepted_tos, respondant.is_user_blocked IS TRUE is_reply_user_blocked, reply.containsKeyword reply_contains_keyword, reply.status reply_status, reply.batch_processor reply_batch_processor  
FROM listing l 
INNER JOIN message lm ON l.id = lm.listing_id 
INNER JOIN message reply ON ( reply.providerId = lm.providerId AND reply.inResponseToTwitterId = lm.twitterId)
LEFT OUTER JOIN user respondant ON ( reply.user_id = respondant.id );

GRANT SELECT ON hootit.ListingReplies TO 'batch'@'%';

ALTER TABLE listing modify column type varchar(255) NOT NULL;
ALTER TABLE listing add column goodsType varchar(255) not null after `type`;

/* Update the existing rows to set the goods type */ 
UPDATE listing 
SET goodsType='DIGITAL' 
WHERE ID IN (
SELECT id FROM ( 
  SELECT l.id 
  FROM listing l 
    INNER JOIN ListingHasDigitalContent dc ON l.id = dc.listing_id 
  WHERE l.goodsType='' AND dc.has_digital_content IS TRUE
) As Sub );

UPDATE listing 
SET goodsType='PHYSICAL' 
WHERE ID IN (
SELECT id FROM ( 
  SELECT l.id 
  FROM listing l 
    INNER JOIN ListingHasDigitalContent dc ON l.id = dc.listing_id 
  WHERE l.goodsType='' AND dc.has_digital_content IS FALSE
) As Sub );

update listing set type='SELLING' where type='PHYSICAL' or type='DIGITAL';

/* Create a table to hold the remption status of vouchers */ 
CREATE TABLE `EffectiveVoucherStatuses` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `start` datetime NOT NULL,
  `end` datetime NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `payment_id` bigint(20) DEFAULT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY (`payment_id`),
  CONSTRAINT FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/* Vouchers will have a serial number */
/*ALTER TABLE payment ADD COLUMN `voucherSerialNumber` varchar(255) DEFAULT NULL after `id`; */
/* ALTER TABLE payment ADD COLUMN `voucherSalt` varchar(255) DEFAULT NULL after `voucherSerialNumber`; */
/* ALTER TABLE payment ADD COLUMN `voucherFilename` varchar(255) DEFAULT NULL after `voucherSalt`; */

/* Vouchers' serial numbers are derived from a counter */
CREATE TABLE VOUCHER_SEQ (
	ID BIGINT NOT NULL,
	UNIQUE_KEY CHAR(1) NOT NULL,
	constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
) ENGINE=MYISAM;
INSERT INTO VOUCHER_SEQ values(100000000000000000, '0');

GRANT SELECT ON hootit.VOUCHER_SEQ TO 'batch'@'%';
GRANT UPDATE ON hootit.VOUCHER_SEQ TO 'batch'@'%';

GRANT SELECT ON hootit.EffectiveVoucherStatuses TO 'batch'@'%';
GRANT INSERT ON hootit.EffectiveVoucherStatuses TO 'batch'@'%';
GRANT UPDATE ON hootit.EffectiveVoucherStatuses TO 'batch'@'%';


/* Add a few cascade delete constraints to make testing easier. 
 * Note that the FK name will differ per schema.
 */
ALTER TABLE effective_payment_status DROP FOREIGN KEY `FK_2ku6t5q0t9vvm4v9uhag2yqpk`;
ALTER TABLE effective_payment_status ADD CONSTRAINT `FK_2ku6t5q0t9vvm4v9uhag2yqpk` FOREIGN KEY(`payment_id`) REFERENCES `payment` (`id`) ON DELETE CASCADE;

ALTER TABLE message_state_change DROP FOREIGN KEY `FK_w2wuby6onu0hetgq1fiq35a2`;
ALTER TABLE message_state_change ADD CONSTRAINT `FK_w2wuby6onu0hetgq1fiq35a2` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`) ON DELETE CASCADE;

/* Add a constraint from the UserConnection to the user table */
ALTER TABLE `UserConnection` ADD CONSTRAINT `fk_user_connection_user_userid` FOREIGN KEY ( `userId` ) REFERENCES `user` ( `username` ) ON DELETE CASCADE;

ALTER TABLE `user_role` DROP FOREIGN KEY `FK_4edf6ibqo9873ixvuyri8xua2`;
ALTER TABLE `user_role` ADD CONSTRAINT `fk_user_role_user_id` FOREIGN KEY(`users_id`) REFERENCES `user` (`id`) ON DELETE CASCADE;

ALTER TABLE `user_terms_of_service` drop foreign key `FK_rkwkfgjfex9629r5nqopowi0c`;
ALTER TABLE `user_terms_of_service` add constraint `fk_user_terms_of_service_user_id` FOREIGN KEY (`users_id` ) REFERENCES `user` (`id`) ON DELETE CASCADE;

/* Modify the current_twitter_stream_sellers table to hold the sellers' display names */
ALTER TABLE current_twitter_stream_sellers ADD COLUMN displayName varchar(255) NOT NULL;

/* Add some type lists and foreign key constraints */
CREATE TABLE `MessageStatus` (
  `status` varchar(30) NOT NULL,
  PRIMARY KEY (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO MessageStatus (status) VALUES ('PROCESSING'), ('IRRELEVANT'), ('PENDING_USER_REGISTRATION'), ('PENDING_MEANS_OF_PAYMENT'), ('PENDING_LISTING_EXPIRY'), ('FAILED_SOLD_OUT'), ('FAILED_INACTIVE_SALE'), ('FAILED_DUPLICATE_PURCHASE'), ('FAILED_DUPLICATE_OPT_IN'), ('FAILED_LOST_GIVEAWAY'), ('PAYMENT_PROCESSING'), ('PAYMENT_PENDING'), ('PAYMENT_FAILED'), ('PENDING_SHIPPING_ADDRESS'), ('PENDING_SHIPMENT'), ('ACTIVE'), ('CANCELED'), ('COMPLETED'), ('PROCESSING_ERROR');
ALTER TABLE `message` ADD CONSTRAINT `fk_message_status_MessageStatus_status` FOREIGN KEY (`status` ) REFERENCES `MessageStatus` (`status`);
ALTER TABLE `message_state_change` ADD CONSTRAINT `fk_message_state_change_previousState_MessageStatus_status` FOREIGN KEY (`previousState`) REFERENCES `MessageStatus` (`status`);
ALTER TABLE `message_state_change` ADD CONSTRAINT `fk_message_state_change_newState_MessageStatus_status` FOREIGN KEY (`newState`) REFERENCES `MessageStatus` (`status`);

CREATE TABLE PaymentStatus (
	status VARCHAR(20) PRIMARY KEY
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO PaymentStatus (status) VALUES ('CREATED'), ('PAYMENT_PROCESSING'), ('COMPLETED'), ('PENDING'), ('PARTIALLY_REFUNDED'), ('DENIED'), ('PROCESSING'), ('REVERSED'), ('REFUNDED'), ('FAILED'), ('UNKNOWN');
ALTER TABLE `effective_payment_status` ADD CONSTRAINT `fk_effective_payment_status_PaymentStatus_status` FOREIGN KEY (`status` ) REFERENCES `PaymentStatus` (`status`);

CREATE TABLE ListingStatus( 
	status VARCHAR(20) PRIMARY KEY
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO ListingStatus (status) VALUES ('PENDING'), ('ACTIVE'), ('COMPLETED'), ('CANCELED');
ALTER TABLE `listing` ADD CONSTRAINT `fk_listing_status_ListingStatus_status` FOREIGN KEY (`status`) REFERENCES `ListingStatus` (`status`);

CREATE TABLE PreapprovalStatus( 
	status VARCHAR(20) PRIMARY KEY
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO PreapprovalStatus (status) VALUES ('ACTIVE'), ('CANCELED');
ALTER TABLE `preapproval` ADD CONSTRAINT `fk_preapproval_PreapprovalStatus_status` FOREIGN KEY (`status`) REFERENCES `PreapprovalStatus` (`status`);

/* Standardize the table names */
RENAME TABLE `address` TO `Addresses`;
RENAME TABLE `content_file` TO `ContentFiles`;
RENAME TABLE `business_metrics` TO `BusinessMetrics`;
RENAME TABLE `current_twitter_stream_sellers` TO `CurrentTwitterStreamSellers`;
RENAME TABLE `download_instance` TO `DownloadInstances`;
RENAME TABLE `effective_payment_status` TO `EffectivePaymentStatuses`;
RENAME TABLE `facebook_update_notification` TO `FacebookUpdateNotifications`;
RENAME TABLE `listing` TO `Listings`;
RENAME TABLE `listing_state_change` TO `ListingStateChanges`;
RENAME TABLE `message` TO `Messages`;
RENAME TABLE `message_state_change` TO `MessageStateChanges`;
RENAME TABLE `metrics_job_execution` TO `MetricsJobExecutions`;
RENAME TABLE `payment` TO `Payments`;
RENAME TABLE `paypal_status_update` TO `PaypalStatusUpdates`;
RENAME TABLE `preapproval` TO `Preapprovals`;
RENAME TABLE `reply_paging_status` TO `ReplyPagingStatuses`;
RENAME TABLE `role` TO `Roles`;
RENAME TABLE `seller_tweet_rate_limits` TO `SellerTweetRateLimits`;
RENAME TABLE `sent_outbound_communications` TO `SentOutboundCommunications`;
RENAME TABLE `terms_of_service` TO `TermsOfServices`;
RENAME TABLE `user` TO `Users`;
RENAME TABLE `user_role` TO `Users_Roles`;
RENAME TABLE `users_termsofservices` TO `Users_TermsOfServices`;

/* Update the views */
CREATE OR REPLACE VIEW `hootit`.`CurrentPreapprovals` AS SELECT id, correlationId, preapprovalKey, user_id, version, status, start, max(end), created, updated FROM Preapprovals WHERE status='ACTIVE' GROUP BY user_id;
CREATE OR REPLACE VIEW `hootit_unit_test`.`CurrentPreapprovals` AS SELECT id, correlationId, preapprovalKey, user_id, version, status, start, max(end), created, updated FROM Preapprovals WHERE status='ACTIVE' GROUP BY user_id;

CREATE OR REPLACE VIEW `hootit`.`ListingHasDigitalContent` AS SELECT l.id listing_id, (cf.id IS NOT NULL) has_digital_content FROM Listings l LEFT OUTER JOIN ContentFiles cf ON l.id = cf.listing_id GROUP BY l.id;
CREATE OR REPLACE VIEW `hootit_unit_test`.`ListingHasDigitalContent` AS SELECT l.id listing_id, (cf.id IS NOT NULL) has_digital_content FROM Listings l LEFT OUTER JOIN ContentFiles cf ON l.id = cf.listing_id GROUP BY l.id;

/* Simplify querying for currently-effective payments and their current status */
CREATE OR REPLACE VIEW `hootit`.`CurrentPaymentStatus` AS SELECT p.id payment_id, p.payer_id, p.payee_id, p.message_id, p.listing_id, p.currencyCode, p.amount, p.rodLaverAmount, p.sellerAmount, p.payKey, p.correlationId, p.shippingAddress_id, p.has_been_shipped, eps.id eps_id, eps.status, eps.start start, eps.end eps_end 
FROM Payments p 
  INNER JOIN EffectivePaymentStatuses eps ON p.id = eps.payment_id 
WHERE NOW() BETWEEN eps.start AND eps.end;

CREATE OR REPLACE VIEW `hootit_unit_test`.`CurrentPaymentStatus` AS SELECT p.id payment_id, p.payer_id, p.payee_id, p.message_id, p.listing_id, p.currencyCode, p.amount, p.rodLaverAmount, p.sellerAmount, p.payKey, p.correlationId, p.shippingAddress_id, p.has_been_shipped, eps.id eps_id, eps.status, eps.start start, eps.end eps_end 
FROM Payments p 
  INNER JOIN EffectivePaymentStatuses eps ON p.id = eps.payment_id 
WHERE NOW() BETWEEN eps.start AND eps.end;

/* Create a view that joins reply messages to their associated listing via the listing message */
CREATE OR REPLACE VIEW `hootit`.`ListingReplies` AS SELECT l.id listing_id, l.locale listing_locale, l.seller_id seller_id, lm.providerId provider_id, lm.providerUserId seller_provider_user_id, l.status listing_status, l.type listing_type, l.goodsType goods_type, l.subType listing_sub_type, l.has_single_inventory listing_has_single_inventory, reply.id reply_id, reply.providerUserId reply_provider_user_id, reply.twitterId reply_content_id, reply.user_id reply_user_id, respondant.has_accepted_current_buyer_tos IS TRUE has_reply_user_accepted_tos, respondant.is_user_blocked IS TRUE is_reply_user_blocked, reply.containsKeyword reply_contains_keyword, reply.status reply_status, reply.batch_processor reply_batch_processor  
FROM Listings l 
INNER JOIN Messages lm ON l.id = lm.listing_id 
INNER JOIN Messages reply ON ( reply.providerId = lm.providerId AND reply.inResponseToTwitterId = lm.twitterId)
LEFT OUTER JOIN Users respondant ON ( reply.user_id = respondant.id );

CREATE OR REPLACE VIEW `hootit_unit_test`.`ListingReplies` AS SELECT l.id listing_id, l.locale listing_locale, l.seller_id seller_id, lm.providerId provider_id, lm.providerUserId seller_provider_user_id, l.status listing_status, l.type listing_type, l.goodsType goods_type, l.subType listing_sub_type, reply.id reply_id, reply.providerUserId reply_provider_user_id, reply.twitterId reply_content_id, reply.user_id reply_user_id, respondant.has_accepted_current_buyer_tos IS TRUE has_reply_user_accepted_tos, respondant.is_user_blocked IS TRUE is_reply_user_blocked, reply.containsKeyword reply_contains_keyword, reply.status reply_status, reply.batch_processor reply_batch_processor  
FROM Listings l 
INNER JOIN Messages lm ON l.id = lm.listing_id 
INNER JOIN Messages reply ON ( reply.providerId = lm.providerId AND reply.inResponseToTwitterId = lm.twitterId)
LEFT OUTER JOIN Users respondant ON ( reply.user_id = respondant.id );

/* Updated batch privileges */
GRANT SELECT ON hootit.Addresses TO 'batch'@'%';
GRANT INSERT ON hootit.Addresses TO 'batch'@'%';
GRANT UPDATE ON hootit.Addresses TO 'batch'@'%';

GRANT SELECT ON hootit.BusinessMetrics TO 'batch'@'%';
GRANT INSERT ON hootit.BusinessMetrics TO 'batch'@'%';
GRANT UPDATE ON hootit.BusinessMetrics TO 'batch'@'%';

GRANT SELECT ON hootit.ContentFiles TO 'batch'@'%';

GRANT SELECT ON hootit.CurrentTwitterStreamSellers TO 'batch'@'%';
GRANT SELECT ON hootit.CurrentPaymentStatus TO 'batch'@'%';


GRANT SELECT ON hootit.DownloadInstances TO 'batch'@'%';

GRANT SELECT ON hootit.EffectivePaymentStatuses TO 'batch'@'%';
GRANT INSERT ON hootit.EffectivePaymentStatuses TO 'batch'@'%';
GRANT UPDATE ON hootit.EffectivePaymentStatuses TO 'batch'@'%';

GRANT SELECT ON hootit.FacebookUpdateNotifications TO 'batch'@'%';

GRANT SELECT ON hootit.Inventories TO 'batch'@'%';
GRANT INSERT ON hootit.Inventories TO 'batch'@'%';
GRANT UPDATE ON hootit.Inventories TO 'batch'@'%';

GRANT SELECT ON hootit.Listings TO 'batch'@'%';
GRANT INSERT ON hootit.Listings TO 'batch'@'%';
GRANT UPDATE ON hootit.Listings TO 'batch'@'%';

GRANT SELECT ON hootit.ListingStateChanges TO 'batch'@'%';
GRANT INSERT ON hootit.ListingStateChanges TO 'batch'@'%';
GRANT UPDATE ON hootit.ListingStateChanges TO 'batch'@'%';

GRANT SELECT ON hootit.ListingHasDigitalContent TO 'batch'@'%';

GRANT SELECT ON hootit.Messages TO 'batch'@'%';
GRANT INSERT ON hootit.Messages TO 'batch'@'%';
GRANT UPDATE ON hootit.Messages TO 'batch'@'%';

GRANT SELECT ON hootit.MessageStateChanges TO 'batch'@'%';
GRANT INSERT ON hootit.MessageStateChanges TO 'batch'@'%';
GRANT UPDATE ON hootit.MessageStateChanges TO 'batch'@'%';

GRANT SELECT ON hootit.MetricsJobExecutions TO 'batch'@'%';
GRANT INSERT ON hootit.MetricsJobExecutions TO 'batch'@'%';
GRANT UPDATE ON hootit.MetricsJobExecutions TO 'batch'@'%';

GRANT SELECT ON hootit.Payments TO 'batch'@'%';
GRANT INSERT ON hootit.Payments TO 'batch'@'%';
GRANT UPDATE ON hootit.Payments TO 'batch'@'%';

GRANT SELECT ON hootit.PaypalStatusUpdates TO 'batch'@'%';
GRANT INSERT ON hootit.PaypalStatusUpdates TO 'batch'@'%';
GRANT UPDATE ON hootit.PaypalStatusUpdates TO 'batch'@'%';

GRANT SELECT ON hootit.Preapprovals TO 'batch'@'%';
GRANT INSERT ON hootit.Preapprovals TO 'batch'@'%';
GRANT UPDATE ON hootit.Preapprovals TO 'batch'@'%';

GRANT SELECT ON hootit.ReplyPagingStatuses TO 'batch'@'%';
GRANT INSERT ON hootit.ReplyPagingStatuses TO 'batch'@'%';
GRANT UPDATE ON hootit.ReplyPagingStatuses TO 'batch'@'%';

GRANT SELECT ON hootit.Roles TO 'batch'@'%';

GRANT SELECT ON hootit.SentOutboundCommunications TO 'batch'@'%';
GRANT INSERT ON hootit.SentOutboundCommunications TO 'batch'@'%';
GRANT UPDATE ON hootit.SentOutboundCommunications TO 'batch'@'%';

GRANT SELECT ON hootit.TermsOfServices TO 'batch'@'%';

GRANT SELECT ON hootit.Users TO 'batch'@'%';
GRANT INSERT ON hootit.Users TO 'batch'@'%';

GRANT SELECT ON hootit.users_roles TO 'batch'@'%';

GRANT SELECT ON hootit.users_termsofservices TO 'batch'@'%';

GRANT SELECT ON `hootit`.`CurrentPreapprovals` TO 'batch'@'%';
GRANT SELECT ON `hootit`.`SellerTweetRateLimits` TO 'batch'@'%';

/* Stream user grants */
GRANT DELETE ON hootit.CurrentTwitterStreamSellers TO 'stream'@'%';
GRANT INSERT ON hootit.CurrentTwitterStreamSellers TO 'stream'@'%';
GRANT SELECT ON hootit.CurrentTwitterStreamSellers TO 'stream'@'%';
GRANT SELECT ON hootit.UserConnection TO 'stream'@'%';
GRANT SELECT ON hootit.Users TO 'stream'@'%';
GRANT SELECT ON hootit.Users_Roles  TO 'stream'@'%';
GRANT SELECT ON hootit.Listings  TO 'stream'@'%';
GRANT SELECT ON hootit.Messages  TO 'stream'@'%';
GRANT INSERT ON hootit.Messages  TO 'stream'@'%';


/* Inventories table */
CREATE TABLE `Inventories` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_code` varchar(255) DEFAULT NULL,
  `product_description` varchar(255) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `remainingQuantity` int(11) DEFAULT NULL,
  `listing_id` bigint(20) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_inventories_listing_id` (`listing_id`),
  CONSTRAINT `fk_inventories_listing_id` FOREIGN KEY (`listing_id`) REFERENCES `Listings` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* Bring the data over from the Listings table and drop the columns from Listings */
INSERT INTO Inventories( quantity, remainingQuantity, listing_id, version)
SELECT quantity, remainingQuantity, id, 0 
FROM Listings;

ALTER TABLE `Payments` ADD COLUMN `inventory_id` bigint(20) DEFAULT NULL AFTER `listing_id`;
ALTER TABLE `Payments` ADD COLUMN `quantity` int(11) NOT NULL AFTER `inventory_id`;
ALTER TABLE `Payments` ADD FOREIGN KEY `fk_payments_inventories_id` (`inventory_id`) REFERENCES `Inventories` (`id`);

ALTER TABLE `Listings` ADD COLUMN `has_single_inventory` bit(1) DEFAULT NULL;
ALTER TABLE `Listings` ADD COLUMN `max_qty_per_purchase` int(11) DEFAULT NULL;
ALTER TABLE `Listings` DROP COLUMN `quantity`;
ALTER TABLE `Listings` DROP COLUMN `remainingQuantity`;

/* Vouchers table */
DROP TABLE IF EXISTS `EffectiveVoucherStatuses`;
DROP TABLE IF EXISTS `Vouchers`;

CREATE TABLE `Vouchers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `serialNumber` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `filename` varchar(255) NOT NULL,
  `payment_id` bigint(20) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_vouchers_payment_id` (`payment_id`),
  CONSTRAINT `fk_vouchers_payment_id` FOREIGN KEY (`payment_id`) REFERENCES `Payments` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* Create a table to hold the remption status of vouchers */ 
CREATE TABLE `EffectiveVoucherStatuses` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `start` datetime NOT NULL,
  `end` datetime NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `voucher_id` bigint(20) NOT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY (`voucher_id`),
  CONSTRAINT FOREIGN KEY (`voucher_id`) REFERENCES `Vouchers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

GRANT SELECT ON `hootit`.`Vouchers` TO 'batch'@'%';
GRANT UPDATE ON `hootit`.`Vouchers` TO 'batch'@'%';
GRANT INSERT ON `hootit`.`Vouchers` TO 'batch'@'%';

GRANT SELECT ON `hootit`.`EffectiveVoucherStatuses` TO 'batch'@'%';
GRANT UPDATE ON `hootit`.`EffectiveVoucherStatuses` TO 'batch'@'%';
GRANT INSERT ON `hootit`.`EffectiveVoucherStatuses` TO 'batch'@'%';

GRANT SELECT ON `hootit`.`VOUCHER_SEQ` TO 'batch'@'%';
GRANT UPDATE ON `hootit`.`VOUCHER_SEQ` TO 'batch'@'%';


ALTER TABLE `Payments` DROP COLUMN `voucherSerialNumber`;
ALTER TABLE `Payments` DROP COLUMN `voucherSalt`;
ALTER TABLE `Payments` DROP COLUMN `voucherFilename`;

ALTER TABLE `Users` ADD COLUMN `merchant_name` varchar(255) DEFAULT NULL AFTER `username`;
ALTER TABLE `Users` ADD COLUMN `voucher_logo_image_url` varchar(255) DEFAULT NULL;
ALTER TABLE `Users` ADD COLUMN `voucher_campaign_image_url` varchar(255) DEFAULT NULL;


/* Grant cleanup */
REVOKE SELECT, INSERT, UPDATE ON `hootit`.`paypal_status_update` FROM 'batch'@'%';
REVOKE SELECT, INSERT, UPDATE ON `hootit`.`listing` FROM 'batch'@'%';             
REVOKE SELECT ON `hootit`.`content_file` FROM 'batch'@'%';                        
REVOKE SELECT ON `hootit`.`download_instance` FROM 'batch'@'%';                   
REVOKE SELECT ON `hootit`.`user_terms_of_service` FROM 'batch'@'%';               
REVOKE SELECT ON `hootit`.`UserConnection` FROM 'batch'@'%';                      
REVOKE SELECT, INSERT, UPDATE ON `hootit`.`business_metrics` FROM 'batch'@'%';    
REVOKE SELECT ON `hootit`.`facebook_update_notification` FROM 'batch'@'%';        
REVOKE SELECT, INSERT, UPDATE ON `hootit`.`metrics_job_execution` FROM 'batch'@'%';
REVOKE SELECT, INSERT, UPDATE ON `hootit`.`payment` FROM 'batch'@'%';             
REVOKE SELECT, INSERT, UPDATE ON `hootit`.`preapproval` FROM 'batch'@'%';         
REVOKE SELECT, INSERT, UPDATE ON `hootit`.`sent_outbound_communications` FROM 'batch'@'%';
REVOKE SELECT, INSERT, UPDATE ON `hootit`.`message` FROM 'batch'@'%';             
REVOKE SELECT, INSERT, UPDATE ON `hootit`.`reply_paging_status` FROM 'batch'@'%'; 
REVOKE SELECT, INSERT, UPDATE ON `hootit`.`address` FROM 'batch'@'%';             
REVOKE SELECT ON `hootit`.`user_role` FROM 'batch'@'%';                           
REVOKE SELECT, INSERT ON `hootit`.`user` FROM 'batch'@'%';                        
REVOKE SELECT, INSERT, UPDATE ON `hootit`.`listing_state_change` FROM 'batch'@'%';
REVOKE SELECT ON `hootit`.`terms_of_service` FROM 'batch'@'%';                    
REVOKE SELECT ON `hootit`.`current_twitter_stream_sellers` FROM 'batch'@'%';      
REVOKE SELECT, INSERT, UPDATE ON `hootit`.`message_state_change` FROM 'batch'@'%';
REVOKE SELECT, INSERT, UPDATE ON `hootit`.`effective_payment_status` FROM 'batch'@'%';
REVOKE SELECT ON `hootit`.`role` FROM 'batch'@'%';

REVOKE SELECT ON `hootit`.`UserConnection` FROM 'stream'@'%';
REVOKE SELECT ON `hootit`.`listing` FROM 'stream'@'%';
REVOKE SELECT ON `hootit`.`user_role` FROM 'stream'@'%';
REVOKE SELECT, INSERT, DELETE ON `hootit`.`current_twitter_stream_sellers` FROM 'stream'@'%';
REVOKE SELECT, INSERT ON `hootit`.`message` FROM 'stream'@'%';
REVOKE SELECT ON `hootit`.`user` FROM 'stream'@'%';