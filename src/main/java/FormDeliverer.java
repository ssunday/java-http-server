public class FormDeliverer extends DelivererBase {

    private FormData formData;
    private HTMLFormDisplay htmlFormDisplay;

    private String params;

    public FormDeliverer(String params, String requestType){
        formData = new FormData();
        htmlFormDisplay = new HTMLFormDisplay();
        this.requestType = requestType;
        this.params = params;
        OPTIONS.add(HTTPVerbs.GET);
        OPTIONS.add(HTTPVerbs.POST);
        OPTIONS.add(HTTPVerbs.PUT);
        OPTIONS.add(HTTPVerbs.DELETE);
        OPTIONS.add(HTTPVerbs.HEAD);
        OPTIONS.add(HTTPVerbs.OPTIONS);
        contentType = "text/html";
    }

    @Override
    protected byte[] getBytes(){
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

    protected int getHTTPCode(){
        int httpCode;
        httpCode = (OPTIONS.contains(requestType)) ? 200 : 405;
        return httpCode;
    }

    private boolean isGet(){
        return requestType.equals(HTTPVerbs.GET);
    }

    private boolean isPost(){
        return requestType.equals(HTTPVerbs.POST);
    }

    private boolean isPut(){
        return requestType.equals(HTTPVerbs.PUT);
    }

    private boolean isDelete(){
        return requestType.equals(HTTPVerbs.DELETE);
    }

}
