package Cobspec.Deliverer;

import Server.HTTP.HTTPCode;
import Server.HTTP.HTTPResponse;
import Server.HTTP.HTTPVerbs;
import Server.Deliverer.DelivererBase;

public class MethodOptionDeliverer extends DelivererBase {

    public MethodOptionDeliverer(String requestType){
        this.requestType = requestType;
        this.OPTIONS.add(HTTPVerbs.GET);
        this.OPTIONS.add(HTTPVerbs.POST);
        this.OPTIONS.add(HTTPVerbs.PUT);
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
