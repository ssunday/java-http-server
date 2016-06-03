package Wiki.Deliverer;

import Server.Deliverer.DelivererBase;
import Server.HTTP.HTTPCode;
import Server.HTTP.HTTPResponse;
import Server.HTTP.HTTPVerbs;
import Wiki.DelivererSupport.PathParser;
import Wiki.DelivererSupport.PostRecorder;
import Wiki.HTML.TempPostTemplate;

import java.util.Map;

public class TempPostDeliverer extends DelivererBase {

    private String title;
    private PostRecorder postRecorder;
    private int port;

    public TempPostDeliverer(String path, String requestType){
        this.title = PathParser.getTitleFromPath(path);
        this.requestType = requestType;
        this.OPTIONS.add(HTTPVerbs.GET);
    }

    public TempPostDeliverer(PostRecorder postRecorder, int port, Map params, String path, String requestType){
        this.postRecorder = postRecorder;
        this.port = port;
        this.title = PathParser.getTitleFromPath(path);
        this.requestType = requestType;
        this.OPTIONS.add(HTTPVerbs.POST);
        this.createPost(params);
    }

    @Override
    protected byte[] getBytes(){
        TempPostTemplate template = new TempPostTemplate(title);
        String html = template.renderPage();
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
        HTTPCode code = isPOST() ? HTTPCode.FOUND : HTTPCode.OK;
        return code;
    }

    private void createPost(Map params){
        String content = params.get("content").toString();
        postRecorder.createNewPost(title, content);
    }

    private boolean isPOST(){
        return requestType.equals(HTTPVerbs.POST);
    }
}
