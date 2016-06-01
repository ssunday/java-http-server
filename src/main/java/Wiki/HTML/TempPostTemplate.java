package Wiki.HTML;

public class TempPostTemplate extends PageTemplate {

    public TempPostTemplate(String pageTitle){
        this.pageTitle = pageTitle;
    }

    @Override
    protected String getBody(){
        String body = InputForms.formStart("/tmp/" + pageTitle);
        body += InputForms.getPostContentForm("");
        body += InputForms.getSubmitButton("Make Post");
        body += InputForms.formEnd();
        return body;
    }

}
