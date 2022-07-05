import java.sql.Connection;
import java.sql.SQLException;

public interface IDBManager {

    Connection getConnectionToDB();

    void dispose();

    void insertCategoryInDB(String categoryName) throws SQLException;

    void insertProductIntoDB(String name, String category, double price, double rate) throws SQLException;
}
