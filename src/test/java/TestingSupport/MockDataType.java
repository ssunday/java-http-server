package TestingSupport;

import Wiki.DelivererSupport.DataType;

import java.util.Map;

public class MockDataType implements DataType{

    private int id = 0;
    public String title;
    public String content;
    public String[][] posts;

    public void addPost(Map fieldsAndValues){
        id++;
        title = (String) fieldsAndValues.get(TITLE);
        content = (String) fieldsAndValues.get(CONTENT);
    }
    public void updatePost(Map newFieldsAndValues){
        title = (String) newFieldsAndValues.get(TITLE);
        content = (String) newFieldsAndValues.get(CONTENT);
    }

    public int getLatestID(){
        return id;
    }

    public String[][] getAllPosts(){
        return posts;
    }

    public String[] selectPostByID(int id){
        String[] post = {title, content};
        return post;
    }
    public void deleteByID(int id){
        title = null;
        content = null;
    }

}
