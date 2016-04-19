

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DelivererBaseTest {

    private DelivererBase delivererBase;

    @Before
    public void setUp(){
        delivererBase = new DelivererBase("GET");
    }

    @Test
    public void testGetBytes() throws Exception {
        assertArrayEquals("Returns new byte of size 0", new byte[0], delivererBase.getBytes());
    }

    @Test
    public void testGetHTTPCode() throws Exception {
        assertEquals("Returns 200 HTTP code", 200, delivererBase.getHTTPCode());
    }

    @Test
    public void testGetContentType() throws Exception {
        assertNull("Returns null", delivererBase.getContentType());
    }

    @Test
    public void testGetMethodOptionsReturnsEmptyStringArray(){
        assertArrayEquals("Method options returns empty string array", new String[0], delivererBase.getMethodOptions());
    }
}