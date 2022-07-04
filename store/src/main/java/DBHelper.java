import java.sql.*;

public class DBHelper {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbc_online_shop";
    private static final String USER = "root";
    private static final String PASS = "110411Sss";



    public Connection getConnectionToDb() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        } finally {
            connection.close();
        }

        return connection;
    }

    public void addCategory(Connection connection) throws SQLException {

        PreparedStatement stmt = connection.prepareStatement("insert into categories " +
                        "values(?, ?)");
        stmt.setString(1, "Book category");
        stmt.setString(2, "Food category");
    }

    public void addProduct(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("insert into products " +
                    "values(1, 2, 'Bread', 1.5, 3)");
            stmt.executeUpdate("insert into products " +
                    "values(2, 2, 'Milk', 2.3, 4)");
            stmt.executeUpdate("insert into products " +
                    "values(3, 2, 'Cheese', 4.8, 8)");
            stmt.executeUpdate("insert into products " +
                    "values(4, 1, 'Harry Potter', 5.9, 12)");
            stmt.executeUpdate("insert into products " +
                    "values(5, 1, 'Alice in Wonderland', 7.9, 18)");
        } catch (SQLException sqlException) {
            sqlException.getMessage();
        }
    }


}
