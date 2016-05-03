
public class FilePaths {

    private String baseDirectory;

    public FilePaths(String baseDirectory){
        this.baseDirectory = baseDirectory;
    }

    public String getPathToServe(String path) {
        String pathToServe = path;
        if (baseDirectory != path) {
            pathToServe = baseDirectory + path.substring(1);
        }
        return pathToServe;
    }

    public String getPreviousPathToLink(String path) {
        String pathToLink = path;
        if (path != "/"){
            String fullPath = getPathToServe(path);
            String pathOneUp = getPathOneLevelUp(fullPath);
            pathToLink = getPathToLink(pathOneUp);
        }
        return pathToLink;
    }

    private String getPathOneLevelUp(String path){
        String pathUp = path;
        String pathWithoutLastSlash = path.substring(0,path.length()-2);
        if (!(path.equals(baseDirectory))){
            int secondToLastSlash = pathWithoutLastSlash.lastIndexOf("/");
            pathUp = path.substring(0, secondToLastSlash+1);
        }
        return pathUp;
    }

    private String getPathToLink(String path) {
        String linkPath = path;
        int baseDirectoryLength = baseDirectory.length() - 1;
        if (baseDirectoryLength != -1 && path.length() > baseDirectoryLength) {
            linkPath = path.substring(baseDirectoryLength);
        }
        return linkPath;
    }

}
