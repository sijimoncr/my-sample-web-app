<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sample Web App - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <h1>Sample Web App (Jakarta EE 10)</h1>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/" class="active">Home</a>
            <a href="${pageContext.request.contextPath}/users">Users</a>
            <a href="${pageContext.request.contextPath}/products">Products</a>
            <a href="${pageContext.request.contextPath}/about">About</a>
        </div>
    </nav>
    
    <div class="container">
        <h1>Welcome to Jakarta EE 10 Web App</h1>
        <p>A modern Java web application running on Jetty 12 with SQLite database</p>
        
        <div class="stats">
            <div class="stat-card">
                <h3>Users</h3>
                <div class="number">${userCount}</div>
                <a href="${pageContext.request.contextPath}/users">View Users</a>
            </div>
            
            <div class="stat-card">
                <h3>Products</h3>
                <div class="number">${productCount}</div>
                <a href="${pageContext.request.contextPath}/products">View Products</a>
            </div>
        </div>
    </div>
</body>
</html>