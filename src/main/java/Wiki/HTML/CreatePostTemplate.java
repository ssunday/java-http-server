package Wiki.HTML;

import HTMLTemplating.PageTemplate;

public class CreatePostTemplate extends PageTemplate {

    public CreatePostTemplate(){
        this.pageTitle = "Create Post";
    }

    @Override
    protected String getBody(){
        String body = InputForms.formStart("/create-post");
        body += InputForms.getTitleForm("Your_Post_Title_Must_Use_Underscores");
        body += InputForms.getPostContentForm("You can use Markdown and HTML");
        body += InputForms.getSubmitButton("Create Post");
        body += InputForms.formEnd();
        return body;
    }

}
