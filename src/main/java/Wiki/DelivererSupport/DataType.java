package Wiki.DelivererSupport;

import java.util.Map;

public interface DataType {

    String ID = "id";
    String TITLE = "title";
    String CONTENT = "content";

    void addPost(Map fieldsAndValues);
    int getLatestID();
    void updatePost(Map newFieldsAndValues);
    String[][] getAllPosts();
    String[] selectPostByID(int id);
    void deleteByID(int id);
}
