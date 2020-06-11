package uj.jwzp.chat;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.time.Instant;

public class Receiver {

    private final static String QUEUE_NAME="chat";
    private final static String QUEUE_HOST="bear.rmq.cloudamqp.com";
    private final static String QUEUE_USER="fwknozud";
    private final static String QUEUE_PASSWORD="omy9Kbzj6Q56GOqb27_iP8MXAK1CVDoL";

    public void main(String[] argv) throws Exception{
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost(QUEUE_HOST);
        factory.setUsername(QUEUE_USER);
        factory.setPassword(QUEUE_PASSWORD);
        factory.setVirtualHost(QUEUE_USER);
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();

        channel.exchangeDeclare(QUEUE_NAME, "fanout");
        String queueName=channel.queueDeclare().getQueue();
        channel.queueBind(queueName, QUEUE_NAME, "");
        System.out.println(" [x] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback=(consumerTag, delivery)->{
            String message=new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '"+message+"', "+ Instant.now());
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
        System.out.println("waiting for messages...");
    }

}
