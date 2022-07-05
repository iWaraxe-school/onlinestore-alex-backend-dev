import categories.Category;
import org.reflections.Reflections;
import java.sql.SQLException;
import java.util.Random;
import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;

public class DBPopulator {

    private IDBManager dbManager;

    public DBPopulator(){

        this.dbManager = new DBHelper();
    }

    public void fillDbByFaker() {

        try {
            StoreHelper storeHelper = new StoreHelper();
            var product = storeHelper.createProduct("Milk");
            Reflections reflections = new Reflections("categories");

            Set<Class<?>> categories =
                    reflections.get(SubTypes.of(Category.class).asClass());

            for (var category: categories) {
                dbManager.insertCategoryInDB(category.getName());

                Random random = new Random();
                int count = random.nextInt(5, 20);

                for (var i = 0; i < count; i++) {
                    dbManager.insertProductIntoDB(product.name, category.getName(), product.price, product.rate);
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("SQL Exception Message in fillDbByFaker(): " + sqlException.getLocalizedMessage());
            dbManager.dispose();
        }
    }
}
