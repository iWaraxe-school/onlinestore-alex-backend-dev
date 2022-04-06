import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Scanner;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class StoreApp {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ParserConfigurationException, SAXException, IOException {

        RandomStorePopulator randomStorePopulator = new RandomStorePopulator(new StoreHelper());
        var storeCreation = randomStorePopulator.createStore();
        System.out.println(storeCreation);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        XMLParser handler = new XMLParser();
        parser.parse(new File("consoleApp/src/main/resources/config.xml"), handler);

        var resultHandler = handler.getSort();

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            switch (input.toLowerCase(Locale.ROOT)) {
                case "sort":
                    System.out.println("sort was called");
                    break;

                case "top" :
                    System.out.println("top was called");
                    break;

                case "quit" :
                    System.out.println("quit was called");
                    return;

                default:
                    System.out.println("incorrect command");
                    break;
            }
        }
    }
}


