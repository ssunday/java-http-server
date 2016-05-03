package Deliverer;

import HTTP.HTTPCode;
import HTTP.HTTPVerbs;

public class NotFoundDeliverer extends DelivererBase {

    public NotFoundDeliverer(String requestType){
        this.requestType = requestType;
        OPTIONS.add(HTTPVerbs.GET);
        OPTIONS.add(HTTPVerbs.HEAD);
        OPTIONS.add(HTTPVerbs.OPTIONS);
        contentType = "text/plain";
    }

    @Override
    protected byte[] getBytes(){
        String message = "Not found.";
        byte[] bytes = message.getBytes();
        return bytes;
    }

    protected HTTPCode getHTTPCode(){
        HTTPCode httpCode;
        httpCode = (OPTIONS.contains(requestType)) ? HTTPCode.NOT_FOUND : HTTPCode.METHOD_NOT_ALLOWED;
        return httpCode;
    }
}
