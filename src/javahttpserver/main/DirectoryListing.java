package javahttpserver.main;

import java.io.File;

public class DirectoryListing {

    public String[] getListing(String path){
        File directory = new File(path);
        File[] filesList = directory.listFiles();
        String[] filesInDirectory = new String[filesList.length];
        for (int i = 0; i < filesList.length; i++){
            File file = filesList[i];
            filesInDirectory[i] = file.getName();
            if (file.isDirectory()){
                filesInDirectory[i] += "/";
            }
        }
        return filesInDirectory;
    }

    public boolean isFolder(String path) {
        File file = new File(path);
        return file.isDirectory();
    }

}
