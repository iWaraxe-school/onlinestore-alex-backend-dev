import java.sql.*;

public class DBHelper implements IDBManager {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbc_online_shop";
    private static final String USER = "root";
    private static final String PASS = "110411Sss";
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    public static final String TABLE_CATEGORY_NAME = "categories";
    public static final String TABLE_PRODUCT_NAME = "products";
    private static Connection connection = null;

    public DBHelper() {
        if (connection == null) {
            connection = getConnectionToDB();
        }
    }

    @Override
    public Connection getConnectionToDB() {
        try {
            Class.forName(DB_DRIVER);
        }
        catch (ClassNotFoundException classNotFoundException) {
            System.out.println("DB Driver  was not found. Exception message: " + classNotFoundException.getLocalizedMessage());
        }

        try {
            return DriverManager.getConnection(DB_URL, USER, PASS);
        }

        catch (SQLException sqlException) {
            System.out.println("Exception when getting database connection. Exception message: " + sqlException.getLocalizedMessage());
        }

        return null;
    }

    private void closeDBConnection() {

        try {
            if (connection != null) {
                connection.close();
            }

        } catch (SQLException sqlException) {
            System.out.println("Exception was thrown when closing database connection: " + sqlException.getMessage());
        }
    }

    @Override
    public void dispose() {
        closeDBConnection();
        connection = null;
    }

    @Override
    public void insertCategoryInDB(String categoryName) throws SQLException {
        Statement stmt;
        stmt = connection.createStatement();

        stmt.execute(String.format("INSERT INTO %s(categoryName) VALUES('%s')", TABLE_CATEGORY_NAME, categoryName));
    }

    @Override
    public void insertProductIntoDB(String name, String category, double price, double rate) throws SQLException {
        Statement stmt;
        stmt = connection.createStatement();

        String getCategoryIdCommand = String.format("SELECT id FROM %s WHERE categoryName='%s'", TABLE_CATEGORY_NAME, category);
        stmt.execute(String.format("INSERT INTO %s(productName,category_id,price,rate) VALUES('%s',%s,%f,%f)",
                TABLE_PRODUCT_NAME,  name, getCategoryIdCommand, price, rate));
    }
}
