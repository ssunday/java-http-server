import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketListener {

    private ServerSocket serverSocket;
    private Socket socket;

    public ServerSocketListener(int port){
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (Exception e){}
    }

    public Socket getSocket() {
        try {
            socket = serverSocket.accept();
            return socket;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean disconnectServer(){
        try {
            serverSocket.close();
            if (socket != null){
                socket.close();
            }
            return true;
        } catch (IOException eio) {
            return false;
        }
    }

}

