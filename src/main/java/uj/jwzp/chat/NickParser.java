package uj.jwzp.chat;

import org.apache.commons.cli.*;

import java.io.*;
import java.util.Properties;

public class NickParser {

    private CommandLine cmd;

    public NickParser(String[] args){
        Options options=new Options();
        Option nick=new Option("n", "nick", true, "Chat Nickname");
        nick.setRequired(true);
        options.addOption(nick);

        CommandLineParser parser=new DefaultParser();
        HelpFormatter formatter=new HelpFormatter();
        CommandLine cmd=null;

        try {
            this.cmd = parser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp("utility-name", options);
            System.exit(0);
        }

        try {
            Properties properties=new Properties();
            properties.setProperty("user.nick", this.cmd.getOptionValue("nick"));
            OutputStream output=new FileOutputStream(NickParser.class.getResource("chat.properties").getPath());
            properties.store(output, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNick(){
        return cmd.getOptionValue("nick");
    }
}
