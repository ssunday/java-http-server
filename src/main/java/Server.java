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

    public void serve(String pathToServe, String baseDirectory) throws Exception {
        String header, contentType;
        int HTTPCode;
        byte[] bytesToWrite;
        ServingBase serving = ServingFactory.getServer(pathToServe, baseDirectory);
        bytesToWrite = serving.getBytes(pathToServe);
        HTTPCode = serving.getHTTPCode();
        contentType = serving.getContentType(pathToServe);
        header = HTTPResponseHeaders.getHTTPHeader(HTTPCode, contentType, bytesToWrite.length);
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

