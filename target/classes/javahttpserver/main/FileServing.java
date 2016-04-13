package javahttpserver.main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileServing extends ServingBase {

    private FilePaths filePaths;

    public FileServing(String baseDirectory){
        this.filePaths = new FilePaths(baseDirectory);
    }

    public byte[] getBytes(String path) {
        String filePath = filePaths.getPathToServe(path);
        File file = new File(filePath);
        byte[] fileBytes;
        try {
            fileBytes = Files.readAllBytes(file.toPath());
        } catch (IOException eio){
            fileBytes = super.getBytes(filePath);
        }
        return fileBytes;
    }

    public String getContentType(String path){
        String contentType = "text/plain";
        if (isImage(path)){
            contentType = "image";
        }
        return contentType;
    }

    private boolean isImage(String path){
        String filePath = filePaths.getPathToServe(path);
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
