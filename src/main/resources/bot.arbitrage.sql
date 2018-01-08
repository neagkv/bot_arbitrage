CREATE DATABASE  IF NOT EXISTS `bot.arbitrage` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `bot.arbitrage`;
-- MySQL dump 10.13  Distrib 5.6.17, for osx10.6 (i386)
--
-- Host: 127.0.0.1    Database: bot.arbitrage
-- ------------------------------------------------------
-- Server version	5.7.19

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
-- Table structure for table `email_info`
--

DROP TABLE IF EXISTS `email_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `TimeOfCall` datetime NOT NULL,
  `EmailAddress` varchar(45) COLLATE utf8_bin NOT NULL DEFAULT 'Email_address',
  `Type` varchar(45) COLLATE utf8_bin NOT NULL DEFAULT 'arbitrage',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_info`
--

LOCK TABLES `email_info` WRITE;
/*!40000 ALTER TABLE `email_info` DISABLE KEYS */;
INSERT INTO `email_info` VALUES (1,'2018-01-03 18:12:24','Email_address','arbitrage'),(2,'2018-01-03 18:13:40','Email_address','arbitrage'),(3,'2018-01-03 18:14:55','Email_address','arbitrage'),(4,'2018-01-03 18:53:11','Email_address','arbitrage'),(5,'2018-01-03 20:58:07','Email_address','2018-01-03 20:58:07'),(6,'2018-01-03 21:08:37','Email_address','2018-01-03 21:08:37'),(7,'2018-01-03 21:17:56','Email_address','2018-01-03 21:17:56'),(8,'2018-01-03 21:26:57','Email_address','2018-01-03 21:26:57'),(9,'2018-01-03 21:36:11','Email_address','arbitrage'),(10,'2018-01-04 09:54:41','Email_address','arbitrage'),(11,'2018-01-04 10:05:16','Email_address','arbitrage'),(12,'2018-01-04 10:05:55','Email_address','arbitrage'),(13,'2018-01-04 10:07:55','Email_address','arbitrage'),(14,'2018-01-04 10:09:48','Email_address','arbitrage'),(15,'2018-01-04 10:31:28','Email_address','2018-01-04 10:31:28'),(16,'2018-01-04 11:22:51','Email_address','arbitrage'),(17,'2018-01-04 12:34:38','Email_address','arbitrage'),(18,'2018-01-04 13:09:09','Email_address','arbitrage'),(19,'2018-01-04 13:12:35','Email_address','arbitrage'),(20,'2018-01-04 13:13:54','Email_address','arbitrage'),(21,'2018-01-04 21:22:32','2018-01-04 21:22:32','arbitrage'),(22,'2018-01-04 21:30:15','neagkv@gmail.com','Arbitrage Update'),(23,'2018-01-05 10:15:37','neagkv@gmail.com','Arbitrage Update'),(24,'2018-01-05 14:32:30','neagkv@gmail.com','Arbitrage Update'),(25,'2018-01-05 15:27:34','neagkv@gmail.com','Arbitrage Update'),(26,'2018-01-05 15:40:21','neagkv@gmail.com','Arbitrage Update'),(27,'2018-01-05 17:48:42','neagkv@gmail.com','Arbitrage Update'),(28,'2018-01-05 18:18:43','neagkv@gmail.com','Arbitrage Update');
/*!40000 ALTER TABLE `email_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-05 20:20:17
