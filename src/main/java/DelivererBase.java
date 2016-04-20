import java.util.ArrayList;
import java.util.List;

abstract class DelivererBase {

    protected HTTPResponse response;
    protected List<String> OPTIONS = new ArrayList<String>();
    protected String requestType;
    protected String contentType;

    abstract byte[] getBytes();

    public String getResponseHeader(){
        response = new HTTPResponse();
        response.setHTTPCode(getHTTPCode());
        response.setContentType(contentType);
        if (requestType.equals("OPTIONS")){
            String[] options = new String[OPTIONS.size()];
            response.setAllow(OPTIONS.toArray(options));
        }
        response.setContentLength(getBytes().length);
        return response.getHeader();
    }

    abstract int getHTTPCode();

}
