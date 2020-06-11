package uj.jwzp.chat;

import org.apache.commons.cli.*;
import java.util.*;

public class Client {

    public static void argsInit(String[] args){
        Options options=new Options();
        Option nick=new Option("n", "nick", true, "Chat Nickname");
        nick.setRequired(true);
        options.addOption(nick);

        CommandLineParser parser=new DefaultParser();
        HelpFormatter formatter=new HelpFormatter();
        CommandLine cmd=null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp("utility-name", options);
            System.exit(0);
        }
        Properties properties=new Properties();
        properties.setProperty("user.nick", cmd.getOptionValue("nick"));
    }

    public static void main(String[] args){
        argsInit(args);

        while(true){

        }
    }
}
