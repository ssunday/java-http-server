package javahttpserver.tests;

import javahttpserver.main.FilePaths;
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

    public void testGetPathToServeFromRequestReturnsFullDirectoryPathGivenHTTPRequest(){
        String request = "GET /folder/ HTTP/1.1";
        String directory = filePaths.getPathToServeFromRequest(request);
        String fullpath = baseDirectory + "folder/";
        assertEquals("Returns full path given HTTP request", fullpath, directory);
    }

    @Test

    public void testGetPathToServeFromRequestReturnsFullNestedDirectoryPathGivenHTTPRequest(){
        String request = "GET /folder/something/ HTTP/1.1";
        String directory = filePaths.getPathToServeFromRequest(request);
        String fullpath = baseDirectory + "folder/something/";
        assertEquals("Returns full path of nested folders given HTTP request", fullpath, directory);
    }

    @Test

    public void testGetPathToServeFromRequestWithFoldersWithSpaceReturnsFullPathGivenHTTPRequest(){
        String request = "GET /folder%20something/ HTTP/1.1";
        String directory = filePaths.getPathToServeFromRequest(request);
        String fullpath = baseDirectory + "folder something/";
        assertEquals("Returns path of folders with html space given HTTP request", fullpath, directory);
    }

    @Test

    public void testGetPathToServeFromRequestReturnsFullDirectoryWithFileGivenHTTPRequest(){
        String request = "GET /file.txt HTTP/1.1";
        String directory = filePaths.getPathToServeFromRequest(request);
        String fullpath = baseDirectory + "file.txt";
        assertEquals("Returns full path of file given HTTP request", fullpath, directory);
    }

    @Test

    public void testGetPathToServeFromRequestReturnsFullNestedDirectoryWithFileGivenHTTPRequest(){
        String request = "GET /something/file.txt HTTP/1.1";
        String directory = filePaths.getPathToServeFromRequest(request);
        String fullPath = baseDirectory + "something/file.txt";
        assertEquals("Returns full path of nested file given HTTP request", fullPath, directory);
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