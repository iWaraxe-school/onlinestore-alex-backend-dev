import http.AppHttpServer;
import product.Product;
import storage.DBHelper;
import storage.Store;
import storage.StoreHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StoreApp {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        initStore();
    }

    private static void initStore() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Store store = StoreHelper.createStore();
        StoreHelper storeHelper = new StoreHelper();

        clearCartByTimer(store);
        System.out.println("Available list of commands: print, sort, top, order, quit");


        try {
            DBHelper dbHelper = new DBHelper(storeHelper);
            storeHelper.setTimer();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            Boolean flag = true;
            while (flag) {

                System.out.println("Please, enter one of the commands: sort/top/quit/create order:");
                String command = reader.readLine();

                System.out.println("Your command is : " + command);

                switch (command.toLowerCase(Locale.ROOT)) {
                    case "sort":
                        store.printListProducts(storeHelper.sortAllProducts());
                        break;

                    case "top":
                        store.printListProducts(storeHelper.getTop5());
                        break;

                    case "quit":
                        flag = false;
                        storeHelper.shutdownThreads();
                        break;

                    case "create order":
                        storeHelper.createOrder(store.getListOfAllProducts().stream().findFirst().orElse(null).getNameProduct());
                        break;

                    case "db execution":
                        dbHelper.dbExecution();
                        break;

                    case "server execution":
                        new AppHttpServer().startServer();
                        initStore();

                    default:
                        System.out.println("Incorrect command");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: the exception was thrown:" + e.getMessage());
        }
    }

    private static void clearCartByTimer(Store store) {
        Runnable cartCleaner = () -> {
            System.out.println("****** Before clearing the cart ******");
            for (Product product : store.getPurchasedProductList()) {
                System.out.println(product);
            }

            store.getPurchasedProductList().clear();
            System.out.println("****** After clearing the cart ******");
            for (Product product : store.getPurchasedProductList()) {
                System.out.println(product);
            }
            System.out.println("****** Cart has been cleared ******");
        };

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        scheduledExecutorService.scheduleAtFixedRate(cartCleaner, 60, 120, TimeUnit.SECONDS);
    }
}


