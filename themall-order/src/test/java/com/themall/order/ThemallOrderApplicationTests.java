package com.themall.order;

import com.themall.order.entity.MqMessageEntity;
import com.themall.order.entity.OrderReturnApplyEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ThemallOrderApplication.class)
@EnableRabbit
public class ThemallOrderApplicationTests {

    /**
     * 管理mq
     */
    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    RabbitTemplate rabbitTemplate;


    /**
     * @RabbitListener 监听消息
     */
    @RabbitListener(queues = {"hello-query"})
    @Test
    public void RabbitListener() {
//        log.info("监听的消息为==========》", o);
    }

    /**
     * 发送消息
     */
    @Test
    public void sendMessage0() {
        rabbitTemplate.convertAndSend("hello-exchange", "hello.java", "我是一个str消息");
    }

    /**
     * 发送消息
     */
    @Test
    public void sendMessage() {
        MqMessageEntity mqMessageEntity = new MqMessageEntity();
        mqMessageEntity.setMessageId("1");
        mqMessageEntity.setToExchange("我是一个消息");
        rabbitTemplate.convertAndSend("hello-exchange", "hello.java", mqMessageEntity);
    }

    /**
     * 发送消息
     */
    @Test
    public void sendMessage1() {
        OrderReturnApplyEntity mqMessageEntity = new OrderReturnApplyEntity();
        mqMessageEntity.setCompanyAddress("1");
        mqMessageEntity.setDescPics("我是一个消息");
        rabbitTemplate.convertAndSend("hello-exchange", "hello.java", mqMessageEntity);
    }

    /**
     * DirectExchange：俗称 点对点交换机
     * DirectExchange：发送所有，没有router-key也可以
     * DirectExchange：发送指定得到交换机 *是一个 #是0个或多个
     */
    @Test
    public void creatExchange() {
        DirectExchange hello = new DirectExchange("hello-exchange", false, false);
        amqpAdmin.declareExchange(hello);
    }

    @Test
    public void creatQuery() {
        /**
         * @param name the name of the queue.  名字
         * @param durable true if we are declaring a durable queue (the queue will survive a server restart)  是否持久化
         * @param exclusive true if we are declaring an exclusive queue (the queue will only be used by the declarer's    connection)   是否是排他队列
         *  如果声明的是独占队列，则为True(该队列只会被声明者的连接使用)
         * @param autoDelete true if the server should delete the queue when it is no longer in use     是否自动删除
         */
        amqpAdmin.declareQueue(new Queue("hello-query", true, false, false));
    }

    @Test
    public void creatBindings() {
        /**
         * String destination,    目的地   队列名字  ========》  也可以跟交换机，如果跟交换机绑定，填写交换机名字
         * DestinationType destinationType, 目的地类型
         * String exchange,   交换机
         * String routingKey,  路由键
         * Map<String, Object> arguments  参数
         */
        Binding binding = new Binding("hello-query", Binding.DestinationType.QUEUE, "hello-exchange", "hello.java", null);
        amqpAdmin.declareBinding(binding);
    }

}
