package Cobspec.Deliverer;

import Cobspec.HTML.HTMLParameterDisplay;
import Server.HTTP.HTTPCode;
import Server.HTTP.HTTPVerbs;
import Server.Deliverer.DelivererBase;

import java.net.URLDecoder;

public class ParameterDeliverer extends DelivererBase {

    private String pathToServe;

    public ParameterDeliverer(String path, String requestType){
        this.pathToServe = path;
        this.requestType = requestType;
        this.OPTIONS.add(HTTPVerbs.GET);
        this.contentType = "text/html";
    }

    @Override
    protected byte[] getBytes(){
        byte[] bytesToWrite;
        HTMLParameterDisplay display = new HTMLParameterDisplay();
        String[] parameters = getParameters(pathToServe);
        String[] parsedParameters = getParsedParameters(parameters);
        String wrappedParameters = display.htmlWrap(parsedParameters);
        bytesToWrite = wrappedParameters.getBytes();
        return bytesToWrite;
    }

    protected HTTPCode getHTTPCode(){
        HTTPCode httpCode;
        httpCode = (OPTIONS.contains(requestType)) ? HTTPCode.OK : HTTPCode.METHOD_NOT_ALLOWED;
        return httpCode;
    }

    private String[] getParameters(String path){
        String[] urlParts = path.split("\\?");
        String encodedParameters = urlParts[1];
        String[] splitParameters = encodedParameters.split("&");
        return splitParameters;
    }

    private String[] getParsedParameters(String[] parameters){
        String[] parsedParameters = new String[parameters.length];
        for (int i = 0; i < parameters.length; i++){
            parsedParameters[i] = parseParameter(parameters[i]);
        }
        return parsedParameters;
    }

    private String parseParameter(String param){
        String[] parameterParts = param.split("=");
        String decodedParameter = decodeParameter(parameterParts[1]);
        String paddedParameter = parameterParts[0] + " = " + decodedParameter;
        return paddedParameter;
    }

    private String decodeParameter(String param){
        String decodedParameter;
        try {
            decodedParameter = URLDecoder.decode(param, "UTF-8");
        } catch (Exception e){
            decodedParameter = param;
        }
        return decodedParameter;
    }

}
