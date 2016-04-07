package javahttpserver.tests;

import javahttpserver.main.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import static org.junit.Assert.assertTrue;

public class ServerTest {
    private Server server;
    private Socket testSocket = null;

    private static final int TEST_PORT = 5000;

    @Before
    public void initialize() throws Exception{
        server = new Server(TEST_PORT);
    }

    @Test
    public void testAcceptConnection() throws Exception {
        testSocket = new Socket("localhost", TEST_PORT);
        assertTrue("Server can accept connection", server.acceptConnection());
        testSocket.close();
    }

    @Test
    public void testServeListingHas200Code() throws Exception {
        testSocket = new Socket("localhost", TEST_PORT);
        server.acceptConnection();
        String[] listing = new String[0];
        server.serveListing(listing);
        BufferedReader input = new BufferedReader(new InputStreamReader(testSocket.getInputStream()));
        String response = input.readLine();
        assertTrue("Socket serves listing with HTTP/1.1 200 code", response.contains("HTTP/1.1 200"));
        testSocket.close();
    }
    

    @After
    public void end() throws Exception{
        server.disconnectServer();
    }


}