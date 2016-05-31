package Routes.Wiki;

import HTTP.HTTPRequestParser;
import HTTP.HTTPVerbs;
import Routes.Core.Deliverer.NotFoundDeliverer;
import Routes.DelivererBase;
import Routes.DelivererFactoryBase;
import Routes.Wiki.Deliverer.CreatePostDeliverer;
import Routes.Wiki.Deliverer.EditPostDeliverer;
import Routes.Wiki.Deliverer.HomePageDeliverer;
import Routes.Wiki.Deliverer.ViewPostDeliverer;
import Routes.Wiki.DelivererSupport.DataType;
import Routes.Wiki.DelivererSupport.PostRecorder;

import java.util.Map;

public class WikiDelivererFactory extends DelivererFactoryBase {

    private PostRecorder postRecorder;

    public WikiDelivererFactory(DataType dataType){
        postRecorder = new PostRecorder(dataType);
    }

    @Override
    public DelivererBase getDeliverer(String request){
        String path = HTTPRequestParser.getPath(request);
        String requestType = HTTPRequestParser.getRequestType(request);
        DelivererBase deliverer;
        if (isRoute(path,WikiRoutes.HOME) && path.length() == 1){
            deliverer = new HomePageDeliverer(postRecorder, requestType);
        }
        else if(isRoute(path, WikiRoutes.CREATE_POST)){
            deliverer = getCreatePostDeliverer(request,requestType);
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
            deliverer = new EditPostDeliverer(postRecorder,path, parsedParams, requestType);
        }
        return deliverer;
    }

    private DelivererBase getCreatePostDeliverer(String request, String requestType){
        DelivererBase deliverer;
        if (requestType.equals(HTTPVerbs.POST)){
            Map parsedParams = HTTPRequestParser.getParsedParams(request);
            deliverer = new CreatePostDeliverer(postRecorder,parsedParams,requestType);
        }
        else {
            deliverer = new CreatePostDeliverer(requestType);
        }
        return deliverer;
    }

}
