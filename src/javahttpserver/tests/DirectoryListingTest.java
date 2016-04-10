package javahttpserver.tests;

import javahttpserver.main.DirectoryListing;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class DirectoryListingTest {

    private DirectoryListing directoryListing;

    private void makePath(String path) throws Exception{
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    private void clearPath(String path) throws Exception {
        File file = new File(path);
        file.delete();
    }

    @Before
    public void initialize(){
        directoryListing = new DirectoryListing();
    }

    @Test
    public void testGetListingReturnsNullWhenEmpty() throws Exception {
        String emptyDirectory = "/Users/sarahsunday/Documents/Github/java-http-server/test-files/nothing";
        makePath(emptyDirectory);
        assertArrayEquals("returns empty string array when directory is empty", new String[0], directoryListing.getListing(emptyDirectory));
        clearPath(emptyDirectory);

    }

    @Test
    public void testGetListingReturnsArrayWithSingleFileInDirectory() throws Exception {
        String singleFileDirectory = "/Users/sarahsunday/Documents/Github/java-http-server/test-files";
        makePath(singleFileDirectory);
        String singleFile = "/Users/sarahsunday/Documents/Github/java-http-server/test-files/single.txt";
        makePath(singleFile);
        String[] listing = directoryListing.getListing(singleFileDirectory);
        assertEquals("returns array of length one when single file exists", 1, listing.length);
        clearPath(singleFileDirectory);
        clearPath(singleFile);
    }


    @Test
    public void testGetListingReturnsArrayofTwoWhenFileAndDirectoryExist() throws Exception {
        String doubleDirectory = "/Users/sarahsunday/Documents/Github/java-http-server/test-files";
        makePath(doubleDirectory);
        String singleFile = "/Users/sarahsunday/Documents/Github/java-http-server/test-files/single.txt";
        makePath(singleFile);
        String singleDirectory = "/Users/sarahsunday/Documents/Github/java-http-server/test-files/single";
        makePath(singleDirectory);
        String[] listing = directoryListing.getListing(doubleDirectory);
        assertEquals("returns array of length two when both file and directory exist", 2, listing.length);
        clearPath(doubleDirectory);
        clearPath(singleFile);
        clearPath(singleDirectory);
    }


    @After
    public void clearTestFiles() throws Exception {
        String test_file_directory = "/Users/sarahsunday/Documents/Github/java-http-server/test-files";
        clearPath(test_file_directory);
    }

}