import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private DataOutputStream output;
    private InputStream stream;
    private RequestLogger logger;
    private int port;
    private String baseDirectory;

    public Server(int port, String directory) throws IOException{
        this.port = port;
        baseDirectory = directory;
        serverSocket = new ServerSocket(port);
        logger = new RequestLogger();
    }

    public boolean acceptConnection(){
        try {
            socket = serverSocket.accept();
            output = new DataOutputStream(socket.getOutputStream());
            stream = socket.getInputStream();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public String getRequest() throws Exception{
        byte[] data = new byte[18000];
        stream.read(data);
        String request = new String(data).trim();
        return request;
    }

    public void serve() throws Exception{
        String request, header;
        byte[] bytesToWrite;
        request = getRequest();
        logger.logRequest(request);
        DelivererBase deliverer = DelivererFactory.getDeliverer(request, port, baseDirectory);
        bytesToWrite = deliverer.getBytes();
        header = deliverer.getResponseHeader();
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

