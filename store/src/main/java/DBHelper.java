import categories.Category;
import org.reflections.Reflections;
import product.Product;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
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

    public void setConnectionToDB() {
        try {
            CONNECTION = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("\nDatabase connection successfull\n");
            STATEMENT = CONNECTION.createStatement();
        } catch (SQLException exception) {
            System.out.println("Problems with DB connection");
            throw new RuntimeException(exception);
        }
    }

    public void clearDB() {
        //String alterTableCategories = "ALTER TABLE CATEGORIES";
        //String dropConstraint = "DROP Constraint products_categories_fk";
        String queryDropCategoriesTable = "DROP TABLE IF EXISTS CATEGORIES;";
        String queryDropProductsTable = "DROP TABLE IF EXISTS PRODUCTS;";
        try {
            //STATEMENT.executeUpdate(alterTableCategories);
            //STATEMENT.executeUpdate(dropConstraint);
            STATEMENT.executeUpdate(queryDropCategoriesTable);
            STATEMENT.executeUpdate(queryDropProductsTable);
        } catch (SQLException exception){
            System.out.println("Problems with cleaning of DB tables");
            throw new RuntimeException(exception);
        }
    }

    public void createCategoryTable() {
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

    public void createProductTable() {
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
            var categoryInstance = (Category)category.getDeclaredConstructor().newInstance();

            Random random = new Random();
            int count = random.nextInt(5, 20);

            for (var i = 0; i < count; i++) {
                categoryInstance.addProductCategory(storeHelper.createProduct(storeHelper.getName(categoryInstance.getNameCategory())));
            }
            categoryList.add(categoryInstance);
        }

        for(var category : categoryList) {
            System.out.println("Inserting category " + category.getNameCategory() + " into database");
            try {
                PreparedStatement insertCategories = CONNECTION.prepareStatement("INSERT INTO CATEGORIES(NAME) VALUES(?)");
                insertCategories.setString(1, category.getNameCategory());
                insertCategories.execute();

                PreparedStatement findCategoryID = CONNECTION.prepareStatement("SELECT ID FROM CATEGORIES WHERE NAME = ?");
                findCategoryID.setString(1, category.getNameCategory());
                RESULTSET = findCategoryID.executeQuery();

                Integer id = 0;
                while(RESULTSET.next()) {
                    id = RESULTSET.getInt("ID");
                }

                Random randomProductAmountToAdd = new Random();
                for (int i = 0; i < randomProductAmountToAdd.nextInt(15) + 1; i++) {
                    PreparedStatement insertProduct = CONNECTION.prepareStatement("INSERT INTO PRODUCTS(category_id, name, rate, price) VALUES(?, ?, ?, ?)");
                    insertProduct.setInt(1, id);
                    insertProduct.setString(2, storeHelper.getNameProduct(category.getNameCategory()));
                    insertProduct.setDouble(3, storeHelper.getRate());
                    insertProduct.setDouble(4, storeHelper.getPrice());
                    System.out.println(insertProduct);
                    insertProduct.execute();
                    System.out.println("One more product inserted");
                }
            }catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public void printFilledStore() {
        try {
            System.out.println("\nPrint Store from DataBase\n");
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




}