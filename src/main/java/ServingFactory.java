import java.io.File;

public class ServingFactory {

    private static final String PARAMETER_PATH = "/parameters?";
    private static final String FORM_PATH = "/form";
    private static final String LOG_PATH = "/logs";
    private static final String OPTIONS_PATH = "/method_options";

    public static ServingBase getServer(String request, String baseDirectory){
        String path = HTTPRequestParser.getPath(request);
        String requestType = HTTPRequestParser.getRequestType(request);
        FilePaths filePaths = new FilePaths(baseDirectory);
        String fullPath = filePaths.getPathToServe(path);
        ServingBase server;
        if (isRoute(path, LOG_PATH)){
            String username = HTTPRequestParser.getAuthenticationUsername(request);
            String password = HTTPRequestParser.getAuthenticationPassword(request);
            server = new LogServing(username, password, requestType);
        }
        else if (isRoute(path, PARAMETER_PATH)){
            server = new ParameterServing(path, requestType);
        }
        else if (isRoute(path, FORM_PATH)){
            String params = HTTPRequestParser.getParams(request);
            server = new FormServing(params, requestType);
        }
        else if (isRoute(path, OPTIONS_PATH)){
            server = new MethodOptionServing(requestType);
        }
        else if (isDirectory(fullPath)){
            server = new DirectoryServing(path, filePaths, requestType);
        }
        else if (isFile(fullPath)){
            server = new FileServing(fullPath, requestType);
        }
        else {
            server = new NotFoundServing(requestType);
        }

        if (HTTPRequestParser.hasContentRange(request)){
            int startPoint = HTTPRequestParser.getContentRangeStart(request);
            int endPoint = HTTPRequestParser.getContentRangeEnd(request);
            server = new PartialContentServing(server, startPoint, endPoint, requestType);
        }

        return server;
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
