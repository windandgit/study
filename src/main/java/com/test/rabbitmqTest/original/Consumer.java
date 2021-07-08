package com.test.rabbitmqTest.original;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("localhost");
        //建立到代理服务器的连接
        Connection connection = factory.newConnection();
        //获取信道
        Channel channel = connection.createChannel();
        //声明交换器
        String exchangeName = "hello-exchange";
        channel.exchangeDeclare(exchangeName,"direct",true);     //声明一个交换机并设置交换机的属性值
        //声明队列
        String queueName = channel.queueDeclare().getQueue();
        String bindingKey = "hola";
        //绑定队列，通过键hala将队列和交换器绑定起来
        channel.queueBind(queueName,exchangeName,bindingKey);

//        JSONObject object = new JSONObject();
//        JSON json = new JSON() {
//            @Override
//            public String toString() {
//                return super.toString();
//            }
//        };



        while(true){
            //消费消息
            boolean autoAck = false;
            String consumerTag = "";
            channel.basicConsume(queueName,autoAck,consumerTag,new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String routingKey = envelope.getRoutingKey();
                    String contentType = properties.getContentType();
                    System.out.println("消费的路由键" + routingKey);
                    System.out.println("消费的内容类型" + contentType);
                    long deliveryTag = envelope.getDeliveryTag();
                    //确认消息
                    channel.basicAck(deliveryTag,false);     //手动确认消息，防止消息丢失
                    System.out.println("消费的消息体内容：");
                    String bodyStr = new String(body,"UTF-8");
                    System.out.println(bodyStr);

                }
            });
        }


    }
}
