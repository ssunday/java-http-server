public class DirectoryDeliverer extends DelivererBase {

    private HTMLDirectoryDisplay display;
    private DirectoryListing directoryListing;
    private FilePaths filePaths;
    private String path;
    private String requestType;

    public DirectoryDeliverer(String path, FilePaths filePaths, String requestType){
        this.path = path;
        this.filePaths = filePaths;
        this.requestType = requestType;
        display = new HTMLDirectoryDisplay();
        directoryListing = new DirectoryListing();
        OPTIONS.add("GET");
    }

    @Override
    public byte[] getBytes(){
        byte[] bytesToWrite;
        String contentToServe = getContentToServe();
        bytesToWrite = contentToServe.getBytes();
        return bytesToWrite;
    }

    @Override
    public int getHTTPCode(){
        int code = super.getHTTPCode();
        if (!(OPTIONS.contains(requestType))){
            code = 405;
        }
        return code;
    }

    @Override
    public String getContentType(){
        return "text/html";
    }

    private String getContentToServe(){
        String pathToServe = filePaths.getPathToServe(path);
        String backNavigation = getBackNavigation(pathToServe);
        String directoryListingDisplay = getDirectoryListing(pathToServe);
        String content = backNavigation + directoryListingDisplay;
        return content;
    }

    private String getDirectoryListing(String pathToServe){
        String pathFromBase = filePaths.getPathToLink(pathToServe);
        String[] listing = directoryListing.getListing(pathToServe);
        return display.displayListing(listing, pathFromBase);
    }

    private String getBackNavigation(String pathToServe){
        String previousDirectory = filePaths.getPreviousPathToLink(pathToServe);
        return display.displayDirectoryBackNavigation(previousDirectory);
    }
}
