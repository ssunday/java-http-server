import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class LogServingTest {

    private String ADMIN_USERNAME = "admin";

    private String ADMIN_PASSWORD = "hunter2";

    @Test
    public void testGetBytesReturnsByteArrayOfLoggerContentIfAuthorized(){
        RequestLogger logger = new RequestLogger();
        LogServing logServing = new LogServing(ADMIN_USERNAME, ADMIN_PASSWORD);
        byte[] bytes = logger.getLog().getBytes();
        assertArrayEquals("Get bytes returns byte array of log content", bytes, logServing.getBytes());
    }

    @Test
    public void testGetBytesReturnsEmptyBytesIfNotAuthorized(){
        LogServing logServing = new LogServing("notadmin", "wrongpassword");
        assertArrayEquals("Get bytes returns empty byte array", new byte[0], logServing.getBytes());
    }

    @Test
    public void testGetHTTPCodeReturns200IfAuthorized(){
        LogServing logServing = new LogServing(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertEquals("HTTP Code is 200 when if the user/password combo is correct", 200, logServing.getHTTPCode());
    }

    @Test
    public void testGetHTTPCodeReturns401IfNotAuthorized(){
        LogServing logServing = new LogServing("notadmin", "wrongpassword");
        assertEquals("HTTP Code is 401 when if the user/password combo is wrong", 401, logServing.getHTTPCode());
    }

    @Test
    public void testGetContentTypeReturnsTextPlain(){
        LogServing logServing = new LogServing(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertEquals("Content type is text/plain", "text/plain", logServing.getContentType());
    }

}