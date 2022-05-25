import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Scanner;

public class StoreApp {

    public static void main(String[] args) {

        try {
            StoreHelper storeHelper = RandomStorePopulator.getRandomStorePopulator();
            var store = storeHelper.createStore();
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
                        break;

                    case "create order":
                        storeHelper.createOrder(store.getListOfAllProducts().stream().findFirst().orElse(null).getNameProduct());
                        break;

                    default:
                        System.out.println("Incorrect command");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: the exception was thrown:" + e.getMessage());
        }
    }
}


