import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileDeliverer extends DelivererBase {

    private File file;
    private String requestType;

    public FileDeliverer(String filePath, String requestType) {
        file = new File(filePath);
        this.requestType = requestType;
        OPTIONS.add("GET");
    }

    @Override
    public byte[] getBytes(){
        byte[] fileBytes;
        try {
            fileBytes = Files.readAllBytes(file.toPath());
        } catch (IOException eio){
            fileBytes = super.getBytes();
        }
        return fileBytes;
    }

    @Override
    public int getHTTPCode(){
        int code = super.getHTTPCode();
        if (!(OPTIONS.contains(requestType))){
            code = 405;
        }
        return code;
    }

    @Override
    public String getContentType(){
        String contentType = "text/plain";
        if (isImage()){
            contentType = "image";
        }
        return contentType;
    }

    private boolean isImage(){
        boolean isImage;
        try {
            Image image = ImageIO.read(file);
            isImage = (image != null);
        } catch(IOException ex) {
            isImage = false;
        }
        return isImage;
    }
}
