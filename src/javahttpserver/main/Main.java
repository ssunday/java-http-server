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

    public static void main(String[] args) throws Exception {
        configuration(args);
        Server server  = new Server(port);
        try {
            while (true) {
                FilePaths filePaths = new FilePaths(directory);
                DirectoryListing directoryListing = new DirectoryListing();
                String previousPath = directory;
                String pathToServe = directory;
                String request;
                server.acceptConnection();
                request = server.getRequest();
                pathToServe = filePaths.getPathToServeFromRequest(request);
                if (filePaths.isFolder(pathToServe)) {
                    String previousPathUp = filePaths.getPathOneLevelUp(pathToServe);
                    previousPath = filePaths.getPathToLink(previousPathUp);
                    String[] listing = directoryListing.getListing(pathToServe);
                    try{
                        server.serveListing(previousPath, listing);
                    } catch (IOException eio){
                        eio.printStackTrace();
                    }
                }
            }
        } finally {
            server.disconnectServer();
        }
    }
}
