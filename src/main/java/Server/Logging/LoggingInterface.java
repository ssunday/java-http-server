package Server.Logging;

public interface LoggingInterface {

    void addToQueue(String request);

    String getHeadRequest();

    void logQueue();
}
