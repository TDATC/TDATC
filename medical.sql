CREATE DATABASE  IF NOT EXISTS `medical` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `medical`;
-- MySQL dump 10.13  Distrib 5.5.46, for debian-linux-gnu (x86_64)
--
-- Host: 172.16.1.15    Database: medical
-- ------------------------------------------------------
-- Server version	5.5.36

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
-- Table structure for table `patient_appointments`
--

DROP TABLE IF EXISTS `patient_appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_appointments` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `patient_id` int(4) NOT NULL,
  `physician_id` int(4) NOT NULL,
  `timings` varchar(20) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `appointment_description` varchar(500) DEFAULT NULL,
  `status` enum('OPEN','CONFIRMED','CANCELLED','CLOSE') DEFAULT NULL,
  `read_unread` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `appointment_index` (`timings`,`date`,`patient_id`,`physician_id`),
  KEY `patient_fk_idx` (`patient_id`),
  KEY `physician_fk_idx` (`physician_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_appointments`
--

LOCK TABLES `patient_appointments` WRITE;
/*!40000 ALTER TABLE `patient_appointments` DISABLE KEYS */;
INSERT INTO `patient_appointments` VALUES (1,25,30,'23:22:30','2015-08-24','Dendal check up','CONFIRMED',1);
/*!40000 ALTER TABLE `patient_appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `physician_details`
--

DROP TABLE IF EXISTS `physician_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `physician_details` (
  `physician_id` int(4) NOT NULL AUTO_INCREMENT,
  `facebook_Link` varchar(200) DEFAULT NULL,
  `blogger_link` varchar(200) DEFAULT NULL,
  `linkedIn_link` varchar(200) DEFAULT NULL,
  `flikker_link` varchar(200) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `notification_alert` tinyint(1) NOT NULL DEFAULT '1',
  `patient_request_alert` tinyint(1) NOT NULL DEFAULT '1',
  `registration_no` varchar(45) DEFAULT NULL,
  `registration_date` varchar(10) DEFAULT NULL,
  `experience_in_year` int(10) DEFAULT '0',
  `branch` varchar(45) DEFAULT NULL,
  `location_id` int(4) DEFAULT '0',
  `specialization_id` int(4) DEFAULT '0',
  `affiliation_id` int(4) DEFAULT '0',
  `qualification_id` int(4) DEFAULT '0',
  `clinic_id` int(4) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`physician_id`),
  KEY `location_fk_idx` (`location_id`),
  KEY `qualification_fk_idx` (`qualification_id`),
  KEY `clinic_fk_idx` (`clinic_id`),
  KEY `affiliation_fk_idx` (`affiliation_id`),
  KEY `specialization_fk_idx` (`specialization_id`),
  KEY `FK_physician_details` (`user_id`),
  CONSTRAINT `physician_details_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `physician_details`
--

LOCK TABLES `physician_details` WRITE;
/*!40000 ALTER TABLE `physician_details` DISABLE KEYS */;
INSERT INTO `physician_details` VALUES (2,NULL,NULL,NULL,NULL,' surgeon',1,1,'1DA10SURGEON','2003-12-10',30,'surgry',2,2,2,2,2,45),(30,NULL,NULL,NULL,NULL,NULL,0,0,NULL,'12-03-1988',56,NULL,NULL,1,NULL,5,2,30),(46,NULL,NULL,NULL,NULL,NULL,1,1,NULL,'',0,NULL,NULL,NULL,NULL,NULL,NULL,56),(62,NULL,NULL,NULL,NULL,NULL,1,1,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,58),(63,NULL,NULL,NULL,NULL,NULL,1,1,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,58),(64,NULL,NULL,NULL,NULL,NULL,0,0,'1991-06-12','1960',54,NULL,NULL,1,2,5,NULL,58),(65,NULL,NULL,NULL,NULL,NULL,0,1,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,58),(66,NULL,NULL,NULL,NULL,NULL,0,1,'4455',NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,58),(76,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,14,NULL,NULL,1,NULL,1,NULL,30),(77,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,14,NULL,NULL,1,NULL,1,NULL,30),(78,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,11,NULL,NULL,1,NULL,1,NULL,30),(79,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,14,NULL,NULL,1,NULL,1,NULL,30),(80,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,16,NULL,NULL,1,NULL,1,NULL,30),(81,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,16,NULL,NULL,1,NULL,1,NULL,30),(82,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,14,NULL,NULL,1,NULL,1,NULL,30),(84,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,88,NULL,NULL,1,NULL,1,NULL,30),(85,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,88,NULL,NULL,1,NULL,1,NULL,30),(86,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,88,NULL,NULL,1,NULL,1,NULL,30),(87,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,14,NULL,NULL,1,NULL,1,NULL,30),(91,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,233),(92,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,234),(93,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,235),(94,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,236),(95,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,237),(96,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,238),(97,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,239),(98,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,240),(99,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,56,NULL,NULL,1,NULL,5,NULL,30),(100,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,243),(101,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,261);
/*!40000 ALTER TABLE `physician_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `app_parameter`
--

DROP TABLE IF EXISTS `app_parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_parameter` (
  `param_id` varchar(30) NOT NULL,
  `param_group_id` varchar(45) DEFAULT NULL,
  `param_name` varchar(45) NOT NULL,
  `param_desc` varchar(45) DEFAULT NULL,
  `valid_from` datetime DEFAULT NULL,
  `valid_to` datetime DEFAULT NULL,
  `status` char(1) DEFAULT 'A',
  `cr_dt` datetime DEFAULT NULL,
  `up_dt` datetime DEFAULT NULL,
  `cr_by` varchar(45) DEFAULT NULL,
  `up_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`param_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_parameter`
--

LOCK TABLES `app_parameter` WRITE;
/*!40000 ALTER TABLE `app_parameter` DISABLE KEYS */;
/*!40000 ALTER TABLE `app_parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_dietary_details`
--

DROP TABLE IF EXISTS `patient_dietary_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_dietary_details` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `patient_id` int(4) NOT NULL,
  `dietary_biotin` varchar(45) DEFAULT NULL,
  `dietary_caffeine` varchar(45) DEFAULT NULL,
  `dietary_calcium` varchar(45) DEFAULT NULL,
  `dietary_carbohydrates` varchar(45) DEFAULT NULL,
  `dietary_chloride` varchar(45) DEFAULT NULL,
  `dietary_cholesterol` varchar(45) DEFAULT NULL,
  `dietary_chromium` varchar(45) DEFAULT NULL,
  `dietary_copper` varchar(45) DEFAULT NULL,
  `dietary_energy_consumed` varchar(45) DEFAULT NULL,
  `dietary_fat_mono_unsaturated` varchar(45) DEFAULT NULL,
  `date_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `patient_dietary_fk_idx` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_dietary_details`
--

LOCK TABLES `patient_dietary_details` WRITE;
/*!40000 ALTER TABLE `patient_dietary_details` DISABLE KEYS */;
INSERT INTO `patient_dietary_details` VALUES (5,25,'70','200','01','55','300','100','33','69','60','70','2015-08-18 11:20:25');
/*!40000 ALTER TABLE `patient_dietary_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `affiliation`
--

DROP TABLE IF EXISTS `affiliation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `affiliation` (
  `affiliation_id` int(11) NOT NULL DEFAULT '0',
  `university_name` varchar(100) NOT NULL,
  PRIMARY KEY (`affiliation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `affiliation`
--

LOCK TABLES `affiliation` WRITE;
/*!40000 ALTER TABLE `affiliation` DISABLE KEYS */;
INSERT INTO `affiliation` VALUES (1,'Pondicherry University'),(2,'NTR University of Health Sciences, VijayWada'),(3,'Gandhi Institute of Technology and Management (GITAM University) Deemed, Visakhapatnam'),(4,'NTR University of Health Sciences, VijayWada'),(5,'Srimanta Shankardeva University of Health Sciences'),(6,'Magadh University'),(7,'LN Mithila University'),(8,'Aryabhatta Knowledge University, Patna'),(9,'Indira Gandhi Instt of Medical Sciences (Deemed University), Patna'),(10,'Tilkamanshi Bhagalpur University');
/*!40000 ALTER TABLE `affiliation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clinic`
--

DROP TABLE IF EXISTS `clinic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clinic` (
  `clinic_id` int(4) NOT NULL AUTO_INCREMENT,
  `clinic_name` varchar(45) NOT NULL,
  `contact_number` varchar(45) DEFAULT NULL,
  `city` varchar(45) NOT NULL,
  `street_address` varchar(500) DEFAULT NULL,
  `specialization` varchar(45) DEFAULT NULL,
  `consultationfees` bigint(20) NOT NULL,
  `phyId` int(11) DEFAULT NULL,
  `locality` varchar(45) DEFAULT NULL,
  `appointment_duration` int(10) DEFAULT '15',
  `photoPath` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`clinic_id`),
  KEY `phy_fk_idx` (`phyId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clinic`
--

LOCK TABLES `clinic` WRITE;
/*!40000 ALTER TABLE `clinic` DISABLE KEYS */;
INSERT INTO `clinic` VALUES (1,'Good','9964960803','Bangalore','2nd Phase, J P Nagar, 560041','',150,30,'Indira Nagar',0,'1bundle_user_file_name.jpg'),(2,'New','9964960804','Chennai','Jeewan Griha Colony, 2nd Phase, J P Nagar, 560078','',250,30,'Alwerpet',0,'2bundle_user_file_name.jpg');
/*!40000 ALTER TABLE `clinic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_measurement_tools_details`
--

DROP TABLE IF EXISTS `patient_measurement_tools_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_measurement_tools_details` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `measurement_tool_id` int(4) NOT NULL,
  `patient_id` int(14) NOT NULL,
  `measurement_tools_value` varchar(45) DEFAULT NULL,
  `date_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `measurement_tool_fk_idx` (`measurement_tool_id`),
  KEY `patient_fk_idx` (`patient_id`),
  KEY `measurement_fx` (`measurement_tool_id`),
  KEY `patient_fk` (`patient_id`),
  CONSTRAINT `measurement_tools_fk` FOREIGN KEY (`measurement_tool_id`) REFERENCES `measurement_tools` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_measurement_tools_details`
--

LOCK TABLES `patient_measurement_tools_details` WRITE;
/*!40000 ALTER TABLE `patient_measurement_tools_details` DISABLE KEYS */;
INSERT INTO `patient_measurement_tools_details` VALUES (20,1,25,'75','2015-10-10 16:15:06'),(21,1,25,'65','2015-09-09 16:16:23'),(22,1,25,'55','2015-09-08 16:15:25'),(23,3,25,'200','2015-09-04 07:00:00'),(24,3,25,'55','2015-09-04 07:00:00'),(25,3,25,'200','2015-09-04 07:00:00'),(26,1,39,'50','2015-09-07 19:53:07'),(27,1,37,'50','2015-09-07 20:01:18'),(28,1,39,'80','2015-09-10 17:29:33'),(29,1,39,'100','2015-09-10 17:57:38'),(32,2,39,'12','2015-09-12 04:51:02'),(33,2,39,'123','2015-09-12 04:51:32'),(34,2,39,'1551','2015-09-12 03:51:40'),(35,3,39,'122','2015-09-12 03:53:07'),(36,4,39,'055','2015-09-12 03:53:36'),(37,10,39,'80','2015-09-12 03:53:54'),(38,10,39,'','2015-09-12 03:54:06'),(39,7,39,'98','2015-09-12 03:54:24'),(40,7,39,'98','2015-09-12 03:54:24'),(41,8,39,'165','2015-09-12 03:55:33'),(42,5,39,'144','2015-09-16 23:32:33'),(43,5,39,'144','2015-09-16 23:33:00'),(44,5,39,'','2015-09-16 23:33:07'),(45,2,39,'1324','2015-09-16 23:34:11'),(47,6,39,'55','2015-09-21 03:42:09'),(48,2,39,'122','2015-09-21 03:42:31'),(49,6,39,'86','2015-09-27 08:26:58'),(50,6,39,'63','2015-09-27 08:27:03'),(51,6,39,'92','2015-09-27 08:27:07'),(52,6,39,'','2015-09-27 08:27:25'),(53,7,39,'','2015-09-27 08:29:33'),(54,7,39,'99','2015-09-27 08:29:39'),(55,7,39,'99','2015-09-27 08:29:39'),(56,7,39,'99','2015-09-27 08:29:39'),(57,7,39,'99','2015-09-27 08:30:02'),(58,7,39,'99','2015-09-27 08:30:02'),(59,9,39,'10','2015-09-30 22:01:21'),(60,6,39,'','2015-10-01 04:19:34'),(63,1,37,'90','2015-10-11 05:00:59'),(64,1,39,'70','2015-10-11 01:46:20'),(65,1,39,'60','2015-10-11 04:01:13'),(66,8,39,'180','2015-10-11 04:03:46'),(67,1,39,'50','2015-12-16 11:29:30'),(68,1,39,'50','2015-12-16 11:29:30');
/*!40000 ALTER TABLE `patient_measurement_tools_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_body_details`
--

DROP TABLE IF EXISTS `patient_body_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_body_details` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `patient_id` int(4) NOT NULL,
  `dietary_zinc` varchar(45) DEFAULT NULL,
  `glycosylated_hemoglobin` varchar(45) DEFAULT NULL,
  `heart_rate` varchar(45) DEFAULT NULL,
  `height` varchar(45) DEFAULT NULL,
  `LBM` varchar(45) DEFAULT NULL,
  `peak_flow` varchar(45) DEFAULT NULL,
  `peripheral_perfusion` varchar(45) DEFAULT NULL,
  `respiratory_rate` varchar(45) DEFAULT NULL,
  `date_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `body_fk_idx` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_body_details`
--

LOCK TABLES `patient_body_details` WRITE;
/*!40000 ALTER TABLE `patient_body_details` DISABLE KEYS */;
INSERT INTO `patient_body_details` VALUES (2,25,'10%','19%','50%','5.5','60%','60liters/min','0.3-10.0','12-20breaths/min','2015-08-18 13:33:40');
/*!40000 ALTER TABLE `patient_body_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patientphysicianmap`
--

DROP TABLE IF EXISTS `patientphysicianmap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patientphysicianmap` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` int(11) DEFAULT NULL,
  `physician_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_patient` (`patient_id`),
  KEY `FK_patientphysicianmap` (`physician_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patientphysicianmap`
--

LOCK TABLES `patientphysicianmap` WRITE;
/*!40000 ALTER TABLE `patientphysicianmap` DISABLE KEYS */;
INSERT INTO `patientphysicianmap` VALUES (3,39,30),(4,37,30),(5,1,2),(8,20,30),(9,20,30),(10,20,30),(11,20,30),(12,8,9),(14,36,30),(15,55,77),(16,33,66),(18,39,87),(19,39,86);
/*!40000 ALTER TABLE `patientphysicianmap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `city_id` int(11) NOT NULL AUTO_INCREMENT,
  `city_name` varchar(45) DEFAULT NULL,
  `countrycode_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'Bangalore',7),(2,'Chennai',9),(3,'Sacramento',23),(4,'Tallahassee',24),(5,'Albany',25),(6,'Austin',26);
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `save_pdf`
--

DROP TABLE IF EXISTS `save_pdf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `save_pdf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` int(11) DEFAULT NULL,
  `file_name` varchar(45) DEFAULT NULL,
  `uploaded_date` datetime DEFAULT NULL,
  `read_unread` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `save_pdf`
--

LOCK TABLES `save_pdf` WRITE;
/*!40000 ALTER TABLE `save_pdf` DISABLE KEYS */;
INSERT INTO `save_pdf` VALUES (32,39,'example.pdf','2015-10-09 20:47:10',1),(33,39,'pdf.pdf','2015-10-10 23:23:12',1),(34,39,'Getting Started.pdf','2016-01-14 12:54:22',0),(35,39,'Getting Started.pdf','2016-01-14 13:03:04',0);
/*!40000 ALTER TABLE `save_pdf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pharmacy_branches`
--

DROP TABLE IF EXISTS `pharmacy_branches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pharmacy_branches` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `pharmacy_id` int(4) NOT NULL,
  `branch_name` varchar(100) DEFAULT NULL,
  `branch_code` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `state` varchar(100) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `pin_code` varchar(50) DEFAULT NULL,
  `phone_business1` varchar(20) DEFAULT NULL,
  `phone_business2` varchar(45) DEFAULT NULL,
  `fax` varchar(50) DEFAULT NULL,
  `is_active` enum('ACTIVE','INACTIVE') DEFAULT 'ACTIVE',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pharmacy_branch_code_unique` (`branch_code`,`pin_code`,`pharmacy_id`),
  KEY `pharmacy_branch_code_index` (`branch_code`),
  KEY `pharmacy_id_fk_idx` (`pharmacy_id`),
  CONSTRAINT `pharmacy_id_fk` FOREIGN KEY (`pharmacy_id`) REFERENCES `pharmacy` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pharmacy_branches`
--

LOCK TABLES `pharmacy_branches` WRITE;
/*!40000 ALTER TABLE `pharmacy_branches` DISABLE KEYS */;
INSERT INTO `pharmacy_branches` VALUES (4,22,'rbranchao123','rao1234578','ongole','ka','rr','5600','64646',NULL,NULL,NULL),(5,2,'apollo Malleshwaram','AP002','bangalore','Karnataka','india','5600','923','834','2346','ACTIVE'),(6,2,'apollo kormangala','AP003','bangalore','karnataka','india','5600','834','722','213','ACTIVE'),(7,5,'Sagar Mudalpalya','SA100','bangalore','karnataka','india','23344','1222222','1111111','2222','ACTIVE'),(8,5,'Sagar Madiwala','SA102','bangalore','karnataka','india','4444','4444','44444','44444','ACTIVE'),(9,5,'Sagar NagarBhavi','SA103','bangalore','karnataka','india','2222','2222','2222','2222','ACTIVE'),(10,5,'Sagar Papreddyplaya','SA104','bangalore','karnataka','india','3333','3333','33333','3333','ACTIVE'),(11,5,'Sagar Sunkkadakatte','SA105','bangalore','karnataka','india','4444','4444','4444','4444','ACTIVE'),(12,5,'Sagar Herohalli','SA106','bangalore','karnataka','india','5555','55555','55555','55555','ACTIVE'),(13,5,'Sagar Tunganagar','SA107','bangalore','karnataka','india','6666','6666','6666','6666','ACTIVE'),(14,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(15,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(16,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(17,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(18,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(19,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(20,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(21,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(22,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(23,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(24,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(25,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(26,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(27,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(28,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(29,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(30,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(31,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(32,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(33,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(34,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(35,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(36,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(37,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(38,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(39,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(40,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(41,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(42,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(43,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(44,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(45,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(46,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(47,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(48,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(49,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(50,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(51,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(52,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(53,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(54,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(55,8,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(56,9,'test','557575757','test',NULL,'dsds',NULL,NULL,NULL,NULL,'ACTIVE'),(57,11,'Sam branch','jgsjsg','Sam branch',NULL,'gssgj',NULL,NULL,NULL,NULL,'ACTIVE'),(58,12,'Arvindha HSR Layout','33','blr',NULL,'india',NULL,NULL,NULL,NULL,'ACTIVE'),(59,13,'Arvindha BTM Layout','22','blr',NULL,'india',NULL,NULL,NULL,NULL,'ACTIVE'),(60,4,'rbranchao','rao123','ongole',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(61,4,'rbranchao1','rao123','ongole',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(62,4,'rbranchao1','rao1234','ongole',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(63,4,'rbranchao1','rao1234','ongole',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(64,4,'rbranchao','rao12345','ongole',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(65,4,'rbranchao','rao12345789','ongole',NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `pharmacy_branches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specialization`
--

DROP TABLE IF EXISTS `specialization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `specialization` (
  `specialization_id` int(4) NOT NULL AUTO_INCREMENT,
  `specialization_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`specialization_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specialization`
--

LOCK TABLES `specialization` WRITE;
/*!40000 ALTER TABLE `specialization` DISABLE KEYS */;
INSERT INTO `specialization` VALUES (1,'Gynecologist'),(2,'Dermatologist'),(3,'Homeopath'),(4,'Ayurveda'),(5,'Cardiologist'),(6,'Gastroenterologist'),(7,'Neurologist'),(8,'Ear-Nose-Throat (ENT)'),(9,'Psychiatrist'),(10,'General Physician'),(11,'Physiotherapist'),(12,'Urologist'),(13,'Orthopedist'),(14,'Psychologist'),(16,'dentist');
/*!40000 ALTER TABLE `specialization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `measurement_tools`
--

DROP TABLE IF EXISTS `measurement_tools`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `measurement_tools` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `tools_name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `measurement_tools`
--

LOCK TABLES `measurement_tools` WRITE;
/*!40000 ALTER TABLE `measurement_tools` DISABLE KEYS */;
INSERT INTO `measurement_tools` VALUES (1,'active_energy_burned'),(2,'basal_energy_burned'),(3,'blood_glucose'),(4,'blood_oxygen_saturation'),(5,'blood_pressure'),(6,'body_mass_index'),(7,'body_temperature'),(8,'body_weight'),(9,'dietary_biotin'),(10,'dietary_caffeine'),(11,'dietary_calcium'),(12,'dietary_carbohydrates'),(13,'dietary_chloride'),(14,'dietary_cholestreol'),(15,'dietary_chromium'),(16,'dietary_copper'),(17,'dietary_energy_consumed'),(18,'dietary_fat_mono_unsaturated'),(19,'dietary_fat_poly_unsaturated'),(20,'dietary_fat_saturated'),(21,'dietary_fat_total'),(22,'dietary_fiber'),(23,'dietary_folate'),(24,'dietary_iodine'),(25,'dietary_iron'),(26,'dietary_magnesium'),(27,'dietary_manganese'),(28,'dietary_molybdenum'),(29,'dietary_niacin'),(30,'dietary_pantothenic_acid'),(31,'dietary_phosphorus'),(32,'dietary_potassium'),(33,'dietary_protien'),(34,'dietary_riboflavin'),(35,'dietary_selenium'),(36,'dietary_sodium'),(37,'dietary_sugar'),(38,'dietary_thiamin'),(39,'dietary_vitamin_A'),(40,'dietary_vitamin_B12'),(41,'dietary_vitamin_B6'),(42,'dietary_vitamin_C'),(43,'dietary_vitamin_D'),(44,'dietary_vitamin_E'),(45,'dietary_vitamin_K'),(46,'dietary_zinc'),(47,'glycosylated_hemoglobin(HbA1c)'),(48,'heart_rate'),(49,'height'),(50,'lean_body_mass'),(51,'peak_flow'),(52,'peripheral_perfusion'),(53,'respiratory_rate');
/*!40000 ALTER TABLE `measurement_tools` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hospitals`
--

DROP TABLE IF EXISTS `hospitals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hospitals` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `hospital_name` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `state` varchar(100) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `pin_code` varchar(100) DEFAULT NULL,
  `phone_business` varchar(20) DEFAULT NULL,
  `fax` varchar(50) DEFAULT NULL,
  `is_active` enum('ACTIVE','INAVRIVE') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospitals`
--

LOCK TABLES `hospitals` WRITE;
/*!40000 ALTER TABLE `hospitals` DISABLE KEYS */;
INSERT INTO `hospitals` VALUES (2,'Fortis Hospital','new york','states','United states','45600','222','2345','ACTIVE'),(3,'ccc','ccc','cccc','ccc','5654','5698','52326','ACTIVE'),(4,'Shobha','blr','karnataka','india','222','333','444','ACTIVE'),(5,'Einstein','Bronx','New York','USA','008877','5182222222','5182222223','ACTIVE'),(6,'Abington Hospital','Abington','Pennsylvania','USA','0000','2155555555','2155555556','ACTIVE'),(7,'Abington Hospital','Abington','Pennsylvania','USA','0000','2155555555','2155555556','ACTIVE');
/*!40000 ALTER TABLE `hospitals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pharmacy`
--

DROP TABLE IF EXISTS `pharmacy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pharmacy` (
  `id` int(1) NOT NULL AUTO_INCREMENT,
  `pharmacy_name` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `state` varchar(100) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `pin_code` varchar(50) DEFAULT NULL,
  `phone_business` varchar(20) DEFAULT NULL,
  `fax` varchar(50) DEFAULT NULL,
  `is_active` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pharmacy`
--

LOCK TABLES `pharmacy` WRITE;
/*!40000 ALTER TABLE `pharmacy` DISABLE KEYS */;
INSERT INTO `pharmacy` VALUES (2,'Apollo Pharmacy','mainCity','mainState','mainCountry','AP100','922222222','22222','ACTIVE'),(3,'','','','','',NULL,NULL,'ACTIVE'),(4,'hgdhgs','ghqg','qgqh','gh','22828','9829828','9829','ACTIVE'),(5,'Sagar Pharmacy','Bangalore','Karnataka','india','2345','2341111','1111','ACTIVE'),(6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(7,'MaruthiPharmacy','vijayanagar','karnataka','india','50071','080-23456712','235674','ACTIVE'),(8,NULL,NULL,NULL,NULL,NULL,'12111111',NULL,'ACTIVE'),(9,'hggh77','hghg','hkhkhk','kkjk','879798787','76767676','7676','ACTIVE'),(10,'Sam Phrmacy','jhsj','jjshjsh','jhjhj','87878','989898989','989898989','ACTIVE'),(11,'Sam 2','dhjhdq','udhudy','dhdhj','hjhjd','28287','87778','ACTIVE'),(12,'Arvindha','blr','karnataka','india','22','33','33','ACTIVE'),(13,'Arvindha','blr','karnataka','india','22','33','33','ACTIVE'),(14,'Esquire Drugs','New York','New York','USA','0099','2122222222','2122222223','ACTIVE'),(15,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(16,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(17,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(18,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(19,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(20,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(21,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(22,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE');
/*!40000 ALTER TABLE `pharmacy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_dietary_acid_details`
--

DROP TABLE IF EXISTS `patient_dietary_acid_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_dietary_acid_details` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `patient_id` int(4) NOT NULL,
  `dietary_niacin` varchar(45) DEFAULT NULL,
  `dietary_pantothenic_acid` varchar(45) DEFAULT NULL,
  `dietary_phosphorus` varchar(45) DEFAULT NULL,
  `dietary_potassium` varchar(45) DEFAULT NULL,
  `dietary_protein` varchar(45) DEFAULT NULL,
  `dietary_riboflavin` varchar(45) DEFAULT NULL,
  `dietary_selenium` varchar(45) DEFAULT NULL,
  `dietary_sodium` varchar(45) DEFAULT NULL,
  `dietary_sugar` varchar(45) DEFAULT NULL,
  `dietary_thiamin` varchar(45) DEFAULT NULL,
  `date_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `dietary_acid_idx` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_dietary_acid_details`
--

LOCK TABLES `patient_dietary_acid_details` WRITE;
/*!40000 ALTER TABLE `patient_dietary_acid_details` DISABLE KEYS */;
INSERT INTO `patient_dietary_acid_details` VALUES (3,25,'15mg','40mg','200mg','50mg','200mg','10%','20%','40%','20%','55mg','2015-08-18 13:22:45');
/*!40000 ALTER TABLE `patient_dietary_acid_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `location_id` int(4) NOT NULL AUTO_INCREMENT,
  `location_name` varchar(200) DEFAULT NULL,
  `city_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Bellandur ',1),(2,'Indira Nagar',1),(3,'BTM Layout ',1),(4,'Banashankari ',1),(5,'Hebbal ',1),(6,'Kanakapura Road',1),(7,'Malleshwaram',1),(8,'Basaveshwara Nagar',1),(9,'Vijayanagar',1),(10,'Jalahalli West',1),(11,'Adambakam',2),(12,'Adayar',2),(13,'Alandur',2),(14,'Alwerpet',2),(15,'Alwarthirunagar',2),(16,'Ambattur',2),(17,'Aminjikarai',2),(18,'Anakaputhur',2),(19,'Anna nagar',2),(20,'Annanur',2),(21,'Arumbakam',2),(22,'Ashok nagar',2),(23,'Valachery',2),(24,'',2),(25,'',2),(26,'',2);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_care_partner`
--

DROP TABLE IF EXISTS `patient_care_partner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_care_partner` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `patient_care_partner_id` int(4) NOT NULL,
  `patient_subscriber_id` int(4) NOT NULL,
  `patient_relationship` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`,`patient_care_partner_id`),
  UNIQUE KEY `careTaker_subscriber_unique` (`patient_care_partner_id`,`patient_subscriber_id`),
  KEY `patient_care_taker_index` (`patient_care_partner_id`),
  KEY `patient_subscriber_index` (`patient_subscriber_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_care_partner`
--

LOCK TABLES `patient_care_partner` WRITE;
/*!40000 ALTER TABLE `patient_care_partner` DISABLE KEYS */;
INSERT INTO `patient_care_partner` VALUES (1,26,27,NULL);
/*!40000 ALTER TABLE `patient_care_partner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicine`
--

DROP TABLE IF EXISTS `medicine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicine` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `medicine_name` varchar(45) DEFAULT NULL,
  `chemical_name` varchar(500) DEFAULT NULL,
  `medicine_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine`
--

LOCK TABLES `medicine` WRITE;
/*!40000 ALTER TABLE `medicine` DISABLE KEYS */;
INSERT INTO `medicine` VALUES (16,'Tolnaftate : 10 mg','Tinactin','Ointment'),(17,'Verapamil : 2.5 mg','Verapamil HCl','Injection'),(18,'Verapamil : 120 mg','Iproveratril','Tablet'),(19,'Vit A : 50000 iu','Retino','Capsule'),(20,'Vit A : 50000 iu	','Retino','Tablet'),(21,'Vit A : 5000 iu','Retino','Injection'),(22,'Vit C : 500 mg','Retino','Injection'),(23,'Aminophylline : 225 mg','Phyllocontin','Tablet'),(24,'Vit E : 100 mg','Tocopherol','Capsule'),(25,'Vit E : 134 mg','Tocopherol','Capsule'),(26,'Aspirin : 75 mg','2-Acetoxybenzoic acid','Tablet'),(27,'Aspirin : 100 mg','2-Acetoxybenzoic acid','Tablet'),(28,'Aspirin : 150 mg','2-Acetoxybenzoic acid','Tablet'),(29,'Benadryl:150ml','anticholinergic ','cough syrup'),(30,'crocin pain relief','crocin ','tablet');
/*!40000 ALTER TABLE `medicine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_medication_details`
--

DROP TABLE IF EXISTS `patient_medication_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_medication_details` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `patient_id` int(4) NOT NULL,
  `medicine_id` int(4) NOT NULL,
  `instruction` varchar(45) DEFAULT NULL,
  `before_after_food` varchar(45) DEFAULT NULL,
  `inserted_date` datetime DEFAULT NULL,
  `from_date` datetime DEFAULT NULL,
  `to_date` datetime DEFAULT NULL,
  `read_unread` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `medicine_fk_idx` (`medicine_id`),
  CONSTRAINT `medicine_fk` FOREIGN KEY (`medicine_id`) REFERENCES `medicine` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_medication_details`
--

LOCK TABLES `patient_medication_details` WRITE;
/*!40000 ALTER TABLE `patient_medication_details` DISABLE KEYS */;
INSERT INTO `patient_medication_details` VALUES (9,25,16,'twice daily','AFTERFOOD','2015-09-14 04:00:00','2015-09-14 04:00:00','2015-09-23 10:00:00',1),(10,39,17,'once daily','BEFOREFOOD','2015-09-15 07:46:33','2015-09-15 07:46:33','2015-09-23 07:49:22',1),(11,39,18,'thrice daily','AFTERFOOD','2015-09-15 07:52:33','2015-09-15 07:52:33','2015-09-24 07:53:33',1),(12,39,16,'Weekly Two Times','Before Food','2015-09-19 13:18:47','2015-09-19 00:00:00','2015-11-27 00:00:00',0),(13,39,20,'Every Day','After Food','2015-09-19 13:22:41','2015-09-19 00:00:00','2015-09-24 00:00:00',0),(14,39,17,'Every Day','After Food','2015-09-19 13:23:54','2015-09-19 00:00:00','2015-09-30 00:00:00',0),(15,39,29,'Every Day','After Food','2015-09-19 13:24:28','2015-09-19 00:00:00','2015-09-30 00:00:00',0),(16,39,23,'Weekly Two Times','Before Food','2015-09-19 13:25:19','2015-09-19 00:00:00','2015-09-30 00:00:00',0),(17,39,30,'Every Day','After Food','2015-09-19 14:25:32','2015-09-19 00:00:00','2015-09-30 00:00:00',0),(18,39,18,'Every Day','After Food','2015-09-19 14:34:38','2015-09-19 00:00:00','2015-09-30 00:00:00',0),(19,39,23,'Weekly Two Times','Before Food','2015-09-19 14:40:53','2015-09-19 00:00:00','2015-09-30 00:00:00',0),(20,39,16,'Every Day','After Food','2015-10-31 21:33:04','2015-10-31 00:00:00','2015-10-31 00:00:00',0);
/*!40000 ALTER TABLE `patient_medication_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_dietary_fat_details`
--

DROP TABLE IF EXISTS `patient_dietary_fat_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_dietary_fat_details` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `patient_id` int(4) NOT NULL,
  `dietary_fat_polyunsaturated` varchar(45) DEFAULT NULL,
  `dietary_fat_saturated` varchar(45) DEFAULT NULL,
  `dietary_fat_total` varchar(45) DEFAULT NULL,
  `dietary_fiber` varchar(45) DEFAULT NULL,
  `dietary_folate` varchar(45) DEFAULT NULL,
  `dietary_iodine` varchar(45) DEFAULT NULL,
  `dietary_iron` varchar(45) DEFAULT NULL,
  `dietary_magnesium` varchar(45) DEFAULT NULL,
  `dietary_manganese` varchar(45) DEFAULT NULL,
  `dietary_molybdenum` varchar(45) DEFAULT NULL,
  `date_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `dietary_fat_fk_idx` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_dietary_fat_details`
--

LOCK TABLES `patient_dietary_fat_details` WRITE;
/*!40000 ALTER TABLE `patient_dietary_fat_details` DISABLE KEYS */;
INSERT INTO `patient_dietary_fat_details` VALUES (2,25,'20gm','10gm','20gm','2%','4%','3%','7%','6gm','3gm','2gm','2015-08-18 13:27:30');
/*!40000 ALTER TABLE `patient_dietary_fat_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medication_procedure`
--

DROP TABLE IF EXISTS `medication_procedure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medication_procedure` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `procedure_name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medication_procedure`
--

LOCK TABLES `medication_procedure` WRITE;
/*!40000 ALTER TABLE `medication_procedure` DISABLE KEYS */;
/*!40000 ALTER TABLE `medication_procedure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_energy_blood_body_details`
--

DROP TABLE IF EXISTS `patient_energy_blood_body_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_energy_blood_body_details` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `patient_id` int(4) NOT NULL,
  `active_energy_burned` varchar(45) DEFAULT NULL,
  `basal_energy_burned` varchar(45) DEFAULT NULL,
  `blood_glucose` varchar(45) DEFAULT NULL,
  `blood_oxygen_saturation` varchar(45) DEFAULT NULL,
  `blood_pressure` varchar(45) DEFAULT NULL,
  `BMI` varchar(45) DEFAULT NULL,
  `body_fat` varchar(45) DEFAULT NULL,
  `body_temperature` varchar(45) DEFAULT NULL,
  `date_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `energy_body_fk_idx` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_energy_blood_body_details`
--

LOCK TABLES `patient_energy_blood_body_details` WRITE;
/*!40000 ALTER TABLE `patient_energy_blood_body_details` DISABLE KEYS */;
INSERT INTO `patient_energy_blood_body_details` VALUES (3,25,'65%','64%',' 4.4 to 6.1 mmol/L','95-100%','120/80 mmHg','18.5–24.9','30%','37C','2015-09-02 16:16:30');
/*!40000 ALTER TABLE `patient_energy_blood_body_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (28);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `email_address` varchar(100) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `user_type` varchar(40) NOT NULL,
  `gender` varchar(40) NOT NULL,
  `phone_personel` varchar(45) DEFAULT NULL,
  `phone_business` varchar(45) DEFAULT NULL,
  `fax_number` varchar(45) DEFAULT NULL,
  `address1` varchar(100) DEFAULT NULL,
  `address2` varchar(100) DEFAULT NULL,
  `address3` varchar(100) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `state` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `account_state` varchar(40) DEFAULT 'ACTIVE',
  `last_login` varchar(30) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `registration_date` varchar(30) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `photo_path` varchar(500) DEFAULT NULL,
  `specialization_id` varchar(45) DEFAULT NULL,
  `confirm_password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_address_UNIQUE` (`email_address`),
  KEY `email_address` (`email_address`)
) ENGINE=InnoDB AUTO_INCREMENT=263 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (25,'ram1234','same','same@gmail.com','password','PT','M','+','','0','','','','','','','Active','','1971-12-03','',NULL,NULL,'bundle_user_file_name.jpg',NULL,NULL),(26,'Jhon','Jhon','jhon@qqq.com','password','PT','M',NULL,NULL,NULL,'address1','address2','address3','India','Karnataka','Bangalore','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(27,'smitha','Jhon','smitha_jhon@qqq.com','password','PT','M',NULL,NULL,NULL,'address1','address2','address3','India','Karnataka','Bangalore','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(28,'admin','admin','admin@qqq.com','password','A','M',NULL,NULL,NULL,'address1','address2','address3','India','Karnataka','Bangalore','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(29,'hospital_admin','admin','hospital_admin@qqq.com','password','HA','M',NULL,NULL,NULL,'address1','address2','address3','India','Karnataka','Bangalore','ACTIVE',NULL,NULL,NULL,NULL,NULL,'null',NULL,NULL),(30,'Doctor',NULL,'doctor@qqq.com','password','PHS','F','+919845066772',NULL,NULL,NULL,NULL,NULL,'india',NULL,NULL,'ACTIVE',NULL,'1971-12-03',NULL,NULL,NULL,NULL,NULL,NULL),(31,'pharmacy Admin','Admin','pharmacy_Admin@qqq.com','password','PHA','M',NULL,NULL,NULL,'address1','address2','address3','India','Karnataka','Bangalore','ACTIVE',NULL,NULL,'',NULL,NULL,NULL,NULL,NULL),(32,'pharmacy Admin1','Admin','pharmacy_Admin1@qqq.com','password','PHA','M',NULL,NULL,NULL,'address1','address2','address3','India','Karnataka','Bangalore','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(33,'pharmacy Admin2','Admin','pharmacy_Admin2@qqq.com','password','PHA','M',NULL,NULL,NULL,'address1','address2','address3','India','Karnataka','Bangalore','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(34,'hospital_admin1','admin','hospital_admin1@qqq.com','password','HA','M',NULL,NULL,NULL,'address1','address2','address3','India','Karnataka','Bangalore','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(35,'hospital_admin2','admin','hospita2_admin1@qqq.com','password','HA','M',NULL,NULL,NULL,'address1','address2','address3','India','Karnataka','Bangalore','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(36,'kandasamy','thulsamrala','kandasamy@skoruz.com','password','PT','M','9629',NULL,'9629','Bharathi Street',NULL,NULL,'India',NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(37,'sami','nathan','sami@gmail.com','password','PT','M','+91998','','2878','bentenville','bangalore','califonia','KM','USA','canada','ACTIVE','','1997-12-03','',NULL,NULL,'37bundle_user_file_name.jpg',NULL,NULL),(38,'ddd',NULL,'ddd@gmail.com','password','PT','F','3333',NULL,'4444','dddd',NULL,NULL,'dddd',NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(39,'kumar','shiva','kumar@gmail.com','password','PT','M','+91222','','22','HSR Layout','','','india','karnataka','blr','ACTIVE','','1997-12-05','',NULL,NULL,'39bundle_user_file_name.jpg',NULL,NULL),(40,'kathir','ravan','kathir@gmail.com',NULL,'PT','M','66',NULL,'77','HSR layout',NULL,NULL,'india','karnataka','blr','ACTIVE',NULL,'2000-09-12',NULL,NULL,NULL,'4010994227_10153061682452999_2223061878296604361_n.jpg',NULL,NULL),(41,'John ','Q','johnq@public.com',NULL,'PT','M','2122222222',NULL,'2122222223','42nd and Broadway',NULL,NULL,'USA','New York','New York','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(43,'jaggu','bai','jaggu@gmail.com',NULL,'PT','M','6464',NULL,'464','jpnagar',NULL,NULL,'india','karnataka','banglr','ACTIVE',NULL,NULL,NULL,NULL,NULL,'431425725_226480907522719_1177757681_n.jpg',NULL,NULL),(44,'babi',' babu','babi@gmail.com',NULL,'PT','M','7475',NULL,'7474','maranahalli',NULL,NULL,'india','karnataka','banglore','ACTIVE',NULL,NULL,NULL,NULL,NULL,'441425725_226480907522719_1177757681_n.jpg',NULL,NULL),(45,'doctor2','doctor2','doctor2@qqq.com','password','PHS','M','333','812345','55345','hsr layout','chandra layout','banglore','india','karnataka','bangalore','ACTIVE',NULL,NULL,NULL,NULL,NULL,'4010994227_10153061682452999_2223061878296604361_n.jpg',NULL,NULL),(46,'Balaji','K','balajik@skoruz.com','welcome','PT','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,'1986-03-14','2015-11-06',12.9077573,77.5876378,'431425725_226480907522719_1177757681_n.jpg',NULL,NULL),(47,'samanvi','bhushan','samnvi@gmail.com',NULL,'PT','F','66',NULL,'44','vijayanager',NULL,NULL,'india','karnataka','blr','ACTIVE',NULL,NULL,NULL,NULL,NULL,'47gown-green-aahira-400x400-imae8xyfdvbpteqb.jpeg',NULL,NULL),(48,'ram','laxman','ravi@gmail.com',NULL,'PT','MALE','8988988999',NULL,'4555','blr',NULL,NULL,'india','karnataka','blr','ACTIVE',NULL,NULL,NULL,NULL,NULL,'48gg.jpg',NULL,NULL),(49,'yy','yy','yy@gmail.com',NULL,'PT','F','888',NULL,'665','yy',NULL,NULL,'yy','yy','yyy','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(50,'dfg','fgfg','td@g.com',NULL,'PT','M','6666666666',NULL,'45667','fghfghfg',NULL,NULL,'ghgfg','yffh','ovvh','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(53,'hjh','hh','gh@g,com',NULL,'PT','M','6567576556',NULL,'76556','hvhvh',NULL,NULL,'hvhvhj','hvhvh','hghvhj','ACTIVE',NULL,NULL,NULL,NULL,NULL,'53431425725_226480907522719_1177757681_n.jpg',NULL,NULL),(54,'jojj','joj','kjjk@gmail.com',NULL,'PT','M','9879879879',NULL,'9879898','jkjkjj',NULL,NULL,'jj','jjj','jj','ACTIVE',NULL,NULL,NULL,NULL,NULL,'54431425725_226480907522719_1177757681_n.jpg',NULL,NULL),(56,NULL,NULL,'ranga@gmail.com',NULL,'PHS','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(58,'ranga','ranganath','ranga112@gmail.com','password','PHS','M','+919845066772',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(59,'jj','jj','nnkj@gmail.com',NULL,'PT','M','8788',NULL,'78667','jj',NULL,NULL,'jj','jj','jj','ACTIVE',NULL,NULL,NULL,NULL,NULL,'59xx.jpg',NULL,NULL),(69,'rajesh','jhjh','lkjkj',NULL,'PT','M','87878',NULL,'7676876','kjkjk',NULL,NULL,'k','kjjk','kj','ACTIVE',NULL,NULL,NULL,NULL,NULL,'69index.jpeg',NULL,NULL),(70,'rr','rr','rao@gmail.com',NULL,'PT','M','89899',NULL,'8778','rr',NULL,NULL,'rr','rr','r','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(72,'hj','hh','hhjkhj@gmail.com',NULL,'PT','M','',NULL,'877222','hh',NULL,NULL,'hh','h','hh','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(74,'bai','lkmkl','lkmlm@gmail.com',NULL,'PT','M','89',NULL,'989','mmm',NULL,NULL,'lmlm','lmk','mkmkm','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(75,'adi','lklk','lmkl@gmail.com',NULL,'PT','M','9899',NULL,'8797','ljklkjlkj',NULL,NULL,'jjjlj','jj','j','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(76,'krhti','jnk','mnbnbb@gmail.com',NULL,'PT','M','87',NULL,'878','nknk',NULL,NULL,'kn','kjjk','kj','ACTIVE',NULL,NULL,NULL,NULL,NULL,'76index.jpeg',NULL,NULL),(77,'kjhjk','hkjhj','khjh@gmail.com',NULL,'PT','M','87788',NULL,'9889','jhjhj',NULL,NULL,'jhjhh','jhjhj','hhjjhhhkjh','ACTIVE',NULL,NULL,NULL,NULL,NULL,'77index.jpeg',NULL,NULL),(78,'karuna','kumar','karuna@gmail.com',NULL,'PT','F','23456',NULL,'3456','JP nagar',NULL,NULL,'india','karnataka','blr','ACTIVE',NULL,NULL,NULL,NULL,NULL,'78index.jpeg',NULL,NULL),(79,'gg','gg','gg@gmail.com',NULL,'PT','TG','88888',NULL,'333','gg',NULL,NULL,'gg','gg','gg','ACTIVE',NULL,NULL,NULL,NULL,NULL,'79hh.jpg',NULL,NULL),(93,'gg','gg','gg123@gmail.com',NULL,'PT','TG','88888',NULL,'333','gg',NULL,NULL,'gg','gg','gg','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(94,'gg','gg','gg13@gmail.com',NULL,'PT','TG','88888',NULL,'333','gg',NULL,NULL,'gg','gg','gg','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(95,'ee','ee','ee@gmail.com',NULL,'PT','F','456',NULL,'233','ee',NULL,NULL,'ee','ee','ee','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(96,'kk','kk','kk@gmail.com',NULL,'PT','F','345',NULL,'333','kk',NULL,NULL,'kk','kk','kk','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(99,'kk','kk','kwek@gmail.com',NULL,'PT','F','345',NULL,'333','kk',NULL,NULL,'kk','kk','kk','ACTIVE',NULL,NULL,NULL,NULL,NULL,'99welcome-green-background-wood-41435889.jpg',NULL,NULL),(100,'parrot','green','parrot@qqq.com',NULL,'PT','F','5677',NULL,'5567','dfgggh',NULL,NULL,'india','blr','green','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(101,'kl','lkml','k',NULL,'PT','M','909009009',NULL,'99899','lkl',NULL,NULL,'lk','lk','lk','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(109,'parrot','green','parrot12@qqq.com',NULL,'PT','F','5677',NULL,'5567','dfgggh',NULL,NULL,'india','blr','green','ACTIVE',NULL,NULL,NULL,NULL,NULL,'109Screenshot from 2015-12-30 15:29:41.png',NULL,NULL),(110,'mm','mm','mm@gmail.com',NULL,'PT','M','8888',NULL,'566','mm',NULL,NULL,'mm','mm','mm','ACTIVE',NULL,NULL,NULL,NULL,NULL,'110Screenshot from 2015-12-30 15:29:41.png',NULL,NULL),(111,'pp','pp','jjdh@gmail.com',NULL,'PT','M','009900',NULL,'98','pppp',NULL,NULL,'pp','ppp','pp','ACTIVE',NULL,NULL,NULL,NULL,NULL,'111Screenshot from 2015-12-30 15:29:41.png',NULL,NULL),(112,'l','l','l@gmail.com',NULL,'PT','M','999999',NULL,'5858','l',NULL,NULL,'l','l','l','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(113,'vv','vv','vv@gmail.com',NULL,'PT','M','345',NULL,'678','vv',NULL,NULL,'vv','vv','vv','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(114,'pp','pp','pp@gmail.com',NULL,'PT','M','8989',NULL,'8888','pp',NULL,NULL,'pp','pp','pp','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(115,'mmm','mm','mmm@gmail.com',NULL,'PT','F','8888',NULL,'456','mmm',NULL,NULL,'mm','mm','mm','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(117,'mmm','mm','mmm56@gmail.com',NULL,'PT','F','8888',NULL,'456','mmm',NULL,NULL,'mm','mm','mm','ACTIVE',NULL,NULL,NULL,NULL,NULL,'117Screenshot from 2015-12-30 15:29:41.png',NULL,NULL),(118,'puspa','k','skjdjj@gmail.com',NULL,'PT','M','998',NULL,'434','jj',NULL,NULL,'jj','k','k','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(119,'puspa','j','njn@gmail.com',NULL,'PT','M','888',NULL,'888','j',NULL,NULL,'j','j','j','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(130,'puspa','lklk','mm,@gmail.com',NULL,'PT','M','909',NULL,'4994','kjj',NULL,NULL,'kkj','ll','ll','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(131,'puspa','lklk','zszpizza@gmail.com',NULL,'PT','M','909',NULL,'4994','kjj',NULL,NULL,'kkj','ll','ll','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(132,'ollk','lkk','mmk@gmail.com',NULL,'PT','M','9099',NULL,'5959','jj',NULL,NULL,'jj','l','lk','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(133,'mm','kjk','jnjn@gmail.com',NULL,'PT','M','08998',NULL,'3948','kjkk',NULL,NULL,'kjk','kjk','jk','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(134,'hi','jhhjh','nnn@gmail.com',NULL,'PT','M','9898',NULL,'748','jhj',NULL,NULL,'jh','jhj','jhhjh','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(135,'hh','kjkj','jjkn@gmail.com',NULL,'PT','M','9898',NULL,'747','knjn',NULL,NULL,'kjnk','knjjn','kjnkjn','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(136,'nmn','mnmn','n@gmail.com',NULL,'PT','M','9898',NULL,'7849','nkn',NULL,NULL,'nkn','njn','nkn','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(137,'hjjj','kjnkjn','mn@gmail.com',NULL,'PT','M','787',NULL,'8585','knkn',NULL,NULL,'knkn','knkn','nkn','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(138,'kjhjh','jkjk','kjnknj@gmail.com',NULL,'PT','M','98349839',NULL,'958','kjkj',NULL,NULL,'lkk','kjn','jnjnjk','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(139,'jkkj','kjn','mkmkmk@gmail.com',NULL,'PT','M','94839',NULL,'9908','kjn',NULL,NULL,'knkn','nn','nkjnk','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(140,'lklk','ko','kmkkmmimkmKM@gmail.com',NULL,'PT','M','989898',NULL,'9893284','ko',NULL,NULL,'kk','ok','ook','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(141,'kjkj','kj','mkkmkm@gmail.com',NULL,'PT','M','040308',NULL,'4039','kjk',NULL,NULL,'kjk','kj','kjkj','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(142,'kmlkmmknkn','kjnkjn','knknkn@gmail.com',NULL,'PT','M','98989',NULL,'89834','knknkn',NULL,NULL,'nnkn','nn','kn','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(143,'ranga','jk','mkmnknkN@gmail.com',NULL,'PT','M','989898',NULL,'784837','knk',NULL,NULL,'nkj','kjjk','k','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(144,'h','jhj','mmjhbjbbjb@gmail.com',NULL,'PT','M','8787',NULL,'4738','hjh',NULL,NULL,'hhk','hh','h','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(145,'jkj','jk','laxmi@gmail.com',NULL,'PT','F','88787',NULL,'787','jj',NULL,NULL,'jjj','jj','jj','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(146,'ty','hh','jnjnjn@gmail.com',NULL,'PT','M','898',NULL,'898','hh',NULL,NULL,'hh','uuh','uh','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(150,'kjkj','kjkj','jkjkJ@gmail.com',NULL,'PT','M','09090',NULL,'98787','kjk',NULL,NULL,'jk','kjk','jk','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(151,'oikoki','kioik','kmkm@gmail.com',NULL,'PT','M','89898',NULL,'8889','ko',NULL,NULL,'oik','ki','kk','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(152,'kjkj','kjkj','kkj',NULL,'PT','M','0090',NULL,'9898','kjk',NULL,NULL,'kjk','kjk','kjk','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(153,'kjkj','kjkj','jbhjh@gmail.com',NULL,'PT','M','0090',NULL,'9898','kjk',NULL,NULL,'kjk','kjk','kjk','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(154,'oko','ko','kjklkl@gmail.com',NULL,'PT','M','9880',NULL,'909','ko',NULL,NULL,'oko','kokok','koko','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(155,'okok','ko','kshjshati@gmail.com',NULL,'PT','M','9009',NULL,'9889','ko',NULL,NULL,'ok','kok','kok','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(156,'jkjk','kjk','kjkjnkn@gmail.com',NULL,'PT','M','989',NULL,'8787','kjkj',NULL,NULL,'jkk','kjkj','kjkj','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(157,'klk','lk','mnkM@gmail.com',NULL,'PT','M','0909',NULL,'8798','lkl',NULL,NULL,'lklk','lk','k','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(171,'jkk','nnkjnk','kkn@gmail.com',NULL,'PT','M','980990',NULL,'8798','knn',NULL,NULL,'kjnkn','nnkn','jnn','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(172,'kjkjkj','kjk','mnnknk@gmail.com',NULL,'PT','M','998',NULL,'8738','kjk',NULL,NULL,'kjk','kjk','kjkj','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(173,'kkkj','kjkj','kmkmkmkm@gmail.com',NULL,'PT','M','0090',NULL,'885585','kjk',NULL,NULL,'jkjkj','kjk','kjk','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(174,'lklkk','k','lmlkmlkm@gmail.com',NULL,'PT','M','0909',NULL,'509509','kok',NULL,NULL,'kko','okko','okok','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(175,'kjk','kjj','jnjknkj@gmail.com',NULL,'PT','M','8998',NULL,'76','jj',NULL,NULL,'jj','ijij','jj','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(180,'iuih','ihih','uih@gmail.com',NULL,'PT','M','89',NULL,'8998','i',NULL,NULL,'ihih','hih','h','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(195,'ll','ll','ljdkdjjd@gmail.com',NULL,'PT','M','8889',NULL,'988','l',NULL,NULL,'l','ll','ll','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(196,'jj','j','mmmbbb2@gmail.com',NULL,'PT','M','7676767',NULL,'8978','j',NULL,NULL,'j','j','j','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(200,'jj','j','mmmbbb@gmail.com',NULL,'PT','M','7676767',NULL,'8978','j',NULL,NULL,'j','j','j','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(201,'h','','jjjjhjhh@gmail.com',NULL,'PT','M','8989',NULL,'88','',NULL,NULL,'h','h','h','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(202,'h','','jjjjhjh@gmail.com',NULL,'PT','M','8989',NULL,'88','asd',NULL,NULL,'h','h','h','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(203,'h','','jjjjhjhff@gmail.com',NULL,'PT','M','8989',NULL,'88','asd',NULL,NULL,'h','h','h','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(204,'h','','jjjjhjhssff@gmail.com',NULL,'PT','M','8989',NULL,'88','asd',NULL,NULL,'h','h','h','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(205,'as','kn','mnbmbmbmb@gmail.com',NULL,'PT','M','78',NULL,'6767','kn',NULL,NULL,'nn','kjn','kjn','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(206,'rfgG','gcch','mbnbn@g.com',NULL,'PT','M','546776',NULL,'545','hjvvnbv',NULL,NULL,'vnbv','hghj','bvbnv','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(207,'iljil','kh','6mmnb@gmail.com',NULL,'PT','M','8768',NULL,'8776','hjhjh',NULL,NULL,'hjh','j','kjhkjh','ACTIVE',NULL,NULL,NULL,NULL,NULL,'207185021_520500164631702_1512629802_n.jpg',NULL,NULL),(218,'kk','kk','hjdfmm@gmail.com',NULL,'PT','M','098',NULL,'887','k',NULL,NULL,'kk','kk','kk','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(221,'kk','kk','hjdfrao123mm@gmail.com',NULL,'PT','M','098',NULL,'887','k',NULL,NULL,'kk','kk','kk','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(223,'kk','kk','rajarao123nvh@gmail.com',NULL,'PT','M','098',NULL,'887','k',NULL,NULL,'kk','kk','kk','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(226,'kk','kk','rhzshdgjhzdgnvh@gmail.com',NULL,'PT','M','098',NULL,'887','k',NULL,NULL,'kk','kk','kk','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(228,'kk','kk','kinskskjfjfs@gmail.com',NULL,'PT','M','098',NULL,'887','k',NULL,NULL,'kk','kk','kk','ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(232,'kk','jl','hjhfhgjggygggjgjgj@gmail.com',NULL,'PT','M','808',NULL,'78','jk',NULL,NULL,'kk','kj','kk','ACTIVE',NULL,NULL,NULL,NULL,NULL,'232185021_520500164631702_1512629802_n.jpg',NULL,NULL),(233,'jjj','jj','jj@gmail.com','pass','PHS','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(234,'jj','j','hsnala@gmail.com','pass','PHS','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(235,'dr.abishake','potala','abhishake@gmail.com','password','PHS','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(236,'ramurthy','ram','ram@gmail.com','password','PHS','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(237,'jh','j','hjBJHB@gmail.com','password','PHS','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(238,'jj','kk','jkklknJN@gmail.com','pass','PHS','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(239,'jjj','jj','jkkj@gmail.com','pass','PHS','F',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(240,'joj','jjj','jkhhnc@gmail.com','pass','PHS','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'pass'),(241,'k','kl','rao1143748@gmail.com',NULL,'PT','M','898',NULL,'988','lk',NULL,NULL,'lk','lk','kl','ACTIVE',NULL,NULL,NULL,NULL,NULL,'241Screenshot from 2015-12-30 15:29:41.png',NULL,NULL),(243,'special','special','special@gmail.com','password','PHA','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'password'),(244,'','','','','USER TYPE','Gender',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,''),(248,'raj','raja','raj@gmail.com',NULL,'PT','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(250,'raj','raja','rauyhuydfudfggudfhj@gmail.com',NULL,'PT','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(251,'','','nn@gmail.com','','USER TYPE','Gender',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,''),(252,'','','rra@gmail.com','','USER TYPE','Gender',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,''),(253,'','','rakshesi@gmail.com','','USER TYPE','Gender',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,''),(254,'','','rakshesi1@gmail.com','','USER TYPE','Gender',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,''),(255,NULL,NULL,'jin','','USER TYPE','Gender',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,''),(256,NULL,NULL,'hanu@gmail.com','','USER TYPE','Gender',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,''),(257,NULL,NULL,'nij@gmail.com','','USER TYPE','Gender',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,''),(258,NULL,NULL,'namaskar@gmail.com','','USER TYPE','Gender',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,''),(259,NULL,NULL,'nknnon@gmail.com','','USER TYPE','Gender',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,''),(260,'final','final','final@gmail.com','','USER TYPE','Gender',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,''),(261,'mljk','jij','jolnononoj@gmail.com','password','A','M',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'password'),(262,'kjjioj','kkononn','khhahuiknn@gmail.com','','USER TYPE','Gender',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'ACTIVE',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pharmacy_admins`
--

DROP TABLE IF EXISTS `pharmacy_admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pharmacy_admins` (
  `pharadmin_id` int(4) NOT NULL AUTO_INCREMENT,
  `pharmacy_branch_id` int(4) DEFAULT NULL,
  `notification_alert` tinyint(1) NOT NULL DEFAULT '1',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`pharadmin_id`),
  KEY `pharmacy_branch_id_fk_idx` (`pharmacy_branch_id`),
  KEY `pharmacy_admin_fk` (`pharadmin_id`),
  CONSTRAINT `pharmacy_admin_fk` FOREIGN KEY (`pharadmin_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pharmacy_branch_id_fk` FOREIGN KEY (`pharmacy_branch_id`) REFERENCES `pharmacy_branches` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pharmacy_admins`
--

LOCK TABLES `pharmacy_admins` WRITE;
/*!40000 ALTER TABLE `pharmacy_admins` DISABLE KEYS */;
INSERT INTO `pharmacy_admins` VALUES (31,4,1,31),(32,5,1,32),(33,6,1,33);
/*!40000 ALTER TABLE `pharmacy_admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_physician_preferences`
--

DROP TABLE IF EXISTS `patient_physician_preferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_physician_preferences` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `patient_id` int(4) NOT NULL,
  `physician_id` int(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `patient_physician_preference_fk_idx` (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_physician_preferences`
--

LOCK TABLES `patient_physician_preferences` WRITE;
/*!40000 ALTER TABLE `patient_physician_preferences` DISABLE KEYS */;
/*!40000 ALTER TABLE `patient_physician_preferences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_details`
--

DROP TABLE IF EXISTS `patient_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_details` (
  `patient_id` int(4) NOT NULL AUTO_INCREMENT,
  `notification_alert` tinyint(1) DEFAULT '1',
  `is_primary_subscriber` tinyint(1) DEFAULT '1',
  `assessment_completion_alert` tinyint(1) DEFAULT '1',
  `assessment_notification_alert` tinyint(1) DEFAULT '1',
  `total_amount_paid` bigint(20) DEFAULT '0',
  `health_plan_id` int(11) DEFAULT '1',
  `blood_type` varchar(45) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`patient_id`),
  KEY `user_patient_id_fk_idx` (`user_id`),
  KEY `fk_patient_details_1` (`user_id`),
  CONSTRAINT `fk_patient_details_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_details`
--

LOCK TABLES `patient_details` WRITE;
/*!40000 ALTER TABLE `patient_details` DISABLE KEYS */;
INSERT INTO `patient_details` VALUES (25,1,0,0,0,1,0,NULL,25),(26,1,1,1,1,0,1,NULL,26),(27,1,0,1,1,0,1,NULL,27),(36,1,1,1,1,0,1,NULL,36),(37,1,0,1,1,0,1,'AB+',37),(38,1,1,1,1,0,1,'AB+',38),(39,1,0,1,1,0,1,'A+',39),(40,1,1,1,1,0,1,NULL,40),(41,1,1,1,1,0,1,NULL,41),(43,1,1,1,1,0,1,NULL,43),(44,1,1,1,1,0,1,NULL,44),(46,0,0,0,0,100,0,NULL,46),(47,1,1,1,1,0,1,NULL,47),(48,1,0,1,1,0,1,NULL,48),(49,1,0,1,1,0,1,NULL,49),(50,1,0,1,1,0,1,NULL,50),(51,1,0,1,1,0,1,NULL,49),(52,1,0,1,1,0,1,NULL,50),(53,1,0,1,1,0,1,NULL,53),(54,1,0,1,1,0,1,NULL,54),(55,1,0,0,0,1,0,NULL,56),(56,1,0,0,0,1,0,NULL,58),(57,1,0,1,1,0,1,NULL,59),(58,1,0,1,1,0,1,NULL,69),(59,1,0,1,1,0,1,NULL,70),(60,1,0,1,1,0,1,NULL,72),(61,1,0,1,1,0,1,NULL,74),(62,1,0,1,1,0,1,NULL,75),(63,1,0,1,1,0,1,NULL,76),(64,1,0,1,1,0,1,NULL,77),(65,1,0,1,1,0,1,NULL,78),(66,1,0,1,1,0,1,NULL,79),(67,1,0,1,1,0,1,NULL,93),(68,1,0,1,1,0,1,NULL,94),(69,1,0,1,1,0,1,NULL,95),(70,1,0,1,1,0,1,NULL,96),(71,1,0,1,1,0,1,NULL,99),(72,1,0,1,1,0,1,NULL,100),(73,1,0,1,1,0,1,NULL,101),(74,1,0,1,1,0,1,NULL,109),(75,1,0,1,1,0,1,NULL,110),(76,1,0,1,1,0,1,NULL,111),(77,1,0,1,1,0,1,NULL,112),(78,1,0,1,1,0,1,NULL,113),(79,1,0,1,1,0,1,NULL,114),(80,1,0,1,1,0,1,NULL,115),(81,1,0,1,1,0,1,NULL,117),(82,1,0,1,1,0,1,NULL,118),(83,1,0,1,1,0,1,NULL,119),(84,1,0,1,1,0,1,NULL,130),(85,1,0,1,1,0,1,NULL,131),(86,1,0,1,1,0,1,NULL,132),(87,1,0,1,1,0,1,NULL,133),(88,1,0,1,1,0,1,NULL,134),(89,1,0,1,1,0,1,NULL,135),(90,1,0,1,1,0,1,NULL,136),(91,1,0,1,1,0,1,NULL,137),(92,1,0,1,1,0,1,NULL,138),(93,1,0,1,1,0,1,NULL,139),(94,1,0,1,1,0,1,NULL,140),(95,1,0,1,1,0,1,NULL,141),(96,1,0,1,1,0,1,NULL,142),(97,1,0,1,1,0,1,NULL,143),(98,1,0,1,1,0,1,NULL,144),(99,1,0,1,1,0,1,NULL,145),(100,1,0,1,1,0,1,NULL,146),(101,1,0,1,1,0,1,NULL,150),(102,1,0,1,1,0,1,NULL,151),(103,1,0,1,1,0,1,NULL,152),(104,1,0,1,1,0,1,NULL,153),(105,1,0,1,1,0,1,NULL,154),(106,1,0,1,1,0,1,NULL,155),(107,1,0,1,1,0,1,NULL,156),(108,1,0,1,1,0,1,NULL,157),(109,1,0,1,1,0,1,NULL,171),(110,1,0,1,1,0,1,NULL,172),(111,1,0,1,1,0,1,NULL,173),(112,1,0,1,1,0,1,NULL,174),(113,1,0,1,1,0,1,NULL,175),(114,0,0,0,0,NULL,0,NULL,114),(115,0,0,0,0,NULL,0,NULL,115),(116,1,0,1,1,0,1,NULL,180),(117,1,0,1,1,0,1,NULL,195),(118,1,0,1,1,0,1,NULL,196),(119,1,0,1,1,0,1,NULL,200),(120,1,0,1,1,0,1,NULL,201),(121,1,0,1,1,0,1,NULL,202),(122,1,0,1,1,0,1,NULL,203),(123,1,0,1,1,0,1,NULL,204),(125,1,0,1,1,0,1,NULL,206),(126,1,0,1,1,0,1,NULL,207),(128,1,0,1,1,0,1,NULL,218),(129,1,0,1,1,0,1,NULL,221),(130,1,0,1,1,0,1,NULL,223),(131,1,0,1,1,0,1,NULL,226),(132,1,0,1,1,0,1,NULL,228),(133,1,0,1,1,0,1,NULL,232),(137,1,0,1,1,0,1,NULL,241);
/*!40000 ALTER TABLE `patient_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_medication_procedure_details`
--

DROP TABLE IF EXISTS `patient_medication_procedure_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_medication_procedure_details` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `medication_procedure_id` int(4) NOT NULL,
  `date_time` datetime NOT NULL,
  `result` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `procedure_fk_idx` (`medication_procedure_id`),
  CONSTRAINT `procedure_fk` FOREIGN KEY (`medication_procedure_id`) REFERENCES `medication_procedure` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_medication_procedure_details`
--

LOCK TABLES `patient_medication_procedure_details` WRITE;
/*!40000 ALTER TABLE `patient_medication_procedure_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `patient_medication_procedure_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clinic_visiting_timing`
--

DROP TABLE IF EXISTS `clinic_visiting_timing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clinic_visiting_timing` (
  `timing_id` int(11) NOT NULL AUTO_INCREMENT,
  `timing_day` varchar(45) DEFAULT NULL,
  `session1_end_time` varchar(45) DEFAULT NULL,
  `session1_start_time` varchar(45) DEFAULT NULL,
  `session2_end_time` varchar(45) DEFAULT NULL,
  `session2_start_time` varchar(45) DEFAULT NULL,
  `clinicId` int(11) DEFAULT NULL,
  `doctorId` int(11) DEFAULT NULL,
  PRIMARY KEY (`timing_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clinic_visiting_timing`
--

LOCK TABLES `clinic_visiting_timing` WRITE;
/*!40000 ALTER TABLE `clinic_visiting_timing` DISABLE KEYS */;
INSERT INTO `clinic_visiting_timing` VALUES (1,'tuesday','','','','',1,30),(2,'sunday','','','','',1,30),(3,'saturday','','','','',1,30),(4,'friday','','','','',1,30),(5,'wednesday','','','','',1,30),(6,'thursday','','','','',1,30),(7,'monday','18:00','16:00','','',1,30),(8,'monday','18:00','16:00','','',2,30),(9,'wednesday','18:00','16:00','','',2,30),(10,'friday','18:00','16:00','','',2,30),(11,'thursday','18:00','16:00','','',2,30),(12,'saturday','','','','',2,30),(13,'tuesday','18:00','16:00','','',2,30),(14,'sunday','','','','',2,30);
/*!40000 ALTER TABLE `clinic_visiting_timing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hospital_admins`
--

DROP TABLE IF EXISTS `hospital_admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hospital_admins` (
  `hospadmin_id` int(4) NOT NULL AUTO_INCREMENT,
  `hospital_branch_id` int(4) NOT NULL,
  `notification_alert` tinyint(1) NOT NULL DEFAULT '1',
  `user_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`hospadmin_id`),
  KEY `hospital_branch_id_index` (`hospital_branch_id`),
  CONSTRAINT `hospital_branch_id` FOREIGN KEY (`hospital_branch_id`) REFERENCES `hospital_branches` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospital_admins`
--

LOCK TABLES `hospital_admins` WRITE;
/*!40000 ALTER TABLE `hospital_admins` DISABLE KEYS */;
INSERT INTO `hospital_admins` VALUES (29,4,1,'29'),(34,5,1,'34'),(35,6,1,'');
/*!40000 ALTER TABLE `hospital_admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `health_plan`
--

DROP TABLE IF EXISTS `health_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `health_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `health_plan_detail` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `health_plan`
--

LOCK TABLES `health_plan` WRITE;
/*!40000 ALTER TABLE `health_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `health_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_upcoming_appointments`
--

DROP TABLE IF EXISTS `patient_upcoming_appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_upcoming_appointments` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `patient_id` int(4) NOT NULL,
  `physician_id` int(4) NOT NULL,
  `date` date DEFAULT NULL,
  `timings` timestamp NULL DEFAULT NULL,
  `check_up_type` varchar(50) NOT NULL,
  `notes` varchar(45) NOT NULL,
  `message` varchar(150) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `patient_upcoming_fk_idx` (`patient_id`),
  KEY `patient_physician_upcoming_fk_idx` (`physician_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_upcoming_appointments`
--

LOCK TABLES `patient_upcoming_appointments` WRITE;
/*!40000 ALTER TABLE `patient_upcoming_appointments` DISABLE KEYS */;
/*!40000 ALTER TABLE `patient_upcoming_appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country_code` varchar(45) DEFAULT NULL,
  `country_state` varchar(45) DEFAULT NULL,
  `country_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'IN','AndhraPradesh','India'),(2,'IN','Assam','India'),(3,'IN','Bihar','India'),(4,'IN','Goa','India'),(5,'IN','Gujarat','India'),(6,'IN','Jammu&Kasmir','India'),(7,'IN','Karnataka','India'),(8,'IN','Kerala','India'),(9,'IN','Tamil Nadu','India'),(10,'IN','Madhya Pradesh','India'),(11,'IN','Maharashtra','India'),(12,'IN','Manipur','India'),(13,'IN','Megalaya','India'),(14,'IN','Mizoram','India'),(15,'IN','Nagaland','India'),(16,'IN','Odisha(Orissa)','India'),(17,'IN','Panjab','India'),(18,'IN','Sikkim','India'),(19,'IN','Telamgana','India'),(20,'IN','Tripura','India'),(21,'IN','Uttar Pradesh','India'),(22,'IN','UttarKhand','India'),(23,'US','California','USA'),(24,'US','Florida','USA'),(25,'US','New York','USA'),(26,'US','Texas','USA'),(27,'US','Washington','USA');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `physician_availability_details`
--

DROP TABLE IF EXISTS `physician_availability_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `physician_availability_details` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `physician_id` int(4) NOT NULL,
  `available_day` enum('SUN','MON','TUE','WED','THUR','FRI','SAT') NOT NULL,
  `available_time_from` time DEFAULT NULL,
  `available_time_to` time DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `physician-available_unique` (`physician_id`,`available_day`),
  KEY `physician_id_index` (`physician_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `physician_availability_details`
--

LOCK TABLES `physician_availability_details` WRITE;
/*!40000 ALTER TABLE `physician_availability_details` DISABLE KEYS */;
INSERT INTO `physician_availability_details` VALUES (4,30,'MON','10:00:00','22:00:00'),(5,30,'WED','10:00:00','22:00:00'),(6,30,'SAT','10:00:00','22:00:00');
/*!40000 ALTER TABLE `physician_availability_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_vitamin_details`
--

DROP TABLE IF EXISTS `patient_vitamin_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_vitamin_details` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `patient_id` int(4) NOT NULL,
  `dietary_vitamin_A` varchar(45) DEFAULT NULL,
  `dietary_vitamin_B12` varchar(45) DEFAULT NULL,
  `dietary_vitamin_B6` varchar(45) DEFAULT NULL,
  `dietary_vitamin_C` varchar(45) DEFAULT NULL,
  `dietary_vitamin_D` varchar(45) DEFAULT NULL,
  `dietary_vitamin_E` varchar(45) DEFAULT NULL,
  `dietary_vitamin_K` varchar(45) DEFAULT NULL,
  `date_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `vitamin_fk_idx` (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_vitamin_details`
--

LOCK TABLES `patient_vitamin_details` WRITE;
/*!40000 ALTER TABLE `patient_vitamin_details` DISABLE KEYS */;
INSERT INTO `patient_vitamin_details` VALUES (2,25,'250%','150%','450%','500%','200%','600%','689%','2015-08-18 13:34:40');
/*!40000 ALTER TABLE `patient_vitamin_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_pharmacy_preferences`
--

DROP TABLE IF EXISTS `patient_pharmacy_preferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_pharmacy_preferences` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `patient_id` int(4) NOT NULL,
  `pharmacy_branch_id` int(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `patient_pharmacy_preference_fk_idx` (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_pharmacy_preferences`
--

LOCK TABLES `patient_pharmacy_preferences` WRITE;
/*!40000 ALTER TABLE `patient_pharmacy_preferences` DISABLE KEYS */;
/*!40000 ALTER TABLE `patient_pharmacy_preferences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hospital_branches`
--

DROP TABLE IF EXISTS `hospital_branches`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hospital_branches` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `hospital_id` int(4) NOT NULL,
  `branch_name` varchar(100) DEFAULT NULL,
  `branch_code` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `state` varchar(100) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `pin_code` varchar(100) DEFAULT NULL,
  `phone_business1` varchar(20) DEFAULT NULL,
  `phone_business2` varchar(20) DEFAULT NULL,
  `fax` varchar(50) DEFAULT NULL,
  `is_active` enum('ACTIVE','INACTIVE') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `branch_code_unique` (`branch_code`,`pin_code`,`hospital_id`),
  KEY `branch_Code` (`branch_code`),
  KEY `hospital_id_fk_idx` (`hospital_id`),
  CONSTRAINT `hospital_id_fk` FOREIGN KEY (`hospital_id`) REFERENCES `hospitals` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hospital_branches`
--

LOCK TABLES `hospital_branches` WRITE;
/*!40000 ALTER TABLE `hospital_branches` DISABLE KEYS */;
INSERT INTO `hospital_branches` VALUES (4,2,'Bangalore','HFDKSHA','Bangalore','Karnataka','India','5662','2',NULL,'233','ACTIVE'),(5,2,'Mysore branch 1','FORTIS002','Bangalore','Karnataka','India','5663','222111',NULL,'3456','ACTIVE'),(6,2,'Mangalore branch','FORTIS003','Bangalore','Karnataka','India','5664','222',NULL,'2344','ACTIVE'),(7,4,'shobha Vijayanagar','44','blr',NULL,'india',NULL,NULL,NULL,NULL,'ACTIVE');
/*!40000 ALTER TABLE `hospital_branches` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_allergy_details`
--

DROP TABLE IF EXISTS `patient_allergy_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_allergy_details` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `allergy_id` int(4) NOT NULL,
  `patient_id` int(4) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  `inserted_date` datetime DEFAULT NULL,
  `severe` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `allergies_fk_idx` (`allergy_id`),
  CONSTRAINT `allergy_fk` FOREIGN KEY (`allergy_id`) REFERENCES `allergies` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_allergy_details`
--

LOCK TABLES `patient_allergy_details` WRITE;
/*!40000 ALTER TABLE `patient_allergy_details` DISABLE KEYS */;
INSERT INTO `patient_allergy_details` VALUES (6,1,37,'ACTIVE','2015-09-18 04:59:09','low '),(7,2,39,'ACTIVE','2015-09-18 05:00:00','high'),(8,1,37,'Active','2015-09-18 05:31:30','low'),(9,3,39,'ACTIVE','2015-09-19 03:57:30','mild'),(10,3,39,'Active','2015-09-19 17:24:46','Mild'),(11,1,39,'Active','2015-10-10 15:51:41','Mild'),(12,1,39,'Active','2015-10-31 21:33:30','Low'),(13,1,39,'Active','2015-11-24 21:09:01','Low'),(14,1,39,'Active','2015-11-24 21:09:01','Low');
/*!40000 ALTER TABLE `patient_allergy_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qualification`
--

DROP TABLE IF EXISTS `qualification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qualification` (
  `qualification_id` int(11) NOT NULL AUTO_INCREMENT,
  `qualification` varchar(100) NOT NULL,
  PRIMARY KEY (`qualification_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qualification`
--

LOCK TABLES `qualification` WRITE;
/*!40000 ALTER TABLE `qualification` DISABLE KEYS */;
INSERT INTO `qualification` VALUES (1,'BDS'),(2,'Bachelor of clinical  optometry(B.optm)'),(3,'BAMS'),(4,'BHMS'),(5,'BPTH/BPT'),(6,'DGO'),(7,'Diploma  in   child  Health(DCH) '),(8,'DMD-Doctor  of Dentel  Medicine'),(9,'DNB'),(10,'MBBS'),(11,'MD'),(12,'MD-Medicine'),(13,'MD-Obstetrtics&Gynaecology'),(14,'MD-paediatrics'),(15,'MD-Dermatology');
/*!40000 ALTER TABLE `qualification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `allergies`
--

DROP TABLE IF EXISTS `allergies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `allergies` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `allergy_name` varchar(45) NOT NULL,
  `allergy_type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allergies`
--

LOCK TABLES `allergies` WRITE;
/*!40000 ALTER TABLE `allergies` DISABLE KEYS */;
INSERT INTO `allergies` VALUES (1,'wheesing','dust allergy'),(2,'coughing','dust allergy'),(3,'tightness in chest ','dust allergy'),(4,'itching','dust allergy'),(5,'sneezing ','pets allergy'),(6,'facial pain','pets allergy'),(7,'itchy eyes','pets allergy'),(8,'skin rash','pets allergy'),(9,'redness of eye','eye allergy'),(10,'burning','eye allergy'),(11,'watery discharge','eye allergy'),(12,'skin rash','drug allergy'),(13,'itching','drug allergy'),(14,'swelling','drug allergy'),(15,'vomating','drug allergy');
/*!40000 ALTER TABLE `allergies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_hospital_preferences`
--

DROP TABLE IF EXISTS `patient_hospital_preferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_hospital_preferences` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `patient_id` int(4) NOT NULL,
  `hospital_branch_id` int(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `patient_hospital_preference_fk_idx` (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_hospital_preferences`
--

LOCK TABLES `patient_hospital_preferences` WRITE;
/*!40000 ALTER TABLE `patient_hospital_preferences` DISABLE KEYS */;
/*!40000 ALTER TABLE `patient_hospital_preferences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_image`
--

DROP TABLE IF EXISTS `users_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_image` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `users_image` longblob NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_image`
--

LOCK TABLES `users_image` WRITE;
/*!40000 ALTER TABLE `users_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'medical'
--

--
-- Dumping routines for database 'medical'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-29 23:10:34
