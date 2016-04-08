package javahttpserver.main;

import java.io.IOException;

public class Main {


    private static int port = 5000;

    private static String directory = "/Users/sarahsunday/Documents/cob_spec/public/";;

    private static void configuration(String[] args) {
        CommandParser parser = new CommandParser(args);

        if (parser.hasPort()){
            port = parser.getPort();
        }

        if (parser.hasDirectory())
        {
            directory = parser.getDirectory();
        }
    }

    public static void main(String[] args) throws IOException {
        configuration(args);
        Server server = new Server(port);
        DirectoryListing directoryListing = new DirectoryListing();
        try {
            while (true) {
                server.acceptConnection();
                server.serveListing(directoryListing.getListing(directory));
            }
        } finally {
            server.disconnectServer();
        }

    }
}
