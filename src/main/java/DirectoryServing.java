public class DirectoryServing extends ServingBase {

    private HTMLDirectoryDisplay display;
    private DirectoryListing directoryListing;
    private FilePaths filePaths;
    private String path;

    public DirectoryServing(String path, FilePaths filePaths) {
        this.path = path;
        this.filePaths = filePaths;
        display = new HTMLDirectoryDisplay();
        directoryListing = new DirectoryListing();
    }

    @Override
    public byte[] getBytes() {
        byte[] bytesToWrite;
        String contentToServe = getContentToServe();
        bytesToWrite = contentToServe.getBytes();
        return bytesToWrite;
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
