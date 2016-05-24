package Routes.Wiki.Deliverer;

import HTTP.HTTPVerbs;
import Routes.DelivererBase;
import Routes.Wiki.DelivererSupport.PostRecorder;
import Routes.Wiki.HTML.HomePageTemplate;

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
        String html = homePageTemplate.renderHomePage();
        return html.getBytes();
    }
}
