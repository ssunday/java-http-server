package javahttpserver.main;

public class HTTPRequestParser {

    private static String[] HTTP_PATH_REQUEST_KEYWORDS = {"PUT", "GET", "POST", "DELETE"};

    public static String getPath(String request) {
        String[] requestHeaders = request.split("\\r?\\n");
        String pathLine = requestHeaders[0];
        String path;
        path = getPathFromHeaderLine(pathLine);
        path = parseEncodingIfFolder(path);
        return path;
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
