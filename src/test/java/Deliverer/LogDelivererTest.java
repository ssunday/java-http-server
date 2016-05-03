package Deliverer;

import Logging.RequestLogger;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

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
        String response = logDeliverer.getResponseHeader();
        assertThat(response, containsString("200 OK"));
    }

    @Test
    public void testGetResponseHeaderIncludes405IfNotGetPassedIn(){
        LogDeliverer logDeliverer = new LogDeliverer(ADMIN_USERNAME, ADMIN_PASSWORD, "POST");
        String response = logDeliverer.getResponseHeader();
        assertThat(response, containsString("405 Method Not Allowed"));
    }

    @Test
    public void testGetResponseHeaderIncludes401IfNotAuthorized(){
        LogDeliverer logDeliverer = new LogDeliverer("notadmin", "wrongpassword", "GET");
        String response = logDeliverer.getResponseHeader();
        assertThat(response, containsString("401 Unauthorized"));
    }

    @Test
    public void testGetResponseHeaderIncludesAllowFieldWhenOptions(){
        LogDeliverer logDeliverer = new LogDeliverer(ADMIN_USERNAME, ADMIN_PASSWORD, "OPTIONS");
        String response = logDeliverer.getResponseHeader();
        assertThat(response, containsString("Allow: "));
    }

    @Test
    public void testGetResponseHeaderIncludesTextPlain(){
        LogDeliverer logDeliverer = new LogDeliverer(ADMIN_USERNAME, ADMIN_PASSWORD, "GET");
        String response = logDeliverer.getResponseHeader();
        assertThat(response, containsString("text/plain"));
    }

}