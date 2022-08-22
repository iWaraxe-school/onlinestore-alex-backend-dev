package http;

import categories.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import storage.DBHelper;
import storage.StoreHelper;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        StoreHelper storeHelper = new StoreHelper();
        List<Category> categories;
        try {
            categories = new DBHelper(storeHelper).getAllCategories();
        }

        catch (Exception e) {
            e.printStackTrace();
            categories = new ArrayList<>();
        }

        ObjectMapper mapper = new ObjectMapper();
        OutputStream out = null;

        try {
            byte[] jsonInBytes = mapper.writeValueAsBytes(categories);
            Headers headers = exchange.getResponseHeaders();
            headers.add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, jsonInBytes.length);
            out = exchange.getResponseBody();
            out.write(jsonInBytes);
        }

        finally {
            if (Objects.nonNull(out))
                out.close();
        }
    }
}
