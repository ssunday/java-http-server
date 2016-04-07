package javahttpserver.main;

import java.io.File;

public class DirectoryListing {

    public String[] getListing(String path){
        File directory = new File(path);
        String[] filesInDirectory = directory.list();
        return filesInDirectory;
    }
}
