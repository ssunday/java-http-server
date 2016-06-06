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
        return "<body>" + getBody() + "</body>";
    }

    private String startHTML(){
        return "<HTML><HEAD>"+
                "<style> body{padding: 80px;} </style>" +
                "<title>" + pageTitle + "</title>" +
                "<h1>" + pageTitle + "</h1></HEAD>";
    }

    private String endHTML(){
        return "</HTML>";
    }
}

