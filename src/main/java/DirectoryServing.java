public class DirectoryServing extends ServingBase {

    private DirectoryListing directoryListing;
    private HTMLDirectoryDisplay directoryDisplay;
    private FilePaths filePaths;

<<<<<<< HEAD:src/main/java/DirectoryServing.java
    public DirectoryServing(FilePaths filePaths) {
        directoryListing = new DirectoryListing();
        directoryDisplay = new HTMLDirectoryDisplay();
        this.filePaths = filePaths;
=======
    public DirectoryServing(String baseDirectory) {
        directoryListing = new DirectoryListing();
        directoryDisplay = new HTMLDirectoryDisplay();
        this.filePaths = new FilePaths(baseDirectory);
>>>>>>> origin/parameter-decoding:src/javahttpserver/main/DirectoryServing.java
    }

    public byte[] getBytes(String path) {
        byte[] bytesToWrite;
        String pathToServe = filePaths.getPathToServe(path);
        String previousDirectory = filePaths.getPreviousPathToLink(pathToServe);
        String pathFromBase = filePaths.getPathToLink(pathToServe);
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
