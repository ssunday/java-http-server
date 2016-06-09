package Wiki.DelivererSupport;
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

    public int getLatestID(){
        int id = 0;
        String query = String.format("SELECT %s FROM %s ORDER BY %s DESC LIMIT 1", ID, tableName, ID);
        ResultSet rs = execQuery(query, new String[0]);
        try{
            while (rs.next()) {
                String stringID = rs.getString(ID);
                id = Integer.parseInt(stringID);
            }
        }catch (SQLException se){}
        return id;
    }

    public void addPost(Map fieldsAndValues){
        String[] parsedFieldAndValues = parseFieldsAndValues(fieldsAndValues);
        String query = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, parsedFieldAndValues[0], parsedFieldAndValues[1]);
        execQuery(query);
    }

    public void updatePost(Map newFieldsAndValues){
        String id = Integer.toString((Integer) newFieldsAndValues.get(ID));
        String newTitle = (String) newFieldsAndValues.get(TITLE);
        String newContent = (String) newFieldsAndValues.get(CONTENT);
        String query = String.format("UPDATE %s SET %s=?, %s=? WHERE %s=?", tableName, TITLE, CONTENT, ID);
        String[] args = {newTitle, newContent, id};
        execQuery(query, args);
    }

    public String[][] getAllPosts(){
        String[][] postIDs = new String[100000][3];
        int count = 0;
        String query = String.format("SELECT * FROM %s", tableName);
        ResultSet resultSet = execQuery(query, new String[0]);
        try {
            while (resultSet.next()) {
                postIDs[count][0] = resultSet.getString(ID);
                postIDs[count][1] = resultSet.getString(TITLE);
                postIDs[count][2] = resultSet.getString(CONTENT);
                count++;
            }
        } catch (SQLException se){}
        return Arrays.copyOfRange(postIDs, 0, count);
    }

    public String[] selectPostByID(int id){
        String[] postContents = new String[2];
        String query = String.format("SELECT * FROM %s WHERE %s=?", tableName, ID);
        String[] args = {Integer.toString(id)};
        ResultSet resultSet = execQuery(query, args);
        try {
            while (resultSet.next()) {
                postContents[0] = resultSet.getString(TITLE);
                postContents[1] = resultSet.getString(CONTENT);
            }
        } catch (SQLException se){}
        return postContents;
    }

    public void deleteByID(int id){
        String query = String.format("DELETE FROM %s WHERE %s=?", tableName, ID);
        String[] args = {Integer.toString(id)};
        execQuery(query,args);
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
        String query = String.format("CREATE TABLE %s (%s serial, %s text, %s text)", tableName, ID, TITLE, CONTENT);
        execQuery(query);
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

    private ResultSet execQuery(String query, String[] args){
        ResultSet resultSet = null;
        try {
            PreparedStatement st = connection.prepareStatement(query);
            for (int i = 1; i < args.length + 1; i++){
                try {
                    int arg = Integer.parseInt(args[i-1]);
                    st.setInt(i, arg);
                } catch (NumberFormatException e){
                    st.setString(i, args[i-1]);
                }
            }
            st.execute();
            resultSet = st.getResultSet();
        }catch (SQLException se){}
        return resultSet;
    }
}
