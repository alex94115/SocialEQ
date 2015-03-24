# Per Ticket-#378

ALTER TABLE `BATCH_JOB_EXECUTION` MODIFY COLUMN `EXIT_CODE` varchar(2500) DEFAULT NULL;
ALTER TABLE `BATCH_JOB_EXECUTION` ADD COLUMN `JOB_CONFIGURATION_LOCATION` varchar(2500) DEFAULT NULL;
ALTER TABLE `BATCH_JOB_EXECUTION_SEQ` ADD COLUMN `UNIQUE_KEY` char(1) NOT NULL;
ALTER TABLE `BATCH_JOB_EXECUTION_SEQ` ADD UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`);
ALTER TABLE `BATCH_JOB_SEQ` ADD COLUMN `UNIQUE_KEY` char(1) NOT NULL;
ALTER TABLE `BATCH_JOB_SEQ` ADD UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`);
ALTER TABLE `BATCH_STEP_EXECUTION` MODIFY COLUMN `EXIT_CODE` varchar(2500) DEFAULT NULL;
ALTER TABLE `BATCH_STEP_EXECUTION_SEQ` ADD COLUMN `UNIQUE_KEY` char(1) NOT NULL;
ALTER TABLE `BATCH_STEP_EXECUTION_SEQ` ADD UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`);

# Per Ticket-#368

###
# New index on the BATCH_JOB_INSTANCE table
###
ALTER TABLE BATCH_JOB_INSTANCE ADD INDEX ( JOB_NAME, JOB_INSTANCE_ID );

### 
# New index on the BATCH_JOB_EXECUTION table to speed up the heartbeat check
###
ALTER TABLE BATCH_JOB_EXECUTION ADD INDEX ( END_TIME, EXIT_CODE, JOB_EXECUTION_ID );

### 
# New index on the BATCH_JOB_EXECUTION table to used before each step execution
###
ALTER TABLE BATCH_STEP_EXECUTION ADD INDEX ( JOB_EXECUTION_ID, STEP_EXECUTION_ID ); 

### 
# New index on the message table
###
ALTER TABLE message ADD INDEX ( listing_id, twitterId, providerId );

### 
# New index on the listing table
###
ALTER TABLE listing ADD INDEX ( id, keyword );

### 
# Drop the old one-column inResponseToTwitterId index and add two new indexes on the message table
###
ALTER TABLE message DROP KEY `inResponseToTwitterId`;
ALTER TABLE message ADD INDEX ( inResponseToTwitterId, providerId )
ALTER TABLE message ADD INDEX ( twitterId, providerId )

### 
# Index based on end time on the metrics_job_exceution table
###
ALTER TABLE metrics_job_execution ADD INDEX ( end_time );

# Per Ticket-#386

CREATE TABLE `current_twitter_stream_sellers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `providerUserId` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_current_twitter_stream_sellers_user_id` (`user_id`),
  CONSTRAINT `fk_current_twitter_stream_sellers_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

# Per Ticket-#406

CREATE USER 'stream'@'%' IDENTIFIED BY 'password';
GRANT DELETE ON hootit.current_twitter_stream_sellers TO 'stream'@'%';
GRANT INSERT ON hootit.current_twitter_stream_sellers TO 'stream'@'%';
GRANT SELECT ON hootit.current_twitter_stream_sellers TO 'stream'@'%';
GRANT SELECT ON hootit.UserConnection TO 'stream'@'%';
GRANT SELECT ON hootit.user TO 'stream'@'%';
GRANT SELECT ON hootit.user_role  TO 'stream'@'%';
GRANT SELECT ON hootit.listing  TO 'stream'@'%';
GRANT SELECT ON hootit.message  TO 'stream'@'%';
GRANT INSERT ON hootit.message  TO 'stream'@'%';

# Per Ticket-#407
CREATE USER 'batch'@'%' IDENTIFIED BY 'password';

GRANT SELECT ON hootit.BATCH_JOB_EXECUTION TO 'batch'@'%';
GRANT INSERT ON hootit.BATCH_JOB_EXECUTION TO 'batch'@'%';
GRANT UPDATE ON hootit.BATCH_JOB_EXECUTION TO 'batch'@'%';

GRANT SELECT ON hootit.BATCH_JOB_EXECUTION_CONTEXT TO 'batch'@'%';
GRANT INSERT ON hootit.BATCH_JOB_EXECUTION_CONTEXT TO 'batch'@'%';
GRANT UPDATE ON hootit.BATCH_JOB_EXECUTION_CONTEXT TO 'batch'@'%';

GRANT SELECT ON hootit.BATCH_JOB_EXECUTION_PARAMS TO 'batch'@'%';
GRANT INSERT ON hootit.BATCH_JOB_EXECUTION_PARAMS TO 'batch'@'%';
GRANT UPDATE ON hootit.BATCH_JOB_EXECUTION_PARAMS TO 'batch'@'%';

GRANT SELECT ON hootit.BATCH_JOB_EXECUTION_SEQ TO 'batch'@'%';
GRANT INSERT ON hootit.BATCH_JOB_EXECUTION_SEQ TO 'batch'@'%';
GRANT UPDATE ON hootit.BATCH_JOB_EXECUTION_SEQ TO 'batch'@'%';

GRANT SELECT ON hootit.BATCH_JOB_INSTANCE TO 'batch'@'%';
GRANT INSERT ON hootit.BATCH_JOB_INSTANCE TO 'batch'@'%';
GRANT UPDATE ON hootit.BATCH_JOB_INSTANCE TO 'batch'@'%';

GRANT SELECT ON hootit.BATCH_JOB_SEQ TO 'batch'@'%';
GRANT INSERT ON hootit.BATCH_JOB_SEQ TO 'batch'@'%';
GRANT UPDATE ON hootit.BATCH_JOB_SEQ TO 'batch'@'%';

GRANT SELECT ON hootit.BATCH_STEP_EXECUTION TO 'batch'@'%';
GRANT INSERT ON hootit.BATCH_STEP_EXECUTION TO 'batch'@'%';
GRANT UPDATE ON hootit.BATCH_STEP_EXECUTION TO 'batch'@'%';

GRANT SELECT ON hootit.BATCH_STEP_EXECUTION_CONTEXT TO 'batch'@'%';
GRANT INSERT ON hootit.BATCH_STEP_EXECUTION_CONTEXT TO 'batch'@'%';
GRANT UPDATE ON hootit.BATCH_STEP_EXECUTION_CONTEXT TO 'batch'@'%';

GRANT SELECT ON hootit.BATCH_STEP_EXECUTION_SEQ TO 'batch'@'%';
GRANT INSERT ON hootit.BATCH_STEP_EXECUTION_SEQ TO 'batch'@'%';
GRANT UPDATE ON hootit.BATCH_STEP_EXECUTION_SEQ TO 'batch'@'%';

GRANT SELECT ON hootit.UserConnection TO 'batch'@'%';

GRANT SELECT ON hootit.address TO 'batch'@'%';
GRANT INSERT ON hootit.address TO 'batch'@'%';
GRANT UPDATE ON hootit.address TO 'batch'@'%';

GRANT SELECT ON hootit.business_metrics TO 'batch'@'%';
GRANT INSERT ON hootit.business_metrics TO 'batch'@'%';
GRANT UPDATE ON hootit.business_metrics TO 'batch'@'%';

GRANT SELECT ON hootit.content_file TO 'batch'@'%';

GRANT SELECT ON hootit.current_twitter_stream_sellers TO 'batch'@'%';

GRANT SELECT ON hootit.download_instance TO 'batch'@'%';

GRANT SELECT ON hootit.effective_payment_status TO 'batch'@'%';
GRANT INSERT ON hootit.effective_payment_status TO 'batch'@'%';
GRANT UPDATE ON hootit.effective_payment_status TO 'batch'@'%';

GRANT SELECT ON hootit.facebook_update_notification TO 'batch'@'%';

GRANT SELECT ON hootit.listing TO 'batch'@'%';
GRANT INSERT ON hootit.listing TO 'batch'@'%';
GRANT UPDATE ON hootit.listing TO 'batch'@'%';

GRANT SELECT ON hootit.listing_state_change TO 'batch'@'%';
GRANT INSERT ON hootit.listing_state_change TO 'batch'@'%';
GRANT UPDATE ON hootit.listing_state_change TO 'batch'@'%';

GRANT SELECT ON hootit.message TO 'batch'@'%';
GRANT INSERT ON hootit.message TO 'batch'@'%';
GRANT UPDATE ON hootit.message TO 'batch'@'%';

GRANT SELECT ON hootit.message_state_change TO 'batch'@'%';
GRANT INSERT ON hootit.message_state_change TO 'batch'@'%';
GRANT UPDATE ON hootit.message_state_change TO 'batch'@'%';

GRANT SELECT ON hootit.metrics_job_execution TO 'batch'@'%';
GRANT INSERT ON hootit.metrics_job_execution TO 'batch'@'%';
GRANT UPDATE ON hootit.metrics_job_execution TO 'batch'@'%';

GRANT SELECT ON hootit.payment TO 'batch'@'%';
GRANT INSERT ON hootit.payment TO 'batch'@'%';
GRANT UPDATE ON hootit.payment TO 'batch'@'%';

GRANT SELECT ON hootit.paypal_status_update TO 'batch'@'%';
GRANT INSERT ON hootit.paypal_status_update TO 'batch'@'%';
GRANT UPDATE ON hootit.paypal_status_update TO 'batch'@'%';

GRANT SELECT ON hootit.preapproval TO 'batch'@'%';
GRANT INSERT ON hootit.preapproval TO 'batch'@'%';
GRANT UPDATE ON hootit.preapproval TO 'batch'@'%';

GRANT SELECT ON hootit.reply_paging_status TO 'batch'@'%';
GRANT INSERT ON hootit.reply_paging_status TO 'batch'@'%';
GRANT UPDATE ON hootit.reply_paging_status TO 'batch'@'%';

GRANT SELECT ON hootit.role TO 'batch'@'%';

GRANT SELECT ON hootit.sent_outbound_communications TO 'batch'@'%';
GRANT INSERT ON hootit.sent_outbound_communications TO 'batch'@'%';
GRANT UPDATE ON hootit.sent_outbound_communications TO 'batch'@'%';

GRANT SELECT ON hootit.terms_of_service TO 'batch'@'%';

GRANT SELECT ON hootit.user TO 'batch'@'%';
GRANT INSERT ON hootit.user TO 'batch'@'%';

GRANT SELECT ON hootit.user_role TO 'batch'@'%';

GRANT SELECT ON hootit.user_terms_of_service TO 'batch'@'%';

# Per Ticket-#417
alter table `paypal_status_update` add column `preapprovalKey` varchar(255) DEFAULT NULL after `updatedPaymentStatus`;
alter table `paypal_status_update` add column `updatedPreapprovalStatus` varchar(255) DEFAULT NULL after `preapprovalKey`;

# Per Ticket-#420
alter table `payment` add column `batch_processor` varchar(255) default null;
alter table `payment` add column `batch_status` varchar(255) default null;


# Per undeclared ticket
alter table listing add column `termsAndConditions` MEDIUMTEXT after `longDescription`;