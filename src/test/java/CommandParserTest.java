
import org.junit.Test;

import static org.junit.Assert.*;


public class CommandParserTest {

    private CommandParser parser;

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

    @Test
    public void testGetModeReturnsDefaultModeWhenNothingPassedIn() throws Exception{
        String[] args = new String[]{""};
        parser = new CommandParser(args);
        assertNotNull(parser.getMode());
    }

    @Test
    public void testGetModeReturnsModeWhenPassedIn() throws Exception{
        String[] args = new String[]{"-m", "cobspec"};
        parser = new CommandParser(args);
        assertEquals("cobspec" , parser.getMode());
    }

}