package Routes.Wiki.Deliverer;

import HTTP.HTTPVerbs;
import Routes.DelivererBase;
import Routes.Wiki.DelivererSupport.PathParser;
import Routes.Wiki.DelivererSupport.PostRecorder;
import Routes.Wiki.HTML.ViewPostTemplate;

public class ViewPostDeliverer extends DelivererBase{

    private int postID;
    private String title;
    private String content;

    public ViewPostDeliverer(PostRecorder postRecorder, String postPath, String requestType){
        this.requestType = requestType;
        this.postID = PathParser.getIDFromPath(postPath);
        this.OPTIONS.add(HTTPVerbs.GET);
        setPostTitleAndContent(postRecorder, postPath);
    }

    @Override
    protected byte[] getBytes() {
        byte[] bytes;
        ViewPostTemplate viewPostTemplate = new ViewPostTemplate(postID, title, content);
        String html = viewPostTemplate.renderPage();
        bytes = html.getBytes();
        return bytes;
    }

    private void setPostTitleAndContent(PostRecorder postRecorder, String postPath){
        String[] titleAndContent = postRecorder.getPostTitleAndContent(postID);
        title = titleAndContent[0];
        content = titleAndContent[1];
    }

}
