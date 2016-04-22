import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;


public class TeapotDelivererTest {

    private TeapotDeliverer teapotDeliverer;

    @Before
    public void setUp() throws Exception {
        teapotDeliverer = new TeapotDeliverer("GET");
    }

    @Test
    public void testGetBytes() throws Exception {
        String message = new String(teapotDeliverer.getBytes());
        assertThat(message, containsString("I'm a teapot"));
    }

    @Test
    public void testGetResponseHeaderIncludes418() throws Exception{
        String response = teapotDeliverer.getResponseHeader();
        assertThat(response, containsString("418 I'm a teapot"));
    }

}