package javahttpserver.main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private DataOutputStream output;

    public Server(int port) throws IOException{
        serverSocket = new ServerSocket(port);
    }

    public boolean acceptConnection() throws IOException{
        try {
            socket = serverSocket.accept();
            output = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String responseHeader(int contentLength){
        String header = "";
        header += "HTTP/1.1 200 OK" + "\r\n";
        header += "Server: Java HTTP Server" + "\r\n";
        header += "Content-Type: text/html" + "\r\n";
        header += "Content-Length: " + contentLength + "\r\n";
        header += "\r\n";
        return header;
    }

    public void serveListing(String[] listing) throws IOException {
        HTMLDirectoryDisplay directoryDisplay = new HTMLDirectoryDisplay();
        String html = directoryDisplay.displayListing(listing);
        int contentLength = html.length();
        output.writeBytes(responseHeader(contentLength));
        output.writeBytes(html);
        output.flush();
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

