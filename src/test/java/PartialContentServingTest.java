import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class PartialContentServingTest {

    @Test
    public void testGetBytesReturnsPartialOfPassedInServerGetBytes() throws Exception {
        NotFoundServing notFoundServing = new NotFoundServing("GET");
        PartialContentServing server = new PartialContentServing(notFoundServing, 0, 20, "GET");
        byte[] directoryBytes = notFoundServing.getBytes();
        byte[] partialBytes = Arrays.copyOfRange(directoryBytes, 0, 21);
        assertArrayEquals("Bytes returned is partial of bytes of passed in server", partialBytes, server.getBytes());

    }

    @Test
    public void testGetHTTPCode() throws Exception {
        PartialContentServing server = new PartialContentServing(new DirectoryServing("/", new FilePaths("/"), "GET"), 0, 20, "GET");
        assertEquals("HTTP Code returns 206", 206, server.getHTTPCode());
    }

    @Test
    public void testGetContentTypeReturnsTypeOfServerPassedInTextHTML() throws Exception {
        DirectoryServing directoryServing = new DirectoryServing("/", new FilePaths("/"), "GET");
        PartialContentServing server = new PartialContentServing(directoryServing, 0, 20, "GET");
        assertEquals("Content type matches server passed in with text/html content type", directoryServing.getContentType(), server.getContentType());
    }

    @Test
    public void testGetContentTypeReturnsTypeOfServerPassedInTextPlain() throws Exception {
        NotFoundServing notFoundServing = new NotFoundServing("GET");
        PartialContentServing server = new PartialContentServing(notFoundServing, 0, 20, "GET");
        assertEquals("Content type matches server passed in with text/plain content type", notFoundServing.getContentType(), server.getContentType());
    }

    @Test
    public void testGetContentTypeReturnsTypeOfServerPassedInImageFile() throws Exception {
        FileTestingUtilities.makePath(FileTestingUtilities.testDirectory);
        String imageName = "/image.jpg";
        String imagePath = FileTestingUtilities.testDirectory + imageName.substring(1);
        File imageOutputFile = new File(imagePath);
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        ImageIO.write(image, "jpg", imageOutputFile);
        FileServing fileServing = new FileServing(imagePath, "GET");
        assertEquals("Returns image for image", "image", fileServing.getContentType());
        FileTestingUtilities.clearPath(imagePath);
        PartialContentServing server = new PartialContentServing(fileServing, 0, 20, "GET");
        assertEquals("Content type matches server passed in with image type", fileServing.getContentType(), server.getContentType());
        FileTestingUtilities.clearPath(imagePath);
        FileTestingUtilities.clearPath(FileTestingUtilities.testDirectory);
    }

}