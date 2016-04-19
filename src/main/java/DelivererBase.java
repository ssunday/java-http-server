import java.util.ArrayList;
import java.util.List;

public class DelivererBase {

    protected List<String> OPTIONS = new ArrayList<String>();
    protected String requestType;

    public DelivererBase(){}

    public DelivererBase(String requestType){
        this.requestType = requestType;
        OPTIONS.add("GET");
    }

    public byte[] getBytes()
    {
        return new byte[0];
    }

    public int getHTTPCode(){
        int httpCode;
        httpCode = (OPTIONS.contains(requestType)) ? 200 : 405;
        return httpCode;
    }

    public String getContentType() {
        return null;
    }

    public String[] getMethodOptions(){
        if (requestType.equals("OPTIONS")){
            String[]methodOptions = new String[OPTIONS.size()];
            return OPTIONS.toArray(methodOptions);
        }
        else{
            return new String[0];
        }
    }
}
