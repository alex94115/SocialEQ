-- MySQL dump 10.13  Distrib 5.5.36, for Linux (x86_64)
--
-- Host: hootit-prod.cfxdha2mcmgt.us-east-1.rds.amazonaws.com    Database: hootit
-- ------------------------------------------------------
-- Server version	5.6.19-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Addresses`
--

DROP TABLE IF EXISTS `Addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Addresses` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `firstLine` varchar(255) DEFAULT NULL,
  `secondLine` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `thirdLine` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `isPrimary` bit(1) DEFAULT NULL,
  `isVerified` bit(1) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKBB979BF4F3A96AD8` (`user_id`),
  CONSTRAINT `FKBB979BF4F3A96AD8` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_JOB_EXECUTION`
--

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_EXECUTION` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `JOB_INSTANCE_ID` bigint(20) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  `START_TIME` datetime DEFAULT NULL,
  `END_TIME` datetime DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT NULL,
  `EXIT_CODE` varchar(2500) DEFAULT NULL,
  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
  `LAST_UPDATED` datetime DEFAULT NULL,
  `JOB_CONFIGURATION_LOCATION` varchar(2500) DEFAULT NULL,
  PRIMARY KEY (`JOB_EXECUTION_ID`),
  KEY `JOB_INST_EXEC_FK` (`JOB_INSTANCE_ID`),
  KEY `END_TIME` (`END_TIME`,`EXIT_CODE`(255),`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_INST_EXEC_FK` FOREIGN KEY (`JOB_INSTANCE_ID`) REFERENCES `BATCH_JOB_INSTANCE` (`JOB_INSTANCE_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_JOB_EXECUTION_CONTEXT`
--

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION_CONTEXT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_EXECUTION_CONTEXT` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `SHORT_CONTEXT` varchar(2500) NOT NULL,
  `SERIALIZED_CONTEXT` text,
  PRIMARY KEY (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_CTX_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_JOB_EXECUTION_PARAMS`
--

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION_PARAMS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_EXECUTION_PARAMS` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `TYPE_CD` varchar(6) NOT NULL,
  `KEY_NAME` varchar(100) NOT NULL,
  `STRING_VAL` varchar(250) DEFAULT NULL,
  `DATE_VAL` datetime DEFAULT NULL,
  `LONG_VAL` bigint(20) DEFAULT NULL,
  `DOUBLE_VAL` double DEFAULT NULL,
  `IDENTIFYING` char(1) NOT NULL,
  KEY `JOB_EXEC_PARAMS_FK` (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_PARAMS_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_JOB_EXECUTION_SEQ`
--

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_EXECUTION_SEQ` (
  `uid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=909608 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_JOB_INSTANCE`
--

DROP TABLE IF EXISTS `BATCH_JOB_INSTANCE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_INSTANCE` (
  `JOB_INSTANCE_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `JOB_NAME` varchar(100) NOT NULL,
  `JOB_KEY` varchar(32) NOT NULL,
  PRIMARY KEY (`JOB_INSTANCE_ID`),
  UNIQUE KEY `JOB_INST_UN` (`JOB_NAME`,`JOB_KEY`),
  KEY `JOB_NAME` (`JOB_NAME`,`JOB_INSTANCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_JOB_SEQ`
--

DROP TABLE IF EXISTS `BATCH_JOB_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_SEQ` (
  `uid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=909608 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_STEP_EXECUTION`
--

DROP TABLE IF EXISTS `BATCH_STEP_EXECUTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_STEP_EXECUTION` (
  `STEP_EXECUTION_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) NOT NULL,
  `STEP_NAME` varchar(100) NOT NULL,
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `START_TIME` datetime NOT NULL,
  `END_TIME` datetime DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT NULL,
  `COMMIT_COUNT` bigint(20) DEFAULT NULL,
  `READ_COUNT` bigint(20) DEFAULT NULL,
  `FILTER_COUNT` bigint(20) DEFAULT NULL,
  `WRITE_COUNT` bigint(20) DEFAULT NULL,
  `READ_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `WRITE_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `PROCESS_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `ROLLBACK_COUNT` bigint(20) DEFAULT NULL,
  `EXIT_CODE` varchar(2500) DEFAULT NULL,
  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
  `LAST_UPDATED` datetime DEFAULT NULL,
  PRIMARY KEY (`STEP_EXECUTION_ID`),
  KEY `JOB_EXEC_STEP_FK` (`JOB_EXECUTION_ID`),
  KEY `JOB_EXECUTION_ID` (`JOB_EXECUTION_ID`,`STEP_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_STEP_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_STEP_EXECUTION_CONTEXT`
--

DROP TABLE IF EXISTS `BATCH_STEP_EXECUTION_CONTEXT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_STEP_EXECUTION_CONTEXT` (
  `STEP_EXECUTION_ID` bigint(20) NOT NULL,
  `SHORT_CONTEXT` varchar(2500) NOT NULL,
  `SERIALIZED_CONTEXT` text,
  PRIMARY KEY (`STEP_EXECUTION_ID`),
  CONSTRAINT `STEP_EXEC_CTX_FK` FOREIGN KEY (`STEP_EXECUTION_ID`) REFERENCES `BATCH_STEP_EXECUTION` (`STEP_EXECUTION_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_STEP_EXECUTION_SEQ`
--

DROP TABLE IF EXISTS `BATCH_STEP_EXECUTION_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_STEP_EXECUTION_SEQ` (
  `uid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=43915527 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BusinessMetrics`
--

DROP TABLE IF EXISTS `BusinessMetrics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BusinessMetrics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seller_id` bigint(20) DEFAULT NULL,
  `listing_id` bigint(20) DEFAULT NULL,
  `metrics_execution_id` bigint(20) DEFAULT NULL,
  `retweets` int(11) DEFAULT NULL,
  `responses` int(11) DEFAULT NULL,
  `fb_responses` int(11) DEFAULT NULL,
  `tw_responses` int(11) DEFAULT NULL,
  `hashtag_responses` int(11) DEFAULT NULL,
  `fb_hashtag_responses` int(11) DEFAULT NULL,
  `tw_hashtag_responses` int(11) DEFAULT NULL,
  `opt_ins` int(11) DEFAULT NULL,
  `tw_opt_ins` int(11) DEFAULT NULL,
  `fb_opt_ins` int(11) DEFAULT NULL,
  `payments` int(11) DEFAULT NULL,
  `tw_payments` int(11) DEFAULT NULL,
  `fb_payments` int(11) DEFAULT NULL,
  `gross_sales` decimal(15,5) DEFAULT NULL,
  `tw_gross_sales` decimal(15,5) DEFAULT NULL,
  `fb_gross_sales` decimal(15,5) DEFAULT NULL,
  `net_sales` decimal(15,5) DEFAULT NULL,
  `tw_net_sales` decimal(15,5) DEFAULT NULL,
  `fb_net_sales` decimal(15,5) DEFAULT NULL,
  `donations` int(11) DEFAULT NULL,
  `tw_donations` decimal(15,5) DEFAULT NULL,
  `fb_donations` decimal(15,5) DEFAULT NULL,
  `gross_donations` decimal(15,5) DEFAULT NULL,
  `tw_gross_donations` decimal(15,5) DEFAULT NULL,
  `fb_gross_donations` decimal(15,5) DEFAULT NULL,
  `net_donations` decimal(15,5) DEFAULT NULL,
  `tw_net_donations` decimal(15,5) DEFAULT NULL,
  `fb_net_donations` decimal(15,5) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `MetricsExecutionListingId` (`metrics_execution_id`,`listing_id`),
  KEY `fk_business_metrics_job_execution_id` (`metrics_execution_id`),
  KEY `fk_business_metrics_listing_id` (`listing_id`),
  KEY `fk_business_metrics_seller_id` (`seller_id`),
  CONSTRAINT `fk_business_metrics_job_execution_id` FOREIGN KEY (`metrics_execution_id`) REFERENCES `MetricsJobExecutions` (`id`),
  CONSTRAINT `fk_business_metrics_listing_id` FOREIGN KEY (`listing_id`) REFERENCES `Listings` (`id`),
  CONSTRAINT `fk_business_metrics_seller_id` FOREIGN KEY (`seller_id`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=167 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ContentFiles`
--

DROP TABLE IF EXISTS `ContentFiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ContentFiles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `contentFilename` varchar(255) DEFAULT NULL,
  `digitalContentOriginalFilename` varchar(512) DEFAULT NULL,
  `digitalContentType` varchar(255) DEFAULT NULL,
  `listing_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_9o375mrn5drc4g1199c1ym8hp` (`listing_id`),
  CONSTRAINT `FK_9o375mrn5drc4g1199c1ym8hp` FOREIGN KEY (`listing_id`) REFERENCES `Listings` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `CurrentPaymentStatus`
--

DROP TABLE IF EXISTS `CurrentPaymentStatus`;
/*!50001 DROP VIEW IF EXISTS `CurrentPaymentStatus`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `CurrentPaymentStatus` (
  `payment_id` tinyint NOT NULL,
  `payer_id` tinyint NOT NULL,
  `payee_id` tinyint NOT NULL,
  `message_id` tinyint NOT NULL,
  `listing_id` tinyint NOT NULL,
  `currencyCode` tinyint NOT NULL,
  `amount` tinyint NOT NULL,
  `rodLaverAmount` tinyint NOT NULL,
  `sellerAmount` tinyint NOT NULL,
  `payKey` tinyint NOT NULL,
  `correlationId` tinyint NOT NULL,
  `shippingAddress_id` tinyint NOT NULL,
  `has_been_shipped` tinyint NOT NULL,
  `eps_id` tinyint NOT NULL,
  `status` tinyint NOT NULL,
  `start` tinyint NOT NULL,
  `eps_end` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `CurrentPreapprovals`
--

DROP TABLE IF EXISTS `CurrentPreapprovals`;
/*!50001 DROP VIEW IF EXISTS `CurrentPreapprovals`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `CurrentPreapprovals` (
  `id` tinyint NOT NULL,
  `correlationId` tinyint NOT NULL,
  `preapprovalKey` tinyint NOT NULL,
  `user_id` tinyint NOT NULL,
  `version` tinyint NOT NULL,
  `status` tinyint NOT NULL,
  `start` tinyint NOT NULL,
  `max(end)` tinyint NOT NULL,
  `created` tinyint NOT NULL,
  `updated` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `CurrentTwitterStreamSellers`
--

DROP TABLE IF EXISTS `CurrentTwitterStreamSellers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CurrentTwitterStreamSellers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `providerUserId` varchar(255) NOT NULL,
  `displayName` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_current_twitter_stream_sellers_user_id` (`user_id`),
  CONSTRAINT `fk_current_twitter_stream_sellers_user_id` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=219 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `DownloadInstances`
--

DROP TABLE IF EXISTS `DownloadInstances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DownloadInstances` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `listing_id` bigint(20) NOT NULL,
  `content_file_id` bigint(20) NOT NULL,
  `payment_id` bigint(20) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_download_instance_user_id` (`user_id`),
  KEY `fk_download_instance_listing_id` (`listing_id`),
  KEY `fk_download_instance_content_file_id` (`content_file_id`),
  CONSTRAINT `fk_download_instance_content_file_id` FOREIGN KEY (`content_file_id`) REFERENCES `ContentFiles` (`id`),
  CONSTRAINT `fk_download_instance_listing_id` FOREIGN KEY (`listing_id`) REFERENCES `Listings` (`id`),
  CONSTRAINT `fk_download_instance_user_id` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `EffectivePaymentStatuses`
--

DROP TABLE IF EXISTS `EffectivePaymentStatuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EffectivePaymentStatuses` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `start` datetime NOT NULL,
  `end` datetime NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `payment_id` bigint(20) DEFAULT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_2ku6t5q0t9vvm4v9uhag2yqpk` (`payment_id`),
  KEY `fk_effective_payment_status_PaymentStatus_status` (`status`),
  KEY `created` (`created`,`status`),
  KEY `start` (`start`,`end`,`status`),
  CONSTRAINT `FK_2ku6t5q0t9vvm4v9uhag2yqpk` FOREIGN KEY (`payment_id`) REFERENCES `Payments` (`id`),
  CONSTRAINT `fk_effective_payment_status_PaymentStatus_status` FOREIGN KEY (`status`) REFERENCES `PaymentStatus` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=289 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `EffectiveVoucherStatuses`
--

DROP TABLE IF EXISTS `EffectiveVoucherStatuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  KEY `voucher_id` (`voucher_id`),
  CONSTRAINT `EffectiveVoucherStatuses_ibfk_1` FOREIGN KEY (`voucher_id`) REFERENCES `Vouchers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FacebookUpdateNotifications`
--

DROP TABLE IF EXISTS `FacebookUpdateNotifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FacebookUpdateNotifications` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `facebook_page_id` varchar(255) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `batch_processor` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Inventories`
--

DROP TABLE IF EXISTS `Inventories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `ListingHasDigitalContent`
--

DROP TABLE IF EXISTS `ListingHasDigitalContent`;
/*!50001 DROP VIEW IF EXISTS `ListingHasDigitalContent`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `ListingHasDigitalContent` (
  `listing_id` tinyint NOT NULL,
  `has_digital_content` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `ListingReplies`
--

DROP TABLE IF EXISTS `ListingReplies`;
/*!50001 DROP VIEW IF EXISTS `ListingReplies`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `ListingReplies` (
  `listing_id` tinyint NOT NULL,
  `listing_locale` tinyint NOT NULL,
  `seller_id` tinyint NOT NULL,
  `provider_id` tinyint NOT NULL,
  `seller_provider_user_id` tinyint NOT NULL,
  `listing_status` tinyint NOT NULL,
  `listing_type` tinyint NOT NULL,
  `goods_type` tinyint NOT NULL,
  `listing_sub_type` tinyint NOT NULL,
  `listing_has_single_inventory` tinyint NOT NULL,
  `reply_id` tinyint NOT NULL,
  `reply_provider_user_id` tinyint NOT NULL,
  `reply_content_id` tinyint NOT NULL,
  `reply_user_id` tinyint NOT NULL,
  `has_reply_user_accepted_tos` tinyint NOT NULL,
  `is_reply_user_blocked` tinyint NOT NULL,
  `reply_contains_keyword` tinyint NOT NULL,
  `reply_status` tinyint NOT NULL,
  `reply_batch_processor` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `ListingStateChanges`
--

DROP TABLE IF EXISTS `ListingStateChanges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ListingStateChanges` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `doesRequireFacebookPosting` tinyint(1) DEFAULT NULL,
  `isFacebookPostingComplete` tinyint(1) DEFAULT NULL,
  `doesRequireTwitterPosting` tinyint(1) DEFAULT NULL,
  `isTwitterPostingComplete` tinyint(1) DEFAULT NULL,
  `newState` varchar(255) DEFAULT NULL,
  `previousState` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `listing_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_listing` (`listing_id`),
  CONSTRAINT `FK_listing` FOREIGN KEY (`listing_id`) REFERENCES `Listings` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ListingStatus`
--

DROP TABLE IF EXISTS `ListingStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ListingStatus` (
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Listings`
--

DROP TABLE IF EXISTS `Listings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Listings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(15,5) DEFAULT NULL,
  `imageFilename` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  `goodsType` varchar(255) NOT NULL,
  `version` int(11) NOT NULL,
  `seller_id` bigint(20) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(255) DEFAULT NULL,
  `imageHeight` int(11) DEFAULT NULL,
  `imageWidth` int(11) DEFAULT NULL,
  `currencyCode` varchar(255) DEFAULT NULL,
  `locale` varchar(10) DEFAULT 'en_US',
  `keyword` varchar(255) DEFAULT NULL,
  `announcementPreamble` varchar(255) DEFAULT NULL,
  `backgroundImageUrl` varchar(512) DEFAULT NULL,
  `expires` datetime NOT NULL,
  `giveawaySeed` int(11) DEFAULT NULL,
  `longDescription` varchar(512) DEFAULT NULL,
  `termsAndConditions` mediumtext,
  `batch_processor` varchar(255) DEFAULT NULL,
  `subType` varchar(255) DEFAULT NULL,
  `doPostToFacebook` bit(1) DEFAULT NULL,
  `doPostToTwitter` bit(1) DEFAULT NULL,
  `sellerRevenueRatio` decimal(6,5) DEFAULT NULL,
  `has_single_inventory` bit(1) DEFAULT NULL,
  `max_qty_per_purchase` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `imageFilename` (`imageFilename`),
  KEY `FKAD8BA843FB2D238` (`seller_id`),
  KEY `id` (`id`,`keyword`),
  KEY `fk_listing_status_ListingStatus_status` (`status`),
  CONSTRAINT `FKAD8BA843FB2D238` FOREIGN KEY (`seller_id`) REFERENCES `Users` (`id`),
  CONSTRAINT `fk_listing_status_ListingStatus_status` FOREIGN KEY (`status`) REFERENCES `ListingStatus` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=90096 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `MessageStateChanges`
--

DROP TABLE IF EXISTS `MessageStateChanges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MessageStateChanges` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` bigint(20) DEFAULT NULL,
  `previousState` varchar(30) NOT NULL,
  `newState` varchar(30) NOT NULL,
  `doesRequireBuyerCommunication` tinyint(1) DEFAULT NULL,
  `areBuyerCommunicationsComplete` tinyint(1) DEFAULT NULL,
  `doesRequireSellerCommunication` tinyint(1) DEFAULT NULL,
  `areSellerCommunicationsComplete` tinyint(1) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_w2wuby6onu0hetgq1fiq35a2` (`message_id`),
  KEY `fk_message_state_change_previousState_MessageStatus_status` (`previousState`),
  KEY `fk_message_state_change_newState_MessageStatus_status` (`newState`),
  CONSTRAINT `fk_message_state_change_newState_MessageStatus_status` FOREIGN KEY (`newState`) REFERENCES `MessageStatus` (`status`),
  CONSTRAINT `fk_message_state_change_previousState_MessageStatus_status` FOREIGN KEY (`previousState`) REFERENCES `MessageStatus` (`status`),
  CONSTRAINT `FK_w2wuby6onu0hetgq1fiq35a2` FOREIGN KEY (`message_id`) REFERENCES `Messages` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1235 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `MessageStatus`
--

DROP TABLE IF EXISTS `MessageStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MessageStatus` (
  `status` varchar(30) NOT NULL,
  PRIMARY KEY (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Messages`
--

DROP TABLE IF EXISTS `Messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Messages` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) DEFAULT NULL,
  `twitterId` varchar(255) DEFAULT NULL,
  `inResponseToTwitterId` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `listing_id` bigint(20) DEFAULT NULL,
  `status` varchar(30) NOT NULL,
  `providerId` varchar(255) DEFAULT NULL,
  `providerUserId` varchar(255) DEFAULT NULL,
  `batch_processor` varchar(255) DEFAULT NULL,
  `containsKeyword` tinyint(1) DEFAULT NULL,
  `embeded_provider_content` varchar(512) DEFAULT NULL,
  `isNaturalReply` tinyint(1) DEFAULT NULL,
  `isRetweet` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `twitterId` (`twitterId`),
  KEY `FK38EB00079128426C` (`user_id`),
  KEY `FK38EB0007C1D197C` (`listing_id`),
  KEY `ix_status_provider_user` (`status`,`providerId`,`providerUserId`),
  KEY `listing_id` (`listing_id`,`twitterId`,`providerId`),
  KEY `inResponseToTwitterId` (`inResponseToTwitterId`,`providerId`),
  KEY `twitterId_2` (`twitterId`,`providerId`),
  CONSTRAINT `FK38EB00079128426C` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`),
  CONSTRAINT `FK38EB0007C1D197C` FOREIGN KEY (`listing_id`) REFERENCES `Listings` (`id`),
  CONSTRAINT `fk_message_status_MessageStatus_status` FOREIGN KEY (`status`) REFERENCES `MessageStatus` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=3673 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `MetricsJobExecutions`
--

DROP TABLE IF EXISTS `MetricsJobExecutions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MetricsJobExecutions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `end_time` (`end_time`),
  KEY `status` (`status`,`start_time`,`end_time`)
) ENGINE=InnoDB AUTO_INCREMENT=44176 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PaymentStatus`
--

DROP TABLE IF EXISTS `PaymentStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PaymentStatus` (
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Payments`
--

DROP TABLE IF EXISTS `Payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Payments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `payKey` varchar(255) DEFAULT NULL,
  `correlationId` varchar(255) DEFAULT NULL,
  `backendPayerId` varchar(255) DEFAULT NULL,
  `authorizationTransactionId` varchar(255) DEFAULT NULL,
  `payer_id` bigint(20) DEFAULT NULL,
  `amount` decimal(15,5) DEFAULT NULL,
  `currencyCode` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `listing_id` bigint(20) DEFAULT NULL,
  `inventory_id` bigint(20) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `message_id` bigint(20) DEFAULT NULL,
  `payee_id` bigint(20) DEFAULT NULL,
  `shippingAddress_id` bigint(20) DEFAULT NULL,
  `rodLaverAmount` decimal(15,5) DEFAULT NULL,
  `sellerAmount` decimal(15,5) DEFAULT NULL,
  `has_been_shipped` tinyint(1) DEFAULT NULL,
  `batch_processor` varchar(255) DEFAULT NULL,
  `batch_status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKD11C3206EBEC56A2` (`payer_id`),
  KEY `FKD11C3206FC6E89E4` (`id`),
  KEY `FKD11C3206C1D197C` (`listing_id`),
  KEY `FKD11C32064E67963B` (`payee_id`),
  KEY `FKD11C32069A76869C` (`message_id`),
  KEY `FKD11C320694547FAA` (`shippingAddress_id`),
  KEY `fk_payments_inventories_id` (`inventory_id`),
  KEY `correlationId` (`correlationId`),
  CONSTRAINT `FKD11C32064E67963B` FOREIGN KEY (`payee_id`) REFERENCES `Users` (`id`),
  CONSTRAINT `FKD11C320694547FAA` FOREIGN KEY (`shippingAddress_id`) REFERENCES `Addresses` (`id`),
  CONSTRAINT `FKD11C32069A76869C` FOREIGN KEY (`message_id`) REFERENCES `Messages` (`id`),
  CONSTRAINT `FKD11C3206C1D197C` FOREIGN KEY (`listing_id`) REFERENCES `Listings` (`id`),
  CONSTRAINT `FKD11C3206EBEC56A2` FOREIGN KEY (`payer_id`) REFERENCES `Users` (`id`),
  CONSTRAINT `Payments_ibfk_1` FOREIGN KEY (`inventory_id`) REFERENCES `Inventories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3149 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PaypalStatusUpdates`
--

DROP TABLE IF EXISTS `PaypalStatusUpdates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PaypalStatusUpdates` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ipnValidated` bit(1) DEFAULT NULL,
  `mapData` varchar(2048) DEFAULT NULL,
  `transactionType` varchar(255) DEFAULT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `paymentKey` varchar(255) DEFAULT NULL,
  `correlationId` varchar(20) DEFAULT NULL,
  `updatedPaymentStatus` varchar(255) DEFAULT NULL,
  `preapprovalKey` varchar(255) DEFAULT NULL,
  `updatedPreapprovalStatus` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `status` varchar(20) DEFAULT NULL,
  `batch_processor` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `correlationId` (`correlationId`,`status`,`transactionType`,`updatedPaymentStatus`,`batch_processor`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PreapprovalStatus`
--

DROP TABLE IF EXISTS `PreapprovalStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PreapprovalStatus` (
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Preapprovals`
--

DROP TABLE IF EXISTS `Preapprovals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Preapprovals` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `correlationId` varchar(255) DEFAULT NULL,
  `preapprovalKey` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end` datetime NOT NULL,
  `start` datetime NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `preapprovalKey` (`preapprovalKey`),
  KEY `FKB267C5E69128426C` (`user_id`),
  KEY `status` (`status`),
  CONSTRAINT `FKB267C5E69128426C` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`),
  CONSTRAINT `fk_preapproval_PreapprovalStatus_status` FOREIGN KEY (`status`) REFERENCES `PreapprovalStatus` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ReplyPagingStatuses`
--

DROP TABLE IF EXISTS `ReplyPagingStatuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ReplyPagingStatuses` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `providerId` varchar(255) NOT NULL,
  `providerContentId` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `listing_message_id` bigint(20) NOT NULL,
  `batch_processor` varchar(255) DEFAULT NULL,
  `max_fetched_comment_time` timestamp NOT NULL DEFAULT '1971-01-01 00:00:00',
  PRIMARY KEY (`id`),
  KEY `fk_message_id` (`listing_message_id`),
  CONSTRAINT `fk_message_id` FOREIGN KEY (`listing_message_id`) REFERENCES `Messages` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Roles`
--

DROP TABLE IF EXISTS `Roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` int(11) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SellerTweetRateLimits`
--

DROP TABLE IF EXISTS `SellerTweetRateLimits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SellerTweetRateLimits` (
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
  CONSTRAINT `FK_client_tweet_rate_limits_user_id` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SentOutboundCommunications`
--

DROP TABLE IF EXISTS `SentOutboundCommunications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `TermsOfServices`
--

DROP TABLE IF EXISTS `TermsOfServices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TermsOfServices` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `internal_version` int(11) DEFAULT NULL,
  `is_for_buyers` tinyint(1) DEFAULT NULL,
  `is_for_sellers` tinyint(1) DEFAULT NULL,
  `public_name` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `UserConnection`
--

DROP TABLE IF EXISTS `UserConnection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserConnection` (
  `userId` varchar(255) NOT NULL,
  `providerId` varchar(255) NOT NULL,
  `providerUserId` varchar(255) NOT NULL DEFAULT '',
  `rank` int(11) NOT NULL,
  `displayName` varchar(255) DEFAULT NULL,
  `profileUrl` varchar(512) DEFAULT NULL,
  `imageUrl` varchar(512) DEFAULT NULL,
  `accessToken` varchar(255) NOT NULL,
  `secret` varchar(255) DEFAULT NULL,
  `refreshToken` varchar(255) DEFAULT NULL,
  `expireTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`userId`,`providerId`,`providerUserId`),
  UNIQUE KEY `UserConnectionRank` (`userId`,`providerId`,`rank`),
  UNIQUE KEY `ProviderConnection` (`providerId`,`providerUserId`),
  CONSTRAINT `fk_user_connection_user_userid` FOREIGN KEY (`userId`) REFERENCES `Users` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `merchant_name` varchar(255) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `emailAddress` varchar(255) DEFAULT NULL,
  `dob` varchar(255) DEFAULT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `mobile_number` varchar(255) DEFAULT NULL,
  `is_mobile_verified` tinyint(1) DEFAULT NULL,
  `profile_image_url` varchar(512) DEFAULT NULL,
  `facebook_album_id` varchar(255) DEFAULT NULL,
  `facebook_page_url` varchar(255) DEFAULT NULL,
  `facebook_page_id` varchar(255) DEFAULT NULL,
  `last_facebook_page_post_retrieved` timestamp NOT NULL DEFAULT '1971-01-01 00:00:00',
  `has_accepted_current_buyer_tos` bit(1) DEFAULT NULL,
  `is_user_blocked` bit(1) DEFAULT NULL,
  `do_use_chained_pmts` bit(1) DEFAULT b'1',
  `voucher_logo_image_url` varchar(255) DEFAULT NULL,
  `voucher_campaign_image_url` varchar(255) DEFAULT NULL,
  `do_allow_seller_emails` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1064 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Users_Roles`
--

DROP TABLE IF EXISTS `Users_Roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users_Roles` (
  `users_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL,
  PRIMARY KEY (`users_id`,`roles_id`),
  KEY `FK_5k3dviices5fr7560hvc81x4r` (`roles_id`),
  KEY `FK_4edf6ibqo9873ixvuyri8xua2` (`users_id`),
  CONSTRAINT `FK_4edf6ibqo9873ixvuyri8xua2` FOREIGN KEY (`users_id`) REFERENCES `Users` (`id`),
  CONSTRAINT `FK_5k3dviices5fr7560hvc81x4r` FOREIGN KEY (`roles_id`) REFERENCES `Roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Users_TermsOfServices`
--

DROP TABLE IF EXISTS `Users_TermsOfServices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users_TermsOfServices` (
  `users_id` bigint(20) NOT NULL,
  `acceptedTermsOfService_id` bigint(20) NOT NULL,
  PRIMARY KEY (`users_id`,`acceptedTermsOfService_id`),
  KEY `FK_rkwkfgjfex9629r5nqopowi0c` (`acceptedTermsOfService_id`),
  KEY `FK_8fx1w5tmksfkskwrcok3ny8i5` (`users_id`),
  CONSTRAINT `FK_8fx1w5tmksfkskwrcok3ny8i5` FOREIGN KEY (`users_id`) REFERENCES `Users` (`id`),
  CONSTRAINT `FK_rkwkfgjfex9629r5nqopowi0c` FOREIGN KEY (`acceptedTermsOfService_id`) REFERENCES `TermsOfServices` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `VOUCHER_SEQ`
--

DROP TABLE IF EXISTS `VOUCHER_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VOUCHER_SEQ` (
  `uid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  UNIQUE KEY `uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000000000022 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Vouchers`
--

DROP TABLE IF EXISTS `Vouchers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Final view structure for view `CurrentPaymentStatus`
--

/*!50001 DROP TABLE IF EXISTS `CurrentPaymentStatus`*/;
/*!50001 DROP VIEW IF EXISTS `CurrentPaymentStatus`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`hootit`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `CurrentPaymentStatus` AS select `p`.`id` AS `payment_id`,`p`.`payer_id` AS `payer_id`,`p`.`payee_id` AS `payee_id`,`p`.`message_id` AS `message_id`,`p`.`listing_id` AS `listing_id`,`p`.`currencyCode` AS `currencyCode`,`p`.`amount` AS `amount`,`p`.`rodLaverAmount` AS `rodLaverAmount`,`p`.`sellerAmount` AS `sellerAmount`,`p`.`payKey` AS `payKey`,`p`.`correlationId` AS `correlationId`,`p`.`shippingAddress_id` AS `shippingAddress_id`,`p`.`has_been_shipped` AS `has_been_shipped`,`eps`.`id` AS `eps_id`,`eps`.`status` AS `status`,`eps`.`start` AS `start`,`eps`.`end` AS `eps_end` from (`Payments` `p` join `EffectivePaymentStatuses` `eps` on((`p`.`id` = `eps`.`payment_id`))) where (now() between `eps`.`start` and `eps`.`end`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `CurrentPreapprovals`
--

/*!50001 DROP TABLE IF EXISTS `CurrentPreapprovals`*/;
/*!50001 DROP VIEW IF EXISTS `CurrentPreapprovals`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`hootit`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `CurrentPreapprovals` AS select `Preapprovals`.`id` AS `id`,`Preapprovals`.`correlationId` AS `correlationId`,`Preapprovals`.`preapprovalKey` AS `preapprovalKey`,`Preapprovals`.`user_id` AS `user_id`,`Preapprovals`.`version` AS `version`,`Preapprovals`.`status` AS `status`,`Preapprovals`.`start` AS `start`,max(`Preapprovals`.`end`) AS `max(end)`,`Preapprovals`.`created` AS `created`,`Preapprovals`.`updated` AS `updated` from `Preapprovals` where (`Preapprovals`.`status` = 'ACTIVE') group by `Preapprovals`.`user_id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ListingHasDigitalContent`
--

/*!50001 DROP TABLE IF EXISTS `ListingHasDigitalContent`*/;
/*!50001 DROP VIEW IF EXISTS `ListingHasDigitalContent`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`hootit`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `ListingHasDigitalContent` AS select `l`.`id` AS `listing_id`,(`cf`.`id` is not null) AS `has_digital_content` from (`Listings` `l` left join `ContentFiles` `cf` on((`l`.`id` = `cf`.`listing_id`))) group by `l`.`id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `ListingReplies`
--

/*!50001 DROP TABLE IF EXISTS `ListingReplies`*/;
/*!50001 DROP VIEW IF EXISTS `ListingReplies`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`hootit`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `ListingReplies` AS select `l`.`id` AS `listing_id`,`l`.`locale` AS `listing_locale`,`l`.`seller_id` AS `seller_id`,`lm`.`providerId` AS `provider_id`,`lm`.`providerUserId` AS `seller_provider_user_id`,`l`.`status` AS `listing_status`,`l`.`type` AS `listing_type`,`l`.`goodsType` AS `goods_type`,`l`.`subType` AS `listing_sub_type`,`l`.`has_single_inventory` AS `listing_has_single_inventory`,`reply`.`id` AS `reply_id`,`reply`.`providerUserId` AS `reply_provider_user_id`,`reply`.`twitterId` AS `reply_content_id`,`reply`.`user_id` AS `reply_user_id`,(`respondant`.`has_accepted_current_buyer_tos` is true) AS `has_reply_user_accepted_tos`,(`respondant`.`is_user_blocked` is true) AS `is_reply_user_blocked`,`reply`.`containsKeyword` AS `reply_contains_keyword`,`reply`.`status` AS `reply_status`,`reply`.`batch_processor` AS `reply_batch_processor` from (((`Listings` `l` join `Messages` `lm` on((`l`.`id` = `lm`.`listing_id`))) join `Messages` `reply` on(((`reply`.`providerId` = `lm`.`providerId`) and (`reply`.`inResponseToTwitterId` = `lm`.`twitterId`)))) left join `Users` `respondant` on((`reply`.`user_id` = `respondant`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-25 20:52:28
