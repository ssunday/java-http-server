package TestingSupport;

import Server.Logging.LoggingInterface;

public class MockLoggingQueue implements LoggingInterface{

    public void addToQueue(String request){}

    public String getHeadRequest(){return "";}

    public void logQueue(){}
}

