package Routes.Cobspec.HTML;

public class HTMLFormDisplay {

    private final String HTML_START = "<HTML>"
            + "<HEAD>"
            + "<style>body{padding:80px;}</style>"
            + "<title>Form</title><h1>Form</h1></HEAD>"
            + "<body>";

    private final String HTML_END = "</body>" + "</HTML>";

    public String displayFormPage(String paramToDisplay) {
        String formPage = HTML_START;
        formPage += getForm();
        if (paramToDisplay != null){
            formPage += displayParam(paramToDisplay);
        }
        formPage += HTML_END;
        return formPage;
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
