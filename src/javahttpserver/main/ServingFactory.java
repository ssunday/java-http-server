package javahttpserver.main;

import java.io.File;

public class ServingFactory {

    public static ServingBase getServer(String path){
        if (isDirectory(path)){
            return new DirectoryServing();
        }
        else if (isFile(path)){
            return new FileServing();
        }
        else{
            return new NotFoundServing();
        }
    }

    public static boolean isDirectory(String path){
        File file = new File(path);
        return file.isDirectory();
    }

    public static boolean isFile(String path){
        File file = new File(path);
        return file.isFile();
    }
}
