package Deliverer;

import HTTP.HTTPCode;
import HTTP.HTTPVerbs;

public class TeapotDeliverer extends DelivererBase {

    private String path;

    public TeapotDeliverer(String path, String requestType){
        this.requestType = requestType;
        this.path = path;
        OPTIONS.add(HTTPVerbs.GET);
        OPTIONS.add(HTTPVerbs.HEAD);
        OPTIONS.add(HTTPVerbs.OPTIONS);
    }

    protected byte[] getBytes(){
        String displayWords = isTea(path) ? "I make tea." : "I'm a teapot";
        return displayWords.getBytes();
    }

    protected HTTPCode getHTTPCode(){
        HTTPCode httpCode;
        httpCode = (isTea(path)) ? HTTPCode.OK : HTTPCode.TEAPOT;
        httpCode = (OPTIONS.contains(requestType)) ? httpCode : HTTPCode.METHOD_NOT_ALLOWED;
        return httpCode;
    }

    private boolean isTea(String path){
        return path.contains("tea");
    }
}
