package javahttpserver.tests;

import javahttpserver.main.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ServingFactoryTest {

    @Before
    public void initialize() throws Exception{
        FileTestingUtilities.makePath(FileTestingUtilities.testDirectory);
    }

    @Test
    public void testGetServerReturnsDirectoryServing() throws Exception {
        String path = FileTestingUtilities.testDirectory + "single/";
        FileTestingUtilities.makePath(path);
        ServingBase server = ServingFactory.getServer(path, "/");
        assertTrue("Returns Directory object when passed in a directory", server instanceof DirectoryServing);
        FileTestingUtilities.clearPath(path);
    }

    @Test
    public void testGetServerReturnsFileServing() throws Exception {
        String singleFile = FileTestingUtilities.testDirectory + "single.txt";
        FileTestingUtilities.makeFile(singleFile);
        ServingBase server = ServingFactory.getServer(singleFile, "/");
        assertTrue("Returns File serving object when file passed in", server instanceof FileServing);
        FileTestingUtilities.clearPath(singleFile);
    }

    @Test
    public void testGetServerReturnsParameterServing() throws Exception {
        String parametersPath = FileTestingUtilities.testDirectory + "parameters?stuff=blarg";
        ServingBase server = ServingFactory.getServer(parametersPath, "/");
        assertTrue("Returns Parameter serving object when path with parameters passed in", server instanceof ParameterServing);
    }

    @Test
    public void testGetServerReturnsNotFoundServing() throws Exception {
        String notFound = FileTestingUtilities.testDirectory + "not here.txt";
        ServingBase server = ServingFactory.getServer(notFound, "/");
        assertTrue("Returns not found server when non-existent file is passed in", server instanceof NotFoundServing);
    }

    @After
    public void clearTestFiles() throws Exception {
        FileTestingUtilities.clearPath(FileTestingUtilities.testDirectory);
    }
}