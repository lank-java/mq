package com.lank.rabbitmq.helloworld;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 消息发送者
 * @author lank
 * @since 2020/10/10 18:26
 */
public class Sender {

    public static void main(String[] args) throws IOException, TimeoutException {

        //1、创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.221");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        //2、创建连接
        Connection connection = connectionFactory.newConnection();
        //3、创建channel
        Channel channel = connection.createChannel();

        String routingKey = "test001";
        Map<String,Object> headers = new HashMap<>();
        AMQP.BasicProperties props = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .headers(headers)
                .build();
        for (int i=0;i<5;i++){
            String msg = "this is a test message , number:"+i;
            channel.basicPublish("",routingKey,props,msg.getBytes());
        }
        channel.close();
        connection.close();

    }
}
