package Deliverer;

import HTTP.HTTPCode;
import HTTP.HTTPResponse;
import HTTP.HTTPVerbs;

import java.util.Arrays;

public class PartialContentDeliverer extends DelivererBase {

    private DelivererBase deliverer;
    private int rangeStart;
    private int rangeEnd;

    public PartialContentDeliverer(DelivererBase deliverer, int startPoint, int endPoint, String requestType){
        this.deliverer = deliverer;
        this.rangeStart = startPoint;
        this.rangeEnd = endPoint;
        this.requestType = requestType;
    }

    @Override
    protected byte[] getBytes(){
        byte[] fullBytes = deliverer.getBytes();
        int[] byteRange = getByteRange(fullBytes.length);
        int start = byteRange[0];
        int end = byteRange[1];
        return Arrays.copyOfRange(fullBytes, start, end);
    }

    @Override
    public String getResponseHeader(){
        response = new HTTPResponse();
        response.setHTTPCode(getHTTPCode());
        if (requestType.equals(HTTPVerbs.OPTIONS)){
            String[] options = new String[deliverer.OPTIONS.size()];
            response.setAllow(deliverer.OPTIONS.toArray(options));
        }
        response.setContentLength(getBytes().length-1);
        return response.getHeader();
    }

    protected HTTPCode getHTTPCode(){
        HTTPCode httpCode;
        httpCode = (deliverer.OPTIONS.contains(requestType)) ? HTTPCode.PARTIAL_CONTENT : HTTPCode.METHOD_NOT_ALLOWED;
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
        return new int[] {rangeStart, rangeEnd+1};
    }

    private boolean validByteRange(int bytePoint){
        return (bytePoint >= -128 && bytePoint < 127);
    }
}
