package http;

import categories.Category;
import product.Product;
import java.util.List;

public class HttpPopulator {

    private final HttpClientt httpClient = new HttpClientt();

    public List<Category> getAllCategories() {
        return httpClient.getAllCategories();
    }

    public void addToCart(Product product) {
        httpClient.addProductToCart(product);
    }
}
