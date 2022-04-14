package XMLWorker;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import java.util.LinkedHashMap;

public class XMLParser extends DefaultHandler {

    LinkedHashMap<String, String> sort = new LinkedHashMap<>();
    private StringBuilder data = null;

    public LinkedHashMap<String, String> getSort() {
        return sort;
    }

    boolean bPrice = false;
    boolean bName = false;
    boolean bRate = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("name")) {
            bName = true;
        } else if (qName.equalsIgnoreCase("price")) {
            bPrice = true;
        } else if (qName.equalsIgnoreCase("rate")) {
            bRate = true;
        }
        data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (bName) {
            sort.put("name", data.toString());
            bName = false;
        } else if (bPrice) {
            sort.put("price", data.toString());
            bPrice = false;
        } else if (bRate) {
            sort.put("rate", data.toString());
            bRate = false;
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }
}
