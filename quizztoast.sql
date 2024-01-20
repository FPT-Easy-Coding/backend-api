CREATE DATABASE  IF NOT EXISTS `quizztoast` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `quizztoast`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: quizztoast
-- ------------------------------------------------------
-- Server version	8.0.29

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
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
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
  UNIQUE KEY `UK_n1e4rphjfnn8d2gdyrml5f3wi` (`slug_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
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
  `user_doquiz_id` bigint NOT NULL,
  `quiz_id` int NOT NULL,
  PRIMARY KEY (`quiz_id`,`user_doquiz_id`),
  KEY `FKx1onfn8vnv0vjayqc0ta27fl` (`user_doquiz_id`),
  CONSTRAINT `FKldq1gy62kmex6lfkknfgc4aky` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`quiz_id`),
  CONSTRAINT `FKx1onfn8vnv0vjayqc0ta27fl` FOREIGN KEY (`user_doquiz_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `do_quiz`
--

LOCK TABLES `do_quiz` WRITE;
/*!40000 ALTER TABLE `do_quiz` DISABLE KEYS */;
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
  `rate` int NOT NULL,
  `category_id` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`quiz_id`),
  KEY `FK82x9fxd5tsbb3i1ewrp3cr8xa` (`category_id`),
  KEY `FK1tofsm1qynhakggx7ttqh8ihu` (`user_id`),
  CONSTRAINT `FK1tofsm1qynhakggx7ttqh8ihu` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK82x9fxd5tsbb3i1ewrp3cr8xa` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz`
--

LOCK TABLES `quiz` WRITE;
/*!40000 ALTER TABLE `quiz` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_answer`
--

LOCK TABLES `quiz_answer` WRITE;
/*!40000 ALTER TABLE `quiz_answer` DISABLE KEYS */;
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
  `exam_id` int NOT NULL,
  PRIMARY KEY (`class_id`,`exam_id`),
  KEY `FKex55xyxag21w5edw7m1oriblo` (`exam_id`),
  CONSTRAINT `FK2j6hk4e069mt92bgeuw6qqx02` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`),
  CONSTRAINT `FKex55xyxag21w5edw7m1oriblo` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`exam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_belong_class`
--

LOCK TABLES `quiz_belong_class` WRITE;
/*!40000 ALTER TABLE `quiz_belong_class` DISABLE KEYS */;
/*!40000 ALTER TABLE `quiz_belong_class` ENABLE KEYS */;
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
  PRIMARY KEY (`quiz_question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_question`
--

LOCK TABLES `quiz_question` WRITE;
/*!40000 ALTER TABLE `quiz_question` DISABLE KEYS */;
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
/*!40000 ALTER TABLE `quiz_question_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token` (
  `token_id` int NOT NULL,
  `expired` bit(1) NOT NULL,
  `revoked` bit(1) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `token_type` enum('BEARER') DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`token_id`),
  KEY `FKe32ek7ixanakfqsdaokm4q9y2` (`user_id`),
  CONSTRAINT `FKe32ek7ixanakfqsdaokm4q9y2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
INSERT INTO `token` VALUES (10002,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjJAZ21haWwuY29tIiwiaWF0IjoxNzA1Njc0NDgzLCJleHAiOjE3MDU3NjA4ODN9.Kuop5LPr2MEiYZ78mJayXoxBT-38dAk0iajXih-RNTE','BEARER',10002),(10052,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjJAZ21haWwuY29tIiwiaWF0IjoxNzA1NzU2MTQ4LCJleHAiOjE3MDU4NDI1NDh9.LwOJAcZoN5vdTy4-UY171EF5wU5p-s94IOatb9z2ZPI','BEARER',10052);
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
INSERT INTO `token_seq` VALUES (10151);
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
  `user_id` bigint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `google_id` varchar(255) DEFAULT NULL,
  `is_banned` bit(1) DEFAULT NULL,
  `is_premium` bit(1) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `mfa_enabled` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` enum('USER','ADMIN') DEFAULT NULL,
  `secret` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (10002,NULL,'admin2@gmail.com','Admin',NULL,_binary '\0',_binary '\0','Demo',_binary '\0','$2a$10$Yd/q6BvJnketEg6iVZ3E7eBXJI3BRjK2zdmhRDogL7oiy2GW7VK9e','ADMIN',NULL,NULL,NULL),(10052,NULL,'admin2@gmail.com','Admin',NULL,_binary '\0',_binary '\0','Demo',_binary '\0','$2a$10$n0Zx2NZcfLzBdRmfcIE7XOze3nGf9.Ouyltb1TxQoGCZHQGo1QqnS','ADMIN',NULL,NULL,NULL);
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
INSERT INTO `user_seq` VALUES (10151);
/*!40000 ALTER TABLE `user_seq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-20 21:30:14
