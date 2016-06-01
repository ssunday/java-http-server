package Wiki.Deliverer;

import Server.HTTP.HTTPVerbs;
import Server.Deliverer.DelivererBase;
import Wiki.DelivererSupport.PathParser;
import Wiki.DelivererSupport.PostRecorder;
import Wiki.HTML.ViewPostTemplate;

public class ViewPostDeliverer extends DelivererBase{

    private int postID;
    private String title;
    private String content;
    private PostRecorder postRecorder;

    public ViewPostDeliverer(PostRecorder postRecorder, String postPath, String requestType){
        this.requestType = requestType;
        this.postID = PathParser.getIDFromPath(postPath);
        this.OPTIONS.add(HTTPVerbs.GET);
        this.postRecorder = postRecorder;
        setPostTitleAndContent();
    }

    @Override
    protected byte[] getBytes() {
        byte[] bytes;
        ViewPostTemplate viewPostTemplate = new ViewPostTemplate(postID, title, content,
                                                                 postRecorder.getAllPostTitles(),
                                                                 postRecorder.getAllPostIDs());
        String html = viewPostTemplate.renderPage();
        bytes = html.getBytes();
        return bytes;
    }

    private void setPostTitleAndContent(){
        String[] titleAndContent = postRecorder.getPostTitleAndContent(postID);
        title = titleAndContent[0];
        content = titleAndContent[1];
    }

}
