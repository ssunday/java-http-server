package Routes.Cobspec;

import TestingSupport.FileTestingUtilities;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FilePathsTest {

    private FilePaths filePaths;
    private String baseDirectory = FileTestingUtilities.testDirectory;

    @Before
    public void setUp(){
        filePaths = new FilePaths(baseDirectory);
    }

    @Test
    public void testGetPathToServeReturnsFullDirectoryPath(){
        String path = "/folder/";
        String directory = filePaths.getPathToServe(path);
        String fullpath = baseDirectory + path.substring(1);
        assertEquals("Returns full path of folder given folder", fullpath, directory);
    }

    @Test
    public void testGetPathToServeReturnsFullDirectoryOfFile(){
        String path = "/file.txt";
        String directory = filePaths.getPathToServe(path);
        String fullpath = baseDirectory + path.substring(1);
        assertEquals("Returns full path of file given file", fullpath, directory);
    }

    @Test
    public void testGetPathToServeReturnsFullNestedDirectory(){
        String path = "/folder/something/";
        String directory = filePaths.getPathToServe(path);
        String fullpath = baseDirectory + path.substring(1);
        assertEquals("Returns full path of nested folder directory", fullpath, directory);
    }

    @Test
    public void testGetPreviousPathToLinkReturnsBaseDirectorIfPathIsTheBaseDirectory(){
        String fullPath = "/";
        String path = filePaths.getPreviousPathToLink(fullPath);
        assertEquals("Returns / if path is /", "/", path);
    }

    @Test
    public void testGetPreviousPathToLinkReturnsPathOneLevelUpWithoutBaseDirectory(){
        String fullpath = "/folder/something/things/";
        String path = filePaths.getPreviousPathToLink(fullpath);
        assertEquals("Returns path one level up without base directory included of a nested folder system", "/folder/something/", path);
    }

    @Test
    public void testGetPreviousPathToLinkReturnsBaseDirectoryWhenFolderIsOneLevelDown(){
        String fullPath = "/folder/";
        String path = filePaths.getPreviousPathToLink(fullPath);
        assertEquals("Returns slash if one level up in path is /", "/", path);
    }

}