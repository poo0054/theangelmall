package com.themall.ware.listener;

import com.rabbitmq.client.Channel;
import com.themall.common.to.mq.OrderTo;
import com.themall.common.to.mq.StockLockedTo;
import com.themall.ware.service.WareSkuService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @ProjectName: theangelmall
 * @Package: com.theangel.themall.ware.listener
 * @ClassName: StockReleaseListener
 * @Author: theangel
 * @Date: 2021/9/21 23:51
 */
@Service
@RabbitListener(queues = "stock.release.stock.queue")
public class StockReleaseListener {

    @Autowired
    WareSkuService wareSkuService;

    /**
     * mq释放锁定的库存
     */
    @RabbitHandler
    public void releaseLockStock(StockLockedTo to, Message message, Channel channel) throws IOException {
        System.out.println("准备处理释放锁定库存：" + to);
        try {
            wareSkuService.unLockStock(to);
            //最后执行成功，手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
            //消息消费失败
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }

    /**
     * mq释放锁定订单取消的库存
     */
    @RabbitHandler
    public void handleOrderCloseRelease(OrderTo orderTo, Message message, Channel channel) throws IOException {
        System.out.println("订单关闭消息：" + orderTo);
        try {
            wareSkuService.unLockStock(orderTo);
            //最后执行成功，手动确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
            //消息消费失败
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }


}
