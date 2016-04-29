package Deliverer;

import DelivererSupport.DirectoryListing;
import HTML.HTMLDirectoryDisplay;
import HTTP.HTTPCode;
import HTTP.HTTPVerbs;

public class DirectoryDeliverer extends DelivererBase {

    private HTMLDirectoryDisplay display;
    private DirectoryListing directoryListing;
    private String path;
    private String pathToServe;
    private String previousPath;

    public DirectoryDeliverer(String path, String pathToServe, String previousPath, String requestType){
        this.path = path;
        this.pathToServe = pathToServe;
        this.previousPath = previousPath;
        this.requestType = requestType;
        this.display = new HTMLDirectoryDisplay();
        this.directoryListing = new DirectoryListing();
        OPTIONS.add(HTTPVerbs.GET);
        OPTIONS.add(HTTPVerbs.HEAD);
        OPTIONS.add(HTTPVerbs.OPTIONS);
        contentType = "text/html";
    }

    @Override
    protected byte[] getBytes(){
        byte[] bytesToWrite;
        String contentToServe = getContentToServe();
        bytesToWrite = contentToServe.getBytes();
        return bytesToWrite;
    }

    protected HTTPCode getHTTPCode(){
        HTTPCode httpCode;
        httpCode = (OPTIONS.contains(requestType)) ? HTTPCode.OK : HTTPCode.METHOD_NOT_ALLOWED;
        return httpCode;
    }

    private String getContentToServe(){
        String backNavigation = getBackNavigation();
        String directoryListingDisplay = getDirectoryListing(pathToServe);
        String content = backNavigation + directoryListingDisplay;
        return content;
    }

    private String getDirectoryListing(String pathToServe){
        String[] listing = directoryListing.getListing(pathToServe);
        return display.displayListing(listing, path);
    }

    private String getBackNavigation(){
        return display.displayDirectoryBackNavigation(previousPath);
    }
}
