import java.io.File;

public class ServingFactory {

    private static String parameterPath = "/parameters?";

    private static String formPath = "/form";

    public static ServingBase getServer(String path, String requestType, String params, String baseDirectory) {
        FilePaths filePaths = new FilePaths(baseDirectory);
        String fullPath = filePaths.getPathToServe(path);
        if (isDirectory(fullPath)){
            return new DirectoryServing(path, filePaths);
        }
        else if (isFile(fullPath)){
            return new FileServing(fullPath);
        }
        else if (isRoute(path, parameterPath)){
            return new ParameterServing(path);
        }
        else if (isRoute(path, formPath)){
            return new FormServing(requestType, params);
        }
        else{
            return new NotFoundServing();
        }
    }

    private static boolean isRoute(String path, String route){
        return path.contains(route);
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
