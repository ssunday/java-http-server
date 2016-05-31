package Routes.Wiki.Deliverer;

import Routes.Wiki.DelivererSupport.PostRecorder;
import Routes.Wiki.HTML.CreatePostTemplate;
import Routes.Wiki.HTML.ViewNewPostTemplate;
import TestingSupport.MockDataType;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;

public class CreatePostDelivererTest {

    private CreatePostDeliverer createPostDeliverer;

    @Test
    public void testGetBytesIsTheSameAsTemplateBytesWhenGET(){
        createPostDeliverer = new CreatePostDeliverer("GET");
        String html = new CreatePostTemplate().renderPage();
        assertArrayEquals(createPostDeliverer.getBytes(), html.getBytes());
    }

    @Test
    public void testGetBytesIsViewNewPostWhenPost(){
        PostRecorder postRecorder = new PostRecorder(new MockDataType());
        Map params = new HashMap();
        params.put("title", "A title");
        params.put("content", "Some content");
        createPostDeliverer = new CreatePostDeliverer(postRecorder, params, "POST");
        String html = new ViewNewPostTemplate("Created Post", "A title", 1).renderPage();
        assertArrayEquals(createPostDeliverer.getBytes(), html.getBytes());
    }
}