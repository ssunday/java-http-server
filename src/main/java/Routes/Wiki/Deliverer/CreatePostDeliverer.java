package Routes.Wiki.Deliverer;

import HTTP.HTTPVerbs;
import Routes.DelivererBase;
import Routes.Wiki.DelivererSupport.PostRecorder;
import Routes.Wiki.HTML.CreatePostTemplate;
import Routes.Wiki.HTML.ViewNewPostTemplate;

import java.util.Map;

public class CreatePostDeliverer extends DelivererBase {

    private PostRecorder postRecorder;
    private String title;
    private int latestPostID;

    public CreatePostDeliverer(String requestType){
        this.requestType = requestType;
        this.OPTIONS.add(HTTPVerbs.GET);
    }

    public CreatePostDeliverer(PostRecorder postRecorder, Map params, String requestType){
        this.requestType = requestType;
        this.postRecorder = postRecorder;
        this.OPTIONS.add(HTTPVerbs.POST);
        latestPostID = createPost(params);
    }

    @Override
    public byte[] getBytes(){
        String html = "";
        if (requestType.equals(HTTPVerbs.GET)){
            CreatePostTemplate createPostTemplate = new CreatePostTemplate();
            html = createPostTemplate.renderPage();

        } else if (requestType.equals(HTTPVerbs.POST)){
            ViewNewPostTemplate viewNewPostTemplate = new ViewNewPostTemplate(title, postRecorder.getLatestPostID());
            html = viewNewPostTemplate.renderPage();
        }
        byte[] bytes = html.getBytes();
        return bytes;
    }

    private int createPost(Map params){
        title = params.get("title").toString();
        String content = params.get("content").toString();
        postRecorder.createNewPost(title, content);
        return postRecorder.getLatestPostID();
    }

}
