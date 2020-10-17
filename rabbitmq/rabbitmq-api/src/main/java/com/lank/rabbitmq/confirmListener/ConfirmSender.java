package com.lank.rabbitmq.confirmListener;

import com.lank.rabbitmq.utils.CollectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 确认消息生产者
 * @author lank
 * @since 2020/10/12 18:18
 */
public class ConfirmSender {
    public static void main(String[] args) throws IOException, TimeoutException {

        //Connection collection = CollectionUtil.getCollection();
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.221");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection collection = connectionFactory.newConnection();
        Channel channel = collection.createChannel();

        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.a";

        //开启消息确认
        channel.confirmSelect();

        channel.addConfirmListener(new ConfirmListener() {
            // 消息成功ack
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.err.println("-----消息发送成功-----");
            }
            //消息失败ack
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.err.println("-----消息发送失败-----");
            }
        });

        String msg = "this is a confirm message !!!";
        channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());
    }
}
