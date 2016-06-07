package Wiki.HTML;

import HTMLTemplating.PageTemplateMarkdown;

public class DeletePostTemplate extends PageTemplateMarkdown {

    private int postID;
    private String postTitle;

    public DeletePostTemplate(String postTitle, int postID){
        this.postTitle = postTitle;
        this.postID = postID;
        this.pageTitle = "Deleted Post";
    }

    @Override
    protected String getBody(){
        String body = String.format("##You have successfully deleted the post %s with ID of %s. <br><br>", postTitle, postID);
        body += InputForms.homeLink();
        return body;
    }
}
