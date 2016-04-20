import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

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
    public void testGetResponseHeaderReturns200HTTPCode() {
        assertTrue("Includes 200 code line", directoryDeliverer.getResponseHeader().contains("200 OK"));
    }

    @Test
    public void testGetResponseHeaderIncludes405WhenNotGet(){
        DirectoryDeliverer directoryServingPost = new DirectoryDeliverer(testDirectory, new FilePaths(testDirectory), "POST");
        assertTrue("Includes 405 code line when Post passed in", directoryServingPost.getResponseHeader().contains("405 Method Not Allowed"));
    }

    @Test
    public void testGetResponseHeaderIncludesTextHtml(){
        assertTrue("Includes text/html", directoryDeliverer.getResponseHeader().contains("text/html"));
    }

    @Test
    public void testGetResponseHeaderIncludesOptionsWhenOPTIONS(){
        DirectoryDeliverer directoryServingOptions = new DirectoryDeliverer(testDirectory, new FilePaths(testDirectory), "OPTIONS");
        assertTrue("Response header includes allow field", directoryServingOptions.getResponseHeader().contains("Allow: GET,OPTIONS"));
    }

    @After
    public void clear() throws  Exception{
        FileTestingUtilities.clearPath(testDirectory);
    }
}