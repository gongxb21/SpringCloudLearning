package com.forezp.rabbtimq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author gongxb
 * @date 2018/9/9
 * @desc
 * @return
 */
@RestController
@RequestMapping(value = "/hello")
public class MqController {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * 一个简单的发送消息
     */
    @RequestMapping(value = "/send")
    public void send() {
        String context = "hello world this is a simple mq test";
        System.out.println("context=" + context);
        rabbitTemplate.convertAndSend("hello", context);
    }

    /**
     * 一个简单的消息发送，循环发送
     */
    @RequestMapping(value = "/send/one2many")
    public void send2() {
        for (int i = 0; i < 100; i++) {
            String context = "hello world this is a simple mq test";
            System.out.println("context=" + context + i);
            rabbitTemplate.convertAndSend("hello", context + i);
        }
    }

    /**
     * 发送一个对象
     */
    @RequestMapping(value = "/send/user/one2many")
    public void send3() throws Exception {
        User user = new User();
        user.setName("helloworld");
        user.setAge("1");
        byte[] bytes = getBytesFromObject(user);
        System.out.println("context=" + bytes);
        rabbitTemplate.convertAndSend("hello", bytes);
    }

    //对象转化为字节码
    public byte[] getBytesFromObject(Serializable obj) throws Exception {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        return bo.toByteArray();
    }

    /**
     * 广播的队列消息
     */
    @RequestMapping(value = "/send/fanout")
    public void send4() {
        String msg = "I am fanout.mesaage msg======";
        System.out.println("msg=" + msg);
        this.rabbitTemplate.convertAndSend("fanoutExchange", "", msg);
    }

    /**
     * 带主体的队列消息
     */
    @RequestMapping(value = "/send/topic")
    public void send5() {
        String context = "I am topic.mesaage msg======";
        System.out.println("msg=" + context);
        this.rabbitTemplate.convertAndSend("topicExchange", "topic.message", context);
        this.rabbitTemplate.convertAndSend("topicExchange", "topic.messages", context);
    }

}
