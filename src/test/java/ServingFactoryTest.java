import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ServingFactoryTest {

    @Before
    public void setUp() throws Exception{
        FileTestingUtilities.makePath(FileTestingUtilities.testDirectory);
    }

    @Test
    public void testGetServerReturnsDirectoryServing() throws Exception {
        String path = FileTestingUtilities.testDirectory + "single/";
        FileTestingUtilities.makePath(path);
        String request = "GET /single/ HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        ServingBase server = ServingFactory.getServer(request, FileTestingUtilities.testDirectory);
        assertTrue("Returns Directory object when passed in a directory", server instanceof DirectoryServing);
        FileTestingUtilities.clearPath(path);
    }

    @Test
    public void testGetServerReturnsFileServing() throws Exception {
        String singleFile = FileTestingUtilities.testDirectory + "single.txt";
        FileTestingUtilities.makeFile(singleFile);
        String request = "GET /single.txt HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        ServingBase server = ServingFactory.getServer(request, FileTestingUtilities.testDirectory);
        assertTrue("Returns File serving object when file passed in", server instanceof FileServing);
        FileTestingUtilities.clearPath(singleFile);
    }

    @Test
    public void testGetServerReturnsParameterServing() throws Exception {
        String request = "GET /parameters?var=blarg HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        ServingBase server = ServingFactory.getServer(request, "/");
        assertTrue("Returns Parameter serving object when path with parameters passed in", server instanceof ParameterServing);
    }

    @Test
    public void testGetServerReturnsFormServing() throws Exception {
        String request = "GET /form HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        ServingBase server = ServingFactory.getServer(request, "/");
        assertTrue("Returns form serving object when path with form is passed in", server instanceof FormServing);
    }

    @Test
    public void testGetServerReturnsLogServing() throws Exception{
        String request = "GET /logs HTTP/1.1\r\n" +
                "Authorization: Basic c29tZW9uZTpwYXNzd29yZA==\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        ServingBase server = ServingFactory.getServer(request, "/");
        assertTrue("Returns log serving when logs route is passed in", server instanceof LogServing);
    }

    @Test
    public void testGetServerReturnsNotFoundServing() throws Exception {
        String request = "GET /notfound.txt HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        ServingBase server = ServingFactory.getServer(request,"/");
        assertTrue("Returns not found server when non-existent file is passed in", server instanceof NotFoundServing);
    }

    @After
    public void tearDown() throws Exception{
        FileTestingUtilities.clearPath(FileTestingUtilities.testDirectory);
    }

}