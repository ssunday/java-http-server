package Cobspec.Deliverer;

import Server.Deliverer.NotFoundDeliverer;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class PartialContentDelivererTest {

    @Test
    public void testGetBytesReturnsPartialOfPassedInDelivererGetBytes() throws Exception {
        NotFoundDeliverer notFoundDeliverer = new NotFoundDeliverer("GET");
        PartialContentDeliverer deliverer = new PartialContentDeliverer(notFoundDeliverer, 0, 20, "GET");
        byte[] directoryBytes = notFoundDeliverer.getContentBytes();
        byte[] partialBytes = Arrays.copyOfRange(directoryBytes, 0, 22);
        assertArrayEquals("Bytes returned is partial of bytes of passed in server", partialBytes, deliverer.getBytes());

    }

    @Test
    public void testGetResponseHeaderIncludes206() throws Exception {
        NotFoundDeliverer notFoundDeliverer = new NotFoundDeliverer("GET");
        PartialContentDeliverer deliverer = new PartialContentDeliverer(notFoundDeliverer, 0, 20, "GET");
        assertTrue("Header includes returns 206", deliverer.getResponseHeader().contains("206 Partial Content"));
    }

    @Test
    public void testGetResponseHeaderIncludes405() throws Exception {
        NotFoundDeliverer notFoundDeliverer = new NotFoundDeliverer("GET");
        PartialContentDeliverer deliverer = new PartialContentDeliverer(notFoundDeliverer, 0, 20, "POST");
        assertTrue("Header includes 405 when server passed in cannot handle it", deliverer.getResponseHeader().contains("405 Method Not Allowed"));
    }

    @Test
    public void testGetResponseHeaderIncludesAllowedFieldWhenOptions() throws Exception {
        NotFoundDeliverer notFoundDeliverer = new NotFoundDeliverer("OPTIONS");
        PartialContentDeliverer deliverer = new PartialContentDeliverer(notFoundDeliverer, 0, 20, "OPTIONS");
        String response = deliverer.getResponseHeader();
        assertThat(response, containsString("Allow: "));
    }
}