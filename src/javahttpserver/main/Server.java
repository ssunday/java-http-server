package javahttpserver.main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private DataOutputStream output;
    private BufferedReader reader;

    public Server(int port) throws IOException{
        serverSocket = new ServerSocket(port);
    }

    public boolean acceptConnection() {
        try {
            socket = serverSocket.accept();
            output = new DataOutputStream(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public String getRequest() throws Exception {
        return reader.readLine();
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

    public void serveListing(String previousDirectory, String[] listing) throws IOException {
        HTMLDirectoryDisplay directoryDisplay = new HTMLDirectoryDisplay();
        String html = directoryDisplay.displayListing(listing);
        String backNavigation = "";
        if (previousDirectory != null) {
            backNavigation = directoryDisplay.displayDirectoryBackNavigation(previousDirectory);
        }
        int contentLength = html.length() + backNavigation.length();
        String header = responseHeader(contentLength);
        try {
            output.writeBytes(header);
            output.writeBytes(backNavigation);
            output.writeBytes(html);
            output.flush();
        } catch (Exception e){
            System.out.println("FAIL");
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

