import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class LogDelivererTest {

    private String ADMIN_USERNAME = "admin";

    private String ADMIN_PASSWORD = "hunter2";

    @Test
    public void testGetBytesReturnsByteArrayOfLoggerContentIfAuthorized(){
        RequestLogger logger = new RequestLogger();
        LogDeliverer logDeliverer = new LogDeliverer(ADMIN_USERNAME, ADMIN_PASSWORD, "GET");
        byte[] bytes = logger.getLog().getBytes();
        assertArrayEquals("Get bytes returns byte array of log content", bytes, logDeliverer.getBytes());
    }

    @Test
    public void testGetBytesReturnsEmptyBytesIfNotAuthorized(){
        LogDeliverer logDeliverer = new LogDeliverer("notadmin", "wrongpassword", "GET");
        assertArrayEquals("Get bytes returns empty byte array", new byte[0], logDeliverer.getBytes());
    }

    @Test
    public void testGetResponseHeaderIncludes200IfAuthorized(){
        LogDeliverer logDeliverer = new LogDeliverer(ADMIN_USERNAME, ADMIN_PASSWORD, "GET");
        assertTrue("HTTP Code is 200 when if the user/password combo is correct", logDeliverer.getResponseHeader().contains("200 OK"));
    }

    @Test
    public void testGetResponseHeaderIncludes405IfNotGetPassedIn(){
        LogDeliverer logDeliverer = new LogDeliverer(ADMIN_USERNAME, ADMIN_PASSWORD, "POST");
        assertTrue("Header includes 405 when POST passed in", logDeliverer.getResponseHeader().contains("405 Method Not Allowed"));
    }

    @Test
    public void testGetResponseHeaderIncludes401IfNotAuthorized(){
        LogDeliverer logDeliverer = new LogDeliverer("notadmin", "wrongpassword", "GET");
        assertTrue("HTTP Code is 401 when if the user/password combo is wrong", logDeliverer.getResponseHeader().contains("401 Unauthorized"));
    }

    @Test
    public void testGetMethodOptionsReturnsGETAndOPTIONS(){
        LogDeliverer logDeliverer = new LogDeliverer(ADMIN_USERNAME, ADMIN_PASSWORD, "OPTIONS");
        assertTrue("Allow field has get and options", logDeliverer.getResponseHeader().contains("Allow: GET,OPTIONS"));
    }

    @Test
    public void testGetContentTypeReturnsTextPlain(){
        LogDeliverer logDeliverer = new LogDeliverer(ADMIN_USERNAME, ADMIN_PASSWORD, "GET");
        assertTrue("Content type is text/plain", logDeliverer.getResponseHeader().contains("text/plain"));
    }

}