package com.lank.rabbitmq.direct;


import com.lank.rabbitmq.utils.CollectionUtil;
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

        Connection connection = CollectionUtil.getCollection();
        //3、创建channel
        Channel channel = connection.createChannel();

        //声明exchange和routingKey
        String exchangeName = "test_direct_exchange";
        String routingKey = "test_direct_routingKey";

        String msg = "this is a direct exchange message ";
        channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());

    }
}
