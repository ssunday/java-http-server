import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MethodOptionDelivererTest {

    private MethodOptionDeliverer methodOptionDeliverer;

    @Before
    public void setUp() throws Exception {
        methodOptionDeliverer = new MethodOptionDeliverer("OPTIONS");
    }

    @Test
    public void testGetOptionsReturnsGetPostPutHeadOptions(){
        String[] options = new String[]{"GET","POST","PUT","OPTIONS", "HEAD"};
        assertArrayEquals("Array has get post put options", options, methodOptionDeliverer.getMethodOptions());
    }

    @Test
    public void testGetHTTPCodeReturns200(){
        assertEquals("HTTP Code is 200 with get", 200, methodOptionDeliverer.getHTTPCode());
    }

    @Test
    public void testGetHTTPCodeReturns405(){
        MethodOptionDeliverer methodOptionDeliverer1 = new MethodOptionDeliverer("DELETE");
        assertEquals("HTTP Code is 405 with DELETE", 405, methodOptionDeliverer1.getHTTPCode());
    }
}