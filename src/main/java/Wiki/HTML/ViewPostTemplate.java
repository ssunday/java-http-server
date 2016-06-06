package Wiki.HTML;

import HTMLTemplating.PageTemplate;

public class ViewPostTemplate extends PageTemplate {

    private int postID;
    private String postContent;
    private String[] postTitles;
    private String[] postIDs;

    public ViewPostTemplate(int postID, String postTitle, String postContent, String[] postTitles, String[] postIDs){
        this.postID = postID;
        this.postContent = postContent;
        this.pageTitle = postTitle;
        this.postTitles = postTitles;
        this.postIDs = postIDs;
    }

    @Override
    protected String getBody(){
        String body = displayContent();
        body += editPostLink();
        body += homeLink();
        return body;
    }

    private String displayContent(){
        String content = ContentProcessing.hyperlinkPostTitles(postContent, postTitles, postIDs);
        return String.format("<p>%s</p><hr><br>", content);
    }

    private String editPostLink(){
        return String.format("<a href='/edit/%s-%s'>Edit Post</a><br><br>", pageTitle, postID);
    }

    private String homeLink(){
        return "<a href='/'>Home</a>";
    }
}
