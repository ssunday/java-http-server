import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class RequestLogger {

    private String logName;

    public RequestLogger(){
        logName = "logs";
    }

    public RequestLogger(String logName){
        this.logName = logName;
    }

    public void logRequest(String request){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(logName, true));
            writer.write(request);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {}
    }

    public String getLog() {
        String log = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(logName));
            String line;
            while ((line = reader.readLine()) != null){
                log += line + "\n";
            }
        } catch (Exception e){
            log = "No Requests Logged";
        }
        return log.trim();
    }
}