
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

    protected int getHTTPCode(){
        int httpCode = 200;
        if (!(OPTIONS.contains(requestType))){
            httpCode = 405;
        }
        return httpCode;
    }

}
