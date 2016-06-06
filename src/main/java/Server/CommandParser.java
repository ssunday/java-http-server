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
        this.options = Arrays.asList(args);
        this.defaultPort = "5000";
        this.defaultDirectory = System.getProperty("user.dir") + "/cob_spec/public/";
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
