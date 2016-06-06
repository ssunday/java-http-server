package Cobspec.HTML;

import HTMLTemplating.PageTemplate;

public class FormDisplayTemplate extends PageTemplate {

    private String paramToDisplay;

    public FormDisplayTemplate(String paramToDisplay){
        this.pageTitle = "Form";
        this.paramToDisplay = paramToDisplay;
    }

    @Override
    public String getBody(){
        String html = "";
        html += getForm();
        if (paramToDisplay != null){
            html += displayParam(paramToDisplay);
        }
        return html;
    }

    private String getForm(){
        String form = "";
        form += formStart();
        form += inputBox();
        form += submitButton();
        form += formEnd();
        return form;
    }

    private String formStart(){
        return "<form action='/form' method='post'>";
    }

    private String inputBox(){
        return "<input type='text' name='data'>";
    }

    private String submitButton(){
        return "<input type='submit' value='Submit'>";
    }

    private String displayParam(String paramToDisplay){
        return "<br><p>" + paramToDisplay + "</p>";
    }

    private String formEnd(){
        return "</form>";
    }
}
