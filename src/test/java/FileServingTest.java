import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class FileServingTest {

    private FileServing fileServing;

    private String testDirectory = FileTestingUtilities.testDirectory;

    @Before
    public void initialize() throws Exception{
        FilePaths filePaths = new FilePaths(testDirectory);
        fileServing = new FileServing(filePaths);
        FileTestingUtilities.makePath(testDirectory);
    }

    @Test

    public void testGetContentTypeReturnsTextPlainForFile() throws Exception{
        String fileName = "/single.txt";
        String file = testDirectory + fileName.substring(1);
        FileTestingUtilities.makeFile(file);
        assertEquals("Returns text/plain for file", "text/plain", fileServing.getContentType(fileName));
        FileTestingUtilities.clearPath(file);
    }

    @Test

    public void testGetContentTypeReturnsImageForImage() throws Exception{
        String imageName = "/image.jpg";
        String imagePath = testDirectory + imageName.substring(1);
        File imageOutputFile = new File(imagePath);
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        ImageIO.write(image, "jpg", imageOutputFile);
        assertEquals("Returns image for image", "image", fileServing.getContentType(imageName));
        FileTestingUtilities.clearPath(imagePath);
    }

    @Test
    public void testGetHTTPCode() {
        assertEquals("Returns 200", 200, fileServing.getHTTPCode());
    }

    @Test
    public void testgetBytesReturnsEmptyArrayOfBytesWhenFileIsEmpty() throws Exception{
        String fileName = "/emptytextfile";
        String file = testDirectory + fileName.substring(1);
        FileTestingUtilities.makeFile(file);
        assertArrayEquals("Returns empty array of filebytes when file is empty ", new byte[0], fileServing.getBytes(fileName));
        FileTestingUtilities.clearPath(file);
    }

    @Test
    public void testgetBytesReturnsNonEmptyWhenFileHasContent() throws Exception{
        String fileName = "/notemptyfile.txt";
        String file = testDirectory + fileName.substring(1);
        FileTestingUtilities.makeFile(file);
        String stringWritten = "a";
        byte[] byteCount;
        byteCount = stringWritten.getBytes();
        Path filePath = Paths.get(file);
        Files.write(filePath, byteCount);
        assertArrayEquals("Returns array of bytes when file has content", byteCount, fileServing.getBytes(fileName));
        FileTestingUtilities.clearPath(file);
    }

    @Test
    public void testgetBytesReturnsJPGImageBytes() throws Exception{
        String imageName = "/image.jpg";
        String imagePath = testDirectory + imageName.substring(1);
        File imageOutputFile = new File(imagePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        ImageIO.write(image, "jpg", baos);
        ImageIO.write(image, "jpg", imageOutputFile);
        byte[] imageBytes = baos.toByteArray();
        assertArrayEquals("GetImageBytes returns jpg image's bytes", imageBytes, fileServing.getBytes(imageName));
        FileTestingUtilities.clearPath(imagePath);
    }

    @After

    public void clearTestFiles() throws Exception{
        FileTestingUtilities.clearPath(testDirectory);
    }

}