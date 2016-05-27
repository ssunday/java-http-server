package Routes.Wiki.HTML;

public class ViewNewPostTemplate extends PageTemplate {

    private String postTitle;
    private int postID;

    public ViewNewPostTemplate(String pageTitle, String postTitle, int postID){
        this.pageTitle = pageTitle;
        this.postTitle = postTitle;
        this.postID = postID;
    }

    @Override
    protected String getBody(){
        String body = getPostLink();
        body += getReturnHomeLink();
        return body;
    }

    private String getPostLink(){
        return String.format("<a href='/post-%s'>%s</a><br><br>", postID, postTitle);
    }

    private String getReturnHomeLink(){
        return "<a href='/'>Home Page</a><br><br>";
    }

}
