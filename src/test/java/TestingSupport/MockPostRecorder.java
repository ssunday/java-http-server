package TestingSupport;

import Routes.Wiki.DelivererSupport.PostRecorder;

public class MockPostRecorder extends PostRecorder {

    private String title;
    private String content;

    public MockPostRecorder(String title, String content){
        this.title = title;
        this.content = content;
    }

    public String[] getPostTitleAndContent(int id){
        String[] postTitleAndContent = {title, content};
        return postTitleAndContent;
    }
}
