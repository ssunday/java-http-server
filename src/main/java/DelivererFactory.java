import java.io.File;

public class DelivererFactory {

    private static final String PARAMETER_PATH = "/parameters?";
    private static final String FORM_PATH = "/form";
    private static final String LOG_PATH = "/logs";
    private static final String OPTIONS_PATH = "/method_options";
    private static final String REDIRECT_PATH = "/redirect";

    public static DelivererBase getDeliverer(String request, int port, String baseDirectory){
        String path = HTTPRequestParser.getPath(request);
        String requestType = HTTPRequestParser.getRequestType(request);
        FilePaths filePaths = new FilePaths(baseDirectory);
        String fullPath = filePaths.getPathToServe(path);
        DelivererBase server;
        if (isRoute(path, LOG_PATH)){
            String username = HTTPRequestParser.getAuthenticationUsername(request);
            String password = HTTPRequestParser.getAuthenticationPassword(request);
            server = new LogDeliverer(username, password, requestType);
        }
        else if (isRoute(path, PARAMETER_PATH)){
            server = new ParameterDeliverer(path, requestType);
        }
        else if (isRoute(path, FORM_PATH)){
            String params = HTTPRequestParser.getParams(request);
            server = new FormDeliverer(params, requestType);
        }
        else if (isRoute(path, OPTIONS_PATH)){
            server = new MethodOptionDeliverer(requestType);
        }
        else if (isRoute(path, REDIRECT_PATH)){
            server = new RedirectDeliverer(port, requestType);
        }
        else if (isDirectory(fullPath)){
            server = new DirectoryDeliverer(path, filePaths, requestType);
        }
        else if (isFile(fullPath)){
            String etag = HTTPRequestParser.getEtag(request);
            if (etag != null){
                String patchedContent = HTTPRequestParser.getPatchContent(request);
                server = new FileDeliverer(fullPath, etag, patchedContent, requestType);
            }
            else
            {
                server = new FileDeliverer(fullPath, requestType);
            }
        }
        else {
            server = new NotFoundDeliverer(requestType);
        }

        if (HTTPRequestParser.hasContentRange(request)){
            int startPoint = HTTPRequestParser.getContentRangeStart(request);
            int endPoint = HTTPRequestParser.getContentRangeEnd(request);
            server = new PartialContentDeliverer(server, startPoint, endPoint, requestType);
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
