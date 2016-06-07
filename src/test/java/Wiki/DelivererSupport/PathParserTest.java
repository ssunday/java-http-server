package Wiki.DelivererSupport;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PathParserTest {

    @Test
    public void testGetIDFromPathReturnsPostIDFromEditPath() throws Exception {
        String path = "/edit/title-1";
        assertEquals(1, PathParser.getIDFromPath(path));
    }

    @Test
    public void testGetIDFromPathReturnsPostIDFromViewPath() throws Exception {
        String path = "/post/large_title-1";
        assertEquals(1, PathParser.getIDFromPath(path));
    }

    @Test
    public void testGetTitleFromPath() throws Exception {
        String title = "Post_Title";
        String path = "/tmp/" + title;
        assertEquals(title, PathParser.getTitleFromPath(path));
    }
    @Test
    public void testGetTitleFromPathDelete() throws Exception {
        String title = "Post_Title";
        String path = "/delete/" + title + "-1";
        assertEquals(title, PathParser.getTitleFromPath(path));
    }
}

