import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDeliverer extends DelivererBase {

    private File file;
    private String etag;

    public FileDeliverer(String filePath, String requestType){
        generalConstructor(filePath, requestType);
        etag = null;
    }

    public FileDeliverer(String filePath, String etag, String patchContent, String requestType) {
        generalConstructor(filePath, requestType);
        this.etag = etag;
        if (requestType.equals("PATCH") && !(isImage())){
            Path path = Paths.get(filePath);
            patchContent(path, patchContent);
        }
    }

    private void generalConstructor(String filePath, String requestType) {
        file = new File(filePath);
        this.requestType = requestType;
        OPTIONS.add(HTTPVerbs.GET);
        OPTIONS.add(HTTPVerbs.PATCH);
        OPTIONS.add(HTTPVerbs.OPTIONS);
        contentType = getContentType();
    }

    @Override
    public byte[] getBytes(){
        byte[] fileBytes = new byte[0];
        if (!(requestType.equals("PATCH"))){
            try {
                fileBytes = Files.readAllBytes(file.toPath());
            } catch (IOException eio){}
        }
        return fileBytes;
    }

    private void patchContent(Path filePath, String patchContent){
        byte[] byteCount = patchContent.getBytes();
        try{
            Files.write(filePath, byteCount);
        } catch (Exception e){}
    }

    @Override
    public String getResponseHeader(){
        response = new HTTPResponse();
        response.setHTTPCode(getHTTPCode());
        response.setContentType(contentType);
        if (etag != null){
            response.setETag(etag);
        }
        addAllowField();
        response.setContentLength(getBytes().length);
        return response.getHeader();
    }

    protected int getHTTPCode(){
        int httpCode = 200;
        if (requestType.equals("PATCH")){
            httpCode = 204;
        }
        httpCode = (OPTIONS.contains(requestType)) ? httpCode : 405;
        return httpCode;
    }

    private String getContentType(){
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
