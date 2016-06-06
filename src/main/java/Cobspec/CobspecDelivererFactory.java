package Cobspec;

import Cobspec.Deliverer.*;
import Server.HTTP.HTTPRequestParser;
import Server.Deliverer.LogDeliverer;
import Server.Deliverer.NotFoundDeliverer;
import Server.Deliverer.DelivererBase;
import Server.DelivererFactoryBase;

import java.io.File;

public class CobspecDelivererFactory implements DelivererFactoryBase{

    private int port;
    private String baseDirectory;
    private String logName;

    public CobspecDelivererFactory(String logName, int port, String baseDirectory){
        this.logName = logName;
        this.port = port;
        this.baseDirectory = baseDirectory;
    }

    public DelivererBase getDeliverer(String request){
        String path = HTTPRequestParser.getPath(request);
        String requestType = HTTPRequestParser.getRequestType(request);
        FilePaths filePaths = new FilePaths(baseDirectory);
        String fullPath = filePaths.getPathToServe(path);
        DelivererBase server;
        if (isRoute(path, CobspecServerRoutes.LOG_PATH)){
            String username = HTTPRequestParser.getAuthenticationUsername(request);
            String password = HTTPRequestParser.getAuthenticationPassword(request);
            server = new LogDeliverer(logName, username, password, requestType);
        }
        else if (isRoute(path, CobspecServerRoutes.PARAMETER_PATH)){
            server = new ParameterDeliverer(path, requestType);
        }
        else if (isRoute(path, CobspecServerRoutes.FORM_PATH)){
            String params = HTTPRequestParser.getParams(request);
            server = new FormDeliverer(params, requestType);
        }
        else if (isRoute(path, CobspecServerRoutes.OPTIONS_PATH)){
            server = new MethodOptionDeliverer(requestType);
        }
        else if (isRoute(path, CobspecServerRoutes.REDIRECT_PATH)){
            server = new RedirectDeliverer(port, requestType);
        }
        else if (isRoute(path, CobspecServerRoutes.TEA_PATH) || isRoute(path, CobspecServerRoutes.COFFEE_PATH)){
            server = new TeapotDeliverer(path, requestType);
        }
        else if (isDirectory(fullPath)){
            String previousPath = filePaths.getPreviousPathToLink(path);
            server = new DirectoryDeliverer(path, fullPath, previousPath, requestType);
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

    public static boolean isRoute(String path, String route){
        return path.contains(route);
    }

    private boolean isDirectory(String path){
        File file = new File(path);
        return file.isDirectory();
    }

    private boolean isFile(String path){
        File file = new File(path);
        return file.isFile();
    }
}
