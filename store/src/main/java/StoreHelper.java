import categories.Category;
import com.github.javafaker.Faker;
import org.reflections.Reflections;
import product.Product;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import static org.reflections.scanners.Scanners.SubTypes;

public class StoreHelper {

    private Faker faker;

    public StoreHelper() {
        faker = new Faker();
    }

    public Product createProduct(String name) {

        Double rate = Double.parseDouble(faker.commerce().price().replace(',','.'));
        Double price = Double.parseDouble(faker.commerce().price().replace(',', '.'));

        return new Product(name, rate, price);
    }

    public String getName(String categoryName) throws IllegalAccessException {
        switch(categoryName) {
            case "Food":
                return faker.food().sushi();
            case "Book":
                return faker.book().title();
            default:
                throw new IllegalAccessException("Not a correct category");
        }
    }

    public Store createStore() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Reflections reflections = new Reflections("categories");

        Set<Class<?>> categories =
                reflections.get(SubTypes.of(Category.class).asClass());

        ArrayList<Category> categoryList = new ArrayList<>();

        for (var category: categories) {
            var categoryInstance = (Category)category.getDeclaredConstructor().newInstance();

            Random random = new Random();
            int count = random.nextInt(20);

            for (var i = 0; i < count; i++) {
                categoryInstance.addProductCategory(createProduct(getName(categoryInstance.getNameCategory())));
            }
            categoryList.add(categoryInstance);
        }

        return new Store(categoryList);
    }
}
