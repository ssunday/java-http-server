package Wiki.Deliverer;

import Server.HTTP.HTTPVerbs;
import Server.Deliverer.DelivererBase;
import Wiki.DelivererSupport.PostRecorder;
import Wiki.HTML.HomePageTemplate;

public class HomePageDeliverer extends DelivererBase {

    private PostRecorder postRecorder;

    public HomePageDeliverer(PostRecorder postRecorder, String requestType){
        this.requestType = requestType;
        this.postRecorder = postRecorder;
        this.OPTIONS.add(HTTPVerbs.GET);
    }

    @Override
    protected byte[] getBytes(){
        HomePageTemplate homePageTemplate = new HomePageTemplate(postRecorder.getAllPostIDs(), postRecorder.getAllPostTitles());
        String html = homePageTemplate.renderPage();
        return html.getBytes();
    }
}
