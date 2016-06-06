package Cobspec.Deliverer;

import Cobspec.DelivererSupport.FormData;
import Cobspec.HTML.FormDisplayTemplate;
import Server.HTTP.HTTPCode;
import Server.HTTP.HTTPVerbs;
import Server.Deliverer.DelivererBase;

public class FormDeliverer extends DelivererBase {

    private FormData formData;
    private FormDisplayTemplate htmlFormDisplay;

    private String params;

    public FormDeliverer(String params, String requestType){
        this.formData = new FormData();
        this.requestType = requestType;
        this.params = params;
        this.OPTIONS.add(HTTPVerbs.GET);
        this.OPTIONS.add(HTTPVerbs.POST);
        this.OPTIONS.add(HTTPVerbs.PUT);
        this.OPTIONS.add(HTTPVerbs.DELETE);
        this.contentType = "text/html";
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
        this.htmlFormDisplay = new FormDisplayTemplate(paramsToDisplay);
        html = htmlFormDisplay.renderPage();
        bytesToWrite = html.getBytes();
        return bytesToWrite;
    }

    protected HTTPCode getHTTPCode(){
        HTTPCode httpCode;
        httpCode = (OPTIONS.contains(requestType)) ? HTTPCode.OK : HTTPCode.METHOD_NOT_ALLOWED;
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
