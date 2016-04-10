package javahttpserver.main;

import java.io.File;

public class DirectoryListing {

    public String[] getListing(String path){
        File directory = new File(path);
        File[] filesList = directory.listFiles();
        try {
            String[] filesInDirectory = new String[filesList.length];
            for (int i = 0; i < filesList.length; i++){
                File file = filesList[i];
                filesInDirectory[i] = file.getName();
                if (file.isDirectory()){
                    filesInDirectory[i] += "/";
                }

            }
            return filesInDirectory;
        } catch (NullPointerException e) {
            return null;
        }

    }

}
