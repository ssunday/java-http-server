package Deliverer;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

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
    public void testGetResponseHeaderIncludesAllowFieldWhenOptions(){
        String response = methodOptionDeliverer.getResponseHeader();
        assertThat(response, containsString("Allow: "));
    }

    @Test
    public void testGetResponseHeaderIncludes200WithGet(){
        String response = methodOptionDeliverer.getResponseHeader();
        assertThat(response, containsString("200 OK"));
    }

    @Test
    public void testGetResponseHeaderIncludes405WithDelete(){
        MethodOptionDeliverer methodOptionDeliverer1 = new MethodOptionDeliverer("DELETE");
        String response = methodOptionDeliverer1.getResponseHeader();
        assertThat(response, containsString("405 Method Not Allowed"));
    }

}