package Wiki.DelivererSupport;

import Wiki.DelivererSupport.PostRecorder;
import Wiki.DelivererSupport.Postgres;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class PostRecorderTest {

    private PostRecorder postRecorder;
    private Postgres postgres;

    @Before
    public void setUp() throws Exception {
        postgres = new Postgres("test");
        postRecorder = new PostRecorder(postgres);
    }

    @Test
    public void testGetLatestPostCountReturnsZeroWhenNoPostsHaveBeenMade(){
        assertEquals(0, postRecorder.getLatestPostID());
    }

    @Test
    public void testCreateNewPostCreatesPost() throws Exception{
        String[] content = {"A title", "Some more content"};
        postRecorder.createNewPost(content[0], content[1]);
        assertArrayEquals(content, postgres.selectPostByID(1));
        postgres.clearData();
    }

    @Test
    public void testUpdateExistingPostUpdatesPost() throws Exception{
        String[] content = {"A title", "Some more content"};
        String[] newContent = {"A New Title", "Some new content"};
        postRecorder.createNewPost(content[0], content[1]);
        postRecorder.updateExistingPost(1, newContent[0], newContent[1]);
        assertArrayEquals(newContent, postgres.selectPostByID(1));
        postgres.clearData();
    }

    @Test
    public void testGetPostTitleAndContentReturnsTitleAndContentInArray(){
        String[] content = {"A title", "Some more content"};
        postRecorder.createNewPost(content[0], content[1]);
        assertArrayEquals(content, postRecorder.getPostTitleAndContent(1));
        postgres.clearData();
    }

    @Test
    public void testGetAllPostTitlesGetsAllPostTitlesInOrder(){
        String[] content1 = {"A title1", "Some more content1"};
        String[] content2 = {"A title2", "Some more content2"};
        String[] content3 = {"A title3", "Some more content3"};
        postRecorder.createNewPost(content1[0], content1[1]);
        postRecorder.createNewPost(content2[0], content2[1]);
        postRecorder.createNewPost(content3[0], content3[1]);
        String[] allTitles = {content1[0], content2[0], content3[0]};
        assertArrayEquals(allTitles, postRecorder.getAllPostTitles());
        postgres.clearData();
    }

    @Test
    public void testGetAllPostContentGetsAllPostContentInOrder(){
        String[] content1 = {"A title1", "Some more content1"};
        String[] content2 = {"A title2", "Some more content2"};
        String[] content3 = {"A title3", "Some more content3"};
        postRecorder.createNewPost(content1[0], content1[1]);
        postRecorder.createNewPost(content2[0], content2[1]);
        postRecorder.createNewPost(content3[0], content3[1]);
        String[] allContent = {content1[1], content2[1], content3[1]};
        assertArrayEquals(allContent, postRecorder.getAllPostContent());
        postgres.clearData();
    }

    @Test
    public void testGetAllPostIDGetsAllPostIDsInOrder(){
        String[] content1 = {"A title1", "Some more content1"};
        String[] content2 = {"A title2", "Some more content2"};
        String[] content3 = {"A title3", "Some more content3"};
        postRecorder.createNewPost(content1[0], content1[1]);
        postRecorder.createNewPost(content2[0], content2[1]);
        postRecorder.createNewPost(content3[0], content3[1]);
        String[] allIDs = {"1", "2", "3"};
        assertArrayEquals(allIDs, postRecorder.getAllPostIDs());
        postgres.clearData();
    }

    @Test
    public void testGetAllPostIDGetsAllPostIDsWithDelete(){
        String[] content1 = {"A title1", "Some more content1"};
        String[] content2 = {"A title2", "Some more content2"};
        String[] content3 = {"A title3", "Some more content3"};
        postRecorder.createNewPost(content1[0], content1[1]);
        postRecorder.createNewPost(content2[0], content2[1]);
        postRecorder.createNewPost(content3[0], content3[1]);
        postRecorder.deletePost(1);
        String[] allIDs = {"2", "3"};
        assertArrayEquals(allIDs, postRecorder.getAllPostIDs());
        postgres.clearData();
    }

    @Test
    public void testDeletePostDeletesPost() throws Exception{
        String[] content = {"A title", "Some more content"};
        String[] nullContent = {null, null};
        postRecorder.createNewPost(content[0], content[1]);
        postRecorder.deletePost(1);
        assertArrayEquals(nullContent, postgres.selectPostByID(1));
        postgres.clearData();
    }

}