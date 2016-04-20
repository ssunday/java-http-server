import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class MethodOptionDelivererTest {

    private MethodOptionDeliverer methodOptionDeliverer;

    @Before
    public void setUp() throws Exception {
        methodOptionDeliverer = new MethodOptionDeliverer("OPTIONS");
    }

    @Test
    public void testGetBytesReturnsEmptyByteArray(){
        assertArrayEquals("Array is an empty byte array", new byte[0], methodOptionDeliverer.getBytes());
    }

    @Test
    public void testGetResponseHeaderIncludesOptions(){
        String response = methodOptionDeliverer.getResponseHeader();
        assertTrue("Response header includes options when options request", response.contains("Allow: GET,POST,PUT,OPTIONS,HEAD"));
    }

    @Test
    public void testGetResponseHeaderIncludes200WithGet(){
        String response = methodOptionDeliverer.getResponseHeader();
        assertTrue("HTTP Code is 200 with get", response.contains("200 OK"));
    }

    @Test
    public void testGetResponseHeaderIncludes405WithDelete(){
        MethodOptionDeliverer methodOptionDeliverer1 = new MethodOptionDeliverer("DELETE");
        String response = methodOptionDeliverer1.getResponseHeader();
        assertTrue("HTTP Code is 405 with DELETE", response.contains("405 Method Not Allowed"));
    }

}