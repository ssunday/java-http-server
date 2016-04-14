

public class HTTPRequestParser {

    private static final String POST = "POST";
    private static final String PUT = "PUT";

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
        if (isPostOrPut(requestLine)){
            params = requestHeaders[requestHeaders.length-1];
        }
        return params;
    }

    private static boolean isPostOrPut(String requestLine){
        return requestLine.contains(POST) || requestLine.contains(PUT);
    }

    private static String[] getRequestLines(String request){
        return request.split("\\r?\\n");
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
