public class LogDeliverer extends DelivererBase {

    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "hunter2";
    private RequestLogger logger;

    private String username;
    private String password;

    private String requestType;

    public LogDeliverer(String username, String password, String requestType){
        this.username = username;
        this.password = password;
        logger = new RequestLogger();
        this.requestType = requestType;
        OPTIONS.add("GET");
    }

    @Override
    public byte[] getBytes(){
        byte[] bytes;
        if (isAuthorized()){
            String content = logger.getLog();
            bytes = content.getBytes();
        }
        else{
            bytes = super.getBytes();
        }
        return bytes;
    }

    @Override
    public String getContentType(){
        return "text/plain";
    }

    @Override
    public int getHTTPCode(){
        int httpCode;
        httpCode = (isAuthorized()) ? 200: 401;
        if (!(OPTIONS.contains(requestType))){
            httpCode = 405;
        }
        return httpCode;
    }

    private boolean isAuthorized(){
        boolean isAdminUsername = username.equals(ADMIN_USERNAME);
        boolean isAdminPassword = password.equals(ADMIN_PASSWORD);
        return isAdminUsername && isAdminPassword;
    }

}
