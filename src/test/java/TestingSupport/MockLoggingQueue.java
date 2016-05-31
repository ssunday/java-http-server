package TestingSupport;

import Server.Logging.LoggingQueue;

public class MockLoggingQueue extends LoggingQueue{
    public void addToQueue(){}

    public String getHeadRequest(){return "";}

    public void logQueue(){}
}

