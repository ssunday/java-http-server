package TestingSupport;

import Wiki.DelivererSupport.DataType;

import java.util.Map;

public class MockDataType implements DataType{
    public void addPost(Map fieldsAndValues){
        return;
    }
    public void updatePost(Map newFieldsAndValues){
        return;
    }
    public int getLatestID(){return 1;}
    public String[][] getAllPosts(){
        return new String[0][0];
    }
    public String[] selectPostByID(int id){
        return new String[0];
    }
    public void deleteByID(int id){
        return;
    }
    public void clearData(){
        return;
    }

}
