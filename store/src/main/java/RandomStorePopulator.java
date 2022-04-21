public class RandomStorePopulator {
    private static StoreHelper storeHelper;

    public static StoreHelper getRandomStorePopulator() {
        if (storeHelper == null) {
            storeHelper = new StoreHelper();
        }

        return storeHelper;
    }

    private RandomStorePopulator() {

    }
}
