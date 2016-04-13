import java.util.Arrays;
import java.util.List;

public class CommandParser {

    private final String PORT_KEYWORD = "-p";

    private final String DIRECTORY_KEYWORD = "-d";

    private List<String> options;

    private int defaultPort;

    private String defaultDirectory;

    public CommandParser(String[] args) {
        options = Arrays.asList(args);
        defaultPort = 5000;
        defaultDirectory = System.getProperty("user.dir") + "/cob_spec/public/";
    }

    public boolean hasPort() {
        return options.contains(PORT_KEYWORD);
    }

    public boolean hasDirectory()
    {
        return options.contains(DIRECTORY_KEYWORD);
    }

    public int getPort(){
        int port = defaultPort;
        if (hasPort()){
            String stringPort = getOptionValue(PORT_KEYWORD);
            port = Integer.parseInt(stringPort);
        }
        return port;
    }

    public String getDirectory(){
        String directory = defaultDirectory;
        if (hasDirectory()){
            directory = getOptionValue(DIRECTORY_KEYWORD);
        }
        return directory;
    }

    private String getOptionValue(String keyword){
        int flagIndex = options.indexOf(keyword);
        String value = options.get(flagIndex+1);
        return value;
    }
}
