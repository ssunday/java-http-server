package Server.HTTP;

import Server.HTTP.HTTPRequestParser;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HTTPRequestParserTest {

    @Test
    public void testGetRequestTypeReturnsGetWhenGet(){
        String request = "GET /file.html HTTP/1.1";
        assertEquals("Request type is GET", "GET", HTTPRequestParser.getRequestType(request));
    }

    @Test
    public void testGetRequestTypeReturnsPutWhenPut(){
        String request = "PUT /file.html HTTP/1.1";
        assertEquals("Request type is PUT", "PUT", HTTPRequestParser.getRequestType(request));
    }

    @Test
    public void testGetRequestTypeReturnsPostWhenPost(){
        String request = "POST /file.html HTTP/1.1";
        assertEquals("Request type is POST", "POST", HTTPRequestParser.getRequestType(request));
    }

    @Test
    public void testGetPathReturnsPathGivenHTTPRequestLine(){
        String request = "GET /folder/ HTTP/1.1";
        String path = "/folder/";
        assertEquals("Simple directory path is returned from request", path, HTTPRequestParser.getPath(request));
    }

    @Test
    public void testGetPathReturnsFullNestedDirectoryPath(){
        String request = "GET /folder/something/ HTTP/1.1";
        String directory = HTTPRequestParser.getPath(request);
        String fullpath = "/folder/something/";
        assertEquals("Returns path of nested folders given HTTP request line", fullpath, directory);
    }

    @Test
    public void testGetPathWithFoldersWithSpaceReturnsFullPath(){
        String request = "GET /folder%20something/ HTTP/1.1";
        String directory = HTTPRequestParser.getPath(request);
        String fullpath = "/folder something/";
        assertEquals("Returns path of folders with html space given HTTP request", fullpath, directory);
    }

    @Test
    public void testGetPathReturnsFullDirectoryWithFile(){
        String request = "GET /file.txt HTTP/1.1";
        String directory = HTTPRequestParser.getPath(request);
        String fullpath = "/file.txt";
        assertEquals("Returns path of file given HTTP request", fullpath, directory);
    }

    @Test
    public void testGetPathReturnsNestedDirectoryWithFile(){
        String request = "GET /something/file.txt HTTP/1.1";
        String directory = HTTPRequestParser.getPath(request);
        String fullPath = "/something/file.txt";
        assertEquals("Returns full path of nested file given HTTP request", fullPath, directory);
    }

    @Test
    public void testGetPathReturnsEncodedPath(){
        String request = "GET /something?var1=blarg%20blarg HTTP/1.1";
        String directory = HTTPRequestParser.getPath(request);
        String fullPath = "/something?var1=blarg%20blarg";
        assertEquals("Returns path with encoded parameters", fullPath, directory);
    }

    @Test
    public void testGetPathReturnsPathGivenMultiLineRequest(){
        String request = "GET /something/ HTTP/1.1" + "\r\n" + "Host: localhost";
        String directory = HTTPRequestParser.getPath(request);
        String fullPath = "/something/";
        assertEquals("Returns path given multi-line request", fullPath, directory);
    }

    @Test
    public void testGetPathReturnsFilePathGivenMultiLineRequest(){
        String request = "GET /something.txt HTTP/1.1" + "\r\n" + "Host: localhost";
        String directory = HTTPRequestParser.getPath(request);
        String fullPath = "/something.txt";
        assertEquals("Returns file path given multi-line request", fullPath, directory);
    }

    @Test
    public void testGetPathReturnsPathWithParametersGivenMultiLineRequest(){
        String request = "GET /something?var1=blarg%20blarg HTTP/1.1" + "\r\n" + "Host: localhost";
        String directory = HTTPRequestParser.getPath(request);
        String fullPath = "/something?var1=blarg%20blarg";
        assertEquals("Returns file path given multi-line request", fullPath, directory);
    }

    @Test
    public void testGetParamsReturnsNullWhenNotPost(){
        String request = "GET /something HTTP/1.1" + "\r\n"
                + "Host: localhost" + "\r\n" + "Content-Type: application/x-www-form-urlencoded" + "\r\n\r\n";
        assertEquals("Returns null when it is not post", null, HTTPRequestParser.getParams(request));
    }

    @Test
    public void testGetParamsReturnsPostParams() {
        String params = "var1=something";
        String request = "POST /something HTTP/1.1" + "\r\n"
                + "Host: localhost" + "\r\n" + "Content-Type: application/x-www-form-urlencoded" + "\r\n\r\n"
                + params;
        assertEquals("Returns post params", params, HTTPRequestParser.getParams(request));
    }

    @Test
    public void testGetParamsReturnsPutParams() {
        String params = "var1=something";
        String request = "PUT /something HTTP/1.1" + "\r\n"
                + "Host: localhost" + "\r\n" + "Content-Type: application/x-www-form-urlencoded" + "\r\n\r\n"
                + params;
        assertEquals("Returns put params", params, HTTPRequestParser.getParams(request));
    }

    @Test
    public void testGetParsedParamsReturnsParamsAsMapOfTypeAndValue(){
        String params = "var1=something";
        String request = "PUT /something HTTP/1.1" + "\r\n"
                + "Host: localhost" + "\r\n" + "Content-Type: application/x-www-form-urlencoded" + "\r\n\r\n"
                + params;
        Map parsed = new HashMap();
        parsed.put("var1", "something");
        assertEquals(parsed, HTTPRequestParser.getParsedParams(request));
    }

    @Test
    public void testGetParsedParamsReturnsDecodedParam(){
        String params = "var1=something%26something";
        String request = "PUT /something HTTP/1.1" + "\r\n"
                + "Host: localhost" + "\r\n" + "Content-Type: application/x-www-form-urlencoded" + "\r\n\r\n"
                + params;
        Map parsed = new HashMap();
        parsed.put("var1", "something&something");
        assertEquals(parsed, HTTPRequestParser.getParsedParams(request));
    }

    @Test
    public void testGetParsedParamsReturnsMultipleParams(){
        String params = "var1=something&var2=something2";
        String request = "PUT /something HTTP/1.1" + "\r\n"
                + "Host: localhost" + "\r\n" + "Content-Type: application/x-www-form-urlencoded" + "\r\n\r\n"
                + params;
        Map parsed = new HashMap();
        parsed.put("var1", "something");
        parsed.put("var2", "something2");
        assertEquals(parsed, HTTPRequestParser.getParsedParams(request));
    }

    @Test
    public void testGetAuthenticationPasswordReturnsEmptyStringWhenNoAuthentication(){
        String request = "GET /logs HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        assertEquals("Get authentication password returns empty string if no authentication line", "", HTTPRequestParser.getAuthenticationPassword(request));
    }

    @Test
    public void testGetAuthenticationPasswordReturnsPassword(){
        String[] information = new String[]{"someone", "password"};
        String request = "GET /logs HTTP/1.1\r\n" +
                "Authorization: Basic c29tZW9uZTpwYXNzd29yZA==\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        assertEquals("Get authentication password returns decoded password", "password", HTTPRequestParser.getAuthenticationPassword(request));
    }

    @Test
    public void testGetAuthenticationUsernameReturnsUsername(){
        String[] information = new String[]{"someone", "password"};
        String request = "GET /logs HTTP/1.1\r\n" +
                "Authorization: Basic c29tZW9uZTpwYXNzd29yZA==\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        assertEquals("Get authentication username returns decoded username", "someone", HTTPRequestParser.getAuthenticationUsername(request));
    }

    @Test
    public void testGetAuthenticationUsernameReturnsEmptyStringWhenNoAuthentication(){
        String request = "GET /logs HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        assertEquals("Get authentication username returns decoded username", "", HTTPRequestParser.getAuthenticationUsername(request));
    }

    @Test
    public void testGetContentRangeStartReturnsInvalidByteRaneIfNoRange(){
        String request = "GET /logs HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        assertEquals("Get content Range Start returns -1000 if no range", -1000, HTTPRequestParser.getContentRangeStart(request));
    }

    @Test
    public void testGetContentRangeStartReturnsStartRangeIfProvided(){
        String request = "GET /logs HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Range: bytes=20-40\r\n"+
                "Connection: Keep-Alive\r\n";
        assertEquals("Get content Range Start returns start range if present", 20, HTTPRequestParser.getContentRangeStart(request));
    }

    @Test
    public void testGetContentRangeEndReturnsInvalidByteRaneIfNoRange(){
        String request = "GET /logs HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        assertEquals("Get content range end returns -1000 if no range", -1000, HTTPRequestParser.getContentRangeEnd(request));
    }

    @Test
    public void testGetContentRangeEndReturnsEndRangeIfProvided(){
        String request = "GET /logs HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Range: bytes=5-40\r\n"+
                "Connection: Keep-Alive\r\n";
        assertEquals("Get content Range end returns end range if range is present", 40, HTTPRequestParser.getContentRangeEnd(request));
    }

    @Test
    public void testGetContentRangeEndReturnsInvalidByteRaneIfNoEndRangeSpecified(){
        String request = "GET /logs HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Range: bytes=10-\r\n"+
                "Connection: Keep-Alive\r\n";
        assertEquals("Get content range end returns -1000 if range not specified", -1000, HTTPRequestParser.getContentRangeEnd(request));
    }

    @Test
    public void testGetContentRangeStartReturnsInvalidByteRangeIfNoStartRangeSpecified(){
        String request = "GET /logs HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Range: bytes=-50\r\n"+
                "Connection: Keep-Alive\r\n";
        assertEquals("Get content range start returns -1000 if range start not specified", -1000, HTTPRequestParser.getContentRangeStart(request));
    }

    @Test
    public void testHasContentRangeReturnsTrueWhenRangeExists(){
        String request = "GET /logs HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Range: bytes=0-\r\n"+
                "Connection: Keep-Alive\r\n";
        assertTrue("Has content range returns true when range exists", HTTPRequestParser.hasContentRange(request));
    }

    @Test
    public void testHasContentRangeReturnsFalseWhenRangeDoesNotExist(){
        String request = "GET /logs HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        assertFalse("Has content range returns false when range does not exists", HTTPRequestParser.hasContentRange(request));
    }

    @Test
    public void testGetEtagReturnsNullIfNotPatchRequest(){
        String request = "GET / HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        assertNull("Get etag returns null with not a patch request", HTTPRequestParser.getEtag(request));
    }

    @Test
    public void testGetEtagReturnsTagIfPatchRequestAndProvidedTag(){
        String request = "PATCH / HTTP/1.1\r\n" +
                "If-Match: 'bwer123124\r\n'" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n";
        assertEquals("Get etag returns tag of patch request", "bwer123124", HTTPRequestParser.getEtag(request));
    }

    @Test
    public void testGetPatchedContentReturnsContentToPatch(){
        String request = "PATCH / HTTP/1.1\r\n" +
                "If-Match: 'bwer123124\r\n'" +
                "Host: localhost\r\n" +
                "Connection: Keep-Alive\r\n\r\n"+
                "some content";
        assertEquals("Patch content returns content to use to patch", "some content", HTTPRequestParser.getPatchContent(request));
    }
}