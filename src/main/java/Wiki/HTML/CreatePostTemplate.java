package Wiki.HTML;

import HTMLTemplating.PageTemplateMarkdown;

public class CreatePostTemplate extends PageTemplateMarkdown {

    public CreatePostTemplate(){
        this.pageTitle = "Create Post";
    }

    @Override
    protected String getBody(){
        String body = InputForms.formStart("/create-post");
        body += InputForms.getTitleForm("Your_Post_Title_Must_Use_Underscores");
        body += InputForms.getPostContentForm("You can use Markdown and HTML. Your mileage may vary.");
        body += InputForms.getSubmitButton("Create Post");
        body += InputForms.formEnd();
        return body;
    }

}
