package HTMLTemplating;

public class PageTemplate implements PageTemplateInterface {

    protected String pageTitle;

    public String renderPage(){
        String html = startHTML();
        html += wrapBodyWithBodyTags();
        html += endHTML();
        return html;
    }

    protected String getBody(){
        return "";
    }

    private String wrapBodyWithBodyTags(){
        return "<body>" + getBody() + "<body>";
    }

    private String startHTML(){
        return "<html><head>"+
                "<title>" + pageTitle + "</title>" +
                "<h1>" + pageTitle + "</h1>"+
                "</head>";
    }

    private String endHTML(){
        return "</html>";
    }
}

