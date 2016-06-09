package Wiki.DelivererSupport;

import TestingSupport.PostgresTestingUtilities;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PostgresTest {
    private Postgres postgres;
    private PostgresTestingUtilities postgresTestingUtilities;

    @Before
    public void setUp() throws Exception{
        postgresTestingUtilities = new PostgresTestingUtilities(Postgres.ID, Postgres.TITLE, Postgres.CONTENT);
        postgres = new Postgres(postgresTestingUtilities.TABLE_NAME);

    }

    @Test
    public void testAddPostEntersDataIntoTable() throws Exception {
        Map fieldsAndValues = new HashMap();
        fieldsAndValues.put(postgres.TITLE, "A title");
        fieldsAndValues.put(postgres.CONTENT, "something");
        postgres.addPost(fieldsAndValues);
        ResultSet resultSet = postgresTestingUtilities.select(1);
        assertTrue(resultSet.next());
        postgresTestingUtilities.clearData();
    }

    @Test
    public void testAddPostEntersCorrectDataIntoTable() throws Exception {
        Map fieldsAndValues = new HashMap();
        fieldsAndValues.put(postgres.TITLE, "A title");
        fieldsAndValues.put(postgres.CONTENT, "something");
        postgres.addPost(fieldsAndValues);
        ResultSet resultSet = postgresTestingUtilities.select(1);
        String[] postContents = new String[2];
        String[] realContent = {"A title", "something"};
        while (resultSet.next()) {
            postContents[0] = resultSet.getString("title");
            postContents[1] = resultSet.getString("content");
        }
        assertArrayEquals(realContent, postContents);
        postgresTestingUtilities.clearData();
    }

    @Test
    public void testGetLatestIDReturnsLatestID() throws Exception{
        postgres = new Postgres("test");
        String title = "A_title";
        String content = "something";
        postgresTestingUtilities.insert(title, content);
        assertEquals(1, postgres.getLatestID());
        postgresTestingUtilities.clearData();
    }

    @Test
    public void testSelectPostByIDGetsCorrectDataFromID() throws Exception{
        postgres = new Postgres("test");
        String title1 = "A_title";
        String content1 = "something";
        String title2 = title1 + "2";
        String content2 = content1 + "2";
        postgresTestingUtilities.insert(title1, content1);
        postgresTestingUtilities.insert(title2, content2);
        String[] correctPost = {title2, content2};
        assertArrayEquals(correctPost, postgres.selectPostByID(2));
        postgresTestingUtilities.clearData();
    }

    @Test
    public void testUpdatePostUpdatesPostData() throws Exception{
        postgres = new Postgres("test");
        Map newFieldsAndValues = new HashMap();
        String title = "A_title";
        String content = "something";
        postgresTestingUtilities.insert(title, content);
        newFieldsAndValues.put(postgres.ID, 1);
        newFieldsAndValues.put(postgres.TITLE, "New_Title");
        newFieldsAndValues.put(postgres.CONTENT, "new Content");
        postgres.updatePost(newFieldsAndValues);
        String[] correctPost = {"New_Title", "new Content"};
        String[] postContents = new String[2];
        ResultSet resultSet = postgresTestingUtilities.select(1);
        while (resultSet.next()) {
            postContents[0] = resultSet.getString(postgres.TITLE);
            postContents[1] = resultSet.getString(postgres.CONTENT);
        }
        assertArrayEquals(correctPost, postContents);
        postgresTestingUtilities.clearData();
    }

    @Test
    public void testSelectByIDReturnsNullIfIDDoesNotExist() throws Exception {
        postgres = new Postgres("test");
        String[] correctPost = {null, null};
        assertArrayEquals(correctPost, postgres.selectPostByID(2));
        postgresTestingUtilities.clearData();
    }

    @Test
    public void testDeleteByIDDeletesPost() throws Exception{
        postgres = new Postgres("test");
        String title = "A title";
        String content = "something";
        postgresTestingUtilities.insert(title, content);
        postgres.deleteByID(1);
        String[] post = {null, null};
        assertArrayEquals(post, postgres.selectPostByID(1));
        postgresTestingUtilities.clearData();
    }

    @Test
    public void testGetAllPostIDsReturnsIntegerArrayOfAllPostIDs() throws Exception {
        postgres = new Postgres("test");
        String title1 = "A_title";
        String content1 = "something";
        String title2 = title1 + "2";
        String content2 = content1 + "2";
        postgresTestingUtilities.insert(title1, content1);
        postgresTestingUtilities.insert(title2, content2);
        String[][] posts = {{"1", title1, content1}, {"2", title2, content2}};
        assertArrayEquals(posts, postgres.getAllPosts());
        postgresTestingUtilities.clearData();
    }

}