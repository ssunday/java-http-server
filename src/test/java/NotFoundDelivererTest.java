import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotFoundDelivererTest {

    private NotFoundDeliverer notFoundDeliverer;

    @Before

    public void setUp(){
        notFoundDeliverer = new NotFoundDeliverer("GET");
    }

    @Test
    public void testGetBytes() throws Exception {
        assertNotEquals("Returns not null bytes", new byte[0], notFoundDeliverer.getBytes());
    }

    @Test
    public void testGetResponseHeaderIncludes404() throws Exception {
        String response = notFoundDeliverer.getResponseHeader();
        assertTrue("Returns 404", response.contains("404 Not Found"));
    }

    @Test
    public void testGetResponseHeaderIncludes405WhenPost() throws Exception {
        NotFoundDeliverer notFoundDeliverer1 = new NotFoundDeliverer("POST");
        String response = notFoundDeliverer1.getResponseHeader();
        assertTrue("Returns 405", response.contains("405 Method Not Allowed"));
    }

    @Test
    public void testGetResponseHeaderIncludesGETAndOPTIONS(){
        NotFoundDeliverer notFoundDeliverer1 = new NotFoundDeliverer("OPTIONS");
        String response = notFoundDeliverer1.getResponseHeader();
        assertTrue("Header includes options", response.contains("GET,OPTIONS"));
    }

    @Test
    public void testGetContentTypeReturnsTextPlain() throws Exception {
        String response = notFoundDeliverer.getResponseHeader();
        assertTrue("Header includes text plain", response.contains("text/plain"));
    }
}