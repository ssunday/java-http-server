package javahttpserver.main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileServing {

    public byte[] getFileBytes(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        return fileBytes;
    }

    public boolean isFile(String path){
        File file = new File(path);
        return file.isFile();
    }

    public boolean isImage(String filePath){
        File file = new File(filePath);
        boolean isImage;
        try {
            Image image = ImageIO.read(file);
            if (image == null) {
                isImage = false;
            }
            else {
                isImage = true;
            }
        } catch(IOException ex) {
            isImage=false;
        }
        return isImage;
    }
}
