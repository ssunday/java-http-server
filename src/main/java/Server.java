import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private DataOutputStream output;
    private InputStream stream;
    private RequestLogger logger;

    public Server(int port) throws IOException{
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

    public void serve(String baseDirectory) throws Exception{
        String header, contentType;
        int HTTPCode;
        byte[] bytesToWrite;
        String request = getRequest();
        logger.logRequest(request);
        ServingBase serving = ServingFactory.getServer(request, baseDirectory);
        bytesToWrite = serving.getBytes();
        HTTPCode = serving.getHTTPCode();
        contentType = serving.getContentType();
        String[] methodOptions = serving.getMethodOptions();
        header = HTTPResponseHeaders.getHTTPHeader(HTTPCode, contentType, bytesToWrite.length, methodOptions);
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

