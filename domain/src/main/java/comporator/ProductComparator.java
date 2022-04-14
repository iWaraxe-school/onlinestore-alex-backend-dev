package comporator;

import sorttypes.OrderSort;
import org.apache.commons.lang3.builder.CompareToBuilder;
import product.Product;
import java.util.Comparator;
import java.util.Map;

public class ProductComparator implements Comparator<Product> {
    public Map<String,String> sortBy;

    public ProductComparator(Map sortBy) {
        this.sortBy = sortBy;
    }

    @Override
    public int compare(Product a, Product b) {
        CompareToBuilder compareToBuilder = new CompareToBuilder();

        for (Map.Entry<String, String> item : sortBy.entrySet()) {

            try {
                var aPropertyValue = getPropertyValue(a, item.getKey());
                var bPropertyValue = getPropertyValue(b, item.getKey());
                if(item.getValue().equals(OrderSort.ASC)){
                    compareToBuilder.append(aPropertyValue, bPropertyValue);
                }
                else{
                    compareToBuilder.append(bPropertyValue, aPropertyValue);
                }
            } catch (Exception e) {
                System.out.println("Exception was thrown:" + e.getMessage());
            }
        }

        return compareToBuilder.toComparison();
    }

    private String getPropertyValue(Product product, String property) throws Exception {
        try {
            String propertyValue = product.getClass().getDeclaredField(property).get(product).toString();
            return propertyValue;
        }

        catch (NoSuchFieldException | IllegalAccessException ex) {
            throw new Exception(ex);
        }
    }
}
