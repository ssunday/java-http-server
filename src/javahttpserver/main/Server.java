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

    public void serveListing(String pathToServe, String previousDirectory, String pathFromBase) throws Exception {
        DirectoryListing directoryListing = new DirectoryListing();
        HTMLDirectoryDisplay directoryDisplay = new HTMLDirectoryDisplay();
        if (directoryListing.isFolder(pathToServe)){
            String[] listing = directoryListing.getListing(pathToServe);
            String html = directoryDisplay.displayListing(listing, pathFromBase);
            String backNavigation = "";
            if (previousDirectory != null) {
                backNavigation = directoryDisplay.displayDirectoryBackNavigation(previousDirectory);
            }
            int contentLength = html.length() + backNavigation.length();
            String header = HTTPResponseHeaders.getDirectoryListingHeader(contentLength);
            output.writeBytes(header);
            output.writeBytes(backNavigation);
            output.writeBytes(html);
            output.flush();
        } else{
            String message = "Not a valid selection.";
            String header = HTTPResponseHeaders.get404Header(message.length());
            output.writeBytes(header);
            output.writeBytes(message);
            output.flush();
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

