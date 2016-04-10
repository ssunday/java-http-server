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

    public String displayListing(String[] listing) {
        String html = HTML_START;
        for (String item: listing){
            html += displayItem(item);
        }
        html += HTML_END;
        return html;
    }

    private String displayItem(String item) {
        String linkedItem = linkItem(item);
        return "<p>" + linkedItem + "</p>";
    }

    private String linkItem(String item) {
        return "<a href=" + "'/" + item + "'>" + item + "</a>";
    }

}
