package com.example.webapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/", "/home", "/users", "/products", "/about"})
public class WebAppServlet extends HttpServlet {
    
    @Override
    public void init() throws ServletException {
        super.init();
        DatabaseManager.initializeDatabase();
        System.out.println("Database initialized successfully!");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        String path = request.getServletPath();
        
        try {
            switch (path) {
                case "/":
                case "/home":
                    showHomePage(response);
                    break;
                case "/users":
                    showUsersPage(response);
                    break;
                case "/products":
                    showProductsPage(response);
                    break;
                case "/about":
                    showAboutPage(response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
    
    private void showHomePage(HttpServletResponse response) throws IOException, SQLException {
        PrintWriter out = response.getWriter();
        List<User> users = DatabaseManager.getAllUsers();
        List<Product> products = DatabaseManager.getAllProducts();
        
        out.println("""
        	    <!DOCTYPE html>
        	    <html>
        	    <head>
        	        <title>Sample Web App - Home</title>
        	        <link rel="stylesheet" href="css/style.css">
        	    </head>
        	    <body>
        	        <nav class="navbar">
        	            <h1>Sample Web App</h1>
        	            <div class="nav-links">
        	                <a href="/">Home</a>
        	                <a href="/users">Users</a>
        	                <a href="/products">Products</a>
        	                <a href="/about">About</a>
        	            </div>
        	        </nav>
        	        
        	        <div class="container">
        	            <h1>Welcome to Sample Web App</h1>
        	            <p>A simple Java web application running on Jetty with SQLite database</p>
        	            
        	            <div class="stats">
        	                <div class="stat-card">
        	                    <h3>Users</h3>
        	                    <div class="number">""" + users.size() + """         	                    		
        	                    </div>
        	                    <a href="/users">View Users</a>
        	                </div>
        	                
        	                <div class="stat-card">
        	                    <h3>Products</h3>
        	                    <div class="number">""" + products.size() + """ 
        	                    </div>
        	                    <a href="/products">View Products</a>
        	                </div>
        	            </div>
        	        </div>
        	    </body>
        	    </html>
        	    """);
    }
    
    private void showUsersPage(HttpServletResponse response) throws IOException, SQLException {
        PrintWriter out = response.getWriter();
        List<User> users = DatabaseManager.getAllUsers();
        
        out.println("""
            <!DOCTYPE html>
            <html>
            <head>
                <title>Sample Web App - Users</title>
                <link rel="stylesheet" href="css/style.css">
            </head>
            <body>
                <nav class="navbar">
                    <h1>Sample Web App</h1>
                    <div class="nav-links">
                        <a href="/">Home</a>
                        <a href="/users" class="active">Users</a>
                        <a href="/products">Products</a>
                        <a href="/about">About</a>
                    </div>
                </nav>
                
                <div class="container">
                    <h1>Users</h1>
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Created At</th>
                            </tr>
                        </thead>
                        <tbody>
            """);
        
        for (User user : users) {
            out.println("<tr>");
            out.println("<td>" + user.getId() + "</td>");
            out.println("<td>" + user.getName() + "</td>");
            out.println("<td>" + user.getEmail() + "</td>");
            out.println("<td>" + user.getCreatedAt() + "</td>");
            out.println("</tr>");
        }
        
        out.println("""
                        </tbody>
                    </table>
                    <p><a href="/">← Back to Home</a></p>
                </div>
            </body>
            </html>
            """);
    }
    
    private void showProductsPage(HttpServletResponse response) throws IOException, SQLException {
        PrintWriter out = response.getWriter();
        List<Product> products = DatabaseManager.getAllProducts();
        
        out.println("""
            <!DOCTYPE html>
            <html>
            <head>
                <title>Sample Web App - Products</title>
                <link rel="stylesheet" href="css/style.css">
            </head>
            <body>
                <nav class="navbar">
                    <h1>Sample Web App</h1>
                    <div class="nav-links">
                        <a href="/">Home</a>
                        <a href="/users">Users</a>
                        <a href="/products" class="active">Products</a>
                        <a href="/about">About</a>
                    </div>
                </nav>
                
                <div class="container">
                    <h1>Products</h1>
                    <div class="products-grid">
            """);
        
        for (Product product : products) {
            out.println("<div class=\"product-card\">");
            out.println("<h3>" + product.getName() + "</h3>");
            out.println("<div class=\"price\">$" + String.format("%.2f", product.getPrice()) + "</div>");
            out.println("<p>" + product.getDescription() + "</p>");
            out.println("<small>Added: " + product.getCreatedAt() + "</small>");
            out.println("</div>");
        }
        
        out.println("""
                    </div>
                    <p><a href="/">← Back to Home</a></p>
                </div>
            </body>
            </html>
            """);
    }
    
    private void showAboutPage(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        
        out.println("""
            <!DOCTYPE html>
            <html>
            <head>
                <title>Sample Web App - About</title>
                <link rel="stylesheet" href="css/style.css">
            </head>
            <body>
                <nav class="navbar">
                    <h1>Sample Web App</h1>
                    <div class="nav-links">
                        <a href="/">Home</a>
                        <a href="/users">Users</a>
                        <a href="/products">Products</a>
                        <a href="/about" class="active">About</a>
                    </div>
                </nav>
                
                <div class="container">
                    <h1>About This Application</h1>
                    <p>This is a sample Java web application built with:</p>
                    <ul>
                        <li><strong>Java 21</strong> - Programming language</li>
                        <li><strong>Maven</strong> - Build tool and dependency management</li>
                        <li><strong>Jetty</strong> - Embedded web server</li>
                        <li><strong>SQLite</strong> - Lightweight database</li>
                        <li><strong>Servlets</strong> - Web request handling</li>
                    </ul>
                    
                    <h2>Features</h2>
                    <ul>
                        <li>4 pages: Home, Users, Products, About</li>
                        <li>SQLite database with sample data</li>
                        <li>Responsive design</li>
                        <li>Embedded Jetty server</li>
                    </ul>
                    
                    <p><a href="/">← Back to Home</a></p>
                </div>
            </body>
            </html>
            """);
    }
}
