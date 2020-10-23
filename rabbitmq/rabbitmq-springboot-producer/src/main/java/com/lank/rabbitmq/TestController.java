package com.lank.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lank
 * @since 2020/10/17 18:13
 */
@RestController
public class TestController {

    @Autowired
    private RabbitSender rabbitSender;

    @RequestMapping("send")
    public String testRabbit(String msg){
        Map<String,Object> prop = new HashMap<>();
        prop.put("lank",123);
        rabbitSender.send(msg,prop);
        return "消息发送成功";
    }
}
