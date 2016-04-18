import java.io.File;

public class ServingFactory {

    private static final String PARAMETER_PATH = "/parameters?";
    private static final String FORM_PATH = "/form";
    private static final String LOG_PATH = "/logs";

    public static ServingBase getServer(String request, String baseDirectory){
        String path = HTTPRequestParser.getPath(request);
        String requestType = HTTPRequestParser.getRequestType(request);
        FilePaths filePaths = new FilePaths(baseDirectory);
        String fullPath = filePaths.getPathToServe(path);
        if (isRoute(path, LOG_PATH)){
            String username = HTTPRequestParser.getAuthenticationUsername(request);
            String password = HTTPRequestParser.getAuthenticationPassword(request);
            return new LogServing(username, password);
        }
        else if (isRoute(path, PARAMETER_PATH)){
            return new ParameterServing(path);
        }
        else if (isRoute(path, FORM_PATH)){
            String params = HTTPRequestParser.getParams(request);
            return new FormServing(requestType, params);
        }
        else if (isDirectory(fullPath)){
            return new DirectoryServing(path, filePaths);
        }
        else if (isFile(fullPath)){
            return new FileServing(fullPath);
        }
        else{
            return new NotFoundServing();
        }
    }

    private static boolean isRoute(String path, String route){
        return path.contains(route);
    }

    private static boolean isDirectory(String path){
        File file = new File(path);
        return file.isDirectory();
    }

    private static boolean isFile(String path){
        File file = new File(path);
        return file.isFile();
    }
}
