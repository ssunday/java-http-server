package javahttpserver.main;

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
        String pathOneUp = getPathOneLevelUp(path);
        String pathToLink = getPathToLink(pathOneUp);
        return pathToLink;
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
