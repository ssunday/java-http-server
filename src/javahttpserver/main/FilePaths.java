package javahttpserver.main;

public class FilePaths {

    private String baseDirectory;
    private String basePath = "/";

    public FilePaths(String baseDirectory){

        this.baseDirectory = baseDirectory;
    }


    public String getPathToServeFromRequest(String request){
        String path = getPath(request);
        if (baseDirectory != path) {
            path = baseDirectory + path.substring(1);
        }
        return path;
    }

    public String getPathToLink(String path) {
        int baseDirectoryLength = baseDirectory.length()-1;
        String linkPath = path;
        if (baseDirectoryLength != -1 && path.length() > baseDirectoryLength){
            linkPath = path.substring(baseDirectoryLength);
        }
        return linkPath;
    }

    public String getPath(String request) {
        String[] requestParts = request.split(" ");
        String directory = requestParts[1];
        return directory;
    }

    public String getPathOneLevelUp(String path){
        String pathUp = basePath;
        String pathWithoutLastSlash = path.substring(0,path.length()-2);
        if (!(path.equals(baseDirectory))){
            int secondtoLastSlash = pathWithoutLastSlash.lastIndexOf("/");
            pathUp = path.substring(0, secondtoLastSlash+1);
        }
        return pathUp;
    }

    public boolean isFolder(String path){
        boolean isFolder = false;
        int lastSlash = path.lastIndexOf("/");
        int pathLength = path.length();
        if (lastSlash == (pathLength - 1)){
            isFolder = true;
        }
        return isFolder;
    }

}
