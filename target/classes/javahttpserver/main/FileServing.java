package javahttpserver.main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileServing extends ServingBase {

    public byte[] getBytes(String filePath, String previousDirectory, String pathFromBase) {
        File file = new File(filePath);
        byte[] fileBytes;
        try {
            fileBytes = Files.readAllBytes(file.toPath());
        } catch (IOException eio){
            fileBytes = super.getBytes(filePath, previousDirectory, pathFromBase);
        }
        return fileBytes;
    }

    public String getContentType(String filePath){
        String contentType = "text/plain";
        if (isImage(filePath)){
            contentType = "image";
        }
        return contentType;
    }

    private boolean isImage(String filePath){
        File file = new File(filePath);
        boolean isImage = false;
        try {
            Image image = ImageIO.read(file);
            if (image != null) {
                isImage = true;
            }
        } catch(IOException ex) {
            isImage = false;
        }
        return isImage;
    }
}
