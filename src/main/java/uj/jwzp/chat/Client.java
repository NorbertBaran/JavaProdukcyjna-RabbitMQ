package uj.jwzp.chat;

import org.apache.log4j.BasicConfigurator;

import java.util.*;

public class Client {

    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();

        Rabbit rabbit=new Rabbit();
        new Receiver(rabbit).start();

        Scanner scanner=new Scanner(System.in);
        String nick=new NickParser(args).getNick();

        System.out.println("Welcome "+nick+" in Rabbit Chat");
        while(true){
            String message=scanner.nextLine();
            if(message.equals(":q"))
                System.exit(0);
            Message toSend=new Message(nick, message);
            rabbit.send(toSend);
        }
    }
}
