package javahttpserver.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javahttpserver.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

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