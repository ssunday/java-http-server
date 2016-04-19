import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RedirectDelivererTest {

    private RedirectDeliverer redirectDeliverer;

    @Before
    public void setUp() throws Exception {
        redirectDeliverer = new RedirectDeliverer("GET");
    }

    @Test
    public void testGetHTTPCode() throws Exception {
        assertEquals("HTTP code is 302", 302, redirectDeliverer.getHTTPCode());
    }

    @Test
    public void testGetHTTPCodeIs405WhenNotGet() throws Exception {
        RedirectDeliverer redirectDeliverer1 = new RedirectDeliverer("POST");
        assertEquals("HTTP code is 405 when post", 405, redirectDeliverer1.getHTTPCode());
    }
}