package product;

public class Product {

    private String name;
    private Double rate;
    private Double price;

    public Product(String name, Double rate, Double price) {
        this.name = name;
        this.rate = rate;
        this.price = price;
    }

    public String getNameProduct() {
        return name;
    }

    public Double getRate() {
        return rate;
    }

    public Double getPrice() {
        return price;
    }

    public String toString() {
        return "Product name: '" + this.name + "', Rate: '" + this.rate + "', Price: '" + this.price + "'";
    }
}
