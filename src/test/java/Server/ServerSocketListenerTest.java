package Server;

import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ServerSocketListenerTest {


    private final int TEST_PORT = 6000;

    @Test
    public void getSocket() throws Exception{
        ServerSocketListener serverSocketListener = new ServerSocketListener(TEST_PORT);
        Socket testSocket = new Socket("localhost", TEST_PORT);
        assertNotNull("Returns a socket if a test socket has connected", serverSocketListener.getSocket());
        serverSocketListener.disconnectServer();
    }

    @Test
    public void testDisconnectServerEndsConnection() throws Exception {
        int dummyPort = 5001;
        ServerSocketListener server_Listener_end = new ServerSocketListener(dummyPort);
        Socket dummySocket = new Socket("localhost", dummyPort);
        server_Listener_end.disconnectServer();
        assertNull("When server ends connection cannot accept connections", server_Listener_end.getSocket());
        dummySocket.close();
    }

}