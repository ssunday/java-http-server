

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import static org.junit.Assert.*;

public class ServerTest {

    private Server server;
    private Socket testSocket;

    private static final int TEST_PORT = 6000;

    @Before
    public void setUp() throws Exception{
        server = new Server(TEST_PORT);
        testSocket = new Socket("localhost", TEST_PORT);
    }

    @Test
    public void testAcceptConnection() throws Exception {
        assertTrue("Server can accept connection", server.acceptConnection());
    }

    @Test
    public void testGetRequest() throws Exception {
        server.acceptConnection();
        DataOutputStream output = new DataOutputStream(testSocket.getOutputStream());
        String get = "GET / HTTP/1.1";
        String request =  get + "\r\n" +
                "Host: " + "http://localhost:" + "\r\n" +
                "Connection: close\r\n\r\n";
        output.writeBytes(request);
        output.flush();
        assertEquals("Server can get entire socket request", request.trim(), server.getRequest());
    }

    @Test
    public void testGetRequestWithParams() throws Exception {
        server.acceptConnection();
        DataOutputStream output = new DataOutputStream(testSocket.getOutputStream());
        String get = "POST / HTTP/1.1";
        String request =  get + "\r\n" +
                "Host: " + "http://localhost:" + "\r\n" + "\r\n" +
                "var1=foo";
        output.writeBytes(request);
        output.flush();
        assertEquals("Server can get entire socket request with params", request.trim(), server.getRequest());
    }

    @Test
    public void testServeHas200Code() throws Exception {
        server.acceptConnection();
        String directory = System.getProperty("user.dir");
        DataOutputStream output = new DataOutputStream(testSocket.getOutputStream());
        String get = "GET / HTTP/1.1";
        String request =  get + "\r\n" +
                "Host: " + "http://localhost:" + "\r\n" +
                "Connection: close\r\n\r\n";
        output.writeBytes(request);
        output.flush();
        server.serve(directory);
        BufferedReader input = new BufferedReader(new InputStreamReader(testSocket.getInputStream()));
        String response = input.readLine();
        assertTrue("Socket serves listing with HTTP/1.1 200 code", response.contains("HTTP/1.1 200"));
    }

    @Test
    public void testServeHas404CodeWithNonExistentDirectory() throws Exception {
        server.acceptConnection();
        String directory = System.getProperty("user.dir");
        DataOutputStream output = new DataOutputStream(testSocket.getOutputStream());
        String get = "GET /foobar HTTP/1.1";
        String request =  get + "\r\n" +
                "Host: " + "http://localhost:" + "\r\n" +
                "Connection: close\r\n\r\n";
        output.writeBytes(request);
        output.flush();
        server.serve(directory);
        BufferedReader input = new BufferedReader(new InputStreamReader(testSocket.getInputStream()));
        String response = input.readLine();
        assertTrue("Socket serves non-existent directory listing with 404 code", response.contains("404"));
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