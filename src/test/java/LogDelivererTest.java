import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

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
    public void testGetHTTPCodeReturns200IfAuthorized(){
        LogDeliverer logDeliverer = new LogDeliverer(ADMIN_USERNAME, ADMIN_PASSWORD, "GET");
        assertEquals("HTTP Code is 200 when if the user/password combo is correct", 200, logDeliverer.getHTTPCode());
    }

    @Test
    public void testGetHTTPCodeReturns405IfNotGetPassedIn(){
        LogDeliverer logDeliverer = new LogDeliverer(ADMIN_USERNAME, ADMIN_PASSWORD, "POST");
        assertEquals("Returns 405 when POST passed in", 405, logDeliverer.getHTTPCode());
    }

    @Test
    public void testGetHTTPCodeReturns401IfNotAuthorized(){
        LogDeliverer logDeliverer = new LogDeliverer("notadmin", "wrongpassword", "GET");
        assertEquals("HTTP Code is 401 when if the user/password combo is wrong", 401, logDeliverer.getHTTPCode());
    }

    @Test
    public void testGetMethodOptionsReturnsGETAndOPTIONS(){
        LogDeliverer logDeliverer = new LogDeliverer(ADMIN_USERNAME, ADMIN_PASSWORD, "OPTIONS");
        assertArrayEquals("Method options returns array with only get and options when options is passed in", new String[]{"GET", "OPTIONS"}, logDeliverer.getMethodOptions());
    }

    @Test
    public void testGetContentTypeReturnsTextPlain(){
        LogDeliverer logDeliverer = new LogDeliverer(ADMIN_USERNAME, ADMIN_PASSWORD, "GET");
        assertEquals("Content type is text/plain", "text/plain", logDeliverer.getContentType());
    }

}