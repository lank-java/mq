package com.lank.rabbitmq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author lank
 * @since 2020/10/17 17:55
 */
@Component
@Slf4j
public class RabbitReceiver {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-springboot",declare = "true"),
            exchange = @Exchange(name = "exchange-springboot",type = "topic",declare = "true"),
            key = "springboot.*"
    ))
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws IOException {
        log.error("--------------------");
        //获取消息体
        log.info("message:{}",message.getPayload());
        //消息消费ack
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        log.info("headers:{}",message.getHeaders());
        channel.basicAck(deliveryTag,false);
    }
}
