
import org.junit.Test;

import static org.junit.Assert.*;


public class CommandParserTest {

    private CommandParser parser;

    @Test
    public void testHasPortReturnsTrueWhenPortIsPassedIn() throws Exception {
        String[] args = new String[] {"-p" ,"1000"};
        parser = new CommandParser(args);
        assertTrue("when -p has been passed in returns true", parser.hasPort());
    }

    @Test
    public void testHasPortReturnsFalseWhenPortIsNotPassedIn() throws Exception {
        String[] args = new String[]{""};
        parser = new CommandParser(args);
        assertFalse("when -p has not been passed in returns false", parser.hasPort());
    }

    @Test
    public void testGetPort() throws Exception {
        String[] args = new String[] {"-p", "1000"};
        parser = new CommandParser(args);
        assertEquals("when -p has been passed in 1000 returns 1000", 1000, parser.getPort());
    }

    @Test
    public void testGetPortReturnsSomeDefaultPortWhenNothingPassedIn() throws Exception {
        String[] args = new String[]{""};
        parser = new CommandParser(args);
        assertNotNull("when -p has not been passed in with returns some default port", parser.getPort());
    }


    @Test
    public void testHasDirectoryReturnsTrueWhenDirectoryIsPassedIn() throws Exception{
        String[] args = new String[]{"-d", "/somefolder"};
        parser = new CommandParser(args);
        assertTrue("when -d has been passed in returns true", parser.hasDirectory());
    }

    @Test
    public void testHasDirectoryReturnsFalseWhenDirectoryIsNotPassedIn() throws Exception{
        String[] args = new String[]{""};
        parser = new CommandParser(args);
        assertFalse("when -d has not been passed in returns false", parser.hasDirectory());
    }

    @Test
    public void testGetDirectoryReturnsDirectoryPassedIn() throws Exception{
        String[] args = new String[]{"-d", "/somefolder"};
        parser = new CommandParser(args);
        assertEquals("when -d has been passed in with '/somefolder,' returns that", "/somefolder", parser.getDirectory());
    }

    @Test
    public void testGetDirectoryReturnsDefaultDirectoryWhenNothingPassedIn() throws Exception {
        String[] args = new String[]{""};
        parser = new CommandParser(args);
        assertNotNull("when -d has not been passed in with returns some default folder", parser.getDirectory());
    }

}