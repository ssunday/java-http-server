import java.net.URLDecoder;

public class ParameterServing extends ServingBase {

    public byte[] getBytes(String pathToServe){
        byte[] bytesToWrite;
        String[] parameters = getParameters(pathToServe);
        String[] parsedParameters = getParsedParameters(parameters);
        String wrappedParameters = HTMLParameterDisplay.htmlWrap(parsedParameters);
        bytesToWrite = wrappedParameters.getBytes();
        return bytesToWrite;
    }

    public String getContentType(String filePath){
        return "text/html";
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
