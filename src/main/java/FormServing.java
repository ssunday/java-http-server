public class FormServing extends ServingBase {

    private final String GET = "GET";
    private final String POST = "POST";
    private final String PUT = "PUT";
    private final String DELETE = "DELETE";

    private FormData formData;
    private HTMLFormDisplay htmlFormDisplay;

    private String requestType;
    private String params;

    public FormServing(String params, String requestType){
        formData = new FormData();
        htmlFormDisplay = new HTMLFormDisplay();
        this.requestType = requestType;
        this.params = params;
        OPTIONS.add(GET);
        OPTIONS.add(POST);
        OPTIONS.add(PUT);
        OPTIONS.add(DELETE);
    }

    @Override
    public byte[] getBytes(){
        byte[] bytesToWrite;
        String paramsToDisplay = null;
        String html;
        if (isGet()){
            paramsToDisplay = formData.getData();
        }
        else if (isPost() || isPut()){
            formData.saveData(params);
            paramsToDisplay = params;
        }
        else if (isDelete()){
            formData.deleteData();
        }
        html = htmlFormDisplay.displayFormPage(paramsToDisplay);
        bytesToWrite = html.getBytes();
        return bytesToWrite;
    }

    @Override
    public int getHTTPCode(){
        int httpCode = super.getHTTPCode();
        if (!(OPTIONS.contains(requestType))){
            httpCode = 405;
        }
        return httpCode;
    }

    @Override
    public String getContentType() {
        return "text/html";
    }

    private boolean isGet(){
        return requestType.equals(GET);
    }

    private boolean isPost(){
        return requestType.equals(POST);
    }

    private boolean isPut(){
        return requestType.equals(PUT);
    }

    private boolean isDelete(){
        return requestType.equals(DELETE);
    }

}
