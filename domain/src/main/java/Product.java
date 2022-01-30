public class Product {

    private String name;
    private Integer rate;
    private Integer price;

    public Product(String name, Integer rate, Integer price) {
        this.name = name;
        this.rate = rate;
        this.price = price;
    }

    public String getNameProduct() {
        return name;
    }

    public Integer getRate() {
        return rate;
    }

    public Integer getPrice() {
        return price;
    }
}
