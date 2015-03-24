-- MySQL dump 10.13  Distrib 5.6.10, for osx10.8 (x86_64)
--
-- Host: localhost    Database: hootit_batch
-- ------------------------------------------------------
-- Server version	5.6.10-log

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
-- Dumping data for table `BATCH_JOB_EXECUTION`
--

LOCK TABLES `BATCH_JOB_EXECUTION` WRITE;
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION` DISABLE KEYS */;
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `BATCH_JOB_EXECUTION_CONTEXT`
--

LOCK TABLES `BATCH_JOB_EXECUTION_CONTEXT` WRITE;
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION_CONTEXT` DISABLE KEYS */;
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION_CONTEXT` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `BATCH_JOB_EXECUTION_PARAMS`
--

LOCK TABLES `BATCH_JOB_EXECUTION_PARAMS` WRITE;
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION_PARAMS` DISABLE KEYS */;
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION_PARAMS` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `BATCH_JOB_EXECUTION_SEQ`
--

LOCK TABLES `BATCH_JOB_EXECUTION_SEQ` WRITE;
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION_SEQ` DISABLE KEYS */;
INSERT INTO `BATCH_JOB_EXECUTION_SEQ` VALUES (0);
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `BATCH_JOB_INSTANCE`
--

LOCK TABLES `BATCH_JOB_INSTANCE` WRITE;
/*!40000 ALTER TABLE `BATCH_JOB_INSTANCE` DISABLE KEYS */;
/*!40000 ALTER TABLE `BATCH_JOB_INSTANCE` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `BATCH_JOB_SEQ`
--

LOCK TABLES `BATCH_JOB_SEQ` WRITE;
/*!40000 ALTER TABLE `BATCH_JOB_SEQ` DISABLE KEYS */;
INSERT INTO `BATCH_JOB_SEQ` VALUES (0);
/*!40000 ALTER TABLE `BATCH_JOB_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BATCH_STAGING`
--

DROP TABLE IF EXISTS `BATCH_STAGING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_STAGING` (
  `ID` bigint(20) NOT NULL,
  `JOB_ID` bigint(20) NOT NULL,
  `VALUE` blob NOT NULL,
  `PROCESSED` char(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BATCH_STAGING`
--

LOCK TABLES `BATCH_STAGING` WRITE;
/*!40000 ALTER TABLE `BATCH_STAGING` DISABLE KEYS */;
/*!40000 ALTER TABLE `BATCH_STAGING` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BATCH_STAGING_SEQ`
--

DROP TABLE IF EXISTS `BATCH_STAGING_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_STAGING_SEQ` (
  `ID` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BATCH_STAGING_SEQ`
--

LOCK TABLES `BATCH_STAGING_SEQ` WRITE;
/*!40000 ALTER TABLE `BATCH_STAGING_SEQ` DISABLE KEYS */;
INSERT INTO `BATCH_STAGING_SEQ` VALUES (0);
/*!40000 ALTER TABLE `BATCH_STAGING_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `BATCH_STEP_EXECUTION`
--

LOCK TABLES `BATCH_STEP_EXECUTION` WRITE;
/*!40000 ALTER TABLE `BATCH_STEP_EXECUTION` DISABLE KEYS */;
/*!40000 ALTER TABLE `BATCH_STEP_EXECUTION` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `BATCH_STEP_EXECUTION_CONTEXT`
--

LOCK TABLES `BATCH_STEP_EXECUTION_CONTEXT` WRITE;
/*!40000 ALTER TABLE `BATCH_STEP_EXECUTION_CONTEXT` DISABLE KEYS */;
/*!40000 ALTER TABLE `BATCH_STEP_EXECUTION_CONTEXT` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `BATCH_STEP_EXECUTION_SEQ`
--

LOCK TABLES `BATCH_STEP_EXECUTION_SEQ` WRITE;
/*!40000 ALTER TABLE `BATCH_STEP_EXECUTION_SEQ` DISABLE KEYS */;
INSERT INTO `BATCH_STEP_EXECUTION_SEQ` VALUES (0);
/*!40000 ALTER TABLE `BATCH_STEP_EXECUTION_SEQ` ENABLE KEYS */;
UNLOCK TABLES;



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
  UNIQUE KEY `UserConnectionRank` (`userId`,`providerId`,`rank`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserConnection`
--

LOCK TABLES `UserConnection` WRITE;
/*!40000 ALTER TABLE `UserConnection` DISABLE KEYS */;
INSERT INTO `UserConnection` VALUES ('twitter/hockingbird','twitter','587581558',1,'@hockingbird','http://twitter.com/hockingbird','http://a0.twimg.com/profile_images/2542244229/35x7pgw1ra8v8ve8hgmu_normal.png','587581558-vj4VpHN0HlAZdqoXc4WTA0FiYgEG5yrJhUAv7koR','KRZxvGSRZs9Umn5bMVaeyXOI0DKcbAOCSJLcpDJ7M',NULL,NULL);
INSERT INTO `UserConnection` VALUES ('twitter/regtestjoe','twitter','606404812',1,'@regtestjoe','http://twitter.com/regtestjoe','http://a0.twimg.com/profile_images/2397949319/114gndpljqj20zdxdktd_normal.jpeg','606404812-V9b72FP4xKfpP5CODkFHv4MAus1GQtuc24CEtMtM','yCboLk2FdJgNZ9vGblzghdvNc6U91QswQCAH3JaI0',NULL,NULL);
INSERT INTO `UserConnection` VALUES ('twitter/regtestnancy','twitter','606486210',1,'@regtestnancy','http://twitter.com/regtestnancy','http://a0.twimg.com/profile_images/2397951177/jl9ghr86640wohahmyjx_normal.jpeg','606486210-fZ44SQZMHphOQ4TqKKGftRT2CemvOWmqSBGINQkm','Jz79phggovVfTjVuKgsNKzv7dG9AMrk0a3tSkwnJtqo',NULL,NULL);
INSERT INTO `UserConnection` VALUES ('twitter/regtestduff','twitter','606555333',1,'@regtestduff','http://twitter.com/regtestnancy','http://a0.twimg.com/profile_images/2397951177/jl9ghr86640wohahmyjx_normal.jpeg','some-key','some-secret',NULL,NULL);

/*!40000 ALTER TABLE `UserConnection` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (7,'Albuquerque','123 Main St','','NM','','87111','2013-07-10 18:36:45.000000','2013-08-04 13:27:12.000000',0,31,NULL,NULL);
INSERT INTO `address` VALUES (8,'Albuquerque','1234 Main St.',NULL,'NM',NULL,'87111','2013-08-04 13:46:19.000000','2013-08-04 18:14:39.414913',6,32,'',NULL);
INSERT INTO `address` VALUES (9,'Seattle','4321 Pine St.',NULL,'WA',NULL,'98109','2013-08-04 13:46:53.000000','2013-08-04 17:58:56.337882',4,32,NULL,NULL);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `effective_payment_status`
--

LOCK TABLES `effective_payment_status` WRITE;
/*!40000 ALTER TABLE `effective_payment_status` DISABLE KEYS */;
INSERT INTO `effective_payment_status` VALUES (1,'2013-07-19 19:57:28','4500-02-01 00:00:00','PAYMENT_PROCESSING',1,'2013-07-19 19:57:28','2013-07-19 19:57:28',0);
INSERT INTO `effective_payment_status` VALUES (2,'2013-07-19 19:57:28','4500-02-01 00:00:00','PAYMENT_PROCESSING',2,'2013-07-19 19:57:28','2013-07-19 19:57:28',0);
INSERT INTO `effective_payment_status` VALUES (3,'2013-07-19 19:57:28','4500-02-01 00:00:00','PAYMENT_PROCESSING',3,'2013-07-19 19:57:28','2013-07-19 19:57:28',0);
INSERT INTO `effective_payment_status` VALUES (4,'2013-07-19 19:57:28','4500-02-01 00:00:00','PAYMENT_PROCESSING',4,'2013-07-19 19:57:28','2013-07-19 19:57:28',0);
/*!40000 ALTER TABLE `effective_payment_status` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `contentFilename` (`contentFilename`),
  UNIQUE KEY `imageFilename` (`imageFilename`),
  KEY `FKAD8BA843FB2D238` (`seller_id`),
  CONSTRAINT `FKAD8BA843FB2D238` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33050 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listing`
--

LOCK TABLES `listing` WRITE;
/*!40000 ALTER TABLE `listing` DISABLE KEYS */;
INSERT INTO `listing` VALUES (1,1.00000,NULL,NULL,NULL,'image1.jpeg',1,'Active DIGITAL Listing','DIGITAL',0,32,'2013-08-05 13:58:57.025463','2013-08-05 13:58:57.025463','ACTIVE',200,200,'USD',1,'en_US','BUY');
INSERT INTO `listing` VALUES (2,1.00000,NULL,NULL,NULL,'image2.jpeg',1,'Canceled DIGITAL Listing','DIGITAL',0,32,'2013-08-05 13:58:57.025463','2013-08-05 13:58:57.025463','CANCELED',200,200,'USD',1,'en_US','BUY');
INSERT INTO `listing` VALUES (3,1.00000,NULL,NULL,NULL,'image3.jpeg',1,'Completed DIGITAL Listing','DIGITAL',0,32,'2013-08-05 13:58:57.025463','2013-08-05 13:58:57.025463','COMPLETED',200,200,'USD',0,'en_US','BUY');
INSERT INTO `listing` VALUES (4,1.00000,NULL,NULL,NULL,'image4.jpeg',1,'Active PHYSICAL Listing','PHYSICAL',0,32,'2013-08-05 13:58:57.025463','2013-08-05 13:58:57.025463','ACTIVE',200,200,'USD',1,'en_US','BUY');
INSERT INTO `listing` VALUES (5,1.00000,NULL,NULL,NULL,'image5.jpeg',1,'Canceled PHYSICAL Listing','PHYSICAL',0,32,'2013-08-05 13:58:57.025463','2013-08-05 13:58:57.025463','CANCELED',200,200,'USD',1,'en_US','BUY');
INSERT INTO `listing` VALUES (6,1.00000,NULL,NULL,NULL,'image6.jpeg',1,'Completed PHYSICAL Listing','PHYSICAL',0,32,'2013-08-05 13:58:57.025463','2013-08-05 13:58:57.025463','COMPLETED',200,200,'USD',0,'en_US','BUY');
INSERT INTO `listing` VALUES (7,1.00000,NULL,NULL,NULL,'image7.jpeg',1,'Active DONATION Listing','DONATION',0,32,'2013-08-05 13:58:57.025463','2013-08-05 13:58:57.025463','ACTIVE',200,200,'USD',1,'en_US','DONATE');
INSERT INTO `listing` VALUES (8,1.00000,NULL,NULL,NULL,'image8.jpeg',1,'Canceled DONATION Listing','DONATION',0,32,'2013-08-05 13:58:57.025463','2013-08-05 13:58:57.025463','CANCELED',200,200,'USD',1,'en_US','DONATE');
INSERT INTO `listing` VALUES (9,1.00000,NULL,NULL,NULL,'image9.jpeg',1,'Completed DONATION Listing','DONATION',0,32,'2013-08-05 13:58:57.025463','2013-08-05 13:58:57.025463','COMPLETED',200,200,'USD',0,'en_US','DONATE');
INSERT INTO `listing` VALUES (10,1.00000,NULL,NULL,NULL,'image10.jpeg',1,'Active CAMPAIGN Listing','CAMPAIGN',0,32,'2013-08-05 13:58:57.025463','2013-08-05 13:58:57.025463','ACTIVE',200,200,'USD',1,'en_US','YES');
INSERT INTO `listing` VALUES (11,1.00000,NULL,NULL,NULL,'image11.jpeg',1,'Canceled CAMPAIGN Listing','CAMPAIGN',0,32,'2013-08-05 13:58:57.025463','2013-08-05 13:58:57.025463','CANCELED',200,200,'USD',1,'en_US','YES');
INSERT INTO `listing` VALUES (12,1.00000,NULL,NULL,NULL,'image12.jpeg',1,'Completed CAMPAIGN Listing','CAMPAIGN',0,32,'2013-08-05 13:58:57.025463','2013-08-05 13:58:57.025463','COMPLETED',200,200,'USD',0,'en_US','YES');

/*!40000 ALTER TABLE `listing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) DEFAULT NULL,
  `twitterId` bigint(20) DEFAULT NULL,
  `inResponseToTwitterId` bigint(20) DEFAULT NULL,
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `twitterId` (`twitterId`),
  KEY `FK38EB00079128426C` (`user_id`),
  KEY `FK38EB0007C1D197C` (`listing_id`),
  KEY `inResponseToTwitterId` (`inResponseToTwitterId`),
  KEY `ix_status_provider_user` (`status`,`providerId`,`providerUserId`),
  CONSTRAINT `FK38EB00079128426C` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK38EB0007C1D197C` FOREIGN KEY (`listing_id`) REFERENCES `listing` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2012 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,'Active DIGITAL listing',500,NULL,32,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,1,'COMPLETED','twitter','606486210',NULL,NULL);
INSERT INTO `message` VALUES (2,'Canceled DIGITAL listing',501,NULL,32,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,2,'COMPLETED','twitter','606486210',NULL,NULL);
INSERT INTO `message` VALUES (3,'Completed DIGITAL listing',502,NULL,32,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,3,'COMPLETED','twitter','606486210',NULL,NULL);
INSERT INTO `message` VALUES (4,'Active PHYSICAL listing',503,NULL,32,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,4,'COMPLETED','twitter','606486210',NULL,NULL);
INSERT INTO `message` VALUES (5,'Canceled PHYSICAL listing',504,NULL,32,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,5,'COMPLETED','twitter','606486210',NULL,NULL);
INSERT INTO `message` VALUES (6,'Completed PHYSICAL listing',505,NULL,32,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,6,'COMPLETED','twitter','606486210',NULL,NULL);
INSERT INTO `message` VALUES (7,'Active DONATION listing',506,NULL,32,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,7,'COMPLETED','twitter','606486210',NULL,NULL);
INSERT INTO `message` VALUES (8,'Canceled DONATION listing',507,NULL,32,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,8,'COMPLETED','twitter','606486210',NULL,NULL);
INSERT INTO `message` VALUES (9,'Completed DONATION listing',508,NULL,32,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,9,'COMPLETED','twitter','606486210',NULL,NULL);
INSERT INTO `message` VALUES (10,'Active CAMPAIGN listing',509,NULL,32,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,10,'COMPLETED','twitter','606486210',NULL,NULL);
INSERT INTO `message` VALUES (11,'Canceled CAMPAIGN listing',510,NULL,32,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,11,'COMPLETED','twitter','606486210',NULL,NULL);
INSERT INTO `message` VALUES (12,'Completed CAMPAIGN listing',511,NULL,32,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,12,'COMPLETED','twitter','606486210',NULL,NULL);

INSERT INTO `message` VALUES (13,'Irrelevant Reply to Active DIGITAL listing',600,500,32,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','606555333',NULL,NULL);
INSERT INTO `message` VALUES (14,'Irrelevant Reply to Canceled DIGITAL listing',601,501,34,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','606555333',NULL,NULL);
INSERT INTO `message` VALUES (15,'Irrelevant Reply to Completed DIGITAL listing',602,502,34,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','606555333',NULL,NULL);
INSERT INTO `message` VALUES (16,'Irrelevant Reply to Active PHYSICAL listing',603,503,34,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','606555333',NULL,NULL);
INSERT INTO `message` VALUES (17,'Irrelevant Reply to Canceled PHYSICAL listing',604,504,34,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','606555333',NULL,NULL);
INSERT INTO `message` VALUES (18,'Irrelevant Reply to Completed PHYSICAL listing',605,505,34,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','606555333',NULL,NULL);
INSERT INTO `message` VALUES (19,'Irrelevant Reply to Active DONATION listing',606,506,34,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','606555333',NULL,NULL);
INSERT INTO `message` VALUES (20,'Irrelevant Reply to Canceled DONATION listing',607,507,34,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','606555333',NULL,NULL);
INSERT INTO `message` VALUES (21,'Irrelevant Reply to Completed DONATION listing',608,508,34,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','606555333',NULL,NULL);
INSERT INTO `message` VALUES (22,'Irrelevant Reply to Active CAMPAIGN listing',609,509,34,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','606555333',NULL,NULL);
INSERT INTO `message` VALUES (23,'Irrelevant Reply to Canceled CAMPAIGN listing',610,510,34,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','606555333',NULL,NULL);
INSERT INTO `message` VALUES (24,'Irrelevant Reply to Completed CAMPAIGN listing',611,511,34,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','606555333',NULL,NULL);

INSERT INTO `message` VALUES (30,'From unregistered - expect pending registration - BUY',620,500,NULL,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','111111111',NULL,1);
INSERT INTO `message` VALUES (31,'From unregistered - expect pending registration - BUY',621,503,NULL,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','111111111',NULL,1);
INSERT INTO `message` VALUES (32,'From unregistered - expect pending registration - DONATE',622,506,NULL,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','111111111',NULL,1);
INSERT INTO `message` VALUES (33,'From unregistered - expect pending registration - YES',623,509,NULL,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','111111111',NULL,1);
INSERT INTO `message` VALUES (34,'From unregistered - expect pending registration - YES',624,510,NULL,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','111111111',NULL,1);
INSERT INTO `message` VALUES (35,'From unregistered - expect pending registration - YES',625,511,NULL,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','111111111',NULL,1);

INSERT INTO `message` VALUES (40,'From a no-preapproval - Active DIGITAL listing - expect pending preapproval - BUY',630,500,31,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','606404812',NULL,1);
INSERT INTO `message` VALUES (41,'From a no-preapproval - Active PHYSICAL listing - expect pending preapproval - BUY',631,503,31,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','606404812',NULL,1);
INSERT INTO `message` VALUES (42,'From a no-preapproval - Active DONATION listing - expect pending preapproval - DONATE',632,506,31,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','606404812',NULL,1);
INSERT INTO `message` VALUES (43,'From a no-preapproval - Active CAMPAIGN listing - expect complete - YES',633,509,31,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','606404812',NULL,1);
INSERT INTO `message` VALUES (44,'From a no-preapproval - Completed CAMPAIGN listing - expect failed cancelled - YES',634,510,31,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','606404812',NULL,1);
INSERT INTO `message` VALUES (45,'From a no-preapproval - Canceled CAMPAIGN listing - expect failed completed - YES',635,511,31,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PROCESSING','twitter','606404812',NULL,1);

INSERT INTO `message` VALUES (59,'Completed CAMPAIGN OPT-IN - should cause another to be marked duplicate',626,509,31,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'COMPLETED','twitter','606404812',NULL,1);

INSERT INTO `message` VALUES (60,'Unblocked pending reg w/no-preapproval - Active DIGITAL listing - expect pending preapproval - BUY',636,500,NULL,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PENDING_USER_REGISTRATION','twitter','606404812',NULL,1);
INSERT INTO `message` VALUES (61,'Unblocked pending reg w/no-preapproval - Active PHYSICAL listing - expect pending preapproval - BUY',637,503,NULL,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PENDING_USER_REGISTRATION','twitter','606404812',NULL,1);
INSERT INTO `message` VALUES (62,'Unblocked pending reg w/no-preapproval - Active DONATION listing - expect pending preapproval - DONATE',638,506,NULL,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PENDING_USER_REGISTRATION','twitter','606404812',NULL,1);
INSERT INTO `message` VALUES (63,'Unblocked pending reg w/no-preapproval - Active CAMPAIGN listing - expect complete - YES',639,509,NULL,'2013-08-05 13:58:56.785331','2013-08-05 13:58:56.785331',0,NULL,'PENDING_USER_REGISTRATION','twitter','606404812',NULL,1);



/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=216 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_state_change`
--

LOCK TABLES `message_state_change` WRITE;
/*!40000 ALTER TABLE `message_state_change` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_state_change` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `payKey` varchar(255) DEFAULT NULL,
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
  `rodLaverAmount` decimal(15,4) DEFAULT NULL,
  `sellerAmount` decimal(15,4) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (4,'AP-6DV88118Y83431923',31,9.20000,'USD',0,'2013-08-15 00:00:00.000000','2013-08-15 00:00:00.000000',33003,1688,32,NULL,0.2000,0.800,NULL);
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

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
  `updatedPaymentStatus` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `batch_processor` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paypal_status_update`
--

LOCK TABLES `paypal_status_update` WRITE;
/*!40000 ALTER TABLE `paypal_status_update` DISABLE KEYS */;
INSERT INTO `paypal_status_update` VALUES (1,'\1','{param1=value1, param2=value2}','PAY','2013-08-15 00:00:00.000000','2013-08-15 00:00:00.000000', 'AP-6DV88118Y83431920', 'COMPLETED', 0, 'PROCESSING',NULL);
INSERT INTO `paypal_status_update` VALUES (2,'\1','{param1=value1, param2=value2}','PAY','2013-08-15 00:00:00.000000','2013-08-15 00:00:00.000000', 'AP-6DV88118Y83431921', 'DENIED', 0, 'PROCESSING',NULL);
INSERT INTO `paypal_status_update` VALUES (3,'\1','{param1=value1, param2=value2}','PAY','2013-08-15 00:00:00.000000','2013-08-15 00:00:00.000000', 'AP-6DV88118Y83431922', 'PENDING', 0, 'PROCESSING',NULL);
INSERT INTO `paypal_status_update` VALUES (4,'\1','{param1=value1, param2=value2}','PAY','2013-08-15 00:00:00.000000','2013-08-15 00:00:00.000000', 'AP-6DV88118Y83431923', 'FAILED', 0, 'PROCESSING',NULL);
/*!40000 ALTER TABLE `paypal_status_update` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `preapprovalKey` (`preapprovalKey`),
  KEY `FKB267C5E69128426C` (`user_id`),
  KEY `status` (`status`),
  CONSTRAINT `FKB267C5E69128426C` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preapproval`
--

LOCK TABLES `preapproval` WRITE;
/*!40000 ALTER TABLE `preapproval` DISABLE KEYS */;
INSERT INTO `preapproval` VALUES (25,'1bcc2c0145279','PA-72K83562T77091701',31,0,'CANCELED','2013-06-17 17:37:49.000000','2013-06-17 17:37:49.000000');
INSERT INTO `preapproval` VALUES (27,'1bad803247a0b','PA-8X6028684P648904G',32,0,'CANCELED','2013-06-20 19:01:53.000000','2013-06-20 19:01:53.000000');
INSERT INTO `preapproval` VALUES (30,'ae9f536229834','PA-41Y25196UN5378344',31,0,'INACTIVE','2013-06-20 19:13:59.000000','2013-06-20 19:13:59.000000');
INSERT INTO `preapproval` VALUES (39,'87f852fc816bc','PA-8J245687P4650772R',33,0,'ACTIVE','2013-07-08 18:32:53.000000','2013-07-08 18:32:53.000000');
INSERT INTO `preapproval` VALUES (40,'bb01c5fb001d0','PA-75C129015Y1701729',31,0,'PENDING','2013-07-09 18:52:22.000000','2013-07-09 18:52:22.000000');
/*!40000 ALTER TABLE `preapproval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `remember_me`
--

DROP TABLE IF EXISTS `remember_me`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `remember_me` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `series` varchar(255) DEFAULT NULL,
  `tokenValue` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `remember_me`
--

LOCK TABLES `remember_me` WRITE;
/*!40000 ALTER TABLE `remember_me` DISABLE KEYS */;
/*!40000 ALTER TABLE `remember_me` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (4,2,'2013-06-04 07:38:36.000000','2013-06-04 07:38:21.000000',1);
INSERT INTO `role` VALUES (5,3,'2013-06-04 09:32:13.000000','2013-06-04 09:32:09.000000',0);
INSERT INTO `role` VALUES (6,1,'2013-06-08 13:51:20.000000','2013-06-08 13:51:20.000000',0);
INSERT INTO `role` VALUES (7,1,'2013-06-08 13:51:20.000000','2013-06-08 13:51:20.000000',0);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `FK36EBCB4E7EA6F8` (`role_id`),
  KEY `FK36EBCBFC6D1E8F` (`id`),
  KEY `FK_qleu8ddawkdltal07p8e6hgva` (`role_id`),
  CONSTRAINT `FK_qleu8ddawkdltal07p8e6hgva` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (31,'Regular','Joe',NULL,'twitter/regtestjoe','2013-06-04 07:38:36.000000','2013-06-04 07:38:21.000000',0,4,'testjoe1@test.com',NULL,NULL,0);
INSERT INTO `user` VALUES (32,'Regular','Nancy',NULL,'twitter/regtestnancy','2013-06-04 09:32:13.000000','2013-06-20 19:14:00.000000',1,5,'testnancy@test.com',NULL,NULL,0);
INSERT INTO `user` VALUES (33,'hockingbird',NULL,NULL,'twitter/hockingbird','2013-06-08 13:51:20.000000','2013-06-08 13:51:20.000000',0,6,NULL,NULL,NULL,0);
INSERT INTO `user` VALUES (34,'Regular','Duff',NULL,'twitter/regtestduff','2013-06-08 13:51:20.000000','2013-06-08 13:51:20.000000',0,7,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-08-03 20:29:12
