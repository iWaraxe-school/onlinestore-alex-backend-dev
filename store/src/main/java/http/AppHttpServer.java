package http;

import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class AppHttpServer {

    public static final String USERNAME = "admin";
    public static final String PASSWORD = "password";

    //Execution of the server

    public void startServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            createContext(server, "/categories", new CategoryHandler());
            createContext(server, "/cart", new CartHandler());
            server.setExecutor(null);
            server.start();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    private void createContext(HttpServer server, String path, HttpHandler handler) {
        server.createContext(path, handler).setAuthenticator(new BasicAuthenticator("test") {
            @Override
            public boolean checkCredentials(String username, String password) {
                return username.equals(USERNAME) && password.equals(PASSWORD);
            }
        });
    }
}
