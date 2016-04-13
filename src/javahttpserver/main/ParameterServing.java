package javahttpserver.main;
import java.net.URLDecoder;

public class ParameterServing extends ServingBase {

    public byte[] getBytes(String pathToServe){
        byte[] bytesToWrite;
        String[] parameters = getParameters(pathToServe);
        String decodedParameters = "";
        for (String param: parameters){
            decodedParameters += decodeParameter(param) + "\n";
        }
        String trimmedParameters = decodedParameters.trim();
        bytesToWrite = trimmedParameters.getBytes();
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

    private String decodeParameter(String param){
        String decodedParameter;
        try {
            decodedParameter = URLDecoder.decode(param, "UTF-8");
        } catch (Exception e){
            decodedParameter = "";
        }
        return decodedParameter;
    }

}
