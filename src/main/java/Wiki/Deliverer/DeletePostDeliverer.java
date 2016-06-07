package Wiki.Deliverer;

import Server.Deliverer.DelivererBase;
import Server.HTTP.HTTPVerbs;
import Wiki.DelivererSupport.PathParser;
import Wiki.DelivererSupport.PostRecorder;
import Wiki.HTML.DeletePostTemplate;

public class DeletePostDeliverer extends DelivererBase{

    private PostRecorder postRecorder;
    private String title;
    private int postID;

    public DeletePostDeliverer(PostRecorder postRecorder, String postPath, String requestType){
        this.requestType = requestType;
        this.postRecorder = postRecorder;
        this.title = PathParser.getTitleFromPath(postPath);
        this.postID = PathParser.getIDFromPath(postPath);
        this.OPTIONS.add(HTTPVerbs.POST);
        this.contentType = "text/html";
        deletePost();
    }

    @Override
    protected byte[] getBytes(){
        DeletePostTemplate deletePostTemplate = new DeletePostTemplate(title, postID);
        String html = deletePostTemplate.renderPage();
        byte[] bytes = html.getBytes();
        return bytes;
    }

    private void deletePost(){
        postRecorder.deletePost(postID);
    }

}
