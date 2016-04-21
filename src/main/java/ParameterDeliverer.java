import java.net.URLDecoder;

public class ParameterDeliverer extends DelivererBase {

    private String pathToServe;

    public ParameterDeliverer(String path, String requestType){
        pathToServe = path;
        this.requestType = requestType;
        OPTIONS.add(HTTPVerbs.GET);
        OPTIONS.add(HTTPVerbs.HEAD);
        OPTIONS.add(HTTPVerbs.OPTIONS);
        contentType = "text/html";
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

    protected int getHTTPCode(){
        int httpCode = 200;
        if (!(OPTIONS.contains(requestType))){
            httpCode = 405;
        }
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
