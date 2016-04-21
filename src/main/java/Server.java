import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocketListener serverSocketListener;
    private int port;
    private String baseDirectory;
    private ExecutorService threadPool;

    public Server(int port, String baseDirectory){
        this.serverSocketListener = new ServerSocketListener(port);
        this.port = port;
        this.baseDirectory = baseDirectory;
        this.threadPool = Executors.newFixedThreadPool(8);
    }

    public void start(){
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                serverSocketListener.disconnectServer();
                threadPool.shutdown();
            }
        });
        Socket socket = serverSocketListener.getSocket();
        ServerRunnable runnable = new ServerRunnable(socket, port, baseDirectory);
        threadPool.execute(runnable);
    }
}
