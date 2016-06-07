package HTMLTemplating;

public class PageTemplateMarkdown implements PageTemplateInterface {

    protected String pageTitle;

    public String renderPage(){
        String html = startHTML();
        html += wrapBodyWithXMP();
        html += endHTMLWithScript();
        return html;
    }

    protected String getBody(){
        return "";
    }

    protected String wrapBodyWithXMP(){
        return "<xmp theme='simplex'>" + getBody() + "</xmp>";
    }

    private String startHTML(){
        return "<!DOCTYPE html><html><head>"+
                "<title>" + pageTitle + "</title>" +
                "</head>";
    }

    private String endHTMLWithScript(){
        String script = "<script src=\"http://strapdownjs.com/v/0.2/strapdown.js\"></script>";
        return script + "</html>";
    }
}
