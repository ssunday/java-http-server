import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RedirectDelivererTest {

    private RedirectDeliverer redirectDeliverer;

    @Before
    public void setUp() throws Exception {
        redirectDeliverer = new RedirectDeliverer();
    }

    @Test
    public void testGetHTTPCode() throws Exception {
        assertEquals("HTTP code is 302", 302, redirectDeliverer.getHTTPCode());
    }
}