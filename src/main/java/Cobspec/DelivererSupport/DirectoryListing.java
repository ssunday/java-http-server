package Cobspec.DelivererSupport;

import java.io.File;

public class DirectoryListing {

    public static String[] getListing(String path) {
        File directory = new File(path);
        File[] filesList = directory.listFiles();
        String[] itemsInDirectory = getStringListOfItemsInDirectory(filesList);
        return itemsInDirectory;
    }

    private static String[] getStringListOfItemsInDirectory(File[] filesList){
        String[] itemsInDirectory = new String[filesList.length];
        for (int i = 0; i < filesList.length; i++){
            File file = filesList[i];
            itemsInDirectory[i] = getItemName(file);
        }
        return itemsInDirectory;
    }

    private static String getItemName(File file){
        String itemName = file.getName();
        itemName += getEndSlashIfFolder(file);
        return itemName;
    }

    private static String getEndSlashIfFolder(File file){
        String end = "";
        if (file.isDirectory()){
            end = "/";
        }
        return end;
    }

}
