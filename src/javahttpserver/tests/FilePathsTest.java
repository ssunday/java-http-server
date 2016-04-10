package javahttpserver.tests;

import javahttpserver.main.FilePaths;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FilePathsTest {

    private FilePaths filePaths;
    private String baseDirectory;

    @Before

    public void initialize(){
        baseDirectory = "/public/";
        filePaths = new FilePaths(baseDirectory);
    }

    @Test

    public void testGetPathOnlyReturnsWithFileGivenHTTPRequest(){
        String request = "GET /file.txt HTTP/1.1";
        String path = filePaths.getPath(request);
        String expectedPath = "/file.txt";
        assertEquals("Returns simple path of file given HTTP request", expectedPath, path);
    }

    @Test

    public void testGetPathOnlyReturnsWithFolderGivenHTTPRequest(){
        String request = "GET /folder/ HTTP/1.1";
        String path = filePaths.getPath(request);
        String expectedPath = "/folder/";
        assertEquals("Returns simple path of folder given HTTP request", expectedPath, path);
    }

    @Test

    public void testGetPathToServeFromRequestReturnsFullDirectoryPathGivenHTTPRequest(){
        String request = "GET /folder/ HTTP/1.1";
        String directory = filePaths.getPathToServeFromRequest(request);
        String fullpath = baseDirectory + "folder/";
        assertEquals("Returns full path given HTTP request", fullpath, directory);
    }

    @Test

    public void testGetPathToServeFromRequestReturnsFullDirectoryWithFileGivenHTTPRequest(){
        String request = "GET /file.txt HTTP/1.1";
        String directory = filePaths.getPathToServeFromRequest(request);
        String fullpath = baseDirectory + "file.txt";
        assertEquals("Returns full path of file given HTTP request", fullpath, directory);
    }

    @Test

    public void testGetPathOneUpReturnsPathOneLevelUp(){
        String fullPath = "/public/folder/something/";
        String path = filePaths.getPathOneLevelUp(fullPath);
        String expectedPath = "/public/folder/";
        assertEquals("Returns path one level up", expectedPath, path);
    }

    @Test

    public void testGetPathOneUpReturnsBasePathIfOnBasePath(){
        String path = filePaths.getPathOneLevelUp(baseDirectory);
        assertEquals("Returns blank slash if path is base path", "/", path);
    }

    @Test

    public void testGetPathToLinkReturnsPathWithoutBaseDirectory(){
        String fullpath = "/public/folder/";
        String path = filePaths.getPathToLink(fullpath);
        assertEquals("Returns path without base directory included", "/folder/", path);
    }

    @Test

    public void testIsFolderReturnsTrueForFolder(){
        String path = "/public/folder/";
        boolean isFolder = filePaths.isFolder(path);
        assertTrue("Returns true when path is a folder", isFolder);
    }

    @Test

    public void testIsFolderReturnsFalseForFile(){
        String path = "/public/file.txt";
        boolean isFolder = filePaths.isFolder(path);
        assertFalse("Returns true when path is a folder", isFolder);
    }


}