package com.lank.rabbitmq.topic;

import com.lank.rabbitmq.utils.CollectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author lank
 * @since 2020/10/12 17:04
 */
public class TopicExchangeSender {

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = CollectionUtil.getCollection();
        //3、创建channel
        Channel channel = connection.createChannel();

        //声明exchange和routingKey
        String exchangeName = "test_topic_exchange";
        String routingKey1 = "topic.a";
        String routingKey2 = "topic.bc.user";
        String routingKey3 = "topic.b";
        String msg = "this is a direct exchange message ";

        channel.basicPublish(exchangeName,routingKey1,null,msg.getBytes());
        channel.basicPublish(exchangeName,routingKey2,null,msg.getBytes());
        channel.basicPublish(exchangeName,routingKey3,null,msg.getBytes());
    }
}
