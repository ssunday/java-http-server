package Cobspec.HTML;

import HTMLTemplating.PageTemplate;

public class DirectoryDisplayTemplate extends PageTemplate{

    private String previousDirectory;
    private String[] listing;
    private String pathFromBase;

    public DirectoryDisplayTemplate(String[] listing, String pathFromBase, String previousDirectory){
        this.pageTitle = "Directory Listing";
        this.listing = listing;
        this.pathFromBase = pathFromBase;
        this.previousDirectory = previousDirectory;
    }

    @Override
    protected String getBody(){
        String body = displayDirectoryBackNavigation();
        body += displayListing();
        return body;
    }

    private String displayDirectoryBackNavigation(){
        String backNavigation = "<h2><a href='" + previousDirectory + "'>" + previousDirectory + "</a></h2>";
        return backNavigation;
    }

    private String displayListing() {
        String html = "";
        for (String item: listing){
            html += displayItem(item);
        }
        return html;
    }

    private String displayItem(String item) {
        String linkedItem = linkItem(item);
        return "<p>" + linkedItem + "</p>";
    }

    private String linkItem(String item) {
        return "<a href='" + pathFromBase + item + "'>" + item + "</a>";
    }

}
