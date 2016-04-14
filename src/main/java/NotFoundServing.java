
public class NotFoundServing extends ServingBase {

    @Override
    public byte[] getBytes(){
        String message = "Not found.";
        byte[] bytes = message.getBytes();
        return bytes;
    }

    @Override
    public int getHTTPCode(){
        return 404;
    }

    @Override
    public String getContentType() {
        return "text/plain";
    }
}
