-- MySQL dump 10.13  Distrib 8.0.35, for Linux (x86_64)
--
-- Host: 192.168.98.11    Database: nacos-config
-- ------------------------------------------------------
-- Server version	8.0.34

--
-- Table structure for table `config_info`
--

DROP TABLE IF EXISTS `config_info`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info`
(
    `id`           bigint                                                 NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin          DEFAULT NULL,
    `content`      longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin     NOT NULL COMMENT 'content',
    `md5`          varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime                                               NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
    `gmt_modified` datetime                                               NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
    `src_user`     text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'source user',
    `src_ip`       varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin           DEFAULT NULL COMMENT 'source ip',
    `app_name`     varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin          DEFAULT NULL,
    `tenant_id`    varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin          DEFAULT '' COMMENT '租户字段',
    `c_desc`       varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin          DEFAULT NULL,
    `c_use`        varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin           DEFAULT NULL,
    `effect`       varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin           DEFAULT NULL,
    `type`         varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin           DEFAULT NULL,
    `c_schema`     text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 272
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin
  ROW_FORMAT = DYNAMIC COMMENT ='config_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info`
--

LOCK TABLES `config_info` WRITE;
/*!40000 ALTER TABLE `config_info`
    DISABLE KEYS */;
INSERT INTO `config_info`
VALUES (1, 'public.yml', 'DEFAULT_GROUP',
        'spring:\n  zipkin:\n    base-url: http://192.168.56.10:9411\n    locator:\n      discovery:\n        #关闭服务发现，否则springcloud会把zipkin的url当成服务名称\n        enabled: false\n    sender:\n      #设置http发送数据\n      type: web\n  sleuth:\n    sampler:\n      #      设置抽取采集率为100%，默认为10%\n      probability: 1\n\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: localhost:8333\n    nacos:\n      discovery:\n        server-addr: theangel.vip:8848\n        namespace: 9e9bfac1-f266-4fc4-b220-2c95997c532b\n\nmybatis-plus:\n  mapper-locations: classpath*:/mapper/**/*.xml\n  global-config:\n    db-config:\n      id-type: auto\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n\nfeign:\n  sentinel:\n    enabled: true\n\n\nlogging:\n  level:\n    org.springframework.cloud.openfeign: debug\n    org.springframework.sleuth: debug\n\n\n    ',
        '45b5b769e147e7dd9de7e5666e1cefa7', '2021-06-12 18:19:06', '2021-10-26 09:52:55', NULL, '113.90.130.187', '',
        '9e9bfac1-f266-4fc4-b220-2c95997c532b', '', '', '', 'yaml', ''),
       (4, 'themall-coupon.yml', 'DEFAULT_GROUP',
        'spring:\n    #session保存redis\n  session:\n    store-type: redis\n  redis:\n    host: 192.168.56.10\n    port: 6379\n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  datasource:\n    username: root\n    password: hjj0127..\n    url: jdbc:mysql://theangel.vip:3306/themall_sms?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai\n    driver-class-name: com.mysql.cj.jdbc.Driver\n\n\n',
        '492f4b8e70fec4a4cee13bcd947bc072', '2021-06-12 18:24:02', '2021-09-07 13:23:40', NULL, '119.123.177.58', '',
        '9e9bfac1-f266-4fc4-b220-2c95997c532b', '优惠服务配置文件', '', '', 'yaml', ''),
       (9, 'themall-member.yml', 'DEFAULT_GROUP',
        'spring:\n    #session保存redis\n  session:\n    store-type: redis\n  redis:\n    host: 192.168.56.10\n    port: 6379\n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  datasource:\n    username: root\n    password: hjj0127..\n    url: jdbc:mysql://theangel.vip:3306/themall_ums?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai\n    driver-class-name: com.mysql.cj.jdbc.Driver\n  mvc:\n    date-format: yyyy-MM-dd HH:mm:ss',
        '5ce6b80eb0377642c83a699b3e386831', '2021-06-14 12:48:59', '2021-10-02 08:17:45', NULL, '121.35.0.182', '',
        '9e9bfac1-f266-4fc4-b220-2c95997c532b', '会员服务配置文件', '', '', 'yaml', ''),
       (12, 'themall-order.yml', 'DEFAULT_GROUP',
        'spring:\n  #session保存redis\n  session:\n    store-type: redis\n  redis:\n    host: 192.168.56.10\n    port: 6379\n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  datasource:\n    username: root\n    password: hjj0127..\n    url: jdbc:mysql://theangel.vip:3306/themall_oms?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai\n    driver-class-name: com.mysql.cj.jdbc.Driver\n\n  rabbitmq:\n    host: 192.168.56.10\n    username: guest\n    password: guest\n    virtual-host: /themall\n    port: 5672\n    #开启发送端确认，到达rabbitmq\n    publisher-confirms: true\n    #发送端确认，有没有路由到指定的key  集群是一个副本模式，所有确认才能够确认\n    publisher-returns: true\n    #    只要抵达队列，以异步发送优先回调我们这个returns\n    template:\n      mandatory: true\n    listener:\n      simple:\n        #        auto:自动回复    manual:手动确认\n        acknowledge-mode: manual\n\n  thymeleaf:\n    cache: false\n  mvc:\n    date-format: yyyy-MM-dd HH:mm:ss\n\n#spring.cloud.alibaba.seata.tx-service-group修改后缀，但是必须和file.conf中的配置保持一致\n',
        'bfe538327d4d56f84d38a4a7ea7f9412', '2021-06-14 12:56:26', '2021-10-02 08:18:04', NULL, '121.35.0.182', '',
        '9e9bfac1-f266-4fc4-b220-2c95997c532b', '', '', '', 'yaml', ''),
       (13, 'themall-product.yml', 'DEFAULT_GROUP',
        'spring:\n      #session保存redis\n  session:\n    store-type: redis\n  redis:\n    host: 192.168.56.10\n    port: 6379\n    \n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  servlet:\n    multipart:\n      max-file-size: 20MB\n      max-request-size: 200MB\n  datasource:\n    username: root\n    password: hjj0127..\n    url: jdbc:mysql://theangel.vip:3306/themall_pms?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai\n    driver-class-name: com.mysql.cj.jdbc.Driver\n\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n  #开发期间 关闭缓存\n  thymeleaf:\n    cache: false\n    prefix: classpath:/templates/\n    suffix: .html\n\nmybatis-plus:\n  mapper-locations: classpath*:/mapper/**/*.xml\n  global-config:\n    db-config:\n      id-type: auto\n      logic-delete-field: flag  # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\n\nthemall:\n  thread:\n    coreSize: 10\n    maxSize: 200\n    keepAliveTime: 10\n\nfeign:\n  client:\n    config:\n      default:\n        connectTimeout: 10000\n        readTimeout: 10000\n\nlogging:\n  level:\n    com.theangel.themall: error\n\n',
        '62ed50d4035a4cbf1d5472bfb5a76b35', '2021-06-14 13:16:07', '2021-09-07 13:18:27', NULL, '119.123.177.58', '',
        '9e9bfac1-f266-4fc4-b220-2c95997c532b', '商品模块', '', '', 'yaml', ''),
       (14, 'themall-ware.yml', 'DEFAULT_GROUP',
        'spring:\n  #session保存redis\n  session:\n    store-type: redis\n  redis:\n    host: 192.168.56.10\n    port: 6379\n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  datasource:\n    username: root\n    password: hjj0127..\n    url: jdbc:mysql://theangel.vip:3306/themall_wms?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai\n    driver-class-name: com.mysql.cj.jdbc.Driver\n\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n  rabbitmq:\n    host: 192.168.56.10\n    username: guest\n    password: guest\n    virtual-host: /themall\n    port: 5672\n    #开启发送端确认，到达rabbitmq\n    publisher-confirms: true\n    #发送端确认，有没有路由到指定的key  集群是一个副本模式，所有确认才能够确认\n    publisher-returns: true\n    #    只要抵达队列，以异步发送优先回调我们这个returns\n    template:\n      mandatory: true\n    listener:\n      simple:\n        #        auto:自动回复    manual:手动确认\n        acknowledge-mode: manual\n\n#spring.cloud.alibaba.seata.tx-service-group修改后缀，但是必须和file.conf中的配置保持一致',
        '5417c0a31d7a55c04c6502620cb620e3', '2021-06-14 13:25:39', '2021-09-21 06:59:06', NULL, '113.90.130.180', '',
        '9e9bfac1-f266-4fc4-b220-2c95997c532b', '库存模块配置文件', '', '', 'yaml', ''),
       (15, 'themall-gatway.yml', 'DEFAULT_GROUP',
        'management:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n\nspring:\n  zipkin:\n    base-url: http://192.168.56.10:9411\n    locator:\n      discovery:\n        #关闭服务发现，否则springcloud会把zipkin的url当成服务名称\n        enabled: false\n    sender:\n      #设置http发送数据\n      type: web\n  sleuth:\n    sampler:\n      #      设置抽取采集率为100%，默认为10%\n      probability: 1\n\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: localhost:8333\n\n    gateway:\n      routes:\n        #         检索服务\n        - id: themall-search\n          uri: lb://themall-search\n          predicates:\n            - Path=/api/search/**\n          filters:\n            - RewritePath=/api/?(?<the>.*), /$\\{the}\n\n        #          库存服务\n        - id: themall-ware\n          uri: lb://themall-ware\n          predicates:\n            - Path=/api/ware/**\n          filters:\n            - RewritePath=/api/?(?<the>.*), /$\\{the}\n\n        #          商品优惠服务\n        - id: themall-coupon\n          uri: lb://themall-coupon\n          predicates:\n            - Path=/api/coupon/**\n          filters:\n            - RewritePath=/api/?(?<the>.*), /$\\{the}\n\n        #         第三方服务\n        - id: third-party\n          uri: lb://third-party\n          predicates:\n            - Path=/api/third-party/**\n          filters:\n            - RewritePath=/api/?(?<the>.*), /$\\{the}\n\n        #         商品服务\n        - id: themall-product\n          uri: lb://themall-product\n          predicates:\n            - Path=/api/product/**\n          filters:\n            - RewritePath=/api/?(?<the>.*), /$\\{the}\n\n        #         外网穿透的地址\n        - id: themall\n          uri: lb://themall-product\n          predicates:\n            - Path=/themall/**\n          filters:\n            - RewritePath=/themall/?(?<the>.*), /$\\{the}\n\n        #         会员服务\n        - id: themall-member\n          uri: lb://themall-member\n          predicates:\n            - Path=/api/member/**\n          filters:\n            - RewritePath=/api/?(?<the>.*), /$\\{the}\n\n        #\n        - id: renren-fast\n          uri: lb://renren-fast\n          predicates:\n            - Path=/api/**,/renren-fast/**\n          filters:\n            - RewritePath=/api/?(?<the>.*), /renren-fast/$\\{the}\n\n\n        # 商品详情页\n        - id: item\n          uri: lb://themall-product\n          predicates:\n            - Host=item.theangel.com\n        #            - Path=/item/*.html\n\n        # 商品搜索\n        - id: search\n          uri: lb://themall-search\n          predicates:\n            - Host=search.theangel.com\n        #            - Path=/list.html\n\n        # 认证\n        - id: auth-server\n          uri: lb://themall-auth-server\n          predicates:\n            - Host=auth.theangel.com\n        #            - Path=/auth/**\n\n        # 购物车\n        - id: themall-cart\n          uri: lb://themall-cart\n          predicates:\n            - Host=cart.theangel.com\n\n        # 订单\n        - id: themall-order\n          uri: lb://themall-order\n          predicates:\n            - Host=order.theangel.com\n\n        # 首页\n        - id: product-index\n          uri: lb://themall-product\n          predicates:\n            - Host=theangel.com,theangel.vip:8888,127.0.0.1:88,159.75.26.150:8888\n\n        # 用户订单\n        - id: member\n          uri: lb://themall-member\n          predicates:\n            - Host=member.theangel.com\n\n\n        # 秒杀\n        - id: themall-seckill\n          uri: lb://themall-seckill\n          predicates:\n            - Host=seckill.theangel.com\n\nlogging:\n  level:\n    org.springframework.cloud.openfeign: debug\n    org.springframework.sleuth: debug\n\n\n\n',
        'a779b8539f4437ccc24584a669d6bfef', '2021-06-14 14:03:33', '2021-10-16 16:19:13', NULL, '113.87.2.85', '',
        '9e9bfac1-f266-4fc4-b220-2c95997c532b', 'API网关配置', '', '', 'yaml', ''),
       (18, 'renren-fast.yml', 'DEFAULT_GROUP',
        'spring: \n  application:\n    name: renren-fast\nserver:\n  port: 8088', '83fa205e829bf70106e59f34c445bcd4',
        '2021-06-17 13:38:51', '2021-06-19 13:13:17', NULL, '183.14.31.49', '', '9e9bfac1-f266-4fc4-b220-2c95997c532b',
        '', '', '', 'yaml', ''),
       (41, 'third-party.yml', 'DEFAULT_GROUP',
        'spring:\n  #session保存redis\n  session:\n    store-type: redis\n  redis:\n    host: 192.168.56.10\n    port: 6379\n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  servlet:\n    multipart:\n      max-file-size: 20MB\n      max-request-size: 200MB\n\n  # 这些配置在腾讯云控制台都可查到（使用时替换为你自己的）\n  # 腾讯云的SecretId（永久的，可在控制台开启或关闭）\n  tencent:\n    secretId: AKIDIPHsCF3aqpdAQP7ZkeGdjY6NN6YicGdi\n    # 腾讯云的SecretKey（永久的，可在控制台开启或关闭）\n    SecretKey: qOffOq1uWrviFVVDetrYx5wWqtfAOKdg\n    # 腾讯云的bucket (存储桶)\n    bucket: theangel-1306086135\n    # 腾讯云的region(bucket所在地区)\n    region: ap-guangzhou\n    # 腾讯云的allowPrefix(允许上传的路径)\n    allowPrefix: \'*\'\n    # 腾讯云的临时密钥时长(单位秒)\n    durationSeconds: 10\n    # 腾讯云的访问基础链接:\n    baseUrl: https://theangel-1306086135.cos.ap-guangzhou.myqcloud.com\n  cloud:\n    alicloud:\n      access-key: LTAI5t5Zw9QpcLrfdGCA4NZZ\n      secret-key: MEYjLvITV4G6hkv12N41mbrcqR3phk\n      oss:\n        endpoint: oss-cn-hangzhou.aliyuncs.com\n        bucket: theangel-mall\n      sms:\n        host: https://dfsns.market.alicloudapi.com\n        path: /data/send_sms\n        appcode: 9d04ec7d99b740a9973cf01a438b9337\n',
        '3e702b87a5206493d8f63127981f9cff', '2021-06-21 16:29:18', '2021-09-07 13:30:06', NULL, '119.123.177.58', '',
        '9e9bfac1-f266-4fc4-b220-2c95997c532b', '第三方服务配置', '', '', 'yaml', ''),
       (42, 'themall-search.yml', 'DEFAULT_GROUP',
        'spring:\n    #session保存redis\n  session:\n    store-type: redis\n  redis:\n    host: 192.168.56.10\n    port: 6379\n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  thymeleaf:\n    cache: false\n',
        '5476c7d72630db6b1b9c19c3f022c447', '2021-08-01 05:54:48', '2021-09-07 13:20:22', NULL, '119.123.177.58', '',
        '9e9bfac1-f266-4fc4-b220-2c95997c532b', '全文检索  elasticsearch ', '', '', 'yaml', ''),
       (111, 'themall-auth-server', 'DEFAULT_GROUP',
        'spring:\n  #session保存redis\n  session:\n    store-type: redis\n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  #开发期间 关闭缓存\n  thymeleaf:\n    cache: false\n    prefix: classpath:/templates/\n    suffix: .html\n\nlogging:\n  level:\n    com.theangel.themall: error\n\nserver:\n  port: 20000\n  servlet:\n    session:\n      #session超时时间\n      timeout: 30m\n',
        '63c71e2c2435dafc95b54e5fd477ba10', '2021-08-19 14:34:56', '2021-12-11 16:08:05', NULL, '113.87.226.163', '',
        '9e9bfac1-f266-4fc4-b220-2c95997c532b', '认证服务', '', '', 'yaml', ''),
       (129, 'themall-cart.yml', 'DEFAULT_GROUP',
        'spring:\n    #session保存redis\n  session:\n    store-type: redis\n  redis:\n    host: 192.168.56.10\n    port: 6379\n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  thymeleaf:\n    cache: false',
        '708feb2c1553c035a1da7ccd65a9b602', '2021-08-29 04:39:46', '2021-09-07 13:21:35', NULL, '119.123.177.58', '',
        '9e9bfac1-f266-4fc4-b220-2c95997c532b', '购物车服务配置', '', '', 'yaml', ''),
       (164, 'themall-seckill.yml', 'DEFAULT_GROUP',
        'spring:\n  #session保存redis\n  session:\n    store-type: redis\n  redis:\n    host: 192.168.56.10\n    port: 6379\n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n  task:\n    execution:\n      pool:\n        core-size: 20\n        maxSize: 50\n        queueCapacity: 50\n\nthemall:\n  thread:\n    coreSize: 10\n    maxSize: 200\n    keepAliveTime: 10\n\nfeign:\n  client:\n    config:\n      default:\n        connectTimeout: 10000\n        readTimeout: 10000\n\nlogging:\n  level:\n    com.theangel.themall: info\n\n',
        '2dc98037e957735e496cf8ab943c1115', '2021-10-03 12:00:34', '2021-10-10 16:22:11', NULL, '119.123.176.241', '',
        '9e9bfac1-f266-4fc4-b220-2c95997c532b', NULL, NULL, NULL, 'text', NULL),
       (246, 'themall-gatway-dev.yaml', 'dev',
        'spring:\n  zipkin:\n    base-url: http://192.168.98.11:30941\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n\n\n\n\n\n',
        '85dd0b1180e0fa4aeb895082da8327a8', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (247, 'renren-fast-dev.yaml', 'dev',
        '\n\nspring:\n  zipkin:\n    base-url: http://192.168.98.11:30941\n\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      db-type: mysql\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      url: jdbc:mysql://192.168.98.11:30306/themall_admin?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n      username: root\n      password: \"005400\"\n      initial-size: 10\n      max-active: 100\n      min-idle: 10\n      max-wait: 60000\n      pool-prepared-statements: true\n      max-pool-prepared-statement-per-connection-size: 20\n      time-between-eviction-runs-millis: 60000\n      min-evictable-idle-time-millis: 300000\n      #Oracle需要打开注释\n      #validation-query: SELECT 1 FROM DUAL\n      test-while-idle: true\n      test-on-borrow: false\n      test-on-return: false\n      stat-view-servlet:\n        enabled: true\n        url-pattern: /druid/*\n        #login-username: admin\n        #login-password: admin\n      filter:\n        stat:\n          log-slow-sql: true\n          slow-sql-millis: 1000\n          merge-sql: false\n        wall:\n          config:\n            multi-statement-allow: true\n  ##多数据源的配置\n  #dynamic:\n  #  datasource:\n  #    slave1:\n  #      driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver\n  #      url: jdbc:sqlserver://localhost:1433;DatabaseName=renren_security\n  #      username: sa\n  #      password: 123456\n  #    slave2:\n  #      driver-class-name: org.postgresql.Driver\n  #      url: jdbc:postgresql://localhost:5432/renren_security\n  #      username: renren\n  #      password: 123456\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n\nmybatis-plus:\n  #实体扫描，多个package用逗号或者分号分隔\n  typeAliasesPackage: io.renren.modules.*.entity\n\n\nrenren:\n  redis:\n    open: false\n  shiro:\n    redis: false\n  # APP模块，是通过jwt认证的，如果要使用APP模块，则需要修改【加密秘钥】\n  jwt:\n    # 加密秘钥\n    secret: f4e2e52034348f86b67cde581c0f9eb5[www.theangel.vip]\n    # token有效时长，7天，单位秒\n    expire: 604800\n    header: token\n\nlogging:\n  level:\n    org.springframework.cloud.openfeign: info\n    org.springframework.sleuth: info\n',
        '6d7e0a2bf6269c00787a7a2b48408a5d', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (248, 'themall-auth-server-dev.yaml', 'dev',
        'spring:\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n  zipkin:\n    base-url: http://192.168.98.11:30941\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n\n  #开发期间 关闭缓存\n  thymeleaf:\n    #    cache: false\n    prefix: classpath:/templates/\n    suffix: .html\n\nlogging:\n  level:\n    org.springframework.cloud.openfeign: info\n    org.springframework.sleuth: info\n\n\n',
        '5c4f5237936c9d83da560aa43f3f7bb2', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (249, 'common.yaml', 'dev',
        '# Tomcat\nserver:\n  tomcat:\n    uri-encoding: UTF-8\n    max-threads: 1000\n    min-spare-threads: 30\n  connection-timeout: 5000ms\n  servlet:\n    session:\n      #session超时时间\n      timeout: 30m\n\nspring:\n  zipkin:\n    locator:\n      discovery:\n        #关闭服务发现，否则springcloud会把zipkin的url当成服务名称\n        enabled: false\n    sender:\n      #设置http发送数据\n      type: web\n  sleuth:\n    sampler:\n      #      设置抽取采集率为100%，默认为10%\n      probability: 1\n\n  session:\n    store-type: redis\n    #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀但开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  # jackson时间格式化\n  jackson:\n    time-zone: GMT+8\n#    date-format: yyyy-MM-dd HH:mm:ss\n  servlet:\n    multipart:\n      max-file-size: 100MB\n      max-request-size: 100MB\n      enabled: true\n  redis:\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    timeout: 6000ms  # 连接超时时长（毫秒）\n    jedis:\n      pool:\n        max-active: 1000  # 连接池最大连接数（使用负值表示没有限制）\n        max-wait: -1ms      # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-idle: 10      # 连接池中的最大空闲连接\n        min-idle: 5       # 连接池中的最小空闲连接\n  mvc:\n    throw-exception-if-no-handler-found: true\n #   date-format: yyyy-MM-dd HH:mm:ss\n\nmybatis-plus:\n  mapper-locations: classpath*:/mapper/**/*.xml\n  global-config:\n    #数据库相关配置\n    db-config:\n      #主键类型  AUTO:\"数据库ID自增\", INPUT:\"用户输入ID\", ID_WORKER:\"全局唯一ID (数字类型唯一ID)\", UUID:\"全局唯一ID UUID\";\n      id-type: ID_WORKER\n      logic-delete-field: flag  # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\n    banner: false\n\n  #原生配置\n  configuration:\n    map-underscore-to-camel-case: true\n    cache-enabled: false\n    call-setters-on-nulls: true\n    jdbc-type-for-null: \'null\'\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n\nfeign:\n  sentinel:\n    enabled: true\n\n',
        'd37df780366b378ba3cad1a1d219897f', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (250, 'themall-cart-dev.yaml', 'dev',
        'spring:\n  zipkin:\n    base-url: http://192.168.98.11:30941\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n  thymeleaf:\n    cache: false',
        '889f6957ba11c5650ae0eb4bd4e70dfa', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (251, 'themall-coupon-dev.yaml', 'dev',
        'spring:\n  zipkin:\n    base-url: http://192.168.98.11:30941\n    \n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.98.11:30306/themall_sms?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858',
        'a8a8c0586f688859ca6fbe3f949c1581', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (252, 'themall-member-dev.yaml', 'dev',
        'spring:\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  zipkin:\n    base-url: http://192.168.98.11:30941\n    \n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.98.11:30306/themall_ums?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n',
        '2eaa662a430161b95458dffbe5ac022c', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (253, 'themall-order-dev.yaml', 'dev',
        'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.98.11:30306/themall_oms?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n\n  rabbitmq:\n    host: 192.168.98.11\n    username: admin\n    password: \"005400\"\n    virtual-host: /themall\n    port: 30672\n    #开启发送端确认，到达rabbitmq\n    publisher-confirms: true\n    #发送端确认，有没有路由到指定的key  集群是一个副本模式，所有确认才能够确认\n    publisher-returns: true\n    #    只要抵达队列，以异步发送优先回调我们这个returns\n    template:\n      mandatory: true\n    listener:\n      simple:\n        #        auto:自动回复    manual:手动确认\n        acknowledge-mode: manual\n        \n  thymeleaf:\n    cache: false\n\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  zipkin:\n    base-url: http://192.168.98.11:30941',
        '65e37f6217fdecb9acfb0c4ebeefa297', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (254, 'themall-product-dev.yaml', 'dev',
        'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.98.11:30306/themall_pms?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n    \n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  #开发期间 关闭缓存\n  thymeleaf:\n    cache: false\n    prefix: classpath:/templates/\n    suffix: .html\n\n\nthemall:\n  thread:\n    coreSize: 10\n    maxSize: 200\n    keepAliveTime: 10\nlogging:\n  level:\n    com.theangel.themall: info\n\n\n',
        '8813b6828123950a525366e82617da4a', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', NULL, NULL, NULL, 'yaml', NULL),
       (255, 'themall-search-dev.yaml', 'dev',
        'spring:\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n  elasticsearch:\n    cluster:\n      nodes: http://192.168.98.11:30920',
        '5397d3f57d3a2fe29082ed2aa7917213', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (256, 'themall-seckill-dev.yaml', 'dev',
        'spring:\n  rabbitmq:\n    host: 192.168.98.11\n    username: admin\n    password: \"005400\"\n    virtual-host: /themall\n    port: 30672\n    #开启发送端确认，到达rabbitmq\n    publisher-confirms: true\n    #发送端确认，有没有路由到指定的key  集群是一个副本模式，所有确认才能够确认\n    publisher-returns: true\n    #    只要抵达队列，以异步发送优先回调我们这个returns\n    template:\n      mandatory: true\n    listener:\n      simple:\n        #        auto:自动回复    manual:手动确认\n        acknowledge-mode: manual\n  thymeleaf:\n    cache: false\n\n\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  zipkin:\n    base-url: http://192.168.98.11:30941\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n  task:\n    execution:\n      pool:\n        core-size: 20\n        maxSize: 50\n        queueCapacity: 50\n\nthemall:\n  thread:\n    coreSize: 10\n    maxSize: 200\n    keepAliveTime: 10\n\nlogging:\n  level:\n    com.theangel.themall: info\n\n\n    ',
        '5594f13c402044d091fc58b898239c55', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', NULL, NULL, NULL, 'yaml', NULL),
       (257, 'themall-ware-dev.yaml', 'dev',
        'spring:\n  rabbitmq:\n    host: 192.168.98.11\n    username: admin\n    password: \"005400\"\n    virtual-host: /themall\n    port: 30672\n    #开启发送端确认，到达rabbitmq\n    publisher-confirms: true\n    #发送端确认，有没有路由到指定的key  集群是一个副本模式，所有确认才能够确认\n    publisher-returns: true\n    #    只要抵达队列，以异步发送优先回调我们这个returns\n    template:\n      mandatory: true\n    listener:\n      simple:\n        #        auto:自动回复    manual:手动确认\n        acknowledge-mode: manual\n\n\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.98.11:30306/themall_wms?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n\n\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  zipkin:\n    base-url: http://192.168.98.11:30941\n\n',
        '2e4c1d49aeb0eb432546e149f9b11de1', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', NULL, NULL, NULL, 'yaml', NULL),
       (258, 'third-party-dev.yaml', 'dev',
        'spring:\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  # 这些配置在腾讯云控制台都可查到（使用时替换为你自己的）\n  # 腾讯云的SecretId（永久的，可在控制台开启或关闭）\n  tencent:\n    secretId: AKIDIPHsCF3aqpdAQP7ZkeGdjY6NN6YicGdi\n    # 腾讯云的SecretKey（永久的，可在控制台开启或关闭）\n    SecretKey: qOffOq1uWrviFVVDetrYx5wWqtfAOKdg\n    # 腾讯云的bucket (存储桶)\n    bucket: theangel-1306086135\n    # 腾讯云的region(bucket所在地区)\n    region: ap-guangzhou\n    # 腾讯云的allowPrefix(允许上传的路径)\n    allowPrefix: \'*\'\n    # 腾讯云的临时密钥时长(单位秒)\n    durationSeconds: 10\n    # 腾讯云的访问基础链接:\n    baseUrl: https://theangel-1306086135.cos.ap-guangzhou.myqcloud.com\n\n    alicloud:\n      access-key: LTAI5t5Zw9QpcLrfdGCA4NZZ\n      secret-key: MEYjLvITV4G6hkv12N41mbrcqR3phk\n      oss:\n        endpoint: oss-cn-hangzhou.aliyuncs.com\n        bucket: theangel-mall\n      sms:\n        host: https://dfsns.market.alicloudapi.com\n        path: /data/send_sms\n        appcode: 9d04ec7d99b740a9973cf01a438b9337',
        'd76a58b36de4cefe600d5b5f132ff334', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (259, 'common.yaml', 'prod',
        '# Tomcat\nserver:\n  tomcat:\n    uri-encoding: UTF-8\n    max-threads: 1000\n    min-spare-threads: 30\n  connection-timeout: 5000ms\n  servlet:\n    session:\n      #session超时时间\n      timeout: 30m\n\nspring:\n  zipkin:\n    locator:\n      discovery:\n        #关闭服务发现，否则springcloud会把zipkin的url当成服务名称\n        enabled: false\n    sender:\n      #设置http发送数据\n      type: web\n  sleuth:\n    sampler:\n      #      设置抽取采集率为100%，默认为10%\n      probability: 1\n\n  session:\n    store-type: redis\n    #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀但开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  # jackson时间格式化\n  jackson:\n    time-zone: GMT+8\n#    date-format: yyyy-MM-dd HH:mm:ss\n  servlet:\n    multipart:\n      max-file-size: 100MB\n      max-request-size: 100MB\n      enabled: true\n  redis:\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    timeout: 6000ms  # 连接超时时长（毫秒）\n    jedis:\n      pool:\n        max-active: 1000  # 连接池最大连接数（使用负值表示没有限制）\n        max-wait: -1ms      # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-idle: 10      # 连接池中的最大空闲连接\n        min-idle: 5       # 连接池中的最小空闲连接\n  mvc:\n    throw-exception-if-no-handler-found: true\n #   date-format: yyyy-MM-dd HH:mm:ss\n\nmybatis-plus:\n  mapper-locations: classpath*:/mapper/**/*.xml\n  global-config:\n    #数据库相关配置\n    db-config:\n      #主键类型  AUTO:\"数据库ID自增\", INPUT:\"用户输入ID\", ID_WORKER:\"全局唯一ID (数字类型唯一ID)\", UUID:\"全局唯一ID UUID\";\n      id-type: ID_WORKER\n      logic-delete-field: flag  # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\n    banner: false\n\n  #原生配置\n  configuration:\n    map-underscore-to-camel-case: true\n    cache-enabled: false\n    call-setters-on-nulls: true\n    jdbc-type-for-null: \'null\'\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n\nfeign:\n  sentinel:\n    enabled: true\n\n',
        'd37df780366b378ba3cad1a1d219897f', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (260, 'themall-gatway-prod.yaml', 'prod',
        'spring:\n  zipkin:\n    base-url: http://192.168.98.11:30941\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n\n\n\n\n\n',
        '85dd0b1180e0fa4aeb895082da8327a8', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (261, 'renren-fast-prod.yaml', 'prod',
        '\n\nspring:\n  zipkin:\n    base-url: http://192.168.98.11:30941\n\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      db-type: mysql\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      url: jdbc:mysql://mysql:3306/themall_admin?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n      username: root\n      password: \"005400\"\n      initial-size: 10\n      max-active: 100\n      min-idle: 10\n      max-wait: 60000\n      pool-prepared-statements: true\n      max-pool-prepared-statement-per-connection-size: 20\n      time-between-eviction-runs-millis: 60000\n      min-evictable-idle-time-millis: 300000\n      #Oracle需要打开注释\n      #validation-query: SELECT 1 FROM DUAL\n      test-while-idle: true\n      test-on-borrow: false\n      test-on-return: false\n      stat-view-servlet:\n        enabled: true\n        url-pattern: /druid/*\n        #login-username: admin\n        #login-password: admin\n      filter:\n        stat:\n          log-slow-sql: true\n          slow-sql-millis: 1000\n          merge-sql: false\n        wall:\n          config:\n            multi-statement-allow: true\n  ##多数据源的配置\n  #dynamic:\n  #  datasource:\n  #    slave1:\n  #      driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver\n  #      url: jdbc:sqlserver://localhost:1433;DatabaseName=renren_security\n  #      username: sa\n  #      password: 123456\n  #    slave2:\n  #      driver-class-name: org.postgresql.Driver\n  #      url: jdbc:postgresql://localhost:5432/renren_security\n  #      username: renren\n  #      password: 123456\n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n\nmybatis-plus:\n  #实体扫描，多个package用逗号或者分号分隔\n  typeAliasesPackage: io.renren.modules.*.entity\n\n\nrenren:\n  redis:\n    open: false\n  shiro:\n    redis: false\n  # APP模块，是通过jwt认证的，如果要使用APP模块，则需要修改【加密秘钥】\n  jwt:\n    # 加密秘钥\n    secret: f4e2e52034348f86b67cde581c0f9eb5[www.theangel.vip]\n    # token有效时长，7天，单位秒\n    expire: 604800\n    header: token\n\nlogging:\n  level:\n    org.springframework.cloud.openfeign: info\n    org.springframework.sleuth: info\n',
        '6baaf57c54be834ebd230b8f69bfcd10', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (262, 'themall-auth-server-prod.yaml', 'prod',
        'spring:\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n  zipkin:\n    base-url: http://192.168.98.11:30941\n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n\n  #开发期间 关闭缓存\n  thymeleaf:\n    #    cache: false\n    prefix: classpath:/templates/\n    suffix: .html\n\nlogging:\n  level:\n    org.springframework.cloud.openfeign: info\n    org.springframework.sleuth: info\n\n\n',
        'b1d8021397af7f2026f5e1ba02011ed2', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (263, 'themall-cart-prod.yaml', 'prod',
        'spring:\n  zipkin:\n    base-url: http://192.168.98.11:30941\n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n  thymeleaf:\n    cache: true',
        '0e586005a11b8fbd1b2ba529635e5d35', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (264, 'themall-coupon-prod.yaml', 'prod',
        'spring:\n  zipkin:\n    base-url: http://192.168.98.11:30941\n    \n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://mysql:3306/themall_sms?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n\n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858',
        '09f72142831cda22761867a672cdf842', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (265, 'themall-member-prod.yaml', 'prod',
        'spring:\n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  zipkin:\n    base-url: http://192.168.98.11:30941\n    \n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://mysql:3306/themall_ums?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n',
        '7303fa0469317ebb7ae7205698850ea3', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (266, 'themall-order-prod.yaml', 'prod',
        'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://mysql:3306/themall_oms?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n\n  rabbitmq:\n    host: rabbit-mq-rabbitmq\n    username: admin\n    password: \"005400\"\n    virtual-host: /themall\n    port: 5672\n    #开启发送端确认，到达rabbitmq\n    publisher-confirms: true\n    #发送端确认，有没有路由到指定的key  集群是一个副本模式，所有确认才能够确认\n    publisher-returns: true\n    #    只要抵达队列，以异步发送优先回调我们这个returns\n    template:\n      mandatory: true\n    listener:\n      simple:\n        #        auto:自动回复    manual:手动确认\n        acknowledge-mode: manual\n        \n  thymeleaf:\n    cache: true\n\n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  zipkin:\n    base-url: http://192.168.98.11:30941',
        'af0a4ec0a16c723e3528e76e2acf5a3b', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (267, 'themall-product-prod.yaml', 'prod',
        'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://mysql:3306/themall_pms?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n    \n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  #开发期间 关闭缓存\n  thymeleaf:\n    cache: true\n    prefix: classpath:/templates/\n    suffix: .html\n\n\nthemall:\n  thread:\n    coreSize: 10\n    maxSize: 200\n    keepAliveTime: 10\nlogging:\n  level:\n    com.theangel.themall: info\n\n\n',
        '5bb126186f2c78361cfe8a4e1f4abcc7', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (268, 'themall-search-prod.yaml', 'prod',
        'spring:\n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n  elasticsearch:\n    cluster:\n      nodes: http://elasticsearch:9200',
        'e5715fa87d4c5ab088a872279b013791', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (269, 'themall-seckill-prod.yaml', 'prod',
        'spring:\n  rabbitmq:\n    host: rabbit-mq-rabbitmq\n    username: admin\n    password: \"005400\"\n    virtual-host: /themall\n    port: 5672\n    #开启发送端确认，到达rabbitmq\n    publisher-confirms: true\n    #发送端确认，有没有路由到指定的key  集群是一个副本模式，所有确认才能够确认\n    publisher-returns: true\n    #    只要抵达队列，以异步发送优先回调我们这个returns\n    template:\n      mandatory: true\n    listener:\n      simple:\n        #        auto:自动回复    manual:手动确认\n        acknowledge-mode: manual\n  thymeleaf:\n    cache: false\n\n\n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  zipkin:\n    base-url: http://192.168.98.11:30941\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n  task:\n    execution:\n      pool:\n        core-size: 20\n        maxSize: 50\n        queueCapacity: 50\n\nthemall:\n  thread:\n    coreSize: 10\n    maxSize: 200\n    keepAliveTime: 10\n\nlogging:\n  level:\n    com.theangel.themall: info\n\n\n    ',
        'bad9545ca2da4f114a01448cfd5ec26a', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (270, 'themall-ware-prod.yaml', 'prod',
        'spring:\n  rabbitmq:\n    host: rabbit-mq-rabbitmq\n    username: admin\n    password: \"005400\"\n    virtual-host: /themall\n    port: 30672\n    #开启发送端确认，到达rabbitmq\n    publisher-confirms: true\n    #发送端确认，有没有路由到指定的key  集群是一个副本模式，所有确认才能够确认\n    publisher-returns: true\n    #    只要抵达队列，以异步发送优先回调我们这个returns\n    template:\n      mandatory: true\n    listener:\n      simple:\n        #        auto:自动回复    manual:手动确认\n        acknowledge-mode: manual\n\n\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://mysql:3306/themall_wms?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n\n\n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  zipkin:\n    base-url: http://192.168.98.11:30941\n\n',
        '03f0f8d9de1f71ccdb085b4c9b48013a', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL),
       (271, 'third-party-prod.yaml', 'prod',
        'spring:\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  # 这些配置在腾讯云控制台都可查到（使用时替换为你自己的）\n  # 腾讯云的SecretId（永久的，可在控制台开启或关闭）\n  tencent:\n    secretId: AKIDIPHsCF3aqpdAQP7ZkeGdjY6NN6YicGdi\n    # 腾讯云的SecretKey（永久的，可在控制台开启或关闭）\n    SecretKey: qOffOq1uWrviFVVDetrYx5wWqtfAOKdg\n    # 腾讯云的bucket (存储桶)\n    bucket: theangel-1306086135\n    # 腾讯云的region(bucket所在地区)\n    region: ap-guangzhou\n    # 腾讯云的allowPrefix(允许上传的路径)\n    allowPrefix: \'*\'\n    # 腾讯云的临时密钥时长(单位秒)\n    durationSeconds: 10\n    # 腾讯云的访问基础链接:\n    baseUrl: https://theangel-1306086135.cos.ap-guangzhou.myqcloud.com\n\n    alicloud:\n      access-key: LTAI5t5Zw9QpcLrfdGCA4NZZ\n      secret-key: MEYjLvITV4G6hkv12N41mbrcqR3phk\n      oss:\n        endpoint: oss-cn-hangzhou.aliyuncs.com\n        bucket: theangel-mall\n      sms:\n        host: https://dfsns.market.alicloudapi.com\n        path: /data/send_sms\n        appcode: 9d04ec7d99b740a9973cf01a438b9337',
        'd76a58b36de4cefe600d5b5f132ff334', '2023-11-20 02:23:29', '2023-11-20 02:23:29', NULL, '10.244.0.0', '',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd', '', NULL, NULL, 'yaml', NULL);
/*!40000 ALTER TABLE `config_info`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_aggr`
--

DROP TABLE IF EXISTS `config_info_aggr`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info_aggr`
(
    `id`           bigint                                                 NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
    `datum_id`     varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'datum_id',
    `content`      longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin     NOT NULL COMMENT '内容',
    `gmt_modified` datetime                                               NOT NULL COMMENT '修改时间',
    `app_name`     varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
    `tenant_id`    varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin
  ROW_FORMAT = DYNAMIC COMMENT ='增加租户字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_aggr`
--

LOCK TABLES `config_info_aggr` WRITE;
/*!40000 ALTER TABLE `config_info_aggr`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_aggr`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_beta`
--

DROP TABLE IF EXISTS `config_info_beta`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info_beta`
(
    `id`           bigint                                                 NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
    `app_name`     varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin          DEFAULT NULL COMMENT 'app_name',
    `content`      longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin     NOT NULL COMMENT 'content',
    `beta_ips`     varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin         DEFAULT NULL COMMENT 'betaIps',
    `md5`          varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime                                               NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
    `gmt_modified` datetime                                               NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
    `src_user`     text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'source user',
    `src_ip`       varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin           DEFAULT NULL COMMENT 'source ip',
    `tenant_id`    varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin          DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin
  ROW_FORMAT = DYNAMIC COMMENT ='config_info_beta';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_beta`
--

LOCK TABLES `config_info_beta` WRITE;
/*!40000 ALTER TABLE `config_info_beta`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_beta`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_info_tag`
--

DROP TABLE IF EXISTS `config_info_tag`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_info_tag`
(
    `id`           bigint                                                 NOT NULL AUTO_INCREMENT COMMENT 'id',
    `data_id`      varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
    `group_id`     varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
    `tenant_id`    varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin          DEFAULT '' COMMENT 'tenant_id',
    `tag_id`       varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_id',
    `app_name`     varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin          DEFAULT NULL COMMENT 'app_name',
    `content`      longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin     NOT NULL COMMENT 'content',
    `md5`          varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin           DEFAULT NULL COMMENT 'md5',
    `gmt_create`   datetime                                               NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
    `gmt_modified` datetime                                               NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
    `src_user`     text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'source user',
    `src_ip`       varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin           DEFAULT NULL COMMENT 'source ip',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin
  ROW_FORMAT = DYNAMIC COMMENT ='config_info_tag';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_info_tag`
--

LOCK TABLES `config_info_tag` WRITE;
/*!40000 ALTER TABLE `config_info_tag`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `config_info_tag`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config_tags_relation`
--

DROP TABLE IF EXISTS `config_tags_relation`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `config_tags_relation`
(
    `id`        bigint                                                 NOT NULL COMMENT 'id',
    `tag_name`  varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_name',
    `tag_type`  varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin  DEFAULT NULL COMMENT 'tag_type',
    `data_id`   varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
    `group_id`  varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
    `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
    `nid`       bigint                                                 NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`nid`) USING BTREE,
    UNIQUE KEY `uk_configtagrelation_configidtag` (`id`, `tag_name`, `tag_type`) USING BTREE,
    KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin
  ROW_FORMAT = DYNAMIC COMMENT ='config_tag_relation';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config_tags_relation`
--

LOCK TABLES `config_tags_relation` WRITE;
/*!40000 ALTER TABLE `config_tags_relation`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `config_tags_relation`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_capacity`
--

DROP TABLE IF EXISTS `group_capacity`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_capacity`
(
    `id`                bigint unsigned                                        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `group_id`          varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
    `quota`             int unsigned                                           NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
    `usage`             int unsigned                                           NOT NULL DEFAULT '0' COMMENT '使用量',
    `max_size`          int unsigned                                           NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int unsigned                                           NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
    `max_aggr_size`     int unsigned                                           NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int unsigned                                           NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
    `gmt_create`        datetime                                               NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
    `gmt_modified`      datetime                                               NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_group_id` (`group_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin
  ROW_FORMAT = DYNAMIC COMMENT ='集群、各Group容量信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_capacity`
--

LOCK TABLES `group_capacity` WRITE;
/*!40000 ALTER TABLE `group_capacity`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `group_capacity`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `his_config_info`
--

DROP TABLE IF EXISTS `his_config_info`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `his_config_info`
(
    `id`           bigint unsigned                                        NOT NULL,
    `nid`          bigint unsigned                                        NOT NULL AUTO_INCREMENT,
    `data_id`      varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
    `group_id`     varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
    `app_name`     varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin          DEFAULT NULL COMMENT 'app_name',
    `content`      longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin     NOT NULL,
    `md5`          varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin           DEFAULT NULL,
    `gmt_create`   datetime                                               NOT NULL DEFAULT '2010-05-05 00:00:00',
    `gmt_modified` datetime                                               NOT NULL DEFAULT '2010-05-05 00:00:00',
    `src_user`     text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
    `src_ip`       varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin           DEFAULT NULL,
    `op_type`      char(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin              DEFAULT NULL,
    `tenant_id`    varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin          DEFAULT '' COMMENT '租户字段',
    PRIMARY KEY (`nid`) USING BTREE,
    KEY `idx_gmt_create` (`gmt_create`) USING BTREE,
    KEY `idx_gmt_modified` (`gmt_modified`) USING BTREE,
    KEY `idx_did` (`data_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 355
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin
  ROW_FORMAT = DYNAMIC COMMENT ='多租户改造';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `his_config_info`
--

LOCK TABLES `his_config_info` WRITE;
/*!40000 ALTER TABLE `his_config_info`
    DISABLE KEYS */;
INSERT INTO `his_config_info`
VALUES (0, 316, 'themall-gatway-dev.yaml', 'dev', '',
        'spring:\n  zipkin:\n    base-url: http://192.168.98.11:30941\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n\n\n\n\n\n',
        '85dd0b1180e0fa4aeb895082da8327a8', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 317, 'renren-fast-dev.yaml', 'dev', '',
        '\n\nspring:\n  zipkin:\n    base-url: http://192.168.98.11:30941\n\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      db-type: mysql\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      url: jdbc:mysql://192.168.98.11:30306/themall_admin?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n      username: root\n      password: \"005400\"\n      initial-size: 10\n      max-active: 100\n      min-idle: 10\n      max-wait: 60000\n      pool-prepared-statements: true\n      max-pool-prepared-statement-per-connection-size: 20\n      time-between-eviction-runs-millis: 60000\n      min-evictable-idle-time-millis: 300000\n      #Oracle需要打开注释\n      #validation-query: SELECT 1 FROM DUAL\n      test-while-idle: true\n      test-on-borrow: false\n      test-on-return: false\n      stat-view-servlet:\n        enabled: true\n        url-pattern: /druid/*\n        #login-username: admin\n        #login-password: admin\n      filter:\n        stat:\n          log-slow-sql: true\n          slow-sql-millis: 1000\n          merge-sql: false\n        wall:\n          config:\n            multi-statement-allow: true\n  ##多数据源的配置\n  #dynamic:\n  #  datasource:\n  #    slave1:\n  #      driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver\n  #      url: jdbc:sqlserver://localhost:1433;DatabaseName=renren_security\n  #      username: sa\n  #      password: 123456\n  #    slave2:\n  #      driver-class-name: org.postgresql.Driver\n  #      url: jdbc:postgresql://localhost:5432/renren_security\n  #      username: renren\n  #      password: 123456\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n\nmybatis-plus:\n  #实体扫描，多个package用逗号或者分号分隔\n  typeAliasesPackage: io.renren.modules.*.entity\n\n\nrenren:\n  redis:\n    open: false\n  shiro:\n    redis: false\n  # APP模块，是通过jwt认证的，如果要使用APP模块，则需要修改【加密秘钥】\n  jwt:\n    # 加密秘钥\n    secret: f4e2e52034348f86b67cde581c0f9eb5[www.theangel.vip]\n    # token有效时长，7天，单位秒\n    expire: 604800\n    header: token\n\nlogging:\n  level:\n    org.springframework.cloud.openfeign: info\n    org.springframework.sleuth: info\n',
        '6d7e0a2bf6269c00787a7a2b48408a5d', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 318, 'themall-auth-server-dev.yaml', 'dev', '',
        'spring:\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n  zipkin:\n    base-url: http://192.168.98.11:30941\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n\n  #开发期间 关闭缓存\n  thymeleaf:\n    #    cache: false\n    prefix: classpath:/templates/\n    suffix: .html\n\nlogging:\n  level:\n    org.springframework.cloud.openfeign: info\n    org.springframework.sleuth: info\n\n\n',
        '5c4f5237936c9d83da560aa43f3f7bb2', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 319, 'common.yaml', 'dev', '',
        '# Tomcat\nserver:\n  tomcat:\n    uri-encoding: UTF-8\n    max-threads: 1000\n    min-spare-threads: 30\n  connection-timeout: 5000ms\n  servlet:\n    session:\n      #session超时时间\n      timeout: 30m\n\nspring:\n  zipkin:\n    locator:\n      discovery:\n        #关闭服务发现，否则springcloud会把zipkin的url当成服务名称\n        enabled: false\n    sender:\n      #设置http发送数据\n      type: web\n  sleuth:\n    sampler:\n      #      设置抽取采集率为100%，默认为10%\n      probability: 1\n\n  session:\n    store-type: redis\n    #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀但开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  # jackson时间格式化\n  jackson:\n    time-zone: GMT+8\n#    date-format: yyyy-MM-dd HH:mm:ss\n  servlet:\n    multipart:\n      max-file-size: 100MB\n      max-request-size: 100MB\n      enabled: true\n  redis:\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    timeout: 6000ms  # 连接超时时长（毫秒）\n    jedis:\n      pool:\n        max-active: 1000  # 连接池最大连接数（使用负值表示没有限制）\n        max-wait: -1ms      # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-idle: 10      # 连接池中的最大空闲连接\n        min-idle: 5       # 连接池中的最小空闲连接\n  mvc:\n    throw-exception-if-no-handler-found: true\n #   date-format: yyyy-MM-dd HH:mm:ss\n\nmybatis-plus:\n  mapper-locations: classpath*:/mapper/**/*.xml\n  global-config:\n    #数据库相关配置\n    db-config:\n      #主键类型  AUTO:\"数据库ID自增\", INPUT:\"用户输入ID\", ID_WORKER:\"全局唯一ID (数字类型唯一ID)\", UUID:\"全局唯一ID UUID\";\n      id-type: ID_WORKER\n      logic-delete-field: flag  # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\n    banner: false\n\n  #原生配置\n  configuration:\n    map-underscore-to-camel-case: true\n    cache-enabled: false\n    call-setters-on-nulls: true\n    jdbc-type-for-null: \'null\'\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n\nfeign:\n  sentinel:\n    enabled: true\n\n',
        'd37df780366b378ba3cad1a1d219897f', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 320, 'themall-cart-dev.yaml', 'dev', '',
        'spring:\n  zipkin:\n    base-url: http://192.168.98.11:30941\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n  thymeleaf:\n    cache: false',
        '889f6957ba11c5650ae0eb4bd4e70dfa', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 321, 'themall-coupon-dev.yaml', 'dev', '',
        'spring:\n  zipkin:\n    base-url: http://192.168.98.11:30941\n    \n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.98.11:30306/themall_sms?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858',
        'a8a8c0586f688859ca6fbe3f949c1581', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 322, 'themall-member-dev.yaml', 'dev', '',
        'spring:\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  zipkin:\n    base-url: http://192.168.98.11:30941\n    \n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.98.11:30306/themall_ums?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n',
        '2eaa662a430161b95458dffbe5ac022c', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 323, 'themall-order-dev.yaml', 'dev', '',
        'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.98.11:30306/themall_oms?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n\n  rabbitmq:\n    host: 192.168.98.11\n    username: admin\n    password: \"005400\"\n    virtual-host: /themall\n    port: 30672\n    #开启发送端确认，到达rabbitmq\n    publisher-confirms: true\n    #发送端确认，有没有路由到指定的key  集群是一个副本模式，所有确认才能够确认\n    publisher-returns: true\n    #    只要抵达队列，以异步发送优先回调我们这个returns\n    template:\n      mandatory: true\n    listener:\n      simple:\n        #        auto:自动回复    manual:手动确认\n        acknowledge-mode: manual\n        \n  thymeleaf:\n    cache: false\n\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  zipkin:\n    base-url: http://192.168.98.11:30941',
        '65e37f6217fdecb9acfb0c4ebeefa297', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 324, 'themall-product-dev.yaml', 'dev', '',
        'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.98.11:30306/themall_pms?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n    \n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  #开发期间 关闭缓存\n  thymeleaf:\n    cache: false\n    prefix: classpath:/templates/\n    suffix: .html\n\n\nthemall:\n  thread:\n    coreSize: 10\n    maxSize: 200\n    keepAliveTime: 10\nlogging:\n  level:\n    com.theangel.themall: info\n\n\n',
        '8813b6828123950a525366e82617da4a', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 325, 'themall-search-dev.yaml', 'dev', '',
        'spring:\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n  elasticsearch:\n    cluster:\n      nodes: http://192.168.98.11:30920',
        '5397d3f57d3a2fe29082ed2aa7917213', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 326, 'themall-seckill-dev.yaml', 'dev', '',
        'spring:\n  rabbitmq:\n    host: 192.168.98.11\n    username: admin\n    password: \"005400\"\n    virtual-host: /themall\n    port: 30672\n    #开启发送端确认，到达rabbitmq\n    publisher-confirms: true\n    #发送端确认，有没有路由到指定的key  集群是一个副本模式，所有确认才能够确认\n    publisher-returns: true\n    #    只要抵达队列，以异步发送优先回调我们这个returns\n    template:\n      mandatory: true\n    listener:\n      simple:\n        #        auto:自动回复    manual:手动确认\n        acknowledge-mode: manual\n  thymeleaf:\n    cache: false\n\n\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  zipkin:\n    base-url: http://192.168.98.11:30941\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n  task:\n    execution:\n      pool:\n        core-size: 20\n        maxSize: 50\n        queueCapacity: 50\n\nthemall:\n  thread:\n    coreSize: 10\n    maxSize: 200\n    keepAliveTime: 10\n\nlogging:\n  level:\n    com.theangel.themall: info\n\n\n    ',
        '5594f13c402044d091fc58b898239c55', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 327, 'themall-ware-dev.yaml', 'dev', '',
        'spring:\n  rabbitmq:\n    host: 192.168.98.11\n    username: admin\n    password: \"005400\"\n    virtual-host: /themall\n    port: 30672\n    #开启发送端确认，到达rabbitmq\n    publisher-confirms: true\n    #发送端确认，有没有路由到指定的key  集群是一个副本模式，所有确认才能够确认\n    publisher-returns: true\n    #    只要抵达队列，以异步发送优先回调我们这个returns\n    template:\n      mandatory: true\n    listener:\n      simple:\n        #        auto:自动回复    manual:手动确认\n        acknowledge-mode: manual\n\n\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://192.168.98.11:30306/themall_wms?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n\n\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  zipkin:\n    base-url: http://192.168.98.11:30941\n\n',
        '2e4c1d49aeb0eb432546e149f9b11de1', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 328, 'third-party-dev.yaml', 'dev', '',
        'spring:\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  # 这些配置在腾讯云控制台都可查到（使用时替换为你自己的）\n  # 腾讯云的SecretId（永久的，可在控制台开启或关闭）\n  tencent:\n    secretId: AKIDIPHsCF3aqpdAQP7ZkeGdjY6NN6YicGdi\n    # 腾讯云的SecretKey（永久的，可在控制台开启或关闭）\n    SecretKey: qOffOq1uWrviFVVDetrYx5wWqtfAOKdg\n    # 腾讯云的bucket (存储桶)\n    bucket: theangel-1306086135\n    # 腾讯云的region(bucket所在地区)\n    region: ap-guangzhou\n    # 腾讯云的allowPrefix(允许上传的路径)\n    allowPrefix: \'*\'\n    # 腾讯云的临时密钥时长(单位秒)\n    durationSeconds: 10\n    # 腾讯云的访问基础链接:\n    baseUrl: https://theangel-1306086135.cos.ap-guangzhou.myqcloud.com\n\n    alicloud:\n      access-key: LTAI5t5Zw9QpcLrfdGCA4NZZ\n      secret-key: MEYjLvITV4G6hkv12N41mbrcqR3phk\n      oss:\n        endpoint: oss-cn-hangzhou.aliyuncs.com\n        bucket: theangel-mall\n      sms:\n        host: https://dfsns.market.alicloudapi.com\n        path: /data/send_sms\n        appcode: 9d04ec7d99b740a9973cf01a438b9337',
        'd76a58b36de4cefe600d5b5f132ff334', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 329, 'common.yaml', 'prod', '',
        '# Tomcat\nserver:\n  tomcat:\n    uri-encoding: UTF-8\n    max-threads: 1000\n    min-spare-threads: 30\n  connection-timeout: 5000ms\n  servlet:\n    session:\n      #session超时时间\n      timeout: 30m\n\nspring:\n  zipkin:\n    locator:\n      discovery:\n        #关闭服务发现，否则springcloud会把zipkin的url当成服务名称\n        enabled: false\n    sender:\n      #设置http发送数据\n      type: web\n  sleuth:\n    sampler:\n      #      设置抽取采集率为100%，默认为10%\n      probability: 1\n\n  session:\n    store-type: redis\n    #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀但开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  # jackson时间格式化\n  jackson:\n    time-zone: GMT+8\n#    date-format: yyyy-MM-dd HH:mm:ss\n  servlet:\n    multipart:\n      max-file-size: 100MB\n      max-request-size: 100MB\n      enabled: true\n  redis:\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    timeout: 6000ms  # 连接超时时长（毫秒）\n    jedis:\n      pool:\n        max-active: 1000  # 连接池最大连接数（使用负值表示没有限制）\n        max-wait: -1ms      # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-idle: 10      # 连接池中的最大空闲连接\n        min-idle: 5       # 连接池中的最小空闲连接\n  mvc:\n    throw-exception-if-no-handler-found: true\n #   date-format: yyyy-MM-dd HH:mm:ss\n\nmybatis-plus:\n  mapper-locations: classpath*:/mapper/**/*.xml\n  global-config:\n    #数据库相关配置\n    db-config:\n      #主键类型  AUTO:\"数据库ID自增\", INPUT:\"用户输入ID\", ID_WORKER:\"全局唯一ID (数字类型唯一ID)\", UUID:\"全局唯一ID UUID\";\n      id-type: ID_WORKER\n      logic-delete-field: flag  # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\n    banner: false\n\n  #原生配置\n  configuration:\n    map-underscore-to-camel-case: true\n    cache-enabled: false\n    call-setters-on-nulls: true\n    jdbc-type-for-null: \'null\'\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n\nfeign:\n  sentinel:\n    enabled: true\n\n',
        'd37df780366b378ba3cad1a1d219897f', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 330, 'themall-gatway-prod.yaml', 'prod', '',
        'spring:\n  zipkin:\n    base-url: http://192.168.98.11:30941\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n\n\n\n\n\n',
        '85dd0b1180e0fa4aeb895082da8327a8', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 331, 'renren-fast-prod.yaml', 'prod', '',
        '\n\nspring:\n  zipkin:\n    base-url: http://192.168.98.11:30941\n\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      db-type: mysql\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      url: jdbc:mysql://mysql:3306/themall_admin?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n      username: root\n      password: \"005400\"\n      initial-size: 10\n      max-active: 100\n      min-idle: 10\n      max-wait: 60000\n      pool-prepared-statements: true\n      max-pool-prepared-statement-per-connection-size: 20\n      time-between-eviction-runs-millis: 60000\n      min-evictable-idle-time-millis: 300000\n      #Oracle需要打开注释\n      #validation-query: SELECT 1 FROM DUAL\n      test-while-idle: true\n      test-on-borrow: false\n      test-on-return: false\n      stat-view-servlet:\n        enabled: true\n        url-pattern: /druid/*\n        #login-username: admin\n        #login-password: admin\n      filter:\n        stat:\n          log-slow-sql: true\n          slow-sql-millis: 1000\n          merge-sql: false\n        wall:\n          config:\n            multi-statement-allow: true\n  ##多数据源的配置\n  #dynamic:\n  #  datasource:\n  #    slave1:\n  #      driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver\n  #      url: jdbc:sqlserver://localhost:1433;DatabaseName=renren_security\n  #      username: sa\n  #      password: 123456\n  #    slave2:\n  #      driver-class-name: org.postgresql.Driver\n  #      url: jdbc:postgresql://localhost:5432/renren_security\n  #      username: renren\n  #      password: 123456\n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n\nmybatis-plus:\n  #实体扫描，多个package用逗号或者分号分隔\n  typeAliasesPackage: io.renren.modules.*.entity\n\n\nrenren:\n  redis:\n    open: false\n  shiro:\n    redis: false\n  # APP模块，是通过jwt认证的，如果要使用APP模块，则需要修改【加密秘钥】\n  jwt:\n    # 加密秘钥\n    secret: f4e2e52034348f86b67cde581c0f9eb5[www.theangel.vip]\n    # token有效时长，7天，单位秒\n    expire: 604800\n    header: token\n\nlogging:\n  level:\n    org.springframework.cloud.openfeign: info\n    org.springframework.sleuth: info\n',
        '6baaf57c54be834ebd230b8f69bfcd10', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 332, 'themall-auth-server-prod.yaml', 'prod', '',
        'spring:\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n  zipkin:\n    base-url: http://192.168.98.11:30941\n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n\n  #开发期间 关闭缓存\n  thymeleaf:\n    #    cache: false\n    prefix: classpath:/templates/\n    suffix: .html\n\nlogging:\n  level:\n    org.springframework.cloud.openfeign: info\n    org.springframework.sleuth: info\n\n\n',
        'b1d8021397af7f2026f5e1ba02011ed2', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 333, 'themall-cart-prod.yaml', 'prod', '',
        'spring:\n  zipkin:\n    base-url: http://192.168.98.11:30941\n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n  thymeleaf:\n    cache: true',
        '0e586005a11b8fbd1b2ba529635e5d35', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 334, 'themall-coupon-prod.yaml', 'prod', '',
        'spring:\n  zipkin:\n    base-url: http://192.168.98.11:30941\n    \n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://mysql:3306/themall_sms?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n\n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858',
        '09f72142831cda22761867a672cdf842', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 335, 'themall-member-prod.yaml', 'prod', '',
        'spring:\n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  zipkin:\n    base-url: http://192.168.98.11:30941\n    \n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://mysql:3306/themall_ums?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n',
        '7303fa0469317ebb7ae7205698850ea3', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 336, 'themall-order-prod.yaml', 'prod', '',
        'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://mysql:3306/themall_oms?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n\n  rabbitmq:\n    host: rabbit-mq-rabbitmq\n    username: admin\n    password: \"005400\"\n    virtual-host: /themall\n    port: 5672\n    #开启发送端确认，到达rabbitmq\n    publisher-confirms: true\n    #发送端确认，有没有路由到指定的key  集群是一个副本模式，所有确认才能够确认\n    publisher-returns: true\n    #    只要抵达队列，以异步发送优先回调我们这个returns\n    template:\n      mandatory: true\n    listener:\n      simple:\n        #        auto:自动回复    manual:手动确认\n        acknowledge-mode: manual\n        \n  thymeleaf:\n    cache: true\n\n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  zipkin:\n    base-url: http://192.168.98.11:30941',
        'af0a4ec0a16c723e3528e76e2acf5a3b', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 337, 'themall-product-prod.yaml', 'prod', '',
        'spring:\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://mysql:3306/themall_pms?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n    \n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  #开发期间 关闭缓存\n  thymeleaf:\n    cache: true\n    prefix: classpath:/templates/\n    suffix: .html\n\n\nthemall:\n  thread:\n    coreSize: 10\n    maxSize: 200\n    keepAliveTime: 10\nlogging:\n  level:\n    com.theangel.themall: info\n\n\n',
        '5bb126186f2c78361cfe8a4e1f4abcc7', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 338, 'themall-search-prod.yaml', 'prod', '',
        'spring:\n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n  elasticsearch:\n    cluster:\n      nodes: http://elasticsearch:9200',
        'e5715fa87d4c5ab088a872279b013791', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 339, 'themall-seckill-prod.yaml', 'prod', '',
        'spring:\n  rabbitmq:\n    host: rabbit-mq-rabbitmq\n    username: admin\n    password: \"005400\"\n    virtual-host: /themall\n    port: 5672\n    #开启发送端确认，到达rabbitmq\n    publisher-confirms: true\n    #发送端确认，有没有路由到指定的key  集群是一个副本模式，所有确认才能够确认\n    publisher-returns: true\n    #    只要抵达队列，以异步发送优先回调我们这个returns\n    template:\n      mandatory: true\n    listener:\n      simple:\n        #        auto:自动回复    manual:手动确认\n        acknowledge-mode: manual\n  thymeleaf:\n    cache: false\n\n\n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  zipkin:\n    base-url: http://192.168.98.11:30941\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: 192.168.56.10:8858\n  task:\n    execution:\n      pool:\n        core-size: 20\n        maxSize: 50\n        queueCapacity: 50\n\nthemall:\n  thread:\n    coreSize: 10\n    maxSize: 200\n    keepAliveTime: 10\n\nlogging:\n  level:\n    com.theangel.themall: info\n\n\n    ',
        'bad9545ca2da4f114a01448cfd5ec26a', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 340, 'themall-ware-prod.yaml', 'prod', '',
        'spring:\n  rabbitmq:\n    host: rabbit-mq-rabbitmq\n    username: admin\n    password: \"005400\"\n    virtual-host: /themall\n    port: 30672\n    #开启发送端确认，到达rabbitmq\n    publisher-confirms: true\n    #发送端确认，有没有路由到指定的key  集群是一个副本模式，所有确认才能够确认\n    publisher-returns: true\n    #    只要抵达队列，以异步发送优先回调我们这个returns\n    template:\n      mandatory: true\n    listener:\n      simple:\n        #        auto:自动回复    manual:手动确认\n        acknowledge-mode: manual\n\n\n  datasource:\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://mysql:3306/themall_wms?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false\n    username: root\n    password: \"005400\"\n\n\n  redis:\n    password: \"005400\"\n    host: redis-master\n    port: 6379\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  zipkin:\n    base-url: http://192.168.98.11:30941\n\n',
        '03f0f8d9de1f71ccdb085b4c9b48013a', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (0, 341, 'third-party-prod.yaml', 'prod', '',
        'spring:\n  redis:\n    password: \"005400\"\n    host: 192.168.98.11\n    port: 30679\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n\n  # 这些配置在腾讯云控制台都可查到（使用时替换为你自己的）\n  # 腾讯云的SecretId（永久的，可在控制台开启或关闭）\n  tencent:\n    secretId: AKIDIPHsCF3aqpdAQP7ZkeGdjY6NN6YicGdi\n    # 腾讯云的SecretKey（永久的，可在控制台开启或关闭）\n    SecretKey: qOffOq1uWrviFVVDetrYx5wWqtfAOKdg\n    # 腾讯云的bucket (存储桶)\n    bucket: theangel-1306086135\n    # 腾讯云的region(bucket所在地区)\n    region: ap-guangzhou\n    # 腾讯云的allowPrefix(允许上传的路径)\n    allowPrefix: \'*\'\n    # 腾讯云的临时密钥时长(单位秒)\n    durationSeconds: 10\n    # 腾讯云的访问基础链接:\n    baseUrl: https://theangel-1306086135.cos.ap-guangzhou.myqcloud.com\n\n    alicloud:\n      access-key: LTAI5t5Zw9QpcLrfdGCA4NZZ\n      secret-key: MEYjLvITV4G6hkv12N41mbrcqR3phk\n      oss:\n        endpoint: oss-cn-hangzhou.aliyuncs.com\n        bucket: theangel-mall\n      sms:\n        host: https://dfsns.market.alicloudapi.com\n        path: /data/send_sms\n        appcode: 9d04ec7d99b740a9973cf01a438b9337',
        'd76a58b36de4cefe600d5b5f132ff334', '2010-05-05 00:00:00', '2023-11-20 02:23:29', NULL, '10.244.0.0', 'I',
        'c6efdd0f-1682-4586-9812-75b5b4e381bd'),
       (198, 342, 'public.yml', 'DEFAULT_GROUP', '',
        'spring:\n  redis:\n    host: redis.themall\n    port: 6379\n  zipkin:\n    base-url: http://zipkin-server.themall:9411\n    locator:\n      discovery:\n        #关闭服务发现，否则springcloud会把zipkin的url当成服务名称\n        enabled: false\n    sender:\n      #设置http发送数据\n      type: web\n  sleuth:\n    sampler:\n      #      设置抽取采集率为100%，默认为10%\n      probability: 1\n\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: sentinel-server.themall:8333\n    nacos:\n      discovery:\n        server-addr: nacos-server.themall:8848\n        namespace: 9e9bfac1-f266-4fc4-b220-2c95997c532b\n\nmybatis-plus:\n  mapper-locations: classpath*:/mapper/**/*.xml\n  global-config:\n    db-config:\n      id-type: auto\n\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n\nfeign:\n  sentinel:\n    enabled: true\n\n\nlogging:\n  level:\n    org.springframework.cloud.openfeign: debug\n    org.springframework.sleuth: debug\n\n\n    ',
        'cd1cbbcf00431b1d95b828dfd753c6bb', '2010-05-05 00:00:00', '2023-11-20 02:35:58', NULL, '10.244.0.0', 'D', ''),
       (199, 343, 'themall-coupon.yml', 'DEFAULT_GROUP', '',
        'server:\n  port: 8080\nspring:\n  cloud:\n    nacos:\n      config:\n        server-addr: nacos-server.themall:8848\n#        namespace: 9e9bfac1-f266-4fc4-b220-2c95997c532b\n        extension-configs:\n          - dataId: public.yml\n            refresh: true\n          - dataId: themall-coupon.yml\n            refresh: true\n  application:\n    name: themall-coupon\n\n\n  #session保存redis\n  session:\n    store-type: redis\n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  datasource:\n    username: root\n    password: hjj0127..\n    url: jdbc:mysql://mysql-master.themall:3306/themall_sms?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai\n    driver-class-name: com.mysql.cj.jdbc.Driver\n\n\n',
        '3521ab675bd70aa04b52e39f5eb314a8', '2010-05-05 00:00:00', '2023-11-20 02:35:58', NULL, '10.244.0.0', 'D', ''),
       (200, 344, 'themall-member.yml', 'DEFAULT_GROUP', '',
        'server:\n  port: 8080\nspring:\n  cloud:\n    nacos:\n      config:\n#        namespace: 9e9bfac1-f266-4fc4-b220-2c95997c532b\n        extension-configs:\n          - dataId: public.yml\n            refresh: true\n          - dataId: themall-member.yml\n            refresh: true\n        server-addr: nacos-server.themall:8848\n  application:\n    name: themall-member\n\n\n\n  #session保存redis\n  session:\n    store-type: redis\n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  datasource:\n    username: root\n    password: hjj0127..\n    url: jdbc:mysql://mysql-master.themall:3306/themall_ums?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai\n    driver-class-name: com.mysql.cj.jdbc.Driver\n  mvc:\n    date-format: yyyy-MM-dd HH:mm:ss',
        '89695c82e1afa2e91d3d5a61ba56a2ce', '2010-05-05 00:00:00', '2023-11-20 02:35:58', NULL, '10.244.0.0', 'D', ''),
       (201, 345, 'themall-order.yml', 'DEFAULT_GROUP', '',
        'server:\n  port: 8080\nspring:\n  cloud:\n    nacos:\n      config:\n#        namespace: 9e9bfac1-f266-4fc4-b220-2c95997c532b\n        extension-configs:\n          - dataId: public.yml\n            refresh: true\n          - dataId: themall-order.yml\n            refresh: true\n        server-addr: nacos-server.themall:8848\n  application:\n    name: themall-order\n  #session保存redis\n  session:\n    store-type: redis\n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  datasource:\n    username: root\n    password: hjj0127..\n    url: jdbc:mysql://mysql-master.themall:3306/themall_oms?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai\n    driver-class-name: com.mysql.cj.jdbc.Driver\n\n  rabbitmq:\n    host: rabbitmq.themall\n    username: guest\n    password: guest\n    virtual-host: /themall\n    port: 5672\n    #开启发送端确认，到达rabbitmq\n    publisher-confirms: true\n    #发送端确认，有没有路由到指定的key  集群是一个副本模式，所有确认才能够确认\n    publisher-returns: true\n    #    只要抵达队列，以异步发送优先回调我们这个returns\n    template:\n      mandatory: true\n    listener:\n      simple:\n        #        auto:自动回复    manual:手动确认\n        acknowledge-mode: manual\n\n  thymeleaf:\n    cache: false\n  mvc:\n    date-format: yyyy-MM-dd HH:mm:ss\n\n#spring.cloud.alibaba.seata.tx-service-group修改后缀，但是必须和file.conf中的配置保持一致\n',
        'de078751d232a2963a36ce931efb3a57', '2010-05-05 00:00:00', '2023-11-20 02:35:58', NULL, '10.244.0.0', 'D', ''),
       (202, 346, 'themall-product.yml', 'DEFAULT_GROUP', '',
        'server:\n  port: 8080\nspring:\n  cloud:\n    nacos:\n      config:\n#        namespace: 9e9bfac1-f266-4fc4-b220-2c95997c532b\n        server-addr: nacos-server.themall:8848\n        extension-configs:\n          - dataId: public.yml\n            refresh: true\n          - dataId: themall-product.yml\n            refresh: true\n  application:\n    name: themall-product\n\n\n  #session保存redis\n  session:\n    store-type: redis\n   #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀但开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  servlet:\n    multipart:\n      max-file-size: 20MB\n      max-request-size: 200MB\n  datasource:\n    username: root\n    password: hjj0127..\n    url: jdbc:mysql://mysql-master.themall:3306/themall_pms?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai\n    driver-class-name: com.mysql.cj.jdbc.Driver\n\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n  #开发期间 关闭缓存\n  thymeleaf:\n    cache: false\n    prefix: classpath:/templates/\n    suffix: .html\n\nmybatis-plus:\n  mapper-locations: classpath*:/mapper/**/*.xml\n  global-config:\n    db-config:\n      id-type: auto\n      logic-delete-field: flag  # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)\n      logic-delete-value: 1 # 逻辑已删除值(默认为 1)\n      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)\n\nthemall:\n  thread:\n    coreSize: 10\n    maxSize: 200\n    keepAliveTime: 10\n\nlogging:\n  level:\n    com.theangel.themall: error',
        '2a336bba1ac9735040fc7c8095bf6a1e', '2010-05-05 00:00:00', '2023-11-20 02:35:58', NULL, '10.244.0.0', 'D', ''),
       (203, 347, 'themall-ware.yml', 'DEFAULT_GROUP', '',
        'server:\n  port: 8080\nspring:\n  cloud:\n    nacos:\n      config:\n#        namespace: 9e9bfac1-f266-4fc4-b220-2c95997c532b\n        extension-configs:\n          - dataId: public.yml\n            refresh: true\n          - dataId: themall-ware.yml\n            refresh: true\n        server-addr: nacos-server.themall:8848\n  application:\n    name: themall-ware\n  #session保存redis\n  session:\n    store-type: redis\n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  datasource:\n    username: root\n    password: hjj0127..\n    url: jdbc:mysql://mysql-master.themall:3306/themall_wms?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai\n    driver-class-name: com.mysql.cj.jdbc.Driver\n\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n  rabbitmq:\n    host: rabbitmq.themall\n    username: guest\n    password: guest\n    virtual-host: /themall\n    port: 5672\n    #开启发送端确认，到达rabbitmq\n    publisher-confirms: true\n    #发送端确认，有没有路由到指定的key  集群是一个副本模式，所有确认才能够确认\n    publisher-returns: true\n    #    只要抵达队列，以异步发送优先回调我们这个returns\n    template:\n      mandatory: true\n    listener:\n      simple:\n        #        auto:自动回复    manual:手动确认\n        acknowledge-mode: manual\n\n#spring.cloud.alibaba.seata.tx-service-group修改后缀，但是必须和file.conf中的配置保持一致',
        '67ddef417a0800779e4297161fd929c0', '2010-05-05 00:00:00', '2023-11-20 02:35:58', NULL, '10.244.0.0', 'D', ''),
       (204, 348, 'themall-gatway.yml', 'DEFAULT_GROUP', '',
        'server:\n  port: 8080\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \"*\"\n\nspring:\n  application:\n    name: themall-gatway\n  zipkin:\n    base-url: http://zipkin-server.themall:9411\n    locator:\n      discovery:\n        #关闭服务发现，否则springcloud会把zipkin的url当成服务名称\n        enabled: false\n\n    sender:\n      #设置http发送数据\n      type: web\n\n  sleuth:\n    sampler:\n      #      设置抽取采集率为100%，默认为10%\n      probability: 1\n\n  cloud:\n    sentinel:\n      transport:\n        port: 8719\n        dashboard: sentinel-server.themall:8333\n    nacos:\n      config:\n        server-addr: nacos-server.themall:8848\n#        namespace: 9e9bfac1-f266-4fc4-b220-2c95997c532b\n        extension-configs:\n          - dataId: themall-gatway.yml\n            refresh: true\n      discovery:\n        server-addr: nacos-server.themall:8848\n#        namespace: 9e9bfac1-f266-4fc4-b220-2c95997c532b\n\n    gateway:\n      routes:\n        #         检索服务\n        - id: themall-search\n          uri: lb://themall-search\n          predicates:\n            - Path=/api/search/**\n          filters:\n            - RewritePath=/api/?(?<the>.*), /$\\{the}\n\n        #          库存服务\n        - id: themall-ware\n          uri: lb://themall-ware\n          predicates:\n            - Path=/api/ware/**\n          filters:\n            - RewritePath=/api/?(?<the>.*), /$\\{the}\n\n        #          商品优惠服务\n        - id: themall-coupon\n          uri: lb://themall-coupon\n          predicates:\n            - Path=/api/coupon/**\n          filters:\n            - RewritePath=/api/?(?<the>.*), /$\\{the}\n\n        #         第三方服务\n        - id: third-party\n          uri: lb://third-party\n          predicates:\n            - Path=/api/third-party/**\n          filters:\n            - RewritePath=/api/?(?<the>.*), /$\\{the}\n\n        #         商品服务\n        - id: themall-product\n          uri: lb://themall-product\n          predicates:\n            - Path=/api/product/**\n          filters:\n            - RewritePath=/api/?(?<the>.*), /$\\{the}\n\n        #         外网穿透的地址\n        - id: themall\n          uri: lb://themall-product\n          predicates:\n            - Path=/themall/**\n          filters:\n            - RewritePath=/themall/?(?<the>.*), /$\\{the}\n\n        #         会员服务\n        - id: themall-member\n          uri: lb://themall-member\n          predicates:\n            - Path=/api/member/**\n          filters:\n            - RewritePath=/api/?(?<the>.*), /$\\{the}\n\n        #\n        - id: renren-fast\n          uri: lb://renren-fast\n          predicates:\n            - Path=/api/**,/renren-fast/**\n          filters:\n            - RewritePath=/api/?(?<the>.*), /renren-fast/$\\{the}\n\n\n        # 商品详情页\n        - id: item\n          uri: lb://themall-product\n          predicates:\n            - Host=item.theangel.com\n        #            - Path=/item/*.html\n\n        # 商品搜索\n        - id: search\n          uri: lb://themall-search\n          predicates:\n            - Host=search.theangel.com\n        #            - Path=/list.html\n\n        # 认证\n        - id: auth-server\n          uri: lb://themall-auth-server\n          predicates:\n            - Host=auth.theangel.com\n        #            - Path=/auth/**\n\n        # 购物车\n        - id: themall-cart\n          uri: lb://themall-cart\n          predicates:\n            - Host=cart.theangel.com\n\n        # 订单\n        - id: themall-order\n          uri: lb://themall-order\n          predicates:\n            - Host=order.theangel.com\n\n        # 首页\n        - id: product-index\n          uri: lb://themall-product\n          predicates:\n            - Host=theangel.com,theangel.vip:8888,127.0.0.1:88,159.75.26.150:8888\n\n        # 用户订单\n        - id: member\n          uri: lb://themall-member\n          predicates:\n            - Host=member.theangel.com\n\n\n        # 秒杀\n        - id: themall-seckill\n          uri: lb://themall-seckill\n          predicates:\n            - Host=seckill.theangel.com\n\nlogging:\n  level:\n    org.springframework.cloud.openfeign: debug\n    org.springframework.sleuth: debug\n\n\n\n',
        'b70c512ecffe769bf1957cbc0dde887c', '2010-05-05 00:00:00', '2023-11-20 02:35:58', NULL, '10.244.0.0', 'D', ''),
       (205, 349, 'renren-fast.yml', 'DEFAULT_GROUP', '',
        '# Tomcat\nserver:\n  port: 8080\n  tomcat:\n    uri-encoding: UTF-8\n    max-threads: 1000\n    min-spare-threads: 30\n  connection-timeout: 5000ms\n  servlet:\n    context-path: /renren-fast\n\nspring:\n  cloud:\n    nacos:\n      config:\n        server-addr: nacos-server.themall:8848\n        #                namespace: 9e9bfac1-f266-4fc4-b220-2c95997c532b\n        extension-configs:\n          - dataId: public.yml\n            refresh: true\n          - dataId: renren-fast.yml\n            refresh: true\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      url: jdbc:mysql://mysql-master.themall:3306/themall_oms?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai\n      username: root\n      password: hjj0127..\n      initial-size: 10\n      max-active: 100\n      min-idle: 10\n      max-wait: 60000\n      pool-prepared-statements: true\n      max-pool-prepared-statement-per-connection-size: 20\n      time-between-eviction-runs-millis: 60000\n      min-evictable-idle-time-millis: 300000\n      #Oracle需要打开注释\n      #validation-query: SELECT 1 FROM DUAL\n      test-while-idle: true\n      test-on-borrow: false\n      test-on-return: false\n      stat-view-servlet:\n        enabled: true\n        url-pattern: /druid/*\n        #login-username: admin\n        #login-password: admin\n      filter:\n        stat:\n          log-slow-sql: true\n          slow-sql-millis: 1000\n          merge-sql: false\n        wall:\n          config:\n            multi-statement-allow: true\n  ##多数据源的配置\n  #dynamic:\n  #  datasource:\n  #    slave1:\n  #      driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver\n  #      url: jdbc:sqlserver://localhost:1433;DatabaseName=renren_security\n  #      username: sa\n  #      password: 123456\n  #    slave2:\n  #      driver-class-name: org.postgresql.Driver\n  #      url: jdbc:postgresql://localhost:5432/renren_security\n  #      username: renren\n  #      password: 123456\n  application:\n    name: renren-fast\n  # 环境 dev|test|prod\n#  profiles:\n#    active: dev\n  # jackson时间格式化\n  jackson:\n    time-zone: GMT+8\n    date-format: yyyy-MM-dd HH:mm:ss\n  servlet:\n    multipart:\n      max-file-size: 100MB\n      max-request-size: 100MB\n      enabled: true\n  redis:\n    open: true  # 是否开启redis缓存  true开启   false关闭\n    database: 0\n    timeout: 6000ms  # 连接超时时长（毫秒）\n    jedis:\n      pool:\n        max-active: 1000  # 连接池最大连接数（使用负值表示没有限制）\n        max-wait: -1ms      # 连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-idle: 10      # 连接池中的最大空闲连接\n        min-idle: 5       # 连接池中的最小空闲连接\n  mvc:\n    throw-exception-if-no-handler-found: true\n\n#  resources:\n#    add-mappings: false\n#mybatis\nmybatis-plus:\n  mapper-locations: classpath*:/mapper/**/*.xml\n  #实体扫描，多个package用逗号或者分号分隔\n  typeAliasesPackage: io.renren.modules.*.entity\n  global-config:\n    #数据库相关配置\n    db-config:\n      #主键类型  AUTO:\"数据库ID自增\", INPUT:\"用户输入ID\", ID_WORKER:\"全局唯一ID (数字类型唯一ID)\", UUID:\"全局唯一ID UUID\";\n      id-type: AUTO\n      logic-delete-value: -1\n      logic-not-delete-value: 0\n    banner: false\n  #原生配置\n  configuration:\n    map-underscore-to-camel-case: true\n    cache-enabled: false\n    call-setters-on-nulls: true\n    jdbc-type-for-null: \'null\'\n\n\nrenren:\n  redis:\n    open: false\n  shiro:\n    redis: false\n  # APP模块，是通过jwt认证的，如果要使用APP模块，则需要修改【加密秘钥】\n  jwt:\n    # 加密秘钥\n    secret: f4e2e52034348f86b67cde581c0f9eb5[www.theangel.vip]\n    # token有效时长，7天，单位秒\n    expire: 604800\n    header: token',
        '87554fe58b06f02161e288e5cb08dbaf', '2010-05-05 00:00:00', '2023-11-20 02:35:58', NULL, '10.244.0.0', 'D', ''),
       (206, 350, 'third-party.yml', 'DEFAULT_GROUP', '',
        'server:\n  port: 8080\nspring:\n  application:\n    name: third-party\n  #session保存redis\n  session:\n    store-type: redis\n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  servlet:\n    multipart:\n      max-file-size: 20MB\n      max-request-size: 200MB\n\n  # 这些配置在腾讯云控制台都可查到（使用时替换为你自己的）\n  # 腾讯云的SecretId（永久的，可在控制台开启或关闭）\n  tencent:\n    secretId: AKIDIPHsCF3aqpdAQP7ZkeGdjY6NN6YicGdi\n    # 腾讯云的SecretKey（永久的，可在控制台开启或关闭）\n    SecretKey: qOffOq1uWrviFVVDetrYx5wWqtfAOKdg\n    # 腾讯云的bucket (存储桶)\n    bucket: theangel-1306086135\n    # 腾讯云的region(bucket所在地区)\n    region: ap-guangzhou\n    # 腾讯云的allowPrefix(允许上传的路径)\n    allowPrefix: \'*\'\n    # 腾讯云的临时密钥时长(单位秒)\n    durationSeconds: 10\n    # 腾讯云的访问基础链接:\n    baseUrl: https://theangel-1306086135.cos.ap-guangzhou.myqcloud.com\n  cloud:\n    nacos:\n      config:\n        server-addr: nacos-server.themall:8848\n#        namespace: 9e9bfac1-f266-4fc4-b220-2c95997c532b\n        extension-configs:\n          - dataId: public.yml\n            refresh: true\n          - dataId: third-party.yml\n            refresh: true\n    alicloud:\n      access-key: LTAI5t5Zw9QpcLrfdGCA4NZZ\n      secret-key: MEYjLvITV4G6hkv12N41mbrcqR3phk\n      oss:\n        endpoint: oss-cn-hangzhou.aliyuncs.com\n        bucket: theangel-mall\n      sms:\n        host: https://dfsns.market.alicloudapi.com\n        path: /data/send_sms\n        appcode: 9d04ec7d99b740a9973cf01a438b9337\n',
        '83cc5bfbeb0fed8546d2f35424d25469', '2010-05-05 00:00:00', '2023-11-20 02:35:58', NULL, '10.244.0.0', 'D', ''),
       (207, 351, 'themall-search.yml', 'DEFAULT_GROUP', '',
        'server:\n  port: 8080\nspring:\n  application:\n    name: themall-search\n  cloud:\n    nacos:\n      config:\n        #        namespace: 9e9bfac1-f266-4fc4-b220-2c95997c532b\n        extension-configs:\n          - dataId: public.yml\n            refresh: true\n          - dataId: themall-search.yml\n            refresh: true\n        server-addr: nacos-server.themall:8848\n\n  #session保存redis\n  session:\n    store-type: redis\n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n',
        '3293c4c46377ed226c39f8f3324aa227', '2010-05-05 00:00:00', '2023-11-20 02:35:58', NULL, '10.244.0.0', 'D', ''),
       (208, 352, 'themall-auth-server', 'DEFAULT_GROUP', '',
        'spring:\n  application:\n    name: themall-auth-server\n  cloud:\n    nacos:\n      config:\n#        namespace: public\n        server-addr: nacos-server.themall:8848\n        extension-configs:\n          - dataId: public.yml\n            refresh: true\n          - dataId: themall-auth-server.yml\n            refresh: true\n\n\n  #session保存redis\n  session:\n    store-type: redis\n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  #开发期间 关闭缓存\n  thymeleaf:\n#    cache: false\n    prefix: classpath:/templates/\n    suffix: .html\n\nlogging:\n  level:\n    com.theangel.themall: error\n\nserver:\n  port: 8080\n  servlet:\n    session:\n      #session超时时间\n      timeout: 30m\n',
        'f7b31d1920e973fe90130c202c2f682b', '2010-05-05 00:00:00', '2023-11-20 02:35:58', NULL, '10.244.0.0', 'D', ''),
       (209, 353, 'themall-cart.yml', 'DEFAULT_GROUP', '',
        'server:\n  port: 8080\nspring:\n  cloud:\n    nacos:\n      config:\n#        namespace: 9e9bfac1-f266-4fc4-b220-2c95997c532b\n        extension-configs:\n          - dataId: public.yml\n            refresh: true\n          - dataId: themall-cart.yml\n            refresh: true\n        server-addr: nacos-server.themall:8848\n  application:\n    name: themall-cart\n\n  #session保存redis\n  session:\n    store-type: redis\n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n#  thymeleaf:\n#    cache: false',
        '206ee956953d675db9c9706ba3f67830', '2010-05-05 00:00:00', '2023-11-20 02:35:58', NULL, '10.244.0.0', 'D', ''),
       (210, 354, 'themall-seckill.yml', 'DEFAULT_GROUP', '',
        'server:\n  port: 8080\nspring:\n  cloud:\n    nacos:\n      config:\n#        namespace: 9e9bfac1-f266-4fc4-b220-2c95997c532b\n        server-addr: nacos-server.themall:8848\n        extension-configs:\n          - dataId: public.yml\n            refresh: true\n          - dataId: themall-seckill.yml\n            refresh: true\n  application:\n    name: themall-seckill\n  thymeleaf:\n    cache: false\n\n  rabbitmq:\n    port: 5672\n    host: rabbitmq.themall\n    username: guest\n    password: guest\n    virtual-host: /themall\n    template:\n      mandatory: true\n    listener:\n      simple:\n        acknowledge-mode: manual\n    publisher-returns: true\n\n\n  #session保存redis\n  session:\n    store-type: redis\n  #配置redis缓存管理器\n  cache:\n    type: redis\n    # 配置了名字，就只能使用自己的名字\n    redis:\n      # 存活时间\n      time-to-live: 3600000\n      #前缀\n      #      key-prefix: AUTO-CACHE_\n      #是否使用前缀 不使用前缀，开启前缀 会把分区名放前面  分区::名字\n      use-key-prefix: true\n      #是否缓存空值，解决缓存穿透\n      cache-null-values: true\n\n  jackson:\n    date-format: yyyy-MM-dd HH:mm:ss\n  task:\n    execution:\n      pool:\n        core-size: 20\n        maxSize: 50\n        queueCapacity: 50\n\nthemall:\n  thread:\n    coreSize: 10\n    maxSize: 200\n    keepAliveTime: 10\n\nlogging:\n  level:\n    com.theangel.themall: info\n',
        '91b8003a4c9b92b92d5f5f1e9461560d', '2010-05-05 00:00:00', '2023-11-20 02:35:58', NULL, '10.244.0.0', 'D', '');
/*!40000 ALTER TABLE `his_config_info`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions`
(
    `role`     varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci  NOT NULL,
    `resource` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
    `action`   varchar(8) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci   NOT NULL,
    UNIQUE KEY `uk_role_permission` (`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  ROW_FORMAT = DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `permissions`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles`
(
    `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
    `role`     varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
    UNIQUE KEY `uk_username_role` (`username`, `role`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  ROW_FORMAT = DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles`
    DISABLE KEYS */;
INSERT INTO `roles`
VALUES ('nacos', 'ROLE_ADMIN');
/*!40000 ALTER TABLE `roles`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenant_capacity`
--

DROP TABLE IF EXISTS `tenant_capacity`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tenant_capacity`
(
    `id`                bigint unsigned                                        NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `tenant_id`         varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
    `quota`             int unsigned                                           NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
    `usage`             int unsigned                                           NOT NULL DEFAULT '0' COMMENT '使用量',
    `max_size`          int unsigned                                           NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
    `max_aggr_count`    int unsigned                                           NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
    `max_aggr_size`     int unsigned                                           NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
    `max_history_count` int unsigned                                           NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
    `gmt_create`        datetime                                               NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
    `gmt_modified`      datetime                                               NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin
  ROW_FORMAT = DYNAMIC COMMENT ='租户容量信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenant_capacity`
--

LOCK TABLES `tenant_capacity` WRITE;
/*!40000 ALTER TABLE `tenant_capacity`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `tenant_capacity`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenant_info`
--

DROP TABLE IF EXISTS `tenant_info`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tenant_info`
(
    `id`            bigint                                                 NOT NULL AUTO_INCREMENT COMMENT 'id',
    `kp`            varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'kp',
    `tenant_id`     varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
    `tenant_name`   varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_name',
    `tenant_desc`   varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tenant_desc',
    `create_source` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin  DEFAULT NULL COMMENT 'create_source',
    `gmt_create`    bigint                                                 NOT NULL COMMENT '创建时间',
    `gmt_modified`  bigint                                                 NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`, `tenant_id`) USING BTREE,
    KEY `idx_tenant_id` (`tenant_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb3
  COLLATE = utf8mb3_bin
  ROW_FORMAT = DYNAMIC COMMENT ='tenant_info';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenant_info`
--

LOCK TABLES `tenant_info` WRITE;
/*!40000 ALTER TABLE `tenant_info`
    DISABLE KEYS */;
INSERT INTO `tenant_info`
VALUES (2, '1', 'c6efdd0f-1682-4586-9812-75b5b4e381bd', 'themall', 'themall', 'nacos', 1700446946074, 1700446946074);
/*!40000 ALTER TABLE `tenant_info`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users`
(
    `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci  NOT NULL,
    `password` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
    `enabled`  tinyint(1)                                                    NOT NULL,
    PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3
  ROW_FORMAT = DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users`
    DISABLE KEYS */;
INSERT INTO `users`
VALUES ('nacos', '$2a$10$eZJFhasn4VauvtZYn5mGz.qtUCLDjKMKFuZO.cVtTmT64yTyxAFbi', 1),
       ('test', '$2a$10$Ejp0PWLPI5bZCw6oMCbaVuv.CBawylvF3JNggY5KSLOwkRU2oR1ne', 1);
/*!40000 ALTER TABLE `users`
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

-- Dump completed on 2023-11-20 10:36:37
