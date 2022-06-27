import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class StoreApp {

    public static void main(String[] args) {

        try {
            StoreHelper storeHelper = RandomStorePopulator.getRandomStorePopulator();
            var store = storeHelper.createStore();
            storeHelper.setTimer();

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_online_shop", "root", "110411Sss");
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM categories");

            while(resultSet.next()) {
                System.out.println(resultSet.getString("categoriescol"));
            }

            connection.close();

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

                    case "status":
                        var list = store.getPurchasedProductList();
                        if(list.size() == 0){
                            System.out.println("Purchase is 0");
                        }else {
                            store.printListProducts(list);
                        }
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


