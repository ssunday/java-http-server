public class Main {

    private static int port;
    private static String directory;
    private static String mode;
    private static boolean isRunning = true;

    private static void configuration(String[] args) {
        CommandParser parser = new CommandParser(args);
        port = parser.getPort();
        directory = parser.getDirectory();
        mode = parser.getMode();
    }

    public static void main(String[] args){
        configuration(args);
        final Server server = new Server(mode, port, directory);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                server.endServer();
            }
        });
        while (isRunning) {
            server.runServer();
        }
    }
}
