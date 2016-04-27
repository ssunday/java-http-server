import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;


public class TeapotDelivererTest {

    private TeapotDeliverer teapotDeliverer;

    @Before
    public void setUp() throws Exception {
        teapotDeliverer = new TeapotDeliverer("/coffee", "GET");
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

    @Test
    public void testGetResponseHeaderIncludes200WhenTeaRequested(){
        TeapotDeliverer teapotDeliverer2 = new TeapotDeliverer("/tea", "GET");
        String response = teapotDeliverer2.getResponseHeader();
        assertThat(response, containsString("200 OK"));
    }

}