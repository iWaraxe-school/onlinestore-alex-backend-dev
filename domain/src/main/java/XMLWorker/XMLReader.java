package XMLWorker;

import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public class XMLReader {

    private String configFilePath = "onlinestore-alex-backend-dev/consoleApp/src/main/resources/config.xml";

    public LinkedHashMap<String,String> XMLRead() throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        XMLParser handler = new XMLParser();
        parser.parse(new File(configFilePath), handler);
        var resultHandler = handler.getSort();

        return resultHandler;
    }
}
