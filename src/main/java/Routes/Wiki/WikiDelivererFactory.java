package Routes.Wiki;

import HTTP.HTTPRequestParser;
import Routes.Core.Deliverer.NotFoundDeliverer;
import Routes.DelivererBase;
import Routes.DelivererFactoryBase;
import Routes.Wiki.Deliverer.CreatePageDeliverer;
import Routes.Wiki.Deliverer.HomePageDeliverer;
import Routes.Wiki.DelivererSupport.DataType;
import Routes.Wiki.DelivererSupport.PostRecorder;

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
            deliverer = new CreatePageDeliverer(postRecorder);
        }
        else {
            deliverer = new NotFoundDeliverer(requestType);
        }
        return deliverer;
    }

}
