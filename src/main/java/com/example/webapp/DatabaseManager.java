package com.example.webapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:sample-app.db";
    
    public static void initializeDatabase() {
        try (Connection conn = getConnection()) {
            createTables(conn);
            insertSampleData(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    
    private static void createTables(Connection conn) throws SQLException {
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT NOT NULL UNIQUE,
                created_at DATETIME DEFAULT CURRENT_TIMESTAMP
            )
        """;
        
        String createProductsTable = """
            CREATE TABLE IF NOT EXISTS products (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                price DECIMAL(10,2) NOT NULL,
                description TEXT,
                created_at DATETIME DEFAULT CURRENT_TIMESTAMP
            )
        """;
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createProductsTable);
        }
    }
    
    private static void insertSampleData(Connection conn) throws SQLException {
        String checkUsers = "SELECT COUNT(*) FROM users";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkUsers)) {
            if (rs.getInt(1) > 0) {
                return; // Data already exists
            }
        }
        
        String insertUser = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertUser)) {
            pstmt.setString(1, "John Doe");
            pstmt.setString(2, "john@example.com");
            pstmt.executeUpdate();
            
            pstmt.setString(1, "Jane Smith");
            pstmt.setString(2, "jane@example.com");
            pstmt.executeUpdate();
        }
        
        String insertProduct = "INSERT INTO products (name, price, description) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertProduct)) {
            pstmt.setString(1, "Laptop");
            pstmt.setDouble(2, 999.99);
            pstmt.setString(3, "High-performance laptop");
            pstmt.executeUpdate();
            
            pstmt.setString(1, "Mouse");
            pstmt.setDouble(2, 29.99);
            pstmt.setString(3, "Wireless mouse");
            pstmt.executeUpdate();
        }
    }
    
    public static List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY name";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setCreatedAt(rs.getString("created_at"));
                users.add(user);
            }
        }
        return users;
    }
    
    public static List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY name";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setCreatedAt(rs.getString("created_at"));
                products.add(product);
            }
        }
        return products;
    }
}
