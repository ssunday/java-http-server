package Routes.Wiki.Deliverer;

import HTTP.HTTPVerbs;
import Routes.DelivererBase;
import Routes.Wiki.DelivererSupport.PathParser;
import Routes.Wiki.DelivererSupport.PostRecorder;
import Routes.Wiki.HTML.EditPostTemplate;
import Routes.Wiki.HTML.ViewNewPostTemplate;

import java.util.Map;

public class EditPostDeliverer extends DelivererBase {

    private PostRecorder postRecorder;
    private int postID;
    private String title;

    public EditPostDeliverer(PostRecorder postRecorder, String path, String requestType){
        this.requestType = requestType;
        this.postRecorder = postRecorder;
        this.postID = PathParser.getIDFromPath(path);
        this.OPTIONS.add(HTTPVerbs.GET);
    }

    public EditPostDeliverer(PostRecorder postRecorder, String path, Map params, String requestType){
        this.requestType = requestType;
        this.postRecorder = postRecorder;
        this.postID = PathParser.getIDFromPath(path);
        this.OPTIONS.add(HTTPVerbs.POST);
        updatePost(params);
    }

    @Override
    protected byte[] getBytes(){
        byte[] bytes;
        String html = "";
        if (requestType.equals(HTTPVerbs.GET)){
            html = getEditPostHTML();

        } else if (requestType.equals(HTTPVerbs.POST)){
            html = getPostUpdatedHTML();
        }
        bytes = html.getBytes();
        return bytes;
    }

    private void updatePost(Map params){
        title = params.get("title").toString();
        String content = params.get("content").toString();
        postRecorder.updateExistingPost(postID, title, content);
    }

    private String getEditPostHTML(){
        String[] titleAndContent = postRecorder.getPostTitleAndContent(postID);
        String title = titleAndContent[0];
        String content = titleAndContent[1];
        EditPostTemplate editPostTemplate = new EditPostTemplate(postID, title, content);
        return editPostTemplate.renderPage();
    }

    private String getPostUpdatedHTML(){
        ViewNewPostTemplate viewNewPostTemplate = new ViewNewPostTemplate("Edited Post", title, postID);
        return viewNewPostTemplate.renderPage();
    }
}
