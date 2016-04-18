import java.util.ArrayList;
import java.util.List;

public class ServingBase {

    protected List<String> OPTIONS = new ArrayList<String>();

    public byte[] getBytes()
    {
        return new byte[0];
    }

    public int getHTTPCode(){ return 200;}

    public String getContentType() {
        return null;
    }

    public String[] getMethodOptions(){
        String[] methodOptions = new String[OPTIONS.size()];
        return OPTIONS.toArray(methodOptions);
    }

}
