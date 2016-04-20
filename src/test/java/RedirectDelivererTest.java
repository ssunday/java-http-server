import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RedirectDelivererTest {

    private RedirectDeliverer redirectDeliverer;
    private final int TEST_PORT = 6000;

    @Before
    public void setUp() throws Exception {
        redirectDeliverer = new RedirectDeliverer(TEST_PORT, "GET");
    }

    @Test
    public void testGetResponseHeaderIncludesLocationField() throws Exception{
        String response = redirectDeliverer.getResponseHeader();
        assertTrue("Header includes location line", response.contains("Location: http://localhost:"+TEST_PORT+"/"));
    }

    @Test
    public void testGetResponseHeaderIncludes302Code() throws Exception {
        String response = redirectDeliverer.getResponseHeader();
        assertTrue("HTTP code is 302", response.contains("302 Found"));
    }

    @Test
    public void testGetResponseHeaderIncludes405WhenNotGet() throws Exception {
        RedirectDeliverer redirectDeliverer1 = new RedirectDeliverer(TEST_PORT, "POST");
        String response = redirectDeliverer1.getResponseHeader();
        assertTrue("HTTP code is 405 when post", response.contains("405 Method Not Allowed"));
    }


    @Test
    public void testGetResponseHeaderIncludesAllowedMethods() throws Exception {
        RedirectDeliverer redirectDeliverer1 = new RedirectDeliverer(TEST_PORT, "OPTIONS");
        String response = redirectDeliverer1.getResponseHeader();
        assertTrue("Header includes options when options", response.contains("GET,OPTIONS"));
    }
}