package Wiki.Deliverer;

import TestingSupport.MockDataType;
import Wiki.DelivererSupport.PostRecorder;
import Wiki.HTML.TempPostTemplate;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

public class TempPostDelivererTest {

    private TempPostDeliverer tempPostDeliverer;
    private String postTitle;

    @Before
    public void setUp() throws Exception {
        postTitle = "SomethingNew";
        tempPostDeliverer = new TempPostDeliverer("/tmp/SomethingNew", "GET");
    }

    @Test
    public void testGetBytesIsTempPostTemplateBytes(){
        String html = new TempPostTemplate(postTitle).renderPage();
        assertArrayEquals(tempPostDeliverer.getBytes(), html.getBytes());
    }

    @Test
    public void testGetResponseHeaderIs302WhenPost(){
        PostRecorder postRecorder = new PostRecorder(new MockDataType());
        Map params = new HashMap();
        params.put("content", "SomeContent");
        TempPostDeliverer tempPostDelivererPost = new TempPostDeliverer(postRecorder, 6000, params,"/tmp/SomethingNew", "POST");
        assertThat(tempPostDelivererPost.getResponseHeader(), containsString("302 Found"));
    }
}