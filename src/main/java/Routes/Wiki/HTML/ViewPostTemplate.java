package Routes.Wiki.HTML;

public class ViewPostTemplate extends PageTemplate {

    private String postTitle;
    private String postContent;

    public ViewPostTemplate(String postTitle, String postContent){
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.pageTitle = postTitle;
    }

    @Override
    protected String getBody(){
        String body = displayTitle();
        body += displayContent();
        body += homeLink();
        return body;
    }

    private String displayTitle(){
        return String.format("<h2>%s</h2><br>", postTitle);
    }

    private String displayContent(){
        return String.format("<p>%s</h2><p><br><br>", postContent);
    }

    private String homeLink(){
        return "<a href='/'>Home</a>";
    }
}
