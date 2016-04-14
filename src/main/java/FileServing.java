import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileServing extends ServingBase {

    private File file;

    public FileServing(String filePath) {
        file = new File(filePath);
    }

    @Override
    public byte[] getBytes() {
        byte[] bytes = getFileBytes();
        return bytes;
    }

    @Override
    public String getContentType(){
        String contentType = "text/plain";
        if (isImage()){
            contentType = "image";
        }
        return contentType;
    }

    private byte[] getFileBytes(){
        byte[] fileBytes;
        try {
            fileBytes = Files.readAllBytes(file.toPath());
        } catch (IOException eio){
            fileBytes = super.getBytes();
        }
        return fileBytes;
    }

    private boolean isImage(){
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
