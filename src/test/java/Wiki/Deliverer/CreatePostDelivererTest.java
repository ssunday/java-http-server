package Wiki.Deliverer;

import TestingSupport.MockDataType;
import Wiki.DelivererSupport.PostRecorder;
import Wiki.HTML.CreatePostTemplate;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

public class CreatePostDelivererTest {

    private CreatePostDeliverer createPostDeliverer;
    private final int TEST_PORT = 6000;

    @Test
    public void testGetBytesIsTheSameAsTemplateBytesWhenGET(){
        createPostDeliverer = new CreatePostDeliverer("GET");
        String html = new CreatePostTemplate().renderPage();
        assertArrayEquals(createPostDeliverer.getBytes(), html.getBytes());
    }

    @Test
    public void testGetResponseHeaderIs302WhenPost(){
        PostRecorder postRecorder = new PostRecorder(new MockDataType());
        Map params = new HashMap();
        params.put("title", "A title");
        params.put("content", "Some content");
        createPostDeliverer = new CreatePostDeliverer(postRecorder, TEST_PORT, params, "POST");
        assertThat(createPostDeliverer.getResponseHeader(), containsString("302 Found"));
    }
}