package Cobspec.Deliverer;

import Cobspec.DelivererSupport.DirectoryListing;
import Cobspec.HTML.DirectoryDisplayTemplate;
import TestingSupport.FileTestingUtilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

public class DirectoryDelivererTest {

    private DirectoryDeliverer directoryDeliverer;
    private String testDirectory = FileTestingUtilities.testDirectory;

    @Before
    public void setUp() throws Exception {
        directoryDeliverer = new DirectoryDeliverer("/", testDirectory, "/", "GET");
        FileTestingUtilities.makePath(testDirectory);
    }

    @Test
    public void testGetBytesReturnsByteArrayOfHeaderAndHTMLBytes() throws Exception {
        DirectoryListing directoryListing = new DirectoryListing();
        DirectoryDisplayTemplate directoryDisplay = new DirectoryDisplayTemplate(directoryListing.getListing(testDirectory), "/", "/");
        String html = directoryDisplay.renderPage();
        byte[] bytes = html.getBytes();
        assertArrayEquals("Get Bytes returns bytes of HTML and back navigation for test directory", bytes, directoryDeliverer.getBytes());
    }

    @Test
    public void testGetResponseHeaderReturns200HTTPCode() {
        String response = directoryDeliverer.getResponseHeader();
        assertThat(response, containsString("200 OK"));
    }

    @Test
    public void testGetResponseHeaderIncludes405WhenNotGet(){
        DirectoryDeliverer directoryServingPost = new DirectoryDeliverer("/",  testDirectory, "/", "POST");
        String response = directoryServingPost.getResponseHeader();
        assertThat(response, containsString("405 Method Not Allowed"));
    }

    @Test
    public void testGetResponseHeaderIncludesTextHtml(){
        String response = directoryDeliverer.getResponseHeader();
        assertThat(response, containsString("text/html"));
    }

    @Test
    public void testGetResponseHeaderIncludesAllowFieldWhenOptions(){
        DirectoryDeliverer directoryServingOptions = new DirectoryDeliverer("/",  testDirectory, "/", "OPTIONS");
        String response = directoryServingOptions.getResponseHeader();
        assertThat(response, containsString("Allow: "));
    }

    @After
    public void clear() throws  Exception{
        FileTestingUtilities.clearPath(testDirectory);
    }
}