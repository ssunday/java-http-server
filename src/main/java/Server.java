import Logging.LoggingQueue;
import Server.ServerSocketListener;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocketListener serverSocketListener;
    private int port;
    private String baseDirectory;
    private ExecutorService threadPool;
    private LoggingQueue loggingQueue;
    private ServerRunnable runnable;
    private Socket socket;

    public Server(int port, String baseDirectory){
        this.port = port;
        this.baseDirectory = baseDirectory;
        this.serverSocketListener = new ServerSocketListener(port);
        this.threadPool = Executors.newFixedThreadPool(8);
        this.loggingQueue = new LoggingQueue();
    }

    public void runServer(){
        socket = serverSocketListener.getSocket();
        runnable = new ServerRunnable(socket, loggingQueue, port, baseDirectory);
        threadPool.execute(runnable);
    }

    public void endServer(){
        loggingQueue.logQueue();
        serverSocketListener.disconnectServer();
        threadPool.shutdown();
    }
}
