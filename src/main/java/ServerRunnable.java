import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;

public class ServerRunnable implements Runnable{

    private DataOutputStream output;
    private InputStream stream;
    private RequestLogger logger;
    private int port;
    private String baseDirectory;

    public ServerRunnable(Socket socket, int port, String baseDirectory){
        this.logger = new RequestLogger();
        this.port = port;
        this.baseDirectory = baseDirectory;
        initializeStreams(socket);
    }

    private void initializeStreams(Socket socket){
        try{
            this.output = new DataOutputStream(socket.getOutputStream());
            this.stream = socket.getInputStream();
        }catch (Exception e){}
    }

    public void run() {
        String request, responseHeader;
        byte[] bytesToWrite;
        try{
            request = getRequest();
            logger.logRequest(request);
            DelivererBase deliverer = DelivererFactory.getDeliverer(request, port, baseDirectory);
            responseHeader = deliverer.getResponseHeader();
            bytesToWrite = deliverer.getBytes();
            output.writeBytes(responseHeader);
            output.write(bytesToWrite);
            output.flush();
        } catch (Exception e){}
    }

    private String getRequest() throws Exception{
        byte[] data = new byte[18000];
        stream.read(data);
        String request = new String(data).trim();
        return request;
    }
}
