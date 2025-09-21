<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sample Web App - Products</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <h1>Sample Web App (Jakarta EE 10)</h1>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/">Home</a>
            <a href="${pageContext.request.contextPath}/users">Users</a>
            <a href="${pageContext.request.contextPath}/products" class="active">Products</a>
            <a href="${pageContext.request.contextPath}/about">About</a>
        </div>
    </nav>
    
    <div class="container">
        <h1>Products</h1>
        <div class="products-grid">
            <c:forEach var="product" items="${products}">
                <div class="product-card">
                    <h3>${product.name}</h3>
                    <div class="price">$<fmt:formatNumber value="${product.price}" pattern="0.00"/></div>
                    <p>${product.description}</p>
                    <small>Added: <fmt:formatDate value="${product.createdAt}" pattern="yyyy-MM-dd"/></small>
                </div>
            </c:forEach>
        </div>
        <p><a href="${pageContext.request.contextPath}/">← Back to Home</a></p>
    </div>
</body>
</html>