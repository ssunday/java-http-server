import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DelivererFactoryTest {

    private static final int TEST_PORT = 6000;
    @Before
    public void setUp() throws Exception{
        FileTestingUtilities.makePath(FileTestingUtilities.testDirectory);
    }

    @Test
    public void testGetServerReturnsDirectoryDeliverer() throws Exception {
        String path = FileTestingUtilities.testDirectory + "single/";
        FileTestingUtilities.makePath(path);
        String request = "GET /single/ HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase server = DelivererFactory.getDeliverer(request, TEST_PORT, FileTestingUtilities.testDirectory);
        assertTrue("Returns Directory object when passed in a directory", server instanceof DirectoryDeliverer);
        FileTestingUtilities.clearPath(path);
    }

    @Test
    public void testGetServerReturnsFileDeliverer() throws Exception {
        String singleFile = FileTestingUtilities.testDirectory + "single.txt";
        FileTestingUtilities.makeFile(singleFile);
        String request = "GET /single.txt HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase server = DelivererFactory.getDeliverer(request, TEST_PORT, FileTestingUtilities.testDirectory);
        assertTrue("Returns File Deliverer object when file passed in", server instanceof FileDeliverer);
        FileTestingUtilities.clearPath(singleFile);
    }

    @Test
    public void testGetServerReturnsParameterDeliverer() throws Exception {
        String request = "GET /parameters?var=blarg HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase server = DelivererFactory.getDeliverer(request, TEST_PORT, "/");
        assertTrue("Returns Parameter Deliverer object when path with parameters passed in", server instanceof ParameterDeliverer);
    }

    @Test
    public void testGetServerReturnsFormDeliverer() throws Exception {
        String request = "GET /form HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase server = DelivererFactory.getDeliverer(request, TEST_PORT, "/");
        assertTrue("Returns form Deliverer object when path with form is passed in", server instanceof FormDeliverer);
    }

    @Test
    public void testGetServerReturnsLogDeliverer() throws Exception{
        String request = "GET /logs HTTP/1.1\r\n" +
                "Authorization: Basic c29tZW9uZTpwYXNzd29yZA==\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase server = DelivererFactory.getDeliverer(request, TEST_PORT, "/");
        assertTrue("Returns log Deliverer when logs route is passed in", server instanceof LogDeliverer);
    }

    @Test
    public void testGetServerReturnsPartialContentDeliverer() throws Exception{
        String request = "GET /logs HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Range: bytes=0-20" + "\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase server = DelivererFactory.getDeliverer(request, TEST_PORT, "/");
        assertTrue("Returns partial content deliverer when range passed in", server instanceof PartialContentDeliverer);
    }

    @Test
    public void testGetServerReturnsNotFoundDeliverer() throws Exception {
        String request = "GET /notfound.txt HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase server = DelivererFactory.getDeliverer(request,TEST_PORT, "/");
        assertTrue("Returns not found server when non-existent file is passed in", server instanceof NotFoundDeliverer);
    }

    @Test
    public void testGetServerReturnsMethodOptionsDeliverer() throws Exception{
        String request = "GET /method_options HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase server = DelivererFactory.getDeliverer(request, TEST_PORT, "/");
        assertTrue("Returns method option deliverer when that route is passed in", server instanceof MethodOptionDeliverer);
    }
    
    @Test
    public void testGetServerReturnsRedirectDeliverer(){
        String request = "GET /redirect HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase server = DelivererFactory.getDeliverer(request,TEST_PORT, "/");
        assertTrue("Returns redirect deliverer when that route is passed in", server instanceof RedirectDeliverer);

    }

    @After
    public void tearDown() throws Exception{
        FileTestingUtilities.clearPath(FileTestingUtilities.testDirectory);
    }

}