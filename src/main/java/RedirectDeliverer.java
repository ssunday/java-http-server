public class RedirectDeliverer extends DelivererBase {

    public RedirectDeliverer(String requestType){
        this.requestType = requestType;
        OPTIONS.add("GET");
        OPTIONS.add("OPTIONS");
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
