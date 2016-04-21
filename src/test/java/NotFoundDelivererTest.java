import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class NotFoundDelivererTest {

    private NotFoundDeliverer notFoundDeliverer;

    @Before

    public void setUp(){
        notFoundDeliverer = new NotFoundDeliverer("GET");
    }

    @Test
    public void testGetBytes() throws Exception {
        assertNotEquals("Returns not null bytes", new byte[0], notFoundDeliverer.getBytes());
    }

    @Test
    public void testGetResponseHeaderIncludes404() throws Exception {
        String response = notFoundDeliverer.getResponseHeader();
        assertThat(response, containsString("404 Not Found"));
    }

    @Test
    public void testGetResponseHeaderIncludes405WhenPost() throws Exception {
        NotFoundDeliverer notFoundDeliverer1 = new NotFoundDeliverer("POST");
        String response = notFoundDeliverer1.getResponseHeader();
        assertThat(response, containsString("405 Method Not Allowed"));
    }

    @Test
    public void testGetResponseHeaderIncludesAllowFieldWhenOptions(){
        NotFoundDeliverer notFoundDeliverer1 = new NotFoundDeliverer("OPTIONS");
        String response = notFoundDeliverer1.getResponseHeader();
        assertThat(response, containsString("Allow: "));
    }

    @Test
    public void testGetResponseHeaderIncludesTextPlain() throws Exception {
        String response = notFoundDeliverer.getResponseHeader();
        assertThat(response, containsString("text/plain"));
    }
}