package Wiki.Deliverer;

import TestingSupport.MockPostRecorder;
import Wiki.DelivererSupport.PostRecorder;
import Wiki.DelivererSupport.Postgres;
import Wiki.HTML.EditPostTemplate;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

public class EditPostDelivererTest {

    private EditPostDeliverer editPostDeliverer;
    private final int TEST_PORT = 6000;

    @Test
    public void testGetBytesReturnsEditPostTemplateBytesWhenGet(){
        MockPostRecorder mockPostRecorder = new MockPostRecorder("Title", "Content");
        editPostDeliverer = new EditPostDeliverer(mockPostRecorder, "/post/Title-1", "GET");
        byte[] templateBytes = new EditPostTemplate(1, "Title", "Content").renderPage().getBytes();
        assertArrayEquals(editPostDeliverer.getBytes(),templateBytes);
    }

    @Test
    public void testGetResponseHeaderHas302Code(){
        Postgres postgres = new Postgres("test");
        PostRecorder postRecorder = new PostRecorder(postgres);
        postRecorder.createNewPost("Atitle", "Content");
        Map newValues = new HashMap();
        newValues.put("title", "Something");
        newValues.put("content", "Content");
        editPostDeliverer = new EditPostDeliverer(postRecorder, "/post/Atitle-1", TEST_PORT, newValues, "POST");
        assertThat(editPostDeliverer.getResponseHeader(), containsString("302 Found"));
        postgres.clearData();
    }

}