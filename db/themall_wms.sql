/*
 Navicat Premium Data Transfer

 Source Server         : theangel.vip
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : theangel.vip:3306
 Source Schema         : themall_wms

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 27/12/2021 13:38:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for wms_purchase
-- ----------------------------
DROP TABLE IF EXISTS `wms_purchase`;
CREATE TABLE `wms_purchase`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `assignee_id` bigint(20) NULL DEFAULT NULL,
  `assignee_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` char(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `priority` int(4) NULL DEFAULT NULL,
  `status` int(4) NULL DEFAULT NULL,
  `ware_id` bigint(20) NULL DEFAULT NULL,
  `amount` decimal(18, 4) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '采购信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wms_purchase
-- ----------------------------
INSERT INTO `wms_purchase` VALUES (1, 2, 'fireflynay', '18156475879', 1, 3, 1, 149700.0000, '2020-06-07 00:34:32', '2020-06-07 15:55:06');
INSERT INTO `wms_purchase` VALUES (2, 1, 'admin', '18173516309', 1, 3, 1, 177760.0000, '2020-06-07 00:55:43', '2020-06-07 14:14:47');
INSERT INTO `wms_purchase` VALUES (3, 1, 'admin', '18173516309', 1, 3, 1, 297520.0000, '2020-06-07 13:33:08', '2020-06-07 15:21:43');
INSERT INTO `wms_purchase` VALUES (4, 2, 'fireflynay', '18156475879', 1, 3, 1, 179640.0000, '2020-06-07 14:01:10', '2020-06-07 15:18:35');
INSERT INTO `wms_purchase` VALUES (5, 1, 'admin', '13612345678', 1, 2, NULL, NULL, NULL, '2021-07-30 23:46:30');
INSERT INTO `wms_purchase` VALUES (6, NULL, NULL, NULL, NULL, 1, NULL, NULL, '2021-07-30 23:18:05', '2021-07-30 23:29:07');
INSERT INTO `wms_purchase` VALUES (7, NULL, NULL, NULL, NULL, 1, NULL, NULL, '2021-07-30 23:28:49', '2021-07-30 23:28:49');

-- ----------------------------
-- Table structure for wms_purchase_detail
-- ----------------------------
DROP TABLE IF EXISTS `wms_purchase_detail`;
CREATE TABLE `wms_purchase_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_id` bigint(20) NULL DEFAULT NULL COMMENT '采购单id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '采购商品id',
  `sku_num` int(11) NULL DEFAULT NULL COMMENT '采购数量',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '采购金额',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态[0新建，1已分配，2正在采购，3已完成，4采购失败]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wms_purchase_detail
-- ----------------------------
INSERT INTO `wms_purchase_detail` VALUES (1, 1, 2, 10, 88880.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (2, 3, 2, 20, 177760.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (3, 3, 3, 5, 29940.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (4, 3, 3, 15, 89820.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (5, 4, 4, 30, 179640.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (6, 1, 5, 25, 149700.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (7, 5, 1, 1, NULL, 1, 4);
INSERT INTO `wms_purchase_detail` VALUES (8, 5, 2, 2, NULL, 1, 3);

-- ----------------------------
-- Table structure for wms_ware_info
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_info`;
CREATE TABLE `wms_ware_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库名',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库地址',
  `areacode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '仓库信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wms_ware_info
-- ----------------------------
INSERT INTO `wms_ware_info` VALUES (1, '1号仓库', '长沙市', '41000');

-- ----------------------------
-- Table structure for wms_ware_order_task
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_order_task`;
CREATE TABLE `wms_ware_order_task`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'order_id',
  `order_sn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'order_sn',
  `consignee` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `consignee_tel` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `delivery_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配送地址',
  `order_comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单备注',
  `payment_way` tinyint(1) NULL DEFAULT NULL COMMENT '付款方式【 1:在线付款 2:货到付款】',
  `task_status` tinyint(2) NULL DEFAULT NULL COMMENT '任务状态',
  `order_body` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单描述',
  `tracking_no` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流单号',
  `create_time` datetime NULL DEFAULT NULL COMMENT 'create_time',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `task_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作单备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1448295174525440002 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '库存工作单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wms_ware_order_task
-- ----------------------------
INSERT INTO `wms_ware_order_task` VALUES (1441436203709677569, NULL, '202109250015456291441436201276968962', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1441438023261741057, NULL, '202109250022594221441438020736733185', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1441478725257523201, NULL, '202109250304434151441478722241712130', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1441675250973638657, NULL, '202109251605390381441675248960303105', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1443796055819853826, NULL, '202110011232582511443796053534003201', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1443796492505620482, NULL, '202110011234426191443796491276734466', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1443796672571285505, NULL, '202110011235255611443796671388536833', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1443799305579175938, NULL, '202110011245531661443799303771484162', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1443801087160131586, NULL, '202110011252579271443801085339774978', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1443802279999217665, NULL, '202110011257423611443802278342500353', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1443802385053949954, NULL, '202110011258075501443802383984435201', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1443802542025777154, NULL, '202110011258449971443802541044342786', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1443961012842688514, NULL, '202110012328271121443961010728771586', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1443979732159942658, NULL, '202110020042502871443979730637393922', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1444194585873006593, NULL, '202110021456348301444194581951315969', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1444209325466763266, NULL, '202110021555096291444209324086800386', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1444678604465139713, NULL, '202110032259541751444678601885663234', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1447181104456003586, NULL, '202110102043566831447181101935177730', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1447181830955593729, NULL, '202110102046501031447181829307183106', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (1448295174525440001, NULL, '202110132230516931448295172038299650', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for wms_ware_order_task_detail
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_order_task_detail`;
CREATE TABLE `wms_ware_order_task_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'sku_id',
  `sku_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sku_name',
  `sku_num` int(11) NULL DEFAULT NULL COMMENT '购买个数',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '工作单id',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `lock_status` int(1) NULL DEFAULT NULL COMMENT '1-已锁定  2-已解锁  3-扣减',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1448295175079088131 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '库存工作单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wms_ware_order_task_detail
-- ----------------------------
INSERT INTO `wms_ware_order_task_detail` VALUES (1443961012985294850, 2, NULL, 1, 1443961012842688514, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (1443961013203398657, 4, NULL, 1, 1443961012842688514, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (1443979732373852161, 2, NULL, 1, 1443979732159942658, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (1443979732570984449, 4, NULL, 1, 1443979732159942658, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (1444194586065944577, 2, NULL, 1, 1444194585873006593, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (1444194586300825602, 4, NULL, 1, 1444194585873006593, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (1444209325621952514, 2, NULL, 1, 1444209325466763266, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (1444209325810696193, 4, NULL, 1, 1444209325466763266, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (1444678604687437825, 4, NULL, 1, 1444678604465139713, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (1444678604985233409, 2, NULL, 1, 1444678604465139713, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (1447181104632164354, 4, NULL, 1, 1447181104456003586, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (1447181104837685250, 5, NULL, 1, 1447181104456003586, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (1447181104992874497, 2, NULL, 1, 1447181104456003586, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (1447181831085617154, 4, NULL, 1, 1447181830955593729, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (1447181831282749442, 5, NULL, 1, 1447181830955593729, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (1447181831429550081, 2, NULL, 1, 1447181830955593729, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (1448295174772903938, 2, NULL, 1, 1448295174525440001, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (1448295174944870401, 4, NULL, 1, 1448295174525440001, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (1448295175079088130, 5, NULL, 1, 1448295174525440001, 1, 2);

-- ----------------------------
-- Table structure for wms_ware_sku
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_sku`;
CREATE TABLE `wms_ware_sku`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'sku_id',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `stock` int(11) NULL DEFAULT NULL COMMENT '库存数',
  `sku_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sku_name',
  `stock_locked` int(11) NULL DEFAULT 0 COMMENT '锁定库存',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sku_id`(`sku_id`) USING BTREE,
  INDEX `ware_id`(`ware_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品库存' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wms_ware_sku
-- ----------------------------
INSERT INTO `wms_ware_sku` VALUES (4, 3, 1, 35, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐一', 0);
INSERT INTO `wms_ware_sku` VALUES (5, 4, 1, 60, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二', 0);
INSERT INTO `wms_ware_sku` VALUES (6, 5, 1, 125, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐三', 0);
INSERT INTO `wms_ware_sku` VALUES (10, 2, 1, 2, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐三', 0);

SET FOREIGN_KEY_CHECKS = 1;
