import XMLWorker.XMLReader;
import categories.Category;
import com.github.javafaker.Faker;
import comporator.ProductComparator;
import lombok.SneakyThrows;
import sorttypes.OrderSort;
import org.reflections.Reflections;
import product.Product;
import javax.xml.parsers.ParserConfigurationException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.reflections.scanners.Scanners.SubTypes;

public class StoreHelper {

    private Faker faker;
    private Store store;

    public ExecutorService executorService = Executors.newFixedThreadPool(3);

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
            int count = random.nextInt(5, 20);

            for (var i = 0; i < count; i++) {
                categoryInstance.addProductCategory(createProduct(getName(categoryInstance.getNameCategory())));
            }
            categoryList.add(categoryInstance);
        }

        store = new Store(categoryList);
        return store;
    }

    public List<Product> sortAllProducts() throws Exception {
        Map<String,String> sortBy;

        try {
            XMLReader xml = new XMLReader();
            sortBy = xml.XMLRead();
        }
        catch (ParserConfigurationException e) {
            throw new Exception("Config file exception");
        }

        return sortAllProducts(sortBy);
    }

    public List<Product> sortAllProducts(Map<String, String> sortBy) {
        if(store == null){
            return new ArrayList<>();
        }

        List<Product> allProducts = this.store.getListOfAllProducts();
        allProducts.sort(new ProductComparator(sortBy));

        return allProducts;
    }

    public List<Product> getTop5() {
        Map<String,String> sortBy = new HashMap<>();
        sortBy.put("rate", OrderSort.DESC);

        List<Product> sortedList = sortAllProducts(sortBy);
        List<Product> top5 = new ArrayList<>(sortedList.subList(0, 5));

        return top5;
    }

    @SneakyThrows
    public void createOrder(String productName) {

        System.out.printf(Thread.currentThread().getName());
        Product orderedProduct = getOrderedProduct(productName);
        if(orderedProduct !=null) {
            int threadTime = new Random().nextInt(30);
            executorService.execute(() -> {
                try {
                    System.out.printf("Starting order ", Thread.currentThread().getName());
                    store.getPurchasedProductList().add(orderedProduct);
                    store.printListProducts(store.getPurchasedProductList());
                    Thread.sleep(threadTime * 1000);
                    System.out.printf("Finishing order ", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("createOrder() is finished " + Thread.currentThread().getName());
        }else {
            System.out.println("createOrder() is finished because orderProducts is not correct ");
        }
    }

    public void shutdownThreads(){
        executorService.shutdown();
    }

    private Product getOrderedProduct(String productName)
    {
        return store.getListOfAllProducts().stream().parallel().filter(x -> x.name.equals(productName)).findFirst().orElse(null);
    }
}
