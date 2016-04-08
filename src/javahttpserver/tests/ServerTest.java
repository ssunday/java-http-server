package javahttpserver.tests;

import javahttpserver.main.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServerTest {
    private Server server;
    private Socket testSocket = null;

    private static final int TEST_PORT = 5000;

    @Before
    public void initialize() throws Exception{
        server = new Server(TEST_PORT);
        testSocket = new Socket("localhost", TEST_PORT);
    }

    @Test
    public void testAcceptConnection() throws Exception {
        assertTrue("Server can accept connection", server.acceptConnection());
    }

    @Test
    public void testServeListingHas200Code() throws Exception {
        server.acceptConnection();
        String[] listing = new String[0];
        server.serveListing(listing);
        BufferedReader input = new BufferedReader(new InputStreamReader(testSocket.getInputStream()));
        String response = input.readLine();
        assertTrue("Socket serves listing with HTTP/1.1 200 code", response.contains("HTTP/1.1 200"));
    }

    @Test
    public void testDisconnectServerEndsConnection() throws Exception {
        int dummyPort = 5001;
        Server server_end = new Server(dummyPort);
        Socket dummySocket = new Socket("localhost", dummyPort);
        server_end.disconnectServer();
        assertFalse("When server ends connection cannot accept connections", server_end.acceptConnection());
        dummySocket.close();
    }


    @After
    public void end() throws Exception{
        testSocket.close();
        server.disconnectServer();
    }


}