public class ServingBase {

    public byte[] getBytes()
    {
        return new byte[0];
    }

    public int getHTTPCode(){
        return 200;
    }

    public String getContentType() {
        return null;
    }

}
