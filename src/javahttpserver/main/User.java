package javahttpserver.main;

import java.io.IOException;
import java.net.Socket;


public class User {

    private Socket userSocket;
    private String host_name;
    private int port;

    public User(String host_name, int port){
        this.port = port;
        this.host_name = host_name;
    }

    public boolean beginConnection(){
        try {
            userSocket = new Socket(host_name, port);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean endConnection() {

        try {
            userSocket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
