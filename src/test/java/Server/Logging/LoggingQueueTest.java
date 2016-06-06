package Server.Logging;

import TestingSupport.FileTestingUtilities;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertThat;


public class LoggingQueueTest {

    private String TEST_LOG = "test-log";

    @Test
    public void testAddQueueAddsToEmptyList(){
        LoggingQueue loggingQueue = new LoggingQueue("test-log");
        String request =  "GET / HTTP/1.1" + "\r\n" +
                "Connection: close\r\n\r\n";
        loggingQueue.addToQueue(request);
        assertThat(loggingQueue.getHeadRequest(), CoreMatchers.equalTo(request));
    }

    @Test
    public void testGetHeadRequestReturnsHeadElement(){
        LoggingQueue loggingQueue = new LoggingQueue("test-log");
        String request =  "GET / HTTP/1.1" + "\r\n" +
                "Connection: close\r\n\r\n";
        loggingQueue.addToQueue(request);
        String request2 =  "GET /something HTTP/1.1" + "\r\n" +
                "Connection: close\r\n\r\n";
        loggingQueue.addToQueue(request2);
        assertThat(loggingQueue.getHeadRequest(), CoreMatchers.equalTo(request));
    }

    @Test
    public void testGetHeadRequestReturnsNullIfEmpty(){
        LoggingQueue loggingQueue = new LoggingQueue("test-log");
        assertThat(loggingQueue.getHeadRequest(), CoreMatchers.equalTo(null));
    }

    @After
    public void tearDown() throws Exception{
        FileTestingUtilities.clearPath(TEST_LOG);
    }
}