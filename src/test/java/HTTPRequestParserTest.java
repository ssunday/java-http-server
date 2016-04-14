import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
}