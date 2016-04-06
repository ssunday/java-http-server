package javahttpserver;

import org.apache.commons.cli.*;

public class CommandParser {

    private Options options;
    private CommandLineParser parser;
    private CommandLine cmd;

    public CommandParser(String[] args) throws ParseException {
        options = new Options();
        parser = new DefaultParser();
        setOptions();
        cmd = parser.parse(options, args);
    }

    private void setOptions(){
        options.addOption("p", true, "port of the server");
        options.addOption("d", true, "public directory that server will read from");
    }

    public boolean hasPort()
    {
        return cmd.hasOption("p");
    }

    public boolean hasDirectory()
    {
        return cmd.hasOption("d");
    }

    public int getPort(){
        String portOption = cmd.getOptionValue("p");
        int port = Integer.parseInt(portOption);
        return port;
    }

    public String getDirectory(){
        return cmd.getOptionValue("d");
    }
}
