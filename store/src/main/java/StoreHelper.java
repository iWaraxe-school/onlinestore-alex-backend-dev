import categories.BikeCategory;
import categories.Category;
import categories.MilkCategory;
import categories.PhoneCategory;
import com.github.javafaker.Faker;
import product.Product;

import java.util.ArrayList;


public class StoreHelper {

    private Faker faker;

    public StoreHelper() {
        faker = new Faker();
    }

    public Product createProduct() {

        String name = faker.commerce().productName();
        Double rate = Double.parseDouble(faker.commerce().price().replace(',','.'));
        Double price = Double.parseDouble(faker.commerce().price().replace(',', '.'));

        return new Product(name, rate, price);
    }

    public Store createStore() {
        ArrayList<Category> categoryList = new ArrayList<>();

        MilkCategory milkCategory = new MilkCategory();
        milkCategory.addProductCategory(createProduct());

        BikeCategory bikeCategory = new BikeCategory();
        bikeCategory.addProductCategory(createProduct());

        PhoneCategory phoneCategory = new PhoneCategory();
        phoneCategory.addProductCategory(createProduct());

        categoryList.add(milkCategory);
        categoryList.add(bikeCategory);
        categoryList.add(phoneCategory);

        return new Store(categoryList);
    }
}
