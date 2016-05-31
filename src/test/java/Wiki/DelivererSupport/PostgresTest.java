package Wiki.DelivererSupport;

import Wiki.DelivererSupport.Postgres;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PostgresTest {

    private final String DATABASE_NAME = "WIKI";
    private final String DATABASE_PORT = "5432";
    private final String USER_NAME = System.getProperty("user.name");

    private Postgres postgres;
    private Connection connection;

    @Before
    public void setUp() throws Exception{
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:" + DATABASE_PORT + "/"
                + DATABASE_NAME, USER_NAME, "");

    }

    @Test
    public void testAddPostEntersDataIntoTable() throws Exception {
        postgres = new Postgres("test");
        Map fieldsAndValues = new HashMap();
        fieldsAndValues.put(postgres.TITLE, "A title");
        fieldsAndValues.put(postgres.CONTENT, "something");
        postgres.addPost(fieldsAndValues);
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT * FROM " + "test");
        assertNotNull(resultSet.next());
        postgres.clearData();
    }

    @Test
    public void testGetLatestIDReturnsLatestID() throws Exception{
        postgres = new Postgres("test");
        Map fieldsAndValues = new HashMap();
        fieldsAndValues.put(postgres.TITLE, "A title");
        fieldsAndValues.put(postgres.CONTENT, "something");
        postgres.addPost(fieldsAndValues);
        assertEquals(1, postgres.getLatestID());
        postgres.clearData();
    }

    @Test
    public void testSelectPostByIDGetsCorrectDataFromID() throws Exception{
        postgres = new Postgres("test");
        Map fieldsAndValues1 = new HashMap();
        Map fieldsAndValues2 = new HashMap();
        Map fieldsAndValues3 = new HashMap();
        fieldsAndValues1.put(postgres.TITLE, "A title");
        fieldsAndValues1.put(postgres.CONTENT, "something");
        fieldsAndValues2.put(postgres.TITLE, "A title 2");
        fieldsAndValues2.put(postgres.CONTENT, "something 2");
        fieldsAndValues3.put(postgres.TITLE, "A title 3");
        fieldsAndValues3.put(postgres.CONTENT, "something 3");
        postgres.addPost(fieldsAndValues1);
        postgres.addPost(fieldsAndValues2);
        postgres.addPost(fieldsAndValues3);
        String[] correctPost = {"A title 2", "something 2"};
        assertArrayEquals(correctPost, postgres.selectPostByID(2));
        postgres.clearData();
    }

    @Test
    public void testUpdatePostUpdatesPostData() throws Exception{
        postgres = new Postgres("test");
        Map fieldsAndValues1 = new HashMap();
        Map newFieldsAndValues = new HashMap();
        fieldsAndValues1.put(postgres.TITLE, "A title");
        fieldsAndValues1.put(postgres.CONTENT, "something");
        postgres.addPost(fieldsAndValues1);
        newFieldsAndValues.put(postgres.ID, 1);
        newFieldsAndValues.put(postgres.TITLE, "New Title");
        newFieldsAndValues.put(postgres.CONTENT, "new Content");
        postgres.updatePost(newFieldsAndValues);
        String[] correctPost = {"New Title", "new Content"};
        String[] postContents = new String[2];
        String query = String.format("SELECT * FROM %s WHERE id=%s", "test", 1);
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery(query);
        while (resultSet.next()) {
            postContents[0] = resultSet.getString(postgres.TITLE);
            postContents[1] = resultSet.getString(postgres.CONTENT);
        }
        assertArrayEquals(correctPost, postContents);
        postgres.clearData();
    }

    @Test
    public void testSelectByIDReturnsNullIfIDDoesNotExist() throws Exception {
        postgres = new Postgres("test");
        Map fieldsAndValues1 = new HashMap();
        fieldsAndValues1.put(postgres.TITLE, "A title");
        fieldsAndValues1.put(postgres.CONTENT, "something");
        postgres.addPost(fieldsAndValues1);
        String[] correctPost = {null, null};
        assertArrayEquals(correctPost, postgres.selectPostByID(2));
        postgres.clearData();
    }

    @Test
    public void testDeleteByIDDeletesPost() throws Exception{
        postgres = new Postgres("test");
        Map fieldsAndValues1 = new HashMap();
        fieldsAndValues1.put(postgres.TITLE, "A title");
        fieldsAndValues1.put(postgres.CONTENT, "something");
        postgres.addPost(fieldsAndValues1);
        postgres.deleteByID(1);
        String[] post = {null, null};
        assertArrayEquals(post, postgres.selectPostByID(1));
        postgres.clearData();
    }

    @Test
    public void testGetAllPostIDsReturnsIntegerArrayOfAllPostIDs() throws Exception{
        postgres = new Postgres("test");
        Map fieldsAndValues1 = new HashMap();
        Map fieldsAndValues2 = new HashMap();
        fieldsAndValues1.put(postgres.TITLE, "A title");
        fieldsAndValues1.put(postgres.CONTENT, "something");
        fieldsAndValues2.put(postgres.TITLE, "A title 2");
        fieldsAndValues2.put(postgres.CONTENT, "something 2");
        postgres.addPost(fieldsAndValues1);
        postgres.addPost(fieldsAndValues2);
        String[][] posts = {{"1","A title", "something"},{"2",  "A title 2", "something 2"}};
        assertArrayEquals(posts, postgres.getAllPosts());
        postgres.clearData();
    }

    @Test
    public void testClearDataDeletesTableContents() throws Exception{
        postgres = new Postgres("test");
        Map fieldsAndValues1 = new HashMap();
        fieldsAndValues1.put(postgres.TITLE, "A title");
        fieldsAndValues1.put(postgres.CONTENT, "something");
        postgres.addPost(fieldsAndValues1);
        postgres.clearData();
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT * FROM " + "test");
        assertFalse(resultSet.next());
        postgres.clearData();
    }

}