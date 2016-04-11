package javahttpserver.tests;

import javahttpserver.main.DirectoryListing;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class DirectoryListingTest {

    private DirectoryListing directoryListing;

    private String testDirectory = FileTestingUtilities.testDirectory;

    @Before
    public void initialize() throws Exception{
        directoryListing = new DirectoryListing();
        FileTestingUtilities.makePath(testDirectory);
    }

    @Test
    public void testGetListingReturnsNullWhenEmpty() throws Exception {
        String emptyDirectory = testDirectory + "nothing/";
        FileTestingUtilities.makePath(emptyDirectory);
        assertArrayEquals("returns empty string array when directory is empty", new String[0], directoryListing.getListing(emptyDirectory));
        FileTestingUtilities.clearPath(emptyDirectory);
    }

    @Test
    public void testGetListingReturnsArrayWithSingleFileInDirectory() throws Exception {
        String singleFile = testDirectory + "single.txt";
        FileTestingUtilities.makeFile(singleFile);
        String[] listing = directoryListing.getListing(testDirectory);
        assertEquals("returns array of length one when single file exists", 1, listing.length);
        FileTestingUtilities.clearPath(singleFile);
    }


    @Test
    public void testGetListingReturnsArrayofTwoWhenFileAndDirectoryExist() throws Exception {
        String singleFile = testDirectory + "single.txt";
        FileTestingUtilities.makeFile(singleFile);
        String singleDirectory = testDirectory + "single/";
        FileTestingUtilities.makePath(singleDirectory);
        String[] listing = directoryListing.getListing(testDirectory);
        assertEquals("returns array of length two when both file and directory exist", 2, listing.length);
        FileTestingUtilities.clearPath(singleFile);
        FileTestingUtilities.clearPath(singleDirectory);
    }

    @Test

    public void testIsFolderReturnsTrueForFolder() throws Exception{
        boolean isFolder = directoryListing.isFolder(testDirectory);
        assertTrue("Returns true when path is a folder", isFolder);
    }

    @Test

    public void testIsFolderReturnsFalseForFile() throws Exception{
        String pathFile = testDirectory + "file.txt";
        FileTestingUtilities.makeFile(pathFile);
        boolean isFolder = directoryListing.isFolder(pathFile);
        assertFalse("Returns false when path is a file", isFolder);
        FileTestingUtilities.clearPath(pathFile);
    }

    @Test

    public void testIsFolderReturnsFalseForFileWithoutExtension() throws Exception{
        String pathFile = testDirectory + "file";
        FileTestingUtilities.makeFile(pathFile);
        boolean isFolder = directoryListing.isFolder(pathFile);
        assertFalse("Returns false when path is a file without extension", isFolder);
        FileTestingUtilities.clearPath(pathFile);
    }


    @After
    public void clearTestFiles() throws Exception {
        FileTestingUtilities.clearPath(testDirectory);
    }

}