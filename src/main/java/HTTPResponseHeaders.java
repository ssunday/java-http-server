public class HTTPResponseHeaders {

    private static final String SERVER_NAME = "Java HTTP Server";

    public static String getHTTPHeader(int port, int code, String contentType, int contentLength, String[] options){
        String header = getHTTPCodeHeader(code, port);
        header += getServerName();
        header += getAllowField(options);
        header += getHTTPContentTypeAndLength(contentType, contentLength);
        header += getConnectionClose();
        return header;
    }

    private static String getConnectionClose(){
        return "Connection: close" + "\r\n\r\n";
    }

    private static String getHTTPCodeHeader(int code, int port){
        String HTTPCodeHeader;
        switch (code){
            case 200:
                HTTPCodeHeader = "HTTP/1.1 200 OK" + "\r\n";
                break;
            case 206:
                HTTPCodeHeader = "HTTP/1.1 206 Partial Content" + "\r\n";
                break;
            case 302:
                HTTPCodeHeader = "HTTP/1.1 302 Found" + "\r\n";
                HTTPCodeHeader += "Location: http://localhost:" + port + "/\r\n";
                break;
            case 401:
                HTTPCodeHeader = "HTTP/1.1 401 Unauthorized" + "\r\n";
                HTTPCodeHeader += "WWW-Authenticate: Basic realm='logs'"+ "\r\n";
                break;
            case 404:
                HTTPCodeHeader = "HTTP/1.1 404 Not Found" + "\r\n";
                break;
            case 405:
                HTTPCodeHeader = "HTTP/1.1 405 Method Not Allowed" + "\r\n";
                break;
            default:
                HTTPCodeHeader = "HTTP/1.1 200 OK" + "\r\n";
                break;
        }
        return HTTPCodeHeader;
    }

    private static String getServerName(){
        String serverNameLine = "Server: " + SERVER_NAME + "\r\n";
        return serverNameLine;
    }

    private static String getAllowField(String[] options){
        String allowField = "";
        if (optionsIsNotEmpty(options)){
            allowField = constructAllowLine(options);
        }
        return allowField;
    }

    private static boolean optionsIsNotEmpty(String[] options){
        return options.length > 0;
    }

    private static String constructAllowLine(String[] options){
        String allowField = "Allow: ";
        for (String option : options) {
            allowField += option + ",";
        }
        allowField = allowField.substring(0, allowField.length() - 1);
        return allowField + "\r\n";
    }

    private static String getHTTPContentTypeAndLength(String contentType, int contentLength){
        String contentTypeAndLength = "";
        if (contentType != null){
            contentTypeAndLength += "Content-Type: " + contentType + "\r\n";
        }
        contentTypeAndLength += "Content-Length: " + contentLength + "\r\n";
        return contentTypeAndLength;
    }

}
