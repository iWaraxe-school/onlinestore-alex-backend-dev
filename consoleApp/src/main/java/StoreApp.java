import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Scanner;

public class StoreApp {

    public static void main(String[] args) {

        try {
            var sh = new StoreHelper();
            RandomStorePopulator randomStorePopulator = new RandomStorePopulator(sh);
            var store = randomStorePopulator.createStore();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            Boolean flag = true;
            while (flag) {

                System.out.println("Please, enter one of the commands: sort/top/quit:");
                String command = reader.readLine();

                System.out.println("Your command is : " + command);

                switch (command.toLowerCase(Locale.ROOT)) {
                    case "sort":
                        store.printListProducts(sh.sortAllProducts());
                        break;

                    case "top":
                        store.printListProducts(sh.getTop5());
                        break;

                    case "quit":
                        flag = false;
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


