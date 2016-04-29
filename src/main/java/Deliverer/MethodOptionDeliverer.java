package Deliverer;

import HTTP.HTTPCode;
import HTTP.HTTPResponse;
import HTTP.HTTPVerbs;

public class MethodOptionDeliverer extends DelivererBase {

    public MethodOptionDeliverer(String requestType){
        this.requestType = requestType;
        OPTIONS.add(HTTPVerbs.GET);
        OPTIONS.add(HTTPVerbs.POST);
        OPTIONS.add(HTTPVerbs.PUT);
        OPTIONS.add(HTTPVerbs.OPTIONS);
        OPTIONS.add(HTTPVerbs.HEAD);
    }

    @Override
    protected byte[] getBytes(){
        return new byte[0];
    }

    @Override
    public String getResponseHeader(){
        response = new HTTPResponse();
        response.setHTTPCode(getHTTPCode());
        addAllowField();
        response.setContentLength(0);
        return response.getHeader();
    }

    protected HTTPCode getHTTPCode(){
        HTTPCode httpCode;
        httpCode = (OPTIONS.contains(requestType)) ? HTTPCode.OK : HTTPCode.METHOD_NOT_ALLOWED;
        return httpCode;
    }

}
