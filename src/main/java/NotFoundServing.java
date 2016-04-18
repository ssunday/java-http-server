public class NotFoundServing extends ServingBase {

    private String requestType;

    public NotFoundServing(String requestType){
        this.requestType = requestType;
        OPTIONS.add("GET");
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
