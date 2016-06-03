package Server;

import Cobspec.CobspecDelivererFactory;
import TestingSupport.FileTestingUtilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServerTest {

    private int TEST_PORT = 6000;
    private String TEST_DIRECTORY = FileTestingUtilities.testDirectory;
    private String TEST_LOG = "test-cobspec-log";

    @Before
    public void setUp() throws Exception {
        FileTestingUtilities.makePath(TEST_DIRECTORY);
    }

    @Test
    public void testRunCanWithstand200Requests() throws Exception{
        Thread.yield();
        runIterationNTimes(200);
    }

    private void runIterationNTimes(int n) throws Exception{
        Socket socket;
        BufferedReader input;
        DataOutputStream output;
        Server server = new Server(new CobspecDelivererFactory(TEST_LOG, TEST_PORT, TEST_DIRECTORY), TEST_LOG, TEST_PORT);
        for (int i = 0; i < n; i++){
            socket = new Socket("localhost", TEST_PORT);
            output = new DataOutputStream(socket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String request =  "HEAD / HTTP/1.1" + "\r\n" +
                    "Connection: close\r\n\r\n";
            output.writeBytes(request);
            output.flush();
            server.runServer();
            String response = input.readLine();
            assertThat(response, containsString("HTTP/1.1 200"));
            socket.close();
            input.close();
            output.close();
        }
        server.endServer();
    }

    @After
    public void tearDown() throws Exception{
        FileTestingUtilities.clearPath(TEST_DIRECTORY);
        FileTestingUtilities.clearPath(TEST_LOG);
    }

}