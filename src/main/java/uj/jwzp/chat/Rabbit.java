package uj.jwzp.chat;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.InputStream;
import java.util.Properties;

public class Rabbit {

    private final Channel channel;
    private String CHANNEL_QUEUE_NAME;

    public Rabbit() throws Exception {
        Properties rabbitmqProp =new Properties();
        InputStream inputStreamRabbitmqProp=Rabbit.class.getResourceAsStream("rabbitmq.properties");
        rabbitmqProp.load(inputStreamRabbitmqProp);
        inputStreamRabbitmqProp.close();

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(rabbitmqProp.getProperty("rabbitmq.host"));
        connectionFactory.setUsername(rabbitmqProp.getProperty("rabbitmq.user"));
        connectionFactory.setPassword(rabbitmqProp.getProperty("rabbitmq.password"));
        connectionFactory.setVirtualHost(rabbitmqProp.getProperty("rabbitmq.user"));
        Connection connection = connectionFactory.newConnection();
        this.channel=connection.createChannel();

        channel.exchangeDeclare(rabbitmqProp.getProperty("rabbitmq.name"), rabbitmqProp.getProperty("rabbitmq.type"));
        CHANNEL_QUEUE_NAME=channel.queueDeclare().getQueue();
        channel.queueBind(CHANNEL_QUEUE_NAME, rabbitmqProp.getProperty("rabbitmq.name"), "");
    }

    public void send(Message message) throws Exception {
        Properties rabbitmqProp =new Properties();
        InputStream inputStreamRabbitmqProp=Rabbit.class.getResourceAsStream("rabbitmq.properties");
        rabbitmqProp.load(inputStreamRabbitmqProp);
        inputStreamRabbitmqProp.close();

        //CHANNEL_QUEUE_NAME=channel.queueDeclare().getQueue();
        //channel.queueBind(CHANNEL_QUEUE_NAME, rabbitmqProp.getProperty("rabbitmq.name"), "");

        Gson gson=new Gson();
        channel.basicPublish(rabbitmqProp.getProperty("rabbitmq.name"), "", null, gson.toJson(message).getBytes("UTF-8"));

        //channel.queueUnbind(CHANNEL_QUEUE_NAME, rabbitmqProp.getProperty("rabbitmq.name"), "");

    }

    public void receive() throws Exception{
        Properties rabbitmqProp =new Properties();
        InputStream inputStreamRabbitmqProp=Rabbit.class.getResourceAsStream("rabbitmq.properties");
        rabbitmqProp.load(inputStreamRabbitmqProp);
        inputStreamRabbitmqProp.close();

        DeliverCallback deliverCallback=(consumerTag, delivery)->{
            String message=new String(delivery.getBody(), "UTF-8");
            Message received=new Gson().fromJson(message, Message.class);

            Properties chatProp=new Properties();
            InputStream inputStreamChatProp=Rabbit.class.getResourceAsStream("chat.properties");
            chatProp.load(inputStreamChatProp);
            inputStreamChatProp.close();

            if(!received.getUser().equals(chatProp.getProperty("user.nick")))
                System.out.println(received.getUser()+": "+received.getMessage());
        };
        //CHANNEL_QUEUE_NAME=channel.queueDeclare().getQueue();
        //channel.queueBind(CHANNEL_QUEUE_NAME, rabbitmqProp.getProperty("rabbitmq.name"), "");

        channel.basicConsume(CHANNEL_QUEUE_NAME, true, deliverCallback, consumerTag -> { });

        //channel.queueUnbind(CHANNEL_QUEUE_NAME, rabbitmqProp.getProperty("rabbitmq.name"), "");

    }
}
