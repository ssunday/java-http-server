package Routes.Cobspec.DelivererSupport;

import TestingSupport.FileTestingUtilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class DirectoryListingTest {

    private DirectoryListing directoryListing;

    private String testDirectory = FileTestingUtilities.testDirectory;

    @Before
    public void setUp() throws Exception{
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

    @After
    public void clearTestFiles() throws Exception {
        FileTestingUtilities.clearPath(testDirectory);
    }

}