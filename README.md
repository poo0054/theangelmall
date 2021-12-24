# theangelmall


#### 介绍

#### 软件架构
1. 软件架构说明

#### 版本

1.  python 3.x
2.  nodejs 13.x

#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

theangelmall采用springcloud-alibab全家桶，服务注册使用nacos，服务调用使用openfeign，网关使用getway，链路追踪使用zipkin
redis使用rediscluster搭建集群
mysql使用日志主从同步，MyCat搭建集群（也试过shadingshpere，最后采用MyCat搭建）
elasticSearch集群，elasticSearch天然就是分布式，直接加入节点。采用master和data分离
rabbitmq采用镜像集群

renren-fast-vue：后台前端模块，采用开源代码renrenfast，整个商城项目的后台管理

renrenfast：后台后端模块

themall-cart：购物车模块，购物车的基本功能（增删改查）

themall-common：公共模块，其余模块都依赖该模块，常用方法，全局异常

themall-coupon：优惠券模块，优惠券的基本功能

themall-gatway：网关模块，项目的路径跳转

themall-member：用户模块，用户和会员的基本功能

themall-order：订购模块，用户下单的基本功能，下单进行锁库存，死信队列设置订单超时时间释放库存

themall-product：商品模块，商品的基本功能

themall-search：搜索模块elasticsearch。商品上架，添加入el，修改使用canal把el和mysql进行同步

themall-seckill：秒杀功能，redisson信号量设置秒杀数量，抢成功添加入mq进行创建订单操作

themall-ware：库存的基本功能

third-party：第三方服务模块，阿里云的发送短信，对象存储，支付宝支付

Jenkinsfile：kubesphere的devops（Jenkins自动化打包部署） 本地打包镜像 -> 发送docker-hub -> k8s部署项目 -> gitee打上标签

#### 参与贡献

