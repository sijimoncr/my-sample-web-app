<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sample Web App - About</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <h1>Sample Web App</h1>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/">Home</a>
            <a href="${pageContext.request.contextPath}/users">Users</a>
            <a href="${pageContext.request.contextPath}/products">Products</a>
            <a href="${pageContext.request.contextPath}/about" class="active">About</a>
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
            <li><strong>JSP + JSTL</strong> - View templating</li>
            <li><strong>Servlets</strong> - Web request handling</li>
        </ul>
        
        <h2>Features</h2>
        <ul>
            <li>4 pages: Home, Users, Products, About</li>
            <li>SQLite database with sample data</li>
            <li>Responsive design with CSS</li>
            <li>Embedded Jetty server</li>
            <li>JSP templating with JSTL</li>
        </ul>
        
        <p><a href="${pageContext.request.contextPath}/">← Back to Home</a></p>
    </div>
</body>
</html>