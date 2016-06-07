package TestingSupport;

import Wiki.DelivererSupport.PostRecorder;

public class MockPostRecorder extends PostRecorder {

    public String title;
    public String content;
    public boolean isDeleted = false;

    public MockPostRecorder(){

    }

    public MockPostRecorder(String title, String content){
        this.title = title;
        this.content = content;
    }

    @Override
    public String[] getPostTitleAndContent(int id){
        String[] postTitleAndContent = {title, content};
        return postTitleAndContent;
    }

    @Override
    public void deletePost(int id){
        isDeleted = true;
    }

    @Override
    public String[] getAllPostTitles(){
        return new String[0];
    }

    @Override
    public String[] getAllPostIDs(){
        return new String[0];
    }
}
