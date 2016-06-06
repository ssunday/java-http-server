package Cobspec.Deliverer;

import Cobspec.Deliverer.FileDeliverer;
import TestingSupport.FileTestingUtilities;
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

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class FileDelivererTest {


    private String testDirectory = FileTestingUtilities.testDirectory;

    @Before
    public void setUp() throws Exception{
        FileTestingUtilities.makePath(testDirectory);
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
    public void testGetBytesReturnsEmptyWhenPatched() throws Exception{
        String content = "something";
        String fileName = "/notemptyfile.txt";
        String file = testDirectory + fileName.substring(1);
        FileTestingUtilities.makeFile(file);
        FileDeliverer fileDeliverer = new FileDeliverer(file, "!421412414", content,  "PATCH");
        assertArrayEquals("Get bytes returns empty bytes when patch", new byte[0], fileDeliverer.getBytes());
    }

    @Test
    public void testConstructorModifiesFileIfPatch() throws Exception{
        String content = "something";
        String fileName = "/patch.txt";
        String filePath = testDirectory + fileName.substring(1);
        FileTestingUtilities.makeFile(filePath);
        FileDeliverer fileDeliverer = new FileDeliverer(filePath, "!421412414", content,  "PATCH");
        File file = new File(filePath);
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        assertArrayEquals("Bytes of file equals content to patch", content.getBytes(), fileBytes);
        FileTestingUtilities.clearPath(filePath);
    }

    @Test
    public void testGetBytesReturnsModifiedContentBytes() throws Exception {
        String content = "something";
        String fileName = "/patch.txt";
        String filePath = testDirectory + fileName.substring(1);
        FileTestingUtilities.makeFile(filePath);
        FileDeliverer fileDeliverer = new FileDeliverer(filePath, "!421412414", content,  "PATCH");
        fileDeliverer = new FileDeliverer(filePath, "GET");
        assertArrayEquals("Bytes of file equals content to patch", content.getBytes(), fileDeliverer.getBytes());
        FileTestingUtilities.clearPath(filePath);
    }

    @Test
    public void testGetResponseHeaderReturns200HTTPCode() {
        FileDeliverer fileDeliverer = new FileDeliverer("somefile.txt", "GET");
        String response = fileDeliverer.getResponseHeader();
        assertThat(response, containsString("200 OK"));
    }

    @Test
    public void testGetResponseHeaderReturns204HTTPCodeWithEtag() throws Exception{
        String fileName = "/patch.txt";
        String filePath = testDirectory + fileName.substring(1);
        FileTestingUtilities.makeFile(filePath);
        FileDeliverer fileDeliverer = new FileDeliverer(filePath, "!421412414", "somecontent", "PATCH");
        String response = fileDeliverer.getResponseHeader();
        assertThat(response, containsString("204 No Content"));
        FileTestingUtilities.clearPath(filePath);
    }

    @Test
    public void testGetResponseHeaderIncludes405WhenNotGet(){
        FileDeliverer fileDeliverer = new FileDeliverer("somefile.txt","POST");
        String response = fileDeliverer.getResponseHeader();
        assertThat(response, containsString("405 Method Not Allowed"));
    }

    @Test
    public void testGetResponseHeaderIncludesTextPlainWhenTextFile(){
        FileDeliverer fileDeliverer = new FileDeliverer("somefile.txt", "GET");
        String response = fileDeliverer.getResponseHeader();
        assertThat(response, containsString("text/plain"));
    }

    @Test
    public void testGetResponseHeaderIncludesImageWhenImage() throws Exception{
        String imageName = "/image.jpg";
        String imagePath = testDirectory + imageName.substring(1);
        File imageOutputFile = new File(imagePath);
        BufferedImage image = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        ImageIO.write(image, "jpg", imageOutputFile);
        FileDeliverer fileDeliverer = new FileDeliverer(imagePath, "GET");
        String response = fileDeliverer.getResponseHeader();
        assertThat(response, containsString("image"));
        FileTestingUtilities.clearPath(imagePath);
    }

    @Test
    public void testGetResponseHeaderIncludesAllowFieldWhenOptions(){
        FileDeliverer fileDeliverer = new FileDeliverer("somefile.txt", "OPTIONS");
        String response = fileDeliverer.getResponseHeader();
        assertThat(response, containsString("Allow: "));
    }

    @Test
    public void testGetResponseHeaderIncludesEtagIfEtagWasProvided() throws Exception{
        String etag = "1232132gaffa";
        String fileName = "/patch.txt";
        String filePath = testDirectory + fileName.substring(1);
        FileTestingUtilities.makeFile(filePath);
        FileDeliverer fileDeliverer = new FileDeliverer(filePath, etag, "somecontent", "PATCH");
        String response = fileDeliverer.getResponseHeader();
        assertThat(response, containsString(etag));
        FileTestingUtilities.clearPath(filePath);
    }

    @Test
    public void testGetResponseHeaderDoesNotIncludeEtagIfNotProvided(){
        FileDeliverer fileDeliverer = new FileDeliverer("somefile.txt", "GET");
        String response = fileDeliverer.getResponseHeader();
        assertFalse("Etag is not present when not provided", response.contains("ETag: "));
    }

    @After
    public void clearTestFiles() throws Exception{
        FileTestingUtilities.clearPath(testDirectory);
    }

}