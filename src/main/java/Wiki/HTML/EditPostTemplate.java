package Wiki.HTML;

import HTMLTemplating.PageTemplate;

public class EditPostTemplate extends PageTemplate {

    private String title;
    private String content;
    private int postID;

    public EditPostTemplate(int postID, String title, String content){
        this.postID = postID;
        this.title = title;
        this.content = content;
        this.pageTitle = "Editing Post - " + title;
    }

    @Override
    public String getBody() {
        String body = InputForms.formStart(String.format("/edit/%s-%s", title, postID));
        body += InputForms.getTitleForm(title);
        body += InputForms.getPostContentForm(content);
        body += InputForms.getSubmitButton("Save Changes");
        body += InputForms.formEnd();
        return body;
    }

}
