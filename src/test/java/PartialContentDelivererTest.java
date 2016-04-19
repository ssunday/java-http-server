import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class PartialContentDelivererTest {

    @Test
    public void testGetBytesReturnsPartialOfPassedInServerGetBytes() throws Exception {
        NotFoundDeliverer notFoundDeliverer = new NotFoundDeliverer("GET");
        PartialContentDeliverer server = new PartialContentDeliverer(notFoundDeliverer, 0, 20, "GET");
        byte[] directoryBytes = notFoundDeliverer.getBytes();
        byte[] partialBytes = Arrays.copyOfRange(directoryBytes, 0, 21);
        assertArrayEquals("Bytes returned is partial of bytes of passed in server", partialBytes, server.getBytes());

    }

    @Test
    public void testGetHTTPCode() throws Exception {
        PartialContentDeliverer server = new PartialContentDeliverer(new DirectoryDeliverer("/", new FilePaths("/"), "GET"), 0, 20, "GET");
        assertEquals("HTTP Code returns 206", 206, server.getHTTPCode());
    }

    @Test
    public void testGetContentTypeReturnsTypeOfServerPassedInTextHTML() throws Exception {
        DirectoryDeliverer directoryDeliverer = new DirectoryDeliverer("/", new FilePaths("/"), "GET");
        PartialContentDeliverer server = new PartialContentDeliverer(directoryDeliverer, 0, 20, "GET");
        assertEquals("Content type matches server passed in with text/html content type", directoryDeliverer.getContentType(), server.getContentType());
    }

    @Test
    public void testGetContentTypeReturnsTypeOfServerPassedInTextPlain() throws Exception {
        NotFoundDeliverer notFoundDeliverer = new NotFoundDeliverer("GET");
        PartialContentDeliverer server = new PartialContentDeliverer(notFoundDeliverer, 0, 20, "GET");
        assertEquals("Content type matches server passed in with text/plain content type", notFoundDeliverer.getContentType(), server.getContentType());
    }

    @Test
    public void testGetContentTypeReturnsTypeOfServerPassedInImageFile() throws Exception {
        FileTestingUtilities.makePath(FileTestingUtilities.testDirectory);
        String imageName = "/image.jpg";
        String imagePath = FileTestingUtilities.testDirectory + imageName.substring(1);
        File imageOutputFile = new File(imagePath);
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        ImageIO.write(image, "jpg", imageOutputFile);
        FileDeliverer fileDeliverer = new FileDeliverer(imagePath, "GET");
        assertEquals("Returns image for image", "image", fileDeliverer.getContentType());
        FileTestingUtilities.clearPath(imagePath);
        PartialContentDeliverer server = new PartialContentDeliverer(fileDeliverer, 0, 20, "GET");
        assertEquals("Content type matches server passed in with image type", fileDeliverer.getContentType(), server.getContentType());
        FileTestingUtilities.clearPath(imagePath);
        FileTestingUtilities.clearPath(FileTestingUtilities.testDirectory);
    }

}