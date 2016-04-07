package javahttpserver.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter output;

    public Server(int port) throws IOException{
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(100);
    }

    public boolean userConnect() throws IOException{
        try {
            while (true) {
                socket = serverSocket.accept();
                output = new PrintWriter(socket.getOutputStream(), true);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void serve() throws IOException {
        output.println("Hello World");
    }

    public boolean disconnectServer() {
        try {
            serverSocket.close();
            if (socket != null) {
                disconnectUser();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void disconnectUser() throws IOException {
        socket.close();
    }
}

