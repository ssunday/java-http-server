import Logging.LoggingQueue;
import Routes.DelivererBase;
import Routes.DelivererFactoryBase;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;

public class ServerRunnable implements Runnable{

    private DataOutputStream output;
    private InputStream stream;
    private DelivererFactoryBase delivererFactory;
    private LoggingQueue loggingQueue;

    public ServerRunnable(Socket socket, LoggingQueue loggingQueue, DelivererFactoryBase delivererFactoryBase){
        this.loggingQueue = loggingQueue;
        this.delivererFactory= delivererFactoryBase;
        initializeStreams(socket);
    }

    public void run() {
        String request, responseHeader;
        byte[] bytesToWrite;
        try{
            request = getRequest();
            loggingQueue.addToQueue(request);
            loggingQueue.logQueue();
            DelivererBase deliverer = delivererFactory.getDeliverer(request);
            responseHeader = deliverer.getResponseHeader();
            bytesToWrite = deliverer.getContentBytes();
            output.writeBytes(responseHeader);
            output.write(bytesToWrite);
            output.flush();
        } catch (Exception e){}
    }

    private void initializeStreams(Socket socket){
        try{
            output = new DataOutputStream(socket.getOutputStream());
            stream = socket.getInputStream();
        }catch (Exception e){}
    }

    private String getRequest() throws Exception{
        byte[] data = new byte[18000];
        stream.read(data);
        String request = new String(data).trim();
        return request;
    }
}
