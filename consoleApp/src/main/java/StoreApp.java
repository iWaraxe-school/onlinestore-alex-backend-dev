public class StoreApp {

    public void main() {
        RandomStorePopulator randomStorePopulator = new RandomStorePopulator(new StoreHelper());

        var storeCreation = randomStorePopulator.createStore();

        System.out.println(storeCreation);
    }
}
