
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

    protected int getHTTPCode(){
        int httpCode = isTea(path) ? 200 : 418;
        httpCode = (OPTIONS.contains(requestType)) ? httpCode : 405;
        return httpCode;
    }

    private boolean isTea(String path){
        return path.contains("tea");
    }
}
