package javahttpserver.tests;

import javahttpserver.main.HTTPResponseHeaders;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HTTPResponseHeadersTest {

    @Test

    public void testGetDirectoryListingHeaderIncludes200Code(){
        int contentLength = 30;
        String header = HTTPResponseHeaders.getDirectoryListingHeader(contentLength);
        assertTrue("Directory Listing header includes 200 code", header.contains("200"));
    }

    @Test

    public void testGetDirectoryListingHeaderIncludesContentLength(){
        int contentLength = 30;
        String header = HTTPResponseHeaders.getDirectoryListingHeader(contentLength);
        assertTrue("Directory Listing header includes content length", header.contains(Integer.toString(contentLength)));
    }

    @Test

    public void testGetDirectoryListingHeaderIncludesTexthtmlContentType(){
        int contentLength = 30;
        String header = HTTPResponseHeaders.getDirectoryListingHeader(contentLength);
        assertTrue("Directory Listing header includes text/html content type", header.contains("text/html"));
    }

    @Test

    public void testGet404HeaderHas404Code(){
        int contentLength = 10;
        String header = HTTPResponseHeaders.get404Header(contentLength);
        assertTrue("404 header includes 404 code", header.contains("404"));
    }

    @Test

    public void testGet404HeaderHasContentLength(){
        int contentLength = 10;
        String header = HTTPResponseHeaders.get404Header(contentLength);
        assertTrue("404 header includes 404 code", header.contains(Integer.toString(contentLength)));
    }

    @Test

    public void testGet404HeaderIncludesTexthtmlContentType(){
        int contentLength = 30;
        String header = HTTPResponseHeaders.get404Header(contentLength);
        assertTrue("404 header includes text/html content type", header.contains("text/html"));
    }


}