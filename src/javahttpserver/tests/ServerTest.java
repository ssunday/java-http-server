package javahttpserver.tests;

import javahttpserver.main.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ServerTest {
    private Server server;
    private Socket testSocket = null;

    private static final int TEST_PORT = 8000;

    @Before
    public void initialize() throws Exception{
        server = new Server(TEST_PORT);
    }

    @Test
    public void testUserConnect() throws Exception {
        testSocket = new Socket(InetAddress.getLocalHost(), TEST_PORT);
        assertTrue("User can connect to server", server.userConnect());
        testSocket.close();
    }

    @Test
    public void testServe() throws Exception {
        testSocket = new Socket(InetAddress.getLocalHost(), TEST_PORT);
        server.userConnect();
        server.serve();
        BufferedReader input = new BufferedReader(new InputStreamReader(testSocket.getInputStream()));
        String answer = input.readLine();
        assertNotNull("Socket serves something", answer);
        testSocket.close();
    }

    @After
    public void end() throws Exception{
        server.disconnectServer();
    }


}