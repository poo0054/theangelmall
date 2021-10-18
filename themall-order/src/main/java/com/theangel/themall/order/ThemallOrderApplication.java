package com.theangel.themall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.aop.framework.AopContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 使用分布式事务： 最大原因 -》网络问题
 * mysql事务隔离级别：
 * 读未提交：  可以读到别人事务中未提交的数据  脏读
 * 读已提交：  只能读到别人事务中提交了的     脏读解决  会有幻读    别人提交前后数据不一样
 * 可重复读：  不管别人怎么操作  在本事务中 读到的第一条数据是多少 后面都是多少                    -》 mysql默认隔离级别
 * 序列化：    只能在一个事务做完了  才能做下一个           没有任何并发会
 * <p>
 * 事务的传播行为：
 * 默认为：如果没有事务，就创建一个，如果有，就加入当前事务中
 * 还有一个常用：不管有没有  都会创建一个新的事务
 * <p>
 * spring本地事务设置失效：
 * 如果在本类，同一个对象中（因为事务就是用的代理） 事务都是默认的，所有的事务设置都会采用最大的那个 其余事务的设置都会不生效
 * 使用this。方法  也没有用
 * 解决方法：
 * 使用代理对象来调用事务方法
 * 1.引用aop-start
 * 2.开启@EnableAspectJAutoProxy   (不使用这个，就是使用jdk接口的方式动态代理)   开启aspectj动态代理  以后所有的动态都是使用它 （即使没有接口，也可以使用动态代理）
 * exposeProxy:对外暴露代理对象
 * 3.用代理对象本类对象
 * ThemallOrderApplication o = (ThemallOrderApplication) AopContext.currentProxy();  转成指定的类
 * 使用这个代理对象调用就会解决这个本地事务设置不生效问题
 * <p>
 * 使用seata:
 * 1.创建日志表
 * 2.下载seata服务
 * 3.导入seata依赖:spring-cloud-starter-alibaba-seata   注意seata-all的版本
 * 4.每个微服务，使用seata代理数据源
 * 5.每个微服务 都必须导入file.conf和registry.conf
 * 6.file.conf 的 service.vgroup_mapping 配置必须和spring.application.name一致
 * 在 org.springframework.cloud:spring-cloud-starter-alibaba-seata的org.springframework.cloud.alibaba.seata.GlobalTransactionAutoConfiguration类中，
 * 默认会使用 ${spring.application.name}-fescar-service-group作为服务名注册到 Seata Server上，如果和file.conf中的配置不一致，会提示 no available server to connect错误
 * 也可以通过配置 spring.cloud.alibaba.seata.tx-service-group修改后缀，但是必须和file.conf中的配置保持一致
 * 在file。conf中server下面改为 vgroup_mapping.{当前应用的名字}-fescar-service-group = "default"   中间使用微服务的项目名
 * 7.给分布式事务，入口 标注全局事务，每一个小事务，使用@Transactional就可以了
 * 注意点
 * vgroupMapping.themall-order-seata-service-group = "default"
 * <p>
 * <p>
 * <p>
 * 使用分布式事务： 最大原因 -》网络问题
 * 使用seata的at模式不适合这种大并发  at类似于pc2
 * tcc：最终一致性，可能某个节点在一段时间不一致，只要最后一致 不推荐
 * 柔性事务： 最大努力通知，如果失败了，一直给自己编写的失败回滚方法发送通知，到达指定的通知量或者自己写的方法回调了，通知我接收到了就不发送了  例如支付宝的充值，一直发送充值成功，等你回调后，就不发送   使用mq
 * 柔性事务：可靠消息加最终一致性。。。业务向消息服务发送通知，不做任何处理，等待mq来做处理，反应快。  类似数据库中加一个处理中     使用mq  推荐
 */
//@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
@EnableDiscoveryClient
public class ThemallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThemallOrderApplication.class, args);
    }

}
