package Cobspec;

import Server.DelivererFactoryBase;
import Server.CommandParser;
import Server.Server;

public class Main {

    private static int port;
    private static String directory;
    private static String logName;
    private static DelivererFactoryBase delivererFactory;
    private static boolean isRunning = true;

    private static void configuration(String[] args) {
        CommandParser parser = new CommandParser(args);
        port = parser.getPort();
        directory = parser.getDirectory();
        logName = "logs";
        delivererFactory = new CobspecDelivererFactory(logName, port, directory);
    }

    public static void main(String[] args){
        configuration(args);
        final Server server = new Server(delivererFactory, logName, port);
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

