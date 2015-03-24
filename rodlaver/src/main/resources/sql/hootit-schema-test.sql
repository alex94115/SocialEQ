-- MySQL dump 10.13  Distrib 5.5.34, for Linux (x86_64)
--
-- Host: hootit-1.cfxdha2mcmgt.us-east-1.rds.amazonaws.com    Database: hootit
-- ------------------------------------------------------
-- Server version	5.6.12-log

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
  `EXIT_CODE` varchar(100) DEFAULT NULL,
  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
  `LAST_UPDATED` datetime DEFAULT NULL,
  PRIMARY KEY (`JOB_EXECUTION_ID`),
  KEY `JOB_INST_EXEC_FK` (`JOB_INSTANCE_ID`),
  CONSTRAINT `JOB_INST_EXEC_FK` FOREIGN KEY (`JOB_INSTANCE_ID`) REFERENCES `BATCH_JOB_INSTANCE` (`JOB_INSTANCE_ID`)
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
  CONSTRAINT `JOB_EXEC_CTX_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
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
  CONSTRAINT `JOB_EXEC_PARAMS_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_JOB_EXECUTION_SEQ`
--

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_EXECUTION_SEQ` (
  `ID` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
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
  UNIQUE KEY `JOB_INST_UN` (`JOB_NAME`,`JOB_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_JOB_SEQ`
--

DROP TABLE IF EXISTS `BATCH_JOB_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_SEQ` (
  `ID` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
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
  `EXIT_CODE` varchar(100) DEFAULT NULL,
  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
  `LAST_UPDATED` datetime DEFAULT NULL,
  PRIMARY KEY (`STEP_EXECUTION_ID`),
  KEY `JOB_EXEC_STEP_FK` (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_STEP_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
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
  CONSTRAINT `STEP_EXEC_CTX_FK` FOREIGN KEY (`STEP_EXECUTION_ID`) REFERENCES `BATCH_STEP_EXECUTION` (`STEP_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_STEP_EXECUTION_SEQ`
--

DROP TABLE IF EXISTS `BATCH_STEP_EXECUTION_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_STEP_EXECUTION_SEQ` (
  `ID` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
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
  UNIQUE KEY `ProviderConnection` (`providerId`,`providerUserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
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
  PRIMARY KEY (`id`),
  KEY `FKBB979BF4F3A96AD8` (`user_id`),
  CONSTRAINT `FKBB979BF4F3A96AD8` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `business_metrics`
--

DROP TABLE IF EXISTS `business_metrics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `business_metrics` (
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
  CONSTRAINT `fk_business_metrics_job_execution_id` FOREIGN KEY (`metrics_execution_id`) REFERENCES `metrics_job_execution` (`id`),
  CONSTRAINT `fk_business_metrics_listing_id` FOREIGN KEY (`listing_id`) REFERENCES `listing` (`id`),
  CONSTRAINT `fk_business_metrics_seller_id` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `content_file`
--

DROP TABLE IF EXISTS `content_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `content_file` (
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
  CONSTRAINT `FK_9o375mrn5drc4g1199c1ym8hp` FOREIGN KEY (`listing_id`) REFERENCES `listing` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `download_instance`
--

DROP TABLE IF EXISTS `download_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `download_instance` (
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
  CONSTRAINT `fk_download_instance_content_file_id` FOREIGN KEY (`content_file_id`) REFERENCES `content_file` (`id`),
  CONSTRAINT `fk_download_instance_listing_id` FOREIGN KEY (`listing_id`) REFERENCES `listing` (`id`),
  CONSTRAINT `fk_download_instance_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `effective_payment_status`
--

DROP TABLE IF EXISTS `effective_payment_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `effective_payment_status` (
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
  CONSTRAINT `FK_2ku6t5q0t9vvm4v9uhag2yqpk` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `facebook_update_notification`
--

DROP TABLE IF EXISTS `facebook_update_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `facebook_update_notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `facebook_page_id` varchar(255) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `batch_processor` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=241 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `listing`
--

DROP TABLE IF EXISTS `listing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `listing` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(15,5) DEFAULT NULL,
  `contentFilename` varchar(255) DEFAULT NULL,
  `digitalContent` longblob,
  `image` longblob,
  `imageFilename` varchar(255) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `seller_id` bigint(20) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(255) DEFAULT NULL,
  `imageHeight` int(11) DEFAULT NULL,
  `imageWidth` int(11) DEFAULT NULL,
  `currencyCode` varchar(255) DEFAULT NULL,
  `remainingQuantity` int(11) DEFAULT NULL,
  `locale` varchar(10) DEFAULT 'en_US',
  `keyword` varchar(255) DEFAULT NULL,
  `announcementPreamble` varchar(255) DEFAULT NULL,
  `backgroundImageUrl` varchar(512) DEFAULT NULL,
  `digitalContentOriginalFilename` varchar(512) DEFAULT NULL,
  `digitalContentType` varchar(255) DEFAULT NULL,
  `expires` datetime NOT NULL,
  `giveawaySeed` int(11) DEFAULT NULL,
  `longDescription` varchar(255) DEFAULT NULL,
  `batch_processor` varchar(255) DEFAULT NULL,
  `subType` varchar(255) DEFAULT NULL,
  `doPostToFacebook` bit(1) DEFAULT NULL,
  `doPostToTwitter` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `contentFilename` (`contentFilename`),
  UNIQUE KEY `imageFilename` (`imageFilename`),
  KEY `FKAD8BA843FB2D238` (`seller_id`),
  CONSTRAINT `FKAD8BA843FB2D238` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33101 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `listing_state_change`
--

DROP TABLE IF EXISTS `listing_state_change`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `listing_state_change` (
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
  CONSTRAINT `FK_listing` FOREIGN KEY (`listing_id`) REFERENCES `listing` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) DEFAULT NULL,
  `twitterId` varchar(255) DEFAULT NULL,
  `inResponseToTwitterId` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `listing_id` bigint(20) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `providerId` varchar(255) DEFAULT NULL,
  `providerUserId` varchar(255) DEFAULT NULL,
  `batch_processor` varchar(255) DEFAULT NULL,
  `containsKeyword` tinyint(1) DEFAULT NULL,
  `embeded_provider_content` varchar(512) DEFAULT NULL,
  `isNaturalReply` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `twitterId` (`twitterId`),
  KEY `FK38EB00079128426C` (`user_id`),
  KEY `FK38EB0007C1D197C` (`listing_id`),
  KEY `inResponseToTwitterId` (`inResponseToTwitterId`),
  KEY `ix_status_provider_user` (`status`,`providerId`,`providerUserId`),
  CONSTRAINT `FK38EB00079128426C` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK38EB0007C1D197C` FOREIGN KEY (`listing_id`) REFERENCES `listing` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2440 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `message_state_change`
--

DROP TABLE IF EXISTS `message_state_change`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message_state_change` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` bigint(20) DEFAULT NULL,
  `previousState` varchar(255) DEFAULT NULL,
  `newState` varchar(255) DEFAULT NULL,
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
  CONSTRAINT `FK_w2wuby6onu0hetgq1fiq35a2` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=628 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `metrics_job_execution`
--

DROP TABLE IF EXISTS `metrics_job_execution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `metrics_job_execution` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `end_time` (`end_time`)
) ENGINE=InnoDB AUTO_INCREMENT=3667 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `payKey` varchar(255) DEFAULT NULL,
  `correlationId` varchar(255) DEFAULT NULL,
  `payer_id` bigint(20) DEFAULT NULL,
  `amount` decimal(15,5) DEFAULT NULL,
  `currencyCode` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `listing_id` bigint(20) DEFAULT NULL,
  `message_id` bigint(20) DEFAULT NULL,
  `payee_id` bigint(20) DEFAULT NULL,
  `shippingAddress_id` bigint(20) DEFAULT NULL,
  `rodLaverAmount` decimal(15,5) DEFAULT NULL,
  `sellerAmount` decimal(15,5) DEFAULT NULL,
  `has_been_shipped` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKD11C3206EBEC56A2` (`payer_id`),
  KEY `FKD11C3206FC6E89E4` (`id`),
  KEY `FKD11C3206C1D197C` (`listing_id`),
  KEY `FKD11C32064E67963B` (`payee_id`),
  KEY `FKD11C32069A76869C` (`message_id`),
  KEY `FKD11C320694547FAA` (`shippingAddress_id`),
  CONSTRAINT `FKD11C32064E67963B` FOREIGN KEY (`payee_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKD11C320694547FAA` FOREIGN KEY (`shippingAddress_id`) REFERENCES `address` (`id`),
  CONSTRAINT `FKD11C32069A76869C` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`),
  CONSTRAINT `FKD11C3206C1D197C` FOREIGN KEY (`listing_id`) REFERENCES `listing` (`id`),
  CONSTRAINT `FKD11C3206EBEC56A2` FOREIGN KEY (`payer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `paypal_status_update`
--

DROP TABLE IF EXISTS `paypal_status_update`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paypal_status_update` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ipnValidated` bit(1) DEFAULT NULL,
  `mapData` varchar(2048) DEFAULT NULL,
  `transactionType` varchar(255) DEFAULT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `paymentKey` varchar(255) DEFAULT NULL,
  `correlationId` varchar(255) DEFAULT NULL,
  `updatedPaymentStatus` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `batch_processor` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `preapproval`
--

DROP TABLE IF EXISTS `preapproval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `preapproval` (
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
  CONSTRAINT `FKB267C5E69128426C` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reply_paging_status`
--

DROP TABLE IF EXISTS `reply_paging_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reply_paging_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `providerId` varchar(255) NOT NULL,
  `providerContentId` varchar(255) NOT NULL,
  `last_page_retrieved` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `listing_message_id` bigint(20) NOT NULL,
  `batch_processor` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_message_id` (`listing_message_id`),
  CONSTRAINT `fk_message_id` FOREIGN KEY (`listing_message_id`) REFERENCES `message` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
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
-- Table structure for table `sent_outbound_communications`
--

DROP TABLE IF EXISTS `sent_outbound_communications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sent_outbound_communications` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `provider_id` varchar(255) DEFAULT NULL,
  `sent_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `type` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sent_outbound_communications_username_fk` (`username`),
  CONSTRAINT `sent_outbound_communications_username_fk` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `terms_of_service`
--

DROP TABLE IF EXISTS `terms_of_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `terms_of_service` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `internal_version` int(11) DEFAULT NULL,
  `is_for_buyers` tinyint(1) DEFAULT NULL,
  `is_for_sellers` tinyint(1) DEFAULT NULL,
  `public_name` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `emailAddress` varchar(255) DEFAULT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `mobile_number` varchar(255) DEFAULT NULL,
  `is_mobile_verified` tinyint(1) DEFAULT NULL,
  `facebook_album_id` varchar(255) DEFAULT NULL,
  `facebook_page_id` varchar(255) DEFAULT NULL,
  `has_accepted_current_buyer_tos` bit(1) DEFAULT NULL,
  `is_user_blocked` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `users_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL,
  PRIMARY KEY (`users_id`,`roles_id`),
  KEY `FK_5k3dviices5fr7560hvc81x4r` (`roles_id`),
  KEY `FK_4edf6ibqo9873ixvuyri8xua2` (`users_id`),
  CONSTRAINT `FK_4edf6ibqo9873ixvuyri8xua2` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_5k3dviices5fr7560hvc81x4r` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_terms_of_service`
--

DROP TABLE IF EXISTS `user_terms_of_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_terms_of_service` (
  `users_id` bigint(20) NOT NULL,
  `acceptedTermsOfService_id` bigint(20) NOT NULL,
  PRIMARY KEY (`users_id`,`acceptedTermsOfService_id`),
  KEY `FK_rkwkfgjfex9629r5nqopowi0c` (`acceptedTermsOfService_id`),
  KEY `FK_8fx1w5tmksfkskwrcok3ny8i5` (`users_id`),
  CONSTRAINT `FK_8fx1w5tmksfkskwrcok3ny8i5` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_rkwkfgjfex9629r5nqopowi0c` FOREIGN KEY (`acceptedTermsOfService_id`) REFERENCES `terms_of_service` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-05-06 16:51:58