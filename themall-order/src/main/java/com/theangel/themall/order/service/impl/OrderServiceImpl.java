package com.theangel.themall.order.service.impl;

import com.rabbitmq.client.Channel;
import com.theangel.themall.order.entity.MqMessageEntity;
import com.theangel.themall.order.entity.OrderReturnApplyEntity;
import com.theangel.themall.order.service.OrderReturnReasonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.theangel.common.utils.PageUtils;
import com.theangel.common.utils.Query;

import com.theangel.themall.order.dao.OrderDao;
import com.theangel.themall.order.entity.OrderEntity;
import com.theangel.themall.order.service.OrderService;

@Slf4j
@Service("orderService")
@RabbitListener(queues = "hello-query")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * RabbitListener可以在类和方法上
     * RabbitHandler 只能标注在方法上
     * 在类上使用RabbitListener监听多个队列
     * RabbitHandler方法重载，接收不同的对象（实体类）  ，每个类上标注注解  重载，获取不同的对象
     * <p>
     * 以后使用：
     * RabbitListener监听不同的队列
     * RabbitHandler重载区分不同的方法
     *
     * @param message         可以用Message：原生消息详细信息
     *                        object：和上类似
     * @param mqMessageEntity 发送的是什么类型，接收用什么类型就可以
     * @param channel         当前传输数据的通道
     * @RabbitListener 监听消息
     * <p>
     * <p>
     * 场景一：
     * message         可以用Message：原生消息详细信息
     * object：和上类似
     * mqMessageEntity 发送的是什么类型，接收用什么类型就可以
     * channel         当前传输数据的通道
     * 场景二：
     * Queue:很多人监听，只能有一个人接收到消息，接收到就删除
     */

    @RabbitHandler
    public void RabbitListener(Message message, MqMessageEntity mqMessageEntity, Channel channel) {
        //获取消息体
        byte[] body = message.getBody();

        log.info("内容" + mqMessageEntity);
        log.info("监听的消息为==========》" + message + "内容" + mqMessageEntity);
        log.info("内容" + channel);
    }

    @RabbitHandler
    public void RabbitListener1(OrderReturnApplyEntity mqMessageEntity) {
        //获取消息体
        log.info("内容==========》" + mqMessageEntity);
    }

}