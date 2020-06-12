package uj.jwzp.chat;

import java.io.InputStream;
import java.util.*;

public class Client {

    public static void main(String[] args) throws Exception {
        Rabbit chat=new Rabbit();
        new Receiver(chat).start();

        Scanner scanner=new Scanner(System.in);
        String nick=new NickParser(args).getNick();

        Properties properties=new Properties();
        InputStream inputStream= Client.class.getResourceAsStream("rabbitmq.properties");
        properties.load(inputStream);
        inputStream.close();
        System.out.println(properties.getProperty("user.nick"));
        while(true){
            String message=scanner.nextLine();
            Message toSend=new Message(nick, message);
            chat.send(toSend);
        }
    }
}
