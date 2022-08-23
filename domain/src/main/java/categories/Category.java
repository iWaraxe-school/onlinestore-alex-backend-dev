package categories;

import product.Product;

import java.util.ArrayList;
import java.util.List;

public class Category {

    public String name;
    protected List<Product> products;

    public Category(String name) {

        this.name = name;
        products = new ArrayList<>();
    }

    public String getNameCategory() {
        return name;
    }

    public void addProductCategory(Product product) {
        products.add(product);
    }

    public String toString() {
        System.out.println("categories.Category name: '" + this.name + "'");

        for(Product item : products)
            System.out.println(item);

        return "";
    }

    public List<Product> getProducts(){
        return products;
    }
}
