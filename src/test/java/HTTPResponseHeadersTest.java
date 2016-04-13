

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HTTPResponseHeadersTest {

    @Test

    public void testGetHeaderIncludes200CodePassedIn(){
        int contentLength = 30;
        String header = HTTPResponseHeaders.getHTTPHeader(200, "text/html", contentLength);
        assertTrue("Header includes 200 code passed in", header.contains("200"));
    }

    @Test

    public void testGetHeaderIncludes206CodePassedIn(){
        int contentLength = 30;
        String header = HTTPResponseHeaders.getHTTPHeader(206, "text/html", contentLength);
        assertTrue("Header includes 206 code passed in", header.contains("206"));
    }

    @Test

    public void testGetHeaderIncludes404CodePassedIn(){
        int contentLength = 30;
        String header = HTTPResponseHeaders.getHTTPHeader(404, "text/html", contentLength);
        assertTrue("Header includes 404 code passed in", header.contains("404"));
    }

    @Test

    public void testGetHeaderIncludesTextHtmlContentType(){
        int contentLength = 30;
        String type = "text/html";
        String header = HTTPResponseHeaders.getHTTPHeader(200, type, contentLength);
        assertTrue("Header includes text/html content type passed in", header.contains(type));
    }

    @Test

    public void testGetHeaderIncludesImageContentType(){
        int contentLength = 30;
        String type = "image";
        String header = HTTPResponseHeaders.getHTTPHeader(200, type, contentLength);
        assertTrue("Header includes image content type passed in", header.contains(type));
    }


    @Test

    public void testGetHeaderIncludesContentLength(){
        int contentLength = 30;
        String type = "text/html";
        String header = HTTPResponseHeaders.getHTTPHeader(200, type, contentLength);
        assertTrue("Header includes content length passed in", header.contains(Integer.toString(contentLength)));
    }



}