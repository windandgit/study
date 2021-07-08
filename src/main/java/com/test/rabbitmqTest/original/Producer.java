package com.test.rabbitmqTest.original;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建链接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        //设置rabbitMq地址
        factory.setHost("localhost");
        //建立到代理服务器的链接(TCP连接)
        Connection connection = factory.newConnection();
        //获得信道(信道复用TCP连接)
        Channel channel = connection.createChannel();
        //声明交换机
        String exchangeName = "hello-exchange";
        /**
         * exchange：交换机名称       type：交换机类型，常见的如fanout、direct、topic
         * durable: 设置是否持久化, true表示持久化, 反之是非持久化, 持久化可以将交换器存盘, 在服务器重启的时候不会丢失相关信息
         *
         * 下面这个方法的作用就是声明一个交换机，并且设置了这个交换机的名称以及其它的属性值，这个方法有很多个重载的方法
         */
        channel.exchangeDeclare(exchangeName,"direct",true);    //"direct"将消息保存到队列的方式
        String routingKey = "hola";

        //发布消息
        byte[] messageBodyBytes = "quit".getBytes();
        /**
         * 下面这个方法主要是对消息进行一些设置，主要讲basicproperties的相关内容
         * 有14个参数：
         *        private String contentType; // 消息的内容类型
         *        private String contentEncoding; // 消息内容的编码
         *        private Map<String, Object> headers; // 消息的头
         *        private Integer deliveryMode; // 消息是否持久化，1为否，2为是
         *        private Integer priority; // 消息的优先级
         *        private String correlationId; // 关联id
         *        private String replyTo; // 消息回复的队列名称
         *        private String expiration; // 消息的有效时间
         *        private String messageId; // 消息的id
         *        private Date timestamp; // 消息的时间戳
         *        private String type; // 类型
         *        private String userId; // 用户id
         *        private String appId; // 应用id
         *        private String clusterId; // 集群id
         */
        channel.basicPublish(exchangeName,routingKey,null,messageBodyBytes);

        channel.close();
        connection.close();
    }
}
