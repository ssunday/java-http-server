package Logging;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;


public class LoggingQueueTest {

    @Test
    public void testAddQueueAddsToEmptyList(){
        LoggingQueue loggingQueue = new LoggingQueue();
        String request =  "GET / HTTP/1.1" + "\r\n" +
                "Connection: close\r\n\r\n";
        loggingQueue.addToQueue(request);
        assertThat(loggingQueue.getHeadRequest(), CoreMatchers.equalTo(request));
    }

    @Test
    public void testGetHeadRequestReturnsHeadElement(){
        LoggingQueue loggingQueue = new LoggingQueue();
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
        LoggingQueue loggingQueue = new LoggingQueue();
        assertThat(loggingQueue.getHeadRequest(), CoreMatchers.equalTo(null));
    }
}