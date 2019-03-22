<%--
  Created by IntelliJ IDEA.
  User: Dawid
  Date: 21.03.2019
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name ="viewport" content="width=device-width,initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" type = "text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" type="text/css" rel="stylesheet">
    <title>Weekop</title>

</head>
<body>

<jsp:include page="fragment/navbar.jspf"></jsp:include>


<table class="table table-striped">
    <thead>
    <tr>
        <th scope="col">Id</th>
        <th scope="col">Nazwa użytkownika</th>
        <th scope="col">Czynność</th>
    </tr>
    </thead>
    <tbody>
<c:if test="${not empty requestScope.users }">
    <c:forEach var="user" items="${requestScope.users }">
    <tr>
        <th scope="row"><c:out value="${user.id}"></c:out></th>
        <td><c:out value="${user.username}"></c:out></td>
        <c:choose>
            <c:when test="${user.active}">
                <td><a href = "${pageContext.request.contextPath}/ban?active=0&id=<c:out value="${user.id}"></c:out>" class="btn btn-danger btn-xs">Zbanuj</a></td>
            </c:when>
            <c:otherwise>
                <td><a href = "${pageContext.request.contextPath}/ban?active=1&id=<c:out value="${user.id}"></c:out>" class="btn btn-primary btn-xs">Aktywuj</a></td>
            </c:otherwise>
        </c:choose>
    </tr>
    </c:forEach>
</c:if>
    </tbody>
</table>

<jsp:include page="fragment/footer.jspf"></jsp:include>

<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>

</body>

</html>