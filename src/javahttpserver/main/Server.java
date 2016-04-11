package javahttpserver.main;

import java.io.*;
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
        FileServing fileServing = new FileServing();
        String header;
        byte[] bytesToWrite;
        if (directoryListing.isFolder(pathToServe)){
            HTMLDirectoryDisplay directoryDisplay = new HTMLDirectoryDisplay();
            String[] listing = directoryListing.getListing(pathToServe);
            String html = directoryDisplay.displayListing(listing, pathFromBase);
            String backNavigation = directoryDisplay.displayDirectoryBackNavigation(previousDirectory);
            String content = backNavigation + html;
            bytesToWrite = content.getBytes();
            header = HTTPResponseHeaders.getTextAndHTMLHeader(content.length());
        }
        else if (fileServing.isFile(pathToServe)){
            bytesToWrite = fileServing.getFileBytes(pathToServe);
            int fileByteLength = bytesToWrite.length;
            header = HTTPResponseHeaders.getTextAndHTMLHeader(fileByteLength);
            if (fileServing.isImage(pathToServe)){
                header = HTTPResponseHeaders.getImageHeader(fileByteLength);
            }
        }
        else {
            String message = "Not Found.";
            header = HTTPResponseHeaders.get404Header(message.length());
            bytesToWrite = message.getBytes();
        }
        output.writeBytes(header);
        output.write(bytesToWrite);
        output.flush();
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

