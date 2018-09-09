package com.forezp.rabbtimq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 * @author gongxb
 * @date 2018/9/9
 * @desc
 * @return
 */
@Component

public class MqReceiver {

    /**
     * 一个简单的接收字符串的监听器
     *
     * @param hello
     */
    @RabbitListener(queues = "hello")
    public void process(String hello) {
        for (int i = 0; i < 4; i++) {
            System.out.println("Receiver  : " + i + hello);
        }
    }

    @RabbitListener(queues = "hello")
    public void process2(byte[] user) throws Exception {
        User user1 = (User) getObjectFromBytes(user);
        System.out.println("Receiver  : " + user1);
    }

    //字节码转化为对象
    public Object getObjectFromBytes(byte[] objBytes) throws Exception {
        if (objBytes == null || objBytes.length == 0) {
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();
    }


    @RabbitListener(queues = "fanout.B")
    public void processFanout1(String message) {
        System.out.println("fanout Receiver B: " + message);
    }

    @RabbitListener(queues = "fanout.A")

    public void processFanout2(String message) {
        System.out.println("fanout Receiver A: " + message);
    }

    @RabbitListener(queues = "fanout.C")
    public void processFanout3(String message) {
        System.out.println("fanout Receiver C: " + message);
    }

    @RabbitListener(queues = "topic.D")
    public void processTopic1(String message) {
        System.out.println("topic Receiver B: " + message);
    }

    @RabbitListener(queues = "topic.E")
    public void processTopic2(String message) {
        System.out.println("topic Receiver A: " + message);
    }


}
