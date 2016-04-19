import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DirectoryDelivererTest {

    private DirectoryDeliverer directoryDeliverer;

    private String testDirectory = FileTestingUtilities.testDirectory;

    @Before
    public void setUp() throws Exception {
        FilePaths filePaths = new FilePaths(testDirectory);
        directoryDeliverer = new DirectoryDeliverer(testDirectory, filePaths, "GET");
        FileTestingUtilities.makePath(testDirectory);
    }

    @Test
    public void testGetBytesReturnsByteArrayOfHeaderAndHTMLBytes() throws Exception {
        HTMLDirectoryDisplay directoryDisplay = new HTMLDirectoryDisplay();
        DirectoryListing directoryListing = new DirectoryListing();
        String html = directoryDisplay.displayListing(directoryListing.getListing(testDirectory), "/");
        String content = directoryDisplay.displayDirectoryBackNavigation("/") + html;
        byte[] bytes = content.getBytes();
        assertArrayEquals("Get Bytes returns bytes of HTML and back navigation for test directory", bytes, directoryDeliverer.getBytes());
    }

    @Test
    public void testGetHTTPCode() {
        assertEquals("Returns 200", 200, directoryDeliverer.getHTTPCode());
    }

    @Test
    public void testGetHTTPCodeReturns405IfNotGetPassedIn(){
        DirectoryDeliverer directoryServingPost = new DirectoryDeliverer(testDirectory, new FilePaths(testDirectory), "POST");
        assertEquals("Returns 405 when POST passed in", 405, directoryServingPost.getHTTPCode());
    }

    @Test
    public void testGetContentTypeReturnsTextHTML(){
        assertEquals("Returns text/html", "text/html", directoryDeliverer.getContentType());
    }

    @Test
    public void testGetMethodOptionsReturnsGET(){
        assertArrayEquals("Method options return array with only get", new String[]{"GET"}, directoryDeliverer.getMethodOptions());
    }

    @After
    public void clear() throws  Exception{
        FileTestingUtilities.clearPath(testDirectory);
    }
}