
public class MethodOptionDeliverer extends DelivererBase {

    public MethodOptionDeliverer(String requestType){
        this.requestType = requestType;
        OPTIONS.add("GET");
        OPTIONS.add("POST");
        OPTIONS.add("PUT");
        OPTIONS.add("OPTIONS");
        OPTIONS.add("HEAD");
    }

    @Override
    public byte[] getBytes(){
        return new byte[0];
    }

    @Override
    public String getResponseHeader(){
        response = new HTTPResponse();
        response.setHTTPCode(getHTTPCode());
        if (requestType.equals("OPTIONS")){
            String[] options = new String[OPTIONS.size()];
            response.setAllow(OPTIONS.toArray(options));
        }
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
