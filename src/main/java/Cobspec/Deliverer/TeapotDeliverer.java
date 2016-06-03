package Cobspec.Deliverer;

import Server.HTTP.HTTPCode;
import Server.HTTP.HTTPVerbs;
import Server.Deliverer.DelivererBase;

public class TeapotDeliverer extends DelivererBase {

    private String path;

    public TeapotDeliverer(String path, String requestType){
        this.requestType = requestType;
        this.path = path;
        this.OPTIONS.add(HTTPVerbs.GET);
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
