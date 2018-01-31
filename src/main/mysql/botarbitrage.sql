CREATE DATABASE  IF NOT EXISTS `botarbitrage` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;
USE `botarbitrage`;
-- MySQL dump 10.13  Distrib 5.6.17, for osx10.6 (i386)
--
-- Host: 127.0.0.1    Database: botarbitrage
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
-- Table structure for table `Detection`
--

DROP TABLE IF EXISTS `Detection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Detection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `currencyPair` varchar(45) COLLATE utf8_bin NOT NULL,
  `Difference` varchar(45) COLLATE utf8_bin NOT NULL,
  `lowAsk` varchar(45) COLLATE utf8_bin NOT NULL,
  `lowAskExchange` varchar(45) COLLATE utf8_bin NOT NULL,
  `highBid` varchar(45) COLLATE utf8_bin NOT NULL,
  `highBidExchange` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Detection`
--

LOCK TABLES `Detection` WRITE;
/*!40000 ALTER TABLE `Detection` DISABLE KEYS */;
/*!40000 ALTER TABLE `Detection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_info`
--

DROP TABLE IF EXISTS `email_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `TimeOfCall` datetime DEFAULT NULL,
  `EmailAddress` varchar(45) COLLATE utf8_bin DEFAULT 'No Email Used',
  `Type` varchar(45) COLLATE utf8_bin DEFAULT 'arbitrage',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_info`
--

LOCK TABLES `email_info` WRITE;
/*!40000 ALTER TABLE `email_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `email_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trades`
--

DROP TABLE IF EXISTS `trades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `baseName` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `counterName` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `buyExchange` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `sellExchange` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `oldTotalBase` double NOT NULL,
  `newTotalBase` double NOT NULL,
  `differenceBaseBuy` double NOT NULL,
  `differenceCounterBuy` double NOT NULL,
  `differenceBasesell` double NOT NULL,
  `differenceCounterSell` double NOT NULL,
  `realAsk` double NOT NULL,
  `realBid` double NOT NULL,
  `realDifference` double NOT NULL,
  `expectedDifferenceFormatted` double NOT NULL,
  `estimatedFee` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trades`
--

LOCK TABLES `trades` WRITE;
/*!40000 ALTER TABLE `trades` DISABLE KEYS */;
/*!40000 ALTER TABLE `trades` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-31 17:41:48
