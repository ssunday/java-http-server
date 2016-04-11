package javahttpserver.main;


public class HTMLDirectoryDisplay {

    private final String HTML_START = "<html>"
            + "<head>"
            + "<style>body{padding:80px;}</style>"
            + "<title>Directory Listing</title>"
            + "<h1>Directory Listing</h1></head>"
             + "<body>";

    private final String HTML_END = "</body>" + "</html>";

    public String displayDirectoryBackNavigation(String previousDirectory){
        String backNavigation = "<h2><a href='" + previousDirectory + "'>" + previousDirectory + "</a></h2>";
        return backNavigation;
    }

    public String displayListing(String[] listing, String pathFromBase) {
        String html = HTML_START;
        for (String item: listing){
            html += displayItem(item, pathFromBase);
        }
        html += HTML_END;
        return html;
    }

    private String displayItem(String item, String pathFromBase) {
        String linkedItem = linkItem(item, pathFromBase);
        return "<p>" + linkedItem + "</p>";
    }

    private String linkItem(String item, String pathFromBase) {
        return "<a href='" + pathFromBase + item + "'>" + item + "</a>";
    }

}
