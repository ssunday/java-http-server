package Routes.Wiki.HTML;

public class ViewPostTemplate extends PageTemplate {

    private int postID;
    private String postContent;

    public ViewPostTemplate(int postID, String postTitle, String postContent){
        this.postID = postID;
        this.postContent = postContent;
        this.pageTitle = postTitle;
    }

    @Override
    protected String getBody(){
        String body = displayContent();
        body += editPostLink();
        body += homeLink();
        return body;
    }

    private String displayContent(){
        return String.format("<p>%s</p><hr><br>", postContent);
    }

    private String editPostLink(){
        return String.format("<a href='/edit-%s'>Edit Post</a><br><br>", postID);
    }

    private String homeLink(){
        return "<a href='/'>Home</a>";
    }
}
