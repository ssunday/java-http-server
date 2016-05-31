package Server;

import Server.Logging.LoggingQueue;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocketListener serverSocketListener;
    private ExecutorService threadPool;
    private DelivererFactoryBase delivererFactory;
    private LoggingQueue loggingQueue;
    private ServerRunnable runnable;
    private Socket socket;

    public Server(DelivererFactoryBase delivererFactory, int port){
        this.delivererFactory = delivererFactory;
        this.serverSocketListener = new ServerSocketListener(port);
        this.threadPool = Executors.newFixedThreadPool(8);
        this.loggingQueue = new LoggingQueue();
    }

    public void runServer(){
        socket = serverSocketListener.getSocket();
        runnable = new ServerRunnable(socket, loggingQueue, delivererFactory);
        threadPool.execute(runnable);
    }

    public void endServer(){
        loggingQueue.logQueue();
        serverSocketListener.disconnectServer();
        threadPool.shutdown();
    }

}
