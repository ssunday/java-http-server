import java.util.Arrays;

public class PartialContentServing extends ServingBase {

    private ServingBase server;
    private int rangeStart;
    private int rangeEnd;

    public PartialContentServing(ServingBase server, int startPoint, int endPoint){
        this.server = server;
        rangeStart = startPoint;
        rangeEnd = endPoint;
    }

    public byte[] getBytes(){
        byte[] fullBytes = server.getBytes();
        int[] byteRange = getByteRange(fullBytes.length);
        int start = byteRange[0];
        int end = byteRange[1];
        return Arrays.copyOfRange(fullBytes, start, end);
    }

    public int getHTTPCode(){
        return 206;
    }

    public String getContentType(){
        return server.getContentType();
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
        return new int[]{rangeStart, rangeEnd};
    }

    private boolean validByteRange(int bytePoint){
        return (bytePoint >= -128 && bytePoint < 127);
    }
}
