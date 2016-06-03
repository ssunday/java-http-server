package Wiki;

import Server.DelivererFactoryBase;
import Server.*;
import Wiki.DelivererSupport.DataType;
import Wiki.DelivererSupport.Postgres;

public class Main {

    private static int port;
    private static String logName;
    private static boolean isRunning = true;
    private static DelivererFactoryBase delivererFactory;

    private static void configuration(String[] args) {
        CommandParser parser = new CommandParser(args);
        logName = "wiki-log";
        port = parser.getPort();
        DataType dataType = new Postgres("posts");
        delivererFactory = new WikiDelivererFactory(logName, dataType, port);
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
