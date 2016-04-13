public class HTMLParameterDisplay {

    private static final String HTML_START = "<HTML>"
            + "<HEAD><title>Parameters</title></HEAD>"
            + "<body>";

    private static final String HTML_END = "</body>" + "</HTML>";

    public static String htmlWrap(String[] params){
        String html = "";
        html += HTML_START;
        for (String param: params){
            html += addParam(param);
        }
        html += HTML_END;
        return html;
    }

    private static String addParam(String param){
        return "<p>" + param + "</p>";
    }
}
