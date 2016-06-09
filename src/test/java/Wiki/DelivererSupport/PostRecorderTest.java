package Wiki.DelivererSupport;

import TestingSupport.MockDataType;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class PostRecorderTest {

    private PostRecorder postRecorder;
    private MockDataType mockData;

    @Before
    public void setUp() throws Exception {
        mockData = new MockDataType();
        postRecorder = new PostRecorder(mockData);
    }

    @Test
    public void testGetLatestPostCountReturnsZeroWhenNoPostsHaveBeenMade(){
        assertEquals(0, postRecorder.getLatestPostID());
    }

    @Test
    public void testCreateNewPostCreatesPost() throws Exception{
        String[] content = {"A title", "Some more content"};
        postRecorder.createNewPost(content[0], content[1]);
        assertArrayEquals(content, mockData.selectPostByID(1));
    }

    @Test
    public void testUpdateExistingPostUpdatesPost() throws Exception{
        String[] content = {"A title", "Some more content"};
        String[] newContent = {"A New Title", "Some new content"};
        postRecorder.createNewPost(content[0], content[1]);
        postRecorder.updateExistingPost(1, newContent[0], newContent[1]);
        assertArrayEquals(newContent, mockData.selectPostByID(1));
    }

    @Test
    public void testGetPostTitleAndContentReturnsTitleAndContentInArray(){
        String[] content = {"A title", "Some more content"};
        postRecorder.createNewPost(content[0], content[1]);
        assertArrayEquals(content, postRecorder.getPostTitleAndContent(1));
    }

    @Test
    public void testGetAllPostTitlesGetsAllPostTitlesInOrder(){
        String[] content1 = {"1","A title1", "Some more content1"};
        String[] content2 = {"2","A title2", "Some more content2"};
        String[] content3 = {"3","A title3", "Some more content3"};
        mockData.posts = new String[3][3];
        mockData.posts[0] = content1;
        mockData.posts[1] = content2;
        mockData.posts[2] = content3;
        String[] allTitles = {content1[1], content2[1], content3[1]};
        assertArrayEquals(allTitles, postRecorder.getAllPostTitles());
    }

    @Test
    public void testGetAllPostContentGetsAllPostContentInOrder(){
        String[] content1 = {"1","A title1", "Some more content1"};
        String[] content2 = {"2","A title2", "Some more content2"};
        String[] content3 = {"3","A title3", "Some more content3"};
        mockData.posts = new String[3][3];
        mockData.posts[0] = content1;
        mockData.posts[1] = content2;
        mockData.posts[2] = content3;
        String[] allContent = {content1[2], content2[2], content3[2]};
        assertArrayEquals(allContent, postRecorder.getAllPostContent());
    }

    @Test
    public void testGetAllPostIDGetsAllPostIDsInOrder(){
        String[] content1 = {"1","A title1", "Some more content1"};
        String[] content2 = {"2","A title2", "Some more content2"};
        String[] content3 = {"3","A title3", "Some more content3"};
        mockData.posts = new String[3][3];
        mockData.posts[0] = content1;
        mockData.posts[1] = content2;
        mockData.posts[2] = content3;
        String[] allIDs = {content1[0], content2[0], content3[0]};
        assertArrayEquals(allIDs, postRecorder.getAllPostIDs());
    }

    @Test
    public void testDeletePostDeletesPost() throws Exception{
        String[] content = {"A title", "Some more content"};
        String[] nullContent = {null, null};
        postRecorder.createNewPost(content[0], content[1]);
        postRecorder.deletePost(1);
        assertArrayEquals(nullContent, mockData.selectPostByID(1));
    }

}