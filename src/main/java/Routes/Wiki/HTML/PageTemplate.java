package Routes.Wiki.HTML;

public abstract class PageTemplate {

    protected String pageTitle;

    public String renderPage(){
        String html = startHTML();
        html += wrapBody();
        html += endHTML();
        return html;
    }

    private String wrapBody(){
        return "<body>" + getBody() + "</body>";
    }

    abstract String getBody();

    private String startHTML(){
        return "<HTML><HEAD>"+
                "<title>" + pageTitle + "</title>" +
                "<h1>" + pageTitle + "</h1></HEAD>";
    }

    private String endHTML(){
        return "</HTML>";
    }
}
