package javahttpserver.tests;

import javahttpserver.main.DirectoryListing;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DirectoryListingTest {

    private DirectoryListing directoryListing;

    private String directory = System.getProperty("user.dir");

    private String testDirectory;

    private void makePath(String path) throws Exception{
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    private void makeFile(String path) throws Exception{
        File file = new File(path);
        if(!file.exists()){
            file.createNewFile();
        }
    }

    private void clearPath(String path) throws Exception {
        File file = new File(path);
        file.delete();
    }

    @Before
    public void initialize() throws Exception{

        directoryListing = new DirectoryListing();
        testDirectory = directory + "/test-files/";
        makePath(testDirectory);

    }

    @Test
    public void testGetListingReturnsNullWhenEmpty() throws Exception {
        String emptyDirectory = testDirectory + "nothing/";
        makePath(emptyDirectory);
        assertArrayEquals("returns empty string array when directory is empty", new String[0], directoryListing.getListing(emptyDirectory));
        clearPath(emptyDirectory);

    }

    @Test
    public void testGetListingReturnsArrayWithSingleFileInDirectory() throws Exception {
        String singleFile = testDirectory + "single.txt";
        makeFile(singleFile);
        String[] listing = directoryListing.getListing(testDirectory);
        assertEquals("returns array of length one when single file exists", 1, listing.length);
        clearPath(singleFile);
    }


    @Test
    public void testGetListingReturnsArrayofTwoWhenFileAndDirectoryExist() throws Exception {
        String singleFile = testDirectory + "single.txt";
        makeFile(singleFile);
        String singleDirectory = testDirectory + "single/";
        makePath(singleDirectory);
        String[] listing = directoryListing.getListing(testDirectory);
        assertEquals("returns array of length two when both file and directory exist", 2, listing.length);
        clearPath(singleFile);
        clearPath(singleDirectory);
    }

    @Test

    public void testIsFolderReturnsTrueForFolder() throws Exception{
        boolean isFolder = directoryListing.isFolder(testDirectory);
        assertTrue("Returns true when path is a folder", isFolder);
    }

    @Test

    public void testIsFolderReturnsFalseForFile() throws Exception{
        String pathFile = testDirectory + "file.txt";
        makeFile(pathFile);
        boolean isFolder = directoryListing.isFolder(pathFile);
        assertFalse("Returns false when path is a file", isFolder);
        clearPath(pathFile);
    }

    @Test

    public void testIsFolderReturnsFalseForFileWithoutExtension() throws Exception{
        String pathFile = testDirectory + "file";
        makeFile(pathFile);
        boolean isFolder = directoryListing.isFolder(pathFile);
        assertFalse("Returns false when path is a file without extension", isFolder);
        clearPath(pathFile);
    }


    @After
    public void clearTestFiles() throws Exception {
        clearPath(testDirectory);
    }

}