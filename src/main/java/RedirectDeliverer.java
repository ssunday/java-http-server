public class RedirectDeliverer extends DelivererBase {

    private String requestType;

    public RedirectDeliverer(String requestType){
        this.requestType = requestType;
        OPTIONS.add("GET");
    }

    @Override
    public int getHTTPCode(){
        int httpCode = 302;
        if (!(OPTIONS.contains(requestType))){
            httpCode = 405;
        }
        return httpCode;
    }
}
