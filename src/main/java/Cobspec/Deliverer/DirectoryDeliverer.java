package Cobspec.Deliverer;

import Cobspec.HTML.HTMLDirectoryDisplay;
import Cobspec.DelivererSupport.DirectoryListing;
import Server.HTTP.HTTPCode;
import Server.HTTP.HTTPVerbs;
import Server.Deliverer.DelivererBase;

public class DirectoryDeliverer extends DelivererBase {

    private HTMLDirectoryDisplay display;
    private String path;
    private String pathToServe;
    private String previousPath;

    public DirectoryDeliverer(String path, String pathToServe, String previousPath, String requestType){
        this.path = path;
        this.pathToServe = pathToServe;
        this.previousPath = previousPath;
        this.requestType = requestType;
        this.display = new HTMLDirectoryDisplay();
        this.OPTIONS.add(HTTPVerbs.GET);
        this.contentType = "text/html";
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
        String[] listing = DirectoryListing.getListing(pathToServe);
        return display.displayListing(listing, path);
    }

    private String getBackNavigation(){
        return display.displayDirectoryBackNavigation(previousPath);
    }
}
