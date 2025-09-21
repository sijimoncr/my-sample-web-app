package com.example.webapp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        
        String path = request.getServletPath();
        
        try {
            switch (path) {
                case "/":
                case "/home":
                    showHomePage(request, response);
                    break;
                case "/users":
                    showUsersPage(request, response);
                    break;
                case "/products":
                    showProductsPage(request, response);
                    break;
                case "/about":
                    showAboutPage(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
    
    private void showHomePage(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException, ServletException {
        
        List<User> users = DatabaseManager.getAllUsers();
        List<Product> products = DatabaseManager.getAllProducts();
        
        // Set attributes for JSP
        request.setAttribute("userCount", users.size());
        request.setAttribute("productCount", products.size());
        
        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
    
    private void showUsersPage(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException, ServletException {
        
        List<User> users = DatabaseManager.getAllUsers();
        
        // Set attributes for JSP
        request.setAttribute("users", users);
        
        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);
    }
    
    private void showProductsPage(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, SQLException, ServletException {
        
        List<Product> products = DatabaseManager.getAllProducts();
        
        // Set attributes for JSP
        request.setAttribute("products", products);
        
        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
    }
    
    private void showAboutPage(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        // Forward to JSP (no data needed)
        request.getRequestDispatcher("/WEB-INF/views/about.jsp").forward(request, response);
    }
}