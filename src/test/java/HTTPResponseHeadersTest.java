import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HTTPResponseHeadersTest {

    @Test
    public void testGetHeaderIncludes200CodePassedIn(){
        int contentLength = 30;
        String[] methodOptions = new String[]{"GET"};
        String header = HTTPResponseHeaders.getHTTPHeader(5000, 200, "text/html", contentLength, methodOptions);
        assertTrue("Header includes 200 code passed in", header.contains("200"));
    }

    @Test
    public void testGetHeaderIncludes206CodePassedIn(){
        int contentLength = 30;
        String[] methodOptions = new String[]{"GET"};
        String header = HTTPResponseHeaders.getHTTPHeader(5000, 206, "text/html", contentLength, methodOptions);
        assertTrue("Header includes 206 code passed in", header.contains("206"));
    }

    @Test
    public void testGetHeaderIncludes404CodePassedIn(){
        int contentLength = 30;
        String[] methodOptions = new String[]{"GET"};
        String header = HTTPResponseHeaders.getHTTPHeader(5000, 404, "text/html", contentLength, methodOptions);
        assertTrue("Header includes 404 code passed in", header.contains("404"));
    }

    @Test
    public void testGetHeaderIncludes401CodePassedIn(){
        int contentLength = 30;
        String[] methodOptions = new String[]{"GET"};
        String header = HTTPResponseHeaders.getHTTPHeader(5000, 401, "text/plain", contentLength, methodOptions);
        assertTrue("Header includes 401 code passed in", header.contains("401"));
    }

    @Test
    public void testGetHeaderIncludesAuthenticationHeaderIf401CodePassedIn(){
        int contentLength = 30;
        String[] methodOptions = new String[]{"GET"};
        String header = HTTPResponseHeaders.getHTTPHeader(5000, 401, "text/plain", contentLength, methodOptions);
        assertTrue("Header includes authentication request header if 401 code passed in", header.contains("WWW-Authenticate"));
    }

    @Test
    public void testGetHeaderIncludes405CodePassedIn(){
        int contentLength = 30;
        String[] methodOptions = new String[]{"GET"};
        String header = HTTPResponseHeaders.getHTTPHeader(5000, 405, "text/plain", contentLength, methodOptions);
        assertTrue("Header includes 405 code if 405 passed in", header.contains("405"));
    }

    @Test
    public void testGetHeaderIncludes302CodePassedIn(){
        int contentLength = 30;
        String[] methodOptions = new String[]{"GET"};
        String header = HTTPResponseHeaders.getHTTPHeader(5000, 302, "text/plain", contentLength, methodOptions);
        assertTrue("Header includes 302 code if 302 passed in", header.contains("302"));
    }

    @Test
    public void testGetHeaderIncludes302LocationFieldIfPassedIn(){
        int contentLength = 30;
        String[] methodOptions = new String[]{"GET"};
        String header = HTTPResponseHeaders.getHTTPHeader(5000, 302, "text/plain", contentLength, methodOptions);
        assertTrue("Header includes location field if 302 passed in", header.contains("Location: "));
    }

    @Test
    public void testGetHeaderIncludesTextHtmlContentType(){
        int contentLength = 30;
        String type = "text/html";
        String[] methodOptions = new String[]{"GET"};
        String header = HTTPResponseHeaders.getHTTPHeader(5000, 200, type, contentLength, methodOptions);
        assertTrue("Header includes text/html content type passed in", header.contains(type));
    }

    @Test
    public void testGetHeaderIncludesImageContentType(){
        int contentLength = 30;
        String type = "image";
        String[] methodOptions = new String[]{"GET"};
        String header = HTTPResponseHeaders.getHTTPHeader(5000, 200, type, contentLength, methodOptions);
        assertTrue("Header includes image content type passed in", header.contains(type));
    }

    @Test
    public void testGetHeaderIncludesContentLength(){
        int contentLength = 30;
        String[] methodOptions = new String[]{"GET"};
        String type = "text/html";
        String header = HTTPResponseHeaders.getHTTPHeader(5000, 200, type, contentLength, methodOptions);
        assertTrue("Header includes content length passed in", header.contains(Integer.toString(contentLength)));
    }

    @Test
    public void testGetHeaderIncludesAllow(){
        int contentLength = 30;
        String type = "text/html";
        String[] methodOptions = new String[]{"GET", "POST"};
        String header = HTTPResponseHeaders.getHTTPHeader(5000, 200, type, contentLength, methodOptions);
        assertTrue("Header includes options passed in", header.contains("Allow: GET,POST"));
    }

}