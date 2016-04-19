public class NotFoundDeliverer extends DelivererBase {

    public NotFoundDeliverer(String requestType){
        this.requestType = requestType;
        OPTIONS.add("GET");
        OPTIONS.add("OPTIONS");
    }

    @Override
    public byte[] getBytes(){
        String message = "Not found.";
        byte[] bytes = message.getBytes();
        return bytes;
    }

    @Override
    public int getHTTPCode(){
        int httpCode = 404;
        if (!(OPTIONS.contains(requestType))){
            httpCode = 405;
        }
        return httpCode;
    }

    @Override
    public String getContentType() {
        return "text/plain";
    }
}
