import categories.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Store {

    private ArrayList<Category> categories;

    public Store(ArrayList<Category> categories) {

        this.categories = categories;
    }

    public String toString() {
        for(var item : categories)
            System.out.println(item);
        return "";
    }
}
