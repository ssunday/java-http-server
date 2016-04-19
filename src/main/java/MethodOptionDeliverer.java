
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
    public int getHTTPCode(){
        int httpCode = super.getHTTPCode();
        if (!(OPTIONS.contains(requestType))){
            httpCode = 405;
        }
        return httpCode;
    }
}
