<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sample Web App - Users</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <nav class="navbar">
        <h1>Sample Web App (Jakarta EE 10 + Tomcat)</h1>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/">Home</a>
            <a href="${pageContext.request.contextPath}/users" class="active">Users</a>
            <a href="${pageContext.request.contextPath}/products">Products</a>
            <a href="${pageContext.request.contextPath}/about">About</a>
        </div>
    </nav>
    
    <div class="container">
        <h1>Users Management</h1>
        
        <c:choose>
            <c:when test="${not empty users}">
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
                        <c:forEach var="user" items="${users}" varStatus="status">
                            <tr class="${status.index % 2 == 0 ? 'even' : 'odd'}">
                                <td>${user.id}</td>
                                <td><c:out value="${user.name}"/></td>
                                <td><c:out value="${user.email}"/></td>
                                <td><fmt:formatDate value="${user.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <p class="summary">Showing ${users.size()} user(s)</p>
            </c:when>
            <c:otherwise>
                <div class="no-data">
                    <p>No users found in the database.</p>
                </div>
            </c:otherwise>
        </c:choose>
        
        <p><a href="${pageContext.request.contextPath}/">← Back to Home</a></p>
    </div>
</body>
</html>