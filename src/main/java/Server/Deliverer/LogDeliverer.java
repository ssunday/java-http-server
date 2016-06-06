package Server.Deliverer;

import Server.HTTP.HTTPCode;
import Server.HTTP.HTTPResponse;
import Server.HTTP.HTTPVerbs;
import Server.Logging.RequestLogger;

public class LogDeliverer extends DelivererBase {

    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "hunter2";

    private String username;
    private String password;
    private RequestLogger logger;

    public LogDeliverer(String logName, String username, String password, String requestType){
        this.username = username;
        this.password = password;
        this.logger = new RequestLogger(logName);
        this.requestType = requestType;
        this.OPTIONS.add(HTTPVerbs.GET);
        this.contentType = "text/plain";
    }

    @Override
    protected byte[] getBytes(){
        byte[] bytes;
        if (isAuthorized()){
            String content = logger.getLog();
            bytes = content.getBytes();
        }
        else{
            bytes = new byte[0];
        }
        return bytes;
    }

    @Override
    public String getResponseHeader(){
        response = new HTTPResponse();
        response.setHTTPCode(getHTTPCode());
        response.setContentType(contentType);
        if (!(isAuthorized())){
            response.setAuthenticateRealm("logs");
        }
        addAllowField();
        response.setContentLength(getBytes().length);
        return response.getHeader();
    }

    protected HTTPCode getHTTPCode(){
        HTTPCode httpCode;
        httpCode = (isAuthorized()) ? HTTPCode.OK: HTTPCode.UNAUTHORIZED;
        httpCode = (OPTIONS.contains(requestType)) ? httpCode : HTTPCode.METHOD_NOT_ALLOWED;
        return httpCode;
    }

    private boolean isAuthorized(){
        boolean isAdminUsername = username.equals(ADMIN_USERNAME);
        boolean isAdminPassword = password.equals(ADMIN_PASSWORD);
        return isAdminUsername && isAdminPassword;
    }

}
