package com.lank.rabbitmq.confirmListener;

import com.lank.rabbitmq.utils.CollectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 * @author lank
 * @since 2020/10/12 18:25
 */
public class ConfirmConsumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection collection = CollectionUtil.getCollection();
        Channel channel = collection.createChannel();

        String exchangeName = "test_confirm_exchange";
        String exchangeType = "topic";
        String routingKey = "confirm.*";
        String queueName = "test_confirm_queue";
        //
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);
        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);

        System.err.println("消费者------>");
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,consumer);
        while (true){
            //获取消息，没有消息会阻塞
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            System.out.println("收到消息："+new String(delivery.getBody())+"routingKey:"+delivery.getEnvelope().getRoutingKey());
        }
    }
}
