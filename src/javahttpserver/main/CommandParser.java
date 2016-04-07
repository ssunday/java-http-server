package javahttpserver.main;

import java.util.Arrays;
import java.util.List;

public class CommandParser {

    private List<String> options;

    public CommandParser(String[] args){

        options = Arrays.asList(args);

    }

    public boolean hasPort()
    {

        return options.contains("-p");
    }

    public boolean hasDirectory()
    {
        return options.contains("-d");
    }

    public int getPort(){
        int flagIndex = options.indexOf("-p");
        String stringPort = options.get(flagIndex+=1);
        int port = Integer.parseInt(stringPort);
        return port;
    }

    public String getDirectory(){

        int flagIndex = options.indexOf("-d");
        return options.get(flagIndex+=1);
    }
}
