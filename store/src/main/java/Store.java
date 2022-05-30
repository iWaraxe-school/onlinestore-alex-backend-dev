import categories.Category;
import product.Product;

import java.util.ArrayList;
import java.util.List;

public class Store {

    private ArrayList<Category> categories;
    private ArrayList<Product> purchasedProducts;

    public Store(ArrayList<Category> categories) {

        this.categories = categories;
        purchasedProducts = new ArrayList<>();
    }

    public String toString() {
        for(var item : categories)
            System.out.println(item);
        return "";
    }

    public void printListProducts(List<Product> products) {
        for(var item : products)
            System.out.println(item);
    }

    public List<Product> getListOfAllProducts(){
        List<Product> products = new ArrayList<Product>();

        for(var item : categories){
            products.addAll(item.getProducts());
        }

        return  products;
    }

    public List<Product> getPurchasedProductList() {
        return purchasedProducts;
    }
}
