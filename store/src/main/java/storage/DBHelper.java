package storage;

import categories.Category;
import com.github.javafaker.Cat;
import org.reflections.Reflections;
import product.Product;
import storage.StoreHelper;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;

public class DBHelper {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbc_online_shop";
    private static final String USER = "root";
    private static final String PASS = "110411Sss";
    private static Connection CONNECTION = null;
    private static Statement STATEMENT = null;
    private static ResultSet RESULTSET = null;

    private StoreHelper storeHelper;

    public DBHelper(StoreHelper storeHelper) {
        this.storeHelper = storeHelper;
    }

    private void setConnectionToDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            CONNECTION = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("\nDatabase connection successfull\n");
            STATEMENT = CONNECTION.createStatement();
        } catch (SQLException exception) {
            System.out.println("Problems with DB connection");
            throw new RuntimeException(exception);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearDB() {
        String queryDropCategoriesTable = "DROP TABLE IF EXISTS CATEGORIES;";
        String queryDropProductsTable = "DROP TABLE IF EXISTS PRODUCTS;";
        try {
            STATEMENT.executeUpdate(queryDropProductsTable);
            STATEMENT.executeUpdate(queryDropCategoriesTable);
        } catch (SQLException exception){
            System.out.println("Problems with cleaning of DB tables");
            throw new RuntimeException(exception);
        }
    }

    private void createCategoryTable() {
        String queryCreateCategoryTable = "CREATE TABLE IF NOT EXISTS CATEGORIES (" +
                "ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                "NAME VARCHAR(255) NOT NULL);";
        try {
            STATEMENT.executeUpdate(queryCreateCategoryTable);
        } catch (SQLException exception) {
            System.out.println("Problems with creation of category table");
            throw new RuntimeException(exception);
        }
    }

    private void createProductTable() {
        String queryCreateProductTable = "CREATE TABLE IF NOT EXISTS PRODUCTS (" +
                "ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                "CATEGORY_ID INT NOT NULL," +
                "NAME VARCHAR(255) NOT NULL," +
                "RATE DECIMAL(10, 2) NOT NULL," +
                "PRICE DECIMAL(10, 2) NOT NULL," +
                "FOREIGN KEY(CATEGORY_ID) REFERENCES CATEGORIES(ID));";
        try {
            STATEMENT.executeUpdate(queryCreateProductTable);
        } catch (SQLException exception) {
            System.out.println("Problems with creation of product table");
            throw new RuntimeException(exception);
        }
    }

    public void fillStoreRandomly() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        StoreHelper storeHelper = new StoreHelper();
        Reflections reflections = new Reflections("categories");

        Set<Class<?>> categorySet =
                reflections.get(SubTypes.of(Category.class).asClass());

        ArrayList<Category> categoryList = new ArrayList<>();

        for (var category: categorySet) {
            var categoryInstance = (Category) category.getDeclaredConstructor().newInstance();
            try {
                PreparedStatement insertCategories = CONNECTION.prepareStatement("INSERT INTO CATEGORIES(NAME) VALUES(?)");
                insertCategories.setString(1, categoryInstance.getNameCategory());
                insertCategories.execute();

                PreparedStatement findCategoryID = CONNECTION.prepareStatement("SELECT ID FROM CATEGORIES WHERE NAME = ?");
                findCategoryID.setString(1, categoryInstance.getNameCategory());
                RESULTSET = findCategoryID.executeQuery();

                Integer id = 0;
                while (RESULTSET.next()) {
                    id = RESULTSET.getInt("ID");
                }

                Random random = new Random();
                int count = random.nextInt(5, 20);

                for (var i = 0; i < count; i++) {
                    Product product = storeHelper.createProduct(storeHelper.getName(categoryInstance.getNameCategory()));
                    PreparedStatement insertProduct = CONNECTION.prepareStatement("INSERT INTO PRODUCTS(category_id, name, rate, price) VALUES(?, ?, ?, ?)");
                    insertProduct.setInt(1, id);
                    insertProduct.setString(2, product.getNameProduct());
                    insertProduct.setDouble(3, product.getRate());
                    insertProduct.setDouble(4, product.getPrice());
                    System.out.println(insertProduct);
                    insertProduct.execute();
                    System.out.println("One more product inserted");
                }
                categoryList.add(categoryInstance);
            } catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    private void printFilledStore() {
        try {
            System.out.println("\nPrint storage.Store from DataBase\n");
            RESULTSET = STATEMENT.executeQuery("SELECT * FROM CATEGORIES");

            System.out.println("List of Categories");
            while (RESULTSET.next()) {
                System.out.println(
                        RESULTSET.getInt("ID") + ", " +
                                RESULTSET.getString("NAME"));
            }

        RESULTSET = STATEMENT.executeQuery("SELECT * FROM PRODUCTS");

        System.out.println("\nList of Products");
        while (RESULTSET.next()) {
            System.out.println(
                    RESULTSET.getString("CATEGORY_ID") + ", " +
                            RESULTSET.getString("NAME") + ", " +
                            RESULTSET.getDouble("PRICE") + ", " +
                            RESULTSET.getString("RATE"));
        }
    } catch (Exception exception) {
        exception.printStackTrace();
        }
    }

    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String allCategories = "SELECT * FROM CATEGORIES";

        Statement stmt;
        stmt = CONNECTION.createStatement();
        stmt.execute(allCategories);

        ResultSet rs = stmt.getResultSet();

        while(rs.next()) {
            categories.add(new Category(rs.getString("category")));
        }

        return categories;
    }

    public void dbExecution() {
        setConnectionToDB();
        clearDB();
        createCategoryTable();
        createProductTable();
        try {
            fillStoreRandomly();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        printFilledStore();
    }
}

