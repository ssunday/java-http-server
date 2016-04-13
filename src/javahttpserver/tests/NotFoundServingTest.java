package javahttpserver.tests;

import javahttpserver.main.NotFoundServing;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotFoundServingTest {

    private NotFoundServing notFoundServing;

    @Before

    public void initialize(){
        notFoundServing = new NotFoundServing();
    }

    @Test
    public void testGetBytes() throws Exception {
        assertNotEquals("Returns not null bytes", new byte[0], notFoundServing.getBytes("/"));
    }

    @Test
    public void testGetHTTPCode() throws Exception {
        assertEquals("Returns 404", 404, notFoundServing.getHTTPCode());
    }

    @Test
    public void testGetContentTypeReturnsTextPlain() throws Exception {
        assertEquals("Returns text plain", "text/plain", notFoundServing.getContentType(null));
    }
}