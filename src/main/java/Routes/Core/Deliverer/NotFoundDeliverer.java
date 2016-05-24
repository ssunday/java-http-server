package Routes.Core.Deliverer;

import HTTP.HTTPCode;
import HTTP.HTTPVerbs;
import Routes.DelivererBase;

public class NotFoundDeliverer extends DelivererBase {

    public NotFoundDeliverer(String requestType){
        this.requestType = requestType;
        OPTIONS.add(HTTPVerbs.GET);
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
