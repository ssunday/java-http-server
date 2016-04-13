package javahttpserver.main;

import java.io.File;

public class ServingFactory {

    private static String parameterPath = "/parameters?";

    public static ServingBase getServer(String path, String baseDirectory) {
        FilePaths filePaths = new FilePaths(baseDirectory);
        String fullPath = filePaths.getPathToServe(path);
        if (isDirectory(fullPath)){
            return new DirectoryServing(baseDirectory);
        }
        else if (isFile(fullPath)){
            return new FileServing(baseDirectory);
        }
        else if (isParametersDirectory(path)){
            return new ParameterServing();
        }
        else{
            return new NotFoundServing();
        }
    }

    private static boolean isParametersDirectory(String path){
        return path.contains(parameterPath);
    }

    private static boolean isDirectory(String path){
        File file = new File(path);
        return file.isDirectory();
    }

    private static boolean isFile(String path){
        File file = new File(path);
        return file.isFile();
    }
}
