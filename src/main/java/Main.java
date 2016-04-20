public class Main {

    private static int port;
    private static String directory;
    private static int maxConnections = 10000;

    private static void configuration(String[] args) {
        CommandParser parser = new CommandParser(args);
        port = parser.getPort();
        directory = parser.getDirectory();
    }

    public static void main(String[] args){
        configuration(args);
        int connections = 0;
        Server server = new Server(port, directory);
        try {
            while ((connections++ < maxConnections)) {
                server.run();
            }
        } finally {
            server.end();
        }
    }
}
