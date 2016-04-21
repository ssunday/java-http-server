public class RedirectDeliverer extends DelivererBase {

    private int port;

    public RedirectDeliverer(int port, String requestType){
        this.requestType = requestType;
        this.port = port;
        OPTIONS.add(HTTPVerbs.GET);
        OPTIONS.add(HTTPVerbs.HEAD);
        OPTIONS.add(HTTPVerbs.OPTIONS);
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

    public int getHTTPCode(){
        int httpCode = 302;
        if (!(OPTIONS.contains(requestType))){
            httpCode = 405;
        }
        return httpCode;
    }
}
