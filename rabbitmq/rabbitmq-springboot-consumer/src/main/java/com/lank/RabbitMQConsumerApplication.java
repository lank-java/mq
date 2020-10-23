package com.lank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lank
 * @since 2020/10/17 17:54
 */
@SpringBootApplication
public class RabbitMQConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQConsumerApplication.class,args);
    }
}
