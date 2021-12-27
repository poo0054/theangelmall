/*
 Navicat Premium Data Transfer

 Source Server         : theangel.vip
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : theangel.vip:3306
 Source Schema         : themall_oms

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 27/12/2021 13:34:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mq_message
-- ----------------------------
DROP TABLE IF EXISTS `mq_message`;
CREATE TABLE `mq_message`  (
  `message_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'JSON',
  `to_exchange` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `class_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `message_status` int(1) NULL DEFAULT 0 COMMENT '0-新建 1-已发送 2-错误抵达 3-已抵达',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mq_message
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT 'member_id',
  `order_sn` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单号',
  `coupon_id` bigint(20) NULL DEFAULT NULL COMMENT '使用的优惠券',
  `create_time` datetime NULL DEFAULT NULL COMMENT 'create_time',
  `member_username` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `total_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '订单总额',
  `pay_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '应付总额',
  `freight_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '运费金额',
  `promotion_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '促销优化金额（促销价、满减、阶梯价）',
  `integration_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '积分抵扣金额',
  `coupon_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '优惠券抵扣金额',
  `discount_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '后台调整订单使用的折扣金额',
  `pay_type` tinyint(4) NULL DEFAULT NULL COMMENT '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】',
  `source_type` tinyint(4) NULL DEFAULT NULL COMMENT '订单来源[0->PC订单；1->app订单]',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
  `delivery_company` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物流公司(配送方式)',
  `delivery_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物流单号',
  `auto_confirm_day` int(11) NULL DEFAULT NULL COMMENT '自动确认时间（天）',
  `integration` int(11) NULL DEFAULT NULL COMMENT '可以获得的积分',
  `growth` int(11) NULL DEFAULT NULL COMMENT '可以获得的成长值',
  `bill_type` tinyint(4) NULL DEFAULT NULL COMMENT '发票类型[0->不开发票；1->电子发票；2->纸质发票]',
  `bill_header` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发票抬头',
  `bill_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发票内容',
  `bill_receiver_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收票人电话',
  `bill_receiver_email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收票人邮箱',
  `receiver_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `receiver_post_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人邮编',
  `receiver_province` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省份/直辖市',
  `receiver_city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '城市',
  `receiver_region` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '区',
  `receiver_detail_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '详细地址',
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单备注',
  `confirm_status` tinyint(4) NULL DEFAULT NULL COMMENT '确认收货状态[0->未确认；1->已确认]',
  `delete_status` tinyint(4) NULL DEFAULT NULL COMMENT '删除状态【0->未删除；1->已删除】',
  `use_integration` int(11) NULL DEFAULT NULL COMMENT '下单时使用的积分',
  `payment_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `delivery_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '确认收货时间',
  `comment_time` datetime NULL DEFAULT NULL COMMENT '评价时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_sn`(`order_sn`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1448295173795713026 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order
-- ----------------------------
INSERT INTO `oms_order` VALUES (1443961012368744449, 6, '202110012328271121443961010728771586', NULL, NULL, NULL, 14876.0000, 14901.0000, 25.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 14876, 14876, NULL, NULL, NULL, NULL, NULL, '张三', '15421564125', NULL, '陕西', '西安', NULL, '龙岗五和南山深南花园1023', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, '2021-10-01 23:28:28');
INSERT INTO `oms_order` VALUES (1443979586865041410, 6, '202110020042156211443979585246040065', NULL, NULL, NULL, 23764.0000, 23766.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 23764, 23764, NULL, NULL, NULL, NULL, NULL, '张三', '18173516102', NULL, '湖南', '长沙', NULL, '雨花区', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, '2021-10-02 00:42:16');
INSERT INTO `oms_order` VALUES (1443979605810712577, 6, '202110020042203111443979604908937217', NULL, NULL, NULL, 23764.0000, 23766.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 23764, 23764, NULL, NULL, NULL, NULL, NULL, '张三', '18173516102', NULL, '湖南', '长沙', NULL, '雨花区', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, '2021-10-02 00:42:21');
INSERT INTO `oms_order` VALUES (1443979617194053633, 6, '202110020042229651443979616036425730', NULL, NULL, NULL, 23764.0000, 23789.0000, 25.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 23764, 23764, NULL, NULL, NULL, NULL, NULL, '张三', '15421564125', NULL, '陕西', '西安', NULL, '龙岗五和南山深南花园1023', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, '2021-10-02 00:42:23');
INSERT INTO `oms_order` VALUES (1443979731832770562, 6, '202110020042502871443979730637393922', NULL, NULL, NULL, 14876.0000, 14901.0000, 25.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 14876, 14876, NULL, NULL, NULL, NULL, NULL, '张三', '15421564125', NULL, '陕西', '西安', NULL, '龙岗五和南山深南花园1023', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, '2021-10-02 00:42:51');
INSERT INTO `oms_order` VALUES (1444194585080266753, 6, '202110021456348301444194581951315969', NULL, NULL, NULL, 14876.0000, 14878.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 14876, 14876, NULL, NULL, NULL, NULL, NULL, '张三', '18173516102', NULL, '湖南', '长沙', NULL, '雨花区', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, '2021-10-02 14:56:36');
INSERT INTO `oms_order` VALUES (1444209325047296001, 6, '202110021555096291444209324086800386', NULL, NULL, NULL, 14876.0000, 14901.0000, 25.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 1, NULL, NULL, 7, 14876, 14876, NULL, NULL, NULL, NULL, NULL, '张三', '15421564125', NULL, '陕西', '西安', NULL, '龙岗五和南山深南花园1023', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, '2021-10-02 15:55:10');
INSERT INTO `oms_order` VALUES (1444678603836014594, 6, '202110032259541751444678601885663234', NULL, NULL, NULL, 14876.0000, 14901.0000, 25.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 1, NULL, NULL, 7, 14876, 14876, NULL, NULL, NULL, NULL, NULL, '张三', '15421564125', NULL, '陕西', '西安', NULL, '龙岗五和南山深南花园1023', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, '2021-10-03 22:59:55');
INSERT INTO `oms_order` VALUES (1447108850430849026, 6, '202110101556504151447108849734561794', NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (1447175738208890882, 6, '202110102022377341447175737651089410', NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (1447175908971589633, 6, '202110102023185821447175908963241986', NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (1447179201839968257, 6, '202110102036236551447179201798066177', NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (1447179601280315393, 6, '202110102037587661447179600730877953', NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (1447179860865789953, 6, '202110102039007851447179860849029122', NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (1447180898230415362, 6, '202110102043081111447180898205265921', NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (1447181103906500609, 6, '202110102043566831447181101935177730', NULL, NULL, NULL, 20864.0000, 20866.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 20864, 20864, NULL, NULL, NULL, NULL, NULL, '张三', '18173516102', NULL, '湖南', '长沙', NULL, '雨花区', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, '2021-10-10 20:43:57');
INSERT INTO `oms_order` VALUES (1447181830536114177, 6, '202110102046501031447181829307183106', NULL, NULL, NULL, 20864.0000, 20889.0000, 25.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 20864, 20864, NULL, NULL, NULL, NULL, NULL, '张三', '15421564125', NULL, '陕西', '西安', NULL, '龙岗五和南山深南花园1023', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, '2021-10-10 20:46:50');
INSERT INTO `oms_order` VALUES (1447182188817756162, 6, '202110102048158121447182188796801026', NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (1447182505626120193, 6, '202110102049313341447182505563222018', NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (1447185203851231233, 6, '202110102100145771447185203524075521', NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (1448295173795713025, 6, '202110132230516931448295172038299650', NULL, NULL, NULL, 20864.0000, 20889.0000, 25.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 20864, 20864, NULL, NULL, NULL, NULL, NULL, '张三', '15421564125', NULL, '陕西', '西安', NULL, '龙岗五和南山深南花园1023', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, '2021-10-13 22:30:52');

-- ----------------------------
-- Table structure for oms_order_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'order_id',
  `order_sn` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'order_sn',
  `spu_id` bigint(20) NULL DEFAULT NULL COMMENT 'spu_id',
  `spu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'spu_name',
  `spu_pic` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'spu_pic',
  `spu_brand` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '品牌',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '商品分类id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '商品sku编号',
  `sku_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品sku名字',
  `sku_pic` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品sku图片',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品sku价格',
  `sku_quantity` int(11) NULL DEFAULT NULL COMMENT '商品购买的数量',
  `sku_attrs_vals` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品销售属性组合（JSON）',
  `promotion_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品促销分解金额',
  `coupon_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '优惠券优惠分解金额',
  `integration_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '积分优惠分解金额',
  `real_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '该商品经过优惠后的分解金额',
  `gift_integration` int(11) NULL DEFAULT NULL COMMENT '赠送积分',
  `gift_growth` int(11) NULL DEFAULT NULL COMMENT '赠送成长值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1448295173984456707 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单项信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_item
-- ----------------------------
INSERT INTO `oms_order_item` VALUES (1443961012452630530, NULL, '202110012328271121443961010728771586', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 2, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 8888.0000, 1, '颜色:流光幻镜;套餐:套餐三', 0.0000, 0.0000, 0.0000, 8888.0000, 8888, 8888);
INSERT INTO `oms_order_item` VALUES (1443961012469407745, NULL, '202110012328271121443961010728771586', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 4, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡四摄 50倍数字变焦  全网通5G手机', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 5988.0000, 1, '颜色:霓影紫;套餐:套餐二', 0.0000, 0.0000, 0.0000, 5988.0000, 5988, 5988);
INSERT INTO `oms_order_item` VALUES (1443979586974093314, NULL, '202110020042156211443979585246040065', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 1, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/e5ba78ec-9cf5-4bbd-86a7-ad2f611e2512_图集1.jpg', 8888.0000, 1, '颜色:流光幻镜;套餐:套餐二', 0.0000, 0.0000, 0.0000, 8888.0000, 8888, 8888);
INSERT INTO `oms_order_item` VALUES (1443979586990870529, NULL, '202110020042156211443979585246040065', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 2, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 8888.0000, 1, '颜色:流光幻镜;套餐:套餐三', 0.0000, 0.0000, 0.0000, 8888.0000, 8888, 8888);
INSERT INTO `oms_order_item` VALUES (1443979586990870530, NULL, '202110020042156211443979585246040065', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 4, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡四摄 50倍数字变焦  全网通5G手机', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 5988.0000, 1, '颜色:霓影紫;套餐:套餐二', 0.0000, 0.0000, 0.0000, 5988.0000, 5988, 5988);
INSERT INTO `oms_order_item` VALUES (1443979605886210049, NULL, '202110020042203111443979604908937217', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 1, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/e5ba78ec-9cf5-4bbd-86a7-ad2f611e2512_图集1.jpg', 8888.0000, 1, '颜色:流光幻镜;套餐:套餐二', 0.0000, 0.0000, 0.0000, 8888.0000, 8888, 8888);
INSERT INTO `oms_order_item` VALUES (1443979605886210050, NULL, '202110020042203111443979604908937217', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 2, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 8888.0000, 1, '颜色:流光幻镜;套餐:套餐三', 0.0000, 0.0000, 0.0000, 8888.0000, 8888, 8888);
INSERT INTO `oms_order_item` VALUES (1443979605886210051, NULL, '202110020042203111443979604908937217', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 4, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡四摄 50倍数字变焦  全网通5G手机', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 5988.0000, 1, '颜色:霓影紫;套餐:套餐二', 0.0000, 0.0000, 0.0000, 5988.0000, 5988, 5988);
INSERT INTO `oms_order_item` VALUES (1443979617261162498, NULL, '202110020042229651443979616036425730', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 1, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/e5ba78ec-9cf5-4bbd-86a7-ad2f611e2512_图集1.jpg', 8888.0000, 1, '颜色:流光幻镜;套餐:套餐二', 0.0000, 0.0000, 0.0000, 8888.0000, 8888, 8888);
INSERT INTO `oms_order_item` VALUES (1443979617269551106, NULL, '202110020042229651443979616036425730', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 2, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 8888.0000, 1, '颜色:流光幻镜;套餐:套餐三', 0.0000, 0.0000, 0.0000, 8888.0000, 8888, 8888);
INSERT INTO `oms_order_item` VALUES (1443979617269551107, NULL, '202110020042229651443979616036425730', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 4, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡四摄 50倍数字变焦  全网通5G手机', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 5988.0000, 1, '颜色:霓影紫;套餐:套餐二', 0.0000, 0.0000, 0.0000, 5988.0000, 5988, 5988);
INSERT INTO `oms_order_item` VALUES (1443979731908268033, NULL, '202110020042502871443979730637393922', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 2, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 8888.0000, 1, '颜色:流光幻镜;套餐:套餐三', 0.0000, 0.0000, 0.0000, 8888.0000, 8888, 8888);
INSERT INTO `oms_order_item` VALUES (1443979731908268034, NULL, '202110020042502871443979730637393922', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 4, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡四摄 50倍数字变焦  全网通5G手机', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 5988.0000, 1, '颜色:霓影紫;套餐:套餐二', 0.0000, 0.0000, 0.0000, 5988.0000, 5988, 5988);
INSERT INTO `oms_order_item` VALUES (1444194585323536386, NULL, '202110021456348301444194581951315969', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 2, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 8888.0000, 1, '颜色:流光幻镜;套餐:套餐三', 0.0000, 0.0000, 0.0000, 8888.0000, 8888, 8888);
INSERT INTO `oms_order_item` VALUES (1444194585331924993, NULL, '202110021456348301444194581951315969', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 4, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡四摄 50倍数字变焦  全网通5G手机', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 5988.0000, 1, '颜色:霓影紫;套餐:套餐二', 0.0000, 0.0000, 0.0000, 5988.0000, 5988, 5988);
INSERT INTO `oms_order_item` VALUES (1444209325185708034, NULL, '202110021555096291444209324086800386', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 2, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 8888.0000, 1, '颜色:流光幻镜;套餐:套餐三', 0.0000, 0.0000, 0.0000, 8888.0000, 8888, 8888);
INSERT INTO `oms_order_item` VALUES (1444209325194096641, NULL, '202110021555096291444209324086800386', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 4, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡四摄 50倍数字变焦  全网通5G手机', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 5988.0000, 1, '颜色:霓影紫;套餐:套餐二', 0.0000, 0.0000, 0.0000, 5988.0000, 5988, 5988);
INSERT INTO `oms_order_item` VALUES (1444678603949260802, NULL, '202110032259541751444678601885663234', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 4, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡四摄 50倍数字变焦  全网通5G手机', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 5988.0000, 1, '颜色:霓影紫;套餐:套餐二', 0.0000, 0.0000, 0.0000, 5988.0000, 5988, 5988);
INSERT INTO `oms_order_item` VALUES (1444678603966038018, NULL, '202110032259541751444678601885663234', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 2, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 8888.0000, 1, '颜色:流光幻镜;套餐:套餐三', 0.0000, 0.0000, 0.0000, 8888.0000, 8888, 8888);
INSERT INTO `oms_order_item` VALUES (1447108850678312961, NULL, '202110101556504151447108849734561794', NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (1447175738443771906, NULL, '202110102022377341447175737651089410', NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (1447175909089030145, NULL, '202110102023185821447175908963241986', NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (1447179201957408769, NULL, '202110102036236551447179201798066177', NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (1447179601406144513, NULL, '202110102037587661447179600730877953', NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (1447179860970647553, NULL, '202110102039007851447179860849029122', NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (1447180898335272961, NULL, '202110102043081111447180898205265921', NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (1447181103994580993, NULL, '202110102043566831447181101935177730', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 4, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡四摄 50倍数字变焦  全网通5G手机', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 5988.0000, 1, '颜色:霓影紫;套餐:套餐二', 0.0000, 0.0000, 0.0000, 5988.0000, 5988, 5988);
INSERT INTO `oms_order_item` VALUES (1447181104002969601, NULL, '202110102043566831447181101935177730', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 5, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡四摄 50倍数字变焦  全网通5G手机', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/0bb896b8-fee8-4933-877c-2c4615c802d6_图集7.jpg', 5988.0000, 1, '颜色:霓影紫;套餐:套餐三', 0.0000, 0.0000, 0.0000, 5988.0000, 5988, 5988);
INSERT INTO `oms_order_item` VALUES (1447181104002969602, NULL, '202110102043566831447181101935177730', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 2, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 8888.0000, 1, '颜色:流光幻镜;套餐:套餐三', 0.0000, 0.0000, 0.0000, 8888.0000, 8888, 8888);
INSERT INTO `oms_order_item` VALUES (1447181830624194562, NULL, '202110102046501031447181829307183106', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 4, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡四摄 50倍数字变焦  全网通5G手机', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 5988.0000, 1, '颜色:霓影紫;套餐:套餐二', 0.0000, 0.0000, 0.0000, 5988.0000, 5988, 5988);
INSERT INTO `oms_order_item` VALUES (1447181830624194563, NULL, '202110102046501031447181829307183106', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 5, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡四摄 50倍数字变焦  全网通5G手机', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/0bb896b8-fee8-4933-877c-2c4615c802d6_图集7.jpg', 5988.0000, 1, '颜色:霓影紫;套餐:套餐三', 0.0000, 0.0000, 0.0000, 5988.0000, 5988, 5988);
INSERT INTO `oms_order_item` VALUES (1447181830632583169, NULL, '202110102046501031447181829307183106', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 2, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 8888.0000, 1, '颜色:流光幻镜;套餐:套餐三', 0.0000, 0.0000, 0.0000, 8888.0000, 8888, 8888);
INSERT INTO `oms_order_item` VALUES (1447182188951973889, NULL, '202110102048158121447182188796801026', NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (1447182505890361345, NULL, '202110102049313341447182505563222018', NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (1447185204035780610, NULL, '202110102100145771447185203524075521', NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 6666.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (1448295173976068098, NULL, '202110132230516931448295172038299650', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 2, '华为 HUAWEI P40 Pro+ 麒麟990 5G  流光幻镜 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡五摄 100倍双目变焦 全网通5G', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/9915d4a7-9f73-4c71-85cc-65c0b7f644dd_图集2.jpg', 8888.0000, 1, '颜色:流光幻镜;套餐:套餐三', 0.0000, 0.0000, 0.0000, 8888.0000, 8888, 8888);
INSERT INTO `oms_order_item` VALUES (1448295173976068099, NULL, '202110132230516931448295172038299650', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 4, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡四摄 50倍数字变焦  全网通5G手机', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/55871b9f-589b-4957-8eb3-9d2c8698406b_图集6.jpg', 5988.0000, 1, '颜色:霓影紫;套餐:套餐二', 0.0000, 0.0000, 0.0000, 5988.0000, 5988, 5988);
INSERT INTO `oms_order_item` VALUES (1448295173984456706, NULL, '202110132230516931448295172038299650', 3, '华为 HUAWEI P40 Pro+ 麒麟990 5G ', NULL, '2', 225, 5, '华为 HUAWEI P40 Pro+ 麒麟990 5G  霓影紫 套餐二 麒麟990 5G SoC芯片 5000万超感知徕卡四摄 50倍数字变焦  全网通5G手机', 'https://mall-fire.oss-cn-shenzhen.aliyuncs.com/2020-06-05/0bb896b8-fee8-4933-877c-2c4615c802d6_图集7.jpg', 5988.0000, 1, '颜色:霓影紫;套餐:套餐三', 0.0000, 0.0000, 0.0000, 5988.0000, 5988, 5988);

-- ----------------------------
-- Table structure for oms_order_operate_history
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_operate_history`;
CREATE TABLE `oms_order_operate_history`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `operate_man` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作人[用户；系统；后台管理员]',
  `create_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `order_status` tinyint(4) NULL DEFAULT NULL COMMENT '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单操作历史记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_operate_history
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_return_apply
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_return_apply`;
CREATE TABLE `oms_order_return_apply`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'order_id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '退货商品id',
  `order_sn` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单编号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '申请时间',
  `member_username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '会员用户名',
  `return_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '退款金额',
  `return_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '退货人姓名',
  `return_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '退货人电话',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]',
  `handle_time` datetime NULL DEFAULT NULL COMMENT '处理时间',
  `sku_img` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品图片',
  `sku_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品名称',
  `sku_brand` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品品牌',
  `sku_attrs_vals` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品销售属性(JSON)',
  `sku_count` int(11) NULL DEFAULT NULL COMMENT '退货数量',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品单价',
  `sku_real_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品实际支付单价',
  `reason` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '原因',
  `description述` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `desc_pics` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '凭证图片，以逗号隔开',
  `handle_note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '处理备注',
  `handle_man` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '处理人员',
  `receive_man` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '收货时间',
  `receive_note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货备注',
  `receive_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货电话',
  `company_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公司收货地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单退货申请' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_return_apply
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_return_reason
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_return_reason`;
CREATE TABLE `oms_order_return_reason`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '退货原因名',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '启用状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT 'create_time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '退货原因' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_return_reason
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_setting
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_setting`;
CREATE TABLE `oms_order_setting`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `flash_order_overtime` int(11) NULL DEFAULT NULL COMMENT '秒杀订单超时关闭时间(分)',
  `normal_order_overtime` int(11) NULL DEFAULT NULL COMMENT '正常订单超时时间(分)',
  `confirm_overtime` int(11) NULL DEFAULT NULL COMMENT '发货后自动确认收货时间（天）',
  `finish_overtime` int(11) NULL DEFAULT NULL COMMENT '自动完成交易时间，不能申请退货（天）',
  `comment_overtime` int(11) NULL DEFAULT NULL COMMENT '订单完成后自动好评时间（天）',
  `member_level` tinyint(2) NULL DEFAULT NULL COMMENT '会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单配置信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_setting
-- ----------------------------

-- ----------------------------
-- Table structure for oms_payment_info
-- ----------------------------
DROP TABLE IF EXISTS `oms_payment_info`;
CREATE TABLE `oms_payment_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_sn` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单号（对外业务号）',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `alipay_trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付宝交易流水号',
  `total_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '支付总金额',
  `subject` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '交易内容',
  `payment_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `confirm_time` datetime NULL DEFAULT NULL COMMENT '确认时间',
  `callback_content` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '回调内容',
  `callback_time` datetime NULL DEFAULT NULL COMMENT '回调时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_id`(`order_id`) USING BTREE,
  UNIQUE INDEX `alipay_trade_no`(`alipay_trade_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1444678989409992707 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '支付信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_payment_info
-- ----------------------------
INSERT INTO `oms_payment_info` VALUES (1444219552081743873, '202110021555096291444209324086800386', NULL, '2021100222001488930501502340', 14901.0000, NULL, '已付款', '2021-10-02 16:35:48', '2021-10-02 16:35:48', '{\"app_id\":\"2021000118622315\",\"auth_app_id\":\"2021000118622315\",\"charset\":\"utf-8\",\"out_trade_no\":\"202110021555096291444209324086800386\",\"seller_id\":\"2088621956499687\",\"timestamp\":\"2021-10-02 15:55:58\",\"total_amount\":\"14901.00\",\"trade_no\":\"2021100222001488930501502340\",\"version\":\"1.0\"}', '2021-10-02 16:35:48');
INSERT INTO `oms_payment_info` VALUES (1444678989409992706, '202110032259541751444678601885663234', NULL, '2021100322001488930501505162', 14901.0000, NULL, '已付款', '2021-10-03 23:01:27', '2021-10-03 23:01:27', '{\"app_id\":\"2021000118622315\",\"auth_app_id\":\"2021000118622315\",\"charset\":\"utf-8\",\"out_trade_no\":\"202110032259541751444678601885663234\",\"seller_id\":\"2088621956499687\",\"timestamp\":\"2021-10-03 23:01:17\",\"total_amount\":\"14901.00\",\"trade_no\":\"2021100322001488930501505162\",\"version\":\"1.0\"}', '2021-10-03 23:01:27');

-- ----------------------------
-- Table structure for oms_refund_info
-- ----------------------------
DROP TABLE IF EXISTS `oms_refund_info`;
CREATE TABLE `oms_refund_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_return_id` bigint(20) NULL DEFAULT NULL COMMENT '退款的订单',
  `refund` decimal(18, 4) NULL DEFAULT NULL COMMENT '退款金额',
  `refund_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '退款交易流水号',
  `refund_status` tinyint(1) NULL DEFAULT NULL COMMENT '退款状态',
  `refund_channel` tinyint(4) NULL DEFAULT NULL COMMENT '退款渠道[1-支付宝，2-微信，3-银联，4-汇款]',
  `refund_content` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '退款信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_refund_info
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;