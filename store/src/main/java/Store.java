import categories.Category;

import java.util.List;

public class Store {

    private List<Category> categories;

    public Store(List<Category> categories) {

        this.categories = categories;
    }

    public String toString() {
        for(Category item : categories)
            System.out.println(item);

        return "";
    }
}
