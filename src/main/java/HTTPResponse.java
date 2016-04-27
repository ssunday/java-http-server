
public class HTTPResponse {

    private static final String SERVER_NAME = "Java HTTP Server";

    private String header, HTTPCode, contentType, contentLength, allow, authenticate, location, eTag;

    public HTTPResponse(){
        header = HTTPCode = contentType = contentLength = allow = authenticate = location = eTag = "";
    }

    public String getHeader(){
        header += HTTPCode;
        header += getServerName();
        header += contentType;
        header += contentLength;
        header += allow;
        header += authenticate;
        header += location;
        header += eTag;
        header += getConnectionClose();
        return header;
    }

    public void setHTTPCode(int code){
        HTTPCode = getHTTPCodeHeader(code);
    }

    public void setContentType(String contentType){
        this.contentType = "Content-Type: " + contentType + "\r\n";
    }

    public void setContentLength(int contentLength){
        this.contentLength = "Content-Length: " + contentLength + "\r\n";
    }

    public void setAllow(String[] options){
        this.allow = constructAllowLine(options);
    }

    public void setAuthenticateRealm(String realm){
        this.authenticate = "WWW-Authenticate: Basic realm='"+ realm + "'"+ "\r\n";
    }

    public void setLocation(String location){
        this.location = "Location: " + location + "\r\n";
    }

    public void setETag(String etag){
        this.eTag += "ETag: \"" + etag + '\"';
    }

    private String getServerName(){
        String serverNameLine = "Server: " + SERVER_NAME + "\r\n";
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
            case 418:
                HTTPCodeHeader += "418 I'm a teapot";
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
