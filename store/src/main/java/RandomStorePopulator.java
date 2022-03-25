import java.lang.reflect.InvocationTargetException;

public class RandomStorePopulator {

    private StoreHelper storeHelper;

    public RandomStorePopulator(StoreHelper storeHelper) {
        this.storeHelper = storeHelper;
    }

    public Store createStore() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return storeHelper.createStore();
    }
}
