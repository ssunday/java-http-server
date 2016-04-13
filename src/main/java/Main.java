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
        String request, requestPath;
        try {
            while (true) {
                server.acceptConnection();
                request = server.getRequest();
                requestPath = HTTPRequestParser.getPath(request);
                server.serve(requestPath, directory);
            }
        } finally {
            server.disconnectServer();
        }
    }
}
