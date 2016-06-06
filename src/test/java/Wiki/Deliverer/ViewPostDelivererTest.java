package Wiki.Deliverer;

import TestingSupport.MockPostRecorder;
import Wiki.HTML.ViewPostTemplate;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ViewPostDelivererTest {

    private ViewPostDeliverer viewPostDeliverer;

    @Test
    public void testGetBytesReturnsTemplateBytes(){
        String title = "title";
        String content = "my post content";
        viewPostDeliverer = new ViewPostDeliverer(new MockPostRecorder(title,content), "/post/title-1" , "GET");
        String html = new ViewPostTemplate(1, title, content, new String[0], new String[0]).renderPage();
        assertArrayEquals(viewPostDeliverer.getBytes(), html.getBytes());
    }


}