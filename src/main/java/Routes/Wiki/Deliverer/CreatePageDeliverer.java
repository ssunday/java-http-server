package Routes.Wiki.Deliverer;

import Routes.DelivererBase;
import Routes.Wiki.DelivererSupport.PostRecorder;

public class CreatePageDeliverer extends DelivererBase {

    private PostRecorder postRecorder;

    public CreatePageDeliverer(PostRecorder postRecorder){
        this.postRecorder = postRecorder;
    }
}
