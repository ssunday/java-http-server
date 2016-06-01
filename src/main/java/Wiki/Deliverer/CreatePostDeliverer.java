package Wiki.Deliverer;

import Server.Deliverer.DelivererBase;
import Server.HTTP.HTTPCode;
import Server.HTTP.HTTPResponse;
import Server.HTTP.HTTPVerbs;
import Wiki.DelivererSupport.PostRecorder;
import Wiki.HTML.CreatePostTemplate;

import java.util.Map;

public class CreatePostDeliverer extends DelivererBase {

    private PostRecorder postRecorder;
    private String title;
    private int port;

    public CreatePostDeliverer(String requestType){
        this.requestType = requestType;
        this.OPTIONS.add(HTTPVerbs.GET);
    }

    public CreatePostDeliverer(PostRecorder postRecorder, int port, Map params, String requestType){
        this.requestType = requestType;
        this.postRecorder = postRecorder;
        this.port = port;
        this.OPTIONS.add(HTTPVerbs.POST);
        createPost(params);
    }

    @Override
    public byte[] getBytes(){
        CreatePostTemplate createPostTemplate = new CreatePostTemplate();
        String html = createPostTemplate.renderPage();
        byte[] bytes = html.getBytes();
        return bytes;
    }

    @Override
    public String getResponseHeader(){
        response = new HTTPResponse();
        response.setHTTPCode(getHTTPCode());
        response.setContentType(contentType);
        addAllowField();
        if (isPOST()){
            response.setLocation("http://localhost:" + port + "/post/" + title + "-" + postRecorder.getLatestPostID());
        }
        response.setContentLength(getBytes().length);
        return response.getHeader();
    }

    @Override
    protected HTTPCode getHTTPCode(){
        HTTPCode code = super.getHTTPCode();
        if (isPOST()){
            code = HTTPCode.FOUND;
        }
        return code;
    }

    private void createPost(Map params){
        title = params.get("title").toString();
        String content = params.get("content").toString();
        postRecorder.createNewPost(title, content);
    }

    private boolean isPOST(){
        return requestType.equals(HTTPVerbs.POST);
    }

}
