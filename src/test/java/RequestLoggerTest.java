import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class RequestLoggerTest {

    private RequestLogger requestLogger;
    private File log;
    private String fileName;

    @Before
    public void setUp() throws Exception {
        fileName = "logtest.txt";
        requestLogger = new RequestLogger(fileName);
        log = new File(fileName);
    }

    @Test
    public void testLogRequest() throws Exception {
        requestLogger.logRequest("Request");
        assertTrue("Log Request creates log file", log.exists());
        log.delete();
    }

    @Test
    public void testLogRequestWritesToLog() throws Exception {
        requestLogger.logRequest("Request");
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String data = reader.readLine();
        assertTrue("Log Request adds line to log", data.contains("Request"));
        log.delete();
    }

    @Test
    public void testLogRequestAppendsToLog() throws Exception {
        String data = "";
        requestLogger.logRequest("Request1");
        requestLogger.logRequest("Request2");
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        data += reader.readLine() + "\n";
        data += reader.readLine();
        assertTrue("Log Request appends to existing log file", data.contains("Request1\nRequest2"));
        log.delete();
    }

    @Test
    public void testGetLogReturnsLoggedData(){
        String data = "Request";
        requestLogger.logRequest(data);
        assertEquals("Get log returns logged data", data, requestLogger.getLog());
        log.delete();
    }

    @Test
    public void testGetLogReturnsMultipleLinesOfLoggedData(){
        String allData = "Request\nRequest2";
        requestLogger.logRequest("Request");
        requestLogger.logRequest("Request2");
        String loggedData = requestLogger.getLog();
        assertEquals("Get log returns multiple lines of logged data", allData, loggedData);
        log.delete();
    }

    @After
    public void tearDown(){
        log.delete();
    }
}