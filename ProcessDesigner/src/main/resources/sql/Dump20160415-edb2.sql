-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: fudanglp.mysql.rds.aliyuncs.com    Database: edb2
-- ------------------------------------------------------
-- Server version	5.6.16

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
-- Table structure for table `jtcyxx`
--

DROP TABLE IF EXISTS `jtcyxx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jtcyxx` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL COMMENT '姓名',
  `id_number` varchar(45) DEFAULT NULL COMMENT '证件号码',
  `phone` varchar(45) DEFAULT NULL COMMENT '联系电话',
  `organiztion` varchar(45) DEFAULT NULL COMMENT '工作单位',
  `income` int(11) DEFAULT NULL COMMENT '可支配收入',
  `applicant_id` int(11) DEFAULT NULL COMMENT '申请人ID',
  PRIMARY KEY (`id`),
  KEY `jtcy_sqr_idx` (`applicant_id`),
  CONSTRAINT `jtcy_sqr` FOREIGN KEY (`applicant_id`) REFERENCES `sqrxx` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='家庭成员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sqrxx`
--

DROP TABLE IF EXISTS `sqrxx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sqrxx` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL COMMENT '姓名',
  `id_number` varchar(45) DEFAULT NULL COMMENT '证件号',
  `phone` varchar(45) DEFAULT NULL COMMENT '联系电话',
  `marriage` varchar(45) DEFAULT NULL COMMENT '婚姻状况',
  `income` int(11) DEFAULT NULL COMMENT '可支配收入',
  `registered_residence` varchar(45) DEFAULT NULL COMMENT '户口所在地',
  `registered_date` date DEFAULT NULL COMMENT '入本市户籍时间',
  `organization` varchar(45) DEFAULT NULL COMMENT '工作单位',
  `organization_no` varchar(45) DEFAULT NULL COMMENT '组织机构代码',
  `occupation_status` varchar(45) DEFAULT NULL COMMENT '职业状况',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='申请人信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zb_sqd`
--

DROP TABLE IF EXISTS `zb_sqd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zb_sqd` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `applicant_org_approval` varchar(45) DEFAULT NULL COMMENT '申请人单位审批信息',
  `family_member_org_approval` varchar(45) DEFAULT NULL COMMENT '家庭成员所在单位审批信息',
  `public_display` varchar(45) DEFAULT NULL COMMENT '公示情况',
  `district_bereau_1st_approval` varchar(45) DEFAULT NULL COMMENT '区局初审',
  `district_bereau_2nd_approval` varchar(45) DEFAULT NULL COMMENT '区局复审',
  `city_bereau_3rd_approval` varchar(45) DEFAULT NULL COMMENT '市局终审',
  `applicant_id` int(11) DEFAULT NULL COMMENT '申请人ID',
  PRIMARY KEY (`id`),
  KEY `sqd_sqrxx_idx` (`applicant_id`),
  CONSTRAINT `sqd_sqrxx` FOREIGN KEY (`applicant_id`) REFERENCES `sqrxx` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='住保申请单';
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-15 17:06:57
