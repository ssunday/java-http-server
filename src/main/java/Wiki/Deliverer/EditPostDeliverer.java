package Wiki.Deliverer;

import Server.Deliverer.DelivererBase;
import Server.HTTP.HTTPCode;
import Server.HTTP.HTTPResponse;
import Server.HTTP.HTTPVerbs;
import Wiki.DelivererSupport.PathParser;
import Wiki.DelivererSupport.PostRecorder;
import Wiki.HTML.EditPostTemplate;

import java.util.Map;

public class EditPostDeliverer extends DelivererBase {

    private PostRecorder postRecorder;
    private int postID;
    private String title;
    private int port;

    public EditPostDeliverer(PostRecorder postRecorder, String path, String requestType){
        this.requestType = requestType;
        this.postRecorder = postRecorder;
        this.postID = PathParser.getIDFromPath(path);
        this.OPTIONS.add(HTTPVerbs.GET);
        this.contentType = "text/html";
    }

    public EditPostDeliverer(PostRecorder postRecorder, String path, int port, Map params, String requestType){
        this.requestType = requestType;
        this.postRecorder = postRecorder;
        this.port = port;
        this.postID = PathParser.getIDFromPath(path);
        this.OPTIONS.add(HTTPVerbs.POST);
        this.contentType = "text/html";
        updatePost(params);
    }

    @Override
    protected byte[] getBytes(){
        byte[] bytes;
        String html = getEditPostHTML();
        bytes = html.getBytes();
        return bytes;
    }

    @Override
    public String getResponseHeader(){
        response = new HTTPResponse();
        response.setHTTPCode(getHTTPCode());
        response.setContentType(contentType);
        addAllowField();
        if (isPOST()){
            response.setLocation("http://localhost:" + port + "/post/" + title + "-" + postID);
        }
        response.setContentLength(getBytes().length);
        return response.getHeader();
    }

    @Override
    protected HTTPCode getHTTPCode(){
        HTTPCode code = isPOST() ? HTTPCode.FOUND : HTTPCode.OK;
        return code;
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

    private boolean isPOST(){
        return requestType.equals(HTTPVerbs.POST);
    }

}
