package Wiki;

import Server.Deliverer.LogDeliverer;
import Server.HTTP.HTTPRequestParser;
import Server.HTTP.HTTPVerbs;
import Server.Deliverer.NotFoundDeliverer;
import Server.Deliverer.DelivererBase;
import Server.DelivererFactoryBase;
import Wiki.Deliverer.*;
import Wiki.DelivererSupport.DataType;
import Wiki.DelivererSupport.PostRecorder;

import java.util.Map;

public class WikiDelivererFactory extends DelivererFactoryBase {

    private PostRecorder postRecorder;
    private int port;

    public WikiDelivererFactory(DataType dataType, int port){
        this.postRecorder = new PostRecorder(dataType);
        this.port = port;
    }

    @Override
    public DelivererBase getDeliverer(String request){
        String path = HTTPRequestParser.getPath(request);
        String requestType = HTTPRequestParser.getRequestType(request);
        DelivererBase deliverer;
        if (isRoute(path,WikiRoutes.HOME) && path.length() == 1){
            deliverer = new HomePageDeliverer(postRecorder, requestType);
        }
        else if (isRoute(path, WikiRoutes.LOGS)){
            String username = HTTPRequestParser.getAuthenticationUsername(request);
            String password = HTTPRequestParser.getAuthenticationPassword(request);
            deliverer = new LogDeliverer(username, password, requestType);
        }
        else if(isRoute(path, WikiRoutes.CREATE_POST)){
            deliverer = getCreatePostDeliverer(request,requestType);
        }
        else if(isRoute(path, WikiRoutes.NOT_CREATED_POST)){
            deliverer = getTempPostDeliverer(request, path, requestType);
        }
        else if(isRoute(path, WikiRoutes.POST)){
            deliverer = new ViewPostDeliverer(postRecorder, path, requestType);
        }
        else if(isRoute(path, WikiRoutes.EDIT_POST)){
            deliverer = getEditPostDeliverer(request, path, requestType);
        }
        else {
            deliverer = new NotFoundDeliverer(requestType);
        }
        return deliverer;
    }

    private DelivererBase getEditPostDeliverer(String request, String path, String requestType){
        DelivererBase deliverer;
        if (requestType.equals(HTTPVerbs.GET)){
            deliverer = new EditPostDeliverer(postRecorder,path,requestType);
        }else{
            Map parsedParams = HTTPRequestParser.getParsedParams(request);
            deliverer = new EditPostDeliverer(postRecorder,path, port, parsedParams, requestType);
        }
        return deliverer;
    }

    private DelivererBase getCreatePostDeliverer(String request, String requestType){
        DelivererBase deliverer;
        if (requestType.equals(HTTPVerbs.POST)){
            Map parsedParams = HTTPRequestParser.getParsedParams(request);
            deliverer = new CreatePostDeliverer(postRecorder, port, parsedParams,requestType);
        }
        else {
            deliverer = new CreatePostDeliverer(requestType);
        }
        return deliverer;
    }

    private DelivererBase getTempPostDeliverer(String request, String route, String requestType){
        DelivererBase deliverer;
        if (requestType.equals(HTTPVerbs.POST)){
            Map parsedParams = HTTPRequestParser.getParsedParams(request);
            deliverer = new TempPostDeliverer(postRecorder, port, parsedParams, route, requestType);
        }
        else {
            deliverer = new TempPostDeliverer(route, requestType);
        }
        return deliverer;
    }

}
