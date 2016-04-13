package javahttpserver.tests;

import javahttpserver.main.DirectoryListing;
import javahttpserver.main.DirectoryServing;
import javahttpserver.main.HTMLDirectoryDisplay;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DirectoryServingTest {

    private DirectoryServing directoryServing;

    private String testDirectory = FileTestingUtilities.testDirectory;

    @Before
    public void initialize() throws Exception {
        directoryServing = new DirectoryServing(testDirectory);
        FileTestingUtilities.makePath(testDirectory);
    }

    @Test
    public void testGetBytesReturnsByteArrayOfHeaderAndHTMLBytes() throws Exception {
        HTMLDirectoryDisplay directoryDisplay = new HTMLDirectoryDisplay();
        DirectoryListing directoryListing = new DirectoryListing();
        String html = directoryDisplay.displayListing(directoryListing.getListing(testDirectory), "/");
        String content = directoryDisplay.displayDirectoryBackNavigation("/") + html;
        byte[] bytes = content.getBytes();
        assertArrayEquals("Get Bytes returns bytes of HTML and back navigation for test directory", bytes, directoryServing.getBytes(testDirectory));
    }

    @Test
    public void testGetHTTPCode() {
        assertEquals("Returns 200", 200, directoryServing.getHTTPCode());
    }

    @Test

    public void testGetContentTypeReturnsTextHTML(){
        assertEquals("Returns text/html", "text/html", directoryServing.getContentType(null));
    }

    @After

    public void clear() throws  Exception{
        FileTestingUtilities.clearPath(testDirectory);
    }
}