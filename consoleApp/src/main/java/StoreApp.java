import java.lang.reflect.InvocationTargetException;

public class StoreApp {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        RandomStorePopulator randomStorePopulator = new RandomStorePopulator(new StoreHelper());
        var storeCreation = randomStorePopulator.createStore();
        System.out.println(storeCreation);
    }
}
