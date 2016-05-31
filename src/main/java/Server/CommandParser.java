package Server;

import java.util.Arrays;
import java.util.List;

public class CommandParser {

    private final String PORT_KEYWORD = "-p";
    private final String DIRECTORY_KEYWORD = "-d";

    private List<String> options;
    private String defaultPort;
    private String defaultDirectory;

    public CommandParser(String[] args) {
        options = Arrays.asList(args);
        defaultPort = "5000";
        defaultDirectory = System.getProperty("user.dir") + "/cob_spec/public/";
    }

    public int getPort(){
        String port = getOption(PORT_KEYWORD, defaultPort);
        return Integer.parseInt(port);
    }

    public String getDirectory(){
        return getOption(DIRECTORY_KEYWORD, defaultDirectory);
    }

    private String getOption(String keyword, String defaultOption){
        String option = hasKeyword(keyword) ? getOptionValue(keyword) : defaultOption;
        return option;
    }

    private boolean hasKeyword(String keyword) {
        return options.contains(keyword);
    }

    private String getOptionValue(String keyword){
        int flagIndex = options.indexOf(keyword);
        String value = options.get(flagIndex+1);
        return value;
    }
}
