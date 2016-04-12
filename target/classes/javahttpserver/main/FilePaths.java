package javahttpserver.main;

public class FilePaths {

    private String baseDirectory;

    public FilePaths(String baseDirectory){

        this.baseDirectory = baseDirectory;
    }

    public String getPathToServeFromRequest(String request) {
        String path = getPath(request);
        if (baseDirectory != path) {
            path = baseDirectory + path.substring(1);
        }
        return path;
    }

    public String getPreviousPathToLink(String path) {
        String pathOneUp = getPathOneLevelUp(path);
        String pathToLink = getPathToLink(pathOneUp);
        return pathToLink;
    }

    private String getPath(String request) {
        int firstSpaceBreak = request.indexOf(" ");
        int lastSpaceBreak = request.lastIndexOf(" ");
        String directory = request.substring(firstSpaceBreak+1, lastSpaceBreak);
        directory = parseHTMLString(directory);
        return directory; }

    private String parseHTMLString(String htmlString){
        String parsedString = htmlString.replace("%20", " ");
        return parsedString;
    }

    public String getPathToLink(String path) {
        int baseDirectoryLength = baseDirectory.length() - 1;
        String linkPath = path;
        if (baseDirectoryLength != -1 && path.length() > baseDirectoryLength) {
            linkPath = path.substring(baseDirectoryLength);
        }
        return linkPath;
    }

    private String getPathOneLevelUp(String path){
        String pathUp = path;
        String pathWithoutLastSlash = path.substring(0,path.length()-2);
        if (!(path.equals(baseDirectory))){
            int secondtoLastSlash = pathWithoutLastSlash.lastIndexOf("/");
            pathUp = path.substring(0, secondtoLastSlash+1);
        }
        return pathUp;
    }


}
