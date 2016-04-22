
public class TeapotDeliverer extends DelivererBase {

    public TeapotDeliverer(String requestType){
        this.requestType = requestType;
        OPTIONS.add(HTTPVerbs.GET);
        OPTIONS.add(HTTPVerbs.HEAD);
        OPTIONS.add(HTTPVerbs.OPTIONS);
    }

    protected byte[] getBytes(){
        String displayWords = "I'm a teapot";
        return displayWords.getBytes();
    }

    protected int getHTTPCode(){
        int httpCode = 418;
        httpCode = (OPTIONS.contains(requestType)) ? httpCode : 405;
        return httpCode;
    }
}
