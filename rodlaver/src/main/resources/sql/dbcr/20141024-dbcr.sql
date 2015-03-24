ALTER TABLE `EffectivePaymentStatuses` ADD INDEX ( `created`, `status` );

ALTER TABLE `MetricsJobExecutions` ADD INDEX ( `start_time`, `end_time`, `status` );

ALTER TABLE `PaypalStatusUpdates` MODIFY COLUMN `correlationId` varchar(20) DEFAULT NULL;
ALTER TABLE `PaypalStatusUpdates` MODIFY COLUMN `status` varchar(20) DEFAULT NULL;
ALTER TABLE `PaypalStatusUpdates` ADD INDEX ( `correlationId`, `status`, `transactionType`, `updatedPaymentStatus`, `batch_processor` );
ALTER TABLE `Payments` ADD INDEX ( `correlationId` );

ALTER TABLE `EffectivePaymentStatuses` ADD INDEX ( `start`, `end`, `status` );

CREATE VIEW `hootit_unit_test`.`CurrentPreapprovals` AS 
SELECT id, correlationId, preapprovalKey, user_id, version, status, start, max(end), created, updated 
FROM `hootit_unit_test`.`Preapprovals` 
WHERE status='ACTIVE' 
GROUP BY user_id;

CREATE VIEW `hootit_unit_test`.`CurrentPaymentStatus` AS 
SELECT p.id payment_id, p.payer_id, p.payee_id, p.message_id, p.listing_id, p.currencyCode, p.amount, p.rodLaverAmount, p.sellerAmount, p.payKey, p.correlationId, p.shippingAddress_id, p.has_been_shipped, eps.id eps_id, eps.status, eps.start start, eps.end eps_end 
FROM `hootit_unit_test`.`Payments` p 
  INNER JOIN `hootit_unit_test`.`EffectivePaymentStatuses` eps ON p.id = eps.payment_id 
WHERE NOW() BETWEEN eps.start AND eps.end;

CREATE VIEW `hootit_unit_test`.`ListingHasDigitalContent`  
AS select `l`.`id` AS `listing_id`,(`cf`.`id` is not null) AS `has_digital_content`  
from (`hootit_unit_test`.`Listings` `l`  left join `hootit_unit_test`.`ContentFiles` `cf` on((`l`.`id` = `cf`.`listing_id`)))  
group by `l`.`id`;

/* This index reduces a lot of work on the step where we look for things that need to have metrics calculated */
ALTER TABLE MetricsJobExecutions ADD INDEX ( status, start_time, end_time );

/* Allow the long description to be longer */
ALTER TABLE Listings MODIFY COLUMN `longDescription` varchar(512) DEFAULT NULL;

/* Adding remember me back to the web app */
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* Need to set a default password on each user so that Spring Security doesn't choke */
UPDATE Users SET password='hootit';

ALTER TABLE `Users` ADD COLUMN `profile_image_url` varchar(512) DEFAULT NULL AFTER `is_mobile_verified`;

/* Convert data from UserConnection */
update Users u INNER JOIN ( select userId, imageUrl from UserConnection GROUP BY userId ) uc ON u.username = uc.userId SET u.profile_image_url = uc.imageUrl;

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
INSERT INTO ListingStatus (status) VALUES ('UNREVIEWED'), ('PENDING'), ('ACTIVE'), ('COMPLETED'), ('CANCELED');
ALTER TABLE `listing` ADD CONSTRAINT `fk_listing_status_ListingStatus_status` FOREIGN KEY (`status`) REFERENCES `ListingStatus` (`status`);

CREATE TABLE PreapprovalStatus( 
	status VARCHAR(20) PRIMARY KEY
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO PreapprovalStatus (status) VALUES ('ACTIVE'), ('CANCELED');
ALTER TABLE `preapproval` ADD CONSTRAINT `fk_preapproval_PreapprovalStatus_status` FOREIGN KEY (`status`) REFERENCES `PreapprovalStatus` (`status`);

ALTER TABLE `Users` ADD COLUMN `last_facebook_page_post_retrieved` TIMESTAMP NOT NULL DEFAULT '1971-01-01 00:00:00' AFTER `facebook_page_id`;

ALTER TABLE `ReplyPagingStatuses` DROP COLUMN `last_page_retrieved`;
ALTER TABLE `ReplyPagingStatuses` ADD COLUMN `max_fetched_comment_time` TIMESTAMP NOT NULL DEFAULT '1971-01-01 00:00:00';

insert into PaymentStatus set status='EXPIRED';
insert into PaymentStatus set status='ERROR';

alter table Payments add column `backendPayerId` varchar(255) DEFAULT NULL after `correlationId`;
alter table Payments add column `authorizationTransactionId` varchar(255) DEFAULT NULL after `backendPayerId`;

alter table Users add column `dob` varchar(255) DEFAULT NULL after `emailAddress`;

alter table Users add column `do_allow_seller_emails` bit(1) DEFAULT NULL;

/* First iteration of the stored procedure for handling sequences 
DELIMITER |
CREATE PROCEDURE getNextSequenceValue( IN p_seqName VARCHAR(255), IN p_cacheSize bigint(20) )


BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        ROLLBACK;
    END;
	    
	START TRANSACTION; 
		UPDATE Sequences SET seq_current = ( @next := seq_current + p_cacheSize) WHERE seq_name = p_seqName;
		SELECT @next;
	COMMIT;
END |

GRANT EXECUTE ON PROCEDURE hootit.getNextSequenceValue TO 'batch'@'%';
*/

/* Drop the old MyISAM sequences */
drop table BATCH_JOB_EXECUTION_SEQ;
drop table BATCH_JOB_SEQ;
drop table BATCH_STEP_EXECUTION_SEQ;
drop table VOUCHER_SEQ;

/* Updated stored procedure for handling sequences */
DELIMITER |
CREATE PROCEDURE getNextSequenceValue( IN p_seqName VARCHAR(255), IN p_cacheSize bigint(20) )

BEGIN
	set @sql_text := concat('INSERT INTO ', p_seqName, ' VALUES( null )');
	prepare stmt from @sql_text;
	execute stmt;
	set @next := LAST_INSERT_ID();
	deallocate prepare stmt;
	select @next;
END |
DELIMITER ;

/* Recereate the sequences with InnoDB tables */

CREATE TABLE `BATCH_JOB_EXECUTION_SEQ` (
  `uid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=560901 DEFAULT CHARSET=utf8;

CREATE TABLE `BATCH_JOB_SEQ` (
  `uid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=560901 DEFAULT CHARSET=utf8;

CREATE TABLE `BATCH_STEP_EXECUTION_SEQ` (
  `uid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=26932520 DEFAULT CHARSET=utf8;

CREATE TABLE `VOUCHER_SEQ` (
  `uid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000000000145 DEFAULT CHARSET=utf8;



/* Modify the batch metadata tables so that deletes on the BATCH_JOB_INSTANCE table cascades across all the rest */

ALTER TABLE `BATCH_STEP_EXECUTION_CONTEXT` DROP FOREIGN KEY `STEP_EXEC_CTX_FK`;
ALTER TABLE `BATCH_STEP_EXECUTION_CONTEXT` ADD CONSTRAINT `STEP_EXEC_CTX_FK` FOREIGN KEY (`STEP_EXECUTION_ID`) REFERENCES `BATCH_STEP_EXECUTION` (`STEP_EXECUTION_ID`) ON DELETE CASCADE;

ALTER TABLE `BATCH_STEP_EXECUTION` DROP FOREIGN KEY `JOB_EXEC_STEP_FK`;
/*DELETE FROM `BATCH_STEP_EXECUTION` WHERE `JOB_EXECUTION_ID` NOT IN ( SELECT `JOB_EXECUTION_ID` FROM `BATCH_JOB_EXECUTION` )*/
ALTER TABLE `BATCH_STEP_EXECUTION` ADD CONSTRAINT `JOB_EXEC_STEP_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`) ON DELETE CASCADE;

ALTER TABLE `BATCH_JOB_EXECUTION_CONTEXT` DROP FOREIGN KEY `JOB_EXEC_CTX_FK`;
ALTER TABLE `BATCH_JOB_EXECUTION_CONTEXT` ADD CONSTRAINT `JOB_EXEC_CTX_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`) ON DELETE CASCADE;

ALTER TABLE `BATCH_JOB_EXECUTION_PARAMS` DROP FOREIGN KEY `JOB_EXEC_PARAMS_FK`;
ALTER TABLE `BATCH_JOB_EXECUTION_PARAMS` ADD CONSTRAINT `JOB_EXEC_PARAMS_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`) ON DELETE CASCADE;

ALTER TABLE `BATCH_JOB_EXECUTION` DROP FOREIGN KEY `JOB_INST_EXEC_FK`;
ALTER TABLE `BATCH_JOB_EXECUTION` ADD CONSTRAINT `JOB_INST_EXEC_FK` FOREIGN KEY (`JOB_INSTANCE_ID`) REFERENCES `BATCH_JOB_INSTANCE` (`JOB_INSTANCE_ID`) ON DELETE CASCADE;


/* These statements may help to get rid of rows and speed up the process a bit
select max( JOB_INSTANCE_ID ) from BATCH_JOB_INSTANCE; 
	556989
select min( JOB_EXECUTION_ID ) from BATCH_JOB_EXECUTION where JOB_INSTANCE_ID = 500000; 
	499999
select min( STEP_EXECUTION_ID ) from BATCH_STEP_EXECUTION  where JOB_EXECUTION_ID = 499999;
	23949007

delete from BATCH_STEP_EXECUTION_CONTEXT where STEP_EXECUTION_ID <= 24102329;

delete from BATCH_STEP_EXECUTION where JOB_EXECUTION_ID <= 499999;
delete from BATCH_JOB_EXECUTION_CONTEXT where JOB_EXECUTION_ID <= 499999;
delete from BATCH_JOB_EXECUTION_PARAMS where JOB_EXECUTION_ID <= 499999;
delete from BATCH_JOB_EXECUTION where JOB_EXECUTION_ID <= 499999;
delete from BATCH_JOB_INSTANCE where JOB_INSTANCE_ID <= 499999;
*/

/* Updated TOS */
insert into TermsOfServices set version=0, internal_version=200, is_for_buyers=TRUE, public_name='Provided by Ruben and updated with Braintree info as of Jan-14 2015';
