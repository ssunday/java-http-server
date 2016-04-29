package HTTP;

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

    public void setHTTPCode(HTTPCode code){
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

    private String getHTTPCodeHeader(HTTPCode code){
        String HTTPCodeHeader = "HTTP/1.1 ";
        HTTPCodeHeader += code.toString();
        return HTTPCodeHeader + "\r\n";
    }

    private String getConnectionClose(){
        return "Connection: close" + "\r\n\r\n";
    }

}
