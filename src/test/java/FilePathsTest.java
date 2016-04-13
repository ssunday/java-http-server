
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FilePathsTest {

    private FilePaths filePaths;
    private String baseDirectory = FileTestingUtilities.testDirectory;

    @Before

    public void initialize(){
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

    public void testGetPathToLinkReturnsPathWithoutBaseDirectory(){
        String fullPath = baseDirectory + "folder/";
        String path = filePaths.getPathToLink(fullPath);
        assertEquals("Returns path without base directory included", "/folder/", path);
    }

    @Test

    public void testGetPathToLinkReturnsNestedPathWithoutBaseDirectory(){
        String fullPath = baseDirectory + "folder/something/";
        String path = filePaths.getPathToLink(fullPath);
        assertEquals("Returns nested folder path without base directory included", "/folder/something/", path);
    }

    @Test

    public void testGetPreviousPathToLinkReturnsBaseDirectorIfPathIsTheBaseDirectory(){
        String fullPath = baseDirectory;
        String path = filePaths.getPreviousPathToLink(fullPath);
        assertEquals("Returns / if path is the base directory", "/", path);
    }

    @Test

    public void testGetPreviousPathToLinkReturnsPathOneLevelUpWithoutBaseDirectory(){
        String fullpath = baseDirectory + "folder/something/things/";
        String path = filePaths.getPreviousPathToLink(fullpath);
        assertEquals("Returns path one level up without base directory included of a nested folder system", "/folder/something/", path);
    }

    @Test

    public void testGetPreviousPathToLinkReturnsBaseDirectoryWhenFolderIsOneLevelDown(){
        String fullPath = baseDirectory + "folder/";
        String path = filePaths.getPreviousPathToLink(fullPath);
        assertEquals("Returns slash if one level up in path is the base directory", "/", path);
    }

}