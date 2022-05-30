public class RandomStorePopulator {
    private volatile static StoreHelper storeHelper;

    public static StoreHelper getRandomStorePopulator() {
        if (storeHelper == null) {
            synchronized (RandomStorePopulator.class) {
                if (storeHelper == null) {
                    storeHelper = new StoreHelper();
                }
            }
        }
        return storeHelper;
    }

    private RandomStorePopulator() {

    }
}
