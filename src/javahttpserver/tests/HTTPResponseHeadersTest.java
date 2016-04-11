package javahttpserver.tests;

import javahttpserver.main.HTTPResponseHeaders;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HTTPResponseHeadersTest {

    @Test

    public void testGetTextAndHtmlHeaderIncludes200Code(){
        int contentLength = 30;
        String header = HTTPResponseHeaders.getTextAndHTMLHeader(contentLength);
        assertTrue("Directory Listing header includes 200 code", header.contains("200"));
    }

    @Test

    public void testGetTextAndHtmlHeaderIncludesContentLength(){
        int contentLength = 30;
        String header = HTTPResponseHeaders.getTextAndHTMLHeader(contentLength);
        assertTrue("Text/Html header includes content length", header.contains(Integer.toString(contentLength)));
    }

    @Test

    public void testGetTextAndHtmlHeaderIncludesTexthtmlContentType(){
        int contentLength = 30;
        String header = HTTPResponseHeaders.getTextAndHTMLHeader(contentLength);
        assertTrue("Text/Html header includes text/html content type", header.contains("text/html"));
    }

    @Test

    public void testGetImageHeaderIncludes200Code(){
        int contentLength = 50;
        String header = HTTPResponseHeaders.getImageHeader(contentLength);
        assertTrue("Image header includes image content type", header.contains("200"));
    }

    @Test

    public void testGetImageHeaderIncludesContentLength(){
        int contentLength = 200;
        String header = HTTPResponseHeaders.getImageHeader(contentLength);
        assertTrue("Image header includes image content type", header.contains(Integer.toString(contentLength)));
    }


    @Test

    public void testGetImageHeaderIncludesImageContentType(){
        int contentLength = 30;
        String header = HTTPResponseHeaders.getImageHeader(contentLength);
        assertTrue("Image header includes image content type", header.contains("image"));
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