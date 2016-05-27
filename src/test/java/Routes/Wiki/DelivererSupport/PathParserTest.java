package Routes.Wiki.DelivererSupport;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PathParserTest {

    @Test
    public void testGetIDFromPathReturnsPostIDFromEditPath() throws Exception {
        String path = "edit-1";
        assertEquals(1, PathParser.getIDFromPath(path));
    }

    @Test
    public void testGetIDFromPathReturnsPostIDFromViewPath() throws Exception {
        String path = "post-1";
        assertEquals(1, PathParser.getIDFromPath(path));
    }
}