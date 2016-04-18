
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NotFoundServingTest {

    private NotFoundServing notFoundServing;

    @Before

    public void setUp(){
        notFoundServing = new NotFoundServing("GET");
    }

    @Test
    public void testGetBytes() throws Exception {
        assertNotEquals("Returns not null bytes", new byte[0], notFoundServing.getBytes());
    }

    @Test
    public void testGetHTTPCode() throws Exception {
        assertEquals("Returns 404", 404, notFoundServing.getHTTPCode());
    }

    @Test
    public void testGetHTTPCodeReturns405WhenNotGet() throws Exception {
        NotFoundServing notFoundServingNotGet = new NotFoundServing("POST");
        assertEquals("Returns 405", 405, notFoundServingNotGet.getHTTPCode());
    }

    @Test
    public void testGetMethodOptionsReturnsGet(){
        assertArrayEquals("Method options returns array with only get", new String[]{"GET"}, notFoundServing.getMethodOptions());
    }

    @Test
    public void testGetContentTypeReturnsTextPlain() throws Exception {
        assertEquals("Returns text plain", "text/plain", notFoundServing.getContentType());
    }
}