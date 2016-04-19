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

public class FileDelivererTest {


    private String testDirectory = FileTestingUtilities.testDirectory;

    @Before
    public void setUp() throws Exception{
        FileTestingUtilities.makePath(testDirectory);
    }

    @Test
    public void testGetContentTypeReturnsTextPlainForFile() throws Exception{
        String fileName = "/single.txt";
        String file = testDirectory + fileName.substring(1);
        FileTestingUtilities.makeFile(file);
        FileDeliverer fileDeliverer = new FileDeliverer(file, "GET");
        assertEquals("Returns text/plain for file", "text/plain", fileDeliverer.getContentType());
        FileTestingUtilities.clearPath(file);
    }

    @Test
    public void testGetContentTypeReturnsImageForImage() throws Exception{
        String imageName = "/image.jpg";
        String imagePath = testDirectory + imageName.substring(1);
        File imageOutputFile = new File(imagePath);
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        ImageIO.write(image, "jpg", imageOutputFile);
        FileDeliverer fileDeliverer = new FileDeliverer(imagePath, "GET");
        assertEquals("Returns image for image", "image", fileDeliverer.getContentType());
        FileTestingUtilities.clearPath(imagePath);
    }

    @Test
    public void testGetHTTPCode() {
        FileDeliverer fileDeliverer = new FileDeliverer("somefile.txt", "GET");
        assertEquals("Returns 200", 200, fileDeliverer.getHTTPCode());
    }

    @Test
    public void testGetHTTPCodeReturns405IfNotGetPassedIn(){
        FileDeliverer fileDeliverer = new FileDeliverer("somefile.txt", "POST");
        assertEquals("Returns 405 when POST passed in", 405, fileDeliverer.getHTTPCode());
    }

    @Test
    public void testGetBytesReturnsEmptyArrayOfBytesWhenFileIsEmpty() throws Exception{
        String fileName = "/emptytextfile";
        String file = testDirectory + fileName.substring(1);
        FileTestingUtilities.makeFile(file);
        FileDeliverer fileDeliverer = new FileDeliverer(file, "GET");
        assertArrayEquals("Returns empty array of filebytes when file is empty ", new byte[0], fileDeliverer.getBytes());
        FileTestingUtilities.clearPath(file);
    }

    @Test
    public void testGetBytesReturnsNonEmptyWhenFileHasContent() throws Exception{
        String fileName = "/notemptyfile.txt";
        String file = testDirectory + fileName.substring(1);
        FileTestingUtilities.makeFile(file);
        String stringWritten = "a";
        byte[] byteCount;
        byteCount = stringWritten.getBytes();
        Path filePath = Paths.get(file);
        Files.write(filePath, byteCount);
        FileDeliverer fileDeliverer = new FileDeliverer(file, "GET");
        assertArrayEquals("Returns array of bytes when file has content", byteCount, fileDeliverer.getBytes());
        FileTestingUtilities.clearPath(file);
    }

    @Test
    public void testGetBytesReturnsJPGImageBytes() throws Exception{
        String imageName = "/image.jpg";
        String imagePath = testDirectory + imageName.substring(1);
        File imageOutputFile = new File(imagePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        ImageIO.write(image, "jpg", baos);
        ImageIO.write(image, "jpg", imageOutputFile);
        byte[] imageBytes = baos.toByteArray();
        FileDeliverer fileDeliverer = new FileDeliverer(imagePath, "GET");
        assertArrayEquals("GetImageBytes returns jpg image's bytes", imageBytes, fileDeliverer.getBytes());
        FileTestingUtilities.clearPath(imagePath);
    }

    @Test
    public void testGetMethodOptionsReturnsGETAndOPTIONS(){
        FileDeliverer fileDeliverer = new FileDeliverer("somefile.txt", "OPTIONS");
        assertArrayEquals("Method options returns array with only get and options when options is passed in", new String[]{"GET", "OPTIONS"}, fileDeliverer.getMethodOptions());
    }

    @After
    public void clearTestFiles() throws Exception{
        FileTestingUtilities.clearPath(testDirectory);
    }

}