package com.lank.rabbitmq.cunsumerack;

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
        //1、创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.221");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);
        //2、创建连接
        Connection connection = connectionFactory.newConnection();
        //3、创建channel
        Channel channel = connection.createChannel();

        String queueName = "test001";
        //
        channel.queueDeclare(queueName,false,false,false,null);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        //autoAck设置为false
        channel.basicConsume(queueName,false,consumer);
        while (true){
            //获取消息，没有消息会阻塞
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            System.out.println("收到消息："+new String(delivery.getBody()));
            if ((Integer) delivery.getProperties().getHeaders().get("flag")==0){
                //requeue属性true设置为重回队列,一般设置为false，不然容易一直循环
                channel.basicNack(delivery.getEnvelope().getDeliveryTag(),false,true);
            }else {
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
            }

        }

    }
}
