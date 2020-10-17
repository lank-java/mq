package com.lank.rabbitmq.direct;

import com.lank.rabbitmq.utils.CollectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息接收者
 * @author lank
 * @since 2020/10/10 18:26
 */
public class Receiver {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = CollectionUtil.getCollection();
        //3、创建channel
        Channel channel = connection.createChannel();
        String exchangeName = "test_direct_exchange";
        String exchangeType = "direct";
        String routingKey = "test_direct_routingKey";
        String queueName = "test_direct_queue";
        //
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);
        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,consumer);
        while (true){
            //获取消息，没有消息会阻塞
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            System.out.println("收到消息："+new String(delivery.getBody()));
        }

    }
}
