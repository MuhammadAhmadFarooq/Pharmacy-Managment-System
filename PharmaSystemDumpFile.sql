-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: drugpharmadb
-- ------------------------------------------------------
-- Server version	8.0.35
create database drugpharmadb;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `account_id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `account_balance` double NOT NULL,
  PRIMARY KEY (`account_id`),
  KEY `fk_user` (`user_id`),
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `patients` (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,1,100),(2,2,200),(3,3,30),(4,4,325),(5,5,250),(6,6,180),(7,7,130),(8,8,90),(9,9,85),(10,10,110),(11,11,160),(12,12,140),(13,13,175),(14,14,155),(15,15,190),(16,16,145),(17,17,210),(18,18,195),(19,19,175),(20,20,130);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctors`
--

DROP TABLE IF EXISTS `doctors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctors` (
  `license_no` int NOT NULL,
  `f_name` varchar(255) DEFAULT NULL,
  `l_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`license_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctors`
--

LOCK TABLES `doctors` WRITE;
/*!40000 ALTER TABLE `doctors` DISABLE KEYS */;
INSERT INTO `doctors` VALUES (1,'Gregory','House'),(2,'Meredith','Grey'),(3,'John','Watson'),(4,'Stephen','Strange'),(5,'Leonard','McCoy'),(6,'Philip','Chandler'),(7,'Lisa','Cuddy'),(8,'James','Wilson'),(9,'Henry','Morgan'),(10,'Marcus','Welby'),(11,'Dana','Scully'),(12,'Allison','Cameron'),(13,'Eric','Foreman'),(14,'Robert','Chase'),(15,'Remy','Hadley'),(16,'Chris','Taub'),(17,'Lawrence','Kutner'),(18,'Travis','Brennan'),(19,'Jessica','Adams'),(20,'Martha','Masters');
/*!40000 ALTER TABLE `doctors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `item_id` int NOT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `quantity` int NOT NULL,
  `unit_cost` int DEFAULT '1',
  `min_stock` int DEFAULT '0',
  `is_locked` int DEFAULT '0',
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1,'Aspirin',100,5,20,0),(2,'Ibuprofen',200,10,15,0),(3,'Paracetamol',150,8,25,0),(4,'Amoxicillin',600,15,30,0),(5,'Metformin',500,12,10,0),(6,'Omeprazole',180,20,18,0),(7,'Atorvastatin',130,25,12,0),(8,'Losartan',90,30,14,0),(9,'Simvastatin',85,22,10,0),(10,'Levothyroxine',110,18,8,0),(11,'Azithromycin',160,28,16,0),(12,'Gabapentin',140,24,13,0),(13,'Lisinopril',175,19,11,0),(14,'Amlodipine',155,26,20,0),(15,'Albuterol',190,29,17,0),(16,'Hydrochlorothiazide',145,21,15,0),(17,'Zoloft',210,33,12,0),(18,'Citalopram',195,27,9,0),(19,'Furosemide',175,23,14,0),(20,'Clonazepam',130,31,18,0);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `order_date` datetime DEFAULT NULL,
  `item_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `supplier_id` int DEFAULT NULL,
  `pharmacist_id` int DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `item_id` (`item_id`),
  KEY `supplier_id` (`supplier_id`),
  KEY `fk_pharmacist` (`pharmacist_id`),
  CONSTRAINT `fk_pharmacist` FOREIGN KEY (`pharmacist_id`) REFERENCES `pharmacists` (`license_no`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `inventory` (`item_id`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`supplier_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2024-05-01 10:00:00',1,100,1,1),(2,'2024-05-02 11:00:00',2,200,2,2),(3,'2024-05-03 12:00:00',3,150,3,3),(6,'2024-05-06 15:00:00',6,180,6,6),(7,'2024-05-07 16:00:00',7,130,7,7),(8,'2024-05-08 17:00:00',8,90,8,8),(9,'2024-05-09 18:00:00',9,85,9,9),(10,'2024-05-10 19:00:00',10,110,10,10),(11,'2024-05-11 20:00:00',11,160,11,11),(12,'2024-05-12 21:00:00',12,140,12,12),(13,'2024-05-13 22:00:00',13,175,13,13),(14,'2024-05-14 23:00:00',14,155,14,14),(15,'2024-05-15 00:00:00',15,190,15,15),(16,'2024-05-16 01:00:00',16,145,16,16),(17,'2024-05-17 02:00:00',17,210,17,17),(18,'2024-05-18 03:00:00',18,195,18,18),(19,'2024-05-19 04:00:00',19,175,19,19),(20,'2024-05-20 05:00:00',20,130,20,20),(21,'2024-06-07 02:29:47',19,250,4,4),(22,'2024-06-07 02:31:37',4,400,4,4);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients` (
  `patient_id` int NOT NULL,
  `birth_day` int NOT NULL,
  `birth_month` int NOT NULL,
  `birth_year` int NOT NULL,
  `contact_info` varchar(255) DEFAULT NULL,
  `CNIC` varchar(255) DEFAULT NULL,
  `f_name` varchar(255) DEFAULT NULL,
  `l_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES (1,15,5,1980,'123 Main St','12345-6789012-3','John','Doe'),(2,22,7,1990,'456 Elm St','12345-6789012-4','Jane','Smith'),(3,3,8,1985,'789 Maple St','12345-6789012-5','Alice','Johnson'),(4,30,12,1975,'321 Oak St','12345-6789012-6','Robert','Brown'),(5,17,11,1965,'654 Pine St','12345-6789012-7','Michael','Davis'),(6,9,2,2000,'987 Cedar St','12345-6789012-8','Sarah','Miller'),(7,25,6,1988,'147 Spruce St','12345-6789012-9','William','Wilson'),(8,14,9,1995,'258 Birch St','12345-6789012-10','Emma','Moore'),(9,11,10,1982,'369 Redwood St','12345-6789012-11','Olivia','Taylor'),(10,4,3,1979,'741 Fir St','12345-6789012-12','Liam','Anderson'),(11,12,1,1992,'852 Palm St','12345-6789012-13','Mason','Thomas'),(12,20,4,1983,'963 Willow St','12345-6789012-14','Sophia','Jackson'),(13,8,11,1987,'174 Magnolia St','12345-6789012-15','James','White'),(14,23,6,1991,'286 Alder St','12345-6789012-16','Isabella','Harris'),(15,6,5,1996,'397 Poplar St','12345-6789012-17','Benjamin','Martin'),(16,18,12,1978,'579 Sycamore St','12345-6789012-18','Charlotte','Thompson'),(17,27,7,1981,'681 Sequoia St','12345-6789012-19','Alexander','Garcia'),(18,5,8,1976,'792 Dogwood St','12345-6789012-20','Elijah','Martinez'),(19,13,9,1984,'158 Juniper St','12345-6789012-21','Amelia','Robinson'),(20,2,3,1989,'269 Ash St','12345-6789012-22','Harper','Clark');
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pharmacists`
--

DROP TABLE IF EXISTS `pharmacists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pharmacists` (
  `license_no` int NOT NULL,
  `f_name` varchar(255) NOT NULL,
  `l_name` varchar(255) NOT NULL,
  PRIMARY KEY (`license_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pharmacists`
--

LOCK TABLES `pharmacists` WRITE;
/*!40000 ALTER TABLE `pharmacists` DISABLE KEYS */;
INSERT INTO `pharmacists` VALUES (1,'Michael','Jones'),(2,'Sarah','Brown'),(3,'David','Williams'),(4,'James','Taylor'),(5,'John','Lee'),(6,'Chris','White'),(7,'Anna','King'),(8,'Robert','Scott'),(9,'Jessica','Green'),(10,'William','Lewis'),(11,'Emily','Young'),(12,'Matthew','Walker'),(13,'Ashley','Hall'),(14,'Joshua','Allen'),(15,'Daniel','Wright'),(16,'Laura','Perez'),(17,'Ryan','Hill'),(18,'Hannah','Adams'),(19,'Brian','Baker'),(20,'Olivia','Mitchell');
/*!40000 ALTER TABLE `pharmacists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescriptions`
--

DROP TABLE IF EXISTS `prescriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prescriptions` (
  `prescription_id` int NOT NULL AUTO_INCREMENT,
  `item_id` int DEFAULT NULL,
  `patient_id` int DEFAULT NULL,
  `details` varchar(255) DEFAULT NULL,
  `quantity` int NOT NULL,
  `is_paid` tinyint(1) DEFAULT '0',
  `doctor_id` int DEFAULT NULL,
  PRIMARY KEY (`prescription_id`),
  KEY `item_id` (`item_id`),
  KEY `fk_doctor` (`doctor_id`),
  CONSTRAINT `fk_doctor` FOREIGN KEY (`doctor_id`) REFERENCES `doctors` (`license_no`),
  CONSTRAINT `prescriptions_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `inventory` (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescriptions`
--

LOCK TABLES `prescriptions` WRITE;
/*!40000 ALTER TABLE `prescriptions` DISABLE KEYS */;
INSERT INTO `prescriptions` VALUES (1,1,1,'Take one tablet daily',30,0,1),(2,2,2,'Take two tablets daily',60,0,2),(3,3,3,'Apply ointment twice daily',15,1,3),(4,4,4,'Take one tablet before meals',45,1,4),(5,5,5,'Take two tablets after meals',90,0,5),(6,6,6,'Apply cream three times a day',25,0,6),(7,7,7,'Take one tablet in the morning',30,0,7),(8,8,8,'Take one tablet at night',30,0,8),(9,9,9,'Take one tablet twice daily',60,0,9),(10,10,10,'Apply ointment as needed',20,0,10),(11,11,11,'Take one tablet every six hours',120,0,11),(12,12,12,'Apply lotion twice daily',30,0,12),(13,13,13,'Take one tablet every eight hours',90,0,13),(14,14,14,'Apply gel twice daily',15,0,14),(15,15,15,'Take one capsule daily',30,0,15),(16,16,16,'Apply spray as needed',50,0,16),(17,17,17,'Take one pill daily',30,0,17),(18,18,18,'Apply patch every 24 hours',30,0,18),(19,19,19,'Take one dropper daily',20,0,19),(20,20,20,'Apply cream every 12 hours',60,0,20),(21,4,6,'Take two tablets before meals',90,0,6),(22,3,4,'Take 2 a Day',30,0,2);
/*!40000 ALTER TABLE `prescriptions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers` (
  `supplier_id` int NOT NULL,
  `supplier_name` varchar(255) DEFAULT NULL,
  `contact_info` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (1,'MedSupplies Co','+92-800-123-4567'),(2,'Pharma Inc','+92-800-234-5678'),(3,'HealthEquip','+92-800-345-6789'),(4,'Wellness Goods','+92-800-456-7890'),(5,'Medic Corp','+92-800-567-8901'),(6,'Care Supplies','+92-800-678-9012'),(7,'Pharma Solutions','+92-800-789-0123'),(8,'Med Essentials','+92-800-890-1234'),(9,'Health Resources','+92-800-901-2345'),(10,'Vital Goods','+92-800-012-3456'),(11,'Wellbeing Supplies','+92-800-123-4560'),(12,'Medi Supply','+92-800-234-5670'),(13,'PharmaStore','+92-800-345-6780'),(14,'HealthPlus','+92-800-456-7891'),(15,'Medical Partners','+92-800-567-8902'),(16,'PharmaLink','+92-800-678-9013'),(17,'MedConnect','+92-800-789-0124'),(18,'Healthcare Supplies','+92-800-890-1235'),(19,'PharmaHealth','+92-800-901-2346'),(20,'Medico Supplies','+92-800-012-3457');
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `transaction_id` int NOT NULL AUTO_INCREMENT,
  `transaction_timestamp` datetime DEFAULT NULL,
  `account_id` int DEFAULT NULL,
  `pharmacist_id` int DEFAULT NULL,
  `transaction_amount` double DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `fk_acct` (`account_id`),
  KEY `fk_pharmacist_id2` (`pharmacist_id`),
  CONSTRAINT `fk_acct` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `fk_pharmacist_id2` FOREIGN KEY (`pharmacist_id`) REFERENCES `pharmacists` (`license_no`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,'2024-06-01 10:00:00',1,1,20),(2,'2024-06-01 11:00:00',2,2,15),(3,'2024-06-01 12:00:00',3,3,30),(4,'2024-06-01 13:00:00',4,4,25),(5,'2024-06-01 14:00:00',5,5,10),(6,'2024-06-01 15:00:00',6,6,35),(7,'2024-06-01 16:00:00',7,7,40),(8,'2024-06-01 17:00:00',8,8,45),(9,'2024-06-01 18:00:00',9,9,50),(10,'2024-06-01 19:00:00',10,10,55),(11,'2024-06-01 20:00:00',11,11,60),(12,'2024-06-01 21:00:00',12,12,65),(13,'2024-06-01 22:00:00',13,13,70),(14,'2024-06-01 23:00:00',14,14,75),(15,'2024-06-02 00:00:00',15,15,80),(16,'2024-06-02 01:00:00',16,16,85),(17,'2024-06-02 02:00:00',17,17,90),(18,'2024-06-02 03:00:00',18,18,95),(19,'2024-06-02 04:00:00',19,19,100),(20,'2024-06-02 05:00:00',20,20,105),(21,'2024-06-07 18:23:25',3,4,120),(22,'2024-06-07 19:03:46',4,4,675);
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

