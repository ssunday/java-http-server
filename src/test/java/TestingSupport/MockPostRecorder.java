package TestingSupport;

import Wiki.DelivererSupport.PostRecorder;

public class MockPostRecorder extends PostRecorder {

    private String title;
    private String content;

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
    public String[] getAllPostTitles(){
        return new String[0];
    }

    @Override
    public String[] getAllPostIDs(){
        return new String[0];
    }
}
