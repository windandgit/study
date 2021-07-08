package com.test.rabbitmqTest.mergeSpringboot;

public class MqListener {

    public void listener(String msg){
        System.out.println("收到的消息为：" + msg);
    }
}
