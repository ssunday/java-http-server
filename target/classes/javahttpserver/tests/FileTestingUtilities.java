package javahttpserver.tests;

import java.io.File;

public class FileTestingUtilities {

    public static String testDirectory = System.getProperty("user.dir") + "/test-files/";

    public static void makePath(String path) throws Exception{
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    public static void makeFile(String path) throws Exception{
        File file = new File(path);
        if(!file.exists()){
            file.createNewFile();
        }
    }

    public static void clearPath(String path) throws Exception {
        File file = new File(path);
        file.delete();
    }
}
