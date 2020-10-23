package com.lank.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * @author lank
 * @since 2020/10/17 17:05
 */

@Component
@Slf4j
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        /**
         * 确认消息回调监听方法
         * @param correlationData 作为消息的唯一标识
         * @param ack 是否落盘成功
         * @param cause 失败原因
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            log.info("correlationData:{},ack:{},cause:{}",correlationData,ack,cause);
        }
    };

    /**
     * 消息发送前对消息的操作
     */
    final MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
        @Override
        public org.springframework.amqp.core.Message postProcessMessage(org.springframework.amqp.core.Message message) throws AmqpException {
            log.info("messagePostProcessor:{}",message);
            return message;
        }
    };


    /**
     * 发送消息的方法
     * @param message 消息对象
     * @param properties 消息的配置，如过期时间
     */
    public void send(Object message, Map<String,Object> properties){

        MessageHeaders messageHeaders = new MessageHeaders(properties);
        Message<Object> msg = MessageBuilder.createMessage(message, messageHeaders);
        //确认消息设置回调
        rabbitTemplate.setConfirmCallback(confirmCallback);
        //生产发送该条消息唯一标识
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("exchange-springboot","springboot.rabbit",msg,messagePostProcessor,correlationData);
    }
}
