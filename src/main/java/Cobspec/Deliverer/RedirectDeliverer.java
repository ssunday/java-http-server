package Cobspec.Deliverer;

import Server.HTTP.HTTPCode;
import Server.HTTP.HTTPResponse;
import Server.HTTP.HTTPVerbs;
import Server.Deliverer.DelivererBase;

public class RedirectDeliverer extends DelivererBase {

    private int port;

    public RedirectDeliverer(int port, String requestType){
        this.requestType = requestType;
        this.port = port;
        this.OPTIONS.add(HTTPVerbs.GET);
    }

    @Override
    protected byte[] getBytes(){
        return new byte[0];
    }

    @Override
    public String getResponseHeader() {
        response = new HTTPResponse();
        response.setHTTPCode(getHTTPCode());
        response.setLocation("http://localhost:" + port +"/");
        addAllowField();
        response.setContentLength(getBytes().length);
        return response.getHeader();
    }

    protected HTTPCode getHTTPCode(){
        HTTPCode httpCode;
        httpCode = (OPTIONS.contains(requestType)) ? HTTPCode.FOUND : HTTPCode.METHOD_NOT_ALLOWED;
        return httpCode;
    }
}
