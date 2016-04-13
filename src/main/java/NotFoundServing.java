
public class NotFoundServing extends ServingBase {

    public byte[] getBytes(String pathToServe, String previousDirectory, String pathFromBase){
        String message = "Not found.";
        byte[] bytes = message.getBytes();
        return bytes;
    }

    public int getHTTPCode(){
        return 404;
    }

    public String getContentType(String filePath) {
        return "text/plain";
    }
}
