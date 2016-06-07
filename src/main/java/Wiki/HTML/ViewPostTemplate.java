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
        body += deletePostButton();
        body += InputForms.homeLink();
        return body;
    }

    private String displayContent(){
        String content = ContentProcessing.hyperlinkPostTitles(postContent, postTitles, postIDs);
        return String.format("%s\n***\n", content);
    }

    private String editPostLink(){
        return String.format("<button><a href='/edit/%s-%s'>Edit Post</a></button><br><br>", pageTitle, postID);
    }

    private String deletePostButton(){
        String deleteButton = String.format("<form action='/delete/%s-%s' method='post'>", pageTitle, postID);
        deleteButton += "<input type='submit' value='Delete Post'><br>";
        deleteButton += "</form>";
        return deleteButton;
    }

}
