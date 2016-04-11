package javahttpserver.main;

public class Main {


    private static int port;

    private static String directory;;

    private static void configuration(String[] args) {
        CommandParser parser = new CommandParser(args);
        port = parser.getPort();
        directory = parser.getDirectory();
    }

    public static void main(String[] args) throws Exception {
        configuration(args);
        Server server  = new Server(port);
        FilePaths filePaths = new FilePaths(directory);
        String request, pathToServe, previousPath, pathFromBase;
        try {
            while (true) {
                server.acceptConnection();
                request = server.getRequest();
                pathToServe = filePaths.getPathToServeFromRequest(request);
                previousPath = filePaths.getPreviousPathToLink(pathToServe);
                pathFromBase = filePaths.getPathToLink(pathToServe);
                    server.serveListing(pathToServe, previousPath, pathFromBase);
            }
        } finally {
            server.disconnectServer();
        }
    }
}
