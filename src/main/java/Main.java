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
        Server server  = new Server(port, directory);
        try {
            while (true) {
                server.acceptConnection();
                server.serve();
            }
        } finally {
            server.disconnectServer();
        }
    }
}
