-- MySQL dump 10.13  Distrib 8.0.35, for Linux (x86_64)
--
-- Host: 192.168.98.51    Database: themall_wms
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `themall_wms`
--

CREATE DATABASE /*!32312 IF NOT EXISTS */ `themall_wms` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION = 'N' */;

USE `themall_wms`;

--
-- Table structure for table `undo_log`
--

DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `undo_log` (
                            `id`         bigint NOT NULL AUTO_INCREMENT,
                            `branch_id`  bigint NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
                            `log_status` int    NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `undo_log`
--

LOCK TABLES `undo_log` WRITE;
/*!40000 ALTER TABLE `undo_log`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `undo_log`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_purchase`
--

DROP TABLE IF EXISTS `wms_purchase`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_purchase` (
                                `id`          bigint NOT NULL AUTO_INCREMENT,
                                `assignee_id` bigint DEFAULT NULL,
  `assignee_name` varchar(255) DEFAULT NULL,
  `phone` char(13) DEFAULT NULL,
                                `priority`    int    DEFAULT NULL,
                                `status`      int    DEFAULT NULL,
                                `ware_id`     bigint DEFAULT NULL,
  `amount` decimal(18,4) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='采购信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_purchase`
--

LOCK TABLES `wms_purchase` WRITE;
/*!40000 ALTER TABLE `wms_purchase`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_purchase`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_purchase_detail`
--

DROP TABLE IF EXISTS `wms_purchase_detail`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_purchase_detail` (
                                       `id`          bigint NOT NULL AUTO_INCREMENT,
                                       `purchase_id` bigint DEFAULT NULL COMMENT '采购单id',
                                       `sku_id`      bigint DEFAULT NULL COMMENT '采购商品id',
                                       `sku_num`     int    DEFAULT NULL COMMENT '采购数量',
  `sku_price` decimal(18,4) DEFAULT NULL COMMENT '采购金额',
                                       `ware_id`     bigint DEFAULT NULL COMMENT '仓库id',
                                       `status`      int    DEFAULT NULL COMMENT '状态[0新建，1已分配，2正在采购，3已完成，4采购失败]',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_purchase_detail`
--

LOCK TABLES `wms_purchase_detail` WRITE;
/*!40000 ALTER TABLE `wms_purchase_detail`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_purchase_detail`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_ware_info`
--

DROP TABLE IF EXISTS `wms_ware_info`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_ware_info` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '仓库名',
  `address` varchar(255) DEFAULT NULL COMMENT '仓库地址',
  `areacode` varchar(20) DEFAULT NULL COMMENT '区域编码',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='仓库信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_ware_info`
--

LOCK TABLES `wms_ware_info` WRITE;
/*!40000 ALTER TABLE `wms_ware_info`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_ware_info`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_ware_order_task`
--

DROP TABLE IF EXISTS `wms_ware_order_task`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_ware_order_task` (
                                       `id`          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                       `order_id`    bigint  DEFAULT NULL COMMENT 'order_id',
  `order_sn` varchar(255) DEFAULT NULL COMMENT 'order_sn',
  `consignee` varchar(100) DEFAULT NULL COMMENT '收货人',
  `consignee_tel` char(15) DEFAULT NULL COMMENT '收货人电话',
  `delivery_address` varchar(500) DEFAULT NULL COMMENT '配送地址',
  `order_comment` varchar(200) DEFAULT NULL COMMENT '订单备注',
  `payment_way` tinyint(1) DEFAULT NULL COMMENT '付款方式【 1:在线付款 2:货到付款】',
                                       `task_status` tinyint DEFAULT NULL COMMENT '任务状态',
  `order_body` varchar(255) DEFAULT NULL COMMENT '订单描述',
  `tracking_no` char(30) DEFAULT NULL COMMENT '物流单号',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
                                       `ware_id`     bigint  DEFAULT NULL COMMENT '仓库id',
  `task_comment` varchar(500) DEFAULT NULL COMMENT '工作单备注',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='库存工作单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_ware_order_task`
--

LOCK TABLES `wms_ware_order_task` WRITE;
/*!40000 ALTER TABLE `wms_ware_order_task`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_ware_order_task`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_ware_order_task_detail`
--

DROP TABLE IF EXISTS `wms_ware_order_task_detail`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_ware_order_task_detail` (
                                              `id`          bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                              `sku_id`      bigint DEFAULT NULL COMMENT 'sku_id',
  `sku_name` varchar(255) DEFAULT NULL COMMENT 'sku_name',
                                              `sku_num`     int    DEFAULT NULL COMMENT '购买个数',
                                              `task_id`     bigint DEFAULT NULL COMMENT '工作单id',
                                              `ware_id`     bigint DEFAULT NULL COMMENT '仓库id',
                                              `lock_status` int    DEFAULT NULL COMMENT '1-已锁定  2-已解锁  3-扣减',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='库存工作单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_ware_order_task_detail`
--

LOCK TABLES `wms_ware_order_task_detail` WRITE;
/*!40000 ALTER TABLE `wms_ware_order_task_detail`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_ware_order_task_detail`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wms_ware_sku`
--

DROP TABLE IF EXISTS `wms_ware_sku`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wms_ware_sku` (
                                `id`           bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                                `sku_id`       bigint DEFAULT NULL COMMENT 'sku_id',
                                `ware_id`      bigint DEFAULT NULL COMMENT '仓库id',
                                `stock`        int    DEFAULT NULL COMMENT '库存数',
  `sku_name` varchar(200) DEFAULT NULL COMMENT 'sku_name',
                                `stock_locked` int    DEFAULT '0' COMMENT '锁定库存',
  PRIMARY KEY (`id`),
  KEY `sku_id` (`sku_id`) USING BTREE,
  KEY `ware_id` (`ware_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='商品库存';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wms_ware_sku`
--

LOCK TABLES `wms_ware_sku` WRITE;
/*!40000 ALTER TABLE `wms_ware_sku`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `wms_ware_sku`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-28 10:22:40
