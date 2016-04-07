package javahttpserver.main;


public class HTMLDirectoryDisplay {

    private final String HTML_START = "<html>"
            + "<head><title>Directory Listing</title>"
            + "<h1>Directory Listing</h1></head>"
             + "<body>";

    private final String HTML_END = "</body>" + "</html>";

    public String displayListing(String[] listing) {
        String html = HTML_START;
        for (String item: listing){
            html += displayItem(item);
        }
        html += HTML_END;
        return html;
    }

    private String displayItem(String item) {
        return "<p>" + item + "</p>";
    }

}
