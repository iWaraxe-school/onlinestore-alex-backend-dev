package http;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.SneakyThrows;
import product.Product;
import storage.Store;
import storage.StoreHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CartHandler implements HttpHandler {

    @SneakyThrows
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.readValue(requestBody, Product.class);
        Store store = StoreHelper.createStore();
        store.getPurchasedProductList().add(product);
        String resonse = "Product is added to the cart";
        exchange.sendResponseHeaders(200, resonse.length());
        OutputStream os = exchange.getResponseBody();
        os.write(resonse.getBytes());
        os.close();
    }
}
