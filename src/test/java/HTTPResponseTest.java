import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class HTTPResponseTest {

    @Test
    public void testSetHTTPCodeIncludes200CodeWithText(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(200);
        String response = httpResponse.getHeader();
        assertTrue("Http response header includes 200 line when set", response.contains("200 OK"));
    }

    @Test
    public void testSetHTTPCodeIncludes204WithText(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(204);
        String response = httpResponse.getHeader();
        assertTrue("Http response header includes 204 line when set", response.contains("204 No Content"));
    }

    @Test
    public void testSetHTTPCodeIncludes206CodeWithText(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(206);
        String response = httpResponse.getHeader();
        assertTrue("Http response header includes 206 line when set", response.contains("206 Partial Content"));
    }

    @Test
    public void testSetHTTPCodeIncludes404CodeWithText(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(404);
        String response = httpResponse.getHeader();
        assertTrue("Http response header includes 404 line when set", response.contains("404 Not Found"));
    }

    @Test
    public void testSetHTTPCodeIncludes302CodeWithText(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(302);
        String response = httpResponse.getHeader();
        assertTrue("Http response header includes 302 line when set", response.contains("302 Found"));
    }

    @Test
    public void testSetHTTPCodeIncludes401CodeWithText(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(401);
        String response = httpResponse.getHeader();
        assertTrue("Http response header includes 401 line when set", response.contains("401 Unauthorized"));
    }

    @Test
    public void testSetHTTPCodeIncludes405CodeWithText(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(405);
        String response = httpResponse.getHeader();
        assertTrue("Http response header includes 405 line when set", response.contains("405 Method Not Allowed"));
    }

    @Test
    public void testSetLocation(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(302);
        httpResponse.setLocation("http://localhost:5000/");
        String response = httpResponse.getHeader();
        assertTrue("Http response header includes redirect location", response.contains("http://localhost:5000/"));
    }

    @Test
    public void testSetAllow(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(200);
        String[] methodOptions = new String[]{"GET", "POST"};
        httpResponse.setAllow(methodOptions);
        String response = httpResponse.getHeader();
        assertTrue("Response header includes allow options set", response.contains("Allow: GET,POST"));
    }

    @Test
    public void testSetContentType(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(200);
        httpResponse.setContentType("text/html");
        String response = httpResponse.getHeader();
        assertTrue("Response header includes content type set", response.contains("Content-Type: text/html"));
    }

    @Test
    public void testSetContentLength(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(200);
        httpResponse.setContentLength(60);
        String response = httpResponse.getHeader();
        assertTrue("Response header includes content length set", response.contains("Content-Length: 60"));
    }

    @Test
    public void testSetAuthenticateRealm(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(401);
        httpResponse.setAuthenticateRealm("logs");
        String response = httpResponse.getHeader();
        assertTrue("Response header includes authenticate realm", response.contains("WWW-Authenticate: Basic realm='logs'"));
    }

    @Test
    public void testSetEtag(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(200);
        httpResponse.setETag("etag12");
        String response = httpResponse.getHeader();
        assertTrue("Response header contains etag line when set", response.contains("ETag: \"etag12\""));
    }

    @Test
    public void testGetHeaderIncludesConnectionClose(){
        HTTPResponse httpResponse = new HTTPResponse();
        String response = httpResponse.getHeader();
        assertTrue("Http response header includes Connection Close line", response.contains("Connection: close"));
    }

}