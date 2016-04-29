package Logging;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LoggingQueue {

    private ConcurrentLinkedQueue requestQueue;

    public LoggingQueue(){
        this.requestQueue = new ConcurrentLinkedQueue();
    }

    public void addToQueue(String request){
        requestQueue.add(request);
    }

    public String getHeadRequest(){
        return (String) requestQueue.poll();
    }

    public void logQueue(){
        RequestLogger requestLogger = new RequestLogger();
        synchronized(requestQueue) {
            if(!requestQueue.isEmpty()) {
                requestLogger.logRequest(getHeadRequest());
            }
        }
    }

}
