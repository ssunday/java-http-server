package Cobspec;

import Server.DelivererFactoryBase;
import Server.CommandParser;
import Server.Server;

public class Main {

    private static int port;
    private static String directory;
    private static DelivererFactoryBase delivererFactory;
    private static boolean isRunning = true;

    private static void configuration(String[] args) {
        CommandParser parser = new CommandParser(args);
        port = parser.getPort();
        directory = parser.getDirectory();
        delivererFactory = new CobspecDelivererFactory(port, directory);
    }

    public static void main(String[] args){
        configuration(args);
        final Server server = new Server(delivererFactory, port);
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

