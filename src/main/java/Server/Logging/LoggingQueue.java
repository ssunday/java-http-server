package Server.Logging;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LoggingQueue implements LoggingInterface {

    private ConcurrentLinkedQueue requestQueue;
    private String logFile;

    public LoggingQueue(){
        this.logFile = "log";
    }
    public LoggingQueue(String logFile){
        this.requestQueue = new ConcurrentLinkedQueue();
        this.logFile = logFile;
    }

    public void addToQueue(String request){
        requestQueue.add(request);
    }

    public String getHeadRequest(){
        return (String) requestQueue.poll();
    }

    public void logQueue(){
        RequestLogger requestLogger = new RequestLogger(logFile);
        synchronized(requestQueue) {
            if(!requestQueue.isEmpty()) {
                requestLogger.logRequest(getHeadRequest());
            }
        }
    }

}
