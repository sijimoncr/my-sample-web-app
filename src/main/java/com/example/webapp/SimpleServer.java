package com.example.webapp;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.example.webapp.DatabaseManager;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.List;

public class SimpleServer {
    
    public static void main(String[] args) throws IOException {
        // Initialize database
        try {
        	DatabaseManager.initializeDatabase();
            System.out.println("Database initialized successfully");
        } catch (Exception e) {
            System.err.println("Database initialization failed: " + e.getMessage());
            return;
        }
        
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        // Register all your application routes
        server.createContext("/", new HomeHandler());
        server.createContext("/home", new HomeHandler());
        server.createContext("/users", new UsersHandler());
        server.createContext("/products", new ProductsHandler());
        server.createContext("/about", new AboutHandler());
        server.createContext("/test", new TestHandler());
        
        server.setExecutor(null);
        server.start();
        
        System.out.println("Jakarta EE Web App started on http://localhost:8080");
        System.out.println("Pages available:");
        System.out.println("  - Home: http://localhost:8080/");
        System.out.println("  - Users: http://localhost:8080/users");
        System.out.println("  - Products: http://localhost:8080/products");
        System.out.println("  - About: http://localhost:8080/about");
        System.out.println("  - Test: http://localhost:8080/test");
    }
    
    static class HomeHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            try {
                List<User> users = DatabaseManager.getAllUsers();
                List<Product> products = DatabaseManager.getAllProducts();
                
                String html = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>Sample Web App - Home</title>
                        <style>
                            body { font-family: Arial, sans-serif; margin: 40px; }
                            .navbar { background: #333; color: white; padding: 15px; margin: -40px -40px 20px; }
                            .navbar h1 { margin: 0; display: inline; }
                            .nav-links { float: right; }
                            .nav-links a { color: white; text-decoration: none; margin-left: 20px; }
                            .stats { display: flex; gap: 20px; margin: 20px 0; }
                            .stat-card { border: 1px solid #ddd; padding: 20px; border-radius: 5px; }
                            .number { font-size: 24px; font-weight: bold; color: #007bff; }
                        </style>
                    </head>
                    <body>
                        <div class="navbar">
                            <h1>Sample Web App (Simple HTTP Server)</h1>
                            <div class="nav-links">
                                <a href="/">Home</a>
                                <a href="/users">Users</a>
                                <a href="/products">Products</a>
                                <a href="/about">About</a>
                            </div>
                        </div>
                        
                        <h1>Welcome to Sample Web App</h1>
                        <p>A Java web application with SQLite database</p>
                        
                        <div class="stats">
                            <div class="stat-card">
                                <h3>Users</h3>
                                <div class="number">%d</div>
                                <a href="/users">View Users</a>
                            </div>
                            
                            <div class="stat-card">
                                <h3>Products</h3>
                                <div class="number">%d</div>
                                <a href="/products">View Products</a>
                            </div>
                        </div>
                    </body>
                    </html>
                    """.formatted(users.size(), products.size());
                
                sendResponse(exchange, 200, html);
                
            } catch (SQLException e) {
                String error = "<h1>Database Error</h1><p>" + e.getMessage() + "</p>";
                sendResponse(exchange, 500, error);
            }
        }
    }
    
    static class UsersHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            try {
                List<User> users = DatabaseManager.getAllUsers();
                
                StringBuilder userRows = new StringBuilder();
                for (User user : users) {
                    userRows.append("<tr>")
                            .append("<td>").append(user.getId()).append("</td>")
                            .append("<td>").append(escapeHtml(user.getName())).append("</td>")
                            .append("<td>").append(escapeHtml(user.getEmail())).append("</td>")
                            .append("<td>").append(user.getCreatedAt()).append("</td>")
                            .append("</tr>");
                }
                
                String html = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>Sample Web App - Users</title>
                        <style>
                            body { font-family: Arial, sans-serif; margin: 40px; }
                            .navbar { background: #333; color: white; padding: 15px; margin: -40px -40px 20px; }
                            .navbar h1 { margin: 0; display: inline; }
                            .nav-links { float: right; }
                            .nav-links a { color: white; text-decoration: none; margin-left: 20px; }
                            table { border-collapse: collapse; width: 100%%; margin: 20px 0; }
                            th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
                            th { background-color: #f2f2f2; }
                        </style>
                    </head>
                    <body>
                        <div class="navbar">
                            <h1>Sample Web App</h1>
                            <div class="nav-links">
                                <a href="/">Home</a>
                                <a href="/users">Users</a>
                                <a href="/products">Products</a>
                                <a href="/about">About</a>
                            </div>
                        </div>
                        
                        <h1>Users</h1>
                        <table>
                            <thead>
                                <tr><th>ID</th><th>Name</th><th>Email</th><th>Created</th></tr>
                            </thead>
                            <tbody>
                                %s
                            </tbody>
                        </table>
                        <p><a href="/">← Back to Home</a></p>
                    </body>
                    </html>
                    """.formatted(userRows.toString());
                
                sendResponse(exchange, 200, html);
                
            } catch (SQLException e) {
                String error = "<h1>Database Error</h1><p>" + e.getMessage() + "</p>";
                sendResponse(exchange, 500, error);
            }
        }
    }
    
    static class ProductsHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            try {
                List<Product> products = DatabaseManager.getAllProducts();
                
                StringBuilder productCards = new StringBuilder();
                for (Product product : products) {
                    productCards.append("<div class=\"product-card\">")
                              .append("<h3>").append(escapeHtml(product.getName())).append("</h3>")
                              .append("<div class=\"price\">$").append(String.format("%.2f", product.getPrice())).append("</div>")
                              .append("<p>").append(escapeHtml(product.getDescription())).append("</p>")
                              .append("<small>Added: ").append(product.getCreatedAt()).append("</small>")
                              .append("</div>");
                }
                
                String html = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>Sample Web App - Products</title>
                        <style>
                            body { font-family: Arial, sans-serif; margin: 40px; }
                            .navbar { background: #333; color: white; padding: 15px; margin: -40px -40px 20px; }
                            .navbar h1 { margin: 0; display: inline; }
                            .nav-links { float: right; }
                            .nav-links a { color: white; text-decoration: none; margin-left: 20px; }
                            .product-card { border: 1px solid #ddd; padding: 15px; margin: 10px 0; border-radius: 5px; }
                            .price { font-size: 18px; font-weight: bold; color: #28a745; }
                        </style>
                    </head>
                    <body>
                        <div class="navbar">
                            <h1>Sample Web App</h1>
                            <div class="nav-links">
                                <a href="/">Home</a>
                                <a href="/users">Users</a>
                                <a href="/products">Products</a>
                                <a href="/about">About</a>
                            </div>
                        </div>
                        
                        <h1>Products</h1>
                        %s
                        <p><a href="/">← Back to Home</a></p>
                    </body>
                    </html>
                    """.formatted(productCards.toString());
                
                sendResponse(exchange, 200, html);
                
            } catch (SQLException e) {
                String error = "<h1>Database Error</h1><p>" + e.getMessage() + "</p>";
                sendResponse(exchange, 500, error);
            }
        }
    }
    
    static class AboutHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            String html = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Sample Web App - About</title>
                    <style>
                        body { font-family: Arial, sans-serif; margin: 40px; }
                        .navbar { background: #333; color: white; padding: 15px; margin: -40px -40px 20px; }
                        .navbar h1 { margin: 0; display: inline; }
                        .nav-links { float: right; }
                        .nav-links a { color: white; text-decoration: none; margin-left: 20px; }
                    </style>
                </head>
                <body>
                    <div class="navbar">
                        <h1>Sample Web App</h1>
                        <div class="nav-links">
                            <a href="/">Home</a>
                            <a href="/users">Users</a>
                            <a href="/products">Products</a>
                            <a href="/about">About</a>
                        </div>
                    </div>
                    
                    <h1>About This Application</h1>
                    <p>This Java web application demonstrates:</p>
                    <ul>
                        <li><strong>Java 21</strong> - Modern language features</li>
                        <li><strong>Simple HTTP Server</strong> - Built-in Java web server</li>
                        <li><strong>SQLite Database</strong> - Lightweight data storage</li>
                        <li><strong>Maven</strong> - Dependency management</li>
                    </ul>
                    
                    <h2>Features</h2>
                    <ul>
                        <li>Database-driven content</li>
                        <li>Clean HTML generation</li>
                        <li>RESTful URL structure</li>
                        <li>No complex configuration</li>
                    </ul>
                    
                    <p><a href="/">← Back to Home</a></p>
                </body>
                </html>
                """;
            
            sendResponse(exchange, 200, html);
        }
    }
    
    static class TestHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            String html = "<h1>Test Servlet Works!</h1><p>Server is running correctly.</p>";
            sendResponse(exchange, 200, html);
        }
    }
    
    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    
    private static String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                  .replace("<", "&lt;")
                  .replace(">", "&gt;")
                  .replace("\"", "&quot;");
    }
}