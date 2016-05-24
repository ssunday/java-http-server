package Routes;

import HTTP.HTTPCode;
import HTTP.HTTPResponse;
import HTTP.HTTPVerbs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DelivererBase {

    protected HTTPResponse response;
    public List<String> OPTIONS = new ArrayList<String>(Arrays.asList(HTTPVerbs.HEAD, HTTPVerbs.OPTIONS));
    protected String requestType;
    protected String contentType;

    public byte[] getContentBytes(){
        byte[] bytes = getBytes();
        if (requestType.equals(HTTPVerbs.HEAD) && OPTIONS.contains(HTTPVerbs.HEAD)){
            bytes = new byte[0];
        }
        return bytes;
    }

    protected byte[] getBytes(){
        return new byte[0];
    }

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

    protected HTTPCode getHTTPCode(){
        return HTTPCode.OK;
    }
}
