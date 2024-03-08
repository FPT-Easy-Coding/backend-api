CREATE DATABASE  IF NOT EXISTS `quizztoast` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `quizztoast`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: quizztoast
-- ------------------------------------------------------
-- Server version	8.2.0

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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(50) NOT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `total_quiz` int NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Sciencess','2024-02-28 01:59:16.000000',1),(2,'Movie','2024-02-28 01:59:18.000000',1),(3,'Geography','2024-02-28 01:59:19.000000',1),(4,'Literary','2024-02-28 01:59:21.000000',1),(5,'Music','2024-02-28 01:59:23.000000',1),(6,'History','2024-02-28 01:59:24.000000',1),(7,'Inventors','2024-01-28 01:59:26.000000',1),(8,'Sports','2024-01-28 01:59:31.000000',1),(9,'Mythology','2024-01-28 01:59:34.000000',1),(10,'Culinary','2024-02-28 01:59:38.000000',1),(14,'Anima','2024-02-28 13:54:26.738968',0);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class` (
  `class_id` int NOT NULL AUTO_INCREMENT,
  `class_name` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `slug_code` varchar(255) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`class_id`),
  UNIQUE KEY `UK_n1e4rphjfnn8d2gdyrml5f3wi` (`slug_code`),
  KEY `FKhjtee0w5l26pivuxosvmxv0ff` (`user_id`),
  CONSTRAINT `FKhjtee0w5l26pivuxosvmxv0ff` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES (1,'Class cua Duc','2024-02-28 10:05:05.000000','sgfgakfas8121',853);
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_answer`
--

DROP TABLE IF EXISTS `class_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_answer` (
  `class_answer_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(1000) NOT NULL,
  `class_question_id` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`class_answer_id`),
  UNIQUE KEY `UK_i9tdu76af2r27im9deff1e5lg` (`class_question_id`),
  KEY `FKbqbst7v3iovkiqtrwag5r53qc` (`user_id`),
  CONSTRAINT `FKbqbst7v3iovkiqtrwag5r53qc` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKmsanese2cenq9xc30uvd1fiet` FOREIGN KEY (`class_question_id`) REFERENCES `class_question` (`class_question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_answer`
--

LOCK TABLES `class_answer` WRITE;
/*!40000 ALTER TABLE `class_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `class_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_question`
--

DROP TABLE IF EXISTS `class_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_question` (
  `class_question_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `create_at` datetime(6) NOT NULL,
  `is_answered` bit(1) NOT NULL,
  `class_id` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`class_question_id`),
  KEY `FKrt6smfd8s021qq17cgy3yebu8` (`class_id`),
  KEY `FKnfdi1buaovucvf2r4mnjwtake` (`user_id`),
  CONSTRAINT `FKnfdi1buaovucvf2r4mnjwtake` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKrt6smfd8s021qq17cgy3yebu8` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_question`
--

LOCK TABLES `class_question` WRITE;
/*!40000 ALTER TABLE `class_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `class_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(1000) NOT NULL,
  `class_question_id` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FK1t6yti8lprs09oun4nr19oiy4` (`class_question_id`),
  KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
  CONSTRAINT `FK1t6yti8lprs09oun4nr19oiy4` FOREIGN KEY (`class_question_id`) REFERENCES `class_question` (`class_question_id`),
  CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `create_quiz`
--

DROP TABLE IF EXISTS `create_quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `create_quiz` (
  `user_create_id` bigint NOT NULL,
  `quiz_id` int NOT NULL,
  PRIMARY KEY (`quiz_id`,`user_create_id`),
  KEY `FKhegnmcmbo8womnetnlvqd8b0t` (`user_create_id`),
  CONSTRAINT `FK2theoyrk5trtgx0j58dpyhi97` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`quiz_id`),
  CONSTRAINT `FKhegnmcmbo8womnetnlvqd8b0t` FOREIGN KEY (`user_create_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `create_quiz`
--

LOCK TABLES `create_quiz` WRITE;
/*!40000 ALTER TABLE `create_quiz` DISABLE KEYS */;
/*!40000 ALTER TABLE `create_quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `do_exam`
--

DROP TABLE IF EXISTS `do_exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `do_exam` (
  `user_id` bigint NOT NULL,
  `exam_id` int NOT NULL,
  PRIMARY KEY (`exam_id`,`user_id`),
  KEY `FKogt5poy5dn3y806e3vhfukfhc` (`user_id`),
  CONSTRAINT `FKhmp3eo7fih9w8mvtk8ivc17lf` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`exam_id`),
  CONSTRAINT `FKogt5poy5dn3y806e3vhfukfhc` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `do_exam`
--

LOCK TABLES `do_exam` WRITE;
/*!40000 ALTER TABLE `do_exam` DISABLE KEYS */;
/*!40000 ALTER TABLE `do_exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `do_quiz`
--

DROP TABLE IF EXISTS `do_quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `do_quiz` (
  `user_do_id` bigint NOT NULL,
  `quiz_id` int NOT NULL,
  PRIMARY KEY (`quiz_id`,`user_do_id`),
  KEY `FKm9kctcgsgtpjju5th0jtcyufh` (`user_do_id`),
  CONSTRAINT `FKldq1gy62kmex6lfkknfgc4aky` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`quiz_id`),
  CONSTRAINT `FKm9kctcgsgtpjju5th0jtcyufh` FOREIGN KEY (`user_do_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `do_quiz`
--

LOCK TABLES `do_quiz` WRITE;
/*!40000 ALTER TABLE `do_quiz` DISABLE KEYS */;
INSERT INTO `do_quiz` VALUES (853,1);
/*!40000 ALTER TABLE `do_quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam`
--

DROP TABLE IF EXISTS `exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exam` (
  `exam_id` int NOT NULL AUTO_INCREMENT,
  `craeted_id` datetime(6) NOT NULL,
  `state` varchar(255) NOT NULL,
  `user_id` bigint NOT NULL,
  `quiz_id` int DEFAULT NULL,
  `craeted_at` datetime(6) NOT NULL,
  PRIMARY KEY (`exam_id`),
  KEY `FKrnmnt31caqly1yk4yrrvmc763` (`quiz_id`),
  CONSTRAINT `FKrnmnt31caqly1yk4yrrvmc763` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`quiz_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam`
--

LOCK TABLES `exam` WRITE;
/*!40000 ALTER TABLE `exam` DISABLE KEYS */;
/*!40000 ALTER TABLE `exam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exam_belong_class`
--

DROP TABLE IF EXISTS `exam_belong_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exam_belong_class` (
  `class_id` int NOT NULL,
  `exam_id` int NOT NULL,
  PRIMARY KEY (`class_id`,`exam_id`),
  KEY `FKsw79h7uubvdlx70pdw395kvbp` (`exam_id`),
  CONSTRAINT `FKq8q9rbro3xc1i0ogy42h1ep3w` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`),
  CONSTRAINT `FKsw79h7uubvdlx70pdw395kvbp` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`exam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exam_belong_class`
--

LOCK TABLES `exam_belong_class` WRITE;
/*!40000 ALTER TABLE `exam_belong_class` DISABLE KEYS */;
/*!40000 ALTER TABLE `exam_belong_class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `folder` (
  `folder_id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `folder_name` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`folder_id`),
  KEY `FK5fd2civdi8s832iyrufpk400k` (`user_id`),
  CONSTRAINT `FK5fd2civdi8s832iyrufpk400k` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder`
--

LOCK TABLES `folder` WRITE;
/*!40000 ALTER TABLE `folder` DISABLE KEYS */;
INSERT INTO `folder` VALUES (1,'2024-02-26 22:51:40.000000','Test1 ',853),(2,'2024-02-26 22:52:12.000000','Test2',853);
/*!40000 ALTER TABLE `folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder_seq`
--

DROP TABLE IF EXISTS `folder_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `folder_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder_seq`
--

LOCK TABLES `folder_seq` WRITE;
/*!40000 ALTER TABLE `folder_seq` DISABLE KEYS */;
INSERT INTO `folder_seq` VALUES (151);
/*!40000 ALTER TABLE `folder_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `image_id` int NOT NULL AUTO_INCREMENT,
  `webp_img` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`image_id`),
  UNIQUE KEY `UK_l8oylel56xvt5huamif0tfhkh` (`user_id`),
  CONSTRAINT `FKlxnnh0ir05khn8iu9tgwh1yyk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marketing`
--

DROP TABLE IF EXISTS `marketing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `marketing` (
  `content` varchar(1000) NOT NULL,
  `embed_link` varchar(255) NOT NULL,
  `image_id` int NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`image_id`),
  KEY `FKq2yk9scap94mpcprxitpkaf3n` (`user_id`),
  CONSTRAINT `FK1w9ih0dvqv21xnefre4wib3tj` FOREIGN KEY (`image_id`) REFERENCES `image` (`image_id`),
  CONSTRAINT `FKq2yk9scap94mpcprxitpkaf3n` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marketing`
--

LOCK TABLES `marketing` WRITE;
/*!40000 ALTER TABLE `marketing` DISABLE KEYS */;
/*!40000 ALTER TABLE `marketing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password_reset_token`
--

DROP TABLE IF EXISTS `password_reset_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `password_reset_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiration_time` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_f90ivichjaokvmovxpnlm5nin` (`user_id`),
  CONSTRAINT `FK5lwtbncug84d4ero33v3cfxvl` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password_reset_token`
--

LOCK TABLES `password_reset_token` WRITE;
/*!40000 ALTER TABLE `password_reset_token` DISABLE KEYS */;
INSERT INTO `password_reset_token` VALUES (15,'2024-02-28 17:50:17.763000','d894e6f0-25c6-4429-b034-9a48887e30e9',853);
/*!40000 ALTER TABLE `password_reset_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `method` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan`
--

DROP TABLE IF EXISTS `plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan` (
  `plan_id` int NOT NULL AUTO_INCREMENT,
  `plan_name` varchar(255) NOT NULL,
  `plan_price` int NOT NULL,
  PRIMARY KEY (`plan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan`
--

LOCK TABLES `plan` WRITE;
/*!40000 ALTER TABLE `plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_transaction_tracking`
--

DROP TABLE IF EXISTS `plan_transaction_tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan_transaction_tracking` (
  `date` date NOT NULL,
  `transaction_id` int NOT NULL,
  PRIMARY KEY (`date`,`transaction_id`),
  KEY `FK2s9y9byuwrh8ht4m88vfw9d7n` (`transaction_id`),
  CONSTRAINT `FK2s9y9byuwrh8ht4m88vfw9d7n` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_transaction_tracking`
--

LOCK TABLES `plan_transaction_tracking` WRITE;
/*!40000 ALTER TABLE `plan_transaction_tracking` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_transaction_tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz` (
  `quiz_id` int NOT NULL AUTO_INCREMENT,
  `class_id` int NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `quiz_name` varchar(255) NOT NULL,
  `quiz_ques_id` int NOT NULL,
  `rate` double NOT NULL,
  `category_id` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `number_of_quiz` int NOT NULL,
  `number_of_quizquestion` int NOT NULL,
  `time_recent_view_quiz` datetime(6) DEFAULT NULL,
  `view` bigint DEFAULT NULL,
  PRIMARY KEY (`quiz_id`),
  KEY `FK82x9fxd5tsbb3i1ewrp3cr8xa` (`category_id`),
  KEY `FK1tofsm1qynhakggx7ttqh8ihu` (`user_id`),
  CONSTRAINT `FK1tofsm1qynhakggx7ttqh8ihu` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK82x9fxd5tsbb3i1ewrp3cr8xa` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz`
--

LOCK TABLES `quiz` WRITE;
/*!40000 ALTER TABLE `quiz` DISABLE KEYS */;
INSERT INTO `quiz` VALUES (1,1,'1970-04-02 11:21:03.000000','Science Wonders',1,4.5,1,652,0,2,NULL,60),(2,2,'1992-03-31 01:49:57.000000','Movie Buff Challenge',2,4,2,652,0,0,NULL,1),(3,3,'1975-06-26 21:11:22.000000','Geography Explorer',3,5,3,652,0,0,NULL,0),(4,4,'1957-03-02 18:20:25.000000','Literary Classics',4,3,4,652,0,0,NULL,0),(5,5,'2046-07-06 03:07:05.000000','Music Maestro',5,2,5,652,0,0,NULL,0),(6,6,'2032-08-24 11:25:53.000000','History Time Machine',6,2.5,6,652,0,0,NULL,0),(7,7,'1958-04-19 04:37:25.000000','Famous Inventors',7,3,7,652,0,0,NULL,0),(8,8,'1929-05-11 16:58:37.000000','Sports Fanatic',8,4,8,652,0,0,NULL,0),(9,9,'1947-01-21 23:52:25.000000','Mythology Mysteries',9,4.5,9,652,0,0,NULL,0),(10,10,'2005-02-17 10:20:13.000000','Culinary Connoisseur',10,3,10,853,0,0,NULL,0);
/*!40000 ALTER TABLE `quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_answer`
--

DROP TABLE IF EXISTS `quiz_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_answer` (
  `quiz_answer_id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `is_correct` bit(1) NOT NULL,
  `quiz_question_id` bigint DEFAULT NULL,
  PRIMARY KEY (`quiz_answer_id`),
  KEY `FK1tfpvs7phj0firv5btt04yrsd` (`quiz_question_id`),
  CONSTRAINT `FK1tfpvs7phj0firv5btt04yrsd` FOREIGN KEY (`quiz_question_id`) REFERENCES `quiz_question` (`quiz_question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_answer`
--

LOCK TABLES `quiz_answer` WRITE;
/*!40000 ALTER TABLE `quiz_answer` DISABLE KEYS */;
INSERT INTO `quiz_answer` VALUES (1,'Au','2024-01-23 10:13:27.466647',_binary '',1),(2,'Ag','2024-01-23 10:13:27.466647',_binary '\0',1),(3,'David Prowse','2024-01-23 10:25:45.652079',_binary '',2),(4,'James Earl Jones','2024-01-23 10:25:45.652079',_binary '\0',2),(5,'Sydney','2024-01-23 10:25:56.360883',_binary '\0',3),(6,'Canberra','2024-01-23 10:25:56.360883',_binary '',3),(7,'Jane Austen','2024-01-23 10:27:49.596625',_binary '',4),(8,'Charles Dickens','2024-01-23 10:27:49.596625',_binary '\0',4),(9,'Pink Floyd','2024-01-23 11:47:48.707868',_binary '',5),(10,'The Beatles','2024-01-23 11:47:48.707868',_binary '\0',5),(11,'George Washington','2024-01-23 11:49:17.393576',_binary '',6),(12,'Thomas Jefferson','2024-01-23 11:49:17.393576',_binary '\0',6),(13,'mock 1 ','2024-02-27 16:34:47.000000',_binary '',7),(14,'mock 2','2024-02-27 16:35:07.000000',_binary '\0',7),(15,'mock 3','2024-02-27 16:35:39.000000',_binary '',8),(16,'mock 4','2024-02-27 16:36:08.000000',_binary '\0',8),(17,'mock 5','2024-02-27 16:36:17.000000',_binary '',9),(18,'mock 6','2024-02-27 16:36:27.000000',_binary '\0',9),(19,'mock 7','2024-02-27 16:36:41.000000',_binary '',10),(20,'mock 8','2024-02-27 16:37:00.000000',_binary '\0',10);
/*!40000 ALTER TABLE `quiz_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_belong_class`
--

DROP TABLE IF EXISTS `quiz_belong_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_belong_class` (
  `class_id` int NOT NULL,
  `quiz_id` int NOT NULL,
  PRIMARY KEY (`class_id`,`quiz_id`),
  KEY `FKs5ip0i11rjfp222h2fpy39ymm` (`quiz_id`),
  CONSTRAINT `FK2j6hk4e069mt92bgeuw6qqx02` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`),
  CONSTRAINT `FKs5ip0i11rjfp222h2fpy39ymm` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`quiz_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_belong_class`
--

LOCK TABLES `quiz_belong_class` WRITE;
/*!40000 ALTER TABLE `quiz_belong_class` DISABLE KEYS */;
INSERT INTO `quiz_belong_class` VALUES (1,1);
/*!40000 ALTER TABLE `quiz_belong_class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_belong_folder`
--

DROP TABLE IF EXISTS `quiz_belong_folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_belong_folder` (
  `folder_id` bigint NOT NULL,
  `quiz_id` int NOT NULL,
  PRIMARY KEY (`folder_id`,`quiz_id`),
  KEY `FKcvi20dvby2sdor184n20w7bx6` (`quiz_id`),
  CONSTRAINT `FKcvi20dvby2sdor184n20w7bx6` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`quiz_id`),
  CONSTRAINT `FKmrb348ihnpb41wyi58drfiwww` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`folder_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_belong_folder`
--

LOCK TABLES `quiz_belong_folder` WRITE;
/*!40000 ALTER TABLE `quiz_belong_folder` DISABLE KEYS */;
INSERT INTO `quiz_belong_folder` VALUES (1,1),(1,2);
/*!40000 ALTER TABLE `quiz_belong_folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_question`
--

DROP TABLE IF EXISTS `quiz_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_question` (
  `quiz_question_id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(1000) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`quiz_question_id`),
  KEY `FKademupmkualcdnl7k8im0m6uo` (`category_id`),
  CONSTRAINT `FKademupmkualcdnl7k8im0m6uo` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_question`
--

LOCK TABLES `quiz_question` WRITE;
/*!40000 ALTER TABLE `quiz_question` DISABLE KEYS */;
INSERT INTO `quiz_question` VALUES (1,'What is the chemical symbol for gold?','1970-04-02 11:21:03.000000',1),(2,'Who played the iconic role of Darth Vader in Star Wars?','1992-03-31 01:49:57.000000',2),(3,'What is the capital city of Australia?','1975-06-26 21:11:22.000000',3),(4,'Who wrote the novel \"Pride and Prejudice\"?','1957-03-02 18:20:25.000000',4),(5,'Which rock band released the album \"The Wall\" in 1979?','2046-07-06 03:07:05.000000',5),(6,'Who was the first President of the United States?','2032-08-24 11:25:53.000000',6),(7,'Who is credited with inventing the telephone?','1958-04-19 04:37:25.000000',7),(8,'In which sport would you perform a slam dunk?','1929-05-11 16:58:37.000000',8),(9,'Who is the Greek god of the sea?','1947-01-21 23:52:25.000000',9),(10,'What is the main ingredient in the Italian dish risotto?','2005-02-17 10:20:13.000000',10);
/*!40000 ALTER TABLE `quiz_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_question_mapping`
--

DROP TABLE IF EXISTS `quiz_question_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_question_mapping` (
  `quiz_question_id` bigint NOT NULL,
  `quiz_id` int NOT NULL,
  PRIMARY KEY (`quiz_id`,`quiz_question_id`),
  KEY `FKark44wojr8tj4rjskq52badr9` (`quiz_question_id`),
  CONSTRAINT `FKark44wojr8tj4rjskq52badr9` FOREIGN KEY (`quiz_question_id`) REFERENCES `quiz_question` (`quiz_question_id`),
  CONSTRAINT `FKqk18eljnxagh5bkvcoonr2wp3` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`quiz_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_question_mapping`
--

LOCK TABLES `quiz_question_mapping` WRITE;
/*!40000 ALTER TABLE `quiz_question_mapping` DISABLE KEYS */;
INSERT INTO `quiz_question_mapping` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1);
/*!40000 ALTER TABLE `quiz_question_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_rating`
--

DROP TABLE IF EXISTS `quiz_rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_rating` (
  `create_at` datetime(6) NOT NULL,
  `rate` float NOT NULL,
  `user_id` bigint NOT NULL,
  `quiz_id` int NOT NULL,
  PRIMARY KEY (`quiz_id`,`user_id`),
  KEY `FKi19tnd64mvnk3mghvh8p9whcn` (`user_id`),
  KEY `FKoaenn55efebr70dy9oebw5iu2` (`quiz_id`),
  CONSTRAINT  FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT  FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`quiz_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_rating`
--

LOCK TABLES `quiz_rating` WRITE;
/*!40000 ALTER TABLE `quiz_rating` DISABLE KEYS */;
/*!40000 ALTER TABLE `quiz_rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token` (
  `token_id` int NOT NULL AUTO_INCREMENT,
  `token` varchar(255) DEFAULT NULL,
  `expired` tinyint(1) DEFAULT NULL,
  `revoked` tinyint(1) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `token_type` enum('BEARER') DEFAULT NULL,
  PRIMARY KEY (`token_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `token_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5003 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
INSERT INTO `token` VALUES (2053,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjUwOTQwMSwiZXhwIjoxNzA2NTk1ODAxfQ.Kt2s7J5XNwu5OGjl7tcWvRwGOJx8acby7ih2kHCPjgc',1,1,652,'BEARER'),(2054,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjUxMDQ3MCwiZXhwIjoxNzA2NTk2ODcwfQ.dsNA2grZfv96wNMvws4L4_elWggo2cZ-bIBWBSp-Db8',1,1,652,'BEARER'),(2055,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjUxMDc0NywiZXhwIjoxNzA2NTk3MTQ3fQ._p_5binAgzgC3J65vRDLvZCXYk-o92gQyqseLoBkm5k',1,1,652,'BEARER'),(2056,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjUxNzUzMCwiZXhwIjoxNzA2NjAzOTMwfQ.p5MT_Xt_pkpVNn5Jt_pHtSGM3UWVJC3geEBHyP4aZ4k',1,1,652,'BEARER'),(2057,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXRAZ21haWwuY29tIiwiaWF0IjoxNzA2NTE5MDUwLCJleHAiOjE3MDY2MDU0NTB9.a11sMenm5XjhuUp6EJXYbrqTmhhbZvFB8aYH-DfpJMo',1,1,752,'BEARER'),(2058,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXRAZ21haWwuY29tIiwiaWF0IjoxNzA2NTE5NTU4LCJleHAiOjE3MDY2MDU5NTh9.GAeTe6MXvHm9zbdsu9hg-rH3smigmd_wCkbNTcZG6C8',1,1,752,'BEARER'),(2059,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjUxOTk4OSwiZXhwIjoxNzA2NjA2Mzg5fQ.AbLQ3L5gsrzP5ipx9bF8DEgvDNgtZvjTzN8ws0-qy-U',1,1,652,'BEARER'),(2060,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjUyMTMzNCwiZXhwIjoxNzA2NjA3NzM0fQ.76fMSyf4-uyNUVaV1WpnVAQCONc7dTgPbVKGXeFSvVI',1,1,652,'BEARER'),(2061,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjUyMTM3NCwiZXhwIjoxNzA2NjA3Nzc0fQ.QmZ7OWdtC_ePt_B93jJYfoQSOAYBvD7NafDouTXWHT4',1,1,652,'BEARER'),(2153,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYwODM3MCwiZXhwIjoxNzA2Njk0NzcwfQ.noi3kJ_nNoiY06JwYypzCEOC_4FkXuluJzs9qwOnIrA',1,1,652,'BEARER'),(2154,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYwODM3MiwiZXhwIjoxNzA2Njk0NzcyfQ.Pz9WDUQHD5Gabe-qFkW8BOOQDtzcgXT7GDWmY-j1PqI',1,1,652,'BEARER'),(2155,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYwODM3MiwiZXhwIjoxNzA2Njk0NzcyfQ.Pz9WDUQHD5Gabe-qFkW8BOOQDtzcgXT7GDWmY-j1PqI',1,1,652,'BEARER'),(2156,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYwODM3MywiZXhwIjoxNzA2Njk0NzczfQ.eHyt4a90BUkBBwAE5BcCkqZiobyDsZWFQUr4AF0vHYw',1,1,652,'BEARER'),(2157,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYwODM3MywiZXhwIjoxNzA2Njk0NzczfQ.eHyt4a90BUkBBwAE5BcCkqZiobyDsZWFQUr4AF0vHYw',1,1,652,'BEARER'),(2158,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYwODM3MywiZXhwIjoxNzA2Njk0NzczfQ.eHyt4a90BUkBBwAE5BcCkqZiobyDsZWFQUr4AF0vHYw',1,1,652,'BEARER'),(2159,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYwODQyNCwiZXhwIjoxNzA2Njk0ODI0fQ.AbH3TFtYIu6nYwceP_YxrcjxkII5aTpjGmIEixtZk6s',1,1,652,'BEARER'),(2160,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYwODQ2NCwiZXhwIjoxNzA2Njk0ODY0fQ.MvwxdPMHzF51heRYRRzxZF00m0rB9gkWuFJam-RLAQU',1,1,652,'BEARER'),(2161,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYwODQ4OSwiZXhwIjoxNzA2Njk0ODg5fQ.U8nJtRjt2QbNhuMipSl2qNflFaUuLB9PxrdepVHk0fw',1,1,652,'BEARER'),(2162,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYwODU1OCwiZXhwIjoxNzA2Njk0OTU4fQ.brhWPtcXXjZzvOAgpmTqZERU9XoCtc1twVZNNi_qe20',1,1,652,'BEARER'),(2163,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYxMDc5NywiZXhwIjoxNzA2Njk3MTk3fQ.ESKhWtxv3_FQ759GhVQBVl7USLOj6E0t6peUk1lyn1U',1,1,652,'BEARER'),(2164,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYxMDgxNCwiZXhwIjoxNzA2Njk3MjE0fQ.UktOZDN_EUln9Kw28XYx_P7ynmALvH7gzxujHzqwUXI',1,1,652,'BEARER'),(2165,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYxMTczNSwiZXhwIjoxNzA2Njk4MTM1fQ.p7w7HIlrbIdKwM115WLloa23bAXBxZ4BRkf06I45fzA',1,1,652,'BEARER'),(2166,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYxMTczOCwiZXhwIjoxNzA2Njk4MTM4fQ.LzscAWs8pcVBA77gw6gu7joeTRQ6kcAh238nHLiCyaI',1,1,652,'BEARER'),(2167,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYxMTg5NywiZXhwIjoxNzA2Njk4Mjk3fQ.lhaYgsAgGAjjV31BIU-iP6pKw6i6_ul0mLr6Qs1zmMw',1,1,652,'BEARER'),(2168,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYxMTg5OSwiZXhwIjoxNzA2Njk4Mjk5fQ.b9RVE9BLn0JelkY_79Uojlp-jpRsi90mo23YVRROUH8',1,1,652,'BEARER'),(2202,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYyMTk3MCwiZXhwIjoxNzA2NzA4MzcwfQ.4eO5SWuTsFRPGIwlsX8BlZinsBTWZjXIma3wJKJOxO4',1,1,652,'BEARER'),(2252,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYyMzQzMywiZXhwIjoxNzA2NzA5ODMzfQ.9z18XbPBey_1FJmFHF_HYfDaZaIpQZ2jhhEb9JKoPmU',1,1,652,'BEARER'),(2253,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYyMzQzNywiZXhwIjoxNzA2NzA5ODM3fQ.VCuiI10HoGSL1ALju9JIMp4ZF6J4ad-LkMQU-Xsi5sg',1,1,652,'BEARER'),(2254,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYyMzQ0MiwiZXhwIjoxNzA2NzA5ODQyfQ.Ol1u7oeO_3aOrr2oehSh4scFs7j37PG_CIz4pPov-MY',1,1,652,'BEARER'),(2255,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYyODA2OCwiZXhwIjoxNzA2NzE0NDY4fQ.2Y5E2xduil_F1Qikc2ArIb9WOhSizrdTYvYt23olyXo',1,1,652,'BEARER'),(2256,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYzMzM4NCwiZXhwIjoxNzA2NzE5Nzg0fQ.mUdPIK7hRGdhI_dGQ-tvDaoqdZsK1fbalcSh3BW-qQk',1,1,652,'BEARER'),(2257,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYzNDM0NSwiZXhwIjoxNzA2NzIwNzQ1fQ.IuAgO3wO6dCbbG3B0FwJBo_uAEWtSm84xcXdMcRjAMg',1,1,652,'BEARER'),(2258,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYzNDQzMSwiZXhwIjoxNzA2NzIwODMxfQ.FBuMQkyiEJEH3Covg463UZ4ChOetW0Efz6qWf2c4N44',1,1,652,'BEARER'),(2259,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjYzNDcxMiwiZXhwIjoxNzA2NzIxMTEyfQ.tcVhFgfy1QTYJ52Dxlid_tdCnuiJf3qCSvlLVXCkccM',1,1,652,'BEARER'),(2553,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjY2NTY5NCwiZXhwIjoxNzA2NzUyMDk0fQ.qGRQsJbvgWdPTgSkihE1FWzcbKE4hsEG3T_IWuDu5Ik',1,1,652,'BEARER'),(2602,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjY2NTg1NSwiZXhwIjoxNzA2NzUyMjU1fQ.KkgB8lxnYQnYeF7EiU3DfDqlFGrdruacAt4BPkt8YJE',1,1,652,'BEARER'),(2603,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTcwNjY2ODc5NiwiZXhwIjoxNzA2NzU1MTk2fQ.Jxe4ZcNfQsfGj7ereYAckZ85C-VuRs1JJ7nTp71qZcE',0,0,652,'BEARER'),(3052,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXRAZ21haWwuY29tIiwiaWF0IjoxNzA2ODUzOTc5LCJleHAiOjE3MDY5NDAzNzl9.EkjILXp8eIvRiU3TEoqjzVgzyzDwgu7qMJEor6TgzfI',1,1,752,'BEARER'),(3053,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXRAZ21haWwuY29tIiwiaWF0IjoxNzA2ODUzOTgyLCJleHAiOjE3MDY5NDAzODJ9.GiHN0Q6qS6NgqhaaFJDtA3Ahh01zwpb_RLdhszZB7n4',1,1,752,'BEARER'),(3054,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXRAZ21haWwuY29tIiwiaWF0IjoxNzA2ODUzOTg2LCJleHAiOjE3MDY5NDAzODZ9.PaYI66a89GoO4kLsWBpOLYerM0uHDULoUT_iIEP8H2k',1,1,752,'BEARER'),(3253,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXRAZ21haWwuY29tIiwiaWF0IjoxNzA3MTQ5MzY5LCJleHAiOjE3MDcyMzU3Njl9.n3lK8sCmDrg00xVEyaDleRC03zRuaa7DHmAFhm4LyyM',1,1,752,'BEARER'),(3254,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXRAZ21haWwuY29tIiwiaWF0IjoxNzA3MTQ5OTkyLCJleHAiOjE3MDcyMzYzOTJ9.v0jexwPMM1ItD9_OD8dBtXkEb0msteD4QNQRR1pUurs',1,1,752,'BEARER'),(3255,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXRAZ21haWwuY29tIiwiaWF0IjoxNzA3MTU5MTQ4LCJleHAiOjE3MDcyNDU1NDh9.LQ4qWxIDWY-YyIPOowjHQQ_nPBqh_JiXoYtRA0fTGPg',1,1,752,'BEARER'),(3256,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXRAZ21haWwuY29tIiwiaWF0IjoxNzA3MTYwNDM3LCJleHAiOjE3MDcyNDY4Mzd9.vQh7wN-cYI_WQiAw7jCXVqvCih6r6WjooOj9HuOEDQE',1,1,752,'BEARER'),(3257,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA3MTYwNDQyLCJleHAiOjE3MDcyNDY4NDJ9.LOYQJ2XzX0Kx8H2npz0QyE1-zhzitPoBeanCX-FCrug',1,1,853,'BEARER'),(3258,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA3MTYwNDYyLCJleHAiOjE3MDcyNDY4NjJ9.5Lc_126m1ukYfcDiOkW3_fqeZOOK8e44CVvHCpW01SU',1,1,853,'BEARER'),(3259,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA3MTYwNDY2LCJleHAiOjE3MDcyNDY4NjZ9.bYQK8ceV7t9rIgqRYrIykM11xqkCUH4SPMprW943-z4',1,1,853,'BEARER'),(3260,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA3MTYwNDg3LCJleHAiOjE3MDcyNDY4ODd9.a3evCp9kmlzcXjNqv5DdtR4jlWRoAcem_NBvf2Q5oio',1,1,853,'BEARER'),(3261,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXRAZ21haWwuY29tIiwiaWF0IjoxNzA3MTYwNDkwLCJleHAiOjE3MDcyNDY4OTB9.czAbeDfN7p1iW4WnYw3k1Dy_rUBcxCbaZPY3cfz-OmI',1,1,752,'BEARER'),(3262,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXRAZ21haWwuY29tIiwiaWF0IjoxNzA3MTYwNTA2LCJleHAiOjE3MDcyNDY5MDZ9.7MJ8i1F97x6yXU-_egF5_t1Af1luJcvAJuK90lLD8bc',1,1,752,'BEARER'),(3263,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA3MTYwNTE1LCJleHAiOjE3MDcyNDY5MTV9.O494nZvQIzuWTZ8Wk7LMOzHjSQzER6ul0rx4EA8jwHY',1,1,853,'BEARER'),(3302,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxdXluZEBnbWFpbC5jb20iLCJpYXQiOjE3MDcyMTU5MzQsImV4cCI6MTcwNzMwMjMzNH0.8KLPx24H_D8Tf3BXdHXaQAhWHaV3M5lg75gpMe-d1dE',1,1,902,'BEARER'),(3303,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxdXluZEBnbWFpbC5jb20iLCJpYXQiOjE3MDcyMTU5MzgsImV4cCI6MTcwNzMwMjMzOH0.4jgCjUth-ReuDt2LdpkZ9PvW-rYzlQeVw3O0iNPZMLg',0,0,902,'BEARER'),(3304,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA3MjE2MDUwLCJleHAiOjE3MDczMDI0NTB9.y7mt4LmVLwhkNTfEphCTe1qkZTjDdswhu2rn2-dAwdc',1,1,853,'BEARER'),(3305,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXRAZ21haWwuY29tIiwiaWF0IjoxNzA3MjE2MDY4LCJleHAiOjE3MDczMDI0Njh9.3TfRbkqATtmBymXAEf8ch15DWSgd1tC5-Ewp4wtig88',0,0,752,'BEARER'),(3306,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA3MjE2MjQ2LCJleHAiOjE3MDczMDI2NDZ9.N7C5pyFWJ4q69JTStNd-fWKN-m4mWgKrXooYfcxblFQ',1,1,853,'BEARER'),(3307,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA3MjE2MjU2LCJleHAiOjE3MDczMDI2NTZ9.LvAhvL1-I5o2H_DST9p-5qg0_NA6j8luUzqsK5hdQTU',1,1,853,'BEARER'),(3308,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA3MjE2OTM4LCJleHAiOjE3MDczMDMzMzh9.0h1xANQUXpd--t3NsmhrXXkclH6OGoi2SmVbE3C6QuY',1,1,853,'BEARER'),(3352,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA3MzAwMzMxLCJleHAiOjE3MDczODY3MzF9.FywZlLF0hc-oP7ixszz2EqwpEvkpBU8o94SaEmMi5PI',1,1,853,'BEARER'),(3402,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA3MzI1MDA3LCJleHAiOjE3MDc0MTE0MDd9.7FGTITtzBcwyIuLbXz8Q4Z7i9zB9BHjvZhyBOZg12gM',1,1,853,'BEARER'),(3403,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA3MzI1MzExLCJleHAiOjE3MDc0MTE3MTF9.bH39a5GZ-8WVOIWWS0pd3PDBdpKeeG2Elt4VLP6ZiHY',1,1,853,'BEARER'),(3404,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA3MzI1ODgxLCJleHAiOjE3MDc0MTIyODF9.iPIycsqBXRwIeFx2--b3u76xhzPRBsemSaVwaVjT5pg',1,1,853,'BEARER'),(3502,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA3NDEwMzM1LCJleHAiOjE3MDc0OTY3MzV9.130hxCQk6sImzaUGtnbEqjAGZPbzFKnasInN2N5TmxU',1,1,853,'BEARER'),(3552,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA3NjIwMTk5LCJleHAiOjE3MDc3MDY1OTl9.bgEzSb2XExGUp1FoY3tNyGAxkbILieqdh2INGNRixWk',1,1,853,'BEARER'),(3602,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA3OTIwMTgxLCJleHAiOjE3MDgwMDY1ODF9.HGiYNqeLf7C40o-GnDWEsTh7dp7EG7eM2T-RsXTm4kc',1,1,853,'BEARER'),(3652,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA4MDg5NTI0LCJleHAiOjE3MDgxNzU5MjR9.rXfS1tGdSKONTTM_nTJgW0WkJ4b7if6FkTMa6XJwt7Y',1,1,853,'BEARER'),(3702,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA4MjQ3ODU1LCJleHAiOjE3MDgzMzQyNTV9.Xx-Dk4NSg27WmHnd5RjG4JLHh5lpgRnUjs2cJiUGXb8',1,1,853,'BEARER'),(3752,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA4MjQ4Mjc4LCJleHAiOjE3MDgzMzQ2Nzh9.VPsVRGk1GKfnTrNrtXqkN9uo0_G-VwD0KcHclxPStxA',1,1,853,'BEARER'),(3753,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA4MjQ5MDk1LCJleHAiOjE3MDgzMzU0OTV9.K2KUUUwa9ZvR2LXF5eipXrkfS1YfOfs1Vl4lYUCX2CY',1,1,853,'BEARER'),(3802,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA4MjUwNTYwLCJleHAiOjE3MDgzMzY5NjB9.l_Mn1bq5TL7aiYeStp3_xriCGsMqX0Lr13Q6lTTBAzQ',1,1,853,'BEARER'),(3852,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA4MjU1OTc1LCJleHAiOjE3MDgzNDIzNzV9.PJ78pb6vMbxf565jx1AuQ6BPF1qwacZagYWIV2Ya2h0',1,1,853,'BEARER'),(3902,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA4MjY4MjQzLCJleHAiOjE3MDgzNTQ2NDN9.Jd_1WCSAStRwmLi9IbVSWsyBXkkW6QDC0IwjIPhO94w',1,1,853,'BEARER'),(3952,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA4MzUwODg5LCJleHAiOjE3MDg0MzcyODl9.XFLoPf6BTuWzc2wDTe-a60Sk9EvtuwKCcXrSojVkysA',1,1,853,'BEARER'),(3953,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA4MzUwODk1LCJleHAiOjE3MDg0MzcyOTV9.mwQslF744KKamI00UO7ARl_itaDtPvBX0b0kc2Lci5Q',1,1,853,'BEARER'),(4002,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA4NDUxMjE3LCJleHAiOjE3MDg1Mzc2MTd9.cVmIqTt0onFZE94hN0g0ptKvqduuOUzP2Z7a9cr8Lf4',1,1,853,'BEARER'),(4052,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA4NDg1Mjg3LCJleHAiOjE3MDg1NzE2ODd9.o7CF1QsJCKlhd4A7OsLoTqHzLfe-E4YEyeT6BC_y04M',1,1,853,'BEARER'),(4102,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA4NTAzMDQwLCJleHAiOjE3MDg1ODk0NDB9.7qdGhTLFeEsvXAK71vzhsOiJKdiqOEZsPzv6jETm1Lw',1,1,853,'BEARER'),(4152,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA4NTA5MjY1LCJleHAiOjE3MDg1OTU2NjV9.AF2SQ5NtLgGImRTzSPA-ICqrJd4Jr_TZQ-ZYMwoDbws',1,1,853,'BEARER'),(4202,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA4NjEzNTY1LCJleHAiOjE3MDg2OTk5NjV9.2FaUduS3TeqkAvyj41Ufff6HpXFazMhDYg5e_7ZKjSY',1,1,853,'BEARER'),(4203,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA4NjEzNzAwLCJleHAiOjE3MDg3MDAxMDB9.28_0Lt8OpjeAR2KuItWdQ3eJp5hvpdyW2rqwkHbbjRM',1,1,853,'BEARER'),(4252,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA4Nzg3MTQ5LCJleHAiOjE3MDg4NzM1NDl9.5U_yf6eSGlZ43mjuTdAAPkGBzBsJhlNVqaKPUa0viTE',1,1,853,'BEARER'),(4302,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA4ODc4MDc5LCJleHAiOjE3MDg5NjQ0Nzl9.AvIN8xmO-KSZjHqfLpkDdCl14bS-8L4T2fhAzORilZQ',1,1,853,'BEARER'),(4352,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA4OTYyNzIwLCJleHAiOjE3MDkwNDkxMjB9._Jhed1Yiw6tPt7w2oqN9gR9NR89xcyidUPBA8eYfIjQ',1,1,853,'BEARER'),(4402,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5MDMzMTcwLCJleHAiOjE3MDkxMTk1NzB9.xJFoSMZgvzfPPqZZNAeG2UwlcNjah_QUBIROLfBsoys',1,1,853,'BEARER'),(4452,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5MDM2OTk3LCJleHAiOjE3MDkxMjMzOTd9.p8BXTWpK3Qu3hHriNkPnIngOii4l9SNs_wvY83eIFrE',1,1,853,'BEARER'),(4502,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5MDQ4OTIxLCJleHAiOjE3MDkxMzUzMjF9.UBopK3vM6itMSHxDmOXvvhTGdWaweJyXrJXUQ_nCVS4',1,1,853,'BEARER'),(4503,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5MDQ5MzMwLCJleHAiOjE3MDkxMzU3MzB9.yQOyzUj0JAq6NgQtx-CFdFuR8jobS65qPOYk8G0AT1c',1,1,853,'BEARER'),(4504,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5MDUyNTkzLCJleHAiOjE3MDkxMzg5OTN9.18AylOrhJqUBp51lL44kGSPveie58LwW1e2VOyMzkrs',1,1,853,'BEARER'),(4505,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5MDYzOTIwLCJleHAiOjE3MDkxNTAzMjB9.k4R6XC8WDIrlRTgRid6mlI0_ObnhXTVdKbT0fi7eNY4',1,1,853,'BEARER'),(4506,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5MDYzOTI0LCJleHAiOjE3MDkxNTAzMjR9.Aqib8FOF9gxFJwIZa9jD68YrIMzaqRoIFadexCMFNNc',1,1,853,'BEARER'),(4552,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5MDgzMzg4LCJleHAiOjE3MDkxNjk3ODh9.nMubCgznNJ3N9aEEQBcDvnn4biJ-kP9KdkszV4yncI0',1,1,853,'BEARER'),(4602,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5MTAwNzU4LCJleHAiOjE3MDkxODcxNTh9.q1OL0NGSlmIz_LyxmCfjkF08fZy-GleKYsZuWKdHwV8',1,1,853,'BEARER'),(4603,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5MTAzNTk4LCJleHAiOjE3MDkxODk5OTh9.yy2CJxjLZix6DPElFcYctTCnm_HLSQHzlHYiQWrxSwA',1,1,853,'BEARER'),(4604,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5MTA0MDgwLCJleHAiOjE3MDkxOTA0ODB9.ihjd7yWmFbkRF702UYifWFzc9ZMLjj7jS-ZZ91tARu8',1,1,853,'BEARER'),(4652,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5MTE2NTYyLCJleHAiOjE3MDkyMDI5NjJ9.U9vhjkOxFDacPYwX9p6WFcf9qP_frnRL47SFm1Z-Eoo',1,1,853,'BEARER'),(4702,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5NDg0NDQyLCJleHAiOjE3MDk1NzA4NDJ9.w2mfjGbgBNaempsxhhe6PtmzOh81RYfsPG_FGmM79IY',1,1,853,'BEARER'),(4752,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5NjU2NzMxLCJleHAiOjE3MDk3NDMxMzF9.DOsbNFPGP5_06wCmLjZFfHI2oZ33CSFBFdZyp2-qhV0',1,1,853,'BEARER'),(4753,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5NjU3NzQ4LCJleHAiOjE3MDk3NDQxNDh9.feK1e-DdgMGyBPuoA8hPCFIRk4Jg7tZhGz5moFv32I4',1,1,853,'BEARER'),(4802,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5Njg5NDg1LCJleHAiOjE3MDk3NzU4ODV9.NYILQSVtBtEqtOd4IL3e7fossU86wAa0w1O4rc-YWVg',1,1,853,'BEARER'),(4803,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5Njg5ODI4LCJleHAiOjE3MDk3NzYyMjh9.ALHMtU-_4zX8F08GwfnpqbVVkGuCZkix7kr-6RPrh6U',1,1,853,'BEARER'),(4804,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5NjkwMDYxLCJleHAiOjE3MDk3NzY0NjF9.MgWa_jeGK_DTl9ObP-3-RRvWalefbRFC5xER-n06RaQ',1,1,853,'BEARER'),(4805,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5NjkwMjQ0LCJleHAiOjE3MDk3NzY2NDR9.5y-oRhLiD7XwtmHPddHVT9bMdFOvoU0CHNDBn3pQyMs',1,1,853,'BEARER'),(4852,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5NzA2MjA1LCJleHAiOjE3MDk3OTI2MDV9.ZeTm88AL1Y3H8kwokxnYJgYGJdPz4xu4t5_xyq3vYh8',1,1,853,'BEARER'),(4902,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5NzUxMzQ1LCJleHAiOjE3MDk4Mzc3NDV9.-HFn081JFigbgDoTSTpcIl8m3hMGizGY1ib_G8iIGKA',1,1,853,'BEARER'),(4952,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5ODA0Njg5LCJleHAiOjE3MDk4OTEwODl9.YAXG7fZYOFj9JA76A3sCNsz3d0bvLnOOaG9-8cYcH3w',1,1,853,'BEARER'),(5002,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicmlhbmR1YzkxMTIwMDNAZ21haWwuY29tIiwiaWF0IjoxNzA5ODA1MjQ1LCJleHAiOjE3MDk4OTE2NDV9.3X-BtIZdFWv0ZOY5qcShQXk-dKQPBxqwiqBAg0jWkiU',0,0,853,'BEARER');
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token_seq`
--

DROP TABLE IF EXISTS `token_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token_seq`
--

LOCK TABLES `token_seq` WRITE;
/*!40000 ALTER TABLE `token_seq` DISABLE KEYS */;
INSERT INTO `token_seq` VALUES (5101);
/*!40000 ALTER TABLE `token_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `transaction_id` int NOT NULL AUTO_INCREMENT,
  `created_id` date NOT NULL,
  `expired_at` date NOT NULL,
  `status` varchar(255) NOT NULL,
  `payment_id` int DEFAULT NULL,
  `plan_id` int DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `created_at` date NOT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `FKq9m7rb5uydysanp8smxcovxlh` (`payment_id`),
  KEY `FKtji7m5bsgexram54dmpy5k3u1` (`plan_id`),
  KEY `FKsg7jp0aj6qipr50856wf6vbw1` (`user_id`),
  CONSTRAINT `FKq9m7rb5uydysanp8smxcovxlh` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`payment_id`),
  CONSTRAINT `FKsg7jp0aj6qipr50856wf6vbw1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKtji7m5bsgexram54dmpy5k3u1` FOREIGN KEY (`plan_id`) REFERENCES `plan` (`plan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `number` int DEFAULT NULL,
  `google_id` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  `telephone` varchar(100) DEFAULT NULL,
  `role` enum('USER','ADMIN') DEFAULT NULL,
  `is_banned` tinyint(1) DEFAULT NULL,
  `is_premium` tinyint(1) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `mfa_enabled` bit(1) NOT NULL,
  `secret` varchar(255) DEFAULT NULL,
  `auth_type` enum('LOCAL','GOOGLE','GITHUB','FACEBOOK') DEFAULT NULL,
  `provider` enum('LOCAL','GOOGLE','GITHUB','FACEBOOK') DEFAULT NULL,
  `is_verified` bit(1) DEFAULT NULL,
  `view` bigint DEFAULT NULL,
  `avatar` BLOB DEFAULT NULL,
  `account_type` enum('STUDENT','TEACHER','MARKETER') DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_t8tbwelrnviudxdaggwr1kd9b` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1003 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (652,NULL,NULL,'vitbrian','$2a$10$nqPOkdQ0GBSh0HwpRnVNYOKArOFq2ntB6hgWe.PTaUmoRRTzz82w.','briannnnn','lmaoo','test@gmail.comm','0373392840','USER',1,0,NULL,_binary '\0',NULL,NULL,NULL,_binary '\0',NULL,NULL,NULL),(752,NULL,NULL,'vitbrian90012','$2a$10$lWr34N/yyEii35hNJg6gX.N3Q26C6pOc9idCgQ48qvynFOarhRWTy','brian','vit','vit@gmail.com','01284184112','ADMIN',1,1,NULL,_binary '\0',NULL,NULL,'LOCAL',_binary '\0',NULL,NULL,NULL),(802,NULL,NULL,'ggm','$2a$10$tIxWt663PYApKG60mCpjY.EeHgCnYt4YwB4PkhoOGRDQdauyBB356','Duc','Lai Dinh Cao','ggmm@gmail.com','0399112003','ADMIN',0,0,'2024-02-01 00:25:47',_binary '\0',NULL,NULL,'LOCAL',_binary '\0',NULL,NULL,NULL),(853,NULL,NULL,'hehehihi','$2a$10$/mfyCKN/H6sgkYTF/dLh1evzbgO2zIHyjGkisvrP4DZv3U8/l4NRS','Đức','Lại Đình Cao','brianduc9112003@gmail.com','0373392840','ADMIN',0,1,'2024-02-06 01:58:46',_binary '\0',NULL,NULL,'LOCAL',_binary '\0',NULL,NULL,NULL),(902,NULL,NULL,'QuyND','$2a$10$0FUVVs6PH4fwipAQMZz0Oe1vK4XkxWOHx6RjmJ08d3/QaAagBd3Fm','Nguyen','Quy','quynd@gmail.com','0366775115','USER',0,0,'2024-02-06 17:38:54',_binary '\0',NULL,NULL,'LOCAL',_binary '\0',NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_belong_class`
--

DROP TABLE IF EXISTS `user_belong_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_belong_class` (
  `class_id` int NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`class_id`,`user_id`),
  KEY `FKg06b8fg2ol5m4u0g054l3f4xb` (`user_id`),
  CONSTRAINT `FKc0ywmblvlqp8hcayoj7p672gk` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`),
  CONSTRAINT `FKg06b8fg2ol5m4u0g054l3f4xb` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_belong_class`
--

LOCK TABLES `user_belong_class` WRITE;
/*!40000 ALTER TABLE `user_belong_class` DISABLE KEYS */;
INSERT INTO `user_belong_class` VALUES (1,853);
/*!40000 ALTER TABLE `user_belong_class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_seq`
--

DROP TABLE IF EXISTS `user_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_seq`
--

LOCK TABLES `user_seq` WRITE;
/*!40000 ALTER TABLE `user_seq` DISABLE KEYS */;
INSERT INTO `user_seq` VALUES (1101);
/*!40000 ALTER TABLE `user_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verification_token`
--

DROP TABLE IF EXISTS `verification_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verification_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiration_time` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_q6jibbenp7o9v6tq178xg88hg` (`user_id`),
  CONSTRAINT `FKrdn0mss276m9jdobfhhn2qogw` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification_token`
--

LOCK TABLES `verification_token` WRITE;
/*!40000 ALTER TABLE `verification_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `verification_token` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-07 18:39:44
