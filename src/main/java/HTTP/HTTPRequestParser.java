package HTTP;

import java.util.Base64;

public class HTTPRequestParser {

    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String PATCH = "PATCH";

    public static String getPath(String request) {
        String[] requestHeaders = getRequestLines(request);
        String pathLine = requestHeaders[0];
        String path;
        path = getPathFromHeaderLine(pathLine);
        path = parseEncodingIfFolder(path);
        return path;
    }

    public static String getRequestType(String request) {
        String[] requestHeaders = getRequestLines(request);
        String requestLine = requestHeaders[0];
        String[] requestParts = requestLine.split(" ");
        return requestParts[0];
    }

    public static String getParams(String request){
        String params = null;
        String[] requestHeaders = getRequestLines(request);
        String requestLine = requestHeaders[0];
        if (isRequestType(requestLine, POST) || isRequestType(requestLine, PUT)){
            params = requestHeaders[requestHeaders.length-1];
        }
        return params;
    }

    public static String getAuthenticationUsername(String request){
        String[] credentials = getAuthenticationCredentials(request);
        return credentials[0];
    }

    public static String getAuthenticationPassword(String request){
        String[] credentials = getAuthenticationCredentials(request);
        return credentials[1];
    }

    public static boolean hasContentRange(String request){
        String[] requestHeaders = getRequestLines(request);
        String rangeLine = findRequestLine("Range", requestHeaders);
        return (rangeLine != null);
    }

    public static int getContentRangeEnd(String request){
        return getContentRangeBeginningOrEnd(request, 1);
    }

    public static int getContentRangeStart(String request){
        return getContentRangeBeginningOrEnd(request, 0);
    }

    public static String getEtag(String request){
        String etag = null;
        String[] requestHeaders = getRequestLines(request);
        String requestLine = requestHeaders[0];
        if (isRequestType(requestLine, PATCH)){
            String ifMatchLine = findRequestLine("If-Match:", requestHeaders);
            etag = parseEtag(ifMatchLine);
        }
        return etag;
    }

    public static String getPatchContent(String request){
        String content;
        String[] requestHeaders = getRequestLines(request);
        content = requestHeaders[requestHeaders.length-1];
        return content;
    }

    private static String parseEtag(String ifMatchLine){
        String[] splitLine = ifMatchLine.split(" ");
        String rawTag = splitLine[1];
        String strippedQuotesTag = rawTag.substring(1,rawTag.length());
        return strippedQuotesTag;
    }

    private static int getContentRangeBeginningOrEnd(String request, int indexPosition){
        int position = -1000;
        String[] requestHeaders = getRequestLines(request);
        String rangeLine = findRequestLine("Range", requestHeaders);
        if (rangeLine != null){
            String[] splitLine = rangeLine.split("=");
            String range = splitLine[1];
            int splitCharPosition = range.indexOf("-");
            String[] rangeParts = range.split("-");
            if (rangeParts.length == 2){
                try {
                    position = Integer.parseInt(rangeParts[indexPosition]);
                }catch (Exception e){}
            }
            else{
                if ((indexPosition == 0 && (splitCharPosition > range.indexOf(rangeParts[0]))) ||
                    (indexPosition == 1 && (splitCharPosition < range.indexOf(rangeParts[0])))) {
                    position = Integer.parseInt(rangeParts[0]);
                }
            }
        }
        return position;
    }

    private static String[] getAuthenticationCredentials(String request){
        String[] credentials = new String[]{"",""};
        String[] requestHeaders = getRequestLines(request);
        String authorizationLine = findRequestLine("Authorization", requestHeaders);
        if (authorizationLine != null){
            credentials = parseAuthenticationInformation(authorizationLine);
        }
        return credentials;
    }

    private static String[] getRequestLines(String request){
        return request.split("\\r?\\n");
    }

    private static String findRequestLine(String requestOption, String[] requestHeaders){
        String requestLine = null;
        for (int i = 0; i < requestHeaders.length; i++){
            String line = requestHeaders[i];
            if (line.contains(requestOption)){
                requestLine = line;
            }
        }
        return requestLine;
    }

    private static String[] parseAuthenticationInformation(String authenticationLine){
        String[] authenticationParts = authenticationLine.split(" ");
        String encodedCredentials = authenticationParts[authenticationParts.length-1];
        byte[] decodedCredentialsBytes = Base64.getDecoder().decode(encodedCredentials);
        String decodedCredentials = new String(decodedCredentialsBytes);
        return decodedCredentials.split(":");
    }

    private static boolean isRequestType(String requestLine, String type){
        return requestLine.contains(type);
    }

    private static String parseEncodingIfFolder(String path){
        String parsedString = path;
        if (pathIsDirectoryOrFile(path)){
            parsedString = path.replace("%20", " ");
        }
        return parsedString;
    }

    private static boolean pathIsDirectoryOrFile(String path){
        return !(path.contains("?"));
    }

    private static String getPathFromHeaderLine(String pathLine) {
        int firstSpaceBreak = pathLine.indexOf(" ");
        int lastSpaceBreak = pathLine.lastIndexOf(" ");
        String path = pathLine.substring(firstSpaceBreak+1, lastSpaceBreak);
        return path;
    }


}
