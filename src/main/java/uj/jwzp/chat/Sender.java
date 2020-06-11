package uj.jwzp.chat;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Properties;

public class Sender {
    private final static String QUEUE_NAME="chat";
    private final static String QUEUE_HOST="bear.rmq.cloudamqp.com";
    private final static String QUEUE_USER="fwknozud";
    private final static String QUEUE_PASSWORD="omy9Kbzj6Q56GOqb27_iP8MXAK1CVDoL";

    private final static Properties properties=new Properties();

    public void main(String[] argv) throws Exception {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost(properties.getProperty("rabbitmq.queue.name"));
        factory.setUsername(properties.getProperty("rabbitmq.queue.user"));
        factory.setPassword(properties.getProperty("rabbitmq.queue.password"));
        factory.setVirtualHost(properties.getProperty("rabbitmq.queue.user"));
        try(Connection connection=factory.newConnection(); Channel channel=connection.createChannel()){
            channel.exchangeDeclare(QUEUE_NAME, properties.getProperty("rabbitmq.queue.type"));
            Gson gson=new Gson();
            String content="Test";
            Message m=new Message("Norbert", content);
            channel.basicPublish(QUEUE_NAME, "", null, gson.toJson(m).getBytes("UTF-8"));
            System.out.println(" [x] Sent '"+m+"'");
        }
    }

}
