package com.lank.rabbitmq.confirmListener;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 确认消息生产者
 * @author lank
 * @since 2020/10/12 18:18
 */
public class ReturnSender {
    public static void main(String[] args) throws IOException, TimeoutException {

        //Connection collection = CollectionUtil.getCollection();
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.221");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection collection = connectionFactory.newConnection();
        Channel channel = collection.createChannel();

        String exchangeName = "test_return_exchange";
        String routingKey1 = "return.a";
        String routingKey2 = "abc.return";


        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("replyCode:"+replyCode);
                System.err.println("replyText:"+replyText);
                System.err.println("exchange:"+exchange);
                System.err.println("routingKey:"+routingKey);
                System.err.println("body:"+new String(body));
            }
        });

        String msg = "this is a confirm message !!!";
        boolean mandatory = true;
        channel.basicPublish(exchangeName,routingKey1,mandatory,null,msg.getBytes());
        channel.basicPublish(exchangeName,routingKey2,mandatory,null,msg.getBytes());
    }
}
