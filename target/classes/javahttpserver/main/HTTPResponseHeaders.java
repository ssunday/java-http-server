package javahttpserver.main;

public class HTTPResponseHeaders {

    private static final String SERVER_NAME = "Java HTTP Server";

    public static String getHTTPHeader(int code, String contentType, int contentLength){
        String header = getHTTPCodeHeader(code);
        header += getServerName();
        header += getHTTPContentTypeAndLength(contentType, contentLength);
        header += getConnectionClose();
        header += "\r\n";
        return header;
    }

    private static String getConnectionClose(){
        return "Connection: close" + "\r\n";
    }

    private static String getHTTPCodeHeader(int code){
        String HTTPCodeHeader;
        switch (code){
            case 200:
                HTTPCodeHeader = "HTTP/1.1 200 OK" + "\r\n";
                break;
            case 206:
                HTTPCodeHeader = "HTTP/1.1 206 Partial Content" + "\r\n";
                break;
            case 404:
                HTTPCodeHeader = "HTTP/1.1 404 Not Found" + "\r\n";
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

    private static String getHTTPContentTypeAndLength(String contentType, int contentLength){
        String contentTypeAndLength = "";
        contentTypeAndLength += "Content-Type: " + contentType + "\r\n";
        contentTypeAndLength += "Content-Length: " + contentLength + "\r\n";
        return contentTypeAndLength;
    }

}
