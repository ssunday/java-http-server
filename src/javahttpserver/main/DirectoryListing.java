package javahttpserver.main;

import java.io.File;

public class DirectoryListing {

    public String[] getListing(File directory){
        String[] filesInDirectory = directory.list();
        return filesInDirectory;
    }
}
