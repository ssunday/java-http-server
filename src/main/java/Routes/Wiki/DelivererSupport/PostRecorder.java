package Routes.Wiki.DelivererSupport;

import java.util.HashMap;
import java.util.Map;

public class PostRecorder {

    private DataType dataType;

    private final int ID_INDEX = 0;
    private final int TITLE_INDEX = 1;
    private final int CONTENT_INDEX = 2;

    int postCount = 0;

    public PostRecorder(DataType dataType) {
        this.dataType = dataType;
    }

    public int getLatestPostCount(){
        return postCount;
    }

    public void createNewPost(String title, String content){
        postCount++;
        Map postMap = new HashMap();
        postMap.put(DataType.ID, postCount);
        postMap.put(DataType.TITLE, title);
        postMap.put(DataType.CONTENT, content);
        dataType.addPost(postMap);
    }

    public void updateExistingPost(int id, String title, String content){
        Map updatedPostMap = new HashMap();
        updatedPostMap.put(DataType.ID, id);
        updatedPostMap.put(DataType.TITLE, title);
        updatedPostMap.put(DataType.CONTENT, content);
        dataType.updatePost(updatedPostMap);
    }

    public String[] getPostTitleAndContent(int id){
        return dataType.selectPostByID(id);
    }

    public void deletePost(int id){
        dataType.deleteByID(id);
    }

    public String[] getAllPostIDs(){
        return getAllPostAttribute(ID_INDEX);
    }

    public String[] getAllPostTitles(){
        return getAllPostAttribute(TITLE_INDEX);
    }

    public String[] getAllPostContent(){
        return getAllPostAttribute(CONTENT_INDEX);
    }

    private String[] getAllPostAttribute(int attributeIndex){
        String[][] allPosts = dataType.getAllPosts();
        int allPostsLength = allPosts.length;
        String[] allPostAttributes = new String[allPostsLength];
        for (int i = 0; i < allPostsLength; i++){
            allPostAttributes[i] = allPosts[i][attributeIndex];
        }
        return allPostAttributes;
    }

}
