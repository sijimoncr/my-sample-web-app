<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sample Web App - About</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <h1>Sample Web App (Jakarta EE 10 + Tomcat)</h1>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/">Home</a>
            <a href="${pageContext.request.contextPath}/users">Users</a>
            <a href="${pageContext.request.contextPath}/products">Products</a>
            <a href="${pageContext.request.contextPath}/about" class="active">About</a>
        </div>
    </nav>
    
    <div class="container">
        <h1>About This Application</h1>
        
        <div class="about-section">
            <h2>Technology Stack</h2>
            <ul>
                <li><strong>Java 21</strong> - Modern LTS version with latest language features</li>
                <li><strong>Jakarta EE 10</strong> - Latest enterprise Java standard</li>
                <li><strong>Apache Tomcat 11</strong> - Embedded servlet container with JSP support</li>
                <li><strong>SQLite</strong> - Lightweight embedded database</li>
                <li><strong>JSP + JSTL</strong> - Server-side templating with tag libraries</li>
                <li><strong>Maven</strong> - Dependency management and build tool</li>
            </ul>
        </div>
        
        <div class="about-section">
            <h2>Features</h2>
            <ul>
                <li>Clean MVC architecture with JSP views</li>
                <li>Database integration with SQLite</li>
                <li>JSTL for dynamic content rendering</li>
                <li>Responsive CSS design</li>
                <li>Embedded server for easy deployment</li>
                <li>Eclipse IDE integration</li>
            </ul>
        </div>
        
        <div class="about-section">
            <h2>System Information</h2>
            <table class="info-table">
                <tr>
                    <td><strong>Java Version:</strong></td>
                    <td>${pageContext.servletContext.serverInfo}</td>
                </tr>
                <tr>
                    <td><strong>Servlet Version:</strong></td>
                    <td>${pageContext.servletContext.majorVersion}.${pageContext.servletContext.minorVersion}</td>
                </tr>
                <tr>
                    <td><strong>Context Path:</strong></td>
                    <td>${pageContext.request.contextPath}</td>
                </tr>
            </table>
        </div>
        
        <p><a href="${pageContext.request.contextPath}/">← Back to Home</a></p>
    </div>
</body>
</html>