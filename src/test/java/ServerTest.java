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

    private Server server;
    private int TEST_PORT;
    private String TEST_DIRECTORY;

    @Before
    public void setUp() throws Exception {
        TEST_PORT = 6000;
        TEST_DIRECTORY = FileTestingUtilities.testDirectory;
        FileTestingUtilities.makePath(FileTestingUtilities.testDirectory);
        server = new Server(TEST_PORT, TEST_DIRECTORY);
    }

    @Test
    public void testRunCanWithstand100Requests() throws Exception{
        runIterationNTimes(100);
    }

    @Test
    public void testRunCanWithstand500Requests() throws Exception{
        runIterationNTimes(500);
    }

    @Test
    public void testRunCanWithstand1000Requests() throws Exception{
        runIterationNTimes(1000);
    }

    @Test
    public void testRunCanWithstand1500Requests() throws Exception{
        runIterationNTimes(1500);
    }

    @Test
    public void testRunCanWithstand3000Requests() throws Exception{
        runIterationNTimes(3000);
    }

    private void runIterationNTimes(int n) throws Exception{
        Socket socket;
        BufferedReader input;
        DataOutputStream output;
        for (int i = 0; i < n; i++){
            socket = new Socket("localhost", TEST_PORT);
            output = new DataOutputStream(socket.getOutputStream());
            String get = "GET / HTTP/1.1" + "\r\n";
            String request =  get + "\r\n" +
                    "Host: " + "http://localhost:" + "\r\n" +
                    "Connection: close\r\n\r\n";
            output.writeBytes(request);
            output.flush();
            server.run();
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = input.readLine();
            assertThat(response, containsString("HTTP/1.1 200"));
            socket.close();
            input.close();
            output.close();
        }
    }

    @After
    public void tearDown(){
        server.end();
    }

}