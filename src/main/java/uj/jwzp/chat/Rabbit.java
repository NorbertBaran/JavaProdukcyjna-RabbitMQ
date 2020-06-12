package uj.jwzp.chat;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.InputStream;
import java.time.Instant;
import java.util.Properties;

public class Rabbit {

    private final Properties properties;
    private final Channel channel;
    private final String CHANNEL_QUEUE_NAME;

    public Rabbit() throws Exception {
        properties=new Properties();
        InputStream inputStream=Rabbit.class.getResourceAsStream("rabbitmq.properties");
        properties.load(inputStream);
        inputStream.close();

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(properties.getProperty("rabbitmq.queue.host"));
        connectionFactory.setUsername(properties.getProperty("rabbitmq.queue.user"));
        connectionFactory.setPassword(properties.getProperty("rabbitmq.queue.password"));
        connectionFactory.setVirtualHost(properties.getProperty("rabbitmq.queue.user"));
        Connection connection = connectionFactory.newConnection();
        this.channel=connection.createChannel();
        channel.exchangeDeclare(properties.getProperty("rabbitmq.queue.name"), properties.getProperty("rabbitmq.queue.type"));

        CHANNEL_QUEUE_NAME=channel.queueDeclare().getQueue();
        channel.queueBind(CHANNEL_QUEUE_NAME, properties.getProperty("rabbitmq.queue.name"), "");
    }

    public void send(Message message) throws Exception {
        Gson gson=new Gson();
        channel.basicPublish(properties.getProperty("rabbitmq.queue.name"), "", null, gson.toJson(message).getBytes("UTF-8"));
    }

    public void receive() throws Exception{
        DeliverCallback deliverCallback=(consumerTag, delivery)->{
            String message=new String(delivery.getBody(), "UTF-8");
            Message received=new Gson().fromJson(message, Message.class);
            System.out.println(received.getUser()+": "+received.getMessage());
        };
        channel.basicConsume(CHANNEL_QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
