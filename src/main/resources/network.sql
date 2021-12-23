-- MySQL dump 10.13  Distrib 8.0.27, for macos11 (arm64)
--
-- Host: localhost    Database: network
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `script_commands`
--

DROP TABLE IF EXISTS `script_commands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `script_commands` (
  `script_file_id` int NOT NULL,
  `router_name` varchar(50) DEFAULT NULL,
  `command_order` int NOT NULL,
  `command` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `script_commands`
--

LOCK TABLES `script_commands` WRITE;
/*!40000 ALTER TABLE `script_commands` DISABLE KEYS */;
INSERT INTO `script_commands` VALUES (9,'routerA',1,'command11'),(9,'routerA',2,'command12'),(9,'routerA',3,'command15'),(9,'routerA',4,'command14'),(9,'routerB',1,'command21'),(9,'routerB',2,'command22'),(9,'routerB',3,'command23'),(9,'routerB',4,'command24'),(9,'routerC',1,'command31'),(9,'routerC',2,'command32'),(9,'routerC',3,'command33'),(9,'routerC',4,'command34'),(10,'routerA',1,'command11'),(10,'routerA',2,'command12'),(10,'routerA',3,'command15'),(10,'routerA',4,'command14'),(10,'routerB',1,'command21'),(10,'routerB',2,'command22'),(10,'routerB',3,'command23'),(10,'routerB',4,'command24'),(10,'routerC',1,'command31'),(10,'routerC',2,'command32'),(10,'routerC',3,'command33'),(10,'routerC',4,'command34');
/*!40000 ALTER TABLE `script_commands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scripts`
--

DROP TABLE IF EXISTS `scripts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scripts` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `file_name` varchar(50) NOT NULL,
  `uselessInfo` mediumtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scripts`
--

LOCK TABLES `scripts` WRITE;
/*!40000 ALTER TABLE `scripts` DISABLE KEYS */;
INSERT INTO `scripts` VALUES (9,'test2','a'),(10,'test3','a');
/*!40000 ALTER TABLE `scripts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-23 20:12:41
