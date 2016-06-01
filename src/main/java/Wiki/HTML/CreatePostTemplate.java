package Wiki.HTML;

public class CreatePostTemplate extends PageTemplate {

    public CreatePostTemplate(){
        this.pageTitle = "Create Post";
    }

    @Override
    protected String getBody(){
        String body = InputForms.formStart("/create-post");
        body += InputForms.getTitleForm("YourPostTitle");
        body += InputForms.getPostContentForm("");
        body += InputForms.getPostContentForm("Create Post");
        body += InputForms.formEnd();
        return body;
    }

}
