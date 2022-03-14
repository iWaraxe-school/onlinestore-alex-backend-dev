public class RandomStorePopulator {

    private StoreHelper storeHelper;

    public RandomStorePopulator(StoreHelper storeHelper) {
        this.storeHelper = storeHelper;
    }

    public Store createStore() {
        return storeHelper.createStore();
    }
}
