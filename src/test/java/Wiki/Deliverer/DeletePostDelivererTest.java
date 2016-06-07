package Wiki.Deliverer;


import TestingSupport.MockPostRecorder;
import Wiki.DelivererSupport.PostRecorder;
import Wiki.HTML.DeletePostTemplate;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class DeletePostDelivererTest {

    private DeletePostDeliverer deletePostDeliverer;

    @Test
    public void testGetBytesReturnsDeletePostTemplateBytes(){
        DeletePostTemplate deletePostTemplate = new DeletePostTemplate("Post_Title", 1);
        PostRecorder postRecorder = new MockPostRecorder();
        deletePostDeliverer = new DeletePostDeliverer(postRecorder, "/delete/Post_Title-1", "DELETE");
        String html = deletePostTemplate.renderPage();
        assertArrayEquals(deletePostDeliverer.getBytes(), html.getBytes());
    }

    @Test
    public void testDeletePostDelivererDeletesPost(){
        MockPostRecorder postRecorder = new MockPostRecorder();
        deletePostDeliverer = new DeletePostDeliverer(postRecorder, "/delete/Post_Title-1", "DELETE");
        assertTrue(postRecorder.isDeleted);

    }

}