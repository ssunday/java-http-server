package TestingSupport;

import java.sql.*;

public class PostgresTestingUtilities {

    public final String TABLE_NAME = "test";

    private final String DATABASE_NAME = "WIKI";
    private final String DATABASE_PORT = "5432";
    private final String USER_NAME = System.getProperty("user.name");

    private String idColumn;
    private String titleColumn;
    private String contentColumn;

    private Connection connection = null;

    public PostgresTestingUtilities(String idColumn, String titleColumn, String contentColumn){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:" + DATABASE_PORT + "/"
                    + DATABASE_NAME, USER_NAME, "");
        }catch (Exception e){}
        this.idColumn = idColumn;
        this.titleColumn = titleColumn;
        this.contentColumn = contentColumn;
    }

    public void insert(String title, String content){
        String query = String.format("INSERT INTO test (%s,%s) VALUES ('%s','%s')", titleColumn, contentColumn, title, content);
        execQuery(query);
    }

    public ResultSet select(int id){
        String query = String.format("SELECT %s, %s FROM %s WHERE id=%s", titleColumn, contentColumn, TABLE_NAME, id);
        return execQuery(query);
    }

    public void clearData(){
        String deleteQuery = String.format("DELETE FROM %s", TABLE_NAME);
        String resetSequenceQuery = String.format("ALTER SEQUENCE %s_%s_seq RESTART", TABLE_NAME, idColumn);
        execQuery(deleteQuery);
        execQuery(resetSequenceQuery);
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
