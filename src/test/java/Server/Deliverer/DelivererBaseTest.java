package Server.Deliverer;

import Server.HTTP.HTTPCode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DelivererBaseTest {

    private DelivererBase delivererBase;

    @Before
    public void setUp(){
        delivererBase = new DelivererBase();
    }

    @Test
    public void testGetBytes() throws Exception {
        assertArrayEquals(new byte[0], delivererBase.getBytes());
    }

    @Test
    public void testGetHTTPCode() throws Exception {
        assertEquals(HTTPCode.OK, delivererBase.getHTTPCode());
    }
}