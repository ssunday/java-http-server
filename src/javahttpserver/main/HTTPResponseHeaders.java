package javahttpserver.main;

public class HTTPResponseHeaders {

    private static final String SERVER_NAME = "Java HTTP Server";

    public static String getTextAndHTMLHeader(int contentLength){
        String header = getHTTPCodeHeader(200);
        header += getServerName();
        header += getHTTPContentTypeAndLength("text/html", contentLength);
        header += "\r\n";
        return header;
    }

    public static String getImageHeader(int contentLength){
        String header = getHTTPCodeHeader(200);
        header += getServerName();
        header += getHTTPContentTypeAndLength("image" , contentLength);
        header += "\r\n";
        return header;
    }

    public static String get404Header(int contentLength){
        String header = getHTTPCodeHeader(404);
        header += getServerName();
        header += getHTTPContentTypeAndLength("text/html", contentLength);
        header += "\r\n";
        return header;
    }

    private static String getHTTPCodeHeader(int code){
        String HTTPCodeHeader;
        switch (code){
            case 200:
                HTTPCodeHeader = "HTTP/1.1 200 OK" + "\r\n";
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
