import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class PartialContentDelivererTest {

    @Test
    public void testGetBytesReturnsPartialOfPassedInServerGetBytes() throws Exception {
        NotFoundDeliverer notFoundDeliverer = new NotFoundDeliverer("GET");
        PartialContentDeliverer server = new PartialContentDeliverer(notFoundDeliverer, 0, 20, "GET");
        byte[] directoryBytes = notFoundDeliverer.getBytes();
        byte[] partialBytes = Arrays.copyOfRange(directoryBytes, 0, 21);
        assertArrayEquals("Bytes returned is partial of bytes of passed in server", partialBytes, server.getBytes());

    }

    @Test
    public void testGetResponseHeaderIncludes206() throws Exception {
        NotFoundDeliverer notFoundDeliverer = new NotFoundDeliverer("GET");
        PartialContentDeliverer server = new PartialContentDeliverer(notFoundDeliverer, 0, 20, "GET");
        assertTrue("Header includes returns 206", server.getResponseHeader().contains("206 Partial Content"));
    }

    @Test
    public void testGetResponseHeaderIncludes405() throws Exception {
        NotFoundDeliverer notFoundDeliverer = new NotFoundDeliverer("GET");
        PartialContentDeliverer server = new PartialContentDeliverer(notFoundDeliverer, 0, 20, "POST");
        assertTrue("Header includes 405 when server passed in cannot handle it", server.getResponseHeader().contains("405 Method Not Allowed"));
    }

    @Test
    public void testGetResponseHeaderIncludesAllowedMethods() throws Exception {
        NotFoundDeliverer notFoundDeliverer = new NotFoundDeliverer("OPTIONS");
        PartialContentDeliverer server = new PartialContentDeliverer(notFoundDeliverer, 0, 20, "OPTIONS");
        assertTrue("Header includes server options when options", server.getResponseHeader().contains("GET,OPTIONS"));
    }
}