package Routes.Wiki.DelivererSupport;
import java.sql.*;
import java.util.Arrays;
import java.util.Map;

public class Postgres implements DataType{

    private final String DATABASE_NAME = "WIKI";
    private final String DATABASE_PORT = "5432";
    private final String USER_NAME = System.getProperty("user.name");

    private String tableName;
    private Connection connection = null;

    public Postgres(String tableName){
        this.tableName = tableName;
        this.initiateConnection();
        this.createTable();
    }

    public void addPost(Map fieldsAndValues){
        String[] parsedFieldAndValues = parseFieldsAndValues(fieldsAndValues);
        String query = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName,  parsedFieldAndValues[0], parsedFieldAndValues[1]);
        execQuery(query);
    }

    public void updatePost(Map newFieldsAndValues){
        int id = (Integer) newFieldsAndValues.get(ID);
        String newTitle = (String) newFieldsAndValues.get(TITLE);
        String newContent = (String) newFieldsAndValues.get(CONTENT);
        String query = String.format("UPDATE %s SET " + TITLE + "='%s', " + CONTENT + "='%s' " + "WHERE " + ID + "=%s", tableName, newTitle, newContent, id);
        execQuery(query);
    }

    public String[][] getAllPosts(){
        String[][] postIDs = new String[100000][3];
        int count = 0;
        String query = String.format("SELECT * FROM %s", tableName);
        ResultSet resultSet = execQuery(query);
        try {
            while (resultSet.next()) {
                postIDs[count][0] = resultSet.getString(ID);
                postIDs[count][1] = resultSet.getString(TITLE);
                postIDs[count][2] = resultSet.getString(CONTENT);
                count++;
            }
        } catch (SQLException se){}
        return Arrays.copyOfRange(postIDs,0, count);
    }

    public String[] selectPostByID(int id){
        String[] postContents = new String[2];
        String query = String.format("SELECT * FROM %s WHERE id=%s", tableName, id);
        ResultSet resultSet = execQuery(query);
        try {
            while (resultSet.next()) {
                postContents[0] = resultSet.getString(TITLE);
                postContents[1] = resultSet.getString(CONTENT);
            }
        } catch (SQLException se){}
        return postContents;
    }

    public void deleteByID(int id){
        execQuery("DELETE FROM " + tableName + " WHERE " + ID + "=" + id);
    }

    public void clearData(){
        execQuery("DELETE FROM " + tableName);
    }

    private void initiateConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:" + DATABASE_PORT + "/"
                    + DATABASE_NAME, USER_NAME, "");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void createTable(){
        execQuery("CREATE TABLE " + tableName + "(" + ID + " serial," + TITLE + " text," + CONTENT + " text)");
    }

    private String[] parseFieldsAndValues(Map fieldsAndValues){
        String[] parsed = new String[2];
        String fields = "";
        String values = "";
        for (Object field : fieldsAndValues.keySet()) {
            fields += field + ",";
            if (fieldsAndValues.get(field) instanceof String) {
                values += "'" + fieldsAndValues.get(field) + "'" + ",";
            }
            else values += fieldsAndValues.get(field) + ",";
        }
        parsed[0] = fields.substring(0, fields.length()-1);
        parsed[1] = values.substring(0, values.length()-1);
        return parsed;
    }

    private ResultSet execQuery(String query){
        ResultSet resultSet = null;
        try {
            Statement st = connection.createStatement();
            st.execute(query);
            resultSet = st.getResultSet();
        }catch (SQLException se){}
        return resultSet;
    }
}
