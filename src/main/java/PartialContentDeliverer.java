import java.util.Arrays;

public class PartialContentDeliverer extends DelivererBase {

    private DelivererBase server;
    private int rangeStart;
    private int rangeEnd;

    public PartialContentDeliverer(DelivererBase server, int startPoint, int endPoint, String requestType){
        this.server = server;
        rangeStart = startPoint;
        rangeEnd = endPoint;
        this.requestType = requestType;
    }

    @Override
    public byte[] getBytes(){
        byte[] fullBytes = server.getBytes();
        int[] byteRange = getByteRange(fullBytes.length);
        int start = byteRange[0];
        int end = byteRange[1];
        return Arrays.copyOfRange(fullBytes, start, end);
    }

    @Override
    public String getResponseHeader(){
        response = new HTTPResponse();
        response.setHTTPCode(getHTTPCode());
        if (requestType.equals("OPTIONS")){
            String[] options = new String[server.OPTIONS.size()];
            response.setAllow(server.OPTIONS.toArray(options));
        }
        response.setContentLength(getBytes().length-1);
        return response.getHeader();
    }

    protected int getHTTPCode(){
        int httpCode = 206;
        if (!(server.OPTIONS.contains(requestType))){
            httpCode = 405;
        }
        return httpCode;
    }

    private int[] getByteRange(int fullByteLength){
        if (validByteRange(rangeStart)){
            rangeEnd = (validByteRange(rangeEnd)) ? rangeEnd + 1: fullByteLength;
        }
        else{
            if (validByteRange(rangeEnd)){
                rangeStart = fullByteLength - rangeEnd;
                rangeEnd = fullByteLength;
            }
        }
        return new int[] {rangeStart, rangeEnd};
    }

    private boolean validByteRange(int bytePoint){
        return (bytePoint >= -128 && bytePoint < 127);
    }
}
