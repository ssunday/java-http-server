package HTMLTemplating;

public class PageTemplate {

    protected String pageTitle;

    public String renderPage(){
        String html = startHTML();
        html += wrapBody();
        html += endHTML();
        return html;
    }

    protected String getBody(){
        return "";
    }

    private String wrapBody(){
        return "<xmp theme='simplex'>" + getBody() + "</xmp>";
    }

    private String startHTML(){
        return "<!DOCTYPE html><html><head>"+
                "<title>" + pageTitle + "</title>" +
                "</head>";
    }

    private String endHTML(){
        String script = "<script src=\"http://strapdownjs.com/v/0.2/strapdown.js\"></script>";
        return script + "</html>";
    }
}

