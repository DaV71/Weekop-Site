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
<c:choose>
    <c:when test="${empty requestScope.discoveryToEdit}">
        <div class = "container">
            <div class="col-md-8 col-md-ofset-2">
                <form class="form-signin" action = "add" method="post">
                    <h2 class="form-signin-heading">Dodaj nowe znalezisko</h2>
                    <c:if test="${not empty sessionScope.addDiscoveryFailed}">
                        <h4 class="form-signin-heading" style = "color: #dc3545">Dane znalezisko już istnieje</h4>
                    </c:if>
                    <input name = "inputName" type="text" class = "form-control" placeholder="Co dodajesz?" required autofocus/>
                    <input name = "inputUrl" type="url" class="form-control" placeholder="URL" required autofocus/>
                    <textarea name = "inputDescription" rows="5" class="form-control" placeholder="Opis" required autofocus></textarea>
                    <button class = "btn btn-lg btn-primary btn-block" type="submit">Dodaj!</button>
                </form>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class = "container">
            <div class="col-md-8 col-md-ofset-2">
                <form class="form-signin" action = "edit?discovery_to_edit_id=${requestScope.discoveryToEdit.id}" method="post">
                    <h2 class="form-signin-heading">Edytuj znalezisko</h2>
                    <input name = "inputName" type="text" class = "form-control" value="${requestScope.discoveryToEdit.name}" required autofocus/>
                    <input name = "inputUrl" type="url" class="form-control" value="${requestScope.discoveryToEdit.url}"  required autofocus/>
                    <textarea name = "inputDescription" rows="5" class="form-control"  required autofocus><c:out value="${requestScope.discoveryToEdit.description}"></c:out></textarea>
                    <button class = "btn btn-lg btn-primary btn-block" type="submit">Edytuj</button>
                    <a href = "${pageContext.request.contextPath}/delete?discovery_to_delete_id=${discoveryToEdit.id}" class="btn btn-danger btn-xs">Usuń znalezisko</a>
                </form>
            </div>
        </div>
    </c:otherwise>

</c:choose>



<jsp:include page="fragment/footer.jspf"></jsp:include>

<script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>

</body>

</html>
