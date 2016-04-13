package javahttpserver.tests;

import javahttpserver.main.ServingBase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ServingBaseTest {

    private ServingBase serving;

    @Before
    public void initialize(){
        serving = new ServingBase();
    }
    @Test
    public void testGetBytes() throws Exception {
        assertArrayEquals("Returns new byte of size 0", new byte[0], serving.getBytes("/"));
    }

    @Test
    public void testGetHTTPCode() throws Exception {
        assertEquals("Returns 200 HTTP code", 200, serving.getHTTPCode());
    }

    @Test
    public void testGetContentType() throws Exception {
        assertNull("Returns null", serving.getContentType(null));
    }
}