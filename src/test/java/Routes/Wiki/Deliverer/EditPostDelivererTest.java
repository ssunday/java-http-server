package Routes.Wiki.Deliverer;

import Routes.Wiki.DelivererSupport.PostRecorder;
import Routes.Wiki.DelivererSupport.Postgres;
import Routes.Wiki.HTML.EditPostTemplate;
import Routes.Wiki.HTML.ViewNewPostTemplate;
import TestingSupport.MockPostRecorder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;

public class EditPostDelivererTest {

    private EditPostDeliverer editPostDeliverer;

    @Test
    public void testGetBytesReturnsEditPostTemplateBytesWhenGet(){
        MockPostRecorder mockPostRecorder = new MockPostRecorder("Title", "Content");
        editPostDeliverer = new EditPostDeliverer(mockPostRecorder, "post-1", "GET");
        byte[] templateBytes = new EditPostTemplate(1, "Title", "Content").renderPage().getBytes();
        assertArrayEquals(editPostDeliverer.getBytes(),templateBytes);
    }

    @Test
    public void testGetBytesReturnsViewNewPostTemplateWhenPOST(){
        Postgres postgres = new Postgres("test");
        PostRecorder postRecorder = new PostRecorder(postgres);
        postRecorder.createNewPost("A title", "Content");
        Map newValues = new HashMap();
        newValues.put("title", "Something");
        newValues.put("content", "Content");
        editPostDeliverer = new EditPostDeliverer(postRecorder, "post-1", newValues, "POST");
        byte[] templateBytes = new ViewNewPostTemplate("Edited Post", "Something", 1).renderPage().getBytes();
        assertArrayEquals(editPostDeliverer.getBytes(),templateBytes);
        postgres.clearData();
    }

}