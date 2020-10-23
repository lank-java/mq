package com.lank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lank
 * @since 2020/10/17 16:15
 */

@SpringBootApplication
public class RabbitMQProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQProducerApplication.class,args);
    }
}
