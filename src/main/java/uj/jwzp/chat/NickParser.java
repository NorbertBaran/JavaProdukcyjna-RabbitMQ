package uj.jwzp.chat;

import org.apache.commons.cli.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            if(Files.exists(Path.of(NickParser.class.getResource("chat.properties").toURI())))
                System.out.println("OK");
            else
                System.out.println("NieOK");
            //Files.createFile(Path.of(NickParser.class.getResource("chat.properties").toURI()));
            /*OutputStream output=new FileOutputStream(NickParser.class.getResource("chat.properties").toString());
            Properties properties=new Properties();
            properties.setProperty("user.nick", this.cmd.getOptionValue("nick"));
            properties.store(output, null);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        Properties properties=new Properties();
        properties.put("user.nick", this.cmd.getOptionValue("nick"));
        System.out.println(properties.getProperty("user.nick"));
    }

    public String getNick(){
        return cmd.getOptionValue("nick");
    }
}
