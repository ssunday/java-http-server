package javahttpserver.main;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {

        int port = 5000;
        String directory = "/Users/sarahsunday/Documents/cob_spec/public/";
        CommandParser parser = new CommandParser(args);

        if (parser.hasPort()){
            port = parser.getPort();
        }

        if (parser.hasDirectory())
        {
            directory = parser.getDirectory();
        }

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
