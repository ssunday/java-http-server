import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

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
        if (HTTPRequestParser.hasContentRange(request)){
            HTTPCode = 206;
            int startPoint = HTTPRequestParser.getContentRangeStart(request);
            int endPoint = HTTPRequestParser.getContentRangeEnd(request);
            if (validByteRange(startPoint)){
                endPoint = (validByteRange(endPoint)) ? endPoint + 1: bytesToWrite.length;
            }
            else{
                if (validByteRange(endPoint)){
                    startPoint = bytesToWrite.length - endPoint;
                    endPoint = bytesToWrite.length;
                }
            }
            bytesToWrite = Arrays.copyOfRange(bytesToWrite, startPoint, endPoint);
        }
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

    private boolean validByteRange(int bytePoint){
       return (bytePoint >= -128 && bytePoint < 127);
    }
}

