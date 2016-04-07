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

    private void makePath(File path){
        if(!path.exists()){
            path.mkdirs();
        }
    }

    private void clearPath(File path) throws Exception{
        path.delete();
    }

    @Before
    public void initialize(){
        directoryListing = new DirectoryListing();
    }

    @Test
    public void testGetListingReturnsNullWhenEmpty() throws Exception {
        File emptyDirectory = new File("/Users/sarahsunday/Documents/Github/java-http-server/test-files/nothing");
        makePath(emptyDirectory);
        assertArrayEquals("returns empty string array when directory is empty", new String[0], directoryListing.getListing(emptyDirectory));
        clearPath(emptyDirectory);

    }

    @Test
    public void testGetListingReturnsArrayWithSingleFileInDirectory() throws Exception {
        File singleFileDirectory = new File("/Users/sarahsunday/Documents/Github/java-http-server/test-files");
        makePath(singleFileDirectory);
        File singleFile = new File("/Users/sarahsunday/Documents/Github/java-http-server/test-files/single.txt");
        makePath(singleFile);
        String[] listing = directoryListing.getListing(singleFileDirectory);
        assertEquals("returns array of length one when single file exists", 1, listing.length);
        clearPath(singleFileDirectory);
        clearPath(singleFile);
    }


    @Test
    public void testGetListingReturnsArrayofTwoWhenFileAndDirectoryExist() throws Exception {
        File doubleDirectory = new File("/Users/sarahsunday/Documents/Github/java-http-server/test-files");
        makePath(doubleDirectory);
        File singleFile = new File("/Users/sarahsunday/Documents/Github/java-http-server/test-files/single.txt");
        makePath(singleFile);
        File singleDirectory = new File("/Users/sarahsunday/Documents/Github/java-http-server/test-files/single");
        makePath(singleDirectory);
        String[] listing = directoryListing.getListing(doubleDirectory);
        assertEquals("returns array of length one when single file exists", 2, listing.length);
        clearPath(doubleDirectory);
        clearPath(singleFile);
        clearPath(singleDirectory);
    }

    @After
    public void clearTestFiles() throws Exception {
        File directory = new File("/Users/sarahsunday/Documents/Github/java-http-server/test-files");
        clearPath(directory);
    }

}