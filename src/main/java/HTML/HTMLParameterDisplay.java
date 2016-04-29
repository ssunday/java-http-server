package HTML;

public class HTMLParameterDisplay {

    private final String HTML_START = "<HTML>"
            + "<HEAD><title>Parameters</title></HEAD>"
            + "<body>";

    private final String HTML_END = "</body>" + "</HTML>";

    public String htmlWrap(String[] params){
        String html = "";
        html += HTML_START;
        for (String param: params){
            html += addParam(param);
        }
        html += HTML_END;
        return html;
    }

    private String addParam(String param){
        return "<p>" + param + "</p>";
    }
}
