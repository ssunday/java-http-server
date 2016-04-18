import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DirectoryServingTest {

    private DirectoryServing directoryServing;

    private String testDirectory = FileTestingUtilities.testDirectory;

    @Before
    public void setUp() throws Exception {
        FilePaths filePaths = new FilePaths(testDirectory);
        directoryServing = new DirectoryServing(testDirectory, filePaths, "GET");
        FileTestingUtilities.makePath(testDirectory);
    }

    @Test
    public void testGetBytesReturnsByteArrayOfHeaderAndHTMLBytes() throws Exception {
        HTMLDirectoryDisplay directoryDisplay = new HTMLDirectoryDisplay();
        DirectoryListing directoryListing = new DirectoryListing();
        String html = directoryDisplay.displayListing(directoryListing.getListing(testDirectory), "/");
        String content = directoryDisplay.displayDirectoryBackNavigation("/") + html;
        byte[] bytes = content.getBytes();
        assertArrayEquals("Get Bytes returns bytes of HTML and back navigation for test directory", bytes, directoryServing.getBytes());
    }

    @Test
    public void testGetHTTPCode() {
        assertEquals("Returns 200", 200, directoryServing.getHTTPCode());
    }

    @Test
    public void testGetHTTPCodeReturns405IfNotGetPassedIn(){
        DirectoryServing directoryServingPost = new DirectoryServing(testDirectory, new FilePaths(testDirectory), "POST");
        assertEquals("Returns 405 when POST passed in", 405, directoryServingPost.getHTTPCode());
    }

    @Test
    public void testGetContentTypeReturnsTextHTML(){
        assertEquals("Returns text/html", "text/html", directoryServing.getContentType());
    }

    @Test
    public void testGetMethodOptionsReturnsGET(){
        assertArrayEquals("Method options return array with only get", new String[]{"GET"}, directoryServing.getMethodOptions());
    }

    @After
    public void clear() throws  Exception{
        FileTestingUtilities.clearPath(testDirectory);
    }
}