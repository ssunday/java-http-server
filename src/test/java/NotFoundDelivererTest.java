import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
    public void testGetHTTPCode() throws Exception {
        assertEquals("Returns 404", 404, notFoundDeliverer.getHTTPCode());
    }

    @Test
    public void testGetHTTPCodeReturns405WhenNotGet() throws Exception {
        NotFoundDeliverer notFoundDeliverer1 = new NotFoundDeliverer("POST");
        assertEquals("Returns 405", 405, notFoundDeliverer1.getHTTPCode());
    }

    @Test
    public void testGetMethodOptionsReturnsGet(){
        assertArrayEquals("Method options returns array with only get", new String[]{"GET"}, notFoundDeliverer.getMethodOptions());
    }

    @Test
    public void testGetContentTypeReturnsTextPlain() throws Exception {
        assertEquals("Returns text plain", "text/plain", notFoundDeliverer.getContentType());
    }
}