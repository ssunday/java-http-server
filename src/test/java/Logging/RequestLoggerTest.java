package Logging;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;


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
        requestLogger.logRequest("GET / HTTP/1.1");
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String data = reader.readLine();
        assertThat(data, containsString("GET /"));
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
        assertThat(data, containsString("Request1"));
        assertThat(data, containsString("Request2"));
        log.delete();
    }

    @Test
    public void testLogRequestIncludesTimeStamp() throws Exception{
        Calendar currentInstance = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String currentTime = sdf.format(currentInstance.getTime());
        requestLogger.logRequest("GET / HTTP/1.1" );
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String data = reader.readLine();
        assertThat(data, containsString(currentTime));
        log.delete();
    }

    @Test
    public void testGetLogReturnsLoggedData(){
        String data = "Request";
        requestLogger.logRequest(data);
        assertThat(requestLogger.getLog(), containsString(data));
        log.delete();
    }

    @Test
    public void testGetLogReturnsNoRequestsLoggedIfNoFile(){
        requestLogger = new RequestLogger("nonexistent.txt");
        assertThat(requestLogger.getLog(), containsString("No Requests Logged"));
    }

    @Test
    public void testGetLogReturnsMultipleLinesOfLoggedData(){
        requestLogger.logRequest("Request1");
        requestLogger.logRequest("Request2");
        String loggedData = requestLogger.getLog();
        assertThat(loggedData, containsString("Request1"));
        assertThat(loggedData, containsString("Request2"));
        log.delete();
    }

    @After
    public void tearDown(){
        log.delete();
    }
}