package Cobspec;

import Cobspec.Deliverer.*;
import Server.Deliverer.LogDeliverer;
import Server.Deliverer.NotFoundDeliverer;
import Server.Deliverer.DelivererBase;
import TestingSupport.FileTestingUtilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CobspecDelivererFactoryTest {

    private final int TEST_PORT = 6000;
    private CobspecDelivererFactory cobspecDelivererFactory;
    
    @Before
    public void setUp() throws Exception{
        FileTestingUtilities.makePath(FileTestingUtilities.testDirectory);
        cobspecDelivererFactory = new CobspecDelivererFactory("TEST_LOG", TEST_PORT, FileTestingUtilities.testDirectory);
    }

    @Test
    public void testGetDelivererReturnsDirectoryDeliverer() throws Exception {
        String path = FileTestingUtilities.testDirectory + "single/";
        FileTestingUtilities.makePath(path);
        String request = "GET /single/ HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase deliverer = cobspecDelivererFactory.getDeliverer(request);
        assertTrue("Returns Directory object when passed in a directory", deliverer instanceof DirectoryDeliverer);
        FileTestingUtilities.clearPath(path);
    }

    @Test
    public void testGetDelivererReturnsFileDeliverer() throws Exception {
        String singleFile = FileTestingUtilities.testDirectory + "single.txt";
        FileTestingUtilities.makeFile(singleFile);
        String request = "GET /single.txt HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase deliverer = cobspecDelivererFactory.getDeliverer(request);
        assertTrue("Returns File Deliverer object when file passed in", deliverer instanceof FileDeliverer);
        FileTestingUtilities.clearPath(singleFile);
    }

    @Test
    public void testGetDelivererReturnsFileDelivererWithEtag() throws Exception {
        String singleFile = FileTestingUtilities.testDirectory + "single.txt";
        FileTestingUtilities.makeFile(singleFile);
        String request = "PATCH /single.txt HTTP/1.1\r\n" +
                "If-Match: 'bwer123124\r\n'" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n" +
                "some content";
        DelivererBase deliverer = cobspecDelivererFactory.getDeliverer(request);
        assertThat(deliverer.getResponseHeader(), containsString("ETag: "));
        FileTestingUtilities.clearPath(singleFile);
    }

    @Test
    public void testGetDelivererReturnsParameterDeliverer() throws Exception {
        String request = "GET /parameters?var=blarg HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase deliverer = cobspecDelivererFactory.getDeliverer(request);
        assertTrue("Returns Parameter Deliverer object when path with parameters passed in", deliverer instanceof ParameterDeliverer);
    }

    @Test
    public void testGetDelivererReturnsFormDeliverer() throws Exception {
        String request = "GET /form HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase deliverer = cobspecDelivererFactory.getDeliverer(request);
        assertTrue("Returns form Deliverer object when path with form is passed in", deliverer instanceof FormDeliverer);
    }

    @Test
    public void testGetDelivererReturnsLogDeliverer() throws Exception{
        String request = "GET /logs HTTP/1.1\r\n" +
                "Authorization: Basic c29tZW9uZTpwYXNzd29yZA==\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase deliverer = cobspecDelivererFactory.getDeliverer(request);
        assertTrue("Returns log Deliverer when logs route is passed in", deliverer instanceof LogDeliverer);
    }

    @Test
    public void testGetDelivererReturnsPartialContentDeliverer() throws Exception{
        String request = "GET /logs HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Range: bytes=0-20" + "\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase deliverer = cobspecDelivererFactory.getDeliverer(request);
        assertTrue("Returns partial content deliverer when range passed in", deliverer instanceof PartialContentDeliverer);
    }

    @Test
    public void testGetDelivererReturnsNotFoundDeliverer() throws Exception {
        String request = "GET /notfound.txt HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase deliverer = cobspecDelivererFactory.getDeliverer(request);
        assertTrue("Returns not found deliverer when non-existent file is passed in", deliverer instanceof NotFoundDeliverer);
    }

    @Test
    public void testGetDelivererReturnsMethodOptionsDeliverer() throws Exception{
        String request = "GET /method_options HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase deliverer = cobspecDelivererFactory.getDeliverer(request);
        assertTrue("Returns method option deliverer when that route is passed in", deliverer instanceof MethodOptionDeliverer);
    }
    
    @Test
    public void testGetDelivererReturnsRedirectDeliverer(){
        String request = "GET /redirect HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase deliverer = cobspecDelivererFactory.getDeliverer(request);
        assertTrue("Returns redirect deliverer when that route is passed in", deliverer instanceof RedirectDeliverer);
    }

    @Test
    public void testGetDelivererReturnsTeapotDeliverer(){
        String request = "GET /tea HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase deliverer = cobspecDelivererFactory.getDeliverer(request);
        assertTrue("Returns teapot deliverer when tea route is passed in", deliverer instanceof TeapotDeliverer);
    }

    @Test
    public void testGetDelivererReturnsTeapotDelivererCoffee(){
        String request = "GET /coffee HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        DelivererBase deliverer = cobspecDelivererFactory.getDeliverer(request);
        assertTrue("Returns teapot deliverer when coffee route is passed in", deliverer instanceof TeapotDeliverer);
    }

    @After
    public void tearDown() throws Exception{
        FileTestingUtilities.clearPath(FileTestingUtilities.testDirectory);
    }

}