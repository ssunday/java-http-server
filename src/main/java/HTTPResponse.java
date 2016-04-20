
public class HTTPResponse {

    private static final String SERVER_NAME = "Java HTTP ServerSocketListener";
    private String header;

    public HTTPResponse(){
        header = "";
    }

    public String getHeader(){
        header += getServerName();
        header += getConnectionClose();
        return header;
    }

    public void setHTTPCode(int code){
        header += getHTTPCodeHeader(code);
    }

    public void setContentType(String contentType){
        header += "Content-Type: " + contentType + "\r\n";
    }

    public void setContentLength(int contentLength){
        header += "Content-Length: " + contentLength + "\r\n";
    }

    public void setAllow(String[] options){
        header += constructAllowLine(options);
    }

    public void setAuthenticateRealm(String realm){
        header += "WWW-Authenticate: Basic realm='"+ realm + "'"+ "\r\n";
    }

    public void setLocation(String location){
        header += "Location: " + location + "\r\n";
    }

    public void setETag(String etag){
        header += "ETag: \"" + etag + '\"';
    }

    private String getServerName(){
        String serverNameLine = "ServerSocketListener: " + SERVER_NAME + "\r\n";
        return serverNameLine;
    }

    private String constructAllowLine(String[] options){
        String allowField = "Allow: ";
        for (String option : options) {
            allowField += option + ",";
        }
        allowField = allowField.substring(0, allowField.length() - 1);
        return allowField + "\r\n";
    }

    private String getHTTPCodeHeader(int code){
        String HTTPCodeHeader = "HTTP/1.1 ";
        switch (code){
            case 200:
                HTTPCodeHeader += "200 OK";
                break;
            case 204:
                HTTPCodeHeader += "204 No Content";
                break;
            case 206:
                HTTPCodeHeader += "206 Partial Content";
                break;
            case 302:
                HTTPCodeHeader += "302 Found";
                break;
            case 401:
                HTTPCodeHeader += "401 Unauthorized";
                break;
            case 404:
                HTTPCodeHeader += "404 Not Found";
                break;
            case 405:
                HTTPCodeHeader += "405 Method Not Allowed";
                break;
            default:
                HTTPCodeHeader += "200 OK";
                break;
        }
        return HTTPCodeHeader + "\r\n";
    }

    private String getConnectionClose(){
        return "Connection: close" + "\r\n\r\n";
    }

}
