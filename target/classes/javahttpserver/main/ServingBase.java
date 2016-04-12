package javahttpserver.main;

public class ServingBase {

    public byte[] getBytes(String pathToServe, String previousDirectory, String pathFromBase)
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
