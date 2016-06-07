package Wiki.HTML;

import HTMLTemplating.PageTemplateMarkdown;

public class TempPostTemplate extends PageTemplateMarkdown {

    public TempPostTemplate(String pageTitle){
        this.pageTitle = pageTitle;
    }

    @Override
    protected String getBody(){
        String body = InputForms.formStart("/tmp/" + pageTitle);
        body += InputForms.getPostContentForm("There is currently no content here.");
        body += InputForms.getSubmitButton("Make Post");
        body += InputForms.formEnd();
        return body;
    }

}
