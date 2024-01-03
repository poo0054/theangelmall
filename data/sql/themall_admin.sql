-- MySQL dump 10.13  Distrib 8.0.35, for Linux (x86_64)
--
-- Host: 192.168.98.51    Database: themall_admin
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Current Database: `themall_admin`
--

CREATE DATABASE /*!32312 IF NOT EXISTS */ `themall_admin` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION = 'N' */;

USE `themall_admin`;

--
-- Table structure for table `oauth2_authorization`
--

DROP TABLE IF EXISTS `oauth2_authorization`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2_authorization`
(
    `id`                            varchar(100) NOT NULL,
    `registered_client_id`          varchar(100) NOT NULL,
    `principal_name`                varchar(200) NOT NULL,
    `authorization_grant_type`      varchar(100) NOT NULL,
    `authorized_scopes`             varchar(1000)     DEFAULT NULL,
    `attributes`                    blob,
    `state`                         varchar(500)      DEFAULT NULL,
    `authorization_code_value`      blob,
    `authorization_code_issued_at`  timestamp    NULL DEFAULT NULL,
    `authorization_code_expires_at` timestamp    NULL DEFAULT NULL,
    `authorization_code_metadata`   blob,
    `access_token_value`            blob,
    `access_token_issued_at`        timestamp    NULL DEFAULT NULL,
    `access_token_expires_at`       timestamp    NULL DEFAULT NULL,
    `access_token_metadata`         blob,
    `access_token_type`             varchar(100)      DEFAULT NULL,
    `access_token_scopes`           varchar(1000)     DEFAULT NULL,
    `oidc_id_token_value`           blob,
    `oidc_id_token_issued_at`       timestamp    NULL DEFAULT NULL,
    `oidc_id_token_expires_at`      timestamp    NULL DEFAULT NULL,
    `oidc_id_token_metadata`        blob,
    `refresh_token_value`           blob,
    `refresh_token_issued_at`       timestamp    NULL DEFAULT NULL,
    `refresh_token_expires_at`      timestamp    NULL DEFAULT NULL,
    `refresh_token_metadata`        blob,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `oauth2_authorization_consent`
--

DROP TABLE IF EXISTS `oauth2_authorization_consent`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2_authorization_consent`
(
    `registered_client_id` varchar(100)  NOT NULL,
    `principal_name`       varchar(200)  NOT NULL,
    `authorities`          varchar(1000) NOT NULL,
    PRIMARY KEY (`registered_client_id`, `principal_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_authorization_consent`
--

--
-- Table structure for table `oauth2_authorized_client`
--

DROP TABLE IF EXISTS `oauth2_authorized_client`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2_authorized_client`
(
    `client_registration_id`  varchar(100) NOT NULL,
    `principal_name`          varchar(200) NOT NULL,
    `access_token_type`       varchar(100) NOT NULL,
    `access_token_value`      blob         NOT NULL,
    `access_token_issued_at`  timestamp    NOT NULL,
    `access_token_expires_at` timestamp    NOT NULL,
    `access_token_scopes`     varchar(1000)         DEFAULT NULL,
    `refresh_token_value`     blob,
    `refresh_token_issued_at` timestamp    NULL     DEFAULT NULL,
    `created_at`              timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`client_registration_id`, `principal_name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_authorized_client`
--


--
-- Table structure for table `oauth2_registered_client`
--

DROP TABLE IF EXISTS `oauth2_registered_client`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2_registered_client`
(
    `id`                            varchar(100)  NOT NULL,
    `client_id`                     varchar(100)  NOT NULL,
    `client_id_issued_at`           timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `client_secret`                 varchar(200)           DEFAULT NULL,
    `client_secret_expires_at`      timestamp     NULL     DEFAULT NULL,
    `client_name`                   varchar(200)  NOT NULL,
    `client_authentication_methods` varchar(1000) NOT NULL,
    `authorization_grant_types`     varchar(1000) NOT NULL,
    `redirect_uris`                 varchar(1000)          DEFAULT NULL,
    `scopes`                        varchar(1000) NOT NULL,
    `client_settings`               varchar(2000) NOT NULL,
    `token_settings`                varchar(2000) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_registered_client`
--


--
-- Table structure for table `sys_captcha`
--

DROP TABLE IF EXISTS `sys_captcha`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_captcha`
(
    `uuid`        char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT 'uuid',
    `code`        varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '验证码',
    `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
    PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='系统验证码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_captcha`
--

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_config`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `param_key`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   DEFAULT NULL COMMENT 'key',
    `param_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'value',
    `status`      tinyint                                                        DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `param_key` (`param_key`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='系统配置信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config`
    DISABLE KEYS */;
INSERT INTO `sys_config`
VALUES (1, 'CLOUD_STORAGE_CONFIG_KEY',
        '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}',
        0, '云存储配置信息');
/*!40000 ALTER TABLE `sys_config`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_log`
--

DROP TABLE IF EXISTS `sys_log`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_log`
(
    `id`             varchar(30) NOT NULL COMMENT '主键',
    `version`        int          DEFAULT NULL COMMENT '版本号',
    `trace_id`       varchar(64)  DEFAULT NULL COMMENT '追踪ID',
    `log_title`      varchar(100) DEFAULT NULL COMMENT '模块标题',
    `business_type`  int          DEFAULT '0' COMMENT '业务类型（1作为服务提供方 13作为客户请求方）',
    `method_name`    varchar(200) DEFAULT NULL COMMENT '方法名称',
    `request_method` varchar(10)  DEFAULT NULL COMMENT '请求方式',
    `operator_type`  int          DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
    `oper_name`      varchar(60)  DEFAULT NULL COMMENT '操作人员',
    `oper_url`       varchar(255) DEFAULT NULL COMMENT '请求URL',
    `oper_ip`        varchar(128) DEFAULT NULL COMMENT '主机地址',
    `oper_location`  varchar(255) DEFAULT NULL COMMENT '操作地点',
    `oper_param`     text COMMENT '请求参数',
    `json_result`    text COMMENT '返回参数',
    `status`         int          DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
    `error_msg`      text COMMENT '错误消息',
    `oper_time`      datetime     DEFAULT NULL COMMENT '操作时间',
    `cost_time`      int          DEFAULT '0' COMMENT '消耗时间秒数',
    `remarks`        varchar(500) DEFAULT NULL COMMENT '备注',
    `create_by`      varchar(60)  DEFAULT NULL COMMENT '创建人',
    `create_date`    datetime     DEFAULT NULL COMMENT '创建时间',
    `update_by`      varchar(60)  DEFAULT NULL COMMENT '更新人',
    `update_date`    datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3 COMMENT ='接口日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_log`
--

LOCK TABLES `sys_log` WRITE;
/*!40000 ALTER TABLE `sys_log`
    DISABLE KEYS */;
INSERT INTO `sys_log`
VALUES ('1736567360044818433', NULL, 'd980bb507726f0d2', '修改角色', 0,
        'io.renren.modules.sys.controller.SysRoleController.update()', 'POST', 1, 'admin',
        '/themall-admin/sys/role/update', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"createUserId\":1,\"menuIdList\":[15,18,19,23,5,7,1735600587225092097,29,30,1735602736864612353,1735603201169870849,-666666,1,2,3,4,6,27,31,32,34],\"remark\":\"用户基本权限\",\"roleId\":1,\"roleName\":\"user\"}',
        '{\"msg\":\"成功\",\"code\":\"00000\"}', 0, NULL, '2023-12-18 10:01:36', 1506, NULL, 'admin', NULL, 'admin',
        NULL),
       ('1736568204295299073', NULL, '6cf90ac4990235dc', '修改角色', 0,
        'io.renren.modules.sys.controller.SysRoleController.update()', 'POST', 1, 'admin',
        '/themall-admin/sys/role/update', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"createUserId\":1,\"menuIdList\":[15,-666666,1,2],\"remark\":\"用户基本权限\",\"roleId\":1,\"roleName\":\"user\"}',
        '{\"msg\":\"成功\",\"code\":\"00000\"}', 0, NULL, '2023-12-18 10:04:58', 539, NULL, 'admin', NULL, 'admin',
        NULL),
       ('1736568485041037314', NULL, 'ba04e264e07cbb8c', '修改角色', 0,
        'io.renren.modules.sys.controller.SysRoleController.update()', 'POST', 1, 'admin',
        '/themall-admin/sys/role/update', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"createUserId\":1,\"menuIdList\":[15,-666666,1,2],\"remark\":\"用户基本权限\",\"roleId\":1,\"roleName\":\"user\"}',
        '{\"msg\":\"成功\",\"code\":\"00000\"}', 0, NULL, '2023-12-18 10:05:48', 17857, NULL, 'admin', NULL, 'admin',
        NULL),
       ('1736569336505720833', NULL, 'bf7ad1a70b7697cf', '修改角色', 0,
        'io.renren.modules.sys.controller.SysRoleController.update()', 'POST', 1, 'admin',
        '/themall-admin/sys/role/update', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"createUserId\":1,\"menuIdList\":[15,17,-666666],\"remark\":\"用户基本权限\",\"roleId\":1,\"roleName\":\"user\"}',
        '{\"msg\":\"成功\",\"code\":\"00000\"}', 0, NULL, '2023-12-18 10:09:22', 6334, NULL, 'admin', NULL, 'admin',
        NULL),
       ('1736569443334643713', NULL, '8b9da287ddaf3bf9', '修改角色', 0,
        'io.renren.modules.sys.controller.SysRoleController.update()', 'POST', 1, 'admin',
        '/themall-admin/sys/role/update', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"createUserId\":1,\"menuIdList\":[15,19,23,7,1735600587225092097,29,1735602736864612353,-666666],\"remark\":\"用户基本权限\",\"roleId\":1,\"roleName\":\"user\"}',
        '{\"msg\":\"成功\",\"code\":\"00000\"}', 0, NULL, '2023-12-18 10:09:53', 623, NULL, 'admin', NULL, 'admin',
        NULL),
       ('1736572152741781506', NULL, 'c69d337e91675892', '修改角色', 0,
        'io.renren.modules.sys.controller.SysRoleController.update()', 'POST', 1, 'admin',
        '/themall-admin/sys/role/update', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"createUserId\":1,\"menuIdList\":[15,19,23,5,7,1735600587225092097,29,1735602736864612353,-666666],\"remark\":\"用户基本权限\",\"roleId\":1,\"roleName\":\"user\"}',
        '{\"msg\":\"成功\",\"code\":\"00000\"}', 0, NULL, '2023-12-18 10:20:39', 920, NULL, 'admin', NULL, 'admin',
        NULL),
       ('1739455763511078913', NULL, 'a920245f5da82ab6', '修改角色', 0,
        'io.renren.modules.sys.controller.SysRoleController.update()', 'POST', 1, 'admin',
        '/themall-admin/sys/role/update', '10.244.0.0', '10.244.6.197',
        '{\"createUserId\":1,\"menuIdList\":[1,3,19,4,23,27,1735600587225092097,31,32,34,37,41,42,43,44,45,46,-666666],\"remark\":\"用户基本权限\",\"roleId\":1,\"roleName\":\"user\"}',
        '{\"msg\":\"成功\",\"code\":\"00000\"}', 0, NULL, '2023-12-26 09:19:05', 1299, NULL, 'admin',
        '2023-12-26 09:19:06', 'admin', '2023-12-26 09:19:06'),
       ('1739467443792338946', NULL, 'd76dc5560d474ae3', '保存菜单', 0,
        'io.renren.modules.sys.controller.SysMenuController.save()', 'POST', 1, 'admin', '/themall-admin/sys/menu/save',
        '10.244.0.0', '10.244.2.26',
        '{\"icon\":\"\",\"menuId\":1739467442815066113,\"name\":\"test\",\"orderNum\":0,\"parentId\":44,\"perms\":\"\",\"type\":1,\"url\":\"/test\"}',
        '{\"msg\":\"成功\",\"code\":\"00000\"}', 0, NULL, '2023-12-26 10:05:31', 282, NULL, 'admin',
        '2023-12-26 10:05:31', 'admin', '2023-12-26 10:05:31'),
       ('1739915528723722242', NULL, '29a40dd8b3778ea9', '修改密码', 0,
        'io.renren.modules.sys.controller.SysUserController.password()', 'POST', 1, 'admin',
        '/themall-admin/sys/user/password', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"newPassword\":\"Poo0054.\",\"password\":\"admin\"}', NULL, 1,
        'There is no PasswordEncoder mapped for the id \"null\"', '2023-12-27 15:45:56', 6745, NULL, 'admin',
        '2023-12-27 15:46:03', 'admin', '2023-12-27 15:46:03'),
       ('1739915762736525313', NULL, 'b33d1116bc541bab', '修改密码', 0,
        'io.renren.modules.sys.controller.SysUserController.password()', 'POST', 1, 'admin',
        '/themall-admin/sys/user/password', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"newPassword\":\"Poo0054.\",\"password\":\"admin\"}', NULL, 1,
        'There is no PasswordEncoder mapped for the id \"null\"', '2023-12-27 15:46:56', 2508, NULL, 'admin',
        '2023-12-27 15:46:59', 'admin', '2023-12-27 15:46:59'),
       ('1739915848304521217', NULL, '3d5442e8aef6558d', '修改密码', 0,
        'io.renren.modules.sys.controller.SysUserController.password()', 'POST', 1, 'admin',
        '/themall-admin/sys/user/password', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"newPassword\":\"Poo0054.\",\"password\":\"admin\"}', NULL, 1,
        'There is no PasswordEncoder mapped for the id \"null\"', '2023-12-27 15:47:10', 9030, NULL, 'admin',
        '2023-12-27 15:47:19', 'admin', '2023-12-27 15:47:19'),
       ('1739915967913488386', NULL, '7ad2800232770e26', '修改密码', 0,
        'io.renren.modules.sys.controller.SysUserController.password()', 'POST', 1, 'admin',
        '/themall-admin/sys/user/password', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"newPassword\":\"Poo0054.\",\"password\":\"admin\"}', '{\"msg\":\"帐号或密码校验失败\",\"code\":\"A0120\"}',
        0, NULL, '2023-12-27 15:47:42', 5676, NULL, 'admin', '2023-12-27 15:47:47', 'admin', '2023-12-27 15:47:47'),
       ('1739916871442067458', NULL, 'b4629b3609a83055', '修改密码', 0,
        'io.renren.modules.sys.controller.SysUserController.password()', 'POST', 1, 'admin',
        '/themall-admin/sys/user/password', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"newPassword\":\"Poo0054.\",\"password\":\"admin\"}', '{\"msg\":\"帐号或密码校验失败\",\"code\":\"A0120\"}',
        0, NULL, '2023-12-27 15:51:18', 4791, NULL, 'admin', '2023-12-27 15:51:23', 'admin', '2023-12-27 15:51:23'),
       ('1739917153886498817', NULL, '384a015f59ec2461', '修改密码', 0,
        'io.renren.modules.sys.controller.SysUserController.password()', 'POST', 1, 'admin',
        '/themall-admin/sys/user/password', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"newPassword\":\"Poo0054.\",\"password\":\"admin\"}', '{\"msg\":\"帐号或密码校验失败\",\"code\":\"A0120\"}',
        0, NULL, '2023-12-27 15:51:41', 49359, NULL, 'admin', '2023-12-27 15:52:30', 'admin', '2023-12-27 15:52:30'),
       ('1739917232856854530', NULL, 'bd789e093ad66e8d', '修改密码', 0,
        'io.renren.modules.sys.controller.SysUserController.password()', 'POST', 1, 'admin',
        '/themall-admin/sys/user/password', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"newPassword\":\"Poo0054.\",\"password\":\"admin\"}', '{\"msg\":\"帐号或密码校验失败\",\"code\":\"A0120\"}',
        0, NULL, '2023-12-27 15:52:39', 10485, NULL, 'admin', '2023-12-27 15:52:49', 'admin', '2023-12-27 15:52:49'),
       ('1739917495151849473', NULL, '406f5b3d99f0e4fd', '修改密码', 0,
        'io.renren.modules.sys.controller.SysUserController.password()', 'POST', 1, 'admin',
        '/themall-admin/sys/user/password', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"newPassword\":\"Poo0054.\",\"password\":\"admin\"}', '{\"msg\":\"帐号或密码校验失败\",\"code\":\"A0120\"}',
        0, NULL, '2023-12-27 15:53:51', 697, NULL, 'admin', '2023-12-27 15:53:52', 'admin', '2023-12-27 15:53:52'),
       ('1739917683845197826', NULL, '5c5173b65f40ddbc', '修改密码', 0,
        'io.renren.modules.sys.controller.SysUserController.password()', 'POST', 1, 'admin',
        '/themall-admin/sys/user/password', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"newPassword\":\"Poo0054.\",\"password\":\"admin\"}', '{\"msg\":\"帐号或密码校验失败\",\"code\":\"A0120\"}',
        0, NULL, '2023-12-27 15:54:19', 17267, NULL, 'admin', '2023-12-27 15:54:37', 'admin', '2023-12-27 15:54:37'),
       ('1739918215913631745', NULL, 'bfb45786af53803d', '修改密码', 0,
        'io.renren.modules.sys.controller.SysUserController.password()', 'POST', 1, 'admin',
        '/themall-admin/sys/user/password', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"newPassword\":\"Poo0054.\",\"password\":\"admin\"}', '{\"msg\":\"帐号或密码校验失败\",\"code\":\"A0120\"}',
        0, NULL, '2023-12-27 15:56:34', 9880, NULL, 'admin', '2023-12-27 15:56:43', 'admin', '2023-12-27 15:56:43'),
       ('1739918342329954305', NULL, '5029d22556ba08c9', '修改密码', 0,
        'io.renren.modules.sys.controller.SysUserController.password()', 'POST', 1, 'admin',
        '/themall-admin/sys/user/password', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"newPassword\":\"Poo0054.\",\"password\":\"admin\"}', '{\"msg\":\"帐号或密码校验失败\",\"code\":\"A0120\"}',
        0, NULL, '2023-12-27 15:57:02', 12060, NULL, 'admin', '2023-12-27 15:57:14', 'admin', '2023-12-27 15:57:14'),
       ('1739918448743641090', NULL, 'bb704743137d24e6', '修改密码', 0,
        'io.renren.modules.sys.controller.SysUserController.password()', 'POST', 1, 'admin',
        '/themall-admin/sys/user/password', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"newPassword\":\"Poo0054.\",\"password\":\"admin\"}', '{\"msg\":\"帐号或密码校验失败\",\"code\":\"A0120\"}',
        0, NULL, '2023-12-27 15:57:29', 10090, NULL, 'admin', '2023-12-27 15:57:39', 'admin', '2023-12-27 15:57:39'),
       ('1739918597943418881', NULL, '3c7fcc091d001568', '修改密码', 0,
        'io.renren.modules.sys.controller.SysUserController.password()', 'POST', 1, 'admin',
        '/themall-admin/sys/user/password', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"newPassword\":\"Poo0054.\",\"password\":\"admin\"}', '{\"msg\":\"帐号或密码校验失败\",\"code\":\"A0120\"}',
        0, NULL, '2023-12-27 15:58:12', 2753, NULL, 'admin', '2023-12-27 15:58:14', 'admin', '2023-12-27 15:58:14'),
       ('1739918955814019074', NULL, 'cee87d1d80538654', '修改密码', 0,
        'io.renren.modules.sys.controller.SysUserController.password()', 'POST', 1, 'admin',
        '/themall-admin/sys/user/password', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"newPassword\":\"Poo0054.\",\"password\":\"admin\"}', '{\"msg\":\"帐号或密码校验失败\",\"code\":\"A0120\"}',
        0, NULL, '2023-12-27 15:59:20', 19550, NULL, 'admin', '2023-12-27 15:59:40', 'admin', '2023-12-27 15:59:40'),
       ('1739919074554765314', NULL, '97100b2db7a9c360', '修改密码', 0,
        'io.renren.modules.sys.controller.SysUserController.password()', 'POST', 1, 'admin',
        '/themall-admin/sys/user/password', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"newPassword\":\"Poo0054.\",\"password\":\"admin\"}', '{\"msg\":\"成功\",\"code\":\"00000\"}', 0, NULL,
        '2023-12-27 16:00:05', 3178, NULL, 'admin', '2023-12-27 16:00:08', 'admin', '2023-12-27 16:00:08'),
       ('1739925766206009346', NULL, '1f445b91b5bb437a', '修改角色', 2,
        'io.renren.modules.sys.controller.SysRoleController.update()', 'POST', 1, 'admin',
        '/themall-admin/sys/role/update', '0:0:0:0:0:0:0:1%0', '127.0.1.1',
        '{\"createUserId\":1,\"menuIdList\":[1,3,19,4,23,27,1735600587225092097,31,32,34,37,41,42,-666666],\"remark\":\"用户基本权限\",\"roleId\":1,\"roleName\":\"user\"}',
        '{\"msg\":\"成功\",\"code\":\"00000\"}', 0, NULL, '2023-12-27 16:26:43', 772, NULL, 'admin',
        '2023-12-27 16:26:44', 'admin', '2023-12-27 16:26:44');
/*!40000 ALTER TABLE `sys_log`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu`
(
    `menu_id`   bigint NOT NULL AUTO_INCREMENT,
    `parent_id` bigint                                                        DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
    `name`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '菜单名称',
    `url`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单URL',
    `perms`     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
    `type`      int                                                           DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
    `icon`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '菜单图标',
    `order_num` int                                                           DEFAULT NULL COMMENT '排序',
    PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1739467442815066114
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='菜单管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu`
    DISABLE KEYS */;
INSERT INTO `sys_menu`
VALUES (1, 0, '系统管理', NULL, 'sys', 0, 'system', 0),
       (2, 1, '管理员列表', 'sys/user', 'sys:user', 1, 'admin', 1),
       (3, 1, '角色管理', 'sys/role', 'sys:role', 1, 'role', 2),
       (4, 1, '菜单管理', 'sys/menu', 'sys:menu', 1, 'menu', 3),
       (5, 1, 'SQL监控', 'http://localhost:8088/themall-admin/druid/sql.html', 'sys:sql', 1, 'sql', 4),
       (6, 1, '定时任务', 'job/schedule', 'sys:schedule', 1, 'job', 5),
       (7, 6, '查看', NULL, 'sys:schedule:list', 2, NULL, 0),
       (8, 6, '新增', NULL, 'sys:schedule:save', 2, NULL, 0),
       (9, 6, '修改', NULL, 'sys:schedule:update', 2, NULL, 0),
       (10, 6, '删除', NULL, 'sys:schedule:delete', 2, NULL, 0),
       (11, 6, '暂停', NULL, 'sys:schedule:pause', 2, NULL, 0),
       (12, 6, '恢复', NULL, 'sys:schedule:resume', 2, NULL, 0),
       (13, 6, '立即执行', NULL, 'sys:schedule:run', 2, NULL, 0),
       (14, 6, '日志列表', NULL, 'sys:schedule:log', 2, NULL, 0),
       (15, 2, '查看', NULL, 'sys:user:list', 2, NULL, 0),
       (16, 2, '新增', NULL, 'sys:user:save', 2, NULL, 0),
       (17, 2, '修改', NULL, 'sys:user:update', 2, NULL, 0),
       (18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0),
       (19, 3, '查看', NULL, 'sys:role:list', 2, NULL, 0),
       (20, 3, '新增', NULL, 'sys:role:save', 2, NULL, 0),
       (21, 3, '修改', NULL, 'sys:role:update', 2, NULL, 0),
       (22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0),
       (23, 4, '查看', NULL, 'sys:menu:list', 2, NULL, 0),
       (24, 4, '新增', NULL, 'sys:menu:save', 2, NULL, 0),
       (25, 4, '修改', NULL, 'sys:menu:update', 2, NULL, 0),
       (26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0),
       (27, 1, '参数管理', 'sys/config', 'sys:config', 1, 'config', 6),
       (29, 1, '系统日志', 'sys/log', 'sys:log:list', 1, 'log', 7),
       (30, 1, '文件上传', 'oss/oss', 'sys:oss:all', 1, 'oss', 6),
       (31, 0, '商品系统', NULL, 'product', 0, 'editor', 0),
       (32, 31, '分类维护', 'product/category', 'product:category', 1, 'menu', 0),
       (34, 31, '品牌管理', 'product/brand', 'product:brand', 1, 'editor', 0),
       (37, 31, '平台属性', 'product/attr', 'product:attr', 0, 'system', 0),
       (38, 37, '属性分组', 'product/attrgroup', 'product:attrgroup', 1, 'tubiao', 0),
       (39, 37, '规格参数', 'product/baseattr', 'product:attrgroup', 1, 'log', 0),
       (40, 37, '销售属性', 'product/saleattr', 'product:saleattr', 1, 'zonghe', 0),
       (41, 31, '商品维护', 'product/spu', 'product:spu', 0, 'zonghe', 0),
       (42, 0, '优惠营销', NULL, 'coupon', 0, 'mudedi', 0),
       (43, 0, '库存系统', NULL, 'ware', 0, 'shouye', 0),
       (44, 0, '订单系统', NULL, 'order', 0, 'config', 0),
       (45, 0, '用户系统', NULL, 'member', 0, 'admin', 0),
       (46, 0, '内容管理', NULL, 'content', 0, 'sousuo', 0),
       (47, 42, '优惠券管理', 'coupon/coupon', '', 1, 'zhedie', 0),
       (48, 42, '发放记录', 'coupon/history', '', 1, 'sql', 0),
       (49, 42, '专题活动', 'coupon/subject', '', 1, 'tixing', 0),
       (50, 42, '秒杀活动', 'coupon/seckill', '', 1, 'daohang', 0),
       (51, 42, '积分维护', 'coupon/bounds', '', 1, 'geren', 0),
       (52, 42, '满减折扣', 'coupon/full', '', 1, 'shoucang', 0),
       (53, 43, '仓库维护', 'ware/wareinfo', '', 1, 'shouye', 0),
       (54, 43, '库存工作单', 'ware/task', '', 1, 'log', 0),
       (55, 43, '商品库存', 'ware/sku', '', 1, 'jiesuo', 0),
       (56, 44, '订单查询', 'order/order', '', 1, 'zhedie', 0),
       (57, 44, '退货单处理', 'order/return', '', 1, 'shanchu', 0),
       (58, 44, '等级规则', 'order/settings', '', 1, 'system', 0),
       (59, 44, '支付流水查询', 'order/payment', '', 1, 'job', 0),
       (60, 44, '退款流水查询', 'order/refund', '', 1, 'mudedi', 0),
       (61, 45, '会员列表', 'member/member', '', 1, 'geren', 0),
       (62, 45, '会员等级', 'member/level', '', 1, 'tubiao', 0),
       (63, 45, '积分变化', 'member/growth', '', 1, 'bianji', 0),
       (64, 45, '统计信息', 'member/statistics', '', 1, 'sql', 0),
       (65, 46, '首页推荐', 'content/index', '', 1, 'shouye', 0),
       (66, 46, '分类热门', 'content/category', '', 1, 'zhedie', 0),
       (67, 46, '评论管理', 'content/comments', '', 1, 'pinglun', 0),
       (68, 41, 'spu管理', 'product/spu', '', 1, 'config', 0),
       (69, 41, '发布商品', 'product/spuadd', '', 1, 'bianji', 0),
       (70, 43, '采购单维护', '', '', 0, 'tubiao', 0),
       (71, 70, '采购需求', 'ware/purchaseitem', '', 1, 'editor', 0),
       (72, 70, '采购单', 'ware/purchase', '', 1, 'menu', 0),
       (73, 41, '商品管理', 'product/manager', '', 1, 'zonghe', 0),
       (74, 42, '会员价格', 'coupon/memberprice', '', 1, 'admin', 0),
       (75, 42, '每日秒杀', 'coupon/seckillsession', '', 1, 'job', 0),
       (1735600587225092010, 27, '删除', '', 'sys:config:delete', 2, '', 0),
       (1735600587225092097, 27, '查看', '', 'sys:config:list', 2, '', 0),
       (1735600587225092098, 27, '新增', '', 'sys:config:save', 2, '', 0),
       (1735600587225092099, 27, '修改', '', 'sys:config:update', 2, '', 0),
       (1735602736864612353, 32, '查看', '', 'product:category:list', 2, '', 0),
       (1735602736864612354, 32, '新增', '', 'product:category:save', 2, '', 0),
       (1735602736864612355, 32, '修改', '', 'product:category:update', 2, '', 0),
       (1735602736864612356, 32, '删除', '', 'product:category:delete', 2, '', 0),
       (1735603201169870849, 34, '查看', '', 'product:brand:list', 2, '', 0),
       (1735603201169870850, 34, '新增', '', 'product:brand:save', 2, '', 0),
       (1735603201169870851, 34, '修改', '', 'product:brand:update', 2, '', 0),
       (1735603201169870852, 34, '删除', '', 'product:brand:delete', 2, '', 0),
       (1739467442815066113, 44, 'test', '/test', '', 1, '', 0);
/*!40000 ALTER TABLE `sys_menu`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oss`
--

DROP TABLE IF EXISTS `sys_oss`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_oss`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `url`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'URL地址',
    `create_date` datetime                                                      DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='文件上传';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oss`
--

LOCK TABLES `sys_oss` WRITE;
/*!40000 ALTER TABLE `sys_oss`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_oss`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role`
(
    `role_id`        bigint NOT NULL AUTO_INCREMENT,
    `role_name`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名称',
    `remark`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `create_user_id` bigint                                                        DEFAULT NULL COMMENT '创建者ID',
    `create_time`    datetime                                                      DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1732601128467795971
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role`
    DISABLE KEYS */;
INSERT INTO `sys_role`
VALUES (1, 'user', '用户基本权限', 1, '2023-12-12 18:25:03'),
       (1732601128467795970, 'sys', '系统权限', 1, '2023-12-07 11:21:14');
/*!40000 ALTER TABLE `sys_role`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `role_id` bigint DEFAULT NULL COMMENT '角色ID',
    `menu_id` bigint DEFAULT NULL COMMENT '菜单ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1739925765794967554
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='角色与菜单对应关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu`
    DISABLE KEYS */;
INSERT INTO `sys_role_menu`
VALUES (1732601128845283329, 1732601128467795970, 1),
       (1732601129008861186, 1732601128467795970, 2),
       (1732601129172439041, 1732601128467795970, 15),
       (1732601129344405506, 1732601128467795970, 16),
       (1732601129516371970, 1732601128467795970, 17),
       (1732601129692532738, 1732601128467795970, 18),
       (1732601129877082114, 1732601128467795970, 3),
       (1732601130061631490, 1732601128467795970, 19),
       (1732601130246180865, 1732601128467795970, 20),
       (1732601130409758721, 1732601128467795970, 21),
       (1732601130577530882, 1732601128467795970, 22),
       (1732601130741108737, 1732601128467795970, 4),
       (1732601134042025985, 1732601128467795970, 23),
       (1732601134251741186, 1732601128467795970, 24),
       (1732601134427901953, 1732601128467795970, 25),
       (1732601134595674114, 1732601128467795970, 26),
       (1732601134767640577, 1732601128467795970, 5),
       (1732601134935412737, 1732601128467795970, 6),
       (1732601135115767810, 1732601128467795970, 7),
       (1732601135275151362, 1732601128467795970, 8),
       (1732601135480672258, 1732601128467795970, 9),
       (1732601135665221633, 1732601128467795970, 10),
       (1732601135870742530, 1732601128467795970, 11),
       (1732601136122400769, 1732601128467795970, 12),
       (1732601136323727361, 1732601128467795970, 13),
       (1732601136504082434, 1732601128467795970, 14),
       (1732601136663465986, 1732601128467795970, 27),
       (1732601136822849538, 1732601128467795970, 29),
       (1732601136986427394, 1732601128467795970, 30),
       (1732601137150005249, 1732601128467795970, -666666),
       (1739925763601346562, 1, 1),
       (1739925763802673154, 1, 3),
       (1739925763962056706, 1, 19),
       (1739925764117245953, 1, 4),
       (1739925764272435201, 1, 23),
       (1739925764444401665, 1, 27),
       (1739925764612173825, 1, 1735600587225092097),
       (1739925764775751682, 1, 31),
       (1739925764947718145, 1, 32),
       (1739925765115490305, 1, 34),
       (1739925765274873857, 1, 37),
       (1739925765434257410, 1, 41),
       (1739925765614612482, 1, 42),
       (1739925765794967553, 1, -666666);
/*!40000 ALTER TABLE `sys_role_menu`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user`
(
    `user_id`        bigint NOT NULL AUTO_INCREMENT,
    `oauth_id`       varchar(64) COLLATE utf8mb4_general_ci                        DEFAULT NULL COMMENT '第三方id',
    `username`       varchar(50) COLLATE utf8mb4_general_ci                        DEFAULT NULL COMMENT '用户名',
    `oauth_name`     varchar(64) COLLATE utf8mb4_general_ci                        DEFAULT NULL COMMENT '第三方用户名',
    `password`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
    `salt`           varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '盐',
    `email`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
    `mobile`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
    `status`         tinyint                                                       DEFAULT '1' COMMENT '状态  0：禁用   1：正常',
    `create_user_id` bigint                                                        DEFAULT NULL COMMENT '创建者ID',
    `create_time`    datetime                                                      DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`user_id`) USING BTREE,
    UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1739455817501605890
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='系统用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user`
    DISABLE KEYS */;
INSERT INTO `sys_user`
VALUES (1, NULL, 'admin', NULL, '123', '',
        'root@renren.io', '13612345678', 1, 1, '2016-11-11 11:11:11'),
       (2, NULL, 'root', NULL, '123456', '',
        'root@renren.io', '13612345678', 1, 1, '2016-11-11 11:11:11');
/*!40000 ALTER TABLE `sys_user`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `user_id` bigint DEFAULT NULL COMMENT '用户ID',
    `role_id` bigint DEFAULT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1739455817593880578
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='用户与角色对应关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role`
    DISABLE KEYS */;
INSERT INTO `sys_user_role`
VALUES (1, 2, 1),
       (1735581167861882881, 85894494, 1),
       (1739455354294956034, 1739455354194292737, 1),
       (1739455817593880577, 1739455817501605889, 1);
/*!40000 ALTER TABLE `sys_user_role`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_token`
--

DROP TABLE IF EXISTS `sys_user_token`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_token`
(
    `user_id`     bigint                                                        NOT NULL,
    `token`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'token',
    `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`user_id`) USING BTREE,
    UNIQUE KEY `token` (`token`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='系统用户Token';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_token`
--

LOCK TABLES `sys_user_token` WRITE;
/*!40000 ALTER TABLE `sys_user_token`
    DISABLE KEYS */;
INSERT INTO `sys_user_token`
VALUES (1, '0332856f6db4373867f91963acda468a', '2023-12-05 04:45:01', '2023-12-04 16:45:01');
/*!40000 ALTER TABLE `sys_user_token`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user`
(
    `user_id`     bigint                                                       NOT NULL AUTO_INCREMENT,
    `username`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
    `mobile`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
    `password`    varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
    `create_time` datetime                                                     DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`user_id`) USING BTREE,
    UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user`
    DISABLE KEYS */;
INSERT INTO `tb_user`
VALUES (1, 'mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',
        '2017-03-23 22:37:41');
/*!40000 ALTER TABLE `tb_user`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2023-12-28 10:18:24
