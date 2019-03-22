<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Dawid
  Date: 17.03.2019
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<div class = "container">
    <div class="col-sm-6 col-md-4 col-md-ofset-4">
        <form class="form-signin" action = "register" method="post">
            <h2 class="form-signin-heading">Zarejestruj się</h2>
            <c:choose>
                <c:when test="${not empty sessionScope.registerFailed}">
                    <h4 class="form-signin-heading" style = "color: #dc3545">Podany email lub nazwa użytkownika jest zajęta</h4>
                </c:when>
            </c:choose>
            <input name = "inputEmail" type="email" class = "form-control" placeholder="Email" required autofocus/>
            <input name = "inputUsername" type="text" class="form-control" placeholder="Nazwa użytkownika" required autofocus/>
            <input name = "inputPassword" type="password" class="form-control" placeholder="Hasło" required/>
            <button class = "btn btn-lg btn-primary btn-block" type="submit">Zarejestruj</button>
        </form>
    </div>
</div>

<jsp:include page="fragment/footer.jspf"></jsp:include>

<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>

</body>

</html>
