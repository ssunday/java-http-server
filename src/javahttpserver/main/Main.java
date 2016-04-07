package javahttpserver.main;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        int port;
        String directory;
        CommandParser parser = new CommandParser(args);

        if (parser.hasPort()){
            port = parser.getPort();
        }
        else
        {
            port = 5000;
        }
        if (parser.hasDirectory())
        {
            directory = parser.getDirectory();
        }
        else
        {
            directory = "/";
        }
        String host_name = "localhost";
        Server server = new Server(port);
        User user = new User(host_name, port);
        user.beginConnection();
        server.userConnect();
    }
}
