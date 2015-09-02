-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: edb
-- ------------------------------------------------------
-- Server version	5.6.26

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
-- Table structure for table `tpg_gzfsqspb`
--

DROP TABLE IF EXISTS `tpg_gzfsqspb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tpg_gzfsqspb` (
  `ID` int(12) NOT NULL DEFAULT '0' COMMENT 'ID',
  `CODE` int(12) DEFAULT NULL COMMENT 'CODE',
  `YWSLID` int(12) DEFAULT NULL COMMENT '业务实例ID',
  `YWLB` varchar(20) DEFAULT NULL COMMENT '廉租房申请、变更、年审',
  `SPND` varchar(10) DEFAULT NULL COMMENT '审批年度',
  `SQRID` int(12) DEFAULT NULL COMMENT '申请人ID',
  `SQRCODE` int(12) DEFAULT NULL COMMENT '申请人CODE',
  `SQRXM` varchar(100) DEFAULT NULL COMMENT '申请人姓名',
  `SQRSFZH` varchar(50) DEFAULT NULL COMMENT '申请人身份证号',
  `SQRNL` int(3) DEFAULT NULL COMMENT '申请人年龄',
  `SQRDWBH` varchar(50) DEFAULT NULL COMMENT '申请人工作单位编号',
  `SQRDWMC` varchar(100) DEFAULT NULL COMMENT '申请人工作单位名称',
  `POID` int(12) DEFAULT NULL COMMENT '配偶ID',
  `POCODE` int(12) DEFAULT NULL COMMENT '配偶CODE',
  `POXM` varchar(100) DEFAULT NULL COMMENT '配偶姓名',
  `POSFZH` varchar(50) DEFAULT NULL COMMENT '配偶身份证号',
  `PONL` int(3) DEFAULT NULL COMMENT '配偶年龄',
  `PODWBH` varchar(50) DEFAULT NULL COMMENT '配偶工作单位编号',
  `PODWMC` varchar(100) DEFAULT NULL COMMENT '配偶工作单位名称',
  `JTID` int(12) DEFAULT NULL COMMENT '家庭ID',
  `JTCODE` int(12) DEFAULT NULL COMMENT '家庭CODE',
  `JTRS` int(2) DEFAULT NULL COMMENT '家庭人数',
  `JTNZSR` int(9) DEFAULT NULL COMMENT '家庭年总收入',
  `JTRJNSR` int(9) DEFAULT NULL COMMENT '家庭人均年收入',
  `HKRHNS` int(3) DEFAULT NULL COMMENT '户口入杭年数',
  `JTZFQKID` int(12) DEFAULT NULL COMMENT '家庭住房情况ID',
  `YYZFMJ` int(9) DEFAULT NULL COMMENT '已有住房面积',
  `XSSWFFMJ` int(9) DEFAULT NULL COMMENT '享受实物分房面积',
  `KNZJLB` int(8) DEFAULT NULL COMMENT '困难证件类别',
  `KNZJHM` varchar(50) DEFAULT NULL COMMENT '困难证件号码',
  `KNZJYXQQ` date DEFAULT NULL COMMENT '困难证件有效期起',
  `KNZJYXQZ` date DEFAULT NULL COMMENT '困难证件有效期至',
  `HSBH` varchar(50) DEFAULT NULL COMMENT '杭社保号',
  `YDDXLB` int(8) DEFAULT NULL COMMENT '普通、残疾、烈属',
  `PZZGZH` varchar(50) DEFAULT NULL COMMENT '配租资格证号',
  `PZFS` int(8) DEFAULT NULL COMMENT '配租方式',
  `NPZRS` int(2) DEFAULT NULL COMMENT '拟配租人数',
  `BTMJ` int(9) DEFAULT NULL COMMENT '补贴面积',
  `PZMJBZ` int(9) DEFAULT NULL COMMENT '配租面积标准',
  `YXQQ` date DEFAULT NULL COMMENT '有效期起',
  `YXQZ` date DEFAULT NULL COMMENT '有效期至',
  `SJGSZT` int(8) DEFAULT NULL COMMENT '市局公示状态：0：默认值；1：待公示；2：公示中；3：完成公示',
  `PZCS` int(2) DEFAULT NULL COMMENT '配租次数',
  `BJSPZFACS` int(2) DEFAULT NULL COMMENT '不接受配租方案次数',
  `SFFQZG` int(1) DEFAULT NULL COMMENT '是否放弃资格',
  `SFQXSWPZZG` int(1) DEFAULT NULL COMMENT '是否取消实物配租资格',
  `SFGDQ` int(1) DEFAULT NULL COMMENT '是否过渡期',
  `GDQQ` date DEFAULT NULL COMMENT '过渡期起',
  `GDQZ` date DEFAULT NULL COMMENT '过渡期至',
  `BZ` varchar(500) DEFAULT NULL COMMENT '备注',
  `SFYX` int(1) DEFAULT NULL COMMENT '是否有效',
  `BGQSPBID` int(12) DEFAULT NULL COMMENT '变更前审批表ID',
  `BGQPZZGZH` varchar(50) DEFAULT NULL COMMENT '变更前配租资格证号',
  `BGQPZFS` int(8) DEFAULT NULL COMMENT '变更前配租方式',
  `BGYY` varchar(500) DEFAULT NULL COMMENT '变更原因',
  `SFCXDZ` int(1) DEFAULT NULL COMMENT '是否重新打证',
  `BGCS` int(2) DEFAULT NULL COMMENT '变更次数',
  `NSYJ` varchar(500) DEFAULT NULL COMMENT '年审意见',
  `NSSFTG` int(1) DEFAULT NULL COMMENT '年审是否通过',
  `LSBZ` int(8) DEFAULT NULL COMMENT '历史标志',
  `CJSJ` date DEFAULT NULL COMMENT '创建时间',
  `ZZSJ` date DEFAULT NULL COMMENT '终止时间',
  `SZCQ` int(8) DEFAULT NULL COMMENT '所在城区',
  `SSSQ` int(8) DEFAULT NULL COMMENT '所属社区',
  `SZJD` int(8) DEFAULT NULL COMMENT '所在街道',
  `GSJG` int(1) DEFAULT NULL COMMENT '公示结果',
  `JXC` int(8) DEFAULT NULL COMMENT '夹心层',
  `SQQD` int(8) DEFAULT NULL COMMENT '申请渠道',
  `SLRQ` date DEFAULT NULL COMMENT '受理时间',
  `SFYYH` int(1) DEFAULT NULL COMMENT '是否已摇号；0表示未摇号，1表示已摇号',
  `YHSJ` date DEFAULT NULL COMMENT '摇号时间',
  `QJGSZT` int(8) DEFAULT NULL COMMENT '区局公示状态：0：默认值；1：待公示；2：公示中；3：完成公示',
  `HTQDSJ` date DEFAULT NULL COMMENT '合同签订时间始',
  `HTQDNS` int(8) DEFAULT NULL COMMENT '合同签订年数',
  `YLBXJNNS` date DEFAULT NULL COMMENT '养老保险交纳时间',
  `JNNS` int(8) DEFAULT NULL COMMENT '养老保险交纳年数',
  `ZFGJJJNSJ` date DEFAULT NULL COMMENT '住房公积金交纳时间',
  `ZFGJJJNNS` int(8) DEFAULT NULL COMMENT '住房公积金交纳年数',
  `SWWSZMQX` date DEFAULT NULL COMMENT '税务完税证明期限',
  `WSNS` int(8) DEFAULT NULL COMMENT '完税年数',
  `YEZZ` varchar(50) DEFAULT NULL COMMENT '营业执照',
  `BYXX` varchar(50) DEFAULT NULL COMMENT '毕业学校',
  `BYZH` varchar(50) DEFAULT NULL COMMENT '毕业证号',
  `BYSJ` date DEFAULT NULL COMMENT '毕业时间',
  `XL` int(8) DEFAULT NULL COMMENT '学历',
  `ZC` int(8) DEFAULT NULL COMMENT '职称',
  `ZYZG` int(8) DEFAULT NULL COMMENT '职业资格',
  `SLR` varchar(100) DEFAULT NULL COMMENT '受理人',
  `ZJLB` int(8) DEFAULT NULL COMMENT '证件类别',
  `ZZZH` varchar(50) DEFAULT NULL COMMENT '临时赞助证号',
  `ZYZT` int(8) DEFAULT NULL COMMENT '职业状态',
  `HZLX` int(8) DEFAULT NULL COMMENT '合租类型',
  `SLBH` varchar(50) DEFAULT NULL COMMENT '受理编号',
  `QS` int(8) DEFAULT NULL COMMENT '摇号期数',
  `YHND` varchar(20) DEFAULT NULL COMMENT '摇号年度',
  `DABH` varchar(50) DEFAULT NULL COMMENT '档案编号',
  `HTQDSJZ` date DEFAULT NULL COMMENT '合同签订时间止',
  `SFYPZ` int(8) DEFAULT NULL COMMENT '是否已配租',
  `BJQSQLX` int(8) DEFAULT NULL COMMENT '杭州高新区（滨江）创业人才公寓申请类型 1：重点骨干企业；2：重大招商项目；3：其他企业单位',
  `HCZT` int(8) DEFAULT NULL COMMENT '(人工核查状态  0全部返回，1，未核查数据)',
  `STOPFLAG` int(1) DEFAULT NULL COMMENT '终止标示：0：正常；1：终止',
  `SQBZLX` int(1) DEFAULT NULL COMMENT '申请保障优先类型：1：优先保障；0：常规保障',
  `YXBZLX` int(1) DEFAULT NULL COMMENT '优先保障类型',
  `HCBH` int(12) DEFAULT NULL COMMENT '档案馆的核查编号，通过当前核查核可以调用到档案馆的核查结果',
  `SXH` int(12) DEFAULT NULL COMMENT '顺序号：调房申请顺序号',
  `YEZZH` varchar(50) DEFAULT NULL COMMENT '营业执照号',
  `ZCHM` varchar(50) DEFAULT NULL COMMENT '职称/职业资格证书号',
  `JPZZGZH` varchar(20) DEFAULT NULL COMMENT '旧保障资格证号',
  `ZXYY` varchar(2000) DEFAULT NULL COMMENT '注销原因',
  `ZXSJ` date DEFAULT NULL COMMENT '注销时间',
  `PZLX` int(8) DEFAULT NULL COMMENT '配租类型',
  `HBBTBZ` int(9) DEFAULT NULL COMMENT '货币补贴标准',
  `HBBTXS` int(9) DEFAULT NULL COMMENT '货币补贴系数',
  `YHBBTJE` int(9) DEFAULT NULL COMMENT '月货币补贴金额',
  `CSYJ` varchar(500) DEFAULT NULL COMMENT '初审意见',
  `CSSFTG` int(1) DEFAULT NULL COMMENT '初审是否通过',
  `FSYJ` varchar(500) DEFAULT NULL COMMENT '复审意见',
  `FSSFTG` int(1) DEFAULT NULL COMMENT '复审是否通过',
  `ZSYJ` varchar(500) DEFAULT NULL COMMENT '终审意见',
  `ZSSFTG` int(1) DEFAULT NULL COMMENT '终审是否通过',
  PRIMARY KEY (`ID`),
  KEY `SQRID` (`SQRID`),
  KEY `POID` (`POID`),
  KEY `JTID` (`JTID`),
  CONSTRAINT `tpg_gzfsqspb_ibfk_1` FOREIGN KEY (`SQRID`) REFERENCES `tpg_ryxx` (`ID`),
  CONSTRAINT `tpg_gzfsqspb_ibfk_2` FOREIGN KEY (`POID`) REFERENCES `tpg_ryxx` (`ID`),
  CONSTRAINT `tpg_gzfsqspb_ibfk_3` FOREIGN KEY (`JTID`) REFERENCES `tpg_jtxx` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公租房申请审批表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tpg_gzfsqspb`
--

LOCK TABLES `tpg_gzfsqspb` WRITE;
/*!40000 ALTER TABLE `tpg_gzfsqspb` DISABLE KEYS */;
/*!40000 ALTER TABLE `tpg_gzfsqspb` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tpg_jtxx`
--

DROP TABLE IF EXISTS `tpg_jtxx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tpg_jtxx` (
  `ID` int(12) NOT NULL DEFAULT '0' COMMENT 'ID',
  `CODE` int(12) DEFAULT NULL COMMENT 'CODE',
  `CJRCODE` int(12) DEFAULT NULL COMMENT '家庭创建人CODE',
  `JTRS` int(2) DEFAULT NULL COMMENT '家庭人数',
  `KNZJLB` int(8) DEFAULT NULL COMMENT '困难证件类别',
  `KNZJHM` varchar(50) DEFAULT NULL COMMENT '困难证件号码',
  `KNZJYXQQ` date DEFAULT NULL COMMENT '困难证件有效期起',
  `KNZJYXQZ` date DEFAULT NULL COMMENT '困难证件有效期至',
  `LSBZ` int(8) DEFAULT NULL COMMENT '历史标志',
  `CJSJ` date DEFAULT NULL COMMENT '创建时间',
  `SFYX` int(1) DEFAULT NULL COMMENT '是否有效',
  `BBBS` varchar(32) DEFAULT NULL COMMENT '版本标识',
  `KNZJMC` int(8) DEFAULT NULL COMMENT '困难证件名称（是困难证件的子类别）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='家庭信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tpg_jtxx`
--

LOCK TABLES `tpg_jtxx` WRITE;
/*!40000 ALTER TABLE `tpg_jtxx` DISABLE KEYS */;
/*!40000 ALTER TABLE `tpg_jtxx` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tpg_rygx`
--

DROP TABLE IF EXISTS `tpg_rygx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tpg_rygx` (
  `ID` int(12) NOT NULL DEFAULT '0' COMMENT 'ID',
  `CJRID` int(12) DEFAULT NULL COMMENT '创建人ID',
  `CJRCODE` int(12) DEFAULT NULL COMMENT '创建人CODE',
  `CJRXM` varchar(100) DEFAULT NULL COMMENT '创建人姓名',
  `GX` int(8) DEFAULT NULL COMMENT '创建人、妻子、丈夫、儿子等单向关系',
  `GXRID` int(12) DEFAULT NULL COMMENT '关系人ID',
  `GXRCODE` int(12) DEFAULT NULL COMMENT '关系人CODE',
  `GXRXM` varchar(100) DEFAULT NULL COMMENT '关系人姓名',
  `JTID` int(12) DEFAULT NULL COMMENT '家庭ID',
  `JTCODE` int(12) DEFAULT NULL COMMENT '家庭CODE',
  `SFYX` int(1) DEFAULT NULL COMMENT '是否有效',
  `LSBZ` int(8) DEFAULT NULL COMMENT '历史标志',
  `CJSJ` date DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`),
  KEY `CJRID` (`CJRID`),
  KEY `GXRID` (`GXRID`),
  KEY `JTID` (`JTID`),
  CONSTRAINT `tpg_rygx_ibfk_1` FOREIGN KEY (`CJRID`) REFERENCES `tpg_ryxx` (`ID`),
  CONSTRAINT `tpg_rygx_ibfk_2` FOREIGN KEY (`GXRID`) REFERENCES `tpg_ryxx` (`ID`),
  CONSTRAINT `tpg_rygx_ibfk_3` FOREIGN KEY (`JTID`) REFERENCES `tpg_jtxx` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人员关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tpg_rygx`
--

LOCK TABLES `tpg_rygx` WRITE;
/*!40000 ALTER TABLE `tpg_rygx` DISABLE KEYS */;
/*!40000 ALTER TABLE `tpg_rygx` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tpg_ryxx`
--

DROP TABLE IF EXISTS `tpg_ryxx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tpg_ryxx` (
  `ID` int(12) NOT NULL DEFAULT '0' COMMENT 'ID',
  `CODE` int(12) DEFAULT NULL COMMENT 'CODE',
  `BZCODE` int(12) DEFAULT NULL COMMENT '标准CODE',
  `XM` varchar(100) DEFAULT NULL COMMENT '姓名',
  `XB` int(8) DEFAULT NULL COMMENT '性别',
  `ZJLB` char(10) DEFAULT NULL COMMENT '证件类别',
  `SFZH` varchar(50) DEFAULT NULL COMMENT '身份证号',
  `CSRQ` date DEFAULT NULL COMMENT '出生日期',
  `SFYCN` int(1) DEFAULT NULL COMMENT '是否已成年',
  `HYZK` int(8) DEFAULT NULL COMMENT '婚姻状况',
  `XL` int(8) DEFAULT NULL COMMENT '学历',
  `HJLX` int(8) DEFAULT NULL COMMENT '本地居民、本地农业、外地居民、外地农业、外籍',
  `HJHM` varchar(50) DEFAULT NULL COMMENT '户籍号码',
  `HKSZD` varchar(100) DEFAULT NULL COMMENT '户口所在地',
  `HKSCRHSJ` date DEFAULT NULL COMMENT '户口首次入杭时间',
  `SZCQ` int(8) DEFAULT NULL COMMENT '所在城区',
  `SZCQMC` varchar(100) DEFAULT NULL COMMENT '所在城区名称',
  `SZJD` int(8) DEFAULT NULL COMMENT '所在街道',
  `SZJDMC` varchar(100) DEFAULT NULL COMMENT '所在街道名称',
  `SSSQ` int(8) DEFAULT NULL COMMENT '所属社区',
  `SSSQMC` varchar(100) DEFAULT NULL COMMENT '所属社区名称',
  `ZZ` varchar(100) DEFAULT NULL COMMENT '住址',
  `DWID` int(12) DEFAULT NULL COMMENT '工作单位ID',
  `DWBH` varchar(50) DEFAULT NULL COMMENT '工作单位编号',
  `DWMC` varchar(100) DEFAULT NULL COMMENT '工作单位名称',
  `DWSSHY` int(8) DEFAULT NULL COMMENT '单位所属行业',
  `ZZQK` int(8) DEFAULT NULL COMMENT '在职情况',
  `ZYZT` int(8) DEFAULT NULL COMMENT '职业状态',
  `ZWZC` int(8) DEFAULT NULL COMMENT '职务职称',
  `CJGZSJ` date DEFAULT NULL COMMENT '参加工作时间',
  `GZSJ` date DEFAULT NULL COMMENT '供职时间',
  `FGGL` int(4) DEFAULT NULL COMMENT '房改工龄',
  `FGJL` int(4) DEFAULT NULL COMMENT '房改教龄',
  `SNDZSR` int(9) DEFAULT NULL COMMENT '上年度总收入',
  `GJJZH` varchar(50) DEFAULT NULL COMMENT '公积金账号',
  `CJZH` varchar(50) DEFAULT NULL COMMENT '残疾证号',
  `CJDJ` int(8) DEFAULT NULL COMMENT '残疾等级',
  `CJLX` int(8) DEFAULT NULL COMMENT '低视力/盲人/上肢/下肢/智力/精神/听力/言语',
  `SFJLS` int(1) DEFAULT NULL COMMENT '是否军烈属',
  `YDDH` varchar(100) DEFAULT NULL COMMENT '移动电话',
  `LXDH` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `LXDZ` varchar(200) DEFAULT NULL COMMENT '联系地址',
  `YZBM` int(6) DEFAULT NULL COMMENT '邮政编码',
  `DZYJ` varchar(100) DEFAULT NULL COMMENT 'EMAIL',
  `BZ` varchar(500) DEFAULT NULL COMMENT '备注',
  `SFGFR` int(1) DEFAULT NULL COMMENT '是否购房人',
  `LSBZ` int(8) DEFAULT NULL COMMENT '历史标志',
  `CJSJ` date DEFAULT NULL COMMENT '创建时间',
  `ZZSJ` date DEFAULT NULL COMMENT '终止时间',
  `SFYX` int(1) DEFAULT NULL COMMENT '是否有效',
  `HTQDSJ` date DEFAULT NULL COMMENT '合同签订时间始',
  `HTQDNS` int(8) DEFAULT NULL COMMENT '合同签订年数',
  `YLBXJNNS` date DEFAULT NULL COMMENT '养老保险交纳时间',
  `JNNS` int(8) DEFAULT NULL COMMENT '养老保险交纳年数',
  `ZFGJJJNSJ` date DEFAULT NULL COMMENT '住房公积金交纳时间',
  `ZFGJJJNNS` int(8) DEFAULT NULL COMMENT '住房公积金交纳年数',
  `SWWSZMQX` date DEFAULT NULL COMMENT '税务完税证明期限',
  `WSNS` int(8) DEFAULT NULL COMMENT '完税年数',
  `ZZZH` varchar(50) DEFAULT NULL COMMENT '临时暂住证',
  `ZYZG` int(8) DEFAULT NULL COMMENT '职业资格',
  `ZC` int(8) DEFAULT NULL COMMENT '职称',
  `BYSJ` date DEFAULT NULL COMMENT '毕业时间',
  `HTQDSJZ` date DEFAULT NULL COMMENT '合同签订时间止',
  `LRHCFW` int(1) DEFAULT NULL COMMENT '列入核查范围（廉租房）',
  `SFXZ` int(1) DEFAULT NULL COMMENT '是否限制购房',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人员信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tpg_ryxx`
--

LOCK TABLES `tpg_ryxx` WRITE;
/*!40000 ALTER TABLE `tpg_ryxx` DISABLE KEYS */;
/*!40000 ALTER TABLE `tpg_ryxx` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-09-01 17:28:03
