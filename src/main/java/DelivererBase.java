import java.util.ArrayList;
import java.util.List;

abstract class DelivererBase {

    protected HTTPResponse response;
    protected List<String> OPTIONS = new ArrayList<String>();
    protected String requestType;
    protected String contentType;

    public byte[] getContentBytes(){
        byte[] bytes = getBytes();
        if (requestType.equals(HTTPVerbs.HEAD) && OPTIONS.contains(HTTPVerbs.HEAD)){
            bytes = new byte[0];
        }
        return bytes;
    }

    abstract byte[] getBytes();

    public String getResponseHeader(){
        response = new HTTPResponse();
        response.setHTTPCode(getHTTPCode());
        response.setContentType(contentType);
        addAllowField();
        response.setContentLength(getBytes().length);
        return response.getHeader();
    }

    protected void addAllowField(){
        if (requestType.equals(HTTPVerbs.OPTIONS) && OPTIONS.contains(HTTPVerbs.HEAD)){
            String[] options = new String[OPTIONS.size()];
            response.setAllow(OPTIONS.toArray(options));
        }
    }

    abstract int getHTTPCode();

}
