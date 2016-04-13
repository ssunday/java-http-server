public class ServingBase {

    public byte[] getBytes(String pathToServe)
    {
        return new byte[0];
    }

    public int getHTTPCode(){
        return 200;
    }

    public String getContentType(String filePath) {
        return null;
    }

}
