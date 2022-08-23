package http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.SneakyThrows;
import product.Product;
import storage.Store;
import storage.StoreHelper;
import java.io.InputStream;
import java.io.OutputStream;

public class CartHandler implements HttpHandler {

    @SneakyThrows
    @Override
    public void handle(HttpExchange exchange) {
        InputStream requestBody = exchange.getRequestBody();
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.readValue(requestBody, Product.class);
        Store store = StoreHelper.createStore();
        store.getPurchasedProductList().add(product);
        String response = "Product is added to the cart";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
