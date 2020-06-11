package uj.jwzp.chat;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.time.Instant;
import java.util.Properties;

public class RabbitChat {

    private static final Properties properties=new Properties();
    private final Channel channel;

    public RabbitChat() throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(properties.getProperty("rabbitmq.queue.host"));
        connectionFactory.setUsername(properties.getProperty("rabbitmq.queue.user"));
        connectionFactory.setPassword(properties.getProperty("rabbitmq.queue.password"));
        connectionFactory.setVirtualHost(properties.getProperty("rabbitmq.queue.user"));
        Connection connection = connectionFactory.newConnection();
        this.channel= connection.createChannel();
        channel.exchangeDeclare(properties.getProperty("rabbitmq.queue.name"), properties.getProperty("rabbitmq.queue.type"));
    }

    public void send(String message) throws Exception {
        Gson gson=new Gson();
        Message m=new Message(properties.getProperty("user.nick"), message);
        channel.basicPublish(properties.getProperty("rabbitmq.queue.name"), "", null, gson.toJson(m).getBytes("UTF-8"));
        System.out.println(" [x] Sent '"+m+"'");
    }

    public void receive() throws Exception{
        String CHANNEL_QUEUE_NAME=channel.queueDeclare().getQueue();
        channel.queueBind(CHANNEL_QUEUE_NAME, properties.getProperty("rabbit.queue.name"), "");
        System.out.println(" [x] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback=(consumerTag, delivery)->{
            String message=new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '"+message+"', "+ Instant.now());
        };

        channel.basicConsume(CHANNEL_QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        System.out.println("waiting for messages...");
    }
}
