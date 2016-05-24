import Routes.Cobspec.CobspecDelivererFactory;
import Routes.Cobspec.Deliverer.DirectoryDeliverer;
import TestingSupport.FileTestingUtilities;
import TestingSupport.MockLoggingQueue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServerRunnableTest {

    private int TEST_PORT;
    private String TEST_DIRECTORY;
    private ServerRunnable serverRunnable;
    private DataOutputStream output;
    private InputStream stream;
    private BufferedReader input;
    private ServerSocket serverSocket;
    private Socket testSocket;

    @Before
    public void setUp() throws Exception{
        TEST_PORT = 6000;
        TEST_DIRECTORY = FileTestingUtilities.testDirectory;
        FileTestingUtilities.makePath(FileTestingUtilities.testDirectory);
        serverSocket = new ServerSocket(TEST_PORT);
        testSocket = new Socket("localhost", TEST_PORT);
        output = new DataOutputStream(testSocket.getOutputStream());
        stream = testSocket.getInputStream();
        input = new BufferedReader(new InputStreamReader(stream));
        serverRunnable = new ServerRunnable(serverSocket.accept(), new MockLoggingQueue(), new CobspecDelivererFactory(TEST_PORT, TEST_DIRECTORY));
    }

    @Test
    public void testServeHas200Code() throws Exception {
        String get = "GET / HTTP/1.1";
        String request =  get + "\r\n" +
                "Host: " + "http://localhost:" + "\r\n" +
                "Connection: close\r\n\r\n";
        output.writeBytes(request);
        output.flush();
        serverRunnable.run();
        String response = input.readLine();
        assertThat(response, containsString("HTTP/1.1 200"));
    }

    @Test
    public void testServeHas404CodeWithNonExistentDirectory() throws Exception {
        String get = "GET /foobar HTTP/1.1";
        String request =  get + "\r\n" +
                "Host: " + "http://localhost:" + "\r\n" +
                "Connection: close\r\n\r\n";
        output.writeBytes(request);
        output.flush();
        serverRunnable.run();
        String response = input.readLine();
        assertThat(response, containsString("HTTP/1.1 404"));
    }

    @Test
    public void testServerHas206WhenContentRangeExists() throws Exception{
        String get = "GET / HTTP/1.1";
        String request =  get + "\r\n" +
                "Host: " + "http://localhost:" + "\r\n" +
                "Range: bytes=0-40" + "\r\n" +
                "Connection: close\r\n\r\n";
        output.writeBytes(request);
        output.flush();
        serverRunnable.run();
        String response = input.readLine();
        assertThat(response, containsString("HTTP/1.1 206"));
    }

    @Test
    public void testServerHasPartialContentWhenRangeIsProvided() throws Exception{
        String get = "GET / HTTP/1.1";
        String request =  get + "\r\n" +
                "Host: " + "http://localhost:" + "\r\n" +
                "Range: bytes=0-20" + "\r\n" +
                "Connection: close\r\n\r\n";
        output.writeBytes(request);
        output.flush();
        serverRunnable.run();
        byte[] data = new byte[18000];
        stream.read(data);
        String response = new String(data).trim();
        DirectoryDeliverer deliver = new DirectoryDeliverer("/", TEST_DIRECTORY, "/", "GET");
        byte[] contentBytes = deliver.getContentBytes();
        byte[] truncated = Arrays.copyOfRange(contentBytes, 0, 20);
        assertThat(response, containsString(Integer.toString(truncated.length)));
    }

    @Test
    public void testServerHasPartialContentWhenEndRangeIsProvided() throws Exception{
        String get = "GET / HTTP/1.1";
        String request =  get + "\r\n" +
                "Host: " + "http://localhost:" + "\r\n" +
                "Range: bytes=-40" + "\r\n" +
                "Connection: close\r\n\r\n";
        output.writeBytes(request);
        output.flush();
        serverRunnable.run();
        byte[] data = new byte[18000];
        stream.read(data);
        String response = new String(data).trim();
        DirectoryDeliverer deliver = new DirectoryDeliverer("/", TEST_DIRECTORY, "/", "GET");
        byte[] contentBytes = deliver.getContentBytes();
        byte[] truncated = Arrays.copyOfRange(contentBytes, 0, 40);
        assertThat(response, containsString(Integer.toString(truncated.length)));
    }

    @Test
    public void testServerHasPartialContentWhenStartRangeIsProvided() throws Exception{
        String get = "GET / HTTP/1.1";
        String request =  get + "\r\n" +
                "Host: " + "http://localhost:" + "\r\n" +
                "Range: bytes=5-" + "\r\n" +
                "Connection: close\r\n\r\n";
        output.writeBytes(request);
        output.flush();
        serverRunnable.run();
        byte[] data = new byte[18000];
        stream.read(data);
        String response = new String(data).trim();
        DirectoryDeliverer deliver = new DirectoryDeliverer("/", TEST_DIRECTORY, "/",  "GET");
        byte[] contentBytes = deliver.getContentBytes();
        byte[] truncated = Arrays.copyOfRange(contentBytes, 5, contentBytes.length);
        assertThat(response, containsString(Integer.toString(truncated.length)));
    }

    @Test
    public void testServerRedirectHasLocationFound() throws Exception{
        String get = "GET /redirect HTTP/1.1";
        String request =  get + "\r\n" +
                "Host: " + "http://localhost:" + "\r\n" +
                "Connection: close\r\n\r\n";
        output.writeBytes(request);
        output.flush();
        serverRunnable.run();
        byte[] data = new byte[18000];
        stream.read(data);
        String response = new String(data).trim();
        assertThat(response, containsString("Location: http://localhost:"+TEST_PORT+"/"));
    }

    @Test
    public void testServerResponseDoesNotHaveAllowWhenRequestTypeIsNotOptions() throws Exception{
        String get = "GET / HTTP/1.1";
        String request =  get + "\r\n" +
                "Host: " + "http://localhost:" + "\r\n" +
                "Connection: close\r\n\r\n";
        output.writeBytes(request);
        output.flush();
        serverRunnable.run();
        byte[] data = new byte[18000];
        stream.read(data);
        String response = new String(data).trim();
        assertFalse("Response does not have an allow field with get request", response.contains("Allow: "));
    }

    @Test
    public void testServerResponseHasAllowLineWhenRequestTypeIsOptions() throws Exception{
        String get = "OPTIONS / HTTP/1.1";
        String request =  get + "\r\n" +
                "Host: " + "http://localhost:" + "\r\n" +
                "Connection: close\r\n\r\n";
        output.writeBytes(request);
        output.flush();
        serverRunnable.run();
        byte[] data = new byte[18000];
        stream.read(data);
        String response = new String(data).trim();
        assertThat(response, containsString("Allow: "));
    }

    @After
    public void tearDown() throws Exception{
        FileTestingUtilities.clearPath(TEST_DIRECTORY);
        testSocket.close();
        serverSocket.close();
    }
}