package Routes.Wiki.Deliverer;

import Routes.Wiki.HTML.ViewPostTemplate;
import TestingSupport.MockPostRecorder;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ViewPostDelivererTest {

    private ViewPostDeliverer viewPostDeliverer;

    @Test
    public void testGetBytesReturnsTemplateBytes(){
        String title = "title";
        String content = "my post content";
        viewPostDeliverer = new ViewPostDeliverer(new MockPostRecorder(title,content), "post-1" , "GET");
        String html = new ViewPostTemplate(1, title, content).renderPage();
        assertArrayEquals(viewPostDeliverer.getBytes(), html.getBytes());
    }


}