package HTTP;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class HTTPResponseTest {

    @Test
    public void testSetHTTPCodeIncludes200CodeWithText(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(HTTPCode.OK);
        String response = httpResponse.getHeader();
        assertThat(response, containsString("200 OK"));
    }

    @Test
    public void testSetHTTPCodeIncludes204WithText(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(HTTPCode.NO_CONTENT);
        String response = httpResponse.getHeader();
        assertThat(response, containsString("204 No Content"));
    }

    @Test
    public void testSetHTTPCodeIncludes206CodeWithText(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(HTTPCode.PARTIAL_CONTENT);
        String response = httpResponse.getHeader();
        assertThat(response, containsString("206 Partial Content"));
    }

    @Test
    public void testSetHTTPCodeIncludes404CodeWithText(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(HTTPCode.NOT_FOUND);
        String response = httpResponse.getHeader();
        assertThat(response, containsString("404 Not Found"));
    }

    @Test
    public void testSetHTTPCodeIncludes302CodeWithText(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(HTTPCode.FOUND);
        String response = httpResponse.getHeader();
        assertThat(response, containsString("302 Found"));
    }

    @Test
    public void testSetHTTPCodeIncludes401CodeWithText(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(HTTPCode.UNAUTHORIZED);
        String response = httpResponse.getHeader();
        assertThat(response, containsString("401 Unauthorized"));
    }

    @Test
    public void testSetHTTPCodeIncludes405CodeWithText(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(HTTPCode.METHOD_NOT_ALLOWED);
        String response = httpResponse.getHeader();
        assertThat(response, containsString("405 Method Not Allowed"));
    }
    @Test
    public void testSetHTTPCodeIncludes418CodeWithText(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(HTTPCode.TEAPOT);
        String response = httpResponse.getHeader();
        assertThat(response, containsString("418 I'm a teapot"));
    }

    @Test
    public void testSetLocation(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(HTTPCode.FOUND);
        httpResponse.setLocation("http://localhost:5000/");
        String response = httpResponse.getHeader();
        assertThat(response, containsString("http://localhost:5000/"));
    }

    @Test
    public void testSetAllow(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(HTTPCode.OK);
        String[] methodOptions = new String[]{"GET", "POST"};
        httpResponse.setAllow(methodOptions);
        String response = httpResponse.getHeader();
        assertThat(response, containsString("Allow: GET,POST"));
    }

    @Test
    public void testSetContentType(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(HTTPCode.OK);
        httpResponse.setContentType("text/html");
        String response = httpResponse.getHeader();
        assertThat(response, containsString("Content-Type: text/html"));
    }

    @Test
    public void testSetContentLength(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(HTTPCode.OK);
        httpResponse.setContentLength(60);
        String response = httpResponse.getHeader();
        assertThat(response, containsString("Content-Length: 60"));
    }

    @Test
    public void testSetAuthenticateRealm(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(HTTPCode.UNAUTHORIZED);
        httpResponse.setAuthenticateRealm("logs");
        String response = httpResponse.getHeader();
        assertThat(response, containsString("WWW-Authenticate: Basic realm='logs'"));
    }

    @Test
    public void testSetEtag(){
        HTTPResponse httpResponse = new HTTPResponse();
        httpResponse.setHTTPCode(HTTPCode.OK);
        httpResponse.setETag("etag12");
        String response = httpResponse.getHeader();
        assertThat(response, containsString("ETag: \"etag12\""));
    }

    @Test
    public void testGetHeaderIncludesConnectionClose(){
        HTTPResponse httpResponse = new HTTPResponse();
        String response = httpResponse.getHeader();
        assertThat(response, containsString("Connection: close"));
    }

}