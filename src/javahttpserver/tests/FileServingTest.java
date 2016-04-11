package javahttpserver.tests;

import javahttpserver.main.FileServing;
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

import static org.junit.Assert.*;

public class FileServingTest {

    private FileServing fileServing;

    private String testDirectory = FileTestingUtilities.testDirectory;

    @Before
    public void initialize() throws Exception{
        fileServing = new FileServing();
        FileTestingUtilities.makePath(testDirectory);
    }

    @Test
    public void testIsFileReturnsTrueForFile() throws Exception{
        String file = testDirectory + "single.txt";
        FileTestingUtilities.makeFile(file);
        assertTrue("Returns True when the path provided refers to a file", fileServing.isFile(file));
        FileTestingUtilities.clearPath(file);
    }

    @Test
    public void testIsFileReturnsTrueForFileWithoutextension() throws Exception{
        String emptyfile = testDirectory + "emptyfile";
        FileTestingUtilities.makeFile(emptyfile);
        assertTrue("Returns true when the path provided refers to a file without an extension", fileServing.isFile(emptyfile));
        FileTestingUtilities.clearPath(emptyfile);
    }

    @Test
    public void testIsFileReturnsFalseForFolder() throws Exception{
        String folder = testDirectory + "single/";
        FileTestingUtilities.makePath(folder);
        assertFalse("Returns false when the path provided refers to a folder", fileServing.isFile(folder));
        FileTestingUtilities.clearPath(folder);
    }

    @Test
    public void testIsImageReturnsTrueForJPGImageFile() throws Exception{
        String imagePath = testDirectory + "image.jpg";
        File imageOutputFile = new File(imagePath);
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        ImageIO.write(image, "jpg", imageOutputFile);
        assertTrue("IsImage returns true for jpg image file", fileServing.isImage(imagePath));
        FileTestingUtilities.clearPath(imagePath);
    }

    @Test
    public void testIsImageReturnsTrueForPNGImageFile() throws Exception{
        String imagePath = testDirectory + "image.png";
        File imageOutputFile = new File(imagePath);
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        ImageIO.write(image, "png", imageOutputFile);
        assertTrue("IsImage returns true for png image file", fileServing.isImage(imagePath));
        FileTestingUtilities.clearPath(imagePath);
    }

    @Test
    public void testIsImageReturnsTrueForGIFImageFile() throws Exception{
        String imagePath = testDirectory + "image.gif";
        File imageOutputFile = new File(imagePath);
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        ImageIO.write(image, "gif", imageOutputFile);
        assertTrue("IsImage returns true for gif image file", fileServing.isImage(imagePath));
        FileTestingUtilities.clearPath(imagePath);
    }

    @Test
    public void testIsImageReturnsFalseForTextFile() throws Exception{
        String txtFile = testDirectory + "txt.txt";
        FileTestingUtilities.makeFile(txtFile);
        assertFalse("IsImage returns false for text file", fileServing.isImage(txtFile));
        FileTestingUtilities.clearPath(txtFile);
    }

    @Test
    public void testIsImageReturnsFalseForFolder() throws Exception{
        String folder = testDirectory + "folder/";
        FileTestingUtilities.makePath(folder);
        assertFalse("IsImage returns false for folder", fileServing.isImage(folder));
        FileTestingUtilities.clearPath(folder);
    }


    @Test
    public void testGetFileBytesReturnsEmptyArrayOfBytesWhenFileIsEmpty() throws Exception{
        String file = testDirectory + "emptytextfile";
        FileTestingUtilities.makeFile(file);
        assertArrayEquals("Returns empty array of filebytes when file is empty ", new byte[0], fileServing.getFileBytes(file));
        FileTestingUtilities.clearPath(file);
    }

    @Test
    public void testGetFileBytesReturnsNonEmptyWhenFileHasContent() throws Exception{
        String file = testDirectory + "notemptyfile.txt";
        FileTestingUtilities.makeFile(file);
        String stringWritten = "a";
        byte[] byteCount;
        byteCount = stringWritten.getBytes();
        Path filePath = Paths.get(file);
        Files.write(filePath, byteCount);
        assertArrayEquals("Returns array of bytes when file has content", byteCount, fileServing.getFileBytes(file));
        FileTestingUtilities.clearPath(file);
    }

    @Test
    public void testGetFileBytesReturnsJPGImageBytes() throws Exception{
        String imagePath = testDirectory + "image.jpg";
        File imageOutputFile = new File(imagePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        ImageIO.write(image, "jpg", baos);
        ImageIO.write(image, "jpg", imageOutputFile);
        byte[] imageBytes = baos.toByteArray();
        assertArrayEquals("GetImageBytes returns jpg image's bytes", imageBytes, fileServing.getFileBytes(imagePath));
        FileTestingUtilities.clearPath(imagePath);
    }

    @After

    public void clearTestFiles() throws Exception{
        FileTestingUtilities.clearPath(testDirectory);
    }

}