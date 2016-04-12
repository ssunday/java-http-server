package javahttpserver.main;

public class DirectoryServing extends ServingBase {

    private DirectoryListing directoryListing;
    private HTMLDirectoryDisplay directoryDisplay;

    public DirectoryServing() {
        directoryListing = new DirectoryListing();
        directoryDisplay = new HTMLDirectoryDisplay();
    }

    public byte[] getBytes(String pathToServe, String previousDirectory, String pathFromBase) {
        byte[] bytesToWrite;
        String[] listing = directoryListing.getListing(pathToServe);
        String backNavigation = directoryDisplay.displayDirectoryBackNavigation(previousDirectory);
        String html = directoryDisplay.displayListing(listing, pathFromBase);
        String content = backNavigation + html;
        bytesToWrite = content.getBytes();
        return bytesToWrite;
    }

    public String getContentType(String filePath){
        return "text/html";
    }
}
