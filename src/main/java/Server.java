import java.net.Socket;

public class Server {

    private ServerSocketListener serverSocketListener;
    private int port;
    private String baseDirectory;

    public Server(int port, String baseDirectory){
        this.serverSocketListener = new ServerSocketListener(port);
        this.port = port;
        this.baseDirectory = baseDirectory;
    }

    public void run(){
        Socket socket = serverSocketListener.getSocket();
        ServerRunnable runnable = new ServerRunnable(socket, port, baseDirectory);
        Thread t = new Thread(runnable);
        t.start();
    }

    public void end(){
        serverSocketListener.disconnectServer();
    }
}
